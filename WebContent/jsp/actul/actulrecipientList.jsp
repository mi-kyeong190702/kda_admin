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
<%@ page import="com.ant.common.properties.AntData"%>

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
String g_id = session.getAttribute("G_ID").toString(); 
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
	  url: 'actul.do?method=actulrecipientList',
	  datatype: "json",
      mtype: 'post',
      width:'1100',
      height:'365',
      colNames: ['등록날짜',' 번호 ','상태','이름','면허번호','생년','성별','보수교육년도','미대상사유','메모','메일','첨부파일','파일url','미활동(미취업)코드','소속기관','활동내용','우편번호','주소','기타','상태코드'],
      colModel: [
			{name:'ar_regi_date',   		index:'ar_regi_date', 		width: 60, align: 'center'},
			{name:'code_seq',       		index:'code_seq',           hidden:true},
			   	
   			{name:'ar_state_kr',   			index:'ar_state_kr',        width: 50, editable:  false, align: 'center'},
   			{name:'pers_name',  			index:'pers_name',   		width: 80, editable:  false, align: 'center'},
   			{name:'lic_no',         		index:'lic_no',           	width: 80, editable:  false, align: 'center'},
   			{name:'pers_year',      		index:'pers_year',          width: 70, editable:  false, align: 'center'},
   			{name:'code_sex_kr',    		index:'code_sex_kr',        width: 70, editable:  false, align: 'center'},   			
   			{name:'cs_con_education',   	index:'cs_con_education',   width: 70, editable:  false, align: 'center'},  
   			{name:'cs_non_target',  		index:'cs_non_target',   	width: 70, editable:  false, align: 'center'},
   			{name:'memo',  					index:'memo',   	width: 70, editable:  false, align: 'center'},
   			{name:'email',  				index:'email',   			width: 200, editable:  false, align: 'center'},
   			{name:'cs_attachments',     	index:'cs_attachments',     width: 100, editable:  false, align: 'center'},
   			{name:'cs_attachments_url', 	index:'cs_attachments_url', hidden:true},
   			
   			{name:'cs_secter',  			index:'cs_secter',   		hidden:true},
   			{name:'cs_secter_kr',  			index:'cs_secter_kr',   	width: 100, editable:  false, align: 'center'},
   			{name:'cs_secter_organ',  		index:'cs_secter_organ',    width: 100, editable:  false, align: 'center'},
   			{name:'code_post',  			index:'code_post',   		width: 100, editable:  false, align: 'center'},
   			{name:'pers_add',  				index:'pers_add',   		width: 300, editable:  false, align: 'center'},
   			/* {name:'cs_non_target',   	index:'cs_non_target',   	width: 100, editable:  false, align: 'center'}, */
   			{name:'cs_non_target_detail',  index:'cs_non_target_detail',width: 100, editable:  false, align: 'center'},
   			{name:'ar_state',  				index:'ar_state',   		hidden:true}
   					
   			],
   		    rowNum:15,
   		   	pager: '#pager2',
	   		rownumbers: true,
	        viewrecords: true,
			multiselect: true,
			gridview: true,
			shrinkToFit:false,
 			caption: '보수교육 미대상자',
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
		
		document.sForm.pers_name1.value       = list.pers_name;
		document.sForm.lic_no1.value       = list.lic_no;
		document.sForm.atc_wrtr_cttno1.value          = list.atc_wrtr_cttno;
		document.sForm.email1.value     = list.atc_wrtr_eml;
		//document.sForm.ar_state1.value            = list.ar_state2;	
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
	
	if(sForm.pers_name1.value   !="")parm+="&pers_name1="       + escape(encodeURIComponent(sForm.pers_name1.value));	
	if(sForm.lic_no1.value   !="")parm+="&lic_no1="       + sForm.lic_no1.value; 
	if(sForm.email1.value   !="")parm+="&email1="       + sForm.email1.value; 
	if(sForm.ar_state1.value        !="")parm+="&ar_state1="       + sForm.ar_state1.value;  
	if(sForm.memo.value        !="")parm+="&memo="       + escape(encodeURIComponent(sForm.memo.value));
	
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"actul.do?method=actulrecipientList&parm="+parm}).trigger("reloadGrid");
}


