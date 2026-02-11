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
	String book_start_dt   = "";
	String book_end_dt     = "";
	String code_book_kind  = "";
	String sendgubun       = "";
	String rece_yn         = "";
	
	request.setCharacterEncoding("UTF-8"); 
	String dm_pers_code   = request.getParameter("dm_pers_code");
	String pers_name   	  = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
	code_book_kind        =  request.getParameter("code_book_kind")==null?""     :   request.getParameter("code_book_kind") + "";	
	sendgubun             =  request.getParameter("sendgubun")==null?""          :   request.getParameter("sendgubun") + "";	
	rece_yn               =  request.getParameter("rece_yn")==null?""            :   request.getParameter("rece_yn") + "";	
	book_start_dt         =  request.getParameter("book_start_dt")==null?""      :   request.getParameter("book_start_dt") + "";					
	book_end_dt           =  request.getParameter("book_end_dt")==null?""        :   request.getParameter("book_end_dt") + "";	
	String isSelect       = request.getParameter("isSelect");
%>

<script language="javascript">

	function sendNote(){
		
		var isSelect = '<%=isSelect%>';	
		if ( isSelect == 'Y' ) {
			 sendNoteall();
			 return;
		}
		 
		var dm_pers_code = "";
			
		var  note_oper     = escape(encodeURIComponent(sForm.note_oper.value));
		var  note_title    = escape(encodeURIComponent(sForm.note_title.value));
		var  note_contents = escape(encodeURIComponent(sForm.note_contents.value));
		var  st_dt    	   = sForm.st_dt.value;
		var  ed_dt    	   = sForm.ed_dt.value;
		   
		if( '<%=dm_pers_code%>'!=""){		
			dm_pers_code      =  '<%=dm_pers_code%>';
		}
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
	  	    
	    var parm = "&dm_pers_code="       +  dm_pers_code       +
	    		   "&note_oper="          +  note_oper          +
	   			   "&note_title="         +  note_title         +
				   "&note_contents="      +  note_contents      +
				   "&isSelect="           +  isSelect;	   
	    	  
		document.sForm.action = "document.do?method=notePad&parm="+parm;
	   	document.sForm.submit();
	   	self.close();		   	
	}

	function sendNoteall(){
		var parm = "";
	
		var pers_name = "";
		var code_book_kind = "";
		var sendgubun = "";
		var book_start_dt = "";
		var book_end_dt = "";
		var rece_yn = "";
		var st_dt = "";
		var ed_dt = "";
						
		var  note_oper     = escape(encodeURIComponent(sForm.note_oper.value));
		var  note_title    = escape(encodeURIComponent(sForm.note_title.value));
		var  note_contents = escape(encodeURIComponent(sForm.note_contents.value));
		var  st_dt    	   = sForm.st_dt.value;
		var  ed_dt    	   = sForm.ed_dt.value;
		
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
	   	if( '<%=code_book_kind%>'!=""){		
			 code_book_kind         =  '<%=code_book_kind%>';			 
		}
		if( '<%=sendgubun%>'!=""){		
			 sendgubun              =  '<%=sendgubun%>';
		}
		if( '<%=book_start_dt%>'!=""){
			book_start_dt           =  '<%=book_start_dt%>';			
			book_start_dt        =  book_start_dt.substring(2,6);
		}
		if( '<%=book_end_dt%>'!=""){		
			book_end_dt             =  '<%=book_end_dt%>';
			book_end_dt          =  book_end_dt.substring(2,6);	
		}
		if( '<%=rece_yn%>'!=""){		
			rece_yn                 =  '<%=rece_yn%>';
		}
		
		 var parm  = "&code_book_kind="     +  code_book_kind     +
			 		 "&sendgubun="     		+  sendgubun          +
			 		 "&book_start_dt="      +  book_start_dt      +
			   		 "&book_end_dt="        +  book_end_dt        +
			 		 "&rece_yn="            +  rece_yn            +						
		 			 "&note_oper="          +  note_oper          +
		   			 "&note_title="         +  note_title         +
		 			 "&note_contents="      +  note_contents      +   
					 "&st_dt="              +  st_dt              +
					 "&ed_dt="              +  ed_dt;   
		 		 	   
    	document.sForm.action = "document.do?method=notePadall&parm="+parm;
	   	document.sForm.submit();
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
		<td align="center" size="10">수신자</td>
		<td>
			<input type="text" name="to"  value="<%=pers_name %>" size="11" readonly="readonly"/>
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



