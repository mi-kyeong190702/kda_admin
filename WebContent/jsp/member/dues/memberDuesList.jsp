<%@page import="org.apache.struts.action.Action"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.ant.common.properties.AntData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

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

	<!-- 
	수정일 : 2012..
	수정자 : 김성훈
	내   용 : 회비처리
	 -->
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>

<%@page import="com.ant.common.code.CommonCode"%>

<%@ page import="net.sf.json.JSONArray"%>
<!-- 김성훈 . 공통함수 -->
<script src="js/form.js" type="text/javascript" ></script>

<%
	String g_name = session.getAttribute("G_NAME").toString();
	String g_id = session.getAttribute("G_ID").toString();
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

//제이슨으로 공통 쿼리 처리
JSONArray comList	= (CommonCode.getInstance()).getCodeList();
JSONArray subList	= (CommonCode.getInstance()).getSubList();


//몇월달인가.
Calendar calendar = Calendar.getInstance();
java.util.Date date = calendar.getTime();

String MM	= new java.text.SimpleDateFormat ("MM"	).format(new java.util.Date());
%>




<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>


<script type="text/javascript">
	$(document).ready(function(){
		
		//전체 권한 여부
		powerinit("103", eval(<%=userpowerm1%>) );


		jQuery("#list").jqGrid({
			url: "memberDues.do?method=memberDuesList",
			datatype: "json",
			mtype: 'POST',
			height:'500',
			//width:'1100',
			sortable:true,
			shrinkToFik:true,
			abGrid:true,
			recordpos:'right',//전체갯수 나오는 위치
			viewrecords:true,//레코드 나오는지 여부.. 근데 위에거랑 이거는 잘 모르겠음.. 쓰는건지.
			colNames:	['번호', '회원코드', '이름', '면허번호','회원구분', '회원구분', '소속지부', '소속지부', '소속분과', '소속분회', '회비구분', '회비구분', '회비', '시작일자', '만료일자', '완납여부', '처리여부', '산하단체', '산하단체', '등록일', '이메일', '연락처','입금처', '주소', '회사명', '회사주소','회원증코드','회비순번'],
			colModel:	[
			{name:'ncount'			, index:'ncount asc, invdate'	, width:0	, align:"center"	, sortable:false, hidden:true},		//번호
			{name:'code_pers'		, index:'code_pers'			, width:70	, align:"center"	, sortable:false},	//회원번호
			{name:'pers_name'		, index:'pers_name'			, width:60	, align:"center"	, sortable:false},	//이름
			{name:'lic_no'			, index:'lic_no'			, width:60	, align:"center"	, sortable:false},	//이름
			{name:'code_member'		, index:'code_member'		, width:0	, align:"center"	, sortable:false, hidden:true},		//회원구분
			{name:'han_code_member'	, index:'han_code_member'	, width:110	, align:"center"	, sortable:false},	//회원구분
			{name:'code_bran'		, index:'han_code_bran'		, width:0	, align:"center"	, sortable:false, hidden:true},		//소속지부
			{name:'han_code_bran'	, index:'code_bran'			, width:110	, align:"center"	, sortable:false},	//소속지부
			{name:'bigname'			, index:'bigname'			, width:110	, align:"center"	, sortable:false},	//소속분과
			{name:'sectname'		, index:'sectname'			, width:110	, align:"center"	, sortable:false},	//소속분회
			{name:'dues_gubun'		, index:'dues_gubun'		, width:0	, align:"center"	, sortable:false, hidden:true},		//회비구분
			{name:'han_dues_gubun'	, index:'han_dues_gubun'	, width:80	, align:"center"	, sortable:false},	//회비구분
			{name:'mem_dues'		, index:'mem_dues'			, width:80	, align:"center"	, sortable:false, formatter:'integer'},	//회비
			{name:'auth_start'		, index:'auth_start'		, width:110	, align:"center"	, sortable:false},	//시작일자
			{name:'auth_end'		, index:'auth_end'			, width:110	, align:"center"	, sortable:false},	//만료일자
			{name:'incom_flag'		, index:'incom_flag'		, width:60	, align:"center"	, sortable:false},	//완납여부
			{name:'conform_yn'		, index:'conform_yn'		, width:60	, align:"center"	, sortable:false},	//확인여부
			{name:'code_sub'		, index:'code_sub'			, width:0	, align:"center"	, sortable:false, hidden:true},		//산하단체
			{name:'han_code_sub'	, index:'han_code_sub'		, width:140	, align:"center"	, sortable:false},	//산하단체
			{name:'regi_dt'			, index:'regi_dt'			, width:140	, align:"center"	, sortable:false},	//등록일
			{name:'email'			, index:'email'				, width:140	, align:"center"	, sortable:false},	//이메일
			{name:'pers_hp'			, index:'pers_hp'			, width:140	, align:"center"	, sortable:false},	//핸펀
			{name:'code_receipt'	, index:'code_receipt'		, width:140	, align:"center"	, sortable:false},	//입금처
			{name:'adrs'			, index:'adrs'				, width:140	, align:"center"	, sortable:false},	//주소
			{name:'company_name'	, index:'company_name'		, width:140	, align:"center"	, sortable:false},	//회사명
			{name:'cadrs'			, index:'cadrs'				, width:140	, align:"center"	, sortable:false},	//회사주소
			{name:'code_MS'			, index:'code_MS'			, width:140	, align:"center"	, sortable:false},	//회원증코드
			{name:'dues_h_seq'		, index:'dues_h_seq'		, width:0	, align:"center"	, sortable:false, hidden:true}
							],
			rowNum:20,
		//	rowList:[10,20,30],
			sortname: 'id',
			sortorder: 'desc',
			pager: '#pager2',
			viewrecords: true,//토탈카운터 나오는것
			caption: 'kda_grid',
			altRows:true,
			altclass:'myAltRowClass',
			rownumbers : true,
			multiselect: true,
			//scroll:true,	//스크롤 상하좌우 생김, 아래로 스크롤 내리면 자동으로 다음것 검색해버림 ㅡㅡ
			caption:"회원 회비처리" 
		});
	jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true,position:'left'});
 	jQuery("#list").setGridWidth(1100,false);
 	
});
	
