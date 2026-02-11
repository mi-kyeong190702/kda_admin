<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.ant.common.code.CommonCode"%>


<%@ page import="net.sf.json.JSONArray"%>
<!-- 김성훈 . 공통함수 -->
<script src="js/form.js" type="text/javascript" ></script>

<%
	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	String g_id = session.getAttribute("G_ID").toString();
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

//제이슨으로 공통 쿼리 처리
JSONArray comList	= (CommonCode.getInstance()).getCodeList();
JSONArray subList	= (CommonCode.getInstance()).getSubList();
JSONArray certifiList	= (CommonCode.getInstance()).getCertifList();

%>


<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>


<script type="text/javascript">
$(document).ready(function(){
	jQuery("#list").jqGrid({
		url: "memberSearch.do?method=memberSearchList",
		datatype: "json",
		mtype: 'POST',
		height:'490',
		//width:'1100',
		sortable:true,
		shrinkToFik:true,//우측 빈부분 없앤다는데 안됨. 
		abGrid:true,
		recordpos:'right',//전체갯수 나오는 위치
		viewrecords:true,//레코드 나오는지 여부.. 근데 위에거랑 이거는 잘 모르겠음.. 쓰는건지.
			
		colNames: [	'번호','이름','면허번호'
// 		           ,'주민등록번호' // JUMIN_DEL
				   , '생년월일'
		           ,'지부명','분과명','분회명','회원종류', '회원상태'
		           ,'회원유효기간시작일','회원유효기간종료일'
		           ,'인증일','인증장소'
// 		           ,'우편번호' // del 1
		           ,'집주소'
// 		           ,'집전화번호' // del 2
		           ,'핸드폰','이메일'
// 		           ,'협회직급' // del 3
		           ,'근무처대분류','근무처소분류','근무처명','근무처전화번호'
// 		           ,'근무처우편번호' // del 4
		           ,'근무처주소'
// 		           ,'직렬','아이디' // del 5,6
// 				   ,'발송지','발송호칭','직급','최종학력','운영형태' // del 7, 8, 9, 10, 11
				   ,'회비구분'
// 				   ,'입금방법','학위','산하단체', '자격증종류', '자격증유효성' // del 12, 13, 14, 15, 16
				   ,'회원번호'
				   ,'탈퇴사유'
				   ,'회원구분코드', '우편번호', '주소', '지부', '발송호칭', '수신처구분', '수신여부'],

		colModel: [
{name:'ncount'			, index:'ncount asc, invdate', width:30	, align:"center", sortable:false, hidden:true},		//번호
{name:'pers_name'		, index:'pers_name'			, width:60	, align:"center", sortable:false},		//이름
{name:'lic_no'			, index:'lic_no'			, width:60	, align:"center", sortable:false},		//면허번호
// {name:'pers_no'			, index:'pers_no'			, width:110	, align:"center", sortable:false},		//주민등록번호 (서버에서 암호화 및.. 숨겨서옴) // JUMIN_DEL
{name:'pers_birth'			, index:'pers_birth'			, width:80	, align:"center", sortable:false,	formatter:persBirth},		//생년월일
{name:'han_code_bran'	, index:'han_code_bran'		, width:110	, align:"center", sortable:false},		//지부명//한글화
{name:'han_code_big'	, index:'han_code_big'		, width:110	, align:"center", sortable:false},		//분과명//한글화
{name:'han_code_sect'	, index:'han_code_sect'		, width:60	, align:"center", sortable:false},		//분회명//한글화
{name:'han_code_member'	, index:'han_code_member'	, width:160	, align:"center", sortable:false},		//회원종류//한글화
{name:'han_pers_state'	, index:'han_pers_state'	, width:60	, align:"center", sortable:false},		//회원상태//한글화

{name:'auth_start'		, index:'auth_start'		, width:100	, align:"center", sortable:false},		//회원유효기간 시작
{name:'auth_end'		, index:'auth_end'			, width:100	, align:"center", sortable:false},		//회원유효기간 종료
{name:'conform_dt'		, index:'conform_dt'		, width:100	, align:"center", sortable:false},		//인증일
{name:'han_code_receipt', index:'han_code_receipt'	, width:50	, align:"center", sortable:false},		//인증장소//한글화
// {name:'pers_post'		, index:'pers_post'			, width:50	, align:"left"	, sortable:false},		//집주소 // del 1
{name:'pers_add'		, index:'pers_add'			, width:240	, align:"left"	, sortable:false},		//집주소
// {name:'pers_tel'		, index:'pers_tel'			, width:110	, align:"center", sortable:false},		//집전화번호 // del 2
{name:'pers_hp'			, index:'pers_hp'			, width:110	, align:"center", sortable:false},		//핸드폰
{name:'email'			, index:'email'				, width:160	, align:"center", sortable:false},		//이메일

// {name:'han_kda_level'	, index:'han_kda_level'		, width:60	, align:"center", sortable:false},		//협회직급//한글화 // del 3
{name:'han_code_great'	, index:'han_code_great'	, width:110	, align:"center", sortable:false},		//근무처대분류//한글화
{name:'han_code_small'	, index:'han_code_small'	, width:110	, align:"center", sortable:false},		//근무처소분류//한글화
{name:'company_name'	, index:'company_name'		, width:160	, align:"center", sortable:false, formatter:decode11},		//근무처명
{name:'company_tel'		, index:'company_tel'		, width:110	, align:"center", sortable:false},		//근무처전화번호
// {name:'company_post'	, index:'company_post'		, width:50	, align:"left"	, sortable:false},		//집주소 // del 4
{name:'company_add'		, index:'company_add'		, width:240	, align:"left"	, sortable:false},		//근무처주소
// {name:'han_job_line_code', index:'han_job_line_code', width:110	, align:"center", sortable:false},		//직렬//한글화 // del 5
				
// {name:'id'				, index:'id'				, width:80	, align:"center", sortable:false},		//아이디 // del 6


// {name:'han_code_place'		, index:'han_code_place'	, width:80	, align:"left"	, sortable:false},		//발송지 // del 7
// {name:'han_code_call'		, index:'han_code_call'		, width:80	, align:"left"	, sortable:false},		//발송호칭 // del 8
// {name:'han_job_level_code'	, index:'han_job_level_code', width:80	, align:"left"	, sortable:false},		//직급 // del 9
// {name:'han_code_scholar'	, index:'han_code_scholar'	, width:80	, align:"left"	, sortable:false},		//최종학력 // del 10
// {name:'han_code_use'		, index:'han_code_use'		, width:80	, align:"left"	, sortable:false},		//운영형태 // del 11
{name:'han_dues_gubun'		, index:'han_dues_gubun'	, width:80	, align:"center", sortable:false},		//회비구분
// {name:'han_code_pay_flag'	, index:'han_code_pay_flag'	, width:80	, align:"left"	, sortable:false},		//입금방법 // del 12
// {name:'han_code_univer'		, index:'han_code_univer'	, width:80	, align:"left"	, sortable:false},		//학위 // del 13
// {name:'han_code_sub'		, index:'han_code_sub'		, width:80	, align:"left"	, sortable:false},		//산하단체 // del 14
// {name:'han_code_certifi'	, index:'han_code_certifi'	, width:80	, align:"left"	, sortable:false},		//자격증종류 // del 15
// {name:'print_state'			, index:'print_state'		, width:80	, align:"left"	, sortable:false},		//자격증유효 // del 16
{name:'code_pers'			, index:'code_pers'			, width:80	, align:"left"	, sortable:false},		//회원번호
{name:'memo_text'			, index:'memo_text'			, width:240	, align:"left"	, sortable:false},	//탈퇴사유

//dm 발송에 사용
{name:'code_member'		, index:'code_member'	, width:80	, align:"left"	, sortable:false, hidden:true},	//회원구분코드
{name:'code_post'		, index:'code_post'		, width:80	, align:"left"	, sortable:false, hidden:true},	//우편번호
{name:'pers_com_add'	, index:'pers_com_add'	, width:80	, align:"left"	, sortable:false, hidden:true},	//주소
{name:'code_bran'		, index:'code_bran'		, width:80	, align:"left"	, sortable:false, hidden:true},	//지부
{name:'code_call'		, index:'code_call'		, width:80	, align:"left"	, sortable:false, hidden:true},	//발송호칭
{name:'code_place'		, index:'code_place'	, width:80	, align:"left"	, sortable:false, hidden:true},	//수신처구분
{name:'rece_yn'			, index:'rece_yn'		, width:80	, align:"left"	, sortable:false, hidden:true}		//수신여부


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
		caption:"회원 상세조회"
	});
				
	jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true,position:'left'});
 	jQuery("#list").setGridWidth(1100,false);
 	

 	//상세검색
	jQuery("#memberAdvancedSearch").click( function(e) {
		e.preventDefault();
		
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');
		
 		if( rownum.length==0)	{			alert("상세검색을 원하는 회원을 선택해 주십시요.");			return;		}
	 	if( rownum.length>1)	{			alert("상세검색은 한명의 회원만 선택해야 합니다.");			return;		}

	 	var row			= 	$("#list").getRowData(rownum);
	 	var code_pers	= row.code_pers;
	 	//alert(code_pers);//code_pers
		window.open('memberBasic.do?method=memInfo&pCode='+code_pers,'','width=1150, height=700, resizable=no,scrollbars=yes');
	});

 	
	
});

	$(function() {	$( "#datepicker" ).datepicker();		});
	$(function() {	$( "#datepicker1" ).datepicker();		});
	$(function() {	$( "#datepicker2" ).datepicker();		});
	$(function() {	$( "#datepicker3" ).datepicker();		});
	
	
