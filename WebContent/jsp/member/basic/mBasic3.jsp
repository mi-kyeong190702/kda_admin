<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="java.text.*"%>
<% 
		request.setCharacterEncoding("utf-8"); 
	  	List<Map> result=(List<Map>)request.getAttribute("result");
	  	List<Map> code=(List<Map>)session.getAttribute("code");
	  	String pCode=StringUtil.nullToStr((String)request.getAttribute("pCode"), request.getParameter("pCode"));	  	
	  	String url="memberBasic.do?method=duesList&pCode="+pCode;
	  	SimpleDateFormat sdf=null;
	  	String format="yyyyMMdd";
	  	sdf=new SimpleDateFormat(format, Locale.KOREA);
	  	String date=sdf.format(new java.util.Date());
	  	String gb="new";
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
      //height:'445', //근무처명, 주소가 들어갈땐 밑의 height으로
      height:'410',
      width:'1097',
      colNames: ['회비구분','차수','유효기간시작일','유효기간종료일','인증일자','확정일자','본회예수금','지부예수금','해당지부 및 산하단체명'],
      colModel: [
			{name:'gubun',				index:'yyyy', 					width:40, 	sortable:false,	align:'center'},
			{name:'pers_mem_order',	index:'pers_mem_order', 	width:20, 	sortable:false,	align:'center'},
			{name:'auth_start',			index:'auth_start', 			width:50, 	sortable:false,	align:'center'},
			{name:'auth_end',				index:'auth_end', 				width:50, 	sortable:false,	align:'center'},
   			{name:'conform_dt',			index:'conform_dt', 			width:30,	sortable:false,	align:'center'},
   			{name:'receipt_dt',			index:'receipt_dt', 			width:30,	sortable:false,	align:'center'},
   			{name:'center_money',		index:'center_money',		width:50,	sortable:false,	formatter:'currency',	formatoptions:{thousandsSeparator:','},	align:'right'},
   			{name:'bran_money',			index:'bran_money', 			width:50,	sortable:false,	formatter:'currency',	formatoptions:{thousandsSeparator:','},	align:'right'},   			
   			{name:'bran_name',			index:'bran_name', 			width:80, 	sortable:false,	align:'center'}   			
                ],
                rowNum:10,
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
<form name="memo" method="post" action="memberBasic.do?method=mMemo">  
<input type="hidden" name="mCode_pers" value="<%=result.get(0).get("code_pers") %>"> 
<input type="hidden" name="register" value="<%=session.getAttribute("G_NAME") %>"/>
<input type="hidden" name="gubun" value="<%=gb %>" />
	<div id="M_contents">
	  <div class="mSearch_01">
        <table class="tbl_type1" border="1" cellspacing="0" summary="회원검색">
          <caption>회원검색</caption>          
			<tbody>
            <tr>           
              <td class="mtbl">이름</td>
              <td class="mtbl1"><%=result.get(0).get("pers_name") %></td>
              <td class="mtbl">아이디</td>
              <td class="mtbl1"><%=result.get(0).get("id") %></td>
              <td class="mtbl">패스워드</td>
              <td class="mtbl1">**********</td>
            </tr> 
            
                                         <tr>
            	
<!--             	JUMIN_DEL -->
<!--               <td>주민번호</td> -->
<!--               <td> -->
<%--               	<%=result.get(0).get("pers_no").toString().substring(0,6)%> - ******* &nbsp;&nbsp; --%>
<%--               	(성별 : <%if("1".equals(result.get(0).get("code_sex").toString())) out.print("남"); else out.print("여"); %>) --%>
<!-- 			  </td> -->
			  <td>생년월일</td>
              <td>
              	<%=result.get(0).get("pers_birth") %>
			  </td>
              <td>성별</td>
              <td>
              	<%if("1".equals(result.get(0).get("code_sex").toString())) out.print("남"); else out.print("여"); %>
			  </td>
			  
              <td>면허번호</td>
              <td><%=result.get(0).get("lic_no") %> &nbsp;&nbsp;(발급일자:<%=result.get(0).get("lic_print_dt") %>)</td>
            </tr>
                             
                             
            <!-- 근무처명, 주소--> 
            <tr>
	            <td>회원구분</td>
	            <td><%=result.get(0).get("c_member_name") %></td>
            	<td>근무처명</td>
            	<td><%=result.get(0).get("company_name") %></td>
            	<td>근무처 주소</td>
            	<td colspan='2'><%=result.get(0).get("company_add") %></td>
            </tr>            
            <tr>           
              <td>소속지부</td>
              <td><%=result.get(0).get("bran_name") %></td>
              <td>소속분과</td>
              <td><%=result.get(0).get("big_name") %></td>
              <td>소속분회</td>
              <td><%=result.get(0).get("sect_name") %></td>         
            </tr>
            <tr>           
              <td>근무처구분</td>
              <td><%=result.get(0).get("great_name") %></td>
              <td>근무처대분류
			  <td><%=result.get(0).get("part_name") %></td>
              <td>근무처소분류
			  <td><%=result.get(0).get("small_name") %></td>
            </tr>			
            </tbody>
            </table>       
	  </div><!--mSearch_01 End-->
	   <div class="mTabmenu">
	    <div id="ts_tabmenu">
          <ul>
	        <li><a href="memberBasic.do?method=memInfo&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>기본정보</span></strong></a></li>
	        <li><a href="memberBasic.do?method=comp&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>근무처정보</span></strong></a></li>
	        <li><a href="memberBasic.do?method=bank&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>회비입출</span></strong></a></li>
	        <li><a href="#" class="overMenu"><strong><span>회비처리</span></strong></a></li>
	        <li><a href="memberBasic.do?method=edu&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>교육</span></strong></a></li>
	        <li><a href="memberBasic.do?method=certifi&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>자격증</span></strong></a></li>
	        <li><a href="memberBasic.do?method=donation&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>기부&frasl;기금</span></strong></a></li>
			<li><a href="memberBasic.do?method=memo&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>메모</span></strong></a></li>			
          </ul>
        </div>
		<div class="mTabmenu_01" id="tab02" style="display:show">
			<table id="list"></table>
			<div id="pager2"></div>
		</div><!--mTabmenu End-->
		</div><!--mTabmenu End-->
	</div><!--M_contents End-->
 </form>
 
</body>
</html>