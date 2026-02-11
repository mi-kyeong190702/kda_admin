<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>

<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/document.css" />
<script type="text/javascript" src="js/comm.js"></script>

<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@ page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%@page import="com.ant.document.dao.documentDao"%>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

<%
String g_name = session.getAttribute("G_NAME").toString(); 
String g_bran = session.getAttribute("G_BRAN").toString(); 
JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
%>

<%
	String tag = "";
%>

<%
	String logid="";
	if(session.getAttribute("G_NAME")!=null){
		logid=session.getAttribute("G_NAME").toString();
	}
	String errMsg="";
	if(request.getAttribute("errMsg")!=null){
		errMsg=request.getAttribute("errMsg").toString();
	}
%>

<%
	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);	
%> 

<script type="text/javascript">

document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};

function init(){
	var logid="<%=logid%>";
	var errMsg="<%=errMsg%>";
	if(errMsg.length>5){
		alert(errMsg);
		return;
	}
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		opener.document.location.href="login.do?method=login";
		self.close();
	}
}

function getAjax(strUrl,funcReceive){
	  $.ajax({
	   cache:false,
	   type:'GET',
	   url:strUrl,
	   dataType:'html',
	   timeout:60000,
	   success:function(html,textStatus){
	    funcReceive(html,textStatus);
	    document.all.div_wait.style.visibility  = "hidden" ;
	   },
	   error: function(xhr,textStatus,errorThrown){
	    document.all.div_wait.style.visibility  = "hidden" ;
	    alert(textStatus);
	    alert("전송에러");
	   }
	  });
	 }

	 function receiveList(data){
	  var jsonData=eval("("+data+")");
	  $("#list").clearGridData();
	  for(var i=0;i<=jsonData.data.length;i++) $("#list").addRowData(i+1,jsonData.data[i]);
	 }

$(document).ready(function(){	
jQuery("#list").jqGrid({	  
   /* url: 'actul.do?method=actulstatusList', */
	  url: '',
	  datatype: "json",
      mtype: 'post',
      width:'1100',
      height:'365',
      colNames: [ ' 번호 ','이름','면허번호','면허발급년월일','생년','전화','휴대전화','메일','활동여부','근무기관','근무처','활동여부코드','년도','보수교육이수년도','상태','상태','보수교육이수년도1','상태코드','상태','보수교육이수년도2','이수시간','상태코드1','완료날짜','날짜'],
      colModel: [
			{name:'code_seq',         index:'code_seq',          hidden:true},
			{name:'ar_conform_date',  index:'ar_conform_date',    width: 70,editable:  false, align: 'center'},
   			{name:'yyyy',         index:'yyyy',          width: 80,editable:  false, align: 'center'},   			
   			{name:'pers_name', 	      index:'pers_name',           width: 100,editable:  false, align: 'center'},
   			{name:'pers_year',          index:'pers_year',           hidden:true},
   			{name:'lic_no',          index:'lic_no',           width:80,editable:  false, align: 'center'},
   			{name:'lic_print_dt',          index:'lic_print_dt',           width: 100,editable:  false, align: 'center'},
   			{name:'email',  index:'email',   width: 180,editable:  false, align: 'center'},
   			{name:'addr',       index:'addr',        width: 100,editable:  false, align: 'center'},
   			{name:'pers_tel',        index:'pers_tel',         width: 100,editable:  false, align: 'center'},   		   
   			{name:'pers_hp',  index:'pers_hp',   width:100,editable:  false, align: 'center'},
   			{name:'ar_code_part',  index:'ar_code_part',   hidden:true},
   			{name:'ar_code_part_kr',  index:'ar_code_part_kr',   width: 50,editable:  false, align: 'center'},
   			{name:'kitchen_code',  index:'kitchen_code',   width: 120,editable:  false, align: 'center'},
   			{name:'yyyy2',  index:'yyyy2',   width: 60,editable:  false, align: 'center',hidden:true},
   			{name:'cs_con_education',  index:'cs_con_education',   width: 60,editable:  false, align: 'center'},
   			{name:'ar_marks_point',  index:'ar_marks_point',   width: 120,editable:  false, align: 'center'},
   			{name:'ar_state',  index:'ar_state',   hidden:true},
   			{name:'ar_state_kr',  index:'ar_state_kr',   width: 60,editable:  false, align: 'center'},
   			{name:'cs_con_education1',  index:'cs_con_education1',   width: 120,editable:  false, align: 'center'},
   			{name:'ar_marks_point1',  index:'ar_marks_point1',   width: 60,editable:  false, align: 'center'},
   			{name:'ar_state1',  index:'ar_state1',   hidden:true},
   			{name:'ar_state_kr1',  index:'ar_state_kr1',   width: 100,editable:  false, align: 'center'},
   			{name:'ar_regi_date',  index:'ar_regi_date',   hidden:true},
   			],
   		    rowNum:15,
   		   	pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			gridview: true,
 			shrinkToFit:false,
 			caption: '신고현황',
			altclass:'myAltRowClass',
			
			onSelectRow: function(ids) {
				//goSelect();
				if(ids == null) {
					ids=0;
					if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
					{
						alert("컬럼을 정확히 선택해 주세요");
					}
				} else {

					//ids 는 몇번째인가, 아래 page 는 몇 페이지인가 . 계산해서 처리하자.
					//alert("page = "+jQuery("#list").jqGrid('getGridParam','page')+" row_id = "+ids);
					var id = jQuery("#list").jqGrid('getGridParam','selarrrow') ;
					
					if(id){
						var ret = jQuery("#list").jqGrid('getRowData',id);
						var id = ret.id;
						//alert(id);
						//onSubmitList(id);
					}
					else {}				
				}
			}
			/* ,loadError:function(xhr, status, error){
			    alert(xhr.responseText); 
			} */
  
});
powerinit();
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "601";
	var bcheck = "N";
	for(i=0;i<userpowerm1.length;i++){
		var setprogid=userpowerm1[i].progid;
		if (meprogid == setprogid){
			bcheck = "Y";
		}
	}
	
	if(bcheck=="N"){
		alert("프로그램에 대한 사용권한이 없습니다.");
		history.back(-1);
	}

	var logid="<%=g_name%>";
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		opener.document.location.href="login.do?method=login";
		self.close();
	}
}

