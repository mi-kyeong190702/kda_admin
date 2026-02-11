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
</script>
</head>
<body> 
<form name="upFile" method="post" enctype="multipart/form-data" action="license.do?method=resultSendListUpExcel" >
	<div id="L_contents" ><br><br>
	<h5>&nbsp;&nbsp;▶엑셀 파일 올리기</h5>
	
	</div>
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="엑셀 파일 올리기">
          <caption>회원검색</caption>          
			<tbody>
            <tr> 
            	<td class="mtbl1" colspan="4">
					<input type="file" name="attatch" size="24" id="attatch" onchange="submit();"/><br><br>
					1. 파일은 한 개만 첨부 가능합니다.<br>
					2. 첨부 가능한 파일의 용량은 10MB입니다.<br>
					3. 화일을 선택하시면 바로 작업이 시작됩니다.
					</td>                         
             </tr>
            </tbody>
          </table>       
	</div>
 </form>
</body>
</html>