</script>
			
			
			
			
			
<script>
$(document).ready(function() {
	
	//전체 권한 여부
	powerinit("102", eval(<%=userpowerm1%>) );



	var codeList	=eval(<%=comList%>);		//전체공통쿼리
	var subList		=eval(<%=subList%>);		//산하단체 리스트
	var certifiList	=eval(<%=certifiList%>);	//자격증 종류 리스트
	
	var form				= memberInputForm;				//폼
	
	var cnt				=0;
	var groupcode		= '';
	var detcode		= '';
	var detcodename	= '';
	
	var zcnt				=0;
	var tempcd1		=0;

	
	for( var i=0; i<codeList.length; i++) {
	
		groupcode		= codeList[i].groupcode;
		detcode			= codeList[i].detcode;
		detcodename	= codeList[i].detcodename;

		if(cnt==0) {
			detcode = '';
			detcodename = '선택';
			i--;
		}

		//첫째줄 중
		//if(groupcode=='007')	//지부
		if(groupcode=='034')	//교육지부
		{
			tempcd1	= codeList[i].tempcd1;
			if(tempcd1 == '') {	//00 값이 '' 이므로 뺀다.
				i++;
				detcode = '';
				tempcd1 = '전체';
			}
			form.code_bran.options[zcnt]=new Option(tempcd1 , detcode , false, false);
			//form.code_bran.options[cnt]=new Option(detcodename , detcode , false, false);
	
			zcnt++;
			
			if(codeList.length == i+1 || codeList[i+1].groupcode != groupcode)	cnt=0;
			
		}//얘만 for  문에서 별개로 돌게해야하나?
		
	 	if(groupcode=='030')	//발송지
			form.code_place.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='010')	//직렬
			form.job_line_code.options[cnt]=new Option(detcodename , detcode , false, false);

		//둘째줄 중
		if(groupcode=='004')	//분과명
			form.code_big.options[cnt]=new Option(detcodename , detcode , false, false);
	 	if(groupcode=='018')	//발송호칭
			form.code_call.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='011')	//직급
			form.job_level_code.options[cnt]=new Option(detcodename , detcode , false, false);

		//셋째줄 중
		if(groupcode=='013')	//인증장소
			form.code_receipt.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='027')	//최종학력
			form.code_scholar.options[cnt]=new Option(detcodename , detcode , false, false);
	 	if(groupcode=='001')	//운영형태
			form.code_use.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='038')	//회비구분
			form.dues_gubun.options[cnt]=new Option(detcodename , detcode , false, false);

		//넷째줄 중
		if(groupcode=='006')	//회원종류
			form.code_member.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='014')	//입금방법
			form.code_pay_flag.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='019')	//학위
			form.code_univer.options[cnt]=new Option(detcodename , detcode , false, false);

		//다섯째줄 중
		if(groupcode=='050')	//협회직급
			form.kda_level.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='021')	//회원상태
			form.pers_state.options[cnt]=new Option(detcodename , detcode , false, false);

		cnt++;
		if(codeList.length == i+1 || codeList[i+1].groupcode != groupcode)	cnt=0;

	}
	form.code_bran.value = '<%=g_bran%>';
	if(form.code_bran.value == '01'){
		form.code_bran.disabled=false;
		form.code_bran.value='';
	}

	//산하단체 리스트
	cnt = 0;
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
	}
	
	//자격증 종류 리스트
	cnt = 0;
	var code_certifi	= "";
	var certifi_name	= "";
	
	for( var i=-1; i<certifiList.length; i++) {
		if(i==-1) {
			code_certifi		= '';
			certifi_name	= '선택';
		}else {
			code_certifi		= certifiList[i].code_certifi;
			certifi_name	= certifiList[i].certifi_name;
		}
		form.code_certifi.options[cnt]=new Option(certifi_name , code_certifi , false, false);
		cnt++;
	}
});



