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
	  url: 'conference.do?method=conferenceList',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'470',
      colNames: [ '순번','회의실코드','시작시간','종료시간','주제','신청자','회의일자','회의실','주관부서','부서코드','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24'],
      colModel: [
			{name:'conference_seq', 		index:'conference_seq',  		 hidden:true},				
			{name:'code_conference',        index:'code_conference',         hidden:true},			
			{name:'use_st_hhmm',   			index:'use_st_hhmm',    		 hidden:true},
			{name:'use_ed_hhmm',    		index:'use_ed_hhmm',   		     hidden:true},
			{name:'subject',       			index:'subject',       			 hidden:true},
			{name:'register',       		index:'register',       		 hidden:true},
			{name:'conference_dt',  		index:'conference_dt',   		 width: 100, editable: false, align: 'center'},
			{name:'code_conferencename',    index:'code_conferencename',     width: 150,editable: false, align: 'center'},
			{name:'detcodename',   			index:'detcodename',    		 width: 100,editable: false, align: 'center'},
			{name:'center',       			index:'center',         		 hidden:true},
			{name:'k08',           			index:'k08',           			 width: 80,editable: false, align: 'center'},
   			{name:'k09',           			index:'k09',           			 width: 80,editable: false, align: 'center'},
   			{name:'k10',           			index:'k10',           			 width: 80,editable: false, align: 'center'},
   			{name:'k11',          			index:'k11',           			 width: 80,editable: false, align: 'center'},
   			{name:'k12',          			index:'k12',          			 width: 80,editable: false, align: 'center'},
   			{name:'k13',         			index:'k13',          			 width: 80,editable: false, align: 'center'},	
   			{name:'k14',          			index:'k14',         			 width: 80,editable: false, align: 'center'},	
   			{name:'k15', 	     			index:'k15',           			 width: 80,editable: false, align: 'center'},
   			{name:'k16',          			index:'k16',          			 width: 80,editable: false, align: 'center'},		
   			{name:'k17',         			index:'k17',         			 width: 80,editable: false, align: 'center'},
   			{name:'k18',          			index:'k18',          			 width: 80,editable: false, align: 'center'},
   			{name:'k19', 	      			index:'k19',           			 width: 80,editable: false, align: 'center'},
   			{name:'k20',         			index:'k20',          			 width: 80,editable: false, align: 'center'},
   			{name:'k21',         			index:'k21',         			 width: 80,editable: false, align: 'center'},
   			{name:'k22',          			index:'k22',          			 width: 80,editable: false, align: 'center'},
   			{name:'k23', 	      			index:'k23',           			 width: 80,editable: false, align: 'center'},
   			{name:'k24',         			index:'k24',          			 width: 80,editable: false, align: 'center'},
   			],
   		    rowNum:20,
   		    sortname: 'conference_dt', 
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '회의 일자별',
			altclass:'myAltRowClass',
			loadComplete:function(){
				var rowIds=$("#list").jqGrid('getDataIDs');
				for(var i=1;i<=rowIds.length;i++){
					rowData=$("#list").jqGrid('getRowData',i);
					if(rowData['k08']=='────────'){
						$("#list").jqGrid('setCell',i,'k08','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k09']=='────────'){
						$("#list").jqGrid('setCell',i,'k09','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k10']=='────────'){
						$("#list").jqGrid('setCell',i,'k10','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k11']=='────────'){
						$("#list").jqGrid('setCell',i,'k11','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k12']=='────────'){
						$("#list").jqGrid('setCell',i,'k12','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k13']=='────────'){
						$("#list").jqGrid('setCell',i,'k13','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k14']=='────────'){
						$("#list").jqGrid('setCell',i,'k14','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k15']=='────────'){
						$("#list").jqGrid('setCell',i,'k15','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k16']=='────────'){
						$("#list").jqGrid('setCell',i,'k16','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k17']=='────────'){
						$("#list").jqGrid('setCell',i,'k17','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k18']=='────────'){
						$("#list").jqGrid('setCell',i,'k18','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k19']=='────────'){
						$("#list").jqGrid('setCell',i,'k19','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k20']=='────────'){
						$("#list").jqGrid('setCell',i,'k20','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k21']=='────────'){
						$("#list").jqGrid('setCell',i,'k21','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k22']=='────────'){
						$("#list").jqGrid('setCell',i,'k22','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k23']=='────────'){
						$("#list").jqGrid('setCell',i,'k23','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
					if(rowData['k24']=='────────'){
						$("#list").jqGrid('setCell',i,'k24','',{  color:'#ffcc00',weightfont:'bold',background:'#ffcc00'});
					}
				}
			},
			onSelectRow: function(ids) {
				goSelect();
				
				if(ids == null) {
					ids=0;
					if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
					{
						alert("컬럼을 정확히 선택해 주세요");
					}
				} else {

					//ids 는 몇번째인가, 아래 page 는 몇 페이지인가 . 계산해서 처리하자.
					//alert("page = "+jQuery("#list").jqGrid('getGridParam','page')+" row_id = "+ids);
					var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
					if(id){
						var ret = jQuery("#list").jqGrid('getRowData',id);
						var id = ret.id;
						//alert(id);
						onSubmitList(id);
					}
					else {}				
				}
			}  
});
powerinit();
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "501";
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

