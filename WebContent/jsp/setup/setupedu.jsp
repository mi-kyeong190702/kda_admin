<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>

<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
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
<script src="js/form.js"  type="text/javascript"></script>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@ page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%@page import="com.ant.document.dao.documentDao"%>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

<%
	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

	Map map = new HashMap();
	Map remap = new HashMap();
	
	documentDao dao = new documentDao();
	StringBuffer detcodename = new StringBuffer();
	List<Map> comcodesearch = dao.comcodesearch(map);	
		 
	StringBuffer certifi_name = new StringBuffer();
	List<Map> certifisearch = dao.certifisearch(map); 	

	String logid="";
	if(session.getAttribute("G_NAME")!=null){
		logid=session.getAttribute("G_NAME").toString();
	}
	String errMsg="";
	if(request.getAttribute("errMsg")!=null){
		errMsg=request.getAttribute("errMsg").toString();
	}
	
	String edutest_name1="";
	String code_certifi1="";
	String code_kind1="";
	String edu_group1="";
	String use_yn1="";
	String conform1="";
	String outside_yn1="";
	String print_kind1="";
// 	String finish_time1="";
// 	String finish_date1="";
// 	String finish_point1="";
// 	String edu_marks1="";
// 	String new_cost1="";
// 	String re_cost1="";
// 	String mr_cost1="";
// 	String service_marks1="";
	String point_manage1="";
	String remark1="";
	
	String edutest_name2="";
	String code_certifi2="";
	String code_kind2="";
	String edu_group2="";
	String use_yn2="";
	String conform2="";
	String outside_yn2="";
	int remaps=0;
	if(request.getAttribute("remap")!=null){
		remap=(HashMap)request.getAttribute("remap");
		edutest_name1	=remap.get("edutest_name1").toString();
		code_certifi1	=remap.get("code_certifi1").toString();
		code_kind1		=remap.get("code_kind1").toString();
		edu_group1		=remap.get("edu_group1").toString();
		use_yn1			=remap.get("use_yn1").toString();
		conform1		=remap.get("conform1").toString();
		outside_yn1		=remap.get("outside_yn1").toString();
		print_kind1		=remap.get("print_kind1").toString();
// 		finish_time1	=remap.get("finish_time1").toString();
// 		finish_date1	=remap.get("finish_date1").toString();
// 		finish_point1	=remap.get("finish_point1").toString();
// 		edu_marks1		=remap.get("edu_marks1").toString();
// 		new_cost1		=remap.get("new_cost1").toString();
// 		re_cost1		=remap.get("re_cost1").toString();
// 		mr_cost1		=remap.get("mr_cost1").toString();
// 		service_marks1	=remap.get("service_marks1").toString();
		point_manage1	=remap.get("point_manage1").toString();
		remark1			=remap.get("remark1").toString();
		
		if(remap.get("edutest_name2")!="")	edutest_name2	=remap.get("edutest_name2").toString();
		if(remap.get("code_certifi2")!="")	code_certifi2	=remap.get("code_certifi2").toString();
		if(remap.get("code_kind2")!="")	code_kind2		=remap.get("code_kind2").toString();
		if(remap.get("edu_group2")!="")	edu_group2		=remap.get("edu_group2").toString();
		if(remap.get("use_yn2")!="")	use_yn2			=remap.get("use_yn2").toString();
		if(remap.get("conform2")!="")	conform2		=remap.get("conform2").toString();
		if(remap.get("outside_yn2")!="")	outside_yn2		=remap.get("outside_yn2").toString();
		remaps			=remap.size();
	}
	
%>

<script type="text/javascript">

document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};

