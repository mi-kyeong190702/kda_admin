<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<title>대한영양사협회</title>

<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/document.css" />
<script type="text/javascript" src="js/comm.js"></script>

<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@ page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%@page import="com.ant.document.dao.documentDao"%>

<!-- 날짜 표시를 위한 스크립트   -->
    <script src="js/jquery.ui.core.js"></script>
    <script src="js/jquery.ui.widget.js"></script>
    <script src="js/jquery.ui.datepicker.js"></script>
    
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
</style>

<%
String g_name = session.getAttribute("G_NAME").toString(); 
String g_bran = session.getAttribute("G_BRAN").toString(); 
JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");
%>

<%
    String logid="";
    if(session.getAttribute("G_NAME")!=null){
        logid=session.getAttribute("G_NAME").toString();
    }
    String errMsg="";
    if(request.getAttribute("errMsg")!=null){
        errMsg=request.getAttribute("errMsg").toString();
    }
%>

<%
    String tag = "";
%>

<script type="text/javascript">

document.onreadystatechange=function(){
    if(document.readyState == "complete")init();
};

function init(){
    var logid="<%=logid%>";
    var errMsg="<%=errMsg%>";
    if(errMsg.length>5){
        alert(errMsg);
        return;
    }
    if(logid==null||logid==""){
        alert("로그아웃 되어있습니다. 로그인후 다시 검색해주십시오.");
        opener.document.location.href="login.do?method=login";
        self.close();
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
      url: 'setupdues.do?method=setupcertifiList',
      datatype: "json",
      mtype: 'post',
      width:'1100',
      height:'365',
      colNames: [ ' 구분코드 ','자격증구분명','갱신주기','이수평점','중앙회평점','봉사평점','사용여부'],
      colModel: [
            {name:'code_certifi',     index:'code_certifi',      width: 100,editable:  false, align: 'center'},
            {name:'certifi_name',     index:'certifi_name',      width: 100,editable:  false, align: 'center'},
            {name:'renewal',          index:'renewal',           width: 100,editable:  false, align: 'center'},
            {name:'finish_marks',     index:'finish_marks',      width: 100,editable:  false, align: 'right'},
            {name:'center_marks',     index:'center_marks',      width: 100,editable:  false, align: 'right'},
            {name:'service_marks',    index:'service_marks',     width: 100,editable:  false, align: 'right'},
            {name:'use_yn',           index:'use_yn',            width: 100,editable:  false, align: 'center'},
            ],
            rowNum:15,
            pager: '#pager2',
            rownumbers: true,
            viewrecords: true,
            multiselect: false,
            gridview: true,
            caption: '자격증 종류',
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
powerinit();
jQuery("#list").jqGrid('navGrid','#pager2',{edit:false,add:false,del:false,search:false,refresh:true});
});

