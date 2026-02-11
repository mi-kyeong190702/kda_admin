<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
	String yyyy1   = "";
	String code_bran1     = "";
	String code_kind1  = "";
	String edutest_name1       = "";
	String code_certifi1         = "";
	String gubun ="";
	
	request.setCharacterEncoding("UTF-8"); 	
	String oper_hp		=  request.getParameter("oper_hp");
	int oper_hp_len		=  0;
	if(oper_hp!=null&&!"".equals(oper_hp)&&!"ALL".equals(oper_hp)){
		oper_hp_len = oper_hp.split(",").length; 		
	}
	
	String oper_name	=  request.getParameter("oper_name");
	yyyy1        		=  request.getParameter("yyyy1")  == null?""     :   request.getParameter("yyyy1") + "";	
	code_bran1			=  request.getParameter("code_bran1")       == null?""     :   request.getParameter("code_bran1") + "";	
	code_kind1			=  request.getParameter("code_kind1")         == null?""     :   request.getParameter("code_kind1") + "";	
	edutest_name1		=  request.getParameter("edutest_name1")   == null?""     :   request.getParameter("edutest_name1") + "";					
	code_certifi1		=  request.getParameter("code_certifi1")     == null?""     :   request.getParameter("code_certifi1") + "";	
	gubun				=  request.getParameter("gubun")     == null?""     :   request.getParameter("gubun") + "";
	String isSelect		=  request.getParameter("isSelect");
	
	//예약전송
	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();

	String yyyyMMdd	= new java.text.SimpleDateFormat ("yyyyMMdd"	).format(new java.util.Date());
	String HH		= new java.text.SimpleDateFormat ("HH"			).format(new java.util.Date());
	String mm		= new java.text.SimpleDateFormat ("mm"			).format(new java.util.Date());
	
	String msg        		=  request.getAttribute("msg")  == null?""     :   request.getAttribute("msg").toString();
	int msgs=msg.length();
%>

<script language="javascript">
function init(){
	if('<%=msgs%>'>0){
		alert('<%=msg%>');
		self.close();
	}
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

<%
String edutake_paramok		=  (String)request.getAttribute("edutake_paramok");
String exam_paramok		=  (String)request.getAttribute("exam_paramok");
String ncount		= request.getAttribute("ncount"		)==null || "".equals(request.getAttribute("ncount"		)) ? "" : String.valueOf((Integer)request.getAttribute("ncount"		)); 
%>


	function sendDms(){	
		if ( '<%=oper_hp%>' == 'ALL' ) {
			 sendDmsall();
			 return;
		}
		
		var  send_phone  = sForm.send_phone.value;
		var  subject     = escape(encodeURIComponent(sForm.subject.value));
		var  msg_body    = escape(encodeURIComponent(sForm.msg_body.value));		
		   
		if( '<%=oper_hp%>'!=""){		
			oper_hp      =  '<%=oper_hp%>';			
		}
		if( '<%=oper_name%>'!=""){					
			oper_name    =  '<%=oper_name%>';
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
	    
		<%
		if("Y".equals(edutake_paramok) || "Y".equals(exam_paramok)){
			String param		=  (String)request.getAttribute("param");
		%>
		var parm  = "<%=param%>" + "&oper_hp=" + oper_hp + "&oper_name=" + oper_name;
		<%
		} else {
		%>
	    var parm = "&oper_hp="       +  oper_hp       +
	               "&oper_name="     +  oper_name       +
	    		   "&subject="       +  subject       +
	   			   "&msg_body="      +  msg_body;   
		<%
		}
		%>

	    
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
	    
		document.sForm.action = "education.do?method=umsData&parm="+parm;
	   	document.sForm.submit();
	   	//self.close();		   	
	}

	function sendDmsall(){
		var parm = "";	
		var yyyy1 = "";
		var code_bran1 = "";
		var code_kind1 = "";
		var edutest_name1 = "";
		var code_certifi1 = "";
		
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
	    
	   	if( '<%=yyyy1%>'!=""){		
	   		yyyy1         =  '<%=yyyy1%>';			 
		}
		if( '<%=code_bran1%>'!=""){		
			code_bran1              =  '<%=code_bran1%>';
		}
		if( '<%=code_kind1%>'!=""){
			code_kind1           =  '<%=code_kind1%>';			
		}
		if( '<%=edutest_name1%>'!=""){		
			edutest_name1             =  '<%=edutest_name1%>';
		}
		if( '<%=code_certifi1%>'!=""){		
			code_certifi1                 =  '<%=code_certifi1%>';
		}
		
		
		<%
		if("Y".equals(edutake_paramok) || "Y".equals(exam_paramok)){
			String param		=  (String)request.getAttribute("param");
			String edutest_name1_edutake	= "";	//교육의 내용
			String oper_name1_edutake		= "";	//교육의 이름
			String oper_comp_name11_edutake	= "";	//교육의 근무처명
			
			//교육에 사용하는것
			if(request.getAttribute("edutest_name1"		)!= null )	edutest_name1_edutake 	= request.getAttribute("edutest_name1"		)+"";
			if(request.getAttribute("oper_name1"		)!= null )	oper_name1_edutake 		= request.getAttribute("oper_name1"			)+"";
			if(request.getAttribute("oper_comp_name11"	)!= null )	oper_comp_name11_edutake= request.getAttribute("oper_comp_name11"	)+"";
		%>
		var param  = "<%=param%>";
		if( "<%=edutest_name1_edutake%>".length		> 0)	param+="&edutest_name1="	+escape(encodeURIComponent("<%=edutest_name1_edutake%>"		));
		if( "<%=oper_name1_edutake%>".length		> 0)	param+="&oper_name1="		+escape(encodeURIComponent("<%=oper_name1_edutake%>"		));
		if( "<%=oper_comp_name11_edutake%>".length	> 0)	param+="&oper_comp_name11="	+escape(encodeURIComponent("<%=oper_comp_name11_edutake%>"	));
		<%
		} else {
		%>
		var param  = "&yyyy1="     +  yyyy1     +
			 		 "&code_bran1="     		+  code_bran1          +
			 		 "&code_kind1="      +  code_kind1      +
			   		 "&edutest_name1="        +  edutest_name1        +
			 		 "&code_certifi1="            +  code_certifi1            +						
		 			 "&send_phone="         +  send_phone         +
		   			 "&subject="            +  subject            +
		 			 "&msg_body="           +  msg_body;
		<%
		}
		%>

		if( '<%=gubun%>'!=""){		
			param+="&gubun=result";
		}
		

	    
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
		
	    //alert(parm);
    	document.sForm.action = "education.do?method=umsDataall&param="+param;
	   	document.sForm.submit();
	   	//self.close();		
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

<body onload="init();">
<form name="sForm" method="post" action="document.do?method=umsData" >

<table class="tbl_type" border="1" cellspacing="0" summary="회원검색" > 
	<tr> 
		<td align="center" >발송 대상</td>
		<td>
			<%
			if(("Y".equals(edutake_paramok) || "Y".equals(exam_paramok))&&!"ALL".equals(oper_hp)){ %>
			<input type="text" name="to"  value="<%=URLDecoder.decode(oper_name,"utf-8") %>" size="25" readonly="readonly"/>
			<%}
			else { %>
			<input type="text" name="to"  value="<%=oper_hp %>" size="25" readonly="readonly"/>
			<%}
			%>
			
			<%
			if("Y".equals(edutake_paramok) || "Y".equals(exam_paramok)){ %>(<%=ncount %>명)<%}
			else if(oper_hp_len>0){ %>(<%=oper_hp_len %>명)<%}
			%>
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



