<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String logid="";
	if(session.getAttribute("G_NAME")!=null){
		logid=session.getAttribute("G_NAME").toString();
	}
	String errMsg="";
	if(request.getAttribute("errMsg")!=null){
		errMsg=request.getAttribute("errMsg").toString();
	}
	
	String msg ="";
	if(request.getAttribute("msg")!=null){
		msg=request.getAttribute("msg").toString();
	}
	String chk="";
	if(request.getAttribute("chk")!=null){
		chk=request.getAttribute("chk").toString();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" href="css/adm_common.css" />
<link rel="stylesheet" type="text/css" href="css/adm_layout.css" />
<script src="js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">

var msg="<%=msg%>";
var chk="<%=chk%>";


if(msg!=null && msg!=""){
	alert(msg);
	if(chk != "chk"){
		opener.goSearch(opener.document.sForm,0);
		self.close();	
	}
	
}

document.onreadystatechange=function(){
    if(document.readyState == "complete")initR();
};

function initR(){
	var logid="<%=logid%>";
	var errMsg="<%=errMsg%>";
	if(errMsg.length>5){
		alert(errMsg);
		return;
	}
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		location.href="login.do?method=login";
	}
	
}


	var firstId = '${result[0].cs_kitchen_code}';

	$(document).ready(function(){
		
		if(firstId != ''){
			doSubActive(firstId);
		}
		
		var id =$(':radio[name="radio2"]:checked').val();	
		$(".activeNo").hide();
		$("#active"+id).show();
		
		$("#pers_tel,#pers_hp,#pers_year,#lic_print_dt,#yyyy,#cs_con_education").keyup(function(event){
			$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );
		});
		$("#pers_tel,#pers_hp,#pers_year,#lic_print_dt,#yyyy,#cs_con_education").focusout(function(event){
			$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );
		});
		
	});
	
	function selectMail(v){
		$("#mail2").val(v);
	}
	
	function openJusoPopup(id) 
	{
		$("#jusoId").val(id);
		var pop = window.open("/post5.do?gubun=1","jusopop","width=570,height=420, scrollbars=yes, resizable=yes");
	}
	
	function jusoCallBack1( roadFullAddr
			 , roadAddrPart1
			 , addrDetail
			 , roadAddrPart2
			 , engAddr
			 , jibunAddr
			 , zipNo
			 , admCd
			 , rnMgtSn
			 , bdMgtSn)
	{
		var jusoId =  $("#jusoId").val();
		if(jusoId=='1'){
			$("#pers_add_detail").val(addrDetail + " " + roadAddrPart2);		
			$("#pers_add").val(roadAddrPart1);		
			$("#code_post").val(zipNo);
		}else if(jusoId=='2'){
			$("#cs_kitchen_add12").val(addrDetail + " " + roadAddrPart2);		
			$("#cs_kitchen_add11").val(roadAddrPart1);		
			$("#code_post1").val(zipNo);
		}else if(jusoId=='3'){
			$("#cs_kitchen_add22").val(addrDetail + " " + roadAddrPart2);		
			$("#cs_kitchen_add21").val(roadAddrPart1);		
			$("#code_post2").val(zipNo);
		}else if(jusoId=='4'){
			$("#cs_kitchen_add32").val(addrDetail + " " + roadAddrPart2);		
			$("#cs_kitchen_add31").val(roadAddrPart1);		
			$("#code_post3").val(zipNo);
		}else if(jusoId=='5'){
			$("#cs_kitchen_add42").val(addrDetail + " " + roadAddrPart2);		
			$("#cs_kitchen_add41").val(roadAddrPart1);		
			$("#code_post4").val(zipNo);
		}else if(jusoId=='6'){
			$("#cs_kitchen_add52").val(addrDetail + " " + roadAddrPart2);		
			$("#cs_kitchen_add51").val(roadAddrPart1);		
			$("#code_post5").val(zipNo);
		}else if(jusoId=='7'){
			$("#ncs_kitchen_add62").val(addrDetail + " " + roadAddrPart2);		
			$("#ncs_kitchen_add61").val(roadAddrPart1);		
			$("#code_post6").val(zipNo);
		}
		
	}
	
	function doActive(id){
		$(".activeNo").hide();
		$("#active"+id).show();
	}
	
	function doSubActive(id){
		$("#addrUl1 li").hide();
		if(id=='03'){
			$("#addrUl1 li").show();
		}else if(id=='05' || id=='06'){
			$("#addrUl1 li").eq(0).show();
			$("#addrUl1 li").eq(1).show();
		}else{
			$("#addrUl1 li").eq(0).show();
		}
	}
	
