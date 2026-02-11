<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<script type="text/javascript">alert('<%=request.getParameter("msg").replaceAll("\\n","\\\\n")%>');history.back();</script>