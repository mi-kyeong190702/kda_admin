<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/m_search.css" />
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

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

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.ant.common.code.CommonCode"%>

<script src="js/form.js" type="text/javascript" ></script>

<%@ page import="net.sf.json.JSONArray"%>
<!-- 김성훈 . 공통함수 -->
<script src="js/form.js" type="text/javascript" ></script>

<%
	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

//제이슨으로 공통 쿼리 처리
JSONArray comList	= (CommonCode.getInstance()).getCodeList();

String Date = new java.text.SimpleDateFormat ("yyyyMMdd").format(new java.util.Date());
String Time = new java.text.SimpleDateFormat ("HHmmss").format(new java.util.Date());
Calendar calendar = Calendar.getInstance();
java.util.Date date = calendar.getTime();

String yyyy = Integer.parseInt((new SimpleDateFormat("yyyy").format(date)))+""; 
String mmdd = "0101";
String startdate = yyyy+mmdd;
//String startyear = (new SimpleDateFormat("yyyy").format(date));
String enddate   = (new SimpleDateFormat("yyyyMMdd").format(date));

%>

<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

		<script type="text/javascript">
			$(document).ready(function(){
				
				//전체 권한 여부
				powerinit("105", eval(<%=userpowerm1%>) );


				jQuery("#list").jqGrid({
					url: "memberFund.do?method=memberFund",
						datatype: "json",
						mtype: 'POST',
						height:'500',
						//width:'1100',
						colNames: [ '입금일자','구분코드','구분','약정번호','회차','금액','이름',
// 						            '주민번호', // JUMIN_DEL
						            '면허번호','전화번호','회원구분코드','회원구분','근무처명'],
						colModel: [
				{name:'pres_let_dt'		,index:'pres_let_dt asc, invdate'	, align:"center", width:80, sortable:false},	//입금일자
				{name:'gubun'			,index:'gubun'			, width:100	, align:"right", sortable:false, hidden:true },
				{name:'han_gubun'		,index:'han_gubun'		, width:60	, align:"center", sortable:false },	//구분
				{name:'promise_no'		,index:'promise_no'		, width:60	, align:"center", sortable:false },	//약정번호
				{name:'promise_mon'		,index:'promise_mon'	, width:60	, align:"center", sortable:false },	//회차
				{name:'pres_money'		,index:'pres_money'		, width:90	, align:"right", sortable:false, formatter:'integer' },	//금액
				{name:'pers_name'		,index:'pers_name'		, width:70	, align:"center", sortable:false },	//이름
				// JUMIN_DEL
// 				{name:'pers_no'			,index:'pers_no'		, width:100	, align:"center", sortable:false },	//주민번호
				{name:'lic_no'			,index:'lic_no'			, width:70	, align:"center", sortable:false },	//면허번호
				{name:'pers_tel'		,index:'pers_tel'		, width:100	, align:"center", sortable:false },	//전화번호
				{name:'code_member'		,index:'code_member'	, width:100	, align:"right", sortable:false, hidden:true },
				{name:'han_code_member'	,index:'han_code_member', width:160	, align:"center", sortable:false },	//회원종류
				{name:'company_name'	,index:'company_name'	, width:180	, align:"center", sortable:false }	//근무처명
						],
						rowNum:20,
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
						caption:"기부/기금 현황"			      
				});
			jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
			jQuery("#list").setGridWidth(1100,false);
			});
			
			$(function() {	$( "#datepicker" ).datepicker();	});
			$(function() {	$( "#datepicker1" ).datepicker();	});
		</script>
		
