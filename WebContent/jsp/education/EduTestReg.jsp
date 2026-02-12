<%@page import="org.apache.struts.action.Action"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.net.*" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.ant.educationexam.dao.educationDao"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>대한영양사협회</title>
  <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.timepicker.css" />
  <link rel="stylesheet" type="text/css" href="css/import.css" />  
  <link rel="stylesheet" type="text/css" href="css/education.css" />
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

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	<script src="js/jquery.ui.timepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
.readOnly1 {background-color:#f2f2f2;}
</style>  
  
<%

	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();
	
	Map map = new HashMap();
	educationDao dao = new educationDao();
	
	String g_id= session.getAttribute("G_ID").toString(); 
	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
	
	String v_code_certifi1=request.getParameter("code_certifi1");
//	out.println(v_code_certifi1!=null);
	String v_code_kind1=request.getParameter("code_kind1");

	List<Map> certifisearch1 = dao.certifisearch1(map);
	List<Map> certifisearch = dao.certifisearch(map);
	// 동일조건에 맞는 값을  edutake1 에 넣어준다
	
	Map 	  edutake1	= (Map)request.getAttribute("edutake1");
	Map 	  edutake2	= (Map)request.getAttribute("edutake2");
	Map       res       = (Map)request.getAttribute("res");
	int etk2=0;
		
	String v_code_seq1="";
	String v_detcode1="";
	
	String v_outside_yn1="";
	String v_edu_t_name="";
// 	String v_finish_date1="";
// 	String v_finish_time1="";
// 	String v_finish_point1="";
// 	String v_edu_marks1="";
	String v_print_kind1="";
	String v_conform1="";
	String v_point_manage1="";
	
	String v_yyyy1="";
	String v_code_bran1="";
	String v_bran_txt1="";
	String v_season1="";
	String v_operation_cnt1="";
	String v_operation1="";
	String v_oper_start_dt1="";
	String v_oper_end_dt1="";
	String v_start_dt1="";
	String v_end_dt1="";
	String v_account_end_dt1="";
	String v_account_way1="";
	String v_receipt_pers_cnt1="";	
	String v_oper_start_tm1="";
	String v_oper_end_tm1="";
	String v_use_yn1="";
	String v_operation_place1="";
	String v_remark1="";
	
	String v_finish_date1="";
	String v_finish_time1="";
	String v_finish_point1="";
	String v_edu_marks1="";
	String v_new_cost1="";
	String v_new_cost_nomem1="";
	String v_re_cost1="";
	String v_mr_cost1="";
	String v_bank_name1="";
	String v_bank_acc1="";
	String v_bank_acc_owner1="";
	String v_service_marks1="";
	String v_code_target1="";
	String v_bran_cardjoin_tp1="";
	
	
	if(edutake2!=null){
		etk2=edutake2.size();

		v_code_seq1			=edutake2.get("code_seq1").toString();
		v_detcode1			=edutake2.get("detcode1").toString();
		
		v_outside_yn1		=edutake2.get("outside_yn1").toString();
		v_edu_t_name		=edutake2.get("edu_t_name").toString();
// 		v_finish_date1		=edutake2.get("finish_date1").toString();
// 		v_finish_time1		=edutake2.get("finish_time1").toString();
// 		v_finish_point1		=edutake2.get("finish_point1").toString();
// 		v_edu_marks1		=edutake2.get("edu_marks1").toString();
		v_print_kind1		=edutake2.get("print_kind1").toString();
		v_conform1			=edutake2.get("conform1").toString();
		v_point_manage1		=edutake2.get("point_manage1").toString();
		
		v_yyyy1				=edutake2.get("yyyy1").toString();
		v_code_bran1		=edutake2.get("code_bran1").toString();
		v_bran_txt1			=edutake2.get("bran_txt1").toString();
		v_season1			=edutake2.get("season1").toString();
		v_operation_cnt1	=edutake2.get("operation_cnt1").toString();	
		v_operation1		=edutake2.get("operation1").toString();
		v_oper_start_dt1	=edutake2.get("oper_start_dt1").toString();	
		v_oper_end_dt1		=edutake2.get("oper_end_dt1").toString();	
		v_start_dt1			=edutake2.get("start_dt1").toString();
		v_end_dt1			=edutake2.get("end_dt1").toString();
		v_account_end_dt1	=edutake2.get("account_end_dt1").toString();
		v_account_way1		=edutake2.get("account_way1").toString();
		v_receipt_pers_cnt1	=edutake2.get("receipt_pers_cnt1").toString();	
		v_oper_start_tm1	=edutake2.get("oper_start_tm1").toString();
		v_oper_end_tm1		=edutake2.get("oper_end_tm1").toString();
		v_use_yn1			=edutake2.get("use_yn1").toString();
		v_operation_place1	=edutake2.get("operation_place1").toString();
		v_remark1			=edutake2.get("remark1").toString();
		
		v_finish_date1		=edutake2.get("finish_date1").toString();
		v_finish_time1		=edutake2.get("finish_time1").toString();
		v_finish_point1		=edutake2.get("finish_point1").toString();
		v_edu_marks1		=edutake2.get("edu_marks1").toString();
		v_new_cost1=edutake2.get("new_cost1").toString();
		v_new_cost_nomem1=edutake2.get("new_cost_nomem1").toString();
		v_re_cost1=edutake2.get("re_cost1").toString();
		v_mr_cost1=edutake2.get("mr_cost1").toString();
		v_bank_name1=edutake2.get("bank_name1").toString();
		v_bank_acc1=edutake2.get("bank_acc1").toString();
		v_bank_acc_owner1=edutake2.get("bank_acc_owner1").toString();
		v_service_marks1=edutake2.get("service_marks1").toString();
		v_code_target1=edutake2.get("code_target1").toString();
		v_bran_cardjoin_tp1=edutake2.get("bran_cardjoin_tp1").toString();
	} if(edutake1!=null){
		v_detcode1			=(String)edutake1.get("detcode1"); if(v_detcode1==null)v_detcode1="";
	}
	
	List<Map> educationsend2 = (List<Map>)request.getAttribute("educationsend2");  
	JSONArray j_educationsend1=(JSONArray)request.getAttribute("j_educationsend1");
	int j_eds=0;
	if(j_educationsend1!=null){
		j_eds=j_educationsend1.size();	
	}
	
	String errMsg="";
	if(request.getAttribute("errMsg")!=null){
		errMsg=URLDecoder.decode(request.getAttribute("errMsg").toString(),"UTF-8"); 
	}
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
     // width:'1100',
      height:'260',
      colNames: [ '교육년도','교육종류','교육주최','교육명','시작일','종료일','접수마감일','이수일수','이수점수','비고','구분코드','이수시간','외부교육여부'
                  ,'평점','발급증종류','평점관리','교육구분','횟수','시행대상','접수시작일','결제마감일','결제방법'
                  ,'접수인원','시행장소','순번','지부순번','교육주최(한)','교육및시험종류','제목첨부','교육및시험코드','사용여부','시행시간1','시행시간2'
                  ,'회원 신규비용','비회원 신규비용','재시험비용','석사이상비용','은행명','계좌번호','예금주','봉사점수','교육대상','지부가맹유형'
                  ],
      colModel: [
			{name:'yyyy',  				index:'yyyy', 			width: 60,editable: false, align: 'center'},		//교육년도
   			{name:'code_kind',    		index:'code_kind',   	width: 160,editable: false, align: 'center'},			//교육종류(key)
   			{name:'code_bran',      	index:'code_bran',      width: 160,editable: false, align: 'center'},			//교육주최(key)
 			{name:'edutestname',   		index:'edutestname',    width: 400,editable: false, align: 'left'},			//내용
   			{name:'oper_start_dt',  	index:'oper_start_dt',  width: 70,editable: false, align: 'center'},			//시작일
   			{name:'oper_end_dt',    	index:'oper_end_dt',    width: 70,editable: false, align: 'center'},			//종료일
   			{name:'end_dt',       		index:'end_dt',      	width: 70,editable: false, align: 'center'},			//접수마감일
   			{name:'finish_date',    	index:'finish_date',    width: 70,editable: false, align: 'center'},			//출석일(이수일수)
   			{name:'finish_point',   	index:'finish_point',   width: 50,editable: false, align: 'center'},			//시험점수(교육평점)
   			{name:'remark',       		index:'remark',      	width: 200,editable: false, align: 'left', formatter:decode11},			//비고
   			
   			{name:'code_certifi',   	index:'code_certifi',		hidden:true},		//구분코드(key) 
   			{name:'finish_time',    	index:'finish_time',    	hidden:true},		//이수시간
   			{name:'outside_yn',     	index:'outside_yn',     	hidden:true},		//외부교육여부   			 
   			{name:'edu_marks',      	index:'edu_marks',      	hidden:true},		//평점   			
   			{name:'print_kind',     	index:'print_kind',			hidden:true},		//발급증종류()
   			{name:'point_manage',   	index:'point_manage',   	hidden:true},		//평점관리구분
   			{name:'season',      		index:'season',         	hidden:true},		//교육구분
   			{name:'operation_cnt',  	index:'operation_cnt',  	hidden:true},		//횟수
   			{name:'operation',      	index:'operation',      	hidden:true},		//시행대상
   			{name:'start_dt',      		index:'start_dt',       	hidden:true},		//접수시작일
   			{name:'account_end_dt', 	index:'account_end_dt', 	hidden:true},		//결제마감일
   			{name:'account_way',    	index:'account_way',    	hidden:true},		//결제방법
   			{name:'receipt_pers_cnt',   index:'receipt_pers_cnt',   hidden:true},		//접수인원
   			{name:'operation_place',    index:'operation_place',    hidden:true,  formatter:decode11},		//시행장소
   			{name:'code_seq',      		index:'code_seq',       	hidden:true},		//순번(key)
   			{name:'bran_seq',      		index:'bran_seq',       	hidden:true},		//지부순번
   			{name:'han_code_bran',      index:'han_code_bran',      hidden:true},		//교육주최
   			{name:'db_code_kind',       index:'db_code_kind', 		hidden:true},		//교육및시험종류값
   			{name:'bran_txt',       	index:'bran_txt', 			hidden:false, formatter:decode11},		//제목추가
   			{name:'detcode',       		index:'detcode', 			hidden:true},		//교육및시험코드값
   			{name:'use_yn',			    index:'use_yn',     		hidden:true},		//시행시간1
   			{name:'oper_start_tm',      index:'oper_start_tm', 		hidden:false},		//시행시간1
   			{name:'oper_end_tm',       	index:'oper_end_tm', 		hidden:false},		//시행시간2
   			
   			{name:'new_cost',       	index:'new_cost', 		hidden:true},		//회원 신규비용
   			{name:'new_cost_nomem',    	index:'new_cost_nomem', hidden:true},		//비회원 신규비용
   			{name:'re_cost',      	 	index:'re_cost', 		hidden:true},		//재시험비용
   			{name:'mr_cost',      	 	index:'mr_cost', 		hidden:true},		//석사이상비용
   			{name:'bank_name',       	index:'bank_name', 		hidden:true},		//은행명
   			{name:'bank_acc',       	index:'bank_acc', 		hidden:true},		//계좌번호
   			{name:'bank_acc_owner',    	index:'bank_acc_owner', hidden:true},		//예금주
   			{name:'service_marks',    	index:'service_marks', hidden:true},		//봉사점수
   			{name:'code_target',    	index:'code_target', hidden:true},		//교육대상
   			{name:'bran_cardjoin_tp',    	index:'bran_cardjoin_tp', hidden:true}		//지부가맹유형
   			],
   		    rowNum:10,
   		   /*  sortname: 'receipt_dt', */
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			//multiselect: true,
 			gridview: true,
 			caption: '교육및시험등록',
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
function goClear(){	
	sForm3.reset();
	sForm4.reset();
	document.sForm.bran_seq1.value="";
	document.sForm.upin.value="in";
	/* $('#list1').each(function(){
		sForm3.reset();		
		alert("지부순번"+ document.sForm.bran_seq1.value);
		alert("접수번호"+document.sForm.receipt_no1.value);
		alert("교육내용"+document.sForm.edutest_name1.value);
 	}); */	
	check_b1(sForm3.b1);
	check_ct();
}

function init(){

	var val1 = '<%=v_code_certifi1%>';
	var val2 = '<%=v_code_kind1%>';	
	var val7 = '<%=v_code_seq1%>';
	var val8 = '<%=v_detcode1%>';
	
	//alert(val1);

	if(<%=etk2%>>0){
		alert("<%=errMsg%>");
		var jc=eval(<%=j_educationsend1%>);

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

		sForm.code_certifi1.value  	= val1;
		sForm.code_kind1.value 		= val2;
		sForm.edutest_name1.value   = val8;
		goSearch(sForm,0);
		
		sForm.upin.value			='up';
		sForm.outside_yn1.value  	='<%=v_outside_yn1%>';
		sForm.edu_t_name.value  	='<%=v_edu_t_name%>';
<%-- 		sForm.finish_date1.value  	='<%=v_finish_date1%>'; --%>
<%-- 		sForm.finish_time1.value  	='<%=v_finish_time1%>'; --%>
<%-- 		sForm.finish_point1.value  	='<%=v_finish_point1%>'; --%>
<%-- 		sForm.edu_marks1.value  	='<%=v_edu_marks1%>'; --%>
		sForm.print_kind1.value  	='<%=v_print_kind1%>';
		sForm.conform1.value  		='<%=v_conform1%>';		 	
		var point_manage1 			='<%=v_point_manage1%>';
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
		
		sForm.t0.checked = false;
		sForm.t1.checked = false;
		sForm.t2.checked = false;
		sForm.t3.checked = false;
		sForm.t5.checked = false;
		sForm.t6.checked = false;
		sForm.t4.checked = false;
		sForm.t7.checked = false;
		sForm.t8.checked = false;
		                         
		if(point_manage1 == "1111111111") {
			sForm.t0.checked = true;
		}else{
			if(t1 == "1") sForm.t1.checked = true;
			if(t2 == "1") sForm.t2.checked = true;
			if(t3 == "1") sForm.t3.checked = true;
			if(t5 == "1") sForm.t5.checked = true;
			if(t6 == "1") sForm.t6.checked = true;
			if(t4 == "1") sForm.t4.checked = true;
			if(t7 == "1") sForm.t7.checked = true;
			if(t8 == "1") sForm.t8.checked = true;
			
		}
		
		sForm3.yyyy1.value   			= '<%=v_yyyy1%>';				
		sForm3.code_bran1.value   		= '<%=v_code_bran1%>';	
		sForm3.bran_txt1.value   		= '<%=v_bran_txt1%>';		
		var  v_season1 			= '<%=v_season1%>';	
		if(v_season1 == '0') {
			document.getElementById("s0").value = '0';
		}else if(v_season1 == '1'){
			//document.getElementById("s1").checked = true;
			document.getElementById("s0").value = '1';
		}else if(v_season1 == '2'){
			//document.getElementById("s2").checked = true;
			document.getElementById("s0").value = '2';
		}else if(v_season1 == '3'){
			document.getElementById("s3").checked = true;
		}else if(v_season1 == '4'){
			document.getElementById("s4").checked = true;
		}else if(v_season1 == '5'){
            document.getElementById("s5").checked = true;
        }else if(v_season1 == '6'){
            document.getElementById("s6").checked = true;
        }
		sForm3.operation_cnt1.value   	= '<%=v_operation_cnt1%>';		

		var operation1 			= '<%=v_operation1%>';
	 	var  o1   = operation1.substr(0,1);
		var  o2   = operation1.substr(1,1);
		var  o3   = operation1.substr(2,1);
		var  o4   = operation1.substr(3,1);
		var  o5   = operation1.substr(4,1);
		var  o6   = operation1.substr(5,1);

		sForm3.o1.checked = false;
		sForm3.o2.checked = false;
		sForm3.o3.checked = false;
		sForm3.o4.checked = false;
		sForm3.o5.checked = false;
		sForm3.o6.checked = false;
		if(list.operation1 == "111111") {
			sForm3.o0.checked = true;
		}else{
			if(o1 == "1") sForm3.o1.checked = true;
			if(o2 == "1") sForm3.o2.checked = true;
			if(o3 == "1") sForm3.o3.checked = true;
			if(o4 == "1") sForm3.o4.checked = true;
			if(o5 == "1") sForm3.o5.checked = true;
			if(o6 == "1") sForm3.o6.checked = true;
		}
		sForm3.oper_start_dt1.value		= '<%=v_oper_start_dt1%>';	
		sForm3.oper_end_dt1.value   	= '<%=v_oper_end_dt1%>';		
		sForm3.start_dt1.value   		= '<%=v_start_dt1%>';			
		sForm3.end_dt1.value   			= '<%=v_end_dt1%>';			
		sForm3.account_end_dt1.value  	= '<%=v_account_end_dt1%>';
		var account_way1		= '<%=v_account_way1%>';
		
		var  b1   = account_way1.substr(0,1);
		var  b3   = account_way1.substr(2,1);
		var  b5   = account_way1.substr(4,1);

		sForm3.b1.checked = false;
		sForm3.b3.checked = false;
		sForm3.b5.checked = false;
		
		if(b1 == "1") sForm3.b1.checked = true;
		if(b3 == "1") sForm3.b3.checked = true;	
		if(b5 == "1") sForm3.b5.checked = true;
		
		sForm3.receipt_pers_cnt1.value	= '<%=v_receipt_pers_cnt1%>';	
		var oper_start_tm1				= '<%=v_oper_start_tm1%>';
		oper_start_tm1=oper_start_tm1.substr(0,2)+":"+oper_start_tm1.substr(2,2);
		sForm3.oper_start_tm1.value		= oper_start_tm1;
		var oper_end_tm1				= '<%=v_oper_end_tm1%>';
		oper_end_tm1=oper_end_tm1.substr(0,2)+":"+oper_end_tm1.substr(2,2);
		sForm3.oper_end_tm1.value		= oper_end_tm1;
		sForm3.use_yn1.value   			= '<%=v_use_yn1%>';			
		sForm3.operation_place1.value   = '<%=v_operation_place1%>';	
		sForm3.remark1.value   			= '<%=v_remark1%>';			
		
		sForm4.finish_date1.value  	='<%=v_finish_date1%>';
		sForm4.finish_time1.value  	='<%=v_finish_time1%>';
		sForm4.finish_point1.value  	='<%=v_finish_point1%>';
		sForm4.edu_marks1.value  	='<%=v_edu_marks1%>';
		sForm4.new_cost1.value   			= '<%=v_new_cost1%>';
		sForm4.new_cost_nomem1.value		= '<%=v_new_cost_nomem1%>';
		sForm4.re_cost1.value   			= '<%=v_re_cost1%>';
		sForm4.mr_cost1.value   			= '<%=v_mr_cost1%>';
		sForm4.bank_name1.value   			= '<%=v_bank_name1%>';
		sForm4.bank_acc1.value   			= '<%=v_bank_acc1%>';
		sForm4.bank_acc_owner1.value   		= '<%=v_bank_acc_owner1%>';
		sForm4.service_marks1.value   		= '<%=v_service_marks1%>';
		
		$(":radio[name=code_target1][value=<%=v_code_target1%>]").prop("checked",true)
		sForm3.bran_cardjoin_tp1.value   		= '<%=v_bran_cardjoin_tp1%>';
		

		check_b1(sForm3.b1);
		check_ct();
		
	}else{
		if(<%=j_eds%>>0){
			var jc=eval(<%=j_educationsend1%>);

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
	    	
	    	//code_kind1 자격증교육 이나 자격시험 인 경우 2026.02
	    	$("#s3").removeAttr("checked");
            $("#s4").removeAttr("checked");
            $("#s5").removeAttr("checked");
            $("#s6").removeAttr("checked");
	        if ( val2 == "1" ) {
	            $("#s3").attr("disabled", "disabled");
	            $("#s4").attr("disabled", "disabled");
	            $("#s5").attr("disabled", "disabled");
                $("#s6").attr("disabled", "disabled");
	        }else if ( val2 == "5" ) {
	        	$("#s3").attr("disabled", "disabled");
                $("#s4").attr("disabled", "disabled");
                $("#s5").removeAttr("disabled");
                $("#s6").removeAttr("disabled");
	        }else{
	            $("#s3").removeAttr("disabled");
	            $("#s4").removeAttr("disabled");
                $("#s5").attr("disabled", "disabled");
                $("#s6").attr("disabled", "disabled");
	        }
	    	
	    	if('<%=v_detcode1%>'!=''){
				sForm.edutest_name1.value   = '<%=v_detcode1%>';
	    		if("<%=errMsg%>"!=""){ alert("<%=errMsg%>");}
				goSearch(sForm,0);
	    	}
		}
	}
	
	$(":radio[name=code_target1]").change(function(e){ check_ct(); });
}


function goSearch(form,intPage){
	/* 검색버튼 클릭시 그리드로 올리는 value 값 */
	 var edutest_name1   		= sForm.edutest_name1.value; 									//내용
	 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
	 var code_kind1   			= sForm.code_kind1.value;										//교육종류(교육및시험종류)(key)	 
 	 var outside_yn1 			= sForm.outside_yn1.value;										//외부교육여부
//  	 var finish_time1   		= sForm.finish_time1.value; 									//이수시간
// 	 var finish_date1 			= sForm.finish_date1.value;										//이수일수
// 	 var finish_point1 			= sForm.finish_point1.value;									//점수
// 	 var edu_marks1   			= sForm.edu_marks1.value;										//평점
	 var conform1				= sForm.conform1.value;											//승인여부
 	 var print_kind1 			= sForm.print_kind1.value;										//수료증관련여부
   	 var point_manage1 			= "";															//평점관리구분
 	 
	if(sForm.point_manage1[0].checked){
	   	//point_manage1+=sForm.point_manage1[0].checked ?"1":"0";
		point_manage1 = "1111111111";	
	} else {
		point_manage1+=sForm.point_manage1[1].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[2].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[3].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[5].checked ?"1":"0"; 
		point_manage1+=sForm.point_manage1[6].checked ?"1":"0"; 
		point_manage1+=sForm.point_manage1[4].checked ?"1":"0";
		//2025.02.04 평점관리 추가
		//t7 : 시니어푸드코디네이터, t8 : 다이어트운동컨설턴트
		point_manage1+=sForm.point_manage1[7].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[8].checked ?"1":"0";
		point_manage1+="00"; 
	}
   	 
 	
 	 var yyyy1					= sForm3.yyyy1.value;											//년도
	 var season1 				= "";
	 /* if(document.getElementById("s1").checked		== true )	season1 = "1";								//교육구분
	 else if(document.getElementById("s2").checked	== true )	season1="2"; */			
	 if(document.getElementById("s3").checked	== true )	season1="3";			
	 else if(document.getElementById("s4").checked == true )   season1="4";
	 else if(document.getElementById("s5").checked == true )   season1="5";
	 else if(document.getElementById("s6").checked == true )   season1="6";
	 else  season1="";
 	 var operation_cnt1 		= sForm3.operation_cnt1.value;									//횟수
	 
	 var operation1 				= "";														//시행대상
	 operation1+=sForm3.operation1[0].checked ?"1":"0";										
	 operation1+=sForm3.operation1[1].checked ?"1":"0";
	 operation1+=sForm3.operation1[2].checked ?"1":"0";
	 operation1+=sForm3.operation1[3].checked ?"1":"0";
	 operation1+=sForm3.operation1[4].checked ?"1":"0";
	 operation1+=sForm3.operation1[5].checked ?"1":"0";
	 
	 var oper_start_dt1 		= sForm3.oper_start_dt1.value;									//시작일
	 var start_dt1				= sForm3.start_dt1.value;										//접수시작일
	 var account_end_dt1 		= sForm3.account_end_dt1.value;									//결제마감일
  
     var account_way1 				= "";														//결제방법
     account_way1+=sForm3.account_way1[0].checked ?"1":"0";										
     account_way1+="0";
     account_way1+=sForm3.account_way1[1].checked ?"1":"0";
     account_way1+="0";
     account_way1+=sForm3.account_way1[2].checked ?"1":"0";
     account_way1+="0";
     account_way1+="0";
     
 	 var use_yn1 				= sForm3.use_yn1.value;											//사용여부
	 var receipt_pers_cnt1 		= sForm3.receipt_pers_cnt1.value;								//접수인원
	 var operation_place1 		= escape(encodeURIComponent(sForm3.operation_place1.value));		//시행장소
	 var oper_end_dt1 			= sForm3.oper_end_dt1.value;										//종료일
	 var end_dt1 				= sForm3.end_dt1.value;											//접수마감일
	 var remark1 				= escape(encodeURIComponent(sForm3.remark1.value));				//비고
	 var code_bran1				= sForm3.code_bran1.value;										//지부(교육주최)
//	 var code_seq1 				= sForm.code_seq1.value;										//순번(key)
	 var code_seq1 				= edutest_name1.substring(2,6);   //sForm.code_seq1.value;										//순번(key)
	 var bran_seq1 				= sForm.bran_seq1.value;										//지부순번
	 var bran_txt1 				= escape(encodeURIComponent(sForm3.bran_txt1.value));			//제목첨부
	 var oper_start_tm1 		= sForm3.oper_start_tm1.value;									//시행시간1
	 var oper_end_tm1 			= sForm3.oper_end_tm1.value;									//시행시간2
	 
 	 var finish_time1   		= sForm4.finish_time1.value; 									//이수시간
	 var finish_date1 			= sForm4.finish_date1.value;										//이수일수
	 var finish_point1 			= sForm4.finish_point1.value;									//점수
	 var edu_marks1   			= sForm4.edu_marks1.value;										//평점
	 
	 
//	 alert("bran_txt1="+bran_txt1);
	 $('#list').jqGrid('clearGridData');
	 
   jQuery("#list").setGridParam({url:"education.do?method=edutestSendList&yyyy1="+yyyy1+"&code_kind1="+code_kind1+"&code_bran1="+code_bran1+"&edutest_name1="+edutest_name1
		   +"&oper_start_dt1="+oper_start_dt1+"&oper_end_dt1="+oper_end_dt1+"&end_dt1="+end_dt1+"&finish_date1="+finish_date1+"&finish_point1="+finish_point1
		   +"&remark1="+remark1+"&code_certifi1="+code_certifi1+"&finish_time1="+finish_time1+"&outside_yn1="+outside_yn1+"&use_yn1="+use_yn1+"&edu_marks1="+edu_marks1
		   +"&conform1="+conform1+"&print_kind1="+print_kind1+"&point_manage1="+point_manage1+"&season1="+season1+"&operation_cnt1="+operation_cnt1
		   +"&oper_start_tm1="+oper_start_tm1+"&oper_end_tm1="+oper_end_tm1
   		   +"&operation1="+operation1+"&start_dt1="+start_dt1+"&account_end_dt1="+account_end_dt1+"&account_way1="+account_way1+"&receipt_pers_cnt1="+receipt_pers_cnt1
   		   +"&operation_place1="+operation_place1+"&code_seq1="+code_seq1+"&bran_seq1="+bran_seq1+"&bran_txt1="+bran_txt1+"&detcode1="+edutest_name1}
   		).trigger("reloadGrid");
}

function goSave(){
	/* search 에서 담아온 항목 이랑 같게 한다 */
	 var edutest_name1   		=sForm.edutest_name1.value; 									//내용
	 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
// 	 var finish_time1   		= sForm.finish_time1.value; 									//이수시간
 	 var outside_yn1 			= sForm.outside_yn1.value;										//외부교육여부
	 var code_kind1   			= sForm.code_kind1.value;										//교육종류(교육및시험종류)(key)
// 	 var finish_date1 			= sForm.finish_date1.value;										//이수일수
// 	 var finish_point1 			= sForm.finish_point1.value;									//점수
// 	 var edu_marks1   			= sForm.edu_marks1.value;										//평점
	 var conform1				= sForm.conform1.value;											//승인여부
 	 var print_kind1 			= sForm.print_kind1.value;										//수료증관련여부
 	 var point_manage1 			= "";									
 	 var upin					= sForm.upin.value;
 	 
	if(sForm.point_manage1[0].checked){
	   	//point_manage1+=sForm.point_manage1[0].checked ?"1":"0";
		point_manage1 = "1111111111";	
	} else {
		point_manage1+=sForm.point_manage1[1].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[2].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[3].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[5].checked ?"1":"0"; 
		point_manage1+=sForm.point_manage1[6].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[4].checked ?"1":"0";
		//2025.02.04 평점관리 추가
		//t7 : 시니어푸드코디네이터, t8 : 다이어트운동컨설턴트
		point_manage1+=sForm.point_manage1[7].checked ?"1":"0";
		point_manage1+=sForm.point_manage1[8].checked ?"1":"0";
		point_manage1+="00"; 
	}
 	
 	
//	 alert("평점관리구분="+point_manage1);
 	 var yyyy1					= sForm3.yyyy1.value;											//년도
//	 alert("년도="+yyyy1);
	 var season1 				= "";
	 
	 /* if(document.getElementById("s1").checked		== true )	season1 ="1";			//교육구분
	 else if(document.getElementById("s2").checked	== true )	season1 ="2"; */			
	 if(document.getElementById("s3").checked	== true )	season1 ="3";			
	 else if(document.getElementById("s4").checked == true )   season1 ="4";
	 else if(document.getElementById("s5").checked == true )   season1 ="5";
	 else if(document.getElementById("s6").checked == true )   season1 ="6";
	 else  season1="0";
//	 alert("교육구분="+season1);
 	 var operation_cnt1 		= sForm3.operation_cnt1.value;									//횟수
//	  alert("횟수="+operation_cnt1);
	 
	 var operation1 				= "";														//시행대상
	 
	 operation1+=sForm3.operation1[0].checked ?"1":"0";										
	 operation1+=sForm3.operation1[1].checked ?"1":"0";
	 operation1+=sForm3.operation1[2].checked ?"1":"0";
	 operation1+=sForm3.operation1[3].checked ?"1":"0";
	 operation1+=sForm3.operation1[4].checked ?"1":"0";
	 operation1+=sForm3.operation1[5].checked ?"1":"0";
	 
	 
	 
//	 alert("시행대상="+operation1);
	 var oper_start_dt1 		= sForm3.oper_start_dt1.value;									//시작일
//	 alert("시작일="+oper_start_dt1);
	 var start_dt1				= sForm3.start_dt1.value;										//접수시작일
//	 alert("접수시작일="+start_dt1);
	 var account_end_dt1 		= sForm3.account_end_dt1.value;									//결제마감일
//	 alert("결제마감일="+account_end_dt1);
  
     var account_way1 				= "";
	 
     account_way1+=sForm3.account_way1[0].checked ?"1":"0";										
     account_way1+="0";
     account_way1+=sForm3.account_way1[1].checked ?"1":"0";
     account_way1+="0";
     account_way1+=sForm3.account_way1[2].checked ?"1":"0";
     account_way1+="0";
     account_way1+="0";
     
	 
     
 	 var use_yn1 				= sForm3.use_yn1.value;											//사용여부
 	 if(use_yn1 == "") use_yn1="Y";

	 var receipt_pers_cnt1 		= sForm3.receipt_pers_cnt1.value;								//접수인원
	 var operation_place1 		= escape(encodeURIComponent(sForm3.operation_place1.value));		//시행장소
	 var oper_end_dt1 			= sForm3.oper_end_dt1.value;										//종료일
	 var end_dt1 				= sForm3.end_dt1.value;											//접수마감일
	 var remark1 				= escape(encodeURIComponent(sForm3.remark1.value));				//비고
	 var code_bran1				= sForm3.code_bran1.value;										//지부(교육주최)
	 var code_seq1 				= edutest_name1.substring(2,6);   //sForm.code_seq1.value;										//순번(key)
	 var bran_seq1 				= sForm.bran_seq1.value;										//지부순번(key)
	 var bran_txt1 				= escape(encodeURIComponent(sForm3.bran_txt1.value));										//제목첨부
	 var oper_start_tm1 		= sForm3.oper_start_tm1.value;										//시행시간1
	 var oper_end_tm1 			= sForm3.oper_end_tm1.value;										//시행시간2
	 oper_start_tm1=oper_start_tm1.replace(":","");
	 oper_end_tm1=oper_end_tm1.replace(":","");

	 var edu_t_name            = escape(encodeURIComponent(sForm.edu_t_name.value));
	 
	var bran_cardjoin_tp1 	= sForm3.bran_cardjoin_tp1.value;
	
	 if(edutest_name1==""){
		 alert("교육 및 시험명을 선택하지 않으시면 저장하실 수 없습니다.");
		 return;
	 }
	 if(yyyy1==""){
		 alert("년도를 선택해주십시오.");
		 return;
	 }
	 if(code_bran1==""){
		 alert("지부를 선택해주십시오.");
		 return;
	 }
	 if(bran_cardjoin_tp1==""){
		 alert("지부가맹유형을 선택해주십시오.");
		 return;
	 }
	 if(oper_start_dt1==""){
		 alert("시행시작일을 선택해주십시오.");
		 return;
	 }
	 if(oper_end_dt1==""){
		 alert("시행종료일을 선택해주십시오.");
		 return;
	 }
	 if(start_dt1==""){
		 alert("접수시작일 선택해주십시오.");
		 return;
	 }
	 if(end_dt1==""){
		 alert("접수마감일을 선택해주십시오.");
		 return;
	 }
	 if(oper_start_tm1==""||oper_end_tm1==""){
		 alert("시행시간을 선택해주십시오.");
		 return;
	 }
	 
	 //2026.02 결제방법 필수선택
	 if( !sForm3.account_way1[0].checked && !sForm3.account_way1[1].checked && !sForm3.account_way1[2].checked ){
		 alert("결제방법을 선택해주십시오.");
         return;
	 }
	 
	 //2026.02 자격시험인 경우 교육구분 필수
	 if( code_kind1 == "5" && season1 != "5" && season1 != "6" ){
         alert("교육구분을 선택해주십시오.");
         return;
     }

	 var finish_time1   		= sForm4.finish_time1.value; 									//이수시간
	 var finish_date1 			= sForm4.finish_date1.value;										//이수일수
	 var finish_point1 			= sForm4.finish_point1.value;									//점수
	 var edu_marks1   			= sForm4.edu_marks1.value;										//평점
	var new_cost1  			= sForm4.new_cost1.value;
	var new_cost_nomem1		= sForm4.new_cost_nomem1.value;
	var re_cost1  			= sForm4.re_cost1.value;
	var mr_cost1  			= sForm4.mr_cost1.value;
	var bank_name1  		= escape(encodeURIComponent(sForm4.bank_name1.value));
	var bank_acc1  			= sForm4.bank_acc1.value;
	var bank_acc_owner1 	= escape(encodeURIComponent(sForm4.bank_acc_owner1.value));
	var service_marks1 	= sForm4.service_marks1.value;
	var code_target1 	= $(":radio[name=code_target1]:checked").val();

	if($(":radio[name=code_target1]:checked").length==0){
		alert("교육대상을 선택해주십시오.");
		return;
	}
	
	if((edu_marks1 > "0")&&(service_marks1 > "0")){    	
		alert("교육평점 봉사점수 중 한 가지만 입력해 주십시오.") ;
	    return ;
	}
   	if( code_kind1 == "1"){
		if((finish_date1==""||finish_date1<="0")&&(finish_point1==""||finish_point1<="0")){
			alert("이수일수, 이수점수를 확인해주십시오.");
			return;
		}
	}else if( code_kind1 == "2"){
		if(edu_marks1==""&&service_marks1==""){
			if(document.sForm.outside_yn1.value=='봉사'){
				alert("봉사점수를 입력해주십시오.");
			}else{
				alert("교육평점을 입력해주십시오.");
			}
			return;
		}
		if((finish_date1==""||finish_date1<="0")&&(finish_time1==""||finish_time1<="0")){
			alert("이수일수, 이수시간 확인해주십시오.");
			return;
		}
	}else if(code_kind1 == "4"||code_kind1 == "8"){
		if(finish_time1==""||finish_time1<="0"){
			alert("이수시간을 확인해주십시오.");
			return;
		}
	}else if(code_kind1 == "6"){
		if(edu_marks1==""&&service_marks1==""){
			if(document.sForm.outside_yn1.value=='봉사'){
				alert("봉사점수를 입력해주십시오.");
			}else{
				alert("교육평점을 입력해주십시오.");
			}
			return;
		}
	}
	 
	if(sForm3.account_way1[0].checked ){
		if(bank_name1==""){ alert("은행명을 입력해주십시오."); sForm4.bank_name1.focus(); return; }
		if(bank_acc1==""){ alert("계좌번호를 입력해주십시오."); sForm4.bank_acc1.focus(); return; }
		if(bank_acc_owner1==""){ alert("예금주를 입력해주십시오."); sForm4.bank_acc_owner1.focus(); return; }
	}
	 
	 if (code_certifi1 != "" || code_kind1 != ""){
		 	if(!confirm("저장하시겠습니까?")){return;}
	    	document.sForm.action = "education.do?method=edutestSave&edutest_name1="+edutest_name1+"&code_certifi1="+code_certifi1+"&finish_time1="+finish_time1
 	    	+"&outside_yn1="+outside_yn1+"&code_kind1="+code_kind1+"&finish_date1="+finish_date1+"&finish_point1="+finish_point1+"&edu_marks1="+edu_marks1
	    	+"&conform1="+conform1+"&print_kind1="+print_kind1+"&point_manage1="+point_manage1+"&yyyy1="+yyyy1+"&season1="+season1+"&operation_cnt1="+operation_cnt1
	    	+"&operation1="+operation1+"&oper_start_dt1="+oper_start_dt1+"&start_dt1="+start_dt1+"&account_end_dt1="+account_end_dt1+"&account_way1="+account_way1
	    	+"&oper_start_tm1="+oper_start_tm1+"&oper_end_tm1="+oper_end_tm1
	    	+"&use_yn1="+use_yn1+"&receipt_pers_cnt1="+receipt_pers_cnt1+"&operation_place1="+operation_place1+"&oper_end_dt1="+oper_end_dt1+"&end_dt1="+end_dt1
	    	+"&remark1="+remark1+"&code_bran1="+code_bran1+"&code_seq1="+code_seq1+"&bran_seq1="+bran_seq1+"&bran_txt1="+bran_txt1+"&detcode1="+edutest_name1+"&edu_t_name="+edu_t_name+"&upin="+upin
	    	+"&new_cost1="+new_cost1+"&new_cost_nomem1="+new_cost_nomem1+"&re_cost1="+re_cost1+"&mr_cost1="+mr_cost1+"&bank_name1="+bank_name1+"&bank_acc1="+bank_acc1+"&bank_acc_owner1="+bank_acc_owner1+"&service_marks1="+service_marks1+"&code_target1="+code_target1+"&bran_cardjoin_tp1="+bran_cardjoin_tp1;

			//alert(document.sForm.action);    	
	    	document.sForm.submit();   
	}else{
		return;
	}
}

function goDel(){
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr == null ) {
		alert("목록에서 삭제하실 교육을 선택해 주세요."); return;
	}
	 if(sForm.bran_seq1.value==""){
		alert("삭제하실 교육을 다시 선택해 주세요."); return;
	}
	
	
	 var edutest_name1   		=sForm.edutest_name1.value; 									//내용

// 	 var code_kind1   			= sForm.code_kind1.value;										//교육종류(교육및시험종류)(key)	 
// 	 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
// //	 var code_seq1 				= sForm.code_seq1.value;										//순번(key)
// 	 var code_seq1 				= edutest_name1.substring(2,6);   //sForm.code_seq1.value;										//순번(key)
	
	 var code_kind1   			= sForm3.code_kind_key.value.substr(0,1);										//교육종류(교육및시험종류)(key)	 
	 var code_certifi1 			= sForm3.code_certifi_key.value;									//구분코드(교육및시험구분)(key)
	 var code_seq1 			= sForm3.code_seq_key.value;									//순번(key)
	 
	 var code_bran1				= sForm3.code_bran1.value;										//지부(교육주최)
	 var bran_seq1 				= sForm.bran_seq1.value;										//지부순번
	
	//alert(code_kind1+" : "+code_certifi1+" : "+code_seq1+" : "+code_bran1+" : "+bran_seq1); return;
	if(!confirm("선택하신 교육을 삭제하시겠습니까?\n\n삭제한 교육은 다시 복구할 수 없습니다.")){return;}
	 
   	document.sFormDel.action = "education.do?method=edutestDel&code_certifi1="+code_certifi1+"&code_kind1="+code_kind1+"&code_bran1="+code_bran1+
   			"&code_seq1="+code_seq1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1;
   	document.sFormDel.submit();   
}


  function goSelect(rowid,iCol){
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
	var list = $("#list").getRowData(gr);
			
	//그리드에서 select시에 테이블로 출력될 value값들  
	
	document.sForm.edu_t_name.value         = list.edutestname;         //교육명

	document.sForm3.yyyy1.value     			= list.yyyy;				//년도
	document.sForm3.code_bran1.value     	= list.code_bran;			//지부(교육주최)

	document.getElementById("s0").value = '';
	document.getElementById("s1").value = '';
	document.getElementById("s2").value = '';
	//document.getElementById("s1").checked = false;
	//document.getElementById("s2").checked = false;
	document.getElementById("s3").checked = false;
	document.getElementById("s4").checked = false;
	document.getElementById("s5").checked = false;
	document.getElementById("s6").checked = false;
	
	if(list.season == '0') {
		document.getElementById("s0").value = '0';
	}else if(list.season == '1'){
		//document.getElementById("s1").checked = true;
		document.getElementById("s1").value = '1';
	}else if(list.season == '2'){
		//document.getElementById("s2").checked = true;
		document.getElementById("s2").value = '2';
	}else if(list.season == '3'){
		document.getElementById("s3").checked = true;
	}else if(list.season == '4'){
		document.getElementById("s4").checked = true;
	}else if(list.season == '5'){
        document.getElementById("s5").checked = true;
	}else if(list.season == '6'){
        document.getElementById("s6").checked = true;
	} 

	document.sForm3.operation_cnt1.value     = list.operation_cnt;		//횟수
	document.sForm3.oper_start_dt1.value     = list.oper_start_dt;		//시행시작일
	document.sForm3.start_dt1.value     		= list.start_dt;			//접수시작일
	document.sForm3.operation_place1.value   = list.operation_place;		//시행장소
	document.sForm3.use_yn1.value     		= list.use_yn;				//사용여부
	document.sForm3.receipt_pers_cnt1.value  = list.receipt_pers_cnt;	//접수인원

	var account_way1 			= list.account_way;
 	var  b1   = account_way1.substr(0,1);
	var  b3   = account_way1.substr(2,1);
	var  b5   = account_way1.substr(4,1);

	sForm3.b1.checked = false;
	//sForm3.a2.checked = false;
	sForm3.b3.checked = false;
	//sForm3.a4.checked = false;
	sForm3.b5.checked = false;
	//sForm3.a6.checked = false;
	//sForm3.a7.checked = false;
	
	if(b1 == "1") sForm3.b1.checked = true;
	if(b3 == "1") sForm3.b3.checked = true;	
	if(b5 == "1") sForm3.b5.checked = true;
	
	var operation1 			= list.operation;
 	var  o1   = operation1.substr(0,1);
	var  o2   = operation1.substr(1,1);
	var  o3   = operation1.substr(2,1);
	var  o4   = operation1.substr(3,1);
	var  o5   = operation1.substr(4,1);
	var  o6   = operation1.substr(5,1);

	sForm3.o1.checked = false;
	sForm3.o2.checked = false;
	sForm3.o3.checked = false;
	sForm3.o4.checked = false;
	sForm3.o5.checked = false;
	sForm3.o6.checked = false;
	if(list.operation1 == "111111") {
		sForm3.o0.checked = true;
	}else{
		if(o1 == "1") sForm3.o1.checked = true;
		if(o2 == "1") sForm3.o2.checked = true;
		if(o3 == "1") sForm3.o3.checked = true;
		if(o4 == "1") sForm3.o4.checked = true;
		if(o5 == "1") sForm3.o5.checked = true;
		if(o6 == "1") sForm3.o6.checked = true;
	}
	
	document.sForm3.account_end_dt1.value    = list.account_end_dt;		//결제마감일
	document.sForm3.oper_end_dt1.value     	= list.oper_end_dt;			//시행종료일
	document.sForm3.end_dt1.value     		= list.end_dt;				//접수마감일
	document.sForm3.remark1.value     		= list.remark;				//비고
	document.sForm3.code_bran1.value     	= list.han_code_bran;			//교육주최(지부)
	document.sForm.code_seq1.value     		= list.code_seq;			//순번(key)
	document.sForm.bran_seq1.value     		= list.bran_seq;			//지부순번
	document.sForm3.bran_txt1.value     		= list.bran_txt;			//제목첨부
	document.sForm3.oper_start_tm1.value     	= list.oper_start_tm.substr(0,2)+":"+list.oper_start_tm.substr(2,2);		//시행시간1
	document.sForm3.oper_end_tm1.value     		= list.oper_end_tm.substr(0,2)+":"+list.oper_end_tm.substr(2,2);		//시행시간2

	document.sForm4.finish_date1.value     	= list.finish_date; //출석일(이수일수)
	document.sForm4.finish_point1.value     = list.finish_point; //시험점수(교육평점)
	document.sForm4.finish_time1.value     	= list.finish_time; //이수시간
	document.sForm4.edu_marks1.value     	= list.edu_marks; //평점   			
	document.sForm4.new_cost1.value     	= list.new_cost; //회원 신규비용
	document.sForm4.new_cost_nomem1.value   = list.new_cost_nomem; //비회원 신규비용
	document.sForm4.re_cost1.value     		= list.re_cost; //재시험비용
	document.sForm4.mr_cost1.value     		= list.mr_cost; //석사이상비용
	document.sForm4.bank_name1.value     	= list.bank_name; //은행명
	document.sForm4.bank_acc1.value     	= list.bank_acc; //계좌번호
	document.sForm4.bank_acc_owner1.value   = list.bank_acc_owner; //예금주
	document.sForm4.service_marks1.value   = list.service_marks; //봉사점수
	$(":radio[name=code_target1][value="+list.code_target+"]").prop("checked",true) //교육대상
	document.sForm3.bran_cardjoin_tp1.value   = list.bran_cardjoin_tp; //지부가맹유형
	
	document.sForm.upin.value         = "up";
	
	check_b1(sForm3.b1);
	check_ct();
	
	document.sForm3.code_kind_key.value   = list.detcode;
	document.sForm3.code_certifi_key.value   = list.code_certifi;
	document.sForm3.code_seq_key.value   = list.code_seq;
}
}



 //검색조건에 맞는 이름을 찾는다.