function goSelect(rowid,iCol){
	var sObj = null;
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
				
		document.sForm.conference_dt1.value         = list.conference_dt;
		document.sForm.code_conferencename1.value   = list.code_conference;	
		document.sForm.conference_seq1.value        = list.conference_seq;
		document.sForm.register1.value       		= list.register;
		document.sForm.use_st_hhmm1.value           = list.use_st_hhmm.substr(0,2);
		document.sForm.use_st_hhmm2.value           = list.use_st_hhmm.substr(2,2);
		document.sForm.use_ed_hhmm1.value           = list.use_ed_hhmm.substr(0,2);
		document.sForm.use_ed_hhmm2.value           = list.use_ed_hhmm.substr(2,2);
		document.sForm.center1.value                = list.center;		
		document.sForm.register1.value              = list.register;
		document.sForm.subject1.value               = list.subject;
		document.sForm.excelFlag.value              = "N";
	}
}

function goDelete(){
	var conf_dt=document.sForm.conference_dt1.value;
	var conf_seq=document.sForm.conference_seq1.value;
	var conf_code=document.sForm.code_conferencename1.value;
	
	if(conf_dt==""||conf_seq==""||conf_code==""){
		alert("리스트에서 삭제할 건을 선택해 주십시오.");
		return;
	}else{
		document.sForm.action = "conference.do?method=delete_conference&conference_dt1="+conf_dt+"&conference_seq1="+conf_seq+"&code_conferencename1="+conf_code;
		document.sForm.submit();	
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();		
	});	
}

function goInsert(){
	var conference_dt1       = sForm.conference_dt1.value;	
	var conference_seq1      = sForm.conference_seq1.value;	
	var code_conferencename1 = sForm.code_conferencename1.value;
	var center1				 = sForm.center1.value;
	var register1			 = sForm.register1.value;
	
	var st=sForm.use_st_hhmm1.value+sForm.use_st_hhmm2.value;
	var ed=sForm.use_ed_hhmm1.value+sForm.use_ed_hhmm2.value;
	
	if (conference_dt1 == "" ) {
        alert("회의일자를 입력해 주십시오.") ;
        sForm.receipt_dt1.focus();
        return ;
    }
    if(code_conferencename1==""){
    	alert("회의실을 선택해 주십시오.");
    	return;
    }
    if(center1==""){
    	alert("주관부서를 선택해 주십시오.");
    	return;
    }
    if(register1==""){
    	alert("신청자를 입력해 주십시오.");
    	return;
    }
    if ( conference_seq1 == "" ) { 
    	var rows= jQuery("#list").jqGrid('getRowData');
    	for(var i=0;i<rows.length;i++){
    		var row=rows[i];
    	    if(row.conference_dt==conference_dt1){
    	    	if(row.code_conference==code_conferencename1){
    	    		//alert(row.use_st_hhmm+'  '+row.use_ed_hhmm);
    	    		if((st>=row.use_st_hhmm&&st<=row.use_ed_hhmm)||(ed>=row.use_st_hhmm&&ed<=row.use_ed_hhmm)||(st<=row.use_st_hhmm&&ed>=row.use_ed_hhmm)){
    	    			alert("입력하신 시간대에 선택하신 회의실은 이미 예약돼있습니다.");
    	    			return; 
    	    		}
    	    	}
    	    }
    	}
    	document.sForm.action = "conference.do?method=insert_conference";
    	document.sForm.submit();  
    }else { 
    	var rows= jQuery("#list").jqGrid('getRowData');
    	for(var i=0;i<rows.length;i++){
    		var row=rows[i];
    	    if(row.conference_dt==conference_dt1){
    	    	if(row.conference_seq!=conference_seq1){
    	    		if(row.code_conference==code_conferencename1){
        	    		//alert(row.use_st_hhmm+'  '+row.use_ed_hhmm);
        	    		if((st>=row.use_st_hhmm&&st<=row.use_ed_hhmm)||(ed>=row.use_st_hhmm&&ed<=row.use_ed_hhmm)){
        	    			alert("입력하신 시간대에 선택하신 회의실은 이미 예약돼있습니다.");
        	    			return; 
        	    		}
        	    	}
    	    	}    	    	
    	    }
    	}    	
    	document.sForm.action = "conference.do?method=update_conference&conference_seq1="+conference_seq1;
    	document.sForm.submit();
	 }
}

