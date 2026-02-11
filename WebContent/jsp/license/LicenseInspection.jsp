<%@page import="com.ant.educationlecture.dao.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>

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

JSONArray j_test=(JSONArray)request.getAttribute("j_test");

Map etest2 = (Map)request.getAttribute("etest2");
int j_eds=0;
if(j_test!=null){
	j_eds=j_test.size();
}
String v_yyyy11="";
String v_code_bran11="";
String v_code_certifi11="";
int etk=0;
if(etest2!=null){
	v_yyyy11=etest2.get("yyyy1").toString();
	v_code_bran11=etest2.get("code_bran1").toString();
	v_code_certifi11=etest2.get("code_certifi1").toString();
	etk=etest2.size();
}
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
     // width:'1100',
      height:'487',
      colNames: [ '자격증구분','대상자','이름',
//                   JUMIN_DEL
//                   '주민번호',
                  '생년월일',
                  '면허번호','핸드폰','이메일','근무처','회원구분','1학기 점수','2학기 점수','집합교육 점수','온라인교육 점수','대상자코드값','자격증발급서류 제출여부','종류'
                  ,'순번','지부','지부순번','접수번호',
//                   '주민번호1',
                  'code','자격구분코드값','회원코드1'
                  ,'지부 OTHER','지부순번 OTHER','접수번호 OTHER','대상자코드값 OTHER'
                  ],
      colModel: [
			{name:'codecertifi',  			index:'codecertifi', 		width: 100,editable: false, align: 'center'},	//자격구분텍스트값
			{name:'codeoperation',    		index:'codeoperation',   	width: 80,editable: false, align: 'left'},		//대상자텍스트값
			{name:'oper_name',    			index:'oper_name',   		width: 80,editable: false, align: 'left'},		//이름
// 			JUMIN_DEL
//    			{name:'oper_no',      			index:'oper_no',     		width: 100,editable: false, align: 'center' , formatter:juminNo},		//주민번호
   			{name:'oper_birth',      			index:'oper_birth',     		width: 100,editable: false, align: 'center'},		//생년월일
   			{name:'oper_lic_no',    		index:'oper_lic_no',   		width: 100,editable: false, align: 'center'},	//면허번호
   			{name:'oper_hp',     			index:'oper_hp',    		width: 100,editable: false, align: 'left'},		//핸드폰
   			{name:'oper_email',       		index:'oper_email',      	width: 150,editable: false, align: 'left'},		//이메일
   			{name:'oper_comp_name1',    	index:'oper_comp_name1',    width: 250,editable: false, align: 'left'},		//근무처
   			{name:'person_yn',      		index:'person_yn',     		width: 120,editable: false, align: 'left'},		//회원구분
   			{name:'season1',    			index:'season1',   			width: 70,editable: false, align: 'center'},	//1학기
   			{name:'season2',    			index:'season2',   			width: 70,editable: false, align: 'center'},	//2학기
   			{name:'season3',    			index:'season3',   			width: 90,editable: false, align: 'center'},	//집합교육
   			{name:'season4',    			index:'season4',   			width: 100,editable: false, align: 'center'},	//온라인교육
   			//{name:'result_point',    		index:'result_point',   	width: 50,editable: false, align: 'center'},	//점수
   			{name:'code_operation',       	index:'code_operation', 			hidden:true},		//대상자 코드값
   			{name:'detcodename',    		index:'detcodename',   		width: 400, editable: false, align: 'left'},		//자격증발급서류제출여부	
   			//{name:'yyyy',      				index:'yyyy', 		     			hidden:true},		//년도
   			{name:'code_kind',      		index:'code_kind',      			hidden:true},		//종류
   			{name:'code_seq',      			index:'code_seq',      				hidden:true},		//순번
   			{name:'code_bran',      		index:'code_bran',      			hidden:true},		//지부
   			{name:'bran_seq',      			index:'bran_seq',      				hidden:true},		//지부순번
   			{name:'receipt_no',      		index:'receipt_no',      			hidden:true},		//접수번호
//    			{name:'oper_no1',      			index:'oper_no1',       			hidden:true},		//주민번호 히든값
   			//{name:'edutest_name',       	index:'edutest_name', 				hidden:true},		//내용값
   			{name:'detcode',       			index:'detcode', 					hidden:true},		//내용값
   			{name:'code_certifi',       	index:'code_certifi', 				hidden:true},		//자격구분 코드값
   			{name:'person_yn1',      		index:'person_yn1',       			hidden:true},		//회원코드1
   			
   			{name:'code_bran_other',      		index:'code_bran_other',      			hidden:true},		//지부 OTHER
   			{name:'bran_seq_other',      			index:'bran_seq_other',      				hidden:true},		//지부순번 OTHER
   			{name:'receipt_no_other',      		index:'receipt_no_other',      			hidden:true},		//접수번호 OTHER
   			{name:'code_operation_other',      		index:'code_operation_other',      			hidden:true}		//접수번호 OTHER
   			
   			],
   		    rowNum:20,
   		    //sortname: 'receipt_dt',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			gridview: true,
 			caption: '자격증심사',
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
});
 
 function powerinit(){

		var userpowerm1=eval(<%=userpowerm1%>);
		var meprogid = "303";
		var bcheck = "N";
//		alert("userpowerm1[i].progid===>"+userpowerm1.length);
//		alert(userpowerm1);
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
 
 
 $(function() {
		$( "#datepicker" ).datepicker();
	}); 
 
 

 function goClear(){	
	 document.sForm3.reset();
	 document.sForm.receipt_no1.value = "";
	 var obj=document.getElementById("chkbx");
	 obj.innerHTML='';
	 }

	function init(){
		var logid="<%=logid%>";	
		if(logid==null||logid==""){
			alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
			opener.document.location.href="login.do?method=login";
			self.close();
		}
		if('<%=etk%>'>0){
			document.getElementById("yyyy1").value='<%=v_yyyy11%>';				
			document.getElementById("code_bran1").value='<%=v_code_bran11%>';		
			document.getElementById("code_certifi1").value='<%=v_code_certifi11%>'; 
				
			alert('<%=errMsg%>');
			goSearch(sForm,0);
		}
	}

	
// JUMIN_DEL
	// 	function juminNo(cellvalue, options, rowObject ){
// 		var jumin=cellvalue.substring(0,6)+"*******";
// 		return jumin;
// 	}
 
	function goSearch(form,intPage){
		/* 검색버튼 클릭시 테이블에서 그리드로 올리는 value 값 자격증 심사 */
		 var code_certifi1					= sForm.code_certifi1.value;				//자격증구분(key)
		 var code_operation1 				= sForm.code_operation1.value;										//대상자

		 var oper_name1 	= escape(encodeURIComponent(sForm3.oper_name1.value));		//이름
// 		 JUMIN_DEL
// 		 var oper_no1 						= sForm3.oper_no11.value+sForm3.oper_no12.value; //주민번호		
		 var oper_birth1					= sForm3.oper_birth1.value;				//생년월일
		 var oper_lic_no1					= sForm3.oper_lic_no1.value;				//면허번호
		 var oper_hp1						= sForm.oper_hp1.value;						//핸드폰
		 var oper_email1 					= sForm.oper_email1.value;					//이메일
		 var oper_comp_name11 				= sForm.oper_comp_name11.value;				//근무처
		 var person_yn1 					= sForm.person_yn1.value;					//회원구분 
		 var season1 						= "";										//학기
		 
		  var param = "";

		  
		var yyyy1				= sForm.yyyy1.value;									//년도
		 // 그리드로 넘길때 히든으로 가지고 있어야 할값
		 var code_kind1				= sForm.code_kind1.value;							//종류(key)
		 var code_seq1				= sForm.code_seq1.value;							//순번(key)
		 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
		 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
		 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
		 var result_point1			= sForm.result_point1.value;						//점수
		 if(yyyy1==""){alert("년도를 선택해 주세요."); return;}
	 $('#list').jqGrid('clearGridData');
	   jQuery("#list").setGridParam({url:"license.do?method=inspectionSandList&code_certifi1="+code_certifi1+"&code_operation1="+code_operation1
			   +"&oper_name1="+oper_name1
// 			   JUMIN_DEL
// 			   +"&oper_no1="+oper_no1
			   +"&oper_birth1="+oper_birth1
			   +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1
			   +"&oper_comp_name11="+oper_comp_name11+"&person_yn1="+person_yn1+"&yyyy1="+yyyy1+"&code_kind1="+code_kind1
			   +"&code_seq1="+code_seq1+"&code_bran1="+code_bran1+"&bran_seq1="+bran_seq1+"&receipt_no1="+receipt_no1+param
			   +"&result_point1="+result_point1}
	   		).trigger("reloadGrid");
	   rowSelected = false;
	}
	
	 var rowSelected = false;
	  function goSelect(rowid,iCol){
		  var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
		  if( gr != null ) {
		  	var list = $("#list").getRowData(gr);
		  			
		  	//그리드에서 select시에 테이블로 출력될 value값들  

		  	document.sForm3.oper_name1.value     		= list.oper_name;				//이름
// 		  	JUMIN_DEL
// 		  	document.sForm3.oper_no11.value      		= list.oper_no.substring(0,6);				//주민번호
// 			document.sForm3.oper_no12.value      		= list.oper_no1.substring(6,13);			//주민번호
		  	document.sForm3.oper_birth1.value     		= list.oper_birth;				//생년월일
		  	document.sForm3.oper_lic_no1.value     		= list.oper_lic_no;				//면허번호
		  	document.sForm3.code_operation1.value     	= list.code_operation;			//대상자
		  	document.sForm.codeoperation.value 			= list.codeoperation;
		  	var add_file_no = list.detcodename;										//자격증발급서류제출여부
			
			var det = add_file_no.split(",") ;
			var obj=document.getElementById("chkbx");
		
		  	document.sForm.code_kind1.value     		= list.code_kind;			//종류
		  	document.sForm.code_seq1.value     			= list.code_seq;			//순번
		  	document.sForm.code_bran1.value     		= list.code_bran;			//지부
		  	document.sForm.bran_seq1.value     			= list.bran_seq;			//지부순번
		  	document.sForm.receipt_no1.value     		= list.receipt_no;			//접수번호
		  	document.sForm.code_certifi1.value     		= list.code_certifi;			//자격증구분
		  	
		   
		  	if(list.code_operation==1 || list.code_operation==7){		  		
		  		obj.innerHTML='<input type="checkbox" name="add_file_no0" id="ff" onclick="selall()"/>&nbsp;전체선택&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no1" id="f0" />&nbsp;신청서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no2" id="f1" />&nbsp;사진&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no3" id="f2" />&nbsp;영양사면허증사본&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no4" id="f3" />&nbsp;경력증명서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no8" id="f7" />&nbsp;발급회비&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no9" id="f8" />&nbsp;압축파일&nbsp;&nbsp;&nbsp;';
		  	}else if(list.code_operation==3){		  		
		  		obj.innerHTML='<input type="checkbox" name="add_file_no0" id="ff" onclick="selall()"/>&nbsp;전체선택&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no1" id="f0" />&nbsp;신청서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no2" id="f1" />&nbsp;사진&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no3" id="f2" />&nbsp;영양사면허증사본&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no4" id="f3" />&nbsp;경력증명서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no5" id="f4" />&nbsp;학위증명서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no6" id="f5" />&nbsp;최종학위성적증명서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no8" id="f7" />&nbsp;발급회비&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no9" id="f8" />&nbsp;압축파일&nbsp;&nbsp;&nbsp;';
		  	}else if(list.code_operation==4){		  		
		  		obj.innerHTML='<input type="checkbox" name="add_file_no0" id="ff" onclick="selall()"/>&nbsp;전체선택&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no1" id="f0" />&nbsp;신청서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no2" id="f1" />&nbsp;사진&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no3" id="f2" />&nbsp;영양사면허증사본&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no7" id="f6" />&nbsp;기존자격증원본&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no8" id="f7" />&nbsp;발급회비&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no9" id="f8" />&nbsp;압축파일&nbsp;&nbsp;&nbsp;';
		  	}else if(list.code_operation==6){		  		
		  		obj.innerHTML='<input type="checkbox" name="add_file_no0" id="ff" onclick="selall()"/>&nbsp;전체선택&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no1" id="f0" />&nbsp;신청서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no2" id="f1" />&nbsp;사진&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no3" id="f2" />&nbsp;영양사면허증사본&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no4" id="f3" />&nbsp;경력증명서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no10" id="f9" />&nbsp;외국취득면허사본&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no8" id="f7" />&nbsp;발급회비&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no9" id="f8" />&nbsp;압축파일&nbsp;&nbsp;&nbsp;';
		  	}else if(list.code_operation==8){		  		
		  		obj.innerHTML='<input type="checkbox" name="add_file_no0" id="ff" onclick="selall()"/>&nbsp;전체선택&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no1" id="f0" />&nbsp;신청서&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no2" id="f1" />&nbsp;사진&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no3" id="f2" />&nbsp;영양사면허증사본&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no8" id="f7" />&nbsp;발급회비&nbsp;&nbsp;&nbsp;<input type="checkbox" name="add_file_no9" id="f8" />&nbsp;압축파일&nbsp;&nbsp;&nbsp;';
		  	}
		  			  	
		  	for(var i=0; i<det.length; i++){

//				alert("==>"+det[i]+"<==");
				if(det[i] == "신청서") 				sForm3.add_file_no1.checked = true;
				if(det[i] == "사진")					sForm3.add_file_no2.checked = true;
				if(det[i] == "영양사면허증사본")		sForm3.add_file_no3.checked = true;
				if(det[i] == "경력증명서") 			sForm3.add_file_no4.checked = true;
				if(det[i] == "학위증명서") 			sForm3.add_file_no5.checked = true;
				if(det[i] == "최종학위성적증명서")	sForm3.add_file_no6.checked = true;
				if(det[i] == "기존자격증사본") 		sForm3.add_file_no7.checked = true;
				if(det[i] == "발급회비") 			sForm3.add_file_no8.checked = true;
				if(det[i] == "압축파일") 			sForm3.add_file_no9.checked = true;
				if(det[i] == "외국취득면허사본") 	 	sForm3.add_file_no10.checked = true;
			}
		  	
		  	document.sForm.code_bran_other1.value     		= list.code_bran_other;			//지부 OTHER
		  	document.sForm.bran_seq_other1.value     			= list.bran_seq_other;			//지부순번 OTHER
		  	document.sForm.receipt_no_other1.value     		= list.receipt_no_other;			//접수번호 OTHER
		  	document.sForm.code_operation_other1.value     		= list.code_operation_other;			//대상자코드값 OTHER
		  	
		  	//alert(list.code_kind+','+list.code_seq+','+list.code_bran+','+list.bran_seq+','+list.receipt_no);
			rowSelected = true;
		  }
		  }
	function selall(){
		var val=document.sForm3.code_operation1.value;
		if(val==1||val==7){
			sForm3.add_file_no1.checked = true;
			sForm3.add_file_no2.checked = true;
			sForm3.add_file_no3.checked = true;
			sForm3.add_file_no4.checked = true;
			sForm3.add_file_no8.checked = true;
		}else if(val==3){
			sForm3.add_file_no1.checked = true;
			sForm3.add_file_no2.checked = true;
			sForm3.add_file_no3.checked = true;
			sForm3.add_file_no4.checked = true;
			sForm3.add_file_no5.checked = true;
			sForm3.add_file_no6.checked = true;
			sForm3.add_file_no8.checked = true;
		}else if(val==4){
			sForm3.add_file_no1.checked = true;
			sForm3.add_file_no2.checked = true;
			sForm3.add_file_no3.checked = true;
			sForm3.add_file_no7.checked = true;
			sForm3.add_file_no8.checked = true;
		}else if(val==6){
			sForm3.add_file_no1.checked = true;
			sForm3.add_file_no2.checked = true;
			sForm3.add_file_no3.checked = true;
			sForm3.add_file_no4.checked = true;
			sForm3.add_file_no10.checked = true;
			sForm3.add_file_no8.checked = true;
		}else if(val==8){
			sForm3.add_file_no1.checked = true;
			sForm3.add_file_no2.checked = true;
			sForm3.add_file_no3.checked = true;			
			sForm3.add_file_no8.checked = true;
		}
	}	   	
	
	  function goSave(){	
		  	 var form=document.sForm;
		     var code_certifi1					= sForm.code_certifi1.value;				//자격증구분(key)
//			 var edutest_name1 		      		= sForm.edutest_name1.value;				//내용
			
			 var code_operation1 				= "";										//대상자
			 var yyyy1 = sForm.yyyy1.value;
				   if(document.getElementById("code_operation1").value	== '1' )	code_operation1 = "1";								
			  else if(document.getElementById("code_operation1").value	== '3'  )	code_operation1	= "3";
			  else if(document.getElementById("code_operation1").value	== '4'  )	code_operation1	= "4";
			  else if(document.getElementById("code_operation1").value	== '6'  )	code_operation1	= "6";
			  else if(document.getElementById("code_operation1").value	== '7'  )	code_operation1	= "7";
			  else if(document.getElementById("code_operation1").value	== '8'  )	code_operation1	= "8";			  
			
			 var oper_name1 	= escape(encodeURIComponent(sForm3.oper_name1.value));		//이름

			  
			  var param = "";

			  var chk=0;
			if(code_operation1==1){
				if(sForm3.add_file_no1.checked == true){
					param+='21 ';
					chk+=1;
				}  
				if(sForm3.add_file_no2.checked == true){
					param+='22 ';
					chk+=1;
				}
				if(sForm3.add_file_no3.checked == true){
					param+='23 ';
					chk+=1;
				}
				if(sForm3.add_file_no4.checked == true){
					param+='24 ';
					chk+=1;
				}
				if(sForm3.add_file_no8.checked == true){
					param+='25';
					chk+=1;
				}				
			}else if(code_operation1==3){
				if(sForm3.add_file_no1.checked == true){
					param+='41 ';
					chk+=1;
				}
				if(sForm3.add_file_no2.checked == true){
					param+='42 ';
					chk+=1;
				}
				if(sForm3.add_file_no3.checked == true){
					param+='43 ';
					chk+=1;
				}
				if(sForm3.add_file_no4.checked == true){
					param+='44 ';
					chk+=1;
				}
				if(sForm3.add_file_no5.checked == true){
					param+='45 ';
					chk+=1;
				}
				if(sForm3.add_file_no6.checked == true){
					param+='46 ';
					chk+=1;
				}
				if(sForm3.add_file_no8.checked == true){
					param+='47';
					chk+=1;
				}
			}else if(code_operation1==4){
				if(sForm3.add_file_no1.checked == true){
					param+='51 ';
					chk+=1;
				}
				if(sForm3.add_file_no2.checked == true){
					param+='52 ';
					chk+=1;
				}
				if(sForm3.add_file_no3.checked == true){
					param+='53 ';
					chk+=1;
				}
				if(sForm3.add_file_no7.checked == true){
					param+='54 ';
					chk+=1;
				}
				if(sForm3.add_file_no8.checked == true){
					param+='55';
					chk+=1;
				}
			}else if(code_operation1==6){				
				if(sForm3.add_file_no1.checked == true){
					param+='71 ';
					chk+=1;
				}
				if(sForm3.add_file_no2.checked == true){
					param+='72 ';
					chk+=1;
				}
				if(sForm3.add_file_no3.checked == true){
					param+='73 ';
					chk+=1;
				}
				if(sForm3.add_file_no4.checked == true){
					param+='74 ';
					chk+=1;
				}
				if(sForm3.add_file_no10.checked == true){
					param+='75 ';
					chk+=1;
				}
				if(sForm3.add_file_no8.checked == true){
					param+='76';
					chk+=1;
				}
			}else if(code_operation1==7){
				if(sForm3.add_file_no1.checked == true){
					param+='81 ';
					chk+=1;
				}  
				if(sForm3.add_file_no2.checked == true){
					param+='82 ';
					chk+=1;
				}
				if(sForm3.add_file_no3.checked == true){
					param+='83 ';
					chk+=1;
				}
				if(sForm3.add_file_no4.checked == true){
					param+='84 ';
					chk+=1;
				}
				if(sForm3.add_file_no8.checked == true){
					param+='85';
					chk+=1;
				}
			}else if(code_operation1==8){
				if(sForm3.add_file_no1.checked == true){
					param+='91 ';
					chk+=1;
				}  
				if(sForm3.add_file_no2.checked == true){
					param+='92 ';
					chk+=1;
				}
				if(sForm3.add_file_no3.checked == true){
					param+='93 ';
					chk+=1;
				}
				if(sForm3.add_file_no8.checked == true){
					param+='94';
					chk+=1;
				}
			}
		  
//			var yyyy1				= sForm.yyyy1.value;									//년도
			 // 그리드로 넘길때 히든으로 가지고 있어야 할값
			 var code_kind1				= sForm.code_kind1.value;							//종류(key)
			 var code_seq1				= sForm.code_seq1.value;							//순번(key)
			 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
			 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
			 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
			 			 
			 var chk2="";
			 
			 if(code_operation1==1&&chk==5) chk2="Y";
			 else if(code_operation1==3&&chk==7) chk2="Y";
			 else if(code_operation1==4&&chk==4) chk2="Y";
			 else if(code_operation1==6&&chk==6) chk2="Y";
			 else if(code_operation1==7&&chk==5) chk2="Y";
			 else if(code_operation1==8&&chk==4) chk2="Y";
			 else chk2="N";

			 document.sForm.action = "license.do?method=inspectionSave&code_certifi1="+code_certifi1+"&code_kind1="+code_kind1+"&yyyy1="+yyyy1+"&code_seq1="+code_seq1
			 +"&oper_name1="+oper_name1+"&code_bran1="+code_bran1+"&bran_seq1="+bran_seq1+"&receipt_no1="+receipt_no1+"&code_operation1="+code_operation1+
			 "&param="+param+"&chk="+chk2; 
            document.sForm.submit();  
			return;
		}
	  
	  
	  
	//자격증 심사 엑셀 저장   
	  function excelDown(){
			if(rowSelected){
	 			alert("엑셀저장을 하시려면 검색을 다시 실행해 주세요.");
	 			return;
	 		}
		
		  var param = "";
			 
// 			if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;		//구분코드(자격증구분)
// 		 	if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;				//년도
// 			if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;		//지부(교육주최)
// 			if(sForm.code_operation1.value  !="" )	param+="&code_operation1="+ sForm.code_operation1.value;											
// 			if(sForm3.oper_name1.value		!= "")	param+="&oper_name1="		+sForm3.oper_name1.value;			//이름
// // 			JUMIN_DEL
// // 		  	if(sForm3.oper_no11.value != ""&&sForm3.oper_no12.value != "")	param+="&oper_no1="		+sForm3.oper_no11.value+""+sForm3.oper_no12.value;
// 			if(sForm3.oper_birth1.value		!= "")	param+="&oper_birth1="		+sForm3.oper_birth1.value;		
			
			 var code_certifi1					= sForm.code_certifi1.value;				//자격증구분(key)
			 var code_operation1 				= sForm.code_operation1.value;										//대상자
			 var oper_name1 	= escape(encodeURIComponent(sForm3.oper_name1.value));		//이름
			 var oper_birth1					= sForm3.oper_birth1.value;				//생년월일
			 var oper_lic_no1					= sForm3.oper_lic_no1.value;				//면허번호
			 var oper_hp1						= sForm.oper_hp1.value;						//핸드폰
			 var oper_email1 					= sForm.oper_email1.value;					//이메일
			 var oper_comp_name11 				= sForm.oper_comp_name11.value;				//근무처
			 var person_yn1 					= sForm.person_yn1.value;					//회원구분 
			 var season1 						= "";										//학기
			var yyyy1				= sForm.yyyy1.value;									//년도
			
			 var code_kind1				= sForm.code_kind1.value;							//종류(key)
			 var code_seq1				= sForm.code_seq1.value;							//순번(key)
			 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
			 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
			 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
			 var result_point1			= sForm.result_point1.value;						//점수

			 param="&code_certifi1="+code_certifi1
			 +"&code_operation1="+code_operation1
			 +"&oper_name1="+oper_name1
			 +"&oper_birth1="+oper_birth1
			 +"&oper_lic_no1="+oper_lic_no1
			 +"&oper_hp1="+oper_hp1
			 +"&oper_email1="+oper_email1
			 +"&oper_comp_name11="+oper_comp_name11
			 +"&person_yn1="+person_yn1
			 +"&yyyy1="+yyyy1
			 +"&code_kind1="+code_kind1
			 +"&code_seq1="+code_seq1
			 +"&code_bran1="+code_bran1
			 +"&bran_seq1="+bran_seq1
			 +"&receipt_no1="+receipt_no1+param
			 +"&result_point1="+result_point1;

			 param+="&register1="+"<%=g_name%>";

			var url="license.do?method=pers_check3"+param;
			window.open(url,"pers_check3","width=400, height=500, left=480, top=200,scrollbars=yes");
		
	  	}
		
		//파일첨부
	  	function uFile(){ 
		 	 var code_kind1				= sForm.code_kind1.value;							//종류(key)
			 var code_certifi1			= sForm.code_certifi1.value;						//구분(key)
			 var code_seq1				= sForm.code_seq1.value;							//순번(key)
			 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
			 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
			 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
			 
			 var code_bran_other1 			= sForm.code_bran_other1.value;							//지부(key) OTHER
			 var bran_seq_other1				= sForm.bran_seq_other1.value;							//지부순번(key) OTHER
			 var receipt_no_other1			= sForm.receipt_no_other1.value;							//접수번호(key) OTHER
			 var code_operation_other1			= sForm.code_operation_other1.value;							//대상자코드값 OTHER
			 
			 
			 
			 var oper_name1				= escape(encodeURIComponent(sForm3.oper_name1.value));							//이름
			 var yyyy1					= sForm.yyyy1.value;
			 var codeoperation		= "";						//대상자text값
			 var code_operation1		= sForm3.code_operation1.value;						//대상자코드값
			 
			 if(code_operation1=='1'){
				 codeoperation=escape(encodeURIComponent("교육이수"));	
			 }else if(code_operation1=='3'){
				 codeoperation=escape(encodeURIComponent("석사이상"));	
			 }else  if(code_operation1=='4'){
				 codeoperation=escape(encodeURIComponent("말소자"));	
			 }else  if(code_operation1=='6'){
				 codeoperation=escape(encodeURIComponent("외국면허소지자"));	
			 }else  if(code_operation1=='7'){
				 codeoperation=escape(encodeURIComponent("교육이수재시험"));	
			 }else  if(code_operation1=='8'){
				 codeoperation=escape(encodeURIComponent("자격시험재시험"));	
			 }
		
		   if ( code_kind1 != "" && code_certifi1 != "" && code_seq1 != ""&& code_bran1 != ""&& bran_seq1 != ""&& receipt_no1 != ""&& oper_name1 != "" && codeoperation != ""){ 
			  
			window.open('license.do?method=uFile&code_kind1='+code_kind1+'&code_certifi1='+code_certifi1
					+'&code_seq1='+code_seq1+'&code_bran1='+code_bran1+'&bran_seq1='+bran_seq1
					+'&receipt_no1='+receipt_no1+'&oper_name1='+oper_name1+'&code_operation1='+code_operation1+'&codeoperation='+codeoperation+"&season1=&yyyy1="+yyyy1+"&edutest_name1="
					+'&code_bran_other1='+code_bran_other1+'&bran_seq_other1='+bran_seq_other1+'&receipt_no_other1='+receipt_no_other1+'&code_operation_other1='+code_operation_other1
					,"uFile","scrollbar=no,resizable=no,toolbar=no,width=675,height=180,left=20,top=29,status=yes");
		  }else{
			  alert("자격증심사 목록에서 심사대상을 선택해 주세요.");
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
		
	  	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

// 	  	if( rownum.length==0)	{
// 	  		alert("메일을 발송할 회원을 선택해 주십시요.");
// 	  		return;	
// 	  	}
	  		
	   	var rowdata=new Array();
	   	var param="";
	 	var addr_infos = "";

	   	for(var i=0;i<rownum.length;i++){
	  			
	  		rowdata= 	$("#list").getRowData(rownum[i]);
	  		
	 		if(rowdata.oper_email.length>0){
	 			if(param.length > 0) param+= ",";
//	  			param += rowdata.oper_email;
	 			param += escape(encodeURIComponent(rowdata.oper_name));
				
				if(addr_infos.length > 0) {
					addr_infos	+= "__";
				}
				addr_infos	+= rowdata.code_kind+"_";
				addr_infos	+= rowdata.code_certifi+"_";
				addr_infos	+= rowdata.code_seq+"_";
				addr_infos	+= rowdata.code_bran+"_";
				addr_infos	+= rowdata.bran_seq+"_";
				addr_infos	+= rowdata.receipt_no;
	 		}
	  	}
	  	//alert("메일발송 = "+param);
	  	
	 	if( rownum.length==0){	
	 		if(rowSelected){
	 			alert("전체메일 발송을 하시려면 검색을 다시 실행해 주세요.");
	 			return;
	 		}
	 		
			 var code_certifi1					= sForm.code_certifi1.value;				//자격증구분(key)
			 var code_operation1 				= sForm.code_operation1.value;										//대상자
			 var oper_name1 	= escape(encodeURIComponent(sForm3.oper_name1.value));		//이름
			 var oper_birth1					= sForm3.oper_birth1.value;				//생년월일
			 var oper_lic_no1					= sForm3.oper_lic_no1.value;				//면허번호
			 var oper_hp1						= sForm.oper_hp1.value;						//핸드폰
			 var oper_email1 					= sForm.oper_email1.value;					//이메일
			 var oper_comp_name11 				= sForm.oper_comp_name11.value;				//근무처
			 var person_yn1 					= sForm.person_yn1.value;					//회원구분 
			 var season1 						= "";										//학기
			var yyyy1				= sForm.yyyy1.value;									//년도
			
			 var code_kind1				= sForm.code_kind1.value;							//종류(key)
			 var code_seq1				= sForm.code_seq1.value;							//순번(key)
			 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
			 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
			 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
			 var result_point1			= sForm.result_point1.value;						//점수

			 param="&code_certifi1="+code_certifi1
			 +"&code_operation1="+code_operation1
			 +"&oper_name1="+oper_name1
			 +"&oper_birth1="+oper_birth1
			 +"&oper_lic_no1="+oper_lic_no1
			 +"&oper_hp1="+oper_hp1
			 +"&oper_email1="+oper_email1
			 +"&oper_comp_name11="+oper_comp_name11
			 +"&person_yn1="+person_yn1
			 +"&yyyy1="+yyyy1
			 +"&code_kind1="+code_kind1
			 +"&code_seq1="+code_seq1
			 +"&code_bran1="+code_bran1
			 +"&bran_seq1="+bran_seq1
			 +"&receipt_no1="+receipt_no1+param
			 +"&result_point1="+result_point1;
			 
			param="ALL"+param;
		}
		param+="&paramfrom=LicenseInspection";
		
	  	window.open('memberBasic.do?method=eMail&toAddr='+param+"&addr_infos="+addr_infos,"oper_email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
	  }

	  /* 쪽지 발송 조건  */
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
	  		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;			//지부(교육주최)	  		
	  		if(sForm.code_operation1.value  != "")	param+="&code_operation1="+sForm.code_operation1.value; 	  		
	  		if(sForm3.oper_name1.value		!= "")	param+="&oper_name1="		+sForm3.oper_name1.value;			//이름
// 	  		JUMIN_DEL
// 	  		if(sForm3.oper_no11.value != ""&&sForm3.oper_no12.value != "")	param+="&oper_no1="		+sForm3.oper_no11.value+""+sForm3.oper_no12.value;
	  		if(sForm3.oper_birth1.value		!= "")	param+="&oper_birth1="		+sForm3.oper_birth1.value;			
	  		
	  		var url="license.do?method=sendnotePadAll2"+param;
	  		window.open(url,"umsData","width=370, height=500, left=280, top=80");	
	  		
	  	}else{
	  //선텍된 항목만 보내기 스트러쳐 등록, 엑션 생성 ,jsp파일 붙이기
	  		oper_name = escape(encodeURIComponent(oper_name)); 

//	  		alert("쪽지발송 조건 = "+oper_name+"  person_yn1 = "+person_yn1);
	  		var url="license.do?method=sendnotePad2&code_pers="+person_yn1+"&pers_name="+oper_name;
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
// 			if(i==0){
// 				dm_pers_code   =  rowdata.person_yn1;
// 				pers_hp		   =  rowdata.oper_hp;
// 				pers_name	   =  rowdata.oper_name;
// 			}else{
// 				dm_pers_code   =  dm_pers_code+","+rowdata.person_yn1;
// 				pers_hp	       =  pers_hp+","+rowdata.oper_hp;
// 				pers_name      =  pers_name+","+rowdata.oper_name;
// 			}
			
			if(rowdata.oper_hp.length>0) {
				if(pers_hp.length > 0){ dm_pers_code+= ","; pers_hp+= ","; pers_name+=",";}	
				dm_pers_code += rowdata.person_yn1;
				pers_hp += rowdata.oper_hp;
				pers_name += escape(encodeURIComponent(rowdata.oper_name));
				
				if(hp_infos.length > 0) {
					hp_infos	+= "__";
				}
				hp_infos	+= rowdata.code_kind+"_";
				hp_infos	+= rowdata.code_certifi+"_";
				hp_infos	+= rowdata.code_seq+"_";
				hp_infos	+= rowdata.code_bran+"_";
				hp_infos	+= rowdata.bran_seq+"_";
				hp_infos	+= rowdata.receipt_no;
			}
		}


	  	if( rownum.length==0){	
	 		if(rowSelected){
	 			alert("전체문자 발송을 하시려면 검색을 다시 실행해 주세요.");
	 			return;
	 		}

// 	 		//여기다 전체 검색(검색과 같은 루트를 탄다.)
	  		
// 	  //조건 검색에 쓰는 조건값만 기입
// 	  		var param = "";
// 	  		//첫째줄-----------------------------------------------
// 	   		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;					//년도
// 	  		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;			//구분코드(자격증구분)
// 	  		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;			//지부(교육주최)	  		
// 	  		if(sForm.code_operation1.value  != "")	param+="&code_operation1="+sForm.code_operation1.value; 	  		
// 	  		if(sForm3.oper_name1.value		!= "")	param+="&oper_name1="		+sForm3.oper_name1.value;			//이름
// // 	  		JUMIN_DEL
// // 	  		if(sForm3.oper_no11.value != ""&&sForm3.oper_no12.value != "")	param+="&oper_no1="		+sForm3.oper_no11.value+""+sForm3.oper_no12.value;  
// 	  		if(sForm3.oper_birth1.value		!= "")	param+="&oper_birth1="		+sForm3.oper_birth1.value;			
// 	  		if(sForm3.oper_lic_no1.value		!= "")	param+="&oper_lic_no1="		+sForm3.oper_lic_no1.value;			//이름
	  		
			 var code_certifi1					= sForm.code_certifi1.value;				//자격증구분(key)
			 var code_operation1 				= sForm.code_operation1.value;										//대상자
			 var oper_name1 	= escape(encodeURIComponent(sForm3.oper_name1.value));		//이름
			 var oper_birth1					= sForm3.oper_birth1.value;				//생년월일
			 var oper_lic_no1					= sForm3.oper_lic_no1.value;				//면허번호
			 var oper_hp1						= sForm.oper_hp1.value;						//핸드폰
			 var oper_email1 					= sForm.oper_email1.value;					//이메일
			 var oper_comp_name11 				= sForm.oper_comp_name11.value;				//근무처
			 var person_yn1 					= sForm.person_yn1.value;					//회원구분 
			 var season1 						= "";										//학기
			var yyyy1				= sForm.yyyy1.value;									//년도
			
			 var code_kind1				= sForm.code_kind1.value;							//종류(key)
			 var code_seq1				= sForm.code_seq1.value;							//순번(key)
			 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
			 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
			 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
			 var result_point1			= sForm.result_point1.value;						//점수

			 param="&code_certifi1="+code_certifi1
			 +"&code_operation1="+code_operation1
			 +"&oper_name1="+oper_name1
			 +"&oper_birth1="+oper_birth1
			 +"&oper_lic_no1="+oper_lic_no1
			 +"&oper_hp1="+oper_hp1
			 +"&oper_email1="+oper_email1
			 +"&oper_comp_name11="+oper_comp_name11
			 +"&person_yn1="+person_yn1
			 +"&yyyy1="+yyyy1
			 +"&code_kind1="+code_kind1
			 +"&code_seq1="+code_seq1
			 +"&code_bran1="+code_bran1
			 +"&bran_seq1="+bran_seq1
			 +"&receipt_no1="+receipt_no1+param
			 +"&result_point1="+result_point1;

			 
	  		//엑션	
	  		var isSelect = "Y";	
			pers_hp = "All";	
			param+="&from=license3";
	  		var url="license.do?method=smssandAll"+param+"&pers_hp="+pers_hp+"&isSelect="+isSelect;
	  		window.open(url,"umsData","width=370, height=550, left=280, top=50");			
	  	}else{
	 		param+="&from=license_1"+"&hp_infos="+hp_infos;
	 		var url="license.do?method=smssand&dm_pers_code="+dm_pers_code+"&pers_hp="+pers_hp+"&pers_name="+pers_name+param;
	  		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
	  	}
	  }
	  
	  function setEduN(val){
			//alert(val);
			var jc=eval(<%=j_test%>);
			for(var i=0;i<jc.length;i++){
				if(jc[i].detcode==val){
					var season1=jc[i].season;
					if(season1=='1') document.getElementById("s1").checked=true;
					if(season1=='2') document.getElementById("s2").checked=true;
				}
			}
		} 

		function setName(){	
			var yyyy=document.getElementById("yyyy1").value;				//교육년도 값
			var code_bran=document.getElementById("code_bran1").value;		//교육주최 값
			var code_certifi1=document.getElementById("code_certifi1").value;		//교육종류 값
				
			if ( yyyy == "" || code_bran == "" || code_certifi1 == ""  ){		//교육년도와 교육주최 교육종류
				setName1("선택","");
				return;
			}
				/*action send 로 보내는 폼  */
			document.sForm.yyyy1.value = yyyy;
			document.sForm.action="license.do?method=selectEtestN3&yyyy1="+yyyy+"&code_bran1="+code_bran+"&code_certifi1="+code_certifi1;
			document.sForm.submit();
		}	

		function setName1(name,value){
			var edutest_name1=document.getElementById("edutest_name1");
			var opts=edutest_name1.options;
			while(opts.length>0){
				opts[opts.length-1]=null;
			}
			edutest_name1[0]=new Option(name,value);
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
// 	 			if(param.length > 0) param+= ",";
// 	 			param += escape(encodeURIComponent(rowdata.oper_name));
				
				if(file_infos.length > 0) {
					file_infos	+= "__";
				}
				file_infos	+= rowdata.code_kind+"_";
				file_infos	+= rowdata.code_certifi+"_";
				file_infos	+= rowdata.code_seq+"_";
				file_infos	+= rowdata.code_bran+"_";
				file_infos	+= rowdata.bran_seq+"_";
				file_infos	+= rowdata.receipt_no;
	 		}
	  	}
	  	
		var yyyy				= sForm.yyyy1.value;									//년도
		
	 	if( rownum.length==0){	
	 		if(rowSelected){
	 			alert("일괄다운로드를 하시려면 검색을 다시 실행해 주세요.");
	 			return;
	 		}
	 		
			 var code_certifi1					= sForm.code_certifi1.value;				//자격증구분(key)
			 var code_operation1 				= sForm.code_operation1.value;										//대상자
			 var oper_name1 	= escape(encodeURIComponent(sForm3.oper_name1.value));		//이름
			 var oper_birth1					= sForm3.oper_birth1.value;				//생년월일
			 var oper_lic_no1					= sForm3.oper_lic_no1.value;				//면허번호
			 var oper_hp1						= sForm.oper_hp1.value;						//핸드폰
			 var oper_email1 					= sForm.oper_email1.value;					//이메일
			 var oper_comp_name11 				= sForm.oper_comp_name11.value;				//근무처
			 var person_yn1 					= sForm.person_yn1.value;					//회원구분 
			 var season1 						= "";										//학기
			var yyyy1				= sForm.yyyy1.value;									//년도
			
			 var code_kind1				= sForm.code_kind1.value;							//종류(key)
			 var code_seq1				= sForm.code_seq1.value;							//순번(key)
			 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
			 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
			 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
			 var result_point1			= sForm.result_point1.value;						//점수

			 param="&code_certifi1="+code_certifi1
			 +"&code_operation1="+code_operation1
			 +"&oper_name1="+oper_name1
			 +"&oper_birth1="+oper_birth1
			 +"&oper_lic_no1="+oper_lic_no1
			 +"&oper_hp1="+oper_hp1
			 +"&oper_email1="+oper_email1
			 +"&oper_comp_name11="+oper_comp_name11
			 +"&person_yn1="+person_yn1
			 +"&yyyy1="+yyyy1
			 +"&code_kind1="+code_kind1
			 +"&code_seq1="+code_seq1
			 +"&code_bran1="+code_bran1
			 +"&bran_seq1="+bran_seq1
			 +"&receipt_no1="+receipt_no1+param
			 +"&result_point1="+result_point1;
		}
		param+="&file_infos="+file_infos+"&yyyy="+yyyy;
	
		if(!confirm((rownum.length>0?"선택하신 "+(rownum.length)+"건을 일괄다운로드 하시겠습니까?":"전체 일괄다운로드 하시겠습니까?"))){return;}
		
		document.dFile.target="frm2";
		document.dFile.action="license.do?method=fDownAll"+param;
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
				oper_infos	+= rowdata.code_kind+"_";
				oper_infos	+= rowdata.code_certifi+"_";
				oper_infos	+= rowdata.code_seq+"_";
				oper_infos	+= rowdata.code_bran+"_";
				oper_infos	+= rowdata.bran_seq+"_";
				oper_infos	+= rowdata.receipt_no+"_";
				oper_infos	+= rowdata.code_operation;
	 		}
	  	}
	  	
		var yyyy				= sForm.yyyy1.value;									//년도
		 var code_bran 			= sForm.code_bran1.value;							//지부(key)
		 var code_certifi					= sForm.code_certifi1.value;				//자격증구분(key)
		
	 	if( rownum.length==0){	
	 		if(rowSelected){
	 			alert("일괄처리를 하시려면 검색을 다시 실행해 주세요.");
	 			return;
	 		}
	 		
			 var code_certifi1					= sForm.code_certifi1.value;				//자격증구분(key)
			 var code_operation1 				= sForm.code_operation1.value;										//대상자
			 var oper_name1 	= escape(encodeURIComponent(sForm3.oper_name1.value));		//이름
			 var oper_birth1					= sForm3.oper_birth1.value;				//생년월일
			 var oper_lic_no1					= sForm3.oper_lic_no1.value;				//면허번호
			 var oper_hp1						= sForm.oper_hp1.value;						//핸드폰
			 var oper_email1 					= sForm.oper_email1.value;					//이메일
			 var oper_comp_name11 				= sForm.oper_comp_name11.value;				//근무처
			 var person_yn1 					= sForm.person_yn1.value;					//회원구분 
			 var season1 						= "";										//학기
			var yyyy1				= sForm.yyyy1.value;									//년도
			
			 var code_kind1				= sForm.code_kind1.value;							//종류(key)
			 var code_seq1				= sForm.code_seq1.value;							//순번(key)
			 var code_bran1 			= sForm.code_bran1.value;							//지부(key)
			 var bran_seq1				= sForm.bran_seq1.value;							//지부순번(key)
			 var receipt_no1			= sForm.receipt_no1.value;							//접수번호(key)
			 var result_point1			= sForm.result_point1.value;						//점수

			 param="&code_certifi1="+code_certifi1
			 +"&code_operation1="+code_operation1
			 +"&oper_name1="+oper_name1
			 +"&oper_birth1="+oper_birth1
			 +"&oper_lic_no1="+oper_lic_no1
			 +"&oper_hp1="+oper_hp1
			 +"&oper_email1="+oper_email1
			 +"&oper_comp_name11="+oper_comp_name11
			 +"&person_yn1="+person_yn1
			 +"&yyyy1="+yyyy1
			 +"&code_kind1="+code_kind1
			 +"&code_seq1="+code_seq1
			 +"&code_bran1="+code_bran1
			 +"&bran_seq1="+bran_seq1
			 +"&receipt_no1="+receipt_no1+param
			 +"&result_point1="+result_point1;
		}
		param+="&oper_infos="+oper_infos+"&yyyy="+yyyy+"&code_bran="+code_bran+"&code_certifi="+code_certifi;

		if(!confirm((rownum.length>0?"선택하신 "+(rownum.length)+"건을 일괄처리 하시겠습니까?":"전체 일괄처리 하시겠습니까?"))){return;}
		
		document.saveAll.action="license.do?method=inspectionSaveBatch"+param;
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
		<li class="NPage">자격증심사</li>
	  </ul>	 
	  <div class="cTabmenu_01">   
      <form id="list1" name="sForm" method="post" action="">
					   <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
						<input type="hidden" name="code_kind1" 	id="code_kind1" value=""/>
						<input type="hidden" name="code_seq1" 	id="code_seq1" value=""/>
						<input type="hidden" name="bran_seq1" 	id="bran_seq1" value=""/>
						<input type="hidden" name="receipt_no1" id="receipt_no1" value=""/>
						<input type="hidden" name="oper_hp1" 	id="oper_hp1" value=""/>
						<input type="hidden" name="oper_email1" id="oper_email1" value=""/>
						<input type="hidden" name="oper_comp_name11" id="oper_comp_name11" value=""/>
						<input type="hidden" name="person_yn1" 	id="person_yn1" value=""/>
						<input type="hidden" name=codecertifi 	id="codecertifi" value=""/>
						<input type="hidden" name=codeoperation id="codeoperation" value=""/>
						<input type="hidden" name=result_point1 id="result_point1" value=""/>
						
						<input type="hidden" name="_21name" value=""/>
						<input type="hidden" name="_22name" value=""/>
						<input type="hidden" name="_23name" value=""/>
						<input type="hidden" name="_24name" value=""/>
						<input type="hidden" name="_25name" value=""/>
						
						<input type="hidden" name="_41name" value=""/>
						<input type="hidden" name="_42name" value=""/>
						<input type="hidden" name="_43name" value=""/>
						<input type="hidden" name="_44name" value=""/>
						<input type="hidden" name="_45name" value=""/>
						<input type="hidden" name="_46name" value=""/>
						<input type="hidden" name="_47name" value=""/>
						
						<input type="hidden" name="_51name" value=""/>
						<input type="hidden" name="_52name" value=""/>
						<input type="hidden" name="_53name" value=""/>
						<input type="hidden" name="_54name" value=""/>
						
						<input type="hidden" name="_61name" value=""/>
						<input type="hidden" name="_62name" value=""/>
						<input type="hidden" name="_63name" value=""/>
						<input type="hidden" name="_64name" value=""/>
						     
						<input type="hidden" name="code_bran_other1" 	id="code_bran_other1" value=""/>
						<input type="hidden" name="bran_seq_other1" 	id="bran_seq_other1" value=""/>
						<input type="hidden" name="receipt_no_other1" id="receipt_no_other1" value=""/>
						<input type="hidden" name="code_operation_other1" id="code_operation_other1" value=""/>
						
		<table class="ctable_01" cellspacing="0" summary="자격증">
          <caption>결과등록</caption>            			 
             <tr>
               <td class="nobg" style="width:10%">※&nbsp;&nbsp;자격증구분</td>
               <td class="nobg1" style="width:10%">
				<!-- <select  id="code_certifi1" name="code_certifi1" onchange="javascript:setName();"> -->
				<select  id="code_certifi1" name="code_certifi1" >
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
			   <td class="nobg2" style="width:10%">※&nbsp;&nbsp;년도</td>
               <td class="nobg1" style="width:10%">
              <!--  <select  id="yyyy1" name="yyyy1" onchange="javascript:setName();"> -->
               <select  id="yyyy1" name="yyyy1">	
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
               <td class="nobg2" style="width:10%">※&nbsp;&nbsp;지부</td>
               <td class="nobg1">
               <!-- <select  id="code_bran1" name="code_bran1"  onchange="javascript:setName();"> -->
               <select  id="code_bran1" name="code_bran1" >
               <option value="">전체</option>	
               		<%
               		String detCode1,detCName1=null;
				 	for(int i=0;i<licenseserch.size();i++){
				 		if("034".equals(licenseserch.get(i).get("groupcode").toString())){
                			detCode1=licenseserch.get(i).get("detcode").toString();
                			detCName1=licenseserch.get(i).get("detcodename").toString();
                			if(licensein!=null&&detCode1.equals(licensein.get("code_bran1").toString())){
                				out.println("<option value='"+detCode1+"' selected>"+detCName1+"</option>");
                			}else{
                				out.println("<option value='"+detCode1+"'>"+detCName1+"</option>");
                			}
                		}
				 	}
				 %>    
               </select>
               </td>
               <td class="nobg2">대상자</td>
			   <td class="nobg1">
				   <select  id="name" name="code_operation1">
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
        </table>
        </form>
        
        <form id="list1" name="sForm3" method="post">
		<table class="ctable_01" cellspacing="0" summary="자격증">
          <caption>결과등록</caption>            			 
             <tr>
			   <td class="nobg" style="width:10%">※&nbsp;&nbsp;이름</td>
			   <td  class="nobg1" >
			   	<input type="text" id="oper_name1" size="10" name="oper_name1"/>
			   </td>
