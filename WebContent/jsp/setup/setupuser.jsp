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

<%
	String tag = "";
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
    var errMsg="<%=errMsg%>";
    if(errMsg.length>5){
        alert(errMsg);
        return;
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
	  url: 'setupdues.do?method=setupuserList',
	  datatype: "json",
      mtype: 'post',      
      width:'200',
      height:'485',
      colNames: [ '사용자 명','사용자 ID','사용자 PW','사용자 근무처명','사용자 근무처','사용자 유형명','사용자 유형','사용 여부'],
      colModel: [
			{name:'UserName', 		index:'UserName',  		 width: 100,editable: false, align: 'center'},									
			{name:'UserId',         index:'UserId',          width: 100,editable: false, align: 'center'},	
			{name:'UserPW', 		index:'UserPW',  		 hidden:true},
			{name:'UserBranname',   index:'UserBranname',    hidden:true},		
			{name:'UserBran',       index:'UserBran',        hidden:true},
			{name:'UserPowername',  index:'UserPowername',   hidden:true},	
			{name:'UserPower',      index:'UserPower',       hidden:true},	
			{name:'use_yn', 		index:'use_yn',  		 hidden:true},				
			
			],
   		    rowNum:500,
   		    sortname: 'UserName', 
   			/* pager: '#pager', */
   			scroll: true,
            rownumbers: true,
            viewrecords: false,
 			multiselect: false,
 			gridview: true,
 			caption: '사용자 코드',
			altclass:'myAltRowClass',
						
			onSelectRow: function(ids) {
 					tag = '';
				    var gr = jQuery("#celltbl").jqGrid('getGridParam','selrow');
				  	var celltbl = $("#celltbl").getRowData(gr); //
				    document.sForm.UserName1.value    =    celltbl.UserName;
				    document.sForm.UserId1.value      =    celltbl.UserId;				    
				    document.sForm.UserPW1.value      =    '00000000';
				    document.sForm.UserBran1.value    =    celltbl.UserBran;
				    document.sForm.UserPower1.value   =    celltbl.UserPower;
				    document.sForm.use_yn1.value      =    celltbl.use_yn;
				    
				    var UserId  = celltbl.UserId;
				  				  
				if(ids == null) {
					ids=0;
					if(jQuery("#celltbl2").jqGrid('getGridParam','records') >0 )
					{
						jQuery("#celltbl2").jqGrid('setGridParam',{url:"setupdues.do?method=setupuserList2&UserId="+UserId}).trigger("reloadGrid");
//						 alert("11");
					}
				} else {  
					    jQuery("#celltbl2").jqGrid('setGridParam',{url:"setupdues.do?method=setupuserList2&UserId="+UserId}).trigger("reloadGrid");	
						}
	}
 });

jQuery("#celltbl").jqGrid('navGrid','#pager',{edit:false,add:false,del:false,search:false,refresh:true});

$("#celltbl2").jqGrid({
	url: 'setupdues.do?method=setupuserList2',
	datatype: "json",
    mtype: 'post',      
    width:'895',
    height:'485',
    colNames: [ '프로그램 ID','프로그램명','프로그램 한글명','사용여부'],
    colModel: [
			{name:'progid', 	   index:'progid',  	 width: 80 ,editable: false, align: 'center'},
			{name:'progname',      index:'progname',     width: 100,editable: true, align: 'center'},
			{name:'proghanname',   index:'proghanname',  width: 100,editable: false, align: 'center'},
			{name:'use_yn',        index:'use_yn',       width: 30 ,editable: true,  align: 'center', formatter:"checkbox", edittype:'checkbox', editoptions: {value:"1:0"},  formatoptions:{disabled:false} }
			],  
 		    rowNum:200,
 		    /* sortname: 'groupcode', */ 
 			/* pager: '#pager2', */
            rownumbers: true,
            viewrecords: true,
			multiselect: false, 
			gridview: true,
			caption: '프로그램 List',
			altclass:'myAltRowClass' ,		    
			onSelectRow: function(ids) {								
				//goSelect();
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
					
					}
					else {}				
				}
			}
	
});

powerinit();
jQuery("#celltbl2").jqGrid('navGrid','#pager2',{edit:true,add:false,del:false,search:false,refresh:true});
});

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "706";
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

function goClear(){	
	$('#list1').each(function(){
		this.reset();
		tag = '*';
	});	
}

function goInsert(){
	var  UserId1     = sForm.UserId1.value;
	var  UserName1   = escape(encodeURIComponent(sForm.UserName1.value));
	var  UserPW1     = sForm.UserPW1.value;
	var  UserBran1   = sForm.UserBran1.value;
	var  UserPower1  = sForm.UserPower1.value;
	var  use_yn1     = sForm.use_yn1.value;
				
	    if ( UserId1 == "" ) {
	        alert("사용자 ID를 입력하세요.") ;
	        sForm.UserId1.focus();
	        return ;
	    }   
	    
	    if ( UserName1 == "" ) {
	        alert("사용자명을 입력하세요.") ;
	        sForm.UserName1.focus();
	        return ;
	    }   
	    
	    if ( UserPW1 == "" ) {
	        alert("비밀번호를 입력하세요.") ;
	        sForm.UserPW1.focus();
	        return ;
	    }
	    
	    if ( UserBran1 == "" ) {
	        alert("사용자근무처를 입력하세요.") ;
	        sForm.UserBran1.focus();
	        return ;
	    }   
	    
	    if (UserPower1 == "" ) {
	        alert("사용자유형을 입력하세요.") ;
	        sForm.UserPower1.focus();
	        return ;
	    }   
	    
	    if ( use_yn1 == "" ) {
	        alert("사용여부를 입력하세요.") ;
	        sForm.use_yn1.focus();
	        return ;
	    }   
	    
	    if ( tag == '*' ) { 
	    	document.sForm.action = "setupdues.do?method=insert_user";
	    	document.sForm.submit();  
	    }else { 
	    	document.sForm.action = "setupdues.do?method=update_user";
	    	document.sForm.submit();
	    }	   
}

