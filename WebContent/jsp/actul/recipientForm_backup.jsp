<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"			uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ page import="com.ant.common.properties.AntData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" href="css/adm_common.css" />
<link rel="stylesheet" type="text/css" href="css/adm_layout.css" />
<script src="js/jquery-1.8.1.min.js"></script>
<script>

	$(document).ready(function(){		
		var cal = new Date();
		var yyyy = cal.getFullYear();
		var mm = cal.getMonth() + 1;
		var dd = cal.getDate();
		var today = yyyy + "년 " + mm + "월 " + dd + "일"; 
		$("#getDate").text(today);
		
		$("#pers_year,#lic_no,#cs_con_education").keyup(function(event){
			$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );
		});
		$("#pers_year,#lic_no,#cs_con_education").focusout(function(event){
			$(this).val( $(this).val().replace(/[^0-9:\-]/gi,"") );
		});
		
	});
	
	function doActive(id){
		if(id=='1'){
			$("#cs_non_target_txt").text("기관 소재지");
		}else{
			$("#cs_non_target_txt").text("자택 소재지");
		}
	}
	
	function selectMail(txt){
		$("#mail2").val(txt);
	}
	
	function openJusoPopup() 
	{
		var pop = window.open("/post5.do?gubun=1","jusopop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	}
	
	function jusoCallBack1( roadFullAddr
			 , roadAddrPart1
			 , addrDetail
			 , roadAddrPart2
			 , engAddr
			 , jibunAddr
			 , zipNo
			 , admCd
			 , rnMgtSn
			 , bdMgtSn)
	{
		$("#pers_add").val(roadAddrPart1 + " " + addrDetail + " " + roadAddrPart2 );		
		$("#code_post").val(zipNo.substring(0, 3) + zipNo.substring(4, 7));			
	}
	
	function f_check(){
		
		var pers_name=$("#pers_name");
		var lic_no=$("#lic_no");
		var pers_year=$("#pers_year");
		var mail1=$("#mail1");
		var mail2=$("#mail2");
		var cs_con_education=$("#cs_con_education");
		var cs_secter_organ=$("#cs_secter_organ");
		var code_post=$("#code_post");
		var pers_add=$("#pers_add");
		var cs_non_target_detail=$("#cs_non_target_detail");
		var image_file=$("#image_file");
		
		if(pers_name.val()==""){
			alert("성명을 입력하세요");
			pers_name.focus();
			return false;
		}
		if(lic_no.val()==""){
			alert("면허번호를 입력하세요");
			lic_no.focus();
			return false;
		}
		if($(':radio[name="code_sex"]:checked').length < 1 ) {
			alert("성별을 선택하세요.");
			$("#auth1_1").focus();
			return false;
		}
		if(pers_year.val() ==""){
			alert("생년을 입력하세요");
			pers_year.focus();
			return false;
		}
		if(mail1.val() ==""){
			alert("메일을 입력하세요");
			mail1.focus();
			return false;
		}
		if(mail2.val() ==""){
			alert("메일을 입력하세요");
			mail2.focus();
			return false;
		}
		if(cs_con_education.val() ==""){
			alert("보수교육년도를 입력하세요");
			cs_con_education.focus();
			return false;
		}
		$("#email").val(mail1.val()+"@"+mail2.val());
		if($(':radio[name="cs_secter"]:checked').length < 1 ) {
			alert("미활동(미취업)을 선택하세요.");
			$("#auth2_1").focus();
			return false;
		}
		var secter = $(':radio[name="cs_secter"]:checked').val();
		$("#authority_premises").val(secter);		
		if(secter == "1"){
			if(cs_secter_organ.val() ==""){
				alert("활동을 입력하세요");
				cs_secter_organ.focus();
				return false;
			}
		}
		if(code_post.val() ==""){
			alert("주소를 검색하세요");
			code_post.focus();
			return false;
		}
		if(pers_add.val() ==""){
			alert("상세주소를 입력하세요");
			pers_add.focus();
			return false;
		}
		if($(':radio[name="cs_non_target"]:checked').length < 1 ) {
			alert("미대상 사유를 선택하세요.");
			$("#auth3_1").focus();
			return false;
		}
		var target = $(':radio[name="cs_non_target"]:checked').val();
		if(target == "4"){
			if(cs_non_target_detail.val() ==""){
				alert("기타 내용을 입력하세요");
				cs_non_target_detail.focus();
				return false;
			}
		}
		if(secter != "1"){
			cs_secter_organ.val("");
		}
		if(target != "4"){
			cs_non_target_detail.val("");
		}
		/* if(target == "1" || target == "2" ){
			if(image_file.val().trim() ==""){
				alert("파일을 선택하세요");
				image_file.focus();
				return false;
			}
		} */
		
		return true;
	}
	
	function f_action(){
		
		if(f_check()){
			var secter = $(':radio[name="cs_secter"]:checked').val();
			var cs_non_target = $(':radio[name="cs_non_target"]:checked').val();
			var code_sex = $(':radio[name="code_sex"]:checked').val();
			opener.dataFrm.data2_code_seq.value=recipientFrm.code_seq.value;
			opener.dataFrm.data2_pers_name.value=recipientFrm.pers_name.value;
			opener.dataFrm.data2_cs_secter.value=secter;
			opener.dataFrm.data2_cs_secter_organ.value=recipientFrm.cs_secter_organ.value;
			opener.dataFrm.data2_email.value=recipientFrm.email.value;
			opener.dataFrm.data2_code_post.value=recipientFrm.code_post.value;
			opener.dataFrm.data2_pers_add.value=recipientFrm.pers_add.value;
			opener.dataFrm.data2_cs_non_target.value=cs_non_target;
			opener.dataFrm.data2_cs_non_target_detail.value=recipientFrm.cs_non_target_detail.value;
			opener.dataFrm.data2_lic_no.value=recipientFrm.lic_no.value;
			opener.dataFrm.data2_pers_year.value=recipientFrm.pers_year.value;
			opener.dataFrm.data2_authority_premises.value=recipientFrm.authority_premises.value;
			opener.dataFrm.data2_code_sex.value=code_sex;
			opener.dataFrm.data2_cs_con_education.value=recipientFrm.cs_con_education.value;
			
			opener.dataFrm.data2_pers_hp.value = recipientFrm.pers_hp.value;
			opener.recipientFormProc();		
			self.close();
		}
	}
	
	function f_cancel(){
		self.close();
	}
	
	function fileDown(){
		var fileUrl = ${result[0].cs_attachments_url};
		var fileName = ${result[0].cs_attachments};
		if(fileUrl == null || fileUrl == ""){
			alert("첨부된 파일이 없습니다.");
			return;
		}else{
			location.href="<%=AntData.DOMAIN%>/work/research/recipient/recipietnDown.do?fileUrl="+fileUrl+"&fileName="+fileName;
		}
		
	}


</script>
</head>
<body> 

<form name="recipientFrm">
<input type="hidden" name="code_seq" id="code_seq" value="${result[0].code_seq}">
					<div class="box t2 bgs p20 text" >
						<div class="area">
							<h5 class="title i_b_t6 bold s1">보수교육 미대상자 <mark class="cm">신청서</mark></h5>
							<div class="box t1 r5 mt10 p10">
								<h6 class="title s1 i_b_t3"><mark class="bold">미대상자 인적사항</mark></h6>
								<div class="form mt10 line bt bcm">
									<div class="f_wrap line bb p8">
										<div class="f_field div2">
											<label for="pers_name" class="ff_title i_b"><strong>성명..</strong></label>
											<div class="ff_wrap ml130">
												<div class="area">
													<input type="text" class="input t1 w200" name="pers_name" id="pers_name" value="${result[0].pers_name}" maxlength="20">
												</div>
											</div>
										</div>
										<div class="f_field div2">
											<label for="lic_no" class="ff_title i_b"><strong>면허번호</strong></label>
											<div class="ff_wrap ml130">
												<div class="area">
													<input type="text" class="input t1 w200" name="lic_no" id="lic_no" value="${result[0].lic_no}" maxlength="6">
												</div>
											</div>
										</div>
									</div>
									<div class="f_wrap line bb p8">
										<div class="f_field div2">
											<div class="ff_title i_b"><strong>성별</strong></div>
											<div class="ff_wrap ml130 text">
												<div class="area">
													<input type="radio" class="mr5" name="code_sex" id="auth1_1" value="1" <c:if test="${result[0].code_sex == '1'}"><c:out value="checked"/></c:if> />
													<label for="auth1_1" class="mr20">남자</label>
													<input type="radio" class="mr5" name="code_sex" id="auth1_2" value="0" <c:if test="${result[0].code_sex == '0'}"><c:out value="checked"/></c:if> />
													<label for="auth1_2" class="mr20">여자</label>
												</div>
											</div>
										</div>
										<div class="f_field div2">
											<label for="pers_year" class="ff_title i_b"><strong>생년</strong></label>
											<div class="ff_wrap ml130">
												<div class="area">
													<input type="text" class="input t1 fl w100" name="pers_year" id="pers_year" value="${result[0].pers_year}" maxlength="4">
													<p class="fl text cp s1 ml10">예) 2015</p>
												</div>
											</div>
										</div>
									</div>
									<div class="f_wrap line bb p8">
										<div class="f_field div1">
											<label for="mail1" class="ff_title i_b"><strong>이메일</strong></label>
											<input type="hidden" name="email" id="email" value="${result[0].email}">
											<c:set var="arrMail" value="${fn:split(result[0].email,'@')}" />
											<div class="ff_wrap ml130">
												<div class="area">
													<input type="text" class="input t1 fl w150" id="mail1" maxlength="20" value="${arrMail[0] }"/>
													<label for="mail2" class="fl text ml10 mr10">@</label>
													<input type="text" class="input t1 fl w150" id="mail2" maxlength="20" value="${arrMail[1] }"/>
													<label for="mail3" class="ti">이메일 입력</label>
													<select class="select t2 fl w150 ml5" id="inputid6" onchange="selectMail(this.value);">
														<option value="" >직접입력</option>
													 	<c:forEach var="result" items="${getEmail}" varStatus="status">
														<option value="<c:out value="${result.detcodename}"/>" <c:if test="${result.detcodename==mail[1]}"> selected="selected"</c:if>  ><c:out value="${result.detcodename}"/></option>														
														</c:forEach>
													</select>
												</div>
											</div>
										</div>
									</div>
									<div class="f_wrap line bb p8">
										<div class="f_field div2">
											<div class="ff_title i_b"><strong>현재근무처</strong></div>
											<div class="ff_wrap ml130 ">
												<div class="area">
													<ul>
														<li class="fl w240">
															<input type="radio" class="fl mt10 mr5" name="cs_secter" id="auth2_1" value="1" <c:if test="${result[0].cs_secter == '1'}"><c:out value="checked"/></c:if> onclick="doActive(this.value);" />
															<input type="hidden" name="authority_premises" id="authority_premises" value="${result[0].authority_premises}">
															<label for="auth2_1" class="fl text">있음</label>
															<input type="text" name="cs_secter_organ" id="cs_secter_organ" value="${result[0].cs_secter_organ}" class="input t1 fl w150 ml10" maxlength="20">
														</li>
														<li class="fl w240">
															<input type="radio" class="fl mt10 mr5" name="cs_secter" id="auth2_2" value="0" <c:if test="${result[0].cs_secter == '0'}"><c:out value="checked"/></c:if> onclick="doActive(this.value);"/>
															<label for="auth2_2" class="fl text">없음</label>
														</li>
													</ul>
												</div>
											</div>
										</div>
										<div class="f_field div2">
											<label for="pers_hp" class="ff_title i_b"><strong>전화번호</strong></label>
											<div class="ff_wrap ml130">
												<div class="area">

													<input name="pers_hp" id="pers_hp" class="input t1 fl w100"  maxlength="11" value="${result[0].pers_hp}"/>
													<!-- <p class="fl text cp s1 ml10">예) 01012345678</p> -->

												</div>
											</div>
										</div>
									</div>
									<div class="f_wrap line bb p8">
										<div class="f_field div1">
											<label for="code_post" class="ff_title i_b">
											<strong id="cs_non_target_txt">
											<c:if test="${result[0].cs_secter == '1'}">
											기관 소재지
											</c:if>
											<c:if test="${result[0].cs_secter == '0'}">
											자택 소재지
											</c:if>
											</strong>
											</label>
											<div class="ff_wrap ml130">
												<div class="area">
													<input type="text" name="code_post" id="code_post" value="${result[0].code_post}" class="input t1 fl w200" readonly="readonly" onclick="openJusoPopup()">
													<a href="javascript:openJusoPopup();" class="btn form t1 fl ml5">주소검색</a>
													<p class="fl ml10 text cp">(- 없이 한 칸으로 만들어주세요.)</p>
												</div>
												<div class="area mt5">
													<label for="pers_add" class="ti">상세주소</label>
													<input type="text" name="pers_add" id="pers_add" value="${result[0].pers_add}" class="input t1" readonly="readonly" onclick="openJusoPopup()">
												</div>
											</div>
										</div>
									</div>
									
									<div class="f_wrap line bb p8">
										<div class="f_field div2">
											<label for="pers_year" class="ff_title i_b"><strong>보수교육 미대상자 신청연도</strong></label>
											<div class="ff_wrap ml170">
												<div class="area">
													 <input type="text" name="cs_con_education" id="cs_con_education" value="${result[0].cs_con_education}" class="input t1 fl w100" maxlength="4"> 
												<!--	<input type="text" name="cs_con_education" id="cs_con_education" value="2013" class="input t1 fl w100" maxlength="4">-->
													<p class="fl text cp s1 ml10">예) 2013</p>
												</div>
											</div>
										</div>
									</div>
										
									
									
								</div>
								<h6 class="title s1 i_b_t3 mt30"><mark class="bold">미대상 사유</mark></h6>
								<div class="form mt10 line bt bcm">
									<div class="f_wrap line bb p8">
										<div class="f_field div1">
											<div class="ff_title i_b"><strong>사유선택</strong></div>
											<div class="ff_wrap ml130 text">
												<div class="area">
													<ul>
														<li>
															<input type="radio" class="mr5" name="cs_non_target" id="radio3_1" value="1" <c:if test="${result[0].cs_non_target == '1'}"><c:out value="checked"/></c:if> />

															<label for="radio3_1">1. 다른분야 근무, 국민영양관리법에 의한 영양사 보수교육 대상 근무처(보건소, 보건지소,<br/> <span class="ml30"></span><span class="ml5"></span>의료기관, 집단급식소, 육아지원종합센터, 어린이급식관리지원센터, 건강기능식품판매업소)  <br/> <span class="ml30"></span><span class="ml5"></span>외에 근무하고 있음.</label>

															<p class="area text cp"><span class="ml30"></span><span class="ml5"></span><span class="ml2"></span>※ 첨부서류 (당해년도 1월부터 12월까지 기간이 명시된 재직증명서 또는 경력증명서 )</p>
														</li>
														<li class="mt5">
															<input type="radio" class="mr5" name="cs_non_target" id="radio3_2" value="2" <c:if test="${result[0].cs_non_target == '2'}"><c:out value="checked"/></c:if> />
															<label for="radio3_2">2. 대학원 진학 등 학업 계속 상태로 미취업 상태임.</label>
															<p class="area text cp"><span class="ml30"></span><span class="ml5"></span><span class="ml2"></span>※ 첨부서류 (당해년도 1월부터 12월까지 기간이 명시된 재학증명서 또는 졸업증명서 )</p>
														</li>
														<li class="mt5">
															<input type="radio" class="mr5" name="cs_non_target" id="radio3_3" value="3" <c:if test="${result[0].cs_non_target == '3'}"><c:out value="checked"/></c:if> />	
															<label for="radio3_3">3. 미취업, 대학 재학중, 면허미취득, 면허 취득 후 임신, 출산 등으로 인한 퇴직, 취업경험 없음 등</label>
															<p class="area text cp"><span class="ml30"></span><span class="ml5"></span><span class="ml2"></span>※ 첨부서류 (미제출)</p>
														</li>
														<li class="mt5">
															<input type="radio" class="mr5" name="cs_non_target" id="radio3_4" value="4" <c:if test="${result[0].cs_non_target == '4'}"><c:out value="checked"/></c:if> />
															<label for="radio3_4">4. 기타</label>
															<input type="text" name="cs_non_target_detail" id="cs_non_target_detail" value="${result[0].cs_non_target_detail}" class="input t1 w400 ml10" maxlength="90">
															<p class="area text cp"><span class="ml30"></span><span class="ml5"></span><span class="ml2"></span>※ 첨부서류 (미제출)</p>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="box t1 r5 mt10 p20 text small">
									<div class="area">
										<p class="mb20">본인은 상기 사유로 영양사 보수교육 미대상자 신청서를 제출하며, 상기의 내용이 틀림없음을 서약합니다.</p>
										<p class="right" id="getDate"></p>
										<p class="right">신고인 <strong class="bold cm">${result[0].pers_name}</strong></p>
										<p class="mt10 center"><strong class="big s1 bold cm">대한영양사협회장</strong> 귀하</p>
									</div>
								</div>
								<div class="form mt10 line bt bcm">
									<div class="f_wrap line bb p8">
										<div class="f_field div1">
											<label for="bbs_file1" class="ff_title i_b"><strong>첨부서류1</strong></label>
											<div class="ff_wrap ml130">
												<div class="area">
													<a href="javascript:fileDown();"	>${result[0].cs_attachments}</a>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form mt10 line bt bcm">
									<div class="f_wrap line bb p8">
										<div class="f_field div1">
											<label for="bbs_file2" class="ff_title i_b"><strong>첨부서류2</strong></label>
											<div class="ff_wrap ml130">
												<div class="area">
													${result[0].cs_attachments2}
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="form mt10 line bt bcm">
									<div class="f_wrap line bb p8">
										<div class="f_field div1">
											<label for="bbs_file3" class="ff_title i_b"><strong>첨부서류3</strong></label>
											<div class="ff_wrap ml130">
												<div class="area">
													${result[0].cs_attachments3}
												</div>
											</div>
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>
</form>					
					<div class="btn_wrap mt30">
						<ul>
							<li><a href="javascript:f_action();" class="btn big t1">제출</a></li>
							<li><a href="javascript:f_cancel();" class="btn big">취소</a></li>
						</ul>
					</div>
					<div class="box t1 r5 mt40 p20 text s1 small">
						<div class="area">
							<ul>
								<li>※ 증빙서류는 <mark class="bold ck">기관장 직인을 포함</mark>하고, 반드시 <mark class="bold ck">발행일자가 명시</mark>되어 있어야 승인됩니다.</li>
								<li>※ 보수교육 미대상자 확인은 당해 연도에 한하므로, 동일한 사유에 해당하더라도 <mark class="bold ck">실태신고가 있는 해에 신고해야 하는 보수교육<br/><span class="ml15"></span> 마다 미대상자 신청</mark>을 해야 합니다.</li>
								<li>※ 보수교육 미대상자는 보수교육 미대상자를 신청하고 <mark class="bold ck">“접수완료”가 확인된 후 초기화면으로 가서실태신고</mark>를 하시면 됩니다.</li>
							</ul>
						</div>
					</div>
				
</body>
</html>
