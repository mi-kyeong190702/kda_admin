
var contextValue = "/kda";

function chtrOver(obj){ // 마우스 오버
 // alert(obj);
 obj.style.backgroundColor="#F1F1F1";
 }

 function chtrOut(obj){ // 마우스 아웃
 obj.style.backgroundColor="#FFFFFF";
 }										
 
 function chtrOver2(obj){ // 마우스 오버
 // alert(obj);
 obj.style.backgroundColor="#D3D3D3";
 }
 
function chtrOut2(obj){ // 마우스 아웃
 obj.style.backgroundColor="#D3D3D3";
}


// ����⵵�� �������� ��Ȱ��ȭ
function fnAuthYearSwitch()
{
	frm = document.forms[0];
	var length = frm.selectPresLetYear.length;
	frm.selectPresLetYear[length-1].selected = true;
	frm.selectPresLetYear.disabled = true;
}

// ���س⵵ ���� ������ ���ȸ�� �˻� 
function fnDepositLifeUpdateAuthSearch()
{
	frm = document.forms[0];

	// �����⵵
	var receipt_year = frm.selectPresLetYear.value;
	frm.all["lifeMemberDepositVO.receipt_year"].value = receipt_year;

	// ����
	frm.all["lifeMemberDepositVO.code_bran"].value = frm.selectBranch.value;
	
	frm.target = "if_detail";
	frm.action = contextValue + '/Deposit.do?method=depositLifeUpdateAuthListWait';
	
	frm.submit();
	frm.target = "";
}

// ���س⵵ ���� ���� ��
function fnDepositLifeYearAuthForm()
{
	frm = document.forms[0];

	var count = 0;
    var pers_no_list = "";
     
    if(frm.param != null)
    {
	    //����Ʈ�� �ϳ���
	    if(frm.param.length == null){
	    	
	    	if (frm.param.checked)
	    	{
	    		pers_no_list = "'" + frm.param.value + "',";
	    		count++;
	    	}
	    }
	    else
	    {
	        for(i=0; i<frm.param.length ; i++)
	        {
	            if (frm.param[i].checked)
	            {
	            	pers_no_list += "'" + frm.param[i].value + "',";
		    		count++;
		    	}
	        }
	    }	
    }
    
	if( count == 0 )
	{
		alert('인증할 평생 회원을 선택하십시오.');
		return;
	}

	frm.all["lifeMemberDepositVO.pers_no_list"].value = "(" + pers_no_list.substring(0, pers_no_list.length-1) + ")";
	
	// �˾�â
	var w_name = "newWin"; 
	var url = ""; 
	var myheight = 200; 
	var mywidth = 320; 
	var winheight = window.screen.height; 
	var winwidth = window.screen.width; 
	var mytop = ( winheight - myheight ) / 2; 
	var myleft = ( winwidth - mywidth ) / 2; 
	popup=window.open('',w_name,'resizable=no,width='+mywidth+',height='+myheight+', top='+mytop+', left='+myleft+','+',status=yes'); 
	frm.action = contextValue + '/Deposit.do?method=depositLifeYearAuthForm';
	
	frm.target = w_name;
	frm.submit();
	frm.target = "";	
	frm.all["lifeMemberDepositVO.pers_no_list"].value = "";
}

