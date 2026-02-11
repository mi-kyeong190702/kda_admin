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
<%@ page import="com.ant.common.properties.AntData"%>

<script src="js/form.js" type="text/javascript" ></script>

<%

String doc_seq = "";
if(request.getAttribute("doc_seq"		)!=null) doc_seq = request.getAttribute("doc_seq").toString();

String btnCnt = request.getParameter("btnCnt");

%>

		
			
<script>
function onload(form) {

	opener.document.dmInputForm.btn<%=btnCnt%>.value="완료";

	var url="<%=AntData.DOMAIN%>/doc_form/docu_print.jsp?doc_code=0108&doc_seq="+"<%=doc_seq%>";

	form.action = url;
	form.submit();
	
}
</script>

<body onload="onload(dmInputForm)">
<form name="dmInputForm" method="post" action="">
</form>
</body>

