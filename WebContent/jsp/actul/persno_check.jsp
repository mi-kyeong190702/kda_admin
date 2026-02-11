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

String yyyy1            = "";	
String ar_name1         = "";
String ar_add11         = "";
String ar_code_part1    = "";
String ar_code_big11    = "";
String ar_state1        = "";

	 if( request.getAttribute("yyyy1")!=null){
		 yyyy1         =  (String)request.getAttribute("yyyy1");}
	 if( request.getAttribute("ar_add11")!=null){
		 ar_add11      =  (String)request.getAttribute("ar_add11");}
	 if( request.getAttribute("ar_code_part1")!=null){
		 ar_code_part1 =  (String)request.getAttribute("ar_code_part1");}
	 if( request.getAttribute("ar_code_big11")!=null){
		 ar_code_big11 =  (String)request.getAttribute("ar_code_big11");}
	 if( request.getAttribute("ar_state1")!=null){
		 ar_state1     =  (String)request.getAttribute("ar_state1");}

%>

function excelDown(){
	var parm             = "";
	var yyyy1            = "";	
	var ar_add11         = "";
	var ar_code_part1    = "";
	var ar_code_big11    = "";
	var ar_state1        = "";
	var check = "";
	
	if((sForm.t1.checked == false)&&(sForm.t2.checked == false)&&(sForm.t3.checked == false)){
		 alert("check box를 선택하세요.") ;	       
	     return ;  
	}
	
	if(sForm.t1.checked == true){
		check = "1";
	}
	if(sForm.t2.checked == true){
		check = "2";
	}
	if(sForm.t3.checked == true){
		check = "3";
	}
	
	if( '<%=yyyy1%>'!=""){		
		 yyyy1         =  '<%=yyyy1%>';
	}
	if( '<%=ar_add11%>'!=""){		
		 ar_add11      =  '<%=ar_add11%>';
	}
	if( '<%=ar_code_part1%>'!=""){
		 ar_code_part1 =  '<%=ar_code_part1%>';
    }
	if( '<%=ar_code_big11%>'!=""){
		 ar_code_big11 =  '<%=ar_code_big11%>';
	}
	if( '<%=ar_state1%>'!=""){
		 ar_state1     =  '<%=ar_state1%>';
	}
	 
	if(yyyy1                  !="")parm+="&yyyy1="          + yyyy1;  
	if(ar_add11               !="")parm+="&ar_add11="       + ar_add11; 
	if(ar_code_part1          !="")parm+="&ar_code_part1="  + ar_code_part1;
	if(ar_code_big11          !="")parm+="&ar_code_big11="  + ar_code_big11; 
	if(ar_state1              !="")parm+="&ar_state1="      + ar_state1;
	if(check                  !="")parm+="&check="          + check;
		
	document.sForm.target="frm";
	document.sForm.action="actul.do?method=actulsearchDown&parm"+parm;
	document.sForm.submit();
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