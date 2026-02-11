<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import= "java.net.URLDecoder" %>
<%
	request.setCharacterEncoding("UTF-8");
	String opername="";
	String codeoperation="";
	String param="";
	String code_operation1="";
	String receipt_no1="";
	String code_kind1="";
	String code_certifi1="";
	String code_seq1="";
	String code_bran1="";
	String bran_seq1="";
	String filename="";
	
	String _21YN="0";
	String _22YN="0";
	String _23YN="0";
	String _24YN="0";
	String _25YN="0";
	
	String _41YN="0";
	String _42YN="0";
	String _43YN="0";
	String _44YN="0";
	String _45YN="0";
	String _46YN="0";
	String _47YN="0";

	String _51YN="0";
	String _52YN="0";
	String _53YN="0";
	String _54YN="0";
	
	String _61YN="0";
	String _62YN="0";
	String _63YN="0";
	String _64YN="0";
	
	
	
	if(request.getParameter("oper_name1")!=null){
		 opername=StringUtil.nullToStr("", URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));
		 codeoperation=StringUtil.nullToStr("",  URLDecoder.decode(request.getParameter("codeoperation"),"UTF-8"));
		 param=StringUtil.nullToStr("", request.getParameter("param"));
		 code_operation1=request.getParameter("code_operation1");
		 code_kind1=StringUtil.nullToStr("", request.getParameter("code_kind1"));
		 code_certifi1=StringUtil.nullToStr("", request.getParameter("code_certifi1"));
		 code_seq1=StringUtil.nullToStr("", request.getParameter("code_seq1"));
		 code_bran1=StringUtil.nullToStr("", request.getParameter("code_bran1"));
		 bran_seq1=StringUtil.nullToStr("", request.getParameter("bran_seq1"));
		 receipt_no1=StringUtil.nullToStr("", request.getParameter("receipt_no1"));
		 filename=code_kind1+code_certifi1+code_seq1+code_bran1+bran_seq1+receipt_no1;
		 
	}
	if(request.getAttribute("oper_name1")!=null){
		opername=StringUtil.nullToStr("", request.getAttribute("oper_name1").toString());
		codeoperation=StringUtil.nullToStr("",request.getAttribute("codeoperation").toString());
		param=StringUtil.nullToStr("", request.getAttribute("param").toString());
		code_operation1=StringUtil.nullToStr("", request.getAttribute("code_operation1").toString());
		receipt_no1=StringUtil.nullToStr("", request.getParameter("receipt_no1"));
		if(code_operation1.equals("1")){
			if(request.getAttribute("_21YN")!=null) _21YN=request.getAttribute("_21YN").toString();
			if(request.getAttribute("_22YN")!=null) _22YN=request.getAttribute("_22YN").toString();
			if(request.getAttribute("_23YN")!=null) _23YN=request.getAttribute("_23YN").toString();
			if(request.getAttribute("_24YN")!=null) _24YN=request.getAttribute("_24YN").toString();
			if(request.getAttribute("_25YN")!=null) _25YN=request.getAttribute("_25YN").toString();
		}else if(code_operation1.equals("3")){
			if(request.getAttribute("_41YN")!=null) _41YN=request.getAttribute("_41YN").toString();
			if(request.getAttribute("_42YN")!=null) _42YN=request.getAttribute("_42YN").toString();
			if(request.getAttribute("_43YN")!=null) _43YN=request.getAttribute("_43YN").toString();
			if(request.getAttribute("_44YN")!=null) _44YN=request.getAttribute("_44YN").toString();
			if(request.getAttribute("_45YN")!=null) _45YN=request.getAttribute("_45YN").toString();
			if(request.getAttribute("_46YN")!=null) _46YN=request.getAttribute("_46YN").toString();
			if(request.getAttribute("_47YN")!=null) _47YN=request.getAttribute("_47YN").toString();
		}else if(code_operation1.equals("4")){
			if(request.getAttribute("_51YN")!=null) _51YN=request.getAttribute("_51YN").toString();
			if(request.getAttribute("_52YN")!=null) _52YN=request.getAttribute("_52YN").toString();
			if(request.getAttribute("_53YN")!=null) _53YN=request.getAttribute("_53YN").toString();
			if(request.getAttribute("_54YN")!=null) _54YN=request.getAttribute("_54YN").toString();
		}else if(code_operation1.equals("6")){
			if(request.getAttribute("_61YN")!=null) _61YN=request.getAttribute("_61YN").toString();
			if(request.getAttribute("_62YN")!=null) _62YN=request.getAttribute("_62YN").toString();
			if(request.getAttribute("_63YN")!=null) _63YN=request.getAttribute("_63YN").toString();
			if(request.getAttribute("_64YN")!=null) _64YN=request.getAttribute("_64YN").toString();
		}
		
	}
	
	
	String msg="";
	if(request.getAttribute("msg")!=null)
		msg=request.getAttribute("msg").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>대한영양사협회 - 파일 첨부</title>
