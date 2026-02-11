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
	  	List<Map> code2=(List<Map>)request.getAttribute("code2");
	  	List<Map> dusH=(List<Map>)request.getAttribute("dusH");
	  	String rdt="";
	  	if(dusH.size()>0){
	  		rdt=dusH.get(0).get("regi_dt").toString();
	  	}else{
	  		rdt="없음";
	  	}
	  	JSONArray jcode=(JSONArray)request.getAttribute("jsoncode");
	  	JSONArray sumduesList=(JSONArray)request.getAttribute("sumdues");
	  	JSONArray subduesList=(JSONArray)request.getAttribute("subdues");
	  	JSONArray jdusH=(JSONArray)request.getAttribute("jdusH");
	  	String pCode=StringUtil.nullToStr((String)request.getAttribute("pCode"), request.getParameter("pCode"));	  	
	  	String url="memberBasic.do?method=bankList&pCode="+pCode;
	  	SimpleDateFormat sdf=null;
	  	String format="yyyyMMdd";
	  	sdf=new SimpleDateFormat(format, Locale.KOREA);
	  	String date=sdf.format(new java.util.Date());
	  	String gb="insert";
	  	String errMsg="";
		if(request.getAttribute("errMsg")!=null){
			errMsg=URLDecoder.decode(request.getAttribute("errMsg").toString(),"UTF-8"); 
		}
		
		String G_ID="";		
		if(session.getAttribute("G_ID")!=null){
			G_ID=session.getAttribute("G_ID").toString();
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
.readOnly1 {background-color:#f2f2f2;}
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

var oldVal=new Array();
$(document).ready(function(){
jQuery("#list").jqGrid({
      url: "<%=url%>",
      datatype: "json",
      mtype: 'POST',
      //height:'330', //근무처명, 근무처주소 들어갈땐 밑의 height으로 교체
      height:'295', 
      width:'1097',
      colNames: ['입출일자','입출구분코드','입출구분','회비구분코드','회비구분','회원구분코드','회원구분','입출금액','입출장소코드','입출장소','입출방법코드','입출방법','금융기관명','유효시작일','유효만료일','확인여부코드','인증상태','인증일','완납여부코드','완납여부','등록자','회비관리순번','회비상세관리순번','duesDelYn'],
      colModel: [
			{name:'pres_let_dt',			index:'pres_let_dt', 		width:40, 	sortable:false,	align:'center'},
			{name:'code_inout_gubun',		index:'code_inout_gubun',	width:100,	sortable:false,	hidden:true},
   			{name:'iog_name',				index:'iog_name', 			width:40, 	sortable:false,	align:'center'},
			{name:'dues_gubun',				index:'dues_gubun', 		width:40, 	sortable:false,	hidden:true},
			{name:'dg_name',				index:'dg_name', 			width:60, 	sortable:false,	align:'center'},
			{name:'code_member',			index:'code_member', 		width:40, 	sortable:false,	hidden:true},
			{name:'cm_name',				index:'cm_name', 			width:80, 	sortable:false,	align:'center'},
   			{name:'pres_money',				index:'pres_money', 		width:50, 	sortable:false,	formatter:'currency',	formatoptions:{thousandsSeparator:','},	align:'right'},
   			{name:'code_receipt',			index:'code_receipt', 		width:100,	sortable:false,	hidden:true},
   			{name:'rc_name',				index:'rc_name', 			width:50,	sortable:false,	align:'center'},	
			{name:'code_pay_flag',			index:'code_pay_flag', 		width:40, 	sortable:false,	hidden:true},
   			{name:'pf_name',				index:'pf_name', 			width:50,	sortable:false,	align:'center'},
   			{name:'bank_name',				index:'bank_name', 			width:100,	sortable:false,	align:'center'},
   			{name:'auth_start',				index:'auth_start', 		width:50,	sortable:false,	align:'center'},
   			{name:'auth_end',				index:'auth_end', 			width:50,	sortable:false,	align:'center'},
   			{name:'conform_yn',				index:'conform_yn', 		width:100,	sortable:false,	hidden:true},
   			{name:'cf_name',				index:'cf_name', 			width:40,	sortable:false,	align:'center'},
   			{name:'conform_dt',				index:'conform_dt', 		width:40,	sortable:false,	align:'center'},
   			{name:'incom_flag',				index:'incom_flag', 		width:100,	sortable:false,	hidden:true},
   			{name:'if_name',				index:'if_name', 			width:40,	sortable:false,	align:'center'},
   			{name:'register',				index:'register', 			width:40, 	sortable:false,	align:'center'},
   			{name:'dues_h_seq',				index:'dues_h_seq', 		width:40, 	sortable:false,	hidden:true},
   			{name:'dues_b_seq',				index:'dues_b_seq', 		width:40, 	sortable:false,	hidden:true},
   			{name:'duesDelYn',				index:'duesDelYn',          width:40, 	sortable:false,	hidden:true}
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
						if(jQuery("#list").jqGrid('getGridParam','records') > 0 ) //전체 레코드가 0보다 많다면
						{
							alert("컬럼을 정확히 선택해 주세요");
						}
					} else {
						var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
						if(id){
							var ret = jQuery("#list").jqGrid('getRowData',id);
							
							if(ret.incom_flag=='D'){
								cmem2(ret.dues_gubun);
								document.bank.reset();
								alert("완납여부가 출금처리인 항목은 변경 불가능합니다.");									
								/* document.bank.ifififififif.value=ret.incom_flag;
								document.bank.cfyncfyncfyn.value=ret.conform_yn;
								oldVal[0]='';
								oldVal[1]='';
								oldVal[2]='';
								oldVal[3]=''; */
							}else if(ret.conform_yn=='1'||ret.conform_yn=='2'){
								document.bank.dues_h_seq.value=ret.dues_h_seq;
								document.bank.dues_b_seq.value=ret.dues_b_seq;
								
								cmem2(ret.dues_gubun);
								document.bank.presDt.value=ret.pres_let_dt;							
								document.bank.dGubun.value=ret.dues_gubun;
								document.bank.authDt.value=ret.auth_start;
								document.bank.iogGubun.value=ret.code_inout_gubun;
								document.bank.pFlag.value=ret.code_pay_flag;
								document.bank.bName.value=ret.bank_name;
								document.bank.pMoney.value=ret.pres_money;
								document.bank.rc.value=ret.code_receipt;
								document.bank.cMember.value=ret.code_member;
								document.bank.cf_yn.value=ret.conform_yn;
								document.bank.gubun.value='update';
								document.bank.dues_h_seq.value=ret.dues_h_seq;
								document.bank.dues_b_seq.value=ret.dues_b_seq;
								oldVal[0]=ret.dues_gubun;
								oldVal[1]=ret.code_member;
								oldVal[2]=ret.conform_yn;
								oldVal[3]=ret.code_inout_gubun;
								document.bank.ifififififif.value=ret.incom_flag;
								document.bank.cfyncfyncfyn.value=ret.conform_yn;
								document.bank.mStart.value='no';
							}else{
								document.bank.dues_h_seq.value=ret.dues_h_seq;
								document.bank.dues_b_seq.value=ret.dues_b_seq;
								
								alert("인증상태가 인증인 항목은 유효시작일만 변경하실 수 있습니다.\r\n나머지 항목은 변경해도 적용되지 않습니다.");
								cmem2(ret.dues_gubun);
								document.bank.presDt.value=ret.pres_let_dt;							
								document.bank.dGubun.value=ret.dues_gubun;
								document.bank.tmpDgubun.value=ret.dues_gubun;
								document.bank.authDt.value=ret.auth_start;
								document.bank.iogGubun.value=ret.code_inout_gubun;
								document.bank.pFlag.value=ret.code_pay_flag;
								document.bank.bName.value=ret.bank_name;
								document.bank.pMoney.value=ret.pres_money;
								document.bank.rc.value=ret.code_receipt;
								document.bank.cMember.value=ret.code_member;
								document.bank.cf_yn.value=ret.conform_yn;
								document.bank.gubun.value='update';
								document.bank.dues_h_seq.value=ret.dues_h_seq;
								document.bank.dues_b_seq.value=ret.dues_b_seq;
								oldVal[0]=ret.dues_gubun;
								oldVal[1]=ret.code_member;
								oldVal[3]=ret.code_inout_gubun;
								document.bank.mStart.value='yes';	
								document.bank.ifififififif.value=ret.incom_flag;
								document.bank.cfyncfyncfyn.value=ret.conform_yn;
							}
												
						}
						else {}
					}
				}      
	});
	jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function reg_reset(){
	document.bank.reset();
	document.bank.gubun.value='insert';
}

/* 2022 */
function reg(){
	var temp=jQuery("#list").jqGrid('getCol','conform_yn');
	for(var i=0;i<temp.length;i++){
		if(temp[i]==1){
			if(document.bank.gubun.value=='insert'){
				alert("인증상태가 미인증인 항목이 있습니다.\r\n미인증인 항목을 먼저 처리해주십시오.");
				return;	
			}		
		}
		if(temp[i]==2){
			if(document.bank.gubun.value=='insert'){
				alert("인증상태가 선인증인 항목이 있습니다.\r\n선인증인 항목을 먼저 처리해주십시오.");
				return;	
			}		
		}
	}
    
	if(document.bank.pMoney.value==""||document.bank.pMoney.value=="0"){
    	alert("입출금액이 0이거나 비어있습니다. 확인해주십시오.");
    	return;
    }
	if(document.bank.authDt.value==""){
    	alert("유효시작일을 입력해주십시오.");
    	return;
    }
    
	var jc=eval(<%=jcode%>);
	var presdt=document.bank.presDt.value;
	var iog="";
    if(document.bank.iogGubun.value=='1'){
    	iog='입금';
    }else{
    	iog='출금';
    }
	var dg="";
	if(document.bank.dGubun.value=='1'){
		dg='연회비';
	}else if(document.bank.dGubun.value=='2'){
		dg='평생회비';
	}else if(document.bank.dGubun.value=='4'){
		dg='CMS회비';
	}else{
		dg='산하단체회비';
	}
	var cm="";
	for(var i=0;i<jc.length;i++){
		if( jc[i].detcode==document.bank.cMember.value){			
			cm=jc[i].detcodename;
		}
	}
	var pm=document.bank.pMoney.value;
	var rc="";
	if(document.bank.rc.value=='1'){
		rc='본회';
	}else if(document.bank.rc.value=='2'){
		rc='지부';
	}
	var pf="";
	if(document.bank.pFlag.value=='10'){
		pf='무통장';
	}else if(document.bank.pFlag.value=='20'){
		pf='카드';
	}else if(document.bank.pFlag.value=='30'){
		pf='계산서';
	}else if(document.bank.pFlag.value=='40'){
		pf='현금';
	}else if(document.bank.pFlag.value=='15'){
		pf='CMS';
	}else if(document.bank.pFlag.value=='60'){
		pf='지로';
	}
	var bn=document.bank.bName.value;
	var adt=document.bank.authDt.value;
	var cfyn="";
	if(document.bank.cf_yn.value=='1'){
		cfyn='미인증';
	}else if(document.bank.cf_yn.value=='2'){
		cfyn='선인증';
	}else  if(document.bank.cf_yn.value=='3'){
		cfyn='인증';
	}
	
	if(document.bank.gubun.value=='insert'){
		var reg_yn=confirm("입력하신 값은 다음과 같습니다.\r\n\r\n입출일자: "+presdt+"\r\n입출구분: "+iog+"\r\n회비구분: "+dg+"\r\n회원구분 "+cm+"\r\n입출금액: "+pm+"\r\n입출장소: "+rc+"\r\n입출방법: "+pf+"\r\n금융기관명: "+bn+"\r\n유효시작일: "+adt+"\r\n인증상태: "+cfyn+"\r\n\r\n확인을 누르시면 저장됩니다.");
		if(reg_yn){
			document.bank.submit();
		}else{
			return;
		}
	}else{
		if(oldVal[0]!=document.bank.dGubun.value){
			alert("회비구분은 변경할 수 없습니다.");
			document.bank.dGubun.value=oldVal[0];
			cmem(oldVal[0]);
			document.bank.cMember.value=oldVal[1];
			return;
		}
		if(oldVal[1]!=document.bank.cMember.value){			
			document.bank.mChange.value='Y';			
		}else{
			document.bank.mChange.value='N';
		}
		if(oldVal[3]!=document.bank.iogGubun.value){
			alert("입출구분은 변경할 수 없습니다.");
			document.bank.iogGubun.value=oldVal[3];
			return;
		}
		document.bank.submit(); 
	}
}

function goDelete(){

	var selItemId = jQuery("#list").jqGrid('getGridParam','selrow') ;
	
	if(selItemId){

		var selData = jQuery("#list").jqGrid('getRowData',selItemId);
		
		if(document.bank.dues_h_seq.value == "" || document.bank.dues_b_seq.value == "") {
			alert("삭제할 행을 다시 선택해 주세요.");
			return;
		}
		
		if(selData.duesDelYn == "Y") {
			var reg_yn = confirm("선택한 회비내역을 삭제 하시겠습니까?\r\n(삭제후 원복 불가능)\r\n\r\n - 입출일자 : "+selData.pres_let_dt+"\r\n - 입출구분 : "+selData.iog_name+"\r\n - 회비구분 : "+selData.dg_name+"\r\n - 회원구분 : "+selData.cm_name
					+"\r\n - 입출금액 : "+selData.pres_money+"\r\n - 입출장소 : "+selData.rc_name+"\r\n - 입출방법 : "+selData.pf_name+"\r\n - 금융기관명 : "+selData.bank_name
					+"\r\n - 유효시작일 : "+selData.auth_start+"\r\n - 유효만료일 : "+selData.auth_end+"\r\n - 인증상태 : "+selData.cf_name+"\r\n - 인증일 : "+selData.conform_dt+"\r\n - 완납여부 : "+selData.if_name
					+"\r\n\r\n확인을 누르시면 삭제됩니다.");
			
			if(reg_yn) {
				document.bank.action="memberBasic.do?method=delBank";
				document.bank.submit();
			} else {
				return;
			}
		} else {
			alert("회비처리가 완료되어 삭제 불가능합니다.");
			return;
		}
		
	} else {
		alert("삭제할 행을 선택해 주세요.");
		return;
	}
	
}

function cmem2(mdg){
	var jc=eval(<%=jcode%>);
	var cm=document.getElementById("cMember");
	var opts=cm.options;
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
	var idx=opts.length;
	for(var i=0;i<jc.length;i++){
		if( jc[i].tempcd==mdg){			
			cm[idx++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
	}
}

/* 2022 */
function cmem(dGubun){
	var jc=eval(<%=jcode%>);
	var sumD=eval(<%=sumduesList%>);
	<%-- var subD=eval(<%=subduesList%>); --%>
	var bran='<%=result.get(0).get("code_bran") %>';
	var csub='<%=result.get(0).get("code_sub") %>';
	
	var cm=document.getElementById("cMember");
	var pm=document.getElementById("pMoney");
	var fm=document.getElementById("pFlag"); 	// 입출방법 추가 20150529
	var rc=document.getElementById("rc"); 	// 입출장소 추가 20240315
	var opts=cm.options;
	var mdg=dGubun;
	while(opts.length>0){
		/* opts[0]=null; */
		 opts[opts.length-1]=null; 
	}
	var idx=opts.length;
	for(var i=0;i<jc.length;i++){
		if( jc[i].tempcd==mdg){			
			cm[idx++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
	}
	
	if(mdg=='1'){
		for(var j=0;j<sumD.length;j++){
			if(sumD[j].dues_gubun==mdg&&sumD[j].code_bran==bran&&sumD[j].code_member=='85'){
				pm.value=sumD[j].sum_dues;
			}
		}
	}else if(mdg=='2'){
		for(var j=0;j<sumD.length;j++){
			if(sumD[j].dues_gubun==mdg&&sumD[j].code_bran==bran&&sumD[j].code_member=='94'){
				pm.value=sumD[j].sum_dues;
			}
		}
		//평생회비인 경우 입출장소 본회 1
		document.bank.rc.value = '1';
		
		//////////////////////////////////////////////CMS 추가////////////////////////////////////
	}else if(mdg=='4'){
		
		for(var j=0;j<sumD.length;j++){
			if(sumD[j].dues_gubun==mdg && sumD[j].code_bran==bran && sumD[j].code_member=='45'){
				pm.value=sumD[j].sum_dues;
			}
		}
		document.bank.pFlag.value = '15'; //입출방법 15 : CMS
	   /////////////////////////////////////////////////////////////////////////////////
	   
	}else if(mdg=='3'){
		for(var j=0;j<sumD.length;j++){
			if(sumD[j].dues_gubun==mdg && sumD[j].code_bran==bran && sumD[j].code_member=='71'){
				pm.value=sumD[j].sum_dues;
			}
		}
		//산하단체인 경우 입출장소 본회 1
		document.bank.rc.value = '1';
	}
}

/* 2022 */
function cpm(mem){
	var sumD=eval(<%=sumduesList%>);
	<%-- var subD=eval(<%=subduesList%>); --%>
	var bran='<%=result.get(0).get("code_bran") %>';
	var csub='<%=result.get(0).get("code_sub") %>';
	var mdg=document.getElementById("dGubun").value;
	var pm=document.getElementById("pMoney");

	/* if(mdg=='1' || mdg=='2' || mdg=='4'){
		for(var j=0;j<sumD.length;j++){
			if(sumD[j].dues_gubun==mdg&&sumD[j].code_bran==bran && sumD[j].code_member==mem.value){
				pm.value=sumD[j].sum_dues;
			}
		}
	}else if(mdg=='3'){
		for(var j=0;j<subD.length;j++){
			if(subD[j].dues_gubun==mdg && subD[j].code_bran==bran && subD[j].code_sub==csub){
				pm.value=subD[j].sub_dues;
			}
		}
	} */
	
	for(var j=0;j<sumD.length;j++){
		if(sumD[j].dues_gubun==mdg&&sumD[j].code_bran==bran && sumD[j].code_member==mem.value){
			pm.value=sumD[j].sum_dues;
		}
	}
}

function crc(mem){
	var mdg=document.getElementById("dGubun").value;
	
	if(mdg=='2' || mdg=='3'){
		//평생회비 / 산하단체인 경우 입출장소 본회 1
		document.bank.rc.value = '1';
	}
}

function ioChk(iov){
	var jdusH=eval(<%=jdusH%>);
	if(iov.value=='1'){
		var cm=document.getElementById("cf_yn");
		var opts=cm.options;
		while(opts.length>0){
			opts[opts.length-1]=null;
		}
		cm[0]=new Option('미인증','1');
		cm[1]=new Option('선인증','2');
		cm[2]=new Option('인증','3');
	}else if(iov.value=='2'){
		var dGubun=document.getElementById("dGubun").value;
		for(var j=0;j<jdusH.length;j++){
			if(jdusH[j].dues_gubun==dGubun){
				if(jdusH[j].regi_dt.length>1){
					alert("분배처리가 끝나서 출금을 할 수 없습니다.");
					iov.value='1';
				}else{
					var cm=document.getElementById("cf_yn");
					var opts=cm.options;
					while(opts.length>0){
						opts[opts.length-1]=null;
					}
					cm[0]=new Option('미인증','1');
					cm[1]=new Option('인증','3');
				}
			}
		}		
	}
}

function ccancel(){
	var dt1='<%=date%>';
	var dt2=document.bank.presDt.value;
	
	dt1=dt1.substring(0,4);
	dt2=dt2.substring(0,4);

	if(document.bank.gubun.value!='update'){
		alert("인증 취소할 항목을 선택해 주십시오.");
		return;
	}
	if((document.bank.cfyncfyncfyn.value!='3')||(document.bank.ifififififif.value!='Y')){
		alert("인증, 완납인 항목만 인증 취소할 수 있습니다.");
		return;
	}
	/*
	if(dt1!=dt2){
		alert("올해 인증한 항목이 아닌 경우 인증 취소 할 수 없습니다.");
		return;
	}
	*/
	if(document.bank.tmpDgubun.value==2){
		alert("평생회비 인증 취소는 시스템 운영자에게 연락바랍니다.");
		return;
	}
	document.bank.action="memberBasic.do?method=ccancel";
	document.bank.submit();
	
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
<form name="bank" method="post" action="memberBasic.do?method=mBank"> 
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
<input type="hidden" name="mCode_pers" value="<%=result.get(0).get("code_pers") %>">
<input type="hidden" name="code_sub" value="<%=result.get(0).get("code_sub") %>">
<input type="hidden" name="code_bran" value="<%=result.get(0).get("code_bran") %>" />
<input type="hidden" name="gubun" value="insert" />
<input type="hidden" name="dues_h_seq" value="">
<input type="hidden" name="dues_b_seq" value="" />
<input type="hidden" name="mStart" value="no"/>
<input type="hidden" name="tmpDgubun" value=""/>
<input type="hidden" name="mChange" value="N"/>
<input type="hidden" name="ifififififif" value=""/>
<input type="hidden" name="cfyncfyncfyn" value=""/>

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
            <!-- 근무처명, 주소을 추가하려면 이쪽 주석을 풀면된다.--> 
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
	        <li><a href="#" class="overMenu"><strong><span>회비입출</span></strong></a></li>
	        <li><a href="memberBasic.do?method=dues&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>회비처리</span></strong></a></li>
	        <li><a href="memberBasic.do?method=edu&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>교육</span></strong></a></li>
	        <li><a href="memberBasic.do?method=certifi&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>자격증</span></strong></a></li>
	        <li><a href="memberBasic.do?method=donation&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>기부&frasl;기금</span></strong></a></li>
			<li><a href="memberBasic.do?method=memo&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>메모</span></strong></a></li>			
          </ul>
        </div>
		<div class="mTabmenu_01" id="tab01" style="display:show">
        <table class="mytable_03" cellspacing="0" summary="회비입출">
          <caption>회비입출</caption>
<%--           	 <tr>
          	 	<td class="nobg">현 회비구분</td>
          	 	<td class="nobg1"><%=dusH.get(0).get("dgname") %></td>
          	 	<td class="nobg3">현 회비</td>
          	 	<td class="nobg1"><%=(int)Float.parseFloat(dusH.get(0).get("mem_dues").toString()) %></td>
          	 	<td class="nobg3">현 유효기간</td>
          	 	<td class="nobg1" colspan="5"><%=dusH.get(0).get("auth_start") %>~<%=dusH.get(0).get("auth_end") %></td>
          	 </tr> --%>
             <tr>
               <td class="nobg">입출일자</td>
               <td class="nobg3">회비구분</td>
               <td class="nobg3">입출구분</td>	
               <td class="nobg3">회원구분</td>		   
			   <td class="nobg3">입출금액</td>
			   <td class="nobg3">입출장소</td>
			   <td class="nobg3">입출방법</td>
               <td class="nobg3">금융기관명</td>
               <!-- <td class="nobg3">만료일</td> -->			   
			   <td class="nobg3">유효시작일</td>	   			  
               <td class="nobg3">인증상태</td>			   			  
			 </tr>             
			 <tr>
               <td class="alt2">
               		<input type="text" name="presDt" id="datepicker" class="input" style=padding-top:3px  style=padding-bottom:3px  value="<%=date %>" size="12" align="center" />
               </td>
               
               <td>
               <!-- 2022 -->
               		<select name="dGubun" id="dGubun" onchange="javascript:cmem(this.value);" class="" >					
	                <% String detCode,detCName="";
	                	if(result.get(0).get("big_name").toString().equals("영양교사")||result.get(0).get("big_name").toString().equals("학교영양사")){
	                	/* if(result.get(0).get("code_sub").toString().equals("01")||result.get(0).get("code_sub").toString().equals("02")||result.get(0).get("code_sub").toString().equals("03")||result.get(0).get("code_sub").toString().equals("04")){ */ 
	                		
		                	for(int i=0;i<code.size();i++){
		                		if("038".equals(code.get(i).get("groupcode").toString())){
		                			detCode=code.get(i).get("detcode").toString();
		                			detCName=code.get(i).get("detcodename").toString();
		                			out.println("<option value="+detCode+">"+detCName+"</option>");	
		                		}
		                	}
	                	}else{
	                		for(int i=0;i<code.size();i++){
		                		if("038".equals(code.get(i).get("groupcode").toString())){
		                			detCode=code.get(i).get("detcode").toString();
		                			detCName=code.get(i).get("detcodename").toString();
		                			if(!detCode.equals("3")){
		                				out.println("<option value="+detCode+">"+detCName+"</option>");
		                			}
		                		}
		                	}
	                	}
	                %>
	                </select>
				</td>
				<td>
               		<select name="iogGubun" class="" onchange="ioChk(this);">
	                <% 	                	
	                	for(int i=0;i<code.size();i++){
	                		if("015".equals(code.get(i).get("groupcode").toString())){
	                			detCode=code.get(i).get("detcode").toString();
	                			detCName=code.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");	
	                		}
	                	}
	                %>
	                </select>
               	</td>
               	<!-- 2022 -->
				<td>
               		<select name="cMember"  id="cMember" onchange="javascript:cpm(this);" class="" >
	                 <% ////////////////////////////연회비 및 교육생만 최초 조회 되도록/////////////////////////////
	                	for(int i=0;i<code2.size();i++){
	                		if("006".equals(code2.get(i).get("groupcode").toString())&&!"2".equals(code2.get(i).get("tempcd").toString())&&!"3".equals(code2.get(i).get("tempcd").toString())&&!"4".equals(code2.get(i).get("tempcd").toString())){
	                			detCode=code2.get(i).get("detcode").toString();
	                			detCName=code2.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");	
	                		}
	                	}
                   ///////////////////////////////////////////////////////////////////
	               %> 
	                </select>
	           	</td>
				<td><input type="text" name="pMoney" id="pMoney"size="10"  value="<%=request.getAttribute("pm")%>"class="" /></td>
				<td>
			   		<select name="rc" onchange="javascript:crc(this);" class="" >
	                <% 
	                	for(int i=0;i<code.size();i++){
	                		if("013".equals(code.get(i).get("groupcode").toString())){
	                			detCode=code.get(i).get("detcode").toString();
	                			detCName=code.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");	
	                		}
	                	}
	                %>
	                </select>
			   </td>
			   <td>
               		<select name="pFlag" class="" >
	                <% 
	                	for(int i=0;i<code.size();i++){
	                		if("014".equals(code.get(i).get("groupcode").toString())){
	                			detCode=code.get(i).get("detcode").toString();
	                			detCName=code.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");	
	                		}
	                	}
	                %>
	                </select>
               </td>
               <td><input type="text" name="bName" size="10" class="" ></td>
               <%-- <td><% if(dusH.size()>0) out.print(dusH.get(0).get("auth_end")); %></td> --%>
               <td>
					<input type="text" name="authDt" id="datepicker1" class="input" style=padding-top:3px  style=padding-bottom:3px  value="<%=date %>" size="12" align="center" />
				</td>	            
			   <td>
			   		<select name="cf_yn" id="cf_yn" class="" >
			   			<% 
	                	for(int i=0;i<code.size();i++){
	                		if("052".equals(code.get(i).get("groupcode").toString())){
	                			detCode=code.get(i).get("detcode").toString();
	                			detCName=code.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");	
	                		}
	                	}
	                %>
			   		</select>
			   </td>               
             </tr>			 
        </table>
        
        <p style="float:left;padding-top:10px;">
            <%-- 2024.06.18 회원관리자 - kdamembership 추가   --%>
            <%-- dongbucni 추가   --%>
            <%-- 2025.08.05 박세은 separk153, 설아영 say1025, 김고은 goeun822 만 삭제 버튼 추가   --%>
			<%if(G_ID.equals("dongbucni") || G_ID.equals("kdamembership") || G_ID.equals("separk153") || G_ID.equals("say1025") || G_ID.equals("goeun822") ){ %>
			<a href="javascript:goDelete();"><img src="images/icon_delete.gif" alt="삭제" /></a> 
			<%} %>
		</p>
		
        <p class="btnSave">
			<a href="javascript:reg_reset();"><img src="images/icon_new.gif" alt="신규" /></a>
			<a href="javascript:reg();"><img src="images/icon_save.gif" alt="저장" /></a>
			<%-- 2024.06.18 회원관리자 - kdamembership 추가   --%>
			<%-- dongbucni 추가   --%>
			<%-- 2025.08.05 박세은 separk153, 설아영 say1025, 김고은 goeun822 만 삭제 버튼 추가   --%>
			<%if(G_ID.equals("dongbucni") || G_ID.equals("kdamembership") || G_ID.equals("separk153") || G_ID.equals("say1025") || G_ID.equals("goeun822") ){ %>
			<a href="javascript:ccancel();"><img src="images/icon_s8.gif" alt="인증취소" /></a>
			<%} %>
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