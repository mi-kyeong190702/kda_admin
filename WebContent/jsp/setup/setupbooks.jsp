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
	String tag = "";
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
	  url: 'setupdues.do?method=setupbooksList',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'365',
      colNames: [ '기관지 코드','기관지 명','작성 주체','연 락 처','담당자 이름','담당자 직위','사용여부'],
      colModel: [
			{name:'code_book_kind',    index:'code_book_kind',     width: 80, editable: false, align: 'center'},
   			{name:'book_name',         index:'book_name',          width: 150,editable: false, align: 'center'},
   			{name:'book_maker',        index:'book_maker',         width: 120,editable: false, align: 'center'},
   			{name:'company_tel', 	   index:'company_tel',        width: 100,editable: false, align: 'center'},
   			{name:'pers_name',         index:'pers_name',          width: 100,editable: false, align: 'center'},
   			{name:'job_kind',          index:'job_kind',           width: 100,editable: false, align: 'center'},
   			{name:'use_yn',            index:'use_yn',             width: 50, editable: false, align: 'center'},   			
   		    ],
   		    rowNum:15,
   		    sortname: 'yyyymm',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: 'DM명',
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
	var meprogid = "705";
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
	tag = '';
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
				
		document.sForm.code_book_kind1.value      = list.code_book_kind;
		document.sForm.book_name1.value           = list.book_name;
		document.sForm.book_maker1.value          = list.book_maker;		
		document.sForm.company_tel1.value         = list.company_tel;
		document.sForm.pers_name1.value           = list.pers_name;
		document.sForm.job_kind1.value            = list.job_kind; 	
		document.sForm.use_yn1.value              = list.use_yn; 
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();
		tag = '*';
	});	
}

function goInsert(){	
	var  code_book_kind1  = sForm.code_book_kind1.value;
	var  book_name1       = escape(encodeURIComponent(sForm.book_name1.value));
	var  book_maker1      = escape(encodeURIComponent(sForm.book_maker1.value));
	var  company_tel1     = sForm.company_tel1.value;
	var  pers_name1       = escape(encodeURIComponent(sForm.pers_name1.value));
	var  job_kind1        = escape(encodeURIComponent(sForm.job_kind1.value));
	var  use_yn1          = sForm.use_yn1.value;
				
	    if ( code_book_kind1 == "" ) {
	        alert("기관지코드를 입력하세요.") ;
	        sForm.code_book_kind1.focus();
	        return ;
	    }   
	    
	    if ( book_name1 == "" ) {
	        alert("기관지명을 입력하세요.") ;
	        sForm.book_name1.focus();
	        return ;
	    }   
	    
	    if ( book_maker1 == "" ) {
	        alert("작성주체를 입력하세요.") ;
	        sForm.book_maker1.focus();
	        return ;
	    }
	    
	    if ( pers_name1 == "" ) {
	        alert("담당자이름을 입력하세요.") ;
	        sForm.pers_name1.focus();
	        return ;
	    } 	    
	
	 if ( tag == '*' ) {
	    	document.sForm.action = "setupdues.do?method=insert_books";
	    	document.sForm.submit();  
	 }else {
		   	document.sForm.action = "setupdues.do?method=update_books";
			document.sForm.submit();
	} 
} 

function goSearch(form,intPage){
var parm ="";
	
	if(sForm.code_book_kind1.value        !="")parm+="&code_book_kind1="       + sForm.code_book_kind1.value; 
	if(sForm.use_yn1.value                !="")parm+="&use_yn1="               + sForm.use_yn1.value; 	
							
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"setupdues.do?method=setupbooksList&parm="+parm}).trigger("reloadGrid");
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
		<li class="NPage">DM명</li>
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
	       
	  <table class="ctable_03" cellspacing="0" summary=""환경설정">                 			 
             <tr>
       		   <td class="nobga" align="center">DM 코드</td>
               <td class="nobg1a"><input type="text" id="name" name="code_book_kind1" size="15" align="center"/></td>      
               <td class="nobg2a" align="center">DM 명</td>
               <td class="nobg1a"><input type="text" id="name" name="book_name1" size="25" align="center"/></td>
               <td class="nobg2a" align="center">작성주체</td>
               <td class="nobg1a"><input type="text" id="name" name="book_maker1" size="25" align="center"/></td>
               <td class="nobg2a" align="center">연 락 처</td>
               <td class="nobg1a"><input type="text" id="name" name="company_tel1" size="25" align="center"/></td>
             </tr> 
             <tr>  
               <td class="alt1" align="center">담당자 명</td>
               <td><input type="text" id="name" name="pers_name1"   size="15" align="center"/></td> 
               <td class="alt" align="center">담당자 직위</td>
               <td><input type="text" id="name" name="job_kind1"    size="15" align="center"/></td> 
               <td class="alt" align="center" >사용 여부</td>
               <td ><select id="name" name="use_yn1" style="width: 100px"/>
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

  