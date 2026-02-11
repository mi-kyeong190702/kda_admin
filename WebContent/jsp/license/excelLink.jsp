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
<link rel="stylesheet" type="text/css" href="css/m_search.css" />
<link rel="stylesheet" type="text/css" href="css/m_member.css" />


<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>

<script src="js/form.js" type="text/javascript" ></script>
<%
String sResultString		= "";

if(request.getAttribute("sResultString"		) != null ) sResultString		= request.getAttribute("sResultString"	).toString();

%>

<script>


function fn_close(form) {
	window.close();
}


function onload(form) {
//	alert(sResultString);
//	window.close();
}
</script>

<head></head>

<body onload="onload(memberInputForm)">


<form name="memberInputForm" method="post" action="">

<div style="width:100%; text-align:center;">
<br/><%=sResultString%><br/><br/>
<input type="button" value="확인" onclick="window.close();"/>
</div>

</form>
 
</body>




