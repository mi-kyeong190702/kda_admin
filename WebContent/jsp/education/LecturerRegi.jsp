<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.Date" %>
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
<script src="js/form.js"  type="text/javascript"></script>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%> 

<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

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
	String pictureFilePath = "";
	String logid="";
	if(session.getAttribute("G_NAME")!=null){
		logid=session.getAttribute("G_NAME").toString();
	}
	String g_id = session.getAttribute("G_ID").toString(); 
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
	  url: 'lecture.do?method=lectureList',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'365',
      colNames: [ '이름',
//                   JUMIN_DEL
//                   '주민번호',
                  '생년월일',
                  '분야','과목1','과목','근무처','연락처','지역','비고','강사번호','사진','우편번호','주소','핸드폰','이메일','직책','근무처주소','근무처우편번호','학력1','학력2','학력3','학력4','학력5','경력1','경력2','경력3','경력4','경력5','경력6','경력7','경력8','경력9','경력10','은행명','예금주','비고',
                  '과목1선택','과목2','과목2선택','과목3','과목3선택','과목4','과목4선택','과목5','과목5선택',
                  '학력1년도','학력1월','학력2년도','학력2월','학력3년도','학력3월','학력4년도','학력4월','학력5년도','학력5월',
                  '경력1년도','경력1월','경력2년도','경력2월','경력3년도','경력3월','경력4년도','경력4월','경력5년도','경력5월',
                  '경력6년도','경력6월','경력7년도','경력7월','경력8년도','경력8월','경력9년도','경력9월','경력10년도','경력10월'
                  ],
      colModel: [
			{name:'lt_name',      		    index:'lt_name',     		   width: 100,editable: false, align: 'center'},
//    			{name:'lt_pers_no',    		    index:'lt_pers_no',  		   width: 100,editable: false, align: 'center'}, // JUMIN_DEL
   			{name:'lt_pers_birth',    		    index:'lt_pers_birth',  		   width: 100,editable: false, align: 'center'},
   			{name:'lt_kind',        		index:'lt_kind',      		   width: 80, editable: false, align: 'center'},
   			{name:'lt_major',      		    index:'lt_major',     	       hidden:true},
   			{name:'lt_major_all',    		    index:'lt_major_all',     	       width: 200,editable: false, align: 'center'},
   			{name:'lt_comp_name',   		index:'lt_comp_name',		   width: 200,editable: false, align: 'center'},
   			{name:'lt_comp_tel',    		index:'lt_comp_tel',  	       width: 150,editable: false, align: 'center'},		
   			{name:'lt_area',        		index:'lt_area',      		   width: 60, editable: false, align: 'center'},   			
   			{name:'lt_code',       		    index:'lt_code',     		   hidden:true},
   			{name:'lt_image',      			index:'lt_image',    		   hidden:true},
   			{name:'lt_post',       			index:'lt_post',   			   hidden:true},
   			{name:'lt_add',           		index:'lt_add',    			   hidden:true},
   			{name:'lt_hp',                  index:'lt_hp',   			   hidden:true},
   			{name:'lt_email',               index:'lt_email',   		   hidden:true},
   			{name:'lt_duty',                index:'lt_duty',  			   hidden:true},
   			{name:'lt_comp_add',            index:'lt_comp_add',    	   hidden:true},
   			{name:'lt_comp_post',           index:'lt_comp_post',          hidden:true},
   			{name:'lt_univer1',             index:'lt_univer1',    		   hidden:true},
   			{name:'lt_univer2',             index:'lt_univer2',  		   hidden:true},
   			{name:'lt_univer3',             index:'lt_univer3',   		   hidden:true},
   			{name:'lt_univer4',             index:'lt_univer4',  		   hidden:true},
   			{name:'lt_univer5',             index:'lt_univer5',   		   hidden:true},
   			{name:'lt_carrier1',            index:'lt_carrier1',   		   hidden:true},  
   			{name:'lt_carrier2',            index:'lt_carrier2',  		   hidden:true},
   			{name:'lt_carrier3',            index:'lt_carrier3',   		   hidden:true},
   			{name:'lt_carrier4',            index:'lt_carrier4',   	       hidden:true},
   			{name:'lt_carrier5',            index:'lt_carrier5',  		   hidden:true},
   			{name:'lt_carrier6',            index:'lt_carrier6',   		   hidden:true},
   			{name:'lt_carrier7',            index:'lt_carrier7',   		   hidden:true},
   			{name:'lt_carrier8',            index:'lt_carrier8',  		   hidden:true},
   			{name:'lt_carrier9',            index:'lt_carrier9',   		   hidden:true},
   			{name:'lt_carrier10',           index:'lt_carrier10',          hidden:true},
   			{name:'lt_bankname',            index:'lt_bankname',           hidden:true},
   			{name:'lt_bankaccount',         index:'lt_bankaccount',        hidden:true},
   			{name:'lt_bankReceiptName',     index:'lt_bankReceiptName',    hidden:true},
   			{name:'lt_remark',      		index:'lt_remark',  		   width: 100,editable: false, align: 'center'},
   			
   			{name:'lt_major_chk',     index:'lt_major_chk',    hidden:true},
   		    {name:'lt_major2',     index:'lt_major2',    hidden:true},
   		    {name:'lt_major_chk2',     index:'lt_major_chk2',    hidden:true},
   		    {name:'lt_major3',     index:'lt_major3',    hidden:true},
   		    {name:'lt_major_chk3',     index:'lt_major_chk3',    hidden:true},
   		    {name:'lt_major4',     index:'lt_major4',    hidden:true},
   		    {name:'lt_major_chk4',     index:'lt_major_chk4',    hidden:true},
   		    {name:'lt_major5',     index:'lt_major5',    hidden:true},
   		    {name:'lt_major_chk5',     index:'lt_major_chk5',    hidden:true},

   			{name:'lt_univer_year1',     index:'lt_univer_year1',    hidden:true},
   			{name:'lt_univer_mon1',     index:'lt_univer_mon1',    hidden:true},
   			{name:'lt_univer_year2',     index:'lt_univer_year2',    hidden:true},
   			{name:'lt_univer_mon2',     index:'lt_univer_mon2',    hidden:true},
   			{name:'lt_univer_year3',     index:'lt_univer_year3',    hidden:true},
   			{name:'lt_univer_mon3',     index:'lt_univer_mon3',    hidden:true},
   			{name:'lt_univer_year4',     index:'lt_univer_year4',    hidden:true},
   			{name:'lt_univer_mon4',     index:'lt_univer_mon4',    hidden:true},
   			{name:'lt_univer_year5',     index:'lt_univer_year5',    hidden:true},
   			{name:'lt_univer_mon5',     index:'lt_univer_mon5',    hidden:true},

   			{name:'lt_carrier_year1',     index:'lt_carrier_year1',    hidden:true},
   			{name:'lt_carrier_mon1',     index:'lt_carrier_mon1',    hidden:true},
   			{name:'lt_carrier_year2',     index:'lt_carrier_year2',    hidden:true},
   			{name:'lt_carrier_mon2',     index:'lt_carrier_mon2',    hidden:true},
   			{name:'lt_carrier_year3',     index:'lt_carrier_year3',    hidden:true},
   			{name:'lt_carrier_mon3',     index:'lt_carrier_mon3',    hidden:true},
   			{name:'lt_carrier_year4',     index:'lt_carrier_year4',    hidden:true},
   			{name:'lt_carrier_mon4',     index:'lt_carrier_mon4',    hidden:true},
   			{name:'lt_carrier_year5',     index:'lt_carrier_year5',    hidden:true},
   			{name:'lt_carrier_mon5',     index:'lt_carrier_mon5',    hidden:true},
   			{name:'lt_carrier_year6',     index:'lt_carrier_year6',    hidden:true},
   			{name:'lt_carrier_mon6',     index:'lt_carrier_mon6',    hidden:true},
   			{name:'lt_carrier_year7',     index:'lt_carrier_year7',    hidden:true},
   			{name:'lt_carrier_mon7',     index:'lt_carrier_mon7',    hidden:true},
   			{name:'lt_carrier_year8',     index:'lt_carrier_year8',    hidden:true},
   			{name:'lt_carrier_mon8',     index:'lt_carrier_mon8',    hidden:true},
   			{name:'lt_carrier_year9',     index:'lt_carrier_year9',    hidden:true},
   			{name:'lt_carrier_mon9',     index:'lt_carrier_mon9',    hidden:true},
   			{name:'lt_carrier_year10',     index:'lt_carrier_year10',    hidden:true},
   			{name:'lt_carrier_mon10',     index:'lt_carrier_mon10',    hidden:true}
   			
   			],
   		    rowNum:15,
   	     	pager: '#pager2',
   		    sortname: 'book_name',
            rownumbers: true,
            viewrecords: true,
 			/* multiselect: true, */
 			gridview: true,
 			caption: '강사등록',
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
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

 function goSelect(rowid,iCol){	
	
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
		
		if(list.lt_image.length!=0)
			pictureFilePath = "upload/picture/"+list.lt_image;
		else
			pictureFilePath = "images/blank_image.png";
		
		document.getElementById("imgView").innerHTML = "";
		document.getElementById("imgView").innerHTML = "<img src='" + pictureFilePath + "' width='70px' height='80px' 'margin-left:10px;'>";
					
		document.mem.lt_name.value      		= list.lt_name;
// 		JUMIN_DEL
// 		document.mem.lt_pers_no1.value      	= list.lt_pers_no.substring(0,6);	
// 		document.mem.lt_pers_no2.value      	= list.lt_pers_no.substring(6,13);
		document.mem.lt_pers_birth.value      	= list.lt_pers_birth;
		document.mem.lt_hp.value            	= list.lt_hp;		
		document.mem.lt_image.value             = list.lt_image;		
		document.mem.lt_major.value             = list.lt_major;
		document.mem.lt_post.value      		= list.lt_post;
		document.mem.lt_add.value      			= list.lt_add;		
		document.mem.lt_comp_name.value      	= list.lt_comp_name;
		document.mem.lt_comp_add.value         	= list.lt_comp_add;
		document.mem.lt_comp_post.value         = list.lt_comp_post;		
		document.mem.lt_duty.value              = list.lt_duty;		
		document.mem.lt_comp_tel.value        	= list.lt_comp_tel;		
		document.mem.lt_email.value       	 	= list.lt_email;	
		document.mem.lt_code.value      		= list.lt_code;
		
		
		
		mem.t0.checked  = false;
		mem.t1.checked  = false;
		mem.t2.checked  = false;
		mem.t3.checked  = false;
		mem.t4.checked  = false;
		mem.t5.checked  = false;
		mem.t6.checked  = false;
		mem.t7.checked  = false;
		mem.t8.checked  = false;
		
		mem.a0.checked  = false;
		mem.a1.checked  = false;
		mem.a2.checked  = false;
		mem.a3.checked  = false;
		mem.a4.checked  = false;
		mem.a5.checked  = false;
		mem.a6.checked  = false;
		mem.a7.checked  = false;
		mem.a8.checked  = false;
		mem.a9.checked  = false;
		mem.a10.checked = false;
		mem.a11.checked = false;
		mem.a12.checked = false;
		mem.a13.checked = false;
		mem.a14.checked = false;
		mem.a15.checked = false;
		mem.a16.checked = false;
				
		if(list.lt_kind.indexOf("급식")>-1){
			mem.t0.checked = true;
		}
		if(list.lt_kind.indexOf("임상")>-1){
			mem.t1.checked = true;
		}
		if(list.lt_kind.indexOf("산업보건")>-1){
			mem.t2.checked = true;
		}
		if(list.lt_kind.indexOf("학교")>-1){
			mem.t3.checked = true;
		}
		if(list.lt_kind.indexOf("학술대회")>-1){
			mem.t4.checked = true;
		}
		if(list.lt_kind.indexOf("위생교육")>-1){
			mem.t5.checked = true;
		}
		if(list.lt_kind.indexOf("기타")>-1){
			mem.t6.checked = true;
		}
		if(list.lt_kind.indexOf("노인")>-1){
			mem.t7.checked = true;
		}
		if(list.lt_kind.indexOf("스포츠")>-1){
			mem.t8.checked = true;
		}
		
		if(list.lt_area == "서울"){
			mem.a0.checked = true;
		}
		if(list.lt_area == "부산"){
			mem.a1.checked = true;
		}
		if(list.lt_area == "인천"){
			mem.a2.checked = true;
		}
		if(list.lt_area == "경기"){
			mem.a3.checked = true;
		}
		if(list.lt_area == "강원"){
			mem.a4.checked = true;
		}
		if(list.lt_area == "충북"){
			mem.a5.checked = true;
		}
		if(list.lt_area == "충남"){
			mem.a6.checked = true;
		}
		if(list.lt_area == "전북"){
			mem.a7.checked = true;
		}
		if(list.lt_area == "광주"){
			mem.a8.checked = true;
		}
		if(list.lt_area == "전남"){
			mem.a9.checked = true;
		}
		if(list.lt_area == "대구"){
			mem.a10.checked = true;
		}
		if(list.lt_area == "대전"){
			mem.a11.checked = true;
		}
		if(list.lt_area == "경북"){
			mem.a12.checked = true;
		}
		if(list.lt_area == "경남"){
			mem.a13.checked = true;
		}
		if(list.lt_area == "울산"){
			mem.a14.checked = true;
		}
		if(list.lt_area == "제주"){
			mem.a15.checked = true;
		}
		if(list.lt_area == "기타"){
			mem.a16.checked = true;
		}
		document.mem.lt_univer1.value      		= list.lt_univer1;
		document.mem.lt_univer2.value      		= list.lt_univer2;
		document.mem.lt_univer3.value      		= list.lt_univer3;
		document.mem.lt_univer4.value      		= list.lt_univer4;
		document.mem.lt_univer5.value      		= list.lt_univer5;		
		document.mem.lt_carrier1.value     		= list.lt_carrier1;
		document.mem.lt_carrier2.value     		= list.lt_carrier2;
		document.mem.lt_carrier3.value     		= list.lt_carrier3;
		document.mem.lt_carrier4.value     		= list.lt_carrier4;
		document.mem.lt_carrier5.value     		= list.lt_carrier5;
		document.mem.lt_carrier6.value     		= list.lt_carrier6;
		document.mem.lt_carrier7.value     		= list.lt_carrier7;
		document.mem.lt_carrier8.value     		= list.lt_carrier8;
		document.mem.lt_carrier9.value     		= list.lt_carrier9;
		document.mem.lt_carrier10.value     	= list.lt_carrier10;
// 		document.mem.lt_bankname.value     		= list.lt_bankname;
// 		document.mem.lt_bankaccount.value     	= list.lt_bankaccount;
// 		document.mem.lt_bankReceiptName.value 	= list.lt_bankReceiptName;	
		document.mem.lt_remark.value 	        = list.lt_remark;	
		
		
		document.mem.lt_major_chk.checked = list.lt_major_chk=="1"?true:false;
		document.mem.lt_major_chk2.checked = list.lt_major_chk2=="1"?true:false;
		document.mem.lt_major_chk3.checked = list.lt_major_chk3=="1"?true:false;
		document.mem.lt_major_chk4.checked = list.lt_major_chk4=="1"?true:false;
		document.mem.lt_major_chk5.checked = list.lt_major_chk5=="1"?true:false;
		
	    document.mem.lt_major2.value 	        = list.lt_major2;
	    document.mem.lt_major3.value 	        = list.lt_major3;
	    document.mem.lt_major4.value 	        = list.lt_major4;
	    document.mem.lt_major5.value 	        = list.lt_major5;

		document.mem.lt_univer_year1.value 	        = list.lt_univer_year1;
		document.mem.lt_univer_mon1.value 	        = list.lt_univer_mon1;
		document.mem.lt_univer_year2.value 	        = list.lt_univer_year2;
		document.mem.lt_univer_mon2.value 	        = list.lt_univer_mon2;
		document.mem.lt_univer_year3.value 	        = list.lt_univer_year3;
		document.mem.lt_univer_mon3.value 	        = list.lt_univer_mon3;
		document.mem.lt_univer_year4.value 	        = list.lt_univer_year4;
		document.mem.lt_univer_mon4.value 	        = list.lt_univer_mon4;
		document.mem.lt_univer_year5.value 	        = list.lt_univer_year5;
		document.mem.lt_univer_mon5.value 	        = list.lt_univer_mon5;

		document.mem.lt_carrier_year1.value 	        = list.lt_carrier_year1;
		document.mem.lt_carrier_mon1.value 	        = list.lt_carrier_mon1;
		document.mem.lt_carrier_year2.value 	        = list.lt_carrier_year2;
		document.mem.lt_carrier_mon2.value 	        = list.lt_carrier_mon2;
		document.mem.lt_carrier_year3.value 	        = list.lt_carrier_year3;
		document.mem.lt_carrier_mon3.value 	        = list.lt_carrier_mon3;
		document.mem.lt_carrier_year4.value 	        = list.lt_carrier_year4;
		document.mem.lt_carrier_mon4.value 	        = list.lt_carrier_mon4;
		document.mem.lt_carrier_year5.value 	        = list.lt_carrier_year5;
		document.mem.lt_carrier_mon5.value 	        = list.lt_carrier_mon5;
		document.mem.lt_carrier_year6.value 	        = list.lt_carrier_year6;
		document.mem.lt_carrier_mon6.value 	        = list.lt_carrier_mon6;
		document.mem.lt_carrier_year7.value 	        = list.lt_carrier_year7;
		document.mem.lt_carrier_mon7.value 	        = list.lt_carrier_mon7;
		document.mem.lt_carrier_year8.value 	        = list.lt_carrier_year8;
		document.mem.lt_carrier_mon8.value 	        = list.lt_carrier_mon8;
		document.mem.lt_carrier_year9.value 	        = list.lt_carrier_year9;
		document.mem.lt_carrier_mon9.value 	        = list.lt_carrier_mon9;
		document.mem.lt_carrier_year10.value 	        = list.lt_carrier_year10;
		document.mem.lt_carrier_mon10.value 	        = list.lt_carrier_mon10;
	}
}

 function goInsert(){	
		
// 	 	var  lt_pers_no       = ""; // JUMIN_DEL
	 	var  lt_pers_birth       = "";
	 	var  lt_kind          = "000000000";
	 	var  lt_area          = "00000000000000000";
				
	    if ( mem.lt_name.value == "" ) {
	         alert("이름을 입력하세요.") ;
	         mem.lt_name.focus();
	         return ;
	    }
	    // JUMIN_DEL
// 		if ( mem.lt_pers_no1.value == "" ) {
// 	         alert("주민번호를 입력하세요.") ;
// 	         mem.lt_pers_no1.focus();
// 	         return ;
// 		}
	    
// 		if ( mem.lt_pers_no2.value == "" ) {
// 	         alert("주민번호를 입력하세요.") ;
// 	         mem.lt_pers_no2.focus();
// 	         return ;
// 		}
	    
		if ( mem.lt_pers_birth.value == "" ) {
	         alert("생년월일을 입력하세요.") ;
	         mem.lt_pers_birth.focus();
	         return ;
		}
		if ( mem.lt_pers_birth.value.length != 8 ) {
	         alert("생년월일을 정확히 입력해 주세요.") ;
	         mem.lt_pers_birth.focus();
	         return ;
		}
		
		if ( mem.lt_hp.value == "" ) {
	         alert("휴대폰을 입력하세요.") ;
	         mem.lt_hp.focus();
	         return ;
		}
	    
		if ( mem.lt_post.value == "" ) {
	         alert("우편번호를 입력하세요.") ;
	         mem.lt_post.focus();
	         return ;
		}
		
		if ( mem.lt_add.value == "" ) {
	         alert("주소를 입력하세요.") ;
	         mem.lt_add.focus();
	         return ;
		}
		
		if ( mem.lt_major.value == "" && mem.lt_major2.value == "" && mem.lt_major3.value == "" && mem.lt_major4.value == "" && mem.lt_major5.value == "") {
	         alert("과목을 최소 하나이상 입력하세요.") ;
	         mem.lt_major.focus();
	         return ;
		}
		
// 		JUMIN_DEL
// 		if(mem.lt_pers_no1.value !=""){
// 			lt_pers_no 			= mem.lt_pers_no1.value + mem.lt_pers_no2.value;
// 		}
		
		if(mem.lt_pers_birth.value !=""){
			lt_pers_birth 			= mem.lt_pers_birth.value;
		}
		
		var  t0   = lt_kind.substr(0,1);	
		var  t1   = lt_kind.substr(1,1);
		var  t2   = lt_kind.substr(2,1);
		var  t3   = lt_kind.substr(3,1);	
		var  t4   = lt_kind.substr(4,1);
		var  t5   = lt_kind.substr(5,1);
		var  t6   = lt_kind.substr(6,1);	
		var  t7   = lt_kind.substr(7,1);	
		var  t8   = lt_kind.substr(8,1);	
		
		if (mem.t0.checked) {
	    	t0 = '1';
	    }
	    if (mem.t1.checked) {
	    	t1 = '1';
	    }
	    if (mem.t2.checked) {
	    	t2 = '1';
	    }
	    if (mem.t3.checked) {
	    	t3 = '1';
	    }
	    if (mem.t4.checked) {
	    	t4 = '1';
	    }
	    if (mem.t5.checked) {
	    	t5 = '1';
	    }
	    if (mem.t6.checked) {
	    	t6 = '1';
	    }
	    if (mem.t7.checked) {
	    	t7 = '1';
	    }
	    if (mem.t8.checked) {
	    	t8 = '1';
	    }
	    
	    lt_kind = t0+t1+t2+t3+t4+t5+t6+t7+t8;
	  
	    if(lt_kind == "000000000"){
	       alert("분야를 선택하세요.") ;
	       return ;    	
	    }
		
	    var  a0    = lt_area.substr(0,1);	
		var  a1    = lt_area.substr(1,1);
		var  a2    = lt_area.substr(2,1);
		var  a3    = lt_area.substr(3,1);	
		var  a4    = lt_area.substr(4,1);
		var  a5    = lt_area.substr(5,1);
		var  a6    = lt_area.substr(6,1);	
		var  a7    = lt_area.substr(7,1);	
		var  a8    = lt_area.substr(8,1);
		var  a9    = lt_area.substr(9,1);
		var  a10   = lt_area.substr(10,1);	
		var  a11   = lt_area.substr(11,1);
		var  a12   = lt_area.substr(12,1);
		var  a13   = lt_area.substr(13,1);	
		var  a14   = lt_area.substr(14,1);	
		var  a15   = lt_area.substr(15,1);
		var  a16   = lt_area.substr(16,1);
		
		if (mem.a0.checked) {
	    	a0 = '1';
	    }
	    if (mem.a1.checked) {
	    	a1 = '1';
	    }
	    if (mem.a2.checked) {
	    	a2 = '1';
	    }
	    if (mem.a3.checked) {
	    	a3 = '1';
	    }
	    if (mem.a4.checked) {
	    	a4 = '1';
	    }
	    if (mem.a5.checked) {
	    	a5 = '1';
	    }
	    if (mem.a6.checked) {
	    	a6 = '1';
	    }
	    if (mem.a7.checked) {
	    	a7 = '1';
	    }
	    if (mem.a8.checked) {
	    	a8 = '1';
	    }
	    if (mem.a9.checked) {
	    	a9 = '1';
	    }
	    if (mem.a10.checked) {
	    	a10 = '1';
	    }
	    if (mem.a11.checked) {
	    	a11 = '1';
	    }
	    if (mem.a12.checked) {
	    	a12 = '1';
	    }
	    if (mem.a13.checked) {
	    	a13 = '1';
	    }
	    if (mem.a14.checked) {
	    	a14 = '1';
	    }
	    if (mem.a15.checked) {
	    	a15 = '1';
	    }
	    if (mem.a16.checked) {
	    	a16 = '1';
	    }
	    
	    lt_area = a0+a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16;
	  
	    if(lt_area == "00000000000000000"){
	       alert("지역을 선택하세요.") ;
	       return ;    	
	    }
	    
	    var lt_code = mem.lt_code.value;
	    
	   	if(!confirm("저장 하시겠습니까?")){return;}
	    if (lt_code == "" ) {
// 	    	JUMIN_DEL
// 	    	document.mem.action = "lecture.do?method=insert_lecture&lt_pers_no="+lt_pers_no+"&lt_kind="+lt_kind+"&lt_area="+lt_area;
	    	document.mem.action = "lecture.do?method=insert_lecture&lt_pers_birth="+lt_pers_birth+"&lt_kind="+lt_kind+"&lt_area="+lt_area;
	    	document.mem.submit();  
	    }else {
// 	    	JUMIN_DEL
// 	    	document.mem.action = "lecture.do?method=update_lecture&lt_code="+lt_code+"&lt_pers_no="+lt_pers_no+"&lt_kind="+lt_kind+"&lt_area="+lt_area;
	    	document.mem.action = "lecture.do?method=update_lecture&lt_code="+lt_code+"&lt_pers_birth="+lt_pers_birth+"&lt_kind="+lt_kind+"&lt_area="+lt_area;
	    	document.mem.submit();
		}	
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();		
	});	
}

