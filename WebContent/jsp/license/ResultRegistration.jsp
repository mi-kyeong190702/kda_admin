
<%@page import="com.ant.educationlecture.dao.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>대한영양사협회</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/certificate.css" />
  
  
<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<script src="js/form.js"  type="text/javascript"></script>
<script src="js/ui.multiselect.js" type="text/javascript"></script>

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

	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

	Map map = new HashMap();
	
	licenseDao dao = new licenseDao();



	List<Map> licenseserch 	= dao.licenseserch(map); 				
	List<Map> licenseserch1 = dao.licenseserch1(map);
	
		
	
	String v_yyyy1=request.getParameter("yyyy1");
	String v_code_bran1=request.getParameter("code_bran1");
	String v_code_certifi1=request.getParameter("code_certifi1");
	
	Map etest1 = (Map)request.getAttribute("etest1");
	Map etest2 = (Map)request.getAttribute("etest2");
	List<Map> selectEtestN = (List<Map>)request.getAttribute("selectEtestN1");
	JSONArray j_test=(JSONArray)request.getAttribute("j_test");
	
	int etk2=0;
	String v_yyyy11="";
	String v_code_bran11="";
	String v_code_certifi11="";	
	String v_code_operation11="";
	String v_detcode11="";
	
	String v_oper_name11="";
// 	JUMIN_DEL
// 	String v_oper_no11="";
	String v_oper_birth11="";
	String v_oper_lic_no11="";
	String v_attend_cnt11="";
	String v_time_cnt11="";
	String v_result_point11="";	
	
	if(etest2!=null){
		etk2=etest2.size();
		v_yyyy11=etest2.get("yyyy1").toString();
		v_code_bran11=etest2.get("code_bran1").toString();
		v_code_certifi11=etest2.get("code_certifi1").toString();		
		v_code_operation11=etest2.get("code_operation1").toString();
		v_detcode11=etest2.get("detcode1").toString();
		
		v_oper_name11=etest2.get("oper_name1").toString();
// 		JUMIN_DEL
// 		v_oper_no11=etest2.get("oper_no1").toString();
		v_oper_birth11=etest2.get("oper_birth1").toString();
		v_oper_lic_no11=etest2.get("oper_lic_no1").toString();
		v_attend_cnt11=etest2.get("attend_cnt1").toString();
		v_time_cnt11=etest2.get("time_cnt1").toString();
		v_result_point11=etest2.get("result_point1").toString();
	}
	
	int j_eds=0;
	if(j_test!=null){
		j_eds=j_test.size();
	}
%>


