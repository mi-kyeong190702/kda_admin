<%@page import="org.apache.struts.action.Action"%>
<%@page import="org.apache.struts.util.TokenProcessor"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.net.*" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.ant.educationexam.dao.educationDao"%>
<%@page import="com.ant.common.util.StringUtil"%>
<%@ page import="com.ant.common.properties.AntData"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<title>대한영양사협회</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui-1.8.23.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="css/import.css" />  
<link rel="stylesheet" type="text/css" href="css/education.css" />
 
<!--날짜 표시를 위한 link  -->
<link rel="stylesheet" href="css/jquery.ui.all.css"/>
<link rel="stylesheet" href="css/demos.css"/>

<script src="js/jquery-1.8.1.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="js/jquery-ui-1.8.23.custom.min.js" ></script>
<script src="js/grid.locale-en.js"    type="text/javascript"></script>

<script src="js/jquery.jqGrid.src.js" type="text/javascript"></script>
<script src="js/comm.js"  type="text/javascript"></script>
<script src="js/form.js"  type="text/javascript"></script>

<!-- 날짜 표시를 위한 스크립트   -->
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.datepicker.js"></script>
    
<style type="text/css">
.myAltRowClass {background-color:#f5f8f9; background-image:none;}
.readOnly1 {background-color:#f2f2f2;}
</style>

<%

    Calendar calendar = Calendar.getInstance();
    java.util.Date date = calendar.getTime();
    
    Map map = new HashMap();
    educationDao dao = new educationDao();

    String g_name = session.getAttribute("G_NAME").toString(); 
    String g_id = session.getAttribute("G_ID").toString(); 
    String g_bran = session.getAttribute("G_BRAN").toString(); 
    JSONArray userpowerm1=(JSONArray)session.getAttribute("userpowerm");

    String v_yyyy1=request.getParameter("yyyy1");
    String v_code_bran1=request.getParameter("code_bran1");
    String v_code_kind1=request.getParameter("code_kind1");
    
    List<Map> certifisearch1 = dao.certifisearch1(map);
    List<Map> certifisearch = dao.certifisearch(map);               //map list  
    
    Map       edutake   = (Map)request.getAttribute("edutake");
    Map       edutakeets    = (Map)request.getAttribute("edutakeets");

    int etk2=0;
    String v_yyyy11="";
    String v_code_bran11="";
    String v_detcode11="";
    String v_code_kind11="";
    
    String v_code_certifi11="";
    String v_oper_name11="";
//  String v_oper_no11=""; // JUMIN_DEL
    String v_oper_birth11="";
    String v_oper_lic_no11="";
    String v_oper_hp11="";
    String v_oper_comp_name11="";
    String v_result_end_dt11="";
    String v_start_dt11="";
    String v_end_dt11="";
    String v_oper_state11="";
    String v_code_pay_flag11="";
    String v_code_operation1="";
    String v_certifi_name="";
    String v_receipt_no1="";
    
    if(edutakeets!=null){
        etk2=edutakeets.size();
        v_yyyy11=edutakeets.get("yyyy1").toString();
        v_code_bran11=edutakeets.get("code_bran1").toString();
        v_code_kind11=edutakeets.get("code_kind1").toString();
        v_detcode11=edutakeets.get("edutest_name1").toString();
        
        v_code_certifi11    =edutakeets.get("code_certifi1").toString();
        v_oper_name11       =edutakeets.get("oper_name1").toString();
//      v_oper_no11         =edutakeets.get("oper_no1").toString(); // JUMIN_DEL
        v_oper_birth11          =(String)edutakeets.get("oper_birth1");
        v_oper_lic_no11     =edutakeets.get("oper_lic_no1").toString();
        v_oper_hp11         =edutakeets.get("oper_hp1").toString();
        v_oper_comp_name11  =edutakeets.get("oper_comp_name11").toString();
        v_result_end_dt11   =edutakeets.get("result_end_dt1").toString();
        v_start_dt11        =edutakeets.get("start_dt1").toString();
        v_end_dt11          =edutakeets.get("end_dt1").toString();
        v_oper_state11      =edutakeets.get("oper_state1").toString();
        v_code_pay_flag11   =edutakeets.get("code_pay_flag1").toString();
        v_code_operation1   =edutakeets.get("code_operation1").toString();  
        v_certifi_name      =edutakeets.get("certifi_name").toString(); 
        
        if(edutakeets.get("tmp_dup").toString().equals("N")){
            if(!edutakeets.get("receipt_no1").toString().equals("")){
                v_receipt_no1=edutakeets.get("receipt_no1").toString();
            }else { 
                v_receipt_no1=edutakeets.get("receipt_no22").toString();
            }
        }
    } if(edutake!=null){
        v_detcode11         =(String)edutake.get("detcode1"); if(v_detcode11==null)v_detcode11="";
    }
    
    List<Map> educationsend1 = (List<Map>)request.getAttribute("educationsend1");  
    JSONArray j_educationsend=(JSONArray)request.getAttribute("j_educationsend");
    int j_eds=0;
    if(j_educationsend!=null){
        j_eds=j_educationsend.size();   
    }
    
    String errMsg="";
    if(request.getAttribute("errMsg")!=null){
        errMsg=URLDecoder.decode(request.getAttribute("errMsg").toString(),"UTF-8"); 
    }
    
    String pgno = StringUtil.NVL((String)request.getAttribute("pgno"));
%>


<script type="text/javascript">

function getAjax(strUrl,funcReceive){
      $.ajax({
       cache:false,
       type:'GET',
       url:strUrl,
       dataType:'json',
       timeout:60000,
       async:false,
       success:function(data){
        funcReceive(data);
//      document.all.div_wait.style.visibility  = "hidden" ;
       },
       error: function(xhr,textStatus,errorThrown){
//      document.all.div_wait.style.visibility  = "hidden" ;
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
         /*  url: 'education.do?method=educationSendList', */
          url: '',
        <%if(!"".equals(pgno)){%>
            page : <%=pgno%>,
        <%}%>
          datatype: "json",
          mtype: 'post',      
          //width:'1100',
          height:'380',
          colNames: [ 'key','내용','신청일자','접수번호','회원구분','성명','면허번호',
//                    '주민번호', // JUMIN_DEL
                      '생년월일',
                      '근무처명','핸드폰','이메일','결제방법e','결제방법','입금자명','진행상태'
                      ,'첨부파일' ,'교육년도','교육주최','교육종류','자격증구분','대상자','유효일자','유예시작일','유예마지막일','상태코드','결제여부'  
                      ,'자격증구분코드값','지부순번',
//                    '주민번호1', // JUMIN_DEL
                      '순번','회원코드','순번','지부번호','자격증구분명','접수번호','확정일자'],
          colModel: [
                {name:'detcode',            index:'detcode',            hidden:true},       //key 
                {name:'edutest_name',       index:'edutest_name',       width: 400,editable: false, align: 'left'},     //교육시험명(edu_test_name)
                {name:'receipt_dt',         index:'receipt_dt',         width: 70,editable: false, align: 'center'},        //신청일자(operater)
                {name:'receipt_no',         index:'receipt_no',         width: 70,editable: false, align: 'center'},        //접수번호
                {name:'person_yn',          index:'person_yn',          width: 100,editable: false, align: 'center'},       //회원구분(operater)
                {name:'oper_name',          index:'oper_name',          width: 80,editable: false, align: 'center'},        //이름(operater)
                {name:'oper_lic_no',        index:'oper_lic_no',        width: 70,editable: false, align: 'center'},        //면허번호(operater)
//              {name:'oper_no',            index:'oper_no',            width: 130,editable: false, align: 'center', formatter:juminNo},        //주민번호(operater) // JUMIN_DEL       
                {name:'oper_birth',             index:'oper_birth',             width: 130,editable: false, align: 'center'},       //생년월일(operater)        
                {name:'oper_comp_name1',    index:'oper_comp_name1',    width: 400,editable: false, align: 'left', formatter:decode11},     //근무처명(operater)
                {name:'oper_hp',            index:'oper_hp',            width: 130,editable: false, align: 'center'},       //핸드폰(operater)
                {name:'oper_email',         index:'oper_email',         width: 130,editable: false, align: 'left'},     //이메일(operater)     
                {name:'code_pay_flag',      index:'code_pay_flag',      hidden:true},                                   //결제방법(operater)
                {name:'payflag',            index:'payflag',            width: 80,editable: false, align: 'center'},        //결제방법(operater)
                {name:'oper_account',       index:'oper_account',       width: 80,editable: false, align: 'center'},        //입금자명(operater)
                {name:'operstate',          index:'operstate',          width: 80,editable: false, align: 'center'},        //진행상태(operater)
                {name:'detcodename',        index:'detcodename',        width: 100,editable: false, align: 'center'},       //첨부파일(oper_add_file)
                {name:'yyyy',               index:'yyyy',               hidden:true},       //교육년도
                {name:'code_bran',          index:'code_bran',          hidden:true},       //교육주최
                {name:'code_kind',          index:'code_kind',          hidden:true},       //교육종류
                {name:'certifi_name',       index:'certifi_name',       hidden:true},       //자격증구분
                {name:'code_operation',     index:'code_operation',     hidden:true},       //대상자
                {name:'result_end_dt',      index:'result_end_dt',      hidden:true},       //유효일자
                {name:'start_dt',           index:'start_dt',           hidden:true},       //유예시작일
                {name:'end_dt',             index:'end_dt',             hidden:true},       //유예마지막일
                {name:'oper_state',         index:'oper_state',         hidden:true},       //상태코드
                {name:'regi_date',          index:'regi_date',          hidden:true},       //결제여부
                
                {name:'code_certifi',       index:'code_certifi',       hidden:true},       //자격증구분코드값
                {name:'bran_seq',           index:'bran_seq',           hidden:true},       //지부순번
//              {name:'oper_no1',           index:'oper_no1',           hidden:true},       //주민번호 히든값 // JUMIN_DEL
                {name:'code_seq',           index:'code_seq',           hidden:true},       //순번히든값
                {name:'person_yn1',         index:'person_yn1',         hidden:true},       //순번히든값
                
                {name:'code_seq',           index:'code_seq',           hidden:true},
                {name:'bran_seq',           index:'bran_seq',           hidden:true},
                {name:'certifi_name',           index:'certifi_name',           hidden:true},
                {name:'receipt_no',         index:'receipt_no',         hidden:true},
                {name:'confirm_dt',         index:'confirm_dt',         width: 80,editable: false, align: 'center'}       //확정일자

                ],
                rowNum:15,
               /*  sortname: '', */
                pager: '#pager2',
                rownumbers: true,
                viewrecords: true,
                multiselect: true,
                gridview: true,
                caption: '교육별응시현황',
                altclass:'myAltRowClass'/* ,                
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
                            //onSubmitList(id);
                        }
                        else {}
                    }
                } */
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
    
    //상세검색
    jQuery("#memberAdvancedSearch").click( function() {
        
        var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');
        
            if( rownum.length==0)   {           alert("상세검색을 원하는 회원을 선택해 주십시요.");           return;     }
        if( rownum.length>1)    {           alert("상세검색은 한명의 회원만 선택해야 합니다.");           return;     }

        var row         =   $("#list").getRowData(rownum);
        var person_yn1  = row.person_yn1;
//      alert(person_yn1);//code_pers
        
        if(person_yn1 =='' ){
            alert("회원이 아닙니다. 다시 확인해주세요!!");
            return;
        }
        window.open('memberBasic.do?method=memInfo&pCode='+person_yn1,'','width=1150, height=700, resizable=no,scrollbars=yes');
        
        
    });
    
    
    //powerinit();

    });  
function decode11(cellvalue, options, rowObject ){
    var temp=decodeURIComponent(cellvalue); 
    return replaceAll(temp,"+"," ");
}

function replaceAll(temp,org,rep){
    return temp.split(org).join(rep);   
}



/*DM 발송  */
function sendDM(){
    resetCresult();
    
    var temp = jQuery("#list").jqGrid('getRowData');
    if(temp.length==0){
        alert("조회된 데이터가 없어 DM을 발송 할 수 없습니다.");
        return;
    }
    
    var temp1=jQuery("#list").jqGrid('getCol','oper_state');
    if(temp1[0]=='09'){
        alert("취소자 목록에서는 DM을 발송 할 수 없습니다.");
        return;
    }
    
    var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');    
    var rowdata=new Array();

    var url="";
    
    var param = "";
    param += "&register="+escape(encodeURIComponent("<%=g_name%>"));
    
    if( rownum.length > 0 ){ 
        var code_kind       = '';
        var code_certifi    = '';
        var code_seq        = '';
        var code_bran       = '';
        var bran_seq        = '';
        var receipt_no      = '';
        //var oper_hp           = ''; //회원번호 없는 회원 번호 만들기 위함
        for(var i=0;i<rownum.length;i++){
            rowdata=    $("#list").getRowData(rownum[i]);
            
            if(i > 0) {
                code_kind   += ",";
                code_certifi+= ",";
                code_seq    += ",";
                code_bran   += ",";
                bran_seq    += ",";
                receipt_no  += ",";
            }
            code_kind   += rowdata.code_kind;
            code_certifi+= rowdata.code_certifi;
            code_seq    += rowdata.code_seq;
            code_bran   += rowdata.code_bran;
            bran_seq    += rowdata.bran_seq;
            receipt_no  += rowdata.receipt_no;
        }
        param += "&code_kind="      +code_kind;
        param += "&code_certifi="   +code_certifi;
        param += "&code_seq="       +code_seq;
        param += "&code_bran="      +code_bran;
        param += "&bran_seq="       +bran_seq;
        param += "&receipt_no="     +receipt_no;
        
        //alert("DM발송 조건 "+param);
        url="education.do?method=sendDMForm&chk=CHK&param="+param;
        
    }else if( rownum.length == 0 ){ 
//          //여기다 전체 검색(검색과 같은 루트를 탄다.)
//          if(sForm.yyyy1.value            != "")  param+="&yyyy1="            +sForm.yyyy1.value;             //년도
//          if(sForm.code_bran1.value       != "")  param+="&code_bran1="       +sForm.code_bran1.value;        //지부(교육주최)
//          if(sForm.code_kind1.value       != "")  param+="&code_kind1="       +sForm.code_kind1.value;        //교육종류
//          if(sForm.edutest_name1.value    != "")  param+="&edutest_name1="    +sForm.edutest_name1.value;     //내용
//          if(sForm.code_certifi1.value    != "")  param+="&code_certifi1="    +sForm.code_certifi1.value;     //구분코드(자격증구분)
        
//          url="education.do?method=sendDMForm&chk=ALL&param="+param;

         var yyyy1              = sForm.yyyy1.value;                                            //년도
         var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)
         var code_kind1         = sForm.code_kind1.value;                                       //교육종류
         var edutest_name1      = sForm.edutest_name1.value;                                    //내용
         var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(자격증구분)
         
         var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름 incording 문제
         var oper_birth1        = sForm3.oper_birth11.value;                                    //생년월일
         var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호
         var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
         var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
         var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
         var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
         var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
         var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
         var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
         var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
         var code_seq1          = sForm.code_seq1.value;                                        //순번
         var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
         var bran_seq1          = sForm.bran_seq1.value;                                        //지부순서

        if(code_operation1=="0") code_operation1 = "";
        if(oper_state1!=""){
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1;    
        }else{
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1+"&sel=Y";
        }

        url="education.do?method=sendDMForm&chk=ALL&param="+param;
    }   
    var status ="toolbar=no, location=yes, directories=no, status=no, menubar=no, resizable=no, scrollbars=no, width=340, height=300, left=100, top=100";
    window.open(url,"sendDM", status);
    
 }



function goSelect(rowid,iCol){
    resetCresult();
    var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
    if( gr != null ) {
        var list = $("#list").getRowData(gr);
                
        /*그리드에서 select시에 테이블로 출력될 value값들  */
        // 상단 필드(교육년도, 교육주체, 교육종류, 교육명)는 검색 조건이므로 절대 업데이트하지 않음
        // 이 필드들은 사용자가 설정한 검색 조건을 유지하기 위해 변경하지 않습니다
        if(list.receipt_no != undefined && list.receipt_no != null && String(list.receipt_no).trim() != '' && String(list.receipt_no).trim() != 'null') {
            document.sForm.receipt_no1.value        = list.receipt_no;          //접수번호
        }
        if(list.bran_seq != undefined && list.bran_seq != null && String(list.bran_seq).trim() != '' && String(list.bran_seq).trim() != 'null') {
            document.sForm.bran_seq1.value          = list.bran_seq;            //지부순번
        }
        //alert("지부순번"+document.sForm.bran_seq1.value);
        
        if(list.code_operation != undefined && list.code_operation != null && String(list.code_operation).trim() != '' && String(list.code_operation).trim() != 'null') {
            document.sForm3.code_operation1.value    = list.code_operation;     //대상자
        }
        if(list.code_pay_flag != undefined && list.code_pay_flag != null && String(list.code_pay_flag).trim() != '' && String(list.code_pay_flag).trim() != 'null') {
            document.sForm3.code_pay_flag1.value     = list.code_pay_flag;      //결제방법
        }
        // certifi_name과 code_certifi도 상단 필드이므로 업데이트하지 않음 (검색 조건 유지)
        //alert(list.code_certifi);
//      alert("1"+document.sForm.code_operation1.value+"1");
        
        if(list.oper_name != undefined && list.oper_name != null && String(list.oper_name).trim() != '' && String(list.oper_name).trim() != 'null') {
            document.sForm3.oper_name1.value        = list.oper_name;           //이름
        }
//      document.sForm3.oper_no11.value         = list.oper_no.substring(0,6);  //주민번호 // JUMIN_DEL
        if(list.oper_birth != undefined && list.oper_birth != null && String(list.oper_birth).trim() != '' && String(list.oper_birth).trim() != 'null') {
            document.sForm3.oper_birth11.value          = list.oper_birth;      //생년월일
        }
//      document.sForm3.oper_no12.value         = list.oper_no1.substring(6,13);//주민번호 // JUMIN_DEL
//      alert("주민번호값"+sForm3.oper_no11.value); // JUMIN_DEL
//      alert("주민번호값"+sForm3.oper_no12.value);// JUMIN_DEL
        if(list.oper_lic_no != undefined && list.oper_lic_no != null && String(list.oper_lic_no).trim() != '' && String(list.oper_lic_no).trim() != 'null') {
            document.sForm3.oper_lic_no1.value      = list.oper_lic_no;         //면허번호
        }
        if(list.oper_hp != undefined && list.oper_hp != null && String(list.oper_hp).trim() != '' && String(list.oper_hp).trim() != 'null') {
            document.sForm3.oper_hp1.value          = list.oper_hp;             //핸드폰
        }
        if(list.oper_comp_name1 != undefined && list.oper_comp_name1 != null && String(list.oper_comp_name1).trim() != '' && String(list.oper_comp_name1).trim() != 'null') {
            document.sForm3.oper_comp_name11.value  = list.oper_comp_name1;     //근무처
        }
        if(list.result_end_dt != undefined && list.result_end_dt != null && String(list.result_end_dt).trim() != '' && String(list.result_end_dt).trim() != 'null') {
            document.sForm3.result_end_dt1.value    = list.result_end_dt;       //유효일자
        }
        // alert(list.result_end_dt); 
        if(list.start_dt != undefined && list.start_dt != null && String(list.start_dt).trim() != '' && String(list.start_dt).trim() != 'null') {
            document.sForm3.start_dt1.value         = list.start_dt;            //유예시작일
        }
        //alert(document.sForm.start_dt1.value );
        if(list.end_dt != undefined && list.end_dt != null && String(list.end_dt).trim() != '' && String(list.end_dt).trim() != 'null') {
            document.sForm3.end_dt1.value           = list.end_dt;              //유예마지막일
        }
        if(list.oper_state != undefined && list.oper_state != null && String(list.oper_state).trim() != '' && String(list.oper_state).trim() != 'null') {
            document.sForm3.oper_state1.value       = list.oper_state;          //진행상태
        }
        if(list.confirm_dt != undefined && list.confirm_dt != null && String(list.confirm_dt).trim() != '' && String(list.confirm_dt).trim() != 'null') {
            document.sForm3.confirm_dt.value       = list.confirm_dt;          //확정일자
        }
//alert("document.sForm3.oper_state1.value==>"+document.sForm3.oper_state1.value);
        if(list.code_seq != undefined && list.code_seq != null && String(list.code_seq).trim() != '' && String(list.code_seq).trim() != 'null') {
            document.sForm.code_seq1.value      = list.code_seq;                //순번 히든값
        }
        //      alert("code_seq1="+document.sForm.code_seq1.value); 
        //document.sForm.person_yn1.value           = list.person_yn;           //회원여부
        //document.sForm.oper_email1.value      = list.oper_email;          //이메일
        //document.sForm.oper_account1.value      = list.oper_account;      //입금자명
        //document.sForm.add_file_no1.value         = list.detcodename;         //첨부파일(detcodename)
    }
}   
     
function goClear(){
    resetCresult();
    $('#list1').each(function(){
        sForm3.reset();
        /* pk 값이 초기화가 되는지 출력 */
/*      alert("지부순번"+ document.sForm.bran_seq1.value);
        alert("접수번호"+document.sForm.receipt_no1.value);
        alert("교육내용"+document.sForm.edutest_name1.value);
 */ }); 
    document.sForm.receipt_no1.value = "";
/*  alert("접수번호="+document.sForm.receipt_no1.value);
 */}

function init(){
    var val2 = "<%=v_code_kind11%>";
    var val3 = "<%=v_yyyy11%>";
    var val4 = "<%=v_code_bran11%>";
    var val8 = "<%=v_detcode11%>";
    
    var val11="<%=v_code_certifi11%>";
    var val12="<%=v_oper_name11%>";
//  JUMIN_DEL
<%--    var val13="<%=v_oper_no11%>"; --%>
    var val13="<%=v_oper_birth11%>";
    var val14="<%=v_oper_lic_no11%>";
    var val15="<%=v_oper_hp11%>";
    var val16="<%=v_oper_comp_name11%>";
    var val17="<%=v_result_end_dt11%>";
    var val18="<%=v_start_dt11%>";
    var val19="<%=v_end_dt11%>";
    var val20="<%=v_oper_state11%>";
    var val21="<%=v_code_pay_flag11%>";
    var val22="<%=v_code_operation1%>";
    var val23="<%=v_certifi_name%>";
    var val24="<%=v_receipt_no1%>";
    //alert("v_receipt_no1==>"+val24);
    
    if(<%=etk2%>>0){
        alert("<%=errMsg%>");
        var jc=eval(<%=j_educationsend%>);

        var e_name=document.getElementById("edutest_name1");
        var opts=e_name.options;
        
        while(opts.length>0){
            opts[opts.length-1]=null;
        }
        setName1("선택 - <%=j_eds%>건","");
        var idx=opts.length;
        for(var i=0;i<jc.length;i++){
            e_name[i]=new Option(jc[i].edutest_name,jc[i].detcode);             
        }
         if(e_name.length==0){
            e_name[idx]=new Option("",null);
        }       
        sForm.code_kind1.value  = val2;
        sForm.yyyy1.value       = val3;
        sForm.code_bran1.value  = val4;
        e_name.value            = val8;
        <%if(!"".equals(pgno)){%>
        goSearch(sForm,<%=pgno%>);
        <%}else{%>
        goSearch(sForm,0);
        <%}%>

         
        sForm.code_certifi1.value=val11;
        //alert(sForm.code_certifi1.value);
        sForm3.oper_name1.value=val12;
//      sForm3.oper_no11.value=val13.substr(0,6); // JUMIN_DEL
        sForm3.oper_birth11.value=val13;
//      sForm3.oper_no12.value=val13.substr(6,7); // JUMIN_DEL
        sForm3.oper_lic_no1.value=val14;
        sForm3.oper_hp1.value=val15;
        sForm3.oper_comp_name11.value=val16;
        sForm3.result_end_dt1.value=val17;
        sForm3.start_dt1.value=val18;
        sForm3.end_dt1.value=val19;
        sForm3.oper_state1.value=val20;
        sForm3.code_pay_flag1.value=val21;
        sForm3.code_operation1.value=val22; 
        sForm.certifi_name.value=val23;
        sForm.receipt_no1.value=val24;
        
    }else{
        if(<%=j_eds%>>0){
            var jc=eval(<%=j_educationsend%>);
            
            var e_name=document.getElementById("edutest_name1");
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
             
            if('<%=v_detcode11%>'!=''){
                sForm.edutest_name1.value   = '<%=v_detcode11%>';
                if("<%=errMsg%>"!=""){ alert("<%=errMsg%>");}
                goSearch(sForm,0);
            }
        }
        
        <%
        if(edutake==null||edutake.get("yyyy1")==null||"".equals(edutake.get("yyyy1").toString())){
        %>
            sForm.yyyy1.value       = (new Date()).getFullYear();
        <%
        }
        %>
    }
}

function goSearch(form,intPage){
    resetCresult();
    /* 검색버튼 클릭시 그리드로 올리는 value 값 */
     var yyyy1              = sForm.yyyy1.value;                                            //년도
     var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)
     var code_kind1         = sForm.code_kind1.value;                                       //교육종류
     var edutest_name1      = sForm.edutest_name1.value;                                    //내용
     var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(자격증구분)
     
     var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름 incording 문제
//   alert("oper_name2======>"+sForm3.oper_name1.value);
//   var oper_name1         = sForm.oper_name1.value;           //이름
//   var oper_no1           = sForm3.oper_no11.value+sForm3.oper_no12.value;                //주민번호 // JUMIN_DEL
     var oper_birth1        = sForm3.oper_birth11.value;                                    //생년월일
     var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호
     var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
     var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
     var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
     var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
     var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
     var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
     var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
     var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
     var code_seq1          = sForm.code_seq1.value;                                        //순번
     var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
     var bran_seq1          = sForm.bran_seq1.value;                                        //지부순서
     

    if(code_operation1=="0") code_operation1 = "";
    $('#list').jqGrid('clearGridData');
    if(oper_state1!=""){
        jQuery("#list").setGridParam({page:(intPage>0?intPage:1),url:"education.do?method=educationSendList&yyyy1="+yyyy1+"&code_bran1="+code_bran1
            +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
            +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
//          JUMIN_DEL
//          +"&oper_no1="+oper_no1
            +"&oper_birth1="+oper_birth1
            +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
            +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
            +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1}
            ).trigger("reloadGrid");    
    }else{
        jQuery("#list").setGridParam({page:(intPage>0?intPage:1),url:"education.do?method=educationSendList&yyyy1="+yyyy1+"&code_bran1="+code_bran1
            +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
            +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
//          JUMIN_DEL
//          +"&oper_no1="+oper_no1
            +"&oper_birth1="+oper_birth1
            +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
            +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
            +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1+"&sel=Y"}
            ).trigger("reloadGrid");
    }
    
    
}



//파일첨부
// function uFile(){
//   var yyyy1                 = sForm.yyyy1.value; 
//   var code_kind1             = sForm.code_kind1.value;                           //종류(key)
//   var code_certifi1          = sForm.code_certifi1.value;                        //종류(key)
//   var code_seq1              = sForm.code_seq1.value;                            //순번(key)
//   var code_bran1             = sForm.code_bran1.value;                           //지부(key)
//   var bran_seq1              = sForm.bran_seq1.value;                            //지부순번(key)
//   var receipt_no1            = sForm.receipt_no1.value;                          //접수번호(key)
//   var edutest_name1          = sForm.edutest_name1.value;
//   var oper_name1             = escape(encodeURIComponent(sForm3.oper_name1.value));                          //이름
//   var codeoperation      = "";                       //대상자text값
//   var code_operation1        = sForm3.code_operation1.value; 
//   if(code_operation1=='1'){
//       codeoperation=escape(encodeURIComponent("교육이수"));  
//   }else if(code_operation1=='2'){
//       codeoperation=escape(encodeURIComponent("재시험"));   
//   }else if(code_operation1=='3'){
//       codeoperation=escape(encodeURIComponent("석사이상"));  
//   }else  if(code_operation1=='4'){
//       codeoperation=escape(encodeURIComponent("말소자"));   
//   }else if(code_operation1 ==''||code_operation1 =='0'  ){
//       alert("대상자를 선택후 첨부하여 주세요");
//       return;
//   }
     
//   if(edutest_name1==""||receipt_no1==""){
//       alert("교육명을 선택, 검색하신후\r\n응시자를 선택하시고 첨부해주십시오.");
//       return;
//   }

//   window.open('education.do?method=uFile&yyyy1='+yyyy1+'&code_kind1='+code_kind1+'&code_certifi1='+code_certifi1
//              +'&code_seq1='+code_seq1+'&code_bran1='+code_bran1+'&bran_seq1='+bran_seq1
//              +'&receipt_no1='+receipt_no1+'&oper_name1='+oper_name1+'&code_operation1='+code_operation1
//              +'&codeoperation='+codeoperation+"&edutest_name1="+edutest_name1,"uFile","scrollbar=no,resizable=no,toolbar=no,width=675,height=470,left=20,top=29,status=yes");        
// }

function goSave(){
     var form=document.sForm;
    /* search 에서 담아온 항목 이랑 같게 한다 */
     var yyyy1              = sForm.yyyy1.value;                                            //년도(필수 체크부분)
     var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)(필수 체크부분)
     var code_kind1         = sForm.code_kind1.value;                                       //교육종류(필수 체크부분)
     var edutest_name1      = sForm.edutest_name1.value;                                    //내용
     var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
     var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
     var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(교육및시험구분)
     var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름(필수 체크부분)
//   var oper_no1           = sForm3.oper_no11.value+sForm3.oper_no12.value;                //주민번호(필수 체크부분) // JUMIN_DEL
     var oper_birth1        = sForm3.oper_birth11.value;                                    //생년월일
     var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호(필수 체크부분)
     var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
     var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
     var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
     var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
     var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
     var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
     var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호 
     var bran_seq1          = sForm.bran_seq1.value;                                        //지부 순번(필수 체크부분)
     var code_seq1          = sForm.code_seq1.value;                                        //순번(필수 체크부분)
     var confirm_dt         = sForm3.confirm_dt.value;                                      //확정일자
     
     var certifi_name      =sForm.certifi_name.value;
     if ( oper_state1 == "" ) {
         oper_state1 = '01';  //상태정보가 없으면 기본값 입금확인중 으로
     }
        
     if(oper_name1 == ''){
         alert("이름을 입력해 주세요.");
         return;
     }
        
     if(oper_birth1.length != 8){
         alert("생년월일을 정확히 입력해 주세요.\n\n예) 19990101");
         return;
     }
     
     // oper_state1이 '11'일 때 confirm_dt 필수 검증
     if(oper_state1 == '11'){
         if(confirm_dt == null || confirm_dt == '' || confirm_dt.trim() == ''){
             alert("확정일자를 입력해주세요.\n\n예) 20251117");
             sForm3.confirm_dt.focus();
             return;
         }
         if(confirm_dt.trim().length != 8){
             alert("확정일자를 정확히 입력해 주세요.\n\n예) 20251117");
             sForm3.confirm_dt.focus();
             return;
         }
     }
     
     //alert(oper_no1.charAt(6)); // JUMIN_DEL
     
     // JUMIN_DEL
//   if(oper_no1.charAt(6)==1 || oper_no1.charAt(6)==2 || oper_no1.charAt(6)==3 || oper_no1.charAt(6)==4){
//      //주민 번호 유호성 검사
//          //sForm3.oper_no11.value+sForm3.oper_no12.value
//          var jmca = [2,3,4,5,6,7,8,9,2,3,4,5];
//          var jmcs = sForm3.oper_no11.value+sForm3.oper_no12.value;
//          if (jmcs.length == 0)
//          {
//              alert('주민등록번호를 입력하세요.');
//              sForm3.oper_no11.focus();
//              return;
//          }else if(jmcs.length == 13){    
//              var jmc = 0;
//              for(var i=0; i<jmca.length; i++) {
//                jmcsc = jmcs.substr(i,1);
//                jmc = jmc + jmca[i]*jmcsc;
//              }
//              var jmch = String(11-(jmc%11)); 
//              jmch = jmch.substr(jmch.length-1,2);
//              if(jmch != jmcs.substr(12,1)) {
//                alert('존재하지 않는 주민번호입니다');
//                sForm3.oper_no11.value="";
//                sForm3.oper_no12.value="";
//                sForm3.oper_no11.focus();
//                return;
//              }
//              // return; 
//          }else{
//              alert('주민등록번호는 13자리여야 합니다.');
//              sForm3.oper_no11.value="";
//              sForm3.oper_no12.value="";
//              sForm3.oper_no11.focus();
//              return;
//          }           
//   }else if(oper_no1.charAt(6)==5 || oper_no1.charAt(6)==6 || oper_no1.charAt(6)==7 || oper_no1.charAt(6)==8){
//      var birthYear="";
//      var birthMonth="";
//      var birthDate="";
//      var birth="";
        
//       if(oper_no1==''){
//              alert('외국인등록번호를 입력하십시오.');
//              return;
//          }
//          if (oper_no1.length != 13) {
//              alert('외국인등록번호 자리수가 맞지 않습니다.');
//              return;
//          }
//              if ((oper_no1.charAt(6) == "5") || (oper_no1.charAt(6) == "6"))
//              {
//                 birthYear = "19";
//              }
//              else if ((oper_no1.charAt(6) == "7") || (oper_no1.charAt(6) == "8"))
//              {
//                 birthYear = "20";
//              }
//              else if ((oper_no1.charAt(6) == "9") || (oper_no1.charAt(6) == "0"))
//              {
//                 birthYear = "18";
//              }
//              else
//              {
//                  alert('외국인등록번호에 오류가 있습니다. 다시 확인하십시오.(1)');
//                  return;
//              }        
//              birthYear += oper_no1.substr(0, 2);
//              birthMonth = oper_no1.substr(2, 2) - 1;
//              birthDate = oper_no1.substr(4, 2);
//              birth = new Date(birthYear, birthMonth, birthDate);
                
//              if ( birth.getYear() % 100 != oper_no1.substr(0, 2) ||
//                   birth.getMonth() != birthMonth ||
//                   birth.getDate() != birthDate) {
//                return;
//              }
                
//              if (fgn_no_chksum(oper_no1) == false){                    
//                  alert('외국인등록번호에 오류가 있습니다. 다시 확인하십시오.(2)');
//                  return;
//              }
//   }else{
//       alert('존재하지 않는 주민번호입니다');
//       return;
//   }
            

    if ( edutest_name1 != "") {  
         var bran_seq1          = edutest_name1.substring(8,11);                                        //지부 순번(필수 체크부분)
         var code_seq1          = edutest_name1.substring(2,6);                                     //순번(필수 체크부분)
         var code_certifi1      = edutest_name1.substring(1,2);                                     //구분코드(교육및시험구분)
     }
     var param = "";
//    alert(code_operation1);
      if(code_operation1==1){
             if(form._21name.value.length!=0) param+=form._21name.value+" ";
             if(form._22name.value.length!=0) param+=form._22name.value+" ";
             if(form._23name.value.length!=0) param+=form._23name.value+" ";
             if(form._24name.value.length!=0) param+=form._24name.value+" ";
             if(form._25name.value.length!=0) param+=form._25name.value+" ";
         }else if(code_operation1==3){
             if(form._41name.value.length!=0) param+=form._41name.value+" ";
             if(form._42name.value.length!=0) param+=form._42name.value+" ";
             if(form._43name.value.length!=0) param+=form._43name.value+" ";
             if(form._44name.value.length!=0) param+=form._44name.value+" ";
             if(form._45name.value.length!=0) param+=form._45name.value+" ";
             if(form._46name.value.length!=0) param+=form._46name.value+" ";
             if(form._47name.value.length!=0) param+=form._47name.value+" ";
         }else if(code_operation1==4){
             if(form._51name.value.length!=0) param+=form._51name.value+" ";
             if(form._52name.value.length!=0) param+=form._52name.value+" ";
             if(form._53name.value.length!=0) param+=form._53name.value+" ";
             if(form._54name.value.length!=0) param+=form._54name.value+" ";
         }else if(code_operation1==6){
             if(form._61name.value.length!=0) param+=form._61name.value+" ";
             if(form._62name.value.length!=0) param+=form._62name.value+" ";
             if(form._63name.value.length!=0) param+=form._63name.value+" ";
             if(form._64name.value.length!=0) param+=form._64name.value+" ";
         }

//   if ( edutest_name1 != "" || oper_no1 != "" ){ // JUMIN_DEL 
     if ( edutest_name1 != "" || (oper_name1 != "" && oper_birth1 != "") ){     
            var code_pers1 = $(".cresult_check:checked").length>0 ? $(".cresult_check:checked").val() : "";
            resetCresult();
            
            if(code_pers1==""&&receipt_no1==""){
                var curl = "education.do?method=edutakeSaveCheck&oper_name1="+oper_name1+"&oper_birth1="+oper_birth1;
                var cok = false;
                getAjax(curl,function(data){
    //              alert(JSON.stringify(data));
                    if(eval(data.records)<=1){
                        cok=true;
                    } else {
                        alert(sForm3.oper_name1.value+"("+oper_birth1+"생) 회원이 1명이상 존재 합니다.\n\n대상선택 목록에서 아이디를 선택해 주세요.");
                        $(".cresult").show();
                        for(var i=0; i<eval(data.records);i++){
                            var newone = $(".cresult_basic").clone(true).removeClass("cresult_basic").addClass("cresult_row");
                            newone.find(".cresult_check").val(data.rows[i].code_pers).attr("id","cresult_check_"+$(".cresult_row").length);
                            newone.find(".cresult_check_label").attr("for","cresult_check_"+$(".cresult_row").length).text(data.rows[i].id);
                            newone.find(".cresult_code_member").text(data.rows[i].code_member);
                            newone.find(".cresult_lic_no").text(data.rows[i].lic_no!=undefined&&data.rows[i].lic_no!=null&&data.rows[i].lic_no!='null'&&data.rows[i].lic_no!=''&&data.rows[i].lic_no!='0'?data.rows[i].lic_no:'면허번호없음');
                            newone.find(".cresult_pers_hp").text(data.rows[i].pers_hp!=undefined&&data.rows[i].pers_hp!=null&&data.rows[i].pers_hp!='null'&&data.rows[i].pers_hp!=''?data.rows[i].pers_hp:'핸드폰정보없음');
                            newone.click(function(){$(this).find(".cresult_check").prop("checked",true);});
                            newone.show().appendTo($("#cresult_list"));
                            //alert(newone.html());
                        }
                    }
                });
                if(!cok){return;}
            }
            //alert("SAVE:"+code_pers1);
            
        /*action  edutakeSave 로 보내는 method 항목일치*/
            document.sForm.action = "education.do?method=edutakeSave&yyyy1="+yyyy1+"&code_bran1="+code_bran1+"&code_kind1="+code_kind1
            +"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1+"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1
            +"&oper_name1="+oper_name1
//          +"&oper_no1="+oper_no1 // JUMIN_DEL
            +"&oper_birth1="+oper_birth1
            +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
            +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1+"&receipt_no1="+receipt_no1
            +"&bran_seq1="+bran_seq1+"&code_seq1="+code_seq1+"&certifi_name="+certifi_name+"&param="+param
            +"&code_pers1="+code_pers1;
            // confirm_dt는 항상 전송 (값이 없으면 빈 문자열)
            var confirm_dt_param = (confirm_dt != null && confirm_dt != '') ? encodeURIComponent(confirm_dt.trim()) : '';
            document.sForm.action += "&confirm_dt="+confirm_dt_param+"&s_pgno="+$('#list').getGridParam('page');
            
            document.sForm.submit();       
            
    }else{
        return;
    }
}

// 개인정보 일괄변경
function goUpdateMember(){

    resetCresult();
    
    /* 검색버튼 클릭시 그리드로 올리는 value 값 */
    var yyyy1              = sForm.yyyy1.value;                                         //년도
    var edutest_name1      = sForm.edutest_name1.value;                                 //교육명
    var code_kind1         = sForm.code_kind1.value;                                    //교육종류
    var code_certifi1      = sForm.code_certifi1.value;                                 //구분코드(자격증구분)
    var code_seq1          = sForm.code_seq1.value;                                     //순번
    var code_bran1         = sForm.code_bran1.value;                                    //지부(교육주체)
    var bran_seq1          = sForm.bran_seq1.value;                                     //지부순서
    
    var oper_state1        = sForm3.oper_state1.value;                                  //진행상태
    
    if(yyyy1 == ''){
        alert("교육년도를 선택해 주세요.");
        return;
    }
    
    if(document.getElementById("code_bran1").value == ''){
        alert("교육주체를 선택해 주세요.");
        return;
    }
    
    if(document.getElementById("code_kind1").value == ''){
        alert("교육종류를 선택해 주세요.");
        return;
    }
    
    if(edutest_name1 == ''){
        alert("교육명을 선택해 주세요.");
        return;
    }
    
    var temp = jQuery("#list").jqGrid('getRowData');
    if(temp.length == 0){
        alert("조회된 데이터가 없어 개인정보 일괄변경을 할 수 없습니다.");
        return;
    }
    
    
    var receipt_infos = "";
    var rcheck = false;
    var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');
    var rowdata = new Array();
    
    if( rownum.length > 0 ){
        for(var i=0; i<rownum.length; i++){
            rowdata = $("#list").getRowData(rownum[i]);
            
            if(i > 0) {
                receipt_infos   += "__";
            }
            
            receipt_infos   += rowdata.code_kind    + "_";
            receipt_infos   += rowdata.code_certifi + "_";
            receipt_infos   += rowdata.code_seq     + "_";
            receipt_infos   += rowdata.code_bran    + "_";
            receipt_infos   += rowdata.bran_seq     + "_";
            receipt_infos   += rowdata.receipt_no   + "_";
            receipt_infos   += rowdata.person_yn1;
        }
        receipt_infos   += "&rcheck=Y";
        rcheck = true;
        
    } else {
        alert("개인정보 일괄변경할 대상자를 선택해 주세요.");
        return;
    }
    
    //alert("rownum.length : " + rownum.length + "\n oper_state1 : " + oper_state1 + "\nreceipt_infos : " + receipt_infos);
    
    // 1 5 0001 01 030
    
    
    var url = "";
    url = "education.do?method=edutakeUpdateMemBatch&yyyy1=" + yyyy1 +"&code_kind1=" + code_kind1 +"&code_certifi1=" + code_certifi1 + "&code_seq1=" + code_seq1 + "&code_bran1=" + code_bran1
        + "&bran_seq1=" + bran_seq1 + "&edutest_name1=" + edutest_name1
        + "&detcode1=" + edutest_name1 + "&receipt_infos=" + receipt_infos + "&sel=Y";
    
    
    var addmsg = "\n\n※ 처리하는데 다소 시간이 소요 될 수도 있습니다.\n'확인' 버튼을 클릭하고 잠시만 기다려 주십시오.";
    
    if(!confirm("선택하신 응시건들을 개인정보 일괄변경 처리 하시겠습니까?" + addmsg)){
        return;
    }
    
    
    $("#goUpdateMemberBtn").css('visibility', 'hidden');
    
    document.sFormBatch.action = url;
    document.sFormBatch.submit();
}


function goDel(){
    var gr = jQuery("#list").jqGrid('getGridParam','selrow');   
    if( gr == null ) {
        alert("목록에서 삭제하실 응시현황을 선택해 주세요."); return;
    }
     if(sForm.receipt_no1.value==""){
        alert("삭제하실 응시현황을 다시 선택해 주세요."); return;
    }
    
     var edutest_name1      = sForm.edutest_name1.value;                                    //내용
     var yyyy1              = sForm.yyyy1.value;                                            //년도(필수 체크부분)
     var code_kind1         = sForm.code_kind1.value;                                       //교육종류(필수 체크부분)
     var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(교육및시험구분)
     var code_seq1          = sForm.code_seq1.value;                                        //순번(필수 체크부분)
     var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)(필수 체크부분)
     var bran_seq1          = sForm.bran_seq1.value;                                        //지부 순번(필수 체크부분)
     var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
     
    if(!confirm("선택하신 응시현황을 삭제하시겠습니까?\n\n삭제한 응시현황은 다시 복구할 수 없습니다.")){return;}
     
    document.sFormDel.action = "education.do?method=edutakeDel&code_certifi1="+code_certifi1+"&code_kind1="+code_kind1+"&code_bran1="+code_bran1+
            "&code_seq1="+code_seq1+"&bran_seq1="+bran_seq1+"&receipt_no1="+receipt_no1+"&yyyy1="+yyyy1+"&detcode1="+edutest_name1;
    document.sFormDel.submit();   
}


