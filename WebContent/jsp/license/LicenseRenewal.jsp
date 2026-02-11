<%@page import="com.ant.educationlecture.dao.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.ant.common.util.StringUtil"%>
<%@ page import="com.ant.common.properties.AntData"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>대한영양사협회</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/certificate.css" />
  
  
<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<script src="js/form.js" type="text/javascript" ></script>

<!-- 날짜 표시를 위한 스크립트   -->
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
.readOnly1 {background-color:#f2f2f2;}
</style>

<%

	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();

	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

	Map map = new HashMap();
	
	licenseDao dao = new licenseDao();

	List<Map> licenseserch 	= dao.licenseserch(map); 				
	List<Map> licenseserch1 = dao.licenseserch1(map);
	
	Map 	  licensein	= (Map)request.getAttribute("licensein");
	List<Map> licensesand = (List<Map>)request.getAttribute("licensesand");  
	JSONArray j_licensesand=(JSONArray)request.getAttribute("j_licensesand");
	Map etest2=(Map)request.getAttribute("etest2");
	Map etest3=(Map)request.getAttribute("etest3");
	
	int etk2=0;
	
	String yyyy1="";
	String code_certifi1="";
	String code_pers1="";
	String result_no1="";
	String code_seq1="";
	String result_start_dt1="";
	String result_end_dt1="";
	String print_state1="";
	String season1="";
	
	String oper_name1="";
// 	JUMIN_DEL
// 	String oper_no11="";
// 	String oper_no12="";
	String oper_birth1="";
	String oper_lic_no1="";
	
	String add_file_11="";
	String add_file_12="";
	String add_file_13="";
	String add_file_14="";
	String add_file_15="";
	
	if(etest2!=null){
		etk2=etest2.size();
		
		yyyy1=etest2.get("yyyy1").toString();
		code_certifi1=etest2.get("code_certifi1").toString();
		code_pers1=etest2.get("code_pers1").toString();
		result_no1=etest2.get("result_no1").toString();
		code_seq1=etest2.get("code_seq1").toString();
		result_start_dt1=etest2.get("result_start_dt1").toString();
		result_end_dt1=etest2.get("result_end_dt1").toString();
		print_state1=etest2.get("print_state1").toString();
		season1=etest2.get("season1").toString();
		
		oper_name1=etest2.get("oper_name1").toString();
// 		JUMIN_DEL
// 		oper_no11=etest2.get("oper_no11").toString();
// 		oper_no12=etest2.get("oper_no12").toString();
		oper_lic_no1=etest2.get("oper_lic_no1").toString();
		oper_birth1=etest2.get("oper_birth1").toString();
		
		add_file_11=etest2.get("add_file_1").toString();
		add_file_12=etest2.get("add_file_2").toString();
		add_file_13=etest2.get("add_file_3").toString();
		add_file_14=etest2.get("add_file_4").toString();
		add_file_15=etest2.get("add_file_5").toString();
	}
	String msg="";
	if(request.getAttribute("msg")!=null) msg=request.getAttribute("msg").toString();
	
	String save_code_certifi1 = StringUtil.NVL((String)request.getAttribute("code_certifi1"));
	String save_yyyy1 = StringUtil.NVL((String)request.getAttribute("yyyy1"));
	String save_season1 = StringUtil.NVL((String)request.getAttribute("season1"));
	String save_print_state1 = StringUtil.NVL((String)request.getAttribute("print_state1"));
	String save_msg = StringUtil.NVL((String)request.getAttribute("save_msg"));
%>

<script type="text/javascript">
function init(){
	if('<%=etk2%>'>0){
		alert('<%=msg%>');
		
		sForm.yyyy1.value="<%=yyyy1%>";
		sForm.code_certifi1.value="<%=code_certifi1%>";
		var season1="<%=season1%>";
		if(season1==1) sForm.s1.checked	== true;
		else sForm.s2.checked	== true;
		sForm.print_state1.value="<%=print_state1%>";
		goSearch();
		
		sForm3.oper_name1.value="<%=oper_name1%>";
// 		JUMIN_DEL
<%-- 		sForm3.oper_no11.value="<%=oper_no11%>"; --%>
<%-- 		sForm3.oper_no12.value="<%=oper_no12%>"; --%>
		sForm3.oper_birth1.value="<%=oper_birth1%>";
		sForm3.oper_lic_no1.value="<%=oper_lic_no1%>";
		sForm3.result_no1.value="<%=result_no1%>";
		sForm3.result_start_dt1.value="<%=result_start_dt1%>";
		sForm3.result_end_dt1.value="<%=result_end_dt1%>";
		
		if('<%=add_file_11%>'=='Y') 
			document.getElementById("f0").checked=true;
		if('<%=add_file_12%>'=='Y') 
			document.getElementById("f1").checked=true;
		if('<%=add_file_13%>'=='Y') 
			document.getElementById("f2").checked=true;
		if('<%=add_file_14%>'=='Y') 
			document.getElementById("f3").checked=true;
		if('<%=add_file_15%>'=='Y') 
			document.getElementById("f4").checked=true;
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
	  url: 'locall',
	  datatype: "json",
      mtype: 'post',      
      height:'480',
      colNames: [ '자격증종류','자격증번호','갱신차수','이름',
//                   JUMIN_DEL
//                   '주민번호',
                  '생년월일',
                  '면허번호','유효시작일','유효종료일','근무처명','핸드폰','이메일','회원구분','본회평점','기타평점','봉사평점'
                  ,'갱신첨부서류','회원인증기간','회비인증상태','평점인증상태','통합인증상태','자격구분','상태h','상태c','key','회원코드1','대상자'],
      colModel: [
			{name:'code_certifi',  		index:'code_certifi', 			width: 70,editable: false, align: 'center'},	//자격증종류
			{name:'result_no',  		index:'result_no', 			width: 70,editable: false, align: 'center'},		//자격증번호
			{name:'code_seq',  			index:'code_seq', 			width: 50,editable: false, align: 'center'},		//갱신차수
			{name:'oper_name',    		index:'oper_name',  		width: 50,editable: false, align: 'left'},			//이름
// 			JUMIN_DEL
//    			{name:'oper_no',      		index:'oper_no',     		width: 90,editable: false, align: 'left', formatter:juminNo},			//주민번호
   			{name:'oper_birth',      		index:'oper_birth',     		width: 90,editable: false, align: 'left'},			//생년월일
   			{name:'oper_lic_no',    	index:'oper_lic_no',   		width: 60,editable: false, align: 'left'},			//면허번호
   			{name:'result_start_dt',    index:'result_start_dt',    width: 70,editable: false, align: 'center'},		//유효시작일
   			{name:'result_end_dt',      index:'result_end_dt',      width: 70,editable: false, align: 'center'},		//유효종료일
   			{name:'oper_comp_name1',    index:'oper_comp_name1',    width: 200,editable: false, align: 'left'},			//근무처명
   			{name:'oper_hp',  			index:'oper_hp', 			width: 80,editable: false, align: 'left'},			//핸드폰
   			{name:'oper_email',    		index:'oper_email',   		width: 160,editable: false, align: 'left'},			//이메일
   			{name:'person_yn',      	index:'person_yn',     		width: 120,editable: false, align: 'left'},			//회원구분
   			{name:'edu_marks',    		index:'edu_marks',   		width: 80,editable: false, align: 'center'},		//본회평점
   			{name:'edu_marks2',     	index:'edu_marks2',    		width: 80,editable: false, align: 'center'},		//기타평점
   			{name:'service_marks',     	index:'service_marks',      width: 80,editable: false, align: 'center'},		//봉사평점
   			{name:'detcodename',  		index:'detcodename', 		width: 200,editable: false, align: 'center'},		//갱신첨부서류
   			{name:'renewalbt',  		index:'renewalbt', 		    width: 500,editable: false, align: 'center'},		//회원인증기간
   			{name:'renewalok',  		index:'renewalok', 		    width: 80,editable: false, align: 'center'},		//회비인증상태
   			{name:'marksok',  		index:'marksok', 		    width: 80,editable: false, align: 'center'},		//평점인증상태(평점PASS여부)
   			{name:'totalok',  		index:'totalok', 		    width: 80,editable: false, align: 'center'},		//통합인증상태(통합PASS여부)
   			{name:'code_certifi_c',      	index:'code_certifi_c',       	hidden:true},		//자격구분
   			{name:'print_state',      	index:'print_state',       		hidden:true},		//상태h
   			{name:'print_state_c',      index:'print_state_c',     		hidden:true},		//상태c
   			{name:'code_pers',      	index:'code_pers',       		hidden:true},		//(key)
   			{name:'person_yn1',      	index:'person_yn1',       		hidden:true},		//회원코드1
   			{name:'codeoperation',      index:'codeoperation',       	hidden:true}		//대상자   			
   			],
   		    rowNum:20,
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			gridview: true,
 			caption: '자격증갱신',
			altclass:'myAltRowClass'
  
});
$("#list").click(function(e) {
    var el = e.target;
    if(el.nodeName == "TD"){
    	if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
		{						
			var id = jQuery("#list").jqGrid('getGridParam','selrow') ;								
			$("#list").jqGrid('resetSelection');
			$("#list").jqGrid('setSelection',id);			
			goSelect();
		}
    }
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
jQuery("#list").setGridWidth(1100,false);
powerinit();
save_init();
$("#code_certifi1,#print_state1").change(function(e){
	searchFlag="";
});
$("#print_state1").change(function(e){
	if($(this).val()=='0'||$(this).val()=='4'||$(this).val()=='5'){
		$("#yyyytit").text("발급년도");
	} else {
		$("#yyyytit").text("갱신년도");
	}
});
});

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "305";
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

function save_init(){
	var save_code_certifi1 = "<%=save_code_certifi1%>";
	var save_yyyy1 = "<%=save_yyyy1%>";
	var save_season1 = "<%=save_season1%>";
	var save_print_state1 = "<%=save_print_state1%>";
	var save_msg = "<%=save_msg%>";
	
	if(save_code_certifi1!=""){ sForm.code_certifi1.value=save_code_certifi1;}
	if(save_yyyy1!=""){ sForm.yyyy1.value=save_yyyy1;}
	if(save_season1!=""){
		if(save_season1=="1"){
			sForm.s1.checked = true;
		} else if(save_season1=="2"){
			sForm.s2.checked = true;
		}
	}
	if(save_print_state1!=""){ sForm.print_state1.value=save_print_state1;}
	if(save_msg!=""){
		alert(save_msg);
		goSearch(sForm,0);
	}
}

function goClear(){	
	$('#list1').each(function(){
		sForm3.reset();
		searchFlag = "";
	});	
}

// JUMIN_DEL
// function juminNo(cellvalue, options, rowObject ){
// 	var jumin=cellvalue.substring(0,6)+"*******";
// 	return jumin;
// }

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});

