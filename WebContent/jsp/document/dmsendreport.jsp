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

<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page import="net.sf.json.JSONArray"%>

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
	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();
	String book_end_dt=(new SimpleDateFormat("yyyyMMdd").format(date));
	String book_start_dt=(new SimpleDateFormat("yyyyMM").format(date))+"01";
	
	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);
	List<Map> dm_creatersearch = dao.dm_creatersearch(map);
	List<Map> dm_creatersearch1 = dao.dm_creatersearch1(map);
	
	JSONArray dm_creater=JSONArray.fromObject(dm_creatersearch);
	JSONArray dm_creater1=JSONArray.fromObject(dm_creatersearch1);
	
	StringBuffer book_name = new StringBuffer();
	List<Map> books_kind = dao.books_kind(map);		
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
	  url: 'document.do?method=dmsendreportList',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'365',
      colNames: [ 'DM회원코드','DM 명','DM코드','녕원','년월순번','이름','면허번호','회원구분','회원구분코드','발송호칭','발송호칭코드','근무처 명','우편번호','주소','핸드폰','이메일','지부','지부코드','발송구분','발송구분코드','생성자','dm키','회원키'],
      colModel: [
			{name:'dm_pers_code',             index:'dm_pers_code',      hidden:true},
			{name:'book_name',                index:'book_name',         width: 80, editable: false, align: 'center'},
			{name:'code_book_kind',           index:'code_book_kind',    hidden:true},
			{name:'dm_print_yymm',            index:'dm_print_yymm',     hidden:true},
			{name:'dm_print_yymm_seq',        index:'dm_print_yymm_seq', hidden:true},			
			{name:'pers_name',                index:'pers_name',         width: 60, editable: false, align: 'center'},
   			{name:'lic_no',                   index:'lic_no',            width: 60, editable: false, align: 'center'},
   			{name:'code_member_name',         index:'code_member_name',  width: 80, editable: false, align: 'center'},
   			{name:'code_member',              index:'code_member',       hidden:true},
   			{name:'code_call_name',           index:'code_call_name',    width: 80, editable: false, align: 'center'},
   			{name:'code_call',                index:'code_call',         hidden:true},
   			{name:'company_name',             index:'company_name',      width: 150, editable: false, align: 'center'},
   			{name:'code_post',                index:'code_post',         width: 60,  editable: false, align: 'center'},		
   			{name:'pers_add',                 index:'pers_add',          width: 180, editable: false, align: 'center'},	
   			{name:'pers_hp', 	              index:'pers_hp',           width: 80,  editable: false, align: 'center'},
   			{name:'email',                    index:'email',             width: 100, editable: false, align: 'center'},
   			{name:'code_bran_name',           index:'code_bran_name',    width: 80,  editable: false, align: 'center'},
   			{name:'code_bran',                index:'code_bran',         hidden:true},
   			{name:'rece_yn_name',         	  index:'rece_yn_name',      width: 60,  editable: false, align: 'center'},   			
   			{name:'rece_yn',                  index:'rece_yn',           hidden:true},	 
   			{name:'dm_creater',               index:'dm_creater',        hidden:true},
   			{name:'dm_key',                   index:'dm_key',            hidden:true},
   			{name:'code_pers',                   index:'code_pers',            hidden:true}
   			],
   		    rowNum:15,
   	        /* rowList:[10,15,30], */ 
   			pager: '#pager2',
   		    sortname: 'book_name',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			gridview: true,
 			caption: '발송대장',
			altclass:'myAltRowClass',
			
			onSelectRow: function(ids) {
				var bacode = "";
				bacode = sForm.bacode.value;
				if(bacode != ""){							
					goSelect();} 
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
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

var rowSelected = false;
function goSelect(rowid,iCol){
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
				
		document.sForm.code_book_kind.value       = list.code_book_kind;		
		document.sForm.pers_name.value            = list.pers_name;
		document.sForm.rece_yn.value              = list.rece_yn;		
		document.sForm.dm_creater.value           = list.dm_creater;
		rowSelected = true;
	}
}

