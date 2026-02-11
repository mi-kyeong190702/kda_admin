<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="net.sf.json.JSONArray"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<%
JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
String logid="";
if(session.getAttribute("G_NAME")!=null){
	logid=session.getAttribute("G_NAME").toString();
}
%>
	<!-- 
	수정일 : 2012.10.09
	수정자 : 박상모
	내   용 : 고용형태별 직급별 인원수 집계
	 -->
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
			var meprogid = "115"; //프로그램 ID ->dbo.prog_list
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
					url: "memberState.do?method=trustComStateList",
						datatype: "json",
						mtype: 'POST',
						height:'500',
						width:'1100',
						colNames: ['위탁업체코드','위탁업체명','우편번호','주소','전화번호'],
						colModel: [
				{name:'trust_code'	,index:'trust_code'		,align:"center", width:1, sortable:false},
				{name:'trust_name'	,index:'trust_name'	,align:"left", width:1, sortable:false},
				{name:'trust_post'		,index:'trust_post'		,align:"center", width:1, sortable:false, formatter:postNo},
				{name:'trust_addr'	,index:'trust_addr'		,align:"left", 	width:1, sortable:false},
				{name:'trust_tel'		,index:'trust_tel'		,align:"center", width:1, sortable:false}				
						],
						rowNum:10000000,						
						sortname: 'id',
						sortorder: 'desc',
						pager: '#pager2',						
						viewrecords: true,
						altRows:true,
						altclass:'myAltRowClass',
						rownumbers : true,
						loadComplete: function () {                    
			            	if($("#list").getCell('1','trust_code')==''){
			            		document.memberStateInputForm.yn.value='n';	
			                }else{
			                	document.memberStateInputForm.yn.value='y';	
			                }					                    
			            },
			            caption:"위탁업체 업체별"
						
						/* multiselect: true,
						beforeSelectRow: function(rowid, e)
					    {
					        jQuery("#list").jqGrid('resetSelection');
					        return(true);
					    } */
				});
			jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
			});
			function postNo(cellvalue, options, rowObject ){
				var post="";
				if(cellvalue.length==6){
					post=cellvalue.substring(0,3)+"-"+cellvalue.substring(3,6);
				}else{
					post=cellvalue;
				}
				return post;
			}
		</script>
		
		<script>
		
		
		function goSearch(form){
			var name   = escape(encodeURIComponent(form.trust_nm.value));
			document.memberStateListForm.trust_keyword.value=form.trust_nm.value;
			$('#list').jqGrid('clearGridData');
			jQuery("#list").setGridParam({url:"memberState.do?method=trustComStateList&trust_nm="+name}).trigger("reloadGrid"); 
		}
		
		function excelDown(){	
			if(document.memberStateInputForm.yn.value=='n'){
				alert("다운받으실 내용을 조회해주십시오.");
				return;
			}else if(document.memberStateInputForm.yn.value=='y'){				
				document.memberStateListForm.target="frm";
				document.memberStateListForm.action="memberState.do?method=trustComStateDown";
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
			<li class="NPage">위탁업체 업체별</li>
		 </ul>
		 <p>	
		<div class="mTabmenu_01"> 
			<div class="mTabmenu_01">
				<form name="memberStateInputForm" method="post">
				<input type="hidden" name="yn" value=""/>					
					    <table width=1100px class="mtable_13" cellspacing="0" summary="위탁업체 업체별">
							<tr align="left">
								<!-- 기간 입력창 -->
								<td class="a01" width=10%>업체명</td>								             			  
			   					<td><input type="text" name="trust_nm" id="trust_nm" size="20" /></td>		  
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
					<input type="hidden" name="trust_keyword" value="">
						<input type="hidden" name="register" value="<%=session.getAttribute("G_ID") %>"/>
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