function goSave(form,intPage){
	
	var id = jQuery("#list").jqGrid('getGridParam','selarrrow');
	
	var selVal = document.sForm.ar_state1.value;
	var to_memo = $.trim(document.sForm.memo.value);
	
	if(id==""){
		alert("저장할 항목을 선택하세요");
		return null;	
	}
	
	if(selVal == "" && to_memo == ""){
		alert("저장할 항목을 입력하세요");
		return null;
	}

	/* 	var data;
	for(var i=0; i<id.length;i++){
		data = jQuery("#list").jqGrid('getRowData',id[i]);
		if(data.ar_state == "1"){
			alert("이미 완료 된 항목은 다시 저장할 수 없습니다.");
			return;
		}
	} */
	
	var pers_name = new Array();
	var to_mail= new Array();
	var lic_no = new Array();
	var cs_con_education = new Array();
	var code_seq = new Array();
	var ar_regi_date = new Array();
		
	for(var i=0; i<id.length;i++){
		data = jQuery("#list").jqGrid('getRowData',id[i]);
		pers_name[i] = data.pers_name;
		to_mail[i] = data.email;
		lic_no[i] = data.lic_no;
		cs_con_education[i] = data.cs_con_education;
		code_seq[i] = data.code_seq;
		ar_regi_date[i] = data.ar_regi_date;
	}	
	
	$("#data_pers_name").val(pers_name);
	$("#data_to_mail").val(to_mail);
	$("#data_lic_no").val(lic_no);
	$("#data_cs_con_education").val(cs_con_education);
	$("#data_code_seq").val(code_seq );
	$("#data_ar_regi_date").val(ar_regi_date );
	$("#data_ar_state").val(document.sForm.ar_state1.value );
	$("#data_memo").val(to_memo);
	
	
	var d = new Date();
	var yyyy = d.getFullYear();
	$("#yyyy ").val(yyyy);
	document.sForm.action = "actul.do?method=recipientUpt";
	document.sForm.submit();
	
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
	
	if(sForm.pers_name1.value   	!="")param+="&pers_name1="    + escape(encodeURIComponent(sForm.pers_name1.value));	
	if(sForm.lic_no1.value        	!="")param+="&lic_no1="       + sForm.lic_no1.value; 
	if(sForm.email1.value   		!="")parm+="&email1="         + sForm.email1.value; 
	if(sForm.ar_state1.value        !="")param+="&ar_state1="     + sForm.ar_state1.value; 
	if(sForm.memo.value        		!="")param+="&memo="       	  + escape(encodeURIComponent(sForm.memo.value));
	
	
	var url="actul.do?method=excel_recipient"+param+"&code_seq="+code_seq;
	window.open(url,"exceldown","width=400, height=500, left=480, top=200,scrollbars=yes");	

}


