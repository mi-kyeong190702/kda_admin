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
<script src="js/form.js" type="text/javascript" ></script>

<%
	// 세션 null 체크
	if(session == null || session.getAttribute("G_ID") == null) {
		response.sendRedirect("login.jsp");
		return;
	}
	
	String g_id = session.getAttribute("G_ID") != null ? session.getAttribute("G_ID").toString() : "";
	String g_name = session.getAttribute("G_NAME") != null ? session.getAttribute("G_NAME").toString() : ""; 
	String g_bran = session.getAttribute("G_BRAN") != null ? session.getAttribute("G_BRAN").toString() : ""; 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
		
	//제이슨으로 공통 쿼리 처리
	JSONArray comList	= (CommonCode.getInstance()).getCodeList();
	JSONArray subList	= (CommonCode.getInstance()).getSubList();
	JSONArray certifiList	= (CommonCode.getInstance()).getCertifList();

	
	String code_pers = "";
	if( request.getAttribute("code_pers") != null) {
		code_pers = (request.getAttribute("code_pers")).toString();
	}

	String code_part = "";
	String code_great = "";
	String code_small = "";

	if( request.getAttribute("code_part") != null) {
		code_part = (request.getAttribute("code_part")).toString();
	}
	if( request.getAttribute("code_great") != null) {
		code_great = (request.getAttribute("code_great")).toString();
	}
	if( request.getAttribute("code_small") != null) {
		code_small = (request.getAttribute("code_small")).toString();
	}
	

	String isCkPersNo = "N";
	String isCkLicNo = "N";
	String cTcode = "";

	if( request.getAttribute("isCkPersNo") != null) {
		code_part = (request.getAttribute("isCkPersNo")).toString();
	}
	if( request.getAttribute("isCkLicNo") != null) {
		code_great = (request.getAttribute("isCkLicNo")).toString();
	}
	if( request.getAttribute("cTcode") != null) {
		code_small = (request.getAttribute("cTcode")).toString();
	}
	
	
	String isBasicOk="";//저장이 성공했나./
	String isComOk="";
	if( request.getAttribute("isBasicOk") != null) {
		isBasicOk = (request.getAttribute("isBasicOk")).toString();
	}
	if( request.getAttribute("isComOk") != null) {
		isComOk = (request.getAttribute("isComOk")).toString();
	}

%>

<script type="text/javascript">

