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
	  url: 'document.do?method=dmsendList',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'365',
      colNames: [ '구독자코드','구독구분','구독구분코드','신청일','기간 시작','기간 종료','근무처 명','연락처','이름','직위','발송호칭','발송호칭코드','우편번호','주소','상세주소','사용'],
      colModel: [
			{name:'code_book',          index:'code_book',         hidden:true},
   			{name:'book_flag_name',     index:'book_flag_name',    width: 80, editable: false, align: 'center'},
   			{name:'book_flag',          index:'book_flag',         hidden:true},
   			{name:'book_receipt_dt',    index:'book_receipt_dt',   width: 80, editable: false, align: 'center'},
   			{name:'book_start_dt',      index:'book_start_dt',     width: 80, editable: false, align: 'center'},
   			{name:'book_end_dt',        index:'book_end_dt',       width: 80, editable: false, align: 'center'},
   			{name:'company_name',       index:'company_name',      width: 100, editable: false, align: 'center'},
   			{name:'company_tel',        index:'company_tel',       width: 80, editable: false, align: 'center'},		
   			{name:'pers_name',          index:'pers_name',         width: 60, editable: false, align: 'center'},	
   			{name:'job_kind', 	        index:'job_kind',          width: 60, editable: false, align: 'center'},
   			{name:'code_call_name',     index:'code_call_name',    width: 100, editable: false, align: 'center'},
   			{name:'code_call',          index:'code_call',         hidden:true},
   			{name:'code_post',          index:'code_post',         width: 60, editable: false, align: 'center'},		
   			{name:'pers_add',           index:'pers_add',          width: 200,editable: false, align: 'left'},	
   			{name:'pers_add1', 	        index:'pers_add1',         hidden:true},
   			{name:'use_yn',             index:'use_yn',            width: 30, editable: false, align: 'center'},		 
   			],
   		    rowNum:15,
   		    sortname: 'book_receipt_dt',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '기타 발송자',
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
	var meprogid = "405";
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
				
		document.mem.code_book1.value        = list.code_book;
		document.mem.book_flag1.value        = list.book_flag;
		document.mem.book_receipt_dt1.value  = list.book_receipt_dt;
		document.mem.book_start_dt1.value    = list.book_start_dt;
		document.mem.book_end_dt1.value      = list.book_end_dt;
		document.mem.company_name1.value     = list.company_name;		
		document.mem.company_tel1.value      = list.company_tel;
		document.mem.pers_name1.value        = list.pers_name;
		document.mem.job_kind1.value         = list.job_kind; 	
		document.mem.code_call1.value        = list.code_call;
		document.mem.m_post.value            = list.code_post;		
		document.mem.m_address.value         = list.pers_add;
		document.mem.d_address.value         = list.pers_add1;
		document.mem.use_yn1.value           = list.use_yn; 	
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();		
	});	
}

function goInsert(){
		
	var code_book1         = mem.code_book1.value;
    var book_flag1         = mem.book_flag1.value;
	var book_receipt_dt1   = mem.book_receipt_dt1.value;
	var book_start_dt1	   = mem.book_start_dt1.value;
	var book_end_dt1	   = mem.book_end_dt1.value;
	var company_name1	   = mem.company_name1.value;
	var company_tel1   	   = mem.company_tel1.value;
	var pers_name1		   = mem.pers_name1.value;	
	var job_kind1		   = mem.job_kind1.value;
	var code_call1	       = mem.code_call1.value;
	var m_post		       = mem.m_post.value;
	var m_address	       = mem.m_address.value;
	var d_address   	   = mem.d_address.value;
	var use_yn1		       = mem.use_yn1.value;
		 	
    if ( book_receipt_dt1 == "" ) {
        alert("구독신청일을 입력해주세요.") ;
        mem.book_receipt_dt1.focus();
        return ;
    }
    if ( book_start_dt1 == "" ) {
    	alert("구독 시작일을 입력해주세요.") ;
    	mem.book_start_dt1.focus();
        return ;
    } 
    if ( book_end_dt1 == "" ) {
        alert("구독종료일을 입력해주세요.") ;
        mem.book_end_dt1.focus();
        return ;
    }
    if ( book_flag1 == "" ) {
        alert("구독구분을 입력해주세요.") ;
        mem.book_flag1.focus();
        return ;
    }
    if ( company_name1 == "" ) {
    	alert("근무처명을 입력해주세요.") ;
    	mem.company_name1.focus();
        return ;
    }     
    if ( company_tel1 == "" ) {
        alert("연락처를 입력해주세요.") ;
        mem.company_tel1.focus();
        return ;
    } 
    if ( use_yn1 == "" ) {
        alert("사용여부를 입력해주세요.") ;
        mem.use_yn1.focus();
        return ;
    } 
    if ( pers_name1 == "" ) {
        alert("이름을 입력해주세요.") ;
        mem.pers_name1.focus();
        return ;
    }
  	if ( job_kind1 == "" ) {
        alert("직위를 입력해주세요.") ;
        mem.job_kind1.focus();
        return ;
    }
    if ( code_call1 == "" ) {
    	alert("발송호칭을 입력해주세요.") ;
    	mem.code_call1.focus();
        return ;
    } 
    if ( m_post == "" ) {
        alert("우편번호를 입력해주세요.") ;
        mem.m_post.focus();
        return ;
    }
    if ( m_address == "" ) {
    	alert("주소를 입력해주세요.") ;
    	mem.m_address.focus();
        return ;
    }   
    if ( d_address == "" ) {
    	alert("상세주소를 입력해주세요.") ;
    	mem.d_address.focus();
        return ;
    }  
    if ( code_book1 == "" ) { 
    	document.mem.action = "document.do?method=insert_dmsend";
    	document.mem.submit();  
    }else { 
    	document.mem.action = "document.do?method=update_dmsend&code_book1="+code_book1;
    	document.mem.submit();
	 }	
}

