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

String error_code_pers = request.getAttribute("error_code_pers").toString();
String[] c_p = error_code_pers.split(",");
String code_pers = "";
for( int i=0; i<c_p.length; i++) code_pers+= c_p[i] + "\\r\\n<br/>";

String success_cnt = request.getAttribute("success_cnt").toString();
String faile_cnt = request.getAttribute("faile_cnt").toString();

%>

<script>

</script>

</head>

<body>


<form name="memberInputForm" method="post" action="" >
	<div id="T_contents" >
		<div class="mSearch_01" >

  
     <table class="mTitle" cellspacing="0" summary=" ">        			
       <tr>           
         <td><img src="images/logo.gif" alt=" " /></td>
       </tr>
     </table><br>
     
        <table class="tbl_type" border="1" cellspacing="0" summary="DM 발송" style="width:300px">
          <caption>면허번호 검색</caption>          
			<tbody>
			
			
            <tr>           
              <td class="mtbl" style="width:100px;background:#F5FAFA">DM 발송 결과</td>
              <td class="mtbl1"style="width:200px"> <input type="button" name="btn" value="확인" onclick="javascript:self.close();" style="left:150px;" /> </td>
            </tr>
            
            <tr>
            	<td class="mtbl" style="width:100px;background:#F5FAFA">발송 성공</td>
            	<td><%=success_cnt %> 건</td>
            </tr>
            
            <tr>
            	<td class="mtbl" style="width:100px;background:#F5FAFA">발송 실패</td>
            	<td><%=faile_cnt %> 건</td>
            </tr>
            
            <tr>
            	<td class="mtbl" style="width:100px;background:#F5FAFA" colspan="2">실패 내역</td>
            </tr>
            <tr>
            	<td style="width:100px; height:200px;" colspan="2">
            		<input type=text value=" <%=code_pers %>"  style="width:99%; height:200px;" TextMode="MultiLine"  TextWrapping = "Wrap" AcceptsReturn = "True" />
            	</td>
            </tr>
            
           
             
           
            
            </tbody>
        </table>
        
		</div>
	</div>
</form>
 
 
</body>

</html>


