<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/m_search.css" />
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>

<!-- 김성훈 . 공통코드 -->
<%@page import="com.ant.common.code.CommonCode"%>
<!-- 김성훈 . 공통함수 -->
<script src="js/memberCommon.js" type="text/javascript" ></script>


<!-- 김성훈 . 공통함수 -->
<script src="js/form.js" type="text/javascript" ></script>

<%
	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

String Date = new java.text.SimpleDateFormat ("yyyyMMdd").format(new java.util.Date());
String Time = new java.text.SimpleDateFormat ("HHmmss").format(new java.util.Date());
Calendar calendar = Calendar.getInstance();
java.util.Date date = calendar.getTime();

String yyyy = Integer.parseInt((new SimpleDateFormat("yyyy").format(date)))+"";
String month= Integer.parseInt((new SimpleDateFormat("MM").format(date)))+""; 
String mmdd = "0101";
String startdate = yyyy+mmdd;
String startyear = (new SimpleDateFormat("yyyy").format(date));
String enddate   = (new SimpleDateFormat("yyyyMMdd").format(date));
String startmonth= (new SimpleDateFormat("MM").format(date));
String startdate1 = yyyy+startmonth+"01";


JSONArray comList	= (CommonCode.getInstance()).getCodeList();
%>
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>


<script type="text/javascript">

function depositchange(form) {
	var code_bran = '<%=g_bran%>';

	document.all.tab_5_1.style.display = "none";
	document.all.tab_5_2.style.display = "none";
	
	if( form.d1.checked == true){
		document.all.tab_5_1.style.display = "";
		document.all.receipt_month2.style.display = "";
		
	}else if( form.d2.checked == true){
		document.all.receipt_month2.style.display = "none";
		if(code_bran == '01')	document.all.tab_5_2.style.display = "";
		else					alert("예수금현황의 평생회원 조회는 중앙회에서 만 조회 가능합니다.");
	}
}

function tab(nTabCnt) {

	var code_bran = '<%=g_bran%>';

	document.all.name_1.className = "";
	document.all.name_2.className = "";
	document.all.name_5.className = "";
	document.all.name_3.className = "";
	document.all.name_4.className = "";
	document.all.name_6.className = "";
	
	document.all.tab_1.style.display = "none";
	document.all.tab_2.style.display = "none";
	document.all.tab_5.style.display = "none";
 	document.all.tab_3.style.display = "none";
	document.all.tab_4.style.display = "none";
	document.all.tab_6.style.display = "none";
	
	if(nTabCnt == '1'){
		document.all.name_1.className = "overMenu";
		document.all.tab_1.style.display = "";
	}
	if(nTabCnt == '2'){
		document.all.name_2.className = "overMenu";
		document.all.tab_2.style.display = "";
	}
	if(nTabCnt == '3'){
		document.all.name_3.className = "overMenu";
		if(code_bran == '01')	document.all.tab_3.style.display = "";
		else					alert("평생회비 예수금은 중앙회에서 만 조회 가능합니다.");
		
	}
	if(nTabCnt == '4'){
		document.all.name_4.className = "overMenu";
		if(code_bran == '01')	document.all.tab_4.style.display = "";
		else					alert("평생회비 예수금 현황은 중앙회에서 만 조회 가능합니다.");
	}
	if(nTabCnt == '5'){
		document.all.name_5.className = "overMenu";
		document.all.tab_5.style.display = "";
	}
	if(nTabCnt == '6'){
		document.all.name_6.className = "overMenu";
		if(code_bran == '01')	document.all.tab_6.style.display = "";
		else					alert("산하단체 예수금 현황은 중앙회에서 만 조회 가능합니다.");
	}

}


