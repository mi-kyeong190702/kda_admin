<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회 쪽지</title>

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
	
	String code_pers = "";
	int code_pers_len = 0;
	if(request.getAttribute("code_pers")!= null ) {
		code_pers = request.getAttribute("code_pers")+"";
		code_pers_len = code_pers.split(",").length; 
	}
	
	String pers_name = "";
	if(request.getAttribute("pers_name")!= null )
		pers_name = request.getAttribute("pers_name")+"";
	
	String chk = "";
	if(request.getAttribute("chk")!= null )
		chk = request.getAttribute("chk")+"";

	String sout = "";
	if("ALL".equals(chk)) sout = "ALL";
	else				  sout = pers_name;
	
	//System.out.println("jsp");
	//System.out.println(param);
%>

<script language="javascript">

	function sendNote(){


		var code_pers = "";
			
		var  note_oper     = escape(encodeURIComponent(sForm.note_oper.value));
		var  note_title    = escape(encodeURIComponent(sForm.note_title.value));
		var  note_contents = escape(encodeURIComponent(sForm.note_contents.value));
		var  st_dt    	   = sForm.st_dt.value;
		var  ed_dt    	   = sForm.ed_dt.value;
		   
		code_pers      =  '<%=code_pers%>';

		if (note_oper  == "") { 
	    	alert("공지자를 입력하세요.") ;
	        sForm.note_oper.focus();
	        return ;
	    }
	    if (note_title  == "") {
	        alert("제목을 입력하세요.") ;
	        sForm.note_title.focus();
	        return ;
	    }
	    if (st_dt  == "") {
	        alert("공지일을 입력하세요.") ;
	        sForm.st_dt.focus();
	        return ;
	    }
	    if (ed_dt  == "") {
	        alert("공지일을 입력하세요.") ;
	        sForm.ed_dt.focus();
	        return ;
	    }
	    if ( note_contents == "") {
	        alert("내용을 입력하세요.") ;
	        sForm.note_contents.focus();
	        return ;
	    }
	  	    
	    var parm = "&dm_pers_code="       +  code_pers       +
	    		   "&note_oper="          +  note_oper          +
	   			   "&note_title="         +  note_title         +
				   "&note_contents="      +  note_contents      ;	   
	    	//  alert(parm);
		document.sForm.action = "education.do?method=notePad&parm="+parm;
	   	document.sForm.submit();
	   	
	   	alert("쪽지를 발송하였습니다.");
	   	self.close();
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
<form name="sForm" method="post" action="document.do?method=notePad" >

<table class="tbl_type" border="1" cellspacing="0" summary="회원검색" > 
	<tr> 
		<td align="center" size="10">수신자 수</td>
		<td colspan="3">
			<%=code_pers_len %>명
		</td>
	</tr>
	<tr> 
		<td align="center" size="10">수신자</td>
		<td>
			<input type="text" name="to"  value="<%=sout %>"  readonly size="11"/>
		</td>
		<td align="center" size="10">공지자</td>
		<td>
			<input type="text" name="note_oper"  value="" size="11"/>
		</td>
	</tr>
	<tr> 
		<td align="center">제 목</td>
		<td colspan="3"> 
			<input type="text" name="note_title"  value="" size="25"/>
		</td>
	</tr>
	<tr>	
		<td align="center">공지기간</td>
        <td colspan="4"><input type="text" name="st_dt" id="datepicker" size="10"/>&nbsp;~
		 <input type="text" name="ed_dt" id="datepicker1" size="10"/></td>
	</tr>
	<tr> 	
		<td colspan="4">
			<textarea  name="note_contents" id="content1" rows="20" style="width:98%"></textarea>
		</td>
	</tr>
</table>
</form>

<p align="right">
<input type="button" name="send"   value="저장"  onclick="sendNote();"> 
<input type="button" name="cancel" value="취소"  onclick="self.close();">
</p> 					

</body>
</html>



