<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<% 
		request.setCharacterEncoding("utf-8"); 	  	
		List<Map> result=(List<Map>)request.getAttribute("result");
		String iid=request.getAttribute("iid").toString();
		String cnt=result.get(0).get("cnt").toString();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" href="css/m_search.css" />
<link rel="stylesheet" type="text/css" href="css/m_member.css" />

<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>
<script src="js/form.js" type="text/javascript" ></script>
<script type="text/javascript">
function idChk(form){
	if(form.id.value==""){
		alert("ID를 입력해주세요");
		form.id.focus();
		return;
	}
	form.submit();
}

function idChkOk(){
	opener.document.comp.id.value="<%=iid%>";
	opener.document.comp.idChkOk.value='Y';
	self.close();
}
</script>

</head>
<body>
<form name="idc" method="post" action="memberInsert.do?method=idChk">
	<div id="L_contents" >
	<h5>&nbsp;&nbsp;▶ID 중복검사</h5>
	</div>
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="면허번호 중복검사">
          <caption>회원검색</caption>          
			<tbody>
            <tr>                         
              <%if("0".equals(cnt)){ %>
              <td>입력하신 ID(<%=iid %>)는 사용 가능합니다.</td>              
             </tr>
            </tbody>
          </table>
          <p align="center"><input type="button" name="btn" value="확인" onClick="javascript:idChkOk();"></p>
          <%}else{ %>
              <td>입력하신 ID(<%=iid %>)는 이미 사용중입니다.<br>
              새로운 아이디 <input type="text" id="id" name="id"  onfocus="fn_IME(this,'ENG');" onkeyup="javascript:check_EngInt('comp','id'); return check_StrByte(this, 20);" size="13" value="" maxlength="20" />
             <input name="bnt_id" type="button" value="중복확인" onclick="javascript:idChk(idc);"/></td>
          	</tr>
            </tbody>
          </table>
          <%} %>       
	</div>
 </form>
</body>
</html>