function goSaveBatch(){
    resetCresult();
    
    var temp = jQuery("#list").jqGrid('getRowData');
    if(temp.length==0){
        alert("조회된 데이터가 없어 확정처리를 할 수 없습니다.");
        return;
    }
    
    // confirm_dt 값 확인
    var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
    var confirm_dt = sForm3.confirm_dt.value;
    
    
    alert(confirm_dt);
    // confirm_dt 필수 검증
    if(confirm_dt == null || confirm_dt == '' || confirm_dt.trim() == ''){
        alert("확정일자를 입력하세요.\n\n예) 20251117");
        sForm3.confirm_dt.focus();
        return;
    }
    if(confirm_dt.trim().length != 8){
        alert("확정일자를 정확히 입력해 주세요.\n\n예) 20251117");
        sForm3.confirm_dt.focus();
        return;
    }


    var receipt_infos = "";
    var rcheck=false;
    var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');    
    var rowdata=new Array();
    if( rownum.length > 0 ){
        for(var i=0;i<rownum.length;i++){
            rowdata=    $("#list").getRowData(rownum[i]);
            
            if(i > 0) {
                receipt_infos   += "__";
            }
            receipt_infos   += rowdata.code_kind+"_";
            receipt_infos   += rowdata.code_certifi+"_";
            receipt_infos   += rowdata.code_seq+"_";
            receipt_infos   += rowdata.code_bran+"_";
            receipt_infos   += rowdata.bran_seq+"_";
            receipt_infos   += rowdata.receipt_no;
        }
        receipt_infos   += "&rcheck=Y";
        rcheck = true;
    }
    
    
    /* 검색버튼 클릭시 그리드로 올리는 value 값 */
     var yyyy1              = sForm.yyyy1.value;                                            //년도
     var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)
     var code_kind1         = sForm.code_kind1.value;                                       //교육종류
     var edutest_name1      = sForm.edutest_name1.value;                                    //내용
     var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(자격증구분)
     
     var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름 incording 문제
