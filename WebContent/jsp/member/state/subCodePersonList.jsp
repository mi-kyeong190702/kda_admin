<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>



<%@ page import="net.sf.json.JSONArray"%>
<!-- 김성훈 . 공통함수 -->
<script src="js/form.js" type="text/javascript" ></script>

<%
	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

String Date = new java.text.SimpleDateFormat ("yyyyMMdd").format(new java.util.Date());
String Time = new java.text.SimpleDateFormat ("HHmmss").format(new java.util.Date());
Calendar calendar = Calendar.getInstance();
java.util.Date date = calendar.getTime();
String startdate=(new SimpleDateFormat("yyyyMM").format(date))+"01";
String enddate=(new SimpleDateFormat("yyyyMMdd").format(date));

%>

<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

<script type="text/javascript">

$(document).ready(function(){	
	
	//전체 권한 여부
	powerinit("114", eval(<%=userpowerm1%>) );


jQuery("#list").jqGrid({	  
	  url: 'memberState.do?method=subCodePerson',
	  datatype: "json",
      mtype: 'post',
      height:'500',
      width:'1100',
      colNames: [ '소속위탁업체', '인원'],
      colModel: [
			{name:'trust_name',index:'trust_name', width:120, editable: false, align: 'center'},
   			{name:'pers_count', index:'pers_count',  width: 100,editable: false, align: 'center', formatter:'integer'}
   			    ],
				rowNum:10000000,
				//rowList:[10,20,30],
   			pager: '#pager2',
            rownumbers: true,
 			viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '위탁업체 개인별',
			altclass:'myAltRowClass',
 				
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function goSearch(form){

	var startdate   = form.startdate.value;
	var enddate		= form.enddate.value;
	if(startdate > enddate){
		alert("시작일이 종료일 보다 커서는 안됩니다.");
		return;
	}
	
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"memberState.do?method=subCodePersonList&startdate="+startdate+"&enddate="+enddate}).trigger("reloadGrid");
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});



function excelDown(form) {
	
	var rownum = jQuery("#list").jqGrid('getGridParam','records')
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}

	var startdate  = form.startdate.value;
	var enddate		= form.enddate.value;
	if(startdate > enddate){
		alert("시작일이 종료일 보다 커서는 안됩니다.");
		return;
	}
	var param="";
	param+="&startdate="+startdate;
	param+="&enddate="+enddate;
	param+="&register="+"<%=session.getAttribute("G_ID").toString()%>";

	form.target="frm";
	form.action="memberState.do?method=subCodePersonExcel"+param;
	form.submit();
	
	//jQuery("#list").setGridParam({url:"memberState.do?method=codeUseMemberExcel"+param}).trigger("reloadGrid"); 

	
}

</script>
</head>

<body>
	<div id="contents">
		<ul class="home">
		    <li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
			<li><a href="memberForm.do">회원</a></li>				<li>&gt;</li>
			<li><a href="memberStateForm.do">현황</a></li>			<li>&gt;</li>
			<li class="NPage">위탁업체 개인별</li>
		 </ul>	
		<div class="mTabmenu_01"> 


			<div class="mTabmenu_01">
				<form name="memberStateInputForm" method="post">
				
					    <table width=1100px class="mtable_13" cellspacing="0" summary="위탁업체 개인별">
							<tr align="left">
								<!-- 기간 입력창 -->
								<td class="a01" width=10%>조회기간</td>
								<td><input type="text" name="startdate" id="datepicker" readonly value='<%=startdate %>'>&nbsp;~&nbsp;<input type="text" name="enddate" id="datepicker1" readonly value='<%=enddate %>'></td>

								<!-- 조회 버튼 -->
								<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(memberStateInputForm);" style="cursor: hand" alt="조회" align="right"/></td>
							</tr>
						</table>
				
				</form>
				<br>
				<!-- 그리드 리스트 --> 		
				<table id="list"></table>
				<!-- 그리드 상태바 -->
				<div id="pager2"></div>
				
				<form name="memberStateListForm" method="post">	
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberStateInputForm);" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>


			</div>
		</div>
	</div>
</body>
</html>

<iframe name="frm" width="0" height="0" tabindex=-1></iframe>