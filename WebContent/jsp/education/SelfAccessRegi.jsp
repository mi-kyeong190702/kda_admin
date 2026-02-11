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
	      height:'100',
	      colNames: [ '자율학습명','시작일','종료일','자격구분','횟수'],
	      colModel: [
				{name:'receipt_dt',  index:'receipt_dt', width: 150,editable: false, align: 'center'},
	   			{name:'year_seq',    index:'year_seq',   width: 100,editable: false, align: 'right'},
	   			{name:'center',      index:'center',     width: 100,editable: false, align: 'right'},
	   			{name:'register',    index:'register',   width: 100,editable: false, align: 'right'},
	   			{name:'receive',     index:'receive',    width: 100,editable: false, align: 'right'},
	   			    ],
	   		    rowNum:15,
	   		    sortname: 'receipt_dt',
	   			pager: '#pager2',
	            rownumbers: true,
	            viewrecords: true,
	 			multiselect: true,
	 			gridview: true,
	 			caption: '자율학습등록',
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
		<li><a href="/main3.3.do">자율학습</a></li>
		<li>&gt;</li>
		<li class="NPage">등록</li>
	  </ul>
	  <div id="ts_tabmenu">
        <ul>
	      <li><a href="#" class="overMenu"><strong><span>등록</span></strong></a></li>
	      <li><a href="/main3.4.do"><strong><span>신청자</span></strong></a></li>	  
		  <li><a href="/main3.5.do"><strong><span>결과처리</span></strong></a></li>
        </ul>
      </div>
	  <div class="eTabmenu_01">
        <table class="etable_03" cellspacing="0" summary="교육">
          <caption>자율학습</caption>            			 
             <tr>
               <td class="nobg">학습명</td>
               <td colspan="3" class="nobg1"><input type="text" id="m" size="30" /></td>
			   <td class="nobg2">자격구분</td>
               <td class="nobg1"><select>
                <option value="선택">2012</option>
                <option value="선택">선택선택선택</option>       
                </select></td>			   
             </tr>
             <tr>
               <td class="alt1">학습년도</td>
               <td><select>
                <option value="선택">서울지부</option>
                <option value="선택">선택선택선택</option>       
                </select></td>
               <td class="alt">학습지부</td>
               <td><select>
                <option value="선택">신청자</option>
                <option value="선택">선택선택선택</option>       
                </select></td>
			   <td class="alt">횟수</td>
			   <td><select>
                <option value="선택">결제완료</option>
                <option value="선택">선택선택선택</option>       
                </select></td>
             </tr>
			 <tr>
               <td class="alt1">외부교육여부</td>
               <td><select>
                <option value="선택">결제완료</option>
                <option value="선택">선택선택선택</option>       
                </select></td>
               <td class="alt">교육평점</td>
               <td><input type="text" id="p" size="15" /></td>
			   <td class="alt">진행일자</td>
               <td><input type="text" id="p" size="15" /></td>			  
             </tr>
			 <tr>
               <td class="alt1">신청마감일</td>
               <td><select>
                <option value="선택">전체</option>
                <option value="선택">선택선택선택</option>       
                </select></td>
               <td class="alt">자율학습비</td>
               <td><select>
                <option value="선택">발급</option>
                <option value="선택">선택선택선택</option>       
                </select></td>
			   <td class="alt">비고</td>
               <td></td>			  
             </tr>			 			 		
        </table>			
		<ul class="btnIcon_1">
		  <li><a href="#"><img src="images/icon_new.gif" alt="신규" /></a></li>
		  <li><a href="#"><img src="images/icon_save.gif" alt="저장" /></a></li>
		  <li><a href="#"><img src="images/icon_search.gif" alt="검색" /></a></li>		  	 
		</ul>
		</form>	  
		<br>   
		<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post"></form> 		
	  </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	  
 
 </body> 
</html>