//   alert("oper_name2======>"+sForm3.oper_name1.value);
//   var oper_name1         = sForm.oper_name1.value;           //이름
//   var oper_no1           = sForm3.oper_no11.value+sForm3.oper_no12.value;                //주민번호 // JUMIN_DEL
     var oper_birth1            = sForm3.oper_birth11.value;                //생년월일
     var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호
     var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
     var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
     var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
     var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
     var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
     var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
     
     var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
     var code_seq1          = sForm.code_seq1.value;                                        //순번
     var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
     var bran_seq1          = sForm.bran_seq1.value;                                        //지부순서
     // confirm_dt는 함수 시작 부분에서 이미 검증됨
     if(confirm_dt != null && confirm_dt != ''){
         confirm_dt = confirm_dt.trim();                                                    //확정일자 (공백 제거)
     } else {
         confirm_dt = '';                                                                   //빈 값일 경우 빈 문자열로 설정
     }

    if(code_operation1=="0") code_operation1 = "";
    var url = "";
    if(oper_state1!=""){
        url = "education.do?method=edutakeSaveBatch&yyyy1="+yyyy1+"&code_bran1="+code_bran1
            +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
            +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
//          JUMIN_DEL
//          +"&oper_no1="+oper_no1
            +"&oper_birth1="+oper_birth1
            +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
            +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
            +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1+"&receipt_infos="+receipt_infos
            +"&confirm_dt="+encodeURIComponent(confirm_dt);    
    }else{
        url = "education.do?method=edutakeSaveBatch&yyyy1="+yyyy1+"&code_bran1="+code_bran1
            +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
            +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
//          JUMIN_DEL
//          +"&oper_no1="+oper_no1
            +"&oper_birth1="+oper_birth1
            +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
            +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
            +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1+"&sel=Y"+"&receipt_infos="+receipt_infos
            +"&confirm_dt="+encodeURIComponent(confirm_dt);
    }
    var addmsg = "\n\n※ 처리하는데 다소 시간이 소요 될 수도 있습니다.\n'확인' 버튼을 클릭하고 잠시만 기다려 주십시오.";
    if(rcheck){
        if(!confirm("선택하신 응시건들을 일괄 확정처리 하시겠습니까?"+addmsg)){return;}
    } else {
        if(!confirm("일괄 확정처리 하시겠습니까?"+addmsg)){return;}
    }
    $("#goSaveBatchBtn").css('visibility', 'hidden'); 
    document.sFormBatch.action=url;
    document.sFormBatch.submit();
}