function sendEmail(form) {
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

	if( rownum.length==0)	{
		alert("메일을 발송할 회원을 선택해 주십시요.");
		return;	
	}
		
 	var rowdata=new Array();
 	var param="";

 	for(var i=0;i<rownum.length;i++){
			
		rowdata= 	$("#list").getRowData(rownum[i]);
		
		if(rowdata.email.length>0){
			if(param.length > 0) param+= ",";
			param += rowdata.email;
		}
	}
 	
 	//param+="&paramfrom=memberSearchList&addr_infos="+addr_infos;
	
	window.open('actul.do?method=eMail&to_addr='+param,"email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
}

function sendUmsForm(form){
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 문자를 발송 할 수 없습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var pers_hp="";
 	var pers_name="";
 	
 	
 	//개별선택
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);

		if(rowdata.atc_wrtr_cttno.length>0) {
			if(pers_hp.length > 0){ pers_hp+= ","; pers_name+=",";}	
			pers_hp += rowdata.atc_wrtr_cttno;
			pers_name += rowdata.pers_name;
		}
	}

	if( rownum.length==0){	
		//여기다 전체 검색(검색과 같은 루트를 탄다.)
		
		//조건 검색에 쓰는 조건값만 기입
		var param = "";
		if(sForm.pers_name1.value   !="")param+="&pers_name1="       + escape(encodeURIComponent(sForm.pers_name1.value));
		if(sForm.lic_no1.value   !="")param+="&lic_no1="       + sForm.lic_no1.value;
		if(sForm.email1.value   !="")parm+="&email1="       + sForm.email1.value; 
		if(sForm.ar_state1.value        !="")param+="&ar_state1="       + sForm.ar_state1.value;			
		param+="&chk=ALL&menu=recipient";  
		
		//엑션
		var url="actul.do?method=sendUmsForm"+param;
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
		
	}else{
		var url="actul.do?method=sendUmsForm&chk=CHK&pers_hp="+pers_hp+"&pers_name="+escape(encodeURIComponent(pers_name));
		window.open(url,"notePad","width=370, height=550, left=280, top=50");	
	}
	
}


function fileDown(form){
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');
	if( rownum.length==0)	{
		alert("회원을 선택해 주십시요.");
		return;	
	}
	if( rownum.length>1)	{
		alert("회원을 한 명만 선택해 주십시요.");
		return;	
	}
	
	var id = jQuery("#list").jqGrid('getGridParam','selrow');
	var list = $("#list").getRowData(id);
	
	var fileUrl =list.cs_attachments_url;
	var fileName =list.cs_attachments;
	if(fileUrl == null || fileUrl == ""){
		alert("첨부된 파일이 없습니다.");
		return;
	}else{
		location.href="<%=AntData.DOMAIN%>/work/research/recipient/recipietnDown.do?fileUrl="+fileUrl+"&fileName="+fileName;
	}
	
}


function recipientForm(){
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');
	if( rownum.length==0)	{
		alert("회원을 선택해 주십시요.");
		return;	
	}
	if( rownum.length>1)	{
		alert("회원을 한 명만 선택해 주십시요.");
		return;	
	}
	
	var id = jQuery("#list").jqGrid('getGridParam','selrow');
	var list = $("#list").getRowData(id);
	
	var url="actul.do?method=recipientForm&code_seq="+list.code_seq;
	window.open(url,"recipientForm","scrollbars=yes,resizable=no,toolbar=no,width=780,height=900,left=20,top=29,status=no");
	
}

function recipientFormProc(){
	if(document.dataFrm.data2_code_seq.value   !=""){
		document.dataFrm.action= "actul.do?method=recipientFormProc";
		document.dataFrm.submit();
	}
}




</script>
</head>

<body>
<form name="dataFrm" method="post">
<input type="hidden" name="data2_code_seq" id="data2_code_seq" value=""/>
<input type="hidden" name="data2_pers_name" id="data2_pers_name" value=""/>
<input type="hidden" name="data2_cs_secter" id="data2_cs_secter" value=""/>
<input type="hidden" name="data2_cs_secter_organ" id="data2_cs_secter_organ" value=""/>
<input type="hidden" name="data2_email" id="data2_email" value=""/>
<input type="hidden" name="data2_code_post" id="data2_code_post" value=""/>
<input type="hidden" name="data2_pers_add" id="data2_pers_add" value=""/>
<input type="hidden" name="data2_cs_non_target" id="data2_cs_non_target" value=""/>
<input type="hidden" name="data2_cs_non_target_detail" id="data2_cs_non_target_detail" value=""/>
<input type="hidden" name="data2_lic_no" id="data2_lic_no" value=""/>
<input type="hidden" name="data2_pers_year" id="data2_pers_year" value=""/>
<input type="hidden" name="data2_authority_premises" id="data2_authority_premises" value=""/>
<input type="hidden" name="data2_code_sex" id="data2_code_sex" value=""/>
<input type="hidden" name="data2_cs_con_education" id="data2_cs_con_education" value=""/>
<input type="hidden" name="data2_pers_hp" id="data2_pers_hp" value=""/>
</form>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">영양사실태신고</a></li>
		<li>&gt;</li>
		<li class="NPage">보수교육 미대상자</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="sForm" method="post">
