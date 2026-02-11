
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

String save_code_certifi1 = StringUtil.NVL((String)request.getAttribute("code_certifi1"));
String save_yyyy1 = StringUtil.NVL((String)request.getAttribute("yyyy1"));
String save_season1 = StringUtil.NVL((String)request.getAttribute("season1"));
// String save_code_operation1 = StringUtil.NVL((String)request.getAttribute("code_operation1"));
String save_code_operation1 = "";
String save_msg = StringUtil.NVL((String)request.getAttribute("msg"));

%>
<script type="text/javascript">

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
	      height:'520',
      colNames: [ '자격증명','자격증번호','유효시작일','유효종료일','출력상태','이름',
//                   JUMIN_DEL
//                   '주민번호',
                  '생년월일',
                  '면허번호','취득구분','회원구분','발급일','핸드폰','이메일','순번','년도','발급반기','회원코드'
                  ,'자격증명코드값','취득구분코드값','회원코드1'],
      colModel: [
			{name:'codecertifi',  		index:'codecertifi', 		width: 70,editable: false, align: 'center'},	//자격증명
   			{name:'result_no',    		index:'result_no',   		width: 80,editable: false, align: 'left'},		//자격증번호
   			{name:'result_start_dt',    index:'result_start_dt',    width: 80,editable: false, align: 'center'},	//유효기간시작
   			{name:'result_end_dt',      index:'result_end_dt',      width: 80,editable: false, align: 'center'},	//유효기간종료
   			{name:'print_state',        index:'print_state',      width: 80,editable: false, align: 'center'},	//유효기간종료
   			{name:'oper_name',    		index:'oper_name',   		width: 70,editable: false, align: 'center'},		//이름
//    			JUMIN_DEL
//    			{name:'oper_no',     		index:'oper_no',    		width: 100,editable: false, align: 'left'},		//주민번호
   			{name:'oper_birth',     		index:'oper_birth',    		width: 100,editable: false, align: 'left'},		//생년월일
   			{name:'oper_lic_no',        index:'oper_lic_no',      	width: 70,editable: false, align: 'center'},		//면허번호	
   			{name:'codeoperation',  	index:'codeoperation', 		width: 70,editable: false, align: 'center'},		//취득구분
   			{name:'person_yn',    		index:'person_yn',   		width: 120,editable: false, align: 'left'},		//회원구분
   			{name:'result_dt',      	index:'result_dt',     		width: 70,editable: false, align: 'center'},		//발급일
   			{name:'oper_hp',    		index:'oper_hp',   			width: 100,editable: false, align: 'center'},		//핸드폰
   			{name:'oper_email',     	index:'oper_email',    		width: 200,editable: false, align: 'left'},		//이메일
   			
   			{name:'code_seq',      		index:'code_seq', 		    hidden:true},		//순번
   			{name:'yyyy',      			index:'yyyy', 		    	hidden:true},		//년도
  			{name:'season',      		index:'season', 		    hidden:true},		//발급반기
   			{name:'code_pers',      	index:'code_pers', 		    hidden:true},		//회원코드
   			{name:'code_certifi',      	index:'code_certifi', 		hidden:true},		//자격증명 코드값
   			{name:'code_operation',  	index:'code_operation', 	hidden:true},		//취득구분
   			{name:'person_yn1',      	index:'person_yn1',       	hidden:true}		//회원코드1
   			    ],
   		    rowNum:20,
   		   //sortname: 'receipt_dt',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			gridview: true,
 			caption: '자격증발급',
			altclass:'myAltRowClass'
});
	$("#list").click(function(e) {
	    var el = e.target;
	    if(el.nodeName == "TD"){
	    	if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
			{						
				var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
				//var rowdata=jQuery("#list").jqGrid('getGridParam','selarrrow');						
				$("#list").jqGrid('resetSelection');
				$("#list").jqGrid('setSelection',id);
				goSelect();
			}
	    }
	});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
