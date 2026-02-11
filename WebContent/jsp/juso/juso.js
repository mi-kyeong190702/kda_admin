var KEY_ENTER = 13;
var orgCode='233',orgNm='대한영양사협회';

function selectSearch(mode,sel) {
	 if (mode == 'road'){
			document.location.href = "memberBasic.do?method=postSearch&sel="+sel;
		}else if(mode =='jibun'){
			document.location.href = "memberBasic.do?method=postSearch3&sel="+sel;
		}else if(mode=='sangho'){
			document.location.href = "memberBasic.do?method=postSearch4&sel="+sel;
		}		
}

function checkKeyASearch(){
	if(event.keyCode == KEY_ENTER)
		normalSearch('1');
}
function isExceptArea(code){
	//areaCode = new Array("41280", "41190", "41130", "41110", "41270", "41170", "41460", "47110", "45110", "43110", "43130");
	areaCode = new Array("고양시", "부천시", "성남시", "수원시", "안산시", "안양시", "용인시", "포항시", "전주시", "청주시","천안시");
	for(var i=0; i<areaCode.length; i++){
		element = areaCode[i];
		if(element == code){
			return true;
		}
	}
	return false;
}



function inputNumCom(obj){
	  var keycode = event.keyCode;
	  if(!((48<=keycode&&keycode<=57)||keycode==13||keycode==46)){
		alert('숫자만 입력가능합니다.');
	    event.keyCode=0;
	  }
}

function createXMLHttpRequest() {
	  if (window.ActiveXObject) {
		  if(xmlHttp != null) {
			  	xmlHttp.Abort();
			  	delete xmlHttp;
			  	xmlHttp = null;
			  }
	       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); 
	  }
	  else if (window.XMLHttpRequest) {
		  if(xmlHttp != null) {
			  	xmlHttp.abort();
			  	delete xmlHttp;
			  	xmlHttp = null;
			  }	  
	       xmlHttp = new XMLHttpRequest();
	  }
}

function handleStateChange() {

	  if(xmlHttp.readyState == 4) {
	       if(xmlHttp.status == 200)
	           	updateAreaList();
	           	delete xmlHttp;
	           	xmlHttp = null;
	       }
	  }

function setParentValue(newaddr){
	var result = newaddr.split("*");

	document.getElementById("formbox").innerHTML ="";
		document.getElementById("formbox").innerHTML += "<li class='popuproad'>도 로 명&nbsp;&nbsp;:&nbsp;<input type='text' style='inputroad' value='"+result[0]+"'></input></li>";
	document.getElementById("formbox").innerHTML += "<li class='popuproad'>상세주소 :&nbsp;<input type='text' id='detail' name ='detail' width='50px' /></li>";
	document.getElementById("formbox").innerHTML += "<li class='popuproad'>참고항목 :&nbsp;<input type='text' value='"+result[1]+"'></input></li>";
	document.getElementById("formbox").innerHTML += "<li class='popuproad'>우편번호 :&nbsp;<input type='text' id='zip' name ='zip' width='50px' value="+result[2]+" /></li>";
	document.getElementById("formbox").innerHTML += "<li class='popuproad'>※상세주소 : 동·층·호</li>";
	document.getElementById("formbox").innerHTML += "<li class='popuproad'>※참고항목 : (법정동,공동주택명)</li>";
	document.getElementById("formbox").innerHTML += "<br/><li class='inputdata'><a href='javascript:setOpenerValue(\""+ newaddr+"\")'><img src='jsp/juso/img/btn_input.gif' alt='' /></a></li>";
	//document.getElementById("formbox").innerHTML += "<br/><li class='inputdata'><img src='jsp/juso/img/test.gif' alt='' width='380px' /></li>";
	//document.getElementById("formbox").innerHTML += "<li class='inputdata'>자세한 표기법은   <a href='http://www.juso.go.kr' style='font-size: 10px' target='_blank'>도로명주소안내홈페이지</a> 를 참조하세요</li>";
 }

/*
 *	행정구역코드 리스트를 update
 */
function updateAreaList() {
  var toObj 	= document.getElementById(objTo);
  var values 	= xmlHttp.responseXML.getElementsByTagName('value');
  var names 	= xmlHttp.responseXML.getElementsByTagName('name');
  var j = 1;

  //  리스트 수만큼 option을 달아준다
  for(var i = 0; i < values.length; i++) {
		
		if(isExceptArea(names[i].firstChild.nodeValue)){
			j = j - 1;				
		}
		else{
			var option = new Option(names[i].firstChild.nodeValue, values[i].firstChild.nodeValue);
			option.title = names[i].firstChild.nodeValue;
       		toObj.options[i+j] = option;
       		
       	}
   }

  if(values.length=='0'&&toObj=='county1'){
	   alert('네트워크가 원활하지 않습니다.\n\n다시선택하여 주시기를 바립니다.');
	   return;
   }
}

function trim(str){
	var temp = str.replaceAll(" ","");
	return temp;
}

