<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- <%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9"> 
<title><tiles:getAsString name="title"/></title>
<link rel="shortcut icon" href="images/favicon.ico">

</head>
<body>

	<table border="0" width="1000" cellspacing="0" cellpadding="0">
		<tr>
			<td height="85" colspan="2"><tiles:insert attribute="top"/></td>
		</tr>
		<tr>
			<td width="180"><tiles:insert attribute="left"/></td>
			<td><tiles:insert attribute="main"/></td>
		</tr>
		<tr>
			<td colspan="2" height="200"><tiles:insert attribute="footer"/></td>
		</tr>
	</table>
</body>

</html>