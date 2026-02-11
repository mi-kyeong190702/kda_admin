<%@page import="javax.swing.text.Document"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>


<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="texta/css" href="css/import.css" />  
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

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@page import="com.ant.common.code.CommonCode"%>

<%@page import="com.ant.document.dao.documentDao"%>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

<%
	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();
	String inqday1=(new SimpleDateFormat("yyyyMM").format(date))+"01";
	String inqday2=(new SimpleDateFormat("yyyyMMdd").format(date));
%>

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
	
	JSONArray comList	= (CommonCode.getInstance()).getCodeList();
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
	  url: 'document.do?method=documentreport',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'365',
      colNames: [ '발급일','발급년도','발급번호','발급자','관리부서','관리부서코드','문서종류','문서종류코드','요청자',
//                   JUMIN_DEL
//                   '요청자No',
                  '생년월일',
                  '요청자주소','발급건수','비고'],
      colModel: [
			{name:'printing_date',   index:'printing_date',   width: 80, editable:  false, align: 'center'},
			{name:'yyyy',            index:'yyyy',            width: 60, editable:  false, align: 'center'},
   			{name:'yyyy_seq',        index:'yyyy_seq',        width: 60, editable:  false, align: 'center'},
   			{name:'operatier_id',    index:'operatier_id',    width: 80, editable:  false, align: 'center'},
   			{name:'center_name',     index:'center_name',     width: 80, editable:  false, align: 'center'},
   			{name:'center',          index:'center',          hidden:true},
   			{name:'doc_kind_name',   index:'doc_kind_name',   width: 120,editable:  false, align: 'center'},
   			{name:'doc_kind',        index:'doc_kind',        hidden:true},
   			{name:'reclaimant',      index:'reclaimant',      width: 80, editable:  false, align: 'center', formatter:decode11},
   			
//    			{name:'reclaimant_no', 	 index:'reclaimant_no',   width: 80, editable:  false, align: 'center'},
   			{name:'reclaimant_birth', 	 index:'reclaimant_birth',   width: 80, editable:  false, align: 'center'},
   			{name:'reclaimant_add',  index:'reclaimant_add',  width: 250,editable:  false, align: 'left', formatter:decode11},
   			{name:'printing_cnt', 	 index:'printing_cnt',    width: 60, editable:  false, align: 'center'},
   			{name:'remark',          index:'remark',          width: 200,editable:  false, align: 'left', formatter:decode11},		
   			],
   		    rowNum:15,
   		    sortname: 'yyyy_seq',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '증서발급현황',
			altclass:'myAltRowClass',
			
});
powerinit();
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});

sForm.yyyy.value 		= (new Date()).getFullYear();
});

function decode11(cellvalue, options, rowObject ){
	var temp=decodeURIComponent(cellvalue);	
	return replaceAll(temp,"+"," ");
}

function replaceAll(temp,org,rep){
	return temp.split(org).join(rep);	
}

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "404";
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
	var parm =""; 
	
   if(sForm.yyyy.value             !="")parm+="&yyyy="         + sForm.yyyy.value;
   if(sForm.inqday1.value          !="")parm+="&inqday1="      + sForm.inqday1.value; 
   if(sForm.inqday2.value          !="")parm+="&inqday2="      + sForm.inqday2.value;  
   if(sForm.center.value           !="")parm+="&center="       + sForm.center.value; 
   if(sForm.doc_kind.value         !="")parm+="&doc_kind="     + sForm.doc_kind.value; 
   
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"document.do?method=documentreportList&parm="+parm}).trigger("reloadGrid");
}

function excelDown(){		
	var parm ="";
	
    if(sForm.yyyy.value             !="")parm+="&yyyy="         + sForm.yyyy.value; 
    if(sForm.inqday1.value          !="")parm+="&inqday1="      + sForm.inqday1.value; 
    if(sForm.inqday2.value          !="")parm+="&inqday2="      + sForm.inqday2.value;  
    if(sForm.center.value           !="")parm+="&center="       + sForm.center.value; 
    if(sForm.doc_kind.value         !="")parm+="&doc_kind="     + sForm.doc_kind.value;
  
	document.sForm.target="frm";
	document.sForm.action="document.do?method=documentreportDown&parm"+parm;
	document.sForm.submit();
}

function fn_doc_kind(form){//관리부서에 대한 문서종류만 선택
		
	//관리부서가 선택으로 바뀌면 문서종류도 선택으로 바꾸고 리턴한다.
	if(form.center.value ==""){
		form.doc_kind.options.length = 0;
		form.doc_kind.options[0] = new Option("선택","",false,false);
		form.doc_kind.disabled = true;
		
		return;
	}
	
	form.doc_kind.disabled=false;
	form.doc_kind.options.length = 0;
	
	var codeList	= eval(<%=comList%>);
	var cnt			= 1;

	form.doc_kind.options[0]=new Option('선택' , '' , false, false);
	
	for( var i=0; i<codeList.length; i++) {

		if(codeList[i].groupcode=='051') {	
			if( form.center.value == codeList[i].tempcd2 ) {
				form.doc_kind.options[cnt]=new Option(codeList[i].detcodename , codeList[i].detcode , false, false);
				cnt++;
			}
		}
	} 
	if( cnt == 1) form.doc_kind.disabled = true;
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});

</script>
</head>

<body>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">문서</a></li>		
		<li>&gt;</li>
		<li class="NPage">증서발급현황</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="sForm" method="post">        
	  <table class="ctable_03" cellspacing="0" summary="문서">
       	
		      <tr>
               <td class="nobg" align="center">발급 년도</td>
               <td class="nobg1a">
               <select  id="search" name="yyyy" style="width:100px;">
               <!-- <option value="">선택</option> -->	
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
			   <td class="nobg2a" align="center">조회 기간</td> 
			   <td class="nobg1a"><input type="text" name="inqday1" id="datepicker"  class="input" style="width:70px;" style=padding-top:3px  style=padding-bottom:3px value=''/>						 
							     ~<input type="text" name="inqday2" id="datepicker1" class="input" style="width:70px;" style=padding-top:3px  style=padding-bottom:3px value=''/></td>		  
			   <td class="nobg2a" align="center">관리 부서</td>
               <td class="nobg1a">
               <select  id="search" name="center" onchange="javascript:fn_doc_kind(sForm);" style="width:100px;">
               <option value="">선택</option>	
              		 <% 
              		String detCode1,detCName1=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("031".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode1=comcodesearch.get(i).get("detcode").toString();
                			detCName1=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td>
			   <td class="nobg2a" align="center">문서 종류</td>        
               <td class="nobg1a"><select name="doc_kind" disabled="true" style="width:100px;"><option value="" >선택</option></select>
               </td>
              </tr>               			              
      </table>
			 <ul class="btnIcon_11">	  	   
			   <li><a href="#"><img src="images/icon_search.gif" onclick="javascript:goSearch(sForm,0);" alt="검색" /></a></li>	 
		 	 </ul>	  
	 </form>
<br><br>
	 
<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
<p class="btnSave"><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onmouseover="this.src=this.src.replace('_off.','_on.')" onmouseout="this.src=this.src.replace('_on.','_off.')"/></a></p>
</form>
</div>
</div>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>