<input type="hidden" name="data_pers_name" id="data_pers_name" value=""/>
<input type="hidden" name="data_to_mail" id="data_to_mail" value=""/>
<input type="hidden" name="data_lic_no" id="data_lic_no" value=""/>
<input type="hidden" name="data_cs_con_education" id="data_cs_con_education" value=""/>
<input type="hidden" name="data_code_seq" id="data_code_seq" value=""/>
<input type="hidden" name="data_ar_state" id="data_ar_state" value=""/>
<input type="hidden" name="data_ar_regi_date" id="data_ar_regi_date" value=""/>
<input type="hidden" name="data_memo" id="data_memo" value=""/>

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

        <table class="ctable_03" cellspacing="0" summary="산하 단체명">			
            <tr>           	   
               <td class="nobga" align="center">성명</td>
               <td class="nobg1a"><input type="text" id="pers_name1" name="pers_name1" size="15" /></td> 
               <td class="nobg2a" align="center">면허번호</td>
               <td class="nobg1a"><input type="text" id="lic_no1" name="lic_no1" size="15" /></td> 
               <td class="nobg2a" align="center">이메일</td>
			   <td class="nobg1a"><input type="text" id="email1" name="email1" size="15" /></td>
               <td class="nobg2a" align="center">상태</td>
               <td class="nobg1a"><select  id="ar_state1" name="ar_state1">
               <option value="">선택</option>
               <option value="0">접수중</option>
               <option value="1">완료</option>
               <option value="2">보류</option>
               <option value="3">회송</option>
               </select></td>				               		
			</tr>
			<tr>           	   
               <td class="nobga" align="center">메모</td>
               <td class="nobg3a" colspan="7"><input type="text" id="memo" name="memo" style="width: 98%"/></td> 
			</tr>
		 </table>			
    	 <ul class="btnIcon_2" style="left:983px;">
    	 	<li><a href="#"><img src="images/icon_save.gif" onclick="javascript:goSave(sForm,0);" alt="저장" /></a></li>	             
		   	<li><a href="#"><img src="images/icon_search.gif" onclick="javascript:goSearch(sForm,0);" alt="검색" /></a></li>	 
		 </ul>	
</form>
<br><br>

<table id="list"></table>
<div id="pager2"></div>

<br/>
			<ul class="btnIcon_1" style="left:780px;">
				<li><a href="javascript:recipientForm();"><img src="images/icon_s5.gif" alt="상세검색"></a></li>
				<li><a href="javascript:excelDown(sForm);"	><img src="images/icon_excel.gif"	alt="엑셀저장"	/></a></li>
				<li><a href="javascript:sendEmail(sForm);"	><img src="images/icon_s3.gif"		alt="메일전송"	/></a></li>
				<li><a href="javascript:fileDown(sForm);"	><img src="images/icon_s13.gif"		alt="첨부문서다운"	/></a></li>
				<!-- <li><a href="javascript:sendUmsForm(sForm);"	><img src="images/icon_s4.gif"		alt="문자전송"	/></a></li> -->
			</ul>
			<br/><br/><br/>
			<div>
				*미대상 사유 코드 설명<br/><br/>
				1. 국민영양관리법에 의한 영양사 보수교육 대상 근무처(보건소, 보건지소, 의료기관, 집단급식소, 육아지원종합센터, 어린이급식관리지원센터, 건강기능식품판매업소) 외에 근무하고 있음.<br/>
				2. 대학원 진학 등 학업 계속 상태로 미취업 상태임.<br/>
				3. 현재 미취업, 면허 취득 후 임신, 출산 등으로 인한 퇴직, 취업경험 없음 등<br/>
				4. 기타
			</div>
			
			
</div>
</div>
</body>
</html>

  