function goSearch(form,intPage){
	var parm ="";	
		
    if(mem.company_name1.value          !="")parm+="&company_name1="       + escape(encodeURIComponent(mem.company_name1.value));
    if(mem.pers_name1.value             !="")parm+="&pers_name1="          + escape(encodeURIComponent(mem.pers_name1.value));
    if(mem.job_kind1.value              !="")parm+="&job_kind1="           + escape(encodeURIComponent(mem.job_kind1.value));
    if(mem.code_call1.value             !="")parm+="&code_call1="          + escape(encodeURIComponent(mem.code_call1.value));
    if(mem.m_address.value              !="")parm+="&m_address="           + escape(encodeURIComponent(mem.m_address.value));
    if(mem.d_address.value              !="")parm+="&d_address="           + escape(encodeURIComponent(mem.d_address.value));    
    if(mem.book_flag1.value             !="")parm+="&book_flag1="          + mem.book_flag1.value;  
    if(mem.book_receipt_dt1.value       !="")parm+="&book_receipt_dt1="    + mem.book_receipt_dt1.value; 
    if(mem.book_start_dt1.value         !="")parm+="&book_start_dt1="      + mem.book_start_dt1.value;  
    if(mem.book_end_dt1.value           !="")parm+="&book_end_dt1="        + mem.book_end_dt1.value;    
    if(mem.company_tel1.value           !="")parm+="&company_tel1="        + mem.company_tel1.value;     
    if(mem.m_post.value          		!="")parm+="&m_post="              + mem.m_post.value;    
    if(mem.use_yn1.value                !="")parm+="&use_yn1="             + mem.use_yn1.value;  
  
  	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"document.do?method=dmsendList&parm"+parm}).trigger("reloadGrid");
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

function postSearch(){
	//window.open('document.do?method=postSearch&sel=N&keyword=',"postnum","scrollbar=no,resizable=no,toolbar=no,width=675,height=500,left=20,top=29,status=no");
	window.open('memberBasic.do?method=postSearch&sel=6',"postnum","scrollbars=yes,menubar=no,resizable=no,toolbar=no,width=440,height=400,status=no");
}

function excelDown(){		
	var parm ="";	
	
    if(mem.company_name1.value          !="")parm+="&company_name1="       + escape(encodeURIComponent(mem.company_name1.value));
    if(mem.pers_name1.value             !="")parm+="&pers_name1="          + escape(encodeURIComponent(mem.pers_name1.value));
    if(mem.job_kind1.value              !="")parm+="&job_kind1="           + escape(encodeURIComponent(mem.job_kind1.value));
    if(mem.code_call1.value             !="")parm+="&code_call1="          + escape(encodeURIComponent(mem.code_call1.value));
    if(mem.m_address.value              !="")parm+="&m_address="           + escape(encodeURIComponent(mem.m_address.value));
    if(mem.d_address.value              !="")parm+="&d_address="           + escape(encodeURIComponent(mem.d_address.value));    
    if(mem.book_flag1.value             !="")parm+="&book_flag1="          + mem.book_flag1.value;  
    if(mem.book_receipt_dt1.value       !="")parm+="&book_receipt_dt1="    + mem.book_receipt_dt1.value; 
    if(mem.book_start_dt1.value         !="")parm+="&book_start_dt1="      + mem.book_start_dt1.value;  
    if(mem.book_end_dt1.value           !="")parm+="&book_end_dt1="        + mem.book_end_dt1.value;    
    if(mem.company_tel1.value           !="")parm+="&company_tel1="        + mem.company_tel1.value;     
    if(mem.m_post.value          		!="")parm+="&m_post="              + mem.m_post.value;    
    if(mem.use_yn1.value                !="")parm+="&use_yn1="             + mem.use_yn1.value;  
	
	document.mem.target="frm";
	document.mem.action="document.do?method=dmsendDown&parm="+parm;
	document.mem.submit();
}

