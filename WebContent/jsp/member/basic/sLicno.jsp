<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<% 
		request.setCharacterEncoding("utf-8"); 	  	
		List<Map> result=(List<Map>)request.getAttribute("result");
		Map map=(Map)request.getAttribute("map");
		
		String cnt=result.get(0).get("cnt").toString();
		String lic_no=map.get("lic_no").toString();
		String code_pers=map.get("code_pers").toString();
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

<script type="text/javascript">
</script>
</head>
<body>
<form name="lic" method="post" action="">
	<div id="L_contents" >
	<h5>&nbsp;&nbsp;▶면허번호 중복검사</h5>
	</div>
	  <div class="mSearch_01">
        <table class="tbl_type" border="1" cellspacing="0" summary="면허번호 중복검사">
          <caption>회원검색</caption>          
			<tbody>
            <tr>                         
              <%if("0".equals(cnt)){ %>
              <td>입력하신 면허번호는 중복되지 않았습니다.</td>
              <%} %>
             </tr>
            </tbody>
          </table>       
	</div>
 </form>
</body>
</html>