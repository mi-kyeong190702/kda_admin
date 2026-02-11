<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String sout	= "asdf메뉴출력";//출력	
	String menu	= "";//대메뉴
	String sub	= "";//소메뉴
	
	if( request.getAttribute("menu") != null){
		menu = (request.getAttribute("menu")).toString();
		sout += "    munu(대메뉴) = "+menu;
	}
	
	if( request.getAttribute("sub") != null){
		sub = (request.getAttribute("sub")).toString();
		sout += "    sub(소메뉴) = "+sub;
	}
	
	//System.out.println(sout);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
  <link rel="stylesheet" type="text/css" href="css/import.css" /> 
 <title>대한영양사협회</title>
</head>

<body>	
    <div id="side_1">
	  <ul>
	    <li><img src="images/snb_menu6.gif" alt="영양사 실태신고" /></li>	
		<% if("actulmanage".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="actul.do?method=actulmanageData">신고관리</a></li>
		<% if("actulstatus".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="actul.do?method=actulstatusData">신고현황</a></li>		
		<% if("actulrecipient".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="actul.do?method=actulrecipientData">보수교육 미대상자</a></li>		
		<% if("actullicense".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="actul.do?method=actullicenseData">면허정보 등록자</a></li>		
		<li><img src="images/snb_menu_bg.gif" alt="이미지" /></li>		
	  </ul>
	</div>
</body>
</html>