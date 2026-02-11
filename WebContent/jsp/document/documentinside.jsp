<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.ant.common.util.StringUtil"%>
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
.readOnly1 {background-color:#f2f2f2;}
</style>

<%
	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);	

	String logid="";
	if(session.getAttribute("G_NAME")!=null){
		logid=session.getAttribute("G_NAME").toString();
	}
	String errMsg="";
	if(request.getAttribute("errMsg")!=null){
		errMsg=request.getAttribute("errMsg").toString();
	}
	String sparam="";String sparam_org="";
	if(request.getAttribute("sparam")!=null){
		sparam=request.getAttribute("sparam").toString();
		sparam_org=request.getAttribute("sparam_org").toString();
	}
	
	
	String register1 = StringUtil.NVL((String)request.getAttribute("register1"));
	String title1 = StringUtil.NVL((String)request.getAttribute("title1"));
	String attach1 = StringUtil.NVL((String)request.getAttribute("attach1"));
	String receipt_dt1 = StringUtil.NVL((String)request.getAttribute("receipt_dt1"));
	String inqday1 = StringUtil.NVL((String)request.getAttribute("inqday1"));
	String inqday2 = StringUtil.NVL((String)request.getAttribute("inqday2"));
	String center1 = StringUtil.NVL((String)request.getAttribute("center1"));
	String remark1 = StringUtil.NVL((String)request.getAttribute("remark1"));
	String pgno = StringUtil.NVL((String)request.getAttribute("pgno"));
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
	  url: 'document.do?method=documentInsideList<%=sparam%>',
	<%if(!"".equals(pgno)){%>
		page : <%=pgno%>,
	<%}%>
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'365',
      colNames: [ '품의일','부서명','부서코드','문서번호','제목','첨부물','비고','작성자',],
      colModel: [
			{name:'receipt_dt',  index:'receipt_dt',   width: 80,editable: false, align: 'center'},
			{name:'detcodename', index:'detcodename',  width: 80,editable: false, align: 'center'},
   			{name:'detcode', 	 index:'detcode',      hidden:true},
   			{name:'year_seq',    index:'year_seq',     width: 80,editable: false, align: 'center'},   			
   			{name:'title',       index:'title',        width: 150,editable: false, align: 'left', formatter:decode11},		
   			{name:'attach',      index:'attach',       width: 100,editable: false, align: 'left', formatter:decode11},	
   			{name:'remark',      index:'remark',       width: 150,editable: false, align: 'left', formatter:decode11},
   			{name:'register',    index:'register',     width: 80,editable: false, align: 'left', formatter:decode11},
   			],
   		    rowNum:15,
   		    sortname: 'receipt_dt',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '내부 결제 공문대장',
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
//$("[name=register1]").val("<%=g_name%>");

document.sForm.register1.value    = "<%=register1%>";
document.sForm.title1.value    = "<%=title1%>";
document.sForm.attach1.value    = "<%=attach1%>";
document.sForm.receipt_dt1.value    = "<%=receipt_dt1%>";
document.sForm.inqday1.value    = "<%=inqday1%>";
document.sForm.inqday2.value    = "<%=inqday2%>";
document.sForm.center1.value    = "<%=center1%>";
document.sForm.remark1.value    = "<%=remark1%>";

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
	var meprogid = "403";
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
				
		document.sForm.receipt_dt1.value = list.receipt_dt;
		document.sForm.year_seq1.value   = list.year_seq;
		document.sForm.center1.value     = list.detcode;
		document.sForm.register1.value   = list.register;		
		document.sForm.title1.value      = list.title;
		document.sForm.attach1.value     = list.attach;
		document.sForm.remark1.value     = list.remark; 	
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();		
	});	
}

function goInsert(){
	var param = "";
    var receipt_dt1    = sForm.receipt_dt1.value;	
	var year_seq1	   = sForm.year_seq1.value;	
	var title1		   = sForm.title1.value;
	var center1 =sForm.center1.value;
	var register1=sForm.register1.value;
	var g_name = "<%=session.getAttribute("G_NAME").toString()%>";
	  	
    if ( receipt_dt1 == "" ) {
        alert("품의일을 입력해주세요.") ;
        sForm.receipt_dt1.focus();
        return ;
    }
    if ( center1 == "" ) {
        alert("발송 부서명을 선택해주세요.") ;
        sForm.center1.focus();
        return ;
    }
    if(register1==""){
    	alert("작성자를 입력해주세요.") ;
        sForm.register1.focus();
        return ;
    }
    if ( title1 == "" ) {
        alert("제목을 입력해주세요.") ;
        sForm.title1.focus();
        return ;
    }
    if ( year_seq1 == "" ) { 
    	document.sForm.action = "document.do?method=insert_inside&g_name="+g_name;
    	document.sForm.submit();  
    }else { 
        sparam+="&s_pgno="+$('#list').getGridParam('page');
    	document.sForm.action = "document.do?method=update_inside&year_seq1="+year_seq1+sparam;
    	document.sForm.submit();
	 }	
}

