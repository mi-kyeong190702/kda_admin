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
String pers_name= "";//검색용 회원 이름(조건검색,기부/기금 에서 필요함)
String company_name=""; //근무처명 기부/기금에서 필요
if( request.getAttribute("param"	)!=null) param		= (String)request.getAttribute("param");
if( request.getAttribute("url"		)!=null) from		= (String)request.getAttribute("url");
if( request.getAttribute("pers_name")!=null) pers_name 	= (String)request.getAttribute("pers_name");
if( request.getAttribute("company_name")!=null) company_name 	= (String)request.getAttribute("company_name");

String edutest_name1	= "";	//교육의 내용
String oper_name1		= "";	//교육의 이름
String oper_comp_name11	= "";	//교육의 근무처명

//교육에 사용하는것
if(request.getAttribute("edutest_name1"		)!= null )	edutest_name1 	= request.getAttribute("edutest_name1"		)+"";
if(request.getAttribute("oper_name1"		)!= null )	oper_name1 		= request.getAttribute("oper_name1"			)+"";
if(request.getAttribute("oper_comp_name11"	)!= null )	oper_comp_name11= request.getAttribute("oper_comp_name11"	)+"";

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
	//alert(param);
	
// JUMIN_DEL
<%-- 	if("<%=from%>" != "memberDues") { --%>
// 		if(form.persno_type.a.checked== true )		param+="&check=1";//
// 		else if(form.persno_type.b.checked== true )param+="&check=2";//
// 		else if(form.persno_type.c.checked== true )param+="&check=3";//
// 	}
	

	//조건검색 일때는 이름이 필요하므로 아래코드 사용
	if( "<%=pers_name%>".length > 0)	param+="&pers_name="+escape(encodeURIComponent("<%=pers_name%>"));
	if( "<%=company_name%>".length > 0)	param+="&company_name="+escape(encodeURIComponent("<%=company_name%>"));
	//교육에 사용하는것
	if( "<%=edutest_name1%>".length		> 0)	param+="&edutest_name1="	+escape(encodeURIComponent("<%=edutest_name1%>"		));
	if( "<%=oper_name1%>".length		> 0)	param+="&oper_name1="		+escape(encodeURIComponent("<%=oper_name1%>"		));
	if( "<%=oper_comp_name11%>".length	> 0)	param+="&oper_comp_name11="	+escape(encodeURIComponent("<%=oper_comp_name11%>"	));
	
	var from = "<%=from%>";
	
//alert(param+"  "+from);
	
	document.sForm.target="frm";
	if( from == "memberSearch"	 )document.sForm.action="memberSearch.do?method=memberSearchExcel"+param;
	if( from == "memberDues"  	 )document.sForm.action="memberDues.do?method=memberDuesExcel"+param;
	if( from == "memberFund"  	 )document.sForm.action="memberFund.do?method=memberFundExcel"+param;	
	if( from == "memberDeposit"  )document.sForm.action="memberDeposit.do?method=memberDepositExcel"+param;
	if( from == "education"		 )document.sForm.action="education.do?method=edutakeListDown"+param;
	if( from == "education2"	 )document.sForm.action="education.do?method=examSendListDown"+param;
	if( from == "license"		 )document.sForm.action="license.do?method=conditionListDown"+param;
 	if( from == "license2"		 )document.sForm.action="license.do?method=resultregistrationListDown"+param; 
	if( from == "license3"		 )document.sForm.action="license.do?method=inspectionListDown"+param; 
	if( from == "license4"		 )document.sForm.action="license.do?method=licenseissueListDown"+param;
	if( from == "license5"		 )document.sForm.action="license.do?method=licenserenewalListDown"+param;
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
<!-- 	JUMIN_DEL -->
<%-- 	<% if(!"memberDues".equals(from)) { %> --%>
<!-- 	<table  border="1" cellspacing="0" summary=" "> -->
<!-- 		<tr><td></td></tr> -->
<!-- 		<tr><td><input type="radio" name="persno_type" id="a" value="1"  checked=true /><label for="1">123456-1234567 주민등록번호 전체를 출력합니다.</label></td></tr> -->
<!-- 		<tr><td><input type="radio" name="persno_type" id="b" value="2" /><label for="2">123456-1****** 뒤의 첫째자리 까지 출력합니다.</label></td></tr> -->
<!-- 		<tr><td><input type="radio" name="persno_type" id="c" value="2" /><label for="3">123456-******* 앞의 일곱자리만 출력합니다.</label></td></tr> -->
<!-- 	</table> -->
<%-- 	<% } %> --%>
 
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