function init(){	
	var logid="<%=logid%>";
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		opener.document.location.href="login.do?method=login";
		self.close();
	}
	var f=document.sForm;
	if(<%=remaps%>>0){
		alert('<%=errMsg%>');
		<%-- var errMsg="<%=errMsg%>";
		if(errMsg.length>5){
			alert(errMsg);
			return;
		} --%>
		f.edutest_name1.value	='<%=edutest_name1%>';
		f.code_certifi1.value	='<%=code_certifi1%>';
		f.code_kind1.value		='<%=code_kind1%>';
		f.edu_group1.value		='<%=edu_group1%>';
		f.use_yn1.value			='<%=use_yn1%>';
		f.conform1.value		='<%=conform1%>';
		f.outside_yn1.value		='<%=outside_yn1%>';
		f.print_kind1.value		='<%=print_kind1%>';
<%-- 		f.finish_time1.value	='<%=finish_time1%>'; --%>
<%-- 		f.finish_date1.value	='<%=finish_date1%>'; --%>
<%-- 		f.finish_point1.value	='<%=finish_point1%>'; --%>
<%-- 		f.edu_marks1.value		='<%=edu_marks1%>'; --%>
<%-- 		f.new_cost1.value		='<%=new_cost1%>'; --%>
<%-- 		f.re_cost1.value		='<%=re_cost1%>'; --%>
<%-- 		f.mr_cost1.value		='<%=mr_cost1%>'; --%>
<%-- 		f.service_marks1.value	='<%=service_marks1%>'; --%>
		<%-- f.point_manage1.value	='<%=point_manage1%>';
		f.remark1.value			='<%=remark1%>'; --%>
		
		var parm ="";  
	    
	    if('<%=edutest_name2%>'     !=""){
	    	parm+="&edutest_name1="  +'<%=edutest_name2%>';
	    }   
	    if('<%=code_certifi2%>'     !=""){
	    	parm+="&code_certifi1="  + '<%=code_certifi2%>';
	    }    
	    if('<%=code_kind2%>'        !=""){
	    	parm+="&code_kind1="     + '<%=code_kind2%>';
	    } 
	    if('<%=outside_yn2%>'       !=""){
	    	parm+="&outside_yn1="    + '<%=outside_yn2%>';
	    }  
	    if('<%=edu_group2%>'        !=""){
	    	parm+="&edu_group1="     + '<%=edu_group2%>';
	    } 
	    if('<%=use_yn2%>'           !=""){
	    	parm+="&use_yn1="        + '<%=use_yn2%>';
	    }
	    if('<%=conform2%>'          !=""){
	    	parm+="&conform1="       + '<%=conform2%>';
	    }    
		
		$('#list').jqGrid('clearGridData');
		jQuery("#list").setGridParam({url:"setupdues.do?method=setupeduList&isSelect=Y"+parm}).trigger("reloadGrid");
	}	
}

function getAjax(strUrl,funcReceive){
	  $.ajax({
	   cache:false,
	   type:'GET',
	   url:strUrl,
	   dataType:'html',
	   timeout:60000,
	   success:function(html,textStatus){
	    funcReceive(html,textStatus);
	    document.all.div_wait.style.visibility  = "hidden" ;
	   },
	   error: function(xhr,textStatus,errorThrown){
	    document.all.div_wait.style.visibility  = "hidden" ;
	    alert(textStatus);
	    alert("전송에러");
	   }
	  });
	 }

	 function receiveList(data){
	  var jsonData=eval("("+data+")");
	  $("#list").clearGridData();
	  for(var i=0;i<=jsonData.data.length;i++) $("#list").addRowData(i+1,jsonData.data[i]);
	 }

