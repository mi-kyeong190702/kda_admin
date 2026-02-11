<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.ant.educationlecture.dao.licenseDao"%>
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

<!-- 날짜 표시를 위한 스크립트   -->
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>  
 
<%

	Calendar calendar = Calendar.getInstance();
	java.util.Date date = calendar.getTime();

	String g_name = session.getAttribute("G_NAME").toString(); 
	String g_bran = session.getAttribute("G_BRAN").toString(); 
	JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
	String g_id = session.getAttribute("G_ID").toString(); 

	Map map = new HashMap();
	
	licenseDao dao = new licenseDao();
	StringBuffer yyyy = new StringBuffer();

 	String v_yyyy1=request.getParameter("yyyy1");
	String v_code_bran1=request.getParameter("code_bran1");
	String v_code_kind1=request.getParameter("code_kind1");
	
	List<Map> licenseserch = dao.licenseserch(map); 				
	List<Map> licenseserch1 = dao.licenseserch1(map); 

	Map 	  licensein	= (Map)request.getAttribute("licensein");
	List<Map> licensesand = (List<Map>)request.getAttribute("licensesand");  
	JSONArray j_licensesand=(JSONArray)request.getAttribute("j_licensesand");
	JSONArray j_test=(JSONArray)request.getAttribute("j_test");
	Map etest1 = (Map)request.getAttribute("etest1");
	
	int j_eds=0;
	if(j_test!=null){
		j_eds=j_test.size();
	}
	String v_yyyy11="";
	String v_code_bran11="";
	String v_code_certifi11="";
	int etk=0;
	if(etest1!=null){
		v_yyyy11=etest1.get("yyyy1").toString();
		v_code_bran11=etest1.get("code_bran1").toString();
		v_code_certifi11=etest1.get("code_certifi1").toString();
		etk=etest1.size();
	}