function goSearch(form,intPage){
	
	var parm       = "";
// 	JUMIN_DEL
// 	var lt_pers_no = "";
	var lt_pers_birth = "";
	var lt_kind    = "000000000";
	
// 	JUMIN_DEL
// 	if(mem.lt_pers_no1.value !=""){
// 		lt_pers_no 			= mem.lt_pers_no1.value + mem.lt_pers_no2.value;
// 	}
	
	if(mem.lt_pers_birth.value !=""){
		lt_pers_birth 			= mem.lt_pers_birth.value;
	}
	
	var  t0   = lt_kind.substr(0,1);	
	var  t1   = lt_kind.substr(1,1);
	var  t2   = lt_kind.substr(2,1);
	var  t3   = lt_kind.substr(3,1);	
	var  t4   = lt_kind.substr(4,1);
	var  t5   = lt_kind.substr(5,1);
	var  t6   = lt_kind.substr(6,1);	
	var  t7   = lt_kind.substr(7,1);	
	var  t8   = lt_kind.substr(8,1);	
	
	if (mem.t0.checked) {
    	t0 = '1';
    }
    if (mem.t1.checked) {
    	t1 = '1';
    }
    if (mem.t2.checked) {
    	t2 = '1';
    }
    if (mem.t3.checked) {
    	t3 = '1';
    }
    if (mem.t4.checked) {
    	t4 = '1';
    }
    if (mem.t5.checked) {
    	t5 = '1';
    }
    if (mem.t6.checked) {
    	t6 = '1';
    }
    if (mem.t7.checked) {
    	t7 = '1';
    }
    if (mem.t8.checked) {
    	t8 = '1';
    }
    
    lt_kind = t0+t1+t2+t3+t4+t5+t6+t7+t8;
  
    if(lt_kind == "000000000"){
    	lt_kind = "";    	
    }
             
    if(mem.lt_name.value              !="")parm+="&lt_name="          + escape(encodeURIComponent(mem.lt_name.value));
//     JUMIN_DEL
//     if(lt_pers_no                     !="")parm+="&lt_pers_no="       + lt_pers_no;
    if(lt_pers_birth                     !="")parm+="&lt_pers_birth="       + lt_pers_birth;
    if(lt_kind                        !="")parm+="&lt_kind="          + lt_kind;
      
    $('#list').jqGrid('clearGridData');
    jQuery("#list").setGridParam({url:"lecture.do?method=lectureList&parm="+parm}).trigger("reloadGrid");   	
}

