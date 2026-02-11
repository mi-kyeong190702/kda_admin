<%@ page language="java" contentType="text/html; charset=EUC-KR"  pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ant.common.util.StringUtil" %>
<%@ page import= "java.net.URLDecoder, java.net.URLEncoder;" %>
<%
	request.setCharacterEncoding("UTF-8");	
	String opername="";
	String param="";
	String code_certifi1="";
	String code_seq1="";
	String filename="";
	String code_pers1="";
	String result_no1="";
	String print_state1="";
	String yyyy1="";
	String season1="";

	String del="";

	List<Map> sFiles=(List<Map>)request.getAttribute("sFiles");
	
	if(request.getParameter("oper_name1")!=null){
		opername=StringUtil.nullToStr("", URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));		
		param=StringUtil.nullToStr("", request.getParameter("param"));
		code_certifi1=StringUtil.nullToStr("", request.getParameter("code_certifi1"));
		code_seq1=StringUtil.nullToStr("", request.getParameter("code_seq1"));
		code_pers1=StringUtil.nullToStr("", request.getParameter("code_pers1"));
		result_no1=StringUtil.nullToStr("", request.getParameter("result_no1"));
		print_state1=StringUtil.nullToStr("", request.getParameter("print_state1"));
		yyyy1=StringUtil.nullToStr("", request.getParameter("yyyy1"));
		season1=StringUtil.nullToStr("", request.getParameter("season1"));
		filename=code_pers1+code_certifi1+result_no1+code_seq1;
	}	
	if(request.getAttribute("oper_name1")!=null){
		del=StringUtil.nullToStr("", request.getAttribute("del").toString());
		opername=StringUtil.nullToStr("", request.getAttribute("oper_name1").toString());		
		param=StringUtil.nullToStr("", request.getAttribute("param").toString());
		code_certifi1=StringUtil.nullToStr("", request.getAttribute("code_certifi1").toString());
		code_seq1=StringUtil.nullToStr("", request.getAttribute("code_seq1").toString());
		code_pers1=StringUtil.nullToStr("", request.getAttribute("code_pers1").toString());
		result_no1=StringUtil.nullToStr("", request.getAttribute("result_no1").toString());
		print_state1=StringUtil.nullToStr("", request.getAttribute("print_state1").toString());
		yyyy1=StringUtil.nullToStr("", request.getAttribute("yyyy1").toString());
		season1=StringUtil.nullToStr("", request.getAttribute("season1").toString());
		filename=code_pers1+code_certifi1+result_no1+code_seq1;
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
		if("<%=del%>"=="Y"){
			alert('<%=msg%>');	
			opener.$('#list').jqGrid('clearGridData');
			if('<%=season1%>'==1)
				opener.$("#list").setGridParam({url:"license.do?method=renewalSandList&code_certifi1="+'<%=code_certifi1%>'+"&result_no1=&oper_name1=&oper_no1=&oper_lic_no1=&code_seq1="+'<%=code_seq1%>'
					   +"&season1=1&season2=&print_state1="+'<%=print_state1%>'+"&yyyy1="+'<%=yyyy1%>'}
			   		).trigger("reloadGrid");
			else
				opener.$("#list").setGridParam({url:"license.do?method=renewalSandList&code_certifi1="+'<%=code_certifi1%>'+"&result_no1=&oper_name1=&oper_no1=&oper_lic_no1=&code_seq1="+'<%=code_seq1%>'
					   +"&season1=&season2=2&print_state1="+'<%=print_state1%>'+"&yyyy1="+'<%=yyyy1%>'}
			   		).trigger("reloadGrid");
		}else if('<%=msg.length()%>'>2){
			alert('<%=msg%>');	
			
			opener.$('#list').jqGrid('clearGridData');
			if('<%=season1%>'==1)
				opener.$("#list").setGridParam({url:"license.do?method=renewalSandList&code_certifi1="+'<%=code_certifi1%>'+"&result_no1=&oper_name1=&oper_no1=&oper_lic_no1=&code_seq1="+'<%=code_seq1%>'
					   +"&season1=1&season2=&print_state1="+'<%=print_state1%>'+"&yyyy1="+'<%=yyyy1%>'}
			   		).trigger("reloadGrid");
			else
				opener.$("#list").setGridParam({url:"license.do?method=renewalSandList&code_certifi1="+'<%=code_certifi1%>'+"&result_no1=&oper_name1=&oper_no1=&oper_lic_no1=&code_seq1="+'<%=code_seq1%>'
					   +"&season1=&season2=2&print_state1="+'<%=print_state1%>'+"&yyyy1="+'<%=yyyy1%>'}
			   		).trigger("reloadGrid");
			self.close();
		}
	}
	
	function download(val){
		document.dFile.target="frm";
		document.dFile.action="license.do?method=fDown1&filename="+val;
		document.dFile.submit();
	}

	function delFile(val){
		document.rupFile.action="license.do?method=delFile2&dFilename="+val;
		document.rupFile.submit();
	}	

</script>
</head>

<body>
<form name="rupFile" method="post" enctype="multipart/form-data" action="license.do?method=rupFile" >
	<table class="tbl_type" border="1" cellspacing="0" summary="화일첨부">
	<tr> 
		<td class="mtbl" >갱신대상</td>
		<td class="mtbl1" >
			<input type="text" name="opername"  size="18" value="<%=opername%>">			
			<input type="hidden" name="param" value="<%=param %>"/>
			<input type="hidden" name="opername1" value="<%=URLEncoder.encode(opername, "UTF-8") %>"/>
			<input type="hidden" name="filename" value="<%=filename%>"/>
			<input type="hidden" name="code_certifi1" value="<%=code_certifi1%>"/>
			<input type="hidden" name="code_seq1" value="<%=code_seq1%>"/>
			<input type="hidden" name="code_pers1" value="<%=code_pers1%>"/>
			<input type="hidden" name="result_no1" value="<%=result_no1 %>" />
			<input type="hidden" name="print_state1" value="<%=print_state1%>"/>
			<input type="hidden" name="yyyy1" value="<%=yyyy1%>"/>
			<input type="hidden" name="season1" value="<%=season1%>"/>
		</td>
	<td class="mtbl1">
	</td>
	</tr>
	<tr>
		<td class="mtbl">사진</td>
		<td class="mtbl1" colspan="3">
			<input type="file" name="add_file_2" size="30" id="attatch" />
			<%if(sFiles!=null&&sFiles.size()>0){
				for(int i=0;i<sFiles.size();i++){
					if(sFiles.get(i).get("add_file_2").toString().length()!=0){
						out.print("기존 파일: <input type='button' value='저장' name='"+sFiles.get(i).get("add_file_2")+"' onclick='javascript:download(this.name);' />");
						out.print("&nbsp;&nbsp;<input type='button' value='삭제' name='"+sFiles.get(i).get("add_file_2")+"' onclick='javascript:delFile(this.name);'/>");
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
					if(sFiles.get(i).get("add_file_5").toString().length()!=0){
						out.print("<input type='button' value='첨부서류 압축파일 저장' name='"+sFiles.get(i).get("add_file_5")+"' onclick='javascript:download(this.name);' />");						
					}
				}
			} %>
			
		</td> 
	</tr>
</table>
<p align="right">
<input type="button" name="send" value="파일전송"  onclick="submit();"> 
<input type="button" name="cancel" value="취소" onclick="self.close();">
</p> 					
</form>
<form name="dFile" method="post" action="" target="" >
</form>
</body>
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>