<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" href="css/m_search.css" />
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

<script type="text/javascript">
<%

String msg="";
if(request.getAttribute("msg")!=null){
	msg=request.getAttribute("msg").toString();
}
int len=msg.length();
%>
function excelDown(){
	
	document.upFile.target="frm";
	document.upFile.action="actul.do?method=actulSampleExcel";
	document.upFile.submit();
}

function upExcel(){
	
	if( upFile.attatch.value == "") {
		alert("엑셀 파일을 선택하십시오.");
		return;
	}
	
	document.upFile.target="frm";
	document.upFile.action="actul.do?method=actulUploadExcel";
	document.upFile.submit();
	var obj=document.getElementById("btnIcon_3");
	
	obj.innerHTML="";
	obj.innerHTML="<li><font color='red'>업로드중입니다.</font></li>";
}

document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
	};
function init(){	
	var len='<%=len%>';
	var msg='<%=msg%>';
	if(len>0){						
		alert(msg);
		parent.close();
	}	
}
</script>
</head>
<body> 
<form name="upFile" method="post" enctype="multipart/form-data" action="actul.do?method=actulUploadExcel" >
	<div id="L_contents" >
	<br>
	<h5>&nbsp;&nbsp;▶실태신고 엑셀 파일 올리기</h5>
	</div>
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="엑셀 파일 올리기">
          <caption>회원검색</caption>          
			<tbody>
            <tr> 
            	<td class="mtbl1" colspan="4">
					<input type="file" name="attatch" size="24" id="attatch"/><br><br>
					1. 액셀저장 버튼 click.<br><br>
					2. 신규/갱신 구분. (신규: i  갱신: u)<br><br>
					3. file 저장 후 찾아보기 click.<br><br>
					4. 파일은 한 개만 첨부 가능(용량 10MB).<br><br>				
					5. file 선택하시고 엑셀올리기 버튼 click.<br><br>
					</td>                         
             </tr>             
            </tbody>
          </table>  
          <ul class="btnIcon_3" id="btnIcon_3">
         	<li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" alt="엑셀저장"/></a></li>
            <li><a href="javascript:upExcel();"><img src="images/icon_s16.gif" alt="엑셀올리기" /></a></li>
   		  </ul>	      
	</div>
</form>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>