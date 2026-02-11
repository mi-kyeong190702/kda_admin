<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>Insert title here</title>
<%

String menu = "";
if( request.getAttribute("menu") != null)
	menu = (request.getAttribute("menu")).toString();
System.out.println("asdfasdf menu = "+menu);
%>
</head>
<body>
	<!-- <div id="container"> -->
    <div id="side_1">
	  <ul>
	  
	<!-- 
	수정일 : 2012.09.13
	수정자 : 김성훈
	내   용 : 현황(Depth2->3), 근무처분류별(3->조회) 메뉴 연결
	 --> 
	    <li><img src="images/snb_menu1.gif" alt="회원" /></li>	
		<li class="aa"><a href="#">회원등록</a></li>
		<li class="aa"><a href="#">조건&nbsp;검색</a></li>
		<li class="aa"><a href="#">예수금&nbsp;현황</a></li>
		<li class="aa"><a href="#">기부&frasl;기금현황</a></li>		
		<li class="menuOn"><a href="memberStateForm.do">현황</a></li>
		  <ul class="bn">
		  
			   
			   <% if("bigStateList".equals(menu)||"smallStateList".equals(menu)||"memGubunStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=bigState">지부별회원</a></li>
			   <% if("companyStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=companyState">근무처분류별</a></li>
			   <li><a href="member.do?method=memberstatusData">운영형태별</a></li>
			   <li><a href="#">위탁업체개인별</a></li>
			   <% if("trustComStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=trustComState">위탁업체업체별</a></li>
			   <% if("jLevelStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
					<a href="memberState.do?method=jLevelState">고용형태별&#40;직급&#41;</a></li>
			   <% if("jLineStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=jLineState">고용형태별&#40;직렬&#41;</a></li>
			   <li><a href="#">자격증소지별</a></li>
			   <% if("univerStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=univerState">최종학위별</a></li>
			   <% if("cookStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=cookState">공동조리별</a></li>
			   <% if("conStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=conState">공동관리별</a></li>
			   <% if("subCodeStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=subCodeState">산하단체소속별</a></li>
			   <% if("mealStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			  		 <a href="memberState.do?method=mealState">피급식자별</a></li>
			   <% if("yearPayStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=yearPayState">영양사연봉별</a></li>
			   <% if("licPayStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=licPayState">영양사면허수당별</a></li>
			   <% if("companyMealStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			  		 <a href="memberState.do?method=companyMealState">1식재료비별</a></li>
			   <% if("multyStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			 		  <a href="memberState.do?method=multyState">겸직별</a></li>
			   <% if("mealAllStateList".equals(menu)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=mealAllState">급식인원별</a></li>
			 </ul> 
		<li><img src="images/snb_menu_bg.gif" alt="이미지" /></li>	
	  </ul>
	</div>
	<!-- </div> -->
</body>
</html>