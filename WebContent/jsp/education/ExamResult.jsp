<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.ant.educationexam.dao.educationDao"%>
<%@page import="com.ant.common.util.StringUtil"%>
<%@ page import="com.ant.common.properties.AntData"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>대한영양사협회</title>
  <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
  <link rel="stylesheet" type="text/css" href="css/import.css" />  
  <link rel="stylesheet" type="text/css" href="css/education.css" />
  <script type="text/javascript" src="js/comm.js"></script>
  <script src="js/form.js"  type="text/javascript"></script>
  
<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
.readOnly1 {background-color:#f2f2f2;}
</style>  
  
<%

	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();
	
	Map map = new HashMap();
	educationDao dao = new educationDao();

	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
	
	String v_yyyy1=request.getParameter("yyyy1");
	String v_code_bran1=request.getParameter("code_bran1");
	String v_code_kind1=request.getParameter("code_kind1");
	
	List<Map> certifisearch1 = dao.certifisearch1(map);
	List<Map> certifisearch = dao.certifisearch(map);
	// 동일조건에 맞는 값을  edutake1 에 넣어준다
	Map 	  edutake4	= (Map)request.getAttribute("edutake4");
	Map 	  edutakeets4	= (Map)request.getAttribute("edutakeets4");
	Map		  edutakeets5	= (Map)request.getAttribute("edutakeets5");
	String	  batchSaved	= (String)request.getAttribute("batchSaved");
	String	  errMsg	= (String)request.getAttribute("errMsg");
	String	  Msg	= (String)request.getAttribute("Msg");
	if ( batchSaved == null) batchSaved = "";
	if ( errMsg == null) errMsg = "";
	if ( Msg == null) Msg = "";
	
	int etk2=0;
	String v_yyyy11="";
	String v_code_bran11="";
	String v_detcode11="";
	String v_code_kind11="";
	
	String v_code_certifi11="";
	String v_oper_name11="";
	String v_oper_lic_no11="";
	String v_oper_hp11="";
	String v_oper_email11="";
	String v_code_operation11="";
	String v_oper_state11="";
	String v_oper_comp_name11="";
	String v_result_point11="";
	String v_attend_cnt11="";
	String v_time_cnt11="";
	String v_receipt_no11="";
	String v_result_state11="";
	
	if(edutakeets4!=null){
		etk2=edutakeets4.size();
		v_yyyy11=edutakeets4.get("yyyy1").toString();
		v_code_bran11=edutakeets4.get("code_bran1").toString();
		v_code_kind11=edutakeets4.get("code_kind1").toString();
		v_detcode11=edutakeets4.get("detcode1").toString();
		
		v_code_certifi11=edutakeets4.get("code_certifi1").toString();
		v_oper_name11=edutakeets4.get("oper_name1").toString();
		v_oper_lic_no11=edutakeets4.get("oper_lic_no1").toString();
		v_oper_hp11=edutakeets4.get("oper_hp1").toString();
		v_oper_email11=edutakeets4.get("oper_email1").toString();
		v_code_operation11=edutakeets4.get("code_operation1").toString();
		v_oper_state11=edutakeets4.get("oper_state1").toString();
		v_oper_comp_name11=edutakeets4.get("oper_comp_name11").toString();
		v_result_point11=edutakeets4.get("result_point1").toString();
		v_attend_cnt11=edutakeets4.get("attend_cnt1").toString();
		v_time_cnt11=edutakeets4.get("time_cnt1").toString();
		v_receipt_no11=edutakeets4.get("receipt_no1").toString();
		v_result_state11=edutakeets4.get("result_state1").toString();
	}
	
	if("Y".equals(batchSaved)){
		v_yyyy11=edutake4.get("yyyy1").toString();
		v_code_bran11=edutake4.get("code_bran1").toString();
		v_code_kind11=edutake4.get("code_kind1").toString();
		v_detcode11=edutake4.get("detcode1").toString();
		v_code_certifi11=edutake4.get("code_certifi1").toString();
	}
	
	int etk5=0;
	String v_yyyy12="";
	String v_code_bran12="";
	String v_detcode12="";
	String v_code_kind12="";
	if(edutakeets5!=null){
		etk5=edutakeets5.size();
		v_yyyy12=edutakeets5.get("yyyy1").toString();
		v_code_bran12=edutakeets5.get("code_bran1").toString();
		v_code_kind12=edutakeets5.get("code_kind1").toString();
		v_detcode12=edutakeets5.get("detcode1").toString();		
	}
	
	List<Map> selectexam1 = (List<Map>)request.getAttribute("selectexam1");
	JSONArray j_educationsend3=(JSONArray)request.getAttribute("j_educationsend3"); 
	int j_eds=0;
	if(j_educationsend3!=null){
		j_eds=j_educationsend3.size();
	}
	
	String pgno = StringUtil.NVL((String)request.getAttribute("pgno"));
%>

<script type="text/javascript">

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
		  url: 'locall',
		  datatype: "json",
	      mtype: 'post',      
	      //width:'1100',
	       height:'370',
	      colNames: [ 'Key','교육명','이름','발급번호','면허번호','생년월일','핸드폰','이메일','출석일수','이수시간','점수','교육년도','교육주최','교육종류','자격증구분'
	                  ,'대상자','결제여부','근무처명','진행상태'
// 	                  ,'발급번호'
	                  ,'순번','지부순번','접수번호','등록일시','등록자','수정일시','수정자','주소','회원코드'],
	      colModel: [
	   			{name:'detcode',       	  index:'detcode', 			hidden:true},		//교육및시험코드값
				{name:'edutest_name',	  index:'edutest_name', 	width: 300,editable: false, align: 'left'},		//교육명
	   			{name:'oper_name',    	  index:'oper_name',    	width: 80,editable: false, align: 'center'},	//이름
	   			{name:'result_no',        index:'result_no',     	width: 80, editable: false, align: 'center'},		//발급번호
	   			{name:'oper_lic_no',      index:'oper_lic_no',  	width: 70, editable: false, align: 'center'},	//면허번호
// 	   			{name:'oper_no',    	  index:'oper_no',   		width: 110,	editable: false, align: 'center'},	//주민번호
	   			{name:'oper_birth',    	  index:'oper_birth',   		width: 80,	editable: false, align: 'center'},	//생년월일
	   			{name:'oper_hp',     	  index:'oper_hp',      	width: 100, editable: false, align: 'center'},	//핸드폰
	   			{name:'operemail',        index:'operemail',   		width: 100, editable: false, align: 'center'},	//이메일	
	   			{name:'attend_cnt',       index:'attend_cnt',   	width: 70, editable: false, align: 'center'},	//출석일수
	   			{name:'time_cnt',      	  index:'time_cnt',     	width: 60, editable: false, align: 'center'},	//이수시간
	   			{name:'result_point',     index:'result_point',   	width: 60, editable: false, align: 'center'},	//점수
	   			{name:'yyyy',      		  index:'yyyy',       		hidden:true},		//교육년도
	   			
	   			{name:'code_bran',        index:'code_bran',       	hidden:true},		//교육주최(지부key)
	   			{name:'code_kind',        index:'code_kind',       	hidden:true},		//교육종류(key)
	   			{name:'code_certifi',     index:'code_certifi',     hidden:true},		//구분(key)
	   			{name:'code_operation',   index:'code_operation',   hidden:true},		//대상자
	   			{name:'oper_state',       index:'oper_state',       hidden:true},		//결제여부
	   			{name:'oper_comp_name1',  index:'oper_comp_name1',  hidden:true, formatter:decode11},		//근무처명
	   			{name:'result_state',     index:'result_state',     hidden:true},		//진행상태(코드값)
// 	   			{name:'result_no',        index:'result_no',     	hidden:true},		//발급번호
	   			
	   			{name:'code_seq',         index:'code_seq',       	hidden:true},		//순번(key)
	   			{name:'bran_seq',         index:'bran_seq',       	hidden:true},		//지부순번(key)
	   			{name:'receipt_no',       index:'receipt_no',       hidden:true},		//접수번호(key)
	   			
	   			{name:'point_manage',     index:'point_manage',     hidden:true},		//점수관리자격증
	   			{name:'register',         index:'register',       	hidden:true},		//등록자
	   			{name:'up_date',          index:'up_date',       	hidden:true},		//수정일시
	   			{name:'updater',          index:'updater',       	hidden:true},		//수정자
	   			{name:'pers_addr1',       index:'pers_addr1',       hidden:true},		//주소
	   			{name:'code_pers',        index:'code_pers',       	hidden:true}
	   			    ],
	   		    rowNum:15,
	   		    /* sortname: 'oper_lic_no', */
	   			pager: '#pager2',
	            rownumbers: true,
	            viewrecords: true,
	 			multiselect: true,
	 			gridview: true,
	 			caption: '응시결과처리',
				altclass:'myAltRowClass'
	});

	$("#list").click(function(e) {
	    var el = e.target;
	    if(el.nodeName == "TD"){
	    	if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
			{						
				var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
				//var rowdata=jQuery("#list").jqGrid('getGridParam','selarrrow');						
				$("#list").jqGrid('resetSelection');
				$("#list").jqGrid('setSelection',id);
				goSelect();
			}
	    }
	});
	jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
	jQuery("#list").setGridWidth(1100,false);
	powerinit();
	
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
	var meprogid = "203";
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



