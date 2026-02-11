<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회 문자</title>

<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/document.css" />
<script type="text/javascript" src="js/comm.js"></script>

<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script> 
<script src="js/comm.js"  type="text/javascript"></script>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLDecoder"%>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
<%
	String register = "";//작성자
	String chk		= "";//전체인지, 선택인지
	String ncount	= "";//넘겨받은 갯수
	String sout		= "";//목표자 출력용
	String pers_name= "";//검색용 회원 이름(조건검사에서 필요함)
	String param	= "";

	if(request.getAttribute("register"	)!= null )	register	= request.getAttribute("register"	)+"";
	if(request.getAttribute("chk"		)!= null )	chk			= request.getAttribute("chk"		)+"";
	if(request.getAttribute("param"		)!= null )	param		= request.getAttribute("param"		)+"";
	if(request.getAttribute("ncount"	)!= null )	ncount		= request.getAttribute("ncount"		)+"";
	if(request.getAttribute("pers_name"	)!= null ) pers_name 	= request.getAttribute("pers_name"	)+"";

	//위에는 고정, 아래는 쪽지와 문자에 따라 변홤(pers_hp를 받느냐, code_pers를 받느냐 차이)
	
	String pers_hp	= "";//휴대폰번호 모음 (선택시 all 말고 화면에 표출용)
	if(request.getAttribute("pers_hp"	)!= null )	pers_hp		= request.getAttribute("pers_hp"	)+"";
	
	if("CHK".equals(chk)) sout = pers_hp;	//출력을 아이디 보낸다.
	if("ALL".equals(chk)) sout = "ALL";		//출력을 ALL 보낸다.

	String from		= "";
	from	= request.getAttribute("from")+"";
	
	

	//예약전송
	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();

	String yyyyMMdd	= new java.text.SimpleDateFormat ("yyyyMMdd"	).format(new java.util.Date());
	String HH		= new java.text.SimpleDateFormat ("HH"			).format(new java.util.Date());
	String mm		= new java.text.SimpleDateFormat ("mm"			).format(new java.util.Date());
%>

<script language="javascript">

function sendUms(form){		
	//필수조건 체크
	if (sForm.send_phone.value  == "") { 
    	alert("발신번호를 입력해 주십시오.") ;
        sForm.send_phone.focus();
        return ;
    }
    if (sForm.subject.value  == "") {
        alert("제목을 입력해 주십시오.") ;
        sForm.subject.focus();
        return ;
    }
    if (sForm.msg_body.value  == "") {
        alert("내용을 입력해 주십시오.") ;
        sForm.msg_body.focus();
        return ;
    }
    
	//저장을 위해 보낼 값
	var  send_phone  = sForm.send_phone.value;
	var  subject     = escape(encodeURIComponent(sForm.subject.value));
	var  msg_body    = escape(encodeURIComponent(sForm.msg_body.value));		

	//보낼값 만듬
	var param='';
	param+="&chk="+"<%=chk%>";
	param+="&register="+"<%=register%>";
	
	//조건검색 일때는 이름이 필요하므로 아래코드 사용
	if( "<%=pers_name%>".length > 0)	param+="&pers_name="+escape(encodeURIComponent("<%=pers_name%>"));

	//넘어온 전체 조건 혹은 회원번호 전화번호를 넘김다.
	param+="<%=param%>";
	
	//저장 데이터
    param+= "&send_phone="	+ send_phone;
    param+= "&subject="		+ subject;
    param+= "&msg_body="	+ msg_body;
    
    //예약전송
    if( document.sForm.isr.checked == true) {
		var yy = sForm.yyyyMMdd.value;
		var HH = sForm.HH.value;
		var mm = sForm.mm.value;

		var yyyyMMdd= '';
		var HHmm	= '';
		yyyyMMdd= yy.substr(0,4)+'-'+yy.substr(4,2)+'-'+yy.substr(6,2);
		HHmm	= HH+':'+mm+':'+'00.000';
		
		param+="&yyyyMMdd="+yyyyMMdd;
		param+="&HHmm="+HHmm;
	}

    //버튼 잠금
	form.btn1.disabled=true;
	form.btn2.disabled=true;
	

	//쿼리 구분자
	var from = "<%=from%>";
	param+="&menu="+from;
	
	var url="";
	url="actul.do?method=sendUmsLink"+param;
	form.action = url;
	form.submit();
}


$(function() {	$( "#datepicker" ).datepicker();		});

$(document).ready(function(){
	document.sForm.yyyyMMdd.value = "<%=yyyyMMdd%>";
	document.sForm.HH.value = "<%=HH%>";
	document.sForm.mm.value = "<%=mm%>";
});
	
	
function fn_isrequest(form) {
	//alert(form.yyyyMMdd.value+"년월일  "+form.HH.value+"시  "+form.mm.value+"분");
	
 	if( form.isr.checked==true) {	
 		form.yyyyMMdd.disabled=false;
		form.HH.disabled=false;
		form.mm.disabled=false;
 	} else {
 		form.yyyyMMdd.disabled=true;
		form.HH.disabled=true;
		form.mm.disabled=true;
 	}
}