/* 액셀 다운 */
  function excelDown(){		
	    var parm       = "";
// 	    JUMIN_DEL
// 		var lt_pers_no = "";
		var lt_pers_birth = "";
		var lt_kind    = "000000000";
		
// 		JUMIN_DEL
// 		if(mem.lt_pers_no1.value !=""){
// 			lt_pers_no 			= mem.lt_pers_no1.value + mem.lt_pers_no2.value;
// 		}
		
		if(mem.lt_pers_birth.value !=""){
			lt_pers_birth 			= mem.lt_pers_birth.value;
		}
		
		var  t0   = lt_kind.substr(0,1);	
		var  t1   = lt_kind.substr(1,1);
		var  t2   = lt_kind.substr(2,1);
		var  t3   = lt_kind.substr(3,1);	
		var  t4   = lt_kind.substr(4,1);
		var  t5   = lt_kind.substr(5,1);
		var  t6   = lt_kind.substr(6,1);	
		var  t7   = lt_kind.substr(7,1);	
		var  t8   = lt_kind.substr(8,1);	
		
		if (mem.t0.checked) {
	    	t0 = '1';
	    }
	    if (mem.t1.checked) {
	    	t1 = '1';
	    }
	    if (mem.t2.checked) {
	    	t2 = '1';
	    }
	    if (mem.t3.checked) {
	    	t3 = '1';
	    }
	    if (mem.t4.checked) {
	    	t4 = '1';
	    }
	    if (mem.t5.checked) {
	    	t5 = '1';
	    }
	    if (mem.t6.checked) {
	    	t6 = '1';
	    }
	    if (mem.t7.checked) {
	    	t7 = '1';
	    }
	    if (mem.t8.checked) {
	    	t8 = '1';
	    }
	    
	    lt_kind = t0+t1+t2+t3+t4+t5+t6+t7+t8;
	  
	    if(lt_kind == "000000000"){
	    	lt_kind = "";    	
	    }
	             
	    if(mem.lt_name.value              !="")parm+="&lta_name="          + escape(encodeURIComponent(mem.lt_name.value));
// 	    JUMIN_DEL
// 	    if(lt_pers_no                     !="")parm+="&lta_pers_no="       + lt_pers_no;
	    if(lt_pers_birth                     !="")parm+="&lta_pers_birth="       + lt_pers_birth;
	    if(lt_kind                        !="")parm+="&lta_kind="          + lt_kind;
		
	    var url="lecture.do?method=lecturepers_check&parm="+parm;
	    window.open(url,"exceldown","width=339, height=280, left=480, top=200");	
	    
} 
 
