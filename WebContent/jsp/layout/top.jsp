<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.ant.common.properties.AntData"%>
<%@ page import="kda.WorkData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>
<link rel="stylesheet" type="text/css" href="css/import.css" />  

<% 
    request.setCharacterEncoding("utf-8"); 
    String g_bran=(String)session.getAttribute("G_BRAN");
    
    String T_userHomeUrl = WorkData.HOME_URL;
    String T_userHomeAdminUrl = WorkData.HOME_URL;
%>  
<script type="text/javascript">
function init(){
    var loginID = "<%=session.getAttribute("G_ID")%>";
    if (loginID == "null" || loginID == "" ) {
//      topForm.method='post'; 
//      topForm.action="login.do?method=login";
//      topForm.submit();
        window.location.href="login.do?method=login";
    }
    return;
}

function mSearch(){
    var gubun=document.getElementById("search").value;
    var sword=escape(encodeURIComponent(encodeURIComponent(document.getElementById("sWord").value)));
    window.open('memberBasic.do?method=mSearch&'+gubun+'='+sword+'&gbran='+<%=g_bran%>,'mSearch','width=1200, height=650, resizable=no, scrollbars=yes');
}

function chkEnter(){
    if(event.keyCode==13){
        var gubun=document.getElementById("search").value;
        var sword=escape(encodeURIComponent(encodeURIComponent(document.getElementById("sWord").value)));
        window.open('memberBasic.do?method=mSearch&'+gubun+'='+sword+'&gbran='+<%=g_bran%>,'mSearch','width=1200, height=650, resizable=no, scrollbars=yes');
    }
        
}

function goprint(){     
    var loginID = "<%=session.getAttribute("G_ID")%>";
//window.open("<%=AntData.DOMAIN%>/doc_form/admin_print.jsp?loginID="+loginID); 
        window.open("http://210.127.55.231:9090/doc_form/admin_print.asp?loginID="+loginID);
}




function jPage(elm){
    var seq=elm.value;
    var form=document.topForm;
     
    
    switch(seq){
    case "1":
        window.open('', 'jump');
        form.action="<%=AntData.DOMAIN%>/djemals/AdminCheck.do";
        form.target='jump';
        form.strID.value = "<%=session.getAttribute("G_ID")%>";
        form.strPWD.value = "<%=session.getAttribute("G_PW")%>";        
        form.submit();
        elm.value="0";
        break;
    case "2":
        window.open('', 'jump');
        <%-- form.action="<%=T_userHomeAdminUrl%>/_PayMent/AdminCheck.asp"; --%>
        form.action="<%=AntData.DOMAIN%>/djemals/AdminCheck.do?method=payment";
        form.target='jump';
        form.strID.value = "<%=session.getAttribute("G_ID")%>";
        form.strPWD.value = "<%=session.getAttribute("G_PW")%>";
        form.submit();
        elm.value="0";
        break;
    case "3":
        window.open('', 'jump');
        form.action="<%=T_userHomeAdminUrl%>/_edu/AdminCheck.asp";
        form.target='jump';
        form.strID.value = "<%=session.getAttribute("G_ID")%>";
        form.strPWD.value = "<%=session.getAttribute("G_PW")%>";
        form.submit();
        elm.value="0";
        break;
    case "4":
        window.open('', 'jump');
        form.action="http://www.kdaedu.or.kr/";
        form.target='jump';
        form.submit();
        elm.value="0";
        break;
    case "5":
        window.open('', 'jump');
        form.action="https://admin8.kcp.co.kr/assist/login.LoginAction.do";
        form.target='jump';
        form.submit();
        elm.value="0";
        break;
    case "6":
        window.open('', 'jump');
        form.action="http://okname.allcredit.co.kr/idcf/back/mbr/login/ID0801_MgrLogin.jsp?menu_id=7";
        form.target='jump';
        form.submit();
        elm.value="0";
        break;
    case "7":
        window.open('', 'jump');
        form.action="http://www.inames.co.kr/";
        form.target='jump';
        form.submit();
        elm.value="0";
        break;
    case "8":
        window.open('', 'jump');
        form.action="<%=AntData.DOMAIN%>/doc_form/receipt_print.jsp";
        form.target='jump';
        form.strID.value = "<%=session.getAttribute("G_ID")%>";
        form.strPWD.value = "<%=session.getAttribute("G_PW")%>";
        form.submit();
        elm.value="0";
        break;      
    case "9":
        window.open('', 'jump');
        <%-- form.action="<%=T_userHomeUrl%>/doc_form/sms_print.asp"; --%>
        form.action="<%=AntData.DOMAIN%>/djemals/AdminCheck.do?method=sms";
        form.target='jump';
        form.strID.value = "<%=session.getAttribute("G_ID")%>";
        form.strPWD.value = "<%=session.getAttribute("G_PW")%>";
        form.submit();
        elm.value="0";
        break;          
    }
}

/* 헬프 */
function help() {

    var url="<%=AntData.DOMAIN%>/help/index.jsp";
    var status ="width=1000, height=800, left=100, top=100";
    window.open(url,"help", status);
    
}

init();

</script>
</head>