function goSelect(rowid,iCol){
	goClear();
	tag = '';
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
		
		document.sForm.yyyy1.value            = list.yyyy;	
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();
		tag = '*';
	});	
}

function goSearch(form,intPage){
	var parm ="";
	
	if(sForm.yyyy1.value        !="")parm+="&yyyy1="       + sForm.yyyy1.value;  
	
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"actul.do?method=actulstatusList&parm="+parm}).trigger("reloadGrid");
}

function excelDown(form){
	
	var rownum = jQuery("#list").jqGrid('getGridParam','records');
	if( rownum == 0) {
		alert("출력할 정보를 조회해 주세요.");
		return;
	}
	
	var id = jQuery("#list").jqGrid('getGridParam','selarrrow');
	var code_seq ="";
	
	if( id.length==0)	{
		code_seq ="0";
	}else{
		var data;
			
		for(var i=0; i<id.length;i++){
			data = jQuery("#list").jqGrid('getRowData',id[i]);
			if(code_seq.length>0) code_seq +=",";
			code_seq+= data.code_seq;
		}
		
	}
	
	var param = "";
	
	if(sForm.yyyy1.value        !="")param+="&yyyy1="       + sForm.yyyy1.value; 
	
	
	var url="actul.do?method=excel_status"+param+"&code_seq="+code_seq;
	window.open(url,"exceldown","width=400, height=500, left=480, top=200,scrollbars=yes");	

}

</script>
</head>

<body>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">영양사실태신고</a></li>
		<li>&gt;</li>
		<li class="NPage">신고현황</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="sForm" method="post">
<!-- 중복처리방지 토큰 처리 시작-->
<%
	//중복처리방지 토큰 처리 - 페이지에 그대로 추가
	String token=TokenProcessor.getInstance().generateToken(request); //현재 시간과 세션아이디를 갖고 token 키생성

	if(session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY)==null){
		session.setAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY,token);	
	%>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
	<%}else{%>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
<%}%> 
<!-- 중복처리방지 토큰 처리  끝-->	  

        <table class="ctable_03" cellspacing="0" summary="등록년도">			
            <tr>           	   
               <td class="nobga" align="center">등록년도</td>
               <td class="nobg1a"><select  id="yyyy1" name="yyyy1">
               <option value="">선택</option>
               <% String lon = "";
				   for( int i=2015; i<2026; i++ ) {
					   lon = i+""; %>
					<option value="<%=lon%>"><%=i%></option>
				<% } %>
               </select></td>				               		
			</tr>
		 </table>			
    	 <ul class="btnIcon_2"  style="left:1042px;">
		   	<li><a href="#"><img src="images/icon_search.gif" onclick="javascript:goSearch(sForm,0);" alt="검색" /></a></li>	 
		 </ul>	
</form>
<br><br>

<table id="list"></table>
<div id="pager2"></div>

<br/>
			<ul class="btnIcon_1" style="left:1025px;">
				<li><a href="javascript:excelDown(sForm);"	><img src="images/icon_excel.gif"	alt="엑셀저장"	/></a></li>
			</ul>
			
			
</div>
</div>
</body>
</html>

  