function doCheck(code){
	
		if($("#pers_name").val()=="" ){
			alert("성명을 입력해야 합니다.");
			$("#pers_name").focus();
			return false;
		}
		
		if($("#yyyy").val()=="" ){
			alert("년도를 입력해야 합니다.");
			$("#yyyy").focus();
			return false;
		}
		
		if($("#lic_no").val()=="" ){
			alert("면허번호을 입력해야 합니다.");
			$("#lic_no").focus();
			return false;
		}
		
 		if($("#lic_print_dt").val()=="" ){
			alert("면허발급일을 입력해야 합니다.");
			$("#lic_print_dt").focus();
			return false;
		} 
	
		if($("#mail1").val()=="" ){
			alert("메일을 입력해야 합니다.");
			$("#mail1").focus();
			return false;
		}
		if($("#mail2").val()==""){
			alert("메일을 입력해야 합니다.");
			$("#mail2").focus();
			return false;
		}
		
		var ckMail = $("#mail1").val() +"@"+ $("#mail2").val();
		var regex=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
		if(regex.test(ckMail) === false) {  
		    alert("잘못된 이메일 형식입니다.");  
		    return false;  
		}
		
		if($("#code_post").val()=="" || $("#pers_add").val()=="" || $("#pers_add_detail").val()==""){
			alert("주소를 입력해야 합니다.");
			return false;
		}
		
		if($("#pers_tel").val()=="" || $("#pers_tel").val().length<9){
			alert("일반전화 연락처를 입력해야 합니다.");
			$("#pers_tel").focus();
			return false;
		}
		
		if($("#pers_hp").val()=="" || $("#pers_hp").val().length<10){
			alert("휴대폰 연락처를 입력해야 합니다.");
			$("#pers_hp").focus();
			return false;
		}
		/* 2021-02-08 김수현 요청으로 생년, 성별 삭제
		if($("#pers_year").val()=="" || $("#pers_year").val().length<4){
			alert("생년을 입력해야 합니다.");
			$("#pers_year").focus();
			return false;
		}
		
		if($(':radio[name="radio1"]:checked').length < 1 ) {
			alert("성별을 꼭 선택해야 합니다.");
			return false;
		}
		 */
		/* if($("#bbs_file1").val()==""){
			if($("#bbs_file_url").val()==""){
				alert("면허증사본을 꼭 첨부해야 합니다.");
				return false;
			}
		} */
		
		if($(':radio[name="radio2"]:checked').length < 1 ) {
			alert("활동여부를 꼭 선택해야 합니다.");
			return false;
		}
		
		var act =$(':radio[name="radio2"]:checked').val();		
		if(act=='1'){
			if($(':radio[name="active1"]:checked').length < 1 ) {
				alert(" 집단급식소 근무 해당사항을 꼭 선택해야 합니다.");
				return false;
			}else{
				var div1 =$(':radio[name="active1"]:checked').val();
				if($("#cs_kitchen_name1").val()==""){
					alert("근무처명을 입력하셔야 합니다.");
					$("#cs_kitchen_name1").focus();
					return false;
				}
				if($("#code_post1").val()=="" || $("#ncs_kitchen_add11").val()=="" || $("#ncs_kitchen_add12").val()==""){
					alert("근무처 주소를 꼭 입력해야 합니다.");
					return false;
				}
				
			}
		}else if(act=='2'){
			if($(':radio[name="active2"]:checked').length < 1 ) {
				alert("비집단급식소 근무 해당사항을 꼭 선택해야 합니다.");
				return false;
			}else{
				var div2 =$(':radio[name="active2"]:checked').val();
				if(div2=='09'){
					if($("#ncs_kitchen_ret_code").val()==""){
						alert("기타사항 선택시 상세입력을 해야 합니다.");		
						$("#ncs_kitchen_ret_code").focus();
						return false;
					}
				}else{
					$("#ncs_kitchen_ret_code").val("");
				}
				if($("#ncs_kitchen_name1").val()==""){
					alert("비집단급식소 근무처명을 입력해야 합니다.");		
					$("#ncs_kitchen_name1").focus();
					return false;
				}
				if($("#code_post6").val()=="" || $("#ncs_kitchen_add61").val()=="" || $("#ncs_kitchen_add62").val()==""){
					alert("비집단급식소 근무처 주소를 꼭 입력해야 합니다.");
					return false;
				}
			}
		}else if(act=='3'){
			if($(':radio[name="active3"]:checked').length < 1 ) {
				alert("미활동 해당사항을 꼭 선택해야 합니다.");
				return false;
			}else{
				var div3 =$(':radio[name="active3"]:checked').val();
				if(div3=='5'){
					if($("#cs_kitchen_ret_code").val()==""){
						alert("기타사항 선택시 상세입력을 해야 합니다.");		
						$("#cs_kitchen_ret_code").focus();
						return false;
					}
				}else{
					$("#cs_kitchen_ret_code").val("");
				}
			}
		}
		
		if($("#ar_state").val()=="" ){
			alert("진행상태를 입력해야 합니다.");
			$("#ar_state").focus();
			return false;
		}
		/*
		if($("#cs_con_education").val()=="" ){
			alert("보수교육년도를 입력해야 합니다.");
			$("#cs_con_education").focus();
			return false;
		}
		*/
		if(code=='0'){
			if ( confirm("저장하시겠습니까?")) {
				return true;	
			}else{
				return false;
			}				
		}else if(code=='1'){
			if ( confirm("제출하시겠습니까?")) {
				return true;
			}
		}else if(code=='2'){
				if ( confirm("완료하시겠습니까?")) {
					return true;
				}
			}else{
				return false;
			}
				
		
	}
	
function init(){
	
	var actCode =$(':radio[name="radio2"]:checked').val();	
	if(actCode=='1'){
		$("input:radio[name='active2']").removeAttr("checked");
		$("input:radio[name='active3']").removeAttr("checked");
		$("#addrUl2 input").val("");
		$("#addrUl3 input").val("");
		$("#ncs_kitchen_ret_code").val("");
	}else if(actCode=='2'){
		$("input:radio[name='active1']").removeAttr("checked");
		$("input:radio[name='active3']").removeAttr("checked");
		$("#addrUl1 input").val("");
		$("#addrUl3 input").val("");
	}else if(actCode=='3'){
		$("input:radio[name='active1']").removeAttr("checked");
		$("input:radio[name='active2']").removeAttr("checked");
		$("#addrUl1 input").val("");
		$("#addrUl2 input").val("");
		$("#ncs_kitchen_ret_code").val("");
	}
	
}


