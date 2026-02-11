<%@page import="org.apache.struts.action.Action"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import="net.sf.json.JSONArray"%>
<% 
		request.setCharacterEncoding("utf-8"); 
	  	String msg=(String)request.getAttribute("msg");
	  	String code=(String)request.getAttribute("code");
	  	
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
var code="<%=code%>";
var msg="<%=msg%>";

if(code == "check"){
	
	if(msg == "Y"){
		alert("중복된 아이디가 없습니다. 사용하셔도 좋습니다");
	}else if(msg == "N"){
		alert("이미 가입된 아이디 이므로 사용하실 수 없습니다");
	}
	
	opener.document.getElementById("code_dup").value = msg;
	self.close();
	
}else if(code == "upt"){
	opener.location.reload();
	alert(msg);
	self.close();
}


</script>
</head>
<body>
 
</body>
</html>