function resetCresult(){
    $(".cresult_row").remove();
    $(".cresult").hide();
}

$(function() {
    $( "#datepicker" ).datepicker();
});

$(function() {
    $( "#datepicker1" ).datepicker();
});
$(function() {
    $( "#datepicker2" ).datepicker();
});
$(function() {
    $( "#datepicker3" ).datepicker();
});

// function fgn_no_chksum(reg_no) {
//     var sum = 0;
//     var odd = 0;
    
//     buf = new Array(13);
//     for (var i = 0; i < 13; i++) buf[i] = parseInt(reg_no.charAt(i));
    
//     odd = buf[7]*10 + buf[8];
    
//     if (odd%2 != 0) {
//       alert('외국인등록번호에 오류가 있습니다. 다시 확인하십시오.(3)');
//    return false;
//     }
    
//  //alert(buf[11]);
//     if ((buf[6] != 6)&&(buf[6] != 7)&&(buf[6] != 8)&&(buf[6] != 9)) {
//       alert('외국인등록번호에 오류가 있습니다. 다시 확인하십시오.(4)');
//    return false;
//     }
        
//     multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
//     for (i = 0, sum = 0; i < 12; i++) sum += (buf[i] *= multipliers[i]);


//     sum=11-(sum%11);
    
//     if (sum>=10) sum-=10;