//TextArea의 문자수 표시
var showmsg = true;
function fnCheckFocus(obj){
   if(obj.value=="80Byte 초과시 자동으로 2000Byte로 전환되어\nLMS로 발송됩니다.\n요금의 차이가 있으니, 주의 바랍니다."){
	   obj.value="";
   }
}
function fnCheckFocusOut(obj){
   if(obj.value==""){
	   obj.value="80Byte 초과시 자동으로 2000Byte로 전환되어\nLMS로 발송됩니다.\n요금의 차이가 있으니, 주의 바랍니다.";
   }
}
function fnCheckStrLength(obj) 
{ 
   var now_str = obj.value;                   
   var now_len = obj.value.length;              //현재 value값의 글자 수 
  
   var i = 0;                                  
   var cnt_byte = 0;                            //한글일 경우 2 그외에는 1바이트 수 저장 	  
   var chk_letter = "";                         //현재 한/영 체크할 letter를 저장 	  
    
   for (i=0; i<now_len; i++) 
   { 
       //1글자만 추출 
       chk_letter = now_str.charAt(i); 

       // 체크문자가 한글일 경우 2byte 그 외의 경우 1byte 증가 
       if (escape(chk_letter).length > 4) 
       { 
           //한글인 경우 2byte (UTF-8인 경우 3byte로...)
           cnt_byte += 2; 
       }else{ 
           //그외의 경우 1byte 증가 
           cnt_byte++; 
       }	        
    } 
        
	sForm.check1.value=cnt_byte;
	if(cnt_byte>80){
		if(showmsg==true){
			alert("80Byte 초과시 자동으로 2000Byte로 전환되어\nLMS로 발송됩니다.\n요금의 차이가 있으니, 주의 바랍니다.");
			showmsg=false;
		}
	} else {
		showmsg=true;
	}
  
   obj.focus(); 
}

function isNumber(){
	var keyCode=event.keyCode;
	if(keyCode<48||keyCode>57){
		alert("숫자만 입력하실 수 있습니다.");
		sForm.send_phone.focus();
        return ;
	}
}
</script>
</head>

<body>
<form name="sForm" method="post" action="actul.do?method=umsData" >

<table class="tbl_type" border="1" cellspacing="0" summary="회원검색" > 
<tr> 
	<td align="center" >수신 번호</td>
	<td>
		<input type="text" name="to"  value="<%=sout %>"  readonly size="25"/>
		(<%=ncount %>명)
	</td>
</tr>
<tr>
	<td align="center" >발신 번호</td>
	<td>
		<input type="text" name="send_phone"  value="" size="25" onkeypress="isNumber()"/>
	</td>		
</tr>
<tr> 
	<td align="center">제 목</td>
	<td> 
		<input type="text" name="subject"  value="" size="25"/>
	</td>
</tr>

	<tr> 
		<td align="center">예약발송</td>
		<td>
			<input type="checkbox" name="isr"  onclick="javascript:fn_isrequest(sForm);">
			<input disabled="true" type="text" name="yyyyMMdd" id="datepicker" readonly size="8" style="width:60px">년월일
			<select name="HH" style="width:50px" disabled="true" >
				<option value="">선택</option>
				<% String lon = "";
				   for( int i=0; i<24; i++ ) {
					   if( i<10 ) lon = "0"+i;
					   else lon = i+""; %>
					<option value="<%=lon%>"><%=i%></option>
				<% } %>
				
			</select>시
			<select name="mm" style="width:50px" disabled="true" >
				<option value="">선택</option>
				<% lon = "";
				   for( int i=0; i<60; i++ ) {
					   if( i<10 ) lon = "0"+i; 
					   else lon = i+""; %>
					<option value="<%=lon%>"><%=i%></option>
				<% } %>
			</select>분

		</td>
	</tr>
	
<tr> 	
	<td colspan="2">
		<textarea  name="msg_body" rows="20" style="width:98%" IME-MODE: active;" onfocus="fnCheckFocus(this)" onfocusout="fnCheckFocusOut(this)" onkeyup="fnCheckStrLength(this)">80Byte 초과시 자동으로 2000Byte로 전환되어
LMS로 발송됩니다.
요금의 차이가 있으니, 주의 바랍니다.</textarea>
	</td>
</tr>
</table>
<table border="0" width="127" height="20" id="table2">
	<tr>
		<td align=center height="18"><input type="text" name="check1" value="0" size=4></td><td>Bytes</td>
	</tr>
</table>

<p align="right">
<input type="button" name="send"   id="btn1" value="저장"  onclick="sendUms(sForm);"> 
<input type="button" name="cancel" id="btn2" value="취소"  onclick="self.close();">
</p> 	
</form>				

</body>
</html>