jQuery("#list").setGridWidth(1100,false);
powerinit();
init();

	$("#s1,#s2").click(function(e){
		if($(this).attr("lastchecked")==$(this).attr("id")){
			$(this).prop("checked",false);
			$("#s1,#s2").attr("lastchecked","");
		} else {
			$("#s1,#s2").attr("lastchecked",$(this).attr("id"));
		}
	});
});

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "304";
	var bcheck = "N";
	
	document.sForm.button_ab.value     			= "";			//대상검색과 발급현황조회구분값
	
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

function init(){
	var save_code_certifi1 = "<%=save_code_certifi1%>";
	var save_yyyy1 = "<%=save_yyyy1%>";
	var save_season1 = "<%=save_season1%>";
	var save_code_operation1 = "<%=save_code_operation1%>";
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
	if(save_code_operation1!=""){ sForm.code_operation1.value=save_code_operation1;}
	if(save_msg!=""){
		alert(save_msg);
		goSearch(sForm,0);
	}
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});


//발급현황조회
function nowSearch(form,intPage){
	/* 발급현황버튼 클릭시 테이블에서 그리드로 올리는 value 값 자격증 발급 */
	 var code_certifi1					= sForm.code_certifi1.value;						//자격증명(key)
	 var oper_name1 					= escape(encodeURIComponent(sForm.oper_name1.value));		//이름
// 	 JUMIN_DEL
// 	 var oper_no1 						= sForm.oper_no1.value;		 						//주민번호		
	 var oper_birth1 						= sForm.oper_birth1.value;		 						//생년월일		
	 var oper_lic_no1					= sForm.oper_lic_no1.value;							//면허번호
	 var code_operation1 				= sForm.code_operation1.value;						//취득구분
	 var yyyy1 					= sForm.yyyy1.value;							//년도
	 if(yyyy1==""){
         alert("발급년도를 선택하세요!");
         return;
     }
	 var season1 				= "";
	 if(sForm.s1.checked		== true )	season1 = "1";								//반기
	 else if(sForm.s2.checked	== true )	season1 = "2";			
	 else  season1="";
	 
	 if(season1!="" && yyyy1==""){
		 if(!confirm("발급년도를 선택하지 않으면 발급반기도 검색조건에 포함되지 않습니다.\n\n검색을 진행 하시겠습니까 ?")){return;}
	 }
	 
	  // 그리드로 넘길때 히든으로 가지고 있어야 할값
	 //var code_seq1						= sForm.code_seq1.value;							//순번(key)
	 //var code_pers1						= sForm.code_pers1.value;							//회원코드
	 document.sForm.button_ab.value     			= "b";			//대상검색과 발급현황조회구분값
	 
$('#list').jqGrid('clearGridData');
 jQuery("#list").setGridParam({url:"license.do?method=nowSandList&code_certifi1="+code_certifi1+"&result_no1="
		   +"&result_start_dt1=&result_end_dt1=&oper_name1="+oper_name1
// 		   JUMIN_DEL
// 		   +"&oper_no1="+oper_no1
		   +"&oper_birth1="+oper_birth1
		   +"&oper_lic_no1="+oper_lic_no1
		   +"&code_operation1="+code_operation1+"&person_yn1=&result_dt1=&oper_hp1=&oper_email1="
		   +"&yyyy1="+yyyy1+"&season1="+season1+"&code_seq1=&code_pers1="}
 		).trigger("reloadGrid");
// alert("엑션으로 가자~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~====");
 rowSelected = false;
}