/*
All 문자치환
*/
String.prototype.replaceAll = function( searchStr, replaceStr )
{
	var temp = this;

	while( temp.indexOf( searchStr ) != -1 )
	{
		temp = temp.replace( searchStr, replaceStr );
	}

	return temp;
}


function normalSearch(mode,currentPage){
	var form = document.check;
	var keyword = "";
	var cityText='',countyText='',townText='',riText='';
	var special_pattern = /['~!@#$%^&*|\\\'\'';:\.?]/gi;
	var url;
	cityText= form.city1.options[form.city1.selectedIndex].text;
	countyText = form.county1.options[form.county1.selectedIndex].text;
	
	if(mode == 'jibun')
	{
		townText = form.town1_oldaddr.options[form.town1_oldaddr.selectedIndex].text;
		riText = form.ri1_oldaddr.options[form.ri1_oldaddr.selectedIndex].text;
	
		if(form.city1.value=='') cityText='';
		if(form.county1.value=='') countyText='';
		if(form.town1_oldaddr.value=='')	townText = '';
		if(form.ri1_oldaddr.value=='') riText = '';
		if(form.san.checked) 	form.san1.value = '1';
		else form.san1.value ='0';
	
		form.engineCtpNm.value =cityText;
		form.engineSigNm.value =countyText;
		form.engineEmdNm.value =townText;
		form.engineLiNm.value = riText;
		form.engineBdMaSn.value= form.bun1.value;
		form.engineBdSbSn.value = form.bun2.value;
		form.engineMtYn.value= form.san1.value;
		form.currentPage.value =currentPage;
	
		if(cityText == ''){
			alert('시군구를 입력하여 주세요');
			return;
		}
		url = "memberBasic.do?method=postAjax&getUrl="+escape("http://www.juso.go.kr/link/search.do?extend=false&mode=jibun_search&searchType=location_jibun&topTab=1&engineCtpNm="+encodeURI(cityText)+"&engineSigNm="+encodeURI(countyText)+"&engineEmdNm="+encodeURI(townText)+"&engineLiNm="+encodeURI(riText)+"&engineBdMaSn="+encodeURI(form.bun1.value)+"&engineBdSbSn="+encodeURI(form.bun2.value)+"&engineMtYn="+encodeURI(form.san1.value)+"&currentPage="+currentPage+"&orgCode="+orgCode+"&orgNm="+encodeURI(orgNm));
	}
	else if(mode=='road')
	{
		if(form.city1.value=='') cityText='';
		if(form.county1.value=='') countyText='';
		if(form.rd_nm1.value=='') {alert("도로명을 입력하세요!"); form.rd_nm1.focus(); return;}
		
		form.engineCtpNm.value = cityText;
		form.engineSigNm.value = countyText;
		form.engineRdNm.value = trim(form.rd_nm1.value);
		form.engineBdMaSn.value = form.ma.value;
		form.engineBdSbSn.value = form.sb.value;
		form.currentPage.value = currentPage;		

		url = "memberBasic.do?method=postAjax&getUrl="+escape("http://www.juso.go.kr/link/search.do?extend=true&mode=road_search&searchType=location_newaddr&topTab=1&engineCtpNm="+encodeURI(cityText)+"&engineSigNm="+encodeURI(countyText)+"&engineRdNm="+encodeURI(trim(form.rd_nm1.value))+"&engineBdMaSn="+encodeURI(form.ma.value)+"&engineBdSbSn="+encodeURI(form.sb.value)+"&currentPage="+currentPage+"&orgCode="+orgCode+"&orgNm="+encodeURI(orgNm));
	}
	else if(mode=='sangho')
	{
		townText = form.town1_oldaddr.options[form.town1_oldaddr.selectedIndex].text;

		if(form.city1.value=='') cityText='';
		if(form.county1.value=='') countyText='';
		if(form.town1_oldaddr.value=='')	townText = '';
		if(form.bdnm.value=='') {alert("건물명을 입력하세요!"); form.bdnm.focus(); return;}

		form.engineCtpNm.value =cityText;
		form.engineSigNm.value =countyText;
		form.engineEmdNm.value =townText;
		form.engineBdNm.value = trim(form.bdnm.value);
		form.currentPage.value =currentPage;
		
		url = "memberBasic.do?method=postAjax&getUrl="+escape("http://www.juso.go.kr/link/search.do?extend=true&mode=road_search&searchType=location_buld&topTab=1&engineCtpNm="+encodeURI(cityText)+"&engineSigNm="+encodeURI(countyText)+"&engineEmdNm="+encodeURI(townText)+"&engineBdNm="+encodeURI(trim(form.bdnm.value))+"&currentPage="+currentPage+"&orgCode="+orgCode+"&orgNm="+encodeURI(orgNm));
	}
	else
	{
		return false;
	}
	createXMLHttpRequest();

 	xmlHttp.onreadystatechange = handleStateChangeSearch;
  	xmlHttp.open("GET", url, true);
  	xmlHttp.send(null);

}