function setName(){
	var code_certifi	=	document.getElementById("code_certifi1").value;		//교육및시험구분 값
	var code_kind		=	document.getElementById("code_kind1").value;		//교육및시험종류 값
	// 교육구분 교육종류로 비교 포함값 표출
	if ( code_certifi == "" || code_kind == "" ){		
		setName1("선택","");
		return;
	}
	/*action send 로 보내는 폼  */
	document.sForm.action="education.do?method=edutestSend&code_certifi1="+code_certifi+"&code_kind1="+code_kind;
	document.sForm.submit();
}

function setName1(name,value){
	var edutest_name1=document.getElementById("edutest_name1");
	var opts=edutest_name1.options;
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
//	alert("list.edutestname 2 ==>"+value);

	edutest_name1[0]=new Option(name,value);
}


 
function downloadExcel() {
	drawExcel("교육별응시현황", "#list");
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});

$(function() {
	$( "#datepicker2" ).datepicker();
});

$(function() {
	$( "#datepicker3" ).datepicker();
});

$(function() {
	$( "#datepicker4" ).datepicker();
});

$(function() {
	$( "#datepicker5" ).datepicker();
});

$(function() {
	$( "#datepicker6" ).datepicker();
});
$(function() {
	$( "#timepicker1" ).timepicker();
});
$(function() {
	$( "#timepicker2" ).timepicker();
});
function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "201";
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

