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

<%@ page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

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

	String logid="";
	if(session.getAttribute("G_NAME")!=null){
		logid=session.getAttribute("G_NAME").toString();
	}
	String errMsg="";
	if(request.getAttribute("errMsg")!=null){
		errMsg=request.getAttribute("errMsg").toString();
	}

	String tag = "";
	Map remap=new HashMap();
	int remaps=0;
	String groupcode1="";
	String groupname1="";
	String gr1="";
	if(request.getAttribute("remap")!=null){
		remap=(HashMap)request.getAttribute("remap");
		remaps=remap.size();
		groupcode1=remap.get("groupcode1").toString();
		groupname1=remap.get("groupname1").toString();
		gr1=remap.get("gr").toString();
	}
%>

<script type="text/javascript">

document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};

function init(){
	var logid="<%=logid%>";	
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		opener.document.location.href="login.do?method=login";
		self.close();
	}
	if(<%=remaps%>>0){
		alert('<%=errMsg%>');
		var groupcode='<%=groupcode1%>';
		document.sForm.groupname1.value='<%=groupname1%>';
		document.sForm.gr.value='<%=gr1%>';
		jQuery("#celltbl2").jqGrid('setGridParam',{url:"setupdues.do?method=comcodeList2&groupcode="+groupcode}).trigger("reloadGrid");
	}
	
}

function getAjaxCelltbl(strUrl,funcReceive){
	  $.ajax({
	   cache:false,
	   //type:'GET',
	   url:strUrl,
	   dataType:'html',
	   timeout:60000,
	   success:function(html,textStatus){
	    funcReceive(html,textStatus);
	    document.all.div_wait.style.visibility  = "hidden" ;
	   },
	   error: function(xhr,textStatus,errorThrown){
	    document.all.div_wait.style.visibility  = "hidden" ;
	    alert("전송에러");
	   }
	  });
	 }

function getAjaxCelltbl2(strUrl,funcReceive){
	   $.ajax({
		   cache:false,
        //type:'GET',
			url:strUrl,
			dataType:'html',
			timeout:60000,
			success:function(html,textStatus){
				funcReceive(html,textStatus);
				document.all.div_wait.style.visibility  = "hidden" ;
			},
			error: function(xhr,textStatus,errorThrown){
				document.all.div_wait.style.visibility  = "hidden" ;
				alert("전송에러");
			}
		});
	}
	 
	 function receiveListcelltbl(data){
	  var jsonData=eval("("+data+")");
	  $("#celltbl").clearGridData();
	  for(var i=0;i<=jsonData.data.length;i++) $("#celltbl").addRowData(i+1,jsonData.data[i]);
	 }

	 function receiveListCelltbl2(data){
			var jsonData=eval("("+data+")");
			$("#celltbl2").clearGridData();
			for(var i=0;i<=jsonData.data.length;i++) $("#celltbl2").addRowData(i+1,jsonData.data[i]);
		}
	 
$(document).ready(function(){	
	
$("#celltbl").jqGrid({	  
	  url: 'setupdues.do?method=comcodeList',
	  datatype: "json",
      mtype: 'post',      
      width:'250',
      height:'485',
      colNames: [ '코드','코드명','길이'],
      colModel: [
			{name:'groupcode', 		index:'groupcode',  		 width: 60, editable: false, align: 'center'},									
			{name:'groupname',      index:'groupname',           width: 300,editable: false, align: 'center'},
			{name:'codelen',        index:'codelen',             width: 60, editable: false, align: 'center'},			
   			],
   		    rowNum:100,
   		    sortname: 'groupcode', 
   			/* pager: '#pager', */
   			scroll: true,
            rownumbers: true,
            viewrecords: false,
 			multiselect: false,
 			gridview: true,
 			caption: '그룹코드',
			altclass:'myAltRowClass',
						
			onSelectRow: function(ids) {
				    var gr = jQuery("#celltbl").jqGrid('getGridParam','selrow');
				    document.sForm.gr.value =gr;
				    
				  	var celltbl = $("#celltbl").getRowData(gr);
				    document.sForm.groupname1.value    =    celltbl.groupname;
				    var groupcode  = celltbl.groupcode;
				    document.sForm.codelen.value    =    celltbl.codelen;
				    document.sForm.groupcode11.value    =    celltbl.groupcode;				    
				if(ids == null) {
					ids=0;
					if(jQuery("#celltbl2").jqGrid('getGridParam','records') >0 )
					{
						jQuery("#celltbl2").jqGrid('setGridParam',{url:"setupdues.do?method=comcodeList2&groupcode="+groupcode}).trigger("reloadGrid");
					}
				} else {  
					    jQuery("#celltbl2").jqGrid('setGridParam',{url:"setupdues.do?method=comcodeList2&groupcode="+groupcode}).trigger("reloadGrid");
					 
				}
				goClear();
			}
		});    

			
jQuery("#celltbl").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:true});

