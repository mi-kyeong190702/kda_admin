<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>대한영양사협회</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" type="text/css" href="css/import.css" />  
  <link rel="stylesheet" type="text/css" href="css/education.css" />
 </head>

 <body>
<form action="" method="post">

	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="/main.do">Home</a></li>
		<li>&gt;</li>
		<li><a href="/main3.do">교육</a></li>
		<li>&gt;</li>
		<li><a href="/main3.10.do">문항관리</a></li>
		<li>&gt;</li>
		<li class="NPage">문항등록</li>
	  </ul>
	  <div id="ts_tabmenu">
        <ul>
	      <li><a href="#" class="overMenu"><strong><span>문항등록</span></strong></a></li>
	      <li><a href="/main3.11.do"><strong><span>문항검색</span></strong></a></li>	 
        </ul>
      </div>
	  <div class="eTabmenu_01">        
		<table class="etable_08" cellspacing="0" summary="문항관리">
          <caption>문항등록</caption>            		 
			 <tr>
               <td class="nobg">자격구분</td>
               <td class="nobg1"><select id="search"><option value="성명">산업보건</option>
                                 <option value="주민번호"></option></select></td>
               <td class="nobg2">년도</td>
               <td class="nobg1"><select id="search"><option value="성명">2012</option>
                                 <option value="주민번호"></option></select></td>		   		  
             </tr>	
			 <tr>
               <td class="alt1">지역</td>
               <td><select id="search"><option value="성명">중앙회</option>
                   <option value="주민번호"></option></select></td> 
			   <td class="alt">분기</td>
			   <td><select id="search"><option value="성명">상반기</option>
                   <option value="주민번호"></option></select></td>
             </tr>
			 <tr>
               <td class="alt1">지역</td>
               <td><select id="search"><option value="성명">중앙회</option>
                   <option value="주민번호"></option></select></td> 
			   <td class="alt">분기</td>
			   <td><select id="search"><option value="성명">상반기</option>
                   <option value="주민번호"></option></select></td>
             </tr>
			 <tr>
               <td class="alt1">출제자</td>
               <td><select id="search"><option value="성명">중앙회</option>
                   <option value="주민번호"></option></select></td> 
			   <td class="alt">과목</td>
			   <td><select id="search"><option value="성명">상반기</option>
                   <option value="주민번호"></option></select></td>
             </tr>
			 <tr>
               <td class="alt1">학기</td>
               <td><select id="search"><option value="성명">중앙회</option>
                   <option value="주민번호"></option></select></td> 
			   <td class="alt">코드</td>
			   <td><select id="search"><option value="성명">상반기</option>
                   <option value="주민번호"></option></select></td>
             </tr>
			 <tr>
               <td class="alt1">학습목표</td>
			   <td colspan="3"><input type="text" id="m" size="100" /></td>
             </tr>
			 <tr>
               <td class="alt1">비고</td>
               <td colspan="3"><input type="text" id="m" size="100" /></td> 			   			   
             </tr>
			 <tr>
               <td class="alt1">문항번호</td>
               <td><input type="text" id="m" size="15" /></td> 
			   <td class="alt">문항유형</td>
			   <td><select id="search"><option value="성명">A형</option>
                   <option value="주민번호"></option></select></td>
             </tr>
			 <tr>
               <td class="alt1">정답률</td>
               <td><input type="text" id="m" size="15" /></td>
			   <td class="alt">답지반응도</td>
			   <td><input type="text" id="m" size="15" /></td>
             </tr>
			 <tr>
               <td class="alt1">문항</td>
               <td colspan="3"><input type="text" id="m" size="100" /></td> 			   
             </tr>
			 <tr>
               <td class="alt1">보기</td>
               <td colspan="3"><input type="text" id="m" size="100" /></td>			  
             </tr>
			 <tr>
               <td rowspan="5" class="alt1">항목</td>
               <td colspan="3">1&nbsp&nbsp&nbsp<input type="text" id="m" size="100" /></td>
             </tr>
			 <tr>               
               <td colspan="3">2&nbsp&nbsp&nbsp<input type="text" id="m" size="100" /></td>
             </tr>
			 <tr>               
               <td colspan="3">3&nbsp&nbsp&nbsp<input type="text" id="m" size="100" /></td>
             </tr>
			 <tr>              
               <td colspan="3">4&nbsp&nbsp&nbsp<input type="text" id="m" size="100" /></td>
             </tr>
			 <tr>               
               <td colspan="3">5&nbsp&nbsp&nbsp<input type="text" id="m" size="100" /></td>
             </tr>
			 <tr>
               <td class="alt1">정답</td>
               <td><select id="search"><option value="성명">1</option>
                   <option value="주민번호"></option></select></td> 
			   <td class="alt">등록일자</td>
			   <td><input type="text" id="m" size="15" /></td>
             </tr>
        </table>				
		<ul class="btnIcon_4">		 
		  <li><a href="#"><img src="images/icon_entry.gif" alt="등록" /></a></li>
		  <li><a href="#"><img src="images/icon_save.gif" alt="저장" /></a></li>		  	  
		</ul>	  	      		
	  </div><!--rTabmenu_01 End-->					  
	</div><!--contents End-->	  
 </form>
 </body> 
</html>
