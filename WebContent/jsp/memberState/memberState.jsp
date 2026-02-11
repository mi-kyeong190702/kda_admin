<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>  
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/m_member.css" />
  
<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

<script type="text/javascript">
$(document).ready(function(){
jQuery("#list").jqGrid({
      url: "memberState.do?method=list",
      datatype: "json",
      mtype: 'POST',
      height:'365',
      width:'1100',
      colNames: ['위탁업체코드','위탁업체명','우편번호','주소','전화번호'],
      colModel: [
			{name:'trust_code',	index:'trust_code', 	width:40, 	align:"center", 	sortable:false},
   			{name:'trust_name',	index:'trust_name', 	width:100,	sortable:false},
   			{name:'trust_post',	index:'trust_post', 	width:40, 	align:"center",	sortable:false,	formatter:postNo},
   			{name:'trust_addr',	index:'trust_addr', 	width:200, 	sortable:false},		
   			{name:'trust_tel',		index:'trust_tel', 		width:50,	sortable:false}
                ],
                rowNum:15,
                pager: '#pager2',
               	/* rowList:[10,20,30],
               	sortname: 'id',
                sortorder: 'desc',
               	scroll:true, */
                viewrecords: true,
                multiselect: true,
				caption: '위탁업체현황',
				altRows:true,
				altclass:'myAltRowClass',
				rownumbers : true,
				caption:"위탁업체업체별",
				beforeSelectRow: function(rowid, e){
			        jQuery("#list").jqGrid('resetSelection');
			        return(true);
			    },
				onSelectRow: function(ids) {
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

function postNo(cellvalue, options, rowObject ){
	var post=cellvalue.substring(0,3)+" - "+cellvalue.substring(3,6);
	return post;
}

function goSearch(form,intPage){
	var trust_nm   = encodeURIComponent(sForm.trust_nm.value);
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"memberState.do?method=list&trust_nm="+trust_nm}).trigger("reloadGrid");
}

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
		<li class="NPage">위탁업체업체별</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form> 
<div class="mTabmenu_01">
<form name="sForm" method="post">

	    <table class="mtable_18" cellspacing="0" summary="근무처분류별">
          <caption>대분류</caption>             
             <tr>
               <td class="nobg">업체명</td>               			   
			   <td class="nobg1"><input type=" trust_nm" name="" id="trust_nm" size="20" /></td>		  
			   <td class="nobg2"><a href="javascript:goSearch(sForm,0);"><img src="images/icon_refer.gif" alt="조회" /></a></td>			   
			 </tr> 					 
        </table>

</form>
<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
<table class="mtable_18" cellspacing="0" >
<tr><td class="nobg" align="right">
<a href="downExcel/trust_company_list.xls" ><img src="images/icon_excel.gif" /></a>
</td></tr>
</table>
</form>
</div>
</div>
</body>
</html>
