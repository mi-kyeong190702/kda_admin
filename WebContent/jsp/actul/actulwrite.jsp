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
<%@ page import="com.ant.common.properties.AntData"%>

<%
	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);
	
	List<Map> result = (List<Map> )request.getAttribute("result");	
%> 

<script type="text/javascript">

function goconfirm(form,intPage){	
		
	 if (!sForm.check.checked) {
		 alert("개인 정보 활용 동의 해주세요 !. ") ;
	     sForm.check.focus();
	     return ;
	    }   
	
  /*   document.sForm.action = "actul.do?method=actulconfirmSearch";
	document.sForm.submit(); 	 */
}

function goclose(){	
	document.sForm.action = "actul.do?method=actulconfirm";
	document.sForm.submit();
}

function gowrite(){	
	var yyyy     = '<%=result.get(0).get("yyyy") %>';	
	var yyyy_seq = '<%=result.get(0).get("yyyy_seq") %>';
	var doc_code = "0403";
	var doc_seq = yyyy + yyyy_seq; 
	
	window.open("<%=AntData.DOMAIN%>/doc_form/docu_print.jsp?doc_code="+doc_code+"&doc_seq="+doc_seq);	
}

</script>
 </head> 
 
 <body>
 <form id="list" name="sForm" method="post">    
  <div id="r_contents1">
     <table class="bTitle" border="1" cellspacing="0" summary="영양사실태신고">        			
       <tr>           
         <td class="sTitle">영양사실태신고내역</td>              
	   </tr> 			
     </table>
     <div class="rTabmenu_01">	    
        <table class="rtable_3" cellspacing="0" summary="영양사실태신고">
          <caption>영양사실태신고</caption>               			 			 
			 <tr>
			   <td class="nobg" align="center">년 도</td>
			   <td class="nobg3" align="center">활동 여부</td>
               <td class="nobg3" align="center">보수교육 및 신고관련</td>
               <td colspan="2" class="nobg3" align="center">진행 상태</td>              
			 </tr>
			 <% 
			 for (int i=0; result.size()> i; i++){
				 System.out.println("i"+i);
			 %>				 	
			 <tr>
			   <td class="nobg1"><%=result.get(i).get("yyyy") %></td>			  			   			   
               <td><%= result.get(i).get("ar_code_part_name") %></td>
               <td>총&nbsp;&#40;<%= result.get(i).get("ar_finish_point") %>
             	  &#41;&nbsp;시간이수 의무 중&#40;
               <%= result.get(i).get("ar_marks_point") %>&#41;&nbsp;시간이수</td>  
			   <td class="nobg2"><%=result.get(i).get("ar_state_name") %></td>			   
			   <td><a href="#" class="nobg1"><img src="images/icon_s12.gif" onclick="javascript:gowrite();"alt="출력" /></a></td>		   
			 </tr>
			 <%			 
			 }
			 %>			  			 		 	 
			</table>				
	  </div><!--rTabmenu_01 End-->	   
   </div><!--r_contents End-->
 </form>
 </body> 
</html>
