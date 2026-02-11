<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>대한영양사협회</title>
  <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
  <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
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

<%@page import="java.util.Calendar"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.ant.educationexam.dao.educationDao"%>

<!-- 날짜 표시를 위한 스크립트   -->
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>  

<%
Map map = new HashMap();
educationDao dao = new educationDao();

List<Map> certifisearch = dao.certifisearch(map);
List<Map> certifisearch1 = dao.certifisearch1(map);
%>

  
<script type="text/javascript">

$(document).ready(function(){	
jQuery("#list").jqGrid({	  
	  url: 'locall',
	  datatype: "json",
      mtype: 'post',      
      width:'1100',
      height:'100',
      colNames: [ '자율학습명','신청일','이름','면허번호','주민번호','핸드폰','이메일','점수','입금자명','비고','교육년도','자격증구분','횟수','시작일자'
                  ,'마감일자','평점종류','교육평점','자율학습비'],
      colModel: [
			{name:'edutest_name',  index:'edutest_name', width: 120,editable: false, align: 'center'},		//자율학습명
   			{name:'receipt_dt',    index:'receipt_dt',   width: 100,editable: false, align: 'right'},		//신청일
   			{name:'oper_name',     index:'oper_name',    width: 100,editable: false, align: 'right'},		//이름
   			{name:'oper_lic_no',   index:'oper_lic_no',  width: 100,editable: false, align: 'right'},		//면허번호
   			{name:'oper_no',       index:'oper_no',      width: 100,editable: false, align: 'right'},		//주민번호
   			{name:'oper_hp',       index:'oper_hp',      width: 100,editable: false, align: 'right'},		//핸드폰
   			{name:'oper_email',    index:'oper_email',   width: 100,editable: false, align: 'right'},		//이메일
   			{name:'result_point',  index:'result_point', width: 100,editable: false, align: 'right'},		//점수
   			{name:'oper_account',  index:'oper_account', width: 100,editable: false, align: 'right'},		//입금자명
   			{name:'remark',        index:'remark',       width: 100,editable: false, align: 'right'},		//비고
   			{name:'yyyy',      	   index:'yyyy',       	  hidden:true},		//교육년도
   			{name:'certifi_name',  index:'certifi_name',  hidden:true},		//자격증구분
   			{name:'operation_cnt', index:'operation_cnt', hidden:true},		//횟수
   			{name:'oper_start_dt', index:'oper_start_dt', hidden:true},		//시작일자
   			{name:'oper_end_dt',   index:'oper_end_dt',   hidden:true},		//마감일자  
   			{name:'point_manage',  index:'point_manage',  hidden:true},		//평점종류
   			{name:'edu_marks',     index:'edu_marks',     hidden:true},		//교육평점
   			{name:'new_cost',      index:'new_cost',      hidden:true}		//자율학습비
   			    ],
   		    rowNum:15,
   		    sortname: 'receipt_dt',
   			pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
 			multiselect: false,
 			gridview: true,
 			caption: '자율학습신청자',
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
						onSubmitList(id);
					}
					else {}
				
				}
			}
  
});
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function downloadExcel() {
	drawExcel("신청자", "#list");
}

$(function() {
	$( "#datepicker" ).datepicker();
});

$(function() {
	$( "#datepicker1" ).datepicker();
});

function goClear(){	
	$('#list1').each(function(){
		this.reset();
		//sForm.oper_name1.frocus();
	});	
	
}



</script>





 </head>

 <body>
<form action="" method="post">

	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="/main3.do">교육</a></li>
		<li>&gt;</li>
		<li><a href="/main3.4.do">자율학습</a></li>
		<li>&gt;</li>
		<li class="NPage">신청자</li>
	  </ul>