var searchFlag = "";
function goSearch(form,intPage){
	/* 검색버튼 클릭시 테이블에서 그리드로 올리는 value 값 자격증 갱신 */
	 var code_certifi1					= sForm.code_certifi1.value;						//자격증구분
	 var result_no1						= sForm3.result_no1.value;								//자격증번호
	 var oper_name1 					= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
// 	 JUMIN_DEL
// 	 var oper_no1 						= sForm3.oper_no11.value+sForm3.oper_no12.value; 		//주민번호	
	 var oper_birth1 						= sForm3.oper_birth1.value; 		//생년월일	
	 var oper_lic_no1					= sForm3.oper_lic_no1.value;							//면허번호

	 var season1 						= "";													//상반기
	 var season2 						= "";													//하반기
	 if(sForm.s1.checked	== true ){
		 season1 = "1";
	 }else{
		 season1 = "";								
	 }
	 if(sForm.s2.checked	== true ){
		 season2	= "2";
	 }else{
		 season2 = "";
	 }
	 
	 var print_state1					= sForm.print_state1.value;								//상태 
	 var yyyy1							= sForm.yyyy1.value;									//년도
	 var code_seq1					= sForm.code_seq1.value;							//순번(key)
	 
	 if(yyyy1==""){
		 alert($("#yyyytit").text()+"를 선택해주십시오.");
		 return;
	 }
	if(print_state1!='0'&&print_state1!='4'&&print_state1!='5'){
		$('#list').jqGrid('clearGridData');
		   jQuery("#list").setGridParam({url:"license.do?method=renewalSandList&code_certifi1="+code_certifi1+"&result_no1="+result_no1+"&oper_name1="+oper_name1
// 				   JUMIN_DEL
// 				   +"&oper_no1="+oper_no1
				   +"&oper_birth1="+oper_birth1
				   +"&oper_lic_no1="+oper_lic_no1+"&code_seq1="+code_seq1
				   +"&season1="+season1+"&season2="+season2+"&print_state1="+print_state1+"&yyyy1="+yyyy1}
		   		).trigger("reloadGrid");
		   searchFlag = "1";
	}else if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
		var mm1=sForm.mm2.value;
		var dd1=sForm.dd2.value;
		
		if(mm1.length==1) mm1='0'+mm1;
		if(dd1.length==1) dd1='0'+dd1;

		$('#list').jqGrid('clearGridData');
		jQuery("#list").setGridParam({url:"license.do?method=renewalSandList&code_certifi1="+code_certifi1+"&result_no1="+result_no1+"&oper_name1="+oper_name1
// 				JUMIN_DEL
// 			   +"&oper_no1="+oper_no1
			   +"&oper_birth1="+oper_birth1
			   +"&oper_lic_no1="+oper_lic_no1+"&code_seq1="+code_seq1
			   +"&season1=&season2=&print_state1="+print_state1+"&yyyy1="+yyyy1+"&mm1="+mm1+"&dd1="+dd1}
	   		).trigger("reloadGrid");
		   searchFlag = "2";
	}
	 rowSelected = false;
	
}