$(document).ready(function(){	
jQuery("#list").jqGrid({	  
	  url: 'setupdues.do?method=setupeduList',
	  datatype: "json",
      mtype: 'post',     
      height:'365',
      colNames: [ ' 교육명 ','구분코드','구분','교육종류코드','교육종류','평점종류','평점종류','교육구분코드','교육구분','사용','승인','순번','평점관리',
//                   '교육평점','봉사점수',
					'수료여부코드','수료여부',
//                   '이수시간','이수일수','이수점수','신규비용','재비용','석사비용',
                  '비고'],
      colModel: [
			{name:'edutest_name',     index:'edutest_name',     width: 300,editable: false, align: 'left', formatter:decode11},
   			{name:'code_certifi',     index:'code_certifi',     hidden:true},
   			{name:'certifi_name',     index:'certifi_name',     width: 50,editable:  false, align: 'center'},
   			{name:'code_kind', 	      index:'code_kind',        hidden:true},
   			{name:'code_kind_name',   index:'code_kind_name',   width: 160,editable: false, align: 'center'},
   			{name:'outside_yn',       index:'outside_yn',       hidden:true},
   			{name:'outside_yn_name',  index:'outside_yn_name',  width: 70,editable:  false, align: 'center'},
   			{name:'edu_group',        index:'edu_group',        hidden:true},
   			{name:'edu_group_name',   index:'edu_group_name',   width: 70,editable:  false, align: 'center'},
   			{name:'use_yn',           index:'use_yn',           width: 30,editable:  false, align: 'center'},		
   			{name:'conform',          index:'conform',          width: 30,editable:  false, align: 'center'},
   			{name:'code_seq',         index:'code_seq',         hidden:true},
   			{name:'point_manage',     index:'point_manage',     width: 70,editable:  false, align: 'center'},
//    			{name:'edu_marks', 	      index:'edu_marks',        width: 70,editable:  false, align: 'right'},
//    			{name:'service_marks', 	  index:'service_marks',    width: 70,editable:  false, align: 'right'},
   			{name:'print_kind',       index:'print_kind',       hidden:true},
   			{name:'print_kind_name',  index:'print_kind_name',  width: 70,editable:  false, align: 'center'},
//    			{name:'finish_time',      index:'finish_time',      width: 70,editable:  false, align: 'right'},
//    			{name:'finish_date',      index:'finish_date',      width: 70,editable:  false, align: 'right'},		
//    			{name:'finish_point',     index:'finish_point',     width: 70,editable:  false, align: 'right'},	
//    			{name:'new_cost',         index:'new_cost',         width: 100,editable: false, align: 'right', formatter:'currency'},		
//    			{name:'re_cost',          index:'re_cost',          width: 100,editable: false, align: 'right', formatter:'currency'},
//    			{name:'mr_cost',          index:'mr_cost',          width: 100,editable: false, align: 'right', formatter:'currency'},		
   			{name:'remark',           index:'remark',           width: 100,editable: false, align: 'left', formatter:decode11},	
   			],
   		    rowNum:15,
   		   	pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '교육 및 시험명',
			altclass:'myAltRowClass',
			
			onSelectRow: function(ids) {
				goSelect();
				if(ids == null) {
					ids=0;
					if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
					{
						alert("컬럼을 정확히 선택해 주세요");
					}
				} else {

					//ids 는 몇번째인가, 아래 page 는 몇 페이지인가 . 계산해서 처리하자.
					//alert("page = "+jQuery("#list").jqGrid('getGridParam','page')+" row_id = "+ids);
					var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
					if(id){
						var ret = jQuery("#list").jqGrid('getRowData',id);
						var id = ret.id;
						//alert(id);
						//onSubmitList(id);
					}
					else {}
				
				}
			}
  
});
powerinit();
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
jQuery("#list").setGridWidth(1100,false);
});

function decode11(cellvalue, options, rowObject ){
	var temp=decodeURIComponent(cellvalue);	
	return replaceAll(temp,"+"," ");
}

function replaceAll(temp,org,rep){
	return temp.split(org).join(rep);	
}

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "704";
	var bcheck = "N";
//	alert("userpowerm1[i].progid===>"+userpowerm1.length);
//	alert(userpowerm1);
	for(i=0;i<userpowerm1.length;i++){
		var setprogid=userpowerm1[i].progid;
		//alert("찾기.=======>"+setprogid);
		if (meprogid == setprogid){
			bcheck = "Y";
		}
	}
	if(bcheck=="N"){
		alert("프로그램에 대한 사용권한이 없습니다.");
		history.back(-1);
	}

	var logid="<%=g_name%>";
	if(logid==null||logid==""){
		alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
		opener.document.location.href="login.do?method=login";
		self.close();
	}
}