//탭 처리
function tab_1(form) {
	/* var tab = document.getElementById(obj);
	if( obj.style.display = 'none') obj.style.display = ''; */
/* 	document.getElementById("name_1").className="overMenu";
	document.getElementById("name_2").className=""; */

	//탭 상단 선택 처리
	document.all.name_1.className = "overMenu";
	document.all.name_2.className = "";

	//기본정보 근무처정보 변경
	document.all.tab_2.style.display = "none";
	document.all.tab_1.style.display = "";
}
function tab_2(form) {
	//탭 상단 선택 처리
	document.all.name_2.className = "overMenu";
	document.all.name_1.className = "";

	//기본정보 근무처정보 변경
	document.all.tab_1.style.display = "none";
	document.all.tab_2.style.display = "";
}
//우편번호 검색
function postSearch(){
	//var url='memberInsert.do?method=postSearch&sel=N&keyword=';
 	//window.open(url, 'trust', 'scrollbar=no,resizable=no,toolbar=no,width=675,height=500,left=20,top=29,status=no');
	//window.open('memberBasic.do?method=postSearch&sel=3',"postnum","scrollbars=yes,menubar=no,resizable=no,toolbar=no,width=440,height=400,status=no");
	var pop = window.open("/post5.do?gubun=1","jusopop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}
//자택주소콜백
function jusoCallBack1(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){

	document.comp.pers_add.value = roadAddrPart1;
	document.comp.pers_add_detail.value = addrDetail;
	document.comp.code_post.value = zipNo;
		
}


//근무처 우편번호 검색
function compostSearch(){
	//var url='memberInsert.do?method=compostSearch&sel=N&keyword=';
	//window.open(url,"trust","scrollbar=no,resizable=no,toolbar=no,width=675,height=500,left=20,top=29,status=no");
	//window.open('memberBasic.do?method=postSearch&sel=4',"postnum","scrollbars=yes,menubar=no,resizable=no,toolbar=no,width=440,height=400,status=no");
	var pop = window.open("/post5.do?gubun=2","jusopop","width=570,height=420, scrollbars=yes, resizable=yes");
	
} 
//근무처 우편번호콜백
function jusoCallBack2(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){

	document.comp.company_add.value = roadAddrPart1;
	document.comp.company_add_detail.value = addrDetail;
	document.comp.company_code_post.value = zipNo;
}






//면허번호 중복 검사
function checkLicNo(form) {
	if(form.lic_no.value == '') {
		alert("라이센스 번호를 입력해 주세요.");
		return;
	}
	var url='memberInsert.do?method=sLicnoCnt&lic_no='+form.lic_no.value;
	window.open(url,"lic_no","scrollbar=no,resizable=no,toolbar=no,width=450,height=300,left=20,top=29,status=no");
}

//위탁업체 검색 페이지 
function fn_getTrustCompany(form) {
	var url = 'memberBasic.do?method=tSearch&sel=N&tName=" "';
	window.open(url,'trust','width=500, height=700, resizable=no, scrollbars=yes');
}

// JUMIN_DEL
// function checkJumin(form){

// 	var pers_no = form.pers_no.value;
// 	var arrayPersNo = pers_no.split("-");
// 	pers_no = arrayPersNo[0]+arrayPersNo[1];
	
// 	//주민 번호 유호성 검사
// 	var jmca = [2,3,4,5,6,7,8,9,2,3,4,5];
// 	var jmcs = pers_no;//form.pers_no.value;
// 	if (jmcs.length == 0)
// 	{
// 		alert('주민등록번호를 입력하세요.');
// 		form.pers_no.focus();
// 		return;
// 	}else if(jmcs.length == 13){	
// 		var jmc = 0;
// 		for(var i=0; i<jmca.length; i++) {
// 		  jmcsc = jmcs.substr(i,1);
// 		  jmc = jmc + jmca[i]*jmcsc;
// 		}
// 		var jmch = String(11-(jmc%11));
// 		jmch = jmch.substr(jmch.length-1,2);
// 		if(jmch != jmcs.substr(12,1)) {
// 		  alert('존재하지 않는 주민번호입니다');
// 			form.pers_no.value="";
// 			form.pers_no.focus();
// 		  return;
// 		}
// 		alert('유효한 주민등록번호입니다.'); 
// 	}else{
// 		alert('주민등록번호는 13자리여야 합니다.');
// 		form.pers_no.value="";
// 		form.pers_no.focus();
// 		return;
// 	}

// 	if( jmcs[6] == '1' || jmcs[6] == '3' ) {
// 		form.code_sex.male.checked	= true;
// 	}else	if( jmcs[6] == '2' || jmcs[6] == '4' ) {
// 		form.code_sex.female.checked = true;
// 	}
// 	form.isCkPersNo.value="Y";
// }

function onChange() {
	alert();
}

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

function fn_code_BigGreat(form, type) {
	
}

function fn_code_great(form){//근무처 대분류 : 0.근무처 대분류가 변경되면 그에따라 분과명도 똑같이 움직인다.
	
	//0.근무처 대분류가 변경되면 그에따라 분과명도 똑같이 움직인다.
	form.code_big.value = form.code_great.value;
	fn_code_big(form); //1, 2 번도 동작하게 아래 함수를 타게 한다.
	
}

function fn_code_big(form){//분과명 : 0.분과명이 변경되면 그에따라  근무처 대 분류도 똑같이 움직인다.
	//                                             1. 분과명(code_big)이 선택되면 근무처소분류(code_small)가 변화한다.
	//                                             2. 지부(code_bran)가 선택된 상태에 분과명(code_big)이 선택되었으면 그에 따라 분회명(code_sect)이 변화한다.
	//                                             3. 분과명이 선택되면 근무처 구분(code_part)이 변화한다.

	//0.분과명이 변경되면 그에따라  근무처 대 분류도 똑같이 움직인다.
	form.code_great.value = form.code_big.value;
	
	
	//분과명을 선택으로 바꾸면 분회명과, 근무처 구분, 근무처소분류 모두 선택으로 바꾸고 리턴한다.
	if(form.code_big.value ==""){
		form.code_small.options.length = 0;
		form.code_small.options[0] = new Option("선택","",false,false);
		form.code_small.disabled=true;
		
		form.code_part.options.length = 0;
		form.code_part.options[0] = new Option("선택","",false,false);
		form.code_part.disabled=true;

		form.code_sect.options.length = 0;
		form.code_sect.options[0] = new Option("선택","",false,false);
		form.code_sect.disabled=true;
		return;
	}

	form.code_small.disabled=false;
	form.code_small.options.length = 0;
	
	form.code_part.disabled=false;
	form.code_part.options.length = 0;
	
	var codeList	=eval(<%=comList%>);		//전체공통쿼리
	var cnt			=1;

	form.code_small.options[0]=new Option('선택' , '' , false, false);
	form.code_part.options[0]=new Option('선택' , '' , false, false);
	
	var nCodePart=0;
	for( var i=0; i<codeList.length; i++) {

		//1. 분과명(code_big)이 선택되면 근무처소분류(code_small)가 변화한다.
		if(codeList[i].groupcode=='005') {	//근무처 소분류
			if( form.code_big.value == codeList[i].tempcd2) {
				form.code_small.options[cnt]=new Option(codeList[i].detcodename , codeList[i].detcode , false, false);
				cnt++;
			}
		}
		
		//근무처대분류것을 먼져 가져와야 하는데. 근무처 대분류는 4번이다. 근무처 구분3보다 늦게 나오네 그럼 얼케 처리할까나.
		//4번이 변하면 3번이 변화되어야 한다. 3번걸 먼져리스트로 저장해놓고 4번게 들어오면 3번리스트를 조정해서 
		if(codeList[i].groupcode=='003' && nCodePart == 0) nCodePart = i;
		
		//1. '004' 근무처 구분이 들어오면 현재 선택된 근무처 구분에 대한 tempcd2 값을 가진다.
		//2. 내부for 문을 돌려 '003' 이 들어왔을때 003.detcode == 004.tempcd2 인값을 찾아서 집어 넣는다.
		if(codeList[i].groupcode=='004') {
			if( form.code_big.value == codeList[i].detcode ) {
				//내부에서 한번 더 돌아서 003의 값을 code_part에 넣는다.
				for( var ii=nCodePart; ii<codeList.length; ii++) {
					if(codeList[ii].groupcode=='003') {
						if( codeList[ii].detcode == codeList[i].tempcd2) {
							form.code_part.options[0]=new Option(codeList[ii].detcodename , codeList[ii].detcode , false, false);
							//alert("code_big.value = "+form.code_big.value+"  codeList["+i+"].detcode="+codeList[i].detcode+" codeList[i].tempcd2="+codeList[i].tempcd2);
							break;
						}
					}
				}
			}
				
			
		}
			
			
	} 
	if( cnt == 1) form.code_small.disabled=true;
	
	//2. 지부(code_bran)가 선택된 상태에 분과명(code_big)이 선택되었으면 그에 따라 분회명(code_sect)이 변화한다.
	if(form.code_bran.value != "" ) fn_code_bran(form);	//지부 변경되었을때 분회명 변경되는 함수.
	
}


$(document).ready(function(){
	
	//전체 권한 여부
	powerinit("101", eval(<%=userpowerm1%>) );


	var codeList	=eval(<%=comList%>);		//전체공통쿼리
	var subList		=eval(<%=subList%>);		//산하단체 리스트
	var certifiList	=eval(<%=certifiList%>);	//자격증 종류 리스트
	
	var form				= comp;				//폼
	
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

		//기본정보------------------
		//두번째줄
		if(groupcode=='006')	//회원종류
			form.code_member.options[cnt]=new Option(detcodename , detcode , false, false);
		
		//세번째줄
		if(groupcode=='007')	//지부
			form.code_bran.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='004')	//분과명
			form.code_big.options[cnt]=new Option(detcodename , detcode , false, false);
		
		//네번째
	 	if(groupcode=='004')	//근무처대분류
			form.code_great.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='025') {//메일업체
			zcnt=cnt-1;
			if(zcnt>-1)	form.code_email_comp.options[zcnt]=new Option(detcodename , detcode , false, false);
		}
		
		//아홉째
		if(groupcode=='028')	//학교
			form.code_school.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='027')	//최종학력
			form.code_scholar.options[cnt]=new Option(detcodename , detcode , false, false);

		//열번째
		if(groupcode=='019')	//학위
			form.code_univer.options[cnt]=new Option(detcodename , detcode , false, false);
		
		//열한째
		if(groupcode=='020')	//종교
			form.code_religion.options[cnt]=new Option(detcodename , detcode , false, false);
		
		//열둘째
		//산하단체는 따로 처리
		if(groupcode=='018')	//발송호칭
			form.code_call.options[cnt]=new Option(detcodename , detcode , false, false);
		
		//열셋째
		if(groupcode=='011')	//협회직급
			form.kda_level.options[cnt]=new Option(detcodename , detcode , false, false);
		/* if(groupcode=='021')	//회원상태
			form.pers_state.options[cnt]=new Option(detcodename , detcode , false, false); */
		
		
		//근무처정보-------------------
		//다섯째
	 	if(groupcode=='001')	//운영형태
			form.code_use.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='010')	//직렬
			form.job_line_code.options[cnt]=new Option(detcodename , detcode , false, false);
		if(groupcode=='011')	//직급
			form.job_level_code.options[cnt]=new Option(detcodename , detcode , false, false);
	
		cnt++;
		if(codeList.length == i+1 || codeList[i+1].groupcode != groupcode)	cnt=0;

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
	
	//기본저장이 완료 되었을때
	if(form.code_pers.value != "" ) {
		alert("기본정보 저장이 완료 되었습니다. \n 근무처 정보를 저장해주세요");
		//탭 상단 선택 처리
		document.all.name_1.className = "";
		document.all.name_2.className = "overMenu";
	
		//기본정보 근무처정보 변경
		document.all.tab_2.style.display = "";
		document.all.tab_1.style.display = "none";
	}
	
	if(form.isComOk.value == "Y") {
		alert("근무처 정보 저장이 완료 되었습니다. ");
		form.action = "memberInsert.do?method=memberInsert";
		form.submit();
	}
});




