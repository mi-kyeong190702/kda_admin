<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<% 
		request.setCharacterEncoding("utf-8"); 	  	
		request.getAttribute("result");
		String url="memberBasic.do?method=tSearchr";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="shortcut icon" href="images/favicon.ico">
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
      url: "<%=url%>",
      datatype: "json",
      mtype: 'POST',
      height:'450',
      width:'475',
      colNames: ['위탁업체코드','위탁업체명','우편번호','위탁업체주소','전화번호'],
      colModel: [
			{name:'trust_code',	index:'trust_code',		width:90, 	sortable:false,	hidden:true},
			{name:'trust_name',	index:'trust_name', 	width:100, 	sortable:false},
   			{name:'trust_post',	index:'trust_post', 	width:30,	sortable:false,	hidden:true},
   			{name:'trust_addr',	index:'trust_addr', 	width:120,	sortable:false},
   			{name:'trust_tel',		index:'trust_tel',		width:40,	sortable:false, align:'center'}
                ],
                rowNum:1000000,
                pager: '#pager2',
                viewrecords: true,	
				altRows:true,
				altclass:'myAltRowClass',
				rownumbers : true,				
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
							opener.document.comp.cTName.value=ret.trust_name;		
							opener.document.comp.cTtel.value=ret.trust_tel;	
							opener.document.comp.cTaddr.value=ret.trust_addr;
							opener.document.comp.cTcode.value=ret.trust_code;
							self.close();
						}
						else {}
					}
				}
      
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function reg_mod(){
	document.tcomp.action="memberBasic.do?method=mTcomp";
	document.tcomp.submit();
}
function tsearch(){
	document.tcomp.action="memberBasic.do?method=tSearch&sel=Y&tName="+escape(encodeURIComponent(document.tcomp.tName.value));
	document.tcomp.submit();
}
function tinsert(){
	inTcomp.style.display="block";
}
</script>
</head>
<body>
<form name="tcomp" method="post" action="">
<input type="hidden" name="register" value="<%=session.getAttribute("G_NAME") %>"/>
	<div id="T_contents">
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="위탁업체 검색">
          <caption>회원검색</caption>          
			<tbody>
            <tr>           
              <td class="mtbl" >업체명 검색</td>
              <td class="mtbl1"><input type="text" name="tName" size="13" value="" />&nbsp;<input type="button" name="btn" value="검색" onclick="javascript:tsearch();">&nbsp;&nbsp;<input type="button" name="btn2" value="신규업체추가" onclick="javascript:tinsert();"></td>
            </tr>                  
            </tbody>
          </table>       
	  </div><!--mSearch_01 End-->
	  <p />
	  <div class="mSearch_01" id="inTcomp" style="display:none;">
        <table class="tbl_type" border="1" cellspacing="0" summary="위탁업체 검색">
        	<tr>
        		<td>위탁업체명</td><td><input type="text" name="trust_name" size="10" value="" /></td>
        	    <td>전화번호</td><td><input type="text" name="trust_tel" size="10" value="" /></td>
        	</tr>
        	<tr>
        		<td>우편번호</td><td><input type="text" name="trust_post" size="10" value="" /></td>
        		<td>주소</td><td><input type="text" name="trust_addr" size="30" value="" /></td></tr>
        </table>
        <p align="right"><a href="javascript:reg_mod();"><img src="images/icon_save.gif" alt="저장" /></a></p>
      </div>
		<div class="mTabmenu_01" id="tab02" style="display:show">
			<table id="list"></table>
			<div id="pager2"></div>
		</div><!--mTabmenu End-->
	</div><!--M_contents End-->
 </form>
</body>
</html>