var rowSelected = false;
	function goSelect(rowid,iCol){
		  var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
		  if( gr != null ) {
		  	var list = $("#list").getRowData(gr);
			
		  	document.getElementById("ff").checked=false;
		  	document.getElementById("f0").checked=false;
		  	document.getElementById("f1").checked=false;
		  	document.getElementById("f2").checked=false;
		  	document.getElementById("f3").checked=false;
		  	document.getElementById("f4").checked=false;
		  	
		  	var add_file_no = list.detcodename;										//자격증발급서류제출여부
			var det = add_file_no.split(",") ;

			document.sForm.code_certifi1.value  			= list.code_certifi_c;			//자격증구분
			document.sForm.print_state1.value  			= list.print_state_c;			//상태
		  	document.sForm3.oper_name1.value     		= list.oper_name;			//이름
// 		  	JUMIN_DEL
// 		  	document.sForm3.oper_no11.value      		= list.oper_no.substring(0,6);				//주민번호
// 			document.sForm3.oper_no12.value      		= list.oper_no.substring(6,13);			//주민번호
			document.sForm3.oper_birth1.value     		= list.oper_birth;			//생년월일
			document.sForm3.oper_lic_no1.value     		= list.oper_lic_no;			//면허번호
		  	document.sForm3.result_no1.value     		= list.result_no;			//자격증번호
		  	/* document.sForm3.yyyy1.value     			= list.yyyy;				//년도 */
		  	document.sForm3.result_start_dt1.value     	= list.result_start_dt;		//유효시작일
		  	document.sForm3.result_end_dt1.value     	= list.result_end_dt;		//유효시작일
		  	document.sForm.code_pers1.value     		= list.code_pers;		//
		  	document.sForm.code_seq1.value     			= list.code_seq;		//
		  	document.sForm.person_yn1.value     		= list.person_yn1;		//
	
		  	if(det.length>0){
		  		for(var i=0;i<det.length;i++){
		  			if(det[i]=='신청서') document.getElementById("f0").checked=true;
		  			else if(det[i]=='사진') document.getElementById("f1").checked=true;
		  			else if(det[i]=='영양사면허증사본') document.getElementById("f2").checked=true;
		  			else if(det[i]=='발급회비') document.getElementById("f3").checked=true;
		  			else if(det[i]=='압축파일') document.getElementById("f4").checked=true;
		  		}
		  	}
		  	rowSelected = true;
		  	searchFlag = "";
		  }
	  }

	function excelDown(){
		if(rowSelected){
			alert("엑셀저장을 하시려면 검색을 다시 실행해 주세요.");
			return;
		}
	
// 		var param = "";
		
// 		var code_certifi1=sForm.code_certifi1.value;
// 		var yyyy1=sForm.yyyy1.value;
// 		var print_state1=sForm.print_state1.value;
// 		var result_no1=sForm3.result_no1.value;
// 		var oper_name1=sForm3.oper_name1.value;
// 		var oper_lic_no1=sForm3.oper_lic_no1.value;
// // 		JUMIN_DEL
// // 		var oper_no1 = sForm3.oper_no11.value+sForm3.oper_no12.value; 		//주민번호
// 		var oper_birth1 = sForm3.oper_birth1.value; 		//생년월일
		
		
// 		if(code_certifi1	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;		//구분코드(자격증구분)
// 	 	if(yyyy1			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;				//년도
// 	 	if(print_state1		!= "")	param+="&print_state1="		+sForm.print_state1.value;		//상태
// 	 	if(result_no1		!= "")	param+="&result_no1="		+sForm.result_no1.value;		//자격증번호
// 	 	if(oper_name1		!= "")	param+="&oper_name1="		+escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
// // 		JUMIN_DEL		
// // 	 	if(oper_no1		!= "")	param+="&oper_no1="		+oper_no1; 		//주민번호
// 	 	if(oper_birth1		!= "")	param+="&oper_birth1="		+oper_birth1; 		//생년월일
// 	 	if(oper_lic_no1		    != "")	param+="&oper_lic_no1="		+sForm3.oper_lic_no1.value;			//면허번호
		
// 		 var season1 						= "";													//상반기
// 		 var season2 						= "";													//하반기
// 		 if(sForm.season1.s1.checked	== true ){
// 			 season1 = "1";
// 		 }else{
// 			 season1 = "";								
// 		 }
// 		 if(sForm.season1.s2.checked	== true ){
// 			 season2	= "2";
// 		 }else{
// 			 season2 = "";
// 		 }
		 
// 		param+="&code_seq1=" + sForm.code_seq1.value;							//순번(key)								
		
// 		param+="&season1=" + season1 ;								
// 		param+="&season2="	+ season2 ;		
		
		
		 var code_certifi1					= sForm.code_certifi1.value;						//자격증구분
		 var result_no1						= sForm3.result_no1.value;								//자격증번호
		 var oper_name1 					= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
		 var oper_birth1 						= sForm3.oper_birth1.value; 		//생년월일	
		 var oper_lic_no1					= sForm3.oper_lic_no1.value;							//면허번호

		 var season1 						= "";													//상반기
		 var season2 						= "";													//하반기
		 if(sForm.s1.checked	== true ){
			 season1 = "1";
		 }else{
			 season1 = "";								
		 }
		 if(sForm.s2.checked	== true ){
			 season2	= "2";
		 }else{
			 season2 = "";
		 }
		 
		 var print_state1					= sForm.print_state1.value;								//상태 
		 var yyyy1							= sForm.yyyy1.value;									//년도
		 var code_seq1					= sForm.code_seq1.value;							//순번(key)
		 
		 if(yyyy1==""){
			 alert($("#yyyytit").text()+"를 선택해주십시오.");
			 return;
		 }

		var mm1="";
		var dd1="";
		
		if(print_state1!='0'&&print_state1!='4'&&print_state1!='5'){
		}else if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
			season1 = "";
			season2 = "";
			
			mm1=sForm.mm2.value;
			dd1=sForm.dd2.value;
			
			if(mm1.length==1) mm1='0'+mm1;
			if(dd1.length==1) dd1='0'+dd1;
		}
		 
		var param = ""
			+"&code_certifi1="+code_certifi1
			+"&result_no1="+result_no1
			+"&oper_name1="+oper_name1
			+"&oper_birth1="+oper_birth1
			+"&oper_lic_no1="+oper_lic_no1
			+"&code_seq1="+code_seq1
			+"&season1="+season1
			+"&season2="+season2
			+"&print_state1="+print_state1
			+"&yyyy1="+yyyy1
			+"&mm1="+mm1
			+"&dd1="+dd1;

		param+="&register1="+"<%=g_name%>";
	//	alert(param);
	
		var url="license.do?method=pers_check5"+param;
		window.open(url,"pers_check5","width=400, height=500, left=480, top=200,scrollbars=yes");
		
	
	}

	//저장
	function goSave(){
	
		var yyyy1				= sForm.yyyy1.value;											//년도
		var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
		var code_pers1   		= sForm.code_pers1.value; 										//회원코드
		var result_no1 			= sForm3.result_no1.value;										//자격증번호
		var code_seq1			= sForm.code_seq1.value;										//순번(key)
		var result_start_dt1	= sForm3.result_start_dt1.value;     							//유효시작일
		var result_end_dt1 		= sForm3.result_end_dt1.value;     								//유효시작일
		var print_state1		= sForm.print_state1.value;
		if(sForm.s1.checked		== true )	season1 = "1";							//반기
		else if(sForm.s2.checked	== true )	season1 = "2";
		
		var oper_name1			= sForm3.oper_name1.value;
// 		JUMIN_DEL
// 		var oper_no11			= sForm3.oper_no11.value;
// 		var oper_no12			= sForm3.oper_no12.value;
		var oper_birth1		= sForm3.oper_birth1.value;
		var oper_lic_no1		= sForm3.oper_lic_no1.value;
		
		var add_file_1 = 'N';
		var add_file_2 = 'N';
		var add_file_3 = 'N';
		var add_file_4 = 'N';
		var add_file_5 = 'N';
		
		if(document.getElementById("f0").checked==true) add_file_1='Y';
		if(document.getElementById("f1").checked==true) add_file_2='Y';
		if(document.getElementById("f2").checked==true) add_file_3='Y';
		if(document.getElementById("f3").checked==true) add_file_4='Y';
		if(document.getElementById("f4").checked==true) add_file_5='Y';
		
		if(code_certifi1 == "" || code_pers1 == "" || result_no1 == "" || result_start_dt1 == "" || result_end_dt1 == "" ){
			alert("자격증구분, 회원코드, 자격증번호, 유효기간이 없는 자료는 저장할 수 없습니다.");
		    return;
		}else{				
	 		document.sForm.action = "license.do?method=licenserenewalListSave&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1+"&=code_pers1"+code_pers1
			+"&result_no1="+result_no1+"&code_seq1="+code_seq1+"&result_start_dt1="+result_start_dt1+"&result_end_dt1="+result_end_dt1+"&print_state1="+print_state1+"&season1="+season1
			+"&oper_name1="+oper_name1
// 			JUMIN_DEL
// 			+"&oper_no11="+oper_no11+"&oper_no12="+oper_no12
			+"&oper_birth1="+oper_birth1
			+"&oper_lic_no1="+oper_lic_no1
			+"&add_file_1="+add_file_1+"&add_file_2="+add_file_2+"&add_file_3="+add_file_3+"&add_file_4="+add_file_4+"&add_file_5="+add_file_5;
	    	document.sForm.submit();   
		}
	}
	
	//갱신
	function goSave2(){
		
		 var result_no1						= sForm3.result_no1.value;								//자격증번호
		 var oper_name1 					= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
		 var oper_birth1 						= sForm3.oper_birth1.value; 		//생년월일	
		 var oper_lic_no1					= sForm3.oper_lic_no1.value;							//면허번호
		 var code_seq1					= sForm.code_seq1.value;							//순번(key)
		 var print_state1					= sForm.print_state1.value;
		 //alert(searchFlag + " : " +  result_no1 + " : " +  oper_name1 + " : " +  oper_birth1 + " : " +  oper_lic_no1 + " : " +  code_seq1  + " : " +  print_state1);

	//	 alert("searchFlag== " +searchFlag);
//		 alert("result_no1== " +result_no1);
//		 alert("oper_name1== " +oper_name1);
//		 alert("oper_birth1== " +oper_birth1);
//		 alert("oper_lic_no1== " +oper_lic_no1);
//		 alert("code_seq1== " +code_seq1);
//		 alert("print_state1== " +print_state1);




		if(searchFlag != "1" || result_no1!="" || oper_name1!="" || oper_birth1!="" || oper_lic_no1!="" || code_seq1!=""  || print_state1!="" ){
			alert("자격증구분, 갱신년도, 발급반기만 선택한 상태에서 검색을 실행한 후에만 갱신이 가능 합니다.\n\n(목록에서 대상자를 선택한 상태인 경우 '신규' 버튼을 클릭하신 후 '상태' 검색조건을 전체로 하고 검색을 다시해 주세요.)");
			return;
		}
		 
		 var code_certifi1					= sForm.code_certifi1.value;						//자격증명(key)
		 var yyyy1 ='';
		 if(sForm.print_state1.value=='')
			 yyyy1 = sForm.yyyy1.value;
		 else 
			 yyyy1 = eval(sForm.yyyy1.value)+3;//년도
			 
		 var season1 						= "";
		 if(sForm.s1.checked		== true )	season1 = "1";							//반기
		 else if(sForm.s2.checked	== true )	season1 = "2";			
		 else  season1="";
	
		if ( code_certifi1 != "" && yyyy1 != "" && season1 != ""){	
	   		if(!confirm("갱신처리 하시겠습니까?")){return;}
		  	document.sForm.action = "license.do?method=licenseRenew&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1+"&season1="+season1;
		    document.sForm.submit();   	 
	 	}else{
			alert("자격증구분, 갱신년도, 발급반기의 선택 없이 갱신 할 수 없습니다.");
			return;
		}
	}

	//파일첨부
	function rFile(){
		var code_pers1				= sForm.code_pers1.value;							//종류(key)
		var code_certifi1			= sForm.code_certifi1.value;						//구분(key)
		var code_seq1				= sForm.code_seq1.value;							//순번(key)
		var result_no1				= sForm3.result_no1.value;							//발급번호,자격증번호
		var oper_name1				= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
		var print_state1			= sForm.print_state1.value;
		var yyyy1					= sForm.yyyy1.value;
		var season1="";
		if(sForm.s1.checked	== true ){
			 season1 = "1";
		 }
		 if(sForm.s2.checked	== true ){
			 season1 = "2";								
		 }
		
		 //alert(code_seq1);
		/* var url='license.do?method=rFile&code_pers1='+code_pers1+'&code_certifi1='+code_certifi1+'&code_seq1='+code_seq1
		+'&result_no1='+result_no1+'&oper_name1='+oper_name1+"&print_state1="+print_state1+"&yyyy1="+yyyy1+"&season1="+season1; 
		
		alert(url); */
		
		if ( code_pers1 != "" && code_certifi1 != "" && result_no1 != ""&& oper_name1 != ""&&code_seq1!=""){ 
			window.open('license.do?method=rFile&code_pers1='+code_pers1+'&code_certifi1='+code_certifi1+'&code_seq1='+code_seq1
				+'&result_no1='+result_no1+'&oper_name1='+oper_name1+"&print_state1="+print_state1+"&yyyy1="+yyyy1+"&season1="+season1 ,"rFile","scrollbar=no,resizable=no,toolbar=no,width=675,height=160,left=20,top=29,status=yes");
		} else {
			alert("대상자를 선택해 주세요.");
			return;
		}
	}
	
	//메일 발송
	function sendEmail() {

		var temp = jQuery("#list").jqGrid('getRowData');
		if(temp.length==0){
			alert("조회된 데이터가 없어 메일을 발송 할 수 없습니다.");
			return;
		}

		var rownum = jQuery("#list").jqGrid('getGridParam', 'selarrrow');

// 		if (rownum.length == 0) {
// 			alert("메일을 발송할 회원을 선택해 주십시요.");
// 			return;
// 		}

		var rowdata = new Array();
	 	var toAddr = "";
	 	var addr_infos = "";

// 		for ( var i = 0; i < rownum.length; i++) {

// 			rowdata = $("#list").getRowData(rownum[i]);

// 			if (rowdata.oper_email.length > 0) {
// 				if (param.length > 0)
// 					param += ",";
// 				param += rowdata.oper_email;
// 			}
// 		}
	   	for(var i=0;i<rownum.length;i++){
			
	  		rowdata= 	$("#list").getRowData(rownum[i]);
	  		
	 		if(rowdata.oper_email.length>0){
	 			if(toAddr.length > 0) toAddr+= ",";
	 			toAddr += escape(encodeURIComponent(rowdata.oper_name));
				
				if(addr_infos.length > 0) {
					addr_infos	+= "__";
				}
				addr_infos	+= rowdata.code_pers;
	 		}
	  	}
		//alert("메일발송 = "+param);

 		if(rowSelected){
 			alert("메일발송을 하시려면 검색을 다시 실행해 주세요.\n\n(선택발송의 경우 목록에서 대상자를 선택하지 마시고 목록 좌측의 체크박스만 선택해 주세요.)");
 			return;
 		}
	 	if( rownum.length==0){	
	 		toAddr = "ALL";
	 	}
	 	

		 var code_certifi1					= sForm.code_certifi1.value;						//자격증구분
		 var result_no1						= sForm3.result_no1.value;								//자격증번호
		 var oper_name1 					= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
		 var oper_birth1 						= sForm3.oper_birth1.value; 		//생년월일	
		 var oper_lic_no1					= sForm3.oper_lic_no1.value;							//면허번호

		 var season1 						= "";													//상반기
		 var season2 						= "";													//하반기
		 if(sForm.s1.checked	== true ){
			 season1 = "1";
		 }else{
			 season1 = "";								
		 }
		 if(sForm.s2.checked	== true ){
			 season2	= "2";
		 }else{
			 season2 = "";
		 }
		 
		 var print_state1					= sForm.print_state1.value;								//상태 
		 var yyyy1							= sForm.yyyy1.value;									//년도
		 var code_seq1					= sForm.code_seq1.value;							//순번(key)
		 
		 if(yyyy1==""){
			 alert($("#yyyytit").text()+"를 선택해주십시오.");
			 return;
		 }

		var mm1="";
		var dd1="";
		
		if(print_state1!='0'&&print_state1!='4'&&print_state1!='5'){
		}else if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
			season1 = "";
			season2 = "";
			
			mm1=sForm.mm2.value;
			dd1=sForm.dd2.value;
			
			if(mm1.length==1) mm1='0'+mm1;
			if(dd1.length==1) dd1='0'+dd1;
		}
		 
		var param = ""
			+"&code_certifi1="+code_certifi1
			+"&result_no1="+result_no1
			+"&oper_name1="+oper_name1
			+"&oper_birth1="+oper_birth1
			+"&oper_lic_no1="+oper_lic_no1
			+"&code_seq1="+code_seq1
			+"&season1="+season1
			+"&season2="+season2
			+"&print_state1="+print_state1
			+"&yyyy1="+yyyy1
			+"&mm1="+mm1
			+"&dd1="+dd1;

		param+="&paramfrom=LicenseRenewal";

	 	window.open('memberBasic.do?method=eMail&toAddr='+toAddr+param+"&addr_infos="+addr_infos,"oper_email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");	 	
	}

	/* 쪽지 발송 조건  */
	function notePad(form) {
		var rownum = jQuery("#list").jqGrid('getGridParam', 'selarrrow');
		var rowdata = new Array();
		var person_yn1 = "";
		var oper_name = "";

		for ( var i = 0; i < rownum.length; i++) {
			rowdata = $("#list").getRowData(rownum[i]);

			if (rowdata.person_yn1.length > 0) {
				if (person_yn1.length > 0)
					person_yn1 += ","; //code_pers
				person_yn1 += rowdata.person_yn1;
			}
			if (rowdata.oper_name.length > 0) {
				if (oper_name.length > 0)
					oper_name += ","; //pers_name
				oper_name += rowdata.oper_name;
			}
		}

		if (rownum.length == 0) {
			//여기다 전체 검색(검색과 같은 루트를 탄다.)

			//조건 검색에 쓰는 조건값만 기입
			var param = "";
			
			if(sForm.yyyy1.value		!= "")	param+="&yyyy1="		+sForm.yyyy1.value;										//년도
	 		if(sForm.code_certifi1.value!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;								//지부(교육주최)
	 		if(sForm3.oper_name1.value	!= "")	param+="&oper_name1="	+sForm3.oper_name1.value;								//교육종류
// 	 		JUMIN_DEL
// 	 		if(sForm3.oper_no11.value != "" && sForm3.oper_no12.value != "")	param+="&oper_no1="		+sForm3.oper_no11.value+""+sForm3.oper_no12.value;			//주민번호
	 		if(sForm3.oper_birth1.value!= "")	param+="&oper_birth1="	+sForm3.oper_birth1.value;								// 생년월일
	 		if(sForm3.oper_lic_no1.value!= "")	param+="&oper_lic_no1="	+sForm3.oper_lic_no1.value;								//구분코드(자격증구분)
	 		if(sForm3.result_no1.value	!= "")	param+="&result_no1="	+sForm3.result_no1.value;
	 		
	 		if(sForm.s1.checked		== true )	param+="&season1=1"; 								
			 else if(sForm.s2.checked	== true ) param+="&season2=1";							
	 		if(sForm.print_state1.value	!= "")	param+="&print_state1="+sForm.print_state1.value;
	 		var print_state1					= sForm.print_state1.value;
	 		if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
	 			var mm1=sForm.mm2.value;
	 			var dd1=sForm.dd2.value;
	 			
	 			if(mm1.length==1) mm1='0'+mm1;
	 			if(dd1.length==1) dd1='0'+dd1;
	 			
	 			if(mm1!="") param+="&mm1="+mm1;
	 			if(dd1!="") param+="&dd1="+dd1;
	 		}

			var url = "license.do?method=sendnotePadAll4" + param;
			window.open(url, "umsData",
					"width=370, height=500, left=280, top=80");

		} else {
			//선텍된 항목만 보내기 스트러쳐 등록, 엑션 생성 ,jsp파일 붙이기
			oper_name = escape(encodeURIComponent(oper_name));

//			alert("쪽지발송 조건 = " + oper_name + "  person_yn1 = " + person_yn1);
			var url = "license.do?method=sendnotePad4&code_pers=" + person_yn1
					+ "&pers_name=" + oper_name;
			window.open(url, "notePad",
					"width=370, height=500, left=280, top=80");
		}

	}
	
	/* 문자 발송 조건  */
	 function smssand(form){
		var temp = jQuery("#list").jqGrid('getRowData');
		if(temp.length==0){
			alert("조회된 데이터가 없어 문자를 발송 할 수 없습니다.");
			return;
		}
			
 		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
 		var rowdata=new Array();
 	 	var param="";
 		var dm_pers_code="";
 	 	var pers_name="";
 	 	var pers_hp="";
 	 	var hp_infos = "";
 		
 		
 		//개별선택
 		for(var i=0;i<rownum.length;i++){
 			rowdata= 	$("#list").getRowData(rownum[i]);
//  			if(i==0){
//  				dm_pers_code   =  rowdata.person_yn1;
//  				pers_hp		   =  rowdata.oper_hp;	
//  				pers_name	   =  rowdata.oper_name;
//  			}else{
//  				dm_pers_code   =  dm_pers_code+","+rowdata.person_yn1;
//  				pers_hp	       =  pers_hp+","+rowdata.oper_hp;
//  				pers_name      =  pers_name+","+rowdata.oper_name;
//  			}
 			if(rowdata.oper_hp.length>0) {
 				if(pers_hp.length > 0){ dm_pers_code+= ","; pers_hp+= ","; pers_name+=",";}	
 				dm_pers_code += rowdata.person_yn1;
 				pers_hp += rowdata.oper_hp;
 				pers_name += escape(encodeURIComponent(rowdata.oper_name));
 				
 				if(hp_infos.length > 0) {
 					hp_infos	+= "__";
 				}
 				hp_infos	+= rowdata.code_pers;
 			}
 		}

 		if(rowSelected){
 			alert("문자발송을 하시려면 검색을 다시 실행해 주세요.\n\n(선택발송의 경우 목록에서 대상자를 선택하지 마시고 목록 좌측의 체크박스만 선택해 주세요.)");
 			return;
 		}
 		if( rownum.length==0){	
 			pers_hp = "All";
 		}
// 	 		//여기다 전체 검색(검색과 같은 루트를 탄다.)
	 		
// 	 //조건 검색에 쓰는 조건값만 기입
// 	 		var param = "";
// 	 		//첫째줄-----------------------------------------------
// 	  		if(sForm.yyyy1.value		!= "")	param+="&yyyy1="		+sForm.yyyy1.value;										//년도
// 	 		if(sForm.code_certifi1.value!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;								//지부(교육주최)
// 	 		if(sForm3.oper_name1.value	!= "")	param+="&oper_name1="	+sForm3.oper_name1.value;								//교육종류
// // 	 		JUMIN_DEL
// // 	 		if(sForm3.oper_no11.value != "" && sForm3.oper_no12.value != "")	param+="&oper_no1="		+sForm3.oper_no11.value+""+sForm3.oper_no12.value;			//주민번호
// 	 		if(sForm3.oper_birth1.value!= "")	param+="&oper_birth1="	+sForm3.oper_birth1.value;								//생년월일
// 	 		if(sForm3.oper_lic_no1.value!= "")	param+="&oper_lic_no1="	+sForm3.oper_lic_no1.value;								//구분코드(자격증구분)
// 	 		if(sForm3.result_no1.value	!= "")	param+="&result_no1="	+sForm3.result_no1.value;
	 		
// 	 		if(sForm.season1.s1.checked		== true )	param+="&season1=1"; 								
// 			 else if(sForm.season1.s2.checked	== true ) param+="&season2=1";							
// 	 		if(sForm.print_state1.value	!= "")	param+="&print_state1="+sForm.print_state1.value;
// 	 		var print_state1					= sForm.print_state1.value;
// 	 		if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
// 	 			var mm1=sForm.mm2.value;
// 	 			var dd1=sForm.dd2.value;
	 			
// 	 			if(mm1.length==1) mm1='0'+mm1;
// 	 			if(dd1.length==1) dd1='0'+dd1;
	 			
// 	 			if(mm1!="") param+="&mm1="+mm1;
// 	 			if(dd1!="") param+="&dd1="+dd1;
// 	 		}
	 		
		 var code_certifi1					= sForm.code_certifi1.value;						//자격증구분
		 var result_no1						= sForm3.result_no1.value;								//자격증번호
		 var oper_name1 					= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
		 var oper_birth1 						= sForm3.oper_birth1.value; 		//생년월일	
		 var oper_lic_no1					= sForm3.oper_lic_no1.value;							//면허번호

		 var season1 						= "";													//상반기
		 var season2 						= "";													//하반기
		 if(sForm.s1.checked	== true ){
			 season1 = "1";
		 }else{
			 season1 = "";								
		 }
		 if(sForm.s2.checked	== true ){
			 season2	= "2";
		 }else{
			 season2 = "";
		 }
		 
		 var print_state1					= sForm.print_state1.value;								//상태 
		 var yyyy1							= sForm.yyyy1.value;									//년도
		 var code_seq1					= sForm.code_seq1.value;							//순번(key)
		 
		 if(yyyy1==""){
			 alert($("#yyyytit").text()+"를 선택해주십시오.");
			 return;
		 }

		var mm1="";
		var dd1="";
		
		if(print_state1!='0'&&print_state1!='4'&&print_state1!='5'){
		}else if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
			season1 = "";
			season2 = "";
			
			mm1=sForm.mm2.value;
			dd1=sForm.dd2.value;
			
			if(mm1.length==1) mm1='0'+mm1;
			if(dd1.length==1) dd1='0'+dd1;
		}
		 
		param = ""
			+"&code_certifi1="+code_certifi1
			+"&result_no1="+result_no1
			+"&oper_name1="+oper_name1
			+"&oper_birth1="+oper_birth1
			+"&oper_lic_no1="+oper_lic_no1
			+"&code_seq1="+code_seq1
			+"&season1="+season1
			+"&season2="+season2
			+"&print_state1="+print_state1
			+"&yyyy1="+yyyy1
			+"&mm1="+mm1
			+"&dd1="+dd1;

		var isSelect = "Y";			

 			param+="&from=license5"+"&hp_infos="+hp_infos;
			
			var url="license.do?method=smssandAll"+param+"&pers_hp="+pers_hp+"&isSelect="+isSelect+"&pers_name="+pers_name;
			window.open(url,"umsData","width=370, height=550, left=280, top=50");	
