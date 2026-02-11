<%@page import="org.apache.struts.action.Action"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="com.ant.common.properties.AntData"%>
<% 
		String logid="";
		if(session.getAttribute("G_NAME")!=null){
			logid=session.getAttribute("G_NAME").toString();
		}
		String errMsg="";
		if(request.getAttribute("errMsg")!=null){
			errMsg=URLDecoder.decode(request.getAttribute("errMsg").toString(),"UTF-8"); 
		}
		request.setCharacterEncoding("utf-8"); 
	  	List<Map> result=(List<Map>)request.getAttribute("result");
	  	List<Map> code=(List<Map>)request.getAttribute("code");
	  	session.setAttribute("code", code);
	  	List<Map> subcom=(List<Map>)request.getAttribute("subcom");
	  	List<Map> g_certifi=(List<Map>)request.getAttribute("g_certifi");
	  	String pCode=StringUtil.nullToStr((String)request.getAttribute("pCode"), request.getParameter("pCode"));
	  	JSONArray jcode=(JSONArray)request.getAttribute("jcode");
	  	String url="memberBasic.do?method=mDetail&pCode="+pCode;
	  	
	  	
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
.readOnly1 {background-color:#f2f2f2;}
</style>

<script type="text/javascript">
document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};
function init(){
	var logid="<%=logid%>";
	var errMsg="<%=errMsg%>";
	if(errMsg.length>5){
		alert(errMsg);
		return;
	}
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		opener.document.location.href="login.do?method=login";
		self.close();
	}
}