function fn_code_bran(form){//지부명 : 1. 분과명(code_big)이 선택된 상태에 지부명(code_bran)이 선택되었으면 그에 따라 분회명(code_sect)이 변화한다.
	
	if(form.code_big.value =="") return;

	//지부명이 선택으로 바꾸면 근무처소분류를 선택으로 바꾸고 리턴한다.
	if(form.code_bran.value ==""){
		form.code_sect.options.length = 0;
		form.code_sect.options[0] = new Option("선택","",false,false);
		form.code_sect.disabled=true;
		
		return;
	}
	
	form.code_sect.disabled=false;
	form.code_sect.options.length = 0;
	
	//1. 지부(code_bran)가 선택된 상태에 분과명(code_big)이 선택되었으면 그에 따라 분회명(code_sect)이 변화한다.		
	var codeList	=eval(<%=comList%>);		//전체공통쿼리
	var cnt			=1;

	form.code_sect.options[0]=new Option('선택' , '' , false, false);
	
	for( var i=0; i<codeList.length; i++) {

		if(codeList[i].groupcode=='009') {	//소속 분회
			if( form.code_bran.value == codeList[i].tempcd1 && form.code_big.value == codeList[i].tempcd2) {
				form.code_sect.options[cnt]=new Option(codeList[i].detcodename , codeList[i].detcode , false, false);
				cnt++;
			}
		}
	} 
	if( cnt == 1) form.code_sect.disabled=true;
}


