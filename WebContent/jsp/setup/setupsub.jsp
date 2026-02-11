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
	  url: 'setupdues.do?method=setupsubList',
	  datatype: "json",
      mtype: 'post',
      width:'1100',
      height:'365',
      colNames: [ ' 코드 ','산하단체명','전화번호','팩스','담당자','이메일','메일업체','메일업체코드','계좌번호','은행명','입금계좌명','사용유무'],
      colModel: [
			{name:'code_sub',         index:'code_sub',          width: 100,editable:  false, align: 'center'},
   			{name:'sub_name',         index:'sub_name',          width: 150,editable:  false, align: 'left'},
   			{name:'sub_tel',          index:'sub_tel',           width: 100,editable:  false, align: 'center'},
   			{name:'sub_fax', 	      index:'sub_fax',           width: 100,editable:  false, align: 'center'},
   			{name:'sub_master',       index:'sub_master',        width: 100,editable:  false, align: 'center'},
   			{name:'sub_email',        index:'sub_email',         width: 100,editable:  false, align: 'left'},   			   
   			{name:'code_email_name',  index:'code_email_name',   width: 150,editable:  false, align: 'left'},
   			{name:'code_email_comp',  index:'code_email_comp',   hidden:true},
   			{name:'sub_account', 	  index:'sub_account',       width: 100,editable:  false, align: 'left'},
   			{name:'sub_bank',         index:'sub_bank',          width: 100,editable:  false, align: 'center'},
   			{name:'sub_bank_ints',    index:'sub_bank_ints',     width: 100,editable:  false, align: 'left'},
   			{name:'use_yn',           index:'use_yn',            width: 50,editable:  false, align: 'center'},
   			],
   		    rowNum:15,
   		   	pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '산하 단체',
			altclass:'myAltRowClass',
			
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
	var meprogid = "708";
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
	goClear();
	tag = '';
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
		
		document.sForm.code_sub1.value          = list.code_sub;
		document.sForm.sub_name1.value          = list.sub_name;
		document.sForm.sub_tel1.value           = list.sub_tel;
		document.sForm.sub_fax1.value           = list.sub_fax;
		document.sForm.sub_master1.value        = list.sub_master;
		document.sForm.sub_email1.value         = list.sub_email;
		document.sForm.code_email_comp1.value   = list.code_email_comp;
		document.sForm.sub_account1.value       = list.sub_account;
		document.sForm.sub_bank1.value          = list.sub_bank;
		document.sForm.sub_bank_ints1.value     = list.sub_bank_ints;
		document.sForm.use_yn1.value            = list.use_yn;	
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();
		tag = '*';
	});	
}

function goInsert(){
	var  code_sub1   = sForm.code_sub1.value;
	var  sub_name1   = sForm.sub_name1.value;
	var  use_yn1     = sForm.use_yn1.value;	
	
    if ( code_sub1 == "" ) {
        alert("코드를 입력하세요.") ;
        sForm.code_sub1.focus();
        return ;
    }   
    
    if ( sub_name1 == "" ) {
        alert("산하단체명을 입력하세요.") ;
        sForm.sub_name1.focus();
        return ;
    }   
    
    if ( tag == '*' ) { 
    	document.sForm.action = "setupdues.do?method=insertsub_sub";
    	document.sForm.submit();  
    }else { 
    	document.sForm.action = "setupdues.do?method=updatesub_sub";
    	document.sForm.submit();
	 }	
}

function goSearch(form,intPage){
	var parm ="";
	
	if(sForm.use_yn1.value        !="")parm+="&use_yn1="       + sForm.use_yn1.value; 
	
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"setupdues.do?method=setupsubList&parm="+parm}).trigger("reloadGrid");
}

</script>
</head>

<body>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">환경설정</a></li>
		<li>&gt;</li>
		<li class="NPage">산하 단체명</li>
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

        <table class="ctable_03" cellspacing="0" summary="산하 단체명">			
            <tr>           	   
               <td class="nobga" align="center">코 드</td>
               <td class="nobg1a"><input type="text" id="m" name="code_sub1" size="15" /></td> 
               <td class="nobg2a" align="center">산하 단체명</td>
               <td class="nobg1a"><input type="text" id="m" name="sub_name1" size="15" /></td> 
               <td class="nobg2a" align="center">전화 번호</td>
			   <td class="nobg1a"><input type="text" id="m" name="sub_tel1" size="15" /></td>
			   <td class="nobg2a" align="center">팩 스</td>
			   <td class="nobg1a"><input type="text" id="m" name="sub_fax1" size="15" /></td>
			</tr>
			<tr>   
			   <td class="alt1" align="center">담당자</td>
               <td><input type="text" id="m" name="sub_master1" size="15" /></td> 
               <td class="alt" align="center">이메일</td>
               <td><input type="text" id="m" name="sub_email1" size="15" /></td> 
               <td class="alt" align="center">메일 업체</td>
               <td><select  id="name" name="code_email_comp1">              			
              		<% 
                	String detCode,detCName=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("025".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               </select></td>				               		
			   <td class="alt" align="center">계좌 번호</td>
			   <td><input type="text" id="m" name="sub_account1" size="15" /></td>
			</tr>  
			<tr>   
			   <td class="alt1" align="center">은행명</td>
               <td><input type="text" id="m" name="sub_bank1" size="15" /></td> 
               <td class="alt" align="center">입금 계좌명</td>
               <td><input type="text" id="m" name="sub_bank_ints1" size="15" /></td> 
               <td class="alt" align="center" >사용 여부</td>        
               <td><select id="name" name="use_yn1" style="width: 100px"/>
             			<option value="">전체</option>
	                    <option value="Y">Y</option>
		                <option value="N">N</option>	                
                    </select></td>	
			  <td class="alt" align="center">비  고</td>
			  <td></td> 
			</tr>
		 </table>			
    	 <ul class="btnIcon_2">	             
	  	   <li><a href="#"><img src="images/icon_new.gif"    onclick="javascript:goClear();" alt="신규" /></a></li>		 
		   <li><a href="#"><img src="images/icon_save.gif"   onclick="javascript:goInsert();" alt="저장" /></a></li>
		   <li><a href="#"><img src="images/icon_search.gif" onclick="javascript:goSearch(sForm,0);" alt="검색" /></a></li>	 
		 </ul>	
</form>
<br><br>

<table id="list"></table>
<div id="pager2"></div>
</div>
</div>
</body>
</html>

  