<!--                <td class="nobg2" style="width:10%">※&nbsp;&nbsp;주민번호</td> -->
               <td class="nobg2" style="width:10%">※&nbsp;&nbsp;생년월일</td>
	               	<td class="nobg1" >
<!-- 	               	JUMIN_DEL -->
<!-- 	               		<input type="text" id="oper_no11" size="7" name="oper_no11"/>-<input type="password" id="oper_no12" size="8" name="oper_no12"/> -->
	               				   		<input type="text" id="oper_birth1" size="7" name="oper_birth1"/>
	               	</td>
               <td class="nobg2" style="width:10%">면허번호</td>
	               	<td  class="nobg1" style="width:10%">
	               				   		<input type="text" id="oper_lic_no1" size="7" name="oper_lic_no1"/>
				   	</td>
			   	 <td class="nobg2" style="width:10%" >대상자</td>
           
               <td class="nobg1"  align="left" disabled="true">             		
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
	               	    </select> </td>
             </tr>           
             
             <tr>
               <td class="alt1" style="width:10%">서류제출여부</td>              
               <td colspan="6" class="alt2" id='chkbx'></td>
               <td class="alt2"><a href="javascript:uFile();"><img src="images/icon_s14.gif" id="uFile"alt="첨부" /></a> 
               </td>
             </tr>			
        </table>
		<ul class="btnIcon_2">		 
		  <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
		  <li><a href="javascript:goSave();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
		  <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a>  </li>	
		  
		</ul>	  
		
		</form>
 <br></br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post"></form>
<ul class="btnIcon_2" style="left:856px;">		 
<!-- 		  <li><a href="javascript:notePad();"><img src="images/icon_s2.gif" onclick=""	alt="쪽지" /></a></li> -->
		  <li><a href="javascript:downloadAll();"><img src="images/icon_download.gif"	onclick=""	alt="일괄다운로드"	/></a></li>		
<!-- 		  <li><span onclick="downloadAll();" style="cursor:pointer;">[일괄다운로드]</span></li>		 -->
		  <li><a href="javascript:goSaveBatch();"><img src="images/icon_processing.gif"	onclick=""	alt="일괄처리"	/></a></li>		
<!-- 		  <li><span onclick="goSaveBatch();" style="cursor:pointer;">[일괄처리]</span></li>		 -->
		  <!-- <li><a href="javascript:sendEmail();"><img src="images/icon_s3.gif"	onclick=""	alt="메일전송"	/></a></li>		
		  <li><a href="javascript:smssand();"><img src="images/icon_s4.gif"	onclick=""	alt="문자전송"	/></a></li> -->
		<!--   <li><a href="#"><img src="images/icon_s16.gif" alt="엑셀올리기" /></a></li>	 -->	 
		  <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" alt="엑셀저장"/></a></li>	
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