function init(){
	var val2 = '<%=v_code_kind11%>';
	var val3 = '<%=v_yyyy11%>';
	var val4 = '<%=v_code_bran11%>';
	var val8 = '<%=v_detcode11%>';
	
	var val11="<%=v_code_certifi11%>";
	var val12="<%=v_oper_name11%>";
	var val13="<%=v_oper_lic_no11%>";
	var val14="<%=v_oper_hp11%>";
	var val15="<%=v_oper_email11%>";
	var val16="<%=v_code_operation11%>";
	var val17="<%=v_oper_state11%>";
	var val18="<%=v_oper_comp_name11%>";
	var val19="<%=v_result_point11%>";
	var val20="<%=v_attend_cnt11%>";
	var val21="<%=v_time_cnt11%>";
	var val22="<%=v_receipt_no11%>";
	var val23="<%=v_result_state11%>";
	
	var va32 = '<%=v_code_kind12%>';
	var va33 = '<%=v_yyyy12%>';
	var va34 = '<%=v_code_bran12%>';
	var va38 = '<%=v_detcode12%>';
	
	if('<%=etk2%>'>0){
		alert("저장됐습니다.");
		var jc=eval(<%=j_educationsend3%>);

	 	var e_name=document.getElementById("edutest_name1");
		var opts=e_name.options;
		
		while(opts.length>0){
			opts[opts.length-1]=null;
		}
		setName1("선택 - <%=j_eds%>건","");
		var idx=opts.length;
		for(var i=0;i<jc.length;i++){
			e_name[idx++]=new Option(jc[i].edutest_name,jc[i].detcode);
		}
		 if(e_name.length==0){
			e_name[idx]=new Option("",null);
		}

		sForm.code_kind1.value 		= val2;
		sForm.yyyy1.value 			= val3;
		sForm.code_bran1.value 		= val4;
		sForm.edutest_name1.value   = val8;
		sForm.code_certifi1.value	= val11;
		<%if(!"".equals(pgno)){%>
		goSearch(sForm,<%=pgno%>,"N");
		<%}else{%>
		goSearch(sForm,0,"N");
		<%}%>
		
		sForm3.oper_name1.value		= val12;
		sForm3.oper_lic_no1.value	= val13;
		sForm3.oper_hp1.value		= val14;
		sForm3.code_operation1.value= val16;
		sForm3.oper_state1.value	= val17;
		sForm3.oper_comp_name11.value= val18;
		sForm3.result_point1.value	= val19;
		sForm3.attend_cnt1.value	= val20;
		sForm3.time_cnt1.value		= val21;
		sForm3.oper_email1.value	= val15;
		sForm3.result_state1.value  =val23;
		
	} else if('<%=batchSaved%>'=="Y"){
		var jc=eval(<%=j_educationsend3%>);

	 	var e_name=document.getElementById("edutest_name1");
		var opts=e_name.options;
		
		while(opts.length>0){
			opts[opts.length-1]=null;
		}
		setName1("선택 - <%=j_eds%>건","");
		var idx=opts.length;
		for(var i=0;i<jc.length;i++){
			e_name[idx++]=new Option(jc[i].edutest_name,jc[i].detcode);
		}
		 if(e_name.length==0){
			e_name[idx]=new Option("",null);
		}

		sForm.edutest_name1.value   = val8;
		sForm.code_certifi1.value	= val11;
		if("<%=errMsg%>"==""){
			alert("일괄처리 되었습니다. " + "<%=Msg%>");
		} else {
			alert("<%=errMsg%>");
		}
		goSearch(sForm,0,"N");
	}else if('<%=etk5%>'>0){
		alert("발급번호가 생성됐습니다.");
		var jc=eval(<%=j_educationsend3%>);

	 	var e_name=document.getElementById("edutest_name1");
		var opts=e_name.options;
		
		while(opts.length>0){
			opts[opts.length-1]=null;
		}
		setName1("선택 - <%=j_eds%>건","");
		var idx=opts.length;
		for(var i=0;i<jc.length;i++){
			e_name[idx++]=new Option(jc[i].edutest_name,jc[i].detcode);
		}
		 if(e_name.length==0){
			e_name[idx]=new Option("",null);
		}

		sForm.code_kind1.value 		= va32;
		sForm.yyyy1.value 			= va33;
		sForm.code_bran1.value 		= va34;
		sForm.edutest_name1.value   = va38;		
		goSearch(sForm,0,"N");
	}else{
		if('<%=j_eds%>'>0){
			var jc=eval(<%=j_educationsend3%>);

		 	var e_name=document.getElementById("edutest_name1");
			var opts=e_name.options;
			
			while(opts.length>0){
				opts[opts.length-1]=null;
			}
			setName1("선택 - <%=j_eds%>건","");
			var idx=opts.length;
			for(var i=0;i<jc.length;i++){
				e_name[idx++]=new Option(jc[i].edutest_name,jc[i].detcode);
			}
			 if(e_name.length==0){
				e_name[idx]=new Option("",null);
			}	
		}		
	}
}

	/* 검색버튼 클릭시 그리드로 올리는 value 값 */
function goSearch(form,intPage,cer){
	
	var yyyy1				= sForm.yyyy1.value;											//년도
	var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
	var code_kind1   		= sForm.code_kind1.value;										//교육종류
	//setName1(list.edutest_name,list.detcode);
	var edutest_name1 		= sForm.edutest_name1.value;									//내용
	var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
	var oper_name1   		= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
	var oper_lic_no1 		= sForm3.oper_lic_no1.value;									//면허번호
	var oper_hp1 			= sForm3.oper_hp1.value;										//핸드폰
	var oper_email1 		= sForm3.oper_email1.value;										//이메일
	var code_operation1		= sForm3.code_operation1.value;									//시행대상
	var oper_state1 		= sForm3.oper_state1.value;										//결제여부
	var oper_comp_name11    = escape(encodeURIComponent(sForm3.oper_comp_name11.value));	//근무처
	var result_point1 		= sForm3.result_point1.value;									//점수
	var attend_cnt1 		= sForm3.attend_cnt1.value;										//출석일수
	var time_cnt1 			= sForm3.time_cnt1.value;										//이수시간
	var result_no1 			= sForm3.result_no1.value;										//발급번호 
	var result_state1 		= sForm3.result_state1.value;									//상태
	//var resultstate1 		= sForm3.resultstate1.value;									//상태(코드값)
	var cert = cer;  
	
	if(edutest_name1==""){
		alert("교육명을 선택해 주십시오.");
		return;
	}
	
	if(cert=="Y") sForm.cer1.value="Y";
	else sForm.cer1.value="";
	
	if(code_operation1=="0") code_operation1 = "";
	
	$('#list').jqGrid('clearGridData');// 검색 할 목록
	jQuery("#list").setGridParam({page:(intPage>0?intPage:1),url:"education.do?method=examSendList&yyyy1="+yyyy1+"&code_bran1="+code_bran1
		+"&code_kind1="+code_kind1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
		+"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1
		+"&oper_comp_name11="+oper_comp_name11+"&result_point1="+result_point1+"&attend_cnt1="+attend_cnt1+"&time_cnt1="+time_cnt1+"&result_no1="+result_no1
		+"&result_state1="+result_state1+"&detcode1="+edutest_name1+"&cert="+cert}).trigger("reloadGrid");

	rowSelected = false;
}	 