function goSelect(rowid,iCol){
	goClear();
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
		
		document.sForm.edutest_name1.value      = list.edutest_name;
		document.sForm.code_certifi1.value      = list.code_certifi;
		document.sForm.code_kind1.value         = list.code_kind;
		document.sForm.outside_yn1.value        = list.outside_yn;
		document.sForm.edu_group1.value         = list.edu_group;
		document.sForm.use_yn1.value            = list.use_yn;
		document.sForm.conform1.value           = list.conform;
		document.sForm.code_seq1.value          = list.code_seq;
		
		var  t1   = list.point_manage.substr(0,1);
		var  t2   = list.point_manage.substr(1,1);
		var  t3   = list.point_manage.substr(2,1);
		var  t5   = list.point_manage.substr(3,1);
		var  t6   = list.point_manage.substr(4,1);
		var  t4   = list.point_manage.substr(5,1);
		//2025.02.04 평점관리 추가
		//t7 : 시니어푸드코디네이터, t8 : 다이어트운동컨설턴트
		var  t7   = list.point_manage.substr(6,1);
		var  t8   = list.point_manage.substr(7,1);
		
		if(list.point_manage == "1111111111") {
			sForm.t0.checked = true;
		}else{
			if(t1 == "1"){
				sForm.t1.checked = true;
			}
			if(t2 == "1"){
				sForm.t2.checked = true;
			}
			if(t3 == "1"){
				sForm.t3.checked = true;
			}
			if(t5 == "1"){
				sForm.t5.checked = true;
			}
			if(t6 == "1"){
				sForm.t6.checked = true;
			}
			if(t4 == "1"){
				sForm.t4.checked = true;
			}
			if(t7 == "1"){
				sForm.t7.checked = true;
			}
			if(t8 == "1"){
				sForm.t8.checked = true;
			}
		}		
	
// 		document.sForm.edu_marks1.value         = list.edu_marks;
// 		document.sForm.service_marks1.value     = list.service_marks;
		document.sForm.print_kind1.value        = list.print_kind;
// 		document.sForm.finish_time1.value       = list.finish_time;
// 		document.sForm.finish_date1.value       = list.finish_date;
// 		document.sForm.finish_point1.value      = list.finish_point;
// 		document.sForm.new_cost1.value          = list.new_cost;
// 		document.sForm.re_cost1.value           = list.re_cost;
// 		document.sForm.mr_cost1.value           = list.mr_cost; 
		document.sForm.remark1.value            = list.remark; 
	}
}

function goClear(){	
	$('#list1').each(function(){
		this.reset();		
	});	
}