//대상검색
function goSearch(form,intPage){
	/* 검색버튼 클릭시 테이블에서 그리드로 올리는 value 값 자격증 발급 */
	 var code_certifi1					= sForm.code_certifi1.value;						//자격증명(key)
	 var code_operation1 				= sForm.code_operation1.value;						//취득구분
	 /* var result_no1						= sForm.result_no1.value;							//자격증번호
	 var result_start_dt1				= sForm.result_start_dt1.value;						//유효시작일
	 var result_end_dt1					= sForm.result_end_dt1.value;						//유효종료일
	 var oper_name1 					= escape(encodeURIComponent(sForm.oper_name1.value));		//이름
	 var oper_no1 						= sForm.oper_no1.value;		 						//주민번호		
	 var oper_lic_no1					= sForm.oper_lic_no1.value;							//면허번호
	 
	 var person_yn1 					= sForm.person_yn1.value;							//회원구분 
	 
	 var result_dt1						= sForm.result_dt1.value;							//발급일
	 var oper_hp1						= sForm.oper_hp1.value;								//핸드폰
	 var oper_email1 					= sForm.oper_email1.value;							//이메일 */
	 var yyyy1 							= sForm.yyyy1.value;								//년도
	 
	 if(yyyy1==""){
         alert("발급년도를 선택하세요!");
         return;
     }
	 
	 var season1 						= "";
	 if(sForm.s1.checked		== true )	season1 = "1";							//반기
	 else if(sForm.s2.checked	== true )	season1 = "2";			
	 else  season1="";
	 
	 if(season1!="" && yyyy1==""){
		 if(!confirm("발급년도를 선택하지 않으면 발급반기도 검색조건에 포함되지 않습니다.\n\n검색을 진행 하시겠습니까 ?")){return;}
	 }
	 
	 
	  // 그리드로 넘길때 히든으로 가지고 있어야 할값
	 /* var code_seq1						= sForm.code_seq1.value;							//순번(key)
	 var code_pers1						= sForm.code_pers1.value;							//회원코드 */

	document.sForm.button_ab.value     			= "a";			//대상검색과 발급현황조회구분값

 $('#list').jqGrid('clearGridData');
   jQuery("#list").setGridParam({url:"license.do?method=issueSandList&code_certifi1="+code_certifi1+"&result_no1="
// 		   JUMIN_DEL
// 		   +"&result_start_dt1=&result_end_dt1=&oper_name1=&oper_no1=&oper_lic_no1="
		   +"&result_start_dt1=&result_end_dt1=&oper_name1=&oper_birth1=&oper_lic_no1="
		   +"&code_operation1="+code_operation1+"&person_yn1=&result_dt1=&oper_hp1=&oper_email1="
		   +"&yyyy1="+yyyy1+"&season1="+season1+"&code_seq1=&code_pers1="}
   		).trigger("reloadGrid");
   
   rowSelected = false;
}

var rowSelected = false;
function goSelect(rowid,iCol){
	  var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	  if( gr != null ) {
	  	var list = $("#list").getRowData(gr);
	  	//alert(list.result_no+""+list.code_seq+""+list.code_pers);		
	  	//그리드에서 select시에 테이블로 출력될 value값들  
	  	
	  	//alert(list.code_certifi);
	  	//document.sForm.code_certifi1.value     		= list.code_certifi;			//자격증명(key)
//	  	alert("교육및시험구분="+list.code_certifi);
	  	//document.sForm.yyyy1.value     				= list.yyyy;					//년도
//	  	alert("년도="+document.sForm.yyyy1.value);
//	  	document.sForm.season1.value     	= list.season;							//발급반기
	  	/* if(list.season == '1'){
			document.getElementById("s1").checked = 'true';
		}else if(list.season == '2'){
			document.getElementById("s2").checked = 'true';
		} */
	  	
		//document.sForm.code_operation1.value  		= list.code_operation;		//취득구분		
	   	  	
	  	document.sForm.code_seq1.value     			= list.code_seq;			//순번
	  	document.sForm.code_pers1.value     		= list.code_pers;			//회원코드
	  	document.sForm.result_no1.value     		= list.result_no;			//자격증번호
	  	/* document.sForm.result_start_dt1.value     	= list.result_start_dt;		//유효시작일
	  	document.sForm.result_end_dt1.value     	= list.result_end_dt;		//유효종료일 */
//	  	alert("유효시작일"+list.result_start_dt);
	  	rowSelected = true;
	  }
	  }