$(function() {	$( "#datepicker" ).datepicker();		});
$(function() {	$( "#datepicker1" ).datepicker();		});
$(function() {	$( "#datepicker2" ).datepicker();		});

//발급일 입사일 퇴사일  클릭했을때 초기화 
function fn_pers_in_dt(form)		{form.pers_in_dt.value	="";	}
function fn_pers_out_dt(form)	{form.pers_out_dt.value="";	}
function fn_lic_print_dt(form)	{form.lic_print_dt.value	="";	}





//기본정보 저장
function saveBasic(form) {

 	//필수조건 유효성 검통
	if(	form.pers_name.value == "" ) {
		alert("이름을 입력해 주세요");
		form.pers_name.focus();
		return;
	}
	if(	form.id.value == "" ) {
		alert("아이디를 입력해 주세요");
		form.id.focus();
		return;
	}
	if(form.idChkOk.value=='N'){
		alert("아이디 중복확인을 해주세요");		
		return;
	}
	if(	form.passwd.value == "" ) {
		alert("패스워드를 입력해 주세요");
		form.passwd.focus();
		return;
	}
	// JUMIN_DEL
// 	if( form.isCkPersNo.value != "Y" ) {
// 		alert("주민번호 유효성 검사를 해주십시요.");
// 		return;
// 	}
	if( form.pers_birth.value=="") {
		alert("생년월일을 입력해 주세요");
		form.pers_birth.focus();
		return;
	}
	if( form.pers_birth.value.length != 8) {
		alert("생년월일을 정확히 입력해 주세요\n\n예) 19980101");
		form.pers_birth.focus();
		return;
	}
	if( !comp.code_sex[0].checked && !comp.code_sex[1].checked ) {
		alert("성별을 선택해주십시요.");
		return;
	}
	if( form.code_member.value=="") {
		alert("회원종류를 선택해주십시요.");
		return;
	}
	
<%--
	<option value="07">학생회원
	
	<option value="28">신규특별회원
	<option value="29">기특별회원
	<option value="32">신규평생특별회원
	<option value="33">기평생특별회원
--%>
	var needlic = false;
	if(
		form.code_member.value!="07" &&
		form.code_member.value!="28" &&
		form.code_member.value!="29" &&
		form.code_member.value!="32" &&
		form.code_member.value!="33"
	){
		needlic = true;
	}

	if( (needlic || form.lic_no.value!="") && form.isCkLicNo.value != "Y" ) {
		alert("면허번호 유효성 검사를 해주십시요.");
		return;
	}
	if( form.code_post.value=="") {
		alert("우편번호를 입력해주십시요.");
		return;
	}
	if( form.pers_add.value=="") {
		alert("주소를 입력해주십시요.");
		return;
	}
	if( form.pers_tel.value=="") {
		alert("자택전화번호를 입력해주십시요.");
		form.pers_tel.focus();
		return;
	}
	if( form.email.value=="") {
		alert("이메일 입력해주십시요.");
		form.email.focus();
		return;
	}

	
	var param = "";

	param+="&register="						+"<%=g_id != null ? g_id : ""%>";			//등록자
	param+="&agree_dt="					+"";			//업체코드
	
	//첫째줄-----------------------------------------------
	param += "&pers_name="		+escape(encodeURIComponent(form.pers_name.value));	//이름
	param += "&id="					+form.id.value;				//아이디
	param +=  "&passwd="			+form.passwd.value;			//패스워드

	//둘째줄-----------------------------------------------

	// JUMIN_DEL
// 	var pers_no = form.pers_no.value;
// 	var arrayPersNo = pers_no.split("-");
// 	pers_no = arrayPersNo[0]+arrayPersNo[1];
// 	param+="&pers_no="			+pers_no;		//주민등록번호
	
	param+="&pers_birth="			+form.pers_birth.value;		//생년월일
	
	if (form.code_sex[0].checked) {
	    param += "&code_sex=1";
	}

	//성별(여)
	if (form.code_sex[1].checked) {
	    param += "&code_sex=2";
	}
	
	param+="&lic_no="			+(form.lic_no.value==""?"0":form.lic_no.value);			//면허번호
	param+="&lic_print_dt="	+form.lic_print_dt.value;	//발급일자(면허발급일자.)
	param+="&code_member="	+form.code_member.value;//회원종류

	//셋째줄-----------------------------------------------
	param+="&code_bran="		+form.code_bran.value;		//지부
	param+="&code_big="		+form.code_big.value;		//분과명
	param+="&code_sect="		+form.code_sect.value;		//분회명
	
	//넷째줄-----------------------------------------------
	param+="&code_part="		+form.code_part.value;		//근무처구분
	param+="&code_great="	+form.code_great.value;	//근무처대분류==분과
	param+="&code_small="		+form.code_small.value;	//근무처소분류

	//다섯줄-----------------------------------------------
	var code_post = '';
	if(form.code_post.value.substr(3,1)=='-')	code_post = form.code_post.value.substr(0,3)+form.code_post.value.substr(4,3);
	else													code_post = form.code_post.value;
	param+="&code_post="		+code_post;		//자택우편번호
	//if(form.code_add_gubun.aOld.checked	== true )	param+="&code_add_gubun=1";			//주소구분(구)
	//else if(form.code_add_gubun.aNew.checked== true )	param+="&code_add_gubun=2";			//주소구분(신)
	param+="&code_add_gubun=2"; //도로명주소 개편으로 인한 신주소값 고정_20141127

	//여섯줄-----------------------------------------------
	param+="&pers_add="		+escape(encodeURIComponent(form.pers_add.value));		//자택주소
	param+="&pers_add_detail="+escape(encodeURIComponent(form.pers_add_detail.value));	//자택주소(상세)

	//일곱줄-----------------------------------------------
	if(form.code_place[0].checked )	param+="&code_place=1";					//우편물 수신처=발송지구분(자택)
	else if(form.code_place[1].checked)	param+="&code_place=2";					//우편물 수신처=발송지구분(근무처)
	else if(form.code_place[2].checked)	param+="&code_place=3";					//우편물 수신처=발송지구분(발송중지)
	param+="&pers_tel="			+form.pers_tel.value;		//자택전화번호

	//여덟줄-----------------------------------------------
	param+="&pers_hp="			+form.pers_hp.value;	//휴대폰

	var email = form.email.value;
	var arrayEmail = email.split("@");
	if( email.length > 0){
		var emailAddr	= arrayEmail[0];
		var emailComp	= arrayEmail[1];
		var codeComp	= form.code_email_comp.value;
		var textComp	= form.code_email_comp.options[form.code_email_comp.options.selectedIndex].text;

		if(emailComp == null || emailComp.length == 0 || emailAddr.length == 0 || (codeComp!= '01' && emailComp != textComp)) {
			alert("이메일을 정확히 입력해 주십시요.");
			form.email.focus();
			return;
		}
		if( codeComp =='01' )	param+="&email="		+email;
		else							param+="&email="		+emailAddr;		//이메일
	}
	
	
	
	param+="&code_email_comp="	+form.code_email_comp.value;	//이메일업체
	var certifi_view = "";
	certifi_view+=form.certifi_view[0].checked ?"1":"0";
	certifi_view+=form.certifi_view[1].checked ?"1":"0";
	certifi_view+=form.certifi_view[2].checked ?"1":"0";
	certifi_view+=form.certifi_view[3].checked ?"1":"0";
	certifi_view+=form.certifi_view[4].checked ?"1":"0";
	param+="&certifi_view="		+certifi_view;	//자격증현황

	//아홉줄-----------------------------------------------
	param+="&code_school="	+form.code_school.value;	//학교
	param+="&code_scholar="	+form.code_scholar.value;	//최종학력

	//열째줄-----------------------------------------------
	param+="&code_univer="	+form.code_univer.value;	//학위
	var pers_career="";
	pers_career += form.yy.value.length == 2 ? form.yy.value : form.yy.value.length == 1 ? "0"+form.yy.value : "00";		//경력(년)
	pers_career += form.mm.value.length == 2 ? form.mm.value : form.mm.value.length == 1 ? "0"+form.mm.value : "00";//경력(월)
	param+="&pers_career="+pers_career;

	//열한줄-----------------------------------------------
	if(form.code_marry[0].checked)	param+="&code_marry=1";			//혼인여부(기혼)
	if(form.code_marry[1].checked)	param+="&code_marry=0";			//혼인여부(미혼)
	param+="&code_religion="	+form.code_religion.value;	//학위

	//열두줄-----------------------------------------------
	param+="&code_sub="		+form.code_sub.value;	//산야단체 및 분야회
	param+="&code_call="		+form.code_call.value;	//발송호칭
	
	//열세줄-----------------------------------------------
	param+="&recommender="	+escape(encodeURIComponent(form.recommender.value));	//추천인 성명
	param+="&recm_hp="		+form.recm_hp.value;		//추천인핸드폰
	param+="&recm_division="	+escape(encodeURIComponent(form.recm_division.value));	//추천인근무처
	
	//열네줄-----------------------------------------------
	param+="&kda_level="		+form.kda_level.value;		//협회직급
	param+="&pers_state="		+form.pers_state.value;	//회원상태

//	alert(param);

	form.action = "memberInsert.do?method=memberInsertList&isCase=basic"+param;
	form.submit();
	
}