function setName(){
	
	var yyyy=document.getElementById("yyyy1").value;				//교육년도 값
	var code_bran=document.getElementById("code_bran1").value;		//교육주최 값
	var code_kind=document.getElementById("code_kind1").value;		//교육종류 값
//	alert("yyyy=>"+yyyy+" "+"code_bran=>"+code_bran+" "+"code_kind=>"+code_kind);
	if ( yyyy == "" || code_bran == "" || code_kind == ""  ){		//교육년도와 교육주최 교육종류
		setName1("","");
		return;
	}
	/*action send 로 보내는 폼  */
	document.sForm.yyyy1.value = yyyy;
	document.sForm.action="education.do?method=examSend&yyyy1="+yyyy+"&code_bran1="+code_bran+"&code_kind1="+code_kind;
	document.sForm.submit();
		
}	

function setName1(name,value){
	var edutest_name1=document.getElementById("edutest_name1");
	var opts=edutest_name1.options;
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
//	alert("name = "+name+"  value="+value);
	edutest_name1[0]=new Option(name,value);
}

var rowSelected = false;
  function goSelect(rowid,iCol){
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
				
		//그리드에서 select시에 테이블로 출력될 value값들  
		//document.sForm.yyyy1.value     				= list.yyyy;				//교육년도
//		alert("교육주최===>"+list.code_bran);
		//document.sForm.code_bran1.value     		= list.code_bran;			//교육주최(key)
//		alert("교육종류===>"+list.code_kind);
		//document.sForm.code_kind1.value     		= list.code_kind;			//교육종류(key)
	//	setName1(list.edutest_name,list.edutest_name);	
		//setName1(list.edutest_name,list.detcode);
//		setName1(list.edutestname,list.edutestname);							//내용
		//document.sForm.code_certifi1.value     		= list.code_certifi;		//자격증구분(key)
		
		document.sForm3.oper_name1.value   			= list.oper_name;			//이름
 		document.sForm3.oper_lic_no1.value  		= list.oper_lic_no;			//면허번호
		document.sForm3.oper_hp1.value    			= list.oper_hp; 			//핸드폰
		document.sForm3.code_operation1.value    	= list.code_operation;		//대상자
		document.sForm3.oper_state1.value    		= list.oper_state;			//결제여부
		document.sForm3.oper_comp_name11.value   	= list.oper_comp_name1;		//근무처
		document.sForm.pers_addr1.value   			= list.pers_addr1;		//주소
		document.sForm3.result_point1.value    		= list.result_point;		//점수
		document.sForm3.attend_cnt1.value    		= list.attend_cnt;			//출석일수
		document.sForm3.time_cnt1.value    			= list.time_cnt;			//이수시간
		document.sForm3.result_no1.value    		= list.result_no;			//발급번호
		document.sForm3.result_state1.value    		= list.result_state; 		//진행상태
		document.sForm3.oper_email1.value    		= list.operemail; 			//이메일
		
		document.sForm.receipt_no1.value     		= list.receipt_no;			//접수번호(key)
		document.sForm.bran_seq1.value     			= list.bran_seq;			//지부순번(key)
		document.sForm.code_seq1.value    			= list.code_seq;			//순번(key)
		document.sForm.pers_addr1.value  			= list.pers_addr1;			//주소
		document.sForm.code_pers.value              = list.code_pers;
//		alert(document.sForm.pers_addr1.value);
		document.sForm.point_manage1.value = "";
		if(list.point_manage.substr(0,1)=="1"){
			document.sForm.point_manage1.value = "0";
		}else if(list.point_manage.substr(1,1)=="1"){
			document.sForm.point_manage1.value = "1";
		}else if(list.point_manage.substr(2,1)=="1"){
			document.sForm.point_manage1.value = "2";
		}else if(list.point_manage.substr(3,1)=="1"){
			document.sForm.point_manage1.value = "3";
		}else if(list.point_manage.substr(4,1)=="1"){
			document.sForm.point_manage1.value = "4";
		}
		var v_comper = document.sForm3.result_point1.value+document.sForm3.attend_cnt1.value+document.sForm3.time_cnt1.value;
//		alert("v_comper==>"+v_comper.replace(" ","")+"<--");//내용
		if(v_comper.replace(" ","") != ""){
			document.sForm.point_manage1.value    		= "U" + document.sForm.point_manage1.value;		//입력수정여부 + 점수관리 
		}else{
			document.sForm.point_manage1.value    		= "I" + document.sForm.point_manage1.value;		//입력수정여부 + 점수관리
		}
//		alert("document.sForm.point_manage1.value3==>"+document.sForm.point_manage1.value);//내용
		rowSelected = true;
	}
} 	
 
function goClear(){	
	$('#list1').each(function(){
		sForm3.reset();
		//sForm.oper_name1.frocus();
	});	
//	setName1("","");
}

function goSave(){
	 if(sForm.cer1.value=="Y"){
		 alert("수료증 발급 목록에서는 변경하실 수 없습니다.");
		 return;
	 }
	/* search 에서 담아온 항목 이랑 같게 한다 */
	
		var yyyy1				= sForm.yyyy1.value;											//년도
		var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
		var code_kind1   		= sForm.code_kind1.value;										//교육종류
		var edutest_name1 		= sForm.edutest_name1.value;									//내용
		var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
		var oper_name1   		= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
		var oper_lic_no1 		= sForm3.oper_lic_no1.value;									//면허번호
		var oper_hp1 			= sForm3.oper_hp1.value;										//핸드폰
		var oper_email1 		= sForm3.oper_email1.value;										//이메일
		var code_operation1		= sForm3.code_operation1.value;									//시행대상
		var oper_state1 		= sForm3.oper_state1.value;										//결제여부
		var oper_comp_name11    = escape(encodeURIComponent(sForm3.oper_comp_name11.value));	//근무처
		var result_point1 		= sForm3.result_point1.value;									//점수
		var attend_cnt1 		= sForm3.attend_cnt1.value;										//출석일수
		var time_cnt1 			= sForm3.time_cnt1.value;										//이수시간
		var result_no1 			= sForm3.result_no1.value;										//발급번호 
		var result_state1 		= sForm3.result_state1.value;									//상태
		var receipt_no1			= sForm.receipt_no1.value;										//접수번호
		var point_manage1		= sForm.point_manage1.value;									//점수관리자격증
		var bran_seq1			= sForm.bran_seq1.value;										//지부순번(key)
		var code_seq1			= sForm.code_seq1.value;										//순번(key)
		var pers_addr1   		= escape(encodeURIComponent(sForm.pers_addr1.value));			//주소
		var code_pers           = sForm.code_pers.value;
//		alert("pers_addr1==>"+pers_addr1+"code_kind1==>"+code_kind1);
		 if ( pers_addr1 == "" && code_kind1 == '4' ){		
				/* 위생교육-주소가 없는 응시자는 처리할 수 없다 20121124 경명신과장 */
				alert("위생교육에선 주소가 없는 응시자는 결과를 등록할 수 없습니다.");
				return;
			}
		if(result_point1.trim()+attend_cnt1.trim()+time_cnt1.trim() == ""){
			alert("점수, 출석일수, 이수시간이 모두 공백인 상태에서는 등록할 수 없습니다.");
			sForm3.result_point1.value = "";
			sForm3.attend_cnt1.value = "";
			sForm3.time_cnt1.value = "";
		    return;
		}

		/*action  edutakeSave 로 보내는 method 항목일치*/
		/* 10-23* 
		1.작업중 search 부분에서 가져운 값이랑 메칭 시킬것 엑션으로 넘길 값도 메칭 시킬걸 
		2.엑션에 메서드 만들기
		3.엑션에서 신규저장과 업데이트 분리 한다
		4.다오에서 xml로 커리 처리 
		5.중간에 xml연결 config,tiles */
		
		if ( yyyy1 != "" || code_bran1 != "" || code_kind1 != ""){	
		document.sForm.action = "education.do?method=examResultSave&yyyy1="+yyyy1+"&code_bran1="+code_bran1
			+"&code_kind1="+code_kind1+"&detcode1="+edutest_name1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
			+"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1
			+"&oper_comp_name11="+oper_comp_name11+"&result_point1="+result_point1+"&attend_cnt1="+attend_cnt1+"&time_cnt1="+time_cnt1+"&result_no1="+result_no1
			+"&result_state1="+result_state1+"&receipt_no1="+receipt_no1+"&point_manage1="+point_manage1+"&bran_seq1="+bran_seq1+"&code_seq1="+code_seq1+"&code_pers="+code_pers
	        +"&s_pgno="+$('#list').getGridParam('page');
	    	document.sForm.submit();   
	}else{
		return;
	}
}