// 발급번호 생성
function licenseNoCreate(form,intPage){
	 var code_certifi1					= sForm.code_certifi1.value;						//자격증명(key)
	 var yyyy1 							= eval(sForm.yyyy1.value);								//년도
	 var season1 						= "";
	 if(sForm.s1.checked		== true )	season1 = "1";							//반기
	 else if(sForm.s2.checked	== true )	season1 = "2";			
	 else  season1="";
	 var code_operation1=sForm.code_operation1.value; 
	 var button=sForm.button_ab.value; 
	 if(button!='a'){
		 alert("자격증 번호 생성은 대상검색 후에 해주십시오. ");
		 return;
	 }
	 var records=jQuery("#list").jqGrid('getGridParam','records')

	if(records==0){
		alert("생성 대상자가 없습니다.");
		return;
	}
	 
   	if ( code_certifi1 != "" && yyyy1 != "" && season1 != ""){	
	   	if ( yyyy1 > "2012" ){	
	   		var start_dt="";
			var end_dt="";
// 			if(season1 == "1"){
// 				start_dt=yyyy1+"년 8월 1일";
// 				end_dt=yyyy1+3+"년 7월 31일";
// 			}else{
// 				start_dt=yyyy1+1+"년 1월 1일";
// 				end_dt=yyyy1+3+"년 12월 31일";
// 			} 
			
			if(season1 == "1"){
				start_dt=yyyy1+"년 1월 1일";
				end_dt=yyyy1+2+"년 12월 31일";
			}else{
				start_dt=yyyy1+"년 8월 1일";
				end_dt=yyyy1+3+"년 7월 31일";
			} 
			
	   		var reg_yn=confirm("자격증 번호를 생성합니다.\r\n\r\n유효기간 : "+start_dt+" ~ "+end_dt+"\r\n\r\n이대로 진행하시겠습니까?");
			
	   		if(reg_yn){
				document.sForm2.action = "license.do?method=licenseNoCreate&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1+"&season1="+season1+"&code_operation1="+code_operation1;		    	
				document.sForm2.submit();
			}else{
				return;
			}
	  	}else{
			alert("과거 데이터로  발급번호생성을 할 수 없습니다.");
			return;
		} 
  	}else{
		alert("자격증구분, 발급년도, 발급반기의 선택 없이 발급번호생성을 할 수 없습니다.");
		return;
	}

 }

