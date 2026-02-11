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
<link rel="stylesheet" type="text/css" href="css/report.css" />
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
	  //url: 'actul.do?method=edusearchList',
	  url: 'local',
	  datatype: "json",
      mtype: 'post',     
      width:'1100',
      height:'365',
      colNames: [ '년도','yyyy_seq','이름','면허번호','ke_seq','제목','보수교육기간','이수시간','재수강여부','총시간','ke_finish_dt','ke_finish_no','이수','ke_birth'],
      colModel: [
			{name:'yyyy',              index:'yyyy',              width: 50, editable: false, align: 'center'},
			{name:'yyyy_seq',          index:'yyyy_seq',          hidden:true},
   			{name:'ke_name',           index:'ke_name',           width: 100,editable: false, align: 'center'},
   			{name:'ke_lic_no',             index:'ke_lic_no',             width: 150,editable: false, align: 'center'},
   			{name:'ke_seq',         index:'ke_seq',         hidden:true},
   			{name:'ke_subject', 	   index:'ke_subject',      width:200,editable:  false, align: 'center'},
   			{name:'ke_fromto',           index:'ke_fromto',           width: 150,editable:  false, align: 'center'},
   			{name:'ke_isutime', index:'ke_isutime', width: 180,editable:  false, align: 'center'},
   			{name:'ke_jesukaing',      index:'ke_jesukaing',      width: 180,editable:  false, align: 'center'},
   			{name:'ke_total', index:'ke_total', width: 150,editable:  false, align: 'center'},
   			{name:'ke_finish_dt',      index:'ke_finish_dt',      hidden:true},   			
   			{name:'ke_finish_no',             index:'ke_finish_no',             hidden:true},
   			{name:'ke_finish_yn',         index:'ke_finish_yn',         width: 100,editable:  false, align: 'center'},
   			{name:'ke_birth',   index:'ke_birth',  hidden:true},		
   			],
   		    rowNum:15,
   		   	pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
            multiselect: true,
 			gridview: true,
 			/* shrinkToFit:false, */
 			caption: '보수교육관리',
			altclass:'myAltRowClass',
			
			onSelectRow: function(ids) {				
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
	var meprogid = "601";
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

function goSearch1(form,intPage){
	
	var gr = jQuery("#list").jqGrid('getGridParam','selarrrow');
	
	if( gr.length == 0){			alert("상세검색을 원하는 회원을 선택해 주십시요.");		    return;		}
 	if( gr.length > 1)	{		  	alert("상세검색은 한명의 회원만 선택해야 합니다.");			return;		}
 	
	if( gr != null ) {
		var list = $("#list").getRowData(gr);		
		var parm = "&ar_name="            +  escape(encodeURIComponent(list.ar_name))       +
		  		   "&yyyy="               +  list.yyyy                                      +
				   "&ar_lic_no="          +  list.ar_lic_no;
		
		window.open("actul.do?method=actulconfirmSearch&isSelect=N"+parm);		 	
	}
}

function goSearch(form,intPage){	
	var parm ="";	

	 if ( sForm.yyyy1.value == "" ) {
	        alert("수강 년도를 선택하세요.") ;
	        sForm.yyyy1.focus();
	        return ;
	 }	 
	 if ( sForm.ke_name1.value == "" && sForm.ke_lic_no1.value == "") {
	        alert("이름, 면허번호 중 한 가지는 꼭 입력하셔야 됩니다..") ;
	        return ;
	 }	 
	
    if(sForm.ke_name1.value          !="")parm+="&ke_name1="       + escape(encodeURIComponent(sForm.ke_name1.value));  
    if(sForm.yyyy1.value             !="")parm+="&yyyy1="          + sForm.yyyy1.value;  
    if(sForm.ke_finish_yn1.value          !="")parm+="&ke_finish_yn1="       + sForm.ke_finish_yn1.value; 
    if(sForm.ke_lic_no1.value     !="")parm+="&ke_lic_no1="  + sForm.ke_lic_no1.value;  
    
   	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"actul.do?method=edusearchList&parm="+parm}).trigger("reloadGrid");
}

//메일 발송
function emailsend(){	
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

		if( rownum.length==0)	{			alert("메일을 발송할 회원을 선택해 주십시요.");			return;		}
	 	var rowdata=new Array();
	 	var email="";
		for(var i=0;i<rownum.length;i++){
			rowdata= 	$("#list").getRowData(rownum[i]);
			if(i==0){
				email	=rowdata.email;	
			}else{
				email	= email+","+rowdata.email;
			}
		}
		
	var url="document.do?method=eMail&toAddr="+email;	
	window.open(url,"Email","scrollbar=no,resizable=no,toolbar=no,width=675,height=700,left=20,top=29,status=yes");
}