// function postSearch(){
// 	window.open('lecture.do?method=postSearch&sel=N&keyword=',"postnum","scrollbar=no,resizable=no,toolbar=no,width=675,height=500,left=20,top=29,status=no");
// }

// function postSearch1(){
// 	window.open('lecture.do?method=postSearch1&sel=N&keyword=',"postnum","scrollbar=no,resizable=no,toolbar=no,width=675,height=500,left=20,top=29,status=no");
// }

function upImage(){
	
	 var url="lecture.do?method=picture";
	 window.open(url,"picture","width=309, height=300, left=480, top=170");	
}



//우편번호 검색
function postSearch(){
	var pop = window.open("/post5.do?gubun=1","jusopop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}
//자택주소콜백
function jusoCallBack1(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
// 	document.comp.pers_add.value = roadAddrPart1;
// 	document.comp.pers_add_detail.value = addrDetail;
// 	document.comp.code_post.value = zipNo;
	document.mem.lt_add.value = roadAddrPart1 + " " + addrDetail;
	document.mem.lt_post.value = zipNo;
}


//근무처 우편번호 검색
function compostSearch(){
	var pop = window.open("/post5.do?gubun=2","jusopop","width=570,height=420, scrollbars=yes, resizable=yes");
	
} 
//근무처 우편번호콜백
function jusoCallBack2(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){
// 	document.comp.company_add.value = roadAddrPart1;
// 	document.comp.company_add_detail.value = addrDetail;
// 	document.comp.company_code_post.value = zipNo;
	document.mem.lt_comp_add.value = roadAddrPart1 + " " + addrDetail;
	document.mem.lt_comp_post.value = zipNo;
}

function goDel(){
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr == null ) {
		alert("목록에서 삭제하실 강사를 선택해 주세요."); return;
	}
	 if(mem.lt_code.value==""){
		alert("삭제하실 강사를 다시 선택해 주세요."); return;
	}
	var lt_code = mem.lt_code.value;
	
	if(!confirm("선택하신 강사를 삭제하시겠습니까?\n\n삭제한 강사는 다시 복구할 수 없습니다.")){return;}
	document.mem.action = "lecture.do?method=delete_lecture&lt_code="+lt_code;
	document.mem.submit();  
}


function goPrint(){	
	
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr == null ) {
		alert("목록에서 출력하실 강사를 선택해 주세요."); return;
	}
	 if(mem.lt_code.value==""){
		alert("출력하실 강사를 다시 선택해 주세요."); return;
	}
	var lt_code = mem.lt_code.value;
	
//     window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_lecture_each.jsp?lt_code="+lt_code);		
    window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_lecture_each.jsp?lt_code="+lt_code);		
}