//     sum += 2;

//     if (sum>=10) sum-=10;

//     if ( sum != buf[12]) {
//         alert('외국인등록번호에 오류가 있습니다. 다시 확인하십시오.(5)');
//      return false;
//     }
//     else {
//         return true;
//     }
// }

// 교육년도, 교육주체, 교육종류 변경시
function setName(){
    
    var yyyy=document.getElementById("yyyy1").value;                //교육년도 값
    var code_bran=document.getElementById("code_bran1").value;      //교육주체 값
    var code_kind=document.getElementById("code_kind1").value;      //교육종류 값
    
    if ( yyyy == "" || code_bran == "" || code_kind == ""  ){       //교육년도와 교육주체 교육종류
        setName1("선택","");
        return;
    }
    /*action send 로 보내는 폼  */
    document.sForm.yyyy1.value = yyyy;
    document.sForm.action="education.do?method=educationSend&yyyy1="+yyyy+"&code_bran1="+code_bran+"&code_kind1="+code_kind;
    document.sForm.submit();
}   

function setName1(name,value){
    var edutest_name1=document.getElementById("edutest_name1");
    var opts=edutest_name1.options;
    while(opts.length>0){
        opts[opts.length-1]=null;
    }
    edutest_name1[0]=new Option(name,value);
}