<form method="post"> 
	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>
	  <div id="ts_tabmenu">
        <ul>
	      <!-- <li><a href="/main3.3.do"><strong><span>등록</span></strong></a></li> -->
	      <li><a href="#" class="overMenu"><strong><span>신청자</span></strong></a></li>	  
		  <li><a href="/main3.5.do"><strong><span>결과처리</span></strong></a></li>
        </ul>
      </div>
	  <div class="eTabmenu_01">
	   <form id="list1" name="sForm" method="post">
        <table class="etable_03" cellspacing="0" summary="교육">
          <caption>신청자</caption>            			 
             <tr>
               <td class="nobg">교육년도</td>
               <td class="nobg1">
               <select  id="yyyy1" name="yyyy1"  onchange="javascript:setName();">	
               <option value="">전체</option>
              		 <% 
                	String detCode,detCName=null;
                	for(int i=0;i<certifisearch.size();i++){
                		 if("036".equals(certifisearch.get(i).get("groupcode").toString())){
                			detCode=certifisearch.get(i).get("detcode").toString();
                			detCName=certifisearch.get(i).get("detcodename").toString();
                			out.println("<option value="+detCode+">"+detCName+"</option>");
                		}
                	}
               		 %>  
               	    </select>
                </td>
			   <td class="nobg2">자격구분</td>
               <td class="nobg1">
               <select  id="name" name="code_certifi1" >
               <option value="">전체</option>	
              		 <% 
                	String detCode5,detCName5=null;
                	for(int i=0;i<certifisearch1.size();i++){
                			detCode5=certifisearch1.get(i).get("code_certifi").toString();
                			detCName5=certifisearch1.get(i).get("certifi_name").toString();
                			out.println("<option value="+detCode5+">"+detCName5+"</option>");
                		}
               		 %>  
               	</select>
                </td>
			   <td class="nobg2">횟수</td>
               <td class="nobg1">
               <input type="text" id="p" size="1" name="operation_cnt1"/>
               </td>
             </tr>           			 
             
             <tr>
               <td class="alt1">자율학습명</td>
               <td colspan="3" class="alt2">
               <input type="text" name="edutest_name1" id="m" class="input" style=padding-top:3px  style=padding-bottom:3px  size="100" align="center" />
               </td>
			   <td class="alt">시작일자</td>
			   <td>
			   <input type="text" id="datepicker" size="5" name="oper_start_dt1" />
			   </td>
             </tr>
			 <tr>
               <td class="alt1">마감일자</td>
               <td>
				<input type="text" id="datepicker1" size="5" name="oper_end_dt1" />
				</td>
               <td class="alt">평점종류</td>
               <td>
               <select>
                <option value="1">기타</option>
                </select>
                </td>
			   <td class="alt">교육평점</td>
               <td><input type="text" id="p" size="15" name="edu_marks1"/></td>	  
             </tr>
			 <tr>
               <td class="alt1">자율학습비</td>
               <td>
				<input type="text" id="p" size="15" id="" name="new_cost1"/>
				</td>
               <td class="alt">비고</td>
               <td><input type="text" id="p" size="15" name="remark1"/></td>
			   <td class="alt"></td>
               <td></td>			  
             </tr>				 			 		
        </table>			
		<ul class="btnIcon_1">
		 	   <li><img src="images/icon_new.gif"    onclick="javascript:goClear();" alt="신규" /></li>		 
			   <li><img src="images/icon_save.gif"   onclick="javascript:goInsert();" alt="저장" /></li>
			   <li><img src="images/icon_search.gif" onclick="javascript:goSearch(sForm,0);" alt="검색" /></li>		  	 
		</ul>	      		
	  </form>
  
 <br><br>

 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">
<table class="mtable_18" cellspacing="0" >


<tr><td class="btnIcon_2">
		<img src="images/icon_excel.gif" onclick="javascript:downloadExcel();" onmouseover="this.src=this.src.replace('_off.','_on.')" onmouseout="this.src=this.src.replace('_on.','_off.')"/>
</td></tr>
</table>
</form>
 </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	
 
 </body> 
</html>