//근무처정보 저장
function saveCom(form) {
	// 필수 파라미터 체크
	if(!form.code_pers || !form.code_pers.value || form.code_pers.value.trim() == ""){
		alert("기본정보 저장 후 근무처정보를 저장할 수 있습니다.");
		return;
	}
	
	var param = "";
	param+="&code_pers="					+(form.code_pers.value || "");			//회원번호
	param+="&comp_seq="					+"1";			//근무처순번
	param+="&register="						+"<%=g_id != null ? g_id : ""%>";			//등록자
	param+="&trust_code="					+(form.cTcode && form.cTcode.value ? form.cTcode.value : "");			//업체코드
	param+="&code_part="			+(form.com_code_part && form.com_code_part.value ? form.com_code_part.value : "");		//근무처구분
	param+="&code_great="		+(form.com_code_great && form.com_code_great.value ? form.com_code_great.value : "");		//근무처대분류
	param+="&code_small="			+(form.com_code_small && form.com_code_small.value ? form.com_code_small.value : "");		//근무처소분류
	
	//첫째줄-----------------------------------------------
	param+="&company_name="			+(form.company_name && form.company_name.value ? escape(encodeURIComponent(form.company_name.value)) : "");	//근무처명
	var company_code_post = '';
	if(form.company_code_post && form.company_code_post.value && form.company_code_post.value.length > 3 && form.company_code_post.value.substr(3,1)=='-') {
		var codePost = (form.code_post && form.code_post.value ? form.code_post.value : "");
		if(codePost.length > 7) {
			company_code_post = form.company_code_post.value.substr(0,3)+codePost.substr(4,3);
		} else {
			company_code_post = form.company_code_post.value;
		}
	} else {
		company_code_post = (form.company_code_post && form.company_code_post.value ? form.company_code_post.value : "");
	}
	param+="&code_post="					+company_code_post;			//근무처우편번호
	
	//둘째줄-----------------------------------------------
	param+="&company_add="				+(form.company_add && form.company_add.value ? escape(encodeURIComponent(form.company_add.value)) : "");	//근무처주소
	param+="&company_add_detail="	+(form.company_add_detail && form.company_add_detail.value ? escape(encodeURIComponent(form.company_add_detail.value)) : "");	//근무처주소(상세)
	param+="&company_tel="				+(form.company_tel && form.company_tel.value ? form.company_tel.value : "");		//근무처전화번호

	//셋째줄-----------------------------------------------
	param+="&company_fax="				+(form.company_fax && form.company_fax.value ? form.company_fax.value : "");		//근무처팩스번호
	param+="&pers_in_dt="					+(form.pers_in_dt && form.pers_in_dt.value ? form.pers_in_dt.value : "");			//입사일
	param+="&pers_out_dt="				+(form.pers_out_dt && form.pers_out_dt.value ? form.pers_out_dt.value : "");		//퇴사일

	//다섯줄-----------------------------------------------
	param+="&code_use="					+(form.code_use && form.code_use.value ? form.code_use.value : "");		//운영형태
	param+="&job_dept="					+(form.job_dept && form.job_dept.value ? escape(encodeURIComponent(form.job_dept.value)) : "");		//부서
	param+="&job_level_code="			+(form.job_level_code && form.job_level_code.value ? form.job_level_code.value : "");		//직렬
	param+="&job_line_code="					+(form.job_line_code && form.job_line_code.value ? form.job_line_code.value : "");		//직급
	param+="&lic_pay="						+(form.lic_pay && form.lic_pay.value ? form.lic_pay.value : "");		//영양사면허수당
	param+="&year_pay="					+(form.year_pay && form.year_pay.value ? form.year_pay.value : "");		//연봉

	//일곱줄-----------------------------------------------
	param+="&company_sick_bad="		+(form.company_sick_bad && form.company_sick_bad.value ? form.company_sick_bad.value : "");			//허가병상
	param+="&company_meal="			+(form.company_meal && form.company_meal.value ? form.company_meal.value : "");				//1식재료비
	param+="&company_count_mom="	+(form.company_count_mom && form.company_count_mom.value ? form.company_count_mom.value : "");		//1일급식인원-아침
	param+="&company_count_lunch="	+(form.company_count_lunch && form.company_count_lunch.value ? form.company_count_lunch.value : "");		//1일급식인원-점심
	param+="&company_count_dinner="	+(form.company_count_dinner && form.company_count_dinner.value ? form.company_count_dinner.value : "");	//1일급식인원-저녁
	param+="&company_count_midnig="+(form.company_count_midnig && form.company_count_midnig.value ? form.company_count_midnig.value : "");	//1일급식인원-야식

	//아홉줄-----------------------------------------------
	param+="&join_con="						+(form.join_con && form.join_con.value ? form.join_con.value : "");		//공동관리
	param+="&join_cook="					+(form.join_cook && form.join_cook.value ? form.join_cook.value : "");		//공동조리
	param+="&pers_multy="					+(form.pers_multy && form.pers_multy.value ? form.pers_multy.value : "");		//겸직여부
	param+="&primary_flag="				+(form.primary_flag && form.primary_flag.value ? form.primary_flag.value : "");		//우선근무처여부
	param+="&secu_no="					+(form.secu_no && form.secu_no.value ? form.secu_no.value : "");		//산재번호
	param+="&trust_name="				+(form.cTName && form.cTName.value ? escape(encodeURIComponent(form.cTName.value)) : "");		//소속위탁업체명  

	//열째줄-----------------------------------------------
	param+="&trust_tel="					+(form.cTtel && form.cTtel.value ? form.cTtel.value : "");		//위탁업체번호
	param+="&trust_addr="					+(form.cTaddr && form.cTaddr.value ? escape(encodeURIComponent(form.cTaddr.value)) : "");		//위탁업체주소
	
//	alert(param);
	form.action = "memberInsert.do?method=memberInsertList&isCase=com"+param;
	form.submit();
}

