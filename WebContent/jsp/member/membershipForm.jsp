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
int ncount = ((Integer)(request.getAttribute("ncount"))).intValue();

String param = "";

if(request.getParameter("regi_dt"		)!=null) param+= "&regi_dt="		+request.getParameter("regi_dt"		);
if(request.getParameter("dues_gubun"	)!=null) param+= "&dues_gubun="		+request.getParameter("dues_gubun"	);
if(request.getParameter("code_member"	)!=null) param+= "&code_member="	+request.getParameter("code_member"	);
if(request.getParameter("code_bran"		)!=null) param+= "&code_bran="		+request.getParameter("code_bran"	);
if(request.getParameter("code_sub"		)!=null) param+= "&code_sub="		+request.getParameter("code_sub"	);
if(request.getParameter("incom_flag"	)!=null) param+= "&incom_flag="		+request.getParameter("incom_flag"	);
if(request.getParameter("conform_yn"	)!=null) param+= "&conform_yn="		+request.getParameter("conform_yn"	);
if(request.getParameter("code_receipt"	)!=null) param+= "&code_receipt="	+request.getParameter("code_receipt");


%>

<script>
function fn_membership(form, count) {

	var i=count;
	var nstart = 0;
	var nend = 0;
	
	nstart=i*100;
	nend=(i+1)*100-1;
	
	var param='';
	param+="&btnCnt="+i;
	param+="&nstart="+nstart;
	param+="&nend="+nend;
	param+="<%=param%>";
	
	var status ="scrollbars=yes, toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, left=100, top=100";
	var url="memberDues.do?method=membershipLink"+param;
	window.open(url,"membershipLink", status);
}

function fn_close(form) {
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
     
        <table class="tbl_type" border="1" cellspacing="0" summary="회원증 출력" style="width:260px">
          <caption>회원증 출력</caption>          
			<tbody>
            <tr>           
              <td class="mtbl" style="width:165px;background:#F5FAFA">출력 대상 개수</td>
              <td class="mtbl1"style="width:165px">
              	<%=ncount%>
              </td>
            </tr>                  
            </tbody>
          </table>
            
		<tr>
			<br>
		
			<td style="background-color:#d4d4d4;">
				<span style="color:red"> * 회원증 출력은 100건씩 진행됩니다.</span>
			</td>
		</tr> 
		<br>
		
		<table class="tbl_type" border="1" cellspacing="0"  style="width:260px">          
			<tbody>
			<% int i=0;
			
			for(i=0; i<ncount/100; i++) { %>
            <tr>           
              <td class="mtbl" style="width:180px;background:#F5FAFA"><%=i+1%>&nbsp;:&nbsp;<%=i*100+1 %> ~ <%=(i+1)*100 %> 건 출력</td>
              <td class="mtbl1"style="width:55px">
              	<input align=center name="bnt" type="button" id="btn<%=i%>" value="출력" onclick="javascript:fn_membership(memberInputForm, <%=i%>);"/>
              </td>
            </tr>
            <%} if( ncount%100 >0) { %>
            <tr>           
              <td class="mtbl" style="width:180px;background:#F5FAFA"><%=i+1%>&nbsp;:&nbsp;<%=(i-1)*100+1+100 %> ~ <%=(i)*100+(ncount%100) %> 건 출력</td>
              <td class="mtbl1"style="width:55px">
              	<input align=center name="bnt" type="button" id="btn<%=i%>" value="출력" onclick="javascript:fn_membership(memberInputForm, <%=i%>);"/>
              </td>
            </tr>
            <% } %>
            </tbody>
          </table>
               <p>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="bnt" type="button" value="종료" onclick="javascript:fn_close(memberInputForm);"/>

   
	  </div><!--mSearch_01 End-->
	</div><!--M_contents End-->
 </form>
 
</body>




