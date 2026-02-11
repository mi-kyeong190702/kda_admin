<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="java.text.*"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<% 
		request.setCharacterEncoding("utf-8"); 
	  	List<Map> result=(List<Map>)request.getAttribute("result");
	  	List<Map> code=(List<Map>)session.getAttribute("code");
	  	String pCode=StringUtil.nullToStr((String)request.getAttribute("pCode"), request.getParameter("pCode"));	  	
	  	String url="memberBasic.do?method=memoList&pCode="+pCode;
	  	SimpleDateFormat sdf=null;
	  	String format="yyyyMMdd";
	  	sdf=new SimpleDateFormat(format, Locale.KOREA);
	  	String date=sdf.format(new java.util.Date());
	  	String errMsg="";
		if(request.getAttribute("errMsg")!=null){
			errMsg=URLDecoder.decode(request.getAttribute("errMsg").toString(),"UTF-8"); 
		}
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
document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};
function init(){
	var errMsg="<%=errMsg%>";
	if(errMsg.length>5){
		alert(errMsg);
		return;
	}
}
$(document).ready(function(){
jQuery("#list").jqGrid({
      url: "<%=url%>",
      datatype: "json",
      mtype: 'POST',
      //height:'271', //근무처명, 근무처주소 들어갈땐 밑의 height으로 교체
      height:'236', 
      width:'1097',
      colNames: ['등록일자','메모구분코드','메모구분','메모내용','등록자'],
      colModel: [
			{name:'rdate',						index:'rdate', 					width:40, 	sortable:false},
			{name:'code_memo',				index:'code_memo', 			width:40, 	sortable:false,	hidden:true},
			{name:'code_memo_name',	index:'code_memo_name', width:40, 	sortable:false},
   			{name:'memo_text',				index:'memo_text', 			width:100,	sortable:false, formatter:decode11},
   			{name:'register',					index:'register', 				width:40, 	sortable:false}   			
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
							document.memo.rDate.value=ret.rdate;
							document.memo.cMemo.value=ret.code_memo;
							var memo_text=ret.memo_text.replace(/            /gi,"\r\n");
							document.memo.mText.value=memo_text;
							document.memo.gubun.value="update";
						}
						else {}
					}
				}      
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});
function decode11(cellvalue, options, rowObject ){
	var temp=decodeURIComponent(cellvalue);	
	return replaceAll(temp,"+"," ");
}

function replaceAll(temp,org,rep){
	return temp.split(org).join(rep).replace(/\r\n/gi,"            ");;	
}
function reg_reset(){
	document.memo.reset();
	document.memo.gubun.value='insert';
}

function reg_memo(){
	var mydata=jQuery("#list").jqGrid('getRowData');
	var input_date=document.memo.rDate.value;
	var input_cmemo=document.getElementById("cMemo").value;
	for(var i=0;i<mydata.length;i++){
		if(document.memo.gubun.value=="insert"){
			if(mydata[i].rdate==input_date&&mydata[i].code_memo==input_cmemo){
				alert("입력하신 날짜, 메모구분에 이미 작성된 메모가 있습니다.\n\r리스트에서 선택 후 수정해주십시오.");
				return;
			}
		}
	}
	if(document.getElementById("mText").value.length<2){
		alert("내용을 입력해 주십시오.");
		document.getElementById("mText").focus();
		return;
	}else{	
		document.memo.submit();
	}
}
$(function() {
	$( "#datepicker" ).datepicker();
});

</script>
</head>
<body>
<form name="memo" method="post" action="memberBasic.do?method=mMemo">
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
<input type="hidden" name="mCode_pers" value="<%=result.get(0).get("code_pers") %>"> 
<input type="hidden" name="register" value="<%=session.getAttribute("G_NAME") %>"/>
<input type="hidden" name="gubun" value="insert" />
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
            <!-- 근무처명, 주소 --> 
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
	        <li><a href="memberBasic.do?method=dues&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>회비처리</span></strong></a></li>
	        <li><a href="memberBasic.do?method=edu&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>교육</span></strong></a></li>
	        <li><a href="memberBasic.do?method=certifi&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>자격증</span></strong></a></li>
	        <li><a href="memberBasic.do?method=donation&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>기부&frasl;기금</span></strong></a></li>
			<li><a href="#" class="overMenu"><strong><span>메모</span></strong></a></li>			
          </ul>
        </div>
		<div class="mTabmenu_01" id="tab01" style="display:show">
        <table class="mytable_08" cellspacing="0" summary="메모">
          <caption>메모</caption>
             <tr>
               <td class="nobg" style="width:30px">날짜</td>
               <td class="nobg3" style="width:30px">메모구분</td>			   
			   <td class="nobg3">내용</td>			   		  		   			  
			 </tr>             
			 <tr>
               <td class="alt2">
               <input type="text" name="rDate" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px  value="<%=date %>" size="12" align="center"  readOnly="false"/></td>
               <td >
               	<select name="cMemo" id="cMemo">
               		<% 
               		String detCode,detCName="";
                	for(int i=0;i<code.size();i++){
                		if("026".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
	                		detCName=code.get(i).get("detcodename").toString();
	                		out.println("<option value="+detCode+">"+detCName+"</option>");	                				                			
                		}
                	}
                %> 
               	</select>
               </td>
               <td><textarea name="mText" id="mText" rows="5" cols="100" ></textarea></td>                                         
             </tr>			 
        </table>
        <p class="btnSave">
			<a href="javascript:reg_reset();"><img src="images/icon_new.gif" alt="신규" /></a>
			<a href="javascript:reg_memo();"><img src="images/icon_save.gif" alt="저장" /></a>
		</p>
		</div><!--mTabmenu_01 End-->
		<div class="mTabmenu_01" id="tab02" style="display:show">
			<table id="list"></table>
			<div id="pager2"></div>
		</div><!--mTabmenu End-->
		</div><!--mTabmenu End-->
	</div><!--M_contents End-->
 </form>
 
</body>
</html>