function doWrite(code, pMode){
	
	if(doCheck(code)){
		
		init();
		
		//	이메일
		var mail = $("#mail1").val()+"@"+$("#mail2").val();
		var input1 = document.createElement("input");
		input1.type="hidden";
		input1.name="email";
		input1.value=mail;
		
		//	주소
		var pers_post= $("#code_post").val();
		var pers_add = $("#pers_add").val();
		var pers_add_detail = $("#pers_add_detail").val();
		var input2 = document.createElement("input");
		input2.type="hidden";
		input2.name="code_post";
		input2.value=pers_post;
		var input3 = document.createElement("input");
		input3.type="hidden";
		input3.name="pers_add";
		input3.value=pers_add;
		var input4 = document.createElement("input");
		input4.type="hidden";
		input4.name="pers_add_detail";
		input4.value=pers_add_detail;
		
		//	연락처
		var tel = $("#pers_tel").val();
		var hp = $("#pers_hp").val();
		var input5 = document.createElement("input");
		input5.type="hidden";
		input5.name="pers_tel";
		input5.value=tel;
		var input6 = document.createElement("input");
		input6.type="hidden";
		input6.name="pers_hp";
		input6.value=hp;
		
		// 2021-02-08 김수현 요청으로 생년, 성별 삭제
		//	생년
		//var yy = "";
		var yy = $("#pers_year").val();
		var input7 = document.createElement("input");
		input7.type="hidden";
		input7.name="pers_year";
		input7.value=yy;
		
		//	성별
		//var sex = "";
		var sex = $(':radio[name="radio1"]:checked').val();
		if(!sex){
			sex = "";
		}
		var input8 = document.createElement("input");
		input8.type="hidden";
		input8.name="code_sex";
		input8.value=sex;
		
		//	활동여부
		var part = $(':radio[name="radio2"]:checked').val();		
		var input9 = document.createElement("input");
		input9.type="hidden";
		input9.name="ar_code_part";
		input9.value=part;
		
		//	활동여부 서브
		var div1 =$(':radio[name="active1"]:checked').val();
		if($(':radio[name="active1"]:checked').length < 1 ) {
			div1 = '';
		}
		var input10 = document.createElement("input");
		input10.type="hidden";
		input10.name="cs_kitchen_code";
		input10.value=div1;
		var div2 =$(':radio[name="active2"]:checked').val();	
		if($(':radio[name="active2"]:checked').length < 1 ) {
			div2 = '';
		}
		var input11 = document.createElement("input");
		input11.type="hidden";
		input11.name="ncs_kitchen_code";
		input11.value=div2;
		var div3 =$(':radio[name="active3"]:checked').val();
		if($(':radio[name="active3"]:checked').length < 1 ) {
			div3 = '';
		}
		var input12 = document.createElement("input");
		input12.type="hidden";
		input12.name="cs_kitchen_act_code";
		input12.value=div3;
		
		//	집단급식소 근무처명
		var name1 = $("#cs_kitchen_name1").val();
		var input13 = document.createElement("input");
		input13.type="hidden";
		input13.name="cs_kitchen_name1";
		input13.value=name1;
		var name2 = $("#cs_kitchen_name2").val();
		var input14 = document.createElement("input");
		input14.type="hidden";
		input14.name="cs_kitchen_name2";
		input14.value=name2;
		var name3 = $("#cs_kitchen_name3").val();
		var input15 = document.createElement("input");
		input15.type="hidden";
		input15.name="cs_kitchen_name3";
		input15.value=name3;
		var name4 = $("#cs_kitchen_name4").val();
		var input16 = document.createElement("input");
		input16.type="hidden";
		input16.name="cs_kitchen_name4";
		input16.value=name4;
		var name5 = $("#cs_kitchen_name5").val();
		var input17 = document.createElement("input");
		input17.type="hidden";
		input17.name="cs_kitchen_name5";
		input17.value=name5;
		
		//	집단급식소 우편번호
		var post1 = $("#code_post1").val();
		var input18 = document.createElement("input");
		input18.type="hidden";
		input18.name="code_post1";
		input18.value=post1;
		var post2 = $("#code_post2").val();
		var input19 = document.createElement("input");
		input19.type="hidden";
		input19.name="code_post2";
		input19.value=post2;
		var post3 = $("#code_post3").val();
		var input20 = document.createElement("input");
		input20.type="hidden";
		input20.name="code_post3";
		input20.value=post3;
		var post4 = $("#code_post4").val();
		var input21 = document.createElement("input");
		input21.type="hidden";
		input21.name="code_post4";
		input21.value=post4;
		var post5 = $("#code_post5").val();
		var input22 = document.createElement("input");
		input22.type="hidden";
		input22.name="code_post5";
		input22.value=post5;
		
		//	집단급식소 주소
		var addr1_1= $("#cs_kitchen_add11").val();
		var input23 = document.createElement("input");
		input23.type="hidden";
		input23.name="cs_kitchen_add11";
		input23.value=addr1_1;
		var addr1_2= $("#cs_kitchen_add12").val();
		var input24 = document.createElement("input");
		input24.type="hidden";
		input24.name="cs_kitchen_add12";
		input24.value=addr1_2;
		var addr2_1= $("#cs_kitchen_add21").val();
		var input25 = document.createElement("input");
		input25.type="hidden";
		input25.name="cs_kitchen_add21";
		input25.value=addr2_1;
		var addr2_2= $("#cs_kitchen_add22").val();
		var input26 = document.createElement("input");
		input26.type="hidden";
		input26.name="cs_kitchen_add22";
		input26.value=addr2_2;
		var addr3_1= $("#cs_kitchen_add31").val();
		var input27 = document.createElement("input");
		input27.type="hidden";
		input27.name="cs_kitchen_add31";
		input27.value=addr3_1;
		var ad7r3_2= $("#cs_kitchen_add32").val();
		var input28 = document.createElement("input");
		input28.type="hidden";
		input28.name="cs_kitchen_add32";
		input28.value=ad7r3_2;
		var addr4_1= $("#cs_kitchen_add41").val();
		var input29 = document.createElement("input");
		input29.type="hidden";
		input29.name="cs_kitchen_add41";
		input29.value=addr4_1;
		var addr4_2= $("#cs_kitchen_add42").val();
		var input30 = document.createElement("input");
		input30.type="hidden";
		input30.name="cs_kitchen_add42";
		input30.value=addr4_2;
		var addr5_1= $("#cs_kitchen_add51").val();
		var input31 = document.createElement("input");
		input31.type="hidden";
		input31.name="cs_kitchen_add51";
		input31.value=addr5_1;
		var addr5_2= $("#cs_kitchen_add52").val();
		var input32 = document.createElement("input");
		input32.type="hidden";
		input32.name="cs_kitchen_add52";
		input32.value=addr5_2;
		
		//	비집단급식소 근무처명
		var name6 = $("#ncs_kitchen_name1").val();
		var input33 = document.createElement("input");
		input33.type="hidden";
		input33.name="ncs_kitchen_name1";
		input33.value=name6;
		
		//	비집단급식소 우편번호
		var post6 = $("#code_post6").val();
		var input34 = document.createElement("input");
		input34.type="hidden";
		input34.name="code_post6";
		input34.value=post6;
		
		//	비집단급식소 주소
		var addr6_1 = $("#ncs_kitchen_add61").val();
		var input35 = document.createElement("input");
		input35.type="hidden";
		input35.name="ncs_kitchen_add61";
		input35.value=addr6_1;
		var addr6_2 = $("#ncs_kitchen_add62").val();
		var input36 = document.createElement("input");
		input36.type="hidden";
		input36.name="ncs_kitchen_add62";
		input36.value=addr6_2;
		
		//	미활동 기타
		var ret_code = $("#cs_kitchen_ret_code").val();
		var input37 = document.createElement("input");
		input37.type="hidden";
		input37.name="cs_kitchen_ret_code";
		input37.value=ret_code;
		
		var input38 = document.createElement("input");
		input38.type="hidden";
		input38.name="ar_finish_yn";
		input38.value=code;
		
		var pers_name = $("#pers_name").val();
		var input39 = document.createElement("input");
		input39.type="hidden";
		input39.name="pers_name";
		input39.value=pers_name;
		
		var lic_no = $("#lic_no").val();
		var input40 = document.createElement("input");
		input40.type="hidden";
		input40.name="lic_no";
		input40.value=lic_no;
		
		var lic_print_dt = $("#lic_print_dt").val();
		var input41 = document.createElement("input");
		input41.type="hidden";
		input41.name="lic_print_dt";
		input41.value=lic_print_dt;
		
		var input42 = document.createElement("input");
		input42.type="hidden";
		input42.name="mode";
		input42.value=$("#mode").val();
		var input43 = document.createElement("input");
		input43.type="hidden";
		input43.name="yyyy";
		input43.value=$("#yyyy").val();
		var input44 = document.createElement("input");
		input44.type="hidden";
		input44.name="cs_con_education";
		input44.value=$("#cs_con_education").val();
		
		var ar_marks_point;
		if($("#ar_state").val()=='0'){
			ar_marks_point=6;
		}else{
			ar_marks_point=0;
		}
		
		var input45 = document.createElement("input");
		input45.type="hidden";
		input45.name="ar_marks_point";
		input45.value=ar_marks_point;
		
		var input46 = document.createElement("input");
		input46.type="hidden";
		input46.name="ar_state";
		input46.value=$("#ar_state").val();
		
		var ncs_ret_code = $("#ncs_kitchen_ret_code").val();
		var input47 = document.createElement("input");
		input47.type="hidden";
		input47.name="ncs_kitchen_ret_code";
		input47.value=ncs_ret_code;
		
		var file_url = $("#bbs_file_url").val();
		var input48 = document.createElement("input");
		input48.type="hidden";
		input48.name="bbs_file_url";
		input48.value=file_url;
		
		var file_yn = "Y";
		if($("#bbs_file1").val() == null || $("#bbs_file1").val() == ""){
			file_yn = "N";
		}

		var input49 = document.createElement("input");
		input49.type="hidden";
		input49.name="file_yn";
		input49.value=file_yn;
		
		var input50 = document.createElement("input");
		input50.type="hidden";
		input50.name="code_seq";
		input50.value='${result[0].code_seq}';
		
		var input51 = document.createElement("input");
		input51.type="hidden";
		input51.name="ar_regi_date";
		input51.value='${result[0].ar_regi_date}';
		
		if(pMode == "I") {
			//input38.value=code; //ar_finish_yn
			input45.value=null; //ar_marks_point
			input46.value=null; //ar_state
		}
		
		var ar_add_file_name = $("#ar_add_file_name").val();
		var input52 = document.createElement("input");
		input52.type="hidden";
		input52.name="ar_add_file_name";
		input52.value=ar_add_file_name;
		
		var ar_add_file_url = $("#ar_add_file_url").val();
		var input53 = document.createElement("input");
		input53.type="hidden";
		input53.name="ar_add_file_url";
		input53.value=ar_add_file_url;
		
		var ar_add_file_type = $("#ar_add_file_type").val();
		var input54 = document.createElement("input");
		input54.type="hidden";
		input54.name="ar_add_file_type";
		input54.value=ar_add_file_type;
		
		var ar_add_file_size = $("#ar_add_file_size").val();
		var input55 = document.createElement("input");
		input55.type="hidden";
		input55.name="ar_add_file_size";
		input55.value=ar_add_file_size;
		
		
		var f = document.forms["write"];	
		f.insertBefore(input1,null);f.insertBefore(input2,null);f.insertBefore(input3,null);f.insertBefore(input4,null);f.insertBefore(input5,null);
		f.insertBefore(input6,null);f.insertBefore(input7,null);f.insertBefore(input8,null);f.insertBefore(input9,null);f.insertBefore(input10,null);
		f.insertBefore(input11,null);f.insertBefore(input12,null);f.insertBefore(input13,null);f.insertBefore(input14,null);f.insertBefore(input15,null);
		f.insertBefore(input16,null);f.insertBefore(input17,null);f.insertBefore(input18,null);f.insertBefore(input19,null);f.insertBefore(input20,null);
		f.insertBefore(input21,null);f.insertBefore(input22,null);f.insertBefore(input23,null);f.insertBefore(input24,null);f.insertBefore(input25,null);
		f.insertBefore(input26,null);f.insertBefore(input27,null);f.insertBefore(input28,null);f.insertBefore(input29,null);f.insertBefore(input30,null);
		f.insertBefore(input31,null);f.insertBefore(input32,null);f.insertBefore(input33,null);f.insertBefore(input34,null);f.insertBefore(input35,null);
		f.insertBefore(input36,null);f.insertBefore(input37,null);f.insertBefore(input38,null);f.insertBefore(input39,null);f.insertBefore(input40,null);
		f.insertBefore(input41,null);f.insertBefore(input42,null);f.insertBefore(input43,null);f.insertBefore(input44,null);f.insertBefore(input45,null);
		f.insertBefore(input46,null);f.insertBefore(input47,null);f.insertBefore(input48,null);f.insertBefore(input48,null);f.insertBefore(input49,null);
		f.insertBefore(input50,null);f.insertBefore(input51,null);
		
		if($("#ar_add_file_size").val() != null){
			f.insertBefore(input52,null);f.insertBefore(input53,null);f.insertBefore(input54,null);f.insertBefore(input55,null);			
		}
		
		f.method = "POST";
		f.action ="actul.do?method=statusUptProc";
		f.submit();
		
	}
	
	
}
	
	
	/* function f_print(printArea){
		win = window.open("","printpop","scrollbars=yes,resizable=no,toolbar=no,width=780,height=900,left=20,top=29,status=no"); 
		self.focus(); 
		win.document.open();
		win.document.write('<html><head><title></title>');
		win.document.write('<link rel="stylesheet" type="text/css" href="css/adm_common.css" />');
		win.document.write('<link rel="stylesheet" type="text/css" href="css/adm_layout.css" />');
		win.document.write('</haed><body>');
		win.document.write(printArea);
 		win.document.write('</body></html>');
		win.document.close();
		win.print();
		win.close();
	} */
	
	function f_print(){
		
		if($("#pers_name").val()==""){
			alert("성명을 입력하세요.");
			return;
		}
		if($("#yyyy").val()==""){
			alert("년도를 입력하세요.");
			return;
		}
		if($("#lic_no").val()==""){
			alert("면허번호를 입력하세요.");
			return;
		}
		
		var prm ="";
		
		
		prm = "&print_pers_name="+escape(encodeURIComponent($("#pers_name").val()))+"&print_lic_no="+$("#lic_no").val();
		prm += "&print_yyyy="+$("#yyyy").val()+"&print_cs_con_education="+$("#cs_con_education").val();
		
		url="actul.do?method=statusPrint&mode=U"+prm;
		window.open(url,"print","scrollbars=yes,resizable=no,toolbar=no,width=780,height=900,left=20,top=29,status=no");
	}
	
	function actulCheck(){
		
		var pers_name = $("#pers_name").val();
		var input39 = document.createElement("input");
		input39.type="hidden";
		input39.name="pers_name";
		input39.value=pers_name;
		
		var lic_no = $("#lic_no").val();
		var input40 = document.createElement("input");
		input40.type="hidden";
		input40.name="lic_no";
		input40.value=lic_no;
		
		var lic_print_dt = $("#lic_print_dt").val();
		var input41 = document.createElement("input");
		input41.type="hidden";
		input41.name="lic_print_dt";
		input41.value=lic_print_dt;
		
		
		var f = document.forms["write"];	
		f.insertBefore(input39,null);f.insertBefore(input40,null);f.insertBefore(input41,null);
		
		f.method = "POST";
		f.action ="actul.do?method=actulCheck";
		f.submit();
			
		
	}
