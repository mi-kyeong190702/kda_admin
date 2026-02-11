<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
  <title>대한영양사협회</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <link rel="stylesheet" type="text/css" href="css/report.css" />
<script type="text/javascript">

<%

String oper_name1          = "";	
String oper_no1       = "";
String code_kind1          = "";

	 if( request.getAttribute("oper_name")!=null){
		 oper_name1         =  (String)request.getAttribute("oper_name");}
	 if( request.getAttribute("oper_no")!=null){
		 oper_no1      =  (String)request.getAttribute("oper_no");}
 	 if( request.getAttribute("code_kind")!=null){
		 code_kind1 =  (String)request.getAttribute("code_kind");} 
	 
%>

function excelDown(){
	var param               = "";
	
	
	
	
	if(sForm.t1.checked == true){
		check = "1";
	}
	if(sForm.t2.checked == true){
		check = "2";
	}
	if(sForm.t3.checked == true){
		check = "3";
	}
	
	if( '<%=oper_name1%>'!=""){		
		oper_name         =  '<%=oper_name1%>';
	}
	if( '<%=oper_no1%>'!=""){		
		oper_no      =  '<%=oper_no1%>';
	}
	if( '<%=code_kind1%>'!=""){
		code_kind =  '<%=code_kind1%>';
	}
	 
	 
	  if(check                       !="")param+="&check="            + check;
//	  alert("check="+check);
		
	//document.sForm.target="frm";
	//document.sForm.action="education.do?method=edutakeListDown&parm"+parm;
	
	close();
	//document.sForm.submit();
}

</script>
 </head>
 <body>
    
   <div id="contents">
   <form id="list1" name="sForm" method="post">
     <table class="mTitle" cellspacing="0" summary="주민번호 검색방법">        			
       <tr>           
         <td><img src="images/logo.gif" alt="로고" /></td>
       </tr>
     </table><br>
     <table  border="1" cellspacing="0" summary="내용">
         <tr>
           <td> < 주민 번호  표시 ></td>
         </tr>
          <tr>           
           <td> &nbsp;&nbsp;&nbsp;1. 13자리 숫자표시   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (ex.&nbsp; 123456-1234567) &nbsp;&nbsp;&nbsp; <input type="checkbox" name="t1" id="t1" style="width:24px"/></td>           		   
         </tr>                   
         <tr> 
           <td> &nbsp;&nbsp;&nbsp;2. 7자리 숫자 6자리 *   &nbsp;&nbsp;&nbsp; (ex.&nbsp; 123456-1******) &nbsp;&nbsp;&nbsp; <input type="checkbox" name="t2" id="t2" style="width:20px"/>&nbsp;&nbsp;</td>              
         </tr>
		 <tr>
		   <td> &nbsp;&nbsp;&nbsp;3. 6자리 숫자 7자리 *   &nbsp;&nbsp;&nbsp; (ex.&nbsp; 123456-*******) &nbsp;&nbsp;&nbsp; <input type="checkbox" name="t3" id="t3" style="width:20px"/>&nbsp;&nbsp;</td>                         
         </tr>        
     </table><br>
    
     <ul class="btnIcon_01">
         <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" alt="엑셀저장" /></a></li>
   	 </ul>	  
   </div><!--r_contents End-->
 </form>
 </body> 
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>