// JUMIN_DEL
// function juminNo(cellvalue, options, rowObject ){
//  var jumin=cellvalue.substring(0,6)+"*******";
//  return jumin;
// }

function excelDown(){
    if(sForm.edutest_name1.value==""){
        alert("교육명을 선택하지 않으시면 엑셀을 다운받으실 수 없습니다.");
        return;
    }
    
    var param = "";

//  if(sForm.yyyy1.value            != "")  param+="&yyyy1="            +sForm.yyyy1.value;             //년도
//  if(sForm.code_bran1.value       != "")  param+="&code_bran1="       +sForm.code_bran1.value;        //지부(교육주최)
//  if(sForm.code_kind1.value       != "")  param+="&code_kind1="       +sForm.code_kind1.value;        //교육종류
//  if(sForm.edutest_name1.value    != "")  param+="&edutest_name1="    +sForm.edutest_name1.value;     //내용
//  if(sForm.code_certifi1.value    != "")  param+="&code_certifi1="    +sForm.code_certifi1.value;     //구분코드(자격증구분)

     var yyyy1              = sForm.yyyy1.value;                                            //년도
     var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)
     var code_kind1         = sForm.code_kind1.value;                                       //교육종류
     var edutest_name1      = sForm.edutest_name1.value;                                    //내용
     var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(자격증구분)
     
     var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름 incording 문제
     var oper_birth1        = sForm3.oper_birth11.value;                                    //생년월일
     var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호
     var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
     var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
     var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
     var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
     var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
     var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
     var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
     var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
     var code_seq1          = sForm.code_seq1.value;                                        //순번
     var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
     var bran_seq1          = sForm.bran_seq1.value;                                        //지부순서

    if(code_operation1=="0") code_operation1 = "";
    if(oper_state1!=""){
        param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
            +"&code_kind1="+code_kind1+"&edutest_name1="+encodeURI(edutest_name1)+"&code_operation1="+code_operation1
            +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
            +"&oper_birth1="+oper_birth1
            +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
            +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
            +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+encodeURI(edutest_name1); 
    }else{
        param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
            +"&code_kind1="+code_kind1+"&edutest_name1="+encodeURI(edutest_name1)+"&code_operation1="+code_operation1
            +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
            +"&oper_birth1="+oper_birth1
            +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
            +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
            +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+encodeURI(edutest_name1)+"&sel=Y";
    }
    
    param+="&register1="+encodeURI("<%=g_name%>");

    var url="education.do?method=pers_check"+param;
    window.open(url,"pers_check","width=400, height=500, left=480, top=200,scrollbars=yes");    

    }


function upExcel(){
    window.open("education.do?method=upEducationSendList","lic_no","scrollbars=yes,resizable=no,toolbar=no,width=350,height=150,left=20,top=29,status=no");
}


function powerinit(){

    var userpowerm1=eval(<%=userpowerm1%>);
    var meprogid = "202";
    var bcheck = "N";

    for(i=0;i<userpowerm1.length;i++){
        var setprogid=userpowerm1[i].progid;
        if (meprogid == setprogid)
            bcheck = "Y";       
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

//메일 발송
function sendEmail() {
    resetCresult();
    
    var temp = jQuery("#list").jqGrid('getRowData');
    if(temp.length==0){
        alert("조회된 데이터가 없어 메일을 발송 할 수 없습니다.");
        return;
    }
    var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');

//  if( rownum.length==0)   {
//      alert("메일을 발송할 회원을 선택해 주십시요.");
//      return; 
//  }
        
    var rowdata=new Array();
    var param = "";
    var toAddr="";
    var addr_infos = "";

    for(var i=0;i<rownum.length;i++){
            
        rowdata=    $("#list").getRowData(rownum[i]);
        
        if(rowdata.oper_email.length>0){
            if(toAddr.length > 0) toAddr+= ",";
//          toAddr += rowdata.oper_email;
            toAddr += escape(encodeURIComponent(rowdata.oper_name));
            
            if(addr_infos.length > 0) {
                addr_infos  += "__";
            }
            addr_infos  += rowdata.code_kind+"_";
            addr_infos  += rowdata.code_certifi+"_";
            addr_infos  += rowdata.code_seq+"_";
            addr_infos  += rowdata.code_bran+"_";
            addr_infos  += rowdata.bran_seq+"_";
            addr_infos  += rowdata.receipt_no;
        }
    }

    if( rownum.length==0){  
         var yyyy1              = sForm.yyyy1.value;                                            //년도
         var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)
         var code_kind1         = sForm.code_kind1.value;                                       //교육종류
         var edutest_name1      = sForm.edutest_name1.value;                                    //내용
         var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(자격증구분)
         
         var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름 incording 문제
         var oper_birth1            = sForm3.oper_birth11.value;                //생년월일
         var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호
         var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
         var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
         var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
         var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
         var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
         var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
         var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
         var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
         var code_seq1          = sForm.code_seq1.value;                                        //순번
         var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
         var bran_seq1          = sForm.bran_seq1.value;                                        //지부순서

        if(code_operation1=="0") code_operation1 = "";
        if(oper_state1!=""){
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1;    
        }else{
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1+"&sel=Y";
        }

        toAddr="ALL";
    }
    param+="&paramfrom=EduTakeStatus";

    window.open('memberBasic.do?method=eMail&toAddr='+toAddr+param+"&addr_infos="+addr_infos,"oper_email","scrollbar=no,resizable=no,toolbar=no,width=675,height=630,left=20,top=29,status=yes");
}



/* 쪽지 발송 조건  */
function notePad(form){ 
    resetCresult();
    
    var temp = jQuery("#list").jqGrid('getRowData');
    if(temp.length==0){
        alert("조회된 데이터가 없어 쪽지를 보낼 수 없습니다.");
        return;
    }
    var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');    
    var rowdata=new Array();
    var person_yn1="";
    var oper_name="";
    
    for(var i=0;i<rownum.length;i++){
        rowdata=    $("#list").getRowData(rownum[i]);

        if(rowdata.person_yn1.length>0) {
            if(person_yn1.length > 0) person_yn1+= ","; //code_pers
            person_yn1 += rowdata.person_yn1;
        }
        if(rowdata.oper_name.length>0) {
            if(oper_name.length > 0) oper_name+= ",";   //pers_name
            oper_name += rowdata.oper_name;
        }
    }

    if( rownum.length==0){  
        //여기다 전체 검색(검색과 같은 루트를 탄다.)
        
//조건 검색에 쓰는 조건값만 기입
        var param = "";
        //첫째줄-----------------------------------------------
//          if(sForm.yyyy1.value            != "")  param+="&yyyy1="        +sForm.yyyy1.value;                                     //년도
//      if(sForm.code_bran1.value       != "")  param+="&code_bran1="   +sForm.code_bran1.value;                                //지부(교육주최)
//      if(sForm.code_kind1.value       != "")  param+="&code_kind1="   +sForm.code_kind1.value;                                //교육종류
//      if(sForm.edutest_name1.value    != "")  param+="&edutest_name1="+sForm.edutest_name1.value; //내용
//      if(sForm.code_certifi1.value    != "")  param+="&code_certifi1="+sForm.code_certifi1.value;                             //구분코드(자격증구분)

         var yyyy1              = sForm.yyyy1.value;                                            //년도
         var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)
         var code_kind1         = sForm.code_kind1.value;                                       //교육종류
         var edutest_name1      = sForm.edutest_name1.value;                                    //내용
         var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(자격증구분)
         
         var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름 incording 문제
         var oper_birth1            = sForm3.oper_birth11.value;                //생년월일
         var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호
         var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
         var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
         var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
         var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
         var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
         var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
         var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
         var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
         var code_seq1          = sForm.code_seq1.value;                                        //순번
         var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
         var bran_seq1          = sForm.bran_seq1.value;                                        //지부순서

        if(code_operation1=="0") code_operation1 = "";
        if(oper_state1!=""){
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1;    
        }else{
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1+"&sel=Y";
        }

        param+="&from=edu1"; 
        //엑션
//      alert("쪽지발송 전체 = "+param);
        var url="education.do?method=sendnotePadAll"+param;
        window.open(url,"notePad","width=370, height=500, left=280, top=80");   
        
    }else{
//선텍된 항목만 보내기 스트러쳐 등록, 엑션 생성 ,jsp파일 붙이기
        oper_name = escape(encodeURIComponent(oper_name)); 

//      alert("쪽지발송 조건 = "+oper_name+"  person_yn1 = "+person_yn1);
        var url="education.do?method=sendnotePad&code_pers="+person_yn1+"&pers_name="+oper_name;
        window.open(url,"notePad","width=370, height=500, left=280, top=80");   
    }
}