</script>
</head>

<body>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">교육</a></li>		
		<li>&gt;</li>
		<li class="NPage">강사관리</li>
		<li>&gt;</li>
		<li class="NPage">등록</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="mem" method="post" >
	<input type="hidden" name="pictureFilePath">

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
        
	  <table class="ctable_03" cellspacing="0" summary="교육">
         <!--   <div id="ts_tabmenu">
	         <ul>
	           <li><a href="lecture.do?method=lecturedata" class="overMenu"><strong><span>강사 등록</span></strong></a></li>
		    <!--    <li><a href=""><strong><span>출강 정보</span></strong></a></li> -->		     
			 </ul>
          </div>	                   			 
             <tr>
               <td class="nobga" align="center" style="width:64.05px;">이 름</td>
               <td class="nobg1a" style="width:156.63px;"><input type="text" id="m" name="lt_name" value="" size="15" /></td>
               <!-- JUMIN_DEL -->
<!-- 			   <td class="nobg2a" align="center">주민 번호</td> -->
<!-- 			   <td class="nobg1a"><input type="text" name="lt_pers_no1" value="" align="center" size="6"/>-<input type="text" name="lt_pers_no2" value="" align="center" size="7"/></td> -->
			   <td class="nobg2a" align="center" style="width:65.05px;">생년월일</td>
			   <td class="nobg1a" style="width:156.64px;"><input type="text" name="lt_pers_birth" value="" size="7" maxlength="8" onKeyUp="check_Int('mem','lt_pers_birth')" /> 예)19990101</td>
