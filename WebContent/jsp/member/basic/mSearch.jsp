<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>대한영양사협회</title> 
<link rel="shortcut icon" href="images/favicon.ico"> 
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
  
<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>
<% 
	String G_NAME="";
	String G_BRAN="";
	if(session.getAttribute("G_NAME")!=null){
		G_NAME=session.getAttribute("G_NAME").toString();
	}
	if(session.getAttribute("G_BRAN")!=null){
		G_BRAN=session.getAttribute("G_BRAN").toString();
	}
	String name=request.getParameter("name");
// 	JUMIN_DEL
// 	String jumin=request.getParameter("jumin");
	String birth=request.getParameter("birth");
	String licno=request.getParameter("licno");
	String email=request.getParameter("email");
	String compname=request.getParameter("compname");
	String memberid=request.getParameter("memberid"); 
	
	String url="";
	if(name!=null){
		url="memberBasic.do?method=mSearchr&name="+name+"&gbran="+G_BRAN;
// 	JUMIN_DEL
// 	}else if(jumin!=null){
// 		url="memberBasic.do?method=mSearchr&jumin="+jumin+"&gbran="+G_BRAN;
	}else if(birth!=null){
		url="memberBasic.do?method=mSearchr&birth="+birth+"&gbran="+G_BRAN;
	}else if(licno!=null){
		url="memberBasic.do?method=mSearchr&licno="+licno+"&gbran="+G_BRAN;
	}else if(email!=null){
		url="memberBasic.do?method=mSearchr&email="+email+"&gbran="+G_BRAN;
	}else if(compname!=null){
		url="memberBasic.do?method=mSearchr&compname="+compname+"&gbran="+G_BRAN;
	}else if(memberid!=null){
		url="memberBasic.do?method=mSearchr&memberid="+memberid+"&gbran="+G_BRAN;
	}
%>
<script type="text/javascript">
document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};
function init(){
	var logid="<%=G_NAME%>";
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		opener.document.location.href="login.do?method=login";
		self.close();
	}
}

$(document).ready(function(){
jQuery("#list").jqGrid({
      url: "<%=url%>",
      datatype: "json",
      mtype: 'POST',
      height:'585',
      width:'1180',
      colNames: ['이름',
//                 	JUMIN_DEL
//                  '주민번호',
				 '생년월일',
                 '면허번호','근무처명','주소','회원구분','회원상태','아이디','휴대폰','이메일','전화번호','회원코드'],
      colModel: [
			{name:'pers_name',		index:'pers_name', 		width:40, 	sortable:false, align:'center'},
// 			JUMIN_DEL
//    			{name:'pers_no',			index:'pers_no', 			width:70,	sortable:false,	formatter:juminNo , align:'center'},
   			{name:'pers_birth',				index:'pers_birth', 				width:40, 	sortable:false,	formatter:persBirth},
   			{name:'lic_no',				index:'lic_no', 				width:40, 	sortable:false},
   			{name:'company_name',			index:'company_name',		 	width:70,	sortable:false},
   			{name:'pers_add',			index:'pers_add', 			width:180, 	sortable:false},		
   			{name:'code_member',	index:'code_member', 	width:60,	sortable:false},
   			{name:'pers_state',		index:'pers_state', 		width:40,	sortable:false, align:'center'},
   			{name:'id',					index:'id',				 	width:50,	sortable:false},
   			{name:'pers_hp',			index:'pers_hp',		 	width:50,	sortable:false},
   			{name:'email',				index:'email',			 	width:50,	sortable:false},
   			{name:'pers_tel',			index:'pers_tel',		 	width:50,	sortable:false},
   			{name:'code_pers',		index:'code_pers',		 	width:50,	sortable:false,	hidden:true}
                ],
                rowNum:100000000,
//                 rowNum:25,
                pager: '#pager2',
                viewrecords: true,
				
				altRows:true,
				altclass:'myAltRowClass',
				rownumbers : true,				
				onSelectRow: function(ids) {
					if(ids == null) {
						ids=0;
						if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
						{
							alert("컬럼을 정확히 선택해 주세요");
						}
					} else {
						var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
						if(id){
							var ret = jQuery("#list").jqGrid('getRowData',id);
							var pCode=ret.code_pers;							
							//document.location.href="memberBasic.do?method=memInfo&pCode="+pCode;							
							//그리드에서 클릭했을때 새창으로 띄우려면 윗줄의 document.location.href="memberBasic.do?method=memInfo&pCode="+pCode; 를 주석처리하고
							//밑의 window.open을 주석해제하면 된다.
							window.open("memberBasic.do?method=memInfo&pCode="+pCode,'dSearch','width=1130, height=700, resizable=no, scrollbars=yes');
						}
						else {}
					}
				}
      
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

// JUMIN_DEL
// function juminNo(cellvalue, options, rowObject ){
// 	var jumin=cellvalue.substring(0,6)+" - *******";
// 	return jumin;
// }

function persBirth(cellvalue, options, rowObject ){
// 	var birth=cellvalue.substring(0,4)+"."+cellvalue.substring(4,6)+"."+cellvalue.substring(6,8);
// 	return birth;
	return cellvalue; 
}


</script>
</head>

<body>
<table id="list"></table>
<div id="pager2"></div>
</body>
</html>