function goInsert(){
	var  edutest_name1   = sForm.edutest_name1.value;
	var  code_certifi1   = sForm.code_certifi1.value;
	var  code_kind1      = sForm.code_kind1.value;
	var  code_seq1       = sForm.code_seq1.value;
	var  use_yn1         = sForm.use_yn1.value;
	var  conform1        = sForm.conform1.value;
// 	var  edu_marks1      = sForm.edu_marks1.value;
// 	var  service_marks1  = sForm.service_marks1.value;
// 	var  finish_time1    = sForm.finish_time1.value;
// 	var  finish_date1    = sForm.finish_date1.value;
// 	var  finish_point1    = sForm.finish_point1.value;
	var  print_kind1     = sForm.print_kind1.value;
	var  outside_yn1     = sForm.outside_yn1.value;
// 	var  edu_marks1      = sForm.edu_marks1.value;
	
	var  point_manage1   = "0000000000";
	var  t1   = point_manage1.substr(0,1);	
	var  t2   = point_manage1.substr(1,1);
	var  t3   = point_manage1.substr(2,1);
	var  t5   = point_manage1.substr(3,1);
	var  t6   = point_manage1.substr(4,1);
	var  t4   = point_manage1.substr(5,1);
	//2025.02.04 평점관리 추가
	//t7 : 시니어푸드코디네이터, t8 : 다이어트운동컨설턴트
	var  t7   = point_manage1.substr(6,1);
	var  t8   = point_manage1.substr(7,1);
	
	if (sForm.t1.checked) {
    	t1 = '1';
    }
    if (sForm.t2.checked) {
    	t2 = '1';
    }
    if (sForm.t3.checked) {
    	t3 = '1';
    }
    if (sForm.t4.checked) {
    	t4 = '1';
    }
    if (sForm.t5.checked) {
    	t5 = '1';
    }
    if (sForm.t6.checked) {
    	t6 = '1';
    }
    if (sForm.t7.checked) {
    	t7 = '1';
    }
    if (sForm.t8.checked) {
    	t8 = '1';
    }
       
    if (sForm.t0.checked) {
    	point_manage1 = '1111111111';    	
    }else {
    	  point_manage1 = t1+t2+t3+t5+t6+t4+t7+t8+'0'+'0';   
    }
    
    if ( edutest_name1 == "" ) {
        alert("교육명을 입력해주세요.") ;
        sForm.edutest_name1.focus();
        return ;
    }
    if( code_certifi1 ==""){
    	alert("구분코드를 선택해주세요");
    	return;
    }
    if( code_kind1 ==""){
    	alert("교육종류를 선택해주세요");
    	return;
    }
    if( use_yn1 == ""){
    	alert("사용여부를 선택해주세요");
    	return;
    }
    if( conform1 == ""){
    	alert("승인여부를 선택해주세요");
    	return;
    }
//    	if((edu_marks1 > "0")&&(service_marks1 > "0")){    	
//     	alert("교육평점 봉사점수 중 한 가지만 입력해 주십시오.") ;
//         sForm.edu_marks1.focus();
//         return ;
//     }
    
   	if( code_kind1 == "1"){
//    		if((finish_date1==""||finish_date1<="0")&&(finish_point1==""||finish_point1<="0")){
//    			alert("이수일수, 이수점수를 확인해주십시오.");
//    			return;
//    		}
   		if(print_kind1!="1"){
   			alert("자격증 교육에서 증서는 수료증만 선택하실 수 있습니다.");
   			return;
   		}
   	}else if( code_kind1 == "2"){
   		if(outside_yn1 ==""){
   			alert("평점종류를 선택해주십시오.");
   			return;
   		}
//    		if((finish_date1==""||finish_date1<="0")&&(finish_time1==""||finish_time1<="0")){
//    			alert("이수일수, 이수시간 확인해주십시오.");
//    			return;
//    		}
//    		if(edu_marks1==""&&service_marks1==""){
//    			alert("교육평점 혹은 봉사점수를 입력해주십시오.");
//    			return;
//    		}
//    		if(service_marks1==""){
//    			alert("봉사점수를 입력해주십시오.");
//    			return;
//    		}
   		if(point_manage1 == "0000000000"){
   			alert("평점관리를 체크해주십시오.");
   			return;
   		}
   	}else if(code_kind1 == "4"){
//    		if(finish_time1==""||finish_time1<="0"){
//    			alert("이수시간을 확인해주십시오.");
//    			return;
//    		}
   		if(print_kind1!="4"){
   			alert("위생교육에서 증서는 이수증만 선택하실 수 있습니다.");
   			return;
   		}
   	}else if(code_kind1 == "6"){
//    		if(edu_marks1==""&&service_marks1==""){
//    			alert("교육평점 혹은 봉사점수를 입력해주십시오.");
//    			return;
//    		}
//    		if(service_marks1==""){
//    			alert("봉사점수를 입력해주십시오.");
//    			return;
//    		}
   	}
   
    if ( code_seq1 == "" ) { 
    	document.sForm.action = "setupdues.do?method=insert_edu&point_manage1="+point_manage1;
    	document.sForm.submit();  
    }else { 
    	document.sForm.action = "setupdues.do?method=update_edu&point_manage1="+point_manage1+"&code_seq1="+code_seq1;
    	document.sForm.submit();
	 }	
}

function goSearch(form,intPage){
	
	var parm ="";  
    
    if(sForm.edutest_name1.value     !=""){
    	parm+="&edutest_name1="  +escape(encodeURIComponent(sForm.edutest_name1.value));
    	sForm.edutest_name2.value=escape(encodeURIComponent(sForm.edutest_name1.value));
    }   
    if(sForm.code_certifi1.value     !=""){
    	parm+="&code_certifi1="  + sForm.code_certifi1.value;
    	sForm.code_certifi2.value=sForm.code_certifi1.value;
    }    
    if(sForm.code_kind1.value        !=""){
    	parm+="&code_kind1="     + sForm.code_kind1.value;
    	sForm.code_kind2.value=sForm.code_kind1.value;
    } 
    if(sForm.outside_yn1.value       !=""){
    	parm+="&outside_yn1="    + sForm.outside_yn1.value;
    	sForm.outside_yn2.value =sForm.outside_yn1.value;
    }  
    if(sForm.edu_group1.value        !=""){
    	parm+="&edu_group1="     + sForm.edu_group1.value;
    	sForm.edu_group2.value=sForm.edu_group1.value;
    } 
    if(sForm.use_yn1.value           !=""){
    	parm+="&use_yn1="        + sForm.use_yn1.value;
    	sForm.use_yn2.value=sForm.use_yn1.value;
    }
    if(sForm.conform1.value          !=""){
    	parm+="&conform1="       + sForm.conform1.value;
    	sForm.conform2.value=sForm.conform1.value;
    }    
	
	$('#list').jqGrid('clearGridData');
	jQuery("#list").setGridParam({url:"setupdues.do?method=setupeduList&isSelect=Y"+parm}).trigger("reloadGrid");
}