<!--                <td class="nobg2a" align="center">휴대폰</td> -->
<!--                <td class="nobg1a"><input type="text" id="m" name="lt_hp" size="15" /></td> -->
               <td class="nobg2a" align="center" style="width:65.06px;">사 진</td>                      
			   <td class="nobg1a" colspan="3" style="width:405.14px;"><input type="text" name="lt_image" id="attatch" onchange="" size="56" style="cursor:hand"/>
				   <input type="button" name="btn" value="등록" onclick="javascript:upImage();" style="cursor:hand"></td>				   
			   <td class="nobg111a" rowspan="3" id="imgView"  style="width:69.43px;"><img src="images/blank_image.png"  alt="사진" onchange="<%=pictureFilePath %>(this.value)"/></td>			 
             </tr>
             <tr>
<!--                <td class="alt1" align="center">과 목</td> -->
<!--                <td><input type="text" id="m" name="lt_major" size="15" /></td> -->
               <td class="alt1" align="center">휴대폰</td>
               <td><input type="text" id="m" name="lt_hp" size="15" /></td>
               <td class="alt" align="center">우편 번호</td>
               <td><input type="text" id="year" name="lt_post" size="7" onkeyup="javascript:check_PostNo('mem','lt_post'); return check_StrByte(this, 20);" />
			   <input name="bnt" type="button" value="우편번호" onclick="javascript:postSearch();"/></td>	
			   <td class="alt" align="center">자택주소</td>
               <td colspan="3"><input type="text" id="m" name="lt_add" size="62" /></td>	
             </tr>
			 <tr>
                <td class="alt1" align="center">근무처명</td>
               <td><input type="text" id="m" name="lt_comp_name" size="15" /></td>
               <td class="alt" align="center">우편 번호</td>
               <td><input type="text" id="year" name="lt_comp_post" size="7" onkeyup="javascript:check_PostNo('mem','lt_comp_post'); return check_StrByte(this, 20);" />
			   <input name="bnt" type="button" value="우편번호" onclick="javascript:compostSearch();"/></td>	
			   <td class="alt" align="center">근무처주소</td>
               <td colspan="3"><input type="text" id="m" name="lt_comp_add" size="62" /></td>               		  
             </tr>
			 <tr>
			    <td class="alt1"  align="center">직 책</td>
               <td><input type="text" id="p" name="lt_duty" size="15" /></td>
			   <td class="alt"  align="center">전화 번호</td>
               <td ><input type="text" id="m" name="lt_comp_tel" size="15" /></td>				  
			   <td class="alt"  align="center">이메일</td>
               <td><input type="text" id="p" name="lt_email" size="15" /></td>              
               <td class="alt" align="center">강사 번호</td>
               <td colspan="2"><input type="text" id="p"  name="lt_code" size="15" disabled="true"/></td>
             </tr>	
             <tr>
               <td class="alt1" align="center">과 목</td>
               <td colspan="8">
               	01. <input type="checkbox" name="lt_major_chk" id="lt_major_chk" value="1" /> <input type="text" id="m" name="lt_major" style="width:134px;" maxlength="300" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               	02. <input type="checkbox" name="lt_major_chk2" id="lt_major_chk2" value="1" /> <input type="text" id="m" name="lt_major2" style="width:134px;"  maxlength="300" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               	03. <input type="checkbox" name="lt_major_chk3" id="lt_major_chk3" value="1" /> <input type="text" id="m" name="lt_major3" style="width:134px;"  maxlength="300" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               	04. <input type="checkbox" name="lt_major_chk4" id="lt_major_chk4" value="1" /> <input type="text" id="m" name="lt_major4" style="width:134px;"  maxlength="300" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               	05. <input type="checkbox" name="lt_major_chk5" id="lt_major_chk5" value="1" /> <input type="text" id="m" name="lt_major5" style="width:134px;"  maxlength="300" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               </td>
             </tr>
			 <tr>                 
	           <td class="alt1" align="center">분 야</td>
	           <td colspan="8"  id="m" name="lt_kind" value="" align="left">	           	                		
               		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="t0" id="t" />&nbsp;&nbsp;급식&nbsp;&nbsp;
               		<input type="checkbox" name="t1" id="t" />&nbsp;&nbsp;임상&nbsp;&nbsp;
               		<input type="checkbox" name="t2" id="t" />&nbsp;&nbsp;산업보건&nbsp;&nbsp;
               		<input type="checkbox" name="t3" id="t" />&nbsp;&nbsp;학교&nbsp;&nbsp;
               		<input type="checkbox" name="t4" id="t" />&nbsp;&nbsp;학술대회&nbsp;&nbsp;
               		<input type="checkbox" name="t5" id="t" />&nbsp;&nbsp;위생교육&nbsp;&nbsp;
               		<input type="checkbox" name="t7" id="t" />&nbsp;&nbsp;노인&nbsp;&nbsp;
               		<input type="checkbox" name="t8" id="t" />&nbsp;&nbsp;스포츠&nbsp;&nbsp;
               		<input type="checkbox" name="t6" id="t" />&nbsp;&nbsp;기타
               </td>                  
		    </tr>
             <tr>
             <td class="alt1" align="center">지 역</td>
             <td colspan="8"  id="m" name="lt_area" align="center">	               		
               		<input type="checkbox" name="a0"  id="t" />&nbsp;&nbsp;서울&nbsp;&nbsp;
               		<input type="checkbox" name="a1"  id="t" />&nbsp;&nbsp;부산&nbsp;&nbsp;
               		<input type="checkbox" name="a2"  id="t" />&nbsp;&nbsp;인천&nbsp;&nbsp;
               		<input type="checkbox" name="a3"  id="t" />&nbsp;&nbsp;경기&nbsp;&nbsp;              		
               		<input type="checkbox" name="a4"  id="t" />&nbsp;&nbsp;강원&nbsp;&nbsp;
               		<input type="checkbox" name="a5"  id="t" />&nbsp;&nbsp;충북&nbsp;&nbsp;
               		<input type="checkbox" name="a6"  id="t" />&nbsp;&nbsp;충남&nbsp;&nbsp;
               		<input type="checkbox" name="a7"  id="t" />&nbsp;&nbsp;전북&nbsp;&nbsp;
               		<input type="checkbox" name="a8"  id="t" />&nbsp;&nbsp;광주&nbsp;&nbsp;
               		<input type="checkbox" name="a9"  id="t" />&nbsp;&nbsp;전남&nbsp;&nbsp;           		
               		<input type="checkbox" name="a10" id="t" />&nbsp;&nbsp;대구&nbsp;&nbsp;
               		<input type="checkbox" name="a11" id="t" />&nbsp;&nbsp;대전&nbsp;&nbsp;        		
               		<input type="checkbox" name="a12" id="t" />&nbsp;&nbsp;경북&nbsp;&nbsp;
               		<input type="checkbox" name="a13" id="t" />&nbsp;&nbsp;경남&nbsp;&nbsp;
               		<input type="checkbox" name="a14" id="t" />&nbsp;&nbsp;울산&nbsp;&nbsp;
               		<input type="checkbox" name="a15" id="t" />&nbsp;&nbsp;제주&nbsp;&nbsp;     		       		
               		<input type="checkbox" name="a16" id="t" />&nbsp;&nbsp;기타               		
               </td>              
             </tr>  
             <%int thisYear=(new Date()).getYear()+1900;%>                			 
             <tr>
               <td rowspan="5" class="alt1" align="center">학 력</td>
               <td colspan="8">
               		01.
       				<select name="lt_univer_year1" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_univer_mon1" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_univer1" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		02.
       				<select name="lt_univer_year2" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_univer_mon2" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_univer2" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		03.
       				<select name="lt_univer_year3" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_univer_mon3" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_univer3" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		04.
       				<select name="lt_univer_year4" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_univer_mon4" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_univer4" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		05.
       				<select name="lt_univer_year5" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_univer_mon5" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_univer5" siz="150" style="width:847px;" />
               </td>			    
             </tr>
