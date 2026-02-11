<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
	<!-- 
	수정일 : 2012.10.09
	수정자 : 박상모
	내   용 : 공동관리별 인원수 집계
	 -->

<%
String Date = new java.text.SimpleDateFormat ("yyyyMMdd").format(new java.util.Date());
String Time = new java.text.SimpleDateFormat ("HHmmss").format(new java.util.Date());
Calendar calendar = Calendar.getInstance();
java.util.Date date = calendar.getTime();
String startdate=(new SimpleDateFormat("yyyyMM").format(date))+"01";
String enddate=(new SimpleDateFormat("yyyyMMdd").format(date));
JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
String logid="";
if(session.getAttribute("G_NAME")!=null){
	logid=session.getAttribute("G_NAME").toString();
}
%>

<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

		<script type="text/javascript">
		document.onreadystatechange=function(){
		    if(document.readyState == "complete")init();
		};
		function init(){
			//프로그램 권한설정 시작
			var userpowerm1=eval(<%=userpowerm1%>);
			var meprogid = "120"; //프로그램 ID ->dbo.prog_list
			var bcheck = "N";
			for(var i=0;i<userpowerm1.length;i++){
				var setprogid=userpowerm1[i].progid;				
				if (meprogid == setprogid){
					bcheck = "Y";
				}
			}
			if(bcheck=="N"){
				alert("프로그램에 대한 사용권한이 없습니다.");
				history.back(-1);
			}
			//프로그램 권한설정 끝
			
			//로그인 체크
			var logid="<%=logid%>";
			if(logid==null||logid==""){
				alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
				location.href="login.do?method=login";
			}
		}
			$(document).ready(function(){
				
				jQuery("#list").jqGrid({
					url: "memberState.do?method=conStateList",
						datatype: "json",
						mtype: 'POST',
						height:'500',
						width:'1100',
						colNames: [ '대분류','소분류','1명','2명','3명','4명','5명','6명','7명','8명','9명','10명','기타','합계'],
						colModel: [
				{name:'tempcd1'			,index:'tempcd1'			,align:"center", width:1, sortable:false},
				{name:'detcodename'	,index:'detcodename'	,align:"center", width:1, sortable:false},
				{name:'col1'				,index:'col1'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col2'				,index:'col2'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col3'				,index:'col3'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col4'				,index:'col4'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col5'				,index:'col5'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col6'				,index:'col6'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col7'				,index:'col7'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col8'				,index:'col8'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col9'				,index:'col9'		, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col10'				,index:'col10'	, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'col11'				,index:'col11'	, width:1, align:"right", sortable:false, formatter:'integer'},
				{name:'totcount'			,index:'totcount'	, width:1, align:"right", sortable:false, formatter:'integer'}
						],
						rowNum:1000000,
						//rowList:[10,20,30],
						sortname: 'id',
						sortorder: 'desc',
						pager: '#pager2',						
						viewrecords: true,
						caption: 'kda_grid',
						altRows:true,
						altclass:'myAltRowClass',
						rownumbers : true,
						loadComplete: function () {                    
			            	if($("#list").getCell('1','tempcd1')==''){
			            		document.memberStateInputForm.yn.value='n';	
			                }else{
			                	document.memberStateInputForm.yn.value='y';	
			                }					                    
			            },
						caption:"공동관리별 인원수 집계"			      
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
				}else{
					document.memberStateListForm.startdate.value=startdate;
					document.memberStateListForm.enddate.value=enddate;
					$('#list').jqGrid('clearGridData');
					jQuery("#list").setGridParam({url:"memberState.do?method=conStateList&startdate="+startdate+"&enddate="+enddate}).trigger("reloadGrid"); 
				}
			}
			function excelDown(){	
				if(document.memberStateInputForm.yn.value=='n'){
					alert("다운받으실 내용을 조회해주십시오.");
					return;
				}else if(document.memberStateInputForm.yn.value=='y'){				
					document.memberStateListForm.target="frm";
					document.memberStateListForm.action="memberState.do?method=conStateDown";
					document.memberStateListForm.submit();
				}
			}
		</script>
	
	</head>



<body>
	<div id="contents">
		<ul class="home">
		    <li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
			<li><a href="memberForm.do">회원</a></li>				<li>&gt;</li>
			<li><a href="memberStateForm.do">현황</a></li>			<li>&gt;</li>
			<li class="NPage">공동관리 현황</li>
		 </ul>
		 <p>	
		<div class="mTabmenu_01"> 
			<div class="mTabmenu_01">
				<form name="memberStateInputForm" method="post">
				<input type="hidden" name="yn" value=""/>		
					    <table width=1100px class="mtable_13" cellspacing="0" summary="최종학위별">
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
				<p>
				<form name="memberStateListForm" method="post">
				<input type="hidden" name="register" value="<%=session.getAttribute("G_ID") %>"/>
				<input type="hidden" name="startdate" value="">
				<input type="hidden" name="enddate" value="">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown();" ><img src="images/icon_excel.gif" /></a>
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