function fn_code_big(form){//분과명 : 1. 분과명(code_big)이 선택되면 근무처소분류(code_small)가 변화한다.
	//                                             2. 지부(code_bran)가 선택된 상태에 분과명(code_big)이 선택되었으면 그에 따라 분회명(code_sect)이 변화한다.

	//1. 분과명(code_big)이 선택되면 근무처소분류(code_small)가 변화한다.
	//분과명을 선택으로 바꾸면 분회명과, 근무처소분류 모두 선택으로 바꾸고 리턴한다.
	if(form.code_big.value ==""){
		form.code_small.options.length = 0;
		form.code_small.options[0] = new Option("선택","",false,false);
		form.code_small.disabled=true;

		form.code_sect.options.length = 0;
		form.code_sect.options[0] = new Option("선택","",false,false);
		form.code_sect.disabled=true;
		return;
	}
	
	form.code_small.disabled=false;
	form.code_small.options.length = 0;
	
	var codeList	=eval(<%=comList%>);		//전체공통쿼리
	var cnt			=1;

	form.code_small.options[0]=new Option('선택' , '' , false, false);
	
	for( var i=0; i<codeList.length; i++) {

		if(codeList[i].groupcode=='005') {	//근무처 소분류
			if( form.code_big.value == codeList[i].tempcd2) {
				form.code_small.options[cnt]=new Option(codeList[i].detcodename , codeList[i].detcode , false, false);
				cnt++;
			}
		}
	} 
	if( cnt == 1) form.code_small.disabled=true;
	
	//2. 지부(code_bran)가 선택된 상태에 분과명(code_big)이 선택되었으면 그에 따라 분회명(code_sect)이 변화한다.
	if(form.code_bran.value != "" ) fn_code_bran(form);	//지부 변경되었을때 분회명 변경되는 함수.
	
}





	//회원유효기간 클릭했을때 내용 초기화 
	function fn_auth_start(form)	{form.auth_start.value="";	}
	function fn_auth_end(form)	{form.auth_end.value="";	}
	//인증기간 클릭했들때 내용 초기화
	function fn_conform_dt_start(form)	{form.conform_dt_start.value="";	}
	function fn_conform_dt_end(form)		{form.conform_dt_end.value="";		}


	
	/* DM 발송 */
	function sendDM(form) {
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata=new Array();

		var url="";
		
		var param = "";
		param+="&register="+escape(encodeURIComponent("<%=g_name%>"));

		
		if( rownum.length > 0 ){ 
			var code_pers	="";//회원구분코드	code_pers
			
			for(var i=0;i<rownum.length;i++){
				rowdata= 	$("#list").getRowData(rownum[i]);
				if(i > 0) code_pers	+= ",";
				code_pers+= rowdata.code_pers;
			}
			param += "&code_pers="+code_pers;
			
		//	alert("DM발송 조건 "+param);
			url="memberSearch.do?method=sendDMForm&chk=CHK&param="+param;
			
		}else if( rownum.length == 0 ){	
			//여기다 전체 검색(검색과 같은 루트를 탄다.)
			
			//첫째줄-----------------------------------------------
			if(form.pers_name.value			!= "")	param+="&pers_name="	+escape(encodeURIComponent(form.pers_name.value));	//이름
			if(form.auth_start.value		!= "")	param+="&auth_start="	+form.auth_start.value;		//(회원 유효기간은 시작일만 가지고 한다.)
			if(form.auth_end.value			!= "")	param+="&auth_end="		+form.auth_end.value;		//(회원 유효기간은 시작일만 가지고 한다.)
	
			if(form.isEnd.checked			== true) param+="&isEnd=1";//만료자
			else param+="&isEnd=0";//유효자
			
			if(form.code_bran.value			!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_place.value		!= "")	param+="&code_place="	+form.code_place.value;		//발송지 수신처
			if(form.job_line_code.value		!= "")	param+="&job_line_code="+form.job_line_code.value;	//직렬
	 		
			//둘째줄-----------------------------------------------
			if(form.lic_no.value			!= "")	param+="&lic_no="		+form.lic_no.value;			//면허번호이름
			if(form.conform_dt_start.value	!= "")	param+="&conform_dt_start="	+form.conform_dt_start.value;	//입금기간은 확인일자가 시작일과
			if(form.conform_dt_end.value	!= "")	param+="&conform_dt_end="+form.conform_dt_end.value;//종료일 안에 있으면 된다.
			if(form.code_big.value			!= "")	param+="&code_big="		+form.code_big.value;		//분과명
			if(form.code_call.value			!= "")	param+="&code_call="	+form.code_call.value;		//발송호칭
			if(form.job_level_code.value	!= "")	param+="&job_level_code="+form.job_level_code.value;//직급
	
			//셋째줄-----------------------------------------------
// 			JUMIN_DEL
// 			if(form.pers_no.value				!= "")	{
// 				var pers_no = form.pers_no.value;
// 				var arrayPersNo = pers_no.split("-");
// 				pers_no = arrayPersNo[0]+(arrayPersNo[1] == null ? "" : arrayPersNo[1]);
// 				param+="&pers_no="					+pers_no;					//주민등록번호
// 			}
			if(form.pers_birth.value		!= "")	param+="&pers_birth="	+form.pers_birth.value;		//생년월일
			if(form.code_receipt.value		!= "")	param+="&code_receipt="	+form.code_receipt.value;	//인증장소
			if(form.code_sect.value			!= "")	param+="&code_sect="	+form.code_sect.value;		//분회명
			if(form.code_scholar.value		!= "")	param+="&code_scholar="	+form.code_scholar.value;	//최종학력
			if(form.code_use.value			!= "")	param+="&code_use="		+form.code_use.value;		//운영형태
			
			//넷째줄-----------------------------------------------
			if(form.code_member.value		!= "")	param+="&code_member="	+form.code_member.value;	//회원종류
			if(form.code_pay_flag.value		!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법 code_inout_gubun이 1인것 중.
			if(form.code_small.value		!= "")	param+="&code_small="	+form.code_small.value;		//근무처소분류
			if(form.code_univer.value		!= "")	param+="&code_univer="	+form.code_univer.value;	//학위
			if(form.code_sub.value			!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회				
			//다섯째줄---------------------------------------------
	
			if(form.kda_level.value			!= "")	param+="&kda_level="	+form.kda_level.value;		//협회직급
			if(form.code_certifi.value		!= "")	param+="&code_certifi="	+form.code_certifi.value;	//자격증종류
			if(form.print_state.value		!= "")	param+="&print_state="	+form.print_state.value;	//자격증말소자
			if(form.id.value				!= "")	param+="&id="			+form.id.value;				//아이디
			if(form.pers_state.value		!= "")	param+="&pers_state="	+form.pers_state.value;		//회원상태

			//여섯째줄---------------------------------------------
			if(form.dues_gubun.value		!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			
			
	//		alert("DM발송 전체 "+param);
			url="memberSearch.do?method=sendDMForm&chk=ALL&param="+param;
		}	
		var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=no, width=340, height=300, left=100, top=100";
		window.open(url,"sendDM", status);
		//window.location.href=url;
		
	}
	


	/* 쪽지 발송 */
	function sendMsgForm(form){
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata=new Array();

		var url="";
		
		var param = "";
		param+="&register="+escape(encodeURIComponent("<%=g_name%>"));

		
		if( rownum.length > 0 ){ 
			var code_pers	="";//회원구분코드	code_pers
			
			for(var i=0;i<rownum.length;i++){
				rowdata= 	$("#list").getRowData(rownum[i]);
				if(i > 0) code_pers	+= ",";
				code_pers+= rowdata.code_pers;
			}
			param += "&code_pers="+code_pers;
			
		//	alert("쪽지 발송 조건 "+param);
			url="memberSearch.do?method=sendMsgForm&chk=CHK&param="+param;
			
		}else if( rownum.length == 0 ){	
			//여기다 전체 검색(검색과 같은 루트를 탄다.)
			
			//첫째줄-----------------------------------------------
			if(form.pers_name.value			!= "")	param+="&pers_name="	+escape(encodeURIComponent(form.pers_name.value));	//이름
			if(form.auth_start.value		!= "")	param+="&auth_start="	+form.auth_start.value;		//(회원 유효기간은 시작일만 가지고 한다.)
			if(form.auth_end.value			!= "")	param+="&auth_end="		+form.auth_end.value;		//(회원 유효기간은 시작일만 가지고 한다.)
	
			if(form.isEnd.checked			== true) param+="&isEnd=1";//만료자
			else param+="&isEnd=0";//유효자
			
			if(form.code_bran.value			!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_place.value		!= "")	param+="&code_place="	+form.code_place.value;		//발송지 수신처
			if(form.job_line_code.value		!= "")	param+="&job_line_code="+form.job_line_code.value;	//직렬
	 		
			//둘째줄-----------------------------------------------
			if(form.lic_no.value			!= "")	param+="&lic_no="		+form.lic_no.value;			//면허번호이름
			if(form.conform_dt_start.value	!= "")	param+="&conform_dt_start="	+form.conform_dt_start.value;	//입금기간은 확인일자가 시작일과
			if(form.conform_dt_end.value	!= "")	param+="&conform_dt_end="+form.conform_dt_end.value;//종료일 안에 있으면 된다.
			if(form.code_big.value			!= "")	param+="&code_big="		+form.code_big.value;		//분과명
			if(form.code_call.value			!= "")	param+="&code_call="	+form.code_call.value;		//발송호칭
			if(form.job_level_code.value	!= "")	param+="&job_level_code="+form.job_level_code.value;//직급
	
			//셋째줄-----------------------------------------------
// 			JUMIN_DEL
// 			if(form.pers_no.value				!= "")	{
// 				var pers_no = form.pers_no.value;
// 				var arrayPersNo = pers_no.split("-");
// 				pers_no = arrayPersNo[0]+(arrayPersNo[1] == null ? "" : arrayPersNo[1]);
// 				param+="&pers_no="					+pers_no;					//주민등록번호
// 			}
			if(form.pers_birth.value		!= "")	param+="&pers_birth="	+form.pers_birth.value;		//생년월일
			if(form.code_receipt.value		!= "")	param+="&code_receipt="	+form.code_receipt.value;	//인증장소
			if(form.code_sect.value			!= "")	param+="&code_sect="	+form.code_sect.value;		//분회명
			if(form.code_scholar.value		!= "")	param+="&code_scholar="	+form.code_scholar.value;	//최종학력
			if(form.code_use.value			!= "")	param+="&code_use="		+form.code_use.value;		//운영형태
			
			//넷째줄-----------------------------------------------
			if(form.code_member.value		!= "")	param+="&code_member="	+form.code_member.value;	//회원종류
			if(form.code_pay_flag.value		!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법 code_inout_gubun이 1인것 중.
			if(form.code_small.value		!= "")	param+="&code_small="	+form.code_small.value;		//근무처소분류
			if(form.code_univer.value		!= "")	param+="&code_univer="	+form.code_univer.value;	//학위
			if(form.code_sub.value			!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회				
			//다섯째줄---------------------------------------------
	
			if(form.kda_level.value			!= "")	param+="&kda_level="	+form.kda_level.value;		//협회직급
			if(form.code_certifi.value		!= "")	param+="&code_certifi="	+form.code_certifi.value;	//자격증종류
			if(form.print_state.value		!= "")	param+="&print_state="	+form.print_state.value;	//자격증말소자
			if(form.id.value				!= "")	param+="&id="			+form.id.value;				//아이디
			if(form.pers_state.value		!= "")	param+="&pers_state="	+form.pers_state.value;		//회원상태

			//여섯째줄---------------------------------------------
			if(form.dues_gubun.value		!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			
	//		alert("쪽지 발송 전체 "+param);
			url="memberSearch.do?method=sendMsgForm&chk=ALL&param="+param;
		}	
		var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=no, width=370, height=500, left=280, top=80";
		window.open(url,"sendMsg", status);
		
	}

	/* 문자 발송 */
	function sendUmsForm(form){
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata=new Array();
		var rowdata2=new Array();

		var url="";
		
		var param = "";
		param+="&register="+"<%=g_id%>";

		
		if( rownum.length > 0 ){ 
			var pers_hp	="";//회원전화번호	pers_hp
			var pers_name="";//회원이름
			var code_pers="";
			
			var isok = "OK";
			for(var i=0;i<rownum.length;i++){
				rowdata= 	$("#list").getRowData(rownum[i]);

				
				for(var ii=0; ii<i; ii++) {
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
			if(pers_hp==""){
				alert("문자발송 대상이 없습니다.\n\n선택한 사용자들의 핸드폰정보 유무를 확인해 주세요.");
				return;
			}
			param += "&code_pers="+code_pers+"&pers_hp="+pers_hp+"&pers_name="+escape(encodeURIComponent(pers_name));
			
			//alert("쪽지 발송 조건 "+param);
			url="memberSearch.do?method=sendUmsForm&chk=CHK&param="+param;
			
		}else if( rownum.length == 0 ){	
			//여기다 전체 검색(검색과 같은 루트를 탄다.)
			
			//첫째줄-----------------------------------------------
			if(form.pers_name.value			!= "")	param+="&pers_name="	+escape(encodeURIComponent(form.pers_name.value));	//이름
			if(form.auth_start.value		!= "")	param+="&auth_start="	+form.auth_start.value;		//(회원 유효기간은 시작일만 가지고 한다.)
			if(form.auth_end.value			!= "")	param+="&auth_end="		+form.auth_end.value;		//(회원 유효기간은 시작일만 가지고 한다.)
	
			if(form.isEnd.checked			== true) param+="&isEnd=1";//만료자
			else param+="&isEnd=0";//유효자
			
			if(form.code_bran.value			!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
			if(form.code_place.value		!= "")	param+="&code_place="	+form.code_place.value;		//발송지 수신처
			if(form.job_line_code.value		!= "")	param+="&job_line_code="+form.job_line_code.value;	//직렬
	 		
			//둘째줄-----------------------------------------------
			if(form.lic_no.value			!= "")	param+="&lic_no="		+form.lic_no.value;			//면허번호이름
			if(form.conform_dt_start.value	!= "")	param+="&conform_dt_start="	+form.conform_dt_start.value;	//입금기간은 확인일자가 시작일과
			if(form.conform_dt_end.value	!= "")	param+="&conform_dt_end="+form.conform_dt_end.value;//종료일 안에 있으면 된다.
			if(form.code_big.value			!= "")	param+="&code_big="		+form.code_big.value;		//분과명
			if(form.code_call.value			!= "")	param+="&code_call="	+form.code_call.value;		//발송호칭
			if(form.job_level_code.value	!= "")	param+="&job_level_code="+form.job_level_code.value;//직급
	
			//셋째줄-----------------------------------------------
// 			JUMIN_DEL
// 			if(form.pers_no.value				!= "")	{
// 				var pers_no = form.pers_no.value;
// 				var arrayPersNo = pers_no.split("-");
// 				pers_no = arrayPersNo[0]+(arrayPersNo[1] == null ? "" : arrayPersNo[1]);
// 				param+="&pers_no="					+pers_no;					//주민등록번호
// 			}
			if(form.pers_birth.value		!= "")	param+="&pers_birth="	+form.pers_birth.value;		//생년월일
			if(form.code_receipt.value		!= "")	param+="&code_receipt="	+form.code_receipt.value;	//인증장소
			if(form.code_sect.value			!= "")	param+="&code_sect="	+form.code_sect.value;		//분회명
			if(form.code_scholar.value		!= "")	param+="&code_scholar="	+form.code_scholar.value;	//최종학력
			if(form.code_use.value			!= "")	param+="&code_use="		+form.code_use.value;		//운영형태
			
			//넷째줄-----------------------------------------------
			if(form.code_member.value		!= "")	param+="&code_member="	+form.code_member.value;	//회원종류
			if(form.code_pay_flag.value		!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법 code_inout_gubun이 1인것 중.
			if(form.code_small.value		!= "")	param+="&code_small="	+form.code_small.value;		//근무처소분류
			if(form.code_univer.value		!= "")	param+="&code_univer="	+form.code_univer.value;	//학위
			if(form.code_sub.value			!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회				
			//다섯째줄---------------------------------------------
	
			if(form.kda_level.value			!= "")	param+="&kda_level="	+form.kda_level.value;		//협회직급
			if(form.code_certifi.value		!= "")	param+="&code_certifi="	+form.code_certifi.value;	//자격증종류
			if(form.print_state.value		!= "")	param+="&print_state="	+form.print_state.value;	//자격증말소자
			if(form.id.value				!= "")	param+="&id="			+form.id.value;				//아이디
			if(form.pers_state.value		!= "")	param+="&pers_state="	+form.pers_state.value;		//회원상태

			//여섯째줄---------------------------------------------
			if(form.dues_gubun.value		!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
			

	//		alert("쪽지 발송 전체 "+param);
			url="memberSearch.do?method=sendUmsForm&chk=ALL&param="+param;
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
	 	var addr_infos="";
	
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
	 	param+="&paramfrom=memberSearchList&addr_infos="+addr_infos;
	 	
		//alert("메일발송 = "+param);
		
		window.open('memberBasic.do?method=eMail&toAddr='+param,"email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
	}
		
		
	//엑셀
	function excelDown(form){
			
		var rownum = jQuery("#list").jqGrid('getGridParam','records')
		if( rownum == 0) {
			alert("출력할 정보를 조회해 주세요.");
			return;
		}
		
		if((form.auth_start.value	!= "" && form.auth_end.value	== "") ||
			(form.auth_start.value	== "" && form.auth_end.value	!= "") ) {
			alert("회원유효기간으로 조회를 위해서는 시작일과 종료일이 모두 들어가야 합니다.");
			return;
		}
		
		if( (form.conform_dt_start.value	!= "" && form.conform_dt_end.value		== "") ||
			(form.conform_dt_start.value	== "" && form.conform_dt_end.value	!= "") ) {
			alert("인증일자로 조회를 위해서는 시작일과 종료일이 모두 들어가야 합니다.");
			return;
		}
		
		if(form.auth_start.value > form.auth_end.value) {
			alert("회원유효기간 시작일이 종료일보다 큽니다.");
			return;
		}
		if(form.conform_dt_start.value > form.conform_dt_end.value) {
			alert("인증일자 시작일이 종료일보다 큽니다.");
			return;
		}
			var param = "";
		//첫째줄-----------------------------------------------
		if(form.pers_name.value			!= "")	param+="&pers_name="	+escape(encodeURIComponent(form.pers_name.value));	//이름
		if(form.auth_start.value		!= "")	param+="&auth_start="	+form.auth_start.value;		//(회원 유효기간은 시작일만 가지고 한다.)
		if(form.auth_end.value			!= "")	param+="&auth_end="		+form.auth_end.value;		//(회원 유효기간은 시작일만 가지고 한다.)

		if(form.isEnd.checked			== true) param+="&isEnd=1";//만료자
		else param+="&isEnd=0";//유효자
		
		if(form.code_bran.value			!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
		if(form.code_place.value		!= "")	param+="&code_place="	+form.code_place.value;		//발송지 수신처
		if(form.job_line_code.value		!= "")	param+="&job_line_code="+form.job_line_code.value;	//직렬
			
		//둘째줄-----------------------------------------------
		if(form.lic_no.value			!= "")	param+="&lic_no="		+form.lic_no.value;			//면허번호이름
		if(form.conform_dt_start.value	!= "")	param+="&conform_dt_start="	+form.conform_dt_start.value;	//입금기간은 확인일자가 시작일과
		if(form.conform_dt_end.value	!= "")	param+="&conform_dt_end="+form.conform_dt_end.value;//종료일 안에 있으면 된다.
		if(form.code_big.value			!= "")	param+="&code_big="		+form.code_big.value;		//분과명
		if(form.code_call.value			!= "")	param+="&code_call="	+form.code_call.value;		//발송호칭
		if(form.job_level_code.value	!= "")	param+="&job_level_code="+form.job_level_code.value;//직급
			//셋째줄-----------------------------------------------
// 			JUMIN_DEL
// 		if(form.pers_no.value				!= "")	{
// 			var pers_no = form.pers_no.value;
// 			var arrayPersNo = pers_no.split("-");
// 			pers_no = arrayPersNo[0]+(arrayPersNo[1] == null ? "" : arrayPersNo[1]);
// 			param+="&pers_no="					+pers_no;					//주민등록번호
// 		}
		if(form.pers_birth.value		!= "")	param+="&pers_birth="	+form.pers_birth.value;		//생년월일
		if(form.code_receipt.value		!= "")	param+="&code_receipt="	+form.code_receipt.value;	//인증장소
		if(form.code_sect.value			!= "")	param+="&code_sect="	+form.code_sect.value;		//분회명
		if(form.code_scholar.value		!= "")	param+="&code_scholar="	+form.code_scholar.value;	//최종학력
		if(form.code_use.value			!= "")	param+="&code_use="		+form.code_use.value;		//운영형태
		
		//넷째줄-----------------------------------------------
		if(form.code_member.value		!= "")	param+="&code_member="	+form.code_member.value;	//회원종류
		if(form.code_pay_flag.value		!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법 code_inout_gubun이 1인것 중.
		if(form.code_small.value		!= "")	param+="&code_small="	+form.code_small.value;		//근무처소분류
		if(form.code_univer.value		!= "")	param+="&code_univer="	+form.code_univer.value;	//학위
		if(form.code_sub.value			!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회				
	
		//다섯째줄---------------------------------------------
		if(form.kda_level.value			!= "")	param+="&kda_level="	+form.kda_level.value;		//협회직급
		if(form.code_certifi.value		!= "")	param+="&code_certifi="	+form.code_certifi.value;	//자격증종류
		if(form.print_state.value		!= "")	param+="&print_state="	+form.print_state.value;	//자격증말소자
		if(form.id.value				!= "")	param+="&id="			+form.id.value;				//아이디
		if(form.pers_state.value		!= "")	param+="&pers_state="	+form.pers_state.value;		//회원상태

		//여섯째줄---------------------------------------------
		if(form.dues_gubun.value		!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
		
		
		//param+="&register="+"<%=g_name%>";

		//var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=no, width=340, height=300, left=100, top=100";
		
		var url="memberSearch.do?method=pers_check"+param;
		window.open(url,"exceldown","width=400, height=500, left=480, top=200,scrollbars=yes");	

	}

	
	//검색 함수
	function goSearch(form){
		if((form.auth_start.value	!= "" && form.auth_end.value	== "") ||
			(form.auth_start.value	== "" && form.auth_end.value	!= "") ) {
			alert("회원유효기간으로 조회를 위해서는 시작일과 종료일이 모두 들어가야 합니다.");
			return;
		}
		
		if( (form.conform_dt_start.value	!= "" && form.conform_dt_end.value		== "") ||
			(form.conform_dt_start.value	== "" && form.conform_dt_end.value	!= "") ) {
			alert("인증일자로 조회를 위해서는 시작일과 종료일이 모두 들어가야 합니다.");
			return;
		}
		
		if(form.auth_start.value > form.auth_end.value) {
			alert("회원유효기간 시작일이 종료일보다 큽니다.");
			return;
		}
		if(form.conform_dt_start.value > form.conform_dt_end.value) {
			alert("인증일자 시작일이 종료일보다 큽니다.");
			return;
		}

		var param = "";
		//첫째줄-----------------------------------------------
		if(form.pers_name.value			!= "")	param+="&pers_name="	+escape(encodeURIComponent(form.pers_name.value));	//이름
		if(form.auth_start.value		!= "")	param+="&auth_start="	+form.auth_start.value;		//(회원 유효기간은 시작일만 가지고 한다.)
		if(form.auth_end.value			!= "")	param+="&auth_end="		+form.auth_end.value;		//(회원 유효기간은 시작일만 가지고 한다.)

		if(form.isEnd.checked			== true) param+="&isEnd=1";//만료자
		else param+="&isEnd=0";//유효자
		
		if(form.code_bran.value			!= "")	param+="&code_bran="	+form.code_bran.value;		//지부
		if(form.code_place.value		!= "")	param+="&code_place="	+form.code_place.value;		//발송지 수신처
		if(form.job_line_code.value		!= "")	param+="&job_line_code="+form.job_line_code.value;	//직렬
 		
		//둘째줄-----------------------------------------------
		if(form.lic_no.value			!= "")	param+="&lic_no="		+form.lic_no.value;			//면허번호이름
		if(form.conform_dt_start.value	!= "")	param+="&conform_dt_start="	+form.conform_dt_start.value;	//입금기간은 확인일자가 시작일과
		if(form.conform_dt_end.value	!= "")	param+="&conform_dt_end="+form.conform_dt_end.value;//종료일 안에 있으면 된다.
		if(form.code_big.value			!= "")	param+="&code_big="		+form.code_big.value;		//분과명
		if(form.code_call.value			!= "")	param+="&code_call="	+form.code_call.value;		//발송호칭
		if(form.job_level_code.value	!= "")	param+="&job_level_code="+form.job_level_code.value;//직급

		//셋째줄-----------------------------------------------
// 		JUMIN_DEL
// 		if(form.pers_no.value				!= "")	{
// 			var pers_no = form.pers_no.value;
// 			var arrayPersNo = pers_no.split("-");
// 			pers_no = arrayPersNo[0]+(arrayPersNo[1] == null ? "" : arrayPersNo[1]);
// 			param+="&pers_no="					+pers_no;					//주민등록번호
// 		}
		if(form.pers_birth.value		!= "")	param+="&pers_birth="	+form.pers_birth.value;		//생년월일
		if(form.code_receipt.value		!= "")	param+="&code_receipt="	+form.code_receipt.value;	//인증장소
		if(form.code_sect.value			!= "")	param+="&code_sect="	+form.code_sect.value;		//분회명
		if(form.code_scholar.value		!= "")	param+="&code_scholar="	+form.code_scholar.value;	//최종학력
		if(form.code_use.value			!= "")	param+="&code_use="		+form.code_use.value;		//운영형태
		
		//넷째줄-----------------------------------------------
		if(form.code_member.value		!= "")	param+="&code_member="	+form.code_member.value;	//회원종류
		if(form.code_pay_flag.value		!= "")	param+="&code_pay_flag="+form.code_pay_flag.value;	//입금방법 code_inout_gubun이 1인것 중.
		if(form.code_small.value		!= "")	param+="&code_small="	+form.code_small.value;		//근무처소분류
		if(form.code_univer.value		!= "")	param+="&code_univer="	+form.code_univer.value;	//학위
		if(form.code_sub.value			!= "")	param+="&code_sub="		+form.code_sub.value;		//산하단체및 분야회				
		//다섯째줄---------------------------------------------

		if(form.kda_level.value			!= "")	param+="&kda_level="	+form.kda_level.value;		//협회직급
		if(form.code_certifi.value		!= "")	param+="&code_certifi="	+form.code_certifi.value;	//자격증종류
		if(form.print_state.value		!= "")	param+="&print_state="	+form.print_state.value;	//자격증말소자
		if(form.id.value				!= "")	param+="&id="			+form.id.value;				//아이디
		if(form.pers_state.value		!= "")	param+="&pers_state="	+form.pers_state.value;		//회원상태

		//여섯째줄---------------------------------------------
		if(form.dues_gubun.value		!= "")	param+="&dues_gubun="	+form.dues_gubun.value;		//회비구분
		
		
		//alert(param);
		//return;
// 		param+="&check=3"; // JUMIN_DEL
		
		$('#list').jqGrid('clearGridData');
		jQuery("#list").setGridParam({url:"memberSearch.do?method=memberSearchList&isSelect=Y"+param}).trigger("reloadGrid");
		
	}
	
	function decode11(cellvalue, options, rowObject ){
		var temp=decodeURIComponent(cellvalue);	
		return replaceAll(temp,"+"," ");
	}
	function replaceAll(temp,org,rep){
		return temp.split(org).join(rep);	
	}
	
	function persBirth(cellvalue, options, rowObject ){
// 		var birth=cellvalue.substring(0,4)+"."+cellvalue.substring(4,6)+"."+cellvalue.substring(6,8);
// 		return birth;
		return cellvalue; 
	}
	
</script>

	</head>
<body>

	<div id="contents"><!-- contents -->
	
		<!-- 상단 바로가기 바 -->
		<ul class="home">
			<li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
			<li><a href="memberForm.do">회원</a></li>					<li>&gt;</li>
			<li class="NPage">조건 검색</li>
		</ul>
	
		<div class="mTabmenu_01"><!-- mTabmenu_01 -->
		
<form name="memberInputForm" method="post"><!-- form -->
		
		<table class="mtable_02" cellspacing="0" summary="조건검색"><!-- table -->
          <caption>조건검색</caption>      
          
             		
<!-- 첫째줄 *********************************************************** -->	 
			<tr>
<!-- Persson_M_TBL(3) pers_name -->
				<td class="nobg">이름</td>
				<td class="nobg1">
				<input type="text" id="pers_name" name="pers_name" onfocus="fn_IME(this,'KOR');" onkeyup=" javascript:return check_StrByte(this, 50);" onKeyDown="javascript:if(event.keyCode==13){goSearch(memberInputForm);}" value="" size="13" maxlength="30" />
				</td>
				
<!-- dues_h_tbl : 6,7 (auth_start, auth_end) -->
				<td class="nobg2">회원유효<br/>기간</td>
				<td class="nobg1">
					<input type="text" name="auth_start" style="width:70px;" id="datepicker"  readonly onclick="fn_auth_start(memberInputForm);">&nbsp;~
					<input type="text" name="auth_end" style="width:70px;" id="datepicker1"  readonly onclick="fn_auth_end(memberInputForm);" >
					<input type="checkbox" name="isEnd" value="만료" />만료자
<!-- Persson_M_TBL(18) code_bran, "034" (007이 지부이나 여기에서는 035 교육지부를 사용한다.-->
				<td class="nobg2">지부</td>
				<td class="nobg1"><select name="code_bran" onchange="javascript:fn_code_bran(memberInputForm);" disabled="true"><option value="">선택</option></select></td>
<!-- Persson_M_TBL(25) code_place, "030" -->
				<td class="nobg2">발송지<br/>(수신처)</td>
				<td class="nobg1"><select name="code_place"><option value="">선택</option></select></td>
<!-- pers_company(17) job_line_code, "010" -->
				<td class="nobg2">직렬</td>
				<td class="nobg1"><select name="job_line_code"><option value="">선택</option></select></td>
			</tr>

<!-- 둘째줄 *********************************************************** -->
		<tr>
<!-- Persson_M_TBL(5) lic_no -->
				<td class="alt1">면허번호</td>
				<td><input type="text" id="lic_no" name="lic_no" onKeyUp="javascript:check_Int('memberInputForm','lic_no');" size="13" maxlength="6"/></td>
<!-- dues_b_tbl(11) conform_dt -->	<!-- 입금에 대한 확인일자가 조건 기간 안에 있는것. -->
				<td class="alt">인증기간</td>
				<td><input type="text" name="conform_dt_start" id="datepicker2" size="8" readonly onclick="fn_conform_dt_start(memberInputForm);">&nbsp;~<input type="text" name="conform_dt_end" id="datepicker3" size="8" readonly onclick="fn_conform_dt_end(memberInputForm);" ></td>
<!-- Persson_M_TBL(19) code_big, "004" -->
				<td class="alt">분과명</td>
				<td><select name="code_big" onchange="javascript:fn_code_big(memberInputForm);"><option value="">선택</option></select></td>
<!-- Persson_M_TBL(24) code_call, "018" -->			   
				<td class="alt">발송호칭</td>
				<td><select name="code_call"><option value="">선택</option></select></td>
<!-- pers_company(16) job_lovel_code, "011" -->
				<td class="alt">직급</td>
				<td><select name="job_level_code"><option value="">선택</option></select></td>
			</tr>
             
<!-- 셋째줄 *********************************************************** -->
			<tr>
			
<!-- JUMIN_DEL -->
<!-- <!-- Persson_M_TBL(4) pers_no - - > -->
<!--  				<td class="alt1">주민등록<br/>번호</td> -->
<!-- 				<td><input type="text" id="pers_no" onkeyup="javascript:check_JuminNo('memberInputForm','pers_no');" size="13" maxlength="14" /></td> -->
				
				<td class="alt1">생년월일</td>
				<td><input type="text" id="pers_birth" name="pers_birth" onKeyUp="javascript:check_Int('memberInputForm','pers_birth');" size="13" maxlength="8"/></td>
<!-- dues_b_tbl(8) code_receipt, "" -->
				<td class="alt">인증장소</td>
				<td><select name="code_receipt"><option value="">선택</option></select></td>
<!-- Persson_M_TBL(20) code_sect, "009" -->
				<td class="alt">분회명</td>
				<td><select name="code_sect" disabled='true'><option value="">선택</option></select></td>
<!-- Persson_M_TBL(21) code_scholar, "027" -->
				<td class="alt">최종학력</td>
				<td><select name="code_scholar"><option value="">선택</option></select></td>
<!-- pers_company(14) code_use, "001" -->
				<td class="alt">운영형태</td>
				<td><select name="code_use"><option value="">선택</option></select></td>
			</tr>
			
<!-- 넷째줄 *********************************************************** -->
			<tr>
<!-- Persson_M_TBL(2) code_member, "006" -->
				<td class="alt1">회원종류</td>
				<td><select name="code_member"><option value="">선택</option></select></td>
<!-- dues_b_tbl((6), 10) (code_inout_gubun)code_pay_flag, "014" -->
				<td class="alt">입금방법</td>
				<td><select name="code_pay_flag"><option value="">선택</option></select></td>
<!-- pers_company(11) code_small, "005" -->
				<td class="alt">근무처<br/>소분류</td>
				<td><select name="code_small" disabled='true'><option value="">선택</option></select></td>
<!-- Persson_M_TBL(22) code_univer, "019" -->
				<td class="alt">학위</td>	<td class="nobg1"><select name="code_univer"><option value="">선택</option>
				</select></td>
<!-- Persson_M_TBL(27) code_sub, "sub_tbl.code_sub" -->
				<td class="alt">산하단체 및<br/>분야회</td>
				<td><select name="code_sub">	<option value="">선택</option>	</select></td>
			</tr>
			
<!-- 다섯째줄 *********************************************************** -->
			<tr>
<!-- Persson_M_TBL(30) kda_level, "011" -->
				<td class="alt1">협회직급</td>
				<td><select name="kda_level"><option value="">선택</option></select></td>
<!-- result_print(2) code_certifi, "certifi.code_certifi" -->
				<td class="alt">자격증<br/>종류</td>
				<td><select name="code_certifi"><option value="">선택</option></select></td>
<!-- result_print(9) print_state, 1=유효, 0=말소 ) -->

				<td class="alt">자격증<br/>말소자</td>
				<td><select name="print_state"><option value="">전체</option><option value="1">유효자</option><option value="0">말소자</option></select></td>
				
				<td class="alt">아이디</td>
				<td>
					<input type="text" id="id" name="id" onfocus="fn_IME(this,'ENG');" onkeyup=" javascript:return check_StrByte(this, 20);" value="" size="13" maxlength="20" />
				</td>
				
				<td class="alt">회원상태</td>
				<td>
					<select name="pers_state"><option value="">선택</option></select>
				</td>
			</tr>
				
			
<!-- 여섯째줄 *********************************************************** -->
			<tr>
				<td class="alt1">회비구분</td>
				<td><select name="dues_gubun"><option value="">선택</option></select></td>
				<td colspan="8"></td>
			</tr>
				
			
<!-- 조건완료 *********************************************************** -->
	
        </table><!-- table -->
		 
		 <br>
		<div class="mTabmenu_01"> <!-- mTabmenu_01 -->
			<!-- 검색 버튼 -->
			<ul class="btnIcon_11">
				<li><a href="javascript:goSearch(memberInputForm);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>
			</ul>
			
			
			<br/><br/>
		
			<!-- 그리드 리스트 --> 		
			<table id="list"></table>
			<!-- 그리드 상태바 -->
			<div id="pager2"></div>
			
			<br/>
			
			<!-- 작업 버튼 -->
			<ul class="btnIcon_1" style="left:875px;">
				<li><a href="javascript:sendDM(memberInputForm);"		><img src="images/icon_s1.gif" 		alt="DM발송" 	/></a></li>
<!-- 				<li><a href="javascript:sendMsgForm(memberInputForm);"	><img src="images/icon_s2.gif" 		alt="쪽지" 		/></a></li> -->
				<!-- <li><a href="javascript:sendEmail(memberInputForm);"	><img src="images/icon_s3.gif"		alt="메일전송"	/></a></li>
				<li><a href="javascript:sendUmsForm(memberInputForm);"	><img src="images/icon_s4.gif"		alt="문자전송"	/></a></li> -->
				<li><a href="javascript:excelDown(memberInputForm);"	><img src="images/icon_excel.gif"	alt="엑셀저장"	/></a></li>
				<li><a href="#"><img src="images/icon_s5.gif" id="memberAdvancedSearch"	alt="상세검색" /></a></li>
			</ul>
		</div><!-- mTabmenu_01 -->
		
</form><!-- form -->
	</div><!-- contents -->
	
</body>
</html>

<iframe name="frm" width="0" height="0" tabindex=-1></iframe>