$(document).ready(function(){
	
	//전체 권한 여부
	powerinit("104", eval(<%=userpowerm1%>) );



	document.all.name_1.className = "overMenu";
	document.all.name_2.className = "";
 	document.all.name_3.className = "";
	document.all.name_4.className = "";
	document.all.name_5.className = "";
	document.all.name_6.className = ""; 
	
	document.all.tab_1.style.display = "block";
	document.all.tab_2.style.display = "none";
 	document.all.tab_3.style.display = "none";
	document.all.tab_4.style.display = "none";
	document.all.tab_5.style.display = "none";
	document.all.tab_6.style.display = "none";

	document.all.tab_5_1.style.display = "block";
	document.all.tab_5_2.style.display = "none";


	var codeList	=eval(<%=comList%>);		//전체공통쿼리
	
	var cnt				=0;
	var groupcode		= '';
	var detcode		= '';
	var detcodename	= '';
	var tempce1		= '';

	for( var i=0; i<codeList.length; i++) {
	
		groupcode		= codeList[i].groupcode;
		detcode			= codeList[i].detcode;
		detcodename		= codeList[i].detcodename;
		tempcd1			= codeList[i].tempcd1;

		if(cnt==0) {
			detcode = '';
			detcodename = '선택';
			i--;
		}

		if(groupcode=='036')	 {
			document.memberDeposit_3.receipt_year.options[cnt]=new Option(detcodename , detcode , false, false);
			document.memberDeposit_4.receipt_year.options[cnt]=new Option(detcodename , detcode , false, false);
			document.memberDeposit_5.receipt_year.options[cnt]=new Option(detcodename , detcode , false, false);
			document.memberDeposit_6.receipt_year.options[cnt]=new Option(detcodename , detcode , false, false);
		}
		if(groupcode=='007')	 {
			if(cnt==0) tempcd1 = '전체';
			document.memberDeposit_1.code_bran.options[cnt]=new Option(tempcd1 , detcode , false, false);
			document.memberDeposit_2.code_bran.options[cnt]=new Option(tempcd1 , detcode , false, false);
		}
		if(groupcode=='039')	 {
			document.memberDeposit_6.receipt_month.options[cnt]=new Option(detcodename , detcode , false, false);

			if(cnt==0) detcodename = '전체';
			document.memberDeposit_5.receipt_month.options[cnt]=new Option(detcodename , detcode , false, false);
		}
		cnt++;
		if(codeList.length == i+1 || codeList[i+1].groupcode != groupcode)	cnt=0;

	}

	document.memberDeposit_1.code_bran.value = '<%=g_bran%>';
	document.memberDeposit_2.code_bran.value = '<%=g_bran%>';

	if( document.memberDeposit_1.code_bran.value == '01'){
		
		document.memberDeposit_1.code_bran.disabled=false;
		document.memberDeposit_1.code_bran.value='';
		document.memberDeposit_2.code_bran.disabled=false;
		document.memberDeposit_2.code_bran.value='';
	}
	

	document.memberDeposit_3.receipt_year.value="<%=startyear%>";
	document.memberDeposit_4.receipt_year.value="<%=startyear%>";
	document.memberDeposit_5.receipt_year.value="<%=startyear%>";
	document.memberDeposit_6.receipt_year.value="<%=startyear%>";
	document.memberDeposit_6.receipt_month.value="<%=startmonth%>";

	
	//tab_1 : 예수금 집계표 그리드
	jQuery("#list1").jqGrid({
		url: "memberDeposit.do?method=memberDeposit",
		datatype: "json",
		mtype: 'POST',
		height:'500',
		//width:'1100',
		sortable:true,
		shrinkToFik:true, 
		abGrid:true,
		recordpos:'right',
		viewrecords:true,
		
		/* 2022.07.19 C신규특별회원 / C기특별회원 컬럼 삭제 및 컬럼 순서 변경 */
		colNames	: [ '지부명', '기 취업회원', '신규 취업회원', '기 미취업회원', '신규 미취업회원', ' 기 특별회원', '신규 특별회원', '학생회원', 'C기취업회원', 'C신규취업회원', 'C기미취업회원', 'C신규미취업회원', 'C기취업회원(년)', 'C신규취업회원(년)', '인원수', '지부예수금'],
		colModel	: [
	        	  		{name:'tempcd1'			, index:'tempcd1 asc, invdate'		, align:"center"	, sortable:false, width:100}					// 지부명
				      , {name:'col1'					, index:'col1'			      , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// 기 취업회원
				      , {name:'col2'		    	  	, index:'col2'			      , width:66	, align:"right"		, sortable:false, formatter:'integer'}	 	// 신규 취업회원
				      , {name:'col3'		    	  	, index:'col3'			 	  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// 기 미취업회원
				      , {name:'col4'		   		  	, index:'col4'			 	  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// 신규 미취업회원
				      , {name:'col5'		    	  	, index:'col5'				  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// 기 특별회원
				      , {name:'col6'		    	  	, index:'col6'		 		  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// 신규 특별회원
				      , {name:'col7'		          	, index:'col7'				  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// 학생회원
				      
				      , {name:'col8'           			, index:'col8'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기취업회원
				      , {name:'col9'		          	, index:'col9'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규취업회원
				      , {name:'col10'	          		, index:'col10'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기미취업회원
				      , {name:'col11'		          	, index:'col11'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규미취업회원
				      , {name:'col12'		          	, index:'col12'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기취업회원(년)
				      , {name:'col13'		          	, index:'col13'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규취업회원(년)
				      
				      /* , {name:'col14'	          		, index:'col14'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규특별회원
				      , {name:'col15'	          		, index:'col15'			  , width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기특별회원 */
				      
				      , {name:'totcount'		  		, index:'totcount'	      , width:60	, align:"right"		, sortable:false, formatter:'integer'}		// 인원수
				      , {name:'bran_money'  	, index:'bran_money'	  , width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 지부예수금
				      ],
				      
		rowNum:10000000,
		sortname: 'id',
		sortorder: 'desc',
		pager: '#pager1',
		viewrecords: true,//토탈카운터 나오는것
		//caption: 'kda_grid',
		altRows:true,
		altclass:'myAltRowClass',
		rownumbers : true,
		caption:"예수금 집계표"
	});
	jQuery("#list1").jqGrid('navGrid','#pager1',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list1").setGridWidth(1100,false);
	
	


	//tab_2 : 지부별 예수금 현황
	jQuery("#list2").jqGrid({
		url: "memberDeposit.do?method=memberDeposit",
		datatype: "json",
		mtype: 'POST',
		height:'500',
		//width:'1100',
		sortable:true,
		shrinkToFik:true, 
		abGrid:true,
		recordpos:'right',
		viewrecords:true,
		
		/* 2022.07.19 C신규특별회원 / C기특별회원 컬럼 삭제 및 컬럼 순서 변경 */
		colNames	: [ '지부명', '기 취업회원', '신규 취업회원', '기 미취업회원', '신규 미취업회원', ' 기 특별회원', '신규 특별회원', '학생회원', 'C기취업회원', 'C신규취업회원', 'C기미취업회원', 'C신규미취업회원', 'C기취업회원(년)', 'C신규취업회원(년)', '인원수', '본회 예수금 집계'],
		colModel	: [
							  {name:'tempcd1'	    ,index:'tempcd1 asc, invdate'	, align:"center"		, sortable:false, width:100}					// 지부명
							, {name:'col1'				,index:'col1'				, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 기 취업회원
							, {name:'col2'				,index:'col2'				, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 신규 취업회원
							, {name:'col3'				,index:'col3'				, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 기 미취업회원
							, {name:'col4'				,index:'col4'				, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 신규 미취업회원
							, {name:'col5'				,index:'col5'				, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 기 특별회원
							, {name:'col6'				,index:'col6'				, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 신규 특별회원
							, {name:'col7'				,index:'col7'				, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 학생회원
							
							, {name:'col8'    	 		,index:'col8'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기취업회원
							, {name:'col9'				,index:'col9'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규취업회원
						    , {name:'col10'	     		,index:'col10'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기미취업회원
						    , {name:'col11'				,index:'col11'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규미취업회원
						    , {name:'col12'				,index:'col12'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기취업회원(년)
						    , {name:'col13'				,index:'col13'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규취업회원(년)
						    
						    /* , {name:'col14'	    		,index:'col14'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C신규특별회원
						    , {name:'col15'	    		,index:'col15'			, width:66	, align:"right"		, sortable:false, formatter:'integer'}		// C기특별회원 */
						    
							, {name:'totcount'		,index:'totcount'		, width:90	, align:"right"		, sortable:false, formatter:'integer'}		// 인원수
							, {name:'bran_money' ,index:'bran_money'	, width:140	, align:"right", sortable:false, formatter:'integer'}		// 본회 예수금 집계
						 ],	 // colModel End
					
		rowNum:10000000,
		sortname: 'id',
		sortorder: 'desc',
		pager: '#pager2',
		viewrecords: true,//토탈카운터 나오는것,
		caption: 'kda_grid',
		altRows:true,
		altclass:'myAltRowClass',
		rownumbers : true,
		caption:'지부별 예수금 집계표'
	});
	jQuery("#list2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list2").setGridWidth(1100,false);

	
	//tab_3 : 평생회비 예수금
	jQuery("#list3").jqGrid({
		url: "memberDeposit.do?method=memberDeposit",
		datatype: "json",
		mtype: 'POST',
		//width:'1100',
		height:'500',
		sortable:true,
		shrinkToFik:true, 
		abGrid:true,
		recordpos:'right',
		viewrecords:true,
		
		colNames	: [ '성명',
// 		        	    JUMIN_DEL
// 		        	    '주민번호',
		        	    '생년월일',
		        	    '면허번호', '회원 가입일', '인증일', '차수', '소속지부', '근무처명', '연락처', '이메일'],
		colModel		: [{name:'pers_name'	,index:'pers_name asc, invdate'		, align:"center", sortable:false, width:80}	//성명
// 		        		   JUMIN_DEL
// 						, {name:'pers_no'		,index:'pers_no'		, width:130	, align:"center", sortable:false}//주민번호
						, {name:'pers_birth'		,index:'pers_birth'		, width:130	, align:"center", sortable:false}//생년월일
						, {name:'lic_no'		,index:'lic_no'			, width:80	, align:"center", sortable:false}//면허번호
						, {name:'regi_dt'		,index:'regi_dt'		, width:90	, align:"center", sortable:false}//회원가입일
						, {name:'pers_lert_st'	,index:'pers_lert_st'	, width:90	, align:"center", sortable:false}//입금일
						, {name:'pers_mem_order' ,index:'pers_mem_order' ,width:40	, align:"center", sortable:false}//차수
						, {name:'code_bran'		,index:'code_bran'		, width:100	, align:"center", sortable:false}//소속지부
						, {name:'company_name'	,index:'company_name'	, width:120	, align:"center", sortable:false}//근무처명
						, {name:'pers_tel'		,index:'pers_tel'		, width:100	, align:"center", sortable:false}//연락처
						, {name:'email'			,index:'email'			, width:200	, align:"center", sortable:false}],//이메일
		rowNum:20,
		sortname: 'id',
		sortorder: 'desc',
		pager: '#pager3',
		viewrecords: true,//토탈카운터 나오는것
		caption: 'kda_grid',
		altRows:true,
		altclass:'myAltRowClass',
		rownumbers : true,
		caption:'평생회비 예수금'
	});
	jQuery("#list3").jqGrid('navGrid','#pager3',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list3").setGridWidth(1100,false);

	
	//tab_4 : 평생회비 예수금 현황
	
	jQuery("#list4").jqGrid({
		url: "memberDeposit.do?method=memberDeposit",
		datatype: "json",
		mtype: 'POST',
		//width:'1100',
		height:'500',
		sortable:true,
		shrinkToFik:true, 
		abGrid:true,
		recordpos:'right',
		viewrecords:true,
		
		colNames	: [ '가입년도', '차수', '서울', '부산', '인천', '경기', '강원', '충북', '대전충남세종', '전북', '광주전남', '대구경북', '경남', '울산', '제주', '총합'],
		colModel		: [{name:'receipt_year'	,index:'receipt_year asc, invdate'		, align:"center", sortable:false, width:80}
						, {name:'pers_mem_order',index:'pers_mem_order'	, width:60	, align:"center", sortable:false, formatter:'integer'}
						, {name:'col2'			,index:'col2'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col3'			,index:'col3'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col4'			,index:'col4'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col5'			,index:'col5'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col6'			,index:'col6'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col7'			,index:'col7'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col8'			,index:'col8'			, width:90	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col9'			,index:'col9'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col10'			,index:'col10'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col11'			,index:'col11'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col12'			,index:'col12'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col13'			,index:'col13'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'col14'			,index:'col14'			, width:60	, align:"right", sortable:false, formatter:'integer'}
						, {name:'totcount'		,index:'totcount'		, width:60	, align:"right", sortable:false, formatter:'integer'}],
		rowNum:10000000,
		sortname: 'id',
		sortorder: 'desc',
		pager: '#pager4',
		viewrecords: true,//토탈카운터 나오는것
		caption: 'kda_grid',
		altRows:true,
		altclass:'myAltRowClass',
		rownumbers : true,
		caption:'평생회비 예수금 현황'
	});
	jQuery("#list4").jqGrid('navGrid','#pager4',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list4").setGridWidth(1100,false);

	
	//tab_5_1
	jQuery("#list5_1").jqGrid({
			url: "memberDeposit.do?method=memberDeposit",
			datatype: "json",
			mtype: 'POST',
			//width:'1100',
			height:'500',
			sortable:true,
			shrinkToFik:true, 
			abGrid:true,
			recordpos:'right',
			viewrecords:true,
			
			/* 2022.07.19 C신규특별회원 / C기특별회원 컬럼 삭제 및 컬럼 순서 변경 */
			//           1   ,   2  ,    	3    ,	4	,	5  ,	6,			7,			8,		9		,	10	 , 	11	,		12		,	13,		14,			15,			16,				17,			18,		     19  ;
			colNames:[ '번호', '입금장소코드', '입금장소', '지부코드', '구분', '신규 취업회원', '신규 미취업회원', '기 취업회원', '기 미취업회원', '학생회원', '신규 특별회원', '기 특별회원', 'C기취업회원', 'C신규취업회원', 'C기미취업회원', 'C신규미취업회원', 'C기취업회원(년)', 'C신규취업회원(년)', '소계'],
			colModel:[
			          		{name:'number'		 		,index:'number asc, invdate'					, align:"center"	, sortable:false, width:60, hidden:true	} 	// 1.  번호
						  , {name:'code_receipt'		,index:'code_receipt'			, width:60		, align:"center"	, sortable:false, hidden:true					}	// 2.  입금장소코드
						  , {name:'detcodename'			,index:'detcodename'			, width:100		, align:"center"	, sortable:false										}	// 3.  입금장소
						  , {name:'code_bran'			,index:'code_bran'				, width:60		, align:"right"		, sortable:false, hidden:true					}	// 4.  지부코드
						  , {name:'tempcd1'				,index:'tempcd1'				, width:90		, align:"center"	, sortable:false										}	// 5.  구분
						  , {name:'col1'				,index:'col1'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 6.  신규 취업회원
						  , {name:'col2'				,index:'col2'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 7. 	 신규 미취업회원
						  , {name:'col5'				,index:'col5'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 8.  기 취업회원
						  , {name:'col6'				,index:'col6'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 9.  기 미취업회원
						  , {name:'col7'				,index:'col7'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 10.학생 회원
						  , {name:'col8'				,index:'col8'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 11.신규특별회원
						  , {name:'col9'				,index:'col9'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 12.기 특별회원
						  
						  , {name:'col41'				,index:'col41'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 15.C신규취업회원
						  , {name:'col45'				,index:'col45'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 17.C기취업회원
						  , {name:'col46'				,index:'col46'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 18.C기미취업회원
						  , {name:'col42'				,index:'col42'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 16.C신규미취업회원
						  , {name:'col40'				,index:'col40'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 14.C기취업회원(년)
						  , {name:'col39'				,index:'col39'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 13.C신규취업회원(년)
						  
						  /*, {name:'col48'				,index:'col48'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 19.C신규특별회원
						  , {name:'col49'				,index:'col49'					, width:90 		, align:"right"		, sortable:false, formatter:'integer'		}	// 20.C기특별회원 */
						  , {name:'totcount'			,index:'totcount'				, width:100		, align:"right"		, sortable:false, formatter:'integer'		}	// 21.소계
						 ],	// colModel End
					
			rowNum:10000000,
			sortname: 'id',
			sortorder: 'desc',
			pager: '#pager5_1',
			viewrecords: true,//토탈카운터 나오는것
			caption: 'kda_grid',
			altRows:true,
			altclass:'myAltRowClass',
			rownumbers : true,
			caption:'예수금 현황>연회원'
		});
	jQuery("#list5_1").jqGrid('navGrid','#pager5_1',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list5_1").setGridWidth(1100,false);
	
	//tab_5_2
	jQuery("#list5_2").jqGrid({
		url: "memberDeposit.do?method=memberDeposit",
		datatype: "json",
		mtype: 'POST',
		//width:'1100',
		height:'500',
		sortable:true,
		shrinkToFik:true, 
		abGrid:true,
		recordpos:'right',
		viewrecords:true,
		
		colNames:[ '번호', '지부코드', '구분', '회원코드', '회원종류', '예수금', '인원수', '예수금 집계'],
		colModel:[{name:'number'		,index:'number asc, invdate'		, align:"center", sortable:false, width:80, hidden:true}
				, {name:'code_bran'		,index:'code_bran'		, width:60	, align:"right", sortable:false	, hidden:true	}
				, {name:'tempcd1'		,index:'tempcd1'		, width:100	, align:"center", sortable:false		}
				, {name:'code_member'	,index:'code_member'	, width:60	, align:"right", sortable:false	, hidden:true	}
				, {name:'detcodename'	,index:'detcodename'	, width:150	, align:"center", sortable:false		}
				, {name:'bran_money'	,index:'bran_money'		, width:100	, align:"right", sortable:false	, formatter:'integer'	}
				, {name:'totCount'		,index:'totCount'		, width:100	, align:"right", sortable:false, formatter:'integer'}
				, {name:'totBran_money'	,index:'totBran_money'	, width:100	, align:"right", sortable:false, formatter:'integer'}],
		rowNum:10000000,
		sortname: 'id',
		sortorder: 'desc',
		pager: '#pager5_2',
		viewrecords: true,//토탈카운터 나오는것
		caption: 'kda_grid',
		altRows:true,
		altclass:'myAltRowClass',
		rownumbers : true,
		caption:'예수금 현황>평생회원'
	});
	jQuery("#list5_2").jqGrid('navGrid','#pager5_2',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list5_2").setGridWidth(1100,false);

	//tab_6
	jQuery("#list6").jqGrid({
		url: "memberDeposit.do?method=memberDeposit",
		datatype: "json",
		mtype: 'POST',
		//width:'1100',
		height:'500',
		sortable:true,
		shrinkToFik:true, 
		abGrid:true,
		recordpos:'right',
		viewrecords:true,
		
		colNames:[ '번호', '지부코드', '구분', '회원코드', '회원종류', '예수금', '인원수', '예수금 집계'],
		colModel:[{name:'number'		,index:'number asc, invdate'		, align:"center", sortable:false, width:80, hidden:true}
				, {name:'code_bran'		,index:'code_bran'		, width:60	, align:"right", sortable:false	, hidden:true	}
				, {name:'tempcd1'		,index:'tempcd1'		, width:100	, align:"center", sortable:false		}
				, {name:'code_member'	,index:'code_member'	, width:60	, align:"right", sortable:false	, hidden:true	}
				, {name:'detcodename'	,index:'detcodename'	, width:150	, align:"center", sortable:false		}
				, {name:'sub_dues'		,index:'sub_dues'		, width:100	, align:"right", sortable:false	, formatter:'integer'	}
				, {name:'totCount'		,index:'totCount'		, width:100	, align:"right", sortable:false, formatter:'integer'}
				, {name:'totSub_dues'	,index:'totSub_dues'	, width:130	, align:"right", sortable:false, formatter:'integer'}],
		rowNum:10000000,
		sortname: 'id',
		sortorder: 'desc',
		pager: '#pager6',
		viewrecords: true,//토탈카운터 나오는것
		caption: 'kda_grid',
		altRows:true,
		altclass:'myAltRowClass',
		rownumbers : true,
		caption:'산하단체 예수금 현황'
	});
	jQuery("#list6").jqGrid('navGrid','#pager6',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list6").setGridWidth(1100,false);
	

	$(function() {	$( "#datepicker_11" ).datepicker();		});
	$(function() {	$( "#datepicker_12" ).datepicker();		});
	$(function() {	$( "#datepicker_21" ).datepicker();		});
	$(function() {	$( "#datepicker_22" ).datepicker();		});
	$(function() {	$( "#datepicker_31" ).datepicker();		});
	$(function() {	$( "#datepicker_32" ).datepicker();		});
	$(function() {	$( "#datepicker_41" ).datepicker();		});
	$(function() {	$( "#datepicker_42" ).datepicker();		});
	$(function() {	$( "#datepicker_51" ).datepicker();		});
	$(function() {	$( "#datepicker_52" ).datepicker();		});
	$(function() {	$( "#datepicker_61" ).datepicker();		});
	$(function() {	$( "#datepicker_62" ).datepicker();		});

});


function goSearch(form, nTabCnt){

	if(nTabCnt == '1'){
		goTabSearch_1(form);
	}else if(nTabCnt == '2'){
		goTabSearch_2(form);
	}else if(nTabCnt == '3'){ 
		goTabSearch_3(form);
	}else if(nTabCnt == '4'){ 
		goTabSearch_4(form);
	}else if(nTabCnt == '5'){ 
		goTabSearch_5(form);
	}else if(nTabCnt == '6'){
		goTabSearch_6(form);
	}
}

//tab_1 예수금 집계표
function goTabSearch_1(form) {

	var param = '';
	var startdate  = form.startdate.value;
	var enddate	= form.enddate.value;
	
	if(startdate > enddate){
		alert("시작일이 종료일 보다 커서는 안됩니다.");
		return;
	}
	
	if(form.code_bran.value != '') param += "&code_bran="+form.code_bran.value;
	
	param+="&nTabCnt=1&code_receipt=1&startdate="+startdate+"&enddate="+enddate;
	$('#list1').jqGrid('clearGridData');
	jQuery("#list1").setGridParam({url:"memberDeposit.do?method=memberDepositList"+param}).trigger("reloadGrid");	
}

//tab_2 지부별 예수금 현황
function goTabSearch_2(form) {

	var param = '';
	var startdate  = form.startdate.value;
	var enddate	= form.enddate.value;
	
	if(startdate > enddate){
		alert("시작일이 종료일 보다 커서는 안됩니다.");
		return;
	}
	
	if(form.code_bran.value != '') param += "&code_bran="+form.code_bran.value;
	
	param+="&nTabCnt=2&code_receipt=2&startdate="+startdate+"&enddate="+enddate;

	$('#list2').jqGrid('clearGridData');
	jQuery("#list2").setGridParam({url:"memberDeposit.do?method=memberDepositList"+param}).trigger("reloadGrid");		

 }

//tab_3 평생회비 예수금
function goTabSearch_3(form) {
	 
	var param = '&nTabCnt=3';
	var receipt_year		= form.receipt_year.value;
	var pers_mem_order		= form.pers_mem_order.value;
	
	if( receipt_year == '' && pers_mem_order == '') {
		alert( "조회년도 및 차수 중 한가지는 선택해야 합니다.");
		return;
	}

	if( receipt_year		!= '' ) param+="&receipt_year="+receipt_year;
	if( pers_mem_order	!= '' ) param+="&pers_mem_order="+pers_mem_order;

	$('#list3').jqGrid('clearGridData');
	jQuery("#list3").setGridParam({url:"memberDeposit.do?method=memberDepositList"+param}).trigger("reloadGrid");	
}




function goTabSearch_4(form) {
	var receipt_year   = form.receipt_year.value;
	
	if(receipt_year == '') {
		alert("검색년도를 선택해 주세요");
		return;
	}
	$('#list4').jqGrid('clearGridData');
	jQuery("#list4").setGridParam({url:"memberDeposit.do?method=memberDepositList&nTabCnt=4&receipt_year="+receipt_year}).trigger("reloadGrid");	
}


function goTabSearch_5(form) {
	var receipt_year   = form.receipt_year.value;
	var receipt_month  = form.receipt_month.value;

	
	
	if(receipt_year == '') {
		alert("검색년도를 선택해 주세요");
		return;
	}

//	alert(receipt_dt);
	if( form.d1.checked == true){
		var receipt_dt     = receipt_year+receipt_month;
		
		$('#list5_1').jqGrid('clearGridData');
		jQuery("#list5_1").setGridParam({url:"memberDeposit.do?method=memberDepositList&nTabCnt=5_1&receipt_dt="+receipt_dt}).trigger("reloadGrid");
	}else if(form.d2.checked == true){
		var receipt_dt     = receipt_year;
		
		$('#list5_2').jqGrid('clearGridData');
		jQuery("#list5_2").setGridParam({url:"memberDeposit.do?method=memberDepositList&nTabCnt=5_2&receipt_dt="+receipt_dt}).trigger("reloadGrid");
	}
}


function goTabSearch_6(form) {
	var receipt_year   = form.receipt_year.value;
	var receipt_month  = form.receipt_month.value;
	

	if(receipt_year == '' || receipt_month == '') {
		alert("검색년도와 월을 선택해 주세요");
		return;
	}
	
	var receipt_dt     = receipt_year+receipt_month;
	
	var param = "";
	if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
	
	$('#list6').jqGrid('clearGridData');
	jQuery("#list6").setGridParam({url:"memberDeposit.do?method=memberDepositList&nTabCnt=6&receipt_dt="+receipt_dt+param}).trigger("reloadGrid");	
}






function excelDown(form, nTabCnt){
	
	var rownum = jQuery("#list").jqGrid('getGridParam','records')
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}

	if(nTabCnt == '1'){
		excelDown_1(form);
	}else if(nTabCnt == '2'){
		excelDown_2(form);
	}else if(nTabCnt == '3'){ 
		excelDown_3(form);
	}else if(nTabCnt == '4'){ 
		excelDown_4(form);
	}else if(nTabCnt == '5'){ 
		excelDown_5(form);
	}else if(nTabCnt == '6'){
		excelDown_6(form);
	}
}

//tab_1 예수금 집계표
function excelDown_1(form) {

	var rownum = jQuery("#list1").jqGrid('getGridParam','records')
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}
	
	var param = '';
	var startdate  = form.startdate.value;
	var enddate	= form.enddate.value;
	
	if(startdate > enddate){
		alert("시작일이 종료일 보다 커서는 안됩니다.");
		return;
	}
	
	if(form.code_bran.value != '') param += "&code_bran="+form.code_bran.value;

	param+="&nTabCnt=1&code_receipt=1&startdate="+startdate+"&enddate="+enddate;
	form.target="frm";
	form.action="memberDeposit.do?method=memberDepositExcel"+param;
	form.submit();	
}

//tab_2 지부별 예수금 현황
function excelDown_2(form) {

	var rownum = jQuery("#list2").jqGrid('getGridParam','records')
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}

	var param = '';
	var startdate  = form.startdate.value;
	var enddate	= form.enddate.value;
	
	if(startdate > enddate){
		alert("시작일이 종료일 보다 커서는 안됩니다.");
		return;
	}
	
	if(form.code_bran.value != '') param += "&code_bran="+form.code_bran.value;
	
	param+="&nTabCnt=2&code_receipt=2&startdate="+startdate+"&enddate="+enddate;


	form.target="frm";
	form.action="memberDeposit.do?method=memberDepositExcel"+param;
	form.submit();		

 }

//tab_3 평생회비 예수금
function excelDown_3(form) {

	var rownum = jQuery("#list3").jqGrid('getGridParam','records')
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}
	
	var param = "";
	var receipt_year		= form.receipt_year.value;
	var pers_mem_order		= form.pers_mem_order.value;
	
	if( receipt_year == '' && pers_mem_order == '') {
		alert( "조회년도 및 차수 중 한가지는 선택해야 합니다.");
		return;
	}
	
	param += "&nTabCnt=3";
	if( receipt_year	!= '' ) param+="&receipt_year="+receipt_year;
	if( pers_mem_order	!= '' ) param+="&pers_mem_order="+pers_mem_order;

	param+="&register="+"<%=g_name%>";


	var url="memberDeposit.do?method=pers_check"+param;
// 	JUMIN_DEL
// 	window.open(url,"pers_no","width=400, height=500, left=480, top=200");
	window.open(url,"pers_check","width=400, height=500, left=480, top=200");

/* 	form.target="frm";
	form.action="memberDeposit.do?method=memberDepositExcel"+param;
	form.submit(); */	
}




function excelDown_4(form) {

	var rownum = jQuery("#list4").jqGrid('getGridParam','records')
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}
	
	var receipt_year   = form.receipt_year.value;
	
	if(receipt_year == '') {
		alert("검색년도를 선택해 주세요");
		return;
	}

	form.target="frm";
	form.action="memberDeposit.do?method=memberDepositExcel&nTabCnt=4&receipt_year="+receipt_year;
	form.submit();	
}


function excelDown_5(form) {
	
	var receipt_year  	= form.receipt_year.value;
	var receipt_month	= form.receipt_month.value;

	if(receipt_year == '') {
		alert("검색년도를 선택해 주세요");
		return;
	}
	
	if( form.d1.checked == true){
		var receipt_dt     = receipt_year+receipt_month;

		var rownum = jQuery("#list5_1").jqGrid('getGridParam','records')
		if( rownum == 0) {
			alert("출력할 정보를 조회해 주세요.");
			return;
		}

		form.target="frm";
		form.action="memberDeposit.do?method=memberDepositExcel&nTabCnt=5_1&receipt_dt="+receipt_dt;
		form.submit();
		
	}else if(form.d2.checked == true){
		var receipt_dt     = receipt_year;

		var rownum = jQuery("#list5_2").jqGrid('getGridParam','records')
		if( rownum == 0) {
			alert("출력할 정보를 조회해 주세요.");
			return;
		}

		form.target="frm";
		form.action="memberDeposit.do?method=memberDepositExcel&nTabCnt=5_2&receipt_dt="+receipt_dt;
		form.submit();
		
	}
}


function excelDown_6(form) {

	var rownum = jQuery("#list6").jqGrid('getGridParam','records')
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}
	
	var receipt_year   = form.receipt_year.value;
	var receipt_month  = form.receipt_month.value;
	

	if(receipt_year == '' || receipt_month == '') {
		alert("검색년도와 월을 선택해 주세요");
		return;
	}
	
	var receipt_dt     = receipt_year+receipt_month;

	form.target="frm";
	form.action="memberDeposit.do?method=memberDepositExcel&nTabCnt=6&receipt_dt="+receipt_dt;
	form.submit();	
}

</script>
</head>

<body >

	<div id="contents">
		<ul class="home">
		    <li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
			<li><a href="memberForm.do">회원</a></li>				<li>&gt;</li>
			<li class="NPage">예수금 현황</li>
		 </ul>	

	
				
		<div id="tabs"><!-- tabs -->
		
			<div id="ts_tabmenu"><!-- ts_tabmenu -->
				<ul>
					<li><a  id="name_1" class="overMenu" onclick="javascript:tab('1');"><strong><span>예수금 집계표</span></strong></a></li>
					<li><a  id="name_2" class="" onclick="javascript:tab('2');"><strong><span>지부별 예수금 집계표</span></strong></a></li>
					<li><a  id="name_3" class="" onclick="javascript:tab('3');"><strong><span>평생회비 예수금</span></strong></a></li>
					<li><a  id="name_4" class="" onclick="javascript:tab('4');"><strong><span>평생회비 예수금 현황</span></strong></a></li>
					<li><a  id="name_5" class="" onclick="javascript:tab('5');"><strong><span>예수금 현황</span></strong></a></li>
					<li><a  id="name_6" class="" onclick="javascript:tab('6');"><strong><span>산하단체 예수금 현황</span></strong></a></li>
				</ul>
			</div><!-- ts_tabmenu -->

<!-- 첫번째 탭 -->
			<div  id=tab_1  class="" style="top-margin:0; display:block;" ><!-- tab_1 예수금 집계표 -->
				<table width=1100px  class="mtable_13" cellspacing="0" summary="예수금 집계표">
					<caption>예수금 집계표</caption>
					<form name="memberDeposit_1" method="post" action="">
						<tr align="left" style="margin-top:0px;">
						
							<!-- 기간 입력창 -->
							<td class="a01" width=10%>조회기간</td>
							<td style="width:250px;">
								<input type="text" name="startdate" id="datepicker_11" readonly value='<%=startdate1 %>'>&nbsp;~&nbsp;
								<input type="text" name="enddate" id="datepicker_12" readonly value='<%=enddate %>'>
							</td>
							
							<td class="a01" style="BORDER-LEFT: #d5d5d5 1px solid;" width=10%>지회</td>
							<td>
								<select name="code_bran" style="width:100px;" disabled='true'>
									<option value="">전체</option>
								</select>
								<span style="color:red">&nbsp;&nbsp;* 입금처가 본회인 것을 조회합니다.</span>
							</td>

							<!-- 조회 버튼 -->
							<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(memberDeposit_1, '1');" style="cursor: hand" alt="조회" align="right"/></td>
						</tr>
					</form>
				</table>
				<table id="list1">	</table><!-- 그리드 리스트 -->
				<div id="pager1">	</div><!-- 그리드 상태바 -->
				
				<form name="memberStateListForm_1" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberDeposit_1, '1');" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>
				
			</div>

<!-- 두번째 탭 -->			
			<div  id=tab_2  class="" style="top-margin:0; display:none;" ><!-- tab_2 지부별 예수금 현황 -->
				<table width=1100px  class="mtable_13"  cellspacing="0" summary="지부별 예수금 현황">
					<caption>지부별 예수금 현황</caption>
					<form name="memberDeposit_2" method="post" action="">
						<tr align="left" style="margin-top:0px;">
						
							<!-- 기간 입력창 -->
							<td class="a01" width=10%>조회기간</td>
							<td style="width:250px;">
								<input type="text" name="startdate" id="datepicker_21" readonly value='<%=startdate1 %>'>&nbsp;~&nbsp;
								<input type="text" name="enddate" id="datepicker_22" readonly value='<%=enddate %>'>
							</td>
							
							<td class="a01" style="BORDER-LEFT: #d5d5d5 1px solid;" width=10%>지회</td>
							<td>
								<select name="code_bran" style="width:100px;" disabled='true'>
									<option value="">전체</option>
								</select>
								<span style="color:red">&nbsp;&nbsp;* 입금처가 지회인 것을 조회합니다.</span>
							</td>
							
							<!-- 조회 버튼 -->
							<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(memberDeposit_2, '2');" style="cursor: hand" alt="조회" align="right"/></td>
						</tr>
					</form>
				</table>
				<table id="list2">	</table><!-- 그리드 리스트 -->
				<div id="pager2">	</div><!-- 그리드 상태바 -->
				
				<form name="memberStateListForm_2" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberDeposit_2, '2');" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>
				
			</div>

<!-- 세번째 탭 -->			
			<div  id=tab_3  class="" style="top-margin:0; display:none;" ><!-- tab_3 평생회비 예수금 -->
				<table width=1100px  class="mtable_13"  cellspacing="0" summary="평생회비 예수금">
					<caption>평생회비 예수금</caption>
					<form name="memberDeposit_3" method="post" action="">
						<tr align="left" style="margin-top:0px;">
						
							<!-- 조건 입력창 -->
							<td class="a01" width=10%>검색년도</td>
							<td style="width:250px;">
								<select name="receipt_year" style="width:100px;"><option value="">선택</option></select>
							</td>
							<td class="a01" style="BORDER-LEFT: #d5d5d5 1px solid;" width=10%>차수</td>
							<td>
								<select name="pers_mem_order" style="width:100px;">
									<option value="">선택</option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="12">12</option>
									<option value="13">13</option>
									<option value="14">14</option>
									<option value="15">15</option>
									<option value="16">16</option>
									<option value="17">17</option>
									<option value="18">18</option>
									<option value="19">19</option>
									<option value="20">10</option>
									<option value="21">21</option>
								</select2>
							</td>

							<!-- 조회 버튼 -->
							<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(memberDeposit_3, '3');" style="cursor: hand" alt="조회" align="right"/></td>
						</tr>
					</form>
				</table>
				<table id="list3"></table><!-- 그리드 리스트 -->
				<div id="pager3"></div><!-- 그리드 상태바 -->
				
				<form name="memberStateListForm_3" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberDeposit_3, '3');" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>
				
			</div>

<!-- 내번째 탭 -->			
			<div  id=tab_4  class="" style="top-margin:0; display:none;" ><!-- tab_4 평생회비 예수금 현황-->
				<table width=1100px  class="mtable_13"  cellspacing="0" summary="평생회비 예수금 현황">
					<caption>평생회비 예수금 현황</caption>
					<form name="memberDeposit_4" method="post" action="">
						<tr align="left" style="margin-top:0px;">
						
							<!-- 기간 입력창 -->
							<!-- 조건 입력창 -->
							<td class="a01" width=10%>검색년도</td>
							<td style="width:200px;">
								<select name="receipt_year" style="width:100px;"><option value="">선택</option></select>
							</td>

							<!-- 조회 버튼 -->
							<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(memberDeposit_4, '4');" style="cursor: hand" alt="조회" align="right"/></td>
						</tr>
					</form>
				</table>
				<table id="list4">	</table><!-- 그리드 리스트 -->
				<div id="pager4">	</div><!-- 그리드 상태바 -->
				
				<form name="memberStateListForm_4" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberDeposit_4, '4');" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>
				
			</div>

<!-- 오번째 탭 -->			
			<div  id=tab_5  class="" style="top-margin:0; display:none;" ><!-- tab_5 예수금 현황-->
				<table width=1100px  class="mtable_13"  cellspacing="0" summary="예수금 현황">
					<caption>예수금 현황</caption>
					
						<form name="memberDeposit_5" method="post" action="">
							<tr align="left" style="margin-top:0px;">
							
								<!-- 기간 입력창 -->
								<td class="a01" width=10%>검색년도</td>
								<td style="width:100px;">
									<select name="receipt_year" style="width:100px;"><option value="">선택</option></select>
								</td>
								<td style="width:50px;">
									<div  id=receipt_month2  class="" style="top-margin:0;"  >
										<select name="receipt_month"  id="receipt_month" style="width:50px;"><option value="">선택</option></select>
									</div>
								</td>
								<td>
								<input type="radio" name="deposit" id="d1" value="1"  checked onchange="javascript:depositchange(memberDeposit_5);"/>년회원</label>
								<input type="radio" name="deposit" id="d2" value="2" onchange="javascript:depositchange(memberDeposit_5);"/>평생회원</label>
								</td>
								<!-- 조회 버튼 -->
								<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(memberDeposit_5, '5');" style="cursor: hand" alt="조회" align="right"/></td>
							</tr>
						</form>
					
					
				</table>
				
				<div  id=tab_5_1  class="" style="top-margin:0; display:block;" >
					<table id="list5_1"></table> <!-- 그리드 리스트 -->
				<div id="pager5_1">	</div> <!-- 그리드 상태바 -->
				
				<form name="memberDeposit_5_1" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberDeposit_5, '5');" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>
				
				</div>
				<div  id=tab_5_2  class="" style="top-margin:0; display:none;" >
					<table id="list5_2"></table> <!-- 그리드 리스트 -->
					<div id="pager5_2">	</div> <!-- 그리드 상태바 -->
				
				<form name="memberDeposit5_2" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberDeposit_5,'5');" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>
				
				</div>
				
			</div>

<!-- 육번째 탭 -->			
			<div  id=tab_6  class="" style="top-margin:0; display:none;" ><!-- tab_6 산하단체 예수금 현황-->
				<table width=1100px  class="mtable_13"  cellspacing="0" summary="산하단체 예수금 현황">
					<caption>산하단체 예수금 현황</caption>
					<form name="memberDeposit_6" method="post" action="">
						<tr align="left" style="margin-top:0px;">
						
							<!-- 기간 입력창 -->
							<td class="a01" width=10%>검색년도</td>
							<td style="width:250px;">
								<select name="receipt_year"  style="width:100px;">
									<option value="">선택</option>
								</select>
								<select name="receipt_month" style="width:50px" >
									<option value="">선택</option>
								</select>
							</td>
							
							<td class="a01" style="BORDER-LEFT: #d5d5d5 1px solid;" width=10%>산하단체구분</td>
							<td>
								<select name="code_sub" style="width:150px;">
									<option value="">선택</option>
									<option value="01">전국영양교사회</option>
									<option value="02">전국학교영양사회</option>
									<option value="03">교정영양사회</option>
									<option value="04">당뇨병영양사회</option>
								</select2>
							</td>
							
							<!-- 조회 버튼 -->
							<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(memberDeposit_6, '6');" style="cursor: hand" alt="조회" align="right"/></td>
						</tr>
					</form>
				</table>
				<table id="list6"></table>	<!-- 그리드 리스트 -->
				<div id="pager6"></div>		<!-- 그리드 상태바 -->
				
				<form name="memberStateListForm_6" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberDeposit_6, '6');" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
		</div><!-- M_contents End -->
	
</body>
</html>

<iframe name="frm" width="0" height="0" tabindex=-1></iframe>