function goSaveBatch(){
	 if(sForm.cer1.value=="Y"){
		 alert("수료증 발급 목록에서는 일괄처리 하실 수 없습니다.");
		 return;
	 }
	 
		var temp = jQuery("#list").jqGrid('getRowData');
		if(temp.length==0){
			alert("조회된 데이터가 없어 일괄처리를 할 수 없습니다.");
			return;
		}
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

// 	 	if( rownum.length==0)	{
// 	 		alert("일괄처리할 회원을 선택해 주십시요.");
// 	 		return;	
// 	 	}
			
	 	if( rownum.length==0 && rowSelected)	{
			 alert("전체 일괄처리를 하시려면 검색을 다시 실행해 주세요.");
	 		return;	
	 	}
			
	 	var rowdata=new Array();
	 	var param="";
	 	var oper_infos="";

	 	for(var i=0;i<rownum.length;i++){
				
			rowdata= 	$("#list").getRowData(rownum[i]);
			
			 if ( rowdata.pers_addr == "" && rowdata.code_kind == '4' ){		
				/* 위생교육-주소가 없는 응시자는 처리할 수 없다 20121124 경명신과장 */
				alert("위생교육에선 주소가 없는 응시자는 결과를 등록할 수 없습니다.\n\n이름: "+rowdata.oper_name+" , 면허번호: "+rowdata.oper_lic_no+" , 생년월일: "+rowdata.oper_birth);
				return;
			}
			 
			var point_manage = "";
			if(rowdata.point_manage.substr(0,1)=="1"){
				point_manage = "0";
			}else if(rowdata.point_manage.substr(1,1)=="1"){
				point_manage = "1";
			}else if(rowdata.point_manage.substr(2,1)=="1"){
				point_manage = "2";
			}else if(rowdata.point_manage.substr(3,1)=="1"){
				point_manage = "3";
			}else if(rowdata.point_manage.substr(4,1)=="1"){
				point_manage = "4";
			}
			var v_comper = rowdata.result_point+rowdata.attend_cnt+rowdata.time_cnt;
			if(v_comper.replace(" ","") != ""){
				point_manage    		= "U" + point_manage;		//입력수정여부 + 점수관리 
			}else{
				point_manage    		= "I" + point_manage;		//입력수정여부 + 점수관리
			}
			
			if(oper_infos.length > 0) {
				oper_infos	+= "__";
			}
			oper_infos	+= rowdata.code_kind+"_";
			oper_infos	+= rowdata.code_certifi+"_";
			oper_infos	+= rowdata.code_seq+"_";
			oper_infos	+= rowdata.code_bran+"_";
			oper_infos	+= rowdata.bran_seq+"_";
			oper_infos	+= rowdata.receipt_no+"_";
			oper_infos	+= (rowdata.code_pers ? rowdata.code_pers : "x")+"_";
			oper_infos	+= point_manage;
		}

		var yyyy1				= sForm.yyyy1.value;											//년도
		var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
		var code_kind1   		= sForm.code_kind1.value;										//교육종류
		var edutest_name1 		= sForm.edutest_name1.value;									//내용
		var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
		
		if( rownum.length==0){	
			var oper_name1   		= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
			var oper_lic_no1 		= sForm3.oper_lic_no1.value;									//면허번호
			var oper_hp1 			= sForm3.oper_hp1.value;										//핸드폰
			var oper_email1 		= sForm3.oper_email1.value;										//이메일
			var code_operation1		= sForm3.code_operation1.value;									//시행대상
			var oper_state1 		= sForm3.oper_state1.value;										//결제여부
			var oper_comp_name11    = escape(encodeURIComponent(sForm3.oper_comp_name11.value));	//근무처
			var result_point1 		= sForm3.result_point1.value;									//점수
			var attend_cnt1 		= sForm3.attend_cnt1.value;										//출석일수
			var time_cnt1 			= sForm3.time_cnt1.value;										//이수시간
			var result_no1 			= sForm3.result_no1.value;										//발급번호
			var result_state1 		= sForm3.result_state1.value;									//상태
			var cert				= sForm.cer1.value;
			if(code_operation1=="0") code_operation1 = "";

			if(oper_name1 != "")	param+="&oper_name1="+oper_name1;
			if(oper_lic_no1 != "")	param+="&oper_lic_no1="+oper_lic_no1;
			if(oper_hp1 != "")	param+="&oper_hp1="+oper_hp1;
			if(oper_email1 != "")	param+="&oper_email1="+oper_email1;
			if(code_operation1 != "")	param+="&code_operation1="+code_operation1;
			if(oper_state1 != "")	param+="&oper_state1="+oper_state1;
			if(oper_comp_name11 != "")	param+="&oper_comp_name11="+oper_comp_name11;
			if(result_point1 != "")	param+="&result_point1="+result_point1;
			if(attend_cnt1 != "")	param+="&attend_cnt1="+attend_cnt1;
			if(time_cnt1 != "")	param+="&time_cnt1="+time_cnt1;
			if(result_no1 != "")	param+="&result_no1="+result_no1;
			if(result_state1 != "")	param+="&result_state1="+result_state1;
			if(cert != "")	param+="&cert="+cert;
		}

		param+="&oper_infos="+oper_infos;
		
		if ( yyyy1 != "" || code_bran1 != "" || code_kind1 != ""){	
// 		document.sForm.action = "education.do?method=examResultSave&yyyy1="+yyyy1+"&code_bran1="+code_bran1
// 			+"&code_kind1="+code_kind1+"&detcode1="+edutest_name1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
// 			+"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1
// 			+"&oper_comp_name11="+oper_comp_name11+"&result_point1="+result_point1+"&attend_cnt1="+attend_cnt1+"&time_cnt1="+time_cnt1+"&result_no1="+result_no1
// 			+"&result_state1="+result_state1+"&receipt_no1="+receipt_no1+"&point_manage1="+point_manage1+"&bran_seq1="+bran_seq1+"&code_seq1="+code_seq1+"&code_pers="+code_pers;
// 	    	document.sForm.submit();   
		document.sForm.action = "education.do?method=examResultSaveBatch&yyyy1="+yyyy1+"&code_bran1="+code_bran1
			+"&code_kind1="+code_kind1+"&detcode1="+edutest_name1+param;
		
			
			if(!confirm((rowSelected?"선택하신 "+(rownum.length)+"건을 일괄처리 하시겠습니까?":"전체 일괄처리 하시겠습니까?"))){return;}
		
	    	document.sForm.submit();   
		}else{
			return;
		}
}

