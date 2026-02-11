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
	  	List<Map> promise=(List<Map>)request.getAttribute("promise");
	  	String pCode=StringUtil.nullToStr((String)request.getAttribute("pCode"), request.getParameter("pCode"));	  	
	  	String url="memberBasic.do?method=donList&pCode="+pCode;
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
      //height:'279', //근무처명, 근무처주소 들어갈땐 밑의 height으로 교체
      height:'244',
      width:'1097',
      colNames: ['구분코드','구분','입금일자','순번','납부방법코드','납부방법','은행명','입금액','약정번호','기금회차','비고','확인여부','등록자'],
      colModel: [
			{name:'gubun',				index:'gubun', 					width:40, 	sortable:false,	hidden:true},
			{name:'gName',				index:'gName', 				width:30, 	sortable:false,	align:'center'},
			{name:'pres_let_dt',			index:'pres_let_dt', 			width:50, 	sortable:false,	align:'center'},
   			{name:'day_seq',				index:'day_seq', 				width:20,	sortable:false,	align:'center'},
   			{name:'code_pay_flag',		index:'code_pay_flag',		width:100,	sortable:false,	hidden:true},
   			{name:'pfName',				index:'pfName', 				width:40, 	sortable:false,	align:'center'},
			{name:'bank_name',			index:'bank_name', 			width:70, 	sortable:false},
   			{name:'pres_money',			index:'pres_money', 			width:50,	sortable:false,	formatter:'currency',	formatoptions:{thousandsSeparator:','},	align:'right'},
   			{name:'promise_no',			index:'promise_no', 			width:30,	sortable:false,	align:'center'},
   			{name:'promise_seq',		index:'promise_seq', 			width:50, 	sortable:false,	align:'center'},
   			{name:'donation_remark',	index:'donation_remark',	width:100,	sortable:false,	hidden:true},
   			{name:'conform_dt',			index:'conform_dt', 			width:50,	sortable:false,	align:'center'},
   			{name:'register',				index:'register', 				width:40, 	sortable:false,	align:'center'}   			
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
							document.donation.gubun.value=ret.gubun;
							document.donation.code_pay_flag.value=ret.code_pay_flag;
							document.donation.bank_name.value=ret.bank_name;
							document.donation.pres_money.value=curr(ret.pres_money);
							document.donation.donation_remark.value=ret.donation_remark;
							if(ret.conform_dt.length!=8){
								document.donation.conform_dt.value='N';
							}else{
								document.donation.conform_dt.value='Y';
							}
							document.donation.pres_let_dt.value=ret.pres_let_dt;
							document.donation.day_seq.value=ret.day_seq;
						}
						else {}
					}
				}      
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function reg_update(){
	document.donation.submit();
}
function curr(n){
	var reg=/(^[+-]?\d+)(\d{3})/;
	n+='';
	while(reg.test(n))
		n=n.replace(reg,'$1'+','+'$2');
	return n;
}

$(function() {
	$( "#datepicker" ).datepicker();
});
</script>
</head>
<body>
<form name="donation" method="post" action="memberBasic.do?method=mDon"> 
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
<input type="hidden" name="pres_let_dt" id="pres_let_dt" value="">
<input type="hidden" name="day_seq" id="day_seq" value="">
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
            
             
            <!-- 근무처명, 주소  --> 
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
	        <li><a href="#" class="overMenu"><strong><span>기부&frasl;기금</span></strong></a></li>
			<li><a href="memberBasic.do?method=memo&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>메모</span></strong></a></li>			
          </ul>
        </div>
		<div class="mTabmenu_01" id="tab01" style="display:show">
        <table class="mytable_07" cellspacing="0" summary="기금">
          <caption>기금</caption>
          	<tr>
               <td class="nobg">약정번호</td>
			   <td class="nobg3">납부방법</td>
			   <td class="nobg3">약정금액</td>
               <td class="nobg3">약정개월수</td>			   
			   <td class="nobg3">시작년월</td>
               <td class="nobg3">종료년월</td>			   			  
               <td class="nobg3">기금약정상태</td>
               <td class="nobg3">비고</td>			   			  
			 </tr>
			 <tr>
			 	<td class="alt2"><%if(promise.size()>0) {out.print(promise.get(0).get("promise_no"));}else{out.print("없음");} %></td>
			 	<td><%if(promise.size()>0) {out.print(promise.get(0).get("payflag"));}else{out.print("없음");} %></td>
			 	<td><%if(promise.size()>0) {out.print((int)Float.parseFloat(promise.get(0).get("promise_amt").toString()));}else{out.print("없음");} %></td>
			 	<td><%if(promise.size()>0) {out.print(promise.get(0).get("promise_mon"));}else{out.print("없음");} %></td>
			 	<td><%if(promise.size()>0) {out.print(promise.get(0).get("start_yyyymm"));}else{out.print("없음");} %></td>
			 	<td><%if(promise.size()>0) {out.print(promise.get(0).get("end_yyyymm"));}else{out.print("없음");} %></td>
			 	<td><%if(promise.size()>0) {out.print(promise.get(0).get("promise_state"));}else{out.print("없음");} %></td>
			 	<td><%if(promise.size()>0) {out.print(promise.get(0).get("promise_remark"));}else{out.print("없음");} %></td>
			 </tr>
			 <tr>
               <td class="nobg">구분</td>
			   <td class="nobg3">납부방법</td>
			   <td class="nobg3">은행명</td>
               <td class="nobg3">입금금액</td>			   
			   <td class="nobg3" colspan="3">비고</td>
               <td class="nobg3">확인여부</td>			   			  
			 </tr>			 	              		  		  		   			   
			 <tr>
               <td class="alt2"><select name="gubun" >
                <% 
                	String detCode,detCName="";
                	for(int i=0;i<code.size();i++){
                		if("040".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");                				                		
                		}
                	}
                %>
                </select></td>              
               <td><select name="code_pay_flag" >
                <% 
                	for(int i=0;i<code.size();i++){
                		if("014".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");                				                		
                		}
                	}
                %>
                </select></td>
               <td><input type="text" name="bank_name" size="10" value=""/></td>
               <td><input type="text" name="pres_money" size="10" value=""/></td>
               <td colspan="3"><input type="text" name="donation_remark" size="58" value=""/></td>
               <td><select name="conform_dt" >
               		<option value="N" selected>미확인</option>
               		<option value="Y">확인</option>
               </select></td>	  
			 </tr>		 
        </table>
        <p class="btnSave">
			<a href="javascript:reg_update();"><img src="images/icon_save.gif" alt="저장" /></a>
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