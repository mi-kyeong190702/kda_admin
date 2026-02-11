<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import= "java.net.URLDecoder, java.net.URLEncoder;" %>
<%
	request.setCharacterEncoding("UTF-8");
	String sFolder="upload/education/";
	String opername="";
	String opername1="";
	String codeoperation="";
	String param="";
	String add_files="";
	String code_operation1="";
	String receipt_no1="";
	String code_kind1="";
	String code_certifi1="";
	String code_seq1="";
	String code_bran1="";
	String bran_seq1="";
	String filename="";
	String yyyy1="";
	String edutest_name1="";
	String del="";
	
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
	
	String t_1,t_2,t_3,t_4,t_5,t_6,t_7,t_8="";

	List<Map> sFiles=(List<Map>)request.getAttribute("sFiles");
	
	int sFiless=sFiles.size();

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
		 yyyy1=	StringUtil.nullToStr("", request.getParameter("yyyy1"));
		 edutest_name1=	StringUtil.nullToStr("", request.getParameter("edutest_name1"));		 
	}
	if(request.getAttribute("oper_name1")!=null){
		del=StringUtil.nullToStr("", request.getAttribute("del").toString());		
		opername=StringUtil.nullToStr("", request.getAttribute("oper_name1").toString());
		codeoperation=StringUtil.nullToStr("",request.getAttribute("codeoperation").toString());
		param=StringUtil.nullToStr("", request.getAttribute("param").toString());
		code_operation1=StringUtil.nullToStr("", request.getAttribute("code_operation1").toString());
		receipt_no1=StringUtil.nullToStr("", request.getAttribute("receipt_no").toString());
		yyyy1=	StringUtil.nullToStr("", request.getAttribute("yyyy1").toString());
		edutest_name1=	StringUtil.nullToStr("", request.getAttribute("edutest_name1").toString());
		code_kind1=StringUtil.nullToStr("", request.getAttribute("code_kind1").toString());
		code_bran1=StringUtil.nullToStr("", request.getAttribute("code_bran1").toString());
		code_certifi1=StringUtil.nullToStr("", request.getAttribute("code_certifi1").toString());
		code_seq1=StringUtil.nullToStr("", request.getAttribute("code_seq").toString());
		bran_seq1=StringUtil.nullToStr("", request.getAttribute("bran_seq").toString());		
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
			opener.$("#list").setGridParam({url:"education.do?method=educationSendList&yyyy1="+<%=yyyy1%>+"&code_bran1="
		   		+"&code_kind1="+<%=code_kind1%>+"&edutest_name1=&code_operation1=&code_pay_flag1=&code_certifi1="+<%=code_certifi1%>+"&oper_name1=&oper_no1=&oper_lic_no1=&oper_hp1=&oper_comp_name11=&result_end_dt1="
		   		+"&start_dt1=&end_dt1=&oper_state1=&code_seq1=&receipt_no1=&bran_seq1=&detcode1="+<%=edutest_name1%>}).trigger("reloadGrid");
		}else if(errMsg.length>2){
			alert(errMsg);	
			
			opener.$('#list').jqGrid('clearGridData');
			opener.$("#list").setGridParam({url:"education.do?method=educationSendList&yyyy1="+<%=yyyy1%>+"&code_bran1="
		   		+"&code_kind1="+<%=code_kind1%>+"&edutest_name1=&code_operation1=&code_pay_flag1=&code_certifi1="+<%=code_certifi1%>+"&oper_name1=&oper_no1=&oper_lic_no1=&oper_hp1=&oper_comp_name11=&result_end_dt1="
		   		+"&start_dt1=&end_dt1=&oper_state1=&code_seq1=&receipt_no1=&bran_seq1=&detcode1="+<%=edutest_name1%>}).trigger("reloadGrid");
			self.close();
		}
	}
function download(val){
	document.dFile.target="frm";
	document.dFile.action="education.do?method=fDown&filename="+val;
	document.dFile.submit();
}

function delFile(val){
	document.upFile.action="education.do?method=delFile&dFilename="+val;
	document.upFile.submit();
}
</script>
</head>

<body>
<form name="upFile" method="post" enctype="multipart/form-data" action="education.do?method=upFile" >
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
		</td>
	</tr>
			
	<%if(code_operation1.equals("1")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_21" size="30" id="attatch" />			
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("21")){
						//out.print("기존 파일: <a href='"+sFolder+sFiles.get(i).get("chang_file_name")+"' target='_blank' style='color:black;' onclick='download("+sFiles.get(i).get("chang_file_name")+")'>보기</a>");
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
		</td> 
	</tr>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_22" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("22")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_23" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("23")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">경력증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_24" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("24")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_25" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("25")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	
	<%}else if(code_operation1.equals("2")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_21" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("21")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
		</td> 
	</tr>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_22" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("22")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_23" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("23")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">경력증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_24" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("24")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_25" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("25")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	
	
	<%}else if(code_operation1.equals("3")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_41" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("41")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_42" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("42")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_43" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("43")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">경력증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_44" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("44")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">학위증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_45" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("45")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">최종학위성적증명서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_46" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("46")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_47" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("47")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<%}else if(code_operation1.equals("4")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_51" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("51")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_52" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("52")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">기존자격증원본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_53" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("53")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_54" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("54")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<%}else if(code_operation1.equals("6")){ %>
	<tr>
		<td class="mtbl">신청서</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_61" size="30" id="attatch" />
			<input type="hidden" name="receipt_no" value="<%=receipt_no1 %>" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("61")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_62" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("62")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	<tr>
		<td class="mtbl">영양사면허증 사본</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_63" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("63")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
					}
				}
			} %>
			
		</td> 
	</tr>
	
	<tr>
		<td class="mtbl">발급회비</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="_64" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_no").toString().equals("64")){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("chang_file_name")+"' onclick='javascript:delFile(this.name)'/>");
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