$("#celltbl2").jqGrid({
	url: 'setupdues.do?method=comcodeList2',
	datatype: "json",
    mtype: 'post',      
    width:'845',
    height:'485',
    colNames: [ '그룹코드','그룹코드명','코드','코드명','대체코드명1','대체코드명2','대체코드명3','코드순서','사용여부'],
    colModel: [
			{name:'groupcode', 		index:'groupcode',  	 hidden:true},
			{name:'groupname',      index:'groupname',       width: 180,editable: false, align: 'center'},
			{name:'detcode',        index:'detcode',         width: 70,editable:  false, align: 'center'},
			{name:'detcodename',    index:'detcodename',     width: 150,editable: false, align: 'center'},	
			{name:'tempcd1',        index:'tempcd1',         width: 100,editable: false, align: 'center'},			
			{name:'tempcd2',        index:'tempcd2',         width: 100,editable: false, align: 'center'},			
			{name:'tempcd3',        index:'tempcd3',         width: 100,editable: false, align: 'center'},			
			{name:'cdseq',          index:'cdseq',           width: 100,editable: false, align: 'center'},			
			{name:'use_yn',         index:'use_yn',          width: 70,editable: false, align: 'center'},			
 			],
 		    rowNum:1000,
 		    sortname: 'groupcode', 
 			/* pager: '#pager2', */
            rownumbers: true,
            viewrecords: true,
			multiselect: false,
			gridview: true,
			caption: '세부코드',
			altclass:'myAltRowClass',
						
			onSelectRow: function(ids) {				
				goSelect();
				
				if(ids == null) {
					ids=0;
					if(jQuery("#celltbl2").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
					{
						alert("컬럼을 정확히 선택해 주세요");
					}
				} else {

					//ids 는 몇번째인가, 아래 page 는 몇 페이지인가 . 계산해서 처리하자.
					//alert("page = "+jQuery("#celltbl").jqGrid('getGridParam','page')+" row_id = "+ids);
					var id = jQuery("#celltbl2").jqGrid('getGridParam','selrow') ;
					if(id){
						var ret = jQuery("#celltbl2").jqGrid('getRowData',id);
						var id = ret.id;
						//alert(id);
						//onSubmitList(id);
					}
					else {}				
				}
			}  
});
powerinit();
jQuery("#celltbl2").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "707";
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
	var gr = jQuery("#celltbl2").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var celltbl2 = $("#celltbl2").getRowData(gr);
				
		document.sForm.groupname1.value         = celltbl2.groupname;
		document.sForm.detcode1.value           = celltbl2.detcode;	
		document.sForm.detcodename1.value       = celltbl2.detcodename;
		document.sForm.tempcd11.value           = celltbl2.tempcd1;
		document.sForm.tempcd21.value           = celltbl2.tempcd2;
		document.sForm.tempcd31.value           = celltbl2.tempcd3;
		document.sForm.cdseq1.value             = celltbl2.cdseq;
		document.sForm.use_yn1.value            = celltbl2.use_yn;			
	}
}

function goClear(){	
	$('#list1').each(function(){
		document.sForm.detcode1.value           = "";	
		document.sForm.detcodename1.value       = "";
		document.sForm.tempcd11.value           = "";
		document.sForm.tempcd21.value           = "";
		document.sForm.tempcd31.value           = "";
		document.sForm.cdseq1.value             = "";
		
		tag = '*';
	});	
}