function chk_pointm(val){
	switch(val){
	case '0': sForm.t0.checked=true; sForm.t1.checked=false; sForm.t2.checked=false; sForm.t3.checked=false; sForm.t4.checked=false; sForm.t5.checked=false; sForm.t6.checked=false; break;
	case '1': sForm.t0.checked=false; sForm.t1.checked=true; sForm.t2.checked=false; sForm.t3.checked=false; sForm.t4.checked=false; sForm.t5.checked=false; sForm.t6.checked=false; break;
	case '2': sForm.t0.checked=false; sForm.t1.checked=false; sForm.t2.checked=true; sForm.t3.checked=false; sForm.t4.checked=false; sForm.t5.checked=false; sForm.t6.checked=false; break;
	case '3': sForm.t0.checked=false; sForm.t1.checked=false; sForm.t2.checked=false; sForm.t3.checked=true; sForm.t4.checked=false; sForm.t5.checked=false; sForm.t6.checked=false; break;
	case '4': sForm.t0.checked=false; sForm.t1.checked=false; sForm.t2.checked=false; sForm.t3.checked=false; sForm.t4.checked=false; sForm.t5.checked=true; sForm.t6.checked=false; break;
	case '5': sForm.t0.checked=false; sForm.t1.checked=false; sForm.t2.checked=false; sForm.t3.checked=false; sForm.t4.checked=false; sForm.t5.checked=false; sForm.t6.checked=true; break;	
	case '9': sForm.t0.checked=false; sForm.t1.checked=false; sForm.t2.checked=false; sForm.t3.checked=false; sForm.t4.checked=true; sForm.t5.checked=false; sForm.t6.checked=false; break;
	default: sForm.t0.checked=false; sForm.t1.checked=false; sForm.t2.checked=false; sForm.t3.checked=false; sForm.t4.checked=false; sForm.t5.checked=false; sForm.t6.checked=false;
	}
}
</script>
</head>

<body>
<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">환경설정</a></li>
		<li>&gt;</li>
		<li class="NPage">교육 및 시험명</li>
	  </ul>	
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="sForm" method="post">
	<input type="hidden" name="edutest_name2" value=""/> 
	<input type="hidden" name="code_certifi2" value=""/> 
	<input type="hidden" name="code_kind2" value=""/> 
	<input type="hidden" name="outside_yn2" value=""/> 
	<input type="hidden" name="edu_group2" value=""/> 
	<input type="hidden" name="use_yn2" value=""/> 
	<input type="hidden" name="conform2" value=""/> 
