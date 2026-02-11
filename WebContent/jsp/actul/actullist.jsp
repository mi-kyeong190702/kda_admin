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
<link rel="stylesheet" type="text/css" href="css/document.css"/>
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
	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);	
%> 

<script type="text/javascript">

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
	  url: 'actul.do?method=actulformList',
	  datatype: "json",
      mtype: 'post',     
      height:'450',
	  width:'1100',
      colNames: [ '대체코드명','근무처','근무기관','서울지부','부산지부','인천지부','경기지부','강원지부','충북지부','대전충남','전북지부','광주전남','대구경북','경남지부','울산지부','제주지부','기타','합계'],
      colModel: [
             	{name:'tempcd1'             ,index:'tempcd1'   ,align:"center"  , hidden:true},
   				{name:'groupname1'			,index:'groupname1'	,align:"center" , width:100, sortable:false},
				{name:'groupname2'			,index:'groupname2'	,align:"center" , width:100, sortable:false},
				{name:'col1'				,index:'col1'					    , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col2'				,index:'col2'					    , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col3'				,index:'col3'				        , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col4'				,index:'col4'				        , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col5'				,index:'col5'				        , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col6'				,index:'col6'				        , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col7'				,index:'col7'			            , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col8'				,index:'col8'					    , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col9'				,index:'col9'					    , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col10'				,index:'col10'				        , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col11'				,index:'col11'					    , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col12'				,index:'col12'				        , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col13'				,index:'col13'					    , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'col14'				,index:'col14'				        , width:50, align:"right", sortable:false, formatter:'integer'},
				{name:'totcount'			,index:'totcount'			        , width:50, align:"right", sortable:false, formatter:'integer'}
   			],
   			rowNum:30,
			//rowList:[10,20,30],
			sortname: 'id',
			sortorder: 'desc',
			/* pager: '#pager2', */						
			viewrecords: true,
			caption: 'kda_grid',			
			altclass:'myAltRowClass',
			rownumbers : true,
 			caption: '영양사실태현황'			 
	});
powerinit();
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "602";
	var bcheck = "N";
//	alert("userpowerm1[i].progid===>"+userpowerm1.length);
//	alert(userpowerm1);
	for(i=0;i<userpowerm1.length;i++){
		var setprogid=userpowerm1[i].progid;
		//alert("찾기.=======>"+setprogid);
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

function goSearch(form,intPage){	
	var parm = "";
	var yyyy = sForm.yyyy.value;
	
	 if ( yyyy == "" ) {
	        alert("신고 년도를 선택하세요.") ;
	        sForm.yyyy.focus();
	        return ;
	    }	 
	  if(yyyy     !="")parm+="&yyyy="  + yyyy; 
	  
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"actul.do?method=actulformList&isSelect=Y"+parm}).trigger("reloadGrid");
}

function excelDown(){		
	var yyyy = sForm.yyyy.value;
	
	 if ( yyyy == "" ) {
	        alert("신고 년도를 선택하세요.") ;
	        sForm.yyyy.focus();
	        return ;
	    }	 
	
	document.sForm.target="frm";
	document.sForm.action="actul.do?method=actulformDown&yyyy"+yyyy;
	document.sForm.submit();
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
    <table class="ctable_03" cellspacing="0" summary="영양사실태현황">			
             <caption>신고현황</caption>  
             <tr>
              <td class="nobgaa" align="center">신고 년도</td>
       		   <td class="nobg1aa">
               <select  id="search" name="yyyy" style="width:80px;">
               <option value="">선택</option>	
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("036".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td>  		  
			 </tr>						 
        </table>
        <ul class="btnIcon_11">       	  
		  <li><a href="#"><img src="images/icon_search.gif"  onclick="javascript:goSearch(sForm,0);"  align="center" alt="검색" /></a></li>	
		</ul>  		
</form>
<br><br>

<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
		<p class="btnSave"><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="javascript:downloadExcel();" onmouseover="this.src=this.src.replace('_off.','_on.')" onmouseout="this.src=this.src.replace('_on.','_off.')"/></a></p>
</form>
</div>
</div>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>
  