</script>
</head>

<body>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">문서</a></li>		
		<li>&gt;</li>
		<li class="NPage">DM 관리</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="mem" method="post">  

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
      
	  <table class="ctable_03" cellspacing="0" summary="문서">
       	 <div id="ts_tabmenu">
	         <ul>
	           <li><a href="document.do?method=dmsendData" class="overMenu"><strong><span>기타 발송자</span></strong></a></li>
		       <li><a href="document.do?method=dmsendreportData"><strong><span>발송대장</span></strong></a></li>		     
			 </ul>
        </div>	       
		      <tr>
               <td class="nobga" align="center">구독신청일</td>
               <td class="nobg1a"><input type="text" name="book_receipt_dt1" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px  value="" align="center" /></td>
			   <td class="nobg2a" align="center">구독시작일</td>
			   <td class="nobg1a"><input type="text" name="book_start_dt1" id="datepicker1" class="input" style=padding-top:3px  style=padding-bottom:3px value="" align="center" /></td>
			   <td class="nobg2a" align="center">구독종료일</td>
			   <td class="nobg1a"><input type="text" name="book_end_dt1" id="datepicker2" class="input" style=padding-top:3px  style=padding-bottom:3px value="" align="center" /></td>
			   <td class="nobg2a" align="center">구독 코드</td>
			   <td class="nobg1a"><input type="text" id="year" name="code_book1" size="10" disabled="true"/></td>               	   
             </tr>             
			 <tr>
               <td class="alt1" align="center">구독 구분</td>              
               <td>
              		<select  id="name" name="book_flag1" style="width: 120px">
              		<option value="">전체</option>	
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("047".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               	    </select>
			   </td>               		     
			   <td class="alt" align="center">근무처 명</td>
               <td><input type="text" id="year" name="company_name1" size="20" /></td>
               <td class="alt" align="center">연락처</td>
               <td><input type="text" id="year" name="company_tel1" size="20" /></td>
               <td class="alt" align="center">사용 여부</td>              
               <td ><select id="name" name="use_yn1" align="center" style="width: 140px">
               		<option value="">선택</option>
                    <option value="Y">Y</option>
	                <option value="N">N</option>	                
                </select></td>
             </tr>
			 <tr>
               <td class="alt1" align="center">이 름</td>
               <td><input type="text" id="year" name="pers_name1" size="10" /></td>
			   <td class="alt" align="center">직 위</td>
               <td><input type="text" id="year" name="job_kind1" size="10" /></td>
               <td class="alt" align="center">발송 호칭</td>               
               <td>
              		<select  id="name" name="code_call1" style="width:120px;">
              		<option value="">선택</option>	
              		 <% 
                	String detCode1,detCName1=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("018".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode1=comcodesearch.get(i).get("detcode").toString();
                			detCName1=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
                		}
                	}
               		 %>  
               	    </select>
			   </td>
			   <td class="alt" align="center">비 고</td>
			   <td></td> 			         
             </tr>
			 <tr>
               <td class="alt1" align="center">우편 번호</td>
               <td><input type="text" id="m_post" name="m_post" size="10"/>
			   <input name="bnt" type="button" value="우편번호" onclick="javascript:postSearch();"/></td>	
			   <td class="alt" align="center">주 소</td>
               <td colspan="3"><input type="text" id="m_address" name="m_address" size="60"/></td>
               <td class="alt" align="center">상세 주소</td>
               <td><input type="text" id="d_address" name="d_address" size="30"/></td>
             </tr>			 
      </table>
			 <ul class="btnIcon_2">	             
		  	   <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
			   <li><a href="javascript:goInsert();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
			   <li><a href="javascript:goSearch(mem,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>	 
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