// 	 	}else{
// 			var url="license.do?method=smssand&dm_pers_code="+dm_pers_code+"&pers_hp="+pers_hp+"&pers_name="+escape(encodeURIComponent(pers_name));
// 	 		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
// 	 	}
	 }
	 function ban(val){
			if(val.tagName=="INPUT"){
				if(event.keyCode==8){
					alert("수정할 수 없는 항목입니다.");
					event.keyCode=0;
					event.cancelBubble=true;
					event.returnValue=false;
				}
			}
		}
	 function psChk(val){
		 if(val=='0'||val=='4'||val=='5'){
			 document.getElementById("yyyy2").disabled=false;
			 document.getElementById("mm2").disabled=false;
			 document.getElementById("dd2").disabled=false;
			 document.getElementById("yyyy2").value=document.getElementById("yyyy1").value;
		 }else{
			 document.getElementById("yyyy2").value="";
			 document.getElementById("mm2").value="";
			 document.getElementById("dd2").value="";
			 document.getElementById("yyyy2").disabled=true;
			 document.getElementById("mm2").disabled=true;
			 document.getElementById("dd2").disabled=true;
		 }
	 }
	function chgY2(val){
		if(sForm.print_state1.value=='0'||sForm.print_state1.value=='4'||sForm.print_state1.value=='5'){
			document.getElementById("yyyy2").value=val;
		}
	} 
	
	function goPrint(){	
		if(sForm.print_state1.value!='0'){
// 			alert("상태가 미발급인 리스트에서만 출력 하실 수 있습니다.");
			alert("상태가 발급인 리스트에서만 출력 하실 수 있습니다.");
			return;
		} else if (searchFlag!="2"){
			alert("출력을 하시려면 검색을 다시 실행해 주세요.\n\n(개별 출력을 하시는 경우 검색 실행 후 대상 건의 체크박스만 클릭해 주세요)");
			return;
		}
		if(sForm.code_certifi1.value==''){
			alert("자격증구분을 선택해 주세요.");
			return;
		}
		if(sForm.yyyy1.value==''){
// 			alert("유효종료일을 기준으로 연도를 입력해주십시오.");
// 			alert("갱신년도를 선택해 주세요.");
			alert("발급년도를 선택해 주세요.");
			return;
		}
		
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata=new Array();	
		
		var parm ="";
		var result_no="";
// 		var yyyy=eval(sForm.yyyy1.value)-1;
// 		var yyyy=eval(sForm.yyyy1.value)-3;
 		var yyyy=eval(sForm.yyyy1.value);
		if(sForm.yyyy1.value		!="") 	parm+="&yyyy="	+ yyyy;
		if(sForm.s1.checked		== true )	parm+="&season=1";								//반기
		else if(sForm.s2.checked	== true ) parm+="&season=2";	
	    if(sForm.code_certifi1.value		!="") parm+="&code_certifi="	+ sForm.code_certifi1.value;  
	   
		if( rownum.length==0){		   
		    //window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_certi_all.asp?gubun=L2"+parm);		
	 	    window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_certi_all.jsp?gubun=L2"+parm);		
		}else{	
		 	for(var i=0;i<rownum.length;i++){
				rowdata= 	$("#list").getRowData(rownum[i]);			
				if(i==0){				
					result_no        =  "'"+escape(encodeURIComponent(rowdata.result_no))+"'";			
				}else{
					result_no +=",";
					result_no += "'"+escape(encodeURIComponent(rowdata.result_no))+"'"; 		
				}
			}	 	 	
		 	parm+="&result_no="+result_no;
		    //window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_certi_each.asp?gubun=L2"+parm);		
	 	    window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_certi_each.jsp?gubun=L2"+parm);		
		}
		sForm.button_ab.value='c';
	}
	
	function selall(){
		document.getElementById("f0").checked=true;
	  	document.getElementById("f1").checked=true;
	  	document.getElementById("f2").checked=true;
	  	document.getElementById("f3").checked=true;	  	
	}
	
	function goStateChg(){
		alert("점검 중입니다.");
		return;
		
		if(sForm.print_state1.value!='1'){
			alert("출력상태변경은 상태가 미발급인 리스트에서만 하실 수 있습니다.");
			return;
		}
		if(sForm.button_ab.value!='c'){
			alert("출력상태변경은 자격증 출력후에 하실 수 있습니다.");
			return;
		}
		
		
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
		var rowdata=new Array();	
		
		var parm ="";
		var result_no="";
		var code_pers="";
		var code_seq="";
		var yyyy=eval(sForm.yyyy1.value)-1;
		if(sForm.yyyy1.value		!="") 	parm+="&yyyy1="	+ yyyy;
		if(sForm.s1.checked		== true )	parm+="&season=1";								//반기
		else if(sForm.s2.checked	== true ) parm+="&season=2";	
	    if(sForm.code_certifi1.value		!="") parm+="&code_certifi1="	+ sForm.code_certifi1.value;
	    
		if( rownum.length==0){
		    document.sForm.action="license.do?method=printStateChg1"+parm;
		    //alert(document.sForm.action);
		    document.sForm.submit();
			
		}else{	
		 	for(var i=0;i<rownum.length;i++){
				rowdata= 	$("#list").getRowData(rownum[i]);			
				if(i==0){				
					result_no        =  escape(encodeURIComponent(rowdata.result_no));
					code_pers		 =  rowdata.code_pers;
					code_seq         =  rowdata.code_seq;
				}else{
					result_no +=",";
					code_pers +=",";
					code_seq +=",";
					result_no += escape(encodeURIComponent(rowdata.result_no)); 	
					code_pers += rowdata.code_pers;
					code_seq  += rowdata.code_seq;
				}
			}	 	 	
		 	parm+="&result_no="+result_no+"&code_pers="+code_pers+"&code_seq="+code_seq+"&sel=Y";
		 	
		 	document.sForm.action="license.do?method=printStateChg1"+parm;
		    //alert(document.sForm.action);
		    document.sForm.submit();
		}
	}
	
	
	function downloadAll(){
		var temp = jQuery("#list").jqGrid('getRowData');
		if(temp.length==0){
			alert("조회된 데이터가 없어 일괄다운로드를 할 수 없습니다.");
			return;
		}
		
	  	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');
	  		
	   	var rowdata=new Array();
	   	var param="";
	 	var file_infos = "";

	   	for(var i=0;i<rownum.length;i++){
	  			
	  		rowdata= 	$("#list").getRowData(rownum[i]);
	  		
	 		if(rowdata.oper_email.length>0){
				if(file_infos.length > 0) {
					file_infos	+= "__";
				}
				file_infos	+= rowdata.code_pers;
	 		}
	  	}
	  	
 		if(rowSelected){
 			alert("일괄다운로드를 하시려면 검색을 다시 실행해 주세요.\n\n(선택다운로드의 경우 목록에서 대상자를 선택하지 마시고 목록 좌측의 체크박스만 선택해 주세요.)");
 			return;
 		}
// 	 	if( rownum.length==0){	
// 	 	}
	 		
		 var code_certifi1					= sForm.code_certifi1.value;						//자격증구분
		 var result_no1						= sForm3.result_no1.value;								//자격증번호
		 var oper_name1 					= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
		 var oper_birth1 						= sForm3.oper_birth1.value; 		//생년월일	
		 var oper_lic_no1					= sForm3.oper_lic_no1.value;							//면허번호

		 var season1 						= "";													//상반기
		 var season2 						= "";													//하반기
		 if(sForm.s1.checked	== true ){
			 season1 = "1";
		 }else{
			 season1 = "";								
		 }
		 if(sForm.s2.checked	== true ){
			 season2	= "2";
		 }else{
			 season2 = "";
		 }
		 
		 var print_state1					= sForm.print_state1.value;								//상태 
		 var yyyy1							= sForm.yyyy1.value;									//년도
		 var code_seq1					= sForm.code_seq1.value;							//순번(key)
		 
		 if(yyyy1==""){
			 alert($("#yyyytit").text()+"를 선택해주십시오.");
			 return;
		 }

		var mm1="";
		var dd1="";
		
		if(print_state1!='0'&&print_state1!='4'&&print_state1!='5'){
		}else if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
			season1 = "";
			season2 = "";
			
			mm1=sForm.mm2.value;
			dd1=sForm.dd2.value;
			
			if(mm1.length==1) mm1='0'+mm1;
			if(dd1.length==1) dd1='0'+dd1;
		}
		 
		param = ""
			+"&code_certifi1="+code_certifi1
			+"&result_no1="+result_no1
			+"&oper_name1="+oper_name1
			+"&oper_birth1="+oper_birth1
			+"&oper_lic_no1="+oper_lic_no1
			+"&code_seq1="+code_seq1
			+"&season1="+season1
			+"&season2="+season2
			+"&print_state1="+print_state1
			+"&yyyy1="+yyyy1
			+"&yyyy="+yyyy1
			+"&mm1="+mm1
			+"&dd1="+dd1;

		param+="&file_infos="+file_infos;
	
		if(!confirm((rownum.length>0?"선택하신 "+(rownum.length)+"건을 일괄다운로드 하시겠습니까?":"전체 일괄다운로드 하시겠습니까?"))){return;}
		if(rownum.length==0){
			alert("일괄다운로드 파일을 생성하는데 시간이 다소 소요될 수도 있습니다.\n\n확인 버튼을 클릭하시고 잠시만 기다려 주세요.");
		}
		
		document.dFile.target="frm2";
		document.dFile.action="license.do?method=fDown1All"+param;
		document.dFile.submit();
	}
	
	function goSaveBatch(){
		var temp = jQuery("#list").jqGrid('getRowData');
		if(temp.length==0){
			alert("조회된 데이터가 없어 일괄처리를 할 수 없습니다.");
			return;
		}
		
	  	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');
	  		
	   	var rowdata=new Array();
	   	var param="";
	 	var oper_infos = "";

	   	for(var i=0;i<rownum.length;i++){
	  			
	  		rowdata= 	$("#list").getRowData(rownum[i]);
	  		
	 		if(rowdata.oper_email.length>0){
				if(oper_infos.length > 0) {
					oper_infos	+= "__";
				}
				oper_infos	+= rowdata.code_pers;
	 		}
	  	}
	  	
 		if(rowSelected){
 			alert("일괄처리를 하시려면 검색을 다시 실행해 주세요.\n\n(선택처리의 경우 목록에서 대상자를 선택하지 마시고 목록 좌측의 체크박스만 선택해 주세요.)");
 			return;
 		}
	 	if( rownum.length==0){
	 	}
	 		
		 var code_certifi1					= sForm.code_certifi1.value;						//자격증구분
		 var result_no1						= sForm3.result_no1.value;								//자격증번호
		 var oper_name1 					= escape(encodeURIComponent(sForm3.oper_name1.value));	//이름
		 var oper_birth1 						= sForm3.oper_birth1.value; 		//생년월일	
		 var oper_lic_no1					= sForm3.oper_lic_no1.value;							//면허번호
		var result_start_dt1	= sForm3.result_start_dt1.value;     							//유효시작일
		var result_end_dt1 		= sForm3.result_end_dt1.value;     								//유효시작일

		 var season1 						= "";													//상반기
		 var season2 						= "";													//하반기
		 if(sForm.s1.checked	== true ){
			 season1 = "1";
		 }else{
			 season1 = "";								
		 }
		 if(sForm.s2.checked	== true ){
			 season2	= "2";
		 }else{
			 season2 = "";
		 }
		 
		 var print_state1					= sForm.print_state1.value;								//상태 
		 var yyyy1							= sForm.yyyy1.value;									//년도
		 var code_seq1					= sForm.code_seq1.value;							//순번(key)
		 
		 if(yyyy1==""){
			 alert($("#yyyytit").text()+"를 선택해주십시오.");
			 return;
		 }

		var mm1="";
		var dd1="";
		
		if(print_state1!='0'&&print_state1!='4'&&print_state1!='5'){
		}else if(print_state1=='0'||print_state1=='4'||print_state1=='5'){
			season1 = "";
			season2 = "";
			
			mm1=sForm.mm2.value;
			dd1=sForm.dd2.value;
			
			if(mm1.length==1) mm1='0'+mm1;
			if(dd1.length==1) dd1='0'+dd1;
		}
		 
		param = ""
			+"&code_certifi1="+code_certifi1
			+"&result_no1="+result_no1
			+"&oper_name1="+oper_name1
			+"&oper_birth1="+oper_birth1
			+"&oper_lic_no1="+oper_lic_no1
			+"&code_seq1="+code_seq1
			+"&result_start_dt1="+result_start_dt1
			+"&result_end_dt1="+result_end_dt1
			+"&season1="+season1
			+"&season2="+season2
			+"&print_state1="+print_state1
			+"&yyyy1="+yyyy1
			+"&yyyy="+yyyy1
			+"&mm1="+mm1
			+"&dd1="+dd1;
		
		param+="&oper_infos="+oper_infos;

		if(!confirm((rownum.length>0?"선택하신 "+(rownum.length)+"건을 일괄처리 하시겠습니까?":"전체 일괄처리 하시겠습니까?"))){return;}
		
		document.saveAll.action="license.do?method=licenserenewalListSaveBatch"+param;
		document.saveAll.submit();
	}
	
	