<link rel="stylesheet" type="text/css" href="css/m_search.css" />

<script type="text/javascript" src="js/jquery-1.8.1.min.js" ></script>

<script language="javascript">	
	document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
	};
	function init(){
		
		var errMsg="<%=msg%>";
			
		if(errMsg.length>2){
			alert(errMsg);	
			if(<%=code_operation1%>==1){
				if('<%=_21YN%>'!='0'){
					opener.document.getElementById("f0").checked=true;
					opener.document.sForm._21name.value='<%=_21YN%>';
				}else{
					opener.document.getElementById("f0").checked=false;
				}
				if('<%=_22YN%>'!='0'){
					opener.document.getElementById("f1").checked=true;
					opener.document.sForm._22name.value='<%=_22YN%>';
				}else{
					opener.document.getElementById("f1").checked=false;
				}
				if('<%=_23YN%>' !='0'){
					opener.document.getElementById("f2").checked=true;
					opener.document.sForm._23name.value='<%=_23YN%>';
				}else{
					opener.document.getElementById("f2").checked=false;
				}
				if('<%=_24YN%>'!='0'){
					opener.document.getElementById("f3").checked=true;
					opener.document.sForm._24name.value='<%=_24YN%>';
				}else{
					opener.document.getElementById("f3").checked=false;
				}
				if('<%=_25YN%>'!='0'){
					opener.document.getElementById("f7").checked=true;
					opener.document.sForm._25name.value='<%=_25YN%>';
				}else{
					opener.document.getElementById("f7").checked=false;
				}
				
			}else if(<%=code_operation1%>==3){
				if('<%=_41YN%>'!='0'){
					opener.document.getElementById("f0").checked=true;	
					opener.document.sForm._41name.value='<%=_41YN%>';
				}else{
					opener.document.getElementById("f0").checked=false;
				}
				if('<%=_42YN%>'!='0'){
					opener.document.getElementById("f1").checked=true;
					opener.document.sForm._42name.value='<%=_42YN%>';
				}else{
					opener.document.getElementById("f1").checked=false;
				}
				if('<%=_43YN%>'!='0'){
					opener.document.getElementById("f2").checked=true;
					opener.document.sForm._43name.value='<%=_43YN%>';
				}else{
					opener.document.getElementById("f2").checked=false;
				}
				if('<%=_44YN%>'!='0'){
					opener.document.getElementById("f3").checked=true;
					opener.document.sForm._44name.value='<%=_44YN%>';
				}else{
					opener.document.getElementById("f3").checked=false;
				}
				if('<%=_45YN%>'!='0'){
					opener.document.getElementById("f4").checked=true;
					opener.document.sForm._45name.value='<%=_45YN%>';
				}else{
					opener.document.getElementById("f4").checked=false;
				}
				if('<%=_46YN%>'!='0'){
					opener.document.getElementById("f5").checked=true;
					opener.document.sForm._46name.value='<%=_46YN%>';
				}else{
					opener.document.getElementById("f5").checked=false;
				}
				if('<%=_47YN%>'!='0'){
					opener.document.getElementById("f7").checked=true;
					opener.document.sForm._47name.value='<%=_47YN%>';
				}else{
					opener.document.getElementById("f7").checked=false;
				}
			}else if(<%=code_operation1%>==4){
				if('<%=_51YN%>'!='0'){
					opener.document.getElementById("f0").checked=true;
					opener.document.sForm._51name.value='<%=_51YN%>';
				}else{
					opener.document.getElementById("f0").checked=false;
				}
				if('<%=_52YN%>'!='0'){
					opener.document.getElementById("f2").checked=true;
					opener.document.sForm._52name.value='<%=_52YN%>';
				}else{
					opener.document.getElementById("f2").checked=false;
				}
				if('<%=_53YN%>'!='0'){
					opener.document.getElementById("f6").checked=true;	
					opener.document.sForm._53name.value='<%=_53YN%>';
				}else{
					opener.document.getElementById("f6").checked=false;
				}
				if('<%=_54YN%>'!='0'){
					opener.document.getElementById("f7").checked=true;
					opener.document.sForm._54name.value='<%=_54YN%>';
				}else{
					opener.document.getElementById("f7").checked=false;
				}
				
			}else if(<%=code_operation1%>==6){
				if('<%=_61YN%>'!='0'){
					opener.document.getElementById("f0").checked=true;
					opener.document.sForm._61name.value='<%=_61YN%>';
				}else{
					opener.document.getElementById("f0").checked=false;
				}
				if('<%=_62YN%>'!='0'){
					opener.document.getElementById("f1").checked=true;
					opener.document.sForm._62name.value='<%=_62YN%>';
				}else{
					opener.document.getElementById("f1").checked=false;
				}
				if('<%=_63YN%>'!='0'){
					opener.document.getElementById("f2").checked=true;	
					opener.document.sForm._63name.value='<%=_63YN%>';
				}else{
					opener.document.getElementById("f2").checked=false;
				}
				if('<%=_64YN%>'!='0'){
					opener.document.getElementById("f7").checked=true;
					opener.document.sForm._64name.value='<%=_64YN%>';
				}else{
					opener.document.getElementById("f7").checked=false;
				}
				
			}
			
			self.close();
		}
	}

