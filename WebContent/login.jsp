<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import="kda.WorkData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title>

<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link href='css/import.css' rel="stylesheet"    type="text/css" />

<script src="js/jquery-1.8.0.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.1.custom.min.js" charset="utf-8"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<link rel="shortcut icon" href="images/favicon.ico">
<style type="text/css">
body {
    background: #c0d1ea;
    margin: 0px;
}

div#wrapper {
    width: 1000px;
    height: 547px;
    margin: 0 auto;
    border: 0px solid silver;
    background-image: url(images/intro_text.gif);
    background-repeat: no-repeat;
}

.login_infoedit {
    font-size: 12px;
    color: #888888;
    border: 1px solid #d1d1d17;
    background-color: #f8f8f8;
}
</style>
<%
    String logout=StringUtil.nullToStr("", request.getParameter("logout"));
    if(logout.equals("true")){
        session.invalidate();
    }
%>
<script type="text/javascript">           
 
function init(){
    var loginStatus = "<%=request.getAttribute("loginStatus")%>";
    if (loginStatus != "null" ) {
        if(loginStatus =="Not Found your ID!") {
            alert("존재하지 않는 아이디입니다.");
        }else if(loginStatus =="No Match your Password!"){
         alert("비밀번호가 일치하지 않습니다.");
        }else if(loginStatus =="Login with same id!"){
            alert("이미 동일 아이디로 접속해있습니다.");
        }else if(loginStatus=="Pers state is not normal!"){
            alert("회비를 납입하지 않으셨거나 회원유효기간이 만료 되었습니다.\n회비를 입금하여 주시면 사용하실 수 있습니다.");
        } 
    }
    frmLogin.u_id.focus();
    if(frmLogin.u_id.value.length>0)
        frmLogin.u_password.focus();
}

function fnLogin() {
	 
    if(!frmLogin.u_id.value){
        alert("아이디를 입력하세요");
        frmLogin.u_id.focus();
        return
    }
    if(!frmLogin.u_password.value){
        alert("비밀번호를 입력하세요");
        frmLogin.u_password.focus();
        return
    }

    save();
    frmLogin.method='post'; 
    frmLogin.action="login.do?method=otp";
    frmLogin.submit();

}   

function fnPwssWdLost(){
//  window.open('idCheck.do','width=500, height=400, resizable=no');
        alert("관리자의 요청 및 정보보안 정책에 의해, 이곳에서 아이디를 찾으실 수 없습니다. 사용자 계정을 관리하는 관리자에게 문의바랍니다. !!!");
    return;
}


/**
 * 쿠키설정
 */
function setCookie(name, value, expiredays) {
    var todayDate = new Date();
    todayDate.setDate(todayDate.getDate() + expiredays);
    document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";
}
 
function save() {
    if (frmLogin.check.checked) {
        setCookie("u_id", frmLogin.u_id.value, 20);
    }else{
        deleteGTCookie("u_id");
    }
    return true;
}
 
/**
 * 쿠키값 추출
 */
function getCookie(name) {
    var nameOfCookie = name + "=";
    var x = 0;
    while (x <= document.cookie.length)
    {
        var y = (x + nameOfCookie.length);
            if (document.cookie.substring(x, y) == nameOfCookie) {
            if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
                endOfCookie = document.cookie.length;
                document.forms[0].check.checked = true;
                return unescape(document.cookie.substring(y, endOfCookie));
            }
                  
            x = document.cookie.indexOf(" ", x) + 1;
            if (x == 0) break;
    }
    return "";
}
    
/**
 * 쿠키 삭제
 * @param cookieName 삭제할 쿠키명
 */
function deleteGTCookie( cookieName )
{
    var expireDate = new Date();
    //어제 날짜를 쿠키 소멸 날짜로 설정한다.
    expireDate.setDate( expireDate.getDate() - 1 );
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
    document.forms[0].check.checked = false;
}

function chkEnter(){
    if(event.keyCode==13){
        fnLogin();
    }
        
}
</script>
</head>

<body id="inbody" onLoad="init();">
    <form name="frmLogin" action="Login" >
        <div id="intro_wrap">
            <div id="intro_header">
                <p>
                    <img src="images/logo.gif" alt="대한영양사협회" />
                    
                    <%if("Y".equals(WorkData.IS_DEV)){ %>
                        <span style="font-weight:bold;margin-left:10px;color:red;">== 계발계 ==</span>
                    <%} %>
                    
                    <%if("LOC".equals(WorkData.IS_DEV)){ %>
                        <span style="font-weight:bold;margin-left:10px;color:red;">== LOCAL ==</span>
                    <%} %>

                </p>
                <p class="Intext">
                    <img src="images/intro_text.gif"
                        alt="대한영양사협회 관리자 아이디로 로그인 하시기 바랍니다." />
                </p>
            </div>
            <div id="intro_container">
                <table class="IdPw">
                    <tr>
                        <td><label><input name="u_id" property="u_id" type="text" size="20" value="" maxlength="20" tabindex='1' /> </label></td>
                        <td rowspan="2"><a href="javascript:fnLogin();"><img
                                src="images/intro_login.gif" alt="로그인버튼" />
                        </a>
                        </td>
                    </tr>
                    <tr>
                        <td><label><input name="u_password" property="u_password" type="password" size="21" value="" maxlength="20" tabindex='2'   onkeypress="javascript:chkEnter();"/> </label></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
                <ul class="idSave">
                    <li><input type="checkbox" name="check" id="saveID" />
                    </li>
                    <li>아이디저장
                    </li>
                </ul>
                <ul class="idpwLook">
                    <li></li>
                    <li><a href="javascript:fnPwssWdLost();">&bull;아이디&frasl;비밀번호찾기</a>
                    </li>
                </ul>
            </div>
            <div id="intro_footer">
                <p>
                    <img src="images/copyright.gif" alt="카피라이트" />
                </p>
            </div>
        </div>
        <!--wrap End-->

<script language='javascript'>
    document.forms[0].u_id.value = getCookie("u_id");   
</script>

    </form>
</body>
</html>