</script>  
 </head>

 <body onload="init();">

	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="license.do?method=licenseSend">자격증</a></li>		
		<li>&gt;</li>
		<li class="NPage">자격증갱신</li>
	  </ul>	 
	<div class="cTabmenu_01">    
	
	     <form id="list1" name="sForm" method="post" action="">   
	   <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
						<input type="hidden" name="code_pers1" 			id="code_pers1" 		value=""/>
						<input type="hidden" name="code_seq1" 			id="code_seq1" 			value=""/>
						<input type="hidden" name="oper_comp_name11" 	id="oper_comp_name11" 	value=""/>
						<input type="hidden" name="oper_hp1" 			id="oper_hp1" 			value=""/>
						<input type="hidden" name="oper_email1" 		id="oper_email1" 		value=""/>
						<input type="hidden" name="person_yn1" 			id="person_yn1" 		value=""/>
						<input type="hidden" name="total_marks1" 		id="total_marks1" 		value=""/>
						<input type="hidden" name="edu_marks1" 			id="edu_marks1" 		value=""/>
						<input type="hidden" name="service_marks1" 		id="service_marks1" 	value=""/>
						<input type="hidden" name="param" 				id="param" 				value=""/>
						<input type="hidden" name="oper_state1" 		id="oper_state1" 		value=""/>
						<input type="hidden" name="code_post1" 			id="code_post1" 		value=""/>
						<input type="hidden" name="oper_comp_add1_11" 	id="oper_comp_add1_11" 	value=""/>
						<input type="hidden" name="result_dt1" 			id="result_dt1" 		value=""/>
						<input type="hidden" name="add_file_no1" 		id="add_file_no1" 		value=""/>
						<input type="hidden" name="add_file_no2" 		id="add_file_no2" 		value=""/>
						<input type="hidden" name="add_file_no3" 		id="add_file_no3" 		value=""/>
						<input type="hidden" name="add_file_no4" 		id="add_file_no4" 		value=""/>
						<input type="hidden" name="codeoperation" 		id="codeoperation" 		value=""/>
						<input type="hidden" name="code_operation1" 	id="code_operation1" 	value=""/>
						<input type="hidden" name="button_ab" value="" />
	  <table class="ctable_05" cellspacing="0" summary="자격증">
        <caption>자격증갱신</caption>            			 
             <tr>
               <td class="nobg" style="width:5%">자격증구분</td>
               <td class="nobg1" align="left" style="width:5%"><select  id="code_certifi1" name="code_certifi1">
               			<option value="">전체</option>	
		                		 <%
		                	String detCode5,detCName5=null;
		                	for(int i=0;i<licenseserch1.size();i++){
		                			detCode5=licenseserch1.get(i).get("code_certifi").toString();
		                			detCName5=licenseserch1.get(i).get("certifi_name").toString();
		                			
		                			if(!"9".equals(licenseserch1.get(i).get("code_certifi").toString())
		                			&&!"0".equals(licenseserch1.get(i).get("code_certifi").toString())){
		                			if(licensein!=null&&detCode5.equals(licensein.get("code_certifi1").toString())){
		                				out.println("<option value='"+detCode5+"' selected>"+detCName5+"</option>");
		                			}else{
		                				out.println("<option value='"+detCode5+"'>"+detCName5+"</option>");
		                			}
		                		}
		                	}
		                	
		               		 %>     
		               	</select></td>
		               	<td class="nobg2" style="width:5%" >※&nbsp;&nbsp;<span id="yyyytit">갱신년도</span></td>
			               <td class="nobg1" align="left" style="width:5%">
			               				<select  id="yyyy1" name="yyyy1" onchange="chgY2(this.value);">	
			               <option value="">전체</option>
			  				 <%
							 	String detCode,detCName=null;
						 	for(int i=0;i<licenseserch.size();i++){
							 		if("036".equals(licenseserch.get(i).get("groupcode").toString())){
			                			detCode=licenseserch.get(i).get("detcode").toString();
			                			detCName=licenseserch.get(i).get("detcodename").toString();
			                			if(licensein!=null&&detCode.equals(licensein.get("yyyy1").toString())){
			                				out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
			                			}else{
			                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
			                			}
			                			
			                		}
							 	}
							 %>         	 
			              	</select>
			              	</td>
			   <td class="nobg2" style="width:6%">발급반기</td>
               <td class="nobg1" style="width:7%">
               			<input type="radio" name="season1" id="s1" value="" checked/>
				   <label for="s1">상반기</label>&nbsp;&nbsp;
				   <input type="radio" name="season1" id="s2" value="" />
				   <label for="s2">하반기</label>
               </td>
             <td class="nobg2" style="width:5%">상태</td>
             
               <td class="nobg1" align="left"> 
               <select  id="print_state1" name="print_state1" onchange="psChk(this.value);">
	           <option value="">전체</option>
	               		<%				 	
					 	for(int i=0;i<licenseserch.size();i++){
					 		if("024".equals(licenseserch.get(i).get("groupcode").toString())){
	                			detCode=licenseserch.get(i).get("detcode").toString();
	                			detCName=licenseserch.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");
	                		}
					 	}
					 %>  
	            </select> 
	             <input type="text" id="yyyy2" name="yyyy2" size="3" maxlength="4" disabled=true onKeyUp="javascript:check_Int('sForm','yyyy2');" onKeyDown="alert($('#yyyytit').text()+'를 선택하시면 자동으로 변경됩니다.'); return;"/>년
	             <input type="text" id="mm2" name="mm2" size="1" maxlength="2" disabled=true onKeyUp="javascript:check_Int('sForm','mm2');"/>월
	             <input type="text" id="dd2" name="dd2" size="1" maxlength="2" disabled=true onKeyUp="javascript:check_Int('sForm','dd2');"/>일
	            </td>
             
             </tr> 
      </table>	