$(document).ready(function(){
jQuery("#list").jqGrid({
      url: "<%=url%>",
      datatype: "json",
      mtype: 'POST',
      height:'250',
      width:'1097',
      colNames: ['년월일','수정차수','작성자','코드','이름','아이디',
//                  '주민번호', // JUMIN_DEL
                 '면허번호','회원구분','소속지부','소속분과','소속분회','우편번호','주소','상세주소','수신처','전화번호','자격증현환','휴대폰','이메일','학교','학력','학위','경력','결혼','종교','산하단체','발송호칭','추천인','추천인근무처','추천인핸드폰','협회직급','성별','자격증발급일','주소구분','이메일구분','회원상태'],
      colModel: [
			{name:'up_dt',					index:'up_dt', 				width:40, 	sortable:false},
			{name:'up_dt_seq',			index:'up_dt', 				width:40, 	sortable:false},
   			{name:'register',				index:'register', 			width:100,	sortable:false},
   			{name:'code_pers',			index:'code_pers', 		width:40, 	sortable:false,	hidden:true},
   			{name:'pers_name',			index:'pers_name', 		width:40, 	sortable:false,	hidden:true},
   			{name:'id',						index:'id', 					width:40, 	sortable:false,	hidden:true},   			
//    			{name:'pers_no',				index:'pers_no', 			width:40, 	sortable:false,	hidden:true}, // JUMIN_DEL
   			{name:'lic_no',					index:'lic_no', 				width:40, 	sortable:false,	hidden:true},
   			{name:'code_member',		index:'code_member', 	width:40, 	sortable:false,	hidden:true},
   			{name:'code_bran',			index:'code_bran', 		width:40, 	sortable:false,	hidden:true},
   			{name:'code_big',				index:'code_big', 			width:40, 	sortable:false,	hidden:true},
   			{name:'code_sect',			index:'code_sect', 		width:40, 	sortable:false,	hidden:true},
   			{name:'code_post',			index:'code_post', 		width:40, 	sortable:false,	hidden:true},
   			{name:'pers_add',				index:'pers_add', 			width:40, 	sortable:false,	hidden:true},
   			{name:'pers_add_detail',	index:'pers_add_detail', 	width:40, 	sortable:false,	hidden:true},
   			{name:'code_place',			index:'code_place', 		width:40, 	sortable:false,	hidden:true},
   			{name:'pers_tel',				index:'pers_tel', 			width:40, 	sortable:false,	hidden:true},
   			{name:'certifi_view',			index:'certifi_view', 		width:40, 	sortable:false,	hidden:true},
   			{name:'pers_hp',				index:'pers_hp', 			width:40, 	sortable:false,	hidden:true},
   			{name:'email',					index:'email', 				width:40, 	sortable:false,	hidden:true},
   			{name:'code_school',			index:'code_school', 		width:40, 	sortable:false,	hidden:true},
   			{name:'code_scholar',		index:'code_scholar', 	width:40, 	sortable:false,	hidden:true},
   			{name:'code_univer',			index:'code_univer', 		width:40, 	sortable:false,	hidden:true},
   			{name:'pers_career',			index:'pers_career', 		width:40, 	sortable:false,	hidden:true},
   			{name:'code_marry',			index:'code_marry', 		width:40, 	sortable:false,	hidden:true},
   			{name:'code_religion',		index:'code_religion', 	width:40, 	sortable:false,	hidden:true},
   			{name:'code_sub',				index:'code_sub', 			width:40, 	sortable:false,	hidden:true},
   			{name:'code_call',				index:'code_call', 			width:40, 	sortable:false,	hidden:true},
   			{name:'recommender',		index:'recommender', 	width:40, 	sortable:false,	hidden:true},
   			{name:'recm_division',		index:'recm_division', 	width:40, 	sortable:false,	hidden:true},
   			{name:'recm_hp',				index:'recm_hp', 			width:40, 	sortable:false,	hidden:true},
   			{name:'kda_level',				index:'kda_level', 			width:40, 	sortable:false,	hidden:true},
   			{name:'code_sex',				index:'code_sex', 			width:40, 	sortable:false,	hidden:true},
   			{name:'lic_print_dt',			index:'lic_print_dt',		width:40, 	sortable:false,	hidden:true},
   			{name:'code_add_gubun',	index:'code_add_gubun',			width:40, 	sortable:false,	hidden:true},
   			{name:'code_email_comp',	index:'code_email_comp', 			width:40, 	sortable:false,	hidden:true},
   			{name:'pers_state',			index:'pers_state',			width:40, 	sortable:false,	hidden:true}
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
							document.mem.m_name.value=ret.pers_name;
							document.mem.m_id.value=ret.id;
// 							JUMIN_DEL
// 							document.mem.mId1.value=ret.pers_no.substring(0,6);
// 							document.mem.mId2.value=ret.pers_no.substring(6,13);
							if(ret.code_sex=='1'){
								document.getElementById("male").checked='true';
							}else if(ret.code_sex=='2'){
								document.getElementById("female").checked='true';
							}
							document.mem.m_licno.value=ret.lic_no;
							document.mem.mCode.value=ret.code_member;
							document.mem.mBran.value=ret.code_bran;
							document.mem.mBig.value=ret.code_big;
							document.mem.mSect.value=ret.code_sect;
							document.mem.m_post.value=ret.code_post.substring(0,3)+"-"+ret.code_post.substring(3,6);
							document.mem.m_address.value=ret.pers_add;
							document.mem.d_address.value=ret.pers_add_detail;
							if(ret.code_place=='1'){
								document.getElementById("h").checked=true;
							}else if(ret.code_place=='2'){
								document.getElementById("f").checked=true;
							}else if(ret.code_place=='3'){
								document.getElementById("s").checked=true;
							}
							document.mem.m_tel.value=ret.pers_tel;
							document.mem.m_hp.value=ret.pers_hp;
							document.mem.m_email.value=ret.email;	
							if(ret.certifi_view.length==0){
								ret.certifi_view='00000';
							}
							if(ret.certifi_view.substring(0,1)=='1'){
								document.getElementById("oo").checked=true;
							}else if(ret.certifi_view.substring(0,1)=='0'){
								document.getElementById("oo").checked=false;
							}
							if(ret.certifi_view.substring(1,2)=='1'){
								document.getElementById("ee").checked=true;
							}else if(ret.certifi_view.substring(1,2)=='0'){
								document.getElementById("ee").checked=false;
							}
							if(ret.certifi_view.substring(2,3)=='1'){
								document.getElementById("ww").checked=true;
							}else if(ret.certifi_view.substring(2,3)=='0'){
								document.getElementById("ww").checked=false;
							}
							if(ret.certifi_view.substring(3,4)=='1'){
								document.getElementById("kk").checked=true;
							}else if(ret.certifi_view.substring(3,4)=='0'){
								document.getElementById("kk").checked=false;
							}
							if(ret.certifi_view.substring(4,5)=='1'){
								document.getElementById("ss").checked=true;
							}else if(ret.certifi_view.substring(4,5)=='0'){
								document.getElementById("ss").checked=false;
							}
							document.mem.mSchool.value=ret.code_school;
							document.mem.mScholar.value=ret.code_scholar;
							document.mem.mUniver.value=ret.code_univer;
							document.mem.mYear.value=ret.pers_career.substring(0,2);
							document.mem.mMonth.value=ret.pers_career.substring(2,4);
							if(ret.code_marry=='1'){
								document.getElementById("l").checked=true;
							}else if(ret.code_marry=='0'){
								document.getElementById("e").checked=true;
							}
							document.mem.mReligion.value=ret.code_religion;
							document.mem.mSub.value=ret.code_sub;
							document.mem.mCall.value=ret.code_call;
							document.mem.mRecm.value=ret.recommender;
							document.mem.mRdivision.value=ret.recm_division;
							document.mem.mReHp.value=ret.recm_hp;
							document.mem.mLevel.value=ret.kda_level;
							if(ret.code_add_gubun=='1'){
								document.getElementById("a_old").checked=true;
							}else if(ret.code_add_gubun!='1'){
								document.getElementById("a_new").checked=true;
							}
							document.mem.mEcomp.value=ret.code_email_comp;
							document.mem.mState.value=ret.pers_state;
							document.mem.mSex.value=ret.code_sex;
						}
						else {}
					}
				}
      
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

// JUMIN_DEL
// function juminNo(cellvalue, options, rowObject ){
// 	var jumin=cellvalue.substring(0,6)+" - *******";
// 	return jumin;
// }

function reg_mod(){
	var oo='0';
	var ee='0';
	var ww='0';
	var kk='0';
	var ss='0';
	if(document.getElementById("oo").checked){
		oo='1';
	}
	if(document.getElementById("ee").checked){
		ee='1';
	}
	if(document.getElementById("ww").checked){
		ww='1';
	}
	if(document.getElementById("kk").checked){
		kk='1';
	}
	if(document.getElementById("ss").checked){
		ss='1';
	}
	document.mem.mCertiview.value=oo+ee+ww+kk+ss;
	
	
	if(document.mem.m_name.value.length==0){
		alert("이름을 입력해 주십시오.");
		return;
	}
	if(document.getElementById("mBig").value!=document.getElementById("mGreat").value){
		alert("소속분과와 근무처대분류가 동일하지 않습니다. \n확인후 다시 저장해주십시오.");
		return;
	}
	if(document.mem.d_address.value.length==0){
		alert("세부 주소를 입력해 주십시오.");
		return;
	}
	if(document.mem.mMonth.value.length!=2||document.mem.mYear.value.length!=2){
		alert("경력을 입력해 주십시오.\r\n정확하지 않을땐 '00년 00월'로 입력해주십시오. ");
		return;
	}
	alert("저장 처리 시 시간이 10초 이상 소요될 수 있습니다.\n'확인' 버튼 클릭 후 잠시만 기다려 주십시오.")
	document.mem.action="memberBasic.do?method=mModify";
	document.mem.submit();
}
function pass_mod(){
	if(confirm("패스워드를 초기화 하시겠습니까?")){
		if(document.mem.m_hp.value.length>=9){
			document.mem.action="memberBasic.do?method=uPasswd&pw="+document.mem.m_hp.value+"&id="+document.mem.m_id.value;
			document.mem.submit();
		}else{
			alert("휴대전화번호를 정확하게 입력해주십시오.");
			document.mem.m_hp.focus();
			return;
		}
	}else{
		return;
	}
}
function id_mod(){
<%-- 	var pers_name = encodeURIComponent("<%=result.get(0).get("pers_name") %>"); --%>
	var pers_name = "<%=result.get(0).get("pers_name") %>";
	var lic_no = "<%=result.get(0).get("lic_no") %>";
	var pers_birth = "<%=result.get(0).get("pers_birth") %>";
	
	
	//alert(pers_birth);
	/* window.open("http://210.127.56.232:70/tempAdmin/SetID.asp?pers_name="+pers_name+"&lic_no="+lic_no+"&use_lic_no=Y&adm=Y","idpop","width=850,height=200, scrollbars=yes, resizable=yes"); */
	window.open("memberBasic.do?method=SetID&pers_name="+pers_name+"&lic_no="+lic_no+"&pers_birth="+pers_birth+"&use_lic_no=Y&adm=Y","idpop","width=1140,height=200, scrollbars=yes, resizable=yes");
}
function cSect(){
	var jc=eval(<%=jcode%>);
	var msect=document.getElementById("mSect");
	var opts=msect.options;
	var mbig=document.getElementById("mBig").value;
	var mbran=document.getElementById("mBran").value;
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
	var idx=opts.length;
	for(var i=0;i<jc.length;i++){
		if(jc[i].groupcode=='009' && jc[i].detcode.substring(0,2)==mbran&& jc[i].detcode.substring(2,5)==mbig){
			msect[idx++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
	}
	if(msect.length==0){
		msect[idx]=new Option("없음","0000000");
	}
}

function cSect2(){
	var jc=eval(<%=jcode%>);
	var msect=document.getElementById("mSect");
	var mgreat=document.getElementById("mGreat");
	var msmall=document.getElementById("mSmall");
	
	var opts=msect.options;
	var optg=mgreat.options;
	var optsm=msmall.options;
	
	var mbig=document.getElementById("mBig").value;
	var mbran=document.getElementById("mBran").value;
	
	document.getElementById("mPart").value=mbig.substring(0,1);
	
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
	while(optg.length>0){
		optg[optg.length-1]=null;
	}
	while(optsm.length>0){
		optsm[optsm.length-1]=null;
	}
		
	var idx=opts.length;
	var idxg=optg.length;
	var idxsm=optsm.length;
	
	for(var i=0;i<jc.length;i++){
		if(jc[i].groupcode=='009' && jc[i].detcode.substring(0,2)==mbran&& jc[i].detcode.substring(2,5)==mbig){
			msect[idx++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
		if(jc[i].groupcode=='004' && jc[i].detcode.substring(0,1)==mbig.substring(0,1)){
			mgreat[idxg++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
		//if(jc[i].groupcode=='005' && jc[i].detcode.substring(0,1)==mbig.substring(0,1)&& jc[i].detcode.substring(1,3)=='01'){
		if(jc[i].groupcode=='005' && jc[i].detcode.substring(0,3)==mbig){
			msmall[idxsm++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
	}
	if(msect.length==0){
		msect[idx]=new Option("없음","0000000");
	}
	
	mgreat.value=mbig;
}

function cGreat(){
	var jc=eval(<%=jcode%>);
	var mgreat=document.getElementById("mGreat");
	var msmall=document.getElementById("mSmall");
	var optg=mgreat.options;
	var opts=msmall.options;
	var mpart=document.getElementById("mPart").value;

	while(optg.length>0){
		optg[optg.length-1]=null;
	}
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
	
	var idx=opts.length;
	var idx2=optg.length;
	for(var i=0;i<jc.length;i++){
		if(jc[i].groupcode=='004' && jc[i].detcode.substring(0,1)==mpart){
			mgreat[idx2++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
		if(jc[i].groupcode=='005' && jc[i].detcode.substring(0,1)==mpart&& jc[i].detcode.substring(1,3)=='01'){
			msmall[idx++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
	}
	if(msmall.length==0){
		msmall[idx]=new Option("없음","00000");
	}
	document.getElementById("mBig").value=mgreat.value;
}

function cSmall(){
	var jc=eval(<%=jcode%>);
	var msmall=document.getElementById("mSmall");
	var msect=document.getElementById("mSect");
	var optsm=msmall.options;
	var opts=msect.options;
	var mgreat=document.getElementById("mGreat").value;
	var mbran=document.getElementById("mBran").value;
	document.getElementById("mBig").value=mgreat;
	
	while(optsm.length>0){
		optsm[optsm.length-1]=null;
	}
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
		
	var idxsm=optsm.length;
	var idxs=opts.length;
	
	for(var i=0;i<jc.length;i++){
		if(jc[i].groupcode=='005' && jc[i].detcode.substring(0,3)==mgreat){
			msmall[idxsm++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
		if(jc[i].groupcode=='009' && jc[i].detcode.substring(0,2)==mbran&& jc[i].detcode.substring(2,5)==mgreat){
			msect[idxs++]=new Option(jc[i].detcodename,jc[i].detcode);
		}
	}
	if(msect.length==0){
		msect[idxs]=new Option("없음","0000000");
	}
}

// JUMIN_DEL
// function checkJumin(){
// 	//주민 번호 유호성 검사
// 	var jmca = [2,3,4,5,6,7,8,9,2,3,4,5];
// 	var jmcs = document.getElementById("mId1").value+document.getElementById("mId2").value;
// 	if (jmcs.length == 0)
// 	{
// 		alert('주민등록번호를 입력하세요.');
// 		document.getElementById("mId1").focus();
// 		return;
// 	}else if(jmcs.length == 13){	
// 		var jmc = 0;
// 		for(var i=0; i<jmca.length; i++) {
// 		  jmcsc = jmcs.substr(i,1);
// 		  jmc = jmc + jmca[i]*jmcsc;
// 		}
// 		var jmch = String(11-(jmc%11));
// 		jmch = jmch.substr(jmch.length-1,2);
// 		if(jmch != jmcs.substr(12,1)) {
// 		  alert('존재하지 않는 주민번호입니다');
// 		  document.getElementById("mId1").value="";
// 		  document.getElementById("mId2").value="";
// 		  document.getElementById("mId1").focus();
// 		  return;
// 		}
// 		alert('유효한 주민등록번호입니다.'); 
// 	}else{
// 		alert('주민등록번호는 13자리여야 합니다.');
// 		document.getElementById("mId1").value="";
// 		document.getElementById("mId2").value="";
// 		document.getElementById("mId1").focus();
// 		return;
// 	}
// }

function postSearch(){
	//window.open('memberBasic.do?method=postSearch&sel=N&keyword=',"postnum","scrollbar=no,resizable=no,toolbar=no,width=675,height=500,left=20,top=29,status=no");
	//window.open('memberBasic.do?method=postSearch&sel=1',"postnum","scrollbars=yes,menubar=no,resizable=no,toolbar=no,width=440,height=400,status=no");
	var pop = window.open("/post5.do","jusopop","width=570,height=420, scrollbars=yes, resizable=yes");

}

//자택우편번호콜백
function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn){

	document.mem.m_address.value = roadAddrPart1;
		document.mem.d_address.value = addrDetail;
		document.mem.m_post.value = zipNo;
		
}






function sLicno(){
	var url='memberBasic.do?method=sLicnoCnt&lic_no='+document.mem.m_licno.value+'&code_pers='+document.mem.mCode_pers.value;
	window.open(url,"lic_no","scrollbar=no,resizable=no,toolbar=no,width=300,height=150,left=20,top=29,status=no");
}

function ban(val){
	if(val.tagName=="INPUT"){
		if(event.keyCode==8){
			alert("수정할 수 없는 항목입니다.");
			event.keyCode=0;
			event.cancelBubble=true;
			event.returnValue=false;
		}
	}
}
function doNotReload(val){
	if(val.tagName=="BODY"){
		if((event.ctrlKey==true&&(event.keyCode==78||event.keyCode==82))||event.keyCode==116||event.keyCode==8){
			event.keyCode=0;
			event.cancelBubble=true;
			event.returnValue=false;
		}
	}
}

/* 쪽지 발송 */
function notePad(){		
 	var dm_pers_code="<%=pCode%>";
 	var pers_name="<%=result.get(0).get("pers_name") %>";
 	
	pers_name = escape(encodeURIComponent(pers_name)); 
	
	var url="memberBasic.do?method=dmsendnotePad&dm_pers_code="+dm_pers_code+"&pers_name="+pers_name;
	window.open(url,"notePad","width=370, height=500, left=280, top=80");	
}

/* 문자 전송 */
function umsData(){	
	var dm_pers_code="<%=pCode%>";
	var pers_hp=document.getElementById("m_hp").value;
	var pers_name=document.getElementById("m_name").value;
	
	if(pers_hp.length==0){
		alert("휴대폰 번호를 입력해 주십시오.");
		document.mem.m_hp.focus();
		return;
	}else if(pers_hp.length<=9){
		alert("휴대폰 번호를 정확하게 입력해 주십시오.");
		document.mem.m_hp.focus();
		return;
	}
	
	var url="memberBasic.do?method=sendumsData&dm_pers_code="+dm_pers_code+"&pers_hp="+pers_hp+"&pers_name="+escape(encodeURIComponent(pers_name));
	window.open(url,"umsData","width=370, height=535, left=280, top=80");

}

//메일 발송
function sendEmail(form) {
	var jc=eval(<%=jcode%>);
 	var tmp=document.mem.mEcomp.value;
	var eId=document.mem.m_email.value;
	var nm=document.mem.m_name.value;
	var code_pers=document.mem.mCode_pers.value;
	var param="";
	if(eId.length==0){
		alert("이메일을 입력해 주십시오.");
		document.mem.m_email.focus();
		return;
	}else if(tmp=='01'){
// 		param=eId;
		param= escape(encodeURIComponent(nm));
	}else{
		for(var i=0;i<jc.length;i++){
			if(jc[i].groupcode=='025' && jc[i].detcode==tmp){
// 				param=eId+"@"+jc[i].detcodename;
				param= escape(encodeURIComponent(nm));
			}
		}
	}
	param+="&paramfrom=mBasic&addr_infos="+code_pers;
	window.open('memberBasic.do?method=eMail&toAddr='+param,"email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
}

/* 영수증 출력 */
function printReceipt(){		
 	var pt_pers_code="<%=pCode%>";	
	window.open("<%=AntData.DOMAIN%>/doc_form/receipt_print2.jsp?doc_seq="+pt_pers_code);	
}

</script>
</head>
<body >
<form name="mem" method="post" action=""> 
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

<input type="hidden" name="mCode_pers" value="<%=result.get(0).get("code_pers") %>"/>
<input type="hidden" name="comp_seq" value="<%=result.get(0).get("comp_seq") %>"/> 
<input type="hidden" name="register" value="<%=session.getAttribute("G_NAME") %>"/>
<input type="hidden" id="pgu" value="basic"/>
<input type="hidden" name="mCode" value="<%=result.get(0).get("code_member") %>">
	<div id="M_contents">
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="회원검색">
          <caption>회원검색</caption>          
			<tbody>
            <tr>           
              <td class="mtbl">이름</td>
              <td class="mtbl1"><input type="text" name="m_name"  id="m_name"  size="25" value="<%=result.get(0).get("pers_name") %>"/>
			    <img src="images/m_serch_icon01_1.gif" alt="찾기" />
			  </td>
              <td class="mtbl">아이디</td>
              
              <td class="mtbl1">
              	<input type="text" class="readOnly1" name="m_id"  id="m_id" size="12"  value="<%=result.get(0).get("id") %>" readonly onkeydown="ban(this);" />
              	<a href="javascript:id_mod();"><img src="images/icon_idchange.gif" alt="아이디변경" /></a>
<!--               	<a href="javascript:id_mod();">아이디변경</a> -->
			  </td>
			  
              <td class="mtbl">패스워드</td>
              <td class="mtbl1">
              	<input type="password" name="m_pw"  size="12" value="**********" readonly class="readOnly1" />
                <a href="javascript:pass_mod();"><img src="images/icon_reset.gif" alt="저장" /></a>
              </td>
            </tr>                  
            <tr>  
<!--             JUMIN_DEL          -->
<!--               <td>주민번호</td> -->
				<td>생년월일</td>
				<td>
				<input type="text" name="mBirth" id="mBirth" value="<%=result.get(0).get("pers_birth").toString()%>" />
				</td>

              <td>성별</td>
              <td>
<%--               	<input type="text" name="mId1" id="mId1"  size="3" maxlength="6" value="<%=result.get(0).get("pers_no").toString().substring(0,6)%>" onkeyup='if(this.value.length==6){document.getElementById("mId2").focus();}'/>-<input type="password" name="mId2" id="mId2"  size="2" value="<%=result.get(0).get("pers_no").toString().substring(6,13)%>"/> --%>
<!-- 			    <a href="javascript:checkJumin();"><img src="images/m_serch_icon01.gif" alt="유효성체크" /></a> -->
				<input type="radio" name="mSex" id="male" value="1"  <%if("1".equals(result.get(0).get("code_sex").toString())) out.print("checked"); %> />
			   <label for="a_male">남</label>
			   <input type="radio" name="mSex" id="female" value="2" <%if("2".equals(result.get(0).get("code_sex").toString())) out.print("checked"); %> />
			   <label for="a_female">여</label>
			  </td>
			  
              <td>면허번호</td>
              <td><input type="text" name="m_licno" id="mLid"  size="3"  value="<%=result.get(0).get("lic_no") %>"/>
			    <a href="javascript:sLicno();"><img src="images/m_serch_icon01.gif" alt="찾기" /></a>
			    발급일자:<%=result.get(0).get("lic_print_dt") %> 
			  </td>
			  
			  
            </tr>
            <!-- 근무처명, 주소을 추가하려면 이쪽 주석을 풀면된다.--> 
            <tr>
              <td>회원구분</td>
              <td><%=result.get(0).get("c_member_name").toString() %>
			  </td>
            	<td>근무처명</td>
            	<td><%=result.get(0).get("company_name") %></td>
            	<td>근무처 주소</td>
            	<td colspan='2'><%=result.get(0).get("company_add") %></td>
            </tr>             
            <tr>           
              <td>소속지부</td>
              <td><select name="mBran" id="mBran" onchange="javascript:cSect();">
                <% 
                    String detCode,detCName,detCode_c=null;
                	for(int i=0;i<code.size();i++){
                		if("007".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_bran").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	} 	
                %>   
                </select>
				<img src="images/m_serch_icon01_1.gif" alt="찾기" />
			  </td>
              <td>소속분과</td>
              <td><select name="mBig" id="mBig" onchange="javascript:cSect2();">
                <% 
                	int idx1=0;
                	for(int i=0;i<code.size();i++){
                		if("004".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_big").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>   
                </select>
				<img src="images/m_serch_icon01_1.gif" alt="찾기" />
              </td>
              <td>소속분회</td>
              <td>
              	<select name="mSect" id="mSect">
                	<% 
                	for(int i=0;i<code.size();i++){
                		if("009".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_sect").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}	
                		}
                	}
                %>
                </select>
				<img src="images/m_serch_icon01_1.gif" alt="찾기" />
			  </td>
              
            </tr>
            <tr>           
              <td>근무처구분</td>
              <td><select name="mPart" id="mPart" onchange="javascript:cGreat();">
                <% 
                	for(int i=0;i<code.size();i++){
                		if("003".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_part").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>   
                </select>
              </td>
              <td>근무처대분류
			  </td>
              <td><select name="mGreat" id="mGreat" onchange="javascript:cSmall();">
                <% 
                	for(int i=0;i<code.size();i++){
                		if("004".equals(code.get(i).get("groupcode").toString())){
                			if(result.get(0).get("code_part").toString().equals(code.get(i).get("detcode").toString().substring(0, 1))){
	                			detCode=code.get(i).get("detcode").toString();
	                			detCName=code.get(i).get("detcodename").toString();
	                			if(detCode.equals(result.get(0).get("code_great").toString())){
	                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
	                			}else{
	                				out.println("<option value="+detCode+">"+detCName+"</option>");
	                			}
                			}
                		}
                	}
                %>
                </select>
				<img src="images/m_serch_icon01_1.gif" alt="찾기" />
			  </td>
              <td>근무처소분류
			  </td>
              <td><select name="mSmall" id="mSmall">
                <% 
                	int idx2=0;
                	for(int i=0;i<code.size();i++){
                		if("005".equals(code.get(i).get("groupcode").toString())){
                			if(result.get(0).get("code_great").toString().equals(code.get(i).get("detcode").toString().substring(0,3))){
	                			detCode=code.get(i).get("detcode").toString();
	                			detCName=code.get(i).get("detcodename").toString();
	                			if(detCode.equals(result.get(0).get("code_small").toString())){
	                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
	                				idx2=1;
	                			}else{
	                				out.println("<option value="+detCode+">"+detCName+"</option>");
	                			}
                			}
                		}
                	}
                if(idx2==0) out.println("<option value=''>없음</option>");
                %>
                </select>
			  </td>
            </tr>			
            </tbody>
            </table>       
	  </div><!--mSearch_01 End-->
	   <div class="mTabmenu">
	    <div id="ts_tabmenu">
          <ul>
	        <li><a href="#" class="overMenu"><strong><span>기본정보</span></strong></a></li>
	        <li><a href="memberBasic.do?method=comp&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>근무처정보</span></strong></a></li>
	        <li><a href="memberBasic.do?method=bank&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>회비입출</span></strong></a></li>
	        <li><a href="memberBasic.do?method=dues&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>회비처리</span></strong></a></li>
	        <li><a href="memberBasic.do?method=edu&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>교육</span></strong></a></li>
	        <li><a href="memberBasic.do?method=certifi&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>자격증</span></strong></a></li>
	        <li><a href="memberBasic.do?method=donation&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>기부&frasl;기금</span></strong></a></li>
			<li><a href="memberBasic.do?method=memo&pCode=<%=result.get(0).get("code_pers") %>" ><strong><span>메모</span></strong></a></li>			
          </ul>
        </div>
		<div class="mTabmenu_01" id="tab01" style="display:show">
        <table class="mytable_01" cellspacing="0" summary="기본정보">
          <caption>기본정보</caption>
             <tr>
               <td class="nobg">자택우편번호</td>


               <td colspan="5" class="nobg1"><input type="text" class="readOnly1" name="m_post" id="m_post" size="15"  onclick="javascript:postSearch();" value="<%=result.get(0).get("code_post") %>" readonly onkeydown="ban(this);"/>


			   <input name="bnt" type="button" value="우편번호" onclick="javascript:postSearch();"/>	
			 <%--   <input type="radio" name="pgubun" id="a_old" value="1" <%if("1".equals(result.get(0).get("code_add_gubun").toString())) out.print("checked"); %>/>
			   <label for="a_old">구주소</label>
			   <input type="radio" name="pgubun" id="a_new" value="2" <%if("2".equals(result.get(0).get("code_add_gubun").toString())) out.print("checked"); %>/>
			   <label for="a_new">신주소</label> 도로명주소개펴능로 인한 삭제_20141127--%> 
			   <input type="hidden" name="pgubun" id="a_new" value="2" /> <!-- 도로명주소개펴능로 인한 추가_20141127 -->
			   </td>
			 </tr>
			 <tr>
               <td class="alt1">자택주소</td>
               <td colspan="3"><input type="text" class="readOnly1" name="m_address" id="m_address" size="60" value="<%=result.get(0).get("pers_add") %>" readonly onkeydown="ban(this);"/>-<input type="text" name="d_address" id="d_address" size="20"  value="<%=result.get(0).get("pers_add_detail") %>"/></td> 
		   <td colspan="2" class="alt">자격증현황</td>	 
             </tr>
             <tr>
               <td class="alt1">우편물&nbsp;수신처</td>
               <td><input type="radio" name="mPlace" id="h" value="1" <% if("1".equals(result.get(0).get("code_place").toString())) out.print("checked"); %>/>
			       <label for="a_home">자택</label>
			       <input type="radio" name="mPlace" id="f" value="2"  <% if("2".equals(result.get(0).get("code_place").toString())) out.print("checked"); %>/>
			       <label for="a_office">근무처</label>
			       <input type="radio" name="mPlace" id="s" value="3" <% if("3".equals(result.get(0).get("code_place").toString())) out.print("checked"); %> />
			       <label for="a_stop">발송중지</label></td>
               <td class="alt">자택전화번호</td>
               <td><input type="text" name="m_tel" size="15" value="<%=result.get(0).get("pers_tel") %>"/></td>
		   <td colspan="2" rowspan="2">
			   <input type="hidden"  name="mCertiview" value="" />
			   <input type="checkbox" name="mCertiview1" id="oo"   <% if("1".equals(result.get(0).get("certifi_view").toString().substring(0, 1))) out.print("checked");  %> />
			   <label for="a_oo">영양교사</label>
			   <input type="checkbox" name="mCertiview2" id="ee"   <% if("1".equals(result.get(0).get("certifi_view").toString().substring(1, 2))) out.print("checked"); %>/>
			   <label for="a_ee">운동처방사</label>
			   <input type="checkbox" name="mCertiview3" id="ww"  <% if("1".equals(result.get(0).get("certifi_view").toString().substring(2, 3))) out.print("checked"); %>/>
			   <label for="a_ww">조리사</label><br>
			   <input type="checkbox" name="mCertiview4" id="kk"   <% if("1".equals(result.get(0).get("certifi_view").toString().substring(3, 4))) out.print("checked"); %>/>
			   <label for="a_kk">기타</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   <input type="checkbox" name="mCertiview5" id="ss"   <% if("1".equals(result.get(0).get("certifi_view").toString().substring(4, 5))) out.print("checked"); %>/>
			   <label for="a_ss">임상영양사 국가자격증</label>
			   </td>	                  
             </tr>
             <tr>
               <td class="alt1">휴대폰</td>
               <td><input type="text" name="m_hp" id="m_hp" size="15" value="<%=result.get(0).get("pers_hp") %>"/></td>
               <td class="alt">이메일</td>
               <td>
               		<input type="text" name="m_email" size="15"  value="<%=result.get(0).get("email") %>"/>
               		<select name="mEcomp"  style="width:80px;">
               		<% 
                	for(int i=0;i<code.size();i++){
                		if("025".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_email_comp").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}%>
                	</select>
               </td>		   
			 <tr>
               <td class="alt1">학교</td>
               <td><select name="mSchool" >
                <% 
                	for(int i=0;i<code.size();i++){
                		if("028".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_school").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>
                </select></td>
               <td class="alt">최종학력</td>
               <td><select name="mScholar" >
                <% 
                	for(int i=0;i<code.size();i++){
                		if("027".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_scholar").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>
                </select></td>
			   <td class="alt" colspan="2">전문자격증&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;자격증번호&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;유효기간
             </tr>
			 <tr>
               <td class="alt1">학위</td>
               <td><select name="mUniver" >
                <% 
                	for(int i=0;i<code.size();i++){
                		if("019".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_univer").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>
                </select>
			   </td>
               <td class="alt">경력</td>
               <td><input type="text" name="mYear" size="3" value="<%=result.get(0).get("pers_career").toString().substring(0, 2) %>"/>년<input type="text" name="mMonth" size="3" value="<%=result.get(0).get("pers_career").toString().substring(2, 4) %>"/>월
			   </td>
			   <td colspan="2">
			   	<input type="text" class="readOnly1"  name="mCertiName1" size="5" value="<% if(g_certifi.size()>0) out.print(g_certifi.get(0).get("certifi_name")); %>" readonly onkeydown="ban(this);" />-----
			   	<input type="text" class="readOnly1"  name="mResultNo1" size="4" value="<% if(g_certifi.size()>0) out.print(g_certifi.get(0).get("result_no")); %>" readonly onkeydown="ban(this);" />-----
			   	<input type="text" class="readOnly1"  name="mResultStDt1" size="5" value="<% if(g_certifi.size()>0) out.print(g_certifi.get(0).get("result_start_dt")); %>" readonly onkeydown="ban(this);" />~
			   	<input type="text" class="readOnly1"  name="mResultEdDt1" size="5" value="<% if(g_certifi.size()>0) out.print(g_certifi.get(0).get("result_end_dt")); %>" readonly onkeydown="ban(this);" />
			   </td>
             </tr>
			 <tr>
               <td class="alt1">혼인여부</td>
               <td><input type="radio" name="mMarry" id="l" value="1" <% if("1".equals(result.get(0).get("code_marry").toString())) out.print("checked"); %> />
			   <label for="a_m">기혼</label>
			   <input type="radio" name="mMarry" id="e" value="0" <% if("0".equals(result.get(0).get("code_marry").toString())) out.print("checked"); %> />
			   <label for="a_um">미혼</label></td>
               <td class="alt">종교</td>
               <td><select name="mReligion" >
                <% 
                	for(int i=0;i<code.size();i++){
                		if("020".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_religion").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>
                </select></td>
			   <td colspan="2">
			   	<input type="text" class="readOnly1"  name="mCertiName2" size="5" value="<% if(g_certifi.size()>1) out.print(g_certifi.get(1).get("certifi_name")); %>" readonly onkeydown="ban(this);" />-----
			   	<input type="text" class="readOnly1"  name="mResultNo2" size="4" value="<% if(g_certifi.size()>1) out.print(g_certifi.get(1).get("result_no")); %>" readonly onkeydown="ban(this);" />-----
			   	<input type="text" class="readOnly1"  name="mResultStDt2" size="5" value="<% if(g_certifi.size()>1) out.print(g_certifi.get(1).get("result_start_dt")); %>" readonly onkeydown="ban(this);" />~
			   	<input type="text" class="readOnly1"  name="mResultEdDt2" size="5" value="<% if(g_certifi.size()>1) out.print(g_certifi.get(1).get("result_end_dt")); %>" readonly onkeydown="ban(this);" />
			   	</td>
             </tr>
			 <tr>
			   <td class="alt1">산하단체&nbsp;및&nbsp;분야회&nbsp;&nbsp;소속현황</td>
               <td>
					<select name="mSub" >
                <% 
                	String subCode,subName="";
                	for(int i=0;i<subcom.size();i++){
               			subCode=subcom.get(i).get("code_sub").toString();
               			subName=subcom.get(i).get("sub_name").toString();
               			if(subCode.equals(result.get(0).get("code_sub").toString())){
               				out.println("<option value="+subCode+" selected>"+subName+"</option>");
               			}else{
               				out.println("<option value="+subCode+">"+subName+"</option>");
               			}	
               		}
                %>
                </select>
			   </td>
               <td class="alt">발송호칭</td>
               <td><select name="mCall" >
                <% 
                	for(int i=0;i<code.size();i++){
                		if("018".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("code_call").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>
                </select></td>
			   <td colspan="2">
				<input type="text" class="readOnly1"  name="mCertiName3" size="5" value="<% if(g_certifi.size()>2) out.print(g_certifi.get(2).get("certifi_name")); %>"  readonly onkeydown="ban(this);" />-----
			   	<input type="text" class="readOnly1"  name="mResultNo3" size="4" value="<% if(g_certifi.size()>2) out.print(g_certifi.get(2).get("result_no")); %>"  readonly onkeydown="ban(this);" />-----
			   	<input type="text" class="readOnly1"  name="mResultStDt3" size="5" value="<% if(g_certifi.size()>2) out.print(g_certifi.get(2).get("result_start_dt")); %>" readonly onkeydown="ban(this);" />~
			   	<input type="text" class="readOnly1"  name="mResultEdDt3" size="5" value="<% if(g_certifi.size()>2) out.print(g_certifi.get(2).get("result_end_dt")); %>" readonly onkeydown="ban(this);" />
			   	</td>		 
			 </tr>
			 <tr>
               <td class="alt1">추천인&nbsp;성명</td>
			   <td><input type="text" name="mRecm" size="15" value="<%=result.get(0).get("recommender") %>"/></td>
			   <td class="alt">추천인&nbsp;핸드폰</td>
               <td><input type="text" name="mReHp" size="15" value="<%=result.get(0).get("recm_hp") %>"/></td>	
               <td class="alt">추천인근무처</td>
               <td><input type="text" name="mRdivision" size="15" value="<%=result.get(0).get("recm_division") %>"/></td>
             </tr>		 
			 <tr>
			 	<td class="alt1">최종수정자</td>
               <td><%=result.get(0).get("register") %></td>
			   <td class="alt">협회직급</td>
               <td>
               <select name="mLevel" >
               		<% 
                	for(int i=0;i<code.size();i++){
                		if("050".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("kda_level").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}%>
                	</select></td>
               <td class="alt">회원상태</td>
               <td><select name="mState" style="width:100%;">
                <% 
                	for(int i=0;i<code.size();i++){
                		if("021".equals(code.get(i).get("groupcode").toString())){
                			detCode=code.get(i).get("detcode").toString();
                			detCName=code.get(i).get("detcodename").toString();
                			if(detCode.equals(result.get(0).get("pers_state").toString())){
                				out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value="+detCode+">"+detCName+"</option>");
                			}	
                		}
                	}
                %>
                </select></td>
			 </tr>
        </table>	
        <p class="btnSave">     
<!--         <a href="javascript:notePad();"	><img src="images/icon_s2.gif" 		alt="쪽지" 		/></a> -->
		<!-- <a href="javascript:sendEmail();"	><img src="images/icon_s3.gif"		alt="메일전송"	/></a>
		<a href="javascript:umsData();"	><img src="images/icon_s4.gif"		alt="문자전송"	/></a> -->
        <a href="javascript:printReceipt();"	><img src="images/membership.png" 		alt="회원증출력" 		/></a>  		
		<a href="javascript:reg_mod();"><img src="images/icon_save.gif" alt="저장" /></a>        
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