function goSearch(form,intPage){
	var parm ="";   

	if(sForm.conference_dt1.value               !="")parm+="&conference_dt1="            + sForm.conference_dt1.value;    
    if(sForm.inqday1.value                      !="")parm+="&inqday1="                   + sForm.inqday1.value; 
    if(sForm.inqday2.value                      !="")parm+="&inqday2="                   + sForm.inqday2.value;  
    if(sForm.code_conferencename1.value         !="")parm+="&code_conferencename1="      + sForm.code_conferencename1.value;  
    if(sForm.center1.value                      !="")parm+="&center1="                   + sForm.center1.value; 
    if(sForm.register1.value                    !="")parm+="&register1="                 + escape(encodeURIComponent(sForm.register1.value));    
      
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"conference.do?method=conferenceList&parm="+parm}).trigger("reloadGrid");
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});

$(function() {
	$( "#datepicker2" ).datepicker();
});

function excelDown(){	
	var parm ="";   
	var excelFlag = sForm.excelFlag.value;
	
	if(excelFlag == "N"){
		sForm.excelFlag.value = "Y";
		return;
	}
	
    if(sForm.conference_dt1.value               !="")parm+="&conference_dt1="            + sForm.conference_dt1.value;    
    if(sForm.inqday1.value                      !="")parm+="&inqday1="                   + sForm.inqday1.value; 
    if(sForm.inqday2.value                      !="")parm+="&inqday2="                   + sForm.inqday2.value;  
    if(sForm.code_conferencename1.value         !="")parm+="&code_conferencename1="      + sForm.code_conferencename1.value;  
    if(sForm.center1.value                      !="")parm+="&center1="                   + sForm.center1.value; 
    if(sForm.register1.value                    !="")parm+="&register1="                 + escape(encodeURIComponent(sForm.register1.value));
    
    //alert("conference.do?method=conferenceListDown&parm="+parm);
	document.sForm.target="frm";
	document.sForm.action="conference.do?method=conferenceListDown&parm="+parm;
	document.sForm.submit(); 	
}

</script>
</head>