<!-- <div style="width:100%; padding-top:7px; text-align:right;">* 상태 조건을 발급, 갱신발급신청, 재발급신청을 선택하신 경우는 발급년도 기준으로 조회되고 발급반기 조건은 조회에 사용되지 않습니다.</div> -->
<div style="width:100%; padding-top:7px; text-align:right;">* 상태 조건으로 발급, 갱신발급신청을 선택하신 경우는 발급년도 기준으로 조회되고 발급반기 조건은 조회에 사용되지 않습니다.</div>
      </form>
      <form id="list2" name="sForm3" method="post" action="">	  
	  <table class="ctable_07" cellspacing="0" summary="자격증">
          <caption>시험별응시현황</caption>            			 
             <tr>
			   <td class="nobg" style="width:7%">이름</td>
<!--                <td class="nobg2" style="width:14%">주민번호</td> -->
               <td class="nobg2" style="width:14%">생년월일</td>
               <td class="nobg2" style="width:8%">면허번호</td>
               <td class="nobg2" style="width:8%">자격증번호</td>
			   <!-- <td class="nobg2"  style="width:7%">년도</td> -->
			   <td class="nobg2"  style="width:15%">유효기간</td>
               <td class="nobg2">사진 및 첨부서류 확인</td>			   
             </tr>            
			 <tr>
               <td class="alt2" style="width:7%"><input type="text" id="oper_name1" name="oper_name1" size="10" readonly onkeydown="ban(this);" class="readOnly1"/></td>
