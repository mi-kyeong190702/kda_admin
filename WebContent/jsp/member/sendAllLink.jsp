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
String tot_cnt		= "";
String success_cnt	= "";
String faile_cnt	= "";

if(request.getAttribute("tot_cnt"		) != null ) tot_cnt		= request.getAttribute("tot_cnt"	).toString();
if(request.getAttribute("success_cnt"	) != null ) success_cnt	= request.getAttribute("success_cnt").toString();
if(request.getAttribute("faile_cnt"		) != null ) faile_cnt	= request.getAttribute("faile_cnt"	).toString();

%>

<script>


function fn_close(form) {
	window.close();
}


function onload(form) {
	alert("총 <%=tot_cnt%> 건중 <%=success_cnt%>건 저장됐습니다.");
	window.close();
}
</script>

<head></head>

<body onload="onload(memberInputForm)">


<form name="memberInputForm" method="post" action="">

</form>
 
</body>