//이메일 셋팅
function fn_setEmailComp(form) {
	//alert(form.code_email_comp.value+"  "+form.code_email_comp.options.selectedIndex+"  "+form.code_email_comp.options[form.code_email_comp.options.selectedIndex].text);
	var codeComp	= form.code_email_comp.value;
	var textComp	= form.code_email_comp.options[form.code_email_comp.options.selectedIndex].text;
	
	if( codeComp != '01') {
		var email = form.email.value;
		var arrayEmail = email.split("@");
		email = arrayEmail[0];
		email += "@"+textComp;
		form.email.value=email;
		
	}else {//직접입력 = @뒤에거 자른다.
		var email = form.email.value;
		var arrayEmail = email.split("@");
		email = arrayEmail[0];
		form.email.value=email;
	}
}

function idChk(form){
	if(form.id.value==""){
		alert("아이디를 입력해 주십시오.");
		form.id.focus();
		return;
	}
	var url='memberInsert.do?method=idChk&id='+form.id.value;
	window.open(url,"idChk","scrollbar=no,resizable=no,toolbar=no,width=300,height=150,left=20,top=29,status=no");
}

</script>
</head>

<body>

<div id="contents"><!-- contents -->

	<!-- 상단 바로가기 바 -->
	<ul class="home">
		<li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
		<li><a href="memberForm.do">회원</a></li>					<li>&gt;</li>
		<li class="NPage">회원 등록</li>
	</ul>

	<form name="comp" method="post" action=""><!-- form : comp -->
				
		<div id="tabs"><!-- tabs -->
			<div id="ts_tabmenu"><!-- ts_tabmenu -->
				<ul>
					<li><a  id="name_1" class="overMenu" onclick="javascript:tab_1(comp);"><strong><span>기본정보</span></strong></a></li>
					<li><a  id="name_2" class="" onclick="javascript:tab_2(comp);"><strong><span>근무처정보</span></strong></a></li>
				</ul>
			</div><!-- ts_tabmenu -->

			<div id=tab_1  style="display:;" ><!-- tab_1 기본정보 탭 -->
			
				<div class="mTabmenu_01"><!-- mTabmenu_01 -->
					<table class="mytable_01" cellspacing="0" summary="회원검색"><!-- mytable_01 -->
						<caption>회원검색</caption>       
					
						
						<!-- 첫번째줄 -->
						<tr>
<!-- Persson_M_TBL(3) pers_name 이름 -->
							<td class="nobg">이름</td>
							<td class="nobg1">
								<input type="text" id="pers_name" name="pers_name"  onfocus="fn_IME(this,'KOR');" onkeyup=" javascript:return check_StrByte(this, 50);" value="" size="13" maxlength="30" />
							</td>
<!-- id_tbl(2) id 아이디 -->
							<td class="nobg2">아이디</td>
							<td class="nobg1"><input type="text" id="id" name="id"  onfocus="fn_IME(this,'ENG');" onkeyup="javascript:check_EngInt('comp','id'); return check_StrByte(this, 20);" size="13" value="" maxlength="20" />
							<input name="bnt_id" type="button" value="중복확인" onclick="javascript:idChk(comp);"/>
							</td>
<!-- id_tbl(3) passwd 패스워드 -->
							<td class="nobg2">패스워드</td>
							<td class="nobg1"><input type="text" id="passwd" name="passwd" onkeyup="javascript:return check_StrByte(this, 20);" size="13" value="" maxlength="20"/></td>
						</tr>
						
						<!-- 두번째줄 -->
						<tr>
						
						           
<!--  JUMIN_DEL -->
<!-- <!-- Persson_M_TBL(4) pers_no 주민등록번호 - - > -->
<!-- 							<td class="alt1">주민등록번호</td> -->
<!-- 							<td><input type="text" id="pers_no" name="pers_no" onkeyup="javascript:check_JuminNo('comp','pers_no');" size="13" value="" maxlength="14" /> -->
<!-- 								<a href="javascript:checkJumin(comp);"><img src="images/m_serch_icon01.gif" id="btnCheckPersNo" alt="유효성체크" /></a> -->
<!-- <!-- Persson_M_TBL(15) code_sex, "002" 성별- - > -->
<!-- 							<input type="radio" name="code_sex" id="male" value="1"   /><label for="1">남</label> -->
<!-- 							<input type="radio" name="code_sex" id="female" value="2" /><label for="2">녀</label> -->
<!-- 						</td> -->
						
							<td class="alt1">생년월일</td>
							<td><input type="text" id="pers_birth" name="pers_birth" onkeyup="javascript:check_Int('comp','pers_birth');" size="13" value="" maxlength="8" style="margin-bottom:5px;" />
							<input type="radio" name="code_sex" id="male" value="1"   /><label for="1">남</label>
							<input type="radio" name="code_sex" id="fe" value="2" /><label for="2">녀</label><br/>
							예) 19980101
							</td>
							
<!-- Persson_M_TBL(2) code_member, "006" 회원종류 -->
							<td class="alt">회원종류</td>
							<td><select name="code_member"><option value="">선택</option></select></td>
							
<!-- Persson_M_TBL(5) lic_no 면허번호 -->
						<td class="alt">면허번호</td>
						<td><input type="text" id="lic_no" name = "lic_no" onkeyup="javascript:check_Int('comp','lic_no');" size="4" maxlength="6" value=""/>
							<a href="javascript:checkLicNo(comp);"><img src="images/m_serch_icon01.gif" alt="찾기" /></a>
