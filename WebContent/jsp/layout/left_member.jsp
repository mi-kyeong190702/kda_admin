<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>Insert title here</title>

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


</head>
<body>
	<!-- <div id="container"> -->
    <div id="side_1">
	  <ul>
	  
	<!-- 
	수정일 : 2012.09.13
	수정자 : 김성훈
	내   용 : 현황(Depth1->2) 메뉴 연결
	 --> 
	 			
                
	    <li><img src="images/snb_menu1.gif" alt="회원" /></li>
		<% if("memberInsertList".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="memberInsert.do?method=memberInsert">회원&nbsp;등록</a></li>
		<% if("memberSearchList".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="memberSearch.do?method=memberSearch">조건&nbsp;검색</a></li>
		<% if("memberDuesList".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="memberDues.do?method=memberDues">회비&nbsp;처리</a></li>
		<% if("memberDepositList".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="memberDeposit.do?method=memberDeposit">예수금&nbsp;현황</a></li>
		<% if("memberFundList".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="memberFund.do?method=memberFund">기부&frasl;기금현황</a></li>
			
		<% if("memberState".equals(menu)) { %><li class="menuOn"><% } else { %><li class="aa"><% } %><a href="memberState.do?method=bigState">현황</a></li>
		<% if("memberState".equals(menu)) { %>
			<ul class="bn">
				<% if("bigStateList".equals(sub)||"smallStateList".equals(sub)||"memGubunStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %><a href="memberState.do?method=bigState">지부별회원</a></li>
				
			   <% if("companyStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=companyState">근무처분류별</a></li>
			   		
			   <% if("codeUseMemberList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=codeUseMember">운영형태별</a></li>
			   <% if("subCodePersonList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=subCodePerson">위탁업체개인별</a></li>
			   
			   <% if("trustComStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=trustComState">위탁업체업체별</a></li>
			   <% if("jLevelStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
					<a href="memberState.do?method=jLevelState">고용형태별&#40;직급&#41;</a></li>
			   <% if("jLineStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=jLineState">고용형태별&#40;직렬&#41;</a></li>
			   <% if("certifiStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=certifiState">자격증소지별</a></li>
			   <% if("univerStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=univerState">최종학위별</a></li>
			   <% if("cookStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=cookState">공동조리별</a></li>
			   <% if("conStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=conState">공동관리별</a></li>
			   <% if("subCodeStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=subCodeState">산하단체소속별</a></li>
			   <% if("mealStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			  		 <a href="memberState.do?method=mealState">피급식자별</a></li>
			   <% if("yearPayStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=yearPayState">영양사연봉별</a></li>
			   <% if("licPayStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=licPayState">영양사면허수당별</a></li>
			   <% if("companyMealStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			  		 <a href="memberState.do?method=companyMealState">1식재료비별</a></li>
			   <% if("multyStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			 		  <a href="memberState.do?method=multyState">겸직별</a></li>
			   <% if("mealNumberStateList".equals(sub)) { %><li class="a_menuOn"><% } else { %><li><% } %>
			   		<a href="memberState.do?method=mealNumberState">급식인원별</a></li>
			</ul>
		<% } %>

		<li><img src="images/snb_menu_bg.gif" alt="이미지" /></li>	
	  </ul>
	</div>
	<!-- </div> -->
</body>
</html>