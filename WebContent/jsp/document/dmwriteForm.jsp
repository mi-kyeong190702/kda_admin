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

String param      = "";
String pers_name  = "";
String dm_creater = "";

if(request.getParameter("pers_name"		    )!=null) pers_name  = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
if(request.getParameter("dm_creater"	    )!=null) dm_creater = URLDecoder.decode(request.getParameter("dm_creater"),"UTF-8"); 
if(request.getParameter("code_book_kind"	)!=null) param+= "&code_book_kind="	+request.getParameter("code_book_kind"	);
if(request.getParameter("sendgubun"	        )!=null) param+= "&sendgubun="	    +request.getParameter("sendgubun"	    );
if(request.getParameter("book_start_dt"		)!=null) param+= "&book_start_dt="	+request.getParameter("book_start_dt"	);
if(request.getParameter("book_end_dt"		)!=null) param+= "&book_end_dt="	+request.getParameter("book_end_dt"	    );
if(request.getParameter("rece_yn"	        )!=null) param+= "&rece_yn="		+request.getParameter("rece_yn"	        );
%>

<script>
function fn_dmship(form, count) {

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
	param+="&pers_name=" +escape(encodeURIComponent("<%=pers_name%>"));
	param+="&dm_creater="+escape(encodeURIComponent("<%=dm_creater%>"));
	
	var url="document.do?method=dmwriteLink"+param;
	window.open(url,"dmwriteLink");
}

function fn_close(form) {
	window.close();
}

</script>

<head></head>

<body>

<form name="dmInputForm" method="post" action="">
	<div id="T_contents">
	  <div class="mSearch_01">
	  
     <table class="mTitle" cellspacing="0" summary=" ">        			
       <tr>           
         <td><img src="images/logo.gif" alt=" " /></td>
       </tr>
     </table><br>
     
        <table class="tbl_type" border="1" cellspacing="0" summary="라벨 출력" style="width:260px">
          <caption>라벨 출력</caption>          
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
				<span style="color:red"> *  라벨은 100건씩 진행됩니다.</span>
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
              	<input align=center name="bnt" type="button" id="btn<%=i%>" value="출력" onclick="javascript:fn_dmship(dmInputForm, <%=i%>);"/>
              </td>
            </tr>
            <%} if( ncount%100 >0) { %>
            <tr>           
              <td class="mtbl" style="width:180px;background:#F5FAFA"><%=i+1%>&nbsp;:&nbsp;<%=(i-1)*100+1+100 %> ~ <%=(i)*100+(ncount%100) %> 건 출력</td>
              <td class="mtbl1"style="width:55px">
              	<input align=center name="bnt" type="button" id="btn<%=i%>" value="출력" onclick="javascript:fn_dmship(dmInputForm, <%=i%>);"/>
              </td>
            </tr>
            <% } %>
            </tbody>
          </table>
               <p>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="bnt" type="button" value="종료" onclick="javascript:fn_close(dmInputForm);"/>

   
	  </div><!--mSearch_01 End-->
	</div><!--M_contents End-->
 </form>
 
</body>