function goInsert(){
	if( typeof tag =='undefined' || document.sForm.groupcode11.value == ''){alert("먼저 그룹코드를 선택해 주세요."); return;}
	var  groupname1   = escape(encodeURIComponent(sForm.groupname1.value));
	var  detcode1     = sForm.detcode1.value;
	var  detcodename1 = escape(encodeURIComponent(sForm.detcodename1.value));
	var  cdseq1       = sForm.cdseq1.value;
	var  tempcd11     = sForm.tempcd11.value;
	var  tempcd21     = sForm.tempcd21.value;
	var  tempcd31     = sForm.tempcd31.value;
	var  use_yn1      = sForm.use_yn1.value;
	var  codelen      = sForm.codelen.value;
	//alert(codelen);
	var gr ="";
	
	if ( tag == '*' ) {
    	gr = jQuery("#celltbl").jqGrid('getGridParam','selrow');
	}else{
		gr = sForm.gr.value;
	}
	
	if( gr != null ) {
		var celltbl = $("#celltbl").getRowData(gr);	
		var groupcode1    = celltbl.groupcode;
		var cnt  = celltbl.codelen;	
		//alert(cnt);
	}
    if (detcode1.length != cnt) { 
    	alert("세부코드길이를 맞쳐주세요.") ;
        sForm.detcode1.focus();
        return ;
    }
    if (detcodename1 == "") {
        alert("세부코드명을 입력하세요.") ;
        sForm.detcodename1.focus();
        return ;
    }
    if ( cdseq1 == "") {
        alert("코드순서를 입력하세요.") ;
        sForm.cdseq1.focus();
        return ;
    }
    
    var parm = "&groupcode1="    +  groupcode1    +
   			   "&groupname1="    +  groupname1    +
			   "&detcode1="      +  detcode1      +
			   "&detcodename1="  +  detcodename1  +
			   "&cdseq1="        +  cdseq1        +	
			   "&tempcd11 ="     +  tempcd11      +
			   "&tempcd21="      +  tempcd21       +
			   "&tempcd31="      +  tempcd31      +
			   "&use_yn1="       +  use_yn1+
			   "&gr="       +  gr;	
    
    
    if ( tag == '*' ) {
    	document.sForm.action = "setupdues.do?method=insert_comcode&parm="+parm;
    	document.sForm.submit();  
 	}else {
	   	document.sForm.action = "setupdues.do?method=update_comcode&parm="+parm;
		document.sForm.submit();
	}  
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
		<li class="NPage">공통코드</li>
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
      <input type="hidden" name="codelen" value="" />
      <input type="hidden" name="groupcode11" value="" /> 
      <input type="hidden" name="gr" value="" /> 
	  <table class="ctable_03" cellspacing="0" summary="공통코드">       	
       		 <tr>
       		   <td class="nobga" align="center">그룹 코드명</td>
               <td class="nobg1a"><input type="text" id="name" name="groupname1" size="15" align="center" disabled="true"/></td>
               <td class="nobg2a" align="center">세부 코드</td>
               <td class="nobg1a"><input type="text" id="name" name="detcode1" size="15" align="center"/></td>
               <td class="nobg2a" align="center">세부 코드명</td>
               <td class="nobg1a"><input type="text" id="name" name="detcodename1" size="15" align="center"/></td>
               <td class="nobg2a" align="center" whith="15px">코드 순서</td>
               <td class="nobg1a"><input type="text" id="name" name="cdseq1" size="15" align="center"/></td>             
             </tr>             
			 <tr>
               <td class="alt1" align="center">대체 코드명1</td>
               <td><input type="text" id="name" name="tempcd11" size="15" align="center"/></td>
               <td class="alt" align="center">대체 코드명2</td>
               <td><input type="text" id="name" name="tempcd21" size="15" align="center"/></td>
			   <td class="alt" align="center">대체 코드명3</td>
               <td><input type="text" id="name" name="tempcd31" size="15"/></td>
               <td class="alt" align="center" >사용 여부</td>
               <td ><select id="name" name="use_yn1"/>
             			<option value="O">전체</option>
	                    <option value="Y">Y</option>
		                <option value="N">N</option>	                
                    </select></td>			
			 </tr>			 
		 </table>
			 <ul class="btnIcon_1">	             
		  	   <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
			   <li><a href="javascript:goInsert();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>			   
		 	 </ul>		 	
	 </form>
<br><br>
<table>
	<tr>
  		<td>   		
    		<table id="celltbl"></table>
    		<!-- <div id="pager"></div>   	 -->
		</td>
		<td>   		
    		<table id="celltbl2"></table>
    		<div id="pager2"></div>
   		</td>  
	</tr>
</table>		 
</div>
</div>
</body>
</html>