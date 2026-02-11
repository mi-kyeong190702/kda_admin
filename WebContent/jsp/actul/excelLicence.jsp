<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <title></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link rel="stylesheet" type="text/css" href="css/report.css" />
<script type="text/javascript">

<%

String param = "";
String from= "";
String pers_name1= "";
String check = "";
String code_seq= "";
String memo= "";

if( request.getAttribute("param"	)!=null) param		= (String)request.getAttribute("param");
if( request.getAttribute("url"		)!=null) from		= (String)request.getAttribute("url");
if( request.getAttribute("pers_name1")!=null) pers_name1 	= (String)request.getAttribute("pers_name1");
if( request.getAttribute("check")!=null) check 	= (String)request.getAttribute("check");
if( request.getAttribute("code_seq")!=null) code_seq 	= (String)request.getAttribute("code_seq");
if( request.getAttribute("memo")!=null) memo 	= (String)request.getAttribute("memo");

int ncount = ((Integer)(request.getAttribute("ncount"))).intValue();

String g_name = session.getAttribute("G_ID").toString();

%>

function excelDown(form, count){
	var i=count;
	var nstart = 0;
	var nend = 0;
	
	nstart=i*10000;
	nend=(i+1)*10000;
	
	var param='';
	param+="&btnCnt="+i;
	param+="&nstart="+nstart;
	param+="&nend="+nend;
	param+= "<%=param%>";
	param+= "&register="+"<%=g_name%>";
	param+= "&check="+"<%=check%>";
	param+= "&code_seq="+"<%=code_seq%>";

	//조건검색 일때는 이름이 필요하므로 아래코드 사용
	if( "<%=pers_name1%>".length > 0)	param+="&pers_name1="+escape(encodeURIComponent("<%=pers_name1%>"));
	if( "<%=memo%>".length > 0)			param+="&memo="+escape(encodeURIComponent("<%=memo%>"));
	
	var from = "<%=from%>";
	
	document.sForm.target="frm";
	if( from == "excel_licence"		 )document.sForm.action="actul.do?method=excelDown_licence"+param;
	if( from == "excel_recipient"		 )document.sForm.action="actul.do?method=excelDown_recipient"+param;
	if( from == "excel_status"		 )document.sForm.action="actul.do?method=excelDown_status"+param;
	if( from == "excel_manage"		 )document.sForm.action="actul.do?method=excelDown_manage"+param;
	document.sForm.submit();

}

</script>
 </head>
 <body>
    
<div id="contents">
<form id="list1" name="sForm" method="post">

	<table class="mTitle" cellspacing="0" summary=" " >
		<tr><td><img src="images/logo.gif" alt=" " /></td></tr>
	</table><br>
 
	<tr><br><td style="background-color:#d4d4d4;"><span style="color:red">* 10000건씩 잘라 저장합니다.</span></td></tr>
		

	<table  border="1" cellspacing="0" summary=" " style="width:290px">

		<tbody>
		<%	int i=0;			
			for(i=0; i<ncount/10000; i++) { %>
			<tr>           
				<td class="mtbl" style="width:180px;background:#F5FAFA"><%=i+1%>&nbsp;:&nbsp;<%=i*10000+1 %> ~ <%=(i+1)*10000 %> 건 출력</td>
				<td class="mtbl1"style="width:110px"><input align=center name="bnt" type="button" id="btn<%=i%>" value="엑셀저장" onclick="javascript:excelDown(list1, <%=i%>);"/></td>
			</tr>
		<%}	if( ncount%10000 >0) { %>
			<tr>           
				<td class="mtbl" style="width:180px;background:#F5FAFA"><%=i+1%>&nbsp;:&nbsp;<%=(i-1)*10000+1+10000 %> ~ <%=(i)*10000+(ncount%10000) %> 건 출력</td>
				<td class="mtbl1"style="width:110px"><input align=center name="bnt" type="button" id="btn<%=i%>" value="엑셀저장" onclick="javascript:excelDown(list1, <%=i%>);"/></td>
			</tr>
		<% } %>
		</tbody>
	</table>

</form>
</div><!--r_contents End-->
</body> 
</html>
<iframe name="frm" width="0" height="0" tabindex=-1 style="display:none;"></iframe>