<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
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
String count = request.getAttribute("count").toString();
String lic_no = request.getAttribute("lic_no").toString();

%>

<script>
function checkLicNo(form) {
	if(form.lic_no.value == "") {
		alert("라이센스 번호를 입력해 주십시오.");
		return;
	}
	form.action = "memberInsert.do?method=sLicnoCnt&lic_no="+form.lic_no.value;
	form.submit();

}

function fn_close(form) {
	window.close();
}

function fn_ok(form) {
	opener.window.document.comp.isCkLicNo.value="Y";
	opener.window.document.comp.lic_no.value="<%=lic_no%>";
	window.close();
}
</script>

<head></head>

<body>


<form name="memberInputForm" method="post" action="">
	<div id="T_contents">
	  <div class="mSearch_01">
	  
     <table class="mTitle" cellspacing="0" summary=" ">        			
       <tr>
         <td><img src="images/logo.gif" alt=" " /></td>
       </tr>
     </table><br>
     
        <table class="tbl_type" border="1" cellspacing="0" summary="면허번호 검색" style="width:400px">
          <caption>면허번호 검색</caption>          
			<tbody>
            <tr>           
              <td class="mtbl" style="width:100px;background:#F5FAFA">면허번호</td>
              <td class="mtbl1"style="width:140px">
              	<input type="text" name="lic_no" onkeyup="javascript:check_Int('memberInputForm','lic_no');" size="8" maxlength="6" value="<%=lic_no %>" />&nbsp;
              	<input type="button" name="btn" value="검색" onclick="javascript:checkLicNo(memberInputForm);" style="left:150px;">&nbsp;&nbsp;
              </td>
            </tr>                  
            </tbody>
          </table>
            
<% if( "0".equals(count) ) { %>
<tr>
            	<td style="background-color:#d4d4d4;">
            	<br/>
            	<strong>* 면허번호 유효성 검사 결과</strong><p>
            	&nbsp;&nbsp;&nbsp;면허번호 '<strong><%=lic_no %></strong>' 는 사용 가능한 면허 번호입니다.<br>
            	&nbsp;&nbsp;&nbsp;확인 버튼을 눌러 주십시요.<br>
            	</td>
            </tr>          
            <br>       
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="bnt" type="button" value="확인" onclick="javascript:fn_ok(memberInputForm);"/>
<input name="bnt" type="button" value="취소" onclick="javascript:fn_close(memberInputForm);"/>
<% } else { %>
<tr>
            	<td style="background-color:#d4d4d4;">
            	<br/>
            	<strong>* 면허번호 유효성 검사 결과</strong><p>
            	&nbsp;&nbsp;&nbsp;면허번호 '<strong><%=lic_no %></strong>' (은)는 사용할 수 없는 면허 번호입니다.<br>
            	&nbsp;&nbsp;&nbsp;면허번호를 확인(중복사용 확인 등)해 주십시요.<br>
            	</td>
            </tr> 
            <br>       
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="bnt" type="button" value="취소" onclick="javascript:fn_close(memberInputForm);"/>
<% } %>
   
	  </div><!--mSearch_01 End-->
	</div><!--M_contents End-->
 </form>
 
 
</body>