/* 문자 발송 조건 수정요망 */
function smssand(form){
    resetCresult();
    
    var temp = jQuery("#list").jqGrid('getRowData');
    if(temp.length==0){
        alert("조회된 데이터가 없어 문자를 발송 할 수 없습니다.");
        return;
    }
    
    var rownum = jQuery("#list").jqGrid('getGridParam','selarrrow');    
    var rowdata=new Array();
    var oper_hp="";
    var oper_name="";
    var hp_infos = "";
    //개별선택
    for(var i=0;i<rownum.length;i++){
        rowdata=    $("#list").getRowData(rownum[i]);

        if(rowdata.oper_hp.length>0) {
            if(oper_hp.length > 0){ oper_hp+= ","; oper_name+=",";} 
            oper_hp += rowdata.oper_hp;
            oper_name += escape(encodeURIComponent(rowdata.oper_name));
            
            if(hp_infos.length > 0) {
                hp_infos    += "__";
            }
            hp_infos    += rowdata.code_kind+"_";
            hp_infos    += rowdata.code_certifi+"_";
            hp_infos    += rowdata.code_seq+"_";
            hp_infos    += rowdata.code_bran+"_";
            hp_infos    += rowdata.bran_seq+"_";
            hp_infos    += rowdata.receipt_no;
        }
    }

    if( rownum.length==0){  
        //여기다 전체 검색(검색과 같은 루트를 탄다.)
        
//조건 검색에 쓰는 조건값만 기입
        var param = "";
        //첫째줄-----------------------------------------------
//          if(sForm.yyyy1.value            != "")  param+="&yyyy1="        +sForm.yyyy1.value;             //년도
//      if(sForm.code_bran1.value       != "")  param+="&code_bran1="   +sForm.code_bran1.value;        //지부(교육주최)
//      if(sForm.code_kind1.value       != "")  param+="&code_kind1="   +sForm.code_kind1.value;        //교육종류
//      if(sForm.edutest_name1.value    != "")  param+="&edutest_name1="+sForm.edutest_name1.value;     //내용
//      if(sForm.code_certifi1.value    != "")  param+="&code_certifi1="+sForm.code_certifi1.value;     //구분코드(자격증구분)
//      if(sForm3.code_operation1.value != "")  param+="&code_operation1="+sForm3.code_operation1.value;    //대상자
//      if(sForm3.oper_state1.value != "")      param+="&oper_state1="+sForm3.oper_state1.value;            //접수상태
        
         var yyyy1              = sForm.yyyy1.value;                                            //년도
         var code_bran1         = sForm.code_bran1.value;                                       //지부(교육주최)
         var code_kind1         = sForm.code_kind1.value;                                       //교육종류
         var edutest_name1      = sForm.edutest_name1.value;                                    //내용
         var code_certifi1      = sForm.code_certifi1.value;                                    //구분코드(자격증구분)
         
         var oper_name1         = escape(encodeURIComponent(sForm3.oper_name1.value));          //이름 incording 문제
         var oper_birth1        = sForm3.oper_birth11.value;                                    //생년월일
         var oper_lic_no1       = sForm3.oper_lic_no1.value;                                    //면허번호
         var oper_hp1           = sForm3.oper_hp1.value;                                        //핸드폰
         var oper_comp_name11   = escape(encodeURIComponent(sForm3.oper_comp_name11.value));    //근무처
         var code_operation1    = sForm3.code_operation1.value;                                 //시행대상
         var result_end_dt1     = sForm3.result_end_dt1.value;                                  //유효종료일
         var start_dt1          = sForm3.start_dt1.value;                                       //유예시작일
         var end_dt1            = sForm3.end_dt1.value;                                         //유예종료일
         var oper_state1        = sForm3.oper_state1.value;                                     //진행상태
         var code_pay_flag1     = sForm3.code_pay_flag1.value;                                  //결제방법
         var code_seq1          = sForm.code_seq1.value;                                        //순번
         var receipt_no1        = sForm.receipt_no1.value;                                      //접수번호
         var bran_seq1          = sForm.bran_seq1.value;                                        //지부순서

        if(code_operation1=="0") code_operation1 = "";
        if(oper_state1!=""){
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1;    
        }else{
            param+="&yyyy1="+yyyy1+"&code_bran1="+code_bran1
                +"&code_kind1="+code_kind1+"&edutest_name1="+edutest_name1+"&code_operation1="+code_operation1
                +"&code_pay_flag1="+code_pay_flag1+"&code_certifi1="+code_certifi1+"&oper_name1="+oper_name1
                +"&oper_birth1="+oper_birth1
                +"&oper_lic_no1="+oper_lic_no1+"&oper_hp1="+oper_hp1+"&oper_comp_name11="+oper_comp_name11
                +"&result_end_dt1="+result_end_dt1+"&start_dt1="+start_dt1+"&end_dt1="+end_dt1+"&oper_state1="+oper_state1
                +"&code_seq1="+code_seq1+"&receipt_no1="+receipt_no1+"&bran_seq1="+bran_seq1+"&detcode1="+edutest_name1+"&sel=Y";
        }

        param+="&oper_hp=ALL&edutake_paramok=Y";
        
        var url="education.do?method=smssandAll"+param;
        window.open(url,"umsData","width=370, height=550, left=280, top=50");   
        
    }else{
        var param = "&hp_infos="+hp_infos+"&edutake_paramok=Y";

        var url="education.do?method=smssand&oper_hp="+oper_hp+"&oper_name="+oper_name+param;
        window.open(url,"umsData","width=370, height=550, left=280, top=50");   
    }
}
function ban(val){
    if(val.tagName=="INPUT"){
        if(event.keyCode==8){
            alert("수정할 수 없는 항목입니다.");
            event.keyCode=0;
            event.cancelBubble=true;
            event.returnValue=false;
        }
    }
}
function setEduN(val){
    //alert(val);
    // 교육명이 비어있거나 유효하지 않으면 자격증 구분을 설정하지 않음
    if(val == null || val == '' || val == undefined || val == 'null' || val == 'undefined') {
        return;
    }
    var jc=eval(<%=j_educationsend%>);
    if(jc == null || jc == undefined || jc.length == 0) {
        return;
    }
    for(var i=0;i<jc.length;i++){
        if(jc[i] != null && jc[i].detcode == val){
            // 값이 유효할 때만 설정
            if(jc[i].certifi_name != null && jc[i].certifi_name != undefined && jc[i].certifi_name != '') {
                sForm.certifi_name.value=jc[i].certifi_name;
            }
            if(jc[i].code_certifi != null && jc[i].code_certifi != undefined && jc[i].code_certifi != '') {
                sForm.code_certifi1.value=jc[i].code_certifi;
            }
            break; // 일치하는 항목을 찾으면 루프 종료
        }
    }
}
// function goPrint(){  
//  if(sForm.code_kind1.value!='4'){
//      alert("위생교육만 출력하실 수 있습니다.");
//      return;
//  }
//  if(sForm.edutest_name1.value==''){
//      alert("교육명을 선택해주십시오.");
//      return;
//  }
    
//  window.open("<%=AntData.DOMAIN%>/doc_form/docu_print_all.jsp?gubun=R&detcode="+sForm.edutest_name1.value);          
// }
</script>
</head>

 <body onload="init();">

    <div id="contents">
      <ul class="home">
        <li class="home_first"><a href="/main.do">Home</a></li>
        <li>&gt;</li>
        <li><a href="/main3.do">교육</a></li>
        <li>&gt;</li>
        <li class="NPage">교육별응시현황</li>
      </ul>  
<form method="post"> 
    <input type="hidden" name="csvBuffer" id="csvBuffer" value=""/> 
