// excel 출력의 hidden column 처리를 위한 변수
var hiddenObj = null;
var hiddenObj_title = null;

/**
 * jqGrid의 grid 부분을 excel 출력하는 함수
 * 
 * 예제) drawExcel('엑셀출력','#celltbl');
 * 
 * jsp 에 이 부분을 추가할 것.(만약 form이 여러개일 경우 이 form을 먼저 기술할 것)
 * <form method="post"> 
 *	<input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
 * </form> 
 * 
 * @param docTitle
 * @param gridId
 */
function drawExcel(docTitle, gridId) {
	var mya = new Array(); 
	mya = jQuery(gridId).getDataIDs(); 
	var data = jQuery(gridId).getRowData(mya[0]);	// Get First row to get the labels
	var colNames = new Array();  
	var ii = 0; 
	var html = ""; 

	html = html + "\t \t" + docTitle+"\n \n";

	var grid = $(gridId);

	/***************************************************************/
	/*** colModel의 properties 중에서 hidden : true 인 컬럼 처리  ***/
	/***************************************************************/
	var idx = 0;
	var model = grid.getGridParam('colModel');
//	for(var k = 0; k < model.length; k++) {
//		if (model[k].hidden == true) idx++;
//	}
	
	hiddenObj = new Array(idx);
	hiddenObj_title = new Array(idx)
	
	idx = 0;
	for(var k = 0; k < model.length; k++) {
		if (model[k].hidden == true) {		
			hiddenObj[idx] = k-1 ;
			hiddenObj_title[idx] = k ;
			idx++;
		}
	}
	/***************************************************************/
	
	// excel title 처리(property hidden 이 true 인 경우는 제외됨)
	var col = grid.getGridParam('colNames');
	for(var j = 1; j < col.length; j++) {
		if (!getHiddenColumn_title(j))
			html = html + col[j] + "\t";
	}
	html = html+"\n";
	
	for (var i in data) {
		colNames[ii++] = i;		// capture col names
	}
	
	// excel data 처리(property hidden 이 true 인 경우는 제외됨)
	for(var i = 0; i < mya.length; i++) { 
		data = jQuery(gridId).getRowData(mya[i]); 		
		for(var j = 0; j < colNames.length; j++) { 
			if (!getHiddenColumn(j))
			   html = html + data[colNames[j]] + "\t"; 
		} 
		html = html+"\n"; 		
	}
	
	html = html+"\n"; 
	
	document.forms[0].csvBuffer.value = html; 
	document.forms[0].method='POST'; 
	document.forms[0].action='excel.do'; 
//	document.forms[0].target='_blank';	// 별도 창이 띄지 않게 주석 처리함. 
	document.forms[0].submit(); 
}

// hidden column 인지를 체크하는 함수
function getHiddenColumn(num) {
	if (hiddenObj != null) {
		for(var i = 0; i < hiddenObj.length; i++) {
			if (hiddenObj[i] == num)
				return true;
		}
	}
	return false;
}

//hidden column 인지를 체크하는 함수 - title 용
function getHiddenColumn_title(num) {
	if (hiddenObj_title != null) {
		for(var i = 0; i < hiddenObj_title.length; i++) {
			if (hiddenObj_title[i] == num)
				return true;
		}
	}
	return false;
}

//str이 null, undefined, NaN, 공백("") 이면 true
function zc_isNull(str) {
	if (str+"" == "undefined" || str+"" == "NaN" || str+"" == "null" || str+"" == "")
		return true;
	return false;
} 

//input 필드 객체(obj)의 값이 정수(0~9)만 입력하였는지 확인
function zc_isNum(obj) {
	var str = obj.value;
	return zc_isNumStr(obj.value);
}

//문자열(str)이 (0-9)만으로 이루어졌는지 체크
function zc_isNumStr(str) {
	if(zc_isNull(str)) return false;
	
	var temp = "";
	var n = String(str);
	var len = n.length;
	var pos = 0;
		var ch = '';
	  	
	while (pos < len) {
		ch = n.charAt(pos);
		if ((ch >= '0') && (ch <= '9')) {
			temp = temp + ch;
		}else{					
			return false;
		}
		pos = pos + 1;
	}
	return true;
}


