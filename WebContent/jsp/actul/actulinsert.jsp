<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>

<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/report.css" />
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

<%@page import="com.ant.document.dao.documentDao"%>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	
<%
	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();
	String ar_lic_print_dt=(new SimpleDateFormat("yyyyMMdd").format(date));	
%>

<%
	Map map = new HashMap();
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);
	
	String yy            = "";
	String yyyy_seq      = "";
	String name          = "";
	String lic_no        = "";
	String no            = "";
	String no1           = "";
	String no2           = "";
	String lic_print_dt  = "";
	String code_sex      = "";
	String email         = "";
	String add1          = "";
	String add2          = "";
	String tel_home      = "";
	String tel_job       = "";
	String tel_hp        = "";
	String code_part     = "";
	String code_big1     = "";
	String comp_name1    = "";
	String comp_add11    = "";	
	String comp_add12    = "";
	String comp_name2    = "";
	String comp_add21    = "";
	String comp_add22    = "";
	String comp_name3    = "";
	String comp_add31    = "";
	String comp_add32    = "";
	String comp_name4    = "";
	String comp_add41    = "";
	String comp_add42    = "";
	String comp_name5    = "";
	String comp_add51    = "";
	String comp_add52    = "";
	String code_big2     = "";
	String comp_name6    = "";
	String comp_add61    = "";	
	String comp_add62    = "";
	String finish_point  = "";
	String marks_point   = "";
	String add_file1     = "";
	String add_file2     = "";
	String add_file3     = "";
	String finish_yn     = "";
	String state         = "";		
			
	if(request.getAttribute("result")!= null) {
		List<Map> result = (List<Map> )request.getAttribute("result");
		if(result != null || result.size()>0) {
			yy              = result.get(0).get("yyyy")==null?""        	   :    result.get(0).get("yyyy") + "";
			yyyy_seq        = result.get(0).get("yyyy_seq")==null?""      	   :    result.get(0).get("yyyy_seq") + "";
			name            = result.get(0).get("ar_name")==null?""            :    result.get(0).get("ar_name") + "";
			lic_no          = result.get(0).get("ar_lic_no")==null?""          :	result.get(0).get("ar_lic_no") + "";
			no              = result.get(0).get("ar_no")==null?""              :    result.get(0).get("ar_no") + "";
			no1=no.substring(0,6);
			no2=no.substring(6,13);			
	 		lic_print_dt    = result.get(0).get("ar_lic_print_dt")==null?""    :    result.get(0).get("ar_lic_print_dt") + "";		
			code_sex        = result.get(0).get("ar_code_sex")==null?""        :    result.get(0).get("ar_code_sex") + "";
			email           = result.get(0).get("ar_email")==null?""           :    result.get(0).get("ar_email") + "";	
			add1            = result.get(0).get("ar_add1")==null?""            :    result.get(0).get("ar_add1") + "";		
			add2            = result.get(0).get("ar_add2")==null?""            :    result.get(0).get("ar_add2") + "";					
			tel_home        = result.get(0).get("ar_tel_home")==null?""        :    result.get(0).get("ar_tel_home") + "";				
			tel_job         = result.get(0).get("ar_tel_job")==null?""         :    result.get(0).get("ar_tel_job") + "";		
			tel_hp          = result.get(0).get("ar_tel_hp")==null?""          :    result.get(0).get("ar_tel_hp") + "";			
			code_part       = result.get(0).get("ar_code_part")==null?""       :    result.get(0).get("ar_code_part") + "";				
			code_big1       = result.get(0).get("ar_code_big1")==null?""       :    result.get(0).get("ar_code_big1") + "";				
			comp_name1      = result.get(0).get("ar_comp_name1")==null?""      :    result.get(0).get("ar_comp_name1") + "";			
			comp_add11      = result.get(0).get("ar_comp_add11")==null?""      :    result.get(0).get("ar_comp_add11") + "";					
			comp_add12      = result.get(0).get("ar_comp_add12")==null?""      :    result.get(0).get("ar_comp_add12") + "";		
			comp_name2      = result.get(0).get("ar_comp_name2")==null?""	   :    result.get(0).get("ar_comp_name2") + "";				
			comp_add21      = result.get(0).get("ar_comp_add21")==null?""      :    result.get(0).get("ar_comp_add21") + "";			
			comp_add22      = result.get(0).get("ar_comp_add22")==null?""      :    result.get(0).get("ar_comp_add22") + "";			
			comp_name3      = result.get(0).get("ar_comp_name3")==null?""      :    result.get(0).get("ar_comp_name3") + "";				
			comp_add31      = result.get(0).get("ar_comp_add31")==null?""      :    result.get(0).get("ar_comp_add31") + "";			
			comp_add32      = result.get(0).get("ar_comp_add32")==null?""      :    result.get(0).get("ar_comp_add32") + "";			
			comp_name4      = result.get(0).get("ar_comp_name4")==null?""      :    result.get(0).get("ar_comp_name4") + "";			
			comp_add41      = result.get(0).get("ar_comp_add41")==null?""      :    result.get(0).get("ar_comp_add41") + "";					
			comp_add42      = result.get(0).get("ar_comp_add42")==null?""      :    result.get(0).get("ar_comp_add42") + "";				
			comp_name5      = result.get(0).get("ar_comp_name5")==null?""      :    result.get(0).get("ar_comp_name5") + "";			
			comp_add51      = result.get(0).get("ar_comp_add51")==null?""      :    result.get(0).get("ar_comp_add51") + "";			
			comp_add52      = result.get(0).get("ar_comp_add52")==null?""      :    result.get(0).get("ar_comp_add52") + "";			
			code_big2       = result.get(0).get("ar_code_big2")==null?""       :    result.get(0).get("ar_code_big2") + "";				
			comp_name6      = result.get(0).get("ar_comp_name6")==null?""      :    result.get(0).get("ar_comp_name6") + "";		
			comp_add61      = result.get(0).get("ar_comp_add61")==null?""      :    result.get(0).get("ar_comp_add61") + "";					
			comp_add62      = result.get(0).get("ar_comp_add62")==null?""      :    result.get(0).get("ar_comp_add62") + "";				
			finish_point    = result.get(0).get("ar_finish_point")==null?""    :    result.get(0).get("ar_finish_point") + "";				
			marks_point     = result.get(0).get("ar_marks_point")==null?""     :    result.get(0).get("ar_marks_point") + "";		
			add_file1       = result.get(0).get("ar_add_file1")==null?""       :    result.get(0).get("ar_add_file1") + "";			
			add_file2       = result.get(0).get("ar_add_file2")==null?""       :    result.get(0).get("ar_add_file2") + "";		
			add_file3       = result.get(0).get("ar_add_file3")==null?""       :    result.get(0).get("ar_add_file3") + "";			
			finish_yn       = result.get(0).get("ar_finish_yn")==null?""       :    result.get(0).get("ar_finish_yn") + "";			
			state           = result.get(0).get("ar_state")==null?""           :    result.get(0).get("ar_state") + "";				
		}
	}else{
		yy      =  request.getAttribute("yy").toString();
		name    =  request.getAttribute("name").toString();		
		lic_no  =  request.getAttribute("lic_no").toString();
	}
	
	List<Map> result1 = (List<Map> )request.getAttribute("result1");	