%>

  
<script type="text/javascript">
function init(){
	if('<%=etk%>'>0){
		document.getElementById("yyyy1").value='<%=v_yyyy11%>';				
		document.getElementById("code_bran1").value='<%=v_code_bran11%>';		
		document.getElementById("code_certifi1").value='<%=v_code_certifi11%>'; 
	}
	if('<%=j_eds%>'>0){
		var jc=eval(<%=j_test%>);
	 	var e_name=document.getElementById("edutest_name2");
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
      height:'500',
      colNames: [ '횟수','학기','내용','이름','면허번호',
//                   JUMIN_DEL
//                   '주민번호',
                  '생년월일',
                  '핸드폰','이메일','회원구분','출석','접수상태',
//                   '응시결과상태',
                  '대상자',
                  '시험장소','구분코드','년도','대상자코드','결제여부'
                  ,'교육주최','지부순번','종류','순번','접수번호','응시상태','회원코드','회원코드1'],
      colModel: [
			{name:'operation_cnt',  	index:'operation_cnt', 		width: 40,editable: false, align: 'center'},				//횟수
   			{name:'season',    			index:'season',   			width: 40,editable: false, align: 'center'},				//학기
   			{name:'edutest_name',       index:'edutest_name',     	width: 300,editable: false, align: 'left'},					//내용
   			{name:'oper_name',    		index:'oper_name',   		width: 80,editable: false, align: 'left'},					//이름
   			{name:'oper_lic_no',     	index:'oper_lic_no',    	width: 80,editable: false, align: 'center'},				//면허번호
//    			JUMIN_DEL
//    			{name:'oper_no',       		index:'oper_no',      		width: 100,editable: false, align: 'center'},				//주민번호
   			{name:'oper_birth',       		index:'oper_birth',      		width: 100,editable: false, align: 'center'},				//생년월일
   			{name:'oper_hp',  			index:'oper_hp', 			width: 100,editable: false, align: 'center'},				//핸드폰
   			{name:'oper_email',    		index:'oper_email',   		width: 200,editable: false, align: 'left'},					//이메일
   			{name:'person_yn',      	index:'person_yn',     		width: 120,editable: false, align: 'left'},					//회원구분
   			{name:'attend_cnt',    		index:'attend_cnt',   		width: 40,editable: false, align: 'center'},				//출석
   			{name:'operstate',      	index:'operstate',      	width: 80,editable: false, align: 'left'},			//상태
//    			{name:'resultstate',     	index:'resultstate',    	width: 80,editable: false, align: 'left'},					//텍스트값
   			{name:'codeoperation',     	index:'codeoperation',    	width: 80,editable: false, align: 'left'},					//텍스트값
   			{name:'operation_place',    index:'operation_place',    width: 200,editable: false, align: 'left'},					//시험장소
   			
   			{name:'code_certifi',      	index:'code_certifi',      	hidden:true},		//구분코드(교육및시험구분)(key)
   			{name:'yyyy',      			index:'yyyy',      			hidden:true},		//년도
   			{name:'code_operation',     index:'code_operation',     hidden:true},		//대상자코드
   			{name:'oper_state',      	index:'oper_state',      	hidden:true},		//결제여부
   			{name:'code_bran',      	index:'code_bran',      	hidden:true},		//교육주최(key)
   			{name:'bran_seq',      		index:'bran_seq',      		hidden:true},		//지부순번(key)
   			{name:'code_kind',      	index:'code_kind',      	hidden:true},		//종류(key)
   			{name:'code_seq',      		index:'code_seq',      		hidden:true},		//순번(key)
   			{name:'receipt_no',      	index:'receipt_no',      	hidden:true},		//접수번호(key)
   			{name:'result_state',      	index:'result_state',      	hidden:true},		//상태
   			
   			{name:'code_pers',      	index:'code_pers',      	hidden:true},		//회원코드
   			{name:'person_yn1',      	index:'person_yn1',       	hidden:true}		//회원코드1
   			
   			    ],
   			    
   		    rowNum:20,
   		    sortname: 'receipt_dt',
   			pager: '#pager2',
   			rocordpos: 'left',
            rownumbers: true,
            viewrecords: true,
 			multiselect: true,
 			//gridview: true,
 			caption: '시험별응시현황',
			altclass:'myAltRowClass'/* ,			
			onSelectRow: function(ids) {	
				//alert(ids);
				if(ids == null) {
					ids=0;
					if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
					{
						alert("컬럼을 정확히 선택해 주세요");
					}
				} else {
					//goSelect();
					if(jQuery("#list").jqGrid('getGridParam','records') >0 ) //전체 레코드가 0보다 많다면
					{						
						var id = jQuery("#list").jqGrid('getGridParam','selrow') ;
						var rowdata=jQuery("#list").jqGrid('getGridParam','selarrrow');						
						for(var i=0;i<rowdata.length;i++){
							if(rowdata[i]!=id){
								jQuery("#list").jqGrid('setSelection',rowdata[i]);
							}
						}
						goSelect();
					}				
				} 
			}*/
  
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
			/* for(var i=0;i<rowdata.length;i++){
				if(rowdata[i]!=id){
					$("#list").jqGrid('resetSelection');
					$("#list").jqGrid('setSelection',rowdata[i]);
				}
			} */
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
	var meprogid = "301";
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

 function goSearch(form,intPage){
		/* 검색버튼 클릭시 그리드로 올리는 value 값 */
		 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
		 
		 
//		 alert("구분코드=="+code_certifi1);
		 var yyyy1					= sForm.yyyy1.value;											//년도
		 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
		 var season1 				= "";															//학기
		 if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4";			
		 else  season1="";
		 
		 var code_operation1 		= sForm.code_operation1.value;									//대상자
		 var oper_state1 			= sForm.oper_state1.value;										//응시상태
		 var result_state1 			= sForm.result_state1.value;									//응시결과상태
		 var code_bran1 			= sForm.code_bran1.value;										//교육주최(key)
		 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
		 var edutest_name2 			= sForm.edutest_name2.value;									//내용
		 var oper_name1 			= escape(encodeURIComponent(sForm.oper_name1.value)); 			//이름
		 var oper_lic_no1 			= sForm.oper_lic_no1.value;										//면허번호
// 		 JUMIN_DEL
// 		 var oper_no1 				= sForm.oper_no1.value;											//주민번호
		 var oper_birth1 				= sForm.oper_birth1.value;											//생년월일
		 var oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
		 var oper_email1 			= sForm.oper_email1.value;										//이메일
		 var person_yn1 			= sForm.person_yn1.value;										//회원구분
		 var attend_cnt1 			= sForm.attend_cnt1.value;										//출석
		 var bran_seq1 				= sForm.bran_seq1.value;										//지부순번(key)
		 var code_kind1 			= sForm.code_kind1.value;										//종류(key)
		 var code_seq1 				= sForm.code_seq1.value;										//순번(key)
		 var receipt_no1 			= sForm.receipt_no1.value;										//접수번호(key)
	 $('#list').jqGrid('clearGridData');
	   jQuery("#list").setGridParam({url:"license.do?method=licenseSendList&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
	   		+"&operation_cnt1="+operation_cnt1+"&season1="+season1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1+"&result_state1="+result_state1
	   		+"&code_bran1="+code_bran1+"&operation_place1="+operation_place1+"&edutest_name1="+edutest_name2+"&oper_name1="+oper_name1
	   		+"&oper_lic_no1="+oper_lic_no1
// 	   		JUMIN_DEL
// 	   		+"&oper_no1="+oper_no1
	   		+"&oper_birth1="+oper_birth1
	   		+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&person_yn1="+person_yn1
	   		+"&attend_cnt1="+attend_cnt1+"&result_state1="+result_state1+"&bran_seq1="+bran_seq1+"&code_kind1="+code_kind1+"&code_seq1="+code_seq1
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
			document.sForm.code_certifi1.value     			= list.code_certifi;			//구분코드
//			alert("구분코드===>"+document.sForm.code_certifi1.value);
			document.sForm.yyyy1.value     					= list.yyyy;					//년도
			document.sForm.operation_cnt1.value     		= list.operation_cnt;			//횟수
//			document.sForm.season1.value     				= list.season1;					//학기
			
			if(list.season == '0') {
				document.getElementById("s0").value = '0';
			}else if(list.season == '1'){
				document.getElementById("s1").checked = 'true';
			}else if(list.season == '2'){
				document.getElementById("s2").checked = 'true';
			}else if(list.season == '3'){
				document.getElementById("s3").checked = 'true';
			}else if(list.season == '4'){
				document.getElementById("s4").checked = 'true';
			} 

			document.sForm.code_operation1.value     		= list.code_operation;			//대상자
			document.sForm.oper_state1.value     			= list.oper_state;				//결제여부
			document.sForm.result_state1.value     			= list.result_state;			//상태
			document.sForm.code_bran1.value     			= list.code_bran;				//교육주최
			document.sForm.operation_place1.value     		= list.operation_place;		//시험장소
			document.sForm.edutest_name1.value     			= list.edutest_name;			//내용
			document.sForm.oper_name1.value     			= list.oper_name;				//이름
			document.sForm.oper_lic_no1.value     			= list.oper_lic_no;			//면허번호
// 			JUMIN_DEL
// 			document.sForm.oper_no1.value     				= list.oper_no;				//주민번호
			document.sForm.oper_birth1.value     				= list.oper_birth;				//생년월일
			document.sForm.oper_hp1.value     				= list.oper_hp;				//핸드폰
			document.sForm.oper_email1.value     			= list.oper_email;				//이메일
			document.sForm.person_yn1.value     			= list.person_yn;				//회원구분
			document.sForm.attend_cnt1.value     			= list.attend_cnt;				//출석
			document.sForm.bran_seq1.value     				= list.bran_seq;				//지부순번
			document.sForm.code_kind1.value     			= list.code_kind;				//종류
			document.sForm.code_seq1.value     				= list.code_seq;				//순번
			document.sForm.receipt_no1.value     			= list.receipt_no;				//접수번호
			rowSelected = true;
		}
	} 
 //시험별응시현황 엑셀 저장
 function excelDown(){
	 
		if(rowSelected){
 			alert("엑셀저장을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
	 	var param = "";
	 
		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;		//구분코드(자격증구분)
	 	if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;				//년도
		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;		//지부(교육주최)
		if(sForm.result_state1.value	!= "")	param+="&result_state1="	+sForm.result_state1.value;		//응시결과상태
		if(sForm.oper_state1.value		!= "")	param+="&oper_state1="		+sForm.oper_state1.value;		//응시상태
										//학기
		 if(sForm.s1.checked		== true )	param+="&season1=" + "1" ;								
		 else if(sForm.s2.checked	== true )	param+="&season1="	+ "2" ;			
		 else if(sForm.s3.checked	== true )	param+="&season1="	+ "3" ;			
		 else if(sForm.s4.checked	== true )	param+="&season1="	+ "4" ;			
		
		if(sForm.edutest_name2.value !="") param+="&edutest_name1="		+sForm.edutest_name2.value;
		
		param+="&register1="+"<%=g_name%>";
//		alert(param);
		var url="license.do?method=pers_check"+param;
		window.open(url,"pers_check","width=400, height=500, left=480, top=200,scrollbars=yes");
	}
 
 /* 쪽지 발송 조건 작업 */
 function notePad(form){
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 쪽지를 발송 할 수 없습니다.");
		return;
	}
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
 		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="	+sForm.code_certifi1.value;			//구분코드(자격증구분)
  		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="			+sForm.yyyy1.value;					//년도
 		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="		+sForm.code_bran1.value;			//지부(교육주최)
 		if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4";			
		 else  season1="";
 		if(season1			!= "")	param+="&season1="			+season1;				//학기
 		
 		if(sForm.operation_cnt1.value	!= "")	param+="&operation_cnt1="	+sForm.operation_cnt1.value;		//횟수
 		if(sForm.code_operation1.value	!= "")	param+="&code_operation1="	+sForm.code_operation1.value;		//대상자
 		if(sForm.result_state1.value	!= "")	param+="&result_state1="	+sForm.result_state1.value;			//응시결과상태
 		if(sForm.oper_state1.value		!= "")	param+="&oper_state1="		+sForm.oper_state1.value;			//응시상태
 		if(sForm.operation_place1.value	!= "")	param+="&operation_place1="	+sForm.operation_place1.value;		//시험장소
 		if(sForm.edutest_name2.value	!= "")	param+="&detcode="	+sForm.edutest_name2.value;
 //		alert(season1);  
 		//엑션
 //		alert("쪽지발송 전체 = "+param);
 		var url="license.do?method=sendnotePadAll"+param;
 		window.open(url,"notePad","width=370, height=500, left=280, top=80");	
 		
 	}else{
 //선텍된 항목만 보내기 스트러쳐 등록, 엑션 생성 ,jsp파일 붙이기
 		oper_name = escape(encodeURIComponent(oper_name)); 

 //		alert("쪽지발송 조건 = "+oper_name+"  person_yn1 = "+person_yn1);
 		var url="license.do?method=sendnotePad&code_pers="+person_yn1+"&pers_name="+oper_name;
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
 	var pers_hp="";
 	var pers_name="";
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
			if(pers_hp.length > 0){ dm_pers_code+= ","; pers_hp+= ","; pers_name+=",";}	
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

	var param = "";
 	if( rownum.length==0){
 		
 		if(rowSelected){
 			alert("전체문자 발송을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
 		
//  		//여기다 전체 검색(검색과 같은 루트를 탄다.)
 		
//  		 //조건 검색에 쓰는 조건값만 기입
//  		var param = "";
//  		//첫째줄-----------------------------------------------
//  		if(sForm.code_certifi1.value	!= "")	param+="&code_certifi1="+sForm.code_certifi1.value;
//   		if(sForm.yyyy1.value			!= "")	param+="&yyyy1="		+sForm.yyyy1.value;										//년도
//  		if(sForm.code_bran1.value		!= "")	param+="&code_bran1="	+sForm.code_bran1.value;								//지부(교
//  		if(sForm.season1.value			!= "")	{ 			
//  			if(sForm.season1.s1.checked		== true )	season1 = "1";								
// 		 	else if(sForm.season1.s2.checked	== true )	season1	= "2";			
// 		 	else  season1="";
//  			param+="&season1="		+season1;}	//내용
//  		if(sForm.oper_state1.value		!= "")	param+="&oper_state1="+sForm.oper_state1.value;
//  		if(sForm.result_state1.value	!= "")	param+="&result_state1="+sForm.result_state1.value;
//  		if(sForm.edutest_name2.value    != "") 	param+="&detcode="+sForm.edutest_name2.value;

		 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
		 var yyyy1					= sForm.yyyy1.value;											//년도
		 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
		 var season1 				= "";															//학기
		 
		 if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4";			
		 else  season1="";
		 
		 var code_operation1 		= sForm.code_operation1.value;									//대상자
		 var oper_state1 			= sForm.oper_state1.value;										//응시상태
		 var result_state1 			= sForm.result_state1.value;									//응시결과상태
		 var code_bran1 			= sForm.code_bran1.value;										//교육주최(key)
		 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
		 var edutest_name2 			= sForm.edutest_name2.value;									//내용
		 var oper_name1 			= escape(encodeURIComponent(sForm.oper_name1.value)); 			//이름
		 var oper_lic_no1 			= sForm.oper_lic_no1.value;										//면허번호
		 var oper_birth1 				= sForm.oper_birth1.value;											//생년월일
		 var oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
		 var oper_email1 			= sForm.oper_email1.value;										//이메일
		 var person_yn1 			= sForm.person_yn1.value;										//회원구분
		 var attend_cnt1 			= sForm.attend_cnt1.value;										//출석
		 var bran_seq1 				= sForm.bran_seq1.value;										//지부순번(key)
		 var code_kind1 			= sForm.code_kind1.value;										//종류(key)
		 var code_seq1 				= sForm.code_seq1.value;										//순번(key)
		 var receipt_no1 			= sForm.receipt_no1.value;										//접수번호(key)

	   param = "&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
	   		+"&operation_cnt1="+operation_cnt1+"&season1="+season1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1+"&result_state1="+result_state1
	   		+"&code_bran1="+code_bran1+"&operation_place1="+operation_place1+"&edutest_name1="+edutest_name2+"&oper_name1="+oper_name1
	   		+"&oper_lic_no1="+oper_lic_no1
	   		+"&oper_birth1="+oper_birth1
	   		+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&person_yn1="+person_yn1
	   		+"&attend_cnt1="+attend_cnt1+"&result_state1="+result_state1+"&bran_seq1="+bran_seq1+"&code_kind1="+code_kind1+"&code_seq1="+code_seq1
	   		+"&receipt_no1="+receipt_no1;

		 var isSelect = "Y";			
		pers_hp = "All";							
 		  
 		//엑션
 		param+="&from=license1";
 		//alert("문자발송 전체 = "+param);
 		var url="license.do?method=smssandAll&parm="+param+"&pers_hp="+pers_hp+"&isSelect="+isSelect;
 		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
 		
 	}else{
 		param+="&from=license_1"+"&hp_infos="+hp_infos;
 //		alert("문자발송 조건 = "+param);
 		var url="license.do?method=smssand&dm_pers_code="+dm_pers_code+"&pers_hp="+pers_hp+"&pers_name="+pers_name+param;
 		window.open(url,"umsData","width=370, height=550, left=280, top=50");	
 	}
 }
 
 
 
//메일 발송
 function sendEmail() {
 	
	var temp = jQuery("#list").jqGrid('getRowData');
	if(temp.length==0){
		alert("조회된 데이터가 없어 메일을 발송 할 수 없습니다.");
		return;
	}
 	var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

//  	if( rownum.length==0)	{
//  		alert("메일을 발송할 회원을 선택해 주십시요.");
//  		return;	
//  	}
 		
  	var rowdata=new Array();
  	var param="";
 	var addr_infos = "";

  	for(var i=0;i<rownum.length;i++){
 			
 		rowdata= 	$("#list").getRowData(rownum[i]);
 		
 		if(rowdata.oper_email.length>0){
 			if(param.length > 0) param+= ",";
//  			param += rowdata.oper_email;
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
 	//alert("메일발송 = "+param);
 	
 	if( rownum.length==0){	
 		if(rowSelected){
 			alert("전체메일 발송을 하시려면 검색을 다시 실행해 주세요.");
 			return;
 		}
 		
		 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
		 var yyyy1					= sForm.yyyy1.value;											//년도
		 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
		 var season1 				= "";															//학기
		 
		 if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4";			
		 else  season1="";
		 
		 var code_operation1 		= sForm.code_operation1.value;									//대상자
		 var oper_state1 			= sForm.oper_state1.value;										//응시상태
		 var result_state1 			= sForm.result_state1.value;									//응시결과상태
		 var code_bran1 			= sForm.code_bran1.value;										//교육주최(key)
		 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
		 var edutest_name2 			= sForm.edutest_name2.value;									//내용
		 var oper_name1 			= escape(encodeURIComponent(sForm.oper_name1.value)); 			//이름
		 var oper_lic_no1 			= sForm.oper_lic_no1.value;										//면허번호
		 var oper_birth1 				= sForm.oper_birth1.value;											//생년월일
		 var oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
		 var oper_email1 			= sForm.oper_email1.value;										//이메일
		 var person_yn1 			= sForm.person_yn1.value;										//회원구분
		 var attend_cnt1 			= sForm.attend_cnt1.value;										//출석
		 var bran_seq1 				= sForm.bran_seq1.value;										//지부순번(key)
		 var code_kind1 			= sForm.code_kind1.value;										//종류(key)
		 var code_seq1 				= sForm.code_seq1.value;										//순번(key)
		 var receipt_no1 			= sForm.receipt_no1.value;										//접수번호(key)

	   param = "&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
	   		+"&operation_cnt1="+operation_cnt1+"&season1="+season1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1+"&result_state1="+result_state1
	   		+"&code_bran1="+code_bran1+"&operation_place1="+operation_place1+"&edutest_name1="+edutest_name2+"&oper_name1="+oper_name1
	   		+"&oper_lic_no1="+oper_lic_no1
	   		+"&oper_birth1="+oper_birth1
	   		+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&person_yn1="+person_yn1
	   		+"&attend_cnt1="+attend_cnt1+"&result_state1="+result_state1+"&bran_seq1="+bran_seq1+"&code_kind1="+code_kind1+"&code_seq1="+code_seq1
	   		+"&receipt_no1="+receipt_no1;
		 
		param="ALL"+param;
	}
	param+="&paramfrom=TestTakeCondition";

 	window.open('memberBasic.do?method=eMail&toAddr='+param+"&addr_infos="+addr_infos,"oper_email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
 }
 
 function upExcel(){
		window.open("license.do?method=upconditionSendList","lic_no","scrollbars=no,resizable=no,toolbar=no,width=350,height=160,left=20,top=29,status=no");
		
	}
 function setEduN(val){
	//alert(val);
	var jc=eval(<%=j_test%>);
	for(var i=0;i<jc.length;i++){
		if(jc[i].detcode==val){
			sForm.operation_place1.value=jc[i].operation_place;
			sForm.operation_cnt1.value=jc[i].operation_cnt;
			var season1=jc[i].season;
			if(season1=='1') document.getElementById("s1").checked=true;
			if(season1=='2') document.getElementById("s2").checked=true;
			if(season1=='3') document.getElementById("s3").checked=true;
			if(season1=='4') document.getElementById("s4").checked=true;
			sForm.finish_point1.value=jc[i].finish_point;
			sForm.finish_time1.value=jc[i].finish_time;
			sForm.print_kind1.value=jc[i].print_kind;
			sForm.finish_date1.value=jc[i].finish_date;
		}
	}
} 

function setName(){	
	var yyyy=document.getElementById("yyyy1").value;				//교육년도 값
	var code_bran=document.getElementById("code_bran1").value;		//교육주최 값
	var code_certifi1=document.getElementById("code_certifi1").value;		//교육종류 값
		
// 	if ( yyyy == "" || code_bran == "" || code_certifi1 == ""  ){		//교육년도와 교육주최 교육종류
	if ( yyyy == ""  ){		//교육년도와 교육주최 교육종류
		setName1("선택","");
		return;
	}
		/*action send 로 보내는 폼  */
	document.sForm.yyyy1.value = yyyy;
	document.sForm.action="license.do?method=selectEtestN2&yyyy1="+yyyy+"&code_bran1="+code_bran+"&code_certifi1="+code_certifi1;
	document.sForm.submit();
}	

function setName1(name,value){
	var edutest_name2=document.getElementById("edutest_name2");
	var opts=edutest_name2.options;
	while(opts.length>0){
		opts[opts.length-1]=null;
	}
	edutest_name2[0]=new Option(name,value);
} 

function goSave(){	
	 var code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
	 var yyyy1					= sForm.yyyy1.value;											//년도
	 var code_bran1 			= sForm.code_bran1.value;										//교육주최(key)
	 var edutest_name2 			= sForm.edutest_name2.value;									//내용
	 
	 var season1 				= "";															//학기
	      
	 if(sForm.s1.checked		== true )	season1 = "1";								
	 else if(sForm.s2.checked	== true )	season1	= "2";			
	 else if(sForm.s3.checked	== true )	season1	= "3";			
	 else if(sForm.s4.checked	== true )	season1	= "4";			
	 else  season1="";
	 
	 var operation_cnt1			= sForm.operation_cnt1.value;									//횟수
	 var code_operation1 		= sForm.code_operation1.value;									//대상자
	 var oper_state1 			= sForm.oper_state1.value;										//응시상태
	 var result_state1 			= sForm.result_state1.value;									//응시결과상태
	 
	 var operation_place1   	= sForm.operation_place1.value; 								//시험장소
	 
	 var bran_seq1 				= sForm.bran_seq1.value;										//지부순번(key)
	 var code_kind1 			= sForm.code_kind1.value;										//종류(key)
	 var code_seq1 				= sForm.code_seq1.value;										//순번(key)
	 var receipt_no1 			= sForm.receipt_no1.value;										//접수번호(key)
	 
     var pgno = $('#list').getGridParam('page');
     alert("구분코드=="+code_certifi1);
 $('#list').jqGrid('clearGridData');
  jQuery("#list").setGridParam({url:"license.do?method=licenseSendSave&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
  		+"&operation_cnt1="+operation_cnt1+"&season1="+season1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1+"&result_state1="+result_state1
  		+"&code_bran1="+code_bran1+"&operation_place1="+operation_place1+"&edutest_name1="+edutest_name2  		
  		+"&bran_seq1="+bran_seq1+"&code_kind1="+code_kind1+"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1}
  		).trigger("reloadGrid"); 
  alert("저장됐습니다");
  jQuery("#list").jqGrid('setGridParam',{page:pgno,url:"license.do?method=licenseSendList&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
  		+"&season1="+season1+"&code_operation1="+code_operation1+"&operation_place1="+operation_place1
  		+"&code_bran1="+code_bran1+"&edutest_name1="+edutest_name2 }).trigger("reloadGrid");
}

function goClear(){
	sForm.reset();
	setName1("선택","");
}

function goSaveBatch(){
		var temp = jQuery("#list").jqGrid('getRowData');
		if(temp.length==0){
			alert("조회된 데이터가 없어 일괄처리를 할 수 없습니다.");
			return;
		}
		var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

//	 	if( rownum.length==0)	{
//	 		alert("일괄처리할 회원을 선택해 주십시요.");
//	 		return;	
//	 	}
			
	 	if( rownum.length==0 && rowSelected)	{
			 alert("전체 일괄처리를 하시려면 검색을 다시 실행해 주세요.");
	 		return;	
	 	}
			
	 	var rowdata=new Array();
	 	var param="";
	 	var oper_infos="";

	 	for(var i=0;i<rownum.length;i++){
				
			rowdata= 	$("#list").getRowData(rownum[i]);
			
			if(oper_infos.length > 0) {
				oper_infos	+= "__";
			}
			oper_infos	+= rowdata.code_kind+"_";
			oper_infos	+= rowdata.code_certifi+"_";
			oper_infos	+= rowdata.code_seq+"_";
			oper_infos	+= rowdata.code_bran+"_";
			oper_infos	+= rowdata.bran_seq+"_";
			oper_infos	+= rowdata.receipt_no;
		}

		 var code_certifi1 			= "";
		 var yyyy1					= "";
		 var operation_cnt1			= "";
		 var season1 				= "";
		 var code_operation1 		= "";
		 var oper_state1 			= "";
		 var result_state1 			= "";
		 var code_bran1 			= "";
		 var operation_place1   	= "";
		 var edutest_name2 			= "";
		 var oper_name1 			= "";
		 var oper_lic_no1 			= "";
		 var oper_birth1 				= "";
		 var oper_hp1 				= "";
		 var oper_email1 			= "";
		 var person_yn1 			= "";
		 var attend_cnt1 			= "";
		 var bran_seq1 				= "";
		 var code_kind1 			= "";
		 var code_seq1 				= "";
		 var receipt_no1 			= "";
		 
// 		if( rownum.length==0){	
			 code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
			 yyyy1					= sForm.yyyy1.value;											//년도
			 operation_cnt1			= sForm.operation_cnt1.value;									//횟수
			 season1 				= "";															//학기
			 
			 if(sForm.s1.checked		== true )	season1 = "1";								
			 else if(sForm.s2.checked	== true )	season1	= "2";			
			 else if(sForm.s3.checked	== true )	season1	= "3";			
			 else if(sForm.s4.checked	== true )	season1	= "4";			
			 else  season1="";
			 
			 code_operation1 		= sForm.code_operation1.value;									//대상자
			 oper_state1 			= sForm.oper_state1.value;										//응시상태
			 result_state1 			= sForm.result_state1.value;									//응시결과상태
			 code_bran1 			= sForm.code_bran1.value;										//교육주최(key)
			 operation_place1   	= sForm.operation_place1.value; 								//시험장소
			 edutest_name2 			= sForm.edutest_name2.value;									//내용
			 oper_name1 			= escape(encodeURIComponent(sForm.oper_name1.value)); 			//이름
			 oper_lic_no1 			= sForm.oper_lic_no1.value;										//면허번호
			 oper_birth1 				= sForm.oper_birth1.value;											//생년월일
			 oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
			 oper_email1 			= sForm.oper_email1.value;										//이메일
			 person_yn1 			= sForm.person_yn1.value;										//회원구분
			 attend_cnt1 			= sForm.attend_cnt1.value;										//출석
			 bran_seq1 				= sForm.bran_seq1.value;										//지부순번(key)
			 code_kind1 			= sForm.code_kind1.value;										//종류(key)
			 code_seq1 				= sForm.code_seq1.value;										//순번(key)
			 receipt_no1 			= sForm.receipt_no1.value;										//접수번호(key)

		   param = "&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
		   		+"&operation_cnt1="+operation_cnt1+"&season1="+season1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1+"&result_state1="+result_state1
		   		+"&code_bran1="+code_bran1+"&operation_place1="+operation_place1+"&edutest_name1="+edutest_name2+"&oper_name1="+oper_name1
		   		+"&oper_lic_no1="+oper_lic_no1
		   		+"&oper_birth1="+oper_birth1
		   		+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&person_yn1="+person_yn1
		   		+"&attend_cnt1="+attend_cnt1+"&result_state1="+result_state1+"&bran_seq1="+bran_seq1+"&code_kind1="+code_kind1+"&code_seq1="+code_seq1
		   		+"&receipt_no1="+receipt_no1;
// 		}

		param+="&oper_infos="+oper_infos;
		
		if(!confirm((rownum.length>0?"선택하신 "+(rownum.length)+"건을 일괄처리 하시겠습니까?":"전체 일괄처리 하시겠습니까?"))){return;}
		
		 $('#list').jqGrid('clearGridData');
		  jQuery("#list").setGridParam({url:"license.do?method=licenseSendSaveBatch"+param,
		  		ajaxGridOptions:{complete:function(){
				  	alert("일괄처리 되었습니다.");
				  	
					  jQuery("#list").jqGrid('setGridParam',{url:"license.do?method=licenseSendList&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
					  		+"&season1="+season1+"&code_operation1="+code_operation1+"&operation_place1="+operation_place1
					  		+"&code_bran1="+code_bran1+"&edutest_name1="+edutest_name2+"&season1="+season1+"&result_state1="+result_state1+"&oper_state1="+oper_state1
					  		,ajaxGridOptions:{complete:function(){}}}).trigger("reloadGrid");
		  		}}}
		  		).trigger("reloadGrid"); 
}

function goDel(){
	var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
	if( gr == null ) {
		alert("목록에서 삭제하실 응시현황을 선택해 주세요."); return;
	}
	 if(sForm.receipt_no1.value==""){
		alert("삭제하실 응시현황을 다시 선택해 주세요."); return;
	}
	
		 var code_certifi1 			= "";
		 var yyyy1					= "";
		 var operation_cnt1			= "";
		 var season1 				= "";
		 var code_operation1 		= "";
		 var oper_state1 			= "";
		 var result_state1 			= "";
		 var code_bran1 			= "";
		 var operation_place1   	= "";
		 var edutest_name2 			= "";
		 var oper_name1 			= "";
		 var oper_lic_no1 			= "";
		 var oper_birth1 				= "";
		 var oper_hp1 				= "";
		 var oper_email1 			= "";
		 var person_yn1 			= "";
		 var attend_cnt1 			= "";
		 var bran_seq1 				= "";
		 var code_kind1 			= "";
		 var code_seq1 				= "";
		 var receipt_no1 			= "";
		 
		 code_certifi1 			= sForm.code_certifi1.value;									//구분코드(교육및시험구분)(key)
		 yyyy1					= sForm.yyyy1.value;											//년도
		 operation_cnt1			= sForm.operation_cnt1.value;									//횟수
		 season1 				= "";															//학기
		 
		 if(sForm.s1.checked		== true )	season1 = "1";								
		 else if(sForm.s2.checked	== true )	season1	= "2";			
		 else if(sForm.s3.checked	== true )	season1	= "3";			
		 else if(sForm.s4.checked	== true )	season1	= "4";			
		 else  season1="";
		 
		 code_operation1 		= sForm.code_operation1.value;									//대상자
		 oper_state1 			= sForm.oper_state1.value;										//응시상태
		 result_state1 			= sForm.result_state1.value;									//응시결과상태
		 code_bran1 			= sForm.code_bran1.value;										//교육주최(key)
		 operation_place1   	= sForm.operation_place1.value; 								//시험장소
		 edutest_name2 			= sForm.edutest_name2.value;									//내용
		 oper_name1 			= escape(encodeURIComponent(sForm.oper_name1.value)); 			//이름
		 oper_lic_no1 			= sForm.oper_lic_no1.value;										//면허번호
		 oper_birth1 				= sForm.oper_birth1.value;											//생년월일
		 oper_hp1 				= sForm.oper_hp1.value;											//핸드폰
		 oper_email1 			= sForm.oper_email1.value;										//이메일
		 person_yn1 			= sForm.person_yn1.value;										//회원구분
		 attend_cnt1 			= sForm.attend_cnt1.value;										//출석
		 bran_seq1 				= sForm.bran_seq1.value;										//지부순번(key)
		 code_kind1 			= sForm.code_kind1.value;										//종류(key)
		 code_seq1 				= sForm.code_seq1.value;										//순번(key)
		 receipt_no1 			= sForm.receipt_no1.value;										//접수번호(key)

	   var param = "&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
	   		+"&operation_cnt1="+operation_cnt1+"&season1="+season1+"&code_operation1="+code_operation1+"&oper_state1="+oper_state1+"&result_state1="+result_state1
	   		+"&code_bran1="+code_bran1+"&operation_place1="+operation_place1+"&edutest_name1="+edutest_name2+"&oper_name1="+oper_name1
	   		+"&oper_lic_no1="+oper_lic_no1
	   		+"&oper_birth1="+oper_birth1
	   		+"&oper_hp1="+oper_hp1+"&oper_email1="+oper_email1+"&person_yn1="+person_yn1
	   		+"&attend_cnt1="+attend_cnt1+"&result_state1="+result_state1+"&bran_seq1="+bran_seq1+"&code_kind1="+code_kind1+"&code_seq1="+code_seq1
	   		+"&receipt_no1="+receipt_no1;
	 
	if(!confirm("선택하신 응시현황을 삭제하시겠습니까?\n\n삭제한 응시현황은 다시 복구할 수 없습니다.")){return;}
	 
	 $('#list').jqGrid('clearGridData');
	  jQuery("#list").setGridParam({url:"license.do?method=licenseDel"+param,
	  		ajaxGridOptions:{complete:function(){
			  	alert("삭제 되었습니다.");
			  	
				  jQuery("#list").jqGrid('setGridParam',{url:"license.do?method=licenseSendList&code_certifi1="+code_certifi1+"&yyyy1="+yyyy1
				  		+"&season1="+season1+"&code_operation1="+code_operation1+"&operation_place1="+operation_place1
				  		+"&code_bran1="+code_bran1+"&edutest_name1="+edutest_name2+"&season1="+season1+"&result_state1="+result_state1+"&oper_state1="+oper_state1
				  		,ajaxGridOptions:{complete:function(){}}}).trigger("reloadGrid");
	  		}}}
	  		).trigger("reloadGrid"); 
}



</script> 


 
 </head>

 <body onload="init();">
<form action="" method="post">
  
	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="license.do?method=licenseSend">자격증</a></li>		
		<li>&gt;</li>
		<li class="NPage">시험별응시현황</li>
	  </ul>	 
	  
	  <form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>
	  <div class="cTabmenu_01">   
	  
	    <form id="list1" name="sForm" method="post">
					   <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
						<input type="hidden" name=edutest_name1 id="edutest_name1" value=""/>
						<input type="hidden" name=oper_name1 id="oper_name1" value=""/>
						<input type="hidden" name=oper_lic_no1 id="oper_lic_no1" value=""/>
<!-- 						JUMIN_DEL -->
<!-- 						<input type="hidden" name=oper_no1 id="oper_no1" value=""/> -->
						<input type="hidden" name=oper_birth1 id="oper_birth1" value=""/>
						<input type="hidden" name=oper_hp1 id="oper_hp1" value=""/>
						<input type="hidden" name=oper_email1 id="oper_email1" value=""/>
						<input type="hidden" name=person_yn1 id="person_yn1" value=""/>
						<input type="hidden" name=attend_cnt1 id="attend_cnt1" value=""/>
						<input type="hidden" name=bran_seq1 id="bran_seq1" value=""/>
						<input type="hidden" name=code_kind1 id="code_kind1" value=""/>
						<input type="hidden" name=code_seq1 id="code_seq1" value=""/>
						<input type="hidden" name="receipt_no1" id="receipt_no1" value=""/> 
						<input type="hidden" name="code_pers" id="code_pers" value=""/> 
						
						  
		<table class="ctable_01" cellspacing="0" summary="자격증">
          <caption>시험별응시현황</caption>            			 
             <tr>
               <td class="nobg">※&nbsp;&nbsp;자격증구분</td>
               <td class="nobg1">
	               <select  id="code_certifi1" name="code_certifi1" onchange="javascript:setName();">
	               	<option value="">전체</option>	
	              		<%
		                	String detCode5,detCName5=null;
		                	for(int i=0;i<licenseserch1.size();i++){
		                			detCode5=licenseserch1.get(i).get("code_certifi").toString();
		                			detCName5=licenseserch1.get(i).get("certifi_name").toString();
		                			
		                			if(!"9".equals(licenseserch1.get(i).get("code_certifi").toString())
		                			&&!"0".equals(licenseserch1.get(i).get("code_certifi").toString())){
		                			if(licensein!=null&&detCode5.equals(licensein.get("code_certifi1").toString())){
		                				out.println("<option value='"+detCode5+"' selected>"+detCName5+"</option>");
		                			}else{
		                				out.println("<option value='"+detCode5+"'>"+detCName5+"</option>");
		                			}
		                		}
		                	}
		                	
		               		 %>    
	               	</select>
               	</td>
			   <td class="nobg2">※&nbsp;&nbsp;년도</td>
               <td class="nobg1">
	               <select  id="yyyy1" name="yyyy1" onchange="javascript:setName();">	
	               	<option value="">전체</option>
 					 <%
					 	String detCode,detCName="";
					 	for(int i=0;i<licenseserch.size();i++){
					 		if("036".equals(licenseserch.get(i).get("groupcode").toString())){
	                			detCode=licenseserch.get(i).get("detcode").toString();
	                			detCName=licenseserch.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode+">"+detCName+"</option>");
	                			
	                		}
					 	}
					 %>        	
	              	</select>
              	</td> 
               <td class="nobg2">※&nbsp;&nbsp;교육주최</td>
               <td class="nobg1">
               		<select  id="code_bran1" name="code_bran1" onchange="javascript:setName();" >
               <option value="">전체</option>	
              		<%
				 	for(int i=0;i<licenseserch.size();i++){
				 		if("034".equals(licenseserch.get(i).get("groupcode").toString())){
                			detCode=licenseserch.get(i).get("detcode").toString();
                			detCName=licenseserch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
				 	}
				 %>    
               	    </select></td>
             </tr>
             <tr>
             <td class="alt1">※&nbsp;&nbsp;시험명</td>
             <td colspan="5" class="alt2">
               <select  name="edutest_name2"  id="edutest_name2" style="width:562px" onchange="javascript:setEduN(this.value)">
               	<option value="">선택</option>	
               </select>
               </td>
             </tr>
             <tr>
               <td class="alt1">※&nbsp;&nbsp;교육구분</td>
               <td >
              	   <input type="hidden" id="s0" value="0" />
              		
				   <div style="border:0px solid black; display:inline-block; text-align:left;">
				   <input type="radio" name="season1" id="s1" value="1" checked="checked" />
				   <label for="s1">1학기</label><br/>
				   <input type="radio" name="season1" id="s3" value="3" />
				   <label for="s3">집합교육</label>
				   </div>
				   <div style="border:0px solid red; display:inline-block; text-align:left;">
				   <input type="radio" name="season1" id="s2" value="2" />
				   <label for="s2">2학기</label><br/>
				   <input type="radio" name="season1" id="s4" value="4" />
				   <label for="s4">온라인교육</label>
				   </div>
			   </td>
               <td class="alt">※&nbsp;&nbsp;응시결과상태</td>
               <td>
               		<select  id="name" name="result_state1" >
	                <option value="">전체</option>
	                       <% 
	                	String detCode7,detCName7=null;
	                	for(int i=0;i<licenseserch.size();i++){
	                		 if("023".equals(licenseserch.get(i).get("groupcode").toString())){
	                			detCode7=licenseserch.get(i).get("detcode").toString();
	                			detCName7=licenseserch.get(i).get("detcodename").toString();
	                			out.println("<option value="+detCode7+">"+detCName7+"</option>");
	                		 }
	                	}
	               		 %>                    
	                </select>
	                </td>
              <td class="alt">※&nbsp;&nbsp;응시상태</td>
			   <td>
			   <select  id="name" name="oper_state1" >
	                <option value="">전체</option>
	                       <% 
	                	
	                	for(int i=0;i<licenseserch.size();i++){
	                		 if("022".equals(licenseserch.get(i).get("groupcode").toString())){
// 	                			 if(!licenseserch.get(i).get("detcode").toString().equals("08")&&!licenseserch.get(i).get("detcode").toString().equals("09")){
	                			 if(!licenseserch.get(i).get("detcode").toString().equals("14")){
	                				detCode=licenseserch.get(i).get("detcode").toString();
	 	                			detCName=licenseserch.get(i).get("detcodename").toString();
	 	                			out.println("<option value="+detCode+">"+detCName+"</option>"); 
	                			 }	                			
	                		 }
	                	}
	               		 %>                     
	                </select>
	                </td>                
             </tr>
			 <tr>               
               <td class="alt1">대상자</td>
               <td>
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
               <td class="alt">횟수</td>
               <td ><input type="text" id="operation_cnt1"  size="4" name="operation_cnt1"/></td>
			   <td class="alt">시험장소</td>
               <td><input type="text" id="p" size="40" name="operation_place1"/></td>			  
             </tr>			 		 
        </table>				
		<p class="btnSave">			

		  	   <%if("cocone".equals(g_id) || "minju51".equals(g_id)){ %>
		  	   <a href="javascript:goDel();"><img src="images/icon_delete.gif"    onclick="" alt="삭제" /></a>		 
<!-- 		  	   <span onclick="goDel();" style="cursor:pointer;">[삭제]</span> -->
		  	   <%} %>		 
		
		  	<a href="javascript:goClear();"><img src="images/icon_new.gif"    onclick="" alt="신규" /></a>		 
			<a href="javascript:goSave();"><img src="images/icon_save.gif" onclick="" alt="저장" /></a>
			<a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a>
		</p>	  
		
	  </form>
 <br><br></br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
	<ul class="btnIcon_2" style="left:865px;">		 
<!-- 		<li><a href="javascript:notePad();"><img src="images/icon_s2.gif" onclick=""	alt="쪽지" /></a></li> -->

		<li><a href="javascript:goSaveBatch();"><img src="images/icon_processing.gif" onclick="" alt="일괄처리" /></a></li>
<!-- 		<li><span onclick="goSaveBatch();" style="cursor:pointer;">[일괄처리]</span></li> -->


		<!-- <li><a href="javascript:sendEmail();"><img src="images/icon_s3.gif"	onclick=""	alt="메일전송"	/></a></li>	
		<li><a href="javascript:smssand();"><img src="images/icon_s4.gif"	onclick=""	alt="문자전송"	/></a></li> -->
		<li><a href="javascript:upExcel();"><img src="images/icon_s16.gif" onclick="" alt="엑셀올리기"/></a></li>
		<li><a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" alt="엑셀저장"/></a></li>		 
	</ul>
</form>

 </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	
 
 </body> 
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>ㅎ