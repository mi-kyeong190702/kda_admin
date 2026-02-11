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
	String register = "";//작성자
	String chk		= "";//전체인지, 선택인지
	String ncount	= "";//넘겨받은 갯수
	String sout		= "";//목표자 출력용
	String pers_name= "";//검색용 회원 이름(조건검색에서 필요함)
	String param	= "";//선택일때, (DM, 쪽지에는 code_pers, 문자에는 pers_hp), 전체일때 조건 (검색을 위함)

	if(request.getAttribute("register"	)!= null )	register	= request.getAttribute("register"	)+"";
	if(request.getAttribute("chk"		)!= null )	chk			= request.getAttribute("chk"		)+"";
	if(request.getAttribute("param"		)!= null )	param		= request.getAttribute("param"		)+"";
	if(request.getAttribute("ncount"	)!= null )	ncount		= request.getAttribute("ncount"		)+"";
	if(request.getAttribute("pers_name"	)!= null ) pers_name 	= request.getAttribute("pers_name"	)+"";

	//위에는 고정, 아래는 쪽지와 문자에 따라 변홤(pers_hp를 받느냐, code_pers를 받느냐 차이)
	
	String code_pers= "";//아이디모음 (선택시 all 말고 화면에 표출용)
	if(request.getAttribute("code_pers"	)!= null )	code_pers	= request.getAttribute("code_pers"	)+"";
	
	if("CHK".equals(chk)) sout = code_pers;	//출력을 아이디 보낸다.
	if("ALL".equals(chk)) sout = "ALL";		//출력을 ALL 보낸다.
%>

<script language="javascript">

function sendNote(form){

	//필수조건 체크
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

	//저장을 위해 보낼 값
	var  note_oper     = escape(encodeURIComponent(sForm.note_oper.value));
	var  note_title    = escape(encodeURIComponent(sForm.note_title.value));
	var  note_contents = escape(encodeURIComponent(sForm.note_contents.value));
	var  st_dt    	   = sForm.st_dt.value;
	var  ed_dt    	   = sForm.ed_dt.value;

	//보낼값 만듬
	var param='';
	param+="&chk="+"<%=chk%>";
	param+="&register="+escape(encodeURIComponent("<%=register%>"			));

	//조건검색 일때는 이름이 필요하므로 아래코드 사용
	if( "<%=pers_name%>".length > 0)	param+="&pers_name="+escape(encodeURIComponent("<%=pers_name%>"));

	//넘어온 전체 조건 혹은 회원번호 전화번호를 넘김다.
	param+="<%=param%>";	

	//저장 데이터
    param+= "&note_title="		+ note_title;
    param+= "&note_contents="	+ note_contents;
    param+= "&st_dt="			+ st_dt;
    param+= "&ed_dt="			+ ed_dt;
    param+= "&note_oper="		+ note_oper;

    //버튼 잠금
	form.btn1.disabled=true;
	form.btn2.disabled=true;	
		
    // alert(param);
    	  
	document.sForm.action = "memberSearch.do?method=sendMsgLink&param="+param;
   	document.sForm.submit();
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
		<td><input type="text" name="to"  value="<%=sout %>"  readonly size="11"/></td>
		<td align="center" size="10">공지자</td>
		<td><input type="text" name="note_oper"  value="" size="11"/></td>
	</tr>
	<tr>
		<td align="center">제 목</td>
		<td colspan="3"><input type="text" name="note_title"  value="" size="25"/></td>
	</tr>
	<tr>
		<td align="center">공지기간</td>
        <td colspan="4"><input type="text" name="st_dt" id="datepicker" size="10"/>&nbsp;~<input type="text" name="ed_dt" id="datepicker1" size="10"/></td>
	</tr>
	<tr> 	
		<td colspan="4"><textarea  name="note_contents" id="content1" rows="20" style="width:98%"></textarea></td>
	</tr>
</table>

<p align="center">
	<input type="button" name="btn1"  id="btn1" value="저장"  onclick="sendNote(sForm);"> 
	<input type="button" name="btn2" id="btn2" value="취소"  onclick="self.close();">
</p> 					
</form>

</body>
</html>



