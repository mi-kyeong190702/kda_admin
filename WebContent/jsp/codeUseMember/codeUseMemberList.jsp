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
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>


<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

<script type="text/javascript">

$(document).ready(function(){	
jQuery("#list").jqGrid({	  
	  url: 'member.do?method=memberstatuslist',
	  datatype: "json",
      mtype: 'post',
      height:'365',
      width:'1100',
      colNames: [ '구분', '직영','준직영','위탁','비집단급식소','합계'],
      colModel: [
			{name:'GUBUN',index:'GUBUN', width:120, editable: false, align: 'center'},
   			{name:'VAR1', index:'VAR1',  width: 100,editable: false, align: 'right'},
   			{name:'VAR2', index:'VAR2',  width: 100,editable: false, align: 'right'},
   			{name:'VAR3', index:'VAR3',  width: 100,editable: false, align: 'right'},
   			{name:'VAR4', index:'VAR4',  width: 100,editable: false, align: 'right'},		
   			{name:'SUM',  index:'SUM',   width: 100,editable: false, align: 'right'},		
   			    ],
   		    rowNum:15,
   			pager: '#pager2',
            rownumbers: true,
 			viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '운영형태별 현황',
			altclass:'myAltRowClass',
 				
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function goSearch(form,intPage){

	var startdate   = sForm.startdate.value;
	var enddate		= sForm.enddate.value;
    if ( startdate == "" || enddate == "" ) {
        alert("기간을 입력하세요.") ;
        return ;
    } 
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"member.do?method=memberstatuslist&startdate="+startdate+"&enddate="+enddate}).trigger("reloadGrid");
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
		<li><a href="#">회원</a></li>
		<li>&gt;</li>
		<li><a href="#">현황</a></li>
		<li>&gt;</li>
		<li class="NPage">운영형태별</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form> 
<div class="mTabmenu_01">   
<form name="sForm" method="post">
	<table class="mtable_15" cellspacing="0" summary="근무처분류별">
          <caption>대분류</caption>             
             <tr>
               <td class="nobg">기간</td>               			   
			   <td class="nobg1"><input type="text" name="startdate" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px  value="" size="12" align="center" >
				~ 
					<input type="text" name="enddate" id="datepicker1" class="input" style=padding-top:3px  style=padding-bottom:3px  value="" size="12" align="center" ></td>		  
			   <td class="nobg2"><img src="images/icon_search.gif"  onclick="javascript:goSearch(sForm,0);" /></td>			   
			 </tr> 					 
	</table>
</form>

<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
</form>
</div>
</div>
</body>
</html>