<body >
<form name="topForm" method="post" >
<input type="hidden" name="strID" value="" />
<input type="hidden" name="strPWD" value="" />
<div id="wrap">
  <div id="header">
  
    <%if("Y".equals(WorkData.IS_DEV)){ %>
        <h1><a href="main.do"><img src="images/logo.gif" alt="대한영양사협회" /></a>
        <span style="font-weight:bold;margin-left:40px;color:red;">== 계발계 ==</span></h1>
    <%} else if("LOC".equals(WorkData.IS_DEV)){ %>
        <h1><a href="main.do"><img src="images/logo.gif" alt="대한영양사협회" /></a>
        <span style="font-weight:bold;margin-left:40px;color:red;">== LOCAL ==</span></h1>
    <%} else { %>
        <h1><a href="main.do"><img src="images/logo.gif" alt="대한영양사협회" /></a></h1>
    <%} %>
    
    <ul class="lnb">
      <li><a href="login.do?method=login&logout=true" ><img src="images/m_logout.gif" alt="로그아웃" /></a></li>
      <li><a href="javascript:help();"><img src="images/m_help.gif" alt="도움말" /></a></li>
      <li>
        <select name="jump" style="width:135px;" onchange="jPage(this);">
            <option value="0">-----관련사이트-----</option>
            <option value="1">홈페이지 관리</option>
            <option value="2">결제관리</option>         
            <option value="8">위생교육영수증</option>
            <!-- <option value="3">위생교육관리</option> -->          
            <option value="4">보수교육</option>
            <option value="9">SMS사용관리</option>              
            <option value="5">결제관리(KCP)</option>
            <option value="6">실명/IPIN 인증 관리</option>
            <option value="7">도메인관리(inames)</option>
        </select>
      </li> 
    </ul>
    <p class="form1">
        <select name="search" id="search">
            <option value="name">이름</option>
<!--                JUMIN_DEL -->
<!--                <option value="jumin">주민번호</option>        -->
            <option value="birth">생년월일</option>       
            <option value="licno">면허번호</option>       
            <option value="email">이메일</option>       
            <option value="compname">근무처명</option>
            <option value="memberid">아이디</option>       
        </select>
    </p>
    <p class="form2">
        <input type="text" name="sWord"  id="sWord" size="12"  onkeydown="javascript:chkEnter();"/>
        <a href="javascript:mSearch();"><img src="images/m_newopen.gif" alt="새창보기" /></a>
    </p>
    
    <div class="gnb">
      <ul>
        <li><a href="memberForm.do">회원</a>
          <ul>
            <li><a href="memberInsert.do?method=memberInsert">회원등록</a></li>
            <li><a href="memberSearch.do?method=memberSearch">조건검색</a></li>
            <li><a href="memberDues.do?method=memberDues">회비처리</a></li>
            <li><a href="memberDeposit.do?method=memberDeposit">예수금현황</a></li>
            <li><a href="memberFund.do?method=memberFund">기부&frasl;기금현황</a></li>        
            <li><a href="memberState.do?method=memberState">현황</a></li>
          </ul>
        </li>
        <li><a href="education.do?method=edutestSend">교육</a>
          <ul>
            <li><a href="education.do?method=edutestSend">교육및시험등록</a></li>
            <li><a href="education.do?method=educationSend">교육별응시현황</a></li>
            <li><a href="education.do?method=examSend">응시결과처리</a></li>          
            <li><a href="lecture.do?method=lecturedata">강사관리</a></li>               
          </ul>
        </li>
        <li><a href="license.do?method=licenseSend">자격증</a>
          <ul>
            <li><a href="license.do?method=licenseSend">시험별응시현황</a></li>
            <li><a href="license.do?method=resultSend">결과등록</a></li>
            <li><a href="license.do?method=inspectionSand">자격증심사</a></li>
            <li><a href="license.do?method=issueSand">자격증발급</a></li>
            <li><a href="license.do?method=renewalSand">자격증갱신</a></li>          
          </ul>     
        </li>
        <li><a href="document.do?method=documentSend">문서</a>
          <ul>
            <li><a href="document.do?method=documentSend">문서발송대장</a></li>
            <li><a href="document.do?method=documentAccp">문서접수대장</a></li>
            <li><a href="document.do?method=documentInside">내부결제공문대장</a></li>
            <li><a href="document.do?method=documentreport">증서발급현황</a></li>         
            <li><a href="#" onclick="javascript:goprint();">증서발급</a></li>           
            <li><a href="document.do?method=dmsendData">DM관리</a></li>           
          </ul>     
        </li>
        <li><a href="conference.do?method=conference">회의실</a>
          <ul class="right_center">
            <li><a href="conference.do?method=conference">회의실관리</a></li>
          </ul>
        </li>
        <li><a href="actul.do?method=edusearchdata">보수교육</a>
          <!-- <ul class="right_center">
            <li><a href="conference.do?method=conference">보수교육관리</a></li>
          </ul> -->
        </li>
        <li><a href="actul.do?method=actulmanageData">영양사실태신고</a>
          <ul class="right_side">
            <li><a href="actul.do?method=actullicenseData">면허정보 등록자</a></li>                
            <li><a href="actul.do?method=actulrecipientData">보수교육 미대상자</a></li>     
            <li><a href="actul.do?method=actulstatusData">신고현황</a></li> 
            <li><a href="actul.do?method=actulmanageData">신고관리</a></li>
          </ul>
        </li>
        <li class="rightLine"><a href="setupdues.do?method=setupdues">환경 설정</a>
          <ul class="right_side">
            <li><a href="setupdues.do?method=setupdues">회비 &frasl; 예수금</a></li>
            <li><a href="setupdues.do?method=setuptarget">목표 인원</a></li>
            <li><a href="setupdues.do?method=setupcertifi">자격증 종류</a></li>
            <li><a href="setupdues.do?method=setupedu">교육 및 시험명</a></li>
            <li><a href="setupdues.do?method=setupbooks">DM명</a></li>
            <li><a href="setupdues.do?method=setupsub">산하 단체명</a></li>
            <li><a href="setupdues.do?method=setupuser">사용자 등록</a></li>
            <li><a href="setupdues.do?method=comcode">공통 코드</a></li>                    
          </ul>
        </li>       
      </ul>
    </div>
  </div>
  </div>
  </form>
</body>
</html>