<!-- 중복처리방지 토큰 처리 시작-->
<%
	//중복처리방지 토큰 처리 - 페이지에 그대로 추가
	String token=TokenProcessor.getInstance().generateToken(request); //현재 시간과 세션아이디를 갖고 token 키생성

	if(session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY)==null){
		session.setAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY,token);	
	%>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
	<%}else{%>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
<%}%> 
<!-- 중복처리방지 토큰 처리  끝-->	  

        <table class="ctable_03" cellspacing="0" summary="교육 및 시험명">			
            <tr>
               <td class="nobga" align="center">교육명</td>
               <td class="nobg1a"><input type="text" id="m" name="edutest_name1" size="15" /></td> 
               <td class="nobg2a" align="center">구분코드</td>
               <td class="nobg1a">
              		<select  id="name" name="code_certifi1" onChange="javascript:chk_pointm(this.value);">
              		<option value="">선택</option>		
              		 <% 
                	String code_Certifi,Certifi_name=null;
                	for(int i=0;i<certifisearch.size();i++){                		
                		code_Certifi=certifisearch.get(i).get("code_certifi").toString();
                		Certifi_name=certifisearch.get(i).get("certifi_name").toString();
                			out.println("<option value="+code_Certifi+">"+Certifi_name+"</option>");                		
                	}
               		 %>  
               	    </select>
			   </td> 		   
			   <td class="nobg2a" align="center">교육종류</td>
			   <td class="nobg1a">
              		<select  id="name" name="code_kind1">
              		<option value="">전체</option>		
              		 <% 
              		String detCode,detCName=null;
              			for(int i=0;i<comcodesearch.size();i++){
                    		if("016".equals(comcodesearch.get(i).get("groupcode").toString())){
                    			detCode=comcodesearch.get(i).get("detcode").toString();
                    			detCName=comcodesearch.get(i).get("detcodename").toString();
                    			out.println("<option value="+detCode+">"+detCName+"</option>");
                    		}
                    	}	              	                	
               		 %>  
               	    </select>
			   </td> 			 
			   <td class="nobg2a" align="center">순번</td>
			   <td class="nobg1a"><input type="text" id="name" name="code_seq1" size="15" disabled="true"/></td> 
			</tr> 
			<tr>   
			   <td class="alt1" align="center">교육구분</td>
               <td>
              		<select  id="name" name="edu_group1">
              		<option value="">선택</option>	
              		 <% 
                	String detCode2,detCName2=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("017".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode2=comcodesearch.get(i).get("detcode").toString();
                			detCName2=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode2+">"+detCName2+"</option>");
                		}
                	}
               		 %>  
               	    </select>
			   </td> 
			   <td class="alt" align="center">사용여부</td>
               <td ><select id="name" name="use_yn1" align="center">	
                    <option value="">전체</option>
                    <option value="Y">사용</option>
	                <option value="N">미사용</option>	                
               </select></td>		   
               <td class="alt" align="center">승인여부</td>
               <td ><select id="name" name="conform1" align="center">
               <%if(g_bran.equals("01")){ %>		
                    <option value="">전체</option>
                    <option value="N">미승인</option>
                    <option value="Y">승인</option>
               <%}else{ %>
               		<option value="">전체</option>
                    <option value="N">미승인</option>
               <%} %>     	                	               
               </select></td> 
               <td class="alt" align="center">평점종류</td>
               <td>               	
              		<select  id="name" name="outside_yn1">	
              		<option value="">선택</option>
              		 <% 
                	String detCode1,detCName1=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("033".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode1=comcodesearch.get(i).get("detcode").toString();
                			detCName1=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode1+">"+detCName1+"</option>");
                		}
                	}
               		 %>  
               	    </select>
			   </td>              
			  </tr>
          </table><br>  
          <table class="ctable_03" cellspacing="0" summary="교육 및 시험명">
<!--           <tr> -->
<!--            	   <td class="nobga" align="center" >증서구분</td> -->
<!--                <td class="nobg1a"><select  id="name" name="print_kind1" >	 -->
              	 <% 
//                 	String detCode3,detCName3=null;
//                 	for(int i=0;i<comcodesearch.size();i++){
//                 		if("029".equals(comcodesearch.get(i).get("groupcode").toString())){
//                 			detCode3=comcodesearch.get(i).get("detcode").toString();
//                 			detCName3=comcodesearch.get(i).get("detcodename").toString();
//                 			out.println("<option value="+detCode3+">"+detCName3+"</option>");
//                 		}
//                 	}
               	  %>  