function normalSearch2(mode,currentPage){
	var url;
	var form = document.check;

	form.currentPage.value =currentPage;

	currentPage=parseInt((currentPage/10)+1);

	if(mode == 'jibun')
	{
	 url = "memberBasic.do?method=postAjax&getUrl="+escape("http://www.juso.go.kr/link/search.do?extend=false&mode=jibun_search&searchType=location_jibun&topTab=1&engineCtpNm="+encodeURI(form.engineCtpNm.value)+"&engineSigNm="+encodeURI(form.engineSigNm.value)+"&engineEmdNm="+encodeURI(form.engineEmdNm.value)+"&engineLiNm="+encodeURI(form.engineLiNm.value)+"&engineBdMaSn="+form.engineBdMaSn.value+"&engineBdSbSn="+form.engineBdSbSn.value+"&engineMtYn="+form.engineMtYn.value+"&currentPage="+currentPage+"&orgCode="+orgCode+"&orgNm="+encodeURI(orgNm));
	}else if(mode =='road')
	{
		url = "memberBasic.do?method=postAjax&getUrl="+escape("http://www.juso.go.kr/link/search.do?extend=true&mode=road_search&searchType=location_newaddr&topTab=1&engineCtpNm="+encodeURI(form.engineCtpNm.value)+"&engineSigNm="+encodeURI(form.engineSigNm.value)+"&engineRdNm="+encodeURI(trim(form.engineRdNm.value))+"&engineBdMaSn="+encodeURI(form.engineBdMaSn.value)+"&engineBdSbSn="+encodeURI(form.engineBdSbSn.value)+"&currentPage="+currentPage+"&orgCode="+orgCode+"&orgNm="+encodeURI(orgNm));
	}else if(mode == 'sangho')
	{
		url = "memberBasic.do?method=postAjax&getUrl="+escape("http://www.juso.go.kr/link/search.do?extend=true&mode=road_search&searchType=location_buld&topTab=1&engineCtpNm="+encodeURI(form.engineCtpNm.value)+"&engineSigNm="+encodeURI(form.engineSigNm.value)+"&engineEmdNm="+encodeURI(form.engineEmdNm.value)+"&engineBdNm="+encodeURI(trim(form.engineBdNm.value))+"&currentPage="+currentPage+"&orgCode="+orgCode+"&orgNm="+encodeURI(orgNm));
	}
	else
	{
		return false;
	}

	createXMLHttpRequest();

 	xmlHttp.onreadystatechange = handleStateChangeSearch;
  	xmlHttp.open("GET", url, true);
  	xmlHttp.send(null);
	
	
}




/*
회원가입창으로 파라미터 전송
*/
function setOpenerValue(newaddr){
	 var result = newaddr.split("*");
	 if(result[3]==1){
		 window.opener.document.getElementById("m_address").value = result[0];
		 window.opener.document.getElementById("d_address").value = document.getElementById("detail").value+" "+result[1];
		 window.opener.document.getElementById("m_post").value = result[2];
		 window.opener.document.getElementById("a_new").checked = true; 
	 }else if(result[3]==2){
		 window.opener.document.getElementById("cAddr").value = result[0];
		 window.opener.document.getElementById("cAddrd").value = document.getElementById("detail").value+" "+result[1];
		 window.opener.document.getElementById("cPost").value = result[2];		  
	 }else if(result[3]==3){
		 window.opener.document.getElementById("pers_add").value = result[0];
		 window.opener.document.getElementById("pers_add_detail").value = document.getElementById("detail").value+" "+result[1];
		 window.opener.document.getElementById("code_post").value = result[2];
		 window.opener.document.getElementById("aNew").checked = true;
	 }else if(result[3]==4){
		 window.opener.document.getElementById("company_add").value = result[0];
		 window.opener.document.getElementById("company_add_detail").value = document.getElementById("detail").value+" "+result[1];
		 window.opener.document.getElementById("company_code_post").value = result[2];		  
	 }else if(result[3]==5){ //홈페이지 회원가입 자택주소
		 document.getElementById("pers_add").value = result[0];
		 document.getElementById("pers_add_detail").value = document.getElementById("detail").value+" "+result[1];
		 document.getElementById("code_post1").value = result[2].substr(0,3);
		 document.getElementById("code_post2").value = result[2].substr(3,3);
		 document.getElementById("add_road").value="2";
	 }else if(result[3]==6){ //기타발송자
		 window.opener.document.getElementById("m_address").value = result[0];
		 window.opener.document.getElementById("d_address").value = document.getElementById("detail").value+" "+result[1];
		 window.opener.document.getElementById("m_post").value = result[2];
	 }else if(result[3]==7){
		 window.opener.document.getElementById("pers_add").value = result[0]+" "+document.getElementById("detail").value+" "+result[1];;
		 //window.opener.document.getElementById("pers_add_detail").value = document.getElementById("detail").value+" "+result[1];
		 window.opener.document.getElementById("code_post1").value = result[2].substr(0,3);
		 window.opener.document.getElementById("code_post2").value = result[2].substr(3,3);
	 }
	 window.close();
}