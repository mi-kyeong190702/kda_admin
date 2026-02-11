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
	수정일 : 2012.09.13
	수정자 : 김성훈
	내   용 : 조회 출력 화면
	 -->

<%
String Date = new java.text.SimpleDateFormat ("yyyyMMdd").format(new java.util.Date());
String Time = new java.text.SimpleDateFormat ("HHmmss").format(new java.util.Date());
Calendar calendar = Calendar.getInstance();
java.util.Date date = calendar.getTime();
String startdate=(new SimpleDateFormat("yyyy/MM/").format(date))+"01";
String enddate=(new SimpleDateFormat("yyyy/MM/dd").format(date));

%>

<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

		<script type="text/javascript">
			$(document).ready(function(){
				
				jQuery("#list").jqGrid({
					url: "memberState.do?method=companyMemberStateList",
						datatype: "json",
						mtype: 'POST',
						height:'250',
						width:'1100',
						colNames: [ '대분류','집단 급식소','비집단 급식소','합계'],
						colModel: [
									{name:'detcodename'	,index:'detcodename asc, invdate', align:"center", width:1, sortable:false},
									{name:'col1'		,index:'col1'		, width:1, align:"right", sortable:false, formatter:'integer'},
									{name:'col2'		,index:'col2'		, width:1, align:"right", sortable:false, formatter:'integer'},
									{name:'totcount'	,index:'totcount'	, width:1, align:"right", sortable:false, formatter:'integer'}
						],
						rowNum:10,
						rowList:[10,20,30],
						sortname: 'id',
						sortorder: 'desc',
						pager: '#pager2',
						/* scroll:true, */
						viewrecords: true,
						caption: 'kda_grid',
						altRows:true,
						altclass:'myAltRowClass',
						rownumbers : true,
						caption:"근무처 분류별 현황"			      
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
				jQuery("#list").setGridParam({url:"memberState.do?method=companyMemberStateList&startdate="+startdate+"&enddate="+enddate}).trigger("reloadGrid"); 

				//아래는 화면 전체를 리로드해버린다. 위의 것과 같이 사용하자.				
			/* 	form.method='post';
				form.action="memberState.do?method=companyMemberState";
				form.submit(); */
	
			}
		</script>
	
	</head>



<body>
	<div id="contents">
		<ul class="home">
		    <li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
			<li><a href="memberForm.do">회원</a></li>				<li>&gt;</li>
			<li><a href="memberStateForm.do">현황</a></li>			<li>&gt;</li>
			<li class="NPage">근무처분류별</li>
		 </ul>	
		<div class="mTabmenu_01"> 


			<div class="mTabmenu_01">
				<form name="companyMemberStateInputForm" method="post">
				
					    <table width=1100px class="mtable_13" cellspacing="0" summary="근무처분류별">
							<tr align="left">
								<!-- 기간 입력창 -->
								<td class="a01" width=10%>조회기간</td>
								<td><input type="text" name="startdate" id="datepicker" readonly value='<%=startdate %>'>&nbsp;~&nbsp;<input type="text" name="enddate" id="datepicker1" readonly value='<%=enddate %>'></td>

								<!-- 조회 버튼 -->
								<td><img src="images/icon_refer.gif" onclick="javascript:goSearch(companyMemberStateInputForm);" style="cursor: hand" alt="조회" align="right"/></td>
							</tr>
						</table>
				
				</form>
				
				<!-- 그리드 리스트 --> 		
				<table id="list"></table>
				<!-- 그리드 상태바 -->
				<div id="pager2"></div>
				
				<form name="companyMemberStateListForm" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border-top:1px #5588CC solid;border-left:1px #5588CC solid;border-right:1px #5588CC solid; border-bottom:1px #5588CC solid;background-color:#EEEEEE">
						<tr>
							<td class="nobg" align="right">
								<a href="downExcel/trust_company_list.xls" ><img src="images/icon_excel.gif" /></a>
							</td>
						</tr>
					</table>
				</form>


			</div>
		</div>
	</div>
</body>
</html>
