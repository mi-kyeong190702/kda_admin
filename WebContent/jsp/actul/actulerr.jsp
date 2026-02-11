<%@page import="org.apache.struts.tiles.taglib.GetAttributeTag"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>영양사 실태 신고</title>
<link rel="stylesheet" type="text/css" href="css/report.css" />
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ant.document.dao.documentDao"%>

<%
	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);
	
	String yy           = "";
	String name         = "";
	String lic_no       = "";
		
	yy      =  request.getAttribute("yyyy").toString();
	name    =  request.getAttribute("ar_name").toString();
	lic_no  =  request.getAttribute("ar_lic_no").toString();		

%> 

<script type="text/javascript">

function goconfirm(form,intPage){	
	var yy              = "";
	var name            = "";	
	var lic_no          = ""; 
	
	 if (!sForm.check.checked) {
		 alert("개인 정보 활용동의 해주세요 !") ;
	     sForm.check.focus();
	     return ;
	    }   
	
	 if( '<%=yy%>'!=""){
		 yy =  '<%=yy%>';
    }
	if( '<%=name%>'!=""){		
		name   = escape(encodeURIComponent('<%=name%>'));	
	}
	if( '<%=lic_no%>'!=""){
		lic_no     =  '<%=lic_no%>';
	}
	
	document.sForm.action = "actul.do?method=actulinsert&yy="+yy+"&name="+name+"&lic_no="+lic_no;
	document.sForm.submit(); 	
}

function goclose(){	
	document.sForm.action = "actul.do?method=actulconfirm";
	document.sForm.submit();
}

</script>
 </head> 
 
 <body> 
 <form id="list" name="sForm" method="post">    
   <div id="r_contents2">
     <table class="bTitle" border="1" cellspacing="0" summary="영양사실태신고">        			
       <tr>           
         <td class="sTitle">영양사 실태 신고</td>              
	   </tr> 			
     </table>
     <table class="r_box1" border="2" cellspacing="0" >        			
       <tr>           
         <td class="r_font"  height="200" align="center" bgcolor="#5198b0"><h4>신고 내용을 찾지 못하였습니다.</h4> 
                           <h4>영양사 실태 신고를 작성하시겠습니까?</h4><br>         
		 </td>	                
	   </tr> 			
     </table> 
     <table class="r_box1" border="2" cellspacing="0" >        			
       <tr>           
         <td class="r_font1" align="center" bgcolor="#5198b0"><br>법령의 규정에 따라 수집/보유 및 처리하는 개인정보를 공공업무의 적절한 <br>
                             수행과 개인의 권익을 보호하기 위해 적법하고 적정하게 취급할 것입니다.<br><br>                                          		
         <input type="checkbox" name="check" id="t" align="center"/>개인정보 활용동의 <br><br><br>
         <input type="button" id="name" size="12"  onclick="javascript:goconfirm();" value="예" />
         <input type="button" id="name" size="12"  onclick="javascript:goclose();" value="아니요" />       
		</td>              
	   </tr> 			
     </table> 
	
   </div><!--r_contents End-->
 </form>
 </body> 
</html>