</script>
						
<script>
$(document).ready(function() {

	var codeList	=eval(<%=comList%>);		//전체공통쿼리
	var subList		=eval(<%=subList%>);		//산하단체 리스트
	
	var form				= memberInputForm;				//폼
	
	var cnt				=0;
	var groupcode		= '';
	var detcode		= '';
	var detcodename	= '';

	
	for( var i=0; i<codeList.length; i++) {
	
		groupcode		= codeList[i].groupcode;
		detcode			= codeList[i].detcode;
		detcodename	= codeList[i].detcodename;

		if(cnt==0) {
			detcode = '';
			detcodename = '선택';
			i--;
		}

		if(groupcode=='036')//기준년
			form.regi_yyyy.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='039')//기준월
			form.regi_mm.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='007')	//지부
			form.code_bran.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='006')	//회원종류
			form.code_member.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='038')	//회비구분
			form.dues_gubun.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='013')	//입금처
			form.code_receipt.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='014')	//입금방법
			form.code_pay_flag.options[cnt]=new Option(detcodename , detcode , false, false);
		
		cnt++;
		if(codeList.length == i+1 || codeList[i+1].groupcode != groupcode)	cnt=0;

	}
	form.code_bran.value = '<%=g_bran%>';

	if(form.code_bran.value == '01'){
		form.code_bran.disabled=false;
		form.code_bran.value='';
		
		//분배처리할때 목표 선택 초기화
		form.whatReceipt.code_receipt_1.checked = true;
		form.whatReceipt.code_receipt_2.disabled = true;
	}
	
	
	form.regi_yyyy.options[0]=new Option("년" , "" , false, true);
	form.regi_mm.options[0]=new Option("월" , "" , false, true);
	
	//산하단체 리스트
	/* cnt = 0;
	var code_sub	= "";
	var sub_name	= "";
	for( var i=-1; i<subList.length; i++) {
		if(i==-1) {
			code_sub	= '';
			sub_name	= '선택';
		}else {
			code_sub	= subList[i].code_sub;
			sub_name	= subList[i].sub_name;
		}
		form.code_sub.options[cnt]=new Option(sub_name , code_sub , false, false);
		cnt++;
	} */
	
});