//자격증 발급 엑셀 저장   작업중===================================11-13
function excelDown(){
	if(rowSelected){
			alert("엑셀저장을 하시려면 검색을 다시 실행해 주세요.");
			return;
		}
	
	
	var button=sForm.button_ab.value; 
	if(button==""){
		 alert("대상검색이나 발급현황조회를 하신 후에\r\n엑셀 파일을 저장하실 수 있습니다.");
		 return;
	 }
	 var param = "";
	 
		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;		//구분코드(자격증구분)
	 	if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;				//년도
		if(sForm.code_operation1.value	!= "")	param+="&code_operation1="	+sForm.code_operation1.value;	//취득구분
		if(sForm.s1.checked		== true )	param+="&season1=1" ;								
		else if(sForm.s2.checked	== true )	param+="&season1=2" ;
		else param+="&season1=" ;//학기
		
		if(sForm.button_ab.value		!= "")	param+="&button_ab="		+sForm.button_ab.value;			//대상검색과 발급현황조회구분값
		
		param+="&register1="+"<%=g_name%>";
//		alert(param);
		var url="license.do?method=pers_check4"+param;
		window.open(url,"pers_check4","width=400, height=500, left=480, top=200,scrollbars=yes");
		
}
//메일 발송
function sendEmail() {
	
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 메일을 발송 할 수 없습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

// 	if( rownum.length==0)	{
// 		alert("메일을 발송할 회원을 선택해 주십시요.");
// 		return;	
// 	}
		
 	var rowdata=new Array();
 	var toAddr = "";
 	var addr_infos = "";

   	for(var i=0;i<rownum.length;i++){
			
  		rowdata= 	$("#list").getRowData(rownum[i]);
  		
 		if(rowdata.oper_email.length>0){
 			if(toAddr.length > 0) toAddr+= ",";
 			toAddr += escape(encodeURIComponent(rowdata.oper_name));
			
			if(addr_infos.length > 0) {
				addr_infos	+= "__";
			}
			addr_infos	+= rowdata.person_yn1;
 		}
  	}
	//alert("메일발송 = "+addr_infos);
	
 	if( rownum.length==0){	
 		if(rowSelected){
 			alert("전체메일 발송을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
 		toAddr = "ALL";
 	}
 		
 		 var code_certifi1					= sForm.code_certifi1.value;						//자격증명(key)
 		 var code_operation1 				= sForm.code_operation1.value;						//취득구분
 		 var yyyy1 							= sForm.yyyy1.value;								//년도
 		 var season1 						= "";
 		 
 		 if(sForm.s1.checked		== true )	season1 = "1";							//반기
 		 else if(sForm.s2.checked	== true )	season1 = "2";			
 		 else  season1="";

		var button_ab = sForm.button_ab.value;			//대상검색과 발급현황조회구분값

		 var param="&code_certifi1="+code_certifi1
		 +"&code_operation1="+code_operation1
		 +"&yyyy1="+yyyy1
		 +"&season1="+season1
		 +"&button_ab="+button_ab;
		 
		
		
	param+="&paramfrom=LicenseIssue";

	window.open('memberBasic.do?method=eMail&toAddr='+toAddr+param+"&addr_infos="+addr_infos,"oper_email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
}
/* 쪽지 발송 조건   작업중*/
function notePad(form){	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var person_yn1="";
 	var oper_name="";
 	
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);

		if(rowdata.person_yn1.length>0) {
			if(person_yn1.length > 0) person_yn1+= ",";	//code_pers
			person_yn1 += rowdata.person_yn1;
		}
		if(rowdata.oper_name.length>0) {
			if(oper_name.length > 0) oper_name+= ",";	//pers_name
			oper_name += rowdata.oper_name;
		}
	}

	if( rownum.length==0){	
		//여기다 전체 검색(검색과 같은 루트를 탄다.)
		
//조건 검색에 쓰는 조건값만 기입
		var param = "";
		//첫째줄-----------------------------------------------
 		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;					//년도
		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;			//구분코드(자격증구분)
		if(sForm.code_operation1.value	!= "0")	param+="&code_operation1="	+sForm.code_operation1.value;			//구분코드(자격증구분)
		if(sForm.button_ab.value		!= "")	param+="&button_ab="		+sForm.button_ab.value;
		if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else  season1="";
		param+="&season1="			+season1;				//대상검색과 발급현황조회구분값
		//엑션
//		alert("쪽지발송 전체 = "+param);
		var url="license.do?method=sendnotePadAll3"+param;
		window.open(url,"umsData","width=370, height=500, left=280, top=80");	
		
	}else{
//선텍된 항목만 보내기 스트러쳐 등록, 엑션 생성 ,jsp파일 붙이기
		oper_name = escape(encodeURIComponent(oper_name)); 

//		alert("쪽지발송 조건 = "+oper_name+"  person_yn1 = "+person_yn1);
		var url="license.do?method=sendnotePad3&code_pers="+person_yn1+"&pers_name="+oper_name;
		window.open(url,"notePad","width=370, height=500, left=280, top=80");	
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
//			if(i==0){
//				dm_pers_code   =  rowdata.person_yn1;
//				pers_hp		   =  rowdata.oper_hp;
//				pers_name	   =  rowdata.oper_name;
//			}else{
//				dm_pers_code   =  dm_pers_code+","+rowdata.person_yn1;
//				pers_hp	       =  pers_hp+","+rowdata.oper_hp;
//				pers_name      =  pers_name+","+rowdata.oper_name;
//			}
		
		if(rowdata.oper_hp.length>0) {
			if(pers_hp.length > 0){ dm_pers_code+= ","; pers_hp+= ","; pers_name+=",";}	
			dm_pers_code += rowdata.person_yn1;
			pers_hp += rowdata.oper_hp;
			pers_name += escape(encodeURIComponent(rowdata.oper_name));
			
			if(hp_infos.length > 0) {
				hp_infos	+= "__";
			}
			hp_infos	+= rowdata.person_yn1;
		}
	}

	if( rownum.length==0){	
 		if(rowSelected){
 			alert("전체문자 발송을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
		pers_hp = "All";
	}
		
// 		//여기다 전체 검색(검색과 같은 루트를 탄다.)
		
// //조건 검색에 쓰는 조건값만 기입
// 		var param = "";
// 		//첫째줄-----------------------------------------------
//  		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;					//년도
// 		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;			//구분코드(자격증구분)
// 		if(sForm.code_operation1.value	!= "0")	param+="&code_operation1="	+sForm.code_operation1.value;			//구분코드(자격증구분)
// 		if(sForm.button_ab.value		!= "")	param+="&button_ab="		+sForm.button_ab.value;
// 		if(sForm.season1.s1.checked		== true )	season1 = "1";								
// 		 else if(sForm.season1.s2.checked	== true )	season1	= "2";			
// 		 else  season1="";
// 		param+="&season1="			+season1;							//학기

 		 var code_certifi1					= sForm.code_certifi1.value;						//자격증명(key)
 		 var code_operation1 				= sForm.code_operation1.value;						//취득구분
 		 var yyyy1 							= sForm.yyyy1.value;								//년도
 		 var season1 						= "";
 		 
 		 if(sForm.s1.checked		== true )	season1 = "1";							//반기
 		 else if(sForm.s2.checked	== true )	season1 = "2";			
 		 else  season1="";

		var button_ab = sForm.button_ab.value;			//대상검색과 발급현황조회구분값

		 param="&code_certifi1="+code_certifi1
		 +"&code_operation1="+code_operation1
		 +"&yyyy1="+yyyy1
		 +"&season1="+season1
		 +"&button_ab="+button_ab;
		
		param+="&from=license4"+"&hp_infos="+hp_infos;
		var isSelect = "Y";			
		//엑션
//		alert("문자발송 전체 = "+param);
//		var url="license.do?method=smssandAll3"+param;
		var url="license.do?method=smssandAll"+param+"&pers_hp="+pers_hp+"&isSelect="+isSelect+"&pers_name="+pers_name;
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
// 	}else{
// //		alert("문자발송 조건 = "+param);
// //		var url="license.do?method=smssand3&param="+param;

//  		param+="&from=license_1"+"&hp_infos="+hp_infos;
//  		var url="license.do?method=smssand&dm_pers_code="+dm_pers_code+"&pers_hp="+pers_hp+"&pers_name="+pers_name+param;
//   		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
// 	}
}

function goPrint(){	
	if(sForm.button_ab.value!='b'){
		alert("자격증은 발급현황 조회후 출력하실 수 있습니다.");
		return;
	} else if(sForm.code_certifi1.value		=="" || sForm.yyyy1.value		==""){
		alert("출력을 하시려면 자격증구분, 발급년도 선택 후 발급현황을 다시 조회해 주세요.");
		sForm.button_ab.value='';
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();	
	
	var parm ="";
	var result_no="";
	
	if(sForm.yyyy1.value		!="") parm+="&yyyy="	+ sForm.yyyy1.value;
    if(sForm.code_certifi1.value		!="") parm+="&code_certifi="	+ sForm.code_certifi1.value;  
    if(sForm.code_operation1.value	!="0"){ 
    	parm+="&code_operation="		+ sForm.code_operation1.value;
    }else{ 
    	parm+="&code_operation=N";
    }

    if(sForm.s1.checked		== true )	parm+="&season=1";								//반기
	 else if(sForm.s2.checked	== true ) parm+="&season=2";			
	 else  parm+="&season=N";
    
	if( rownum.length==0){
	    //window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_certi_all.jsp?gubun=L1"+parm);		
 	    window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_certi_all.jsp?gubun=L1"+parm);		
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
	    //window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_certi_each.jsp?gubun=L1"+parm);		
 	    window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_certi_each.jsp?gubun=L1"+parm);		
	}
	//sForm.button_ab.value='c';
	licPrinted = true;
}
var licPrinted = false;
function goStateChg(){
	
	alert("점검 중입니다.");
	return;
	
	//if(sForm.button_ab.value!='c'){
	if(licPrinted==false){
		alert("출력상태변경은 자격증 출력후에 하실 수 있습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();	
	
	var parm ="";
	var result_no="";
	var code_pers="";
	if(sForm.yyyy1.value		!="") parm+="&yyyy="	+ sForm.yyyy1.value;  
    if(sForm.code_certifi1.value		!="") parm+="&code_certifi="	+ sForm.code_certifi1.value;  
    if(sForm.code_operation1.value	!="0"){ 
    	parm+="&code_operation="		+ sForm.code_operation1.value;
    }

    if(sForm.s1.checked		== true )	parm+="&season=1";								//반기
	else if(sForm.s2.checked	== true ) parm+="&season=2";			
	
    
	if( rownum.length==0){
	    //document.sForm.action="license.do?method=printStateChg"+parm;
	    //alert(document.sForm.action);
	    //document.sForm.submit();
		$('#list').jqGrid('clearGridData');
	    jQuery("#list").setGridParam({url:"license.do?method=printStateChg"+parm}).trigger("reloadGrid");
	    //alert('');
	}else{	
	 	for(var i=0;i<rownum.length;i++){
			rowdata= 	$("#list").getRowData(rownum[i]);			
			if(i==0){				
				result_no        =  escape(encodeURIComponent(rowdata.result_no));
				code_pers=rowdata.person_yn1;
			}else{
				result_no +=",";
				code_pers +=",";
				result_no += escape(encodeURIComponent(rowdata.result_no)); 	
				code_pers += rowdata.person_yn1;
			}
		}	 	 	
	 	parm+="&result_no="+result_no+"&code_pers="+code_pers+"&sel=Y";
	 	
	 	//document.sForm.action="license.do?method=printStateChg"+parm;
	    //alert(document.sForm.action);
	    //document.sForm.submit();	 
	 	$('#list').jqGrid('clearGridData');
	    jQuery("#list").setGridParam({url:"license.do?method=printStateChg"+parm}).trigger("reloadGrid");
	}
	
	
}
</script>  
 </head>

 <body >
	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="license.do?method=licenseSend">자격증</a></li>		
		<li>&gt;</li>
		<li class="NPage">자격증발급</li>
	  </ul>	 
	  <div class="cTabmenu_01">    
	  <form id="list1" name="sForm" method="post" action="">   
	   <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
						<input type="hidden" name="code_seq1" 		id="code_seq1" 		value=""/>
						<input type="hidden" name="result_no1" 		id="result_no1" 	value=""/>
						<input type="hidden" name="oper_name1" 		id="oper_name1" 	value=""/>
						<input type="hidden" name="code_pers1" 		id="code_pers1" 	value=""/>
<!-- 						JUMIN_DEL -->
<!-- 						<input type="hidden" name="oper_no1" 		id="oper_no1" 		value=""/> -->
						<input type="hidden" name="oper_birth1" 		id="oper_birth1" 		value=""/>
						<input type="hidden" name="oper_lic_no1" 	id="oper_lic_no1" 	value=""/>
						<input type="hidden" name="person_yn1" 		id="person_yn1" 	value=""/>
						<input type="hidden" name="result_dt1" 		id="result_dt1" 	value=""/>
						<input type="hidden" name="oper_hp1" 		id="oper_hp1" 		value=""/>
						<input type="hidden" name="oper_email1" 	id="oper_email1" 	value=""/>
						<input type="hidden" name="codecertifi" 	id="codecertifi" 	value=""/>
						<input type="hidden" name="code_post1" 		id="code_post1" 	value=""/>
						<input type="hidden" name="oper_comp_name11" 		id="oper_comp_name11" 	value=""/>
						<input type="hidden" name="oper_comp_add1_11" 		id="oper_comp_add1_11" 	value=""/>
						<input type="hidden" name="button_ab" 		id="button_ab" 	value=""/>
		<table class="ctable_05" cellspacing="0" summary="자격증">
          <caption>자격증발급</caption>            			 
             <tr>
               <td class="nobg">※&nbsp;&nbsp;자격증구분</td>
               <td class="nobg1">
               		<select  id="code_certifi1" name="code_certifi1">
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

		               	</select>
		       </td>
		       
			   <td class="nobg2" style="width:10%" >※&nbsp;&nbsp;발급년도</td>
               <td class="nobg1">
               				<select  id="yyyy1" name="yyyy1" >	
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
			   <td class="nobg2"   >발급반기</td>
               <td class="nobg1">
               			<input type="radio" name="season1" id="s1" value="" />
				   <label for="s1">상반기</label>&nbsp;&nbsp;
				   <input type="radio" name="season1" id="s2" value="" />
				   <label for="s2">하반기</label>
               </td>
               <td class="nobg2">취득구분</td>
             	<td class="nobg1">
	               	<select  id="code_operation1" name="code_operation1">
		               		<%				 	
						 	for(int i=0;i<licenseserch.size();i++){
						 		if("035".equals(licenseserch.get(i).get("groupcode").toString())){
		                			detCode=licenseserch.get(i).get("detcode").toString();
		                			detCName=licenseserch.get(i).get("detcodename").toString();
		                			out.println("<option value="+detCode+">"+detCName+"</option>");
		                		}
						 	}
						 %>  
		        	</select> 
	        	</td>
             </tr>             
			<!--  <tr>
             
	        <td class="alt"  style="width:10%" >유효기간</td>
            <td><input type="text" id="datepicker" name="result_start_dt1" size="5" /> ~ <input type="text" id="datepicker1" name="result_end_dt1" size="5"/></td>			  
            <td class="alt"></td>
               <td></td>
             </tr> -->
               
			  	
        </table>		
		<ul class="btnIcon_4">		 
		  <li><input type="button" value="자격증번호생성" onclick="javascript:licenseNoCreate(sForm,0);" /></li>		 
		 <li><input type="button" value="대상검색" onclick="javascript:goSearch(sForm,0);"/></li>	
		 <li><input type="button" value="발급현황조회" onclick="javascript:nowSearch(sForm,0);"/></li>	
		</ul>	  
		
		 </form>
 <br></br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post" action=""></form>
<ul class="btnIcon_2" style="left:967px;">		 
<!-- 		  <li><a href="#"><img src="images/icon_s2.gif" onclick="notePad();"	alt="쪽지" /></a></li> -->
		  <!-- <li><a href="javascript:sendEmail();"><img src="images/icon_s3.gif"	onclick=""	alt="메일전송"	/></a></li>	
		  <li><a href="javascript:smssand();"><img src="images/icon_s4.gif"	onclick=""	alt="문자전송"	/></a></li> -->
		 <!--  <li><a href="#"><img src="images/icon_s16.gif" alt="엑셀올리기" /></a></li>	 -->	 
		  <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" alt="엑셀저장"/></a></li>
		  <li><a href="javascript:goPrint();"><img src="images/icon_s12.gif" onclick="" alt="출력"/></a></li>
<!-- 		  <li><a href="javascript:goStateChg();"><img src="images/icon_s20.gif" onclick="" /></a></li>	 -->
		</ul>
 </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	
 
 </body> 
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>