/* 문자 전송 */
function umsData(){	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var ar_tel_hp=""; 
 	var ar_name="";
 	
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);
		if(i==0){
			ar_tel_hp      =  rowdata.ar_tel_hp;
			ar_name		   =  rowdata.ar_name;
		}else{
			ar_tel_hp	   =  ar_tel_hp+","+rowdata.ar_tel_hp;
			ar_name        =  ar_name+","+rowdata.ar_name;
		}
	}	
		
	if( rownum.length==0){		
		var parm ="";
	    if(sForm.ar_name1.value          !="")parm+="&ar_name1="       + escape(encodeURIComponent(sForm.ar_name1.value));  
	    if(sForm.yyyy1.value             !="")parm+="&yyyy1="          + sForm.yyyy1.value;  
	    if(sForm.ar_add11.value          !="")parm+="&ar_add11="       + sForm.ar_add11.value; 
	    if(sForm.ar_code_part1.value     !="")parm+="&ar_code_part1="  + sForm.ar_code_part1.value;  
	    if(sForm.ar_code_big11.value     !="")parm+="&ar_code_big11="  + sForm.ar_code_big11.value; 
	    if(sForm.ar_state1.value         !="")parm+="&ar_state1="      + sForm.ar_state1.value;
		var isSelect = "Y";			
		ar_tel_hp = "All";			
		var url="actul.do?method=sendumsDataall&parm="+parm+"&ar_tel_hp="+ar_tel_hp+"&isSelect="+isSelect;
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
	}else{		
		var url="actul.do?method=sendumsData&ar_tel_hp="+ar_tel_hp+"&ar_name="+escape(encodeURIComponent(ar_name));
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
	}
}

function excelDown(){		
	var parm ="";	

	 if ( sForm.yyyy1.value == "" ) {
	        alert("신고 년도를 선택하세요.") ; 
	        sForm.yyyy1.focus();
	        return ;
	    }	 
	
   if(sForm.ar_name1.value          !="")parm+="&ar_name1="       + escape(encodeURIComponent(sForm.ar_name1.value));  
   if(sForm.yyyy1.value             !="")parm+="&yyyy1="          + sForm.yyyy1.value;  
   if(sForm.ar_add11.value          !="")parm+="&ar_add11="       + sForm.ar_add11.value; 
   if(sForm.ar_code_part1.value     !="")parm+="&ar_code_part1="  + sForm.ar_code_part1.value;  
   if(sForm.ar_code_big11.value     !="")parm+="&ar_code_big11="  + sForm.ar_code_big11.value; 
   if(sForm.ar_state1.value         !="")parm+="&ar_state1="      + sForm.ar_state1.value;
	
   var url="actul.do?method=actulpers_no"+parm;
   window.open(url,"pers_no","width=339, height=280, left=480, top=200");	
}

function upExcel(){	
	/* var gr = jQuery("#list").jqGrid('getGridParam','selarrrow');
	
	if( gr.length == 0){			alert("엑셀올리기를 원하는 회원을 선택해 주십시요.");		    return;		}
 	if( gr.length > 1)	{		  	alert("엑셀올리기를 한명의 회원만 선택해야 합니다.");			return;		} 
 	
 	if( gr != null ) {
		var list = $("#list").getRowData(gr);		
		var parm = "&yyyy="               +  list.yyyy                                      +
				   "&ar_lic_no="          +  list.ke_lic_no;
 	}
 	
	var url="actul.do?method=upExamSendList&parm="+parm; 
	popup = window.open(url,"ar_lic_no","scrollbar=no,resizable=no,toolbar=no,width=330,height=300,left=20,top=29,status=no","left=0");
	*/
	
	var url="actul.do?method=uploadEduExcel";
	popup = window.open(url,"uploadEduExcel","scrollbar=no,resizable=no,toolbar=no,width=330,height=300,left=20,top=29,status=no","left=0");	
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
		<li class="NPage">신고관리</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="rTabmenu_01">
<form id="list1" name="sForm" method="post">
    <table class="rtable_01" cellspacing="0" summary="영양사실태신고">			
       <!--  <div id="container"> -->
	         <caption>신고관리</caption>             
             <tr>
               <td class="nobg"  size ="16" align="center">수강 년도</td>               			   
			   <td class="nobg1" size ="16" align="center">이름</td>
			   <td class="nobg1" size ="16" align="center">면허번호</td>
			   <td class="nobg1" size ="16" align="center">상태</td> 			  
			 </tr> 			 
			 <tr>
			  <td class="alt" >
               <select  id="search" name="yyyy1" style="width:120px;">
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
               <td><input type="text" id="name" name="ke_name1" size="16" /></td>
               <td><input type="text" id="name" name="ke_lic_no1" size="16" /></td>
               <td>
               <select  id="search" name="ke_finish_yn1" style="width:120px;">
               <option value="">선택</option>	
               <option value="Y">이수</option>	
               <option value="N">미이수</option>	
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
		<ul class="btnIcon_33"  style="left: 978px;">		  
		  <li><a href="javascript:upExcel();"><img src="images/icon_s16_2.gif" alt="보수교육 엑셀올리기" /></a></li>
		  <!-- <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" alt="엑셀저장" /></a></li>
		  <li><a href="javascript:umsData();"><img src="images/icon_s4.gif" alt="문자전송" /></a></li>		 
		  <li><a href="javascript:emailsend();"><img src="images/icon_s3.gif" alt="메일전송" /></a></li>	
		  <li><a href="#"><img src="images/icon_s5.gif" onclick="javascript:goSearch1(sForm,0);" alt="상세검색" /></a></li> -->				   	
		</ul>
</form>
</div>
</div>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>
  