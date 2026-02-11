<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.URLDecoder"%>
<%
	request.setCharacterEncoding("UTF-8");
	String toAddr=URLDecoder.decode(request.getParameter("toAddr"	),"UTF-8");
	String param=(String)request.getAttribute("param"); if(param==null) param="";
	String ncount=(String)request.getAttribute("ncount");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>대한영양사협회 - 메일 발송</title>
<link rel="stylesheet" type="text/css" href="css/m_search.css" />

<script type="text/javascript" src="js/jquery-1.8.1.min.js" ></script>
<script type="text/javascript" src="se2/js/HuskyEZCreator.js" charset="utf-8"></script>

<script language="javascript">
	/*
	+This Application includes SmartEditor Basic. 
	+SmartEditor Basic contains the following license and notice below: 
	+ 
	+Copyright (c) 2008 NHN Corp 
	+This library is free software; you can redistribute it and/or 
	+modify it under the terms of the GNU Library General Public 
	+License as published by the Free Software Foundation; either 
	+version 2 of the License, or (at your option) any later version. 
  
	+This library is distributed in the hope that it will be useful, 
	+but WITHOUT ANY WARRANTY; without even the implied warranty of 
	+MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
	+Library General Public License for more details.
	
	+You should have received a copy of the GNU Library General Public 
	+License along with this library; if not, write to the 
	+Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, 
	+Boston, MA 02110-1301, USA. 
	*/
	function sendMail(){
		oEditors[0].exec("UPDATE_CONTENTS_FIELD", []); 
		document.mail.fr_name.value=encodeURIComponent(document.mail.fr_name1.value);
		document.mail.from.value=document.mail.from1.value;
		document.mail.subj.value=encodeURIComponent(document.mail.subj1.value);
		document.mail.content.value=encodeURIComponent(document.mail.content1.value);
		
		var regix = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var vali=regix.test(document.mail.from1.value);
		if(document.mail.from.value==null||document.mail.from.value==""){
			alert("보내는 분의 이메일 주소를 입력해주십시오.");
			document.mail.from1.focus();
			return;
		}		
		if(!vali){
			alert("이메일 주소 형식이 올바르지 않습니다.");
			document.mail.from1.focus();
			return;
		}
		if(document.mail.fr_name1.value==null||document.mail.fr_name1.value==""){
			alert("보내는 분의 이름을 입력해주십시오.");
			document.mail.fr_name1.focus();
			return;
		}
		if(document.mail.subj1.value==null||document.mail.subj1.value==""){
			alert("제목을 입력해주십시오.");
			document.mail.subj1.focus();
			return;
		}
		if(document.mail.content1.value==null||document.mail.content1.value==""){
			alert("내용을 입력해주십시오.");
			document.mail.content1.focus();
			return;
		}
		document.mail.submit();
		alert("메일발송 요청 되었습니다.");
		self.close();
	}
	
	function upFiles(){
		document.mail.fileName.value=encodeURIComponent(document.upFile.attatch.value);
		document.mail.upYN.value="y";
		document.upFile.submit();
		document.upFile.onload=function(){
			alert("파일 업로드 완료");	
		};
	} 
</script>
</head>

<body>
<form name="mail" method="post" action="memberBasic.do?method=SendMail<%=param %>" >
<input type="hidden" name="fr_name">
<input type="hidden" name="from">
<input type="hidden" name="subj">
<input type="hidden" name="content">
<input type="hidden" name="fileName">
<input type="hidden" name="upYN" value="n">
<table class="tbl_type" border="1" cellspacing="0" summary="회원검색"> 
	<tr> 
		<td style="width:100px;">받을 사람</td>
		<td colspan="3">
			<input type="text" name="to" size="70"  value="<%=toAddr %>" readonly="readonly">
			<%if(toAddr!=null && !"".equals(toAddr) && !"ALL".equals(toAddr) ){%>
				(<%=toAddr.split(",").length %>명)
			<%}else{ %>
				(<%=ncount %>명)
			<%} %>
		</td>
	</tr>
	<tr> 
		<td>보내는 사람</td>
		<td>
			<input type="text" name="fr_name1"  size="10" value="">
		</td>
		<td>이메일 주소</td>
		<td>
			<input type="text" name="from1"  size="30" value="">
		</td>
	</tr>
	<tr> 
		<td>제 목</td>
		<td colspan="3"> 
			<input type="text" name="subj1" size="78" value="대한영양사협회 안내 메일">
		</td>
	</tr>
	<tr> 
		<td colspan="4"> 
			<textarea name="content1" id="content1" rows="19" style="width:645px;"></textarea>
			<script type="text/javascript">
				 var oEditors = [];
				 nhn.husky.EZCreator.createInIFrame({
				 oAppRef: oEditors,
				 elPlaceHolder: "content1",
				 sSkinURI: "se2/SmartEditor2Skin.html",
				 fCreator: "createSEditor2"
				 });
			</script>	
		</td>
	</tr>
	</table>
</form>
<br>
<form name="upFile" method="post" enctype="multipart/form-data" action="memberBasic.do?method=upFile" target="fileUploadFrm" >
	<table class="tbl_type" border="1" cellspacing="0" summary="화일첨부">
	<tr>
		<td class="mtbl">파일첨부</td>
		<td class="mtbl1" colspan="4">
			<input type="file" name="attatch" size="65" id="attatch" onchange="javascript:upFiles();"/><br><br>
			1. 파일은 한 개만 첨부 가능합니다.<br>
			2. 첨부 가능한 파일의 용량은 10MB입니다.<br>
		</td> 
	</tr>
</table>
<p align="right">
<input type="button" name="send" value="발송"  onclick="sendMail();"> 
<input type="button" name="cancel" value="취소" onclick="self.close();">
</p> 					
</form>
</body>
</html>
<iframe name="fileUploadFrm" width=0 height=0 tabindex=-1></iframe>


