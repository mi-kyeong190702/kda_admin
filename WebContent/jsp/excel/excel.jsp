<%@ page language="java" contentType="application/vnd.ms-excel;charset=utf-8" %> 
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%
// This is the JSP code to output the file in excel

//response.setContentType("application/vnd.ms-excel");

String buf=request.getParameter("csvBuffer");

try {
	response.getWriter().println(buf);
} catch(Exception e){}

%>