//등록년 변경시 등록일 변경여부
	function fn_regi_yyyy(form) {
	
		if( form.regi_yyyy.value != "") {
			form.regi_mm.disabled=false;
			return;
		}
		if( form.regi_yyyy.value == "") {
			form.regi_mm.disabled=true;
			form.regi_mm.value = '';
		}
		
	}

	//검색 함수
	function goSearch(form){
		

		var param = "";
		if(form.dues_gubun.value != "2"){
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value + form.regi_mm.value;		//등록일
		}else{
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value;		//등록일
			// 평생회비 3월 검색시 DUES_P 테이블을 조회해야 한다
//		alert("form.regi_mm.value==>"+form.regi_mm.value+"<==");
			if(form.regi_mm.value != "" && (form.regi_mm.value == "03" || form.regi_mm.value == "02" || form.regi_mm.value == "01")){
				param+="&duesp=duesp";
			}
		}
//		alert("param==>"+param+"<==");
		if(form.dues_gubun.value	!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
		if(form.code_member.value	!= "")	param+="&code_member="	+form.code_member.value;	//회원구분
		if(form.code_bran.value		!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
		if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
		if(form.incom_flag.value	!= "")	param+="&incom_flag="	+form.incom_flag.value;		//완납여부
		if(form.conform_yn.value	!= "")	param+="&conform_yn="	+form.conform_yn.value;		//확인여부
		if(form.code_receipt.value	!= "")	param+="&code_receipt="	+form.code_receipt.value;	//입금장소
		if(form.code_pay_flag.value	!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법
		
		if(document.getElementById("YY").checked){
			param+="&cfyn=Y";	//입금장소	
		}
		  
//alert(param);
		$('#list').jqGrid('clearGridData');
		jQuery("#list").setGridParam({url:"memberDues.do?method=memberDuesList&isSelect=Y"+param}).trigger("reloadGrid");
		
	}
	
	//평생회비 배치함수 = 평생회비 분배처리
	function goAllLifeBatch(form) {
		
		var temp=jQuery("#list").jqGrid('getCol','incom_flag');
		if(temp[0]=='미완납'){
			alert("선인증 목록은 평생회비 확정처리를 할 수 없습니다.");
			return;
		}else{
			var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
			var rowdata=new Array();
			var code_pers	="";
			var code_pers_cnt	=0;
			if( rownum.length > 0 ){ 
				for(var i=0;i<rownum.length;i++){
					rowdata= 	$("#list").getRowData(rownum[i]);
					if(i > 0) code_pers	+= "__";
					code_pers+= rowdata.code_pers+"_"+rowdata.dues_gubun+"_"+rowdata.dues_h_seq;
					code_pers_cnt++;
				}
			}	

			var MM = form.regi_mm.value;
			var param = "";
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value;		//등록일

			if(form.dues_gubun.value	!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			if(form.code_member.value	!= "")	param+="&code_member="	+form.code_member.value;	//회원구분
			if(form.code_bran.value		!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
			if(form.incom_flag.value	!= "")	param+="&incom_flag="	+form.incom_flag.value;		//완납여부
			if(form.conform_yn.value	!= "")	param+="&conform_yn="	+form.conform_yn.value;		//확인여부
			if(form.code_receipt.value	!= "")	param+="&code_receipt="	+form.code_receipt.value;	//입금장소
			if(form.code_pay_flag.value	!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법
			
			if(document.getElementById("YY").checked){
				param+="&cfyn=Y";	//입금장소	
			}
			if(code_pers != "")	param+="&code_pers="	+code_pers;
			
			//MM='3';
			if( form.regi_mm.value != '12' && form.regi_mm.value != '03'){
//				if( MM != '12' && MM != '3'){
				alert("평생회비 확정처리는 매해 3월과 12월에만 가능합니다. \n");
				return;
			}
			
			if(code_pers_cnt==0) {
				if(!confirm("전체 평생회비를 확정처리 하시겠습니까?")){return;}
			} else {
				if(!confirm("선택하신 "+code_pers_cnt+"건의 평생회비를 확정처리 하시겠습니까?")){return;}
			}
			
			$('#list').jqGrid('clearGridData');
			jQuery("#list").setGridParam({url:"memberDues.do?method=memberDuesAllLifeBatch&MM="+MM+param+"&isSelect=Y"}).trigger("reloadGrid");	
		}		
	}
	
	//배치함수=분배처리
	function goBatch(form) {
		//배치처리 
		//후 다시 검색
		var temp=jQuery("#list").jqGrid('getCol','incom_flag');
		if(temp[0]=='미완납'){
			alert("선인증 목록은 확정처리를 할 수 없습니다.");
			return;
		}else{
			var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
			var rowdata=new Array();
			var code_pers	="";
			var code_pers_cnt = 0;
			if( rownum.length > 0 ){ 
				for(var i=0;i<rownum.length;i++){
					rowdata= 	$("#list").getRowData(rownum[i]);
					if(i > 0) code_pers	+= "__";
					code_pers+= rowdata.code_pers+"_"+rowdata.dues_gubun+"_"+rowdata.dues_h_seq;
					code_pers_cnt++;
				}
			}	

			var param = "";
			if(form.dues_gubun.value != "2"){
				if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value + form.regi_mm.value;		//등록일
			}else{
				if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value;		//등록일
				// 평생회비 3월 검색시 DUES_P 테이블을 조회해야 한다
//			alert("form.regi_mm.value==>"+form.regi_mm.value+"<==");
				if(form.regi_mm.value != "" && (form.regi_mm.value == "03")){
					param+="&duesp=duesp";
				}
			}
//			alert("param==>"+param+"<==");
			if(form.dues_gubun.value	!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			if(form.code_member.value	!= "")	param+="&code_member="	+form.code_member.value;	//회원구분
			if(form.code_bran.value		!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
			if(form.incom_flag.value	!= "")	param+="&incom_flag="	+form.incom_flag.value;		//완납여부
			if(form.conform_yn.value	!= "")	param+="&conform_yn="	+form.conform_yn.value;		//확인여부
			if(form.code_receipt.value	!= "")	param+="&code_receipt="	+form.code_receipt.value;	//입금장소
			if(form.code_pay_flag.value	!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법
			
			if(document.getElementById("YY").checked){
				param+="&cfyn=Y";	//입금장소	
			}
			if(code_pers != "")	param+="&code_pers="	+code_pers;

			//alert(param);
			
			if(code_pers_cnt==0) {
				if(!confirm("전체 확정처리 하시겠습니까?")){return;}
			} else {
				if(!confirm("선택하신 "+code_pers_cnt+"건을 확정처리 하시겠습니까?")){return;}
			}
			
			$('#list').jqGrid('clearGridData');
			jQuery("#list").setGridParam({url:"memberDues.do?method=memberDuesBatch&isSelect=Y"+param}).trigger("reloadGrid");
		}
	}
	//회원증 발급
	function membership(form) {
		
		var rownum		= jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata		=new Array();
		var param = "";
		
		if( rownum.length > 0 ){	//선택 발송			
			var isin		="OK";//집어넣나 마나.
			
			for(var i=0;i<rownum.length;i++){//전체 돌면서
				
				rowdata= $("#list").getRowData(rownum[i]);	
				if(rowdata.pers_name.length > 0) {
					
					if(param.length>0) param	+= ",";
					param+= "'"+rowdata.code_MS+"'";
				}
				isin = "OK";
			}
	
	//	 alert(param);
			var status ="scrollbars=yes, toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, left=100, top=100";
			var url="<%=AntData.DOMAIN%>/doc_form/docu_print.jsp?doc_code=0105&doc_seq="+param;
			window.open(url,"membership",status);	//윈도우 뛰운다.
				
		}else{
			//여기다 전체 검색(검색과 같은 루트를 탄다.)
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value + form.regi_mm.value;		//등록일
			if(form.dues_gubun.value	!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			if(form.code_member.value	!= "")	param+="&code_member="	+form.code_member.value;	//회원구분
			if(form.code_bran.value		!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
			if(form.incom_flag.value	!= "")	param+="&incom_flag="	+form.incom_flag.value;		//완납여부
			if(form.conform_yn.value	!= "")	param+="&conform_yn="	+form.conform_yn.value;		//확인여부
			if(form.code_receipt.value	!= "")	param+="&code_receipt="	+form.code_receipt.value;	//입금장소
			if(form.code_pay_flag.value	!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법

			var url="memberDues.do?method=membershipForm"+param;
			var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=yes, width=340, height=500, left=100, top=100";
			window.open(url,"membershipForm", status);
			
		}
	}


	/* DM 발송 */
	function sendDM(form) {
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata=new Array();
		
		var url="";
		
		var param = "";
		param += "&register="+escape(encodeURIComponent("<%=g_name%>"));
	
		if( rownum.length > 0 ){ 
			var code_pers	="";//회원구분코드	code_pers
			
			for(var i=0;i<rownum.length;i++){
				rowdata= 	$("#list").getRowData(rownum[i]);
				if(i > 0) code_pers	+= ",";
				code_pers+= rowdata.code_pers;
			}
			param += "&code_pers="+code_pers;
			
		//	alert("DM발송 조건 "+param);
			url="memberDues.do?method=sendDMForm&chk=CHK&param="+param;
			
		}else if( rownum.length == 0 ){	
			//여기다 전체 검색(검색과 같은 루트를 탄다.)
			
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value + form.regi_mm.value;		//등록일
			if(form.dues_gubun.value	!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			if(form.code_member.value	!= "")	param+="&code_member="	+form.code_member.value;	//회원구분
			if(form.code_bran.value		!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
			if(form.incom_flag.value	!= "")	param+="&incom_flag="	+form.incom_flag.value;		//완납여부
			if(form.conform_yn.value	!= "")	param+="&conform_yn="	+form.conform_yn.value;		//확인여부
			if(form.code_receipt.value	!= "")	param+="&code_receipt="	+form.code_receipt.value;	//입금장소
			if(form.code_pay_flag.value	!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법
			
			if(document.getElementById("YY").checked){
				param+="&cfyn=Y";	//입금장소	
			}
	//		alert("DM발송 전체 "+param);
			url="memberDues.do?method=sendDMForm&chk=ALL&param="+param;
		}	
		var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=no, width=340, height=300, left=100, top=100";
		window.open(url,"sendDM", status);
	}
	
	//문자 전송
	
	function sendUms(form){
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata=new Array();
		var rowdata2=new Array();

		var url="";
		
		var param = "";
		param+="&register="+"<%=g_id%>";

		
		if( rownum.length > 0 ){ 
			var pers_hp	="";//회원전화번호	pers_hp
			var pers_name="";
			var code_pers="";
			
			var isok = "OK";
			for(var i=0;i<rownum.length;i++){
				rowdata= 	$("#list").getRowData(rownum[i]);

				for(var ii=0; ii<i; ii++) {//중복 검사
					rowdata2= 	$("#list").getRowData(rownum[ii]);

					if(rowdata.pers_hp == rowdata2.pers_hp) isok = "NO";
				}
				
				if( isok == "OK" && rowdata.pers_hp != "") {
					if(i > 0 && pers_hp.length > 0){ 
						pers_hp	+= ",";  
						pers_name += ",";
						code_pers += ",";
					}
					pers_hp+= rowdata.pers_hp;
					pers_name+= rowdata.pers_name;
					code_pers+= rowdata.code_pers;
				}
				isok = "OK";
			}
			param += "&code_pers="+code_pers+"&pers_hp="+pers_hp+"&pers_name="+escape(encodeURIComponent(pers_name));
			
		//	alert("쪽지 발송 조건 "+param);
			url="memberDues.do?method=sendUmsForm&chk=CHK&param="+param;
			
		}else if( rownum.length == 0 ){	
			//여기다 전체 검색(검색과 같은 루트를 탄다.)
			
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value + form.regi_mm.value;		//등록일
			if(form.dues_gubun.value	!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			if(form.code_member.value	!= "")	param+="&code_member="	+form.code_member.value;	//회원구분
			if(form.code_bran.value		!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
			if(form.incom_flag.value	!= "")	param+="&incom_flag="	+form.incom_flag.value;		//완납여부
			if(form.conform_yn.value	!= "")	param+="&conform_yn="	+form.conform_yn.value;		//확인여부
			if(form.code_receipt.value	!= "")	param+="&code_receipt="	+form.code_receipt.value;	//입금장소
			if(form.code_pay_flag.value	!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법
			
			if(document.getElementById("YY").checked){
				param+="&cfyn=Y";	//입금장소	
			}
		//	alert("쪽지 발송 전체 "+param);
			url="memberDues.do?method=sendUmsForm&chk=ALL&param="+param;
		}	
		var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=no, width=370, height=550, left=280, top=50";
		window.open(url,"sendUms", status);
		
	}
	//메일 발송
	function sendEmail(form) {
		
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

 		if( rownum.length==0)	{
 			alert("메일을 발송할 회원을 선택해 주십시요.");
 			return;	
 		}
 		
	 	var rowdata=new Array();
	 	var param="";
	 	var addr_infos = "";
 		for(var i=0;i<rownum.length;i++){
 			
			rowdata= 	$("#list").getRowData(rownum[i]);
			
			if(rowdata.email.length>0){
				if(param.length > 0) param+= ",";
// 				param += rowdata.email;
				param += escape(encodeURIComponent(rowdata.pers_name));
				
				if(addr_infos.length > 0) {
					addr_infos	+= ",";
				}
				addr_infos	+= rowdata.code_pers;
			}
 		}
 		param+="&paramfrom=memberDuesList&addr_infos="+addr_infos;
		//alert("메일발송 = "+param);
		
		//$('#list').jqGrid('clearGridData');
		window.open('memberBasic.do?method=eMail&toAddr='+param,"email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
	}
	//엑셀 출력
	function excelDown(form) {
		var param = "";
		if(form.dues_gubun.value != "2"){
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value + form.regi_mm.value;		//등록일
		}else{
			if(form.regi_yyyy.value		!= "")	param+="&regi_dt="		+form.regi_yyyy.value;		//등록일
			// 평생회비 3월 검색시 DUES_P 테이블을 조회해야 한다
			if(form.regi_mm.value != "" && (form.regi_mm.value == "03" || form.regi_mm.value == "02" || form.regi_mm.value == "01")){
				param+="&duesp=duesp";
			}
		}
		if(form.dues_gubun.value	!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
		if(form.code_member.value	!= "")	param+="&code_member="	+form.code_member.value;	//회원구분
		if(form.code_bran.value		!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
		if(form.code_sub.value		!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회
		if(form.incom_flag.value	!= "")	param+="&incom_flag="	+form.incom_flag.value;		//완납여부
		if(form.conform_yn.value	!= "")	param+="&conform_yn="	+form.conform_yn.value;		//확인여부
		if(form.code_receipt.value	!= "")	param+="&code_receipt="	+form.code_receipt.value;	//입금장소
		if(form.code_pay_flag.value	!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법
		
		if(document.getElementById("YY").checked){
			param+="&cfyn=Y";	//입금장소	
		}
		param+="&register="+"<%=g_name%>";
		var url="memberDues.do?method=pers_check"+param;
//alert(param);
		window.open(url,"pers_no","width=400, height=500, left=480, top=200,scrollbars=yes");
		
		//form.target="frm";
		//form.action="memberDues.do?method=memberDuesExcel"+param;
		//form.submit();	
	}
	
	//소속지부 변경되었을때
	function fn_code_bran(form) {
		if( form.code_bran.value == ""){
			form.whatReceipt.code_receipt_1.checked = true;
			form.whatReceipt.code_receipt_2.disabled = true;
		} else {
			form.whatReceipt.code_receipt_2.disabled = false;
		}
	}
</script>

	</head>
<body>

	<div id="contents"><!-- contents -->
	
		<!-- 상단 바로가기 바 -->
		<ul class="home">
			<li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
			<li><a href="memberForm.do">회원</a></li>					<li>&gt;</li>
			<li class="NPage">회비처리</li>
		</ul>	
	
		<div class="mTabmenu_01"><!-- mTabmenu_01 -->
		
<form name="memberInputForm" method="post"><!-- form -->
<!-- 중복처리방지 토큰 처리 시작-->
<%
	//중복처리방지 토큰 처리 - 페이지에 그대로 추가
	String token=TokenProcessor.getInstance().generateToken(request); //현재 시간과 세션아이디를 갖고 token 키생성

	if(session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY)==null){
		session.setAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY,token);	
	%>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
	<%}else{%>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
<%}%> 
<!-- 중복처리방지 토큰 처리  끝-->
		
		<table class="mtable_02" cellspacing="0" summary="회비처리"><!-- table -->
          <caption>회비처리</caption>         
          
          
<!-- 첫째줄 *********************************************************** -->	 
			<tr>
				<td class="nobg">등록일</td>
				<td class="nobg1">	<select name="regi_yyyy" style=width:60px onchange="javascript:fn_regi_yyyy(memberInputForm);" ><option value="">선택</option></select>
									<select name="regi_mm" style=width:45px disabled = true ><option value="">선택</option></select></td>
<!-- dues_b_tbl (2) dues_gubun, "038" -->
				<td class="nobg2">회비구분</td>
				<td class="nobg1"><select name="dues_gubun"><option value="">선택</option></select></td>
<!-- dues_h_tbl (5) code_member, "006" -->
				<td class="nobg2">회원구분</td>
				<td class="nobg1"><select name="code_member"><option value="">선택</option></select></td>
<!-- Persson_M_TBL(18) code_bran, "007" -->
				<td class="nobg2">소속지부</td>
				<td class="nobg1"><select name="code_bran" disabled = true onchange="javascript:fn_code_bran(memberInputForm);" ><option value="">선택</option></select></td>
<!-- Persson_M_TBL(27) code_sub, "sub_tbl.code_sub" -->
				<td class="nobg2">산하단체</td>
				<td class="nobg1">
					<select name="code_sub">
						<option value="">선택</option>
						<option value="01">전국영양교사회</option>
						<option value="02">전국학교영양사회</option>
						<option value="03">교정영양사회</option>
						<option value="04">당뇨병영양사회</option>
					</select>
				</td>
			</tr>
			
<!-- 둘째줄 *********************************************************** -->
			<tr>
<!-- deus_h_tbl (12) regi_dt -->
				<td class="alt1">완납여부</td>
				<td><select name="incom_flag"><option value="">전체</option><option value="Y">완납</option><option value="N">미완납</option></select></td>
				<td class="alt">확정처리</td>
				<td><select name="conform_yn"><option value="">전체</option><option value="Y">처리</option><option value="N">미처리</option></select></td>
				<td class="alt">입금처</td>
				<td><select name="code_receipt"><option value="">선택</option></select></td>
				<td class="alt">입금방법</td>
				<td><select name="code_pay_flag"><option value="">선택</option></select></td>
				<td class="alt">선인증목록</td>
				<td><input type="radio" name="cfyn" id="NN" value="N" checked />
				   <label for="a_m">아니오</label>
				   <input type="radio" name="cfyn" id="YY" value="Y"  />
				   <label for="a_um">예</label>
				</td>
			</tr>
			
        </table><!-- table -->
		 
		<div class="mTabmenu_01"> <!-- mTabmenu_01 -->
			<!-- 검색 버튼 -->
			<ul class="btnIcon_11"><br/>
				<li><a href="javascript:goSearch(memberInputForm);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>
			</ul>
			
			
			<br/><br/>
		
			<!-- 그리드 리스트 --> 		
			<table id="list"></table>
			<!-- 그리드 상태바 -->
			<div id="pager2"></div>
			
			<br/>
			
			<!-- 작업 버튼 -->
			<% if( g_bran.equals("01")) { %>
			
			<ul class="btnIcon_19" style="left:462px;">
			<li><a href="javascript:goAllLifeBatch(memberInputForm);"	><img src="images/icon_decide_fee.gif" 		onclick="" alt="평생회비 확정처리" 		/></a></li>
<!-- 			<li><span onclick="goAllLifeBatch(memberInputForm);" style="cursor:pointer">[평생회비 확정처리]</span></li> -->
				<li><span style="color:red"> * 배치처리 :&nbsp;</span>
					<input type="radio" name="whatReceipt" id="code_receipt_1" checked value="1" />본회전체
					<input type="radio" name="whatReceipt" id="code_receipt_2" value="2" />선택지회
				</li>
<li><a href="javascript:goBatch(memberInputForm);"	><img src="images/icon_decide.gif"	onclick="" 		alt="확정처리" 		/></a></li>
<!-- <li><span onclick="goBatch(memberInputForm);" style="cursor:pointer">[확정처리]</span></li> -->
<li><a href="javascript:membership(memberInputForm);"	><img src="images/membership.png" 		onclick=""	alt="회원증출력" 	/></a></li>
<li><a href="javascript:sendDM(memberInputForm);"	><img src="images/icon_s1.gif" 			onclick=""		alt="DM발송" 		/></a></li>
<!-- <li><a href="javascript:sendUms(memberInputForm);"	><img src="images/icon_s4.gif"			onclick=""		alt="문자전송" 		/></a></li>
<li><a href="javascript:sendEmail(memberInputForm);"	><img src="images/icon_s3.gif"			onclick=""	alt="메일전송" 		/></a></li> -->
<li><a href="javascript:excelDown(memberInputForm);"	><img src="images/icon_excel.gif"		onclick=""	alt="엑셀저장" 		/></a></li>
			</ul>
				
			<% } else { %>
			<ul class="btnIcon_16">
<li><a href="javascript:goBatch(memberInputForm);"	><img src="images/icon_decide.gif"	onclick="" 		alt="확정처리" 		/></a></li>
<!-- <li><span onclick="goBatch(memberInputForm);" style="cursor:pointer">[확정처리]</span></li> -->
<li><a href="javascript:membership(memberInputForm);"	><img src="images/membership.png" 		onclick=""	alt="회원증출력" 	/></a></li>
<li><a href="javascript:sendDM(memberInputForm);"	><img src="images/icon_s1.gif" 			onclick=""		alt="DM발송" 		/></a></li>
<!-- <li><a href="javascript:sendUms(memberInputForm);"	><img src="images/icon_s4.gif"			onclick=""		alt="문자전송" 		/></a></li>
<li><a href="javascript:sendEmail(memberInputForm);"	><img src="images/icon_s3.gif"			onclick=""	alt="메일전송" 		/></a></li> -->
<li><a href="javascript:excelDown(memberInputForm);"	><img src="images/icon_excel.gif"		onclick=""	alt="엑셀저장" 		/></a></li>
			</ul>
			<% } %>
		</div><!-- mTabmenu_01 -->
		
</form><!-- form -->
	</div><!-- contents -->
	
</body>
</html>

<iframe name="frm" width="0" height="0" tabindex=-1></iframe>