</form>
      <div class="eTabmenu_01">
      <form id="list1" name="sForm" method="post" action="">
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
      <!-- 히든값으로 반드시 필요한 pk값을 잡아준다   -->
        <input type="hidden" name="receipt_no1" id="receipt_no1" value=""/> 
        <input type="hidden" name="bran_seq1" id="bran_seq1" value=""/> 
        <input type="hidden" name="code_seq1" id="code_seq1" value=""/>
        <input type="hidden" name="code_certifi1" id="code_certifi1" value=""/>
        
        <input type="hidden" name="_21name" value=""/>
        <input type="hidden" name="_22name" value=""/>
        <input type="hidden" name="_23name" value=""/>
        <input type="hidden" name="_24name" value=""/>
        <input type="hidden" name="_25name" value=""/>
                        
        <input type="hidden" name="_41name" value=""/>
        <input type="hidden" name="_42name" value=""/>
        <input type="hidden" name="_43name" value=""/>
        <input type="hidden" name="_44name" value=""/>
        <input type="hidden" name="_45name" value=""/>
        <input type="hidden" name="_46name" value=""/>
        <input type="hidden" name="_47name" value=""/>
                        
        <input type="hidden" name="_51name" value=""/>
        <input type="hidden" name="_52name" value=""/>
        <input type="hidden" name="_53name" value=""/>
        <input type="hidden" name="_54name" value=""/>
                        
        <input type="hidden" name="_61name" value=""/>
        <input type="hidden" name="_62name" value=""/>
        <input type="hidden" name="_63name" value=""/>
        <input type="hidden" name="_64name" value=""/>
        
        <input type="hidden" name="person_yn1" value=""/>
                        
        <table class="etable_01" cellspacing="0" summary="교육">
          <caption>교육별응시현황</caption> 
          <tr>
               <td class="nobg">※&nbsp;&nbsp;교육년도</td>
               <td class="nobg1">
               <select  id="yyyy1" name="yyyy1" onchange="javascript:setName();">   
               <option value="">전체</option>
                 <%
                    String detCode,detCName="";
                    for(int i=0;i<certifisearch.size();i++){
                        if("036".equals(certifisearch.get(i).get("groupcode").toString())){
                            detCode=certifisearch.get(i).get("detcode").toString();
                            detCName=certifisearch.get(i).get("detcodename").toString();
                            if(edutake!=null&&detCode.equals(edutake.get("yyyy1").toString())){
                                out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
                            }else{
                                out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                            }
                            
                        }
                    }
                 %>         
                </select></td>
               <td class="nobg2">※&nbsp;&nbsp;교육주체</td>
               <td class="nobg1">
               <select  id="code_bran1" name="code_bran1"  onchange="javascript:setName();">
               <option value="">전체</option> 
                    <%
                    for(int i=0;i<certifisearch.size();i++){
                        if("034".equals(certifisearch.get(i).get("groupcode").toString())){
                            detCode=certifisearch.get(i).get("detcode").toString();
                            detCName=certifisearch.get(i).get("detcodename").toString();
                            if(edutake!=null&&detCode.equals(edutake.get("code_bran1").toString())){
                                out.println("<option value='"+detCode+"' selected>"+detCName+"</option>");
                            }else{
                                out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                            }
                        }
                    }
                 %>   
                    </select>
               </td>
               <td class="nobg2">※&nbsp;&nbsp;교육종류</td>
               <td class="nobg1">
                <select  id="code_kind1" name="code_kind1" onchange="javascript:setName();">
                <option value="">전체</option>    
                    <%
                    String detCode1,detCName1="";
                    for(int i=0;i<certifisearch.size();i++){
                        if("016".equals(certifisearch.get(i).get("groupcode").toString())){
                            detCode1=certifisearch.get(i).get("detcode").toString();
                            detCName1=certifisearch.get(i).get("detcodename").toString();
                            
                            if(!"5".equals(certifisearch.get(i).get("detcode").toString())){
                                if(edutake!=null&&detCode1.equals(edutake.get("code_kind1").toString())){
                                    out.println("<option value='"+detCode1+"' selected>"+detCName1+"</option>");
                                }else{
                                    out.println("<option value='"+detCode1+"'>"+detCName1+"</option>");
	                            }
	                        }
	                    }
	                 }
                 %>   
                    </select>
                </td>
             </tr>                       
             
             <tr>
               <td class="alt1">교육명</td>
               <td colspan="3" class="alt2">
               <select  name="edutest_name1"  id="edutest_name1" style="width:562px" onchange="javascript:setEduN(this.value);">
               <option value="">선택</option> 
               </select>
               </td>
               
               <td class="alt">자격증구분</td>
               <td >
               <input type="text" name="certifi_name" readonly class="readOnly1" onkeydown="ban(this);"/>
                    </td>
             </tr>
        </table>        
    </form>
      <form id="list2" name="sForm3" method="post" action="">
        
        <table class="etable_02" cellspacing="0" summary="교육">
          <caption>교육및시험등록</caption>             
             <tr>
               <td class="nobg">※&nbsp;&nbsp;이름</td>             
<!--                JUMIN_DEL               -->
<!--               <td class="nobg1">※&nbsp;&nbsp;주민번호</td> -->
               <td class="nobg1">※&nbsp;&nbsp;생년월일</td>
               <td class="nobg1">※&nbsp;&nbsp;면허번호</td>
               <td class="nobg1">※&nbsp;&nbsp;핸드폰</td>
               <td class="nobg1">※&nbsp;&nbsp;근무처명</td>
               <td class="nobg1">※&nbsp;&nbsp;대상자</td>
               </tr>
               <tr>
               <td class="alt"><input type="text" id="name" size="5" name="oper_name1" value=""/></td>
<!--                <td><input type="text" id="name" size="4" name="oper_no11" maxlength="6" onKeyUp="javascript:check_Int('sForm3','oper_no11')"/>-<input type="password" id="name" size="6" name="oper_no12" maxlength="7" onKeyUp="javascript:check_Int('sForm3','oper_no12')"/></td> -->
               <td><input type="text" id="name" size="8" name="oper_birth11" maxlength="8" value="" onKeyUp="javascript:check_Int('sForm3','oper_birth11')"/> 예) 19990101</td>
               <td><input type="text" id="name" size="4" name="oper_lic_no1" maxlength="6" onKeyUp="javascript:check_Int('sForm3','oper_lic_no1')"/></td>
               <td><input type="text" id="name" size="12" name="oper_hp1" maxlength="11" onKeyUp="javascript:check_Int('sForm3','oper_hp1')"/></td>
               <td><input type="text" id="name" size="35" name="oper_comp_name11"/></td>
               <td>
               <select  id="name" name="code_operation1">
                    <%                  
                    for(int i=0;i<certifisearch.size();i++){
                        if("035".equals(certifisearch.get(i).get("groupcode").toString())){
                            detCode=certifisearch.get(i).get("detcode").toString();
                            detCName=certifisearch.get(i).get("detcodename").toString();
                            out.println("<option value="+detCode+">"+detCName+"</option>");
                        }
                    }
                 %> 
                    </select>
               
               </td>
             </tr>           
             <tr>
 
               <td class="nobg">유효일자</td>                              
               <td class="nobg1">유예시작일</td>
               <td class="nobg1">유예마지막일</td>
               <td class="nobg2">※&nbsp;&nbsp;접수상태</td>
               <td class="nobg2">영수증 발행일</td>
               <td class="nobg2">※&nbsp;&nbsp;결제방법</td>
<!--               <td class="nobg2">※&nbsp;&nbsp;첨부파일</td>             -->
                         
                </tr>  
                <tr>
               <td class="alt"><input type="text" id="datepicker2" size="5" name="result_end_dt1"/></td>
               <td><input type="text" id="datepicker" size="5" name="start_dt1" class="input" /></td>
               <td><input type="text" id="datepicker1" size="5" name="end_dt1"/></td>
               <td>
               <select  id="name" name="oper_state1">
                <option value="">전체</option>
                           <%
                    
                           for(int i=0;i<certifisearch.size();i++){
                            if("022".equals(certifisearch.get(i).get("groupcode").toString())){
                                detCode=certifisearch.get(i).get("detcode").toString();
                                detCName=certifisearch.get(i).get("detcodename").toString();
                                    out.println("<option value='"+detCode+"'>"+detCName+"</option>");
                            }
                        }
                     %>               
                </select> 
               </td>
               <td><input type="text" id="datepicker3" size="5" name="confirm_dt" class="input" /></td>
               <td >
               <select  id="name" name="code_pay_flag1" >
               <option value="">전체</option>
                    <%                  
                    for(int i=0;i<certifisearch.size();i++){
                        if("014".equals(certifisearch.get(i).get("groupcode").toString())){
                            detCode=certifisearch.get(i).get("detcode").toString();
                            detCName=certifisearch.get(i).get("detcodename").toString();
                            out.println("<option value="+detCode+">"+detCName+"</option>");
                        }
                    }
                 %> 
                </select>
              </td>
               
              </tr>
                                     
             <tr style="display:none;" class="cresult">
               <td class="nobg" colspan="6">대상선택</td>                              
                </tr>
                  
                <tr style="display:none;" class="cresult">
               <td class="alt" colspan="6">
                <div style="border:solid 0px red;" id="cresult_list" >
                    <div style="border:solid 0px blue; text-align:left; display:none; cursor:default;" class="cresult_basic">
                        <input type="radio" name="cresult_check" class="cresult_check" /> <label class="cresult_check_label">아이디</label>
                        - <span class="cresult_code_member">회원종류</span> 
                        , <span class="cresult_pers_hp">핸드폰</span> 
                        , <span class="cresult_lic_no">면허번호</span> 
                    </div>
                </div>
               </td>               
             </tr>
                                     
        </table>
        
        <p style="float:left;margin-top:10px;">
            <a id="goUpdateMemberBtn" href="javascript:goUpdateMember();"><img src="images/icon_updateMember.gif" style="width:120px;height:20px;" onclick="" alt="개인정보 일괄변경" /></a>
        </p>

        <ul class="btnIcon_1" style="left:750px;">
               <%if( "minju51".equals(g_id) || "wnfo15".equals(g_id) || "dongbucni".equals(g_id) ){ %>
               <li><a href="javascript:goDel();"><img src="images/icon_delete.gif"    onclick="" alt="삭제" /></a></li>
<!--               <li><span onclick="goDel();" style="cursor:pointer;">[삭제]</span></li> -->
               <%} %>


               <li><a href="javascript:goClear();"><img src="images/icon_new.gif" onclick="" alt="신규" /></a></li>
               <li><a href="javascript:goSave();"><img src="images/icon_save.gif" onclick="" alt="저장" /></a></li>
               <li><a href="javascript:goSearch(sForm,0);"><img src="images/icon_search.gif" onclick="" alt="검색" /></a></li>     
        </ul>
        
    </form>
 <br><br>
 <table id="list"></table>
<div id="pager2"></div>
<form name="sForm2" method="post">

<p class="btnSave">
<a id="goSaveBatchBtn" href="javascript:goSaveBatch();" ><img src="images/icon_processing.gif"  onclick=""      alt="일괄처리" /></a>
<!-- <span onclick="goSaveBatch();" style="cursor:pointer;">[일괄처리]</span> -->
<a href="javascript:sendDM();"  ><img src="images/icon_s1.gif"  onclick=""      alt="DM발송" /></a>
<!-- <a href="javascript:notePad();"    ><img src="images/icon_s2.gif"  onclick=""  alt="쪽지" /></a> -->
<!-- <a href="javascript:sendEmail();"><img src="images/icon_s3.gif"    onclick=""  alt="메일전송"  /></a>
<a href="javascript:smssand();"><img src="images/icon_s4.gif"   onclick=""  alt="문자전송"  /></a> -->
<a href="#"><img src="images/icon_s5.gif" id="memberAdvancedSearch" alt="상세검색" /></a>
<a href="javascript:upExcel();"><img src="images/icon_s16.gif" onclick="" /></a>
<a href="javascript:excelDown();"><img src="images/icon_excel.gif" onclick="" /></a>
<!-- <a href="javascript:goPrint();"><img src="images/icon_s19.gif" onclick="" /></a> -->
</p>
</form>
<form name="sFormDel" method="post"></form>
<form name="sFormBatch" method="post"></form>
 </div><!--rTabmenu_01 End-->                     
    </div><!--contents End-->   
 
 </body> 
</html>
<iframe name="frm" width="0" height="0" tabindex=-1></iframe>
