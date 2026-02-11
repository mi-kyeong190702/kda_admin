<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*, java.io.*, java.sql.*, java.text.*;"%>
<%
response.setContentType("application/x-msdownload"); 
request.setCharacterEncoding("euc-kr"); 

String fileDir="D:/WEB/dietitian.or.kr_ver3/upload/license_inspection";

String filename = request.getParameter("filename");  
response.setHeader("Content-Disposition","attachment; filename="+filename); 

File file = new File(fileDir + "/" + filename);
byte[] bytestream = new byte[4096]; 
FileInputStream filestream = new FileInputStream(file); 
int i = 0, j = 0; 

try{ 
out.clear();
OutputStream outStream = response.getOutputStream(); 
while ((i = filestream.read(bytestream)) != -1) { 
outStream.write(bytestream,0,i); 
} 
outStream.close(); 
filestream.close();
}catch(IOException e){
	e.printStackTrace();
}finally{ 
} 
%>