<body>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">회의실</a></li>		
		<li>&gt;</li>
		<li class="NPage">회의실관리</li>
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
      
      <input type="hidden" name="excelFlag" id="excelFlag" value="Y"/>
	  <table class="ctable_03" cellspacing="0" summary="회의실">       	
       		 <tr>
       		   <td class="nobg" align="center">회의 일자</td>
               <td class="nobg1a"><input type="text" name="conference_dt1" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px  value="" size="12" align="center" /></td>
               <td class="nobg2a" align="center">회의실</td>
               <td class="nobg1a">
                	<select  id="name" name="code_conferencename1">
                	<option value="">선택</option>		
              		 <% 
	                	String detCode1,detCName1=null;
	                	for(int i=0;i<comcodesearch.size();i++){
	                		if("032".equals(comcodesearch.get(i).get("groupcode").toString())){
	                			detCode1=comcodesearch.get(i).get("detcode").toString();
	                			detCName1=comcodesearch.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
	                		}
	                	}
               		 %>  
               	    </select>
			   </td>                
               <td class="nobg2a" align="center">순 번</td>
               <td class="nobg1a"><input type="text" id="name" name="conference_seq1" size="10" disabled="true"/></td> 
               <td class="nobg2a" align="center">조회 기간</td> 
			   <td class="nobg1a"><input type="text" name="inqday1" id="datepicker1"  class="input" style="width:70px;" style=padding-top:3px  style=padding-bottom:3px value=''/>						 
							     ~<input type="text" name="inqday2" id="datepicker2" class="input" style="width:70px;" style=padding-top:3px  style=padding-bottom:3px value=''/></td>		  
             </tr>             
			 <tr>
               <td class="alt1" align="center">사용 시간 </td>
               <td><select id="name" name="use_st_hhmm1">
	                <option value="08">08</option>
	                <option value="09">09</option>
	                <option value="10">10</option>
	                <option value="11">11</option>
	                <option value="12">12</option>
	                <option value="13">13</option>
	                <option value="14">14</option>
	                <option value="15">15</option>
	                <option value="16">16</option>
	                <option value="17">17</option>
	                <option value="18">18</option>
	                <option value="19">19</option>
	                <option value="20">20</option> 
	                <option value="21">21</option>
	                <option value="22">22</option>
	                <option value="23">23</option>
	                <option value="24">24</option>	         
                </select>&nbsp;시&nbsp;<select id="name" name="use_st_hhmm2">
	                <option value="00">00</option>
	                <option value="30">30</option>	                    
                </select>&nbsp;분&nbsp;</td>               
                <td class="alt" align="center">종료 시간 </td>
                <td><select id="name" name="use_ed_hhmm1">
	                <option value="08">08</option>
	                <option value="09">09</option>
	                <option value="10">10</option>
	                <option value="11">11</option>
	                <option value="12">12</option>
	                <option value="13">13</option>
	                <option value="14">14</option>
	                <option value="15">15</option>
	                <option value="16">16</option>
	                <option value="17">17</option>
	                <option value="18">18</option>
	                <option value="19">19</option>
	                <option value="20">20</option> 
	                <option value="21">21</option>
	                <option value="22">22</option>
	                <option value="23">23</option>
	                <option value="24">24</option>	               
                </select>&nbsp;시&nbsp;<select id="name" name="use_ed_hhmm2">
	                <option value="00">00</option>
	                <option value="30">30</option>	                    
                </select>&nbsp;분&nbsp;</td>
                <td class="alt" align="center">주관 부서</td>
                <td>
              		<select  id="name" name="center1" style="width:100px;">
              		<option value="">선택</option>		
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("031".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               	    </select>
				</td> 
				<td class="alt" align="center">신청자</td>
                <td><input type="text" id="name" name="register1" size="10"/></td>    
			 </tr>	
			 <tr>            	   			
                <td class="alt1" align="center">주 제</td>
                <td  colspan="7"><input type="text" id="name" name="subject1" size="146"/></td>                 
              </tr>
	 </table>
			 <ul class="btnIcon_55">	             
		  	   <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
			   <li><a href="javascript:goInsert();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
			   <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>	
			   <li><a href="javascript:goDelete();"><img src="images/icon_delete.gif" onclick="" alt="삭제" /></a></li> 
			   <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" alt="엑셀저장"/></li>
		 	 </ul>
		<!-- <div id="ts_tabmenu">
	         <ul>
	           <li><a href="conference.do?method=conference" class="overMenu"><strong><span>일자</span></strong></a></li>
		       <li><a href="conference.do?method=conferencemeet"><strong><span>회의실</span></strong></a></li>		     
			 </ul>
        </div>	   -->
	 </form>
<br><br>
	 
<table id="list"></table>
<div id="pager2"></div>
<!-- <form name="sForm2" method="post">
<p class="btnSave"><a href="#"><img src="images/icon_excel.gif" onclick="javascript:downloadExcel();" onmouseover="this.src=this.src.replace('_off.','_on.')" onmouseout="this.src=this.src.replace('_on.','_off.')"/></a></p>
</form> -->
</div>
</div>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>