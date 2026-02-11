
function checkJumin(form){
	//주민 번호 유호성 검사
	var jmca = [2,3,4,5,6,7,8,9,2,3,4,5];
	var jmcs = form.pers_no.value;
	if (jmcs.length == 0)
	{
		alert('주민등록번호를 입력하세요.');
		form.pers_no.focus();
		return;
	}else if(jmcs.length == 13){	
		var jmc = 0;
		for(var i=0; i<jmca.length; i++) {
		  jmcsc = jmcs.substr(i,1);
		  jmc = jmc + jmca[i]*jmcsc;
		}
		var jmch = String(11-(jmc%11));
		jmch = jmch.substr(jmch.length-1,2);
		if(jmch != jmcs.substr(12,1)) {
		  alert('존재하지 않는 주민번호입니다');
			form.pers_no.value="";
			form.pers_no.focus();
		  return;
		}
		alert('유효한 주민등록번호입니다.'); 
	}else{
		alert('주민등록번호는 13자리여야 합니다.');
		form.pers_no.value="";
		form.pers_no.focus();
		return;
	}

	if( jmcs[6] == '1' || jmcs[6] == '3' ) {
		form.code_sex.male.checked	= true;
	}else	if( jmcs[6] == '2' || jmcs[6] == '4' ) {
		form.code_sex.female.checked = true;
	}
	form.isCkPersNo.value="Y";
}


//키 입력시 한,영,숫자 등 제어하기
function onKeyDown(form, type, event) {
	
	var ev=window.event||event;

	if('8'==ev.keyCode) return true;	//백스페이스
	if('9'==ev.keyCode) return true;	//tab
	if('16'==ev.keyCode) return true;	//쉬프트
	if('37'<=ev.keyCode&&ev.keyCode<='40') return true //화살표
	if('46'==ev.keyCode) return true;	//딜리트
	
	if('13'==ev.keyCode ){	//엔터이면
		return;
	}
	
	 if(type=='han') {		//한글만 입력
			if('229'==ev.keyCode||ev.keyCode=='197') return true;
	//		alert("한글만 입력 가능합니다.");
			return false;
			
	}else if(type=='eng') {	//영어만 입력가능
	//	alert(ev.keyCode);
		if('65'<=ev.keyCode&&ev.keyCode<='90') return true;	//소문자
		if('97'<=ev.keyCode&&ev.keyCode<='122') return true;	//대문자
	//		alert("영어만 입력 가능합니다.");
		return false;
	
	}else if(type=='num') {		//숫자만 입력
		if('48'<=ev.keyCode&&ev.keyCode<='57') return true;	//숫자키
		if('96'<=ev.keyCode&&ev.keyCode<='108') return true;	//숫자패드
//		alert("숫자만 입력 가능합니다.");
		return false;
				
	}else if(type=='haneng' || type=='enghan') {	//영어와 한글 입력가능
		if('229'==ev.keyCode||ev.keyCode=='197') return true;
		if(('65'<=ev.keyCode&&ev.keyCode<='90') || ('97'<=ev.keyCode&&ev.keyCode<='122')) return true;
//		alert("영어와 한글만 입력 가능합니다.");
		return false;
				
	}else if(type=='jumin') {
		
		if(('48'<=ev.keyCode&&ev.keyCode<='57')||('96'<=ev.keyCode&&ev.keyCode<='108')) {
			
			return true;	//숫자키
		}
		
	}else if(type=='licno') {
		form.isCkLicNo.value = "";
		if(('48'<=ev.keyCode&&ev.keyCode<='57')||('96'<=ev.keyCode&&ev.keyCode<='108')) {
			
			return true;	//숫자키
		}
		
	}
}


//탭 처리
function tab_1(form) {
	/* var tab = document.getElementById(obj);
	if( obj.style.display = 'none') obj.style.display = ''; */
/* 	document.getElementById("name_1").className="overMenu";
	document.getElementById("name_2").className=""; */

	//탭 상단 선택 처리
	document.all.name_1.className = "overMenu";
	document.all.name_2.className = "";

	//기본정보 근무처정보 변경
	document.all.tab_2.style.display = "none";
	document.all.tab_1.style.display = "";
}
function tab_2(form) {

	//탭 상단 선택 처리
	document.all.name_1.className = "";
	document.all.name_2.className = "overMenu";

	//기본정보 근무처정보 변경
	document.all.tab_1.style.display = "none";
	document.all.tab_2.style.display = "";
}