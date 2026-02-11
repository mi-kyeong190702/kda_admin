<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String logid="";
	if(session.getAttribute("G_NAME")!=null){
		logid=session.getAttribute("G_NAME").toString();
	}
	String errMsg="";
	if(request.getAttribute("errMsg")!=null){
		errMsg=request.getAttribute("errMsg").toString();
	}
	
	String msg ="";
	if(request.getAttribute("msg")!=null){
		msg=request.getAttribute("msg").toString();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" href="css/adm_common.css" />
<link rel="stylesheet" type="text/css" href="css/adm_layout.css" />
<script src="js/jquery-1.8.1.min.js"></script>
<script type="text/javascript">

var msg="<%=msg%>";
if(msg!=null && msg!=""){
	alert(msg);
	opener.goSearch(opener.document.sForm,0);	
	self.close();
}

document.onreadystatechange=function(){
    if(document.readyState == "complete")initR();
};

function initR(){
	var logid="<%=logid%>";
	var errMsg="<%=errMsg%>";
	if(errMsg.length>5){
		alert(errMsg);
		return;
	}
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		location.href="login.do?method=login";
	}
	
}


	$(document).ready(function(){
		
	});
	

</script>
</head>
<body> 
<div class="w700 ac pt40 pb40">
	<div class="text ssmall ca">
		■ 국민영양관리법 시행규칙 [별지 제8호의2서식] <신설 2015.5.19.>
	</div>
	<div class="center p20">
		<h1 class="title t2 s1 bold">영양사의 실태 등 신고서</h1>
	</div>
	<div class="area text ssmall">
		<div class="fl">
			※ 뒤쪽의 작성방법을 읽고 작성하시기 바라며, [&nbsp;&nbsp;&nbsp;&nbsp;]에는 해당되는 곳에 ●표를 합니다.
		</div>
		<div class="fr">
			(앞쪽)
		</div>
	</div>
	<table class="table t4 text ssmall">
		<tr>
			<th rowspan="4" width="15%" class="bold">기본<br />인적사항</th>
			<td class="left" width="42%">성명 <span class="ml100">${result[0].pers_name }</span></td>
			<td class="left">면허번호 <span class="ml100">${result[0].lic_no }</span></td>
		</tr>
		<tr>
			<td class="left">면허발급 연월일 <span class="ml100">${result[0].lic_print_dt }</span></td>
			<td class="left">E-Mail <span class="ml100">${person[0].email }</span></td>
		</tr>
		<tr>
			<td colspan="2" class="left">주소 <span class="ml100">(${person[0].code_post }) ${person[0].pers_add } ${person[0].pers_add_detail }</span></td>
		</tr>
		<tr>
			<td class="left">일반전화 <span class="ml100">${person[0].pers_tel }</span></td>
			<td class="left">휴대전화 <span class="ml100">${person[0].pers_hp }</span></td>
		</tr>
	</table>
	<table class="table t4 text ssmall mt5">
		<tr>
			<th rowspan="4" width="15%" class="bold">취업상황</th>
			<td colspan="2" class="left"><strong class="bold">활동 여부</strong><br />
			<c:choose>
				<c:when test="${result[0].ar_code_part == '1'}">
					[ ● ]집단급식소 근무&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;&nbsp;&nbsp;&nbsp;] 집단급식소 외 근무&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;&nbsp;&nbsp;&nbsp;] 미활동
				</c:when>
				<c:when test="${result[0].ar_code_part == '2'}">
					[&nbsp;&nbsp;&nbsp;&nbsp;]집단급식소 근무&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[ ● ] 집단급식소 외 근무&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;&nbsp;&nbsp;&nbsp;] 미활동
				</c:when>
				<c:when test="${result[0].ar_code_part == '3'}">
					[&nbsp;&nbsp;&nbsp;&nbsp;]집단급식소 근무&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;&nbsp;&nbsp;&nbsp;] 집단급식소 외 근무&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[ ● ] 미활동
				</c:when>
			</c:choose>
			</td>
		</tr>
		<tr>
			<td width="15%" class="bold">집단급식소<br />근무자</td>
			<td class="left">
				근무 집단급식소 구분
				<table class="table t5 mb5">
					<tr>
						<td width="25%">
						<c:if test="${result[0].cs_kitchen_code == '01'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '01'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						산업체
						</td>
						<td width="25%">
						<c:if test="${result[0].cs_kitchen_code == '02'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '02'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						병원
						</td>
						<td width="25%">
						<c:if test="${result[0].cs_kitchen_code == '03'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '03'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						학교
						</td>
						<td width="25%">
						<c:if test="${result[0].cs_kitchen_code == '04'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '04'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						사회복지시설
						</td>
					</tr>
					<tr>
						<td>
						<c:if test="${result[0].cs_kitchen_code == '05'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '05'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						어린이집
						</td>
						<td>
						<c:if test="${result[0].cs_kitchen_code == '06'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '06'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						유치원
						</td>
						<td>
						<c:if test="${result[0].cs_kitchen_code == '07'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '07'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						교정시설
						</td>
						<td>
						<c:if test="${result[0].cs_kitchen_code == '08'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '08'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						군대
						</td>
					</tr>
					<tr>
						<td>
						<c:if test="${result[0].cs_kitchen_code == '09'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '09'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						경찰서
						</td>
						<td>
						<c:if test="${result[0].cs_kitchen_code == '11'}">[ ● ]</c:if>
						<c:if test="${result[0].cs_kitchen_code != '11'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						소방서
						</td>
						
						<td></td>
						<td></td>
					</tr>
				</table>
				근무처명 및 주소(공동관리의 경우 모두 기재)
				<table class="table t5">
					<tr>
						<th width="30%">집단급식소명</th>
						<th>주소</th>
					</tr>
					<tr>
						<td>1. ${result[0].cs_kitchen_name1 }</td>
						<td>1.
						<c:if test="${result[0].ar_code_part == '1'}"> 
						(${result[0].code_post1 }) ${result[0].cs_kitchen_add11 } ${result[0].cs_kitchen_add12 }
						</c:if>
						</td>
					</tr>
					<c:choose>
						<c:when test="${result[0].ar_code_part == '1' && (result[0].cs_kitchen_code =='03' || result[0].cs_kitchen_code =='05' || result[0].cs_kitchen_code =='06')}">
							<tr>
								<td>2. ${result[0].cs_kitchen_name2 }</td>
								<td>2. (${result[0].code_post2 }) ${result[0].cs_kitchen_add21 } ${result[0].cs_kitchen_add22 }</td>
							</tr>
							<tr>
								<td>3. ${result[0].cs_kitchen_name3 }</td>
								<td>3. (${result[0].code_post3 }) ${result[0].cs_kitchen_add31 } ${result[0].cs_kitchen_add32 }</td>
							</tr>
							<tr>
								<td>4. ${result[0].cs_kitchen_name4 }</td>
								<td>4. (${result[0].code_post4 }) ${result[0].cs_kitchen_add41 } ${result[0].cs_kitchen_add42 }</td>
							</tr>
							<tr>
								<td>5. ${result[0].cs_kitchen_name5 }</td>
								<td>5. (${result[0].code_post5 }) ${result[0].cs_kitchen_add51 } ${result[0].cs_kitchen_add52 }</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>2.</td>
								<td>2.</td>
							</tr>
							<tr>
								<td>3.</td>
								<td>3.</td>
							</tr>
							<tr>
								<td>4.</td>
								<td>4.</td>
							</tr>
							<tr>
								<td>5.</td>
								<td>5.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</td>
		</tr>
		<tr>
			<td class="bold">집단급식소<br />외 근무자</td>
			<td class="left">
				근무 기관 구분
				<table class="table t5 mb5">
					<tr>
						<td width="50%">
						<c:if test="${result[0].ncs_kitchen_code == '01'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '01'}">	[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
						건강기능식품 판매업소
						</td>
						<td width="50%">
						<c:if test="${result[0].ncs_kitchen_code == '02'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '02'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						교육 및 연구기관
						</td>
					</tr>
					<tr>
						<td>
						<c:if test="${result[0].ncs_kitchen_code == '03'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '03'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						어린이 급식관리지원센터
						</td>
						<td>
						<c:if test="${result[0].ncs_kitchen_code == '04'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '04'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						위탁급식전문업체(행정업무)
						</td>
					</tr>
					<tr>
						<td>
						<c:if test="${result[0].ncs_kitchen_code == '05'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '05'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						보건소
						</td>
						<td>
						<c:if test="${result[0].ncs_kitchen_code == '06'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '06'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						행정직 공무원
						</td>
					</tr>
					<tr>
						<td>
						<c:if test="${result[0].ncs_kitchen_code == '07'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '07'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						육아종합지원센터
						</td>
						<td>
						<c:if test="${result[0].ncs_kitchen_code == '08'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '08'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
						식품제조 가공업소
						</td>
					</tr>
					<tr>
						<td>
						<c:if test="${result[0].ncs_kitchen_code == '09'}">[ ● ]</c:if>
						<c:if test="${result[0].ncs_kitchen_code != '09'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>  
						기타 
						(<span class="ib w150">내용
						<c:if test="${result[0].ncs_kitchen_code == '09'}">
						 &nbsp;&nbsp;&nbsp;${result[0].ncs_kitchen_ret_code}
						</c:if>
						</span>	)
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="2">근무처명 &nbsp;&nbsp;&nbsp;${result[0].ncs_kitchen_name1 }</td>
					</tr>
					<tr>
						<td colspan="2">주소 
						<c:if test="${result[0].ar_code_part == '2'}">
						&nbsp;&nbsp;&nbsp;(${result[0].code_post6 }) ${result[0].ncs_kitchen_add61 } ${result[0].ncs_kitchen_add62 }
						</c:if>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td class="bold">미활동</td>
			<td class="left">
				미활동 사유<br />
				<c:if test="${result[0].cs_kitchen_act_code == '1'}">[ ● ]</c:if>
				<c:if test="${result[0].cs_kitchen_act_code != '1'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
				임신&middot;출산&middot;육아&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:if test="${result[0].cs_kitchen_act_code == '2'}">[ ● ]</c:if>
				<c:if test="${result[0].cs_kitchen_act_code != '2'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
				일시적 폐업&middot;실직&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:if test="${result[0].cs_kitchen_act_code == '3'}">[ ● ]</c:if>
				<c:if test="${result[0].cs_kitchen_act_code != '3'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
				질병&middot;사고 등&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:if test="${result[0].cs_kitchen_act_code == '4'}">[ ● ]</c:if>
				<c:if test="${result[0].cs_kitchen_act_code != '4'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if> 
				해외체류<br />
				<c:if test="${result[0].cs_kitchen_act_code == '5'}">[ ● ]</c:if>
				<c:if test="${result[0].cs_kitchen_act_code != '5'}">[&nbsp;&nbsp;&nbsp;&nbsp;]</c:if>
				기타 (<span class="ib w150">내용
				<c:if test="${result[0].cs_kitchen_act_code == '5'}">
				 &nbsp;&nbsp;&nbsp;${result[0].cs_kitchen_ret_code}
				</c:if>
				</span>)
			</td>
		</tr>
	</table>
	<table class="table t4 text ssmall mt5">
		<tr>
			<th rowspan="4" width="15%" class="bold">보수교육 및<br />신고 관련</th>
			<td class="left">최근 신고연도
			<c:set var="length" value="${fn:length(recentYearRow)}"/>
			<c:if test="${length<1 }">신고 이력이 없습니다.</c:if>
			<c:forEach var="list" items="${recentYearRow}">
				&nbsp;&nbsp;${list.report_year } 년
			</c:forEach>
			</td>
		</tr>
		<tr>
			<td class="left">
				보수교육 이수상황<br />
				<p class="center">
					총 (
					<c:set var="comma" value="0"></c:set>																
					<c:forEach var="list2" items="${recentRow}" varStatus="status">
					${list2.cs_con_education }년 ${list2.AR_FINISH_POINT }
					<c:if test="${comma == 0 }">,</c:if><c:set var="comma" value="1"/>
					<c:if test="${comma != 0 }"></c:if>
					</c:forEach>
					)시간 이수의무 중 
					(
					<c:set var="comma" value="0"></c:set>																
					<c:forEach var="list3" items="${recentRow}" varStatus="status">
						${list3.cs_con_education }년 ${list3.AR_STATE_KR} ${list3.AR_MARKS_POINT }
						<c:if test="${comma == 0 }">,</c:if><c:set var="comma" value="1"/>
						<c:if test="${comma != 0 }"></c:if>
					</c:forEach>
					)시간 이수
				</p>
			</td>
		</tr>
	</table>
	<div class="text ssmall ca mt10">
		「국민영양관리법」 제20조의2제1항 및 같은 법 시행령 제4조의2제1항에 따라 위와 같이 신고합니다.
	</div>
	<div class="text ssmall right mt10">
		<span class="ib w50 right">${year }</span> 년 <span class="ib w30 right">${month }</span> 월 <span class="ib w30 right">${date }</span> 일
	</div>
	<div class="text ssmall right mt10">
		신고인 <span class="ib w100 right">${result[0].pers_name }</span> (서명 또는 인)
	</div>
	<div class="text small mt10">
		대한영양사협회장 귀하
	</div>
	<table class="table t4 text ssmall mt10">
		<tr>
			<th rowspan="4" width="15%" class="bold">첨부서류</th>
			<td class="left">1. 보수교육 이수증(이수자만 첨부합니다)<br />
			2. 보수교육 면제 확인서(해당자만 첨부합니다)</td>
		</tr>
	</table>
</div>

</body>
</html>
<script type="text/javascript">
window.print();
</script>
