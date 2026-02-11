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

	<!-- 
	수정일 : 2012.11.06
	수정자 : 김성훈
	내  용 : 겸직별
	 -->




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
				powerinit("127", eval(<%=userpowerm1%>) );

				
				jQuery("#list").jqGrid({
					url: "memberState.do?method=multyStateList",
					datatype: "json",
					mtype: 'POST',
					height:'500',
					width:'1100',
					colNames: [ '대분류','대분류','소분류','소분류',
					            '겸직','비겸직',
					            '합계'],
					colModel: [
			{name:'code_great'	,index:'code_great asc, invdate', align:"center", width:150, sortable:false, hidden:true},
			{name:'h_code_great',index:'h_code_great'	, width:100, align:"center", sortable:false},
			{name:'code_small'	,index:'code_small'		, width:100, align:"right", sortable:false, hidden:true},
			{name:'h_code_small',index:'h_code_small'	, width:100, align:"center", sortable:false},
			{name:'col1'		,index:'col1'			, width:100, align:"right", sortable:false, formatter:'integer'},
			{name:'col2'		,index:'col2'			, width:100, align:"right", sortable:false, formatter:'integer'},
			{name:'totcount'	,index:'totcount'		, width:100, align:"right", sortable:false, formatter:'integer'}
					],
					rowNum:10000000,
					//rowList:[10,20,30],
					sortname: 'id',
					sortorder: 'desc',
					pager: '#pager2',
					/* scroll:true, */
					viewrecords: true,
					caption: 'kda_grid',
					altRows:true,
					altclass:'myAltRowClass',
					rownumbers : true,
						caption:"겸직별"			      
				});
			jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
			});
			
			$(function() {
				$( "#datepicker" ).datepicker();
			});
			$(function() {
				$( "#datepicker1" ).datepicker();
			});
		</script>
		
		<script>
			
			function juminFormat(cellvalue, options, rowObject ){
				var jumin=cellvalue.substring(0,6)+" - *******";
				return jumin;
			}
		
			function goSearch(form){

				var startdate   = form.startdate.value;
				var enddate		= form.enddate.value;
				
				if(startdate > enddate){
					alert("시작일이 종료일 보다 커서는 안됩니다.");
					return;
				}

				$('#list').jqGrid('clearGridData');
				jQuery("#list").setGridParam({url:"memberState.do?method=multyStateList&startdate="+startdate+"&enddate="+enddate}).trigger("reloadGrid"); 

				//아래는 화면 전체를 리로드해버린다. 위의 것과 같이 사용하자.				
			/* 	form.method='post';
				form.action="memberState.do?method=companyState";
				form.submit(); */
	
			}

			//엑셀 출력
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
				form.action="memberState.do?method=multyStateExcel"+param;
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
			<li class="NPage">겸직별</li>
		 </ul>	
		<div class="mTabmenu_01"> 


			<div class="mTabmenu_01">
				<form name="memberStateInputForm" method="post">
				
					    <table width=1100px class="mtable_13" cellspacing="0" summary="근무처분류별">
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