<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회 DM발송</title>

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
	String pers_name= "";//검색용 회원 이름(조건검사에서 필요함)
	String param	= "";//선택일때, (DM, 쪽지에는 code_pers, 문자에는 pers_hp), 전체일때 조건 (검색을 위함)
	
	String edutest_name1	= "";	//교육의 내용
	String oper_name1		= "";	//교육의 이름
	String oper_comp_name11	= "";	//교육의 근무처명
	
	if(request.getAttribute("register"	)!= null )	register	= request.getAttribute("register"	)+"";
	if(request.getAttribute("chk"		)!= null )	chk			= request.getAttribute("chk"		)+"";
	if(request.getAttribute("param"		)!= null )	param		= request.getAttribute("param"		)+"";
	if(request.getAttribute("ncount"	)!= null )	ncount		= request.getAttribute("ncount"		)+"";
	
	//조건검색에 사용
	if(request.getAttribute("pers_name"	)!= null )	pers_name 	= request.getAttribute("pers_name"	)+"";
	//교육에 사용하는것
	if(request.getAttribute("edutest_name1"		)!= null )	edutest_name1 	= request.getAttribute("edutest_name1"		)+"";
	if(request.getAttribute("oper_name1"		)!= null )	oper_name1 		= request.getAttribute("oper_name1"			)+"";
	if(request.getAttribute("oper_comp_name11"	)!= null )	oper_comp_name11= request.getAttribute("oper_comp_name11"	)+"";
	
	//위에는 고정, 아래는 쪽지와 문자에 따라 변홤(pers_hp를 받느냐, code_pers를 받느냐 차이)
	
	String pers_hp	= "";//휴대폰번호 모음 (선택시 all 말고 화면에 표출용)
	if(request.getAttribute("pers_hp"	)!= null )	pers_hp		= request.getAttribute("pers_hp"	)+"";
	
	if("CHK".equals(chk)) sout = pers_hp;	//출력을 아이디 보낸다.
	if("ALL".equals(chk)) sout = "ALL";		//출력을 ALL 보낸다.
	
	//조건검색, 회비처리 구분 
	String from		= "";
	from	= request.getAttribute("from")+"";
%>

<script>

function fn_sendDM(form) {

	//필수조건 체크
	//저장을 위해 보낼 값
	
	//보낼값 만듬
	var param='';
	param+="&chk="+"<%=chk%>";
	param+="&register="+escape(encodeURIComponent("<%=register%>"			));
	
	//조건검색 일때는 이름이 필요하므로 아래코드 사용
	if( "<%=pers_name%>".length			> 0)	param+="&pers_name="		+escape(encodeURIComponent("<%=pers_name%>"			));
	//교육에 사용하는것
	if( "<%=edutest_name1%>".length		> 0)	param+="&edutest_name1="	+escape(encodeURIComponent("<%=edutest_name1%>"		));
	if( "<%=oper_name1%>".length		> 0)	param+="&oper_name1="		+escape(encodeURIComponent("<%=oper_name1%>"		));
	if( "<%=oper_comp_name11%>".length	> 0)	param+="&oper_comp_name11="	+escape(encodeURIComponent("<%=oper_comp_name11%>"	));
	
	//넘어온 전체 조건 혹은 회원번호 전화번호를 넘김다.
	param+="<%=param%>";
	
	//버튼 잠금
	form.btn.disabled=true;
	
// 	alert(param);return;
	
	//조건검색, 회비처리에 따라 포워딩 바꿈
	var from = "<%=from%>";
	var url="";

	if( from == "memberDues"	) url="memberDues.do?method=sendDMLink"		+param;
	if( from == "memberSearch"	) url="memberSearch.do?method=sendDMLink"	+param;
	if( from == "education"		) url="education.do?method=sendDMLink"		+param;
	
	form.action = url;
	form.submit();
}

function fn_close(form) {
	window.close();
}


function onload(form) {

}
</script>

<head></head>

<body onload="onload(memberInputForm)">



<form name="memberInputForm" method="post" action="">
	<div id="T_contents">
	  <div class="mSearch_01">
	  
     <table class="mTitle" cellspacing="0" summary=" ">        			
       <tr>           
         <td><img src="images/logo.gif" alt=" " /></td>
       </tr>
     </table><br>
     
        <table class="tbl_type" border="1" cellspacing="0" summary="회원증 출력" style="width:260px">
          <caption>회원증 출력</caption>          
			<tbody>
            <tr>           
              <td class="mtbl" style="width:165px;background:#F5FAFA">DM 발송 총 개수</td>
              <td class="mtbl1"style="width:165px">
              	<%=ncount%>
              </td>
            </tr>                  
            </tbody>
          </table>
            
		<tr>
			<br>
		
			<td style="background-color:#d4d4d4;">
				<span style="color:red;font-size:12px;">*주소(우편번호)가 있는 회원만 발송됩니다.</span><p>
<!--  제외 20130202  
				<span style="color:red">*DM 발송은 인당 매달 9회까지 가능합니다.</span><p>
 -->
			</td>
		</tr> 
		<br>
		

               
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input align=center name="bnt" type="button" id="btn" value="출력" onclick="javascript:fn_sendDM(memberInputForm);"/>

   
	  </div><!--mSearch_01 End-->
	</div><!--M_contents End-->
 </form>
 
</body>