function excelDown(){

// 	var param = "";
	
// 	if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;				//년도
// 	if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;		//지부(교육주최)
// 	if(sForm.code_kind1.value		!= "")	param+="&code_kind1="		+sForm.code_kind1.value;		//교육종류
// 	if(sForm.edutest_name1.value	!= "")	param+="&edutest_name1="	+sForm.edutest_name1.value;		//내용
// 	if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;		//구분코드(자격증구분)
// 	if(sForm.cer1.value	!= "")	param+="&cert=Y";


	var yyyy1				= sForm.yyyy1.value;											//년도
	var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
	var code_kind1   		= sForm.code_kind1.value;										//교육종류
	var edutest_name1 		= sForm.edutest_name1.value;									//내용
	var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
	var oper_name1   		= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
	var oper_lic_no1 		= sForm3.oper_lic_no1.value;									//면허번호
	var oper_hp1 			= sForm3.oper_hp1.value;										//핸드폰
	var oper_email1 		= sForm3.oper_email1.value;										//이메일
	var code_operation1		= sForm3.code_operation1.value;									//시행대상
	var oper_state1 		= sForm3.oper_state1.value;										//결제여부
	var oper_comp_name11    = escape(encodeURIComponent(sForm3.oper_comp_name11.value));	//근무처
	var result_point1 		= sForm3.result_point1.value;									//점수
	var attend_cnt1 		= sForm3.attend_cnt1.value;										//출석일수
	var time_cnt1 			= sForm3.time_cnt1.value;										//이수시간
	var result_no1 			= sForm3.result_no1.value;										//발급번호
	var result_state1 		= sForm3.result_state1.value;									//상태
	var cert				= sForm.cer1.value;
	if(code_operation1=="0") code_operation1 = "";

	var param = "";
	if(yyyy1 != "")	param+="&yyyy1="+yyyy1;
	if(code_bran1 != "")	param+="&code_bran1="+code_bran1;
	if(code_kind1 != "")	param+="&code_kind1="+code_kind1;
	if(edutest_name1 != "")	param+="&edutest_name1="+edutest_name1;
	if(code_certifi1 != "")	param+="&code_certifi1="+code_certifi1;
	if(oper_name1 != "")	param+="&oper_name1="+oper_name1;
	if(oper_lic_no1 != "")	param+="&oper_lic_no1="+oper_lic_no1;
	if(oper_hp1 != "")	param+="&oper_hp1="+oper_hp1;
	if(oper_email1 != "")	param+="&oper_email1="+oper_email1;
	if(code_operation1 != "")	param+="&code_operation1="+code_operation1;
	if(oper_state1 != "")	param+="&oper_state1="+oper_state1;
	if(oper_comp_name11 != "")	param+="&oper_comp_name11="+oper_comp_name11;
	if(result_point1 != "")	param+="&result_point1="+result_point1;
	if(attend_cnt1 != "")	param+="&attend_cnt1="+attend_cnt1;
	if(time_cnt1 != "")	param+="&time_cnt1="+time_cnt1;
	if(result_no1 != "")	param+="&result_no1="+result_no1;
	if(result_state1 != "")	param+="&result_state1="+result_state1;
	if(cert != "")	param+="&cert="+cert;
	param+="&paramfrom=ExamResult";

	
	param+="&register1="+"<%=g_name%>";
	
	//alert(param);
	var url="education.do?method=pers_check2"+param;
	window.open(url,"pers_check2","width=400, height=500, left=480, top=200,scrollbars=yes");	

	
}

 function upExcel(){
	window.open("education.do?method=upExamSendList","lic_no","scrollbar=no,resizable=no,toolbar=no,width=350,height=150,left=20,top=29,status=no");
}

function resultNoCreate(){
	var yyyy1				= sForm.yyyy1.value;											//년도
	var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
	var code_kind1   		= sForm.code_kind1.value;										//교육종류
	var code_certifi1		= sForm.code_certifi1.value;
	var edutest_name1 		= sForm.edutest_name1.value;									//내용
	//alert(code_certifi1);
   	if ( edutest_name1 != ""){	
		if (confirm("전체 이수자(발급번호 미생성)에 대한 수료증 발급번호를 생성하시겠습니까?")) {
			document.sForm.action = "education.do?method=resultNoCreate&yyyy1="+yyyy1+"&code_bran1="+code_bran1
	        +"&code_kind1="+code_kind1+"&detcode1="+edutest_name1;
	        document.sForm.submit();
		}else{
			return;
		}
		   
  	}else{
		alert("교육명을 선택하지 않으면 발급번호생성을 할 수 없습니다.");
		return;
	}

 }
function ban(val){
	if(val.tagName=="INPUT"){
		if(event.keyCode==8){
			alert("수정할 수 없는 항목입니다.");
			event.keyCode=0;
			event.cancelBubble=true;
			event.returnValue=false;
		}
	}
}
function setEduN(val){
	//alert(val);
	var jc=eval(<%=j_educationsend3%>);
	for(var i=0;i<jc.length;i++){
		if(jc[i].detcode==val){
			sForm.certifi_name.value=jc[i].certifi_name;
			sForm.code_certifi1.value=jc[i].code_certifi;
			//alert(jc[i].print_kind);
			sForm.print_kind.value=jc[i].print_kind;
		}
	}
}

function goPrint(){	
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();	
	
	if(sForm.edutest_name1.value ==""){
		alert("교육명을 선택해 주십시오.");
		return;
	}
	
	var parm ="";
	var result_no="";
	
	if(sForm.print_kind.value		!="")parm+="&print_kind="	+ sForm.print_kind.value;  
    if(sForm.code_kind1.value		!="")parm+="&code_kind="	+ sForm.code_kind1.value;  
    if(sForm.edutest_name1.value	!="")parm+="&detcode="		+ sForm.edutest_name1.value;
    

    
// 	var yyyy1				= sForm.yyyy1.value;											//년도
// 	var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
// 	var code_kind1   		= sForm.code_kind1.value;										//교육종류
// 	var edutest_name1 		= sForm.edutest_name1.value;									//내용
// 	var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
// 	var oper_name1   		= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
// 	var oper_lic_no1 		= sForm3.oper_lic_no1.value;									//면허번호
// 	var oper_hp1 			= sForm3.oper_hp1.value;										//핸드폰
// 	var oper_email1 		= sForm3.oper_email1.value;										//이메일
// 	var code_operation1		= sForm3.code_operation1.value;									//시행대상
// 	var oper_state1 		= sForm3.oper_state1.value;										//결제여부
// 	var oper_comp_name11    = escape(encodeURIComponent(sForm3.oper_comp_name11.value));	//근무처
// 	var result_point1 		= sForm3.result_point1.value;									//점수
// 	var attend_cnt1 		= sForm3.attend_cnt1.value;										//출석일수
// 	var time_cnt1 			= sForm3.time_cnt1.value;										//이수시간
// 	var result_no1 			= sForm3.result_no1.value;										//발급번호
// 	var result_state1 		= sForm3.result_state1.value;									//상태
// 	var cert				= sForm.cer1.value;
// 	if(code_operation1=="0") code_operation1 = "";

// 	if(yyyy1 != "")	param+="&yyyy1="+yyyy1;
// 	if(code_bran1 != "")	param+="&code_bran1="+code_bran1;
// 	if(code_kind1 != "")	param+="&code_kind1="+code_kind1;
// 	if(edutest_name1 != "")	param+="&edutest_name1="+edutest_name1;
// 	if(code_certifi1 != "")	param+="&code_certifi1="+code_certifi1;
// 	if(oper_name1 != "")	param+="&oper_name1="+oper_name1;
// 	if(oper_lic_no1 != "")	param+="&oper_lic_no1="+oper_lic_no1;
// 	if(oper_hp1 != "")	param+="&oper_hp1="+oper_hp1;
// 	if(oper_email1 != "")	param+="&oper_email1="+oper_email1;
// 	if(code_operation1 != "")	param+="&code_operation1="+code_operation1;
// 	if(oper_state1 != "")	param+="&oper_state1="+oper_state1;
// 	if(oper_comp_name11 != "")	param+="&oper_comp_name11="+oper_comp_name11;
// 	if(result_point1 != "")	param+="&result_point1="+result_point1;
// 	if(attend_cnt1 != "")	param+="&attend_cnt1="+attend_cnt1;
// 	if(time_cnt1 != "")	param+="&time_cnt1="+time_cnt1;
// 	if(result_no1 != "")	param+="&result_no1="+result_no1;
// 	if(result_state1 != "")	param+="&result_state1="+result_state1;
// 	if(cert != "")	param+="&cert="+cert;

    
    
    
    //alert(parm);
	if( rownum.length==0){			    
		//window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_all.jsp?gubun=E"+parm);		
		window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_all.jsp?gubun=E"+parm);		
	}else{	
	 	for(var i=0;i<rownum.length;i++){
			rowdata= 	$("#list").getRowData(rownum[i]);			
			if(i==0){				
				result_no        =  "'"+escape(encodeURIComponent(rowdata.result_no))+"'";			
			}else{
				result_no +=",";
				result_no += "'"+escape(encodeURIComponent(rowdata.result_no))+"'"; 		
			}
		}	 	 	
	 	parm+="&doc_seq="+result_no;
	 	//alert(parm);
		//window.open("http://wwwlocal.dietitianref.or.kr:70/doc_form/docu_print_each.jsp?gubun=E"+parm);		
		window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_each.jsp?gubun=E"+parm);		
	}
}
function printAll(){
	
	var rating = 'N';
	// 교육명 체크
    var edutest_name = document.getElementById("edutest_name1").value;
    if (edutest_name === "") {
        alert("교육명을 선택해 주십시오.");
        return;
    }
    
    if (confirm("이수자 평점을 표시하시겠습니까?")) {
        rating = 'Y';
    }

    // 파라미터 구성
    var param  = "print_kind=" + sForm.print_kind.value
               + "&code_kind=" + encodeURIComponent(sForm.code_kind1.value)
               + "&detcode="   + encodeURIComponent(edutest_name)
               + "&rating=" + rating ;
	
	var width  = 794;
    var height = 1122;
    var left   = Math.floor((screen.availWidth  - width ) / 2);
    var top    = Math.floor((screen.availHeight - height) / 2);

    var opts = [
      "width=" + width,
      "height=" + height,
      "toolbar=no",
      "location=no",
      "directories=no",
      "status=no",
      "menubar=no",
      "scrollbars=no",
      "resizable=yes",
      "top=" + top,
      "left=" + left
    ];

    var popupOpts = opts.join(",");
    //var url = "http://211.171.32.206:5588/print/certi_user.do?" + param;
    var url = "<%=AntData.DOMAIN%>/print/certi_user.do?" + param;
   
    window.open(url, "certi_user", popupOpts);
	
}
function goPrint_new(){
	
	var rating = 'N';
	// 선택된 행 번호 배열
	var rownums = jQuery("#list").jqGrid('getGridParam','selarrrow');
	if (!rownums || rownums.length === 0) {
	  alert("먼저 한 건 이상 선택해주세요.");
	  return;
	}
	
	if (!rownums || rownums.length === 0) {
		  alert("먼저 한 건 이상 선택해주세요.");
		  return;
		}
    if (confirm("이수자 평점을 표시하시겠습니까?")) {
    	rating = 'Y';
    } 
    
	var resultNos = [];

	for (var i = 0; i < rownums.length; i++) {
	    var rowData = jQuery("#list").jqGrid('getRowData', rownums[i]);

	    if (rowData.result_state !== "10") {
	        alert("[" + (i+1) + "번째 행] 이수 상태가 아니므로 출력할 수 없습니다.");
	        return;
	    }

	    if (!rowData.result_no) {
	        alert("[" + (i+1) + "번째 행] 발급번호가 없습니다.");
	        return;
	    }

	    resultNos.push(rowData.result_no);
	}

	// 교육명 체크
	var edutest_name = document.getElementById("edutest_name1").value;
	if (edutest_name === "") {
	    alert("교육명을 선택해 주십시오.");
	    return;
	}

	// 파라미터 구성
	var param  = "print_kind=" + sForm.print_kind.value
	           + "&code_kind=" + encodeURIComponent(sForm.code_kind1.value)
	           + "&detcode="   + encodeURIComponent(edutest_name)
	           + "&rating=" + rating
	           + "&doc_seq=" + encodeURIComponent(resultNos.join(","));

	// 팝업 설정
	var width  = 794;
	var height = 1122;
	var left   = Math.floor((screen.availWidth  - width ) / 2);
	var top    = Math.floor((screen.availHeight - height) / 2);

	var opts = [
	  "width=" + width,
	  "height=" + height,
	  "toolbar=no",
	  "location=no",
	  "directories=no",
	  "status=no",
	  "menubar=no",
	  "scrollbars=no",
	  "resizable=yes",
	  "top=" + top,
	  "left=" + left
	];

	var popupOpts = opts.join(",");
	//var url = "http://211.171.32.206:5588/print/certi_user.do?" + param;
	var url = "<%=AntData.DOMAIN%>/print/certi_user.do?" + param;
	window.open(url, "certi_user", popupOpts);
    
	
}

