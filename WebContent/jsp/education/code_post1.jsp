<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회-우편번호 검색</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/m_search.css" />
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
      url: "lecture.do?method=postSearch&sel=Y",
      datatype: "json",
      mtype: 'POST',
      height:'260',
      width:'650',
      colNames: ['우편번호','도로명주소','지번주소','성공여부','에러메세지'],
      colModel: [
			{name:'zipNo',			index:'zipNo',			width:30, 	sortable:false, align:'center'},
			{name:'lnmAdres',	index:'lnmAdres',		width:100,	sortable:false},
			{name:'rnAdres',		index:'rnAdres', 		width:100, 	sortable:false},
			{name:'successYN',	index:'successYN',	width:100,	sortable:false, hidden:true},
			{name:'errMsg',		index:'errMsg',			width:100,	sortable:false, hidden:true}
                ],
                rowNum:20000,
                pager: '#pager2',
                viewrecords: true,	
				altRows:true,
				altclass:'myAltRowClass',
				rownumbers : true,
				loadComplete: function () {                    
                    if($("#list").getCell('1','successYN')=='N'){
                    	alert($("#list").getCell('1','errMsg'));
                    	$('#list').jqGrid('clearGridData');
                    }					                    
                },
				onSelectRow: function(ids) {
					if(ids == null) {
						ids=0;
						if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
						{
							alert("컬럼을 정확히 선택해 주세요");
						}
					} else {
						var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
						if(id){
							var ret = jQuery("#list").jqGrid('getRowData',id);
							opener.document.mem.lt_comp_post.value=ret.zipNo;
							opener.document.mem.lt_comp_add.value=ret.lnmAdres;
							self.close();
						}
						else {}
					}
				}     
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:false});
});

function tsearch(){	
	
	var keyword   = escape(encodeURIComponent(post.tName.value));	
	var searchSe = post.searchSe.value;
	
	if(keyword.length==0){
		alert("도로명을 입력하십시오.\n\r\n\r검색방법:도로명(~로,~길)+건물번호\n\r-서울시 송파구 잠실로(도로명) 51-33(건물번호)\n\r 검색어 예)'잠실로51-33'\n\r-서울시 강동구 양재대로112길(도로명) 57(건물번호)\n\r 검색어 예)'양재대로11길57' ");
		return;
	}
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"lecture.do?method=postSearchr1&sel=Y&keyword="+keyword+"&searchSe="+searchSe}).trigger("reloadGrid"); 
}
</script>
</head>
<body>
<form name="post" method="post" action="">
<input type="hidden" name="register" value="<%=session.getAttribute("G_NAME") %>"/>
	<div id="p_contents">
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="우편번호 검색">      
			<tbody>
            <tr>           
              <td>
	              <select name="searchSe" style="width:100px">
	              		<option value="road">도로명</option>
	              		<option value="dong">동(읍/면)명</option>
	              		<option value="post">우편번호</option>
	              </select> 으로&nbsp;&nbsp;
             	  <input type="text" name="tName" size="13" value="" />&nbsp;<input type="button" name="btn" value="검색" onclick="javascript:tsearch();"></td>
            </tr>
            <tr>
            	<td style="background-color:#d4d4d4;">
            	<strong>* 검색방법</strong><p>
            	&nbsp;&nbsp;&nbsp;도로명(~로,~길)의 경우 - 서울시 중구 <strong>소공로(도로명)  70(건물번호)</strong>&nbsp;&nbsp;검색어 예) '<strong>소공로70</strong>'<br>
            	&nbsp;&nbsp;&nbsp;동(읍/면)명의 경우&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 서울시 중구 <strong>충무로1가(동명) 21-1(지번)</strong>&nbsp;&nbsp;&nbsp;검색어 예) '<strong>충무로1가21-1</strong>'<p>
            	* 도로명주소가 검색되지 않은 경우는 동(읍/면)명으로 검색하거나 또는 행정안전부 <a href="https://www.juso.go.kr" target="_blank" title="도로명주소안내시스템 - 새창으로 열림"><span style="color:#0958db;text-decoration:underline;">도로명주소안내시스템</span></a>에서<br> 
            	&nbsp;&nbsp;확인하시기 바랍니다.
            	</td>
            </tr>                  
            </tbody>
          </table>       
	  </div><!--mSearch_01 End-->
	  <p />	  
		<div class="mTabmenu_01" id="tab02" style="display:show">
			<table id="list"></table>
			<div id="pager2"></div>
		</div><!--mTabmenu End-->
	</div><!--M_contents End-->
 </form>
</body>
</html>