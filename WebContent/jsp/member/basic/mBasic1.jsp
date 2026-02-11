<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@ page import="net.sf.json.JSONArray"%>
<% 
		request.setCharacterEncoding("utf-8"); 
		String errMsg="";
		if(request.getAttribute("errMsg")!=null){
			errMsg=URLDecoder.decode(request.getAttribute("errMsg").toString(),"UTF-8"); 
		}
	  	List<Map> result=(List<Map>)request.getAttribute("result");
	  	List<Map> code=(List<Map>)session.getAttribute("code");	  		  	
	  	List<Map> dcomp=(List<Map>)request.getAttribute("defcomp");
	  	String pCode=StringUtil.nullToStr((String)request.getAttribute("pCode"), request.getParameter("pCode"));
	  	String url="memberBasic.do?method=compList&pCode="+pCode;
	  	
	  	DecimalFormat df=new DecimalFormat("###,###");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/m_search.css" />
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

<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
.readOnly1 {background-color:#f2f2f2;}
</style>

<script type="text/javascript">
document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};
function init(){
	var errMsg="<%=errMsg%>";
	if(errMsg.length>5){
		alert(errMsg);
		return;
	}
}
$(document).ready(function(){
jQuery("#list").jqGrid({
      url: "<%=url%>",
      datatype: "json",
      mtype: 'POST',
      height:'250',
      width:'1097',
      colNames: ['근무처순번','근무처명','우편번호','근무처주소','근무처상세주소','전화번호','팩스번호','입사일','퇴사일','운영형태코드','운영형태','부서','직급코드','직급','직렬코드','직렬','영양사면허수당','연봉','허가병상','1식재료비','공동관리','공동조리','우선근무처','겸직여부','급식인원-아침','급식인원-점심','급식인원-저녁','급식인원-야식','위탁업체코드','위탁업체명','위탁업체주소','위탁업체전화','산재번호','등록일자','등록자'],
      colModel: [
			{name:'comp_seq',					index:'comp_seq', 					width:100, 	sortable:false,	hidden:true},
			{name:'company_name',			index:'company_name', 			width:40, 	sortable:false},
   			{name:'code_post',					index:'code_post', 					width:100,	sortable:false,	hidden:true},
   			{name:'company_add',				index:'company_add', 				width:100,	sortable:false},
   			{name:'company_add_detail',		index:'company_add_detail',		width:100,	sortable:false,	hidden:true},
   			{name:'company_tel',				index:'company_tel', 					width:100,	sortable:false,	hidden:true},
   			{name:'company_fax',				index:'company_fax', 				width:100,	sortable:false,	hidden:true},
   			{name:'pers_in_dt',					index:'pers_in_dt', 					width:40,	sortable:false},
   			{name:'pers_out_dt',					index:'pers_out_dt', 					width:40,	sortable:false},
   			{name:'code_use',						index:'code_use', 						width:100,	sortable:false,	hidden:true},
   			{name:'useName',						index:'useName', 						width:60,	sortable:false},
   			{name:'job_dept',						index:'job_dept', 						width:100,	sortable:false},
   			{name:'job_level_code',				index:'job_level_code', 				width:100,	sortable:false,	hidden:true},
   			{name:'levelName',					index:'levelName', 					width:40,	sortable:false},
   			{name:'job_line_code',				index:'job_line_code', 				width:40,	sortable:false,	hidden:true},
   			{name:'lineName',					index:'lineName', 						width:60,	sortable:false},
   			{name:'lic_pay',						index:'lic_pay', 							width:100,	sortable:false,	hidden:true,	formatter:'currency',	formatoptions:{thousandsSeparator:','}},
   			{name:'year_pay',						index:'year_pay', 						width:100,	sortable:false,	hidden:true,	formatter:'currency',	formatoptions:{thousandsSeparator:','}},
   			{name:'company_sick_bad',		index:'company_sick_bad', 		width:100,	sortable:false,	hidden:true},
   			{name:'company_meal',				index:'company_meal_mom', 		width:100,	sortable:false,	hidden:true},
   			{name:'join_con',						index:'join_con', 						width:100,	sortable:false,	hidden:true},
   			{name:'join_cook',					index:'join_cook', 						width:100,	sortable:false,	hidden:true},
   			{name:'primary_flag',					index:'primary_flag', 					width:100,	sortable:false,	hidden:true},
   			{name:'pers_multy',					index:'pers_multy', 					width:100,	sortable:false,	hidden:true},
   			{name:'company_count_mom',	index:'company_count_mom',		width:100,	sortable:false,	hidden:true},
   			{name:'company_count_lunch',	index:'company_count_lunch', 	width:100,	sortable:false,	hidden:true},
   			{name:'company_count_dinner',	index:'company_count_dinner', 	width:100,	sortable:false,	hidden:true},
   			{name:'company_count_midnig',	index:'company_count_midnig',	width:100,	sortable:false,	hidden:true},
   			{name:'trust_code',					index:'trust_code', 					width:100,	sortable:false,	hidden:true},
   			{name:'trust_name',					index:'trust_name', 					width:100,	sortable:false,	hidden:true},
   			{name:'trust_addr',					index:'trust_addr', 					width:100,	sortable:false,	hidden:true},
   			{name:'trust_tel',						index:'trust_tel', 						width:100,	sortable:false,	hidden:true},
   			{name:'secu_no',						index:'secu_no', 						width:100,	sortable:false,	hidden:true},
   			{name:'regi_date',						index:'regi_date', 						width:100,	sortable:false,	hidden:true},
   			{name:'register',						index:'register',						width:40, 	sortable:false,	hidden:true}
                ],
                rowNum:10,
                pager: '#pager2',
                viewrecords: true,	
				altRows:true,
				altclass:'myAltRowClass',
				rownumbers : true,				
				onSelectRow: function(ids) {
					if(ids == null) {
						ids=0;
						if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
						{
							alert("컬럼을 정확히 선택해 주세요");
						}
					} else {
						var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
						if(id){
							var ret = jQuery("#list").jqGrid('getRowData',id);
							document.comp.comp_seq.value=ret.comp_seq;
							document.comp.cName.value=ret.company_name;
							document.comp.cPost.value=ret.code_post.substring(0,3)+'-'+ret.code_post.substring(3,6);
							document.comp.cAddr.value=ret.company_add;
							document.comp.cAddrd.value=ret.company_add_detail;
							document.comp.cTel.value=ret.company_tel;
							document.comp.cFax.value=ret.company_fax;
							document.comp.in_dt.value=ret.pers_in_dt;
							document.comp.out_dt.value=ret.pers_out_dt;
							document.comp.cUse.value=ret.code_use;
							document.comp.cDept.value=ret.job_dept;
							document.comp.cLevel.value=ret.job_level_code;
							document.comp.cLine.value=ret.job_line_code;
							document.comp.lPay.value=ret.lic_pay;
							document.comp.yPay.value=ret.year_pay;
							document.comp.cBad.value=ret.company_sick_bad;
							document.comp.cMeal.value=ret.company_meal;
							document.comp.cMom.value=ret.company_count_mom;
							document.comp.cLunch.value=ret.company_count_lunch;
							document.comp.cDinner.value=ret.company_count_dinner;
							document.comp.cCom.value=ret.join_con;							
							document.comp.cCook.value=ret.join_cook;
							document.comp.cMulty.value=ret.pers_multy;
							document.comp.cPrime.value=ret.primary_flag;
							document.comp.cSecu.value=ret.secu_no;
							document.comp.cTName.value=ret.trust_name;
							document.comp.cTcode.value=ret.trust_code;
							document.comp.cTtel.value=ret.trust_tel;
							document.comp.cTaddr.value=ret.trust_addr;	
							document.comp.gubun.value='update';
						}
						else {}
					}
				}
      
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function reg_mod(){
	if(document.comp.gubun.value=='update'){
		document.comp.action="memberBasic.do?method=mComp";
		document.comp.submit();	
	}else if(document.comp.gubun.value=='insert'){
		document.comp.action="memberBasic.do?method=iComp";
		document.comp.submit();
	}
	
}

function tSearch(){
	window.open('memberBasic.do?method=tSearch&sel=N&tName=" "','trust','width=500, height=700, resizable=no, scrollbars=yes');
}

function postSearch(){
	//window.open('memberBasic.do?method=postSearch&sel=N&keyword=',"postnum","scrollbar=no,resizable=no,toolbar=no,width=675,height=500,left=20,top=29,status=no");
	//window.open('memberBasic.do?method=postSearch&sel=2',"postnum","scrollbars=yes,menubar=no,resizable=no,toolbar=no,width=440,height=400,status=no");
	var pop = window.open("/post5.do","jusopop","width=570,height=420, scrollbars=yes, resizable=yes");

}

//근무처우편번호콜백
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){

	document.comp.cAddr.value = roadAddrPart1;
		document.comp.cAddrd.value = addrDetail;
		document.comp.cPost.value = zipNo;
		
}

function ban(val){
	if(val.tagName=="INPUT"){
		if(event.keyCode==8){
			alert("수정할 수 없는 항목입니다.");
			event.keyCode=0;
			event.cancelBubble=true;
			event.returnValue=false;
		}
	}
}

function reg_reset(){
	document.comp.gubun.value='insert';
	document.comp.comp_seq.value='';
	document.comp.cTcode.value='';
	document.comp.cTcode.value='';
	
	document.comp.cName.value='';
	document.comp.cTel.value='';
	document.comp.cPost.value='';
	document.comp.cAddr.value='';
	document.comp.cAddrd.value='';
	document.comp.cFax.value='';
	document.comp.in_dt.value='';
	document.comp.out_dt.value='';
	document.comp.cUse.value='1';
	document.comp.cDept.value='';
	document.comp.cLevel.value='99';
	document.comp.cLine.value='99';
	document.comp.lPay.value='0';
	document.comp.yPay.value='0';
	document.comp.cBad.value='0';
	document.comp.cMeal.value='0';
	document.comp.cMom.value='0';
	document.comp.cLunch.value='0';
	document.comp.cDinner.value='0';
	document.comp.cMid.value='0';
	document.comp.cCom.value='0';
	document.comp.cCook.value='0';
	document.comp.cMulty.value='0';
	document.comp.cPrime.value='1';
	document.comp.cSecu.value='';
	document.comp.cTName.value='';
	document.comp.cTtel.value='';
	document.comp.cTaddr.value='';
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});
</script>
</head>
<body>
<form name="comp" method="post" action="">
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
	<div id="M_contents">
	  <div class="mSearch_01">
        <table class="tbl_type1" border="1" cellspacing="0" summary="회원검색">
          <caption>회원검색</caption>          
			<tbody>
            <tr>           
              <td class="mtbl">이름</td>
              <td class="mtbl1"><%=result.get(0).get("pers_name") %></td>
              <td class="mtbl">아이디</td>
              <td class="mtbl1"><%=result.get(0).get("id") %></td>
              <td class="mtbl">패스워드</td>
              <td class="mtbl1">**********</td>
            </tr>
            
            <tr>
            	
<!--             	JUMIN_DEL -->
<!--               <td>주민번호</td> -->
<!--               <td> -->
<%--               	<%=result.get(0).get("pers_no").toString().substring(0,6)%> - ******* &nbsp;&nbsp; --%>
<%--               	(성별 : <%if("1".equals(result.get(0).get("code_sex").toString())) out.print("남"); else out.print("여"); %>) --%>
<!-- 			  </td> -->
			  <td>생년월일</td>
              <td>
              	<%=result.get(0).get("pers_birth") %>
			  </td>
              <td>성별</td>
              <td>
              	<%if("1".equals(result.get(0).get("code_sex").toString())) out.print("남"); else out.print("여"); %>
			  </td>
			  
              <td>면허번호</td>
              <td><%=result.get(0).get("lic_no") %> &nbsp;&nbsp;(발급일자:<%=result.get(0).get("lic_print_dt") %>)</td>
            </tr>
            <!-- 근무처명, 주소을 추가하려면 이쪽 주석을 풀면된다.--> 
            <tr>
              	<td>회원구분</td>
              	<td><%=result.get(0).get("c_member_name") %></td>
            	<td>근무처명</td>
            	<td><%=result.get(0).get("company_name") %></td>
            	<td>근무처 주소</td>
            	<td colspan='2'><%=result.get(0).get("company_add") %></td>
            </tr>         
            <tr>           
              <td>소속지부</td>
              <td><%=result.get(0).get("bran_name") %></td>
              <td>소속분과</td>
              <td><%=result.get(0).get("big_name") %></td>
              <td>소속분회</td>
              <td><%=result.get(0).get("sect_name") %></td>         
            </tr>
            <tr>           
              <td>근무처구분</td>
              <td><%=result.get(0).get("great_name") %></td>
              <td>근무처대분류
			  <td><%=result.get(0).get("part_name") %></td>
              <td>근무처소분류
			  <td><%=result.get(0).get("small_name") %></td>
            </tr>			
            </tbody>
            </table>       
	  </div><!--mSearch_01 End-->
	   <div class="mTabmenu">
	    <div id="ts_tabmenu">
          <ul>
	        <li><a href="memberBasic.do?method=memInfo&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>기본정보</span></strong></a></li>
	        <li><a href="#"  class="overMenu"><strong><span>근무처정보</span></strong></a></li>
	        <li><a href="memberBasic.do?method=bank&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>회비입출</span></strong></a></li>
	        <li><a href="memberBasic.do?method=dues&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>회비처리</span></strong></a></li>
	        <li><a href="memberBasic.do?method=edu&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>교육</span></strong></a></li>
	        <li><a href="memberBasic.do?method=certifi&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>자격증</span></strong></a></li>
	        <li><a href="memberBasic.do?method=donation&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>기부&frasl;기금</span></strong></a></li>
			<li><a href="memberBasic.do?method=memo&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>메모</span></strong></a></li>			
          </ul>
        </div>
		<div class="mTabmenu_01" id="tab01" style="display:show">
	  <%if(dcomp.size()>0){ %>
        <table class="mytable_02" cellspacing="0" summary="근무처정보">
        	<input type="hidden" name="mCode_pers" value="<%=result.get(0).get("code_pers") %>"/>
		<input type="hidden" name="comp_seq" value="<%=dcomp.get(0).get("comp_seq") %>"/>
		<input type="hidden" name="cTcode" value="<%=dcomp.get(0).get("trust_code") %>"/>  
		<input type="hidden" name="register" value="<%=session.getAttribute("G_NAME") %>"/>
		<input type="hidden" name="code_part" value="<%=result.get(0).get("code_part")  %>"/>
		<input type="hidden" name="code_great" value="<%=result.get(0).get("code_great")  %>"/>
		<input type="hidden" name="code_small" value="<%=result.get(0).get("code_small")  %>"/>
		<input type="hidden" id="pgu" value="comp"/>
		<input type="hidden" id="gubun" value="update"/>
          <caption>근무처정보</caption>
             <tr>
               <td class="nobg">근무처명</td>
               <td colspan="3" class="nobg1"><input type="text"  name="cName" size="35" value="<%=dcomp.get(0).get("company_name") %>"/></td>			   			   
			   <td class="nobg3">근무처전화번호</td>
			   <td class="nobg1"><input type="text"  name="cTel" size="15" value="<%=dcomp.get(0).get("company_tel") %>"/></td>
			 </tr>			 
             <tr>
             	<td class="alt1">근무처우편번호</td>
			   <td><input type="text"   name="cPost" id="cPost" size="7" onclick="javascript:postSearch();" value="<%if(!"".equals(dcomp.get(0).get("code_post").toString())&&dcomp.get(0).get("code_post").toString().length()==5){ out.print(dcomp.get(0).get("code_post").toString().subSequence(0, 3)+"-"+dcomp.get(0).get("code_post").toString().subSequence(3, 5)); }%>" />
			   <input name="bnt" type="button" value="우편번호" onclick="javascript:postSearch();"/></td>
               <td class="alt">근무처주소</td>
               <td colspan="3"><input type="text" name="cAddr" id="cAddr" size="45" " value="<%=dcomp.get(0).get("company_add") %>" />-<input type="text" name="cAddrd" id="cAddrd" size="25" value="<%=dcomp.get(0).get("company_add_detail") %>"/></td>
             </tr>
			 <tr>
               <td class="alt1">근무처팩스번호</td>
			   <td><input type="text"  name="cFax" size="15" value="<%=dcomp.get(0).get("company_fax") %>"/></td>
               <td class="alt">입사일</td>
               <td><input type="text" name="in_dt" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px size="15" value="<%=dcomp.get(0).get("pers_in_dt") %>"/></td>
               <td class="alt">퇴사일</td>
			   <td><input type="text" name="out_dt" id="datepicker1" class="input" style=padding-top:3px  style=padding-bottom:3px size="15" value="<%=dcomp.get(0).get("pers_out_dt") %>"/></td>
             </tr>            
			  <tr>
               <td class="alt1">운영형태</td>
               <td class="alt">부서</td>
               <td class="alt">직급</td>
               <td class="alt">직렬</td>
			   <td class="alt">영양사면허수당</td>
               <td class="alt">연봉</td>
             </tr>
			 <tr>
               <td class="alt2"><select name="cUse" >
                <% 
                	String detCode, detCName="";
                	for(int i=0;i<code.size();i++){
                		if("001".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(dcomp.get(0).get("code_use").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	                			
                		}
                	}
                %>
                </select></td>
               <td><input type="text"  name="cDept" size="15" value="<%=dcomp.get(0).get("job_dept") %>"/></td>
               <td><select name="cLevel" >
                <% 
                    out.println("<option value=''>선택</option>");
                	for(int i=0;i<code.size();i++){
                		if("011".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(dcomp.get(0).get("job_level_code").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}                			
                		}
                	}
                %></select></td>
               <td><select name="cLine" >
                <% 
                    out.println("<option value=''>선택</option>");
                	for(int i=0;i<code.size();i++){
                		if("010".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(dcomp.get(0).get("job_line_code").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}                			
                		}
                	}
                %></select></td>
			   <td><input type="text"  name="lPay" size="15" value="<%=df.format(dcomp.get(0).get("lic_pay")) %>"/></td>
               <td><input type="text"  name="yPay" size="15" value="<%=df.format(dcomp.get(0).get("year_pay")) %>"/></td>
             </tr>	
			 <tr>
               <td class="alt1">허가병상</td>
               <td class="alt">1식재료비</td>
               <td class="alt">1일급식인원-아침</td>
               <td class="alt">1일급식인원-점심</td>
               <td class="alt">1일급식인원-저녁</td>
               <td class="alt">1일급식인원-야식</td>
             </tr>
			 <tr>
               <td class="alt2"><input type="text" name="cBad" size="15" value="<%=dcomp.get(0).get("company_sick_bad") %>"/></td>
               <td><input type="text" name="cMeal" size="15" value="<%=df.format(dcomp.get(0).get("company_meal")) %>"/></td>
               <td><input type="text" name="cMom" size="15" value="<%=dcomp.get(0).get("company_count_mom") %>"/></td>
               <td><input type="text" name="cLunch" size="15" value="<%=dcomp.get(0).get("company_count_lunch") %>"/></td>
               <td><input type="text" name="cDinner" size="15" value="<%=dcomp.get(0).get("company_count_dinner") %>"/></td>
               <td><input type="text" name="cMid" size="15" value="<%=dcomp.get(0).get("company_count_midnig") %>"/></td>
             </tr>
			 <tr>
			 	<td class="alt1">공동관리</td>
			   	<td class="alt">공동조리</td>
              	<td class="alt">겸직여부</td>
               	<td class="alt">우선근무처여부</td>
               	<td class="alt">산재번호</td>
               	<td class="alt">소속위탁업체</td>		  
             </tr>
			 <tr>
			 	<td class="alt2"><select name="cCom">
                <% 
                	for(int i=0;i<11;i++){       
                			if(i==Integer.parseInt(dcomp.get(0).get("join_con").toString())){
                				out.println("<option value="+i+" selected>"+i+"</option>");
                			}else{
                				out.println("<option value="+i+">"+i+"</option>");
                			}                			
                	}
                %>       
                </select>
			   </td>
			   <td><select name="cCook">
                <% 
                	for(int i=0;i<11;i++){       
                			if(i==Integer.parseInt(dcomp.get(0).get("join_cook").toString())){
                				out.println("<option value="+i+" selected>"+i+"</option>");
                			}else{
                				out.println("<option value="+i+">"+i+"</option>");
                			}                			
                	}
                %>       
                </select>
			   </td>
               <td><select name="cMulty">
                <option value="">선택</option>
                <option value="1" <%if("1".equals(dcomp.get(0).get("pers_multy").toString())) out.print("selected"); %>>겸직</option>
                <option value="0" <%if("0".equals(dcomp.get(0).get("pers_multy").toString())) out.print("selected"); %>>비겸직</option>       
                </select>
			   </td>
               <td><select name="cPrime"> 
               	<option value="0" <%if("0".equals(dcomp.get(0).get("primary_flag").toString())) out.print("selected"); %>>아니오</option>               
                <option value="1" <%if("1".equals(dcomp.get(0).get("primary_flag").toString())) out.print("selected"); %>>예</option>                       
                </select></td>               
               <td><input type="text" name="cSecu" size="15" value="<%=dcomp.get(0).get("secu_no") %>"/></td>
			   <td><input type="text" name="cTName" size="12" value="<%=dcomp.get(0).get("trust_name") %>" readonly/>&nbsp;<input name="bnt1" type="button" value="검색" onclick="javascript:tSearch();"/></td>
             </tr>
			 <tr>
			   <td class="alt1">위탁업체전화번호</td>
			   <td><input type="text" name="cTtel" size="15" value="<%=dcomp.get(0).get("trust_tel") %>" readonly/></td>
               <td class="alt">위탁업체주소</td>
               <td colspan="3"><input type="text" name="cTaddr" size="77" value="<%=dcomp.get(0).get("trust_addr") %>" readonly/></td>
             </tr>
        </table>
        <%}else if(dcomp.size()==0){ %>
        <table class="mytable_02" cellspacing="0" summary="근무처정보">
        	<input type="hidden" name="mCode_pers" value="<%=result.get(0).get("code_pers") %>"/>
		<input type="hidden" name="comp_seq" value="1"/>
		<input type="hidden" name="cTcode" /> 
		<input type="hidden" name="register" value="<%=session.getAttribute("G_NAME") %>"/>
		<input type="hidden" name="code_part" value="<%=result.get(0).get("code_part")  %>"/>
		<input type="hidden" name="code_great" value="<%=result.get(0).get("code_great")  %>"/>
		<input type="hidden" name="code_small" value="<%=result.get(0).get("code_small")  %>"/>
		<input type="hidden" id="pgu" value="comp"/>
		<input type="hidden" id="gubun" value="insert"/>
          <caption>근무처정보</caption>
             <tr>
               <td class="nobg">근무처명</td>
               <td colspan="3" class="nobg1"><input type="text"  name="cName" size="35" value=""/></td>			   			   
			   <td class="nobg3">근무처전화번호</td>
			   <td class="nobg1"><input type="text"  name="cTel" size="15" value=""/></td>
			 </tr>			 
             <tr>
             	<td class="alt1">근무처우편번호</td>
			   <td><input type="text"  class="readOnly1" name="cPost" id="cPost" size="7" onclick="javascript:postSearch();" value="" readonly/>
			   <input name="bnt" type="button" value="우편번호" onclick="javascript:postSearch();"/></td>
               <td class="alt">근무처주소</td>
               <td colspan="3"><input type="text" name="cAddr" id="cAddr" size="45" onkeydown="ban(this);" value="" class="readOnly1" readonly/>-<input type="text" name="cAddrd" id="cAddrd" size="25" value=""/></td>
             </tr>
			 <tr>
               <td class="alt1">근무처팩스번호</td>
			   <td><input type="text"  name="cFax" size="15" value=""/></td>
               <td class="alt">입사일</td>
               <td><input type="text" name="in_dt" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px size="15" value=""/></td>
               <td class="alt">퇴사일</td>
			   <td><input type="text" name="out_dt" id="datepicker1" class="input" style=padding-top:3px  style=padding-bottom:3px size="15" value=""/></td>
             </tr>            
			  <tr>
               <td class="alt1">운영형태</td>
               <td class="alt">부서</td>
               <td class="alt">직급</td>
               <td class="alt">직렬</td>
			   <td class="alt">영양사면허수당</td>
               <td class="alt">연봉</td>
             </tr>
			 <tr>
               <td class="alt2"><select name="cUse" >
                <% 
                	String detCode, detCName="";
                	for(int i=0;i<code.size();i++){
                		if("001".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();      
                			out.println("<option value="+detCode+">"+detCName+"</option>");	                			
                		}
                	}
                %>
                </select></td>
               <td><input type="text"  name="cDept" size="15" value=""/></td>
               <td><select name="cLevel" >
                <%  out.println("<option value=''>선택</option>");
                	for(int i=0;i<code.size();i++){
                		if("011".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");             			
                		}
                	}
                %></select></td>
               <td><select name="cLine" >
                <% 
                    out.println("<option value=''>선택</option>");
                	for(int i=0;i<code.size();i++){
                		if("010".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");              			
                		}
                	}
                %></select></td>
			   <td><input type="text"  name="lPay" size="15" value=""/></td>
               <td><input type="text"  name="yPay" size="15" value=""/></td>
             </tr>	
			 <tr>
               <td class="alt1">허가병상</td>
               <td class="alt">1식재료비</td>
               <td class="alt">1일급식인원-아침</td>
               <td class="alt">1일급식인원-점심</td>
               <td class="alt">1일급식인원-저녁</td>
               <td class="alt">1일급식인원-야식</td>
             </tr>
			 <tr>
               <td class="alt2"><input type="text" name="cBad" size="15" value=""/></td>
               <td><input type="text" name="cMeal" size="15" value=""/></td>
               <td><input type="text" name="cMom" size="15" value=""/></td>
               <td><input type="text" name="cLunch" size="15" value=""/></td>
               <td><input type="text" name="cDinner" size="15" value=""/></td>
               <td><input type="text" name="cMid" size="15" value=""/></td>
             </tr>
			 <tr>
			 	<td class="alt1">공동관리</td>
			   	<td class="alt">공동조리</td>
              	<td class="alt">겸직여부</td>
               	<td class="alt">우선근무처여부</td>
               	<td class="alt">산재번호</td>
               	<td class="alt">소속위탁업체</td>		  
             </tr>
			 <tr>
			 	<td class="alt2"><select name="cCom">
                <% 
                	for(int i=1;i<11;i++){       
	            	out.println("<option value="+i+">"+i+"</option>");             			
                	}
                %>       
                </select>
			   </td>
			   <td><select name="cCook">
                <% 
                	for(int i=1;i<11;i++){       
                		out.println("<option value="+i+">"+i+"</option>");
                	}
                %>       
                </select>
			   </td>
               <td><select name="cMulty">
                <option value="">선택</option>
                <option value="1" >겸직</option>
                <option value="0" >비겸직</option>       
                </select>
			   </td>
               <td><select name="cPrime">
               	<option value="0" >아니오</option>                
                <option value="1" >예</option>                       
                </select></td>               
               <td><input type="text" name="cSecu" size="15" value=""/></td>
			   <td><input type="text" name="cTName" size="12" value="" readonly/>&nbsp;<input name="bnt1" type="button" value="검색" onclick="javascript:tSearch();"/></td>
             </tr>
			 <tr>
			   <td class="alt1">위탁업체전화번호</td>
			   <td><input type="text" name="cTtel" size="15" value="" readonly/></td>
               <td class="alt">위탁업체주소</td>
               <td colspan="3"><input type="text" name="cTaddr" size="77" value="" readonly/></td>
             </tr>
        </table>
        <%} %>	
        <p class="btnSave">
        	<a href="javascript:reg_reset();"><img src="images/icon_new.gif" alt="신규" /></a>
        	<a href="javascript:reg_mod();"><img src="images/icon_save.gif" alt="저장" /></a>        	
        </p>
		</div><!--mTabmenu_01 End-->
		<div class="mTabmenu_01" id="tab02" style="display:show">
			<table id="list"></table>
			<div id="pager2"></div>
		</div><!--mTabmenu End-->
		</div><!--mTabmenu End-->
	</div><!--M_contents End-->
 </form>
 
</body>
</html>