</script>
</head>
<body> 
<form name="write" enctype="multipart/form-data" method="post">
<div class="p15 text">
	<div class="area">
		<h5 class="title bold s1">영양사의 실태 등 신고서</h5>
		<div class="box t1 r5 mt10 p10" id="div_print">
			<h6 class="title s1 i_b_t3">기본 인적사항 <span class="ssmall">( <span class="i_b_t5"> 표시는 필수항목입니다.</span> )</span></h6>
			<div class="form mt10 line bt bcm" id="aaa">
				<div class="f_wrap line bb p8">
					<div class="f_field div2">
						<label for="pers_name" class="ff_title"><strong>성명</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="text" class="input t1 w200" id="pers_name" value="${person[0].pers_name }" <c:if test="${mode=='U' }">readonly</c:if> />
							</div>
						</div>
					</div>
					<div class="f_field div2">
						<label for="lic_print_dt" class="ff_title"><strong>등록년도</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="hidden" class="input t1 fl w200" id="yyyy" value="${yyyy }"  maxlength="4" <c:if test="${mode=='U' }">readonly</c:if> />
								${yyyy }
							</div>
						</div>
					</div>
				</div>
				<div class="f_wrap line bb p8">
					<div class="f_field div2">
						<label for="lic_no" class="ff_title"><strong>면허번호</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="text" class="input t1 fl w200" id="lic_no" value="${person[0].lic_no }" maxlength="6" <c:if test="${mode=='U' }">readonly</c:if>/>
								<c:if test="${mode=='I'}"><a href="javascript:actulCheck();" class="btn form t1 fl ml5">확인</a></c:if>
							</div>
						</div>
					</div>
					<div class="f_field div2">
						<label for="lic_print_dt" class="ff_title"><strong>면허발급일</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="text" class="input t1 fl w200" id="lic_print_dt" value="${person[0].lic_print_dt }"  maxlength="8" readonly/>
								<p class="fl text cp s1 ml10">예) 20150101</p>
							</div>
						</div>
					</div>
				</div>
				<c:set var="mail" value="${fn:split(person[0].email,'@') }"/>
				<div class="f_wrap line bb p8">
					<div class="f_field div1">
						<label for="mail1" class="ff_title i_b"><strong>이메일</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="text" class="input t1 fl w150" id="mail1" value="${mail[0] }"/>
								<label for="mail2" class="fl text ml10 mr10">@</label>
								<input type="text" class="input t1 fl w150" id="mail2" value="${mail[1] }"/>
								<label for="inputid6" class="ti">이메일 입력</label>
								<select class="select t2 fl w150 ml5" id="inputid6" onchange="selectMail(this.value);">
									<option value="" >직접입력</option>
									<c:forEach items="${getEmail}" var="email">
										<option value="${email.detcodename}"  <c:if test="${email.detcodename==mail[1]}"> selected="selected"</c:if> >${email.detcodename}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="f_wrap line bb p8">
					<div class="f_field div1">
						<label for="code_post" class="ff_title i_b"><strong>주소</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="text" class="input t1 fl w200" id="code_post" readonly="readonly" value="${person[0].code_post }"/>
								<a href="javascript:openJusoPopup('1');" class="btn form t1 fl ml5">주소검색</a>
								<p class="fl text cp">주소검색(우편번호) :  - 없이 한 칸으로 만들어주세요. </p>
							</div>
							<div class="area mt5">
								<label for="pers_add" class="ti">기본주소</label>
								<input type="text" class="input t1 fl w200" id="pers_add" value="${person[0].pers_add }"  readonly="readonly" />
								<label for="pers_add_detail" class="ti">상세주소</label>
								<input type="text" class="input t1" id="pers_add_detail" value="${person[0].pers_add_detail }"/>
							</div>
						</div>
					</div>
				</div>
				<div class="f_wrap line bb p8">
					<div class="f_field div1">
						<div class="ff_title i_b"><strong>연락처</strong></div>
						<div class="ff_wrap ml130">
							<ul>
								<li>
									<div class="area">
										<label for="pers_tel" class="fl w100 text bold cm">일반전화</label>
										<input type="text" class="input t1 fl w150" id="pers_tel" value="${person[0].pers_tel }" maxlength="11" />
										<p class="fl text cp s1 ml10">예) 021234567</p>
									</div>
								</li>
								<li class="mt5">
									<div class="area">
										<label for="pers_hp" class="fl w100 text bold cm">휴대폰</label>
										<input type="text" class="input t1 fl w150" id="pers_hp" value="${person[0].pers_hp }" maxlength="11" />
										<p class="fl text cp s1 ml10">예) 01012345678</p>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				
