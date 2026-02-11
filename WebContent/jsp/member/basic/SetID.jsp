<%@page import="org.apache.struts.action.Action"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import="net.sf.json.JSONArray"%>
<% 
		request.setCharacterEncoding("utf-8"); 
	  	String code=(String)request.getAttribute("msg");
	  	
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

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<style type="text/css">
</style>

<script type="text/javascript">

var msg="<%=code%>";
if(msg == "0"){
	alert("회원이 존재하지 않습니다");
	
}else if(msg == "2"){
	alert("회원이 두명이상 존재합니다");
	
}

function unCheckDup(){
	$("#code_dup").val("N");
	$("#pers_id").val("");
}

function id_check(){
	
	var id = $("#pers_id").val();
	var cd = $("#code_pers").val();
	if(id.length < 4){
		alert("아이디는 영문,숫자 혹은 조합으로 4~12자 이내 이어야 합니다.");
		$("#pers_id").focus();
		return false;
	}
	if(cd.length < 4){
		alert("검색을 먼저 하세요");
		$("#code_pers").focus();
		return false;
	}
	
	window.open("memberBasic.do?method=idCheck&pers_id="+id,"checkidpop","width=200,height=200,left=500,top=200, scrollbars=yes, resizable=yes");
	
}

function save_id(){
	
	if($("#code_dup").val()=="N"){
		alert("아이디 중복확인을 먼저 하세요.");
		return false;
	}
	
	var pers =$("#code_pers").val();
	var id = $("#pers_id").val();
	
	window.open("memberBasic.do?method=idUpt&pers_id="+id+"&code_pers="+pers,"checkidpop","width=200,height=200,left=500,top=200, scrollbars=yes, resizable=yes");
	
}

function search_id(){
	
	if($("#pers_name").val()==""){
		alert("이름을 입력하세요");
		return false;
	}
	var pers_name=$("#pers_name").val();
	var lic_no=$("#lic_no").val();

	location.href="memberBasic.do?method=SetID&pers_name="+pers_name+"&lic_no="+lic_no+"&use_lic_no=Y&adm=Y";
	
}

</script>
</head>
<body>
<div id="M_contents">
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="회원검색">
          <caption>회원검색</caption>          
			<tbody>
            <tr>           
              <td class="mtbl">이름${msg }</td>
              <td class="mtbl1">
              <input type="text" name="pers_name"  id="pers_name"  size="25" value="${pers_name }" maxlength="30"/>
              <a href="javascript:search_id();">검색</a>              
			  </td>
			  
              <td class="mtbl">면허번호</td>
              <td class="mtbl1">
              <input type="text" name="lic_no"  id="lic_no"  size="25" value="${lic_no }" maxlength="6"/>
              <input type="hidden" name="use_lic_no" value="N"/>
			  </td>
			  
			  <td class="mtbl">휴대폰번호</td>
              <td class="mtbl1">
              <input type="text" name="pers_hp"  id="pers_hp"  size="25" value="${pers_hp }" readOnly="readOnly"/>
			  </td>
            </tr>		
            <tr>
	            <td class="mtbl">아이디</td>
              	<td class="mtbl1">
              	<input type="text" name="pers_id"  id="pers_id"  size="25" value="${id }" maxlength="30" OnClick="unCheckDup();"/>
              	<input type="hidden" name="code_dup" id="code_dup" value="N">
        		<input type="hidden" name="code_pers" id="code_pers" value="${code_pers }">
              	<a href="javascript:id_check();">중복확인</a>
			  	</td>
			  	<td colspan="4" class="mtbl">(첫자는 영문, 그외 문자는 영문,숫자 혹은 그 조합으로 4~12자 이내) <a href="javascript:save_id();">저장</a></td>
			  	
            </tr>	
            </tbody>
            </table>       
	  </div>
</div>
</body>
</html>