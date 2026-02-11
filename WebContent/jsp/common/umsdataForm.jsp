<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회 문자전송</title>

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
	String book_start_dt   = "";
	String book_end_dt     = "";
	String code_book_kind  = "";
	String sendgubun       = "";
	String rece_yn         = "";

	String dm_creater         = "";
	String dm_print_yymm_seq         = "";
	String t_bacode         = "";
	
	request.setCharacterEncoding("UTF-8"); 	
	String pers_hp   	  =  request.getParameter("pers_hp");
	String pers_name	  =  request.getParameter("pers_name")  == null?""     :   request.getParameter("pers_name") + "";
	code_book_kind        =  request.getParameter("code_book_kind")  == null?""     :   request.getParameter("code_book_kind") + "";	
	sendgubun             =  request.getParameter("sendgubun")       == null?""     :   request.getParameter("sendgubun") + "";	
	rece_yn               =  request.getParameter("rece_yn")         == null?""     :   request.getParameter("rece_yn") + "";	
	book_start_dt         =  request.getParameter("book_start_dt")   == null?""     :   request.getParameter("book_start_dt") + "";					
	book_end_dt           =  request.getParameter("book_end_dt")     == null?""     :   request.getParameter("book_end_dt") + "";
	
	dm_creater           =  request.getParameter("dm_creater")     == null?""     :   request.getParameter("dm_creater") + "";
	dm_print_yymm_seq           =  request.getParameter("dm_print_yymm_seq")     == null?""     :   request.getParameter("dm_print_yymm_seq") + "";
	t_bacode           =  request.getParameter("t_bacode")     == null?""     :   request.getParameter("t_bacode") + "";
	
	String isSelect       =  request.getParameter("isSelect");
	
	

	//예약전송
	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();

	String yyyyMMdd	= new java.text.SimpleDateFormat ("yyyyMMdd"	).format(new java.util.Date());
	String HH		= new java.text.SimpleDateFormat ("HH"			).format(new java.util.Date());
	String mm		= new java.text.SimpleDateFormat ("mm"			).format(new java.util.Date());
	
	String param		= "";
	param = request.getAttribute("param") != null ? (String)request.getAttribute("param") : ""; 
	
%>