<!-- 			 <tr> -->
<!--                <td rowspan="5" class="alt1" align="center">경 력</td> -->
<!--                <td colspan="4">01. <input type="text" id="m" name="lt_carrier1" size="61" /></td>			     -->
<!--                <td colspan="4">02. <input type="text" id="m" name="lt_carrier2" size="65" /></td>             			    -->
<!--              </tr> -->
<!-- 			 <tr> -->
<!--                <td colspan="4">03. <input type="text" id="m" name="lt_carrier3" size="61" /></td>			     -->
<!--                <td colspan="4">04. <input type="text" id="m" name="lt_carrier4" size="65" /></td>             			    -->
<!--              </tr> -->
<!--              <tr> -->
<!--                <td colspan="4">05. <input type="text" id="m" name="lt_carrier5" size="61" /></td>			     -->
<!--                <td colspan="4">06. <input type="text" id="m" name="lt_carrier6" size="65" /></td>               			    -->
<!--              </tr> -->
<!--              <tr> -->
<!--                <td colspan="4">07. <input type="text" id="m" name="lt_carrier7" size="61" /></td>			     -->
<!--                <td colspan="4">08. <input type="text" id="m" name="lt_carrier8" size="65" /></td>              			    -->
<!--              </tr> -->
<!--              <tr> -->
<!--                <td colspan="4">09. <input type="text" id="m" name="lt_carrier9"  size="61" /></td>			     -->
<!--                <td colspan="4">10. <input type="text" id="m" name="lt_carrier10" size="65" /></td>			     -->
<!--              </tr> -->
             <tr>
               <td rowspan="10" class="alt1" align="center">경 력</td>
               <td colspan="8">
               		01.
       				<select name="lt_carrier_year1" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon1" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier1" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		02.
       				<select name="lt_carrier_year2" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon2" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier2" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		03.
       				<select name="lt_carrier_year3" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon3" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier3" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		04.
       				<select name="lt_carrier_year4" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon4" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier4" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		05.
       				<select name="lt_carrier_year5" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon5" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier5" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		06.
       				<select name="lt_carrier_year6" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon6" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier6" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		07.
       				<select name="lt_carrier_year7" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon7" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier7" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		08.
       				<select name="lt_carrier_year8" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon8" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier8" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		09.
       				<select name="lt_carrier_year9" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon9" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier9" siz="150" style="width:847px;" />
               </td>			    
             </tr>
             <tr>
               <td colspan="8">
               		10.
       				<select name="lt_carrier_year10" style="width:60px;"><option value="">년도</option><%for(int i=0;i<100;i++){%><option value="<%=thisYear-i%>"><%=thisYear-i%></option><%}%></select>
					<select name="lt_carrier_mon10" style="width:45px;" ><option value="">월</option><%for(int i=0;i<12;i++){%><option value="<%=(i+1<10?"0":"")%><%=i+1%>"><%=(i+1<10?"0":"")%><%=i+1%></option><%}%></select>
       				<input type="text" id="m" name="lt_carrier10" siz="150" style="width:847px;" />
               </td>			    
             </tr>
			 <tr>
               <td class="alt1" align="center">비 고</td>
               <td colspan="8">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="m" name="lt_remark" siz="148" style="width:960px;" /></td>               		   
             </tr>			 