function setEduT(code){
	var jc=eval(<%=j_educationsend1%>);
	for(var i=0;i<jc.length;i++){
		if(jc[i].detcode==code){
			document.sForm.outside_yn1.value     	= jc[i].outside_nm;			//외부교육여부
			if(document.sForm.outside_yn1.value=='봉사'){
				$("#edu_marks1").val('').prop("disabled",true);
				$("#service_marks1").prop("disabled",false);
			}else{
				$("#edu_marks1").prop("disabled",false);
				$("#service_marks1").val('').prop("disabled",true)
			}
// 			document.sForm.finish_time1.value     	= jc[i].finish_time;			//이수시간
// 			document.sForm.finish_point1.value     	= jc[i].finish_point;		//시험점수
			if(jc[i].conform=="Y"){document.sForm.conform1.value="승인"}else{document.sForm.conform1.value="미승인"}//승인여부				
// 			document.sForm.finish_date1.value     	= jc[i].finish_date;			//이수일수
// 			document.sForm.edu_marks1.value     	= jc[i].edu_marks;			//평점
			document.sForm.print_kind1.value     	= jc[i].print_nm;			//수료증관련여부
			
			var point_manage1 			= jc[i].point_manage;
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
			
			sForm.t0.checked = false;
			sForm.t1.checked = false;
			sForm.t2.checked = false;
			sForm.t3.checked = false;
			sForm.t5.checked = false;
			sForm.t6.checked = false;
			sForm.t4.checked = false;
			sForm.t7.checked = false;
			sForm.t8.checked = false;
			                         
			if(jc[i].point_manage == "1111111111") {
				sForm.t0.checked = true;
			}else{
				if(t1 == "1") sForm.t1.checked = true;
				if(t2 == "1") sForm.t2.checked = true;
				if(t3 == "1") sForm.t3.checked = true;
				if(t5 == "1") sForm.t5.checked = true;
				if(t6 == "1") sForm.t6.checked = true;
				if(t4 == "1") sForm.t4.checked = true;
				if(t7 == "1") sForm.t7.checked = true;
				if(t8 == "1") sForm.t8.checked = true;
				
			}
		}
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

function check_b1(obj){
	if(obj.checked){
		document.sForm4.bank_name1.disabled="";
		document.sForm4.bank_acc1.disabled="";
		document.sForm4.bank_acc_owner1.disabled="";
	} else {
		document.sForm4.bank_name1.value=""; document.sForm4.bank_name1.disabled="disabled";
		document.sForm4.bank_acc1.value=""; document.sForm4.bank_acc1.disabled="disabled";
		document.sForm4.bank_acc_owner1.value=""; document.sForm4.bank_acc_owner1.disabled="disabled";
	}
}
function check_ct(){
	if($(":radio[name=code_target1]:checked").length==0){
		document.sForm4.new_cost1.value=""; document.sForm4.new_cost1.disabled="disabled";
		document.sForm4.new_cost_nomem1.value=""; document.sForm4.new_cost_nomem1.disabled="disabled";
	} else if($(":radio[name=code_target1]:checked").val()=='1'){
		document.sForm4.new_cost1.disabled="";
		document.sForm4.new_cost_nomem1.value=""; document.sForm4.new_cost_nomem1.disabled="disabled";
	} else if($(":radio[name=code_target1]:checked").val()=='2'){
		document.sForm4.new_cost1.value=""; document.sForm4.new_cost1.disabled="disabled";
		document.sForm4.new_cost_nomem1.disabled="";
	} else if($(":radio[name=code_target1]:checked").val()=='3'){
		document.sForm4.new_cost1.disabled="";
		document.sForm4.new_cost_nomem1.disabled="";
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
		<li class="NPage">교육및시험등록</li>
	  </ul>	
	  
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>

	  <div class="eTabmenu_01">
<form id="list1" name="sForm" method="post" action="">
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
	  <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
	  <input type="hidden" name="code_seq1" id="code_seq1" value=""/>
	  <input type="hidden" name="bran_seq1" id="bran_seq1" value=""/>
	  <input type="hidden" name="upin" id="upin" value="in"/>  
  <table class="etable_01" cellspacing="0" summary="교육">
          <caption>교육및시험등록</caption>
          	<tr>
             	<td class="nobg">※&nbsp;&nbsp;교육및시험구분</td>
	            <td class="nobg1">
	               <select  id="code_certifi1" name="code_certifi1" onchange="javascript:setName();">
	              <option value="">전체</option>
	              		 <%
	                	String detCode5,detCName5=null;
	                	for(int i=0;i<certifisearch1.size();i++){
	                			detCode5=certifisearch1.get(i).get("code_certifi").toString();
	                			detCName5=certifisearch1.get(i).get("certifi_name").toString();
	                			if(edutake1!=null&&detCode5.equals(edutake1.get("code_certifi1").toString())){
	                				out.println("<option value='"+detCode5+"' selected>"+detCName5+"</option>");
	                			}else{
	                				out.println("<option value='"+detCode5+"'>"+detCName5+"</option>");
	                			}
	                		}
	                	
	               		 %>   
	               	</select>
	            </td>
	            <td class="nobg2">※&nbsp;&nbsp;교육및시험종류</td>
			   <td class="nobg1">
			   		<select  id="code_kind1" name="code_kind1" onchange="javascript:setName();">
						<option value="">전체</option>	
		              		<%
		              		String detCode,detCName=null;
						 	for(int i=0;i<certifisearch.size();i++){
						 		if("016".equals(certifisearch.get(i).get("groupcode").toString())){
		                			detCode=certifisearch.get(i).get("detcode").toString();
		                			detCName=certifisearch.get(i).get("detcodename").toString();
		                			if(edutake1!=null&&detCode.equals(edutake1.get("code_kind1").toString())){
		                				out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
		                			}else{
		                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
		                			}
		                		}
						 	}
						 	%>   
               	    </select>
               	</td>
               	
               	<td class="nobg2">외부교육여부</td>
               <td class="nobg1">
               		<input  id="name" name="outside_yn1" value="" class="readOnly1" readonly onkeydown="ban(this);"/>
               	</td>
               </tr>            			 
             <tr>
               <td class="alt1">교육및시험명</td>
               <td colspan="3" >
	               <select  name="edutest_name1"  id="edutest_name1" style="width:500px" onchange="javascript:setEduT(this.value)">
	               <option value="">선택</option>	
	               </select>
	           </td>
               <td colspan="2" >
               	<input name="edu_t_name" value="" size="50" class="readOnly1" readonly onkeydown="ban(this);"/>
               </td>	
             </tr>
             
<!--                <tr> -->
<!--                		<td class="alt1">이수일수</td> -->
<!--                		<td><input type="text" id="finish_date1" name="finish_date1" class="readOnly1" readonly onkeydown="ban(this);"/></td> -->
<!-- 	               	<td class="alt">이수시간</td>  -->
<!-- 	               	<td><input type="text" id="finish_time1" name="finish_time1" class="readOnly1" readonly onkeydown="ban(this);"/></td> -->
<!-- 	             	<td class="alt">이수점수</td>  -->
<!-- 	               	<td><input type="text" id="finish_point1" name="finish_point1" class="readOnly1" readonly onkeydown="ban(this);"/></td>	                 -->
<!--                </tr> -->
			 <tr>	  
<!--                <td class="alt1">교육평점</td> -->
<!--                <td><input type="text" id="edu_marks1" name="edu_marks1" class="readOnly1" readonly onkeydown="ban(this);"/></td> -->
               <td class="alt1">증서구분</td>
               <td><input  id="print_kind1" name="print_kind1" value="" class="readOnly1" readonly onkeydown="ban(this);"/></td>
                 <td class="alt">승인여부</td>
		         <td>
		         	<input id="conform1" name="conform1" value="" class="readOnly1" readonly onkeydown="ban(this);"/>
					</td>
                 <td class="alt" colspan="2"></td>
             </tr>
			 
			 <tr>
               <td class="alt1" >평점관리</td>
               <td colspan="5"  align="left">             		
               		<input type="checkbox" name="point_manage1" id="t0" />&nbsp;<label for="t0">전문</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t1" />&nbsp;<label for="t1">급식경영</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t2" />&nbsp;<label for="t2">임상영양</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t3" />&nbsp;<label for="t3">산업보건</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t5" />&nbsp;<label for="t5">노인영양사</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t6" />&nbsp;<label for="t6">스포츠영양사</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t7" />&nbsp;<label for="t7">시니어푸드코디네이터</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t8" />&nbsp;<label for="t8">다이어트운동컨설턴트</label>&nbsp;&nbsp;&nbsp;&nbsp;
               		<input type="checkbox" name="point_manage1" id="t4" />&nbsp;<label for="t4">기타</label><br>
               		<font color="red">(※ 이 체크박스는 변경하셔도 저장되지 않습니다.)</font>
               	</td>
			 </tr>		 		
        </table>
    </form>    
        
       <form id="list2" name="sForm3" method="post" action="">
       <input type="hidden" name="code_kind_key" value="" />	
       <input type="hidden" name="code_certifi_key" value="" />	
       <input type="hidden" name="code_seq_key" value="" />
       	
		<table class="etable_02" cellspacing="0" summary="교육">
          <caption>교육및시험등록</caption>             
             <tr>
               <td class="nobg">제목첨부</td>
               <td class="nobg1" colspan="5" style="text-align:left;">
               		<input type="text" id="bran_txt1"  size="100" name="bran_txt1"/>
               </td>
			 </tr> 
             <tr>
               <td class="nobg">년도</td>
               <td class="nobg1" >지부</td>
               <td class="nobg1" >지부가맹유형</td>               			   
			   <td class="nobg1" style="width:10%">교육구분</td>
			   <td class="nobg1">횟수</td>
			   <td class="nobg2" style="width:20%">시행대상</td>
			 </tr> 
			 <tr>
			 	<td class="alt">
					   <select id="yyyy1" name="yyyy1">	
		               <option value="">전체</option>
						 <%
						 	String detCode2,detCName2="";
						 	for(int i=0;i<certifisearch.size();i++){
						 		if("036".equals(certifisearch.get(i).get("groupcode").toString())){
		                			detCode2=certifisearch.get(i).get("detcode").toString();
		                			detCName2=certifisearch.get(i).get("detcodename").toString();
		                			out.println("<option value="+detCode2+">"+detCName2+"</option>");
		                		}
						 	}
						 %>        	
		              	</select>
              	</td>
              	<td >
		              	<select  id="code_bran1" name="code_bran1" >
		               		<option value="">전체</option>	
		              		<%
						 	for(int i=0;i<certifisearch.size();i++){
						 		if("034".equals(certifisearch.get(i).get("groupcode").toString())){
		                			detCode=certifisearch.get(i).get("detcode").toString();
		                			detCName=certifisearch.get(i).get("detcodename").toString();
		                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
						 		}
						 	}
						 %>   
		               	    </select>
              	</td>
              	 <td>
		              	<select  id="bran_cardjoin_tp1" name="bran_cardjoin_tp1" disabled="disabled">
<!-- 		               		<option value="">선택</option>	 -->
		              		<%
						 	for(int i=0;i<certifisearch.size();i++){
						 		if("055".equals(certifisearch.get(i).get("groupcode").toString())){
		                			detCode=certifisearch.get(i).get("detcode").toString();
		                			detCName=certifisearch.get(i).get("detcodename").toString();
		                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
						 		}
						 	}
						 %>   
		               	    </select>
			     </td>
              	<td style="width:12%">
              	   <input type="hidden" id="s0" value="0" />
              	   <input type="hidden" id="s1" value="1" />
              	   <input type="hidden" id="s2" value="2" />
              	   
				   <div style="border:0px solid black; display:inline-block; text-align:left;">
					   <!-- <input type="radio" name="season1" id="s1" value="1" />
					   <label for="s1">1학기</label><br/> -->
					   <input type="radio" name="season1" id="s3" value="3" />
					   <label for="s3">집합교육</label><br/>
					   <input type="radio" name="season1" id="s5" value="5" />
                       <label for="s5">검정과목1</label>
				   </div>
				   <div style="border:0px solid red; display:inline-block; text-align:left;">
					   <!-- <input type="radio" name="season1" id="s2" value="2" />
					   <label for="s2">2학기</label><br/> -->
					   <input type="radio" name="season1" id="s4" value="4" />
                       <label for="s4">온라인교육</label><br/>
					   <input type="radio" name="season1" id="s6" value="6" />
                       <label for="s6">검정과목2</label>
				   </div>
			   </td>
			   <td>
			   		<input type="text" id="operation_cnt1"  size="4" name="operation_cnt1" onKeyUp="javascript:check_Int('sForm3','operation_cnt1')"/>
			   </td>
			 	<td style="width:20%">
		               	   <input type="checkbox" name="operation1" id="o1" value="post" />
						   <label for="a_old">전체</label>
						   <input type="checkbox" name="operation1" id="o2" value="post" />
						   <label for="a_new">교육이수</label>
						   <input type="checkbox" name="operation1" id="o3" value="post" />
						   <label for="a_old">재시험</label>
						   <input type="checkbox" name="operation1" id="o4" value="post" />
						   <label for="a_new">석사이상</label>
						   <input type="checkbox" name="operation1" id="o5" value="post" />
						   <label for="a_new">말소자</label>
						   <input type="checkbox" name="operation1" id="o6" value="post" />
						   <label for="a_new">일반인</label>
		            </td>
			 </tr>
			 <tr> 
			   <td class="nobg" >시행시작일</td>
			   <td class="nobg1" >시행종료일</td>
			   <td class="nobg2" >접수시작일</td>
			   <td class="nobg1" style="width:10%">접수마감일</td>
			   <td class="nobg1" >결제마감일</td>
			   <td class="nobg2" style="width:20%">결제방법</td>
			 </tr>
			 <tr>
			   <td class="alt">
			   		<input type="text" id="datepicker"  size="4" name="oper_start_dt1"/>
			   </td>
			   <td>
               		<input type="text" id="datepicker4"  size="4" name="oper_end_dt1"/>
               	</td>
               	<td>
			   		<input type="text" id="datepicker1" size="4" name="start_dt1"/>
			   </td>
               	<td style="width:10%">
               		<input type="text" id="datepicker5"  size="4" name="end_dt1"/>
               		
               	</td>
               	<td>
			   				<input type="text" id="datepicker2" size="4" name="account_end_dt1" />
			   	</td>
			   	<td style="width:20%">
						   <input type="checkbox" name="account_way1" id="b1" value="post" onclick="check_b1(this);"/>
						   <label for="b1">무통장</label>
						   <!-- <input type="checkbox" name="account_way1" id="a2" value="post" />
						   <label for="a_new">CMS</label> -->
						   <input type="checkbox" name="account_way1" id="b3" value="post" />
						   <label for="b3">카드</label>
						   <!-- <input type="checkbox" name="account_way1" id="a4" value="post" />
						   <label for="a_new">계산서</label> -->
						   <input type="checkbox" name="account_way1" id="b5" value="post" />
						   <label for="b5">현장납부</label>
						   <!-- <input type="checkbox" name="account_way1" id="a6" value="post" />
						   <label for="a_new">지로</label>
						   <input type="checkbox" name="account_way1" id="a7" value="post" />
						   <label for="a_new">기타</label> -->
					   </td>
               	</tr>
               <tr>
				   <td class="nobg" >접수인원</td>
				   <td class="nobg2" >시행시간</td>
				   <td class="nobg2" >사용여부</td>
				    <td class="nobg2" colspan="2">시행장소</td>
				   
				   <td class="nobg1" style="width:20%" colspan="2">비고</td>
			 </tr>
               <tr>
		               <td class="alt" >
		               		<input type="text" id="receipt_pers_cnt1"  size="4" name="receipt_pers_cnt1" onKeyUp="javascript:check_Int('sForm3','receipt_pers_cnt1')"/>
		               </td>
		               <td >
               				<input type="text" id="timepicker1" size="6" name="oper_start_tm1" />-<input type="text" id="timepicker2" size="6" name="oper_end_tm1" />
               			</td>
		               <td>
		               		<select id="use_yn1" name="use_yn1" >	
			                    <option value="Y">사용</option>
				                <option value="N">미사용</option>	                
		               		</select>
               			</td>
               			<td colspan="2">
               				<input type="text" id="p" size="40" name="operation_place1"/>
               			</td>
               			<td style="width:20%" colspan="2">
			   				<input type="text" id="p" size="60"  name="remark1"/>
			   			</td>
			 </tr>
        </table>
        </form>
        
        <form id="list3" name="sForm4" method="post" action="">
          <table class="etable_02" cellspacing="0" summary="교육 및 시험명">
          <tr>
			   <td class="nobg" align="center" >교육대상</td>
               <td class="nobg1" colspan="3">
		              		<%
						 	int code_target_cnt = 0;
		              		for(int i=0;i<certifisearch.size();i++){
						 		if("054".equals(certifisearch.get(i).get("groupcode").toString())){
		                			detCode=certifisearch.get(i).get("detcode").toString();
		                			detCName=certifisearch.get(i).get("detcodename").toString();
		                	%>
				   <input type="radio" name="code_target1" id="code_target1_<%=++code_target_cnt%>" value="<%=detCode %>" /> <label for="code_target1_<%=code_target_cnt%>"><%=detCName %></label>
		                	<%
						 		}
						 	}
						 %>   

               </td>
               <td class="nobg2" align="center" >교육평점</td>
               <td class="nobg1"><input type="text" id="edu_marks1" name="edu_marks1" size="15" onKeyUp="javascript:check_Int('sForm4','edu_marks1')"/></td>
               <td class="nobg2" align="center" >봉사점수</td>
               <td class="nobg1"><input type="text" id="service_marks1" name="service_marks1" size="15" onKeyUp="javascript:check_Int('sForm4','service_marks1')" disabled="disabled"/></td>
			  </tr>	
          <tr>
			   <td class="alt" align="center" >이수일수</td>
               <td ><input type="text" id="finish_date1" name="finish_date1" size="15" onKeyUp="javascript:check_Int('sForm4','finish_date1')"/></td>
               <td class="alt1" align="center">이수시간</td>
               <td ><input type="text" id="finish_time1" name="finish_time1" size="15" onKeyUp="javascript:check_Int('sForm4','finish_time1')"/></td>
               <td class="alt1" align="center" >이수점수</td>
               <td ><input type="text" id="finish_point1" name="finish_point1" size="15" onKeyUp="javascript:check_Int('sForm4','finish_point1')"/></td>
               <td class="alt1" align="center" colspan="2"></td>
			  </tr>	
			  <tr>
           	    <td class="alt" align="center">회원 신규비용</td>
               <td><input type="text" id="new_cost1" name="new_cost1" size="15" onKeyUp="javascript:check_Int('sForm4','new_cost1')" disabled="disabled"/></td>
           	    <td class="alt1" align="center">비회원 신규비용</td>
               <td><input type="text" id="new_cost_nomem1" name="new_cost_nomem1" size="15" onKeyUp="javascript:check_Int('sForm4','new_cost_nomem1')" disabled="disabled"/></td>
           		<td class="alt1" align="center">재시험비용</td>
               <td><input type="text" id="re_cost1" name="re_cost1" size="15" onKeyUp="javascript:check_Int('sForm4','re_cost1')"/></td>
               <td class="alt1" align="center">석사이상비용</td>
               <td><input type="text" id="mr_cost1" name="mr_cost1" size="15" onKeyUp="javascript:check_Int('sForm4','mr_cost1')"/></td> 
              </tr>           	
			  <tr>
           	    <td class="alt" align="center">은행명</td>
               <td><input type="text" id="bank_name1" name="bank_name1" size="15" disabled="disabled"/></td>
           	    <td class="alt1" align="center">계좌번호</td>
               <td><input type="text" id="bank_acc1" name="bank_acc1" size="15" disabled="disabled"/></td>
           		<td class="alt1" align="center">예금주</td>
               <td><input type="text" id="bank_acc_owner1" name="bank_acc_owner1" size="15" disabled="disabled"/></td>
               <td class="alt1" align="center" colspan="2"></td>
              </tr>           	
			 </table>			
        </form>
        <br/>
        
        
        
        
        
        
        
         	
		<ul class="btnIcon_1" style="left:869px;">
		  	   <%if("cocone".equals(g_id) || "minju51".equals(g_id)){ %>
		  	   <li><a href="javascript:goDel();"><img src="images/icon_delete.gif"    onclick="" alt="삭제" /></a></li>		 
<!-- 		  	   <li><span onclick="goDel();" style="cursor:pointer;">[삭제]</span></li> -->
		  	   <%} %>		 
		  	   <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li>		 
			   <li><a href="javascript:goSave();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
			   <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>	 
		</ul>			
 <br>
 <br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post"></form>
<form name="sFormDel" method="post"></form>
 </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	
 
 </body> 
</html>