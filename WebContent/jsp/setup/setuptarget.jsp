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
		  url: 'setupdues.do?method=setuptargetList',
		  datatype: "json",
	      mtype: 'post',      
	      width:'1100',
	      height:'365',
	      colNames: [ '기준년도','소속지부','소속지부코드','소속분과','소속분과코드','목표인원'],
	      colModel: [
				{name:'yyyy', 	     	index:'yyyy',  		     width: 100,editable: false, align: 'center'},									
				{name:'code_branname',  index:'code_branname',   width: 100,editable: false, align: 'center'},	
				{name:'code_bran', 		index:'code_bran',  	 hidden:true},
				{name:'code_bigname',   index:'code_bigname',    width: 100,editable: false, align: 'center'},			
				{name:'code_big',       index:'code_big',        hidden:true},
				{name:'target_cnt',     index:'target_cnt',      width: 100,editable: false, align: 'center'},		
				],
   		    rowNum:15,
   		   	pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '목표 인원',
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
	var meprogid = "702";
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
		
		document.sForm.yyyy1.value             = list.yyyy;		
		document.sForm.code_bran1.value        = list.code_bran;
		document.sForm.code_big1.value         = list.code_big;
		document.sForm.target_cnt1.value       = list.target_cnt;		
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();
		tag = '*';
	});	
}

function goInsert(){
	var  yyyy1   	  = sForm.yyyy1.value;
	var  code_bran1   = sForm.code_bran1.value;
	var  code_big1    = sForm.code_big1.value;
	var  target_cnt1  = sForm.target_cnt1.value;	
	
	if ( yyyy1 == "" ) {
        alert("기준년도를 선택하세요.") ;
        sForm.yyyy1.focus();
        return ;
    }   
	
    if ( code_bran1 == "" ) {
        alert("소속지부를 선택하세요.") ;
        sForm.code_bran1.focus();
        return ;
    }   
    
    if ( code_big1 == "" ) {
        alert("소속분과를 선택하세요.") ;
        sForm.code_big1.focus();
        return ;
    }   
    
    if ( target_cnt1 == "" ) {
        alert("목표인원을 선택하세요.") ;
        sForm.target_cnt1.focus();
        return ;
    }   
    
    var parm = "&yyyy1="         +  yyyy1         +
			   "&code_bran1="    +  code_bran1    +
			   "&code_big1="     +  code_big1     +
			   "&target_cnt1="   +  target_cnt1;	   
    
    if ( tag == '*' ) { 
    	document.sForm.action = "setupdues.do?method=insert_target&parm="+parm;
    	document.sForm.submit();  
    }else { 
    	document.sForm.action = "setupdues.do?method=update_target&parm="+parm;
    	document.sForm.submit();
	 }	
}

function goSearch(form,intPage){
		
	var parm =""; 
	
    if(sForm.yyyy1.value          !="")parm+="&yyyy1="       + sForm.yyyy1.value;     
    if(sForm.code_bran1.value     !="")parm+="&code_bran1="  + sForm.code_bran1.value;    
    if(sForm.code_big1.value      !="")parm+="&code_big1="   + sForm.code_big1.value; 
 
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"setupdues.do?method=setuptargetList&parm="+parm}).trigger("reloadGrid");
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
		<li class="NPage">목표 인원</li>
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

        <table class="ctable_03" cellspacing="0" summary="목표 인원">			
             <tr>
       		   <td class="nobga" align="center">기준 년도</td>
       		   <td class="nobg1a">
               <select  id="name" name="yyyy1" >
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
               <td class="nobg2a" align="center">소속 지부</td>
               <td class="nobg1a">
               <select  id="name" name="code_bran1">
               <option value="">선택</option>	
              		 <% 
                	String detCode1,detCName1=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("007".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode1=comcodesearch.get(i).get("detcode").toString();
                			detCName1=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td> 
			   <td class="nobg2a" align="center">소속 분과</td>
       		   <td class="nobg1a">
               <select  id="name" name="code_big1">
               <option value="">선택</option>	
              		 <% 
                	String detCode2,detCName2=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("004".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode2=comcodesearch.get(i).get("detcode").toString();
                			detCName2=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode2+">"+detCName2+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td> 		
               <td class="nobg2a" align="center">목표 인원</td>
               <td class="nobg1a"><input type="text" id="name" name="target_cnt1" size="15" align="center"/></td>	
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

  