<script>
	$(document).ready(function() {

		var codeList	= eval(<%=comList%>);	//전체공통쿼리
		var form		= memberInputForm;		//폼
		var cnt			= 0;
		var groupcode	= '';
		var detcode		= '';
		var detcodename	= '';
		
		for( var i=0; i<codeList.length; i++) {
			groupcode		= codeList[i].groupcode;
			detcode			= codeList[i].detcode;
			detcodename	= codeList[i].detcodename;
			
			if(cnt==0) {
				detcode = '';
				detcodename = '선택';
				i--;
			}
			
			if(groupcode=='040'){	//구분
				if( cnt==0 ) detcodename = "전체";
				form.gubun.options[cnt]=new Option(detcodename , detcode , false, false);
			}
			if(groupcode=='006')	//회원종류
				form.code_member.options[cnt]=new Option(detcodename , detcode , false, false);
				
			cnt++;
			if(codeList.length == i+1 || codeList[i+1].groupcode != groupcode)	cnt=0;
		}
	});

	function goSearch(form){
		var param="";
			
		if(form.startdate.value != '' && form.enddate.value != '') {
			if(form.startdate.value > form.enddate.value){
				alert("시작일이 종료일 보다 커서는 안됩니다.");
				return;
			}
			param+="&startdate="+form.startdate.value+"&enddate="+form.enddate.value;
		}
		if(form.startdate.value		!= "" ) param+="&startdate="	+form.startdate.value;
		if(form.enddate.value		!= "" ) param+="&enddate="		+form.enddate.value;
		if(form.gubun.value			!= "" ) param+="&gubun="		+form.gubun.value;
		if(form.code_member.value	!= "" ) param+="&code_member="	+form.code_member.value;
		if(form.pers_name.value		!= "")	param+="&pers_name="	+escape(encodeURIComponent(form.pers_name.value));
		if(form.lic_no.value		!= "" ) param+="&lic_no="		+form.lic_no.value;
		if(form.company_name.value	!= "")	param+="&company_name="	+escape(encodeURIComponent(form.company_name.value));

		$('#list').jqGrid('clearGridData');
		jQuery("#list").setGridParam({url:"memberFund.do?method=memberFundList"+param}).trigger("reloadGrid"); 
	}

	function excelDown(form){
		
		var rownum = jQuery("#list").jqGrid('getGridParam','records')
		if( rownum == 0) {
			alert("출력할 정보를 조회해 주세요.");
			return;
		}
		
		var param="";
		if(form.startdate.value != '' && form.enddate.value != '') {
			if(form.startdate.value > form.enddate.value){
				alert("시작일이 종료일 보다 커서는 안됩니다.");
				return;
			}
		}
		if(form.startdate.value		!= "" ) param+="&startdate="	+form.startdate.value;
		if(form.enddate.value		!= "" ) param+="&enddate="		+form.enddate.value;
		if(form.gubun.value			!= "" ) param+="&gubun="		+form.gubun.value;
		if(form.code_member.value	!= "" ) param+="&code_member="	+form.code_member.value;
		if(form.pers_name.value		!= "" )	param+="&pers_name="	+escape(encodeURIComponent(form.pers_name.value));
		if(form.lic_no.value		!= "" ) param+="&lic_no="		+form.lic_no.value;
		if(form.company_name.value	!= "")	param+="&company_name="	+escape(encodeURIComponent(form.company_name.value));

		param+="&register="+"<%=g_name%>";

		var url="memberFund.do?method=pers_check"+param;
		window.open(url,"pers_check","width=400, height=300, left=480, top=200");
/* 
		form.target="frm";
		form.action="memberFund.do?method=memberFundExcel"+param;
		form.submit();
 */
	}
	
	//검색기간 클릭했을때 내용 초기화 
	function fn_auth_start(form)	{form.startdate.value="";	}
	function fn_auth_end(form)	{form.enddate.value="";	}
</script>

	</head>



<body>
<div id="contents"><!-- contents -->

	<!-- 상단 바로가기 바 -->
	<ul class="home">
	    <li class="home_first"><a href="main.do">Home</a></li>	<li>&gt;</li>
		<li><a href="memberForm.do">회원</a></li>				<li>&gt;</li>
		<li class="NPage">기부/기금 현황</li>
	</ul>
		 	
	<div class="mTabmenu_01"> <!-- mTabmenu_01 -->
	<form name="memberInputForm" method="post">
		<table class="mytable_01" cellspacing="0" summary="근무처분류별">
			<caption>평생회비 예수금</caption>
			
						
				<!-- 조건 입력창 -->
				<tr>
					<td class="nobg">검색기간</td>
					<td class="nobg1"><input type="text" name="startdate" id="datepicker" size="8" readonly onclick="fn_auth_start(memberInputForm);">&nbsp;~<input type="text" name="enddate" id="datepicker1" size="8" readonly onclick="fn_auth_end(memberInputForm);" ></td>
					<td class="nobg2">구분</td>
					<td class="nobg1"><select name="gubun" ><option value="">선택</option></select></td>
					<td class="nobg2">근무처명</td>
					<td class="nobg1"><input type="text" id="company_name" name="company_name" onfocus="fn_IME(this,'KOR');" onkeyup="javascript:check_StrByte(this, 50);" onKeyDown="javascript:if(event.keyCode==13){goSearch(memberInputForm);}" value="" size="13" maxlength="30" /></td>
				</tr>

				<tr>
					<td class="alt1" >회원구분</td>
					<td><select name="code_member" ><option value="">선택</option></select></td>
					<td class="alt" >이름</td>
					<td><input type="text" id="pers_name" name="pers_name" onfocus="fn_IME(this,'KOR');" onkeyup="javascript:check_StrByte(this, 50);" onKeyDown="javascript:if(event.keyCode==13){goSearch(memberInputForm);}" value="" size="13" maxlength="30" /></td>
					<td class="alt" >면허번호</td>
					<td><input type="text" id="lic_no" name="lic_no" onKeyUp="javascript:check_Int('memberInputForm','lic_no');" onKeyDown="javascript:if(event.keyCode==13){goSearch(memberInputForm);}" size="13" maxlength="6"/></td>
				</tr>
				
			<tr><td colspan="6" style="border-right:1px solid #FFFFFF ;">
				<img src="images/icon_refer.gif" onclick="javascript:goSearch(memberInputForm);" style="cursor: hand" alt="조회" align="right"/>
			</td></tr>
			
		</table>
		</form>
		<div class="mTabmenu_01"> <!-- mTabmenu_01 -->
				
				<!-- 그리드 리스트 --> 		
				<table id="list"></table>
				<!-- 그리드 상태바 -->
				<div id="pager2"></div>
				
				<form name="memberStateListForm" method="post">
					<!-- 엑셀출력 버튼 -->		
					<table width="1100" height="36" border="0" cellpadding="5" cellspacing="0" style="border:0px;">
						<tr>
							<td class="nobg" align="right">
								<a href="javascript:excelDown(memberInputForm);" ><img src="images/icon_excel.gif" /></a>
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