<!--                <td style="width:14%"><input type="text" id="oper_no11" size="7" name="oper_no11" readonly onkeydown="ban(this);" class="readOnly1"/>-<input type="password" id="oper_no12" size="8" name="oper_no12" readonly onkeydown="ban(this);" class="readOnly1"/></td> -->
               <td style="width:14%"><input type="text" id="oper_birth1" size="7" name="oper_birth1" readonly onkeydown="ban(this);" class="readOnly1"/></td>
               <td style="width:8%"><input type="text" id="oper_lic_no1" name="oper_lic_no1" size="5" readonly onkeydown="ban(this);" class="readOnly1"/></td>
               <td style="width:8%"><input type="text" id="result_no1" name="result_no1" size="5" readonly onkeydown="ban(this);" class="readOnly1"/></td>
			 
               <td style="width:14%" >
               		<input type="text" id="datepicker" name="result_start_dt1" size="5" /> ~ <input type="text" id="datepicker1" name="result_end_dt1" size="5"/>
               </td> 
               <td>
               <a href="javascript:rFile();"><img src="images/icon_s14.gif" id="rFile"alt="첨부" /></a>	              
               </td>             
             </tr>
             <tr>
             	<td class="alt1" >사진 및 첨부서류 제출여부</td>
             	<td  class="alt3" colspan='5' align='left'>
					<input type="checkbox" name="add_file_no0" id="ff" onclick="selall()"/>&nbsp;전체선택&nbsp;&nbsp;
					<input type="checkbox" name="add_file_no1" id="f0" />&nbsp;신청서&nbsp;&nbsp;
	               	<input type="checkbox" name="add_file_no2" id="f1" />&nbsp;사진&nbsp;&nbsp;
	               	<input type="checkbox" name="add_file_no3" id="f2" />&nbsp;영양사면허증사본&nbsp;&nbsp;
	               	<input type="checkbox" name="add_file_no4" id="f3" />&nbsp;발급회비&nbsp;&nbsp;
	               	<input type="checkbox" name="add_file_no5" id="f4" />&nbsp;압축파일&nbsp;&nbsp;
             	</td>
             </tr>			
        </table>
		<ul class="btnIcon_8">		 
		  <li><a href="javascript:goSave2();"><img src="images/icon_s18.gif" onclick="" alt="갱신" /></a></li>		 
 		   <li><a href="javascript:goClear(sForm3,0);"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
		   <li><a href="javascript:goSave();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li> 
		   <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>		
		</ul>
	  </form>
 <br></br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post"></form>
