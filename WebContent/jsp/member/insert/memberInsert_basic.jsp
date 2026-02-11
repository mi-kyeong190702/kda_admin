
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <title>대한영양사협회</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" type="text/css" href="css/import.css" />  
  <link rel="stylesheet" type="text/css" href="css/m_member.css" />
 </head>

 <body>
<form action="" method="post">
<div id="wrap">
  <div id="header">
    <h1><a href="index.html"><img src="images/logo.gif" alt="대한영양사협회" /></a></h1>
	<ul class="lnb">
	  <li><a href="#"><img src="images/m_logout.gif" alt="로그아웃" /></a></li>	      
	  <li><a href="#"><img src="images/m_help.gif" alt="도움말" /></a></li>	
    </ul>
	<p class="form1"><select id="search">
       <option value="이름">이름</option>
       <option value="주민번호">주민번호</option>       
       </select>
    </p>
	<p class="form2"><input type="text" id="name" size="12" /></p>
	<ul class="newOpen">
	  <li><img src="images/m_view.gif" alt="돋보기이미지" /></li>	
	  <li><a href="#"><img src="images/m_newopen.gif" alt="새창보기" /></a></li>
	</ul>
	<div class="gnb">
	  <ul>
	    <li><a href="#">회원</a>
		  <ul>
		    <li><a href="#">회원등록</a></li>
			<li><a href="#">조건검색</a></li>
			<li><a href="#">예수금현황</a></li>
			<li><a href="#">기부&frasl;기금현황</a></li>			
			<li><a href="#">현황</a></li>
		  </ul>
		</li>
		<li><a href="#">교육</a>
		  <ul>
		    <li><a href="#">교육등록</a></li>
			<li><a href="#">교육관리</a></li>
			<li><a href="#">자율학습</a></li>
			<li><a href="#">강사관리</a></li>
			<li><a href="#">문항관리</a></li>			
		  </ul>
		</li>
		<li><a href="#">자격증</a>
		  <ul>
		    <li><a href="#">자격시험 등록</a></li>
			<li><a href="#">자격시험 관리</a></li>
			<li><a href="#">자격증 등록</a></li>
			<li><a href="#">자격증 발급</a></li>			
		  </ul>		
		</li>
		<li><a href="#">문서</a>
		  <ul>
		    <li><a href="#">문서발송대장</a></li>
			<li><a href="#">문서접수대장</a></li>
			<li><a href="#">내부결제공문대장</a></li>
			<li><a href="#">증서발급</a></li>
			<li><a href="#">DM관리</a></li>			
		  </ul>		
		</li>
		<li><a href="#">회의실</a>
		  <ul class="right_side">	
		    <li><a href="#"></a></li>
			<li><a href="#">수정</a></li>
			<li><a href="#">예약</a></li>
		  </ul>
		</li>
		<li><a href="#">영양사실태신고</a>
		  <ul class="right_side">
		    <li><a href="#">확인</a></li>
			<li><a href="#">접수</a></li>			
		  </ul>
		</li>
		<li class="rightLine"><a href="#">환경설정</a>
		  <ul class="right_side">
		    <li><a href="#">회비&frasl;예수금</a></li>
			<li><a href="#">목표인원</a></li>
			<li><a href="#">공통코드</a></li>
			<li><a href="#">교육코드</a></li>
			<li><a href="#">로그</a></li>		
		  </ul>
		</li>		
	  </ul>
	</div>
  </div><!--header-->
  
  <div id="container">
    <div id="side">
	  <ul>
	    <li><img src="images/snb_menu1.gif" alt="회원" /></li>	
		<li class="menuOn">회원등록</li>
		<li class="aa"><a href="#">조건&nbsp;검색</a></li>
		<li class="aa"><a href="#">예수금&nbsp;현황</a></li>
		<li class="aa"><a href="#">기부&frasl;기금현황</a></li>		
		<li class="aa"><a href="#">현황</a></li>
		<li><img src="images/snb_menu_bg.gif" alt="이미지" /></li>	
	  </ul>
	</div>
	<div id="contents">
	  <ul class="home">
	    <li class="home_first"><a href="#">Home</a></li>
		<li>&gt;</li>
		<li><a href="#">회원</a></li>
		<li>&gt;</li>
		<li class="NPage">회원등록</li>
	  </ul>
	  <div id="ts_tabmenu">
        <ul>
	      <li><a href="#" class="overMenu"><strong><span>기본정보</span></strong></a></li>
	      <li><a href="#"><strong><span>근무처정보</span></strong></a></li>	       		
        </ul>
      </div>
	  <div class="mTabmenu_01">
        <table class="mytable_01" cellspacing="0" summary="기본정보">
          <caption>기본정보</caption>
             <tr>
               <td class="nobg">자택우편번호</td>
               <td colspan="5" class="nobg1"><input type="text" id="m_post" size="15" />
			   <input name="bnt" type="button" value="우편번호" />	
			   <input type="radio" name="post" id="o" value="post" />
			   <label for="a_old">구주소</label>
			   <input type="radio" name="post" id="n" value="post" />
			   <label for="a_new">신주소</label>
			   </td>
			 </tr>
			 <tr>
               <td class="alt1">자택주소</td>
               <td colspan="3"><input type="text" id="m_address" size="45" />-<input type="text" id="address" size="25" /></td> 
			 <td class="alt">자택전화번호</td>
			   <td><input type="text" id="mail" size="15" /></td>
             </tr>
             <tr>
               <td class="alt1">휴대폰</td>
               <td><input type="text" id="m" size="15" /></td>
               <td class="alt">이메일</td>
               <td><input type="text" id="p" size="15" /></td>
			   <td class="alt">우편물&nbsp;수신처</td>
               <td><input type="radio" name="home" id="h" value="post" />
			       <label for="a_old">자택</label>
			       <input type="radio" name="office" id="f" value="post" />
			       <label for="a_new">근무처</label>
			       <input type="radio" name="stop" id="s" value="post" />
			       <label for="a_new">발송중지</label>
			   </td>
             </tr>
             <tr>
               <td class="alt1">학교</td>
               <td><select>
                <option value="선택">선택</option>
                <option value="선택">선택선택선택</option>       
                </select>
			   </td>
               <td class="alt">최종학력</td>
               <td><select>
                <option value="선택">선택</option>
                <option value="선택">선택선택선택</option>       
                </select>
			   </td>
			   <td class="alt">학위</td>
               <td><select>
                <option value="선택">선택</option>
                <option value="선택">선택선택선택</option>       
                </select>
			   </td>
             </tr>
			 <tr>
               <td class="alt1">경력</td>
               <td><input type="text" id="year" size="3" />년<input type="text" id="month" size="3" />월</td>
               <td class="alt">혼인여부</td>
               <td><input type="radio" name="post" id="l" value="post" />
			   <label for="a_old">기혼</label>
			   <input type="radio" name="post" id="e" value="post" />
			   <label for="a_new">미혼</label>
			   </td>
			   <td class="alt">종교</td>
               <td><select>
                <option value="선택">선택</option>
                <option value="선택">선택선택선택</option>       
                </select>
			   </td>
             </tr>
			 <tr>
               <td class="alt1">산하단체&nbsp;및&nbsp;분야회&nbsp;&nbsp;소속현황</td>
               <td><select>
                <option value="선택">선택</option>
                <option value="선택">선택선택선택</option>       
                </select>
			   </td>
               <td class="alt">발송호칭</td>
               <td><select>
                <option value="선택">선택</option>
                <option value="선택">선택선택선택</option>       
                </select>
			   </td>
			   <td class="alt">최종&nbsp;수정자</td>
               <td><input type="text" id="yearrr" size="15" /></td>
             </tr>			 
        </table>
		<p class="btnEntry"><a href="#"><img src="images/icon_entry.gif" alt="등록" /></a></p>
	  </div><!--mTabmenu_01 End-->					  
	</div><!--contents End-->	  
  </div><!--container End-->
  <div id="footer">
    <p><img src="images/copyright.gif" alt="카피라이트" /></p>
  </div>

</div><!--wrap End-->
 </form>
 </body> 
</html>