<script language="javascript">

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
	function sendDms(){
		
		var isSelect = '<%=isSelect%>';	
		if ( isSelect == 'Y' ) {
			 sendDmsall();
			 return;
		}
		 
		var dm_pers_code = "";
		
		var  send_phone  = sForm.send_phone.value;
		var  subject     = escape(encodeURIComponent(sForm.subject.value));
		var  msg_body    = escape(encodeURIComponent(sForm.msg_body.value));		
		   
		if( '<%=pers_hp%>'!=""){		
			pers_hp      =  '<%=pers_hp%>';			
		}
		if( '<%=pers_name%>'!=""){					
			pers_name    =  '<%=pers_name%>';
		}
		if (send_phone  == "") { 
	    	alert("발신번호를 입력해 주십시오.") ;
	        sForm.send_phone.focus();
	        return ;
	    }
	    if (subject  == "") {
	        alert("제목을 입력해 주십시오.") ;
	        sForm.subject.focus();
	        return ;
	    }
	    if (msg_body  == "") {
	        alert("내용을 입력해 주십시오.") ;
	        sForm.msg_body.focus();
	        return ;
	    }
	    
	    var parm = "&pers_hp="       +  pers_hp       +
	               "&pers_name="     +  escape(encodeURIComponent(pers_name))       +
	    		   "&subject="       +  subject       +
	   			   "&msg_body="      +  msg_body      +		
	   			   "&isSelect="      +  isSelect;	   

	    
	    //예약전송
	    if( document.sForm.isr.checked == true) {
			var yy = sForm.yyyyMMdd.value;
			var HH = sForm.HH.value;
			var mm = sForm.mm.value;

			var yyyyMMdd= '';
			var HHmm	= '';
			yyyyMMdd= yy.substr(0,4)+'-'+yy.substr(4,2)+'-'+yy.substr(6,2);
			HHmm	= HH+':'+mm+':'+'00.000';
			
			parm+="&yyyyMMdd="+yyyyMMdd;
			parm+="&HHmm="+HHmm;
		}
	    
		document.sForm.action = "document.do?method=umsData&parm="+parm+"<%=param%>";
	   	document.sForm.submit();
	   	alert("발송요청 하였습니다.");
	   	self.close();		   	
	}

	function sendDmsall(){
		var parm = "";	
		var code_book_kind = "";
		var sendgubun = "";
		var book_start_dt = "";
		var book_end_dt = "";
		var rece_yn = "";
		
		var pers_name = "";
		var dm_creater = "";
		var dm_print_yymm_seq = "";
		var t_bacode = "";
		
		var  send_phone    = sForm.send_phone.value;
		var  subject       = escape(encodeURIComponent(sForm.subject.value));
		var  msg_body      = escape(encodeURIComponent(sForm.msg_body.value));
				
		if (send_phone  == "") { 
	    	alert("발신번호를 입력해 주십시오.") ;
	        sForm.send_phone.focus();
	        return ;
	    }
	    if (subject  == "") {
	        alert("제목을 입력해 주십시오.") ;
	        sForm.subject.focus();
	        return ;
	    }
	    if (msg_body  == "") {
	        alert("내용을 입력해 주십시오.") ;
	        sForm.msg_body.focus();
	        return ;
	    }
	    
	   	if( '<%=code_book_kind%>'!=""){		
			 code_book_kind         =  '<%=code_book_kind%>';			 
		}
		if( '<%=sendgubun%>'!=""){		
			 sendgubun              =  '<%=sendgubun%>';
		}
		if( '<%=book_start_dt%>'!=""){
			book_start_dt           =  '<%=book_start_dt%>';			
			//book_start_dt        =  book_start_dt.substring(2,6);
		}
		if( '<%=book_end_dt%>'!=""){		
			book_end_dt             =  '<%=book_end_dt%>';
			//book_end_dt          =  book_end_dt.substring(2,6);	
		}
		if( '<%=rece_yn%>'!=""){		
			rece_yn                 =  '<%=rece_yn%>';
		}
		
		if( '<%=pers_name%>'!=""){		
			pers_name                 =  '<%=pers_name%>';
		}
		if( '<%=dm_creater%>'!=""){		
			dm_creater                 =  '<%=dm_creater%>';
		}
		if( '<%=dm_print_yymm_seq%>'!=""){		
			dm_print_yymm_seq                 =  '<%=dm_print_yymm_seq%>';
		}
		if( '<%=t_bacode%>'!=""){		
			t_bacode                 =  '<%=t_bacode%>';
		}
		
		var parm  = "&code_book_kind="     +  code_book_kind     +
			 		 "&sendgubun="     		+  sendgubun          +
			 		 "&book_start_dt="      +  book_start_dt      +
			   		 "&book_end_dt="        +  book_end_dt        +
			 		 "&rece_yn="            +  rece_yn            +
			 		 
			 		 "&pers_name="            +  pers_name            +						
			 		 "&dm_creater="            +  dm_creater            +						
			 		 "&dm_print_yymm_seq="            +  dm_print_yymm_seq            +
			 		 "&t_bacode="            +  t_bacode            +
			 		 
		 			 "&send_phone="         +  send_phone         +
		   			 "&subject="            +  subject            +
		 			 "&msg_body="           +  msg_body;  

	    
	    //예약전송
	    if( document.sForm.isr.checked == true) {
			var yy = sForm.yyyyMMdd.value;
			var HH = sForm.HH.value;
			var mm = sForm.mm.value;

			var yyyyMMdd= '';
			var HHmm	= '';
			yyyyMMdd= yy.substr(0,4)+'-'+yy.substr(4,2)+'-'+yy.substr(6,2);
			HHmm	= HH+':'+mm+':'+'00.000';
			
			parm+="&yyyyMMdd="+yyyyMMdd;
			parm+="&HHmm="+HHmm;
		}
		 		 	   
    	document.sForm.action = "document.do?method=umsDataall&parm="+parm;
	   	document.sForm.submit();
	   	alert("발송요청 하였습니다.");
	   	self.close();		
	}
	
	$(function() {
		$( "#datepicker" ).datepicker();
	});

	$(function() {
		$( "#datepicker1" ).datepicker();
	});
	
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
<form name="sForm" method="post" action="document.do?method=umsData" >

<table class="tbl_type" border="1" cellspacing="0" summary="회원검색" > 
	<tr> 
		<td align="center" >발송 대상</td>
		<td>
			<%if(request.getParameter("hp_infos")!=null && !"".equals(request.getParameter("hp_infos"))){ %>
			<input type="text" name="to"  value="<%=URLDecoder.decode(pers_name,"utf-8")%>" size="25" readonly="readonly"/>
			<%}else{ %>
			<input type="text" name="to"  value="<%=pers_hp %>" size="25" readonly="readonly"/>
			<%} %>
			
			<%if(request.getAttribute("ncount")!=null){ %>
			(<%=request.getAttribute("ncount")%>명)
			<%} %>
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
</form>

<p align="right">
<input type="button" name="send"   value="저장"  onclick="sendDms();"> 
<input type="button" name="cancel" value="취소"  onclick="self.close();">
</p> 					

</body>
</html>