//메일 발송
function sendEmail() {
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 메일을 발송 할 수 없습니다.");
		return;
	}
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

// 	if( rownum.length==0)	{
// 		alert("메일을 발송할 회원을 선택해 주십시요.");
// 		return;	
// 	}
		
 	var rowdata=new Array();
 	var param="";
 	var addr_infos="";

 	for(var i=0;i<rownum.length;i++){
			
		rowdata= 	$("#list").getRowData(rownum[i]);
		
		if(rowdata.operemail.length>0){
			if(param.length > 0) param+= ",";
// 			param += rowdata.operemail;
			param += escape(encodeURIComponent(rowdata.oper_name));
			
			if(addr_infos.length > 0) {
				addr_infos	+= "__";
			}
			addr_infos	+= rowdata.code_kind+"_";
			addr_infos	+= rowdata.code_certifi+"_";
			addr_infos	+= rowdata.code_seq+"_";
			addr_infos	+= rowdata.code_bran+"_";
			addr_infos	+= rowdata.bran_seq+"_";
			addr_infos	+= rowdata.receipt_no;
		}
	}

	if( rownum.length==0){	
		param+="ALL";
		
		var yyyy1				= sForm.yyyy1.value;											//년도
		var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
		var code_kind1   		= sForm.code_kind1.value;										//교육종류
		var edutest_name1 		= sForm.edutest_name1.value;									//내용
		var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
		var oper_name1   		= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
		var oper_lic_no1 		= sForm3.oper_lic_no1.value;									//면허번호
		var oper_hp1 			= sForm3.oper_hp1.value;										//핸드폰
		var oper_email1 		= sForm3.oper_email1.value;										//이메일
		var code_operation1		= sForm3.code_operation1.value;									//시행대상
		var oper_state1 		= sForm3.oper_state1.value;										//결제여부
		var oper_comp_name11    = escape(encodeURIComponent(sForm3.oper_comp_name11.value));	//근무처
		var result_point1 		= sForm3.result_point1.value;									//점수
		var attend_cnt1 		= sForm3.attend_cnt1.value;										//출석일수
		var time_cnt1 			= sForm3.time_cnt1.value;										//이수시간
		var result_no1 			= sForm3.result_no1.value;										//발급번호
		var result_state1 		= sForm3.result_state1.value;									//상태
		var cert				= sForm.cer1.value;
		if(code_operation1=="0") code_operation1 = "";

		if(yyyy1 != "")	param+="&yyyy1="+yyyy1;
		if(code_bran1 != "")	param+="&code_bran1="+code_bran1;
		if(code_kind1 != "")	param+="&code_kind1="+code_kind1;
		if(edutest_name1 != "")	param+="&edutest_name1="+edutest_name1;
		if(code_certifi1 != "")	param+="&code_certifi1="+code_certifi1;
		if(oper_name1 != "")	param+="&oper_name1="+oper_name1;
		if(oper_lic_no1 != "")	param+="&oper_lic_no1="+oper_lic_no1;
		if(oper_hp1 != "")	param+="&oper_hp1="+oper_hp1;
		if(oper_email1 != "")	param+="&oper_email1="+oper_email1;
		if(code_operation1 != "")	param+="&code_operation1="+code_operation1;
		if(oper_state1 != "")	param+="&oper_state1="+oper_state1;
		if(oper_comp_name11 != "")	param+="&oper_comp_name11="+oper_comp_name11;
		if(result_point1 != "")	param+="&result_point1="+result_point1;
		if(attend_cnt1 != "")	param+="&attend_cnt1="+attend_cnt1;
		if(time_cnt1 != "")	param+="&time_cnt1="+time_cnt1;
		if(result_no1 != "")	param+="&result_no1="+result_no1;
		if(result_state1 != "")	param+="&result_state1="+result_state1;
		if(cert != "")	param+="&cert="+cert;
	}
	
 	param+="&paramfrom=ExamResult&addr_infos="+addr_infos;
	//alert("메일발송 = "+param);
	
	window.open('memberBasic.do?method=eMail&toAddr='+param,"oper_email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
	
}
/* 문자 발송 조건 수정요망 */
function smssand(form){
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 문자를 발송 할 수 없습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var oper_hp="";
 	var oper_name="";
 	var hp_infos = "";
 	//개별선택
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);

		if(rowdata.oper_hp.length>0) {
			if(oper_hp.length > 0){ oper_hp+= ","; oper_name+=",";}	
			oper_hp += rowdata.oper_hp;
			oper_name += escape(encodeURIComponent(rowdata.oper_name));
			
			if(hp_infos.length > 0) {
				hp_infos	+= "__";
			}
			hp_infos	+= rowdata.code_kind+"_";
			hp_infos	+= rowdata.code_certifi+"_";
			hp_infos	+= rowdata.code_seq+"_";
			hp_infos	+= rowdata.code_bran+"_";
			hp_infos	+= rowdata.bran_seq+"_";
			hp_infos	+= rowdata.receipt_no;
		}
	}

	if( rownum.length==0){	
		//여기다 전체 검색(검색과 같은 루트를 탄다.)
		
//조건 검색에 쓰는 조건값만 기입
		var param = "";
// 		//첫째줄-----------------------------------------------
//  		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="		+sForm.yyyy1.value;					//년도
// 		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="	+sForm.code_bran1.value;			//지부(교육주최)
// 		if(sForm.code_kind1.value		!= "")	param+="&code_kind1="	+sForm.code_kind1.value;			//교육종류
// 		if(sForm.edutest_name1.value	!= "")	param+="&edutest_name1="+sForm.edutest_name1.value;			//내용
// 		if(sForm3.code_operation1.value	!= "")	param+="&code_operation1="+sForm3.code_operation1.value;	//대상자

		var yyyy1				= sForm.yyyy1.value;											//년도
		var code_bran1			= sForm.code_bran1.value;										//지부(교육주최)
		var code_kind1   		= sForm.code_kind1.value;										//교육종류
		var edutest_name1 		= sForm.edutest_name1.value;									//내용
		var code_certifi1 		= sForm.code_certifi1.value;									//구분코드(교육및시험구분)
		var oper_name1   		= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
		var oper_lic_no1 		= sForm3.oper_lic_no1.value;									//면허번호
		var oper_hp1 			= sForm3.oper_hp1.value;										//핸드폰
		var oper_email1 		= sForm3.oper_email1.value;										//이메일
		var code_operation1		= sForm3.code_operation1.value;									//시행대상
		var oper_state1 		= sForm3.oper_state1.value;										//결제여부
		var oper_comp_name11    = escape(encodeURIComponent(sForm3.oper_comp_name11.value));	//근무처
		var result_point1 		= sForm3.result_point1.value;									//점수
		var attend_cnt1 		= sForm3.attend_cnt1.value;										//출석일수
		var time_cnt1 			= sForm3.time_cnt1.value;										//이수시간
		var result_no1 			= sForm3.result_no1.value;										//발급번호
		var result_state1 		= sForm3.result_state1.value;									//상태
		var cert				= sForm.cer1.value;
		if(code_operation1=="0") code_operation1 = "";

		if(yyyy1 != "")	param+="&yyyy1="+yyyy1;
		if(code_bran1 != "")	param+="&code_bran1="+code_bran1;
		if(code_kind1 != "")	param+="&code_kind1="+code_kind1;
		if(edutest_name1 != "")	param+="&edutest_name1="+edutest_name1;
		if(code_certifi1 != "")	param+="&code_certifi1="+code_certifi1;
		if(oper_name1 != "")	param+="&oper_name1="+oper_name1;
		if(oper_lic_no1 != "")	param+="&oper_lic_no1="+oper_lic_no1;
		if(oper_hp1 != "")	param+="&oper_hp1="+oper_hp1;
		if(oper_email1 != "")	param+="&oper_email1="+oper_email1;
		if(code_operation1 != "")	param+="&code_operation1="+code_operation1;
		if(oper_state1 != "")	param+="&oper_state1="+oper_state1;
		if(oper_comp_name11 != "")	param+="&oper_comp_name11="+oper_comp_name11;
		if(result_point1 != "")	param+="&result_point1="+result_point1;
		if(attend_cnt1 != "")	param+="&attend_cnt1="+attend_cnt1;
		if(time_cnt1 != "")	param+="&time_cnt1="+time_cnt1;
		if(result_no1 != "")	param+="&result_no1="+result_no1;
		if(result_state1 != "")	param+="&result_state1="+result_state1;
		if(cert != "")	param+="&cert="+cert;


		param+="&oper_hp=ALL&gubun=result&exam_paramok=Y";  
		
		//엑션
//		alert("문자발송 전체 = "+param);
		var url="education.do?method=smssandAll"+param;
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
		
	}else{
		var param = "&hp_infos="+hp_infos+"&exam_paramok=Y";
//		alert("문자발송 조건 = "+param);
		var url="education.do?method=smssand&gubun=result&oper_hp="+oper_hp+"&oper_name="+oper_name+param;
		window.open(url,"notePad","width=370, height=550, left=280, top=50");	
	}
}