<!-- 			 <tr> -->
<!--                <td class="alt1" align="center">은행명</td> -->
<!--                <td><input type="text" id="m" name="lt_bankname" size="15" /></td> -->
<!--                <td class="alt" align="center">예금주</td> -->
<!--                <td><input type="text" id="m" name="lt_bankReceiptName" size="15" /></td>            -->
<!--                <td class="alt" align="center">계좌 번호</td> -->
<!--                <td colspan="4"><input type="text" id="m" name="lt_bankaccount" size="68" /></td>         		  		   -->
<!--              </tr>  -->
        </table>
		<%if("cocone".equals(g_id) || "minju51".equals(g_id)){ %>
		<ul class="btnIcon_2" style="left:868px;">
		<%}else{ %>
		<ul class="btnIcon_2">
		<%}%>
	  	   <%if("cocone".equals(g_id) || "minju51".equals(g_id)){ %>
		  <li><a href="javascript:goDel();"><img src="images/icon_delete.gif"    onclick="" alt="삭제" /></a></li>
<!-- 		  <li><span onclick="goDel();" style="cursor:pointer;">[삭제]</span></li> -->
		  <%} %>
		  <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>
		  <li><a href="javascript:goInsert();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
		  <li><a href="javascript:goSearch(mem,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>		  	 
		</ul>	      		
</form>
<br><br>
	 
<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
<p class="btnSave">
	<a href="javascript:excelDown();"><img src="images/icon_excel.gif" onmouseover="this.src=this.src.replace('_off.','_on.')" onmouseout="this.src=this.src.replace('_on.','_off.')"/></a>
	<a href="javascript:goPrint();"><img src="images/icon_s12.gif" onclick="" alt="출력" /></a>
</p>
</form>
</div>
</div>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>
