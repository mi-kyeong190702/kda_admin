<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import= "java.net.URLDecoder, java.net.URLEncoder;" %>
<%
	request.setCharacterEncoding("UTF-8");	
	String opername="";
	String opername1="";
	String codeoperation="";
	String param="";
	String add_files="";
	String code_operation1="";
	String code_operation_other1="";
	String receipt_no1="";
	String code_kind1="";
	String code_certifi1="";
	String code_seq1="";
	String code_bran1="";
	String bran_seq1="";
	String filename="";
	String yyyy1="";
	String edutest_name1="";
	String season1="";
	String del="";
		

	List<Map> sFiles=(List<Map>)request.getAttribute("sFiles");
	
	int sFiless=sFiles.size();

	if(request.getParameter("oper_name1")!=null){
		 opername=StringUtil.nullToStr("", URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));
		 codeoperation=StringUtil.nullToStr("",  URLDecoder.decode(request.getParameter("codeoperation"),"UTF-8"));
		 param=StringUtil.nullToStr("", request.getParameter("param"));
		 code_operation1=request.getParameter("code_operation1");
// 		 code_operation_other1=request.getParameter("code_operation_other1");
		 code_kind1=StringUtil.nullToStr("", request.getParameter("code_kind1"));
		 code_certifi1=StringUtil.nullToStr("", request.getParameter("code_certifi1"));
		 code_seq1=StringUtil.nullToStr("", request.getParameter("code_seq1"));
		 code_bran1=StringUtil.nullToStr("", request.getParameter("code_bran1"));
		 bran_seq1=StringUtil.nullToStr("", request.getParameter("bran_seq1"));
		 receipt_no1=StringUtil.nullToStr("", request.getParameter("receipt_no1"));
		 filename=code_kind1+code_certifi1+code_seq1+code_bran1+bran_seq1+receipt_no1;
		 yyyy1=	StringUtil.nullToStr("", request.getParameter("yyyy1"));
		 edutest_name1=	StringUtil.nullToStr("", request.getParameter("edutest_name1"));
		 season1=StringUtil.nullToStr("", request.getParameter("season1"));
	}
	if(request.getAttribute("oper_name1")!=null){
		del=StringUtil.nullToStr("", request.getAttribute("del").toString());
		//opername=URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8");
		opername=StringUtil.nullToStr("", request.getAttribute("oper_name1").toString());
		codeoperation=StringUtil.nullToStr("",request.getAttribute("codeoperation").toString());
		param=StringUtil.nullToStr("", request.getAttribute("param").toString());
		code_operation1=StringUtil.nullToStr("", request.getAttribute("code_operation1").toString());
// 		code_operation_other1=StringUtil.nullToStr("", request.getAttribute("code_operation_other1").toString());
		receipt_no1=StringUtil.nullToStr("", request.getAttribute("receipt_no").toString());
		yyyy1=	StringUtil.nullToStr("", request.getAttribute("yyyy1").toString());
		edutest_name1=	StringUtil.nullToStr("", request.getAttribute("edutest_name1").toString());
		code_kind1=StringUtil.nullToStr("", request.getAttribute("code_kind1").toString());
		code_bran1=StringUtil.nullToStr("", request.getAttribute("code_bran1").toString());
		code_certifi1=StringUtil.nullToStr("", request.getAttribute("code_certifi1").toString());
		code_seq1=StringUtil.nullToStr("", request.getAttribute("code_seq").toString());
		bran_seq1=StringUtil.nullToStr("", request.getAttribute("bran_seq").toString());
		season1=StringUtil.nullToStr("", request.getAttribute("season1").toString());
		filename=code_kind1+code_certifi1+code_seq1+code_bran1+bran_seq1+receipt_no1;			
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
		
        if("<%=del%>"=="Y"){
			alert(errMsg);	
			opener.$('#list').jqGrid('clearGridData');
			opener.$("#list").setGridParam({url:"license.do?method=inspectionSandList&code_certifi1="+'<%=code_certifi1%>'+"&code_operation1=&oper_name1=&oper_no1=&oper_lic_no1=&oper_hp1=&oper_email1=&oper_comp_name11=&person_yn1=&season1="
			   +"&yyyy1="+'<%=yyyy1%>'+"&code_kind1=&detcode1=&code_seq1=&code_bran1="+'<%=code_bran1%>'+"&bran_seq1=&receipt_no1=&result_point1="}
	   		).trigger("reloadGrid");

		}else if(errMsg.length>2){
			alert(errMsg);	
			
			opener.$('#list').jqGrid('clearGridData');			
			opener.$("#list").setGridParam({url:"license.do?method=inspectionSandList&code_certifi1="+'<%=code_certifi1%>'+"&code_operation1=&oper_name1=&oper_no1=&oper_lic_no1=&oper_hp1=&oper_email1=&oper_comp_name11=&person_yn1=&season1="
				   +"&yyyy1="+'<%=yyyy1%>'+"&code_kind1=&detcode1=&code_seq1=&code_bran1="+'<%=code_bran1%>'+"&bran_seq1=&receipt_no1=&result_point1="}
	   		).trigger("reloadGrid");

			self.close();
		} 
	}