// ���κ� ������ ��Ȳ �˻�
function fnDepositStatusSearch()
{
	frm = document.forms[0];

	// ���� ����
	if( IsEmpty(frm.start_pers_mem_day.value) )
	{
		alert('시작 일자를 입력하십시오!');
		frm.start_pers_mem_day.focus();	
		frm.action = "";
		return;
	}
	else
	{
		var str = frm.start_pers_mem_day.value;
		if( str.length != 10 )
		{
			alert("설정하신 시작 일자는 YYYY-MM-MM에 맞지 않습니다.");
			frm.start_pers_mem_day.focus();				
			frm.action = "";
			return;					
		}
		
		frm.all["statDepositInfoVO.start_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}
	
	// 마지막 일자
	if( IsEmpty(frm.end_pers_mem_day.value) )
	{
		alert('마지막 일자를 입력하십시오!');
		frm.end_pers_mem_day.focus();		
		frm.action = "";
		return;
	}
	else
	{
		var str = frm.end_pers_mem_day.value;
		if( str.length != 10 )
		{
			alert("설정하신 마지막 일자는 YYYY-MM-MM에 맞지 않습니다.");
			frm.end_pers_mem_day.focus();
			frm.action = "";
			return;					
		}
		
		frm.all["statDepositInfoVO.end_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}	

	frm.target = "if_detail";
		
	// �Ա����
	frm.all["statDepositInfoVO.kdaType"].value = frm.kdaType.value;
		
	// ����
	frm.all["statDepositInfoVO.code_bran"].value = frm.selectBranch.value;
	frm.action = contextValue + '/Deposit.do?method=depositStatusListWait';
	frm.submit();
	frm.target = "";
}

// ���ȸ�� ������ �� > ������ ��
function fnDepositLifeNonAuthSearch()
{
	frm = document.forms[0];

	// �ϳ��⵵
	var pers_mem_year = frm.selectPresLetYear.value;
	frm.all["lifeMemberDepositVO.pers_mem_year"].value = pers_mem_year;

	// ���� ����
	if( IsEmpty(frm.start_pers_mem_day.value) )
	{
		alert('완납 시작 일자를 입력하십시오!');
		frm.start_pers_mem_day.focus();	
		frm.action = "";
		return;
	}
	else
	{
		var str = frm.start_pers_mem_day.value;
		if( str.length != 10 )
		{
			alert("설정하신 완납 시작 일자는 YYYY-MM-MM에 맞지 않습니다.");
			frm.start_pers_mem_day.focus();				
			frm.action = "";
			return;					
		}

		if( pers_mem_year != str.substring(0,4) )
		{
			alert("설정하신 완납 시작 일자는 완납년도와 일치하지 않습니다.");
			frm.start_pers_mem_day.focus();						
			frm.action = "";
			return;				
		}
		
		frm.all["lifeMemberDepositVO.start_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}
	
	// ������ ����
	if( IsEmpty(frm.end_pers_mem_day.value) )
	{
		alert('완납 마지막 일자를 입력하십시오!');
		frm.end_pers_mem_day.focus();		
		frm.action = "";
		return;
	}
	else
	{
		var str = frm.end_pers_mem_day.value;
		if( str.length != 10 )
		{
			alert("설정하신 완납 마지막 일자는 YYYY-MM-MM에 맞지 않습니다.");
			frm.end_pers_mem_day.focus();
			frm.action = "";
			return;					
		}

		if( pers_mem_year != str.substring(0,4) )
		{
			alert("설정하신 완납 마지막 일자는 완납년도와 일치하지 않습니다.");;
			frm.end_pers_mem_day.focus();
			frm.action = "";
			return;				
		}
		
		frm.all["lifeMemberDepositVO.end_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}	

	// ����
	frm.all["lifeMemberDepositVO.code_bran"].value = frm.selectBranch.value;
	
	frm.target = "if_detail";
	frm.action = contextValue + '/Deposit.do?method=depositLifeNonAuthListWait';
	
	frm.submit();
	frm.target = "";
}

// ���ȸ�� ���κ� ������ ��Ȳ �˻�
function fnDepositLifeStatusSearch()
{
	frm = document.forms[0];

	// �ϳ��⵵
	var pers_mem_year = frm.selectPresLetYear.value;
	frm.all["lifeMemberDepositVO.pers_mem_year"].value = pers_mem_year;

	var displayType = frm.displayType.value;	
	frm.target = "if_detail";
			

	// ����
	frm.all["lifeMemberDepositVO.pers_mem_order"].value = frm.selectPresLetOrder.value;
	
	// ����
	frm.all["lifeMemberDepositVO.code_bran"].value = frm.selectBranch.value;
	

	if( displayType == 'status' )
	{
		frm.action = contextValue + '/Deposit.do?method=depositLifeStatusListWait';
	}
	else if( displayType == 'roaster' )
	{

		if( frm.selectPresLetOrder.value == -1 )
		{
			alert('차수를 선택하십시오.');
			frm.selectPresLetOrder.focus();
			frm.target = "";
			return;
		}
		/*
		if( frm.selectBranch.value == -1 )
		{
			alert('���θ� �����Ͻʽÿ�.');
			frm.selectBranch.focus();
			frm.target = "";
			return;
		}
		*/	
	
		frm.action = contextValue + '/Deposit.do?method=depositLifeRosterListWait';
	}
	
	frm.submit();
	frm.target = "";
}


/**
 * Search
 */
function fnDepositLifeAuthForm()
{
	frm = document.forms[0];

	var count = 0;
    var pers_no_list = "";
     
    if(frm.param != null)
    {
	    //����Ʈ�� �ϳ���
	    if(frm.param.length == null){
	    	
	    	if (frm.param.checked)
	    	{
	    		pers_no_list = "'" + frm.param.value + "',";
	    		count++;
	    	}
	    }
	    else
	    {
	        for(i=0; i<frm.param.length ; i++)
	        {
	            if (frm.param[i].checked)
	            {
	            	pers_no_list += "'" + frm.param[i].value + "',";
		    		count++;
		    	}
	        }
	    }	
    }
    
	if( count == 0 )
	{
		alert('인증할 평생 회원을 선택하십시오.');
		return;
	}

	frm.all["lifeMemberDepositVO.pers_no_list"].value = "(" + pers_no_list.substring(0, pers_no_list.length-1) + ")";
	
	// �˾�â
	var w_name = "newWin"; 
	var url = ""; 
	var myheight = 200; 
	var mywidth = 320; 
	var winheight = window.screen.height; 
	var winwidth = window.screen.width; 
	var mytop = ( winheight - myheight ) / 2; 
	var myleft = ( winwidth - mywidth ) / 2; 
	popup=window.open('',w_name,'resizable=no,width='+mywidth+',height='+myheight+', top='+mytop+', left='+myleft+','+',status=yes'); 
	frm.action = contextValue + '/Deposit.do?method=depositLifeAuthForm';
	
	frm.target = w_name;
	frm.submit();
	frm.target = "";	
	frm.all["lifeMemberDepositVO.pers_no_list"].value = "";
}




function fnDepositLifeStatusSwitch()
{
	frm = document.forms[0];

	var displayType = frm.displayType.value;
	
	if( displayType == "status" )
	{
		frm.selectPresLetOrder.disabled = true;
		frm.selectBranch.disabled = true;
	}
	else if( displayType == "roaster" )
	{
		frm.selectPresLetOrder.disabled = false;
		frm.selectBranch.disabled = false;
	}	
}

function fnSwitchPresLetDay()
{
	frm = document.forms[0];
	var pers_mem_year = frm.selectPresLetYear.value;
	
	frm.start_pers_mem_day.value = pers_mem_year + "-01-01";
	frm.end_pers_mem_day.value = pers_mem_year + "-12-31";
	
	if( pers_mem_year == '2007' )
	{
		frm.start_pers_mem_day.disabled = true;
		frm.end_pers_mem_day.disabled = true;
	}
	else
	{
		frm.start_pers_mem_day.disabled = false;
		frm.end_pers_mem_day.disabled = false;
	}
}

 // ���� �ٿ�ε�
 function fnDownLoadExcel()
 {
 	frm = document.forms[0];

	// �˾�â 
	var w_name = "newWin"; 
	var url = ""; 
	var myheight = 180; 
	var mywidth = 320; 
	var winheight = window.screen.height; 
	var winwidth = window.screen.width; 
	var mytop = ( winheight - myheight ) / 2; 
	var myleft = ( winwidth - mywidth ) / 2; 
	popup = window.open('',w_name,'resizable=no,width='+mywidth+',height='+myheight+', top='+mytop+', left='+myleft+','+',status=yes'); 
	frm.action = contextValue + '/Deposit.do?method=excelDownLoad';
	frm.target = w_name;
	frm.submit();
	frm.target = "";
 }	
 
/**
 * ������ ���ǥ 
 */
function fnDepositTotalSearch(){
	frm = document.forms[0];

	frm.all["statDepositInfoVO.kdaType"].value = frm.kdaType.value;
	
	// ���� ����
	if( IsEmpty(frm.start_day.value) )
	{
		frm.all["statDepositInfoVO.start_pers_mem_day"].value = '00000000';	
	}
	else
	{
		var str = frm.start_day.value;
		if( str.length != 10 )
		{
			alert("YYYY-MM-MM에 맞지 않습니다.");
			frm.action = "";
			return;					
		}

		frm.all["statDepositInfoVO.start_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}
	
	// ������ ����
	if( IsEmpty(frm.end_day.value) )
	{
		frm.all["statDepositInfoVO.end_pers_mem_day"].value = '99999999'	
	}
	else
	{
		var str = frm.end_day.value;
		if( str.length != 10 )
		{
			alert("YYYY-MM-MM에 맞지 않습니다.");
			frm.action = "";
			return;					
		}
		
		frm.all["statDepositInfoVO.end_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}
	
	frm.target = "if_detail";
	frm.action = contextValue + '/Deposit.do?method=depositTotalListWait';
	frm.submit();
	
	// �ʱ�ȭ 
	frm.target = "";
}	


/**
 * ������ ���ǥ - ����
 */
function fnDepositTotalSearchToExcel(){
	frm = parent.document.forms[0];

	frm.all["statDepositInfoVO.kdaType"].value = frm.kdaType.value;
	
	// ���� ����
	if( IsEmpty(frm.start_day.value) )
	{
		frm.all["statDepositInfoVO.start_pers_mem_day"].value = '00000000';	
	}
	else
	{
		var str = frm.start_day.value;
		if( str.length != 10 )
		{
			alert("YYYY-MM-MM에 맞지 않습니다.");
			frm.action = "";
			return;					
		}

		frm.all["statDepositInfoVO.start_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}
	
	// ������ ����
	if( IsEmpty(frm.end_day.value) )
	{
		frm.all["statDepositInfoVO.end_pers_mem_day"].value = '99999999'	
	}
	else
	{
		var str = frm.end_day.value;
		if( str.length != 10 )
		{
			alert("YYYY-MM-MM에 맞지 않습니다.");
			frm.action = "";
			return;					
		}
		
		frm.all["statDepositInfoVO.end_pers_mem_day"].value = str.substring(0,4) + str.substring(5,7) + str.substring(8);
	}
	
	// �˾�â
	var w_name = "newWin"; 
	var url = ""; 
	var myheight = 180; 
	var mywidth = 320; 
	var winheight = window.screen.height; 
	var winwidth = window.screen.width; 
	var mytop = ( winheight - myheight ) / 2; 
	var myleft = ( winwidth - mywidth ) / 2; 
	popup = window.open('',w_name,'resizable=no,width='+mywidth+',height='+myheight+', top='+mytop+', left='+myleft+','+',status=yes'); 
	frm.action = contextValue + '/Deposit.do?method=depositTotalListToExcel';
	frm.target = w_name;
	frm.submit();
	frm.target = "";	
}	
/**
 * Field Check
 */
function IsEmpty(str){
	for(var intLoop = 0; intLoop < str.length; intLoop++)
		if(str.charAt(intLoop) != " ")
			return false;
			return true;
}

/**
 * Tab Link
 */
function fnShowTabTable(id)
{
	// Tab Link
	var mylist = document.getElementById("memtabList");
	var listItems = mylist.getElementsByTagName("li");

	for(i=0;i<listItems.length;i++){
		if (i == id){
			listItems[i].id = "current";
		}else{
			listItems[i].id = "";
		}
	}	
	
	// Tab table Link
	for(i=0; i<listItems.length; i++)
	{
		eval("tabTable"+i+".style.display = 'none'");
	}
	eval("tabTable"+id+".style.display = ''");
}

/**
 * Ckeck All
 */
function MainCheck(){

    var frm = document.forms[0];
    var dcodeCount = 0;

    //����Ʈ�� ������
    if(frm.param == null) {
    	alert("목록이 존재 하지 않습니다!");
    	return false;
    }
        
    //����Ʈ�� �ϳ���
    if(frm.param.length == null){
    	
    	if (frm.param.checked == true){
    		frm.param.checked = false;
    	}else{
    		frm.param.checked = true;
    	}
        dcodeCount++;
    }else{
        for(i=0 ; i<frm.param.length ; i++){
            if (frm.param[i].checked == true){
	    		frm.param[i].checked = false;
	    	}else{
	    		frm.param[i].checked = true;
	    		dcodeCount++;
	    	}
        }
    }
}