</script>
</head>

<body>
<form name="upFile" method="post" enctype="multipart/form-data" action="license.do?method=upFile" >
	<table class="tbl_type" border="1" cellspacing="0" summary="화일첨부">
	<tr> 
		<td class="mtbl" >응시자</td>
		<td class="mtbl1" >
			<input type="text" name="opername"  size="18" value="<%=opername%>">
		</td>
		<td class="mtbl">대상자</td>
		<td class="mtbl1">
		
			<input type="text" name="codeoperation"  size="16" value="<%=codeoperation%>"> 
			<input type="hidden" name="param" value="<%=param %>"/>
			<input type="hidden" name="code_operation1" value="<%=code_operation1 %>"/>
			<input type="hidden" name="opername1" value="<%=request.getParameter("oper_name1") %>"/>
			<input type="hidden" name="codeoperation2" value="<%=request.getParameter("codeoperation") %>"/>
			<input type="hidden" name="filename" value="<%=filename%>"/>
		</td>
	</tr>
		
	
	<%if(code_operation1.equals("1")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_21" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_22" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_23" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">경력증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_24" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_25" size="30" id="attatch" />
		</td> 
	</tr>
	<%}else if(code_operation1.equals("3")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_41" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_42" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_43" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">경력증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_44" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">학위증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_45" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">최종학위성적증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_46" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_47" size="30" id="attatch" />
		</td> 
	</tr>
	<%}else if(code_operation1.equals("4")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_51" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_52" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">기존자격증원본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_53" size="30" id="attatch" />
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_47" size="30" id="attatch" />
		</td> 
	</tr>
	<%}else if(code_operation1.equals("6")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_61" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_62" size="30" id="attatch" />
		</td> 
	</tr>
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_63" size="30" id="attatch" />
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_64" size="30" id="attatch" />
		</td> 
	</tr>
	<%} %>
</table>
<p align="right">
<input type="button" name="send" value="파일전송"  onclick="submit();"> 
<input type="button" name="cancel" value="취소" onclick="self.close();">
</p> 					
</form>
</body>
</html>