/* 쪽지 발송 조건  */
function notePad(form){	
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 쪽지를 보낼 수 없습니다.");
		return;
	}
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var person_yn1="";
 	var oper_name="";
 	 	
 	
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);

		if(rowdata.code_pers.length>0) {
			if(person_yn1.length > 0) person_yn1+= ",";	//code_pers
			person_yn1 += rowdata.code_pers;
		}
		if(rowdata.oper_name.length>0) {
			if(oper_name.length > 0) oper_name+= ",";	//pers_name
			oper_name += rowdata.oper_name;
		}
	}

	if( rownum.length==0){	
		//여기다 전체 검색(검색과 같은 루트를 탄다.)
		
//조건 검색에 쓰는 조건값만 기입
		var param = "";
		//첫째줄-----------------------------------------------
 		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="		+sForm.yyyy1.value;										//년도
		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="	+sForm.code_bran1.value;								//지부(교육주최)
		if(sForm.code_kind1.value		!= "")	param+="&code_kind1="	+sForm.code_kind1.value;								//교육종류
		if(sForm.edutest_name1.value	!= "")	param+="&edutest_name1="+sForm.edutest_name1.value;	//내용
		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="+sForm.code_certifi1.value;								//구분코드(자격증구분)
		if(sForm.cer1.value	!= "")	param+="&cert=Y";  
		param+="&from=edu1";
		//엑션
//		alert("쪽지발송 전체 = "+param);
		var url="education.do?method=sendnotePadAll"+param;
		window.open(url,"umsData","width=370, height=500, left=280, top=80");	
		
	}else{
//선텍된 항목만 보내기 스트러쳐 등록, 엑션 생성 ,jsp파일 붙이기
		oper_name = escape(encodeURIComponent(oper_name)); 

//		alert("쪽지발송 조건 = "+oper_name+"  person_yn1 = "+person_yn1);
		var url="education.do?method=sendnotePad&code_pers="+person_yn1+"&pers_name="+oper_name;
		window.open(url,"notePad","width=370, height=500, left=280, top=80");	
	}
}
</script>
   
 
 </head>

  <body onload="init();">
	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="/main3.do">교육</a></li>
		<li>&gt;</li>
		<li class="NPage">응시결과처리</li>
	  </ul>	 
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>
	  <div class="eTabmenu_01">
	   <form id="list1" name="sForm" method="post">
					   <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
						<input type="hidden" name="receipt_no1" id="receipt_no1" value=""/> 
						<input type="hidden" name="bran_seq1" id="bran_seq1" value=""/> 
				        <input type="hidden" name="code_seq1" id="code_seq1" value=""/>
				        <input type="hidden" name="resultstate1" id="resultstate1" value=""/>
				        <input type="hidden" name="point_manage1" id="point_manage1" value=""/>
						<input type="hidden" name="pers_addr1" value=""/>
						<input type="hidden" name="code_certifi1" value="">
						<input type="hidden" name="print_kind" value="">
						<input type="hidden" name="code_pers" value="">
						<input type="hidden" name="cer1" value="">
        <table class="etable_01" cellspacing="0" summary="교육">
          <caption>응시결과처리</caption>            			 
             <tr>
               <td class="nobg">※&nbsp;&nbsp;교육년도</td>
               <td class="nobg1">
               <select  id="yyyy1" name="yyyy1"  onchange="javascript:setName();">	
               <option value="">전체</option>
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<certifisearch.size();i++){
                		 if("036".equals(certifisearch.get(i).get("groupcode").toString())){
                			detCode=certifisearch.get(i).get("detcode").toString();
                			detCName=certifisearch.get(i).get("detcodename").toString();
                			if(edutake4!=null&&detCode.equals(edutake4.get("yyyy1").toString())){
                				out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                			}
                		}
                	}
               		 %>  
               	    </select>
                </td>
			   <td class="nobg2">※&nbsp;&nbsp;교육주체</td>
               <td class="nobg1">
               <select  id="code_bran1" name="code_bran1"  onchange="javascript:setName();">
               <option value="">전체</option>	
              		  <%
                	
                	for(int i=0;i<certifisearch.size();i++){
                		 if("034".equals(certifisearch.get(i).get("groupcode").toString())){
                			detCode=certifisearch.get(i).get("detcode").toString();
                			detCName=certifisearch.get(i).get("detcodename").toString();
                			if(edutake4!=null&&detCode.equals(edutake4.get("code_bran1").toString())){
                				out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                			}
                		}
                	}
               		 %>  
               	    </select>
                </td>
			   <td class="nobg2">※&nbsp;&nbsp;교육종류</td>
               <td class="nobg1">
               <select  id="code_kind1" name="code_kind1" onchange="javascript:setName();">
				<option value="">전체</option>	
              		  <%
                	for(int i=0;i<certifisearch.size();i++){
                		 if("016".equals(certifisearch.get(i).get("groupcode").toString())){
                			detCode=certifisearch.get(i).get("detcode").toString();
                			detCName=certifisearch.get(i).get("detcodename").toString();
                			if(!detCode.equals("5")){
                				if(edutake4!=null&&detCode.equals(edutake4.get("code_kind1").toString())){
                    				out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
                    			}else{
                    				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                    			}	
                			}                			
                		}
                	}
               		 %>  
               	    </select>
                </td>
             </tr>           			 
             
             <tr>
               <td class="alt1">교육명</td>
               <td colspan="3" class="alt2">
               <select  name="edutest_name1"  id="edutest_name1" style="width:562px" onchange="javascript:setEduN(this.value)">
               <option value="">선택</option>	
               </select>
               </td>
			   <td class="alt">&nbsp;&nbsp;자격증구분</td>
               <td>
               	<input type="text" name="certifi_name" value="" readonly class="readOnly1" onkeydown="ban(this);"/>               
               </td>
			  </tr>
			 </table>
			 </form>
			 
			 
			  <form id="list2" name="sForm3" method="post" action="">
			 <table class="etable_02" cellspacing="0" summary="응시결과처리">
			 	<tr>
	               <td class="nobg">이름</td>               			   
				   <td class="nobg1">면허번호</td>
				   <td class="nobg1">핸드폰</td>
				   <td class="nobg1">대상자</td>
				   <td class="nobg1">접수상태</td>
				   <td class="nobg1">근무처명</td>
			   
			   </tr>
			 
			  <tr>
			  <td class="alt">
               <input type="text" id="name" size="5" name="oper_name1" />
               </td>
               <td>
               <input type="text" id="name" size="4" name="oper_lic_no1" maxlength="6" onKeyUp="javascript:check_Int('sForm3','oper_lic_no1')"/>
               </td>
               <td>
			   <input type="text" id="name" size="10" name="oper_hp1" maxlength="11" onKeyUp="javascript:check_Int('sForm3','oper_hp1')"/>
			   </td>              
               <td>
			   <select  id="name" name="code_operation1">
              		 <%
                	String detCode3,detCName3=null;
                	for(int i=0;i<certifisearch.size();i++){
                		 if("035".equals(certifisearch.get(i).get("groupcode").toString())){
                			detCode3=certifisearch.get(i).get("detcode").toString();
                			detCName3=certifisearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode3+">"+detCName3+"</option>");
                		}
                	}
                	%> 
               	    </select>
                </td>
                <td>
               <select  id="name" name="oper_state1" >
	                <option value="">전체</option>
	                      <% 
	                	
	                	for(int i=0;i<certifisearch.size();i++){
	                		 if("022".equals(certifisearch.get(i).get("groupcode").toString())){
	                			detCode=certifisearch.get(i).get("detcode").toString();
	                			detCName=certifisearch.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");
	                		 }
	                	}
	               		 %>                    
	                </select>       
                </td>
               <td>
			   <input type="text" id="name" size="35" name="oper_comp_name11" readonly class="readOnly1" onkeydown="ban(this);"/>
			   </td>
			  </tr>
			 
			   
			 <tr>
			 	<td class="nobg">※&nbsp;&nbsp;점수</td>
			 	<td class="nobg1">※&nbsp;&nbsp;출석일수</td>
			 	<td class="nobg1">※&nbsp;&nbsp;이수시간</td>  
			 	<td class="nobg1">발급번호</td>  
			 	<td class="nobg1">응시결과상태</td>  
			 	<td class="nobg1">이메일</td>  
			 </tr>
			 <tr>
				 <td class="alt">
				   <input type="text" id="name" size="5" name="result_point1" onKeyUp="javascript:check_Int('sForm3','result_point1')"/>
				   </td>
				 	<td >
	               <input type="text" id="name" size="5" name="attend_cnt1" onKeyUp="javascript:check_Int('sForm3','attend_cnt1')"/>
	               </td>
	               <td>
	               <input type="text" id="name" size="5" name="time_cnt1" onKeyUp="javascript:check_Int('sForm3','time_cnt1')"/>
	               </td>
	               <td>
	               <input type="text" id="name" size="15" name="result_no1" readonly class="readOnly1" onkeydown="ban(this);"/>
	               </td>
	               <td>
				   <select  id="name" name="result_state1" >
	                <option value="">전체</option>
	                      <% 
	                	String detCode7,detCName7=null;
	                	for(int i=0;i<certifisearch.size();i++){
	                		 if("023".equals(certifisearch.get(i).get("groupcode").toString())){
	                			detCode7=certifisearch.get(i).get("detcode").toString();
	                			detCName7=certifisearch.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode7+">"+detCName7+"</option>");
	                		 }
	                	}
	               		 %>                    
	                </select>
				   </td>
				 	<td>
				   <input type="text" id="oper_email1" size="35" name="oper_email1" readonly class="readOnly1" onkeydown="ban(this);"/>
			  	 	</td>
			 </tr>
			 </table>
			 
			 
			 
			 	
		<ul class="btnIcon_99" >
		  	   <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
			   <li><a href="javascript:goSave();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
			   <li><a href="javascript:goSearch(sForm,0,'N');"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>
			   <li><a href="javascript:goSearch(sForm,0,'Y');"><img src="images/icon_search1.gif" onclick="" alt="수료증 발급자 검색" /></a></li>
		 	 </ul>	
		
		</form>
		<br>
		<br>