%> 

<script type="text/javascript">

function goInsert(){	
	var  yyyy   	  = sForm.yyyy.value;
	var  yyyy_seq  	  = sForm.yyyy_seq.value;
	var  ar_name   = escape(encodeURIComponent(sForm.ar_name.value));	
	var  ar_lic_no    = sForm.ar_lic_no.value;
	var  ar_no1  	  = sForm.ar_no1.value;
	var  ar_no2  	  = sForm.ar_no2.value;
					
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
    
    if ( ar_no1 == "" ) {
        alert("주민번호를 선택하세요.") ;
        sForm.ar_no1.focus();
        return ;
    }  
    
    if ( ar_no2 == "" ) {
        alert("주민번호를 선택하세요.") ;
        sForm.ar_no2.focus();
        return ;
    }    
	
   	 if ( yyyy_seq == '' ) {
		 document.sForm.action = "actul.do?method=actulinsertdata";
		 document.sForm.submit(); 	
	 }else {
		 document.sForm.action = "actul.do?method=actulupdatedata&yyyy_seq="+yyyy_seq;
		 document.sForm.submit(); 
	} 
} 

$(function() {
	$( "#datepicker" ).datepicker();
});

</script>
</head> 
 
<body>
<div align:center >
<form id="list" name="sForm" method="post"> 
    <div id="r_contents1">
     <table class="bTitle" border="1" cellspacing="0" summary="영양사실태신고">        			
       <tr>           
         <td class="sTitle">영양사실태신고</td>              
	   </tr> 			
     </table>
     <div class="rTabmenu_01">	    
        <table class="rtable_1" cellspacing="0" summary="영양사실태신고">
          <caption>영양사실태신고</caption>  
          	 <tr>
          	   <td class="nobg" colspan="2" align="center">신고 년도</td>
       		   <td class="nobg4" colspan="1">
               <select  id="search" name="yyyy" value="<%= yy%>">
               <option value="O">선택</option>	
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("036".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode=comcodesearch.get(i).get("detcode").toString();
                			detCName=comcodesearch.get(i).get("detcodename").toString();
                			/* if(request.getAttribute("result")!= null){ */
                				if(detCode.equals(yy))
                					out.println("<option value="+detCode+" selected>"+detCName+"</option>");
                			/* } */
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td> 
			   <td class="nobg3"  align="center">순 번</td>
			   <td class="nobg4"><input type="text" id="name" name="yyyy_seq" value="<%= yyyy_seq%>" size="12" disabled="true"/></td>         	 	
          	 </tr>            			 			 
			 <tr>
			   <td rowspan="5" class="nobga" align="center">기본 인적사항</td>
			   <td class="nobg1" align="center">이 름</td>
               <td class="nobg2"><input type="text" id="name" name="ar_name" value="<%= name%>" size="12" /></td>
               <td class="nobg1" align="center">면허 번호</td>
               <td class="nobg2"><input type="text" id="name"  name="ar_lic_no" value="<%= lic_no%>" size="12" /></td>              
			 </tr>			
			 <tr>		   
			   <td class="nobg1" align="center">주민 번호</td>
               <td class="nobg2"><input type="text" id="name" name="ar_no1" value="<%= no1%>" size="6" />&nbsp;-
              					 <input type="password" id="name" name="ar_no2" value="<%= no2%>" size="7" /></td>
               <td class="nobg1" align="center">면허발급 년월일</td>
               <td class="nobg2"><input type="text" id="datepicker" name="ar_lic_print_dt" class="input" style=padding-top:3px  style=padding-bottom:3px value="<%= lic_print_dt%>" size="12" align="center"/></td>           
			 </tr>
			 <tr>
			   <td class="nobg1" align="center">성 별</tHd>
			   <td class="nobg2">
               <select  id="search" name="ar_code_sex" value="<%= code_sex%>">
               <option value="O">선택</option>	
              		<%
                	String detCode1,detCName1=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("002".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode1=comcodesearch.get(i).get("detcode").toString();
                			detCName1=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode1.equals(code_sex))
                					out.println("<option value="+detCode1+" selected>"+detCName1+"</option>"); 
                			}
                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
                		}
                	}
               		 %>
               </select>
			   </td>			  
               <td class="nobg1" align="center">이메일</td>
               <td class="nobg2"><input type="text" id="name" name="ar_email" value="<%= email%>" size="12" /></td>              
			 </tr>
			 <tr>			   
			   <td class="nobg1" align="center">주 소</td>
			   <td colspan="3" class="nobg2">
               <select  id="search" name="ar_add1" value="<%= add1%>">
               <option value="O">지역</option>	
              		 <% 
                	String detCode11,detCName11=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("046".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode11=comcodesearch.get(i).get("detcode").toString();
                			detCName11=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode11.equals(add1))
                					out.println("<option value="+detCode11+" selected>"+detCName11+"</option>"); 
                			}
                			out.println("<option value="+detCode11+">"+detCName11+"</option>");
                		}
                	}
               		 %>  
               </select>
               <input type="text" id="name"name="ar_add2" value="<%= add2%>" size="93" >
			   </td>            
             </tr>
			 <tr>			   
			   <td class="nobg1" align="center">연락처</td>
               <td colspan="3" class="nobg2"><input type="text" id="name" name="ar_tel_home" value="<%= tel_home%>" size="12" />&nbsp;
               <input type="text" id="name" name="ar_tel_job" value="<%= tel_job%>" size="12" />&nbsp;
               <input type="text" id="name" name="ar_tel_hp" value="<%= tel_hp%>" size="12" /></td>                          
			 </tr>
			 </table>
			 <table class="rtable_2" cellspacing="0" summary="영양사실태신고">
			 <tr>
			   <td rowspan="13" class="nobg" align="center">근무처<br>정보</td>
			 </tr>			
			 <tr>			 
			 </tr>
			 <tr>		   
			   <td rowspan="8" class="nobg3" align="center">집단급식소 근무자</td>
			   <td class="nobg3" align="center">활동 여부</td>
			   <td class="nobg4" colspan="4">
               <select  id="search" name="ar_code_part" value="<%= code_part%>">
               <option value="O">선택</option>	
              		 <% 
                	String detCode2,detCName2=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("042".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode2=comcodesearch.get(i).get("detcode").toString();
                			detCName2=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode2.equals(code_part))
                					out.println("<option value="+detCode2+" selected>"+detCName2+"</option>"); 
                			}
                			out.println("<option value="+detCode2+">"+detCName2+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td>
			   </tr>
			   <tr>
			   <td class="nobg1_2" align="center">근무집단 급식소 구분</td>
			   <td class="nobg2" colspan="4">
               <select  id="search" name="ar_code_big1" value="<%= code_big1%>">
               <option value="00">선택</option>	
              		 <% 
                	String detCode3,detCName3=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("043".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode3=comcodesearch.get(i).get("detcode").toString();
                			detCName3=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode3.equals(code_big1))
                					out.println("<option value="+detCode3+" selected>"+detCName3+"</option>"); 
                			}
                			out.println("<option value="+detCode3+">"+detCName3+"</option>");
                		}
                	}
               		 %>  
               </select>
			   </td>                            
			 </tr>			
			 <tr>			   
               <td rowspan="6" class="nobg1_2" align="center">근무처명 및 주소<br>&#40;공동관리의 경우<br> 모두 기재&#41;</td>               
               <td colspan="1" class="nobg6" align="center" style="width:5%">집단 급식소명</td> 
			   <td colspan="3" class="nobg6" align="center">주 소</td>
			 </tr>
			 <tr>			   	                             
               <td colspan="1" class="nobg2">1.<input type="text" id="name"name="ar_comp_name1" value="<%= comp_name1%>" size="14" ></td> 
			   <td colspan="3" class="nobg2">
               <select  id="search" name="ar_comp_add11" value="<%= comp_add11%>">
               <option value="00">지역</option>	
              		 <% 
                	String detCode4,detCName4=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("046".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode4=comcodesearch.get(i).get("detcode").toString();
                			detCName4=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode4.equals(comp_add11))
                					out.println("<option value="+detCode4+" selected>"+detCName4+"</option>"); 
                			}
                			out.println("<option value="+detCode4+">"+detCName4+"</option>");
                		}
                	}
               		 %>  
               </select>
               <input type="text" id="name"name="ar_comp_add12" value="<%= comp_add12%>" size="70" >
			   </td>                            
		     <tr>	                             
                <td colspan="1" class="nobg2">2.<input type="text" id="name"name="ar_comp_name2" value="<%= comp_name2%>" size="14" ></td> 
			   <td colspan="3" class="nobg2">
               <select  id="search" name="ar_comp_add21" value="<%= comp_add21%>">
               <option value="00">지역</option>	
              		 <% 
                	String detCode5,detCName5=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("046".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode5=comcodesearch.get(i).get("detcode").toString();
                			detCName5=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode5.equals(comp_add21))
                					out.println("<option value="+detCode5+" selected>"+detCName5+"</option>"); 
                			}
                			out.println("<option value="+detCode5+">"+detCName5+"</option>");
                		}
                	}
               		 %>  
               </select>
               <input type="text" id="name"name="ar_comp_add22" value="<%= comp_add22%>" size="70" >
			   </td>                            
			 </tr>
			 <tr>	                             
               <td colspan="1" class="nobg2">3.<input type="text" id="name"name="ar_comp_name3" value="<%= comp_name3%>" size="14" ></td> 
			   <td colspan="3" class="nobg2">
               <select  id="search" name="ar_comp_add31" value="<%= comp_add31%>">
               <option value="00">지역</option>	
              		 <% 
                	String detCode6,detCName6=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("046".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode6=comcodesearch.get(i).get("detcode").toString();
                			detCName6=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode6.equals(comp_add31))
                					out.println("<option value="+detCode6+" selected>"+detCName6+"</option>"); 
                			}
                			out.println("<option value="+detCode6+">"+detCName6+"</option>");
                		}
                	}
               		 %>  
               </select>
               <input type="text" id="name"name="ar_comp_add32" value="<%= comp_add32%>" size="70" >
			   </td>                            
			 </tr>
			 <tr>	                             
               <td colspan="1" class="nobg2">4.<input type="text" id="name"name="ar_comp_name4" value="<%= comp_name4%>" size="14" ></td> 
			   <td colspan="3" class="nobg2">
               <select  id="search" name="ar_comp_add41" value="<%= comp_add41%>">
               <option value="00">지역</option>	
              		 <% 
                	String detCode7,detCName7=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("046".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode7=comcodesearch.get(i).get("detcode").toString();
                			detCName7=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode7.equals(comp_add41))
                					out.println("<option value="+detCode7+" selected>"+detCName7+"</option>"); 
                			}
                			out.println("<option value="+detCode7+">"+detCName7+"</option>");
                		}
                	}
               		 %>  
               </select>
               <input type="text" id="name"name="ar_comp_add42" value="<%= comp_add12%>" size="70" >
			   </td>                            
             </tr>
			 <tr>	                             
               <td colspan="1" class="nobg2">5.<input type="text" id="name"name="ar_comp_name5" value="<%= comp_name5%>" size="14" ></td> 
			   <td colspan="3" class="nobg2">
               <select  id="search" name="ar_comp_add51" value="<%= comp_add51%>">
               <option value="00">지역</option>	
              		 <% 
                	String detCode8,detCName8=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("046".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode8=comcodesearch.get(i).get("detcode").toString();
                			detCName8=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode8.equals(comp_add51))
                					out.println("<option value="+detCode8+" selected>"+detCName8+"</option>"); 
                			}
                			out.println("<option value="+detCode8+">"+detCName8+"</option>");
                		}
                	}
               		 %>  
               </select>
               <input type="text" id="name"name="ar_comp_add52" value="<%= comp_add52%>" size="70" >
			   </td>                            
			 </tr>
			 <tr>			   
			   <td rowspan="3" class="nobg1" align="center">집단급식소<br>외 근무자</td>
               <td rowspan="3" class="nobg1_2" align="center">근무기관 구분</td> 
               <td colspan="4" class="nobg2">근무기관&nbsp;
			   <select  id="search" name="ar_code_big2" value="<%= code_big2%>">
               <option value="00">선택</option>	
              		 <% 
                	String detCode9,detCName9=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("044".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode9=comcodesearch.get(i).get("detcode").toString();
                			detCName9=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode9.equals(code_big2))
                					out.println("<option value="+detCode9+" selected>"+detCName9+"</option>"); 
                			}
                			out.println("<option value="+detCode9+">"+detCName9+"</option>");
                		}
                	}
               		 %>  
               </select> 
               </td>
             </tr>
			 <tr>			    
			   <td colspan="4" class="nobg2">근무처명 &nbsp;<input type="text" id="name"name="ar_comp_name6" value="<%= comp_name6%>" size="88" ></td>  
			 </tr>
			 <tr>
			 <td colspan="4" class="nobg2">주  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소&nbsp;
               <select  id="search" name="ar_comp_add61" value="<%= comp_add61%>">
               <option value="00">지역</option>	
              		 <% 
                	String detCode10,detCName10=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("046".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode10=comcodesearch.get(i).get("detcode").toString();
                			detCName10=comcodesearch.get(i).get("detcodename").toString();
                			if(request.getAttribute("result")!= null){
                				if(detCode10.equals(comp_add61))
                					out.println("<option value="+detCode10+" selected>"+detCName10+"</option>"); 
                			}
                			out.println("<option value="+detCode10+">"+detCName10+"</option>");
                		}
                	}
               		 %>  
               </select>
               <input type="text" id="name" name="ar_comp_add62" value="<%= comp_add62%>" size="70" >
			   </td>        			    
			 </tr>
			 <tr>
			   <td rowspan="3" colspan="3" class="alt" align="center">보수교육 및 신고관련</td>
			   <td colspan="6" class="alt2">최근 신고년도&nbsp;&nbsp;<input type="text" id="yyyy" size="10" /></td>
			 </tr>
			 <tr>			   
			   <td colspan="7" class="alt2">총&nbsp;<input type="text" id="mail" name="ar_finish_point" value="<%= finish_point%>" size="5" />&nbsp;시간&nbsp;이수&nbsp;의무&nbsp;중&nbsp;<input type="text" id="mail" name="ar_marks_point" value="<%= marks_point%>" size="5" />&nbsp;시간&nbsp;이수</td>
			 </tr>
        </table>
        <ul class="btnIcon_2">  	   	 
		   <li><a href="#"><img src="images/icon_save.gif"   onclick="javascript:goInsert();" alt="저장" /></a></li>   
		 </ul>   					
	  </div><!--mTabmenu_01 End-->	   
 </table>
 <div class="rTabmenu_01">	    
 	   <table class="rtable_3" cellspacing="0" summary="영양사실태신고">
          <caption>영양사실태신고</caption>               			 			 
			 <tr>
			   <td class="nobg" align="center">년 도</td>
			   <td class="nobg4" align="center">교  육  명</td>
               <td class="nobg5" align="center">이수 시간</td>
               <td class="nobg5" align="center">이수 일자</td>
               <td class="nobg5" align="center">이수 여부</td>                                       
			 </tr>
			<%
 			   if(result1!=null){
 			 %> 
			 	<% 		  
				 for (int i=0; result1.size()> i; i++){					
				 %>				 	
				 <tr>
				   <td class="nobg11"><%=result1.get(i).get("yyyy") %></td>			  			   			   
	               <td><%=result1.get(i).get("ke_subject") %></td>
	               <td><%=result1.get(i).get("ke_isutime") %></td>             	 
	               <td><%=result1.get(i).get("ke_finish_dt") %></td>
	               <td><%=result1.get(i).get("ke_finish_yn") %></td>			  
				 </tr>
				 <%} %>			 		  			 		 	 
			</table><br><br><br><br>	
	  </div><!--rTabmenu_01 End-->  
			  <%}
 			   else { %>
 			       <td class="nobg11" colspan="5"> 보수 교육 내역이 없습니다.</td>
 			  <%} %>
  </div><!--r_contents1 End-->		 
 </form>
 </div>
 </body> 
</html>