function download(val){
	document.dFile.target="frm";
	document.dFile.action="license.do?method=fDown&filename="+val;
	document.dFile.submit();
}

function delFile(val){
	document.upFile.action="license.do?method=delFile&dFilename="+val;
	document.upFile.submit();
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
			<input type="hidden" name="opername1" value="<%=URLEncoder.encode(opername, "UTF-8") %>"/>
			<input type="hidden" name="codeoperation2" value="<%=URLEncoder.encode(codeoperation, "UTF-8") %>"/>
			<input type="hidden" name="filename" value="<%=filename%>"/>
			
			<input type="hidden" name="code_kind1" value="<%=code_kind1%>"/>
			<input type="hidden" name="code_certifi1" value="<%=code_certifi1%>"/>
			<input type="hidden" name="code_seq1" value="<%=code_seq1%>"/>
			<input type="hidden" name="code_bran1" value="<%=code_bran1%>"/>
			<input type="hidden" name="bran_seq1" value="<%=bran_seq1%>"/>		
			<input type="hidden" name="yyyy1" value="<%=yyyy1%>"/>
			<input type="hidden" name="edutest_name1" value="<%=edutest_name1%>"/>
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />		
			<input type="hidden" name="season1" value="<%=season1 %>" />						
		</td>
	</tr>
			
	<%if(code_operation1.equals("1") && !"3".equals(code_operation_other1)){ %>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_22" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("22")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name);'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">압축파일</td>
		<td class="mtbl1" colspan="3">
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("28")){
						out.print("<input type='button' value='심사서류 압축파일 저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");						
					}
				}
			} %>
			
		</td> 
	</tr>	
	<%}else if(code_operation1.equals("3") || "3".equals(code_operation_other1)){ %>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_42" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("42")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name);'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">압축파일</td>
		<td class="mtbl1" colspan="3">			
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("48")){
						out.print("<input type='button' value='심사서류 압축파일 저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");						
					}
				}
			} %>
			
		</td> 
	</tr>
	<%}else if(code_operation1.equals("4") && !"3".equals(code_operation_other1)){ %>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_52" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("52")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name);'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">압축파일</td>
		<td class="mtbl1" colspan="3">			
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("58")){
						out.print("<input type='button' value='심사서류 압축파일 저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");						
					}
				}
			} %>
			
		</td> 
	</tr>
	<%}else if(code_operation1.equals("6") && !"3".equals(code_operation_other1)){ %>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_72" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("72")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name);'/>");
					}
				}
			} %>
			
		</td> 
	</tr>	
	<tr>
		<td class="mtbl">압축파일</td>
		<td class="mtbl1" colspan="3">			
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("78")){
						out.print("<input type='button' value='심사서류 압축파일 저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");						
					}
				}
			}%>
		</td> 
	</tr>
	<%}else if(code_operation1.equals("7") && !"3".equals(code_operation_other1)){ %>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_82" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("22")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name);'/>");
					}
				}
			} %>
			
		</td> 
	</tr>	
	<tr>
		<td class="mtbl">압축파일</td>
		<td class="mtbl1" colspan="3">			
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("28")){
						out.print("<input type='button' value='심사서류 압축파일 저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");						
					}
				}
			}%>
		</td> 
	</tr>
	<%}else if(code_operation1.equals("8") && !"3".equals(code_operation_other1)){ %>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_92" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("92")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name);'/>");
					}
				}
			} %>
			
		</td> 
	</tr>	
	<tr>
		<td class="mtbl">압축파일</td>
		<td class="mtbl1" colspan="3">			
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("98")){
						out.print("<input type='button' value='심사서류 압축파일 저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");						
					}
				}
			}%>
		</td> 
	</tr>
	<%} %>
</table>
<p align="right">
<input type="button" name="send" value="파일전송"  onclick="submit();"/> 
<input type="button" name="cancel" value="닫기" onclick="self.close();"/>
</p> 					
</form>
<form name="dFile" method="post" action="" target="" >
</form>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>


