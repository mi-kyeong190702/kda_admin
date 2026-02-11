<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>대한영양사협회</title>
  <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
  
  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
  <link rel="stylesheet" type="text/css" href="css/import.css" />  
  <link rel="stylesheet" type="text/css" href="css/education.css" />
  <script type="text/javascript" src="js/comm.js"></script>
  
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
  
<script type="text/javascript">

$(document).ready(function(){	
jQuery("#list").jqGrid({	  
	  url: 'locall',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'300',
      colNames: [ '이름','주민번호','분야','과목','근무처','연락처','지역','비고'],
      colModel: [
			{name:'receipt_dt',  index:'receipt_dt', width: 120,editable: false, align: 'center'},
   			{name:'year_seq',    index:'year_seq',   width: 100,editable: false, align: 'right'},
   			{name:'center',      index:'center',     width: 100,editable: false, align: 'right'},
   			{name:'register',    index:'register',   width: 100,editable: false, align: 'right'},
   			{name:'receive',     index:'receive',    width: 100,editable: false, align: 'right'},
   			{name:'title',       index:'title',      width: 100,editable: false, align: 'right'},		
   			{name:'attach',      index:'attach',     width: 100,editable: false, align: 'right'},	
   			{name:'remark',      index:'remark',     width: 100,editable: false, align: 'right'},
   			    ],
   		    rowNum:15,
   		    sortname: 'receipt_dt',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			gridview: true,
 			caption: '교육및시험등록',
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
						onSubmitList(id);
					}
					else {}
				
				}
			}
  
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});
</script>  
 </head>

 <body>
<form action="" method="post">

	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="/main3.do">교육</a></li>
		<li>&gt;</li>
		<li><a href="/main3.7.do">강사관리</a></li>
		<li>&gt;</li>
		<li class="NPage">검색</li>
	  </ul>
	  <div id="ts_tabmenu">
        <ul>
	      <li><a href="/main3.7.do"><strong><span>등록</span></strong></a></li>
	      <li><a href="#" class="overMenu"><strong><span>검색</span></strong></a></li>	  
		  <li><a href="/main3.9.do"><strong><span>출강정보</span></strong></a></li>
        </ul>
      </div>
	  <div class="eTabmenu_01">        
		<table class="etable_06" cellspacing="0" summary="강사관리">
          <caption>검색</caption>            		 
			 <tr>
               <td class="nobg">분야</td>
               <td class="nobg1"><select id="search">
       <option value="성명">산업보건</option>
       <option value="주민번호">주민번호</option>       
       </select></td>
               <td class="nobg2">지역</td>
               <td class="nobg1"><select id="search">
       <option value="성명">서울</option>
       <option value="주민번호">주민번호</option>       
       </select></td>			   		  
             </tr>	
			 <tr>
               <td class="alt1">검색항목</td>
               <td colspan="2" class="nobg3">&nbsp;&nbsp;&nbsp;<input type="radio" name="post" id="o" value="post" />이름&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="post" id="n" value="post" />주민번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="post" id="n" value="post" />근무처&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="post" id="n" value="post" />
			   과목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="m" size="20" /></td> 
			   <td><a href="#"><img src="images/icon_search.gif" alt="검색" /></a></td>
             </tr>
        </table>
		<!-- <table class="etable_07" cellspacing="0" summary="강사관리">
          <caption>검색</caption>             
             <tr>
               <td class="nobg">번호</td>               			   
			   <td class="nobg1">선택</td>
			   <td class="nobg1">이름</td>
			   <td class="nobg1">주민번호</td>               			   
			   <td class="nobg1">분야</td>
			   <td class="nobg1">과목</td>
			   <td class="nobg2">근무처</td> 
			   <td class="nobg2">연락처</td> 
			   <td class="nobg2">지역</td> 
			   <td class="nobg2">비고</td> 
			 </tr> 			 
			 <tr>
               <td class="alt">1</td>
               <td><input type="checkbox" name="t" id="t" /></td>
               <td></td>
               <td></td> 
			   <td></td>
               <td></td>
               <td></td>
			   <td></td>
               <td></td>
               <td></td>
			 </tr>
			 <tr>
               <td class="alt">2</td>
               <td><input type="checkbox" name="t" id="t" /></td>
               <td></td>
               <td></td> 
			   <td></td>
               <td></td>
               <td></td>
			   <td></td>
               <td></td>
               <td></td>
			 </tr>	
			 <tr>
               <td class="alt">3</td>
               <td><input type="checkbox" name="t" id="t" /></td>
               <td></td>
               <td></td> 
			   <td></td>
               <td></td>
               <td></td>
			   <td></td>
               <td></td>
               <td></td>
			 </tr>				
        </table> -->		
		<!-- <ul class="btnIcon_2">
		  <li><a href="#"><img src="images/icon_s4.gif" alt="문자전송" /></a></li>
		  <li><a href="#"><img src="images/icon_s3.gif" alt="메일발송" /></a></li>
		  <li><a href="#"><img src="images/icon_excel.gif" alt="엑설저장" /></a></li>
		  <li><a href="#"><img src="images/icon_s12.gif" alt="출력" /></a></li>
		  <li><a href="#"><img src="images/icon_save.gif" alt="저장" /></a></li>
		</ul> -->				
 </form>
 <br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
		<ul class="btnIcon_2">
		  <!-- <li><a href="#"><img src="images/icon_s4.gif" alt="문자전송" /></a></li>
		  <li><a href="#"><img src="images/icon_s3.gif" alt="메일발송" /></a></li> -->
		  <li><a href="#"><img src="images/icon_excel.gif" alt="엑설저장" /></a></li>
		  <li><a href="#"><img src="images/icon_s12.gif" alt="출력" /></a></li>
		  <li><a href="#"><img src="images/icon_save.gif" alt="저장" /></a></li>
		</ul></form>
 </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	
 
 </body> 
</html>
</html>