function goInsert(){	
	var parm ="";
	var bacode ="";
	var t_bacode = "";	
	var rece_yn              = "";
	var sendgubun            = "";
	var code_book_kind       = "";
	
	sendgubun        = sForm.sendgubun.value;
	code_book_kind   = sForm.code_book_kind.value;	
	rece_yn          = sForm.rece_yn.value;
	
	bacode           = sForm.bacode.value;        
    for(var i=0;i<bacode.length;i+=15){
    	if(i>0) t_bacode+=",";    	
    		t_bacode+=bacode.substring(i, i+15);
    } 
	
	if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  
	//if(sForm.book_end_dt.value        !="")parm+="&book_end_dt="        + sForm.book_end_dt.value;	
			
	var parm  =  parm +  "&rece_yn="            +  rece_yn        +
    					 "&sendgubun="          +  sendgubun      +
  						 "&code_book_kind="     +  code_book_kind;    				            
	
	if(!confirm("저장 하시겠습니까?")){return;}
	alert("저장 하는데 다소 시간이 소요될 수도 있습니다.\n'확인' 버튼을 클릭하시고 잠시 기다려 주십시오.");
	
	if ( t_bacode =="" ) {		
	    	document.sForm.action = "document.do?method=insert_dmlist&isSelect=Y"+parm;
	    	document.sForm.submit(); 
	 }else {	 
	 		document.sForm.action = "document.do?method=update_rece_yn&t_bacode="+t_bacode;
			document.sForm.submit();
	 }
 }

function goCreate(form,intPage){
	var parm ="";	
	
	if ( sForm.code_book_kind.value == "" ) {
        alert("DM구분을 선택하세요.") ;
        sForm.code_book_kind.focus();
        return ;
    }
	if ( sForm.sendgubun.value == "" ) {
        alert("발송대상을 선택하세요.") ;
        sForm.sendgubun.focus();
        return ;
    }
	
	if(sForm.code_book_kind.value     !="")parm+="&code_book_kind="     + sForm.code_book_kind.value;	
	if(sForm.sendgubun.value          !="a")parm+="&sendgubun="         + sForm.sendgubun.value;
	if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  		
			
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"document.do?method=dmsendreportList&isSelect=Y"+parm}).trigger("reloadGrid");
}

function goSearch(form,intPage){
	
	var parm ="";
	var bacode ="";
	var t_bacode = "";	

    if(sForm.pers_name.value          !="")parm+="&pers_name="          + escape(encodeURIComponent(sForm.pers_name.value));  
    if(sForm.code_book_kind.value     !="")parm+="&code_book_kind="     + sForm.code_book_kind.value;  
    if(sForm.sendgubun.value          !="")parm+="&sendgubun="          + sForm.sendgubun.value; 
    if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  
    /* if(sForm.book_end_dt.value        !="")parm+="&book_end_dt="        + sForm.book_end_dt.value; */
    if(sForm.rece_yn.value            !="")parm+="&rece_yn="            + sForm.rece_yn.value;      
    if(sForm.dm_creater.value         !="")parm+="&dm_creater="         + escape(encodeURIComponent(sForm.dm_creater.value)); 
    if(sForm.dm_creater_seq.value         !="")parm+="&dm_print_yymm_seq="+sForm.dm_creater_seq.value;
    
    bacode          = sForm.bacode.value;        
    for(var i=0;i<bacode.length;i+=15){
    	if(i>0) t_bacode+=",";    	
    		t_bacode+=bacode.substring(i, i+15);
    } 
  	sForm.barsearch.value="";
  	
   	if(t_bacode ==""){
   		$('#list').jqGrid('clearGridData');
   		jQuery("#list").jqGrid('setGridParam',{rowNum:15});
   		//alert(parm);
   		jQuery("#list").setGridParam({url:"document.do?method=dmsendsearchList&parm="+parm}).trigger("reloadGrid");
   	}else{
   		sForm.barsearch.value="Y";
   		$('#list').jqGrid('clearGridData');
   		jQuery("#list").jqGrid('setGridParam',{rowNum:100000});
		jQuery("#list").setGridParam({url:"document.do?method=dmbacode&t_bacode="+t_bacode}).trigger("reloadGrid");
   	}
   	
   	rowSelected = false;
}

