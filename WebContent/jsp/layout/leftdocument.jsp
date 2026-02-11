<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.ant.common.properties.AntData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">

function goprint(){	
	var loginID = "<%=session.getAttribute("G_ID").toString()%>";
	//window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/admin_print.jsp?loginID="+loginID);	

	window.open("http://210.127.55.231:9090/doc_form/admin_print.asp?loginID="+loginID);

	if (loginID = 'gleehee7'|| loginID = 'dongbucni')
	{	window.open("http://210.127.55.231:9090/doc_form/admin_print.asp?loginID="+loginID);
	}
	alert ("해당 메뉴는 점검중입니다.");


	//window.open("<%=AntData.DOMAIN%>/doc_form/admin_print.jsp?loginID="+loginID);		
}

</script>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
  <link rel="stylesheet" type="text/css" href="css/import.css" />  
 <title>대한영양사협회</title>
</head>
<body>
    <div id="side_1">
	  <ul>
	    <li><img src="images/snb_menu4.gif" alt="문서" /></li>	
		<li class="aa"><a href="document.do?method=documentSend">문서발송대장</a></li>
		<li class="aa"><a href="document.do?method=documentAccp">문서접수대장</a></li>	
		<li class="aa"><a href="document.do?method=documentInside">내부결제공문대장</a></li>
		<li class="aa"><a href="document.do?method=documentreport">증서발급현황</a></li>
		<li class="aa"><a href="#" onclick="javascript:goprint();">증서발급</a></li>
		<li class="aa"><a href="document.do?method=dmsendData">DM관리</a></li>		
		<li><img src="images/snb_menu_bg.gif" alt="이미지" /></li>	
	  </ul>
	</div> 
 </body> 
</html>



   