<script type="text/javascript">
function init(){
	var val3 = '<%=v_yyyy11%>';
	var val4 = '<%=v_code_bran11%>';
	var val5 = '<%=v_code_certifi11%>';
	var val6 = '<%=v_code_operation11%>';
	var val7 = '<%=v_detcode11%>';
	var val8 = '<%=v_oper_name11%>';
// 	JUMIN_DEL
<%-- 	var val9 = '<%=v_oper_no11%>'; --%>
	var val9 = '<%=v_oper_birth11%>';
	var val10="<%=v_oper_lic_no11%>";
	var val11="<%=v_attend_cnt11%>";
	var val12="<%=v_time_cnt11%>";
	var val13="<%=v_result_point11%>";
	
	if('<%=etk2%>'>0){
		alert("저장됐습니다.");
		
		var jc=eval(<%=j_test%>);

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

		//sForm.code_kind1.value 		= val2;
		sForm.yyyy1.value 			= val3;
		sForm.code_bran1.value 		= val4;
		sForm.edutest_name1.value   = val7;
		sForm.code_certifi1.value	= val5;
		sForm.code_operation1.value = val6;
		
		var season1=val7.substr(val7.length-1,1);
		/* if(season1=='1') document.getElementById("s1").checked=true;
		if(season1=='2') document.getElementById("s2").checked=true;
		if(season1=='3') document.getElementById("s3").checked=true;
		if(season1=='4') document.getElementById("s4").checked=true; */
		if(season1=='5') document.getElementById("s5").checked=true;
		if(season1=='6') document.getElementById("s6").checked=true;
		
		
		goSearch(sForm,0);
		
		sForm3.oper_name1.value		= val8;
		sForm3.oper_lic_no1.value	= val10;
// 		JUMIN_DEL
// 		sForm3.oper_no11.value		= val9.substr(0,6);
// 		sForm3.oper_no12.value		= val9.substr(6,7);
		sForm3.oper_birth1.value	= val9;
		sForm3.attend_cnt1.value	= val11;
		sForm3.time_cnt1.value		= val12;
		sForm3.result_point1.value  = val13; 
	}else{
		if('<%=j_eds%>'>0){
			var jc=eval(<%=j_test%>);

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
      height:'390',
      colNames: [ '횟수','구분','시험명','이름',
//                   JUMIN_DEL
//                   '주민번호',
                  '생년월일',
                  '면허번호','핸드폰','이메일','회원구분','출석일수','이수시간','이수점수','상태','시험점수','시험장소','증서발급'
                  ,'년도','지부','구분코드','대상자','합격가능점수','합격일수','합격시간','종류','순번','지부순번',
//                   JUMIN_DEL
//                   '주민번호1',
                  '접수번호','상태','증서발급코드값','회원코드1','키','시험명코드'],
      colModel: [
			{name:'operation_cnt',  	index:'operation_cnt', 		width: 30,editable: false, align: 'center'},		//횟수
   			{name:'season',    			index:'season',   			width: 30,editable: false, align: 'center'},		//학기
   			{name:'edutest_name',    	index:'edutest_name',   	width: 250,editable: false, align: 'center'},		//시험명
   			{name:'oper_name',      	index:'oper_name',     		width: 60,editable: false, align: 'center'},		//이름
//    			JUMIN_DEL
//    			{name:'oper_no',    		index:'oper_no',   			width: 100,editable: false, align: 'center', formatter:juminNo},		//주민번호
   			{name:'oper_birth',    		index:'oper_birth',   			width: 100,editable: false, align: 'center'},		//생년월일
   			{name:'oper_lic_no',     	index:'oper_lic_no',    	width: 60,editable: false, align: 'center'},		//면허번호
   			{name:'oper_hp',       		index:'oper_hp',      		width: 100,editable: false, align: 'center'},		//핸드폰	
   			{name:'oper_email',  		index:'oper_email', 		width: 180,editable: false, align: 'left'},			//이메일
   			{name:'person_yn',    		index:'person_yn',   		width: 120,editable: false, align: 'center'},		//회원구분
   			{name:'attend_cnt',      	index:'attend_cnt',     	width: 60,editable: false, align: 'center'},		//출석일수
   			{name:'time_cnt',      		index:'time_cnt',     		width: 60,editable: false, align: 'center'},		//이수시간
   			{name:'result_point',      	index:'result_point',     	width: 60,editable: false, align: 'center'},		//이수점수
   			{name:'resultstate',    	index:'resultstate',   		width: 100,editable: false, align: 'center'},		//상태텍스트값
   			{name:'result_point',     	index:'result_point',    	width: 60,editable: false, align: 'center', hidden:true},		//시험점수
   			{name:'operation_place',    index:'operation_place',    width: 200,editable: false, align: 'left'},			//시험장소
   			{name:'printkind',    		index:'printkind',   		width: 80,editable: false, align: 'center', hidden:true},		//증서발급텍스트값
   			{name:'yyyy',      			index:'yyyy',      			hidden:true},		//년도
   			{name:'code_bran',      	index:'code_bran',      	hidden:true},		//지부
   			{name:'code_certifi',      	index:'code_certifi',      	hidden:true},		//구분코드(교육및시험구분)
   			{name:'code_operation',     index:'code_operation',     hidden:true},		//대상자
   			{name:'finish_point',      	index:'finish_point',      	hidden:true},		//합격가능점수
   			{name:'finish_date',    	index:'finish_date',   		width: 80,editable: false, align: 'center', hidden:true},		//합격일수
   			{name:'finish_time',    	index:'finish_time',   		width: 80,editable: false, align: 'center', hidden:true},		//합격시간
   			{name:'code_kind',      	index:'code_kind',      	hidden:true},		//종류
   			{name:'code_seq',      		index:'code_seq',      		hidden:true},		//순번
   			{name:'bran_seq',      		index:'bran_seq',      		hidden:true},		//지부순번
//    			{name:'oper_no1',      		index:'oper_no1',       	hidden:true},		//주민번호 히든값
   			{name:'receipt_no',      	index:'receipt_no',      	hidden:true},		//접수번호
   			{name:'result_state',    	index:'result_state',   	hidden:true},		//상태
   			{name:'print_kind',    		index:'print_kind',   		hidden:true},		//증서발급코드값
   			{name:'person_yn1',      	index:'person_yn1',       	hidden:true},		//회원코드1
   			{name:'receipt_no1',      	index:'receipt_no1',       	hidden:true},		//회원코드1
   			{name:'detcode',      		index:'detcode',       		hidden:true}		//시험명코드
   		
   			    ],
   		    rowNum:15,
   		  //  sortname: 'receipt_dt',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			gridview: true,
 			caption: '결과등록',
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

function powerinit(){

	var userpowerm1=eval(<%=userpowerm1%>);
	var meprogid = "302";
	var bcheck = "N";

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

// JUMIN_DEL
// function juminNo(cellvalue, options, rowObject ){
// 	var jumin=cellvalue.substring(0,6)+"*******";
// 	return jumin;
// }


function goSearch(form,intPage){
	/* 검색버튼 클릭시 테이블에서 그리드로 올리는 value 값 */
	 var yyyy1					= sForm.yyyy1.value;											//년도
	 var code_bran1				= sForm.code_bran1.value;										//지부(교육주최)(key)
	 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
	 var season1 				= "";															//학기
	 
	 /* if(sForm.s1.checked		== true )	season1 = "1";								
	 else if(sForm.s2.checked	== true )	season1	= "2";			
	 else if(sForm.s3.checked	== true )	season1	= "3";			
	 else if(sForm.s4.checked	== true )	season1	= "4"; */
	 if(sForm.s5.checked       == true )   season1 = "5";                              
     else if(sForm.s6.checked   == true )   season1 = "6";
	 else  season1="";
	 	 
	 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
	 var edutest_name1			= escape(encodeURIComponent(sForm.edutest_name1.value));		//시험명
	 var code_operation1 		= sForm.code_operation1.value;									//대상자
	 var print_kind1 			= sForm.print_kind1.value;										//증서발급
	 var finish_point1 			= sForm.finish_point1.value;									//합격가능점수 
	 var finish_time1			= sForm.finish_time1.value;										//합격가능이수시간
	 var finish_date1			= sForm.finish_date1.value;										//합격가능이수일수
	 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
	 
	 var oper_name1 			= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
// 	 JUMIN_DEL
// 	 var oper_no1 				= sForm3.oper_no11.value+sForm3.oper_no12.value;				//주민번호
	 var oper_birth1 				= sForm3.oper_birth1.value;				//생년월일
	 var oper_lic_no1 			= sForm3.oper_lic_no1.value;									//면허번호
	 var attend_cnt1 			= sForm3.attend_cnt1.value;										//출석일수
	 var result_state1 			= sForm3.result_state1.value;									//상태
	 var result_point1 			= sForm3.result_point1.value;									//시험점수
	 var time_cnt1				= sForm3.time_cnt1.value;										//이수시간							
	 //히든으로 가지고 있어야 할값
	 var oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
	 var oper_email1			= sForm.oper_email1.value;										//이메일
	 var person_yn1				= sForm.person_yn1.value;										//회원구분			
	 var code_kind1				= sForm.code_kind1.value;										//종류(key)
	 var code_seq1				= sForm.code_seq1.value;										//순번(key)
	 var bran_seq1				= sForm.bran_seq1.value;										//지부순번(key)
	 var receipt_no1			= sForm.receipt_no1.value;										//접수번호(key)
	 
 $('#list').jqGrid('clearGridData');
   jQuery("#list").setGridParam({url:"license.do?method=resultSendList&yyyy1="+yyyy1+"&code_bran1="+code_bran1+"&code_certifi1="+code_certifi1+"&season1="+season1
		   +"&operation_cnt1="+operation_cnt1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1+"&print_kind1="+print_kind1
   		+"&finish_point1="+finish_point1+"&finish_time1="+finish_time1+"&finish_date1="+finish_date1+"&operation_place1="+operation_place1+"&oper_name1="+oper_name1
//    		JUMIN_DEL
//    		+"&oper_no1="+oper_no1
   		+"&oper_birth1="+oper_birth1
   		+"&oper_lic_no1="+oper_lic_no1+"&attend_cnt1="+attend_cnt1+"&result_state1="+result_state1+"&result_point1="+result_point1+"&time_cnt1="+time_cnt1
   		+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&person_yn1="+person_yn1+"&code_kind1="+code_kind1+"&code_seq1="+code_seq1+"&bran_seq1="+bran_seq1
   		+"&receipt_no1="+receipt_no1}
   		).trigger("reloadGrid");
   
	rowSelected = false;
   
}

var rowSelected = false;
function goSelect(rowid,iCol){
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr != null ) {
		var list = $("#list").getRowData(gr);
				
		/*그리드에서 select시에 테이블로 출력될 value값들  */
		document.sForm.yyyy1.value     						= list.yyyy;			//시험년도
		document.sForm.code_bran1.value     				= list.code_bran;		//주최
		document.sForm.code_certifi1.value     				= list.code_certifi;	//구분
		
		if(list.season == '0') {													//학기
			document.getElementById("s0").value = '0';
		}else if(list.season == '1'){
			document.getElementById("s1").value = '1';
			//document.getElementById("s1").checked = 'true';
		}else if(list.season == '2'){
			document.getElementById("s2").value = '2';
			//document.getElementById("s2").checked = 'true';
		}else if(list.season == '3'){
			document.getElementById("s3").value = '3';
			//document.getElementById("s3").checked = 'true';
		}else if(list.season == '4'){
			document.getElementById("s4").value = '4';
			//document.getElementById("s4").checked = 'true';
		}else if(list.season == '5'){
            document.getElementById("s5").checked = 'true';
		}else if(list.season == '6'){
            document.getElementById("s6").checked = 'true';
		} 

		document.sForm.operation_cnt1.value     			= list.operation_cnt;	//횟수
		document.sForm.code_operation1.value     			= list.code_operation;	//대상자
		document.sForm.edutest_name1.value     				= list.detcode;	//시험명
		document.sForm.print_kind1.value     				= list.print_kind;		//증서발급
		document.sForm.finish_point1.value     				= list.finish_point;	//합격가능점수
		document.sForm.finish_time1.value     				= list.finish_time;		//합격가능시간
		document.sForm.finish_date1.value     				= list.finish_date;		//합격가능일수
		document.sForm.operation_place1.value     			= list.operation_place;	//시험장소
		
		document.sForm3.oper_name1.value     				= list.oper_name;		//이름
// 		JUMIN_DEL
// 		document.sForm3.oper_no11.value      				= list.oper_no.substring(0,6);				//주민번호
// 		document.sForm3.oper_no12.value      				= list.oper_no1.substring(6,13);			//주민번호
		document.sForm3.oper_birth1.value     				= list.oper_birth;		//생년월일
		document.sForm3.oper_lic_no1.value     				= list.oper_lic_no;		//면허번호
		document.sForm3.attend_cnt1.value     				= list.attend_cnt;		//출석
		document.sForm3.result_point1.value     			= list.result_point;	//시험점수
		document.sForm3.time_cnt1.value		     			= list.time_cnt;		//이수시간
		document.sForm3.result_state1.value     			= list.result_state;	//상태
		//히든값
		document.sForm.code_kind1.value     				= list.code_kind;		//종류(key)
		document.sForm.code_seq1.value     					= list.code_seq;		//순번(key)
		document.sForm.bran_seq1.value     					= list.bran_seq;		//지부순번(key)
		document.sForm.receipt_no1.value     				= list.receipt_no;		//접수번호(key)
		document.sForm.point_manage1.value     				= list.point_manage;		//point_manage
				
		if(list.receipt_no1 != ""){
			document.sForm.point_manage1.value    		= "U";		//순번(key) + 입력수정여부
		}else{
			document.sForm.point_manage1.value    		= "I";		//순번(key) + 입력수정여부
		}
		
		rowSelected = true;

	}
} 

function goSave(){
	/* search 에서 담아온 항목 이랑 같게 한다 */
	 var yyyy1						= sForm.yyyy1.value;					//년도(필수 체크부분)
	 var code_bran1					= sForm.code_bran1.value;				//지부(교육주최)(필수 체크부분)
	 var code_certifi1 				= sForm.code_certifi1.value;			//구분코드(교육및시험구분)
	 var detcode1 					= sForm.edutest_name1.value; //종류,구분,순번
	 var season1 				= "";
	 
	 /* if(sForm.s1.checked		== true )	season1 ="1";			//학기
	 else if(sForm.s2.checked	== true )	season1 ="2";
	 else if(sForm.s3.checked	== true )	season1 ="3";
	 else if(sForm.s4.checked	== true )	season1 ="4"; */
	 if(sForm.s5.checked       == true )   season1 ="5";
	 else if(sForm.s6.checked  == true )   season1 ="6";

	 var operation_cnt1 			= sForm.operation_cnt1.value;			//횟수
	 var edutest_name1				= sForm.edutest_name1.value;			//시험명
	 var code_operation1			= sForm.code_operation1.value;			//시행대상
	 var print_kind1 				= sForm.print_kind1.value;				//증서발급
	 var finish_point1   			= sForm.finish_point1.value; 			//합격가능점수
	 
	 var finish_time1 				= sForm.finish_time1.value;				//이수시간
	 var finish_date1 				= sForm.finish_date1.value;				//이수일수
	 var operation_place1 			= sForm.operation_place1.value;			//시험장소
	 var oper_name1   				= sForm3.oper_name1.value;				//이름
// 	 JUMIN_DEL
// 	 var oper_no1 					= sForm3.oper_no11.value+sForm3.oper_no12.value;				//주민번호
	 var oper_birth1 				= sForm3.oper_birth1.value;			//생년월일
	 var oper_lic_no1 				= sForm3.oper_lic_no1.value;			//면허번호
	 var attend_cnt1 				= sForm3.attend_cnt1.value;				//출석일수
	 var result_state1 				= sForm3.result_state1.value;			//상태
	 var time_cnt1 					= sForm3.time_cnt1.value;				//이수시간
	 var result_point1 				= sForm3.result_point1.value;			//시험점수
	 var oper_hp1 					= sForm.oper_hp1.value;					//핸드폰
	 var oper_email1				= sForm.oper_email1.value;				//이메일
	 var person_yn1					= sForm.person_yn1.value;				//회원구분
	 var code_kind1					= sForm.code_kind1.value;				//종류
	 var code_seq1					= sForm.code_seq1.value;				//순번
	 var bran_seq1					= sForm.bran_seq1.value;				//지부순번
	 var receipt_no1				= sForm.receipt_no1.value;				//접수번호
	 var point_manage1				= sForm.point_manage1.value;			//point_manage1
	 //alert(attend_cnt1);
	 
	 if(detcode1==""){
		 alert("시험명을 선택하지 않으시면 등록하실 수 없습니다.");
		 return;
	 }

	 if(attend_cnt1.trim()+result_point1.trim()+time_cnt1.trim() == ""){
			//alert(attend_cnt1.trim()+result_point1.trim()+time_cnt1.trim());
		 	alert("출석일수, 시험점수 , 이수시간이 비어있으면 등록하실 수 없습니다.");
			sForm3.attend_cnt1.value = "";
			sForm3.result_point1.value = "";
			sForm3.time_cnt1.value = "";
		    return;
		}
 
		/*action  licenseResultSave 로 보내는 method 항목일치*/
		//alert(document.sForm.point_manage1.value);
	    	document.sForm.action = "license.do?method=licenseResultSave&yyyy1="+yyyy1+"&code_bran1="+code_bran1+"&code_certifi1="+code_certifi1
	    	+"&season1="+season1+"&operation_cnt1="+operation_cnt1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1+"&print_kind1="+print_kind1
	    	+"&finish_point1="+finish_point1+"&finish_time1="+finish_time1+"&finish_date1="+finish_date1+"&operation_place1="+operation_place1
	    	+"&oper_name1="+oper_name1
// 	    	JUMIN_DEL
// 	    	+"&oper_no1="+oper_no1
	    	+"&oper_birth1="+oper_birth1
	    	+"&oper_lic_no1="+oper_lic_no1+"&attend_cnt1="+attend_cnt1+"&result_state1="+result_state1+"&time_cnt1="+time_cnt1
	    	+"&result_point1="+result_point1+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&person_yn1="+person_yn1+"&code_kind1="+code_kind1
	    	+"&code_seq1="+code_seq1+"&bran_seq1="+bran_seq1+"&receipt_no1="+receipt_no1+"&detcode1="+detcode1+"&point_manage1="+point_manage1;   	
	    	
	    	document.sForm.submit();  
		return;
	}

//결과등록 엑셀 저장   
function excelDown(){
	
	if(rowSelected){
			alert("엑셀저장을 하시려면 검색을 다시 실행해 주세요.");
			return;
		}
	var param = "";
	
// 	if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;				//년도
// 	if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;		//구분코드(자격증구분)
// 	if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;		//지부(교육주최)
// 	if(sForm.code_kind1.value		!= "")	param+="&code_kind1="		+sForm.code_kind1.value;		//교육종류
// 	if(sForm.edutest_name1.value	!= "")	param+="&edutest_name1="	+sForm.edutest_name1.value;		//내용
// 	//학기
// 		 if(sForm.season1.s1.checked		== true )	param+="&season1=" + "1" ;								
// 		 else if(sForm.season1.s2.checked	== true )	param+="&season1="	+ "2" ;			
// 		 else if(sForm.season1.s3.checked	== true )	param+="&season1="	+ "3" ;			
// 		 else if(sForm.season1.s4.checked	== true )	param+="&season1="	+ "4" ;			
	
		 var yyyy1					= sForm.yyyy1.value;											//년도
		 var code_bran1				= sForm.code_bran1.value;										//지부(교육주최)(key)
		 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
		 var season1 				= "";															//학기
		 
		 /* if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4"; */
		 if(sForm.s5.checked      == true )   season1 = "5";                              
         else if(sForm.s6.checked   == true )   season1 = "6";
		 else  season1="";
		 	 
		 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
		 var edutest_name1			= escape(encodeURIComponent(sForm.edutest_name1.value));		//시험명
		 var code_operation1 		= sForm.code_operation1.value;									//대상자
		 var print_kind1 			= sForm.print_kind1.value;										//증서발급
		 var finish_point1 			= sForm.finish_point1.value;									//합격가능점수 
		 var finish_time1			= sForm.finish_time1.value;										//합격가능이수시간
		 var finish_date1			= sForm.finish_date1.value;										//합격가능이수일수
		 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
		 
		 var oper_name1 			= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
		 var oper_birth1 				= sForm3.oper_birth1.value;				//생년월일
		 var oper_lic_no1 			= sForm3.oper_lic_no1.value;									//면허번호
		 var attend_cnt1 			= sForm3.attend_cnt1.value;										//출석일수
		 var result_state1 			= sForm3.result_state1.value;									//상태
		 var result_point1 			= sForm3.result_point1.value;									//시험점수
		 var time_cnt1				= sForm3.time_cnt1.value;										//이수시간							
		 //히든으로 가지고 있어야 할값
		 var oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
		 var oper_email1			= sForm.oper_email1.value;										//이메일
		 var person_yn1				= sForm.person_yn1.value;										//회원구분			
		 var code_kind1				= sForm.code_kind1.value;										//종류(key)
		 var code_seq1				= sForm.code_seq1.value;										//순번(key)
		 var bran_seq1				= sForm.bran_seq1.value;										//지부순번(key)
		 var receipt_no1			= sForm.receipt_no1.value;										//접수번호(key)
		 
		 param+=""
		 +"&yyyy1="+yyyy1
		 +"&code_bran1="+code_bran1
		 +"&code_certifi1="+code_certifi1
		 +"&season1="+season1
		 +"&operation_cnt1="+operation_cnt1
		 +"&edutest_name1="+edutest_name1
		 +"&code_operation1="+code_operation1
		 +"&print_kind1="+print_kind1
		 +"&finish_point1="+finish_point1
		 +"&finish_time1="+finish_time1
		 +"&finish_date1="+finish_date1
		 +"&operation_place1="+operation_place1
		 +"&oper_name1="+oper_name1
		 +"&oper_birth1="+oper_birth1
		 +"&oper_lic_no1="+oper_lic_no1
		 +"&attend_cnt1="+attend_cnt1
		 +"&result_state1="+result_state1
		 +"&result_point1="+result_point1
		 +"&time_cnt1="+time_cnt1
		 +"&oper_hp1="+oper_hp1
		 +"&oper_email1="+oper_email1
		 +"&person_yn1="+person_yn1
		 +"&code_kind1="+code_kind1
		 +"&code_seq1="+code_seq1
		 +"&bran_seq1="+bran_seq1
		 +"&receipt_no1="+receipt_no1;

		 param+="&register1="+"<%=g_name%>";
//	alert(param);
	var url="license.do?method=pers_check2"+param;
	window.open(url,"pers_check2","width=400, height=500, left=480, top=200,scrollbars=yes");	
	 
		
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
		
		if(rowdata.oper_email.length>0){
			if(param.length > 0) param+= ",";
// 			param += rowdata.oper_email;
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
 		if(rowSelected){
 			alert("전체메일 발송을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
		param+="ALL";
		
		 var yyyy1					= sForm.yyyy1.value;											//년도
		 var code_bran1				= sForm.code_bran1.value;										//지부(교육주최)(key)
		 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
		 var season1 				= "";															//학기
		 
		 /* if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4"; */
		 if(sForm.s5.checked      == true )   season1 = "5";                              
         else if(sForm.s6.checked   == true )   season1 = "6";
		 else  season1="";
		 	 
		 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
		 var edutest_name1			= escape(encodeURIComponent(sForm.edutest_name1.value));		//시험명
		 var code_operation1 		= sForm.code_operation1.value;									//대상자
		 var print_kind1 			= sForm.print_kind1.value;										//증서발급
		 var finish_point1 			= sForm.finish_point1.value;									//합격가능점수 
		 var finish_time1			= sForm.finish_time1.value;										//합격가능이수시간
		 var finish_date1			= sForm.finish_date1.value;										//합격가능이수일수
		 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
		 
		 var oper_name1 			= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
		 var oper_birth1 				= sForm3.oper_birth1.value;				//생년월일
		 var oper_lic_no1 			= sForm3.oper_lic_no1.value;									//면허번호
		 var attend_cnt1 			= sForm3.attend_cnt1.value;										//출석일수
		 var result_state1 			= sForm3.result_state1.value;									//상태
		 var result_point1 			= sForm3.result_point1.value;									//시험점수
		 var time_cnt1				= sForm3.time_cnt1.value;										//이수시간							
		 //히든으로 가지고 있어야 할값
		 var oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
		 var oper_email1			= sForm.oper_email1.value;										//이메일
		 var person_yn1				= sForm.person_yn1.value;										//회원구분			
		 var code_kind1				= sForm.code_kind1.value;										//종류(key)
		 var code_seq1				= sForm.code_seq1.value;										//순번(key)
		 var bran_seq1				= sForm.bran_seq1.value;										//지부순번(key)
		 var receipt_no1			= sForm.receipt_no1.value;										//접수번호(key)
		 
		 param+=""
		 +"&yyyy1="+yyyy1
		 +"&code_bran1="+code_bran1
		 +"&code_certifi1="+code_certifi1
		 +"&season1="+season1
		 +"&operation_cnt1="+operation_cnt1
		 +"&edutest_name1="+edutest_name1
		 +"&code_operation1="+code_operation1
		 +"&print_kind1="+print_kind1
		 +"&finish_point1="+finish_point1
		 +"&finish_time1="+finish_time1
		 +"&finish_date1="+finish_date1
		 +"&operation_place1="+operation_place1
		 +"&oper_name1="+oper_name1
		 +"&oper_birth1="+oper_birth1
		 +"&oper_lic_no1="+oper_lic_no1
		 +"&attend_cnt1="+attend_cnt1
		 +"&result_state1="+result_state1
		 +"&result_point1="+result_point1
		 +"&time_cnt1="+time_cnt1
		 +"&oper_hp1="+oper_hp1
		 +"&oper_email1="+oper_email1
		 +"&person_yn1="+person_yn1
		 +"&code_kind1="+code_kind1
		 +"&code_seq1="+code_seq1
		 +"&bran_seq1="+bran_seq1
		 +"&receipt_no1="+receipt_no1;
	}
	
 	param+="&paramfrom=ResultRegistration&addr_infos="+addr_infos;
	//alert("메일발송 = "+param);
	
	window.open('memberBasic.do?method=eMail&toAddr='+param,"oper_email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
}

/* 쪽지 발송 조건  */
function notePad(form){	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
	var rowdata=new Array();
 	var person_yn1="";
 	var oper_name="";
 	
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);

		if(rowdata.person_yn1.length>0) {
			if(person_yn1.length > 0) person_yn1+= ",";	//code_pers
			person_yn1 += rowdata.person_yn1;
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
 		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;					//년도
		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;			//구분코드(자격증구분)
		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;			//지부(교육주최)
		if(sForm.edutest_name1.value	!= "")	param+="&edutest_name1="	+sForm.edutest_name1.value;			//시험명
		
		/* if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4"; */
		if(sForm.s5.checked       == true )   season1 = "5";                              
        else if(sForm.s6.checked   == true )   season1 = "6";
		else  season1="";
		if(season1			!= "")	param+="&season1="			+season1;							//학기
		
		if(sForm3.oper_name1.value		!= "")	param+="&oper_name1="		+sForm3.oper_name1.value;			//이름
// 		JUMIN_DEL
// 		if(sForm3.oper_no1.value		!= "")	param+="&oper_no1="			+sForm3.oper_no11.value+sForm3.oper_no12.value; //주민번호
		if(sForm3.oper_birth1.value		!= "")	param+="&oper_birth1="		+sForm3.oper_birth1.value;			//생년월일
//		alert(season1);  
		//엑션
//		alert("쪽지발송 전체 = "+param);
		var url="license.do?method=sendnotePadAll1"+param;
		window.open(url,"notePad","width=370, height=500, left=280, top=80");	
		
	}else{
//선텍된 항목만 보내기 스트러쳐 등록, 엑션 생성 ,jsp파일 붙이기
		oper_name = escape(encodeURIComponent(oper_name)); 

//		alert("쪽지발송 조건 = "+oper_name+"  person_yn1 = "+person_yn1);
		var url="license.do?method=sendnotePad1&code_pers="+person_yn1+"&pers_name="+oper_name;
		window.open(url,"notePad","width=370, height=500, left=280, top=80");	
	}
	
}

/* 문자 발송 조건  */
function smssand(form){	
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 문자를 발송 할 수 없습니다.");
		return;
	}
	
	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');	
 	var rowdata=new Array();
 	var dm_pers_code="";
 	var pers_name="";
 	var pers_hp="";
 	var hp_infos = "";
  	
  	//개별선택
	for(var i=0;i<rownum.length;i++){
		rowdata= 	$("#list").getRowData(rownum[i]);
// 		if(i==0){
// 			dm_pers_code   =  rowdata.person_yn1;
// 			pers_hp		   =  rowdata.oper_hp;
// 			pers_name	   =  rowdata.oper_name;
// 		}else{
// 			dm_pers_code   =  dm_pers_code+","+rowdata.person_yn1;
// 			pers_hp	       =  pers_hp+","+rowdata.oper_hp;
// 			pers_name      =  pers_name+","+rowdata.oper_name;
// 		}
		
		if(rowdata.oper_hp.length>0) {
			if(pers_hp.length > 0){ pers_hp+= ","; pers_name+=","; dm_pers_code+=",";}	
			dm_pers_code += rowdata.person_yn1;
			pers_hp += rowdata.oper_hp;
			pers_name += escape(encodeURIComponent(rowdata.oper_name));
			
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
 		if(rowSelected){
 			alert("전체문자 발송을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
 		
		//여기다 전체 검색(검색과 같은 루트를 탄다.)
		
//조건 검색에 쓰는 조건값만 기입
		var param = "";
		
		
// 		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;					//년도
// 		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;			//구분코드(자격증구분)
// 		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;			//지부(교육주최)
// 		if(sForm.edutest_name1.value	!= "")	param+="&edutest_name1="	+sForm.edutest_name1.value;			//시험명
		
// 		if(sForm.season1.s1.checked		== true )	season1 = "1";								
// 		 else if(sForm.season1.s2.checked	== true )	season1	= "2";			
// 		 else if(sForm.season1.s3.checked	== true )	season1	= "3";			
// 		 else if(sForm.season1.s4.checked	== true )	season1	= "4";			
// 		 else  season1="";

// 		if(sForm3.oper_name1.value		!= "")	param+="&oper_name1="		+sForm3.oper_name1.value;			//이름
// // 		JUMIN_DEL
// // 		if(sForm3.oper_no11.value != "" && sForm3.oper_no12.value != "")	param+="&oper_no1="		+sForm3.oper_no11.value+""+sForm3.oper_no12.value;			//주민번호 
// 		if(sForm3.oper_birth1.value		!= "")	param+="&oper_birth1="		+sForm3.oper_birth1.value;			//생년월일
		
		 var yyyy1					= sForm.yyyy1.value;											//년도
		 var code_bran1				= sForm.code_bran1.value;										//지부(교육주최)(key)
		 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
		 var season1 				= "";															//학기
		 
		 /* if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4"; */
		 if(sForm.s5.checked      == true )   season1 = "5";                              
         else if(sForm.s6.checked   == true )   season1 = "6";
		 else  season1="";
		 	 
		 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
		 var edutest_name1			= escape(encodeURIComponent(sForm.edutest_name1.value));		//시험명
		 var code_operation1 		= sForm.code_operation1.value;									//대상자
		 var print_kind1 			= sForm.print_kind1.value;										//증서발급
		 var finish_point1 			= sForm.finish_point1.value;									//합격가능점수 
		 var finish_time1			= sForm.finish_time1.value;										//합격가능이수시간
		 var finish_date1			= sForm.finish_date1.value;										//합격가능이수일수
		 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
		 
		 var oper_name1 			= escape(encodeURIComponent(sForm3.oper_name1.value)); 			//이름
		 var oper_birth1 				= sForm3.oper_birth1.value;				//생년월일
		 var oper_lic_no1 			= sForm3.oper_lic_no1.value;									//면허번호
		 var attend_cnt1 			= sForm3.attend_cnt1.value;										//출석일수
		 var result_state1 			= sForm3.result_state1.value;									//상태
		 var result_point1 			= sForm3.result_point1.value;									//시험점수
		 var time_cnt1				= sForm3.time_cnt1.value;										//이수시간							
		 //히든으로 가지고 있어야 할값
		 var oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
		 var oper_email1			= sForm.oper_email1.value;										//이메일
		 var person_yn1				= sForm.person_yn1.value;										//회원구분			
		 var code_kind1				= sForm.code_kind1.value;										//종류(key)
		 var code_seq1				= sForm.code_seq1.value;										//순번(key)
		 var bran_seq1				= sForm.bran_seq1.value;										//지부순번(key)
		 var receipt_no1			= sForm.receipt_no1.value;										//접수번호(key)
		 
		 param+=""
		 +"&yyyy1="+yyyy1
		 +"&code_bran1="+code_bran1
		 +"&code_certifi1="+code_certifi1
		 +"&season1="+season1
		 +"&operation_cnt1="+operation_cnt1
		 +"&edutest_name1="+edutest_name1
		 +"&code_operation1="+code_operation1
		 +"&print_kind1="+print_kind1
		 +"&finish_point1="+finish_point1
		 +"&finish_time1="+finish_time1
		 +"&finish_date1="+finish_date1
		 +"&operation_place1="+operation_place1
		 +"&oper_name1="+oper_name1
		 +"&oper_birth1="+oper_birth1
		 +"&oper_lic_no1="+oper_lic_no1
		 +"&attend_cnt1="+attend_cnt1
		 +"&result_state1="+result_state1
		 +"&result_point1="+result_point1
		 +"&time_cnt1="+time_cnt1
		 +"&oper_hp1="+oper_hp1
		 +"&oper_email1="+oper_email1
		 +"&person_yn1="+person_yn1
		 +"&code_kind1="+code_kind1
		 +"&code_seq1="+code_seq1
		 +"&bran_seq1="+bran_seq1
		 +"&receipt_no1="+receipt_no1;

		 
		var isSelect = "Y";		
		pers_hp = "All";							
 		  
 		//엑션
 		param+="&from=license2";
		//엑션
//		alert("문자발송 전체 = "+param);
		var url="license.do?method=smssandAll"+param+"&pers_hp="+pers_hp+"&isSelect="+isSelect;
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
		
	}else{
		var param="&from=license_1"+"&hp_infos="+hp_infos;
//		alert("문자발송 조건 = "+param);
		var url="license.do?method=smssand&dm_pers_code="+dm_pers_code+"&pers_hp="+pers_hp+"&pers_name="+pers_name+param;
//		var url="license.do?method=smssand1&param="+param;
		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
	}
}

function upExcel(){
	window.open("license.do?method=upresultSendList","lic_no","scrollbar=no,resizable=no,toolbar=no,width=350,height=150,left=20,top=29,status=no");
}



function setName(){
	
	var yyyy=document.getElementById("yyyy1").value;				//교육년도 값
	var code_bran=document.getElementById("code_bran1").value;		//교육주최 값
	var code_certifi1=document.getElementById("code_certifi1").value;		//교육종류 값
	
// 	if ( yyyy == "" || code_bran == "" || code_certifi1 == ""  ){		//교육년도와 교육주최 교육종류
	if ( yyyy == "" || code_certifi1 == ""  ){		//교육년도와 교육주최 교육종류
		setName1("선택","");
		return;
	}
	/*action send 로 보내는 폼  */
	document.sForm.yyyy1.value = yyyy;
	document.sForm.action="license.do?method=selectEtestN&yyyy1="+yyyy+"&code_bran1="+code_bran+"&code_certifi1="+code_certifi1;
	document.sForm.submit();
}	

function setName1(name,value){
	var edutest_name1=document.getElementById("edutest_name1");
	var opts=edutest_name1.options;
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
	edutest_name1[0]=new Option(name,value);
}

function setEduN(val){
	//alert(val);
	var jc=eval(<%=j_test%>);
	for(var i=0;i<jc.length;i++){
		if(jc[i].detcode==val){
			sForm.operation_place1.value=jc[i].operation_place;
			sForm.operation_cnt1.value=jc[i].operation_cnt;
			var season1=jc[i].season;
			/* if(season1=='1') document.getElementById("s1").checked=true;
			if(season1=='2') document.getElementById("s2").checked=true;
			if(season1=='3') document.getElementById("s3").checked=true;
			if(season1=='4') document.getElementById("s4").checked=true; */
			if(season1=='5') document.getElementById("s5").checked=true;
			if(season1=='6') document.getElementById("s6").checked=true;
			sForm.finish_point1.value=jc[i].finish_point;
			sForm.finish_time1.value=jc[i].finish_time;
			sForm.print_kind1.value=jc[i].print_kind;
			sForm.finish_date1.value=jc[i].finish_date;
			
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

function goClear(){
	sForm3.reset();
}
</script>  
 </head>

 <body onload="init();">
	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="license.do?method=licenseSend">자격증</a></li>		
		<li>&gt;</li>
		<li class="NPage">결과등록</li>
	  </ul>	
	  <form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form> 
	  <div class="cTabmenu_01"> 
	  <form id="list1" name="sForm" method="post">
					   <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
						<input type="hidden" name="receipt_no1" 		id="receipt_no1" value=""/> 
						<input type="hidden" name="bran_seq1" 			id="bran_seq1" value=""/> 
				        <input type="hidden" name="code_seq1" 			id="code_seq1" value=""/>
				        <input type="hidden" name="oper_hp1" 			id="oper_hp1" value=""/>
				        <input type="hidden" name="oper_email1" 		id="oper_email1" value=""/> 
				        <input type="hidden" name="person_yn1" 			id="person_yn1" value=""/>
				        <input type="hidden" name="code_kind1" 			id="code_kind1" value=""/>
				        <input type="hidden" name="printkind" 			id="printkind" value=""/>
				        <input type="hidden" name="oper_comp_name11" 	id="oper_comp_name11" value=""/>
				        <input type="hidden" name="oper_comp_add1_11" 	id="oper_comp_add1_11" value=""/>
				        <input type="hidden" name="code_post1" 			id="code_post1" value=""/>
				        <input type="hidden" name="point_manage1" 		id="point_manage1" value=""/>
				                 
		<table class="ctable_01" cellspacing="0" summary="자격증">
          <caption>결과등록</caption>            			 
             <tr>
               <td class="nobg">※&nbsp;&nbsp;자격증구분</td>
               <td class="nobg1">
               <select  id="code_certifi1" name="code_certifi1" onchange="javascript:setName();" >
	               <option value="">전체</option>	
	               <%
	                	String detCode5,detCName5=null;
	                	for(int i=0;i<licenseserch1.size();i++){
	                		detCode5=licenseserch1.get(i).get("code_certifi").toString();
	                		detCName5=licenseserch1.get(i).get("certifi_name").toString();
	                		if(!"9".equals(licenseserch1.get(i).get("code_certifi").toString())
			                	 &&!"0".equals(licenseserch1.get(i).get("code_certifi").toString())){
	                			if(etest1!=null&&detCode5.equals(etest1.get("code_certifi1").toString())){
	                				out.println("<option value='"+detCode5+"' selected>"+detCName5+"</option>");
	                			}else{
	                				out.println("<option value='"+detCode5+"'>"+detCName5+"</option>");
	                			}
	                		}
	                	}
	               	%>     
               	</select>
                </td>
               <td class="nobg2">※&nbsp;&nbsp;시험년도</td>
               <td class="nobg1">
               <select  id="yyyy1" name="yyyy1"  onchange="javascript:setName();">	
               <option value="">전체</option>
  				 <%
				 	String detCode,detCName="";
			 	for(int i=0;i<licenseserch.size();i++){
				 		if("036".equals(licenseserch.get(i).get("groupcode").toString())){
                			detCode=licenseserch.get(i).get("detcode").toString();
                			detCName=licenseserch.get(i).get("detcodename").toString();
                			if(etest1!=null&&detCode.equals(etest1.get("yyyy1").toString())){
                				out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                			}
                			
                		}
				 	}
				 %>         	 
              	</select>
              	</td>                             	
              	<td class="nobg2">※&nbsp;&nbsp;주최</td>
               <td class="nobg1">
               <select  id="code_bran1" name="code_bran1"  style="width:150px" onchange="javascript:setName();">
               <option value="">전체</option>	
               		<%
				 	for(int i=0;i<licenseserch.size();i++){
				 		if("034".equals(licenseserch.get(i).get("groupcode").toString())){
                			detCode=licenseserch.get(i).get("detcode").toString();
                			detCName=licenseserch.get(i).get("detcodename").toString();
                			if(etest1!=null&&detCode.equals(etest1.get("code_bran1").toString())){
                				out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
                			}else{
                				out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                			}
                		}
				 	}
				 %>    
               	    </select>
               </td>
             	
             <td class="nobg2">대상자</td>
			   <td class="nobg1">
				   <select  id="name" name="code_operation1">
	                		<%				 	
					 	for(int i=0;i<licenseserch.size();i++){
					 		if("035".equals(licenseserch.get(i).get("groupcode").toString())){
	                			detCode=licenseserch.get(i).get("detcode").toString();
	                			detCName=licenseserch.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");
	                		}
					 	}
					 %>   
	               </select>			   
			   </td>
             
             </tr>
             
             <tr>
             <td class="alt1">※&nbsp;&nbsp;시험명</td>
             <td colspan="3" class="alt2">
               <select  name="edutest_name1"  id="edutest_name1" style="width:460px" onchange="javascript:setEduN(this.value)">
               	<option value="">선택</option>	
               </select>
               </td>
			   <td class="alt" >시험장소</td>
               	<td colspan="3" >
               		<input type="text" id="p" size="50" name="operation_place1" readonly class="readOnly1" onkeydown="ban(this);"/>
               </td>
			   </tr>
			  
			   <tr>
               <td class="alt1">※&nbsp;&nbsp;교육구분</td>
               <td >
              	   <input type="hidden" id="s0" value="0" />
              	   <input type="hidden" id="s1" value="1" />
              	   <input type="hidden" id="s2" value="2" />
              	   <input type="hidden" id="s3" value="3" />
              	   <input type="hidden" id="s4" value="4" />
              		
				   <div style="border:0px solid black; display:inline-block; text-align:left;">
				   <input type="radio" name="season1" id="s5" value="5" checked="checked" />
                   <label for="s1">검정과목1</label><br/>
				   <!-- <input type="radio" name="season1" id="s1" value="1" checked="checked" />
				   <label for="s1">1학기</label><br/>
				   <input type="radio" name="season1" id="s3" value="3" />
				   <label for="s3">집합교육</label> -->
				   </div>
				   <div style="border:0px solid red; display:inline-block; text-align:left;">
				   <input type="radio" name="season1" id="s6" value="6" />
                   <label for="s2">검정과목2</label><br/>
				   <!-- <input type="radio" name="season1" id="s2" value="2" />
				   <label for="s2">2학기</label><br/>
				   <input type="radio" name="season1" id="s4" value="4" />
				   <label for="s4">온라인교육</label> -->
				   </div>
			   </td>
			   <td class="alt">합격이수일수</td>
			   <td>
			   		<input type="text" id="finish_date1"  size="4" name="finish_date1" readonly class="readOnly1" onkeydown="ban(this);"/>
			   </td>			   
			    <td class="alt">합격이수점수</td>
                <td>
               		<input type="text" id="p" size="4" name="finish_point1" readonly class="readOnly1" onkeydown="ban(this);"/>
               </td>
			 	<td class="alt">합격이수시간</td>
			   <td>
			   		<input type="text" id="finish_time1"  size="4" name="finish_time1" readonly class="readOnly1" onkeydown="ban(this);"/>
			   </td>
			    
                
			 </tr>
			 <tr>
			 	<td class="alt1">증서발급</td>
               	<td>
               		<select  id="print_kind1" name="print_kind1" >	
                	 <% 
                	
                	for(int i=0;i<licenseserch.size();i++){
                		if("029".equals(licenseserch.get(i).get("groupcode").toString())){
                			detCode=licenseserch.get(i).get("detcode").toString();
                			detCName=licenseserch.get(i).get("detcodename").toString();
                			
                			if(!"0".equals(licenseserch.get(i).get("detcode").toString())
       		                	 &&!"1".equals(licenseserch.get(i).get("detcode").toString())
       		                	 &&!"2".equals(licenseserch.get(i).get("detcode").toString())
       		                	 &&!"4".equals(licenseserch.get(i).get("detcode").toString())){
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                			
                		}
                	}
                	}
               	  %>   
                 	</select>
                </td>
			 	<td class="alt">횟수</td>
			   <td>
			   		<input type="text" id="operation_cnt1"  size="4" name="operation_cnt1" readonly class="readOnly1" onkeydown="ban(this);"/>
			   </td>
			   <td class="alt"></td>
			   <td></td>
			    <td class="alt"></td>
			   <td></td>
			 </tr>
			 
			         </table>
        </form>
        
        <form id="list1" name="sForm3" method="post">
		<table class="ctable_04" cellspacing="0" summary="자격증">
          <caption>결과등록</caption>            			 
             <tr>
			   <td class="nobg" >※&nbsp;&nbsp;이름</td>
<!-- 			   JUMIN_DEL -->
<!--                <td class="nobg2">※&nbsp;&nbsp;주민번호</td> -->
               <td class="nobg2">※&nbsp;&nbsp;생년월일</td>
               <td class="nobg2">면허번호</td>
               <td class="nobg2">출석일수</td>
			   <td class="nobg2">시험점수</td>
			   <td class="nobg2">이수시간</td>
               <td class="nobg2">상태</td>				   			   
             </tr>             
			 <tr>
               <td class="alt2"><input type="text" id="p" size="15" name="oper_name1"/></td>
<!--                JUMIN_DEL -->
<!--                <td><input type="text" id="name" size="6" name="oper_no11" maxlength="6" onKeyUp="javascript:check_Int('sForm3','oper_no11')"/>-<input type="password" id="name" size="7" name="oper_no12" maxlength="6" onKeyUp="javascript:check_Int('sForm3','oper_no12')"/></td> -->
               <td><input type="text" id="oper_birth1" size="10" name="oper_birth1" onKeyUp="javascript:check_Int('sForm3','oper_birth1')"/></td>
               <td><input type="text" id="oper_lic_no1" size="15" name="oper_lic_no1" maxlength="6" onKeyUp="javascript:check_Int('sForm3','oper_lic_no1')" /></td>
               <td><input type="text" id="attend_cnt1" size="10" name="attend_cnt1" onKeyUp="javascript:check_Int('sForm3','attend_cnt1')"/></td>
			   <td><input type="text" id="result_point1" size="10" name="result_point1" onKeyUp="javascript:check_Int('sForm3','result_point1')"/></td>
			   <td><input type="text" id="time_cnt1" size="10" name="time_cnt1" onKeyUp="javascript:check_Int('sForm3','time_cnt1')"/></td>
			   <td>
			   		<select  id="name" name="result_state1" disabled>
	                <option value="">전체</option>
	                      <% 
	                	String detCode7,detCName7=null;
	                	for(int i=0;i<licenseserch.size();i++){
	                		 if("023".equals(licenseserch.get(i).get("groupcode").toString())){
	                			detCode7=licenseserch.get(i).get("detcode").toString();
	                			detCName7=licenseserch.get(i).get("tempcd1").toString();
	                			
	                			if(!"02".equals(licenseserch.get(i).get("detcode").toString())
               					&&!"03".equals(licenseserch.get(i).get("detcode").toString())
         		                &&!"11".equals(licenseserch.get(i).get("detcode").toString())){
	                			out.println("<option value="+detCode7+">"+detCName7+"</option>");
	                		 }
	                	}
	                	}
	               		 %>
	               		</select>
	                </td>
             </tr>			
        </table>
		<ul class="btnIcon_2" >		 
		  <li><a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a></li> 
		  <li><a href="javascript:goSave();"><img src="images/icon_save.gif"   onclick="" alt="저장" /></a></li>
		  <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>			 
		</ul>	  
</form>
 <br></br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post"></form>
<ul class="btnIcon_2" style="left:940px;">		 
<!-- 		  <li><a href="#"><img src="images/icon_s2.gif" onclick="notePad();"	alt="쪽지" /></a></li> -->
		  <!-- <li><a href="javascript:sendEmail();"><img src="images/icon_s3.gif"	onclick=""	alt="메일전송"	/></a></li>	
		  <li><a href="javascript:smssand();"><img src="images/icon_s4.gif"	onclick=""	alt="문자전송"	/></a></li> -->
		  <li><a href="javascript:upExcel();"><img src="images/icon_s16.gif" onclick="" alt="엑셀올리기"/></a></li>		 
		  <li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" alt="엑셀저장"/></a></li>	
		</ul>
 </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	
 
 </body> </html>
 <iframe name="frm" width="0" height="0" tabindex=-1></iframe>