<!-- Persson_M_TBL(6) lic_print_dt 면허발급일자 -->
							&nbsp;발급일:<input type="text" name="lic_print_dt"  id="datepicker" class="input"  size="7" readonly onclick="fn_lic_print_dt(comp);" value=""/></td>
						
						</tr>
					
						<!-- 세번재줄 -->
						<tr>
<!-- Persson_M_TBL(18) code_bran, "034" 지부 (007이 지부이나 여기에서는 035 교육지부를 사용한다.-->
							<td class="alt1">지부</td>
							<td><select name="code_bran" onchange="javascript:fn_code_bran(comp);"><option value="">선택</option></select></td>
<!-- Persson_M_TBL(19) code_big, "004" 분과명 -->
							<td class="alt">분과명</td>
							<td><select name="code_big" onchange="javascript:fn_code_big(comp);"><option value="">선택</option></select></td>
<!-- Persson_M_TBL(20) code_sect, "009" 분회명 -->
							<td class="alt">분회명</td>
							<td><select name="code_sect" disabled='true'><option value="">선택</option></select></td>
						</tr>
						
						<!-- 네번째줄 -->
						<tr>
<!-- pers_company(10) code_part, "003" 근무처구분 -->
							<td class="alt1">근무처구분</td>
							<td><select name="code_part" disabled='true' ><option value="">선택</option></select></td>
<!-- pers_company(09) code_great, "004" 근무처대분류 == 분과 -->
							<td class="alt">근무처대분류</td>
							<td><select name="code_great" onchange="javascript:fn_code_great(comp);"><option value="">선택</option></select></td>
<!-- pers_company(11) code_small, "005" 근무처소분류 -->
							<td class="alt">근무처소분류</td>
							<td><select name="code_small" disabled='true'><option value="">선택</option></select></td>
						</tr>
						
						<!-- 다섯째줄 -->
						<tr>
<!-- Persson_M_TBL(8) code_post 자택우편번호 -->
							<td class="alt1">자택우편번호</td>
							<td colspan="3"><input type="text" name="code_post" id="code_post" onkeyup="javascript:check_PostNo('comp','code_post'); return check_StrByte(this, 20);" size="15" maxlength="7" value="" />
								<input name="bnt" type="button" value="우편번호" onclick="javascript:postSearch();"/>
<!-- Persson_M_TBL(7) code_add_gubun 주소구분(신,구) -->
								<!-- <input type="radio" name="code_add_gubun" id="aOld" value="0" checked=true/>구주소 //도로명주소 개펴으로 인한 라디오박스 삭제_20141127  -->
								<!-- <input type="radio" name="code_add_gubun" id="aNew" value="1" />신주소 //도로명주소 개펴으로 인한 라디오박스 삭제_20141127 -->
							</td>
							<td class="alt" colspan="2"></td>
						</tr>
						
						<!-- 여섯째줄 -->
						<tr>
<!-- Persson_M_TBL(9) pers_add 자택주소 -->
							<td class="alt1">자택주소</td>
							<td colspan="3">
								<input type="text" name="pers_add" id="pers_add" onkeyup="return check_StrByte(this, 200);" size="60" maxsize="200" /> -
<!-- Persson_M_TBL(10) pers_add_detail 자택주소(상세) --> 
								<input type="text" name="pers_add_detail" id="pers_add_detail" onkeyup="return check_StrByte(this, 200);" size="20" maxsize="200" />
							</td>
							<td class="alt" colspan='2'>자격증현황</td>
						</tr>
						
						<!-- 일곱째줄 -->
						<tr>
<!-- Persson_M_TBL(25) code_place, "030" 우편물 수신처 -->
							<td class="alt1">우편물&nbsp;수신처</td>
							<td>
								<input type="radio" name="code_place" id="aHome" value="1" checked=true/>자택
								<input type="radio" name="code_place" id="aOffice" value="2"  />근무처
								<input type="radio" name="code_place" id="aStop" value="3"  />발송중지
							</td>
<!-- Persson_M_TBL(11) pers_tel 자택전화번호 -->
							<td class="alt">자택전화번호</td>
							<td><input type="text" name="pers_tel"onkeyup="javascript:check_Int('comp','pers_tel');"size="15" maxlength="11" /></td>
<!-- 여덟째줄 세번째 퀄럼에 검색하여 값이 들어간다. 자격증현황 -->
							<td colspan="2" rowspan='2' >
								<input type="checkbox" name="certifi_view" value="영양교사" />영양교사&nbsp;&nbsp;
								<input type="checkbox" name="certifi_view" value="운동처방사" />운동처방사&nbsp;&nbsp;
								<input type="checkbox" name="certifi_view" value="조리사" />조리사<br/>
								<input type="checkbox" name="certifi_view" value="기타" />기타&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="checkbox" name="certifi_view" value="임상영양사 국가자격증" />임상영양사 국가자격증
							</td>
						</tr>
						
						<!-- 여덟째줄 -->
						<tr>
<!-- Persson_M_TBL(12) pers_hp 휴대폰 -->
							<td class="alt1">휴대폰</td>
							<td><input type="text" name="pers_hp"onkeyup="javascript:check_Int('comp','pers_hp');"size="15" maxlength="11"/></td>
<!-- Persson_M_TBL(13) email 이메일 -->
							<td class="alt">이메일</td>
							<td><input type="text" name="email"onkeyup="javascript:return check_StrByte(this, 30);" onfocus="fn_IME(this,'ENG');" size="15" maxlength="30"/>
<!-- Persson_M_TBL(14) code_email_comp, "025" 메일업체 -->
								<select name="code_email_comp"   onChange="javascript:fn_setEmailComp(comp)" style="width:80px;"><option value="">직접입력</option></select>
							</td>               
<!-- Persson_M_TBL(27) certifi_view, "certifi(1) codecertifi" 자격증현황 뭘까 -->
							<!-- <td colspan="2">
								
							</td> -->
						</tr>
			   
						<!-- 아홉째줄 -->
						<tr>
<!-- Persson_M_TBL(23) code_school, "028" 학교 -->
							<td class="alt1">학교</td>
							<td><select name="code_school" ><option value="">선택</option></select></td>
<!-- Persson_M_TBL(21) code_scholar, "027" 최종학력 -->
							<td class="alt">최종학력</td>
							<td><select name="code_scholar" ><option value="">선택</option></select></td>

							<td class="alt" colspan="2">
							<!-- 
								전문자격증&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								자격증번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								유효기간
								-->
						</tr>
						
						<!-- 열번째줄 -->
						<tr>
<!-- Persson_M_TBL(22) code_univer, "019" 학위 -->
							<td class="alt1">학위</td>
							<td><select name="code_univer" ><option value="">선택</option></select></td>
<!-- Persson_M_TBL(26) pers_career 경력 (0000네자리이므로 yy+mm 으로 처리)-->
							<td class="alt">경력</td>
							<td>
								<input type="text" name="yy" size="3" maxlength="2"  onKeyUp="javascript:check_Int('comp','yy');"  />년
								<input type="text" name="mm" size="3"  maxlength="2"  onKeyUp="javascript:check_Int('comp','mm');"  />월</td>
<!-- 전문자격증, 자격증번호, 유효기간 -->
							<td colspan="2"></td>
						</tr>
					
						<!-- 열한째줄 -->
						<tr>
<!-- Persson_M_TBL(22) code_marry, "017" 혼인여부 -->
							<td class="alt1">혼인여부</td>
							<td>
								<input type="radio" name="code_marry" id="a_m" value="1"  />기혼
								<input type="radio" name="code_marry" id="a_um" value="0" checked=true />미혼
							</td>
<!-- Persson_M_TBL(16) code_religion, "020" 종교 -->
							<td class="alt">종교</td>
							<td><select name="code_religion" ><option value="">선택</option></select></td>
						
							<td colspan="2"></td>
						</tr>
					
						<!-- 열둘째줄 -->
						<tr>
<!-- Persson_M_TBL(27) code_sub, "sub_tbl(1) code_sub" 산하단체 및 분야회 소속현황 -->
							<td class="alt1">산하단체&nbsp;및&nbsp;분야회<br/>소속현황</td>
							<td><select name="code_sub" ><option value="">선택</option></select></td>
<!-- Persson_M_TBL(24) code_call, "018" 발송호칭-->
							<td class="alt">발송호칭</td>
							<td><select name="code_call" ><option value="">선택</option></select></td>
	
							<td colspan="2"></td>
						</tr>
					
						<!-- 열셋째줄 -->
						<tr>
<!-- Persson_M_TBL(32) recommender 추천인 성명 -->
							<td class="alt1">추천인&nbsp;성명</td>
							<td><input type="text" name="recommender" onkeyup="javascript:return check_StrByte(this, 50);" onfocus="fn_IME(this,'KOR');"  size="15" maxlength="15" maxlength="50"/></td>
<!-- Persson_M_TBL(34) recm_hp 추천인 핸드폰 -->
							<td class="alt">추천인&nbsp;핸드폰</td>
							<td><input type="text" name="recm_hp"onkeyup="javascript:check_Int('comp','recm_hp');"  size="15" maxlength="11"/></td>
<!-- Persson_M_TBL(33) recm_division 추천인 근무처-->
							<td class="alt">추천인 근무처</td>
							<td><input type="text" name="recm_division" maxlength="20" onkeyup="javascript:return check_StrByte(this, 50);" onfocus="fn_IME(this,'HAN');"  size="15" maxlength="50"/></td>
						</tr>
					
						<!-- 열넷째줄 -->
						<tr>
<!-- ?????????????? 최종수정자 -->
							<td class="alt1">최종수정자</td>
							<td><%=g_id != null ? g_id : ""%></td>
<!-- Persson_M_TBL(31) kda_level, "011" 협회직급 -->
							<td class="alt">협회직급</td>
							<td><select name="kda_level" ><option value="">선택</option></select></select></td>
<!-- Persson_M_TBL(35) pers_state, "021" 회원상태 -->
							<td class="alt">회원상태</td>
							<td><select name="pers_state" ><option value="06">승인대기</option></select></td>
						</tr>
					</table><!-- mytable_01 -->
					<p class="btnSave"><a href="javascript:saveBasic(comp);"><img src="images/icon_save.gif" alt="저장" /></a></p>
				</div><!--mTabmenu_01 -->
			</div><!-- tab_1 -->


			
			<div id=tab_2  style="display:none;" ><!-- tab_2 업체정보 탭 -->
				<div class="mTabmenu_01" id="tab01" style="display:show">
					<table class="mytable_02" cellspacing="0" summary="근무처정보">
						<caption>근무처정보</caption>
						
						<!-- 첫번째줄 -->
						<tr>
<!-- pers_company(3) company_name 근무처명 -->
							<td class="nobg">근무처명</td>
							<td colspan="2" class="nobg1"><input type="text"  name="company_name" onKeyUp="check_StrByte(this, 100);" onFocus="fn_IME(this,'KOR');" size="35" maxlength="100" value=""/></td>
<!-- pers_company(4) code_post 근무처우편번호 -->
							<td class="nobg3">근무처우편번호</td>
							<td colspan="2" class="nobg1"><input type="text"  name="company_code_post" id="company_code_post" onKeyUp="javascript:check_PostNo('comp','company_code_post');" size="15" maxlength="7"  value="" />
								<input name="bnt" type="button" value="우편번호"  onclick="javascript:compostSearch();" />
							</td>
						</tr>
						
						<!-- 두번째줄 -->
						<tr>
<!-- pers_company(5) company_add 근무처주소 -->
							<td class="alt1">근무처주소</td>
							<td colspan="3">
								<input type="text" name="company_add" id="company_add" onKeyUp="check_StrByte(this, 200);" size="30" maxlength="200" value=""/>-
<!-- pers_company(6) company_add_detail 근무처주소(상세)-->
								<input type="text" name="company_add_detail" id="company_add_detail" onKeyUp="check_StrByte(this, 200);" size="40" maxlength="200" value=""/>
							</td>
<!-- pers_company(7) company_tel 근무처전화번호 -->
							<td class="alt">근무처전화번호</td>
							<td><input type="text"  name="company_tel" onKeyUp="javascript:check_Int('comp','company_tel');" size="15" maxlength="20" value=""/></td>
						</tr>
						
						<!-- 세번째줄 -->
						<tr>
<!-- pers_company(8) company_fax 근무처팩스번호 -->
							<td class="alt1">근무처팩스번호</td>
							<td><input type="text"  name="company_fax" onKeyUp="javascript:check_Int('comp','company_fax');" size="15" maxlength="20" value=""/></td>
<!-- pers_company(12) pers_in_dt 입사일 -->
							<td class="alt">입사일</td>
							<td><input type="text" name="pers_in_dt" readonly onclick="fn_pers_in_dt(comp);"  id="datepicker1" class="input" style=padding-top:3px  style=padding-bottom:3px size="15" value=""/></td>
<!-- pers_company(13) pers_out_dt 퇴사일 -->
							<td class="alt">퇴사일</td>
							<td><input type="text" name="pers_out_dt"readonly onclick="fn_pers_out_dt(comp);"  id="datepicker2" class="input" style=padding-top:3px  style=padding-bottom:3px size="15" value=""/></td>
						</tr>
						
						<!-- 네번째줄 -->
						<tr>
							<td class="alt1">운영형태</td>
							<td class="alt">부서</td>
							<td class="alt">직렬</td>
							<td class="alt">직급</td>
							<td class="alt">영양사면허수당</td>
							<td class="alt">연봉</td>
						</tr>
						
						<!-- 다섯째줄 -->
						<tr>
<!-- pers_company(14) code_use, "001" 운영형태 -->
							<td class="alt2"><select name="code_use" ><option value="">선택</option></select></td>
<!-- pers_company(15) job_dept 부서 -->
							<td><input type="text" id="job_dept" onKeyUp="check_StrByte(this, 30);" size="15" value=""/></td>
<!-- pers_company(16) job_line_code, "010" 직렬 -->
							<td><select name="job_line_code" ><option value="">선택</option></select></td>
<!-- pers_company(17) job_level_code, "011" 직급 -->
							<td><select name="job_level_code" ><option value="">선택</option></select></td>
<!-- pers_company(18) lic_pay 영양사면허수당 -->
							<td><input type="text"  name="lic_pay" onKeyUp="javascript:check_Int('comp','lic_pay');" size="15" maxlength="15" value=""/></td>
<!-- pers_company(19) year_pay 연봉 -->
							<td><input type="text"  name="year_pay" onKeyUp="javascript:check_Int('comp','year_pay');" size="15" maxlength="15" value=""/></td>
						</tr>
						
						<!-- 여섯째줄 -->
						<tr>
							<td class="alt1">허가병상</td>
							<td class="alt">1식재료비</td>
							<td class="alt">1일급식인원-아침</td>
							<td class="alt">1일급식인원-점심</td>
							<td class="alt">1일급식인원-저녁</td>
							<td class="alt">1일급식인원-야식</td>
						</tr>
						
						<!-- 일곱째줄 -->
						<tr>
<!-- pers_company(20) company_sick_bad 허가병상 -->
							<td class="alt2"><input type="text" name="company_sick_bad" onKeyUp="javascript:check_Int('comp','company_sick_badcompany_sick_bad');" size="15" maxlength="15" value=""/></td>
<!-- pers_company(21) company_meal 1식재료비 -->
							<td><input type="text" name="company_meal" onKeyUp="javascript:check_Int('comp','company_meal');" size="15" maxlength="15" value=""/></td>
<!-- pers_company(26) company_count_mom 1일급식인원-아침 -->
							<td><input type="text" name="company_count_mom" onKeyUp="javascript:check_Int('comp','company_count_mom');" size="15" maxlength="15" value=""/></td>
<!-- pers_company(27) company_count_lunch 1일급식인원-점심 -->
							<td><input type="text" name="company_count_lunch" onKeyUp="javascript:check_Int('comp','company_count_lunch');" size="15" maxlength="15" value=""/></td>
<!-- pers_company(28) company_count_dinner 1일급식인원-저녁 -->
							<td><input type="text" name="company_count_dinner" onKeyUp="javascript:check_Int('comp','company_count_dinner');" size="15" maxlength="15" value=""/></td>
<!-- pers_company(29) company_count_midnig 1일급식인원-야식 -->
							<td><input type="text" name="company_count_midnig" onKeyUp="javascript:check_Int('comp','company_count_midnig');" size="15" maxlength="15" value=""/></td>
						</tr>
						
						<!-- 여덟째줄 -->
						<tr>
							<td class="alt1">공동관리</td>
							<td class="alt">공동조리</td>
							<td class="alt">겸직여부</td>
							<td class="alt">우선근무처여부</td>
							<td class="alt">산재번호</td>
							<td class="alt">소속위탁업체</td>
						</tr>
						
						<!-- 아홉째줄 -->
						<tr>
<!-- pers_company(22) join_con 공동관리 -->
							<td class="alt2"><select name="join_con">
								<option value="">선택</option>
								<option value="1" >공동관리</option>
								<option value="0" >단독관리</option>
							</select></td>
<!-- pers_company(23) join_cook 공동조리 -->
							<td><select name="join_cook">
								<option value="">선택</option>
								<option value="1" >공동조리</option>
								<option value="0" >단독조리</option>
							</select></td>
<!-- pers_company(25) pers_multy 겸직여부 -->
							<td><select name="pers_multy">
								<option value="">선택</option>
								<option value="1" >겸직</option>
								<option value="0" >비겸직</option>
							</select></td>
<!-- pers_company(24) primary_flag 우선근무처여부 -->
							<td><select name="primary_flag">
								<option value="1" >예</option>
								<option value="0" >아니오</option>
							</select></td>
<!-- pers_company(35) secu_no 산재번호 -->
							<td><input type="text" name="secu_no" onKeyUp="javascript:check_Int('comp','secu_no');"  size="15" maxlength="15" value=""/></td>
<!-- pers_company(32) trust_name 소속위탁업체 -->
							<td>
								<input type="text" name="cTName" onKeyUp="check_StrByte(this, 60);" size="12" maxlength="60" value="" />&nbsp;
								<input name="bnt1" type="button" value="검색" onclick="javascript:fn_getTrustCompany(comp);"/>
							</td>
						</tr>
						
						<!-- 열번째줄 -->
						<tr>
<!-- pers_company(34) trust_tel 위탁업체전화번호 -->
							<td class="alt1">위탁업체전화번호</td>
							<td><input type="text" name="cTtel" onKeyUp="javascript:check_Int('comp','cTtel');" size="15" maxlength="11" value="" /></td>
<!-- pers_company(33) trust_addr 위탁업체주소 -->
							<td class="alt">위탁업체주소</td>
							<td colspan="3"><input type="text" name="cTaddr" onKeyUp="check_StrByte(this, 300);" size="77" maxlength="300"  value="" /></td>
						</tr>
						
					</table>
					<p class="btnSave"><a href="javascript:saveCom(comp)"><img src="images/icon_save.gif" alt="저장" /></a></p>
				</div><!--mTabmenu_01 End-->
			</div><!-- tab_2 -->
			
		</div><!-- M_contents End -->
	<input type='hidden' name='code_pers' value="<%=code_pers%>" style="width:50px;" />				<!-- 기본정보 저장시 생성( 근무처정보 저장때 사용) -->
	<input type='hidden' name='isCkPersNo' value="<%=isCkPersNo%>" style="width:50px;"/>									<!-- 주민등록 번호 체크여부 (기본정보 저장여부 확인 )-->
	<input type='hidden' name='isCkLicNo' value="<%=isCkLicNo%>" style="width:50px;"/>										<!-- 라이센스번호 체크여부(기본정보 저장여부 확인) -->
	<input type='hidden' name='com_code_part' value="<%=code_part%>" style="width:50px;"/>		<!-- 근무처구분 기본정보 저장시 생성( 근무처정보저장때 사용) -->
	<input type='hidden' name='com_code_great' value="<%=code_great%>" style="width:50px;"/>	<!-- 근무처대분류 기본정보 저장시 생성( 근무처정보저장때 사용) -->
	<input type='hidden' name='com_code_small' value="<%=code_small%>" style="width:50px;"/>		<!-- 근무처소분류 기본정보 저장시 생성( 근무처정보저장때 사용) -->
	<input type='hidden' name='cTcode' value="<%=cTcode%>" style="width:50px;"/>											<!-- 소속위탁업체코드 소속위탁업체 검색시 생성 (근무처정보 저장시 사용) -->
	<input type='hidden' name='isBasicOk' value="<%=isBasicOk%>" style="width:50px;"/>						<!-- 기본정보 저장완료 여부 -->
	<input type='hidden' name='isComOk' value="<%=isComOk%>" style="width:50px;"/>							<!-- 근무처 정보 저장완료 여부 -->
	<input type='hidden' name='idChkOk' value="N"/>
	</form><!-- form : comp -->
</body>
</html>