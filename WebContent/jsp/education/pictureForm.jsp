<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@page import="java.util.Calendar"%> 
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="com.ant.common.util.StringUtil" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <title>대한영양사협회</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" type="text/css" href="css/popup.css" />  

<%
	String name = "";
	String msg ="";
	if(request.getAttribute("msg")!=null){
		msg=StringUtil.nullToStr("", request.getAttribute("msg").toString());
	}
	if(request.getAttribute("name")!= null) {
		name = request.getAttribute("name").toString();				
	} 
%>
   
<script type="text/javascript">
document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};
function init(){
	var msg='<%=msg%>';
	if(msg.length!=0){
		alert(msg);
		opener.document.mem.lt_image.value='<%=name%>';
		
		var pictureFilePath = "/upload/picture/<%=name%>";
		opener.document.getElementById("imgView").innerHTML = "";
		opener.document.getElementById("imgView").innerHTML = "<img src='" + pictureFilePath + "' width='70px' height='80px' 'margin-left:10px;'>";
		
		self.close();
	}
}

// 등록
function fnUpload(){
	if( sForm.formFile.value == "") {
		alert("사진 파일을 선택하십시오.");
		return;
	}
	document.sForm.submit();
}

</script>
 </head>
 <body>   
  
 <form id="list1" name="sForm" method="post" enctype="multipart/form-data" action = "lecture.do?method=picture_up">
  <input type="hidden" name="fileName">
  <input type="hidden" name="upYN" value="n">
   <table  cellspacing="0" summary="사진 업로드">        			
       <tr>           
         <td><img src="images/logo.gif" alt="로고" /></td>
       </tr>
    </table><br>
    <table border="0" width="100%" height="100%">
	<tr>
		<td align="center" colspan="2" height="36" bgcolor="#3C8AD6">&nbsp; 
			<font color=white><b>사진 업로드</b></font>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="20" >
			&nbsp;
		</td>
	</tr>		
	<tr height="20">
		<td width="40%" align="center" class="th_gray">
			<b>사진</b>
		</td>
		<td align="left">
			<div id="f1Area"><input type="file" name="formFile" id="f1" style="background-color:#DCDCDC;"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="20" >
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="8" align="center" class="th_gray">
			! 사진 사이즈 조절해 주세요.
		</td>
	</tr>
	<tr>
		<td colspan="8" align="center" class="th_gray">
			 ( 가로:70px&nbsp; 세로:80px )
		</td>
	</tr>
  </table>
 </form>
	
<p align="center">
	<input type="button" name="send"   value="등록"  onclick="fnUpload();"> 
	<input type="button" name="cancel" value="취소"  onclick="self.close();">
</p> 	

</body> 
</html>