/* 쪽지 발송 */
function notePad(){	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var dm_pers_code="";
 	var pers_name="";
 	
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);
		if(i==0){
			dm_pers_code	=rowdata.dm_pers_code;
			pers_name		=rowdata.pers_name;	
		}else{
			dm_pers_code	= dm_pers_code+","+rowdata.dm_pers_code;
			pers_name		= pers_name+","+rowdata.pers_name;
		}
	}	
	pers_name = escape(encodeURIComponent(pers_name)); 
	
	if( rownum.length==0){		
		var parm ="";
	    if(sForm.pers_name.value          !="")parm+="&pers_name="          + escape(encodeURIComponent(sForm.pers_name.value));  
	    if(sForm.code_book_kind.value     !="")parm+="&code_book_kind="     + sForm.code_book_kind.value;  
	    if(sForm.sendgubun.value          !="")parm+="&sendgubun="          + sForm.sendgubun.value; 
	    if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  
	    /* if(sForm.book_end_dt.value        !="")parm+="&book_end_dt="        + sForm.book_end_dt.value; */
	    if(sForm.rece_yn.value            !="")parm+="&rece_yn="            + sForm.rece_yn.value;      
	    if(sForm.dm_creater.value         !="")parm+="&dm_creater="         + escape(encodeURIComponent(sForm.dm_creater.value)); 
	    if(sForm.dm_creater_seq.value         !="")parm+="&dm_print_yymm_seq="+sForm.dm_creater_seq.value;

	    var isSelect = "Y";			
		pers_name = escape(encodeURIComponent("전체"));	
		
		var url="document.do?method=dmsendnotePadall&parm="+parm+"&pers_name="+pers_name+"&isSelect="+isSelect;
		window.open(url,"notePad","width=370, height=500, left=280, top=80");	
	}else{
		
		 var url="document.do?method=dmsendnotePad&dm_pers_code="+dm_pers_code+"&pers_name="+pers_name;
		 window.open(url,"notePad","width=370, height=500, left=280, top=80");	
	}
}