function powerinit(){

    var userpowerm1=eval(<%=userpowerm1%>);
    var meprogid = "703";
    var bcheck = "N";
//  alert("userpowerm1[i].progid===>"+userpowerm1.length);
//  alert(userpowerm1);
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

function goSelect(rowid,iCol){
    goClear();
    tag = '';
    var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
    if( gr != null ) {
        var list = $("#list").getRowData(gr);
        
        document.sForm.code_certifi1.value      = list.code_certifi;
        document.sForm.certifi_name1.value      = list.certifi_name;
        document.sForm.renewal1.value           = list.renewal;
        document.sForm.finish_marks1.value      = list.finish_marks;
        document.sForm.center_marks1.value      = list.center_marks;
        document.sForm.service_marks1.value     = list.service_marks;
        document.sForm.use_yn1.value            = list.use_yn;  
    }
}

function goClear(){ 
    $('#list1').each(function(){
        this.reset();
        tag = '*';
    }); 
}

function goInsert(){
    var  code_certifi1   = sForm.code_certifi1.value;
    var  certifi_name1   = sForm.certifi_name1.value;
    var  use_yn1         = sForm.use_yn1.value; 
    
    if ( code_certifi1 == "" ) {
        alert("구분코드를 입력하세요.") ;
        sForm.code_certifi1.focus();
        return ;
    }   
    
    if ( certifi_name1 == "" ) {
        alert("자격증명을 입력하세요.") ;
        sForm.certifi_name1.focus();
        return ;
    }   
    
    if ( tag == '*' ) { 
        document.sForm.action = "setupdues.do?method=insert_certifi";
        document.sForm.submit();  
    }else { 
        document.sForm.action = "setupdues.do?method=update_certifi";
        document.sForm.submit();
     }  
}

function goSearch(form,intPage){
    var parm ="";
    
    if(sForm.use_yn1.value        !="")parm+="&use_yn1="       + sForm.use_yn1.value; 
    
    $('#list').jqGrid('clearGridData');
    jQuery("#list").setGridParam({url:"setupdues.do?method=setupcertifiList&parm="+parm}).trigger("reloadGrid");
}

</script>
</head>

<body>
<div id="contents">
      <ul class="home">
        <li class="home_first"><a href="#">Home</a></li>
        <li>&gt;</li>
        <li><a href="#">환경설정</a></li>
        <li>&gt;</li>
        <li class="NPage">자격증 종류</li>
      </ul> 
<form method="post"> 
    <input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>  
<div class="cTabmenu_01">
<form id="list1" name="sForm" method="post">

<!-- 중복처리방지 토큰 처리 시작-->
<%
    //중복처리방지 토큰 처리 - 페이지에 그대로 추가
    String token=TokenProcessor.getInstance().generateToken(request); //현재 시간과 세션아이디를 갖고 token 키생성

    if(session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY)==null){
        session.setAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY,token);    
    %>
        <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
    <%}else{%>
        <input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value="<%=session.getAttribute(org.apache.struts.Globals.TRANSACTION_TOKEN_KEY) %>"/>
<%}%> 
<!-- 중복처리방지 토큰 처리  끝-->   

        <table class="ctable_03" cellspacing="0" summary="자격증 종류">          
            <tr>               
               <td class="nobga" align="center">구분 코드</td>
               <td class="nobg1a"><input type="text" id="m" name="code_certifi1" size="15" maxlength="1" oninput="this.value = this.value.replace(/[^0-9]/g, '')" /></td> 
               <td class="nobg2a" align="center">자격증 명</td>
               <td class="nobg1a"><input type="text" id="m" name="certifi_name1" size="15" maxlength="24" /></td> 
               <td class="nobg2a" align="center">갱신 주기</td>
               <td class="nobg1a"><input type="text" id="m" name="renewal1" size="15" maxlength="3" oninput="this.value = this.value.replace(/[^0-9]/g, '')" /></td>
               <td class="nobg2a" align="center" >사용 여부</td>
               <td class="nobg1a"><select id="name" name="use_yn1" style="width: 100px"/>
                        <option value="">전체</option>
                        <option value="Y">Y</option>
                        <option value="N">N</option>                    
                    </select></td>  
            </tr>
            <tr>  
               <td class="alt1" align="center">이수 평점</td>
               <td><input type="text" id="name" name="finish_marks1" size="15" maxlength="3" oninput="this.value = this.value.replace(/[^0-9]/g, '')" /></td> 
               <td class="alt" align="center">중앙회 평점</td>
               <td><input type="text" id="name" name="center_marks1" size="15" maxlength="3" oninput="this.value = this.value.replace(/[^0-9]/g, '')" /></td> 
               <td class="alt" align="center">봉사 평점</td>
               <td><input type="text" id="name" name="service_marks1" size="15" maxlength="3" oninput="this.value = this.value.replace(/[^0-9]/g, '')" /></td> 
               <td class="alt" align="center">비  고</td>
               <td></td> 
             </tr>          
        </table>            
         <ul class="btnIcon_2">              
           <li><a href="#"><img src="images/icon_new.gif"    onclick="javascript:goClear();" alt="신규" /></a></li>        
           <li><a href="#"><img src="images/icon_save.gif"   onclick="javascript:goInsert();" alt="저장" /></a></li>
           <li><a href="#"><img src="images/icon_search.gif" onclick="javascript:goSearch(sForm,0);" alt="검색" /></a></li>    
         </ul>  
</form>
<br><br>

<table id="list"></table>
<div id="pager2"></div>
</div>
</div>
</body>
</html>

  