<!--                  </select></td> 		              	                   		              			   -->
<!--                <td class="nobg2a" align="center">이수시간</td> -->
<!--                <td class="nobg1a"><input type="text" id="finish_time1" name="finish_time1" size="15" onKeyUp="javascript:check_Int('sForm','finish_time1')"/></td> -->
<!-- 			   <td class="nobg2a" align="center" >이수일수</td> -->
<!--                <td class="nobg1a"><input type="text" id="finish_date1" name="finish_date1" size="15" onKeyUp="javascript:check_Int('sForm','finish_date1')"/></td> -->
<!--                <td class="nobg2a" align="center" >이수점수</td> -->
<!--                <td class="nobg1a"><input type="text" id="finish_point1" name="finish_point1" size="15" onKeyUp="javascript:check_Int('sForm','finish_point1')"/></td> -->
<!--                <td class="nobg2a" style="width:75%;" colspan="6">&nbsp;</td> -->
<!-- 			  </tr>	 -->
<!-- 			  <tr> -->
<!-- 			   <td class="alt1" align="center">교육평점</td> -->
<!--            	   <td ><input type="text" id="edu_marks1" name="edu_marks1" size="15" onKeyUp="javascript:check_Int('sForm','edu_marks1')"/></td> -->
<!--            	    <td class="alt" align="center">신규비용</td> -->
<!--                <td><input type="text" id="new_cost1" name="new_cost1" size="15" onKeyUp="javascript:check_Int('sForm','new_cost1')"/></td> -->
<!--            		<td class="alt" align="center">재시험비용</td> -->
<!--                <td><input type="text" id="re_cost1" name="re_cost1" size="15" onKeyUp="javascript:check_Int('sForm','re_cost1')"/></td> -->
<!--                <td class="alt" align="center">석사이상비용</td> -->
<!--                <td><input type="text" id="mr_cost1" name="mr_cost1" size="15" onKeyUp="javascript:check_Int('sForm','mr_cost1')"/></td>  -->
<!--               </tr>           	 -->
              <tr>
<!--                <td class="alt1" align="center">봉사점수</td> -->
<!--            	   <td ><input type="text" id="service_marks1" name="service_marks1" size="15" onKeyUp="javascript:check_Int('sForm','service_marks1')"/></td> -->

           	   <td class="nobga" align="center" >증서구분</td>
               <td class="nobg1a"><select  id="name" name="print_kind1" >	
              	 <% 
                	String detCode3,detCName3=null;
                	for(int i=0;i<comcodesearch.size();i++){
                		if("029".equals(comcodesearch.get(i).get("groupcode").toString())){
                			detCode3=comcodesearch.get(i).get("detcode").toString();
                			detCName3=comcodesearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode3+">"+detCName3+"</option>");
                		}
                	}
               	  %>  
                 </select></td> 		              	                   		              			  

               <td class="nobg2a" align="center" style="width:8%;">평점관리</td>
               <td class="nobg1a" colspan="3"  id="m" name="point_manage1" align="left" style="width:50%;">             		
               		<input type="checkbox" name="t0" id="t0" />&nbsp;<label for="t0">전문</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="t1" id="t1" />&nbsp;<label for="t1">급식경영</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="t2" id="t2" />&nbsp;<label for="t2">임상영양</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="t3" id="t3" />&nbsp;<label for="t3">산업보건</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="t5" id="t5" />&nbsp;<label for="t5">노인영양사</label><br>
               		<input type="checkbox" name="t6" id="t6" />&nbsp;<label for="t6">스포츠영양사</label>&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="t7" id="t7" />&nbsp;<label for="t7">시니어푸드코디네이터</label>&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="t8" id="t8" />&nbsp;<label for="t8">다이어트운동컨설턴트</label>&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="t4" id="t4" />&nbsp;<label for="t4">기타</label></td>		       
               <td class="nobg2a" align="center" style="width:8%;">비고</td>
               <td class="nobg1a" style="width:17%;"><input type="text" id="m" name="remark1" size="15" /></td>
              </tr>  
			 </table>			
    	 <ul class="btnIcon_2">	             
	  	   <li><a href="#"><img src="images/icon_new.gif"    onclick="javascript:goClear();" alt="신규" /></a></li>		 
		   <li><a href="#"><img src="images/icon_save.gif"   onclick="javascript:goInsert();" alt="저장" /></a></li>
		   <li><a href="#"><img src="images/icon_search.gif" onclick="javascript:goSearch(sForm,0);" alt="검색" /></a></li>	 
		 </ul>	
</form>
<br><br>

<table id="list"></table>
<div id="pager2"></div>


</div>
</div>
</body>
</html>

  