//메일 발송
function emailsend(){	
	
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 메일을 발송 할 수 없습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

		if( rownum.length==0)	{			alert("메일을 발송할 회원을 선택해 주십시요.");			return;		}
	 	var rowdata=new Array();
	 	var email="";
	 	var addr_infos="";
		for(var i=0;i<rownum.length;i++){
			rowdata= 	$("#list").getRowData(rownum[i]);
// 			if(i==0){
// 				email	=rowdata.email;	
// 			}else{
// 				email	= email+","+rowdata.email;
// 			}
			
	 		if(rowdata.email.length>0){
	 			if(email.length > 0) email+= ",";
	 			email += escape(encodeURIComponent(rowdata.pers_name));
				
				if(addr_infos.length > 0) {
					addr_infos	+= "__";
				}
				addr_infos	+= (rowdata.dm_key!=''?rowdata.dm_key:'*')+"_";
				addr_infos	+= (rowdata.code_pers!=''?rowdata.code_pers:'*');
	 		}
			
		}
		
	var param ="&addr_infos="+addr_infos;
	var url="document.do?method=eMail&toAddr="+email+param;
	window.open(url,"Email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
}

/* 문자 전송 */
function umsData(){	
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 문자를 발송 할 수 없습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var dm_pers_code="";
 	var pers_name="";
 	var pers_hp="";
 	var hp_infos="";
 	
	for(var i=0;i<rownum.length;i++){
	    if(sForm.sendgubun.value !="1"){
			alert("대상생성 목록에서는 발송대상이 회원일때만 문자발송을 할 수 있습니다.");
			return;
	    }
	    
		rowdata= 	$("#list").getRowData(rownum[i]);
// 		if(i==0){
// 			dm_pers_code   =  rowdata.dm_pers_code;
// 			pers_hp		   =  rowdata.pers_hp;
// 			pers_name      =  rowdata.pers_name;
// 		}else{
// 			dm_pers_code   =  dm_pers_code+","+rowdata.dm_pers_code;
// 			pers_hp	       =  pers_hp+","+rowdata.pers_hp;
// 			pers_name      =  pers_name+","+rowdata.pers_name;
// 		}
 		if(rowdata.pers_hp.length>0){
 			if(pers_hp.length > 0) { dm_pers_code+= ","; pers_hp+= ","; pers_name+= ","; };
 			dm_pers_code += rowdata.dm_pers_code;
 			pers_hp += rowdata.pers_hp;
 			pers_name += escape(encodeURIComponent($.trim(rowdata.pers_name)!=""?rowdata.pers_name:"이름없음"));
			
			if(hp_infos.length > 0) {
				hp_infos	+= "__";
			}
			hp_infos	+= (rowdata.dm_key!=''?rowdata.dm_key:'*')+"_";
			hp_infos	+= (rowdata.code_pers!=''?rowdata.code_pers:'*');
 		}
	}	
		
	 if( rownum.length==0){
		var temp=jQuery("#list").jqGrid('getCol','dm_key');
		if(temp[0]==''){
			alert("대상생성 목록에서는 전체문자발송을 할 수 없습니다.");
			return;
		}
		 
 		if(rowSelected){
 			alert("전체문자 발송을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
 		
// 		var parm ="";

// 	    if(sForm.pers_name.value          !="")parm+="&pers_name="          + escape(encodeURIComponent(sForm.pers_name.value));  
// 	    if(sForm.code_book_kind.value     !="")parm+="&code_book_kind="     + sForm.code_book_kind.value;  
// 	    if(sForm.sendgubun.value          !="")parm+="&sendgubun="          + sForm.sendgubun.value; 
// 	    if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  	    
// 	    if(sForm.rece_yn.value            !="")parm+="&rece_yn="            + sForm.rece_yn.value;      
// 	    if(sForm.dm_creater.value         !="")parm+="&dm_creater="         + escape(encodeURIComponent(sForm.dm_creater.value)); 
// 	    if(sForm.dm_creater_seq.value         !="")parm+="&dm_print_yymm_seq="+sForm.dm_creater_seq.value;
	    
		var parm ="";
		var bacode ="";
		var t_bacode = "";	

	    if(sForm.pers_name.value          !="")parm+="&pers_name="          + escape(encodeURIComponent(sForm.pers_name.value));  
	    if(sForm.code_book_kind.value     !="")parm+="&code_book_kind="     + sForm.code_book_kind.value;  
	    if(sForm.sendgubun.value          !="")parm+="&sendgubun="          + sForm.sendgubun.value; 
	    if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  
	    if(sForm.rece_yn.value            !="")parm+="&rece_yn="            + sForm.rece_yn.value;      
	    if(sForm.dm_creater.value         !="")parm+="&dm_creater="         + escape(encodeURIComponent(sForm.dm_creater.value)); 
	    if(sForm.dm_creater_seq.value         !="")parm+="&dm_print_yymm_seq="+sForm.dm_creater_seq.value;
	    
	    bacode          = sForm.bacode.value;        
	    for(var i=0;i<bacode.length;i+=15){
	    	if(i>0) t_bacode+=",";    	
	    		t_bacode+=bacode.substring(i, i+15);
	    } 

	   	var isSelect="Y";			
		if(sForm.barsearch.value=="Y"){
			parm+="&t_bacode="+t_bacode;
		}
		pers_hp = "All";	
		
		var url="document.do?method=sendumsDataall&parm="+parm+"&pers_hp="+pers_hp+"&isSelect="+isSelect;
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
	}else{
		 var url="document.do?method=sendumsData&dm_pers_code="+dm_pers_code+"&pers_hp="+pers_hp+"&pers_name="+pers_name+"&hp_infos="+hp_infos;
		 window.open(url,"umsData","width=370, height=550, left=280, top=50");	
	}
}

/* 액셀 다운 */
function excelDown(){		
	if(rowSelected){
		alert("엑셀저장을 하시려면 검색을 다시 실행해 주세요.");
		return;
	}
	
	var temp=jQuery("#list").jqGrid('getCol','dm_key');
	//alert(temp[0]);
	if(temp[0]==''){
		alert("대상생성 목록에서는 엑셀 파일을 저장할 수 없습니다.");
		return;
	}
	
	var parm ="";
	var bacode ="";
	var t_bacode = "";
	
	if(sForm.barsearch.value!="Y"){
		if (sForm.book_start_dt.value      !=""){
			book_start_dt = sForm.book_start_dt.value;	
		}
		
	    if(sForm.pers_name.value          !="")parm+="&pers_name="          + escape(encodeURIComponent(sForm.pers_name.value));  
	    if(sForm.code_book_kind.value     !="")parm+="&code_book_kind="     + sForm.code_book_kind.value;  
	    if(sForm.sendgubun.value          !="")parm+="&sendgubun="          + sForm.sendgubun.value; 
	    if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  
	    /* if(sForm.book_end_dt.value        !="")parm+="&book_end_dt="        + sForm.book_end_dt.value; */
	    if(sForm.rece_yn.value            !="")parm+="&rece_yn="            + sForm.rece_yn.value;      
	    if(sForm.dm_creater.value         !="")parm+="&dm_creater="         + escape(encodeURIComponent(sForm.dm_creater.value)); 
	    if(sForm.dm_creater_seq.value         !="")parm+="&dm_print_yymm_seq="+sForm.dm_creater_seq.value;
	    
	    document.sForm.target="frm";
		document.sForm.action="document.do?method=dmsendsearchDown&isBarcode=N&parm"+parm;
		alert("엑셀파일을 생성하는데 다소 시간이 소요될 수도 있습니다.\n'확인' 버튼을 클릭하시고 잠시 기다려 주십시오.");
		document.sForm.submit(); 
	}else{
		bacode = sForm.bacode.value;        
	    for(var i=0;i<bacode.length;i+=15){
	    	if(i>0) t_bacode+=",";    	
	    		t_bacode+=bacode.substring(i, i+15);
	    }
	    document.sForm.target="frm";
		document.sForm.action="document.do?method=dmsendsearchDown&isBarcode=Y&t_bacode="+t_bacode;
		alert("엑셀파일을 생성하는데 다소 시간이 소요될 수도 있습니다.\n'확인' 버튼을 클릭하시고 잠시 기다려 주십시오.");
		document.sForm.submit();	    
	}
}

function gowrite(){	
	var temp=jQuery("#list").jqGrid('getCol','dm_key');
	//alert(temp[0]);
	if(temp[0]==''){
		alert("대상생성 목록에서는 출력 할 수 없습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();	
	var doc_seq=new Array();
	var t_comma = "";
			
	if( rownum.length==0){
		var parm ="";
	    if(sForm.pers_name.value          !="")parm+="&pers_name="          + escape(encodeURIComponent(sForm.pers_name.value));  
	    if(sForm.code_book_kind.value     !="")parm+="&code_book_kind="     + sForm.code_book_kind.value;  
	    //if(sForm.sendgubun.value          !="")parm+="&sendgubun="          + sForm.sendgubun.value; 
	    if(sForm.book_start_dt.value      !="")parm+="&book_start_dt="      + sForm.book_start_dt.value;  
	    /* if(sForm.book_end_dt.value        !="")parm+="&book_end_dt="        + sForm.book_end_dt.value; */
	    if(sForm.rece_yn.value            !="")parm+="&rece_yn="            + sForm.rece_yn.value;      
	    if(sForm.dm_creater.value         !="")parm+="&dm_creater="         + escape(encodeURIComponent(sForm.dm_creater.value)); 
	    if(sForm.dm_creater_seq.value         !="")parm+="&dm_print_yymm_seq="+sForm.dm_creater_seq.value;
	    
	    //var url="document.do?method=dmwriteall&parm="+parm;
		//var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=yes, width=340, height=500, left=100, top=100";		
		//window.open(url,"dmwriteForm", status);		
	    //alert(parm);
		//window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_dm_all.jsp?doc_code=0108"+parm);		
		window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_dm_all.jsp?doc_code=0108"+parm);		
	}else{	
	 	for(var i=0;i<rownum.length;i++){
			rowdata= 	$("#list").getRowData(rownum[i]);
			if(i==0){				
				doc_seq        =  "'"+rowdata.dm_key+"'";			
			}else{
				doc_seq +=",";
				doc_seq+= "'"+rowdata.dm_key+"'"; 		
			}
		}	 	 	
		//window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print.jsp?doc_code=0108&doc_seq="+doc_seq);		
		window.open("<%=AntData.DOMAIN%>/doc_form/docu_print.jsp?doc_code=0108&doc_seq="+doc_seq);		
	}
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});

function cCreate(){
	var jc=eval(<%=dm_creater%>);
	var msect=document.getElementById("dm_creater");
	var opts=msect.options;
	var cbk=document.getElementById("code_book_kind").value;
	
	var jc1=eval(<%=dm_creater1%>);
	var msect1=document.getElementById("dm_creater_seq");
	var opts1=msect1.options;	
	
	
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
	while(opts1.length>0){
		opts1[opts1.length-1]=null;
	}
	
	var idx=opts.length;
	var idx1=opts1.length;
	for(var i=0;i<jc.length;i++){
		if(jc[i].code_book_kind==cbk){
			msect[idx++]=new Option(jc[i].dm_creater,jc[i].dm_creater);
		}		
	}
	var val=document.getElementById("dm_creater").value;
	//alert(val);
	for(var j=0;j<jc1.length;j++){
		if(jc1[j].code_book_kind==cbk&&jc1[j].dm_creater==val){
			msect1[idx1++]=new Option(jc1[j].dm_print_yymm_seq,jc1[j].dm_print_yymm_seq);
		}		
	}
	if(msect.length==0){
		msect[idx]=new Option("선택","");
	}
	if(msect1.length==0){
		msect1[idx1]=new Option("선택","");
	}
}

function cCreate1(){
	var jc=eval(<%=dm_creater1%>);
	var msect1=document.getElementById("dm_creater_seq");
	var opts1=msect1.options;
	var cbk=document.getElementById("code_book_kind").value;
	var val=document.getElementById("dm_creater").value;
	while(opts1.length>0){
		opts1[opts1.length-1]=null;
	}

	var idx1=opts1.length;
	for(var i=0;i<jc.length;i++){
		if(jc[i].code_book_kind==cbk&&jc[i].dm_creater==val){
			msect1[idx1++]=new Option(jc[i].dm_print_yymm_seq,jc[i].dm_print_yymm_seq);
		}		
	}

	if(msect1.length==0){
		msect1[idx1]=new Option("선택","");
	}
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
		<input type="hidden" name="barsearch" value=""/>
        
	  <table class="ctable_03" cellspacing="0" summary="문서">
       	<div id="ts_tabmenu">
	         <ul>
	           <li><a href="document.do?method=dmsendData"><strong><span>기타 발송자</span></strong></a></li>
		       <li><a href="document.do?method=dmsendreportData" class="overMenu"><strong><span>발송대장</span></strong></a></li> 		     
			 </ul>
        </div>	       
		     <tr>		          	
               <td class="nobga" align="center">DM 구분</td>
			   <td class="nobg1a" >
              		<select  id="code_book_kind" name="code_book_kind" style="width: 90px" onchange="javascript:cCreate();">
              		<option value="">선택</option>
              		<% 
                	String Code_book,Book_name=null; 
                	for(int i=0;i<books_kind.size();i++){                		
                		Code_book=books_kind.get(i).get("code_book_kind").toString();
                		Book_name=books_kind.get(i).get("book_name").toString();
               			out.println("<option value="+Code_book+">"+Book_name+"</option>");                		
                	}
               		 %>  
               	    </select>
			   </td>
			   <td class="nobg2a" align="center">발송 대상</td>
			   <td class="nobg1a">
              		<select  id="sendgubun" name="sendgubun" style="width: 90px">
              		<!-- <option value="">전체</option> -->
              		<% 
                	String detCode1,detCName1=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("049".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode1=comcodesearch.get(i).get("detcode").toString();
                			detCName1=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
                		}
                	}
               		 %>  
               	    </select>
               	    &nbsp;&nbsp;<input type="button" onclick="javascript:goCreate(sForm,0);" align="center" value=" 대상생성 " />
			   </td>			    
			   <td class="nobg2a" align="center">생성자</td>
			   <td class="nobg1a">
			 	  <select  id="dm_creater" name="dm_creater" style="width: 90px" onchange="javascript:cCreate1()">
	              		<option value="">선택</option>	
	              		<%-- <% 
	                	String dm_creater1=null;
	                	for(int i=0;i<dm_creatersearch.size();i++){                		
	                		dm_creater1=dm_creatersearch.get(i).get("dm_creater").toString();
	                		out.println("<option value="+dm_creater1+">"+dm_creater1+"</option>");	                		
	                	}
               		 %>  --%>
               	    </select>	
           	    </td>		
           	    <td colspan='2' class="nobg1a">
           	    	<select  id="dm_creater_seq" name="dm_creater_seq" style="width: 150px">
           	    		<option value="">선택</option>
           	    	</select>
           	    </td>         
			   </tr>
			   <tr>
			   <td class="alt1" align="center">발송일</td>
               <td><input type="text" name="book_start_dt" id="datepicker" class="input" value="" style="size:8"/><%-- &nbsp;~
			   	   <input type="text" name="book_end_dt" id="datepicker1" class="input"  value="<%=book_end_dt%>" style="size:8" /> --%></td>
			   <td class="alt" align="center">발송 구분</td>			   
			   <td>
              		<select  id="name" name="rece_yn" style="width: 90px">
              		<option value="">전체</option>
              		<% 
                	String detCode,detCName=null; 
                	for(int i=0;i<comcodesearch.size();i++){
                		if("048".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               	    </select>
			   </td>
			   <td class="alt" align="center">이 름</td>
			   <td><input type="text" id="year" name="pers_name" style="width:100px"/></td>  
			   <td class="alt" align="center">바코드</td>
			   <td><input type="text" id="year" name="bacode"  style="width:100px" />						 
             </tr>		 
		 </table>
			<ul class="btnIcon_1">			  	 
			   <li><a href="javascript:goInsert();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
		  	   <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif"  onclick=""  align="center" alt="검색" /></a></li>		  	 
			</ul>  
	 	 
	 </form>
<br><br>
	 
<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
		<ul class="btnIcon_33" style="left:967px;">
<!-- 		  <li><a href="javascript:notePad();"><img src="images/icon_s2.gif" alt="쪽지" /></a></li> -->
		  <!-- <li><a href="javascript:emailsend();"><img src="images/icon_s3.gif" alt="메일전송" /></a></li>	
		  <li><a href="javascript:umsData();"><img src="images/icon_s4.gif" alt="문자전송" /></a></li> -->
		  <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" alt="엑셀저장" /></a></li>
		  <li><a href="javascript:gowrite();"><img src="images/icon_s12.gif" onclick=""alt="출력" /></a></li>	  	
		</ul>

</form>
</div>
</div>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>