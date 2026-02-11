<%@page import="org.apache.struts.tiles.taglib.GetAttributeTag"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>영양사 실태 신고</title>
<link rel="stylesheet" type="text/css" href="css/report.css" />
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ant.document.dao.documentDao"%>

<%
	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);
	
	String yy   = "";
	String name = "";
	String lic_no = "";
	String no = "";
	if(request.getAttribute("result")!= null) {
		List<Map> result = (List<Map> )request.getAttribute("result");
	if(result != null || result.size()>0) {
		yy     = result.get(0).get("yyyy")+"";		
		name   = result.get(0).get("ar_name")+"";
		lic_no = result.get(0).get("ar_lic_no")+"";
		no     = result.get(0).get("ar_no")+"";
	}
}
%> 

<script type="text/javascript">

function goSearch(form,intPage){	
	var  yyyy   	  = sForm.yyyy.value;
	var  ar_name   = escape(encodeURIComponent(sForm.ar_name.value));	
	var  ar_lic_no    = sForm.ar_lic_no.value;
	var  ar_no   	  = sForm.ar_no.value;
	
	if ( yyyy == "O" ) {
        alert("신고년도를 선택하세요.") ;
        sForm.yyyy.focus();
        return ;
    }   
    
    if ( ar_name == "" ) {
        alert("성명을 입력하세요.") ;
        sForm.ar_name.focus();
        return ;
    }   
    
    if ( ar_lic_no == "" ) {
        alert("면허번호를 선택하세요.") ;
        sForm.ar_lic_no.focus();
        return ;
    }   
    
    if ( ar_no == "" ) {
        alert("생년월일를 선택하세요.") ;
        sForm.ar_no.focus();
        return ;
    }    
	
    document.sForm.action = "actul.do?method=actulconfirmSearch&isSelect=K";
	document.sForm.submit(); 	
}

function goSearch1(form,intPage){	
	var  yyyy   	  = sForm.yyyy.value;
	var  ar_name   = escape(encodeURIComponent(sForm.ar_name.value));	
	var  ar_lic_no    = sForm.ar_lic_no.value;
	var  ar_no   	  = sForm.ar_no.value;	
	
	if ( yyyy == "O" ) {
        alert("신고년도를 선택하세요.") ;
        sForm.yyyy.focus();
        return ;
    }   
    
    if ( ar_name == "" ) {
        alert("성명을 입력하세요.") ;
        sForm.ar_name.focus();
        return ;
    }   
    
    if ( ar_lic_no == "" ) {
        alert("면허번호를 선택하세요.") ;
        sForm.ar_lic_no.focus();
        return ;
    }   
    
    if ( ar_no == "" ) {
        alert("생년월일를 선택하세요.") ;
        sForm.ar_no.focus();
        return ;
    }    
   
     document.sForm.action = "actul.do?method=actulconfirmSearch&isSelect=N";
	 document.sForm.submit(); 	
}

function golic(){	
	window.open("http://lic.mw.go.kr/search_license.jsp");	
}

</script>
 </head> 
 
 <body>
 <form id="list" name="sForm" method="post">    
   <div id="r_contents">
     <table class="bTitle" border="1" cellspacing="0" summary="영양사실태신고">        			
       <tr>           
         <td class="sTitle">영양사 실태 신고</td>              
	   </tr> 			
     </table>
     <table class="r_box" border="1" cellspacing="0" summary="신고관련안내문구">        			
       <tr>           
         <td class="r_font"><br>영양사는 대통령령으로 정하는 바에 따라 최초로 면허를 받은 후부터<br>
                            3년마다 그 실태와 취업상황 등을 보건복지부장관에게 신고하여야 합니다.<br><br> 
		</td>              
	   </tr> 			
     </table> 
     <table class="report" border="1" cellspacing="0" summary="영양사실태신고">
       <caption>신고관련</caption> 			
         <tr>		   
		   <td class="r_title" align="center">신고 년도</td>
       		   <td>
               <select  id="search" name="yyyy" value="<%=yy %>" style="width: 80px">
               <option value="O">선택</option>	
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("036".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode.equals(yy))
                					out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			}
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td> 		
		   </tr>                  
           <tr>           
             <td class="r_title" align="center">성  명</td>
             <td><input type="text" id="search" name="ar_name" size="22" value="<%=name %>" /></td>             
           </tr>
		   <tr>           
             <td class="r_title" align="center">면허 번호</td>
             <td><input type="text" id="search" name="ar_lic_no" size="14" value="<%=lic_no %>"/>
    			 <input type="button" id="name" size="12"  onclick="javascript:golic();" value="조회" /></td>   			   
           </tr> 
		   <tr>           
             <td class="r_title" align="center">생년 월일</td>
             <td><input type="text" id="search" name="ar_no" size="22" value="<%=no %>"/></td>              
           </tr> 			
     </table> 
	 <ul class="rButton">
		 <li><a href="#"><img src="images/icon_s5.gif"  onclick="javascript:goSearch1(sForm,0);" alt="상세조회" align="center"/></a></li>
    	 <li><a href="#"><img src="images/icon_s10.gif" onclick="javascript:goSearch(sForm,0);"  alt="신고확인" align="center"/></a></li>   	 			 
   	 </ul>	   
   </div><!--r_contents End-->
 </form>
 </body> 
</html>