<!-- 2021-02-08 김수현 요청으로 생년, 성별 삭제 -->
				<div class="f_wrap line bb p8">
					<div class="f_field div2">
						<label for="pers_year" class="ff_title i_b"><strong>생년</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="text" class="input t1 fl w100" id="pers_year"  maxlength="4" value="${person[0].pers_year }"/>
								<p class="fl text cp s1 ml10">예) 2015</p>
							</div>
						</div>
					</div>
					<div class="f_field div2">
						<div class="ff_title i_b"><strong>성별</strong></div>
						<div class="ff_wrap ml130 text">
							<div class="area">
								<input type="radio" class="mr5" name="radio1" id="radio1_1" value="1"  <c:if test="${person[0].code_sex == '1'}"><c:out value="checked"/></c:if>  />
								<label for="radio1_1" class="mr20">남자</label>
								<input type="radio" class="mr5" name="radio1" id="radio1_2" value="0"  <c:if test="${person[0].code_sex == '0'}"><c:out value="checked"/></c:if>  />
								<label for="radio1_2" class="mr20">여자</label>
							</div>
						</div>
					</div>
				</div>

				<!-- <div class="f_wrap line bb p8">
					<div class="f_field div1">
						<label for="inputid16" class="ff_title i_b"><strong>면허증사본 첨부</strong></label>
						<form name="write" enctype="multipart/form-data" method="post">
							<input type="hidden" name="step" value="step6">
						<div class="ff_wrap ml130">
							<div class="area">
								<input type="file" class="file t1"  id="bbs_file1" name="bbs_file1" />
							</div>
							<div class="area text mt5">
								파일명 ( <input type="checkbox" name="filecheck" id="filecheck" /> <label for="filecheck">삭제</label> )
								<input type="hidden" id="bbs_file_url" value="" />
								<input type="hidden" id="file_yn" value="" />
							</div>
						</div>
						</form>
					</div>
				</div> -->
			</div>
			<h6 class="title s1 i_b_t3 mt40">취업상황 <span class="ssmall">( <span class="i_b_t5"> 표시는 필수항목입니다.</span> )</span></h6>
			<div class="form mt10 line bt bcm">
				<div class="f_wrap line bb p8">
					<div class="f_field div1">
						<div class="ff_title i_b"><strong>활동여부</strong></div>
						<div class="ff_wrap ml130 text">
							<div class="area">
								<input type="radio" class="mr5" name="radio2" id="radio2_1" onclick="doActive(this.value);" value="1"  <c:if test="${result[0].ar_code_part == '1'}"><c:out value="checked"/></c:if>   />
								<label for="radio2_1" class="mr20">집단급식소 근무</label>
								<input type="radio" class="mr5" name="radio2" id="radio2_2" onclick="doActive(this.value);" value="2"  <c:if test="${result[0].ar_code_part == '2'}"><c:out value="checked"/></c:if>  />
								<label for="radio2_2" class="mr20">비집단급식소근무</label>
								<input type="radio" class="mr5" name="radio2" id="radio2_3" onclick="doActive(this.value);" value="3"  <c:if test="${result[0].ar_code_part == '3'}"><c:out value="checked"/></c:if>  />
								<label for="radio2_3" class="mr20">미활동(미취업)</label>
							</div>
						</div>
					</div>
				</div>
				<div class="f_wrap line bb p8 activeNo" id="active1" style="display:none;">
					<div class="f_field div1">
						<div class="ff_title i_b"><strong>집단급식소근무자</strong></div>
						<div class="ff_wrap ml130 text">
							<div class="area">
								<ul>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_1" value="01"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '01'}"><c:out value="checked"/></c:if> /><label for="radio3_1">산업체</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_2" value="02"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '02'}"><c:out value="checked"/></c:if> /><label for="radio3_2">병원</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_3" value="03"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '03'}"><c:out value="checked"/></c:if> /><label for="radio3_3">학교</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_4" value="04"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '04'}"><c:out value="checked"/></c:if> /><label for="radio3_4">사회복지시설</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_5" value="05"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '05'}"><c:out value="checked"/></c:if> /><label for="radio3_5">어린이집</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_6" value="06"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '06'}"><c:out value="checked"/></c:if> /><label for="radio3_6">유치원</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_7" value="07"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '07'}"><c:out value="checked"/></c:if> /><label for="radio3_7">교정</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_8" value="08"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '08'}"><c:out value="checked"/></c:if> /><label for="radio3_8">군대</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_9" value="09"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '09'}"><c:out value="checked"/></c:if> /><label for="radio3_9">경찰서</label></li>
									<li class="fl w170"><input type="radio" class="mr5" name="active1" id="radio3_11" value="11"  onclick="doSubActive(this.value);"  <c:if test="${result[0].cs_kitchen_code == '11'}"><c:out value="checked"/></c:if> /><label for="radio3_11">소방서</label></li>
								</ul>
								<p class="area text cp">※ 근무 집단급식명 / 주소(공동관리의 경우 모두기재)</p>
							</div>
						</div>
						<div class="ff_wrap ml130">
							<ul id="addrUl1">
								<li>
									<div class="area">
										<label for="cs_kitchen_name1" class="fl w100 text bold cm">근무처명</label>
										<input type="text" class="input t1 fl w200" id="cs_kitchen_name1" value="${result[0].cs_kitchen_name1 }"/>
									</div>
									<div class="area mt5">
										<label for="code_post1" class="fl w100 text bold cm">주소</label>
										<div class="ml100">
											<div class="area">
												<input type="text" class="input t1 fl w200" id="code_post1"  value="${result[0].code_post1 }"/>
												<a href="javascript:openJusoPopup('2');" class="btn form t1 fl ml5">주소검색</a>
											</div>
											<div class="area mt5">
												<label for="cs_kitchen_add11" class="ti">기본주소</label>
												<input type="text" class="input t1 fl w150" id="cs_kitchen_add11"  value="${result[0].cs_kitchen_add11 }"/>
												<label for="cs_kitchen_add12" class="ti">상세주소</label>
												<input type="text" class="input t1 fl w200 ml5" id="cs_kitchen_add12" value="${result[0].cs_kitchen_add12 }" />
											</div>
										</div>
									</div>
								</li>
								<li class="mt5 pt5 line bt">
									<div class="area">
										<label for="cs_kitchen_name2" class="fl w100 text bold cm">근무처명</label>
										<input type="text" class="input t1 fl w200" id="cs_kitchen_name2" value="${result[0].cs_kitchen_name2 }" />
									</div>
									<div class="area mt5">
										<label for="code_post2" class="fl w100 text bold cm">주소</label>
										<div class="ml100">
											<div class="area">
												<input type="text" class="input t1 fl w200" id="code_post2"  value="${result[0].code_post2 }" />
												<a href="javascript:openJusoPopup('3');" class="btn form t1 fl ml5">주소검색</a>
											</div>
											<div class="area mt5">
												<label for="cs_kitchen_add21" class="ti">기본주소</label>
												<input type="text" class="input t1 fl w150" id="cs_kitchen_add21"   value="${result[0].cs_kitchen_add21 }"/>
												<label for="cs_kitchen_add22" class="ti">상세주소</label>
												<input type="text" class="input t1 fl w200 ml5" id="cs_kitchen_add22" value="${result[0].cs_kitchen_add22 }" />
											</div>
										</div>
									</div>
								</li>
								<li class="mt5 pt5 line bt">
									<div class="area">
										<label for="cs_kitchen_name3" class="fl w100 text bold cm">근무처명</label>
										<input type="text" class="input t1 fl w200" id="cs_kitchen_name3" value="${result[0].cs_kitchen_name3 }"/>
									</div>
									<div class="area mt5">
										<label for="code_post3" class="fl w100 text bold cm">주소</label>
										<div class="ml100">
											<div class="area">
												<input type="text" class="input t1 fl w200" id="code_post3"  value="${result[0].code_post3 }"/>
												<a href="javascript:openJusoPopup('4');" class="btn form t1 fl ml5">주소검색</a>
											</div>
											<div class="area mt5">
												<label for="cs_kitchen_add31" class="ti">기본주소</label>
												<input type="text" class="input t1 fl w150" id="cs_kitchen_add31" value="${result[0].cs_kitchen_add31 }" />
												<label for="cs_kitchen_add32" class="ti">상세주소</label>
												<input type="text" class="input t1 fl w200 ml5" id="cs_kitchen_add32" value="${result[0].cs_kitchen_add32 }" />
											</div>
										</div>
									</div>
								</li>
								<li class="mt5 pt5 line bt">
									<div class="area">
										<label for="cs_kitchen_name4" class="fl w100 text bold cm">근무처명</label>
										<input type="text" class="input t1 fl w200" id="cs_kitchen_name4" value="${result[0].cs_kitchen_name4 }" />
									</div>
									<div class="area mt5">
										<label for="code_post4" class="fl w100 text bold cm">주소</label>
										<div class="ml100">
											<div class="area">
												<input type="text" class="input t1 fl w200" id="code_post4"  value="${result[0].code_post4 }"/>
												<a href="javascript:openJusoPopup('5');" class="btn form t1 fl ml5">주소검색</a>
											</div>
											<div class="area mt5">
												<label for="cs_kitchen_add41" class="ti">기본주소</label>
												<input type="text" class="input t1 fl w150" id="cs_kitchen_add41" value="${result[0].cs_kitchen_add41 }" />
												<label for="cs_kitchen_add42" class="ti">상세주소</label>
												<input type="text" class="input t1 fl w200 ml5" id="cs_kitchen_add42" value="${result[0].cs_kitchen_add42 }" />
											</div>
										</div>
									</div>
								</li>
								<li class="mt5 pt5 line bt">
									<div class="area">
										<label for="cs_kitchen_name5" class="fl w100 text bold cm">근무처명</label>
										<input type="text" class="input t1 fl w200" id="cs_kitchen_name5" value="${result[0].cs_kitchen_name5 }"/>
									</div>
									<div class="area mt5">
										<label for="code_post5" class="fl w100 text bold cm">주소</label>
										<div class="ml100">
											<div class="area">
												<input type="text" class="input t1 fl w200" id="code_post5"  value="${result[0].code_post5 }"/>
												<a href="javascript:openJusoPopup('6');" class="btn form t1 fl ml5">주소검색</a>
											</div>
											<div class="area mt5">
												<label for="cs_kitchen_add51" class="ti">기본주소</label>
												<input type="text" class="input t1 fl w150" id="cs_kitchen_add51" value="${result[0].cs_kitchen_add51 }" />
												<label for="cs_kitchen_add52" class="ti">상세주소</label>
												<input type="text" class="input t1 fl w200 ml5" id="cs_kitchen_add52" value="${result[0].cs_kitchen_add52 }" />
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="f_wrap line bb p8 activeNo"  id="active2" style="display:none;">
					<div class="f_field div1">
						<div class="ff_title i_b"><strong>집단급식소외근무자</strong></div>
						<div class="ff_wrap ml130 text">
							<div class="area">
								<ul>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_1" value="01" <c:if test="${result[0].ncs_kitchen_code == '01'}"><c:out value="checked"/></c:if>  /><label for="radio4_1">건강기능식품 판매업소</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_2" value="02" <c:if test="${result[0].ncs_kitchen_code == '02'}"><c:out value="checked"/></c:if>  /><label for="radio4_2">교육 및 연구기관</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_3" value="03" <c:if test="${result[0].ncs_kitchen_code == '03'}"><c:out value="checked"/></c:if>  /><label for="radio4_3">어린이급식관리지원센터</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_4" value="04" <c:if test="${result[0].ncs_kitchen_code == '04'}"><c:out value="checked"/></c:if>  /><label for="radio4_4">위탁급식전문업체(행정업무)</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_5" value="05" <c:if test="${result[0].ncs_kitchen_code == '05'}"><c:out value="checked"/></c:if>  /><label for="radio4_5">보건소</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_6" value="06" <c:if test="${result[0].ncs_kitchen_code == '06'}"><c:out value="checked"/></c:if>  /><label for="radio4_6">행정직 공무원</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_7" value="07" <c:if test="${result[0].ncs_kitchen_code == '07'}"><c:out value="checked"/></c:if>  /><label for="radio4_7">육아종합지원센터</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active2" id="radio4_8" value="08" <c:if test="${result[0].ncs_kitchen_code == '08'}"><c:out value="checked"/></c:if>  /><label for="radio4_8">식품제조 가공업소</label></li>
									<li class="fl w240"><input type="radio" class="fl mr5" name="active2" id="radio4_9" value="09" <c:if test="${result[0].ncs_kitchen_code == '09'}"><c:out value="checked"/></c:if>  /><label for="radio4_9" class="fl">기타</label><input type="text" class="input t1 fl w100 ml10" id="ncs_kitchen_ret_code" value="${result[0].ncs_kitchen_ret_code}" maxlength="40"/></li>
								</ul>
							</div>
						</div>
						<div class="ff_wrap ml130">
							<ul>
								<li>
									<div class="area">
										<label for="ncs_kitchen_name1" class="fl w100 text bold cm">근무처명</label>
										<input type="text" class="input t1 fl w200" id="ncs_kitchen_name1" value="${result[0].ncs_kitchen_name1 }"/>
									</div>
									<div class="area mt5">
										<label for="code_post6" class="fl w100 text bold cm">주소</label>
										<div class="ml100">
											<div class="area">
												<input type="text" class="input t1 fl w200" id="code_post6"  value="${result[0].code_post6 }"/>
												<a href="javascript:openJusoPopup('7');" class="btn form t1 fl ml5">주소검색</a>
											</div>
											<div class="area mt5">
												<label for="ncs_kitchen_add61" class="ti">상세주소</label>
												<input type="text" class="input t1 fl w150" id="ncs_kitchen_add61" value="${result[0].ncs_kitchen_add61 }" />
												<label for="ncs_kitchen_add62" class="ti">상세주소</label>
												<input type="text" class="input t1 fl w200 ml5" id="ncs_kitchen_add62" value="${result[0].ncs_kitchen_add62 }" />
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="f_wrap line bb p8 activeNo" id="active3" style="display:none;">
					<div class="f_field div1">
						<div class="ff_title i_b"><strong>미활동(미취업)</strong></div>
						<div class="ff_wrap ml130 text">
							<div class="area">
								<ul>
									<li class="fl w240"><input type="radio" class="mr5" name="active3" id="radio5_1" value="1" <c:if test="${result[0].cs_kitchen_act_code == '1'}"><c:out value="checked"/></c:if> /><label for="radio5_1">임신,출산,육아</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active3" id="radio5_2" value="2" <c:if test="${result[0].cs_kitchen_act_code == '2'}"><c:out value="checked"/></c:if> /><label for="radio5_2">일시적 폐업, 실직</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active3" id="radio5_3" value="3" <c:if test="${result[0].cs_kitchen_act_code == '3'}"><c:out value="checked"/></c:if> /><label for="radio5_3">질병, 사고 등</label></li>
									<li class="fl w240"><input type="radio" class="mr5" name="active3" id="radio5_4" value="4" <c:if test="${result[0].cs_kitchen_act_code == '4'}"><c:out value="checked"/></c:if> /><label for="radio5_4">해외체류</label></li>
									<li class="fl w240"><input type="radio" class="fl mr5" name="active3" id="radio5_5" value="5" <c:if test="${result[0].cs_kitchen_act_code == '5'}"><c:out value="checked"/></c:if> /><label for="radio5_5" class="fl">기타</label>
									<input type="text" class="input t1 fl w100 ml10" id="cs_kitchen_ret_code" value="${result[0].cs_kitchen_ret_code}" maxlength="40"/></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${result[0].ar_finish_yn == '2'}">
				<div class="f_wrap line bb p8">
					<div class="f_field div1">
						<div class="ff_title"><strong>보수교육 이수사항</strong></div>
						<div class="ff_wrap ml130">
							<div class="area text">
							<input type="hidden" class="input t1 fl w100" id="cs_con_education"  maxlength="4" value="${result[0].cs_con_education }" />
								<ul>
									<li>
										총 (
										<c:set var="comma" value="0"></c:set>																
										<c:forEach var="list2" items="${recentRow}" varStatus="status">
											<c:if test="${comma !=0 }">,</c:if>
											<c:if test="${comma ==0 }"><c:set var="comma" value="1"/></c:if>
											<strong class="bold cm">${list2.cs_con_education }</strong> 년 ${list2.AR_FINISH_POINT }
										</c:forEach>
										) 시간 이수의무 중
										(
										<c:set var="comma" value="0"></c:set>																
										<c:forEach var="list3" items="${recentRow}" varStatus="status">
											<c:if test="${comma !=0 }">,</c:if>
											<c:if test="${comma ==0 }"><c:set var="comma" value="1"/></c:if>
											<strong class="bold cm">${list3.cs_con_education }</strong> 년 ${list3.AR_STATE_KR} ${list3.AR_MARKS_POINT }
										</c:forEach>
										) 시간 이수
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
				<c:if test="${mode=='I'}">
				<div class="f_wrap line bb p8">
					<div class="f_field div1">
						<div class="ff_title"><strong>보수교육 이수사항</strong></div>
						<div class="ff_wrap ml130">
							<div class="area text">
							<input type="hidden" class="input t1 fl w100" id="cs_con_education"  maxlength="4" value="${result[0].cs_con_education }" />
								<ul>
									<li>
									<c:set var="length" value="${fn:length(recentYearRow)}"/>
									<c:choose>
										<c:when test="{length<1 }">
											<strong class="bold cm">이수사항이 없습니다.</strong>
										</c:when>
										<c:otherwise>
											총 (
											<c:set var="comma" value="0"></c:set>																
											<c:forEach var="list2" items="${recentRow}" varStatus="status">
												<c:if test="${comma !=0 }">,</c:if>
												<c:if test="${comma ==0 }"><c:set var="comma" value="1"/></c:if>
												<strong class="bold cm">${list2.cs_con_education }</strong> 년 ${list2.AR_FINISH_POINT }
											</c:forEach>
											) 시간 이수의무 중
											(
											<c:set var="comma" value="0"></c:set>																
											<c:forEach var="list3" items="${recentRow}" varStatus="status">
												<c:if test="${comma !=0 }">,</c:if>
												<c:if test="${comma ==0 }"><c:set var="comma" value="1"/></c:if>
												<strong class="bold cm">${list3.cs_con_education }</strong> 년 ${list3.AR_STATE_KR} ${list3.AR_MARKS_POINT }
											</c:forEach>
											) 시간 이수
										</c:otherwise>
									</c:choose>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
				<c:if test="${mode=='U' }">
				<div class="f_wrap line bb p8">
					<div class="f_field div2">
						<label for="ar_state" class="ff_title i_b"><strong>진행상태</strong></label>
						<div class="ff_wrap ml130">
							<div class="area">
								<select class="select t2 fl w150 ml5" id="ar_state" >
									<option value="" >선택</option>
									<option value="0" <c:if test="${result[0].ar_state=='0'}"> selected="selected"</c:if> >이수</option>
									<option value="1" <c:if test="${result[0].ar_state=='1'}"> selected="selected"</c:if> >미이수</option>
									<option value="2" <c:if test="${result[0].ar_state=='2'}"> selected="selected"</c:if> >면제</option>
									<option value="3" <c:if test="${result[0].ar_state=='3'}"> selected="selected"</c:if> >알수없음</option>
									<option value="4" <c:if test="${result[0].ar_state=='4'}"> selected="selected"</c:if> >미대상</option>
									<option value="5" <c:if test="${result[0].ar_state=='5'}"> selected="selected"</c:if> >면허 재교부 신청중</option>
								</select>
							</div>
						</div>
					</div>
					<div class="f_field div2">
						<div class="ff_title i_b"><strong>보수교육년도</strong></div>
						<div class="ff_wrap ml130 text">
							<div class="area">
								<input type="text" class="input t1 fl w100" id="cs_con_education"  max="4" value="${result[0].cs_con_education }" />
							</div>
						</div>
					</div>
				</div>
				</c:if>
			</div>
			<h6 class="title s1 i_b_t3 mt40"><mark class="bold">첨부서류</mark><span class="ssmall">( <span class="i_b_t5"> 필수첨부</span> )</span></h6>
			<div class="form mt10 line bt bcm">
				<div class="f_wrap p8">
					<div class="f_field div1">
						<label for="bbs_file1" class="ff_title i_b"><strong>영양사 면허증 사본</strong></label>
						<div class="ff_wrap ml180">
							<input type="hidden" name="step" value="step6">
							<div class="area">
								<input type="file" class="file t1"  id="bbs_file1" name="bbs_file1" />
							</div>
							<c:choose>
							<c:when test="${mode=='I'}">
							<div class="area text mt5">
								파일명 ( ${fileInfo[0].AR_ADD_FILE_NAME} )
								<input type="hidden" id="bbs_file_url" value="${fileInfo[0].AR_ADD_FILE_URL}" />
								<input type="hidden" id="file_yn" value="" />
								<input type="hidden" id="ar_add_file_name" value="${fileInfo[0].AR_ADD_FILE_NAME}" />
								<input type="hidden" id="ar_add_file_url"  value="${fileInfo[0].AR_ADD_FILE_URL}" />
								<input type="hidden" id="ar_add_file_type" value="${fileInfo[0].AR_ADD_FILE_TYPE}" />
								<input type="hidden" id="ar_add_file_size" value="${fileInfo[0].AR_ADD_FILE_SIZE}" />
							</div>
							 <input type="hidden" id="file_yn" value="" />
							</c:when>
							<c:otherwise>
							<input type="hidden" id="bbs_file_url" value="" />
							</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
	</div>
	<div class="btn_wrap mt30">
		<ul>
			<c:if test="${mode!='I'}">
			<!-- <li><a href="javascript:f_print(document.getElementById('div_print').innerHTML);" class="btn big">프린트</a></li> -->
			<li><a href="javascript:f_print();" class="btn big">프린트</a></li>
			</c:if>
			<c:if test="${mode=='I'}">
			<li><a href="javascript:doWrite('0', '${mode}');" class="btn big t1">저장</a></li>
			</c:if>
			<li><a href="javascript:doWrite('1', '${mode}');" class="btn big t2">제출</a></li>
			<li><a href="javascript:doWrite('2', '${mode}');" class="btn big t2">완료</a></li>
			<li><a href="javascript:self.close();" class="btn big">취소</a></li>
		</ul>
		<input type="hidden" id="jusoId" />
		<input type="hidden" id="mode" value="${mode}"/>
	</div>

</div>
</div>
</form>
</body>
</html>