<table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post" >
<!--<p class="btnSave">
<!--<a href="javascript:goSaveBatch();"><img src="images/icon_processing.gif" alt="일괄처리" onclick="" /></a>-->
<!-- <span onclick="goSaveBatch();" style="cursor:pointer;">[일괄처리]</span> -->
<!--<a href="javascript:resultNoCreate();"><img src="images/icon_s17.gif" alt="전체발급번호생성" onclick="" /></a>-->
<!--<a href="javascript:upExcel();"><img src="images/icon_s16.gif" onclick="" /></a>-->
<!--<a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" /></a>-->
<!-- <a href="javascript:notePad();"	><img src="images/icon_s2.gif" 	onclick=""/></a> -->
<!-- <a href="javascript:sendEmail();"><img src="images/icon_s3.gif"	onclick=""/></a>-->
<!-- <a href="javascript:smssand();"><img src="images/icon_s4.gif"	onclick=""/></a> -->
<!--<a href="javascript:goPrint_new();"><img src="images/icon_s12.gif" onclick="" /></a></p>-->
<div class="btn-area">
    <button type="button" onclick="javascript:goSaveBatch();">일괄처리</button>
    <button type="button" onclick="javascript:resultNoCreate();">전체발급번호생성</button>
    <button type="button" onclick="javascript:upExcel();">엑셀올리기</button>
    <button type="button" onclick="javascript:excelDown();">엑셀저장</button>
    <button type="button" onclick="javascript:goPrint_new();">출력</button>
    
    <!-- ✅ 전체출력 버튼 추가 -->
    <button type="button" onclick="printAll();">전체출력</button>
</div>


</form>
	  </div><!-- rTabmenu_01 End	 -->				  
	</div><!-- contents End -->	  
 
 </body> 
</html>
<style>
.btn-area {
    text-align: right;        /* ✅ 좌측 정렬 */
    margin-top: 10px;
}

.btn-area button {
    padding: 4px 10px;
    margin-right: 5px;
    font-size: 13px;
    cursor: pointer;
}

</style>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>