var sparam = "<%=sparam_org%>";
function goSearch(form,intPage){
	var parm ="";     
    
    if(sForm.receipt_dt1.value     !="")parm+="&receipt_dt1="  + sForm.receipt_dt1.value;    
    if(sForm.inqday1.value         !="")parm+="&inqday1="      + sForm.inqday1.value; 
    if(sForm.inqday2.value         !="")parm+="&inqday2="      + sForm.inqday2.value;  
    if(sForm.center1.value         !="")parm+="&center1="      + sForm.center1.value;      
    if(sForm.register1.value       !="")parm+="&register1="    +escape(encodeURIComponent(sForm.register1.value)); 
    if(sForm.title1.value          !="")parm+="&title1="       +escape(encodeURIComponent(sForm.title1.value)); 
    if(sForm.attach1.value         !="")parm+="&attach1="      +escape(encodeURIComponent(sForm.attach1.value));
    if(sForm.remark1.value         !="")parm+="&remark1="      +escape(encodeURIComponent(sForm.remark1.value));
    sparam="";
    if(sForm.receipt_dt1.value     !="")sparam+="&s_receipt_dt1="  + sForm.receipt_dt1.value;    
    if(sForm.inqday1.value         !="")sparam+="&s_inqday1="      + sForm.inqday1.value; 
    if(sForm.inqday2.value         !="")sparam+="&s_inqday2="      + sForm.inqday2.value;  
    if(sForm.center1.value         !="")sparam+="&s_center1="      + sForm.center1.value;      
    if(sForm.register1.value       !="")sparam+="&s_register1="    +escape(encodeURIComponent(sForm.register1.value)); 
    if(sForm.title1.value          !="")sparam+="&s_title1="       +escape(encodeURIComponent(sForm.title1.value)); 
    if(sForm.attach1.value         !="")sparam+="&s_attach1="      +escape(encodeURIComponent(sForm.attach1.value));
    if(sForm.remark1.value         !="")sparam+="&s_remark1="      +escape(encodeURIComponent(sForm.remark1.value));
		
   	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"document.do?method=documentInsideList&parm="+parm}).trigger("reloadGrid");
}

function excelDown(){	
	var g_name = "<%=session.getAttribute("G_NAME").toString()%>";
	var g_id = "<%=session.getAttribute("G_ID").toString()%>";
	var parm ="";     
    
    if(sForm.receipt_dt1.value     !="")parm+="&receipt_dt1="  + sForm.receipt_dt1.value;    
    if(sForm.inqday1.value         !="")parm+="&inqday1="      + sForm.inqday1.value; 
    if(sForm.inqday2.value         !="")parm+="&inqday2="      + sForm.inqday2.value;  
    if(sForm.center1.value         !="")parm+="&center1="      + sForm.center1.value;   
    if(sForm.register1.value       !="")parm+="&register1="    +escape(encodeURIComponent(sForm.register1.value)); 
    if(sForm.title1.value          !="")parm+="&title1="       +escape(encodeURIComponent(sForm.title1.value)); 
    if(sForm.attach1.value         !="")parm+="&attach1="      +escape(encodeURIComponent(sForm.attach1.value)); 
    if(sForm.remark1.value         !="")parm+="&remark1="      +escape(encodeURIComponent(sForm.remark1.value));
    
	document.sForm.target="frm";
	document.sForm.action="document.do?method=documentInsideDown&parm="+parm+"&g_name="+g_name+"&g_id="+g_id;
	document.sForm.submit();
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
		<li class="NPage">내부 결제 공문대장</li>
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
	       
	  <table class="ctable_03" cellspacing="0" summary="문서">
                 			 
             <tr>
              	<td class="nobg" align="center">품의일</td>               			   
			  	<td class="nobg1" colspan="3"><input type="text" name="receipt_dt1" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px  value="" size="12" align="center" /></td>
			 	<td class="nobg2" align="center">조회기간</td> 
			    <td class="nobg1"><input type="text" name="inqday1" id="datepicker1" class="input" style=padding-top:3px  style=padding-bottom:3px value='' size="12" align="center"/>						 
							   ~ <input type="text" name="inqday2" id="datepicker3" class="input" style=padding-top:3px  style=padding-bottom:3px value='' size="12" align="center"/></td>		  
              </tr> 
              <tr>            
              	<td class="alt1" align="center">발송부서명</td>
              	<td width="24%">
              		<select  id="name" name="center1">
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
               	<td class="alt" width="10%" align="center">문서번호</td>
               	<td><input type="text"  id="name" name="year_seq1" size="15" disabled="true"/></td>
               	<td class="alt" align="center">작성자</td>
               	<td><input  type="text"  id="name" name="register1" size="15" value="" /></td>      			  
             </tr>	
			 <tr>
               <td class="alt1" align="center">제목</td>
               <td colspan="5"><input type="text" id="name" name="title1" size="147" value="" /></td>     		  		  
             </tr>
			 <tr>
               <td class="alt1" align="center">첨부물</td>
               <td colspan="5"><input type="text" id="name" name="attach1" size="147" value="" /></td>			     			  
             </tr>
			 <tr>
               <td class="alt1" align="center">비고</td>
               <td colspan="5"><input type="text" id="name" name="remark1" size="147" value="" /></td>  		   			  
             </tr> 
       </table> 
             <ul class="btnIcon_2">	             
		  	   <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
			   <li><a href="javascript:goInsert();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
			   <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>	 
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
  