//오직 숫자만 입력가능하게 만드는 펑션.(onkeypress 이벤트에 호출한다)
function zu_inputNum_RT(obj){
	var keycode = event.keyCode;
	
	if( !((48 <= keycode && keycode <=57) || keycode == 13 || keycode == 45) )
		event.keyCode = 0;		// 이벤트 cancel
}

//SOSI를 고려한 문자열 길이를 실시간으로 계산하여 글자수 초과시 alert창 보임
//onkeydown  이벤트에서 호출 해야 정상 동작 함.
function zs_byteLengthRT(str,limitLen, keyCode) {
	var nLen = 0;
	var hFlag = 0;
	var iLen = str.length;
	var i = 0;
	var val = ""
	for (i = 0; i < iLen; i++) {
			tmp_chr = str.charAt(i);
			if (tmp_chr > "~") {
		 		if(hFlag == 0) {
		 			nLen = nLen + 1;
		 		}
	 		nLen = nLen + 2;
	 		hFlag = 1;
	 		if (i == iLen) {
	 			nLen = nLen + 1;
	 		}
			} else {
				if (hFlag == 1) nLen = nLen + 1;
			hFlag = 0;
				nLen = nLen + 1;
		}
			if( nLen < limitLen ) val += tmp_chr;
	}
	if (hFlag == 1) nLen = nLen + 1;
	
	if( event ){
		if( event.keyCode ==  8 || event.keyCode == 46 || event.keyCode == 45 || event.keyCode == 91 ||
			event.keyCode ==  9 || event.keyCode == 13 || event.keyCode == 16 || event.keyCode == 17 ||
			event.keyCode == 18 || event.keyCode == 92 || event.keyCode == 93 || event.keyCode == 116||
			(112 <= event.keyCode && event.keyCode <= 123 ) ||(33 <= event.keyCode && event.keyCode <= 40 ) ||
			event.keyCode == 27 
			
		){
			return str;
		}
	}	
	else{
		if( keyCode ==  8 || keyCode == 46 || keyCode == 45 || keyCode == 91 ||
			keyCode ==  9 || keyCode == 13 || keyCode == 16 || keyCode == 17 ||
			keyCode == 18 || keyCode == 92 || keyCode == 93 || keyCode == 116||
			(112 <= keyCode && keyCode <= 123 ) ||(33 <= keyCode && keyCode <= 40 ) ||
			keyCode == 27|| keyCode == 229 
			
		){
			return str;
		}
	}	
	if (nLen == limitLen) {
		alert("더 이상 입력하실 수 없습니다.\n\n입력하실 수 있는 최대 문자열 길이가 이미 입력 되셨습니다.");
		if( event ) event.returnValue = false;
	}
	else if (nLen > limitLen) {
		alert("입력하실 수 있는 최대 문자열 길이를 초과하셨습니다.\n\n 최대 입력가능 문자열 길이 => " + limitLen + "\n 입력하신 문자열 길이 => " + nLen);
		if( event && (event.srcElement.type == "text" || event.srcElement.type == "textarea" ) ) {
		    event.srcElement.value = val;
		}
		if( event ) event.returnValue = false;
	}
	return val;
}

//str의 문자열중 rmChar의 특정 문자만 삭제한 문자열을 리턴.
//rmChar는 길이 1의 문자
function zu_setDate_RT(obj) {
	var str = obj.value;
	
	if( str == null || str == "" ) return "";
	
	var data_1 = zs_rmString(str, "-");	// "-" 제거
	var retStr = "";
	
	if (data_1.length > 7)
		retStr = data_1.substring(0,4) + "-" + data_1.substring(4,6) + "-" + data_1.substring(6);
	else if (data_1.length > 4)
		retStr = data_1.substring(0,4) + "-" + data_1.substring(4);
	else
		retStr = data_1;
	
	if (data_1.length == 8) {
		if (zd_isDate(retStr))
			obj.value = retStr;
		else {
//			obj.value = "";
			obj.select();
		}
	} else
		obj.value = retStr;
}