<ul class="btnIcon_3" style="left:799px;">		 
		  <li><a href="javascript:downloadAll();"><img src="images/icon_download.gif"	onclick=""	alt="일괄다운로드"	/></a></li>		
		  <li><a href="javascript:goSaveBatch();"><img src="images/icon_processing.gif"	onclick=""	alt="일괄처리"	/></a></li>		
<!-- 		  <li><a href="javascript:notePad();"><img src="images/icon_s2.gif" onclick=""	alt="쪽지" /></a></li> -->
		  <!-- <li><a href="javascript:sendEmail();"><img src="images/icon_s3.gif"	onclick=""	alt="메일전송"	/></a></li>		
		  <li><a href="javascript:smssand();"><img src="images/icon_s4.gif"	onclick=""	alt="문자전송"	/></a></li> -->
		  <!-- <li><a href="#"><img src="images/icon_s16.gif" alt="엑셀올리기" /></a></li>	 -->	 
		  <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" alt="엑셀저장"/></a></li>
		  <li><a href="javascript:goPrint();"><img src="images/icon_s12.gif" onclick="" /></a></li>
<!-- 		  <li><a href="javascript:goStateChg();"><img src="images/icon_s20.gif" onclick="" /></a></li>	 -->
		</ul>
 </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	
 <form name="dFile" method="post" action="" target="" >
</form>
 <form name="saveAll" method="post" action="" target="" >
</form>
 
 </body> 
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>
<iframe name="frm2" width="0" height="0" tabindex=-1></iframe>
