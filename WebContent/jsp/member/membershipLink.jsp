<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import="com.ant.common.properties.AntData"%>
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

String code_pers = "";
if(request.getAttribute("param"		)!=null) code_pers = request.getAttribute("param").toString();

String btnCnt = request.getParameter("btnCnt");

%>

		
			
<script>
function onload(form) {

	opener.document.memberInputForm.btn<%=btnCnt%>.value="완료";

	var url="<%=AntData.DOMAIN%>/doc_form/docu_print.jsp?"+"<%=code_pers%>";

	form.action = url;
	form.submit();
	
}
</script>

<body onload="onload(memberInputForm)">
<form name="memberInputForm" method="post" action="">
</form>
</body>