//str의 문자열중 rmChar의 특정 문자만 삭제한 문자열을 리턴.
//	rmChar는 길이 1의 문자
function zs_rmString(str, rmChar){
	//null이 있다면	
	if(zc_isNull(str) || zc_isNull(rmChar)) return "";
	
	// 한자리의 rmChar인지 체크
	if(rmChar.length != 1){
		alert("[스크립트 사용 오류]\r\n rmStr에는 1자리의 문자만 올 수 있습니다.");
		return;
	}
	var len = str.length;
	var rtValue = "";
	
	for(var i =0 ; i < len ; i++){
		if(str.charAt(i) != rmChar && str.charAt(i) != " "){
			rtValue += str.charAt(i);
		}	
	}
	return rtValue;
}

//19000909형태, 1900-09-09형태의 날짜(str)가  올바른 날짜인지 체크
function zd_isDate(str, silent ) {
	if(zc_isNull(str)) return false;	//공백이라면
	
	var valid = true;
	var msg="";
	str =  zs_rmString(str, "-");
	var birth = str;
	var yyyy = birth.substring(0,4);
	var mm = birth.substring(4,6);
	var dd = birth.substring(6,8);
	
	if ( birth.length != 8 || !zc_isNumStr(birth) ) {
		if( !silent ) alert("올바른 일자형식이 아닙니다.\n예) 20040101");
		if( event && event.srcElement.type == "text" ) {
			event.srcElement.select();
			event.srcElement.focus();
		}
		return false;
	}

	if (!(yyyy.substring(0,2)=="19" || yyyy.substring(0,2)=="20" || yyyy.substring(0,4)=="9999")) {
		msg+="년 입력오류!\n";
		valid= false;
	}

	if (mm > 12 || mm < 1) {
		msg+="월 입력오류! (1~12)\n";
		valid =  false;
	}
	
	var chkdt = new Date( yyyy, mm-1, dd );
	if( chkdt.getYear() > 1900 && (chkdt.getYear() != yyyy || chkdt.getMonth() != (mm-1) || chkdt.getDate() != dd) ){
		if( !silent ) alert( msg+"날짜의 입력이 잘못되었습니다.");
		if( event && event.srcElement.type == "text" ) {
			event.srcElement.select();
			event.srcElement.focus();
		}
		return false;
	}
	else if( chkdt.getYear() < 1900 && ( chkdt.getYear() != yyyy.substring(2) || chkdt.getMonth() != (mm-1) || chkdt.getDate() != dd )){
		if( !silent ) alert( msg+"날짜의 입력이 잘못되었습니다.");
		if( event && event.srcElement.type == "text" ) {
			event.srcElement.select();
			event.srcElement.focus();
		}
		return false;
	}
	return true;
}

function getToDay()
{
	var date = new Date();

    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
    var day   = date.getDate();

    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }

    return ("" + year + month + day)
}

function shiftTime(time,y,m,d,h) { //moveTime(time,y,m,d,h)
   var date = toTimeObject(time);

   date.setFullYear(date.getFullYear() + y); //y년을 더함
   date.setMonth(date.getMonth() + m);       //m월을 더함
   date.setDate(date.getDate() + d);         //d일을 더함
   date.setHours(date.getHours() + h);       //h시를 더함

   return toTimeString(date);
}

function toTimeObject(time) { //parseTime(time)
   var year  = time.substr(0,4);
   var month = time.substr(4,2)-1; // 1월=0,12월=11
   var day   = time.substr(6,2);
   var hour  = time.substr(8,2);
   var min   = time.substr(10,2);

   return new Date(year,month,day,hour,min);
}

function toTimeString(date) { //formatTime(date)
    var year  = date.getFullYear();
    var month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
    var day   = date.getDate();
    var hour  = date.getHours();
    var min   = date.getMinutes();
 
    if (("" + month).length == 1) { month = "0" + month; }
    if (("" + day).length   == 1) { day   = "0" + day;   }
    if (("" + hour).length  == 1) { hour  = "0" + hour;  }
    if (("" + min).length   == 1) { min   = "0" + min;   }
 
    return ("" + year + month + day + hour + min)
}

function getCurrentTime() {
   return toTimeString(new Date());
}

function getRelativeTime(y,m,d,h) {
/*
   var date = new Date();

   date.setFullYear(date.getFullYear() + y); //y년을 더함
   date.setMonth(date.getMonth() + m);       //m월을 더함
   date.setDate(date.getDate() + d);         //d일을 더함
   date.setHours(date.getHours() + h);       //h시를 더함

   return toTimeString(date);
*/
   return shiftTime(getCurrentTime(),y,m,d,h);
}