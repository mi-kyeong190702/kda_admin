

/**************************************************************
*
* Program ID	: form.js (version 1.0)
* Description	: Javascript Functions
* Written by/on	: 김성훈
**************************************************************
* check_StrByte	: 글자길이 제한 체크			onKeyUp="check_StrByte(this, 10);"
* fn_IME				: 한영자동변환(KOR,ENG)		onFocus="fn_IME(this,'KOR');"
* 
* check_JuminNo	: 주민번호 입력함수			onkeyup="javascript:check_JuminNo('comp','pers_no');" // JUMIN_DEL
* 
* check_PostNo	: 우편번호 입력함수			onkeyup="javascript:check_PostNo('comp','code_post');"
* check_Int		: 자연수형만 입력				onKeyUp="javascript:check_Int('comp','id');"
* check_Eng		: 알파벳형만 입력				onKeyUp="javascript:check_Eng('comp','id');"
* check_EngInt	: 알파벳, 숫자형만 입력		onKeyUp="javascript:check_EngInt('comp','id');"
* check_IntEng	: 알파벳, 숫자형만 입력		onKeyUp="javascript:check_IntEng('comp','id');"
* 
* ex : onkeyup="javascript:check_EngInt('comp','id'); return check_StrByte(this, 20);"
*       글자길이 제한 체크와 **형만 입력 을 같이 사용할때는 글자길이 제한 체크를 먼저 해주세요.
**************************************************************/


function powerinit(meprogid, userpowerm1){

 var bcheck = "N";
// alert(""userpowerm1[i].progid===>""+userpowerm1.length);
// alert(userpowerm1);
 for(i=0;i<userpowerm1.length;i++){
  var setprogid=userpowerm1[i].progid;
  //alert(""찾기.=======>""+setprogid);
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

/**********************************
* 글자길이 제한 체크
* 사용법 :  	onKeyUp='check_StrByte(this, 10);'
* @param		폼
* @param		최대글자수
* @return  	T/F
**********************************/
function check_StrByte(obj, maxnum)
{
	
	var val = obj.value;
	var str=0;
	for(var i=0; i<val.length; i++) str+=(val.charCodeAt(i) > 128 ? 2  : 1);
	//alert("입력값 = "+val+"  입력값 길이="+val.length+"   최대값/바이트화 = "+maxnum+"/"+str);
	if( str > maxnum) {
		alert("" +maxnum + "바이트 이상은 입력 하실수 없습니다.");
		obj.value = val.substring(0, val.length-1);
		return false;
	}
	return true;
}

//
/**********************************
* 한영자동변환
* 사용법 : onfocus="fn_IME(this,'KOR');" or onfocus="fn_IME(this,'ENG');
* @param		KR:한 EN:영
* @return  	N/A
**********************************/
function fn_IME(obj, lang)
{
	  obj.style.imeMode = (lang == "ENG" ? "inactive":"active");
}

// JUMIN_DEL
///**********************************
//* 주민번호 입력함수
//* 사용법 : check_JuminNo(
//* @param	폼명, 컴포넌트명
//* @return  	null
//**********************************/
//function check_JuminNo(formNm, colNm) {
//
//	var gForm = formNm + "." + colNm;
//	obj	= eval(gForm);
//	
//	var val = obj.value;
//	var num=/[^0-9^-]/;
//	
//	if (num.test(val)) {
//		obj.value = val.replace(/[^0-9^-]/gi, "");
//	}
//	
//	if(val.length == 6)		obj.value+="-";
//}

/**********************************
* 우편번호 입력함수
* 사용법 : 
* @param	폼명, 컴포넌트명
* @return  	null
**********************************/
function check_PostNo(formNm, colNm) {

	var gForm = formNm + "." + colNm;
	obj	= eval(gForm);
	
	var val = obj.value;
	var num=/[^0-9^-]/;
	
	if (num.test(val)) {
		obj.value = val.replace(/[^0-9^-]/gi, "");
	}
	
	if(val.length == 3){
		obj.value+="-";

	}
}

/**********************************
* 자연수형 입력함수
* 사용법 : onKeyUp="javascript:check_Int('comp','id');
* @param	폼명, 컴포넌트명
* @return  	null
**********************************/
function check_Int(formNm, colNm) {

	var gForm = formNm + "." + colNm;
	obj	= eval(gForm);

	var val = obj.value;
	var num=/[^0-9]/;

	if (num.test(val)) {
		obj.value = val.replace(/[^0-9]/gi, "");
	}
}

/**********************************
* 알파벳형 입력함수
* 사용법 : onKeyUp="javascript:check_Eng('comp','id');
* @param	폼명, 컴포넌트명
* @return  	null
**********************************/
function check_Eng(formNm, colNm) {

	var gForm = formNm + "." + colNm;
	obj	= eval(gForm);

	var val = obj.value;
	var num=/[^a-z|A-Z]/;

	if (num.test(val)) {
		obj.value = val.replace(/[^a-z|A-Z]/gi, "");
	}
}

/**********************************
* 알파벳, 숫자형 입력함수
* 사용법 : onKeyUp="javascript:check_EngInt('comp','id');
* @param	폼명, 컴포넌트명
* @return  	null
**********************************/
function check_EngInt(formNm, colNm) {

	var gForm = formNm + "." + colNm;
	obj	= eval(gForm);

	var val = obj.value;
	var num=/[^a-z|A-Z|0-9]/;

	if (num.test(val)) {
		obj.value = val.replace(/[^a-z|A-Z|0-9]/gi, "");
	}
}

/**********************************
* 알파벳, 숫자형 입력함수
* 사용법 : onKeyUp="javascript:check_IntEng('comp','id');
* @param	폼명, 컴포넌트명
* @return  	null
**********************************/
function check_IntEng(formNm, colNm) {

	var gForm = formNm + "." + colNm;
	obj	= eval(gForm);

	var val = obj.value;
	var num=/[^a-z|A-Z|0-9]/;

	if (num.test(val)) {
		obj.value = val.replace(/[^a-z|A-Z|0-9]/gi, "");
	}
}

/**********************************
* 한글형 입력함수
* @param	폼명, 컴포넌트명
* @return  	null
**********************************/
function check_Han(formNm, colNm) {

	var gForm = formNm + "." + colNm;
	obj	= eval(gForm);

	var val = obj.value;
	var num=/[^a-z|A-Z|0-9]/;

	if (num.test(val)) {
		obj.value = val.replace(/[^a-z|A-Z|0-9]/gi, "");
	}
}
/*

//키 입력시 한,영,숫자 등 제어하기
function check_HanEng(obj, event) {

	var ev=window.event||event;
	if('8'==ev.keyCode) return true;	//백스페이스
	if('16'==ev.keyCode) return true;	//쉬프트
	if('37'<=ev.keyCode&&ev.keyCode<='40') return true //화살표
	if('46'==ev.keyCode) return true;	//딜리트
	
	if('229'==ev.keyCode||ev.keyCode=='197') return true;
	if('65'<=ev.keyCode&&ev.keyCode<='90') return true;	//소문자
	if('97'<=ev.keyCode&&ev.keyCode<='122') return true;	//대문자
	if('48'<=ev.keyCode&&ev.keyCode<='57') return true;	//숫자키
	if('96'<=ev.keyCode&&ev.keyCode<='108') return true;	//숫자패드

	var val = obj.value;
	var str=0;
	obj.value = val.substring(0, val.length-1);
	
}*/