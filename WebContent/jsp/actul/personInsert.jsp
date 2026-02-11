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
<link rel="stylesheet" type="text/css" href="css/adm_common.css" />
<link rel="stylesheet" type="text/css" href="css/adm_layout.css" />
<script src="js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">
	function uploadBtn(){
		
		
		if($("#new_pers_name").val()==''){
			alert("성명을 입력하세요");
			$("#new_pers_name").focus();
			return;
		}
		if($("#new_lic_no").val()==''){
			alert("면허번호를 입력하세요");
			$("#new_lic_no").focus();
			return;
		}
		if($("#new_lic_print_dt").val()==''){
			alert("면허발급년월일을 입력하세요");
			$("#new_lic_print_dt").focus();
			return;
		}
		
		opener.frm.new_pers_name.value=frm.new_pers_name.value;
		opener.frm.new_lic_no.value=frm.new_lic_no.value;
		opener.frm.new_lic_print_dt.value=frm.new_lic_print_dt.value;
		opener.personInsertProc();		
		self.close();
		//document.frm.action = "actul.do?method=personUptProc";
		//document.frm.submit();
	}
</script>
</head>
<body> 
<div class="w450 ac">
	<div class="box t2 mt10 p50 pt30 pb30">
		<div class="area mb20">
			<h4 class="title bold t2 s1"><mark class="cm">면허정보</mark> 등록</h4>
		</div>
		<form name="frm">
			<fieldset>
				<legend><span class="ti">LOGIN</span></legend>
				<div class="area">
					<ul>
						<li>
							<label for="data_pers_name"><span class="ti">성명</span></label>
							<input type="text" class="input login" id="new_pers_name" name="new_pers_name" value="${pers_name }"placeholder="성명" />
						</li>
						<li class="mt5">
							<label for="data_lic_no"><span class="ti">면허번호</span></label>
							<input type="text" class="input login" id="new_lic_no" name="new_lic_no" value="${lic_no}" placeholder="면허번호" maxlength="6"/>
						</li>
						<li class="mt5">
							<label for="data_lic_print_dt"><span class="ti">면허발급년월일</span></label>
							<input type="text" class="input login" id="new_lic_print_dt" name="new_lic_print_dt" value="${lic_print_dt}" placeholder="면허발급년월일" maxlength="8" />
						</li>
					</ul>
				</div>
				<div class="btn_wrap mt20">
					<ul>
						<li><a href="javascript:uploadBtn();" class="btn big t1">확인</a></li>
						<li><a href="javascript:self.close();" class="btn big">취소</a></li>
					</ul>
				</div>
			</fieldset>
		</form>
	</div>
</div>
</body>
</html>