function goSelect(rowid,iCol){
	var  UserId1     = sForm.UserId1.value;

	if (UserId1== "" || UserId1 == null){
		alert("왼쪽 리스트에서 사용자를 선택해 주십시오.");
		return;
	}

    var gr = jQuery("#celltbl2").jqGrid('getGridParam','selrow');
   	if( gr != null ) {
		var celltbl2 = $("#celltbl2").getRowData(gr);
        var use_yn1    = celltbl2.use_yn;
        var progid1    = celltbl2.progid;
//   		alert(celltbl2.use_yn);
        if (use_yn1 == '0'){
        	use_yn1 = '1';
        }else{
			use_yn1 = '0'
        };
        jQuery("#celltbl2").setGridParam({url:"setupdues.do?method=update_user1&use_yn1="+use_yn1+"&progid1="+progid1+"&UserId1="+UserId1}).trigger("reloadGrid");	

        if (use_yn1 == '1'){
        	alert("사용권한을 부여합니다.");
        }else{
        	alert("사용권한을 회수합니다.");
        };
	    jQuery("#celltbl2").jqGrid('setGridParam',{url:"setupdues.do?method=setupuserList2&UserId="+UserId1}).trigger("reloadGrid");	
    }; 
} 	
function goInsert2(){
	var  UserId1     = sForm.UserId1.value;
	if (UserId1== "" || UserId1 == null){
		alert("왼쪽 리스트에서 사용자를 선택해 주십시오.");
		return;
	}
	var progid1='';
	var i=0;
	$("#celltbl2").find('input[type=checkbox]').each(function() {				
	    if( $(this).is(':checked')==true) {
	    	var colid = $(this).parents('tr').attr('id');
			var ret = jQuery("#celltbl2").jqGrid('getRowData',colid);
	    	if(i==0) progid1 = ret.progid;
	    	else progid1 += ','+ret.progid;	    	
	    }	
	    i++;
	 });
	jQuery("#celltbl2").setGridParam({url:"setupdues.do?method=update_user1&&progid1="+progid1+"&UserId1="+UserId1}).trigger("reloadGrid");	
	alert("사용권한을 저장했습니다.");
	
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
		<li class="NPage">사용자 등록</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="sForm" method="post">        
	  <table class="ctable_03" cellspacing="0" summary="사용자 등록">       	
       		 <tr>
       		   <td class="nobg" align="center">사용자 ID</td>
               <td class="nobg1"><input type="text" id="name" name="UserId1" size="15" align="center"/></td>
               <td class="nobg2" align="center">사용자 명</td>
               <td class="nobg1"><input type="text" id="name" name="UserName1" size="15" align="center"/></td>
               <td class="nobg2" align="center">비밀 번호</td>
               <td class="nobg1"><input type="password" id="name" name="UserPW1" size="15" align="center"/></td>
             </tr> 
             <tr>
               <td class="alt1" align="center" whith="15px">사용자 근무처</td>
               <td>
               <select  id="name" name="UserBran1">
               <option value="O">선택</option>	
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("007".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td> 			                	
               <td class="alt" align="center">사용자 유형</td>
               <td>
               <select  id="name" name="UserPower1">
               <option value="O">선택</option>	
              		 <% 
                	String detCode1,detCName1=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("041".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode1=comcodesearch.get(i).get("detcode").toString();
                			detCName1=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td>              
               <td class="alt" align="center" >사용 여부</td>
               <td ><select id="name" name="use_yn1"/>
             			<option value="O">선택</option>
	                    <option value="Y">Y</option>
		                <option value="N">N</option>	                
                    </select></td>			
			 </tr>			 
		 </table>
			 <ul class="btnIcon_789">	             
		  	   <li><a href="#"><img src="images/icon_new.gif"    onclick="javascript:goClear();" alt="신규" /></a></li>		 
			   <li><a href="#"><img src="images/icon_save.gif"   onclick="javascript:goInsert();" alt="저장" /></a></li>	
			   <li><a href="#"><img src="images/icon_save3.gif"   onclick="javascript:goInsert2();" alt="저장" /></a></li>			   
		 	 </ul>		 	
	 </form><br><br>

<table>
	<tr>
  		<td>   		
    		<table id="celltbl"></table>
    		<!-- <div id="pager"></div>   	 -->
		</td>
		<td>   		
    		<table id="celltbl2"></table>
    		<!-- <div id="pager2"></div> -->
   		</td>  
	</tr>
</table>		 
</div>
</div>
</body>
</html>