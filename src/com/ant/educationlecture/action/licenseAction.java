package com.ant.educationlecture.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.common.util.CommonUtil;
import com.ant.common.util.StringUtil;
import com.ant.document.dao.documentDao;
import com.ant.educationexam.dao.educationDao;
import com.ant.educationlecture.dao.licenseDao;
import com.ant.member.state.dao.memberStateDao;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class licenseAction extends DispatchAction {

	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";	

	/*시험별응시현황*/	
	public ActionForward licenseSend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("licenseSendList"));
	}
	
	/*시험별응시현황 출력*/	
	public ActionForward licenseSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
//		System.out.println("대상자=====================================================>"+request.getParameter("code_operation1"));
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

			if(request.getParameter("code_certifi1") != null||request.getParameter("yyyy1") != null||request.getParameter("operation_cnt1") != null
			||request.getParameter("season1") != null||request.getParameter("code_operation1") != null||request.getParameter("oper_state1") != null
			||request.getParameter("result_state1") != null||request.getParameter("code_bran1") != null||request.getParameter("operation_place1") != null
			||request.getParameter("edutest_name1") != null||request.getParameter("oper_name1") != null||request.getParameter("oper_lic_no1") != null
//			JUMIN_DEL
//			||request.getParameter("oper_no1") != null
			||request.getParameter("oper_birth1") != null
			||request.getParameter("oper_hp1") != null||request.getParameter("oper_email1") != null
			||request.getParameter("person_yn1") != null||request.getParameter("attend_cnt1") != null||request.getParameter("bran_seq1") != null
			||request.getParameter("code_kind1") != null||request.getParameter("code_seq1") != null||request.getParameter("receipt_no1") != null){
				/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
		map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수
		map.put("season1",        		request.getParameter("season1"));										//학기
		map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
//		System.out.println("대상자==========================================>"+request.getParameter("code_operation1"));
		map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
		map.put("result_state1",       	request.getParameter("result_state1"));									//상태
		map.put("code_bran1",       	request.getParameter("code_bran1"));									//교육주최
		map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
		map.put("detcode",       	    request.getParameter("edutest_name1"));									//내용
		map.put("oper_name1",       	request.getParameter("oper_name1"));									//이름
		map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
//		JUMIN_DEL
//		map.put("oper_no1",       		request.getParameter("oper_no1"));										//주민번호
		map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
		map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
		map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
		map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
		map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석
		map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
		map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
		map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
		map.put("receipt_no1",       	request.getParameter("receipt_no1"));									//접수번호
		map.put("sel", "Y");
//		map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
		
		/*System.out.println("이름값="+URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));
		System.out.println("이름값1="+request.getParameter("oper_name1"));*/
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");

		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		map.put("nstart", nstart);
		
		map.put("nend", nend);
		
		int ncount = 0;
		List<Map> licensecount=dao.licensesendcnt(map); 
		
		ncount = ((Integer)(licensecount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> selectlicense = dao.selectlicense(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		for(int i=0; i<selectlicense.size(); i++){
			/* 그리드에 출력하는 컬럼 순서*/
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( selectlicense.get(i).get("operation_cnt")		== null ? "" : selectlicense.get(i).get("operation_cnt"))  	+"\",");   	//횟수
			result.append("\"" + ( selectlicense.get(i).get("season")				== null ? "" : selectlicense.get(i).get("season"))  		+"\",");   	//학기
			result.append("\"" + ( selectlicense.get(i).get("edutest_name")   		== null ? "" : selectlicense.get(i).get("edutest_name") )   +"\",");	//내용
			result.append("\"" + ( selectlicense.get(i).get("oper_name")			== null ? "" : selectlicense.get(i).get("oper_name")) 	  	+"\",");	//이름
			result.append("\"" + ( selectlicense.get(i).get("oper_lic_no")	    	== null ? "" : selectlicense.get(i).get("oper_lic_no") )    +"\",");	//면허번호
//			JUMIN_DEL
//			result.append("\"" + ( selectlicense.get(i).get("oper_no")				== null ? "" : selectlicense.get(i).get("oper_no") )  		+"\",");	//주민번호
			result.append("\"" + ( selectlicense.get(i).get("oper_birth")				== null ? "" : selectlicense.get(i).get("oper_birth") )  		+"\",");	//생년월일
			result.append("\"" + ( selectlicense.get(i).get("oper_hp")				== null ? "" : selectlicense.get(i).get("oper_hp") )      	+"\",");	//핸드폰
			result.append("\"" + ( selectlicense.get(i).get("oper_email")			== null ? "" : selectlicense.get(i).get("oper_email")) 		+"\",");	//이메일	
			result.append("\"" + ( selectlicense.get(i).get("person_yn")	   		== null ? "" : selectlicense.get(i).get("person_yn") )     	+"\",");	//회원구분
			result.append("\"" + ( selectlicense.get(i).get("attend_cnt")	    	== null ? "" : selectlicense.get(i).get("attend_cnt") )     +"\",");	//출석
			result.append("\"" + ( selectlicense.get(i).get("operstate")			== null ? "" : selectlicense.get(i).get("operstate") )  	+"\","); 	//상태
//			result.append("\"" + ( selectlicense.get(i).get("resultstate")	    	== null ? "" : selectlicense.get(i).get("resultstate") )    +"\",");	//상태text값
			result.append("\"" + ( selectlicense.get(i).get("codeoperation")	    	== null ? "" : selectlicense.get(i).get("codeoperation") )    +"\",");	//상태text값
			
			result.append("\"" + ( selectlicense.get(i).get("operation_place")	    == null ? "" : selectlicense.get(i).get("operation_place") )+"\",");	//시험장소
			result.append("\"" + ( selectlicense.get(i).get("code_certifi")	    	== null ? "" : selectlicense.get(i).get("code_certifi") )   +"\",");	//구분코드
			result.append("\"" + ( selectlicense.get(i).get("yyyy")	    			== null ? "" : selectlicense.get(i).get("yyyy") )    		+"\",");	//년도
			result.append("\"" + ( selectlicense.get(i).get("code_operation")	   	== null ? "" : selectlicense.get(i).get("code_operation") ) +"\",");	//대상자
			result.append("\"" + ( selectlicense.get(i).get("oper_state")	    	== null ? "" : selectlicense.get(i).get("oper_state") )    	+"\",");	//결제여부
			result.append("\"" + ( selectlicense.get(i).get("code_bran")	    	== null ? "" : selectlicense.get(i).get("code_bran") )      +"\",");	//교육주최
			result.append("\"" + ( selectlicense.get(i).get("bran_seq")	    		== null ? "" : selectlicense.get(i).get("bran_seq") )   	+"\",");	//지부순번
			result.append("\"" + ( selectlicense.get(i).get("code_kind")			== null ? "" : selectlicense.get(i).get("code_kind") ) 		+"\",");	//종류
			result.append("\"" + ( selectlicense.get(i).get("code_seq")				== null ? "" : selectlicense.get(i).get("code_seq") ) 		+"\",");	//순번
			result.append("\"" + ( selectlicense.get(i).get("receipt_no")			== null ? "" : selectlicense.get(i).get("receipt_no") ) 	+"\",");		//접수번호
			result.append("\"" + ( selectlicense.get(i).get("result_state")			== null ? "" : selectlicense.get(i).get("result_state") )  	+"\","); 	//상태
			
			result.append("\"" + ( selectlicense.get(i).get("code_pers")	   		== null ? "" : selectlicense.get(i).get("code_pers") )     	+"\",");	//히든 주민번호(xml에서 추가한 명칭값과동일하게 )
			result.append("\"" + ( selectlicense.get(i).get("person_yn1")	    	== null ? "" : selectlicense.get(i).get("person_yn1") )     +"\"");		//히든 회원코드값 )
			result.append("]}");
			
		}	

		result.append("]}");
		request.setAttribute("result",result);
	}

		return (mapping.findForward("ajaxout"));
}
	
	public ActionForward licenseSendSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 
		if(request.getParameter("code_certifi1") != null||request.getParameter("yyyy1") != null||request.getParameter("operation_cnt1") != null
				||request.getParameter("season1") != null||request.getParameter("code_operation1") != null||request.getParameter("oper_state1") != null
				||request.getParameter("result_state1") != null||request.getParameter("code_bran1") != null||request.getParameter("operation_place1") != null
				||request.getParameter("edutest_name1") != null||request.getParameter("bran_seq1") != null
				||request.getParameter("code_kind1") != null||request.getParameter("code_seq1") != null||request.getParameter("receipt_no1") != null){
			map.put("code_kind1",       	request.getParameter("code_kind1"));
			map.put("code_certifi1",        request.getParameter("code_certifi1"));	
			map.put("code_seq1",       		request.getParameter("code_seq1"));
			map.put("code_bran1",       	request.getParameter("code_bran1"));
			map.put("bran_seq1",       		request.getParameter("bran_seq1"));										
			map.put("receipt_no1",       	request.getParameter("receipt_no1"));
			map.put("oper_state1",       	request.getParameter("oper_state1"));
			
			int uOPsate=dao.uOPstate(map);
			
		}
		
		return (mapping.findForward("licenseSendList"));
	}
	public ActionForward licenseSendSaveBatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		licenseDao dao = new licenseDao();
		
		String oper_infos_org_str =  (String)request.getParameter("oper_infos");

		//-- 전체 일괄처리의 경우 [START]
		if("".equals(StringUtil.NVL(oper_infos_org_str))){
			Map map3 = new HashMap();
			
			map3.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
			map3.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
			map3.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수
			map3.put("season1",        		request.getParameter("season1"));										//학기
			map3.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
			map3.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
			map3.put("result_state1",       	request.getParameter("result_state1"));									//상태
			map3.put("code_bran1",       	request.getParameter("code_bran1"));									//교육주최
			map3.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
			map3.put("detcode",       	    request.getParameter("edutest_name1"));									//내용
			map3.put("oper_name1",       	request.getParameter("oper_name1"));									//이름
			map3.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
			map3.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
			map3.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
			map3.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
			map3.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
			map3.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석
			map3.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
			map3.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
			map3.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
			map3.put("receipt_no1",       	request.getParameter("receipt_no1"));									//접수번호
			map3.put("sel", "Y");

			map3.put("nstart", 0);
			map3.put("nend", 1000000);
			
			List<Map> result= dao.selectlicense(map3);
	    	
	    	StringBuffer oper_infos_org_str_buf = new StringBuffer();
	    	if(result!=null){
		    	for(int i=0; i<result.size(); i++){
					Map rowdata= 	result.get(i);
					
					if(oper_infos_org_str_buf.length() > 0) {
						oper_infos_org_str_buf.append("__");
					}
					oper_infos_org_str_buf.append((String)rowdata.get("code_kind")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("code_certifi")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("code_seq")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("code_bran")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("bran_seq")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("receipt_no"));
		    	}
	    	}
	    	oper_infos_org_str = oper_infos_org_str_buf != null ?  oper_infos_org_str_buf.toString() : "";
		}
		//-- 전체 일괄처리의 경우 [END]
		
		if(!"".equals(StringUtil.NVL(oper_infos_org_str))){
			List<String> oper_infos_org =  Arrays.asList(oper_infos_org_str.split("__"));
			List<List<String>> oper_infos = new ArrayList<List<String>>();
			
			for(String item : oper_infos_org){
				List<String> oper_info =  Arrays.asList(item.split("_"));
				oper_infos.add(oper_info);
			}
			
			for(List<String> oper_info : oper_infos){
				String code_kind1 = oper_info.get(0);
				String code_certifi1 = oper_info.get(1);
				String code_seq1 = oper_info.get(2);
				String code_bran1 = oper_info.get(3);
				String bran_seq1 = oper_info.get(4);
				String receipt_no1 = oper_info.get(5);
		
				map.put("code_kind1",       	code_kind1);
				map.put("code_certifi1",        code_certifi1);	
				map.put("code_seq1",       		code_seq1);
				map.put("code_bran1",       	code_bran1);
				map.put("bran_seq1",       		bran_seq1);										
				map.put("receipt_no1",       	receipt_no1);
				
				int uOPsate=dao.uOPstateBatch(map);
			}
		}
		
		return (mapping.findForward("licenseSendList"));
	}
	public ActionForward licenseDel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		licenseDao dao = new licenseDao();

		map.put("code_kind1",       	request.getParameter("code_kind1"));
		map.put("code_certifi1",        request.getParameter("code_certifi1"));	
		map.put("code_seq1",       		request.getParameter("code_seq1"));
		map.put("code_bran1",       	request.getParameter("code_bran1"));
		map.put("bran_seq1",       		request.getParameter("bran_seq1"));										
		map.put("receipt_no1",       	request.getParameter("receipt_no1"));
		
		int uOPsate=dao.uOPdelete(map);
		
		return (mapping.findForward("licenseSendList"));
	}
	//자격증 시험별응시현황 엑셀 저장 =================================11-13작업중
	public void conditionListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		licenseDao dao = new licenseDao(); 
		memberStateDao dao1=new memberStateDao(); 
		
		HttpSession session=request.getSession();
		String user=session.getAttribute("G_ID").toString(); //유저ID
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
				
		//여기부터 개발자 변경 필요
		String name="takeconditionList"; //프로그램명==>시험별응시현황 엑셀저장
		String s_name="시험별응시현황"; //엑셀 시트명
//		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"upYN","detcode","receipt_no","season","operation_cnt", "edutest_name", "yyyy", "code_bran", "code_certifi"
							,"oper_name","oper_lic_no"
//							JUMIN_DEL
//							,"oper_no"
							,"oper_birth"
							,"oper_hp","operemail","person_yn","code_operation","result_state","oper_state","attend_cnt","operation_place","refund_bank", "refund_account", "refund_account_owner"}; //헤더 영문
		String header_k[]={"*신규/갱신(i/u)","*Key", "(접수번호)","(학기)","(횟수)", "(내용)", "(시험년도)", "(시험주최)", "(시험구분)"
							,"*이름","*면허번호"
//							JUMIN_DEL
//							,"*주민번호"
							,"*생년월일"
							,"*핸드폰","*이메일","회원구분","*대상자","응시결과상태","*응시상태","출석","시험장소", "(환불계좌은행명)","(환불계좌)","(환불계좌예금주)"}; //헤더 국문
		int c_size[]={14,14,9,5,5,58,9,9,9,9,9,15,13,27,23,9,13,9,5,27,20,20,20};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		
//		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_certifi1") != null||request.getParameter("edutest_name1") != null){
		if(request.getParameter("code_certifi1") != null||request.getParameter("yyyy1") != null||request.getParameter("operation_cnt1") != null
		||request.getParameter("season1") != null||request.getParameter("code_operation1") != null||request.getParameter("oper_state1") != null
		||request.getParameter("result_state1") != null||request.getParameter("code_bran1") != null||request.getParameter("operation_place1") != null
		||request.getParameter("edutest_name1") != null||request.getParameter("oper_name1") != null||request.getParameter("oper_lic_no1") != null
		||request.getParameter("oper_birth1") != null
		||request.getParameter("oper_hp1") != null||request.getParameter("oper_email1") != null
		||request.getParameter("person_yn1") != null||request.getParameter("attend_cnt1") != null||request.getParameter("bran_seq1") != null
		||request.getParameter("code_kind1") != null||request.getParameter("code_seq1") != null||request.getParameter("receipt_no1") != null){
			
			map.put("nstart", request.getParameter("nstart"));
			map.put("nend"	, request.getParameter("nend"));
		
		
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1",        request.getParameter("code_certifi1"));			//자격증 구분
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1",        		request.getParameter("yyyy1"));					//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1",        	request.getParameter("code_bran1"));			//교육주최
			if(request.getParameter("season1"			)!=null)map.put("season1",        		request.getParameter("season1"));				//학기
			if(request.getParameter("result_state1"		)!=null)map.put("result_state1",        request.getParameter("result_state1"));			//응시결과상태
			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1",        	request.getParameter("oper_state1"));			//응시상태
			if(request.getParameter("edutest_name1"		)!=null)map.put("detcode",        	    request.getParameter("edutest_name1"));			//응시상태
			map.put("check", request.getParameter("check"));
			map.put("sel", "Y");
		}

			List<Map> conditionExcel=dao.conditionExcel(map,true);
		//-----------여기까지 변경---------------------------------------------------------------------------------------
		
		//이 이하는 변경할 필요 없음
			int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
			String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
		CommonUtil.makeExcelFile(conditionExcel, filename, s_name,header_e,header_k,c_size);
		
		File f=new File(fileDir+filename); //파일생성
		
		String contentType=request.getContentType();
		response.setContentType("x-msdownload");
		
		if(contentType==null){
			if(request.getHeader("user-agent").indexOf("MSIE 5.5")!=-1)
				response.setContentType("doesn/matter;");
			else
				response.setContentType("application/octet-stream");
		}else{
			response.setContentType(contentType);
		}
		
		response.setHeader("Content-Transfer-Encoding:", "binary");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
		response.setHeader("Content-Length", ""+f.length());
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		byte b[]=new byte[1024];
		BufferedInputStream fin=new BufferedInputStream(new FileInputStream(f));
		BufferedOutputStream fout=new BufferedOutputStream(response.getOutputStream());
		
		try{
			int read=0;
			while((read=fin.read(b))!=-1){
				fout.write(b, 0, read);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fout!=null) fout.close();
			if(fin!=null) fin.close();
		}
		
		List<Map> i=dao1.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}	
	
	/*
	 * 작업명 : Home>자격증>시험별응시현황
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.14
	 * 작   업 : memberpers_no		주민번호 체크
	 */
	public ActionForward pers_check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//전체건수 구해서 넘긴다.
		Map map = new HashMap();
		licenseDao dao=new licenseDao();
		
		//조건 입력
		// 변수에 값 받아서 넣음
		if(request.getParameter("operation_place1"	)!=null)map.put("operation_place1"	, URLDecoder.decode(request.getParameter("operation_place1"	),"UTF-8"));	//시험장소
		if(request.getParameter("edutest_name1"		)!=null)map.put("detcode"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
		if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
		if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
		if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
		if(request.getParameter("season1"			)!=null)map.put("season1"			, request.getParameter("season1"		));	//학기
		if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
		if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//결제여부
		if(request.getParameter("result_state1"		)!=null)map.put("result_state1"		, request.getParameter("result_state1"	));	//상태
		if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
		if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
//		JUMIN_DEL
//		if(request.getParameter("oper_no1"			)!=null)map.put("oper_no1"			, request.getParameter("oper_no1"		));	//주민번호
		if(request.getParameter("oper_birth1"			)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
		if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
		if(request.getParameter("oper_email1"		)!=null)map.put("oper_email1"		, request.getParameter("oper_email1"	));	//이메일
		if(request.getParameter("person_yn1"		)!=null)map.put("person_yn1"		, request.getParameter("person_yn1"		));	//회원구분
		if(request.getParameter("attend_cnt1"		)!=null)map.put("attend_cnt1"		, request.getParameter("attend_cnt1"	));	//출석
		if(request.getParameter("bran_seq1"			)!=null)map.put("bran_seq1"			, request.getParameter("bran_seq1"		));	//지부순번
		if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
		if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
		if(request.getParameter("receipt_no1"		)!=null)map.put("receipt_no1"		, request.getParameter("receipt_no1"	));	//접수번호
		map.put("sel", "Y");

		//여기서 구하고 넘긴다.
		List<Map> count = dao.licensesendcnt(map);
		int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

		// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
		if(request.getParameter("edutest_name1"		)!=null)request.setAttribute("edutest_name1"	,  URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8") );	//내용
		if(request.getParameter("oper_name1"		)!=null)request.setAttribute("oper_name1"		,  URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8") );	//이름
		if(request.getParameter("operation_place1"	)!=null)request.setAttribute("operation_place1"	,  URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8") );	//시험장소
		
		String param = "";
		if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
		if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
		if(request.getParameter("season1"			)!=null)param+="&season1="			+ request.getParameter("season1"		);	//학기
		if(request.getParameter("code_operation1"	)!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");	//대상자
		if(request.getParameter("oper_state1"		)!=null)param+="&oper_state1="		+ request.getParameter("oper_state1"	);	//결제여부
		if(request.getParameter("result_state1"		)!=null)param+="&result_state1="	+ request.getParameter("result_state1"	);	//상태
		if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);		//교육주최
		if(request.getParameter("oper_lic_no1"		)!=null)param+="&oper_lic_no1="		+ request.getParameter("oper_lic_no1"	);	//면허번호
//		JUMIN_DEL
//		if(request.getParameter("oper_no1"			)!=null)param+="&oper_no1="			+ request.getParameter("oper_no1"		);	//주민번호
		if(request.getParameter("oper_birth1"			)!=null)param+="&oper_birth1="			+ request.getParameter("oper_birth1"		);	//생년월일
		if(request.getParameter("oper_hp1"			)!=null)param+="&oper_hp1="			+ request.getParameter("oper_hp1"		);	//핸드폰
		if(request.getParameter("oper_email1"		)!=null)param+="&oper_email1="		+ request.getParameter("oper_email1"	);	//이메일
		if(request.getParameter("person_yn1"		)!=null)param+="&person_yn1="		+ request.getParameter("person_yn1"		);		//회원구분
		if(request.getParameter("attend_cnt1"		)!=null)param+="&attend_cnt1="		+ request.getParameter("attend_cnt1"	);	//출석
		if(request.getParameter("bran_seq1"			)!=null)param+="&bran_seq1="		+ request.getParameter("bran_seq1"		);	//지부순번
		if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);		//교육종류
		if(request.getParameter("code_seq1"			)!=null)param+="&code_seq1="		+ request.getParameter("code_seq1"		);	//순번
		if(request.getParameter("receipt_no1"		)!=null)param+="&receipt_no1="		+ request.getParameter("receipt_no1"	);	//접수번호
		if(request.getParameter("edutest_name1"		)!=null)param+="&edutest_name1="	+ request.getParameter("edutest_name1"	);
		
//System.out.println("asdfasdf person_check in = "+param);
		//검색 변수 넘김
		request.setAttribute("param", param);
		//전체 개수 넘김
		request.setAttribute("ncount", ncount);

		request.setAttribute("url", "license");
		
	return (mapping.findForward("pers_check"));
			
	}
	
	
	
	//시험별응시현황 엑셀 업로드
	public ActionForward upconditionSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	return mapping.findForward("upconditionSendListr");
	}
	public ActionForward conditionSendListUpExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 
		List<Map> code=dao.sCodeExcel1(map);
		
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
				
		String saveFolder="D:\\WEB\\KDA_VER3\\upload\\excel\\";
		String encType="UTF-8";
		int maxSize=10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
		String filename="";
		
		Workbook openWorkbook;
		Sheet openSheet;
		String sheetName = "";
		Cell openCell;

		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				filename=multi.getFilesystemName(name);
				f=new File(saveFolder+"/"+filename);
				
				// 엑셀을 불러 들어 온다.
				openWorkbook = Workbook.getWorkbook(f);
				//엑셀의 첫번째 시트 인덱스(0)을 얻어 온다.
				openSheet = openWorkbook.getSheet(0);
				  
				int iCols = openSheet.getColumns();
				int iRows = openSheet.getRows();

				String sResultString =  "";  
				
				
				int cellLen = 0;
				for(int i=1; i < iRows; i++){
					Cell cell[] = openSheet.getRow(i);
				    cellLen = cell.length;
//				    System.out.println("cellLen=======>"+cellLen);
							
					if("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())||"i".equals(cell[0].getContents())||"I".equals(cell[0].getContents())){
					    				
					    map.put("yyyy1",        		cell[6].getContents());		//교육년도
					    map.put("code_bran1",        	cell[1].getContents().substring(6, 8));		//교육주최(key)
					    map.put("code_kind1",        	cell[1].getContents().substring(0, 1));		//교육종류(key)
					    map.put("edutest_name1",        cell[1].getContents());		//내용
					    map.put("code_certifi1",        cell[1].getContents().substring(1, 2));				//구분코드(교육및시험구분)(key)
	
						map.put("code_operation1",      getDetcode(code,"035",cell[15].getContents()));		//대상자
//						map.put("code_pay_flag1",       getDetcode(code,"014",cell[4].getContents()));		//결제방법
						map.put("oper_name1",        	cell[9].getContents());								//이름
//						JUMIN_DEL
//						map.put("oper_no1",       		cell[11].getContents());							//주민번호
						map.put("oper_birth1",       		cell[11].getContents());							//생년월일
	
						map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
						map.put("oper_hp1",       		cell[12].getContents());							//핸드폰
						map.put("oper_email1",       	cell[13].getContents());							//이메일
						map.put("oper_state1",       	getDetcode(code,"022",cell[17].getContents()));		//진행상태
						map.put("receipt_no1",       	cell[2].getContents());								//접수번호
						map.put("bran_seq1",       		cell[1].getContents().substring(8, 11));				//지부순번
						map.put("code_seq1",       		cell[1].getContents().substring(2, 6));				//순번
						map.put("attend_cnt1",       	cell[18].getContents());							//출석
						map.put("operation_place1",     cell[19].getContents());							//시험장소
						
						map.put("register1",       		g_name);
						
						//주민등록번호로 회원정보 읽어오기
						List<Map> selectpersonmtbl1 = dao.selectpersonmtbl1(map,true);
						
						if ( selectpersonmtbl1.size() > 0 ) {
							if ( "4".equals(cell[1].getContents().substring(0, 1)) ) {
								map.put("person_yn1",       	( selectpersonmtbl1.get(0).get("code_pers")	== null ? "" : selectpersonmtbl1.get(0).get("code_pers")) );		//회원코드
	
								map.put("oper_name1",        	cell[9].getContents());								//이름
//								JUMIN_DEL
//								map.put("oper_no1",       		cell[11].getContents());							//주민번호
								map.put("oper_birth1",       		cell[11].getContents());							//생년월일
								map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
								map.put("oper_hp1",       		cell[12].getContents());							//핸드폰
								map.put("oper_email1",       	cell[13].getContents() );							//email
							
							} else {
								map.put("oper_name1",        	( selectpersonmtbl1.get(0).get("pers_name")	== null ? "" : selectpersonmtbl1.get(0).get("pers_name")) );		//이름
								map.put("oper_lic_no1",       	( selectpersonmtbl1.get(0).get("lic_no")		== null ? "" : selectpersonmtbl1.get(0).get("lic_no")) );		//면허번호
								map.put("oper_hp1",       		( selectpersonmtbl1.get(0).get("pers_hp")	== null ? "" : selectpersonmtbl1.get(0).get("pers_hp")) );		//핸드폰
								map.put("oper_email1",       	( selectpersonmtbl1.get(0).get("email")		== null ? "" : selectpersonmtbl1.get(0).get("email")) );			//email
								map.put("oper_comp_name11",     ( selectpersonmtbl1.get(0).get("company_name")	== null ? "" : selectpersonmtbl1.get(0).get("company_name")) );		//근무처명
								map.put("code_post1",       	( selectpersonmtbl1.get(0).get("code_post")	== null ? "" : selectpersonmtbl1.get(0).get("code_post")) );		//우편번호
								map.put("oper_comp_add1_21",    ( selectpersonmtbl1.get(0).get("comp_add")	== null ? "" : selectpersonmtbl1.get(0).get("comp_add")) );		//주소
								map.put("person_yn1",       	( selectpersonmtbl1.get(0).get("code_pers")	== null ? "" : selectpersonmtbl1.get(0).get("code_pers")) );		//회원코드
								map.put("code_use1",       		( selectpersonmtbl1.get(0).get("code_use")	== null ? "" : selectpersonmtbl1.get(0).get("code_use")) );		//운영형태
								map.put("trust_name1",       	( selectpersonmtbl1.get(0).get("trust_name")	== null ? "" : selectpersonmtbl1.get(0).get("trust_name")) );	//위탁업체명
								map.put("code_great1",       	( selectpersonmtbl1.get(0).get("code_great")	== null ? "" : selectpersonmtbl1.get(0).get("code_great")) );	//근무처대분류
								map.put("company_tel1",       	( selectpersonmtbl1.get(0).get("company_tel")	== null ? "" : selectpersonmtbl1.get(0).get("company_tel")) );		//전화번호
							}
	
						} else {
							map.put("person_yn1",       	"" );		//회원코드
						}
	
						System.out.println("접수번호="+cell[2].getContents());
						System.out.println("회원코드:		"+map.get("person_yn1"));
						System.out.println("이름:		"+map.get("oper_name1"));
						System.out.println("교육주최:	"+cell[1].getContents().substring(5, 7));
						System.out.println("교육종류:	"+cell[1].getContents().substring(0, 1));
						System.out.println("db_licno:	"+map.get("oper_lic_no1"));
						System.out.println("db_comp_name:	"+map.get("oper_comp_name11"));
		
						List<Map> selectoperater1 = dao.selectoperater1(map);
						if("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())){
							
							if ( selectoperater1.size() > 0 ) {
								map.put("receipt_no1",       	( selectoperater1.get(0).get("receipt_no")	== null ? "" : selectoperater1.get(0).get("receipt_no")) );		//접수번호
	//								System.out.println("접수번호 up="+selectoperater.get(0).get("receipt_no"));
									int updatelicense = dao.updatelicense(map);
									
									if ( updatelicense < 1 ){
										sResultString = sResultString + (i+1) +"번라인 수정중 에러 <br>"; // +  (char) 13;
									}
							}
						} else if("i".equals(cell[0].getContents())||"I".equals(cell[0].getContents())){
							if ( selectoperater1.size() <= 0 ) {
	
									map.put("receipt_no1",       	"" );		//접수번호
	//								System.out.println("접수번호 in=");
									try {
										List<Map> licenseinsert = dao.insertlicense(map);
									} catch (Exception e) {
										sResultString = sResultString + (i+1) +"번라인 입력중 에러 <br>"; //+ (char) 13;
									}
							} else {
								sResultString = sResultString + (i+1) +"번라인 입력중 중복에러 <br>";
								
							}
						}
					}
	/*			    Map map1 = new HashMap();
					dao = new educationDao(); 

					map1.put("yyyy1",        		cell[3].getContents());									//교육년도
					map1.put("code_bran1",        	cell[1].getContents().substring(5, 7));					//교육주최
					map1.put("code_kind1",        	cell[1].getContents().substring(0, 1));					//교육종류
					map1.put("edutest_name1",       cell[1].getContents());									//교육명키값

					List<Map> educationsend1 = dao.educationsend1(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
					JSONArray j_educationsend=JSONArray.fromObject(educationsend1);
					request.setAttribute("educationsend1" , educationsend1);
					request.setAttribute("j_educationsend" , j_educationsend);
					request.setAttribute("edutakeets", map1);

					return mapping.findForward("eduinsert_send");//저장후 저장한값을 화면에 다시 표출해야한다
	*/
				}  //for
				
				if("".equals(sResultString)) sResultString = "정상 처리 되었습니다."; 
				request.setAttribute("sResultString", sResultString);
				openWorkbook.close();
				
			}   //while
		}catch(Exception e){   //try
			e.printStackTrace();
		}
		return (mapping.findForward("excelLink"));
	}  //Exception

	
	/*결과등록*/	
	public ActionForward resultSend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("resultSendList"));
	}
	
	/*결과등록  출력*/	
	public ActionForward resultSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

			if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_certifi1") != null
					||request.getParameter("season1") != null||request.getParameter("operation_cnt1") != null||request.getParameter("code_operation1") != null
					||request.getParameter("print_kind") != null||request.getParameter("finish_point1") != null||request.getParameter("finish_time1") != null
					||request.getParameter("finish_date1") != null||request.getParameter("operation_place1") != null||request.getParameter("oper_name1") != null
//					JUMIN_DEL
//					||request.getParameter("oper_no1") != null
					||request.getParameter("oper_birth1") != null
					||request.getParameter("oper_lic_no1") != null||request.getParameter("attend_cnt1") != null
					||request.getParameter("result_state1") != null||request.getParameter("result_point1") != null||request.getParameter("oper_hp1") != null
					||request.getParameter("oper_email1") != null||request.getParameter("person_yn1") != null||request.getParameter("code_kind1") != null
					||request.getParameter("code_seq1") != null||request.getParameter("bran_seq1") != null||request.getParameter("receipt_no1") != null
					||request.getParameter("edutest_name1") != null){
				/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//지부
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
		map.put("season1",        		request.getParameter("season1"));										//학기
		map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수		
		map.put("edutest_name1",      	request.getParameter("edutest_name1"));									//시험명
		map.put("print_kind",       	request.getParameter("print_kind1"));									//증서발급
		map.put("finish_point1",       	request.getParameter("finish_point1"));									//합격가능점수
		map.put("finish_time1",       	request.getParameter("finish_time1"));									//이수시간
		map.put("finish_date1",       	request.getParameter("finish_date1"));									//이수일수
		map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
		map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
//		JUMIN_DEL
//		map.put("oper_no1",       		request.getParameter("oper_no1"));										//주민번호
		map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
		map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
		map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
		map.put("result_state1",       	request.getParameter("result_state1"));									//상태
		map.put("result_point1",       	request.getParameter("result_point1"));									//시험점수
		map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
		map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
		map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
		map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
		map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
		map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
		map.put("receipt_no1",     		request.getParameter("receipt_no1"));									//접수번호
		map.put("edutest_name1",     	URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//시험명
		map.put("check",     	 "2");		//시험명
		map.put("sel", "Y");
		if(!request.getParameter("code_operation1").equals("0")) map.put("code_operation1",      request.getParameter("code_operation1"));								
		
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");

		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		map.put("nstart", nstart);
		
		map.put("nend", nend);
		
		int ncount = 0;
//		System.out.println("테스트================================================================1111111111111");
		List<Map> resultcount=dao.resultsendcnt(map); 
//		System.out.println("테스트================================================================2222222222222");
		//그리드에 뿌려주는 카운터
		ncount = ((Integer)(resultcount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> selectresult = dao.selectresult(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		for(int i=0; i<selectresult.size(); i++){
			/* 그리드에 출력하는 컬럼 순서*/
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( selectresult.get(i).get("operation_cnt")		== null ? "" : selectresult.get(i).get("operation_cnt"))  	+"\",");   	//횟수
			result.append("\"" + ( selectresult.get(i).get("season")			== null ? "" : selectresult.get(i).get("season"))  			+"\",");   	//학기
			result.append("\"" + ( selectresult.get(i).get("edutest_name")		== null ? "" : selectresult.get(i).get("edutest_name"))  	+"\",");   	//시험명
			result.append("\"" + ( selectresult.get(i).get("oper_name")   		== null ? "" : selectresult.get(i).get("oper_name") )   	+"\",");	//이름
//			JUMIN_DEL
//			result.append("\"" + ( selectresult.get(i).get("oper_no")			== null ? "" : selectresult.get(i).get("oper_no")) 	  		+"\",");	//주민번호
			result.append("\"" + ( selectresult.get(i).get("oper_birth")			== null ? "" : selectresult.get(i).get("oper_birth")) 	  		+"\",");	//생년월일
			result.append("\"" + ( selectresult.get(i).get("oper_lic_no")	    == null ? "" : selectresult.get(i).get("oper_lic_no") )     +"\",");	//면허번호
			result.append("\"" + ( selectresult.get(i).get("oper_hp")			== null ? "" : selectresult.get(i).get("oper_hp") )  		+"\",");	//핸드폰
			result.append("\"" + ( selectresult.get(i).get("oper_email")		== null ? "" : selectresult.get(i).get("oper_email") )      +"\",");	//이메일
			result.append("\"" + ( selectresult.get(i).get("person_yn")			== null ? "" : selectresult.get(i).get("person_yn")) 		+"\",");	//회원구분
			result.append("\"" + ( selectresult.get(i).get("attend_cnt")	   	== null ? "" : selectresult.get(i).get("attend_cnt") )     	+"\",");	//출석일수
			result.append("\"" + ( selectresult.get(i).get("time_cnt")	   		== null ? "" : selectresult.get(i).get("time_cnt") )     	+"\",");	//이수시간
			result.append("\"" + ( selectresult.get(i).get("result_point")	   	== null ? "" : selectresult.get(i).get("result_point") )    +"\",");	//이수점수
			result.append("\"" + ( selectresult.get(i).get("resultstate")	    == null ? "" : selectresult.get(i).get("resultstate") )     +"\",");	//상태text값
			
			result.append("\"" + ( selectresult.get(i).get("result_point")		== null ? "" : selectresult.get(i).get("result_point") )  	+"\","); 	//시험점수
			result.append("\"" + ( selectresult.get(i).get("operation_place")	== null ? "" : selectresult.get(i).get("operation_place"))	+"\",");	//시험장소
			result.append("\"" + ( selectresult.get(i).get("printkind")	    	== null ? "" : selectresult.get(i).get("printkind") )   	+"\",");	//증서발급텍스트값
			result.append("\"" + ( selectresult.get(i).get("yyyy")	    		== null ? "" : selectresult.get(i).get("yyyy") )    		+"\",");	//년도
			result.append("\"" + ( selectresult.get(i).get("code_bran")	   		== null ? "" : selectresult.get(i).get("code_bran") )   	+"\",");	//지부
			result.append("\"" + ( selectresult.get(i).get("code_certifi")	    == null ? "" : selectresult.get(i).get("code_certifi") )    +"\",");	//구분코드
			result.append("\"" + ( selectresult.get(i).get("code_operation")	== null ? "" : selectresult.get(i).get("code_operation") )  +"\",");	//대상자
			result.append("\"" + ( selectresult.get(i).get("finish_point")	    == null ? "" : selectresult.get(i).get("finish_point") )	+"\",");	//합격가능점수
			result.append("\"" + ( selectresult.get(i).get("finish_time")	    == null ? "" : selectresult.get(i).get("finish_time") )   	+"\",");	//합격가능시간
			result.append("\"" + ( selectresult.get(i).get("finish_date")	    == null ? "" : selectresult.get(i).get("finish_date") )   	+"\",");	//합격가능일수
			result.append("\"" + ( selectresult.get(i).get("code_kind")			== null ? "" : selectresult.get(i).get("code_kind") ) 		+"\",");	//종류
			result.append("\"" + ( selectresult.get(i).get("code_seq")			== null ? "" : selectresult.get(i).get("code_seq") ) 		+"\",");	//순번
			result.append("\"" + ( selectresult.get(i).get("bran_seq")			== null ? "" : selectresult.get(i).get("bran_seq") ) 		+"\",");	//지부순번
//			result.append("\"" + ( selectresult.get(i).get("oper_no1")			== null ? "" : selectresult.get(i).get("oper_no1") ) 		+"\",");	//주민번호1
			result.append("\"" + ( selectresult.get(i).get("receipt_no")		== null ? "" : selectresult.get(i).get("receipt_no") ) 		+"\",");	//접수번호
			result.append("\"" + ( selectresult.get(i).get("result_state")		== null ? "" : selectresult.get(i).get("result_state") )  	+"\","); 	//상태
			result.append("\"" + ( selectresult.get(i).get("print_kind")	    == null ? "" : selectresult.get(i).get("print_kind") )   	+"\",");	//증서발급코드값
			result.append("\"" + ( selectresult.get(i).get("person_yn1")	    == null ? "" : selectresult.get(i).get("person_yn1") )     	+"\",");		//히든 회원코드값 )
			result.append("\"" + ( selectresult.get(i).get("receipt_no1")	    == null ? "" : selectresult.get(i).get("receipt_no1") )     	+"\",");		//히든 회원코드값 )
			result.append("\"" + ( selectresult.get(i).get("detcode")	    == null ? "" : selectresult.get(i).get("detcode") )     	+"\"");		//시험명코드값 )
			result.append("]}");
			
		}	

		result.append("]}");
		request.setAttribute("result",result);
	}

		return (mapping.findForward("ajaxout"));
}
	
	
	// 자격증결과등록 저장
		public ActionForward licenseResultSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
			HttpSession session=request.getSession();
			String g_name = session.getAttribute("G_NAME").toString(); 
			Map map = new HashMap();

			map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
			map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최(key)
			map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류(key)
			map.put("detcode1",        		request.getParameter("detcode1"));										//내용
			map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드(교육및시험구분)(key)
			
			map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_lic_no1",        	request.getParameter("oper_lic_no1"));									//면허번호
			map.put("oper_hp1",        		request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",        	request.getParameter("oper_email1"));									//이메일
			map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
			map.put("oper_state1",        	request.getParameter("oper_state1"));									//결제여부
			map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
			map.put("result_point1",        request.getParameter("result_point1"));									//시험점수
			map.put("attend_cnt1",        	request.getParameter("attend_cnt1"));									//출석일수
			map.put("time_cnt1",        	request.getParameter("time_cnt1"));										//이수시간
			map.put("receipt_no1",        	request.getParameter("receipt_no1"));									//접수번호
			map.put("bran_seq1",        	request.getParameter("bran_seq1"));										//지부순번(key)
			map.put("code_seq1",        	request.getParameter("code_seq1"));										//순번(key)
			map.put("season1",        		request.getParameter("season1"));										//반기
//			JUMIN_DEL
//			map.put("oper_no1",        		request.getParameter("oper_no1"));										//주민번호
			map.put("oper_birth1",        		request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",        	request.getParameter("oper_lic_no1"));									//면허번호
			map.put("g_name",        		g_name);																//등록자
			
			
			//jsp 넘긴 항목이랑 같은 갯수로 값을 받아야 한다.
			/*educationDao dao1 = new educationDao(); */
			licenseDao   dao = new licenseDao(); 

			System.out.println("detcode1==========>"+request.getParameter("detcode1"));
			//교육및시험명 테이블에서 관리점수항목 불러오기
			List<Map> selectexampoint1 = dao.selectexampoint1(map);

			int v_edu_marks 		= Integer.parseInt(selectexampoint1.get(0).get("edu_marks").toString());
			int v_service_marks 	= Integer.parseInt(selectexampoint1.get(0).get("service_marks").toString());
			int v_finish_time 		= Integer.parseInt(selectexampoint1.get(0).get("finish_time").toString());
			int v_finish_date 		= Integer.parseInt(selectexampoint1.get(0).get("finish_date").toString());
			int v_finish_point 		= Integer.parseInt(selectexampoint1.get(0).get("finish_point").toString());
			
			if ( (v_finish_point <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("result_point1"))))&&
				 (v_finish_date <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("attend_cnt1"))))&&
				 (v_finish_time <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("time_cnt1"))))
			){
				map.put("edu_marks1",        		v_edu_marks);										//이수점수
				map.put("service_marks1",        	v_service_marks);									//봉사점수
				map.put("result_state1",        "10");					//상태 (합격/이수)

			}else{
				if ( (v_finish_point > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("result_point1"))))
					){
						map.put("edu_marks1",        		"0");										//이수점수
						map.put("service_marks1",        	"0");									//봉사점수
						map.put("result_state1",        "04");					//상태 (시험성적미달)

					}else if ( (v_finish_date > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("attend_cnt1"))))
						){
							map.put("edu_marks1",        		"0");										//이수점수
							map.put("service_marks1",        	"0");									//봉사점수
							map.put("result_state1",        "04");					//상태 (합격/이수)

						} else	if ( (v_finish_time > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("time_cnt1"))))
							){
							map.put("edu_marks1",        		"0");										//이수점수
							map.put("service_marks1",        	"0");									//봉사점수
							map.put("result_state1",        "04");					//상태 (합격/이수)

							} else {
								map.put("edu_marks1",        		"0");										//이수점수
								map.put("service_marks1",        	"0");									//봉사점수
								map.put("result_state1",        "04");					//상태 (합격/이수)
								
							}
			}
			//if(점수나 출석일수나 이수시간이 있으면 update,존재하지 않으면 insert)
			if ("U".equals(request.getParameter("point_manage1").toString())){
				map.put("point_manage1",        request.getParameter("code_certifi1"));									//점수관리 자격증
				int updateresult = dao.updateresult(map);
				System.out.println("update=======>"+updateresult);
			}else{
				map.put("point_manage1",        request.getParameter("point_manage1").toString().substring(0, 1));									//점수관리 자격증
				List<Map> insertresult = dao.insertresult(map);
				System.out.println("insert=======>"+insertresult);
			}
			Map map1=new HashMap();
			
			map1.put("yyyy1",        		request.getParameter("yyyy1"));											
			map1.put("code_bran1",        	request.getParameter("code_bran1"));									
			map1.put("code_certifi1",        request.getParameter("code_certifi1"));
			
			List<Map> selectEtestN = dao.selectEtestN(map1); 

			JSONArray j_test=JSONArray.fromObject(selectEtestN);
			request.setAttribute("selectEtestN" , selectEtestN);
			
			request.setAttribute("j_test" , j_test);
			request.setAttribute("etest2", map);
			
			return mapping.findForward("licenseResultSave");//저장후 저장한값을 화면에 다시 표출해야한다
		}
	
		//자격증결과등록 엑셀 저장
		public void resultregistrationListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			Map map = new HashMap();
			Map map1=new HashMap();
			licenseDao dao = new licenseDao(); 
			memberStateDao dao1=new memberStateDao(); 
			
			String format="yyyyMMdd";
			SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
			String date=sdf.format(new java.util.Date()); //현재 날짜
			
			HttpSession session=request.getSession();
			String user=session.getAttribute("G_ID").toString(); //유저ID
			//여기부터 개발자 변경 필요
			String name="resultregistrationList"; //프로그램명==>시험별응시현황 엑셀저장
			String s_name="결과등록"; //엑셀 시트명
			String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
			String header_e[]={"upYN","detcode","receipt_no","yyyy","code_bran","code_certifi","season","operation_cnt","code_operation","edutest_name","print_kind"
								,"finish_point","finish_time","finish_date","operation_place","oper_name",
//								JUMIN_DEL
//								"oper_no",
								"oper_birth",
								"oper_lic_no","attend_cnt","result_state","result_point"
								,"time_cnt","oper_hp","oper_email","person_yn","oper_comp_name1","oper_comp_add1_1","code_post","code_kind","code_seq","bran_seq"}; //헤더 영문
			String header_k[]={"신규/갱신 구분","Key","접수번호","년도","지부","구분코드","학기","횟수","대상자","시험명","증서발급","합격가능점수","합격가능이수시간","합격가능이수일수"
								,"시험장소","이름",
//								JUMIN_DEL
//								"주민번호",
								"생년월일",
								"면허번호","출석일수","상태","시험점수","이수시간","핸드폰","이메일","회원구분","근무처","근무처주소","우편번호","종류","순번","지부순번"}; //헤더 국문
			int c_size[]={14,14,9,9,9,9,9,9,9,40,9,9,9,9,9,9,15,9,9,9,9,9,19,25,15,30,30,15,9,9,9};  //열 넓이를 위한 배열
			
			//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
			map1.put("prog_name", name);
			map1.put("create_user", user);
			map1.put("sheet_name", s_name);
		
//			if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_certifi1") != null||request.getParameter("edutest_name1") != null){
			
				map.put("nstart", request.getParameter("nstart"));
				map.put("nend"	, request.getParameter("nend"));
				
				
//				if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1",        		request.getParameter("yyyy1"));					//교육년도
//				if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1",        request.getParameter("code_certifi1"));			//자격증 구분
//				if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1",        	request.getParameter("code_bran1"));			//교육주최
//				if(request.getParameter("season1"			)!=null)map.put("season1",        		request.getParameter("season1"));				//학기
//				if(request.getParameter("edutest_name1"			)!=null)map.put("edutest_name1",    request.getParameter("edutest_name1"));				//학기
//				
//				map.put("check", request.getParameter("check"));
//				map.put("sel", "1");
	
				
				map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
				map.put("code_bran1",        	request.getParameter("code_bran1"));									//지부
				map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
				map.put("season1",        		request.getParameter("season1"));										//학기
				map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수		
				map.put("edutest_name1",      	request.getParameter("edutest_name1"));									//시험명
				map.put("print_kind",       	request.getParameter("print_kind1"));									//증서발급
				map.put("finish_point1",       	request.getParameter("finish_point1"));									//합격가능점수
				map.put("finish_time1",       	request.getParameter("finish_time1"));									//이수시간
				map.put("finish_date1",       	request.getParameter("finish_date1"));									//이수일수
				map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
				map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
				map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
				map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
				map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
				map.put("result_state1",       	request.getParameter("result_state1"));									//상태
				map.put("result_point1",       	request.getParameter("result_point1"));									//시험점수
				map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
				map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
				map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
				map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
				map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
				map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
				map.put("receipt_no1",     		request.getParameter("receipt_no1"));									//접수번호
				map.put("edutest_name1",     	URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//시험명
				map.put("check",     	 "2");		//시험명
				map.put("sel", "Y");
				if(!request.getParameter("code_operation1").equals("0")) map.put("code_operation1",      request.getParameter("code_operation1"));								
				
				
//			}
	
			System.out.println("결과등록===============================================>>>>>>>>>>");
			
			List<Map> resultregistrationExcel=dao.selectresult(map,true);
			//-----------여기까지 변경---------------------------------------------------------------------------------------
			
			//이 이하는 변경할 필요 없음
			CommonUtil.makeExcelFile(resultregistrationExcel, filename, s_name,header_e,header_k,c_size);
			
			File f=new File(fileDir+filename); //파일생성
			
			String contentType=request.getContentType();
			response.setContentType("x-msdownload");
			
			if(contentType==null){
				if(request.getHeader("user-agent").indexOf("MSIE 5.5")!=-1)
					response.setContentType("doesn/matter;");
				else
					response.setContentType("application/octet-stream");
			}else{
				response.setContentType(contentType);
			}
			
			response.setHeader("Content-Transfer-Encoding:", "binary");
			response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
			response.setHeader("Content-Length", ""+f.length());
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			
			byte b[]=new byte[1024];
			BufferedInputStream fin=new BufferedInputStream(new FileInputStream(f));
			BufferedOutputStream fout=new BufferedOutputStream(response.getOutputStream());
			
			try{
				int read=0;
				while((read=fin.read(b))!=-1){
					fout.write(b, 0, read);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(fout!=null) fout.close();
				if(fin!=null) fin.close();
			}
			
			List<Map> i=dao1.iExcelLog(map1); //엑셀 로그 INSERT
			
			try{
				if(f.exists()) f.delete();
			}catch(Exception e){e.printStackTrace();
			}
			
			
		}	
		
		/*
		 * 작업명 : Home>자격증>결과등록
		 * 작업자 : 윤석희
		 * 작업일 : 2012.11.14
		 * 작   업 : memberpers_no		주민번호 체크
		 */
		public ActionForward pers_check2(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			//전체건수 구해서 넘긴다.
			Map map = new HashMap();
			licenseDao dao=new licenseDao();
			
			//조건 입력
			// 변수에 값 받아서 넣음
//			if(request.getParameter("operation_place1"	)!=null)map.put("operation_place1"	, URLDecoder.decode(request.getParameter("operation_place1"	),"UTF-8"));	//시험장소
//			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
//			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
//			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
//			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
//			if(request.getParameter("season1"			)!=null)map.put("season1"			, request.getParameter("season1"		));	//학기
//			if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
//			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//결제여부
//			if(request.getParameter("result_state1"		)!=null)map.put("result_state1"		, request.getParameter("result_state1"	));	//상태
//			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
//			if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
////			JUMIN_DEL
////			if(request.getParameter("oper_no1"			)!=null)map.put("oper_no1"			, request.getParameter("oper_no1"		));	//주민번호
//			if(request.getParameter("oper_birth1"			)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
//			if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
//			if(request.getParameter("oper_email1"		)!=null)map.put("oper_email1"		, request.getParameter("oper_email1"	));	//이메일
//			if(request.getParameter("person_yn1"		)!=null)map.put("person_yn1"		, request.getParameter("person_yn1"		));	//회원구분
//			if(request.getParameter("attend_cnt1"		)!=null)map.put("attend_cnt1"		, request.getParameter("attend_cnt1"	));	//출석
//			if(request.getParameter("bran_seq1"			)!=null)map.put("bran_seq1"			, request.getParameter("bran_seq1"		));	//지부순번
//			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
//			if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
//			if(request.getParameter("receipt_no1"		)!=null)map.put("receipt_no1"		, request.getParameter("receipt_no1"	));	//접수번호
//			map.put("sel", "1");
			
			map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
			map.put("code_bran1",        	request.getParameter("code_bran1"));									//지부
			map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
			map.put("season1",        		request.getParameter("season1"));										//학기
			map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수		
			map.put("edutest_name1",      	request.getParameter("edutest_name1"));									//시험명
			map.put("print_kind",       	request.getParameter("print_kind1"));									//증서발급
			map.put("finish_point1",       	request.getParameter("finish_point1"));									//합격가능점수
			map.put("finish_time1",       	request.getParameter("finish_time1"));									//이수시간
			map.put("finish_date1",       	request.getParameter("finish_date1"));									//이수일수
			map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
			map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
			map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
			map.put("result_state1",       	request.getParameter("result_state1"));									//상태
			map.put("result_point1",       	request.getParameter("result_point1"));									//시험점수
			map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
			map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
			map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
			map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
			map.put("receipt_no1",     		request.getParameter("receipt_no1"));									//접수번호
			map.put("edutest_name1",     	URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//시험명
			map.put("check",     	 "2");		//시험명
			map.put("sel", "Y");
			if(!request.getParameter("code_operation1").equals("0")) map.put("code_operation1",      request.getParameter("code_operation1"));								
			
			//여기서 구하고 넘긴다.
			List<Map> count = dao.resultsendcnt(map);
			int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
//			if(request.getParameter("edutest_name1"		)!=null)request.setAttribute("edutest_name1"	,  URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8") );	//내용
//			if(request.getParameter("oper_name1"		)!=null)request.setAttribute("oper_name1"		,  URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8") );	//이름
//			if(request.getParameter("operation_place1"	)!=null)request.setAttribute("operation_place1"	,  URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8") );	//시험장소
//			
			String param = "";
//			if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
//			if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
//			if(request.getParameter("season1"			)!=null)param+="&season1="			+ request.getParameter("season1"		);	//학기
//			if(request.getParameter("code_operation1"	)!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");	//대상자
//			if(request.getParameter("oper_state1"		)!=null)param+="&oper_state1="		+ request.getParameter("oper_state1"	);	//결제여부
//			if(request.getParameter("result_state1"		)!=null)param+="&result_state1="	+ request.getParameter("result_state1"	);	//상태
//			if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);		//교육주최
//			if(request.getParameter("oper_lic_no1"		)!=null)param+="&oper_lic_no1="		+ request.getParameter("oper_lic_no1"	);	//면허번호
////			JUMIN_DEL
////			if(request.getParameter("oper_no1"			)!=null)param+="&oper_no1="			+ request.getParameter("oper_no1"		);	//주민번호
//			if(request.getParameter("oper_birth1"			)!=null)param+="&oper_birth1="			+ request.getParameter("oper_birth1"		);	//생년월일
//			if(request.getParameter("oper_hp1"			)!=null)param+="&oper_hp1="			+ request.getParameter("oper_hp1"		);	//핸드폰
//			if(request.getParameter("oper_email1"		)!=null)param+="&oper_email1="		+ request.getParameter("oper_email1"	);	//이메일
//			if(request.getParameter("person_yn1"		)!=null)param+="&person_yn1="		+ request.getParameter("person_yn1"		);		//회원구분
//			if(request.getParameter("attend_cnt1"		)!=null)param+="&attend_cnt1="		+ request.getParameter("attend_cnt1"	);	//출석
//			if(request.getParameter("bran_seq1"			)!=null)param+="&bran_seq1="		+ request.getParameter("bran_seq1"		);	//지부순번
//			if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);		//교육종류
//			if(request.getParameter("code_seq1"			)!=null)param+="&code_seq1="		+ request.getParameter("code_seq1"		);	//순번
//			if(request.getParameter("receipt_no1"		)!=null)param+="&receipt_no1="		+ request.getParameter("receipt_no1"	);	//접수번호
//			if(request.getParameter("edutest_name1"		)!=null)param+="&edutest_name1="	+ request.getParameter("edutest_name1"	);
			
			if(request.getParameter("yyyy1")!=null)param+="&yyyy1="        	+ request.getParameter("yyyy1");											//년도
			if(request.getParameter("code_bran1")!=null)param+="&code_bran1="        + request.getParameter("code_bran1");									//지부
			if(request.getParameter("code_certifi1")!=null)param+="&code_certifi1="        + request.getParameter("code_certifi1");									//구분코드
			if(request.getParameter("season1")!=null)param+="&season1="        	+ request.getParameter("season1");										//학기
			if(request.getParameter("operation_cnt1")!=null)param+="&operation_cnt1="       + request.getParameter("operation_cnt1");								//횟수		
			if(request.getParameter("edutest_name1")!=null)param+="&edutest_name1="      + request.getParameter("edutest_name1");									//시험명
			if(request.getParameter("print_kind")!=null)param+="&print_kind="       + request.getParameter("print_kind1");									//증서발급
			if(request.getParameter("finish_point1")!=null)param+="&finish_point1="       + request.getParameter("finish_point1");									//합격가능점수
			if(request.getParameter("finish_time1")!=null)param+="&finish_time1="       + request.getParameter("finish_time1");									//이수시간
			if(request.getParameter("finish_date1")!=null)param+="&finish_date1="       + request.getParameter("finish_date1");									//이수일수
			if(request.getParameter("operation_place1")!=null)param+="&operation_place1="     + request.getParameter("operation_place1");	//시험장소
			if(request.getParameter("oper_name1")!=null)param+="&oper_name1="       	+ request.getParameter("oper_name1");			//이름
			if(request.getParameter("oper_birth1")!=null)param+="&oper_birth1="       	+ request.getParameter("oper_birth1");										//생년월일
			if(request.getParameter("oper_lic_no1")!=null)param+="&oper_lic_no1="       + request.getParameter("oper_lic_no1");									//면허번호
			if(request.getParameter("attend_cnt1")!=null)param+="&attend_cnt1="       + request.getParameter("attend_cnt1");									//출석일수
			if(request.getParameter("result_state1")!=null)param+="&result_state1="       + request.getParameter("result_state1");									//상태
			if(request.getParameter("result_point1")!=null)param+="&result_point1="       + request.getParameter("result_point1");									//시험점수
			if(request.getParameter("oper_hp1")!=null)param+="&oper_hp1="       	+ request.getParameter("oper_hp1");										//핸드폰
			if(request.getParameter("oper_email1")!=null)param+="&oper_email1="       + request.getParameter("oper_email1");									//이메일
			if(request.getParameter("person_yn1")!=null)param+="&person_yn1="       + request.getParameter("person_yn1");									//회원구분
			if(request.getParameter("code_kind1")!=null)param+="&code_kind1="       + request.getParameter("code_kind1");									//종류
			if(request.getParameter("code_seq1")!=null)param+="&code_seq1="       	+ request.getParameter("code_seq1");										//순번
			if(request.getParameter("bran_seq1")!=null)param+="&bran_seq1="       	+ request.getParameter("bran_seq1");										//지부순번
			if(request.getParameter("receipt_no1")!=null)param+="&receipt_no1="     	+ request.getParameter("receipt_no1");									//접수번호
			if(request.getParameter("edutest_name1")!=null)param+="&edutest_name1="     	+ request.getParameter("edutest_name1");		//시험명
			if(request.getParameter("code_operation1")!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");								
			
			//System.out.println("asdfasdf person_check in = "+param);
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);

			request.setAttribute("url", "license2");
			
		return (mapping.findForward("pers_check2"));
				
		}
		
		
		//결과등록 엑셀 업로드
		public ActionForward upresultSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		return mapping.findForward("upresultSendListr");
		}	
		
		public ActionForward  resultSendListUpExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
			response.setCharacterEncoding("UTF-8");
			
			Map map = new HashMap();
			licenseDao dao = new licenseDao(); 
			
			
			List<Map> code=dao.sCodeExcel1(map);
			
			HttpSession session=request.getSession();
			String g_name = session.getAttribute("G_NAME").toString(); 
			
			String saveFolder="D:\\WEB\\KDA_VER3\\upload\\excel\\";
			String encType="UTF-8";
			int maxSize=10*1024*1024;
			File f=null;
			MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
			String filename="";
			
			Workbook openWorkbook;
			Sheet openSheet;
			String sheetName = "";
			Cell openCell;
			String sResultString = "";
			int ins=0;
			int upd=0;
			try{
				Enumeration files=multi.getFileNames();
				while(files.hasMoreElements()){
					String name=(String)files.nextElement();
					filename=multi.getFilesystemName(name);
					f=new File(saveFolder+"/"+filename);
					
					// 엑셀을 불러 들어 온다.
					openWorkbook = Workbook.getWorkbook(f);
					//엑셀의 첫번째 시트 인덱스(0)을 얻어 온다.
					openSheet = openWorkbook.getSheet(0);
					  
					int iCols = openSheet.getColumns();
					int iRows = openSheet.getRows();
					  
					int cellLen = 0;
					for(int i=1; i < iRows; i++){
						Cell cell[] = openSheet.getRow(i);
					    cellLen = cell.length;
//					    System.out.println("cellLen=======>"+cellLen);
					    map.put("yyyy1",        		cell[3].getContents());						//교육년도
					    map.put("code_bran1",        	cell[1].getContents().substring(6, 8));		//교육주최(key)
					    map.put("code_certifi1",        cell[1].getContents().substring(1, 2));		//구분코드(교육및시험구분)(key)
					    map.put("code_kind1",        	cell[1].getContents().substring(0, 1));		//교육종류(key)
					    map.put("detcode1",        		cell[1].getContents());						//내용

					    map.put("result_point1",        cell[20].getContents());					//점수
					    map.put("attend_cnt1",        	cell[18].getContents());					//출석일수
					    map.put("time_cnt1",        	cell[21].getContents());					//이수시간
					    map.put("receipt_no1",        	cell[2].getContents());						//접수번호
					    map.put("bran_seq1",        	cell[1].getContents().substring(8, 11));	//지부순번(key)
					    map.put("code_seq1",        	cell[1].getContents().substring(2, 6));		//순번(key)
					    map.put("g_name",        		g_name);									//등록자

					    //교육및시험명 테이블에서 관리점수항목 불러오기
					    List<Map> selectexampoint1 = dao.selectexampoint1(map);
					    
					    int v_edu_marks = Integer.parseInt(selectexampoint1.get(0).get("edu_marks").toString());
					    int v_service_marks = Integer.parseInt(selectexampoint1.get(0).get("service_marks").toString());
					    int v_finish_time = Integer.parseInt(selectexampoint1.get(0).get("finish_time").toString());
					    int v_finish_date = Integer.parseInt(selectexampoint1.get(0).get("finish_date").toString());
					    int v_finish_point = Integer.parseInt(selectexampoint1.get(0).get("finish_point").toString());
					    
					    //System.out.println("v_edu_marks=======>"+v_edu_marks);
					    //System.out.println("v_service_marks=======>"+v_service_marks);
//					    System.out.println("v_finish_time=======>"+v_finish_time);
//					    System.out.println("v_finish_date=======>"+v_finish_date);
//					    System.out.println("v_finish_point=======>"+v_finish_point);
//					    System.out.println("finish_time=======>"+StringUtil.nullToStr("0", cell[21].getContents()));
//					    System.out.println("finish_date=======>"+StringUtil.nullToStr("0", cell[18].getContents()));
//					    System.out.println("finish_point=======>"+StringUtil.nullToStr("0", cell[20].getContents()));

					    if ( (v_finish_point <= Integer.parseInt(StringUtil.nullToStr("0", cell[20].getContents())))&&
					    		(v_finish_date <= Integer.parseInt(StringUtil.nullToStr("0", cell[18].getContents())))&&
					    		(v_finish_time <= Integer.parseInt(StringUtil.nullToStr("0", cell[21].getContents())))
					    ){
					    	map.put("edu_marks1",        		v_edu_marks);										//이수점수
					    	
					    	// 발급증서별 발급번호는 1, 2학기 모두 수료시에만 발급하도록 한다. 즉 발급번호 생성 모듈을 따로 가져갈것...
//								map.put("result_no1",        	request.getParameter("code_kind1")+"-"+request.getParameter("yyyy1")+"-"+request.getParameter("code_bran1")+"-");	//발급번호(교육구분-년도-지부코드-
					    	
					    	map.put("result_state1",        "10");					//상태 (합격/이수)
					    	
					    }else{
					    	if ( (v_finish_point > Integer.parseInt(StringUtil.nullToStr("0", cell[20].getContents())))
					    	){
					    		map.put("edu_marks1",        		"0");										//이수점수
					    		map.put("result_state1",        "02");					//상태 (시험성적미달)
					    	}else if ( (v_finish_date > Integer.parseInt(StringUtil.nullToStr("0", cell[18].getContents())))
					    	){
					    		map.put("edu_marks1",        		"0");										//이수점수
					    		map.put("result_state1",        "04");					//상태 (합격/이수)
					    	} else	if ( (v_finish_time > Integer.parseInt(StringUtil.nullToStr("0", cell[21].getContents())))
					    	){
					    		map.put("edu_marks1",        		"0");										//이수점수
					    		map.put("result_state1",        "04");					//상태 (합격/이수)
					    	} else {
					    		map.put("edu_marks1",        		"0");										//이수점수
					    		map.put("result_state1",        "01");					//상태 (합격/이수)
					    		
					    	}
					    }  //end if
					    

					    if("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())){
							try{
								int updateresult = dao.updateresult(map);
								System.out.println("updat=======>"+i+"번째"+updateresult);
								if(updateresult<1)
									sResultString = sResultString + (i+1) +"번라인 데이터가 없습니다.";
							}catch(Exception e){
								sResultString = sResultString + (i+1) +"번라인 수정중 에러";
							}
							upd++;
						}else if("i".equals(cell[0].getContents())||"I".equals(cell[0].getContents())){
							try{
								int deletresult = dao.deletresult(map);							
								List<Map> insertresult = dao.insertresult(map);
								System.out.println("insert=======>"+i+"번째"+insertresult);
							}catch(Exception e){
								sResultString = sResultString + (i+1) +"번라인 입력중 에러"; 
							}
							ins++;
						}
				    
					}  
					if(sResultString.length()<5) sResultString="삽입:"+ins+"건, 수정:"+upd+"건 업로드가 완료됐습니다.";					
					request.setAttribute("sResultString", sResultString);
					openWorkbook.close();
				}   
			}catch(Exception e){   
				e.printStackTrace();
			}
			
			return (mapping.findForward("excelLink9"));
		}  

			
		
	/*자격증심사*/	
	public ActionForward inspectionSand(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

		if(request.getParameter("code_certifi1") != null||request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null){
		
		map.put("code_certifi1",        request.getParameter("code_certifi1"));							//자격증구분
		System.out.println("code_certifi1============================================================>"+request.getParameter("code_certifi1"));
		map.put("yyyy1",        		request.getParameter("yyyy1"));									//년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));							//지부
		
		/*3가지 조건에 맞는 교육명 출력*/
		List<Map> licensesand = dao.licensesand(map);//licensesand커리 타고 실행 넘어온값 licensesand list<map>객체에 집어 넣는다
		JSONArray j_licensesand=JSONArray.fromObject(licensesand);
		request.setAttribute("licensesand" , licensesand);
		request.setAttribute("j_licensesand" , j_licensesand);
		request.setAttribute("licensein", map);
		}
		
		return (mapping.findForward("inspectionSand"));
	}
	
	/*자격증심사  출력*/	
	public ActionForward inspectionSandList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

			if(request.getParameter("code_certifi1") != null||request.getParameter("yyyy1") != null
					||request.getParameter("oper_name1") != null
//					JUMIN_DEL
//					||request.getParameter("oper_no1") != null
					||request.getParameter("oper_birth1") != null
					||request.getParameter("oper_lic_no1") != null
					||request.getParameter("oper_hp1") != null||request.getParameter("oper_email1") != null||request.getParameter("oper_comp_name11") != null
					||request.getParameter("person_yn1") != null
					||request.getParameter("add_file_no1") != null||request.getParameter("code_kind1") != null
					||request.getParameter("code_seq1") != null||request.getParameter("code_bran1") != null||request.getParameter("bran_seq1") != null
					||request.getParameter("receipt_no1") != null){

				 //파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. 
				map.put("code_certifi1",        	request.getParameter("code_certifi1"));									//자격증구분
				map.put("yyyy1",        			request.getParameter("yyyy1"));											//년도
				if(!request.getParameter("code_operation1").equals("0")&&!request.getParameter("code_operation1").equals("2")){
					map.put("code_operation1",        	request.getParameter("code_operation1"));								//대상자
				}else if(request.getParameter("code_operation1").equals("2")){
					map.put("code_operation2",        	"Y");	
				}
				map.put("oper_name1",        		URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
//				JUMIN_DEL
//				map.put("oper_no1",        			request.getParameter("oper_no1"));										//주민번호
				map.put("oper_birth1",        			request.getParameter("oper_birth1"));										//생년월일
				map.put("oper_lic_no1",        		request.getParameter("oper_lic_no1"));									//면허번호
				map.put("oper_hp1",        			request.getParameter("oper_hp1"));										//핸드폰
				map.put("oper_email1",        		request.getParameter("oper_email1"));									//이메일
				map.put("oper_comp_name11",        	request.getParameter("oper_comp_name11"));								//근무처
				map.put("person_yn1",        		request.getParameter("person_yn1"));									//회원구분										
				
				map.put("code_kind1",        		request.getParameter("code_kind1"));									//종류
				map.put("code_seq1",        		request.getParameter("code_seq1"));										//순번
				map.put("code_bran1",        		request.getParameter("code_bran1"));									//지부
				map.put("bran_seq1",        		request.getParameter("bran_seq1"));										//지부순번
				map.put("receipt_no1",        		request.getParameter("receipt_no1"));									//접수번호

				map.put("param1",           request.getParameter("param1"));        //대상자
				map.put("check", "2");
				

		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");

		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		map.put("nstart", nstart);
		
		map.put("nend", nend);
		
		int ncount = 0;
//		System.out.println("테스트================================================================1111111111111");
		List<Map> inspectionsendcnt=dao.inspectionsendcnt(map); 
//		System.out.println("테스트================================================================2222222222222");
		//그리드에 뿌려주는 카운터
		ncount = ((Integer)(inspectionsendcnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> selectlinspection = dao.selectlinspection(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		for(int i=0; i<selectlinspection.size(); i++){
			 //그리드에 출력하는 컬럼 순서
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( selectlinspection.get(i).get("codecertifi")		== null ? "" : selectlinspection.get(i).get("codecertifi"))  	+"\",");   	//자격증구분텍스트값
			result.append("\"" + ( selectlinspection.get(i).get("codeoperation")	== null ? "" : selectlinspection.get(i).get("codeoperation"))   +"\",");   	//대상자 코드값
			result.append("\"" + ( selectlinspection.get(i).get("oper_name")		== null ? "" : selectlinspection.get(i).get("oper_name"))  		+"\",");   	//이름
//			JUMIN_DEL
//			result.append("\"" + ( selectlinspection.get(i).get("oper_no")   		== null ? "" : selectlinspection.get(i).get("oper_no") )   		+"\",");	//주민번호
			result.append("\"" + ( selectlinspection.get(i).get("oper_birth")   		== null ? "" : selectlinspection.get(i).get("oper_birth") )   		+"\",");	//생년월일
			result.append("\"" + ( selectlinspection.get(i).get("oper_lic_no")	    == null ? "" : selectlinspection.get(i).get("oper_lic_no") )    +"\",");	//면허번호
			result.append("\"" + ( selectlinspection.get(i).get("oper_hp")			== null ? "" : selectlinspection.get(i).get("oper_hp") )  		+"\",");	//핸드폰
			result.append("\"" + ( selectlinspection.get(i).get("operemail")		== null ? "" : selectlinspection.get(i).get("operemail") )     +"\",");	//이메일
			result.append("\"" + ( selectlinspection.get(i).get("oper_comp_name1")	== null ? "" : selectlinspection.get(i).get("oper_comp_name1")) +"\",");	//근무처
			result.append("\"" + ( selectlinspection.get(i).get("person_yn")		== null ? "" : selectlinspection.get(i).get("person_yn")) 		+"\",");	//회원구분
			
			if("1".equals(selectlinspection.get(i).get("season")) || "2".equals(selectlinspection.get(i).get("season")) ){
				result.append("\"" + ( selectlinspection.get(i).get("seasonA")			== null ? "" : selectlinspection.get(i).get("seasonA")) 		+"\",");	//1학기 점수
				result.append("\"" + ( selectlinspection.get(i).get("seasonB")			== null ? "" : selectlinspection.get(i).get("seasonB")) 		+"\",");	//2학기 점수
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
			} else if("3".equals(selectlinspection.get(i).get("season")) || "4".equals(selectlinspection.get(i).get("season")) ){
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
				result.append("\"" + ( selectlinspection.get(i).get("seasonA")			== null ? "" : selectlinspection.get(i).get("seasonA")) 		+"\",");	//1학기 점수
				result.append("\"" + ( selectlinspection.get(i).get("seasonB")			== null ? "" : selectlinspection.get(i).get("seasonB")) 		+"\",");	//2학기 점수
			} else {
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
				result.append("\"" + ( "") 		+"\",");	//1학기 점수
			}
			
			//result.append("\"" + ( selectlinspection.get(i).get("result_point")	   	== null ? "" : selectlinspection.get(i).get("result_point") )    +"\",");	//이수점수
			result.append("\"" + ( selectlinspection.get(i).get("code_operation")	== null ? "" : selectlinspection.get(i).get("code_operation"))  +"\",");   	//대상자코드값
			
			result.append("\"" + ( selectlinspection.get(i).get("detcodename")		== null ? "" : selectlinspection.get(i).get("detcodename") )    +"\",");	//detcodename
				  
			//result.append("\"" + ( selectlinspection.get(i).get("yyyy")			    == null ? "" : selectlinspection.get(i).get("yyyy") )    		+"\",");	//년도
			result.append("\"" + ( selectlinspection.get(i).get("code_kind")	    == null ? "" : selectlinspection.get(i).get("code_kind") )    	+"\",");	//종류
			result.append("\"" + ( selectlinspection.get(i).get("code_seq")			== null ? "" : selectlinspection.get(i).get("code_seq") )  		+"\",");	//순번
			result.append("\"" + ( selectlinspection.get(i).get("code_bran")	    == null ? "" : selectlinspection.get(i).get("code_bran") )		+"\",");	//지부
			result.append("\"" + ( selectlinspection.get(i).get("bran_seq")	    	== null ? "" : selectlinspection.get(i).get("bran_seq") )   	+"\",");	//지부순번
			result.append("\"" + ( selectlinspection.get(i).get("receipt_no")	    == null ? "" : selectlinspection.get(i).get("receipt_no") )   	+"\",");	//접수번호
//			JUMIN_DEL
//			result.append("\"" + ( selectlinspection.get(i).get("oper_no1")	    	== null ? "" : selectlinspection.get(i).get("oper_no1") )     	+"\",");	//히든 주민번호(xml에서 추가한 명칭값과동일하게 )
			//result.append("\"" + ( selectlinspection.get(i).get("edutest_name")		== null ? "" : selectlinspection.get(i).get("edutest_name") ) 	+"\",");		//내용값
			result.append("\"" + ( selectlinspection.get(i).get("detcode")			== null ? "" : selectlinspection.get(i).get("detcode") ) 		+"\",");		//내용값
			result.append("\"" + ( selectlinspection.get(i).get("code_certifi")		== null ? "" : selectlinspection.get(i).get("code_certifi"))  	+"\",");   	//자격증구분코드값
			result.append("\"" + ( selectlinspection.get(i).get("person_yn1")	    == null ? "" : selectlinspection.get(i).get("person_yn1") )     +"\",");		//히든 회원코드값 )
			
			result.append("\"" + ( selectlinspection.get(i).get("code_bran_other")	    == null ? "" : selectlinspection.get(i).get("code_bran_other") )		+"\",");	//지부 OTHER
			result.append("\"" + ( selectlinspection.get(i).get("bran_seq_other")	    	== null ? "" : selectlinspection.get(i).get("bran_seq_other") )   	+"\",");	//지부순번 OTHER
			result.append("\"" + ( selectlinspection.get(i).get("receipt_no_other")	    == null ? "" : selectlinspection.get(i).get("receipt_no_other") )   	+"\",");	//접수번호 OTHER
			result.append("\"" + ( selectlinspection.get(i).get("code_operation_other")	    == null ? "" : selectlinspection.get(i).get("code_operation_other") )   	+"\",");	//대상자코드값 OTHER

			result.append("\"" + ( selectlinspection.get(i).get("season")	    == null ? "" : selectlinspection.get(i).get("season") )     +"\"");		//히든 회원코드값 )
			
			result.append("]}");
			
			System.out.println(result);
		}	

		result.append("]}");
		request.setAttribute("result",result);
	}
		//	return null;
		return (mapping.findForward("ajaxout"));
}
	
	//자격증 심사 저장~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~10월25일 작업중 funtion 부분메치시킬것
		public ActionForward inspectionSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				HttpSession session=request.getSession();
				String g_name=session.getAttribute("G_NAME").toString();
				Map map = new HashMap();
				String errMsg="";
				String chk=request.getParameter("chk");
												
				map.put("code_kind1",        	request.getParameter("code_kind1"));							//종류
				map.put("code_certifi1",        request.getParameter("code_certifi1"));							//자격구분
				map.put("code_seq1",        	request.getParameter("code_seq1"));								//순번
				map.put("code_bran1",        	request.getParameter("code_bran1"));							//지부
				map.put("bran_seq1",        	request.getParameter("bran_seq1"));								//지부순번
				map.put("receipt_no1",        	request.getParameter("receipt_no1"));							//접수번호
 			
				//jsp 넘긴 항목이랑 같은 갯수로 값을 받아야 한다.
				licenseDao dao = new licenseDao(); 
				//int inspectionD = dao.deletinspection(map);
				
				String param=request.getParameter("param");
				String[] files=param.split(" ");
				int fileNo=files.length;
				for(int i=0;i<fileNo;i++){								
					map.put("add_file_no", files[i]);
					map.put("chang_file_name", files[i]);					
					List<Map> sFiles=dao.selectCFiles(map);					
					if(sFiles.size()==0){
						List<Map> insertinspection1 = dao.insertinspection(map);
					}
				}

				if(chk.equals("Y")){
					//code_operation1값에 따라 요청한 파일을 전부 첨부했다면 chk=Y 아니면 N
					//oper_result의 result_state를 12로 업데이트한다.
					map.put("result_state", "12");
					map.put("g_name", g_name);
					map.put("insp_date", "Y");
					int uRstate=dao.uRstate(map);
				}
				errMsg="저장됐습니다.";
				map.put("yyyy1",        	request.getParameter("yyyy1"));
				request.setAttribute("etest2", map);
				request.setAttribute("errMsg", errMsg);
				return mapping.findForward("inspectionSave");//저장후 저장한값을 화면에 다시 표출해야한다
			}			
				
				
			
		
		
		public ActionForward inspectionSaveBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			HttpSession session=request.getSession();
			String g_name=session.getAttribute("G_NAME").toString();
			String errMsg="";
			
 			String yyyy = StringUtil.NVL(request.getParameter("yyyy"));
			
			if(!"".equals(StringUtil.NVL(request.getParameter("oper_infos")))){
				List<String> oper_infos_org =  Arrays.asList(request.getParameter("oper_infos"	).split("__"));
				List<List<String>> oper_infos = new ArrayList<List<String>>();
				
				for(String item : oper_infos_org){
					List<String> oper_info =  Arrays.asList(item.split("_"));
					oper_infos.add(oper_info);
				}
				
				
				licenseDao dao = new licenseDao();
				for(List<String> oper_info : oper_infos){
					Map map = new HashMap();
					map.put("code_kind1",        	StringUtil.NVL(oper_info.get(0)));
					map.put("code_certifi1",        StringUtil.NVL(oper_info.get(1)));
					map.put("code_seq1",        	StringUtil.NVL(oper_info.get(2)));
					map.put("code_bran1",        	StringUtil.NVL(oper_info.get(3)));
					map.put("bran_seq1",        	StringUtil.NVL(oper_info.get(4)));
					map.put("receipt_no1",        	StringUtil.NVL(oper_info.get(5)));
					
					String param= getFileParams(StringUtil.NVL(oper_info.get(6)));
					String[] files=param.split(" ");
					int fileNo=files.length;
					for(int i=0;i<fileNo;i++){								
						map.put("add_file_no", files[i]);
						map.put("chang_file_name", files[i]);					
						List<Map> sFiles=dao.selectCFiles(map);					
						if(sFiles.size()==0){
							List<Map> insertinspection1 = dao.insertinspection(map);
						}
					}
					map.put("result_state", "12");
					map.put("g_name", g_name);
					map.put("insp_date", "Y");
					int uRstate=dao.uRstate(map);
				}
			} else {
				Map map = new HashMap(); 
				
				map.put("code_certifi1",        	request.getParameter("code_certifi1"));									//자격증구분
				map.put("yyyy1",        			request.getParameter("yyyy1"));											//년도
				if(!request.getParameter("code_operation1").equals("0")&&!request.getParameter("code_operation1").equals("2")){
					map.put("code_operation1",        	request.getParameter("code_operation1"));								//대상자
				}else if(request.getParameter("code_operation1").equals("2")){
					map.put("code_operation2",        	"Y");	
				}
				map.put("oper_name1",        		URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
				map.put("oper_birth1",        			request.getParameter("oper_birth1"));										//생년월일
				map.put("oper_lic_no1",        		request.getParameter("oper_lic_no1"));									//면허번호
				map.put("oper_hp1",        			request.getParameter("oper_hp1"));										//핸드폰
				map.put("oper_email1",        		request.getParameter("oper_email1"));									//이메일
				map.put("oper_comp_name11",        	request.getParameter("oper_comp_name11"));								//근무처
				map.put("person_yn1",        		request.getParameter("person_yn1"));									//회원구분										
				
				map.put("code_kind1",        		request.getParameter("code_kind1"));									//종류
				map.put("code_seq1",        		request.getParameter("code_seq1"));										//순번
				map.put("code_bran1",        		request.getParameter("code_bran1"));									//지부
				map.put("bran_seq1",        		request.getParameter("bran_seq1"));										//지부순번
				map.put("receipt_no1",        		request.getParameter("receipt_no1"));									//접수번호
				
				map.put("param1",           request.getParameter("param1"));        //대상자
				map.put("check", "2");
				
				map.put("nstart", 0);
				map.put("nend", 1000000);
				
				licenseDao dao = new licenseDao();
				List<Map> result = dao.selectlinspection(map);
				
				for(int i=0; i<result.size(); i++){
					String code_kind = (String) result.get(i).get("code_kind"); 
					String code_certifi = (String) result.get(i).get("code_certifi"); 
					String code_seq = (String) result.get(i).get("code_seq");
					String code_bran = (String) result.get(i).get("code_bran");
					String bran_seq = (String) result.get(i).get("bran_seq");
					String receipt_no = (String) result.get(i).get("receipt_no"); 
					String code_operation = (String) result.get(i).get("code_operation"); 
					
					Map map1 = new HashMap();
					map1.put("code_kind1",        	StringUtil.NVL(code_kind));
					map1.put("code_certifi1",        StringUtil.NVL(code_certifi));
					map1.put("code_seq1",        	StringUtil.NVL(code_seq));
					map1.put("code_bran1",        	StringUtil.NVL(code_bran));
					map1.put("bran_seq1",        	StringUtil.NVL(bran_seq));
					map1.put("receipt_no1",        	StringUtil.NVL(receipt_no));
					
					String param= getFileParams(StringUtil.NVL(code_operation));
					String[] files=param.split(" ");
					int fileNo=files.length;
					for(int j=0;j<fileNo;j++){								
						map1.put("add_file_no", files[j]);
						map1.put("chang_file_name", files[j]);					
						List<Map> sFiles=dao.selectCFiles(map1);					
						if(sFiles.size()==0){
							List<Map> insertinspection1 = dao.insertinspection(map1);
						}
					}
					map1.put("result_state", "12");
					map1.put("g_name", g_name);
					map1.put("insp_date", "Y");
					int uRstate=dao.uRstate(map1);
				}
			}
			
			errMsg="일괄처리 되었습니다.";
			Map map2 = new HashMap();
			map2.put("yyyy1",        	request.getParameter("yyyy"));
			map2.put("code_bran1",        	request.getParameter("code_bran"));
			map2.put("code_certifi1",        	request.getParameter("code_certifi"));
			
			request.setAttribute("etest2", map2);
			request.setAttribute("errMsg", errMsg);
			return mapping.findForward("inspectionSave");//저장후 저장한값을 화면에 다시 표출해야한다
		}

		//자격증심사 엑셀 저장 =================================11-13작업중
		public void inspectionListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			Map map = new HashMap();
			Map map1=new HashMap();
			licenseDao dao = new licenseDao(); 
			memberStateDao dao1=new memberStateDao(); 
			
			System.out.println("자격증심사==========================================================>");
			String format="yyyyMMdd";
			SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
			String date=sdf.format(new java.util.Date()); //현재 날짜
			
			HttpSession session=request.getSession();
			String user=session.getAttribute("G_ID").toString(); //유저ID
			//여기부터 개발자 변경 필요
			String name="licenceInspectionList"; //프로그램명==>자격증심사 엑셀저장
			String s_name="자격증심사"; //엑셀 시트명
	//		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
			String header_e[]={"detcode","codecertifi", "codeoperation"
								,"oper_name"
//								JUMIN_DEL
//								,"oper_no"
								,"oper_birth"
								,"oper_lic_no","detcodename","oper_hp","operemail","oper_comp_name1","person_yn","seasonA","seasonB"}; //헤더 영문
			String header_k[]={"Key","자격증구분","대상자"
								,"이름"
//								JUMIN_DEL
//								,"주민번호"
								,"생년월일"
								,"면허번호","자격증발급서류","핸드폰","이메일","근무처","회원구분","1학기 점수","2학기 점수"}; //헤더 국문
			int c_size[]={14,10,7,9,13,9,15,13,27,35,22,12,12};  //열 넓이를 위한 배열
			
			//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
			map1.put("prog_name", name);
			map1.put("create_user", user);
			map1.put("sheet_name", s_name);
			

			if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_certifi1") != null){
				
				map.put("nstart", request.getParameter("nstart"));
				map.put("nend"	, request.getParameter("nend"));
			
//				if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1",        request.getParameter("code_certifi1"));			//자격증 구분
//				if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1",        		request.getParameter("yyyy1"));					//교육년도
//				if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1",        	request.getParameter("code_bran1"));			//교육주최
//				//if(request.getParameter("season1"			)!=null)map.put("season1",        		request.getParameter("season1"));				//학기
//				//if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1",        request.getParameter("edutest_name1"));		//내용
//				if(request.getParameter("code_operation1"	)!=null&&!request.getParameter("code_operation1"	).equals("0"))map.put("code_operation1",      request.getParameter("code_operation1"));			//응시상태
//				if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1",        request.getParameter("oper_name1"));		//내용
////				JUMIN_DEL
////				if(request.getParameter("oper_no1"	)!=null)map.put("oper_no1",      request.getParameter("oper_no1"));			//응시상태
//				if(request.getParameter("oper_birth1"	)!=null)map.put("oper_birth1",      request.getParameter("oper_birth1"));			//생년월일
//				
//				map.put("check", request.getParameter("check"));
				
				
				map.put("code_certifi1",        	request.getParameter("code_certifi1"));									//자격증구분
				map.put("yyyy1",        			request.getParameter("yyyy1"));											//년도
				if(!request.getParameter("code_operation1").equals("0")&&!request.getParameter("code_operation1").equals("2")){
					map.put("code_operation1",        	request.getParameter("code_operation1"));								//대상자
				}else if(request.getParameter("code_operation1").equals("2")){
					map.put("code_operation2",        	"Y");	
				}
				map.put("oper_name1",        		URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
				map.put("oper_birth1",        			request.getParameter("oper_birth1"));										//생년월일
				map.put("oper_lic_no1",        		request.getParameter("oper_lic_no1"));									//면허번호
				map.put("oper_hp1",        			request.getParameter("oper_hp1"));										//핸드폰
				map.put("oper_email1",        		request.getParameter("oper_email1"));									//이메일
				map.put("oper_comp_name11",        	request.getParameter("oper_comp_name11"));								//근무처
				map.put("person_yn1",        		request.getParameter("person_yn1"));									//회원구분										
				
				map.put("code_kind1",        		request.getParameter("code_kind1"));									//종류
				map.put("code_seq1",        		request.getParameter("code_seq1"));										//순번
				map.put("code_bran1",        		request.getParameter("code_bran1"));									//지부
				map.put("bran_seq1",        		request.getParameter("bran_seq1"));										//지부순번
				map.put("receipt_no1",        		request.getParameter("receipt_no1"));									//접수번호

				map.put("param1",           request.getParameter("param1"));        //대상자
				map.put("check", "2");
			}
			int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
			String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
			
			List<Map> inspectionExcel=dao.selectlinspection(map,true);
			//-----------여기까지 변경---------------------------------------------------------------------------------------
			
			//이 이하는 변경할 필요 없음
			CommonUtil.makeExcelFile(inspectionExcel, filename, s_name,header_e,header_k,c_size);
			
			File f=new File(fileDir+filename); //파일생성
			
			String contentType=request.getContentType();
			response.setContentType("x-msdownload");
			
			if(contentType==null){
				if(request.getHeader("user-agent").indexOf("MSIE 5.5")!=-1)
					response.setContentType("doesn/matter;");
				else
					response.setContentType("application/octet-stream");
			}else{
				response.setContentType(contentType);
			}
			
			response.setHeader("Content-Transfer-Encoding:", "binary");
			response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
			response.setHeader("Content-Length", ""+f.length());
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			
			byte b[]=new byte[1024];
			BufferedInputStream fin=new BufferedInputStream(new FileInputStream(f));
			BufferedOutputStream fout=new BufferedOutputStream(response.getOutputStream());
			
			try{
				int read=0;
				while((read=fin.read(b))!=-1){
					fout.write(b, 0, read);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(fout!=null) fout.close();
				if(fin!=null) fin.close();
			}
			
			List<Map> i=dao1.iExcelLog(map1); //엑셀 로그 INSERT
			
			try{
				if(f.exists()) f.delete();
			}catch(Exception e){e.printStackTrace();}
			
		}			
		
		
		/*
		 * 작업명 : Home>자격증>자격증심사
		 * 작업자 : 윤석희
		 * 작업일 : 2012.11.14
		 * 작   업 : memberpers_no		주민번호 체크
		 */
		public ActionForward pers_check3(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			//전체건수 구해서 넘긴다.
			Map map = new HashMap();
			licenseDao dao=new licenseDao();
			
//			//조건 입력
//			// 변수에 값 받아서 넣음
//			//if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, request.getParameter("edutest_name1"	));	//내용
//			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
//			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
//			/*if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
//				map.put("season1",       		"1");									//반기									//반기
//			} else {									//반기
//				map.put("season2",       		"2");									//반기
//			}*/
//			if(request.getParameter("code_operation1"	)!=null&&!request.getParameter("code_operation1"	).equals("0"))map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
//			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
//			
//			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, request.getParameter("oper_name1"		));	//교육주최
////			JUMIN_DEL
////			if(request.getParameter("oper_no1"		)!=null)map.put("oper_no1"		, request.getParameter("oper_no1"		));	//교육주최
//			if(request.getParameter("oper_birth1"		)!=null)map.put("oper_birth1"		, request.getParameter("oper_birth1"		));	//생년월일
//			map.put("check","2");
			
			
			map.put("code_certifi1",        	request.getParameter("code_certifi1"));									//자격증구분
			map.put("yyyy1",        			request.getParameter("yyyy1"));											//년도
			if(!request.getParameter("code_operation1").equals("0")&&!request.getParameter("code_operation1").equals("2")){
				map.put("code_operation1",        	request.getParameter("code_operation1"));								//대상자
			}else if(request.getParameter("code_operation1").equals("2")){
				map.put("code_operation2",        	"Y");	
			}
			map.put("oper_name1",        		URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_birth1",        			request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",        		request.getParameter("oper_lic_no1"));									//면허번호
			map.put("oper_hp1",        			request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",        		request.getParameter("oper_email1"));									//이메일
			map.put("oper_comp_name11",        	request.getParameter("oper_comp_name11"));								//근무처
			map.put("person_yn1",        		request.getParameter("person_yn1"));									//회원구분										
			
			map.put("code_kind1",        		request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",        		request.getParameter("code_seq1"));										//순번
			map.put("code_bran1",        		request.getParameter("code_bran1"));									//지부
			map.put("bran_seq1",        		request.getParameter("bran_seq1"));										//지부순번
			map.put("receipt_no1",        		request.getParameter("receipt_no1"));									//접수번호

			map.put("param1",           request.getParameter("param1"));        //대상자
			map.put("check", "2");
			
			
			
			//여기서 구하고 넘긴다.
			List<Map> count = dao.inspectionsendcnt(map);
			int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			
			String param = "";
//			//if(request.getParameter("edutest_name1"		)!=null)param+="edutest_name1="		+ request.getParameter("edutest_name1"	);	//내용
//			if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
//			if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
//			/*if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
//				param += "&season1=1";									//반기									//반기
//			} else {									//반기
//				param += "&season2=2";										//반기
//			}*/
//			if(request.getParameter("code_operation1"	)!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");	//대상자
//			if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);		//교육주최
//			if(request.getParameter("oper_name1"	)!=null)param+="&oper_name1="	+ request.getParameter("oper_name1");	//대상자
////			JUMIN_DEL
////			if(request.getParameter("oper_no1"		)!=null)param+="&oper_no1="		+ request.getParameter("oper_no1"		);		//교육주최
//			if(request.getParameter("oper_birth1"		)!=null)param+="&oper_birth1="		+ request.getParameter("oper_birth1"		);		//생년월일
			
			if(request.getParameter("code_certifi1")!=null)param+="&code_certifi1="        	+ request.getParameter("code_certifi1");									//자격증구분
			if(request.getParameter("yyyy1")!=null)param+="&yyyy1="        	+ request.getParameter("yyyy1");											//년도
			if(request.getParameter("code_operation1")!=null)param+="&code_operation1="        	+ request.getParameter("code_operation1");								//대상자
			if(request.getParameter("oper_name1")!=null)param+="&oper_name1="        	+ request.getParameter("oper_name1");			//이름
			if(request.getParameter("oper_birth1")!=null)param+="&oper_birth1="        	+ request.getParameter("oper_birth1");										//생년월일
			if(request.getParameter("oper_lic_no1")!=null)param+="&oper_lic_no1="        	+ request.getParameter("oper_lic_no1");									//면허번호
			if(request.getParameter("oper_hp1")!=null)param+="&oper_hp1="        	+ request.getParameter("oper_hp1");										//핸드폰
			if(request.getParameter("oper_email1")!=null)param+="&oper_email1="        	+ request.getParameter("oper_email1");									//이메일
			if(request.getParameter("oper_comp_name11")!=null)param+="&oper_comp_name11="        	+ request.getParameter("oper_comp_name11");								//근무처
			if(request.getParameter("person_yn1")!=null)param+="&person_yn1="        	+ request.getParameter("person_yn1");									//회원구분										
			if(request.getParameter("code_kind1")!=null)param+="&code_kind1="        	+ request.getParameter("code_kind1");									//종류
			if(request.getParameter("code_seq1")!=null)param+="&code_seq1="        	+ request.getParameter("code_seq1");										//순번
			if(request.getParameter("code_bran1")!=null)param+="&code_bran1="        	+ request.getParameter("code_bran1");									//지부
			if(request.getParameter("bran_seq1")!=null)param+="&bran_seq1="        	+ request.getParameter("bran_seq1");										//지부순번
			if(request.getParameter("receipt_no1")!=null)param+="&receipt_no1="        	+ request.getParameter("receipt_no1");									//접수번호
			if(request.getParameter("param1")!=null)param+="&param1="        	+ request.getParameter("param1");        //대상자
			
			
			
	//System.out.println("asdfasdf person_check in = "+param);
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);

			request.setAttribute("url", "license3");
			
		return (mapping.findForward("pers_check3"));
				
		}
		
	
	
	/*자격증발급*/	
	public ActionForward issueSand(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		return (mapping.findForward("issueSandList"));
	}
	

	/*
	 * 작업명 : Home>자격증>자격증발급
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.14
	 * 작   업 : memberpers_no		주민번호 체크
	 */
	public ActionForward pers_check4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//전체건수 구해서 넘긴다.
		Map map = new HashMap();
		licenseDao dao=new licenseDao();
		
		
		if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"		));	//자격증 구분
		if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"				));	//
		if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
			map.put("season1",       		"1");									//반기									//반기
		}else if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("2")){
			map.put("season2",       		"2");									//반기
		} else {									//반기
		}
		if(request.getParameter("code_operation1"	)!=null&&!request.getParameter("code_operation1"	).equals("0"))map.put("code_operation1"	, request.getParameter("code_operation1"	));	//
		if(request.getParameter("result_start_dt1"	)!=null)map.put("result_start_dt1"	, request.getParameter("result_start_dt1"	));	//
		if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"		));	//
		if(request.getParameter("button_ab"			)!=null)map.put("button_ab"			, request.getParameter("button_ab"));//대상검색과 발급현황조회구분값
		

		String chk = request.getParameter("button_ab");
		
		List<Map> issuesendcnt = null;
		List<Map> nowsendcnt = null;
		int ncount = 0;
		//대상검색
		if( "a".equals(chk)) {
			issuesendcnt=dao.issuesendcnt(map);
			ncount = ((Integer)(issuesendcnt.get(0).get("cnt"))).intValue();
		}
		//발급현황조회
		if( "b".equals(chk)) {
			nowsendcnt=dao.nowsendcnt(map); 
			ncount = ((Integer)(nowsendcnt.get(0).get("cnt"))).intValue();
		}
		
		String param = "";
		if(request.getParameter("code_certifi1"		)!=null) param += "&code_certifi1="		+ request.getParameter("code_certifi1"		);	//자격증 구분
		if(request.getParameter("yyyy1"				)!=null) param += "&yyyy1="				+ request.getParameter("yyyy1"				);	//
		if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
			param += "&season1=1";									//반기									//반기
		} else if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("2")){
			param += "&season2=2";									//반기									//반기
		} else {									//반기
		}

		if(request.getParameter("code_operation1"	)!=null) param += "&code_operation1="	+ request.getParameter("code_operation1"	);	//
		if(request.getParameter("result_start_dt1"	)!=null) param += "&result_start_dt1="	+ request.getParameter("result_start_dt1"	);	//
		if(request.getParameter("result_end_dt1"	)!=null) param += "&result_end_dt1="	+ request.getParameter("result_end_dt1"		);	//
		if(request.getParameter("button_ab"			)!=null) param += "&button_ab="			+ request.getParameter("button_ab");//대상검색과 발급현황조회구분값
		
		
		//검색 변수 넘김
		request.setAttribute("param", param);
		//전체 개수 넘김
		request.setAttribute("ncount", ncount);

		request.setAttribute("url", "license4");
		
	return (mapping.findForward("pers_check"));
			
	}
	
	/*자격증발급 대상검색  출력*/	
	public ActionForward issueSandList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		System.out.println("자격증대상검색=====================================================>자격증발급대상검색 출력리스트엑션");
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

			if(request.getParameter("code_certifi1") != null||request.getParameter("result_no1") != null||request.getParameter("result_start_dt1") != null
					||request.getParameter("result_end_dt1") != null||request.getParameter("oper_name1") != null
//					JUMIN_DEL
//					||request.getParameter("oper_no1") != null
					||request.getParameter("oper_birth1") != null
					||request.getParameter("oper_lic_no1") != null||request.getParameter("code_operation1") != null||request.getParameter("person_yn1") != null
					||request.getParameter("result_dt1") != null||request.getParameter("oper_hp1") != null||request.getParameter("oper_email1") != null
					||request.getParameter("season1") != null
					||request.getParameter("code_seq1") != null||request.getParameter("yyyy1") != null||request.getParameter("code_pers1") != null){
				/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */

				map.put("code_certifi1",        request.getParameter("code_certifi1"));								//자격증명
				map.put("yyyy1",       			request.getParameter("yyyy1"));										//년도
				map.put("season1",       		"");									//반기
				map.put("season2",       		"");									//반기
				if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
					map.put("season1",       		"1");									//반기
				} else if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("2")){
					map.put("season2",       		"2");									//반기
				}
				
				if(!request.getParameter("code_operation1").equals("0")) map.put("code_operation1",      request.getParameter("code_operation1"));							//취득구분
				map.put("result_start_dt1",     request.getParameter("result_start_dt1"));							//유효시작일
				map.put("result_end_dt1",       request.getParameter("result_end_dt1"));	
				map.put("check", "2");

		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");

		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		map.put("nstart", nstart);
		
		map.put("nend", nend);
		
		int ncount = 0;
		List<Map> issuesendcnt=dao.issuesendcnt(map); 
		//그리드에 뿌려주는 카운터
		ncount = ((Integer)(issuesendcnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> selectissue = dao.selectissue(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		for(int i=0; i<selectissue.size(); i++){
			/* 그리드에 출력하는 컬럼 순서*/
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( selectissue.get(i).get("codecertifi")		== null ? "" : selectissue.get(i).get("codecertifi"))  	+"\",");   	//자격증명
			result.append("\"" + ( selectissue.get(i).get("result_no")			== null ? "" : selectissue.get(i).get("result_no"))  		+"\",");   	//자격증번호
			result.append("\"" + ( selectissue.get(i).get("result_start_dt")	== null ? "" : selectissue.get(i).get("result_start_dt"))  	+"\",");   	//유효시작일
			result.append("\"" + ( selectissue.get(i).get("result_end_dt")   	== null ? "" : selectissue.get(i).get("result_end_dt") )   	+"\",");	//유효종료일
			result.append("\"" + ( selectissue.get(i).get("print_state")   	== null ? "" : selectissue.get(i).get("print_state") )   	+"\",");	//유효종료일
			result.append("\"" + ( selectissue.get(i).get("oper_name")			== null ? "" : selectissue.get(i).get("oper_name")) 	  	+"\",");	//이름
//			JUMIN_DEL
//			result.append("\"" + ( selectissue.get(i).get("oper_no")	    	== null ? "" : selectissue.get(i).get("oper_no") )     		+"\",");	//주민번호
			result.append("\"" + ( selectissue.get(i).get("oper_birth")	    	== null ? "" : selectissue.get(i).get("oper_birth") )     		+"\",");	//생년월일
			result.append("\"" + ( selectissue.get(i).get("oper_lic_no")		== null ? "" : selectissue.get(i).get("oper_lic_no") )  	+"\",");	//면허번호
			result.append("\"" + ( selectissue.get(i).get("codeoperation")		== null ? "" : selectissue.get(i).get("codeoperation") )   +"\",");	//취득구분
			result.append("\"" + ( selectissue.get(i).get("person_yn")			== null ? "" : selectissue.get(i).get("person_yn")) 		+"\",");	//회원구분
			result.append("\"" + ( selectissue.get(i).get("result_dt")	   		== null ? "" : selectissue.get(i).get("result_dt") )     	+"\",");	//발급일
			result.append("\"" + ( selectissue.get(i).get("oper_hp")	   		== null ? "" : selectissue.get(i).get("oper_hp") )     		+"\",");	//핸드폰
			result.append("\"" + ( selectissue.get(i).get("operemail")	   		== null ? "" : selectissue.get(i).get("operemail") )    	+"\",");	//이메일
			result.append("\"" + ( selectissue.get(i).get("code_seq")			== null ? "" : selectissue.get(i).get("code_seq") )  		+"\","); 	//순번
			result.append("\"" + ( selectissue.get(i).get("yyyy")				== null ? "" : selectissue.get(i).get("yyyy") )  			+"\","); 	//년도
			result.append("\"" + ( selectissue.get(i).get("season")				== null ? "" : selectissue.get(i).get("season") )  			+"\","); 	//발급반기
			result.append("\"" + ( selectissue.get(i).get("code_pers")			== null ? "" : selectissue.get(i).get("code_pers") )  		+"\","); 	//회원코드
			result.append("\"" + ( selectissue.get(i).get("code_certifi")		== null ? "" : selectissue.get(i).get("code_certifi"))  	+"\",");   	//자격증명
			result.append("\"" + ( selectissue.get(i).get("code_operation")		== null ? "" : selectissue.get(i).get("code_operation") )   +"\",");	//취득구분코드값
			result.append("\"" + ( selectissue.get(i).get("person_yn1")	    	== null ? "" : selectissue.get(i).get("person_yn1") )     +"\"");		//히든 회원코드값 )
			result.append("]}");
			
		}	

		result.append("]}");
		request.setAttribute("result",result);
	}

		return (mapping.findForward("ajaxout"));
}
	
	/*자격증발급현황조회  출력*/	
	public ActionForward nowSandList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		System.out.println("자격증발급현황조회=====================================================>");
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

			if(request.getParameter("code_certifi1") != null||request.getParameter("result_no1") != null||request.getParameter("result_start_dt1") != null
					||request.getParameter("result_end_dt1") != null||request.getParameter("oper_name1") != null
//					JUMIN_DEL
//					||request.getParameter("oper_no1") != null
					||request.getParameter("oper_birth1") != null
					||request.getParameter("oper_lic_no1") != null||request.getParameter("code_operation1") != null||request.getParameter("person_yn1") != null
					||request.getParameter("result_dt1") != null||request.getParameter("oper_hp1") != null||request.getParameter("oper_email1") != null
					||request.getParameter("code_seq1") != null||request.getParameter("yyyy1") != null||request.getParameter("code_pers1") != null){
				/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
		map.put("code_certifi1",        request.getParameter("code_certifi1"));								//자격증명
		map.put("yyyy1",       			request.getParameter("yyyy1"));										//년도									//반기
		if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
			map.put("season1",       		"1");									//반기									//반기
		} else if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("2")){									//반기
			map.put("season2",       		"2");									//반기
		}
		if(!request.getParameter("code_operation1").equals("0")) map.put("code_operation1",      request.getParameter("code_operation1"));							//취득구분
		map.put("check", "2");
		/*map.put("result_start_dt1",     request.getParameter("result_start_dt1"));							//유효시작일
		map.put("result_end_dt1",       request.getParameter("result_end_dt1"));							//유효종료일
*/

		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");

		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		map.put("nstart", nstart);
		
		map.put("nend", nend);
		
		int ncount = 0;
		List<Map> nowsendcnt=dao.nowsendcnt(map); 
		//그리드에 뿌려주는 카운터
		ncount = ((Integer)(nowsendcnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> selectnow = dao.selectnow(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		for(int i=0; i<selectnow.size(); i++){
			/* 그리드에 출력하는 컬럼 순서*/
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( selectnow.get(i).get("codecertifi")			== null ? "" : selectnow.get(i).get("codecertifi"))  	+"\",");   	//자격증명 텍스트값
			result.append("\"" + ( selectnow.get(i).get("result_no")			== null ? "" : selectnow.get(i).get("result_no"))  		+"\",");   	//자격증번호
			result.append("\"" + ( selectnow.get(i).get("result_start_dt")		== null ? "" : selectnow.get(i).get("result_start_dt")) +"\",");   	//유효시작일
			result.append("\"" + ( selectnow.get(i).get("result_end_dt")   		== null ? "" : selectnow.get(i).get("result_end_dt") )  +"\",");	//유효종료일
			result.append("\"" + ( selectnow.get(i).get("print_state")   		== null ? "" : selectnow.get(i).get("print_state") )  +"\",");	//출력상태
			result.append("\"" + ( selectnow.get(i).get("oper_name")			== null ? "" : selectnow.get(i).get("oper_name")) 	  	+"\",");	//이름
//			JUMIN_DEL
//			result.append("\"" + ( selectnow.get(i).get("oper_no")	    		== null ? "" : selectnow.get(i).get("oper_no") )     	+"\",");	//주민번호
			result.append("\"" + ( selectnow.get(i).get("oper_birth")	    		== null ? "" : selectnow.get(i).get("oper_birth") )     	+"\",");	//생년월일
			result.append("\"" + ( selectnow.get(i).get("oper_lic_no")			== null ? "" : selectnow.get(i).get("oper_lic_no") )  	+"\",");	//면허번호
			result.append("\"" + ( selectnow.get(i).get("codeoperation")		== null ? "" : selectnow.get(i).get("codeoperation") ) 	+"\",");	//취득구분
			result.append("\"" + ( selectnow.get(i).get("person_yn")			== null ? "" : selectnow.get(i).get("person_yn")) 		+"\",");	//회원구분
			result.append("\"" + ( selectnow.get(i).get("result_dt")	   		== null ? "" : selectnow.get(i).get("result_dt") )     	+"\",");	//발급일
			result.append("\"" + ( selectnow.get(i).get("oper_hp")	   			== null ? "" : selectnow.get(i).get("oper_hp") )     	+"\",");	//핸드폰
			result.append("\"" + ( selectnow.get(i).get("operemail")	   		== null ? "" : selectnow.get(i).get("operemail") )    	+"\",");	//이메일
			result.append("\"" + ( selectnow.get(i).get("code_seq")				== null ? "" : selectnow.get(i).get("code_seq") )  		+"\","); 	//순번
			result.append("\"" + ( selectnow.get(i).get("yyyy")					== null ? "" : selectnow.get(i).get("yyyy") )  			+"\","); 	//년도
			result.append("\"" + ( selectnow.get(i).get("season")				== null ? "" : selectnow.get(i).get("season") )  		+"\","); 	//발급반기
			result.append("\"" + ( selectnow.get(i).get("code_pers")			== null ? "" : selectnow.get(i).get("code_pers") )  	+"\","); 	//회원코드
			result.append("\"" + ( selectnow.get(i).get("code_certifi")			== null ? "" : selectnow.get(i).get("code_certifi"))  	+"\",");   	//자격증명
			result.append("\"" + ( selectnow.get(i).get("code_operation")		== null ? "" : selectnow.get(i).get("code_operation") ) +"\",");	//취득구분코드값
			result.append("\"" + ( selectnow.get(i).get("person_yn1")	    	== null ? "" : selectnow.get(i).get("person_yn1") )     +"\"");		//히든 회원코드값 )
			result.append("]}");
			
		}	

		result.append("]}");
		request.setAttribute("result",result);
	}

		return (mapping.findForward("ajaxout"));
}
	
	//자격증발급 엑셀 저장 =================================11-14작업중
	public void licenseissueListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		licenseDao dao = new licenseDao(); 
		memberStateDao dao1=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		HttpSession session=request.getSession();
		String user=session.getAttribute("G_ID").toString(); //유저ID
		//여기부터 개발자 변경 필요
		String name="licenceissueList"; //프로그램명==>자격증심사 엑셀저장
		String s_name="자격증발급"; //엑셀 시트명
		String header_e[]={"codecertifi","result_no", "result_start_dt", "result_end_dt", "oper_name",
//							JUMIN_DEL
//							"oper_no"
							"oper_birth"
							,"oper_lic_no","codeoperation","person_yn","result_dt","oper_hp","operemail","oper_comp_name1","oper_comp_add1_1","code_post"}; //헤더 영문
		String header_k[]={"자격증명","자격증번호", "유효시작일", "유효종료일", "이름"
//				JUMIN_DEL
//				,"주민번호"
				,"생년월일"
				,"면허번호","취득구분","회원구분","발급일","핸드폰","이메일","근무처명","주소","우편번호"}; //헤더 국문
		int c_size[]={10,10,10,10,7,13,9,9,25,15,13,25,25,22,10};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"		));	//자격증 구분
		if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"				));	//
		if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
			map.put("season1",       		"1");									//반기									//반기
		}else if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("2")){
			map.put("season2",       		"2");									//반기
		}
		if(request.getParameter("code_operation1"	)!=null&&!request.getParameter("code_operation1"	).equals("0"))map.put("code_operation1"	, request.getParameter("code_operation1"	));	//
		/*if(request.getParameter("result_start_dt1"	)!=null)map.put("result_start_dt1"	, request.getParameter("result_start_dt1"	));	//
		if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"		));	//
*/		if(request.getParameter("button_ab"			)!=null)map.put("button_ab"			, request.getParameter("button_ab"));//대상검색과 발급현황조회구분값
		
		if(request.getParameter("check"	)!=null)map.put("check"	, request.getParameter("check"	));
		if(request.getParameter("nstart")!=null)map.put("nstart", request.getParameter("nstart"	));
		if(request.getParameter("nend"	)!=null)map.put("nend"	, request.getParameter("nend"	));
		
		List<Map> issueExcel=null;
		String chk = request.getParameter("button_ab");
		
		if(chk.equals("a")){
			issueExcel=dao.selectissue(map,true);
			}
		else if(chk.equals("b")){
			issueExcel=dao.selectnow(map,true);
		}
		else{
			return;
		}

		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름
		
		//-----------여기까지 변경---------------------------------------------------------------------------------------
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(issueExcel, filename, s_name,header_e,header_k,c_size);
		
		File f=new File(fileDir+filename); //파일생성
		
		String contentType=request.getContentType();
		response.setContentType("x-msdownload");
		
		if(contentType==null){
			if(request.getHeader("user-agent").indexOf("MSIE 5.5")!=-1)
				response.setContentType("doesn/matter;");
			else
				response.setContentType("application/octet-stream");
		}else{
			response.setContentType(contentType);
		}
		
		response.setHeader("Content-Transfer-Encoding:", "binary");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
		response.setHeader("Content-Length", ""+f.length());
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		byte b[]=new byte[1024];
		BufferedInputStream fin=new BufferedInputStream(new FileInputStream(f));
		BufferedOutputStream fout=new BufferedOutputStream(response.getOutputStream());
		
		try{
			int read=0;
			while((read=fin.read(b))!=-1){
				fout.write(b, 0, read);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fout!=null) fout.close();
			if(fin!=null) fin.close();
		}
		
		List<Map> i=dao1.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}			
	
			
	
	
	
	/*자격증갱신*/	
	public ActionForward renewalSand(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		return (mapping.findForward("renewalSandList"));
	}
	
	/*자격증갱신  출력*/	
	public ActionForward renewalSandList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

		
	    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
		map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
//		JUMIN_DEL
//		map.put("oper_no1",        		request.getParameter("oper_no1"));								//주민번호				
		map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
		map.put("print_state1",        	request.getParameter("print_state1"));							//상태
		map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
		map.put("check", "2");
//		map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
		map.put("yyyy1", request.getParameter("yyyy1"));

		if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
			map.put("selB", "Y");
			map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
			map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
		}else{
			map.put("selA", "Y");
			map.put("season1",        		request.getParameter("season1"));								//발급반기
			map.put("season2",        		request.getParameter("season2"));								//발급반기											
		}

		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");

		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		map.put("nstart", nstart);
		
		map.put("nend", nend);

		int ncount = 0;
		List<Map> renewalsendcnt=dao.renewalsendcnt(map); 
		//그리드에 뿌려주는 카운터
		ncount = ((Integer)(renewalsendcnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> selectrenewal = dao.selectrenewal(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		for(int i=0; i<selectrenewal.size(); i++){
			/* 그리드에 출력하는 컬럼 순서*/
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( selectrenewal.get(i).get("code_certifi")		== null ? "" : selectrenewal.get(i).get("code_certifi")) 	+"\",");   	//자격증종류
			result.append("\"" + ( selectrenewal.get(i).get("result_no")		== null ? "" : selectrenewal.get(i).get("result_no"))  		+"\",");   	//자격증번호
			result.append("\"" + ( selectrenewal.get(i).get("code_seq")			== null ? "" : selectrenewal.get(i).get("code_seq"))  		+"\",");   	//갱신차수
			result.append("\"" + ( selectrenewal.get(i).get("oper_name")		== null ? "" : selectrenewal.get(i).get("oper_name"))  		+"\",");   	//이름
//			JUMIN_DEL
//			result.append("\"" + ( selectrenewal.get(i).get("oper_no")			== null ? "" : selectrenewal.get(i).get("oper_no"))  		+"\",");   	//주민번호
			result.append("\"" + ( selectrenewal.get(i).get("oper_birth")			== null ? "" : selectrenewal.get(i).get("oper_birth"))  		+"\",");   	//생년월일
			result.append("\"" + ( selectrenewal.get(i).get("oper_lic_no")   	== null ? "" : selectrenewal.get(i).get("oper_lic_no") )   	+"\",");	//면허번호
			result.append("\"" + ( selectrenewal.get(i).get("result_start_dt")	== null ? "" : selectrenewal.get(i).get("result_start_dt")) +"\",");	//유효시작일
			result.append("\"" + ( selectrenewal.get(i).get("result_end_dt")	== null ? "" : selectrenewal.get(i).get("result_end_dt") )  +"\",");	//유효종료일
			result.append("\"" + ( selectrenewal.get(i).get("oper_comp_name1")	== null ? "" : selectrenewal.get(i).get("oper_comp_name1") )+"\",");	//근무처명
			result.append("\"" + ( selectrenewal.get(i).get("oper_hp")			== null ? "" : selectrenewal.get(i).get("oper_hp") )   		+"\",");	//핸드폰
			result.append("\"" + ( selectrenewal.get(i).get("oper_email")		== null ? "" : selectrenewal.get(i).get("oper_email")) 		+"\",");	//이메일
			result.append("\"" + ( selectrenewal.get(i).get("member")	   		== null ? "" : selectrenewal.get(i).get("member") )     	+"\",");	//회원구분
   			result.append("\"" + ( selectrenewal.get(i).get("col1")	   			== null ? "" : selectrenewal.get(i).get("col1") )     		+"\",");	//본회평점
			result.append("\"" + ( selectrenewal.get(i).get("col2")	   			== null ? "" : selectrenewal.get(i).get("col2") )    		+"\",");	//기타평점
			result.append("\"" + ( selectrenewal.get(i).get("col3")				== null ? "" : selectrenewal.get(i).get("col3") )  			+"\","); 	//봉사평점
			result.append("\"" + ( selectrenewal.get(i).get("docname")	== null ? "" : selectrenewal.get(i).get("docname") )  +"\",");	//detcodename
			result.append("\"" + ( selectrenewal.get(i).get("renewalbt")	== null ? "" : selectrenewal.get(i).get("renewalbt") )  +"\",");	//회비인증기간
			result.append("\"" + ( selectrenewal.get(i).get("renewalok")	== null ? "" : selectrenewal.get(i).get("renewalok") )  +"\",");	//회비인증기간
			result.append("\"" + ( selectrenewal.get(i).get("marksok")	== null ? "" : selectrenewal.get(i).get("marksok") )  +"\",");	//평점PASS여부
			result.append("\"" + ( selectrenewal.get(i).get("totalok")	== null ? "" : selectrenewal.get(i).get("totalok") )  +"\",");	//통합PASS여부
			result.append("\"" + ( selectrenewal.get(i).get("code_certifi_c")		== null ? "" : selectrenewal.get(i).get("code_certifi_c") )  	+"\","); 	//자격구분
			result.append("\"" + ( selectrenewal.get(i).get("prtstateh")		== null ? "" : selectrenewal.get(i).get("prtstateh") )  	+"\","); 	//상태
			result.append("\"" + ( selectrenewal.get(i).get("prtstatec")		== null ? "" : selectrenewal.get(i).get("prtstatec") )  	+"\","); 	//상태
			result.append("\"" + ( selectrenewal.get(i).get("code_pers")	    == null ? "" : selectrenewal.get(i).get("code_pers") )     	+"\",");	//히든 주민번호(xml에서 추가한 명칭값과동일하게 )
			result.append("\"" + ( selectrenewal.get(i).get("person_yn1")	    == null ? "" : selectrenewal.get(i).get("person_yn1") )     +"\",");	//히든 회원코드값 )
			result.append("\"" + ( selectrenewal.get(i).get("codeoperation")	== null ? "" : selectrenewal.get(i).get("codeoperation") ) 	+"\"");	//대상자 )			
			result.append("]}");
		}	
		
		result.append("]}");
		request.setAttribute("result",result);
		return (mapping.findForward("ajaxout"));
	} 
	
	
	
	
	/*자격증갱신 주민번호 체크*/
	public ActionForward pers_check5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//전체건수 구해서 넘긴다.
		Map map = new HashMap();
		licenseDao dao=new licenseDao();
		
//		//조건 입력
//		// 변수에 값 받아서 넣음
//		if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
//		if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));	//교육년도
//		if(request.getParameter("season1"			)!=null)map.put("season1"			, request.getParameter("season1"		));	//학기
//		if(request.getParameter("season2"			)!=null)map.put("season2"			, request.getParameter("season2"		));	//학기
//
//		if(request.getParameter("result_no1"			)!=null)map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
//		if(request.getParameter("oper_name1"			)!=null)map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
////		JUMIN_DEL
////		if(request.getParameter("oper_no1"			)!=null)map.put("oper_no1",        		request.getParameter("oper_no1"));								//주민번호
//		if(request.getParameter("oper_birth1"			)!=null)map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일
////		map.put("oper_lic_no1",        	request.getParameter("oper_lic_no1"));							//면허번호
//		if(request.getParameter("season2"			)!=null)map.put("season2",        		request.getParameter("season2"));								//발급반기
//		if(request.getParameter("print_state1"			)!=null)map.put("print_state1",        	request.getParameter("print_state1"));							//상태
//		if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
//		map.put("check", "2");
		
	    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
		map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
		map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
		map.put("print_state1",        	request.getParameter("print_state1"));							//상태
		map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
		map.put("check", "2");
//		map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
		map.put("yyyy1", request.getParameter("yyyy1"));

		if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
			map.put("selB", "Y");
			map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
			map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
		}else{
			map.put("selA", "Y");
			map.put("season1",        		request.getParameter("season1"));								//발급반기
			map.put("season2",        		request.getParameter("season2"));								//발급반기											
		}

		//여기서 구하고 넘긴다.
		List<Map> count = dao.renewalsendcnt(map);
		int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

		// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
		
		String param = "";
//		if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
//		if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1);	//교육년도
//		if(request.getParameter("season1"			)!=null)param+="&season1="			+ request.getParameter("season1"		);	//학기
//		if(request.getParameter("season2"			)!=null)param+="&season2="			+ request.getParameter("season2"		);	//학기
//		if(request.getParameter("result_no1"			)!=null)param+="&result_no1="			+ request.getParameter("result_no1");							//자격증번호
//		if(request.getParameter("oper_name1"			)!=null)param+="&oper_name1="			+ URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8");	//이름
////		JUMIN_DEL
////		if(request.getParameter("oper_no1"			)!=null)param+="&oper_no1="			+ request.getParameter("oper_no1");								//주민번호
//		if(request.getParameter("oper_birth1"			)!=null)param+="&oper_birth1="			+ request.getParameter("oper_birth1");								//생년월일
////		map.put("oper_lic_no1",        	request.getParameter("oper_lic_no1"));							//면허번호
//		if(request.getParameter("season2"			)!=null)param+="&season2="			+ request.getParameter("season2");								//발급반기
//		if(request.getParameter("print_state1"			)!=null)param+="&print_state1="			+ request.getParameter("print_state1");							//상태
//		if(request.getParameter("code_seq1"			)!=null)param+="&code_seq1="			+ request.getParameter("code_seq1");							//상태
		
		if(request.getParameter("code_certifi1")!=null) param+="&code_certifi1="        	+ request.getParameter("code_certifi1");							//자격증구분		  
		if(request.getParameter("result_no1")!=null) param+="&result_no1="        	+ request.getParameter("result_no1");							//자격증번호
		if(request.getParameter("oper_name1")!=null) param+="&oper_name1="        	+ request.getParameter("oper_name1");							//자격증번호
		if(request.getParameter("oper_birth1")!=null) param+="&oper_birth1="        	+ request.getParameter("oper_birth1");								//생년월일				
		if(request.getParameter("print_state1")!=null) param+="&print_state1="        	+ request.getParameter("print_state1");							
		if(request.getParameter("code_seq1")!=null) param+="&code_seq1="        	+ request.getParameter("code_seq1");							
		if(request.getParameter("yyyy1")!=null) param+="&yyyy1="        	+ request.getParameter("yyyy1");							
		if(request.getParameter("mm1")!=null) param+="&mm1="        	+ request.getParameter("mm1");							
		if(request.getParameter("dd1")!=null) param+="&dd1="        	+ request.getParameter("dd1");							
		if(request.getParameter("season1")!=null) param+="&season1="        	+ request.getParameter("season1");							
		if(request.getParameter("season2")!=null) param+="&season2="        	+ request.getParameter("season2");							

		//System.out.println("asdfasdf person_check in = "+param);
		
		//검색 변수 넘김
		request.setAttribute("param", param);
		//전체 개수 넘김
		request.setAttribute("ncount", ncount);

		request.setAttribute("url", "license5");
		
	return (mapping.findForward("pers_check5"));
			
	}
	

	//자격증갱신 엑셀 저장 =================================11-14작업중
	public void licenserenewalListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		licenseDao dao = new licenseDao(); 
		memberStateDao dao1=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		HttpSession session=request.getSession();
		String user=session.getAttribute("G_ID").toString(); //유저ID
		//여기부터 개발자 변경 필요
		String name="licenserenewalList"; //프로그램명==>자격증갱신 엑셀저장
		String s_name="자격증갱신"; //엑셀 시트명
//		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"detcode","code_certifi","result_no","code_seq","oper_name"
//							JUMIN_DEL
//							, "oper_no"
							, "oper_birth"
							, "oper_lic_no", "result_start_dt", "result_end_dt","oper_comp_name1"
							,"oper_hp","oper_email","member","total_marks","col1","col2","col3","docname","renewalbt","renewalok","marksok","totalok","code_post","oper_comp_add1_1","result_dt"}; //헤더 영문
		String header_k[]={"Key","자격증종류","자격증번호", "갱신차수", "이름"
//						JUMIN_DEL
//							, "주민번호"
							, "생년월일"
							, "면허번호","유효시작일","유효종료일","근무처명"
							,"핸드폰","이메일","회원구분","총평점","본회평점","기타평점","봉사평점","갱신서류","회비인증기간","회비인증","평점인증","통합인증","우편번호","발송주소","갱신일자"}; //헤더 국문
		int c_size[]={18,10,10,10,10,15,10,15,15,27,15,33,25,8,8,8,8,22,25,10,10,10,10,55,15};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);

		
		if(request.getParameter("yyyy1") != null||request.getParameter("code_certifi1") != null){
			
			map.put("nstart", request.getParameter("nstart"));
			map.put("nend"	, request.getParameter("nend"));

//			map.put("yyyy1",        		request.getParameter("yyyy1"));									//발급년도
//		    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증번호
//			map.put("season1",        		request.getParameter("season1"));
//			map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
//			if(request.getParameter("oper_name1"			)!=null)map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
////			JUMIN_DEL
////			map.put("oper_no1",        		request.getParameter("oper_no1"));								//주민번호
//			map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일
////			map.put("oper_lic_no1",        	request.getParameter("oper_lic_no1"));							//면허번호
//			map.put("season2",        		request.getParameter("season2"));								//발급반기
//			map.put("print_state1",        	request.getParameter("print_state1"));							//상태
//			map.put("code_seq1",        	request.getParameter("code_seq1"));//발급반기
//
//			
//			map.put("check", request.getParameter("check"));
			
		    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
			map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
			map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
			map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
			map.put("print_state1",        	request.getParameter("print_state1"));							//상태
			map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
			map.put("check", "2");
//			map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
			map.put("yyyy1", request.getParameter("yyyy1"));
			
			if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
				map.put("selB", "Y");
				map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
				map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
			}else{
				map.put("selA", "Y");
				map.put("season1",        		request.getParameter("season1"));								//발급반기
				map.put("season2",        		request.getParameter("season2"));								//발급반기											
			}
		}

			int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
			String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름
		List<Map> renewalExcel=dao.selectrenewal(map,true);
		//-----------여기까지 변경---------------------------------------------------------------------------------------
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(renewalExcel, filename, s_name,header_e,header_k,c_size);
		
		File f=new File(fileDir+filename); //파일생성
		
		String contentType=request.getContentType();
		response.setContentType("x-msdownload");
		
		if(contentType==null){
			if(request.getHeader("user-agent").indexOf("MSIE 5.5")!=-1)
				response.setContentType("doesn/matter;");
			else
				response.setContentType("application/octet-stream");
		}else{
			response.setContentType(contentType);
		}
		
		response.setHeader("Content-Transfer-Encoding:", "binary");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
		response.setHeader("Content-Length", ""+f.length());
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		byte b[]=new byte[1024];
		BufferedInputStream fin=new BufferedInputStream(new FileInputStream(f));
		BufferedOutputStream fout=new BufferedOutputStream(response.getOutputStream());
		
		try{
			int read=0;
			while((read=fin.read(b))!=-1){
				fout.write(b, 0, read);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fout!=null) fout.close();
			if(fin!=null) fin.close();
		}
		
		List<Map> i=dao1.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}	
	/*자격증심사 첨부파일*/
	public ActionForward uFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String code_kind1 		= request.getParameter("code_kind1");
		String code_certifi1 	= request.getParameter("code_certifi1");
		String code_seq1 		= request.getParameter("code_seq1");
		String code_bran1 		= request.getParameter("code_bran1");
		String bran_seq1 		= request.getParameter("bran_seq1");
		String receipt_no1 		= request.getParameter("receipt_no1");
		
//		String code_operation1 		= request.getParameter("code_operation1");
//		String code_operation_other1 		= request.getParameter("code_operation_other1");
//		
//		if(code_operation1!=null && code_operation_other1!=null && !code_operation1.equals(code_operation_other1) && "3".equals(code_operation_other1)){
//			code_bran1 		= request.getParameter("code_bran_other1");
//			bran_seq1 		= request.getParameter("bran_seq_other1");
//			receipt_no1 		= request.getParameter("receipt_no_other1");
//		}
		
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 
		
		map.put("code_kind1", code_kind1);
		map.put("code_certifi1", code_certifi1);
		map.put("code_seq1", code_seq1);
		map.put("code_bran1", code_bran1);
		map.put("bran_seq1", bran_seq1);
		map.put("receipt_no1", receipt_no1);
		
		List<Map> sFiles=dao.selectCFiles(map);
		
		request.setAttribute("sFiles", sFiles);
		
		return (mapping.findForward("fileUP"));		
	}
	//자격증심사 첨부파일
	public ActionForward upFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		Map map = new HashMap();
		Map map1 = new HashMap();
		licenseDao dao = new licenseDao();
		
		String saveFolder="D:/WEB/dietitian.or.kr_ver3/upload/license_inspection/";		
		
		String encType="UTF-8";
		int maxSize=10*1024*1024;
		File f1=null;
		File f2=null;
		File f3=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType,new DefaultFileRenamePolicy());
		
		String oper_name1=StringUtil.nullToStr("",URLDecoder.decode(multi.getParameter("opername1"),"UTF-8"));
		String codeoperation=StringUtil.nullToStr("", URLDecoder.decode(multi.getParameter("codeoperation2"),"UTF-8"));
		String param=StringUtil.nullToStr("", multi.getParameter("param"));
		String code_operation1=StringUtil.nullToStr("", multi.getParameter("code_operation1"));
		
		System.out.println("oper_name1>>>>>>>"+oper_name1);
		String orgfilename="";
		String filename="";
		String msg="";
		String temp="";
		String[] files1;
		int fileNo=0;
		
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();				
				orgfilename=multi.getFilesystemName(name);
				if(orgfilename!=null){					
					System.out.println("name="+name+"       orginal filename="+orgfilename);
					String[] ext=orgfilename.split("\\.");
					filename=multi.getParameter("filename")+name;
					System.out.println("filename==============>"+filename);
					f1=new File(saveFolder+"/"+multi.getOriginalFileName(name));
					f2=new File(saveFolder+"/"+filename+"."+ext[ext.length-1]);
					f3=new File(saveFolder+"/"+filename+"."+ext[ext.length-1]);
					
					System.out.println(f1.getName()+"       "+f2.getName()+"       "+f3.getName());
					
					if(f3.exists()){
						boolean a=f3.delete();
						System.out.println("f3.delete(); >>>> "+a);
					}
					if(f1.exists()) {
						boolean b=f1.renameTo(f2);
						System.out.println("f1.renameTo(f2) >>>> "+b);
					}
					request.setAttribute(name+"YN", filename+"."+ext[ext.length-1]);
					temp+=filename+"."+ext[ext.length-1]+" ";
				}
			}
			
			files1=temp.split(" ");
			fileNo=files1.length;
			
			if(temp.length()!=0){				
				for(int i=0;i<fileNo;i++){
					String[] tmp=files1[i].split("\\.");				
					//map.put("add_file_no", tmp[0].substring(14, 16));
					map.put("add_file_no", tmp[0].substring(16, 18));
					map.put("code_kind1", multi.getParameter("code_kind1"));
					map.put("code_certifi1", multi.getParameter("code_certifi1"));
					map.put("code_seq1", multi.getParameter("code_seq1"));
					map.put("code_bran1", multi.getParameter("code_bran1"));
					map.put("bran_seq1", multi.getParameter("bran_seq1"));
					map.put("receipt_no1", multi.getParameter("receipt_no"));
					map.put("chang_file_name", files1[i]);
					List<Map> sFiles=dao.selectCFiles(map);
					if(sFiles.size()==0){
						List<Map> insertinspection1 = dao.insertinspection(map);
					}else{
						int upinspection1=dao.upinspection2(map);
					}											
				}
			}
			msg="화일 첨부가 완료됐습니다.";

		}catch(Exception e){
			e.printStackTrace();
		}
		map1.put("code_kind1", multi.getParameter("code_kind1"));
		map1.put("code_certifi1", multi.getParameter("code_certifi1"));
		map1.put("code_seq1", multi.getParameter("code_seq1"));
		map1.put("code_bran1", multi.getParameter("code_bran1"));
		map1.put("bran_seq1", multi.getParameter("bran_seq1"));
		map1.put("receipt_no1", multi.getParameter("receipt_no"));
		
		List<Map> sFiles=dao.selectCFiles(map1);
		
		request.setAttribute("sFiles", sFiles);
		String bran1=multi.getParameter("code_bran1");
		//System.out.println("sFiles.size()==================>"+sFiles.size());
		request.setAttribute("msg", msg);
		request.setAttribute("oper_name1", oper_name1);
		request.setAttribute("codeoperation", codeoperation);
		request.setAttribute("param", param);
		request.setAttribute("code_operation1", code_operation1);
		request.setAttribute("yyyy1", multi.getParameter("yyyy1"));
		request.setAttribute("code_kind1", multi.getParameter("code_kind1"));
		request.setAttribute("code_bran1", bran1);
		request.setAttribute("edutest_name1", multi.getParameter("edutest_name1"));
		request.setAttribute("code_certifi1", multi.getParameter("code_certifi1"));
		request.setAttribute("receipt_no", multi.getParameter("receipt_no"));
		request.setAttribute("del", "N");		
		request.setAttribute("code_seq", multi.getParameter("code_seq1"));
		request.setAttribute("bran_seq", multi.getParameter("bran_seq1"));
		request.setAttribute("season1", multi.getParameter("season1"));
		
		return (mapping.findForward("fileUP"));	
	}
	
	public ActionForward delFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
			Map map = new HashMap();
			educationDao dao = new educationDao();
			licenseDao dao1 = new licenseDao();
			HttpSession session=request.getSession();
			String g_name = session.getAttribute("G_NAME").toString(); 
			String saveFolder="D:/WEB/dietitian.or.kr_ver3/upload/license_inspection/";			

			String encType="UTF-8";
			int maxSize=10*1024*1024;
			File f1=null;
			File f2=null;
			File f3=null;
			MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType,new DefaultFileRenamePolicy());
			
			String oper_name1=StringUtil.nullToStr("",URLDecoder.decode(multi.getParameter("opername1"),"UTF-8"));
			String codeoperation=StringUtil.nullToStr("", URLDecoder.decode(multi.getParameter("codeoperation2"),"UTF-8"));
			String param=StringUtil.nullToStr("", multi.getParameter("param"));
			String code_operation1=StringUtil.nullToStr("", multi.getParameter("code_operation1"));
			
			System.out.println("oper_name1>>>>>>>"+oper_name1);
			String orgfilename="";
			String filename="";
			String msg="";
			String temp="";
			String dFilename="";
			try{
				dFilename=multi.getParameter("dFilename");
				System.out.println("dFilename==========>"+dFilename);
				
				f1=new File(saveFolder+"/"+dFilename);
				boolean del=false;
				if(f1.exists()){
					del=f1.delete();
					System.out.println(dFilename+"삭제 결과="+del);
				}
				
				if(del)  msg="파일이 삭제됐습니다.";
				
				//map.put("add_file_no", dFilename.substring(14, 16));
				map.put("add_file_no", dFilename.substring(16, 18));
				map.put("code_kind1", multi.getParameter("code_kind1"));
				map.put("code_certifi1", multi.getParameter("code_certifi1"));
				map.put("code_seq1", multi.getParameter("code_seq1"));
				map.put("code_bran1", multi.getParameter("code_bran1"));
				map.put("bran_seq1", multi.getParameter("bran_seq1"));
				map.put("receipt_no1", multi.getParameter("receipt_no"));
				map.put("chang_file_name", "");				
				
				int deletAFiles=dao.deletAFiles(map);
				
			}catch(Exception e){
				e.printStackTrace();
			}	
			
			Map map1=new HashMap();
			map1.put("code_kind1", multi.getParameter("code_kind1"));
			map1.put("code_certifi1", multi.getParameter("code_certifi1"));
			map1.put("code_seq1", multi.getParameter("code_seq1"));
			map1.put("code_bran1", multi.getParameter("code_bran1"));
			map1.put("bran_seq1", multi.getParameter("bran_seq1"));
			map1.put("receipt_no1", multi.getParameter("receipt_no"));
			
			List<Map> sFiles=dao.selectAFiles(map1);
			int uRstate=0;	
			map1.put("result_state", "10");
			map1.put("g_name", g_name);
			System.out.println("sFiles.size()====================>"+sFiles.size());
			uRstate=dao1.uRstate(map1);
						
			request.setAttribute("sFiles", sFiles);
			String bran1=multi.getParameter("code_bran1");
			System.out.println("sFiles.size()==================>"+sFiles.size());
			request.setAttribute("msg", msg);
			request.setAttribute("oper_name1", oper_name1);
			System.out.println("oper_name1==========>"+oper_name1);
			request.setAttribute("codeoperation", codeoperation);
			request.setAttribute("param", param);
			request.setAttribute("code_operation1", code_operation1);
			request.setAttribute("yyyy1", multi.getParameter("yyyy1"));
			request.setAttribute("code_kind1", multi.getParameter("code_kind1"));
			request.setAttribute("code_bran1", bran1);
			request.setAttribute("edutest_name1", multi.getParameter("edutest_name1"));
			request.setAttribute("code_certifi1", multi.getParameter("code_certifi1"));
			request.setAttribute("del", "Y");
			request.setAttribute("code_seq", multi.getParameter("code_seq1"));
			request.setAttribute("bran_seq", multi.getParameter("bran_seq1"));
			request.setAttribute("receipt_no", multi.getParameter("receipt_no"));
			request.setAttribute("season1", multi.getParameter("season1"));
			return (mapping.findForward("fileUP"));		
		}
	
	//자격증갱신 파일 첨부
	public ActionForward rFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			String code_pers1 		= StringUtil.nullToStr("",request.getParameter("code_pers1"));
			String code_certifi1 	= StringUtil.nullToStr("",request.getParameter("code_certifi1"));
			String code_seq1 		= StringUtil.nullToStr("",request.getParameter("code_seq1"));
			String result_no1 		= StringUtil.nullToStr("",request.getParameter("result_no1"));
			
			
			Map map = new HashMap();
			licenseDao dao = new licenseDao(); 
			
			map.put("code_pers1", code_pers1);
			map.put("code_certifi1", code_certifi1);
			map.put("code_seq1", code_seq1);
			map.put("result_no1", result_no1);
			
			
			List<Map> sFiles=dao.selectBFiles(map);			
			request.setAttribute("sFiles", sFiles);

			return (mapping.findForward("rfileUP"));		
		}
	
	public ActionForward rupFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		Map map = new HashMap();
		licenseDao dao = new licenseDao();
		
		String saveFolder="D:/WEB/dietitian.or.kr_ver3/upload/license_renewal";
		
		String encType="UTF-8";
		int maxSize=10*1024*1024;
		File f1=null;
		File f2=null;
		File f3=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType,new DefaultFileRenamePolicy());
		
		String oper_name1=StringUtil.nullToStr("",URLDecoder.decode(multi.getParameter("opername1"),"UTF-8"));
		String param=StringUtil.nullToStr("", multi.getParameter("param"));
		String del=StringUtil.nullToStr("", multi.getParameter("del"));
		String code_certifi1=StringUtil.nullToStr("", multi.getParameter("code_certifi1"));
		String code_seq1=StringUtil.nullToStr("", multi.getParameter("code_seq1"));
		String code_pers1=StringUtil.nullToStr("", multi.getParameter("code_pers1"));
		String result_no1=StringUtil.nullToStr("", multi.getParameter("result_no1"));
		String print_state1=StringUtil.nullToStr("", multi.getParameter("print_state1"));
		String yyyy1=StringUtil.nullToStr("", multi.getParameter("yyyy1"));
		String season1=StringUtil.nullToStr("", multi.getParameter("season1"));
		
		System.out.println("oper_name1>>>>>>>"+oper_name1);
		String orgfilename="";
		String filename="";
		String msg="";
		String temp="";
		String temp1="";
		String[] files1;
		String[] files2;
		int fileNo=0;
		
		filename=code_pers1+code_certifi1+result_no1+code_seq1;
		
		
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();				
				orgfilename=multi.getFilesystemName(name);
				if(orgfilename!=null){					
					System.out.println("name="+name+"       orginal filename="+orgfilename);
					String[] ext=orgfilename.split("\\.");
					filename=multi.getParameter("filename")+name.substring(8,10);
					System.out.println("filename==============>"+filename+"    name=============>"+name);
					f1=new File(saveFolder+"/"+multi.getOriginalFileName(name));
					f2=new File(saveFolder+"/"+filename+"."+ext[ext.length-1]);
					f3=new File(saveFolder+"/"+filename+"."+ext[ext.length-1]);
					
					if(f3.exists()){
						boolean a=f3.delete();
						System.out.println("f3.delete(); >>>> "+a);
					}
					if(f1.exists()) {
						boolean b=f1.renameTo(f2);
						System.out.println("f1.renameTo(f2) >>>> "+b);
					}
					request.setAttribute(name+"YN", filename+"."+ext[ext.length-1]);
					temp+=filename+"."+ext[ext.length-1]+" ";
					temp1+=name+" ";
				}
			}
			
			files1=temp.split(" ");
			files2=temp1.split(" ");
			fileNo=files1.length;
			
			if(temp.length()!=0){				
				for(int i=0;i<fileNo;i++){
					map.put(files2[i], files1[i]);
				}
				map.put("code_pers1", code_pers1);
				map.put("code_certifi1", code_certifi1);
				map.put("code_seq1", code_seq1);
				map.put("result_no1", result_no1);
				System.out.println("result_no1========>"+result_no1);				
				int upBFiles=dao.upBFiles(map);
			}
			
			msg="화일 첨부가 완료됐습니다.";

		}catch(Exception e){
			e.printStackTrace();
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("oper_name1", oper_name1);
		request.setAttribute("param", param);
		request.setAttribute("del", "N");
		request.setAttribute("code_certifi1", code_certifi1);
		request.setAttribute("code_seq1", code_seq1);
		request.setAttribute("code_pers1", code_pers1);
		request.setAttribute("result_no1", result_no1);
		request.setAttribute("print_state1", print_state1);
		request.setAttribute("yyyy1", yyyy1);
		request.setAttribute("season1", season1);

		return (mapping.findForward("rfileUP"));	
	}
	
	public ActionForward delFile2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
			Map map = new HashMap();
			educationDao dao = new educationDao();
			licenseDao dao1 = new licenseDao();
			HttpSession session=request.getSession();
			String g_name = session.getAttribute("G_NAME").toString(); 
			String saveFolder="D:/WEB/dietitian.or.kr_ver3/upload/license_renewal";			

			String encType="UTF-8";
			int maxSize=10*1024*1024;
			File f1=null;
			File f2=null;
			File f3=null;
			MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType,new DefaultFileRenamePolicy());
			
			String oper_name1=StringUtil.nullToStr("",URLDecoder.decode(multi.getParameter("opername1"),"UTF-8"));
			String param=StringUtil.nullToStr("", multi.getParameter("param"));
			
			
			System.out.println("oper_name1>>>>>>>"+oper_name1);
			String orgfilename="";
			String filename="";
			String msg="";
			String temp="";
			String dFilename="";
			try{
				dFilename=multi.getParameter("dFilename");
				System.out.println("dFilename==========>"+dFilename);
				
				f1=new File(saveFolder+"/"+dFilename);
				boolean del=false;
				if(f1.exists()){
					del=f1.delete();
					System.out.println(dFilename+"삭제 결과="+del);
				}
				
				if(del)  msg="파일이 삭제됐습니다.";
				
				map.put("code_pers1", multi.getParameter("code_pers1"));
				map.put("code_certifi1", multi.getParameter("code_certifi1"));
				map.put("result_no1", multi.getParameter("result_no1"));
				map.put("code_seq1", multi.getParameter("code_seq1"));
								
				int deletBFiles=dao1.deletBFiles(map);
				
			}catch(Exception e){
				e.printStackTrace();
			}	
			
			Map map1=new HashMap();

			map1.put("code_pers1", multi.getParameter("code_pers1"));
			map1.put("code_certifi1", multi.getParameter("code_certifi1"));
			map1.put("result_no1", multi.getParameter("result_no1"));
			map1.put("code_seq1", multi.getParameter("code_seq1"));

			List<Map> sFiles=dao1.selectBFiles(map);
			
			request.setAttribute("sFiles", sFiles);
			
			System.out.println("sFiles.size()==================>"+sFiles.size());
			request.setAttribute("msg", msg);
						
			request.setAttribute("oper_name1", oper_name1);
			request.setAttribute("param", param);
			request.setAttribute("del", "Y");
			request.setAttribute("code_certifi1", multi.getParameter("code_certifi1"));
			request.setAttribute("code_seq1", multi.getParameter("code_seq1"));
			request.setAttribute("code_pers1", multi.getParameter("code_pers1"));
			request.setAttribute("result_no1", multi.getParameter("result_no1"));
			request.setAttribute("print_state1", multi.getParameter("print_state1"));
			request.setAttribute("yyyy1", multi.getParameter("yyyy1"));
			request.setAttribute("season1", multi.getParameter("season1"));
			return (mapping.findForward("rfileUP"));		
		}
	
	
	
	
	//자격증쪽지발송=============================================================================================================================================
	
	/*
	 * 작업명 : Home>자격증>시험별응시현황
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.21
	 * 작   업 : sendnotePad	쪽지발송
	 */
	public ActionForward sendnotePad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String code_pers = "";
		String pers_name = "";
		if(request.getParameter("code_pers")!= null)	code_pers = request.getParameter("code_pers")+"";
		if(request.getParameter("pers_name")!= null)	pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		
		
		request.setAttribute("code_pers", code_pers);
		request.setAttribute("pers_name", pers_name);
		request.setAttribute("chk", "");
		return (mapping.findForward("sendnotePadAll"));
	}
	/*
	 * 작업명 : Home>자격증>시험별응시현황
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.21
	 * 작   업 : sendnotePadAll	쪽지발송전체
	 */
	public ActionForward sendnotePadAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//검색합시다.
		licenseDao dao = new licenseDao(); 
	 		
		if(request.getParameter("code_certifi1") != null||request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null
		||request.getParameter("season1") != null||request.getParameter("operation_cnt1") != null||request.getParameter("code_operation1") != null
		||request.getParameter("result_state1") != null||request.getParameter("detcode") != null||request.getParameter("oper_state1") != null||request.getParameter("operation_place1") != null
		){
		
			/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
			map.put("code_certifi1",        request.getParameter("code_certifi1"));		//자격증 구분
			map.put("yyyy1",        		request.getParameter("yyyy1"));				//교육년도
			map.put("code_bran1",        	request.getParameter("code_bran1"));		//교육주최
			map.put("season1",        		request.getParameter("season1"));			//학기
			map.put("operation_cnt1",       request.getParameter("operation_cnt1"));	//횟수
			map.put("code_operation1",      request.getParameter("code_operation1"));	//대상자
			map.put("result_state1",       	request.getParameter("result_state1"));		//응시결과상태
			map.put("oper_state1",       	request.getParameter("oper_state1"));		//응시상태
			map.put("operation_place1",     request.getParameter("operation_place1"));	//시험장소
			map.put("sel", "Y");
			map.put("detcode", 				request.getParameter("detcode"));
			List<Map> list = dao.selectlicense(map);		
			//검색 종료
	
			//전송할 전화번호 
			String code_pers ="";
			String pers_name ="";
			
			if(list!=null) System.out.println("list 사이즈============>"+list.size());
			
			for(int i=0; i<list.size(); i++) {
				
				if(!(list.get(i).get("person_yn1")+"").equals("")) {
					if((list.get(i).get("person_yn1")+"").length()>0){
						if(code_pers.length() > 0) code_pers+= ",";
						code_pers += list.get(i).get("person_yn1");
					}
				}
	
				if(!(list.get(i).get("oper_name")+"").equals("")) {
					if((list.get(i).get("oper_name")+"").length()>0){
						if(pers_name.length() > 0) pers_name+= ",";
						pers_name += list.get(i).get("oper_name");
					}
				}
			}
			request.setAttribute("code_pers", code_pers);
			request.setAttribute("pers_name", pers_name);
			request.setAttribute("chk", "ALL");
		}

		return (mapping.findForward("sendnotePadAll"));
	}	
//==================================================================================================================	
	/*
	 * 작업명 : Home>자격증>결과등록
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePad1	쪽지발송
	 */
	public ActionForward sendnotePad1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String code_pers = "";
		String pers_name = "";
		if(request.getParameter("code_pers")!= null)	code_pers = request.getParameter("code_pers")+"";
		if(request.getParameter("pers_name")!= null)	pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		
		
		request.setAttribute("code_pers", code_pers);
		request.setAttribute("pers_name", pers_name);
		request.setAttribute("chk", "");
		return (mapping.findForward("sendnotePadAll1"));
	}
	
	/*
	 * 작업명 : Home>자격증>결과등록
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePadAll1	쪽지발송전체
	 */
	public ActionForward sendnotePadAll1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//검색합시다.
		licenseDao dao = new licenseDao(); 
	 		
		if(request.getParameter("yyyy1") != null||request.getParameter("code_certifi1") != null||request.getParameter("code_bran1") != null
		||request.getParameter("edutest_name1") != null||request.getParameter("season1") != null||request.getParameter("oper_name1") != null
		||request.getParameter("oper_no1") != null){
		
			/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
			map.put("yyyy1",        		request.getParameter("yyyy1"));				//교육년도
			map.put("code_certifi1",        request.getParameter("code_certifi1"));		//자격증 구분
			map.put("code_bran1",        	request.getParameter("code_bran1"));		//교육주최
			map.put("edutest_name1",        request.getParameter("edutest_name1"));		//내용
			map.put("season1",        		request.getParameter("season1"));			//학기
			map.put("oper_name1",     		request.getParameter("oper_name1"));		//이름
			map.put("oper_no1",       		request.getParameter("oper_no1"));			//주민번호
			map.put("sel", "Y");
			
			List<Map> list = dao.selectresult(map);		
			//검색 종료
			
			if(list!=null) System.out.println("list사이즈======>"+list.size());
			
			//전송할 전화번호 
			String code_pers ="";
			String pers_name ="";
			
			for(int i=0; i<list.size(); i++) {
				
				if(!(list.get(i).get("person_yn1")+"").equals("")) {
					if((list.get(i).get("person_yn1")+"").length()>0){
						if(code_pers.length() > 0) code_pers+= ",";
						code_pers += list.get(i).get("person_yn1");
					}
				}
	
				if(!(list.get(i).get("oper_name")+"").equals("")) {
					if((list.get(i).get("oper_name")+"").length()>0){
						if(pers_name.length() > 0) pers_name+= ",";
						pers_name += list.get(i).get("oper_name");
					}
				}
			}
			request.setAttribute("code_pers", code_pers);
			request.setAttribute("pers_name", pers_name);
			request.setAttribute("chk", "ALL");
		}

		return (mapping.findForward("sendnotePadAll1"));
	}	
//==========================================================================================	
	/*
	 * 작업명 : Home>자격증>자격증심사
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePad2	쪽지발송
	 */
	public ActionForward sendnotePad2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String code_pers = "";
		String pers_name = "";
		if(request.getParameter("code_pers")!= null)	code_pers = request.getParameter("code_pers")+"";
		if(request.getParameter("pers_name")!= null)	pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		
		
		request.setAttribute("code_pers", code_pers);
		request.setAttribute("pers_name", pers_name);
		request.setAttribute("chk", "");
		return (mapping.findForward("sendnotePadAll2"));
	}
	
	/*
	 * 작업명 : Home>자격증>자격증심사
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePadAll2	쪽지발송전체
	 */
	public ActionForward sendnotePadAll2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//검색합시다.
		licenseDao dao = new licenseDao(); 
	 	  		
		if(request.getParameter("yyyy1") != null||request.getParameter("code_certifi1") != null||request.getParameter("code_bran1") != null
		||request.getParameter("code_operation1") != null||request.getParameter("oper_name1") != null
		||request.getParameter("oper_no1") != null){
		
			/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
			map.put("yyyy1",        		request.getParameter("yyyy1"));				//교육년도
			map.put("code_certifi1",        request.getParameter("code_certifi1"));		//자격증 구분
			map.put("code_bran1",        	request.getParameter("code_bran1"));		//교육주최
			if( request.getParameter("code_operation1") != null && !request.getParameter("code_operation1").equals("0") )	
					map.put("code_operation1", request.getParameter("code_operation1"));
			map.put("oper_name1",     		request.getParameter("oper_name1"));		//이름
			map.put("oper_no1",       		request.getParameter("oper_no1"));			//주민번호
			
			List<Map> list = dao.selectlinspection(map);		
			//검색 종료
	
			if(list!=null) System.out.println("list 사이즈====>"+list.size());
			String code_pers ="";
			String pers_name ="";
			
			for(int i=0; i<list.size(); i++) {
				
				if(!(list.get(i).get("person_yn1")+"").equals("")) {
					if((list.get(i).get("person_yn1")+"").length()>0){
						if(code_pers.length() > 0) code_pers+= ",";
						code_pers += list.get(i).get("person_yn1");
					}
				}
	
				if(!(list.get(i).get("oper_name")+"").equals("")) {
					if((list.get(i).get("oper_name")+"").length()>0){
						if(pers_name.length() > 0) pers_name+= ",";
						pers_name += list.get(i).get("oper_name");
					}
				}
			}
			request.setAttribute("code_pers", code_pers);
			request.setAttribute("pers_name", pers_name);
			request.setAttribute("chk", "ALL");
		}

		return (mapping.findForward("sendnotePadAll2"));
	}
//	==========================================================================================	
	
	/*
	 * 작업명 : Home>자격증>자격증발급 대상검색
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePad3	쪽지발송
	 */
	public ActionForward sendnotePad3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String code_pers = "";
		String pers_name = "";
		if(request.getParameter("code_pers")!= null)	code_pers = request.getParameter("code_pers")+"";
		if(request.getParameter("pers_name")!= null)	pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		
		
		request.setAttribute("code_pers", code_pers);
		request.setAttribute("pers_name", pers_name);
		request.setAttribute("chk", "");
		return (mapping.findForward("sendnotePadAll3"));
	}
	
	/*
	 * 작업명 : Home>자격증>자격증발급 대상검색
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePadAll3	쪽지발송전체
	 */
	public ActionForward sendnotePadAll3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//검색합시다.
		licenseDao dao = new licenseDao(); 
	 		
		if(request.getParameter("yyyy1") != null||request.getParameter("code_certifi1") != null
				||request.getParameter("season1") != null||request.getParameter("oper_name1") != null
				){
		
			/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
			map.put("yyyy1",        		request.getParameter("yyyy1"));				//교육년도
			map.put("code_certifi1",        request.getParameter("code_certifi1"));		//자격증 구분
			map.put("season1",        		request.getParameter("season1"));			//학기
			if( request.getParameter("code_operation1") != null && !request.getParameter("code_operation1").equals("0") )	
				map.put("code_operation1",request.getParameter("code_operation1"));
			
			List<Map> list = new ArrayList();		
			//검색 종료
	
//			List<Map> list=null;
			String chk = request.getParameter("button_ab");
			if(chk.equals("a")){
				list=dao.selectissue(map);
				}
			else{
				list=dao.selectnow(map);
			}
			
			if(list!=null) System.out.println("list사이즈=====>"+list.size());
			
			//전송할 전화번호 
			String code_pers ="";
			String pers_name ="";
			
			for(int i=0; i<list.size(); i++) {
				
				if(!(list.get(i).get("person_yn1")+"").equals("")) {
					if((list.get(i).get("person_yn1")+"").length()>0){
						if(code_pers.length() > 0) code_pers+= ",";
						code_pers += list.get(i).get("person_yn1");
					}
				}
	
				if(!(list.get(i).get("oper_name")+"").equals("")) {
					if((list.get(i).get("oper_name")+"").length()>0){
						if(pers_name.length() > 0) pers_name+= ",";
						pers_name += list.get(i).get("oper_name");
					}
				}
			}
			request.setAttribute("code_pers", code_pers);
			request.setAttribute("pers_name", pers_name);
			request.setAttribute("chk", "ALL");
		}

		return (mapping.findForward("sendnotePadAll3"));
	}
//	==========================================================================================	
	/*
	 * 작업명 : Home>자격증>자격증 갱신
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePad4	쪽지발송
	 */
	public ActionForward sendnotePad4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String code_pers = "";
		String pers_name = "";
		if(request.getParameter("code_pers")!= null)	code_pers = request.getParameter("code_pers")+"";
		if(request.getParameter("pers_name")!= null)	pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		
		
		request.setAttribute("code_pers", code_pers);
		request.setAttribute("pers_name", pers_name);
		request.setAttribute("chk", "");
		return (mapping.findForward("sendnotePadAll4"));
	}
	
	/*
	 * 작업명 : Home>자격증>자격증 갱신
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendnotePadAll4	쪽지발송전체
	 */
	public ActionForward sendnotePadAll4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//검색합시다.
		licenseDao dao = new licenseDao(); 
	 		
		/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
		if(request.getParameter("yyyy1"		)!=null)
			map.put("yyyy1",              Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
		map.put("check", "2");
		
		if(request.getParameter("season1"		)!=null)
			map.put("season1",		request.getParameter("season1"));
    	if(request.getParameter("season2"		)!=null)
    		map.put("season2",		request.getParameter("season2"));
    	if(request.getParameter("code_certifi1")!=null)
    		map.put("code_certifi1",              request.getParameter("code_certifi1"));    	
    	if(request.getParameter("oper_name1"	)!=null)
    		map.put("oper_name1",	request.getParameter("oper_name1"));
    	if(request.getParameter("oper_no1"	)!=null)
    		map.put("oper_no1",		request.getParameter("oper_no1"));
    	if(request.getParameter("oper_lic_no1"	)!=null)
    		map.put("oper_lic_no1",	request.getParameter("oper_lic_no1"));
    	if(request.getParameter("result_no1"	)!=null)
    		map.put("result_no1",	request.getParameter("result_no1"));
    	if(request.getParameter("oper_lic_no1"	)!=null)
    		map.put("oper_lic_no1",	request.getParameter("oper_lic_no1"));
    	if(request.getParameter("print_state1"	)!=null)
    		map.put("print_state1",	request.getParameter("print_state1"));    	
    			
		if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
			map.put("selB", "Y");
			map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
			map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
		}else{
			map.put("selA", "Y");															
		}
		
		List<Map> list = dao.selectrenewal(map);		
		//검색 종료

		//전송할 전화번호 
		String code_pers ="";
		String pers_name ="";
		
		for(int i=0; i<list.size(); i++) {
				
			if(!(list.get(i).get("code_pers")+"").equals("")) {
				if((list.get(i).get("code_pers")+"").length()>0){
					if(code_pers.length() > 0) code_pers+= ",";
					code_pers += list.get(i).get("code_pers");
				}
			}

			if(!(list.get(i).get("oper_name")+"").equals("")) {
				if((list.get(i).get("oper_name")+"").length()>0){
					if(pers_name.length() > 0) pers_name+= ",";
					pers_name += list.get(i).get("oper_name");
				}
			}
		}
		request.setAttribute("code_pers", code_pers);
		request.setAttribute("pers_name", pers_name);
		request.setAttribute("chk", "ALL");


		return (mapping.findForward("sendnotePadAll4"));
	}	
	
	public String getDetcode(List<Map> code,String gc,String detCN) throws SQLException{	
		String detcode="";
		for(int i=0;i<code.size();i++){
			if(gc.equals(code.get(i).get("groupcode"))){
				if(detCN.equals(code.get(i).get("detcodename"))){
					detcode=code.get(i).get("detcode").toString();
				}
			}
		}
		return detcode;
	}
	
	public ActionForward notePad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();    		
    	HttpSession session=request.getSession();     		
    	
    	String dm_pers_code=request.getParameter("dm_pers_code");    	
    	String [] dmpers=dm_pers_code.split(","); 
    	
    	for(int i=0;i<dmpers.length;i++){
    		map.put("dmperscode",                dmpers[i]);
    		String note_oper = URLDecoder.decode(request.getParameter("note_oper"),"UTF-8");
    		String note_title = URLDecoder.decode(request.getParameter("note_title"),"UTF-8");
    		String note_contents = URLDecoder.decode(request.getParameter("note_contents"),"UTF-8");
    		map.put("note_title",                note_title);
    	    map.put("note_contents",             note_contents);   	
    		map.put("note_oper",                 note_oper);
    	    map.put("st_dt",                     request.getParameter("st_dt"));   	
     		map.put("ed_dt",                     request.getParameter("ed_dt"));
    		map.put("register",                  session.getAttribute("G_NAME"));
    		
    		documentDao dao = new documentDao();
    		int n = dao.insertnotePad(map);    		
    	}     	
		return (mapping.findForward("insertnotePad_ok"));				
	}

	/*
	 * 작업명 : Home>자격증>시험별응시현황 
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : sendumsDataAll		문자발송 전체 포워딩
	 */
	public ActionForward smssandAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		if("license1".equals(request.getParameter("from"		))){
			Map map = new HashMap();
			licenseDao dao = new licenseDao();
			
			map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
			map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
			map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수
			map.put("season1",        		request.getParameter("season1"));										//학기
			map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
			map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
			map.put("result_state1",       	request.getParameter("result_state1"));									//상태
			map.put("code_bran1",       	request.getParameter("code_bran1"));									//교육주최
			map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
			map.put("detcode",       	    request.getParameter("edutest_name1"));									//내용
			map.put("oper_name1",       	request.getParameter("oper_name1"));									//이름
			map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
			map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
			map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
			map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석
			map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
			map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
			map.put("receipt_no1",       	request.getParameter("receipt_no1"));									//접수번호
			map.put("sel", "Y");
				
			//전체 개수 검색
			List<Map> count=dao.licensesendcnt(map); 
			
			String ncount = String.valueOf( ((Integer)count.get(0).get("cnt")) );
			
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param = "";
			if(request.getParameter("code_certifi1")!=null)        param+="&code_certifi1="+request.getParameter("code_certifi1");									//구분코드
			if(request.getParameter("yyyy1")!=null)        		param+="&yyyy1="+request.getParameter("yyyy1");											//년도
			if(request.getParameter("operation_cnt1")!=null)       param+="&operation_cnt1="+request.getParameter("operation_cnt1");								//횟수
			if(request.getParameter("season1")!=null)        		param+="&season1="+request.getParameter("season1");										//학기
			if(request.getParameter("code_operation1")!=null)      param+="&code_operation1="+request.getParameter("code_operation1");								//대상자
			if(request.getParameter("oper_state1")!=null)       	param+="&oper_state1="+request.getParameter("oper_state1");									//결제여부
			if(request.getParameter("result_state1")!=null)       	param+="&result_state1="+request.getParameter("result_state1");									//상태
			if(request.getParameter("code_bran1")!=null)       	param+="&code_bran1="+request.getParameter("code_bran1");									//교육주최
			if(request.getParameter("operation_place1")!=null)     param+="&operation_place1="+request.getParameter("operation_place1");	//시험장소
			if(request.getParameter("edutest_name1")!=null)       	    param+="&edutest_name1="+request.getParameter("edutest_name1");									//내용
			if(request.getParameter("oper_name1")!=null)       	param+="&oper_name1="+request.getParameter("oper_name1");									//이름
			if(request.getParameter("oper_lic_no1")!=null)       	param+="&oper_lic_no1="+request.getParameter("oper_lic_no1");									//면허번호
			if(request.getParameter("oper_birth1")!=null)       		param+="&oper_birth1="+request.getParameter("oper_birth1");										//생년월일
			if(request.getParameter("oper_hp1")!=null)       		param+="&oper_hp1="+request.getParameter("oper_hp1");										//핸드폰
			if(request.getParameter("oper_email1")!=null)       	param+="&oper_email1="+request.getParameter("oper_email1");									//이메일
			if(request.getParameter("person_yn1")!=null)       	param+="&person_yn1="+request.getParameter("person_yn1");									//회원구분
			if(request.getParameter("attend_cnt1")!=null)       	param+="&attend_cnt1="+request.getParameter("attend_cnt1");									//출석
			if(request.getParameter("bran_seq1")!=null)       		param+="&bran_seq1="+request.getParameter("bran_seq1");										//지부순번
			if(request.getParameter("code_kind1")!=null)       	param+="&code_kind1="+request.getParameter("code_kind1");									//종류
			if(request.getParameter("code_seq1")!=null)       		param+="&code_seq1="+request.getParameter("code_seq1");										//순번
			if(request.getParameter("receipt_no1")!=null)       	param+="&receipt_no1="+request.getParameter("receipt_no1");									//접수번호
			param+="&from="+request.getParameter("from");
	
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
		}
		else if("license2".equals(request.getParameter("from"		))){
			Map map = new HashMap();
			licenseDao dao = new licenseDao();
			
			map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
			map.put("code_bran1",        	request.getParameter("code_bran1"));									//지부
			map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
			map.put("season1",        		request.getParameter("season1"));										//학기
			map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수		
			map.put("edutest_name1",      	request.getParameter("edutest_name1"));									//시험명
			map.put("print_kind",       	request.getParameter("print_kind1"));									//증서발급
			map.put("finish_point1",       	request.getParameter("finish_point1"));									//합격가능점수
			map.put("finish_time1",       	request.getParameter("finish_time1"));									//이수시간
			map.put("finish_date1",       	request.getParameter("finish_date1"));									//이수일수
			map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
			map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
			map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
			map.put("result_state1",       	request.getParameter("result_state1"));									//상태
			map.put("result_point1",       	request.getParameter("result_point1"));									//시험점수
			map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
			map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
			map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
			map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
			map.put("receipt_no1",     		request.getParameter("receipt_no1"));									//접수번호
			map.put("edutest_name1",     	URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//시험명
			map.put("check",     	 "2");		//시험명
			map.put("sel", "Y");
			if(!request.getParameter("code_operation1").equals("0")) map.put("code_operation1",      request.getParameter("code_operation1"));								
				
			//전체 개수 검색
			List<Map> count=dao.resultsendcnt(map);
			
			String ncount = String.valueOf( ((Integer)count.get(0).get("cnt")) );
			
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param="";
			if(request.getParameter("yyyy1")!=null)param+="&yyyy1="        	+ request.getParameter("yyyy1");											//년도
			if(request.getParameter("code_bran1")!=null)param+="&code_bran1="        + request.getParameter("code_bran1");									//지부
			if(request.getParameter("code_certifi1")!=null)param+="&code_certifi1="        + request.getParameter("code_certifi1");									//구분코드
			if(request.getParameter("season1")!=null)param+="&season1="        	+ request.getParameter("season1");										//학기
			if(request.getParameter("operation_cnt1")!=null)param+="&operation_cnt1="       + request.getParameter("operation_cnt1");								//횟수		
			if(request.getParameter("edutest_name1")!=null)param+="&edutest_name1="      + request.getParameter("edutest_name1");									//시험명
			if(request.getParameter("print_kind")!=null)param+="&print_kind="       + request.getParameter("print_kind1");									//증서발급
			if(request.getParameter("finish_point1")!=null)param+="&finish_point1="       + request.getParameter("finish_point1");									//합격가능점수
			if(request.getParameter("finish_time1")!=null)param+="&finish_time1="       + request.getParameter("finish_time1");									//이수시간
			if(request.getParameter("finish_date1")!=null)param+="&finish_date1="       + request.getParameter("finish_date1");									//이수일수
			if(request.getParameter("operation_place1")!=null)param+="&operation_place1="     + request.getParameter("operation_place1");	//시험장소
			if(request.getParameter("oper_name1")!=null)param+="&oper_name1="       	+ request.getParameter("oper_name1");			//이름
			if(request.getParameter("oper_birth1")!=null)param+="&oper_birth1="       	+ request.getParameter("oper_birth1");										//생년월일
			if(request.getParameter("oper_lic_no1")!=null)param+="&oper_lic_no1="       + request.getParameter("oper_lic_no1");									//면허번호
			if(request.getParameter("attend_cnt1")!=null)param+="&attend_cnt1="       + request.getParameter("attend_cnt1");									//출석일수
			if(request.getParameter("result_state1")!=null)param+="&result_state1="       + request.getParameter("result_state1");									//상태
			if(request.getParameter("result_point1")!=null)param+="&result_point1="       + request.getParameter("result_point1");									//시험점수
			if(request.getParameter("oper_hp1")!=null)param+="&oper_hp1="       	+ request.getParameter("oper_hp1");										//핸드폰
			if(request.getParameter("oper_email1")!=null)param+="&oper_email1="       + request.getParameter("oper_email1");									//이메일
			if(request.getParameter("person_yn1")!=null)param+="&person_yn1="       + request.getParameter("person_yn1");									//회원구분
			if(request.getParameter("code_kind1")!=null)param+="&code_kind1="       + request.getParameter("code_kind1");									//종류
			if(request.getParameter("code_seq1")!=null)param+="&code_seq1="       	+ request.getParameter("code_seq1");										//순번
			if(request.getParameter("bran_seq1")!=null)param+="&bran_seq1="       	+ request.getParameter("bran_seq1");										//지부순번
			if(request.getParameter("receipt_no1")!=null)param+="&receipt_no1="     	+ request.getParameter("receipt_no1");									//접수번호
			if(request.getParameter("edutest_name1")!=null)param+="&edutest_name1="     	+ request.getParameter("edutest_name1");		//시험명
			if(request.getParameter("code_operation1")!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");								
			param+="&from="+request.getParameter("from");
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
		}
		else if("license3".equals(request.getParameter("from"		))){
			Map map = new HashMap();
			licenseDao dao = new licenseDao();
			
			map.put("code_certifi1",        	request.getParameter("code_certifi1"));									//자격증구분
			map.put("yyyy1",        			request.getParameter("yyyy1"));											//년도
			if(!request.getParameter("code_operation1").equals("0")&&!request.getParameter("code_operation1").equals("2")){
				map.put("code_operation1",        	request.getParameter("code_operation1"));								//대상자
			}else if(request.getParameter("code_operation1").equals("2")){
				map.put("code_operation2",        	"Y");	
			}
			map.put("oper_name1",        		URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_birth1",        			request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",        		request.getParameter("oper_lic_no1"));									//면허번호
			map.put("oper_hp1",        			request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",        		request.getParameter("oper_email1"));									//이메일
			map.put("oper_comp_name11",        	request.getParameter("oper_comp_name11"));								//근무처
			map.put("person_yn1",        		request.getParameter("person_yn1"));									//회원구분										
			
			map.put("code_kind1",        		request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",        		request.getParameter("code_seq1"));										//순번
			map.put("code_bran1",        		request.getParameter("code_bran1"));									//지부
			map.put("bran_seq1",        		request.getParameter("bran_seq1"));										//지부순번
			map.put("receipt_no1",        		request.getParameter("receipt_no1"));									//접수번호

			map.put("param1",           request.getParameter("param1"));        //대상자
			map.put("check", "2");
			
			//전체 개수 검색
			List<Map> count=dao.inspectionsendcnt(map);
			
			String ncount = String.valueOf( ((Integer)count.get(0).get("cnt")) );
			
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param="";
			if(request.getParameter("code_certifi1")!=null)param+="&code_certifi1="        	+ request.getParameter("code_certifi1");									//자격증구분
			if(request.getParameter("yyyy1")!=null)param+="&yyyy1="        	+ request.getParameter("yyyy1");											//년도
			if(request.getParameter("code_operation1")!=null)param+="&code_operation1="        	+ request.getParameter("code_operation1");								//대상자
			if(request.getParameter("oper_name1")!=null)param+="&oper_name1="        	+ request.getParameter("oper_name1");			//이름
			if(request.getParameter("oper_birth1")!=null)param+="&oper_birth1="        	+ request.getParameter("oper_birth1");										//생년월일
			if(request.getParameter("oper_lic_no1")!=null)param+="&oper_lic_no1="        	+ request.getParameter("oper_lic_no1");									//면허번호
			if(request.getParameter("oper_hp1")!=null)param+="&oper_hp1="        	+ request.getParameter("oper_hp1");										//핸드폰
			if(request.getParameter("oper_email1")!=null)param+="&oper_email1="        	+ request.getParameter("oper_email1");									//이메일
			if(request.getParameter("oper_comp_name11")!=null)param+="&oper_comp_name11="        	+ request.getParameter("oper_comp_name11");								//근무처
			if(request.getParameter("person_yn1")!=null)param+="&person_yn1="        	+ request.getParameter("person_yn1");									//회원구분										
			if(request.getParameter("code_kind1")!=null)param+="&code_kind1="        	+ request.getParameter("code_kind1");									//종류
			if(request.getParameter("code_seq1")!=null)param+="&code_seq1="        	+ request.getParameter("code_seq1");										//순번
			if(request.getParameter("code_bran1")!=null)param+="&code_bran1="        	+ request.getParameter("code_bran1");									//지부
			if(request.getParameter("bran_seq1")!=null)param+="&bran_seq1="        	+ request.getParameter("bran_seq1");										//지부순번
			if(request.getParameter("receipt_no1")!=null)param+="&receipt_no1="        	+ request.getParameter("receipt_no1");									//접수번호
			if(request.getParameter("param1")!=null)param+="&param1="        	+ request.getParameter("param1");        //대상자
			param+="&from="+request.getParameter("from");
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
		}
		else if("license4".equals(request.getParameter("from"		))){
			Map map = new HashMap();
			licenseDao dao = new licenseDao();
			
			List<String> hp_infos =  null;
			if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
				hp_infos =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
			}
			
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"		));	//자격증 구분
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"				));	//
			if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
				map.put("season1",       		"1");									//반기									//반기
			}else if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("2")){
				map.put("season2",       		"2");									//반기
			} else {									//반기
			}
			if(request.getParameter("code_operation1"	)!=null&&!request.getParameter("code_operation1"	).equals("0"))map.put("code_operation1"	, request.getParameter("code_operation1"	));	//
			if(request.getParameter("button_ab"			)!=null)map.put("button_ab"			, request.getParameter("button_ab"));//대상검색과 발급현황조회구분값
			
			map.put("person_yns", hp_infos);

			String chk = request.getParameter("button_ab");
			
			List<Map> issuesendcnt = null;
			List<Map> nowsendcnt = null;
			String ncount = "0";
			
			//대상검색
			if( "a".equals(chk)) {
				issuesendcnt=dao.issuesendcnt(map);
				ncount = String.valueOf( ((Integer)issuesendcnt.get(0).get("cnt")) );

			}
			//발급현황조회
			if( "b".equals(chk)) {
				nowsendcnt=dao.nowsendcnt(map); 
				ncount = String.valueOf( ((Integer)nowsendcnt.get(0).get("cnt")) );
			}
			
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param="";
			if(request.getParameter("code_certifi1")!=null)param+="&code_certifi1="        	+ request.getParameter("code_certifi1");									//자격증구분
			if(request.getParameter("yyyy1")!=null)param+="&yyyy1="        	+ request.getParameter("yyyy1");											//년도
			if(request.getParameter("code_operation1")!=null)param+="&code_operation1="        	+ request.getParameter("code_operation1");								//대상자
			if(request.getParameter("season1")!=null)param+="&season1="        	+ request.getParameter("season1");		
			if(request.getParameter("button_ab")!=null)param+="&button_ab="        	+ request.getParameter("button_ab"); 
			param+="&from="+request.getParameter("from")+"&hp_infos="+request.getParameter("hp_infos");
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
		}
		else if("license5".equals(request.getParameter("from"		))){
			Map map = new HashMap();
			licenseDao dao = new licenseDao();
			
			List<String> hp_infos =  null;
			if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
				hp_infos =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
			}
			
		    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
			map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
			map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
			map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
			map.put("print_state1",        	request.getParameter("print_state1"));							//상태
			map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
			map.put("check", "2");
//			map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
			map.put("yyyy1", request.getParameter("yyyy1"));

			if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
				map.put("selB", "Y");
				map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
				map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
			}else{
				map.put("selA", "Y");
				map.put("season1",        		request.getParameter("season1"));								//발급반기
				map.put("season2",        		request.getParameter("season2"));								//발급반기											
			}
			
			map.put("person_yns", hp_infos);
			
			List<Map> renewalsendcnt=dao.renewalsendcnt(map); 
			String ncount = String.valueOf( ((Integer)renewalsendcnt.get(0).get("cnt")) );
			
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param="";
			if(request.getParameter("code_certifi1")!=null) param+="&code_certifi1="        	+ request.getParameter("code_certifi1");							//자격증구분		  
			if(request.getParameter("result_no1")!=null) param+="&result_no1="        	+ request.getParameter("result_no1");							//자격증번호
			if(request.getParameter("oper_name1")!=null) param+="&oper_name1="        	+ request.getParameter("oper_name1");							//자격증번호
			if(request.getParameter("oper_birth1")!=null) param+="&oper_birth1="        	+ request.getParameter("oper_birth1");								//생년월일				
			if(request.getParameter("print_state1")!=null) param+="&print_state1="        	+ request.getParameter("print_state1");							
			if(request.getParameter("code_seq1")!=null) param+="&code_seq1="        	+ request.getParameter("code_seq1");							
			if(request.getParameter("yyyy1")!=null) param+="&yyyy1="        	+ request.getParameter("yyyy1");							
			if(request.getParameter("mm1")!=null) param+="&mm1="        	+ request.getParameter("mm1");							
			if(request.getParameter("dd1")!=null) param+="&dd1="        	+ request.getParameter("dd1");							
			if(request.getParameter("season1")!=null) param+="&season1="        	+ request.getParameter("season1");							
			if(request.getParameter("season2")!=null) param+="&season2="        	+ request.getParameter("season2");							
			param+="&from="+request.getParameter("from")+"&hp_infos="+request.getParameter("hp_infos");
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
		}
		
		return (mapping.findForward("umsDataall"));
	}
	
	/*
	 * 작업명 : Home>자격증>시험별응시현황 
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.22
	 * 작   업 : 		문자발송 선택 포워딩
	 */
	public ActionForward smssand(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String param = "";
		if(request.getParameter("param")!= null)
			param = request.getParameter("param")+"";
		
		//System.out.println(pers_name);
		
		if("license_1".equals(request.getParameter("from"		))){
			String hp_infos = "";
			if(request.getParameter("hp_infos")!= null)
				hp_infos = request.getParameter("hp_infos");
			
			param = "&hp_infos="+hp_infos+"&from="+request.getParameter("from"		);
			
			request.setAttribute("ncount", hp_infos.split("__").length);
		}

		request.setAttribute("param", param);
		request.setAttribute("chk", "");
		return (mapping.findForward("umsDataall"));
	}
	
	//문자 개별 발송
	public ActionForward umsData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();    		
    	HttpSession session=request.getSession(); 
    	
    	String subject         =       URLDecoder.decode(request.getParameter("subject"),"UTF-8");
    	String msg_body        =       URLDecoder.decode(request.getParameter("msg_body"),"UTF-8");
    	
    	int str_len = msg_body.length();
    	int strlength = 0;
    	char tempChar[] = new char[str_len];
    	
    	for(int i=0;i<str_len;i++){
    		tempChar[i] = msg_body.charAt(i);
    		if(tempChar[i] < 128){
    			strlength++;
    		}else{
    			strlength +=2;
    		}
    	}
    	
    	String msg_type = strlength <= 80 ? "0" : "5";
    	
    	//예약발송용
    	String request_time = "";
    	if( request.getParameter("yyyyMMdd") != null )
    		request_time = request.getParameter("yyyyMMdd") + " " + request.getParameter("HHmm");   	
    	
    	String pers_hp   = request.getParameter("pers_hp");    	
    	String [] pershp = pers_hp.split(",");
    	String pers_name   = URLDecoder.decode(request.getParameter("pers_name"), "UTF-8");    	
    	String [] persname = pers_name.split(",");
    	
		if("license_1".equals(request.getParameter("from"		))){
			if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
				List<String> hp_infos_org =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
				List<List<String>> hp_infos = new ArrayList<List<String>>();
				
				for(String item : hp_infos_org){
					List<String> hp_info =  Arrays.asList(item.split("_"));
					hp_infos.add(hp_info);
				}
				Map map1 = new HashMap();
				map1.put("receipt_infos", hp_infos);
				
		    	educationDao dao = new educationDao();
		    	List<Map> result= dao.selecteducationBatch(map1,true);
		    	
		    	StringBuffer oper_hpArr = new StringBuffer(); 
		    	StringBuffer oper_nameArr = new StringBuffer(); 
		    	for(int i=0; i<result.size(); i++){
		    		String oper_hp_tmp = (String) result.get(i).get("oper_hp");
		    		String oper_name_tmp = (String) result.get(i).get("oper_name");
		    		if(oper_hp_tmp!=null && !"".equals(oper_hp_tmp)){
		    			if(oper_hpArr.length()>0) oper_hpArr.append(","); oper_hpArr.append(oper_hp_tmp);
		    			if(oper_nameArr.length()>0) oper_nameArr.append(","); oper_nameArr.append(oper_name_tmp);
		    		}
		    	}   
		    	pers_hp   = oper_hpArr.toString();    	
		    	pers_name = oper_nameArr.toString();
		    	pershp = pers_hp.split(","); 
		    	persname = pers_name.split(",");
			}
		}
		
    	int rtn=0;
    	for(int i=0;i<pershp.length;i++){
    		if(pershp[i].length()>9){
    			map.put("dest_phone",                pershp[i]); 
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                request.getParameter("send_phone"));      		
        		map.put("register",                  session.getAttribute("G_NAME"));
        		map.put("etc1",                      session.getAttribute("G_ID"));
        		map.put("etc2",                      session.getAttribute("G_BRAN"));
        		map.put("etc3",                      persname[i]);
        		map.put("umscnt", i+1);
        		licenseDao dao = new licenseDao();

        		//예약발송
        		int n = 0;
        		if( request_time.length() > 0 ) {
    				map.put("request_time", request_time);
        			n = dao.insertLicUmsResultData(map);
        		}else {
        			n = dao.insertLicUmsData(map);
        		}
        		rtn++;
    		}    		
    	}     	
    	String msg=rtn+"건 저장됐습니다.";
		request.setAttribute("msg", msg);
		return (mapping.findForward("umsDataall"));				
	}
	
	public ActionForward umsDataall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();    		
    	HttpSession session=request.getSession(); 
    	licenseDao dao = new licenseDao();    	
    	String from="";
    	if(request.getParameter("from"		)!=null) from=request.getParameter("from");
    	
    	if(request.getParameter("code_bran1"	)!=null)map.put("code_bran1",              request.getParameter("code_bran1"));
    	//if(!request.getParameter("code_kind1"	).equals(""))map.put("code_kind1",              request.getParameter("code_kind1"));
    	if(request.getParameter("season1"		)!=null)map.put("season1",              request.getParameter("season1"));
    	if(request.getParameter("season2"		)!=null)map.put("season2",              request.getParameter("season2"));
    	if(request.getParameter("code_certifi1")!=null)map.put("code_certifi1",              request.getParameter("code_certifi1"));
    	if(request.getParameter("oper_state1")!=null)map.put("oper_state1",              request.getParameter("oper_state1"));
    	if(request.getParameter("result_state1")!=null)map.put("result_state1",              request.getParameter("result_state1"));
    	
    	if(request.getParameter("edutest_name1"	)!=null)map.put("edutest_name1",              request.getParameter("edutest_name1"));
    	if(request.getParameter("oper_name1"	)!=null)map.put("oper_name1",              request.getParameter("oper_name1"));
//    	JUMIN_DEL
//    	if(request.getParameter("oper_no1"	)!=null)map.put("oper_no1",              request.getParameter("oper_no1"));
    	if(request.getParameter("oper_birth1"	)!=null)map.put("oper_birth1",              request.getParameter("oper_birth1"));
    	if(request.getParameter("oper_lic_no1"	)!=null)map.put("oper_lic_no1",              request.getParameter("oper_lic_no1"));
    	if(request.getParameter("result_no1"	)!=null)map.put("result_no1",              request.getParameter("result_no1"));
    	if(request.getParameter("oper_lic_no1"	)!=null)map.put("oper_lic_no1",              request.getParameter("oper_lic_no1"));
    	if(request.getParameter("print_state1"	)!=null)map.put("print_state1",              request.getParameter("print_state1"));    	
    	if(request.getParameter("result_start_dt1")  	!= null )	map.put("result_start_dt1",              request.getParameter("result_start_dt1"));	
    	if(request.getParameter("result_end_dt1")  	!= null )	map.put("result_end_dt1",              request.getParameter("result_end_dt1"));    	 
    	if(request.getParameter("detcode"	)!=null)map.put("detcode",              request.getParameter("detcode"));

		String subject         =       URLDecoder.decode(request.getParameter("subject"),"UTF-8");
    	String msg_body        =       URLDecoder.decode(request.getParameter("msg_body"),"UTF-8");
    	//예약발송용
    	String request_time = "";
    	if( request.getParameter("yyyyMMdd") != null )
    		request_time = request.getParameter("yyyyMMdd") + " " + request.getParameter("HHmm");
    	
    	String G_NAME=session.getAttribute("G_NAME").toString();
    	String G_ID=session.getAttribute("G_ID").toString();
    	String G_BRAN=session.getAttribute("G_BRAN").toString();
    	String send_phone=request.getParameter("send_phone");
    	
    	map.put("nstart", 0);
		map.put("nend", 100000000);
		
		int rtn=0;
		if(from.equals("license1")){
			System.out.println("from===============>license1");
//			if(request.getParameter("yyyy1"		)!=null)map.put("yyyy1",              request.getParameter("yyyy1"));
//			if( request.getParameter("code_operation1") != null && !request.getParameter("code_operation1").equals("0") )	map.put("code_operation1",              request.getParameter("code_operation1"));
//			map.put("sel", "Y");
			
			map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
			map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
			map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수
			map.put("season1",        		request.getParameter("season1"));										//학기
			map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
			map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
			map.put("result_state1",       	request.getParameter("result_state1"));									//상태
			map.put("code_bran1",       	request.getParameter("code_bran1"));									//교육주최
			map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
			map.put("detcode",       	    request.getParameter("edutest_name1"));									//내용
			map.put("oper_name1",       	request.getParameter("oper_name1"));									//이름
			map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
			map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
			map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
			map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석
			map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
			map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
			map.put("receipt_no1",       	request.getParameter("receipt_no1"));									//접수번호
			map.put("sel", "Y");
			
			List<Map> result= dao.selectlicense(map,true);
			rtn=commonSend(result, subject, msg_body, send_phone, G_NAME, G_ID, G_BRAN, request_time);
		}else if(from.equals("license2")){
			System.out.println("from===============>license2");
//			if(request.getParameter("yyyy1"		)!=null)map.put("yyyy1",              request.getParameter("yyyy1"));
//			if( request.getParameter("code_operation1") != null && !request.getParameter("code_operation1").equals("0") )	map.put("code_operation1",              request.getParameter("code_operation1"));
//			map.put("sel", "Y");
			
			map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
			map.put("code_bran1",        	request.getParameter("code_bran1"));									//지부
			map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드
			map.put("season1",        		request.getParameter("season1"));										//학기
			map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수		
			map.put("edutest_name1",      	request.getParameter("edutest_name1"));									//시험명
			map.put("print_kind",       	request.getParameter("print_kind1"));									//증서발급
			map.put("finish_point1",       	request.getParameter("finish_point1"));									//합격가능점수
			map.put("finish_time1",       	request.getParameter("finish_time1"));									//이수시간
			map.put("finish_date1",       	request.getParameter("finish_date1"));									//이수일수
			map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시험장소
			map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
			map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
			map.put("result_state1",       	request.getParameter("result_state1"));									//상태
			map.put("result_point1",       	request.getParameter("result_point1"));									//시험점수
			map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
			map.put("person_yn1",       	request.getParameter("person_yn1"));									//회원구분
			map.put("code_kind1",       	request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
			map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
			map.put("receipt_no1",     		request.getParameter("receipt_no1"));									//접수번호
			map.put("edutest_name1",     	URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//시험명
			map.put("check",     	 "2");		//시험명
			map.put("sel", "Y");
			if(!request.getParameter("code_operation1").equals("0")) map.put("code_operation1",      request.getParameter("code_operation1"));								

			List<Map> result = dao.selectresult(map,true);
			rtn=commonSend(result, subject, msg_body, send_phone, G_NAME, G_ID, G_BRAN, request_time);
		}else if(from.equals("license3")){
			System.out.println("from===============>license3");
//			if(request.getParameter("yyyy1"		)!=null)
//				map.put("yyyy1",              request.getParameter("yyyy1"));
//			if( request.getParameter("code_operation1") != null && !request.getParameter("code_operation1").equals("0") )	map.put("code_operation1",              request.getParameter("code_operation1"));
//			map.put("check", "2");
			
			map.put("code_certifi1",        	request.getParameter("code_certifi1"));									//자격증구분
			map.put("yyyy1",        			request.getParameter("yyyy1"));											//년도
			map.put("code_operation1",        	null);
			map.put("code_operation2",        	null);
			if(!request.getParameter("code_operation1").equals("0")&&!request.getParameter("code_operation1").equals("2")){
				map.put("code_operation1",        	request.getParameter("code_operation1"));								//대상자
			}else if(request.getParameter("code_operation1").equals("2")){
				map.put("code_operation2",        	"Y");	
			}
			map.put("oper_name1",        		URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_birth1",        			request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",        		request.getParameter("oper_lic_no1"));									//면허번호
			map.put("oper_hp1",        			request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",        		request.getParameter("oper_email1"));									//이메일
			map.put("oper_comp_name11",        	request.getParameter("oper_comp_name11"));								//근무처
			map.put("person_yn1",        		request.getParameter("person_yn1"));									//회원구분										
			
			map.put("code_kind1",        		request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",        		request.getParameter("code_seq1"));										//순번
			map.put("code_bran1",        		request.getParameter("code_bran1"));									//지부
			map.put("bran_seq1",        		request.getParameter("bran_seq1"));										//지부순번
			map.put("receipt_no1",        		request.getParameter("receipt_no1"));									//접수번호

			map.put("param1",           request.getParameter("param1"));        //대상자
			map.put("check", "2");
			
			List<Map> result = dao.selectlinspection(map,true);
			rtn=commonSend(result, subject, msg_body, send_phone, G_NAME, G_ID, G_BRAN, request_time);
		}else if(from.equals("license4")){
			System.out.println("from===============>license4");
//			if(request.getParameter("yyyy1"		)!=null)
//				map.put("yyyy1",              request.getParameter("yyyy1"));
//			if( request.getParameter("code_operation1") != null && !request.getParameter("code_operation1").equals("0") )	map.put("code_operation1",              request.getParameter("code_operation1"));
//			map.put("check", "2");
			
			List<String> hp_infos =  null;
			if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
				hp_infos =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
			}
			
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"		));	//자격증 구분
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"				));	//
			map.put("season1",       		null);
			map.put("season2",       		null);
			if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("1")){
				map.put("season1",       		"1");									//반기									//반기
			}else if(request.getParameter("season1")!=null&&request.getParameter("season1").equals("2")){
				map.put("season2",       		"2");									//반기
			} else {									//반기
			}
			if(request.getParameter("code_operation1"	)!=null&&!request.getParameter("code_operation1"	).equals("0"))map.put("code_operation1"	, request.getParameter("code_operation1"	));	//
			if(request.getParameter("button_ab"			)!=null)map.put("button_ab"			, request.getParameter("button_ab"));//대상검색과 발급현황조회구분값
			
			map.put("person_yns", hp_infos);
			
			List<Map> result = new ArrayList();
			if( request.getParameter("button_ab")    !=null){
				if(request.getParameter("button_ab").equals("a")){
					result = dao.selectissue(map,true);
				}else{
					result = dao.selectnow(map,true);
				} 
			}						
			rtn=commonSend(result, subject, msg_body, send_phone, G_NAME, G_ID, G_BRAN, request_time);
		}else if(from.equals("license5")){
			System.out.println("from===============>license5");
//			if(request.getParameter("yyyy1"		)!=null)
//				map.put("yyyy1",              Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
//			map.put("check", "2");
//			
//			if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
//				map.put("selB", "Y");
//				map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
//				map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
//			}else{
//				map.put("selA", "Y");															
//			}
									
			List<String> hp_infos =  null;
			if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
				hp_infos =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
			}

			map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
			map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
			map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
			map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
			map.put("print_state1",        	request.getParameter("print_state1"));							//상태
			map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
			map.put("check", "2");
//			map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
			map.put("yyyy1", request.getParameter("yyyy1"));

			map.put("season1",        		null);								//발급반기
			map.put("season2",        		null);								//발급반기											
			if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
				map.put("selB", "Y");
				map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
				map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
			}else{
				map.put("selA", "Y");
				map.put("season1",        		request.getParameter("season1"));								//발급반기
				map.put("season2",        		request.getParameter("season2"));								//발급반기											
			}

			map.put("person_yns", hp_infos);
			
			List<Map> result = dao.selectrenewal(map,true);
			rtn=commonSend(result, subject, msg_body, send_phone, G_NAME, G_ID, G_BRAN, request_time);
		}		
		String msg=rtn+"건 저장됐습니다.";
		request.setAttribute("msg", msg);
		return (mapping.findForward("umsDataall"));				
	}
	
	public int commonSend(List<Map> result, String subject, String msg_body, String send_phone, String G_NAME, String G_ID, String G_BRAN, String request_time){
		Map map=new HashMap();
    	licenseDao dao = new licenseDao();

		
		int str_len = msg_body.length();
    	int strlength = 0;
    	char tempChar[] = new char[str_len];
    	
    	for(int i=0;i<str_len;i++){
    		tempChar[i] = msg_body.charAt(i);
    		if(tempChar[i] < 128){
    			strlength++;
    		}else{
    			strlength +=2;
    		}
    	}   	
    	
    	String msg_type = strlength <= 80 ? "0" : "5"; 
    	
    	int rtn=0;
    	
    	for(int i=0; i<result.size(); i++){
    		if(result.get(i).get("oper_hp").toString().length()>9){
    			map.put("dest_phone" ,               result.get(i).get("oper_hp"));
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                send_phone);      		
        		map.put("register",                  G_NAME);
        		map.put("etc1",                      G_ID);
        		map.put("etc2",                      G_BRAN);
        		map.put("etc3",                      result.get(i).get("oper_name"));
        		map.put("umscnt", i+1);
        		//예약발송
        		int n = 0;
        		if( request_time.length() > 0 ) {
    				map.put("request_time", request_time);
        			try {
						n = dao.insertLicUmsResultData(map);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}else {
        			try {
						n = dao.insertLicUmsData(map);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		rtn++;
    		}    		
    	}
    	return rtn;
	}
	public ActionForward selectEtestN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_certifi1") != null){
		
		map.put("yyyy1",        		request.getParameter("yyyy1"));											
		map.put("code_bran1",        	request.getParameter("code_bran1"));									
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									
		
		/*3가지 조건에 맞는 교육명 출력*/
		List<Map> selectEtestN = dao.selectEtestN(map); 

		JSONArray j_test=JSONArray.fromObject(selectEtestN);
		request.setAttribute("selectEtestN" , selectEtestN);
		
		request.setAttribute("j_test" , j_test);
		request.setAttribute("etest1", map);
		
		}		
		return (mapping.findForward("resultSendList"));
	}
	
	public ActionForward selectEtestN2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_certifi1") != null){
		
		map.put("yyyy1",        		request.getParameter("yyyy1"));											
		map.put("code_bran1",        	request.getParameter("code_bran1"));									
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									
		
		/*3가지 조건에 맞는 교육명 출력*/
		List<Map> selectEtestN = dao.selectEtestN(map); 

		JSONArray j_test=JSONArray.fromObject(selectEtestN);
		request.setAttribute("selectEtestN" , selectEtestN);
		
		request.setAttribute("j_test" , j_test);
		request.setAttribute("etest1", map);
		
		}		
		return (mapping.findForward("licenseSendList"));
	}
	
	public ActionForward selectEtestN3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		licenseDao dao = new licenseDao(); 

		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_certifi1") != null){
		
		map.put("yyyy1",        		request.getParameter("yyyy1"));											
		map.put("code_bran1",        	request.getParameter("code_bran1"));									
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									
		
		/*3가지 조건에 맞는 교육명 출력*/
		List<Map> selectEtestN = dao.selectEtestN(map); 

		JSONArray j_test=JSONArray.fromObject(selectEtestN);
		request.setAttribute("selectEtestN" , selectEtestN);
		
		request.setAttribute("j_test" , j_test);
		request.setAttribute("etest1", map);
		
		}		
		return (mapping.findForward("inspectionSandList"));
	}
	
	public ActionForward licenseNoCreate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		
		Map map = new HashMap();
		
		String iCertifi1=request.getParameter("code_certifi1");
		String yyyy1=request.getParameter("yyyy1");
		String season1=request.getParameter("season1");
		String code_operation1=request.getParameter("code_operation1");
		
		System.out.println(iCertifi1+"  "+yyyy1+"  "+season1);
		
		map.put("iCertifi", iCertifi1   );	
		map.put("iYyyy",    yyyy1);																					
		map.put("iSeason",   season1);									
		int procReturn=0;
		map.put("procReturn",procReturn);
		licenseDao dao = new licenseDao(); 
 		
		Map selectexampoint = dao.callLicenseNoCreat_STR(map);
		System.out.println("procReturn===========>"+selectexampoint);
		
		request.setAttribute("code_certifi1", StringUtil.NVL(iCertifi1));
		request.setAttribute("yyyy1", StringUtil.NVL(yyyy1));
		request.setAttribute("season1", StringUtil.NVL(season1));
		request.setAttribute("code_operation1", StringUtil.NVL(code_operation1));
		request.setAttribute("msg", "자격증번호가 생성 되었습니다.");

		return mapping.findForward("issueSand");//저장후 저장한값을 화면에 다시 표출해야한다
	}
	
	public ActionForward licenseRenew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		
		Map map = new HashMap();

		map.put("iCertifi",    request.getParameter("code_certifi1"));	
//		map.put("iYyyy",        	Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));																					
		map.put("iYyyy",        	request.getParameter("yyyy1"));																					
		map.put("iSeason",        	Integer.parseInt(request.getParameter("season1")));									
		int procReturn=0;
		map.put("procReturn",procReturn);
		licenseDao dao = new licenseDao(); 
 		
		Map selectexampoint = dao.callRenewal_STR(map);
		System.out.println("procReturn===========>"+selectexampoint);


		String iCertifi1=request.getParameter("code_certifi1");
		String yyyy1=request.getParameter("yyyy1");
		String season1=request.getParameter("season1");
		if(selectexampoint!=null){
			procReturn =Integer.parseInt(StringUtil.NVL(String.valueOf(selectexampoint.get("procReturn")),"0"));
		}
		
		request.setAttribute("code_certifi1", StringUtil.NVL(iCertifi1));
		request.setAttribute("yyyy1", StringUtil.NVL(yyyy1));
		request.setAttribute("season1", StringUtil.NVL(season1));
		request.setAttribute("save_msg", String.valueOf(procReturn)+ "건 갱신 되었습니다.");

		return mapping.findForward("renewalSandList");//저장후 저장한값을 화면에 다시 표출해야한다
	}
	
	public ActionForward fDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("download"));
	}
	
	public ActionForward fDownAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<HashMap<String,String>> filenames = new ArrayList<HashMap<String,String>>();
		String zipfilename = null;
		String zipfilefullpath = null;
		String yyyy = StringUtil.NVL(request.getParameter("yyyy"));
		
		if(!"".equals(StringUtil.NVL(request.getParameter("file_infos")))){
			List<String> file_infos_org =  Arrays.asList(request.getParameter("file_infos"	).split("__"));
			List<List<String>> file_infos = new ArrayList<List<String>>();
			
			for(String item : file_infos_org){
				List<String> file_info =  Arrays.asList(item.split("_"));
				file_infos.add(file_info);
			}
			
			Map map = new HashMap();
			map.put("file_infos", file_infos);
			
			licenseDao dao = new licenseDao();
	    	List<Map> result= dao.selectCFilesBatch(map);
	    	filenames = getFilenames(result);
		} else {
			Map map = new HashMap(); 
			
			map.put("code_certifi1",        	request.getParameter("code_certifi1"));									//자격증구분
			map.put("yyyy1",        			request.getParameter("yyyy1"));											//년도
			if(!request.getParameter("code_operation1").equals("0")&&!request.getParameter("code_operation1").equals("2")){
				map.put("code_operation1",        	request.getParameter("code_operation1"));								//대상자
			}else if(request.getParameter("code_operation1").equals("2")){
				map.put("code_operation2",        	"Y");	
			}
			map.put("oper_name1",        		URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			map.put("oper_birth1",        			request.getParameter("oper_birth1"));										//생년월일
			map.put("oper_lic_no1",        		request.getParameter("oper_lic_no1"));									//면허번호
			map.put("oper_hp1",        			request.getParameter("oper_hp1"));										//핸드폰
			map.put("oper_email1",        		request.getParameter("oper_email1"));									//이메일
			map.put("oper_comp_name11",        	request.getParameter("oper_comp_name11"));								//근무처
			map.put("person_yn1",        		request.getParameter("person_yn1"));									//회원구분										
			
			map.put("code_kind1",        		request.getParameter("code_kind1"));									//종류
			map.put("code_seq1",        		request.getParameter("code_seq1"));										//순번
			map.put("code_bran1",        		request.getParameter("code_bran1"));									//지부
			map.put("bran_seq1",        		request.getParameter("bran_seq1"));										//지부순번
			map.put("receipt_no1",        		request.getParameter("receipt_no1"));									//접수번호

			map.put("param1",           request.getParameter("param1"));        //대상자
			map.put("check", "2");

			map.put("nstart", 0);
			map.put("nend", 1000000);
			
			licenseDao dao = new licenseDao();
			List<Map> result = dao.selectlinspection(map,true);
	    	
	    	for(int i=0; i<result.size(); i++){
				String code_kind = (String) result.get(i).get("code_kind"); 
				String code_certifi = (String) result.get(i).get("code_certifi"); 
				String code_seq = (String) result.get(i).get("code_seq");
				String code_bran = (String) result.get(i).get("code_bran");
				String bran_seq = (String) result.get(i).get("bran_seq");
				String receipt_no = (String) result.get(i).get("receipt_no"); 
				
	    		List<List<String>> file_infos = new ArrayList<List<String>>();
				List<String> file_info =  new ArrayList<String>();
				
				file_info.add(code_kind);
				file_info.add(code_certifi);
				file_info.add(code_seq);
				file_info.add(code_bran);
				file_info.add(bran_seq);
				file_info.add(receipt_no);
				file_infos.add(file_info);
				
				Map map1 = new HashMap();
				map1.put("file_infos", file_infos);
				
		    	List<Map> result1= dao.selectCFilesBatch(map1);
		    	filenames.addAll(getFilenames(result1));
	    	}   
		}

		try {
			String zipFolderFullPath = createZipFolder(filenames);
			zipFolderFullPath = zipFolderFullPath.substring(0,zipFolderFullPath.length()-1);
			if(zipFolderFullPath!=null){
				String zipFileName = yyyy + "_LicDownAll_"+zipFolderFullPath.substring(zipFolderFullPath.lastIndexOf("/")+1);
				String resultZipFileFullPath = createZipFile(zipFolderFullPath,zipFileName);
				zipfilefullpath = resultZipFileFullPath;
				zipfilename = zipFileName+".zip";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
		}
		
		request.setAttribute("fullfilepath", zipfilefullpath);
		request.setAttribute("filename", zipfilename);
		return (mapping.findForward("download_all"));
	}
	
	private String getFileParams(String code_operation) throws Exception{
		String result = "";
		
		String code = StringUtil.NVL(code_operation);
		
		if(code.equals("1")){
			result="21 22 23 24 25";
		}else if(code.equals("3")){
			result="41 42 43 44 45 46 47";
		}else if(code.equals("4")){
			result="51 52 53 54 55";
		}else if(code.equals("6")){				
			result="71 72 73 74 75 76";
		}else if(code.equals("7")){
			result="81 82 83 84 85";
		}else if(code.equals("8")){
			result="91 92 93 94";
		}
		return result;
	}
	private List<HashMap<String,String>> getFilenames(List<Map> list) throws Exception{
		List<HashMap<String,String>> filenames = new ArrayList<HashMap<String,String>>();
		if(list !=null) {
	    	for(int i=0; i<list.size(); i++){
	    		String add_file_no = StringUtil.NVL((String) list.get(i).get("add_file_no"));
	    		String chang_file_name = StringUtil.NVL((String) list.get(i).get("chang_file_name"));
	    		String extension = chang_file_name.substring(chang_file_name.lastIndexOf(".")>=0?chang_file_name.lastIndexOf("."):chang_file_name.length());
	    		String detcode = StringUtil.NVL((String) list.get(i).get("detcode"));
	    		String oper_name = StringUtil.NVL((String) list.get(i).get("oper_name"));
	    		String oper_birth = StringUtil.NVL((String) list.get(i).get("oper_birth"));
	    		String oper_lic_no = StringUtil.NVL((String) list.get(i).get("oper_lic_no"));
	    		String code_operation = StringUtil.NVL((String) list.get(i).get("code_operation"));
	    		String codeoperation = StringUtil.NVL((String) list.get(i).get("codeoperation"));
	    		
	    		String new_filename_base = detcode+"_"+oper_name+"("+oper_lic_no+")_"+codeoperation;
	    		String new_filename = "";
	    		
	    		if(chang_file_name!=null && !"".equals(chang_file_name)){
		    		if(code_operation.equals("1")){
		    			if(add_file_no.equals("22")){ new_filename = new_filename_base + "_PIC" + extension; }
		    			else if(add_file_no.equals("28")){ new_filename = new_filename_base + "_ZIP" + extension; }
		    		}
		    		else if(code_operation.equals("3")){
		    			if(add_file_no.equals("42")){ new_filename = new_filename_base + "_PIC" + extension; }
		    			else if(add_file_no.equals("48")){ new_filename = new_filename_base + "_ZIP" + extension; }
		    		}
		    		else if(code_operation.equals("4")){ 
		    			if(add_file_no.equals("52")){ new_filename = new_filename_base + "_PIC" + extension; }
		    			else if(add_file_no.equals("58")){ new_filename = new_filename_base + "_ZIP" + extension; }
		    		}
		    		else if(code_operation.equals("6")){ 
		    			if(add_file_no.equals("72")){ new_filename = new_filename_base + "_PIC" + extension; }
		    			else if(add_file_no.equals("78")){ new_filename = new_filename_base + "_ZIP" + extension; }
		    		}
		    		else if(code_operation.equals("7")){ 
		    			if(add_file_no.equals("82")){ new_filename = new_filename_base + "_PIC" + extension; }
		    			else if(add_file_no.equals("88")){ new_filename = new_filename_base + "_ZIP" + extension; }
		    		}
		    		else if(code_operation.equals("8")){ 
		    			if(add_file_no.equals("92")){ new_filename = new_filename_base + "_PIC" + extension; }
		    			else if(add_file_no.equals("98")){ new_filename = new_filename_base + "_ZIP" + extension; }
		    		}
	
		    		if(!"".equals(new_filename)){
			    		HashMap<String,String> filename = new HashMap<String,String>();
			    		filename.put("new_filename", new_filename);
			    		filename.put("curr_filename", chang_file_name);
						filenames.add(filename);
		    		}
		    		
	    		} // if 파일존재
	    		
	    	} // for 파일목록 만큼
		}
		return filenames;
	}
	
	private List<HashMap<String,String>> getFilenamesRenew(List<Map> list) throws Exception{
		List<HashMap<String,String>> filenames = new ArrayList<HashMap<String,String>>();
		if(list !=null) {
	    	for(int i=0; i<list.size(); i++){
	    		String add_file_2 = StringUtil.NVL((String) list.get(i).get("add_file_2"));
	    		String add_file_5 = StringUtil.NVL((String) list.get(i).get("add_file_5"));
	    		
	    		String extension_2 = add_file_2.substring(add_file_2.lastIndexOf(".")>=0?add_file_2.lastIndexOf("."):add_file_2.length());
	    		String extension_5 = add_file_5.substring(add_file_5.lastIndexOf(".")>=0?add_file_5.lastIndexOf("."):add_file_5.length());
	    		String detcode = StringUtil.NVL((String) list.get(i).get("detcode"));
	    		String pers_name = StringUtil.NVL((String) list.get(i).get("pers_name"));
	    		String pers_birth = StringUtil.NVL((String) list.get(i).get("pers_birth"));
	    		String pers_lic_no = StringUtil.NVL((String) list.get(i).get("lic_no"));
	    		
	    		String new_filename_base = detcode+"_"+pers_name+"("+pers_lic_no+")";
	    		String new_filename_2 = "";
	    		String new_filename_5 = "";
	    		
	    		if(add_file_2!=null && !"".equals(add_file_2)){
	    			new_filename_2 = new_filename_base + "_PIC" + extension_2;
	
		    		if(!"".equals(new_filename_2)){
			    		HashMap<String,String> filename = new HashMap<String,String>();
			    		filename.put("new_filename", new_filename_2);
			    		filename.put("curr_filename", add_file_2);
						filenames.add(filename);
		    		}
		    		
	    		} // if 파일존재
	    		
	    		if(add_file_5!=null && !"".equals(add_file_5)){
	    			new_filename_5 = new_filename_base + "_ZIP" + extension_5;
	    			
	    			if(!"".equals(new_filename_5)){
	    				HashMap<String,String> filename = new HashMap<String,String>();
	    				filename.put("new_filename", new_filename_5);
	    				filename.put("curr_filename", add_file_5);
	    				filenames.add(filename);
	    			}
	    			
	    		} // if 파일존재
	    		
	    		
	    	} // for 파일목록 만큼
		}
		return filenames;
	}
	
	private String createZipFolder(List<HashMap<String,String>> filenames) throws Exception{
		return createZipFolder(filenames,null);
	}
		
	private String createZipFolder(List<HashMap<String,String>> filenames,String renewFlag) throws Exception{
		String result = null;
		String baseSaveFolder= "RENEW".equals(renewFlag) ?
				"D:/WEB/dietitian.or.kr_ver3/upload/license_renewal/":
				"D:/WEB/dietitian.or.kr_ver3/upload/license_inspection/";
		
		Calendar cal = Calendar.getInstance();
		String zipFolderFullPath = baseSaveFolder + String.valueOf(cal.getTimeInMillis()) +"/";
		File zipFolder =  null;
		
		try {
			zipFolder =  new File(zipFolderFullPath);
			zipFolder.deleteOnExit();
			zipFolder.mkdirs();
			
			for(HashMap<String,String> filename : filenames){
				String inFileFullPath = baseSaveFolder + filename.get("curr_filename");
				String outFileFullPath = zipFolderFullPath + filename.get("new_filename");
				try {
					copyFile(inFileFullPath,outFileFullPath );
				}catch(Exception ex){
				}
			}
			result = zipFolderFullPath;
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
		}
		return result;
	}
	
	private boolean copyFile(String inFileFullPath, String outFileFullPath)throws IOException {
		boolean result = false;
		
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcin =  null;
		FileChannel fcout = null;
		
		try{
			inputStream = new FileInputStream(inFileFullPath);
			outputStream = new FileOutputStream(outFileFullPath);
			fcin =  inputStream.getChannel();
			fcout = outputStream.getChannel();
			long size = fcin.size();    
			fcin.transferTo(0, size, fcout);
			result = true;
		}catch(IOException ex){
			ex.printStackTrace();
		} finally {
			if(fcin!=null && fcin.isOpen()) fcin.close();
			if(fcout!=null && fcout.isOpen()) fcin.close();
			if(inputStream!=null ) outputStream.close();
			if(outputStream!=null ) inputStream.close();
		}
		return result;
	}
	
	private String createZipFile(String zipFolder, String zipName)throws IOException{
		int COMPRESSION_LEVEL = 8; 
		int BUFFER_SIZE = 64*1024;
		
		String result = null;
		
		FileInputStream finput = null;
        FileOutputStream foutput;
        int cnt;
        byte[] buffer = new byte[BUFFER_SIZE];
        /*
         **********************************************************************************
         * java.util.zip.ZipOutputStream을 사용하면 압축시 한글 파일명은 깨지는 버그 방지
         *********************************************************************************/
        net.sf.jazzlib.ZipOutputStream zoutput;
        
		// 압축할 폴더를 파일 객체로 생성한다.
        File file = new File(zipFolder);
        //폴더 안에 있는 파일들을 파일 배열 객체로 가져온다.
        File[] fileArray = file.listFiles();
        
        
        String zipFileFullName = zipFolder+File.separator+ zipName + ".zip";
       
        // Zip 파일을 만든다.
        File zipFile = new File(zipFileFullName);
        // Zip 파일 객체를 출력 스트림에 넣는다. 
        foutput = new FileOutputStream(zipFile); 

        // 집출력 스트림에 집파일을 넣는다.
        zoutput = new net.sf.jazzlib.ZipOutputStream((OutputStream)foutput);

        net.sf.jazzlib.ZipEntry zentry = null;
        
        //압축시작
        try {
            for (int i=0; i < fileArray.length; i++) {
                // 압축할 파일 배열 중 하나를 꺼내서 입력 스트림에 넣는다.
                finput = new FileInputStream(fileArray[i]);

//                zentry = new net.sf.jazzlib.ZipEntry(new String(fileArray[i].getName().getBytes("ms949"),"utf-8"));
                zentry = new net.sf.jazzlib.ZipEntry(fileArray[i].getName());

                //System.out.println("Target File Name for Compression : "  + fileArray[i].getName()  + ", File Size : "+ finput.available());

                zoutput.putNextEntry(zentry);
                /*
                 ****************************************************************
                 * 압축 레벨을 정하는것인데 9는 가장 높은 압축률
                 *  디폴트는 8입니다.
                 *****************************************************************/
                zoutput.setLevel(COMPRESSION_LEVEL);

                cnt = 0;
                while ((cnt = finput.read(buffer)) != -1) {
                    zoutput.write(buffer, 0, cnt);
                }

                finput.close();
                zoutput.closeEntry();
            }
            zoutput.close();
            foutput.close();
        } catch (Exception e) {
            System.out.println("Compression Error : " + e.toString());
            /*
             **********************************************
             * 압축이 실패했을 경우 압축 파일을 삭제한다.
             ***********************************************/
            System.out.println(zipFile.toString() + " : 압축이 실패하여 파일을 삭제합니다...");
            if (!zipFile.delete()) {
            	System.out.println(zipFile.toString() + " : 파일 삭제가 실패하여 다시 삭제합니다...");
                while(!zipFile.delete()) {
                	System.out.println(zipFile.toString() + " : 삭제가 실패하여 다시 삭제합니다....");
                }
            }
            e.printStackTrace();
            result = null;
        } finally {
            if (finput != null) {
                finput.close();
            }
            if (zoutput != null) {
                zoutput.close();
            }
            if (foutput != null) {
                foutput.close();
            }
            /***********zip파일 상위폴더로 이동**************/
			//FileCopy fileCopy = new FileCopy();
			String fFile = zipFileFullName;
			String cFile = file.getParent().toString()+"/zip_temp/"+zipName + ".zip";
			String cFolder = file.getParent().toString()+"/zip_temp/";
			
			File cFolderDel = new File(cFolder);
			
			if(!cFolderDel.exists()) cFolderDel.mkdirs();
			
//			if(cFolderDel.isDirectory()){
//				File[] childFile = cFolderDel.listFiles();
//				int len = childFile.length;
//				//폴더안의 파일들 삭제
//				for(int i=0; i<len; i++){
//					if(!childFile[i].delete()){
//						System.err.println("파일삭제 실패: "+childFile[i]);
//					}
//				}
//			}
			
			/**
				fileCopy.copyFile(fFile,cFile)
				- fFile:카피될 파일 전체 패스
			    - cFile: 카피된 파일 전체 패스	
			*/
			copyFile(fFile,cFile);
			//System.out.println("file_Name: "+file.toString());
			//System.out.println("isfile: "+file.isDirectory());
			
			if(file.isDirectory()){
				File[] childFile = file.listFiles();
				int len = childFile.length;
				//폴더안의 파일들 삭제
				for(int i=0; i<len; i++){
					if(!childFile[i].delete()){
						System.err.println("파일삭제 실패: "+childFile[i]);
					}
				}
				//폴더삭제
				if (!file.delete()) {
					System.err.println("폴더삭제 실패: "+file);
				}
			}			
//            /*************파일 이동**************/ KOY
//			/**zip 파일 확장자 때어내기**/
//		    String reName = file.getParent().toString()+File.separator+zipName;
//		    //System.out.println("cFile: "+cFile);
//		    //System.out.println("reName: "+reName);
//		    File upFile = new File(cFile); 
//			File reFile = new File(reName);
//			
//		    if (!upFile.renameTo(reFile)) {
//		      System.err.println("이름 변경 에러 : " + upFile);
//		    }
			result = cFile;
        }
        
		return result;
	}
	
	public ActionForward fDown1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("download1"));
	}
	
	public ActionForward fDown1All(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<HashMap<String,String>> filenames = new ArrayList<HashMap<String,String>>();
		String zipfilename = null;
		String zipfilefullpath = null;
		String yyyy = StringUtil.NVL(request.getParameter("yyyy"));
		
		Map map = new HashMap();
		licenseDao dao = new licenseDao();
		
		List<String> file_infos =  null;
		if(!"".equals(StringUtil.NVL(request.getParameter("file_infos")))){
			file_infos =  Arrays.asList(request.getParameter("file_infos"	).split("__"));
		}
		
	    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
		map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
		map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
		map.put("print_state1",        	request.getParameter("print_state1"));							//상태
		map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
		map.put("check", "2");
//		map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
		map.put("yyyy1", request.getParameter("yyyy1"));

		if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
			map.put("selB", "Y");
			map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
			map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
		}else{
			map.put("selA", "Y");
			map.put("season1",        		request.getParameter("season1"));								//발급반기
			map.put("season2",        		request.getParameter("season2"));								//발급반기											
		}
		
		map.put("person_yns", file_infos);

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		List<Map> result = dao.selectrenewal(map);
    	
    	for(int i=0; i<result.size(); i++){

    		String code_pers1 = (String) result.get(i).get("code_pers"); 
			String code_certifi1 = (String) result.get(i).get("code_certifi_c"); 
			String code_seq1 = (String) result.get(i).get("code_seq");
			String result_no1 = (String) result.get(i).get("result_no");
			
			Map map1 = new HashMap();
			map1.put("code_pers1", code_pers1);
			map1.put("code_certifi1", code_certifi1);
			map1.put("code_seq1", code_seq1);
			map1.put("result_no1", result_no1);
			
			List<Map> result1=dao.selectBFiles(map1);			
	    	filenames.addAll(getFilenamesRenew(result1));
    	}   

		try {
			String zipFolderFullPath = createZipFolder(filenames,"RENEW");
			zipFolderFullPath = zipFolderFullPath.substring(0,zipFolderFullPath.length()-1);
			if(zipFolderFullPath!=null){
				String zipFileName = yyyy + "_LicRenewDownAll_"+zipFolderFullPath.substring(zipFolderFullPath.lastIndexOf("/")+1);
				String resultZipFileFullPath = createZipFile(zipFolderFullPath,zipFileName);
				zipfilefullpath = resultZipFileFullPath;
				zipfilename = zipFileName+".zip";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
		}
		
		request.setAttribute("fullfilepath", zipfilefullpath);
		request.setAttribute("filename", zipfilename);
		return (mapping.findForward("download_all"));
	}

	public ActionForward licenserenewalListSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		Map map = new HashMap();
		
		if(request.getParameter("add_file_1").equals("Y")) 
			map.put("add_file_11", "Y");
		else
			map.put("add_file_11", "");
		if(request.getParameter("add_file_3").equals("Y")) 
			map.put("add_file_31", "Y");
		else
			map.put("add_file_31", "");
		if(request.getParameter("add_file_4").equals("Y")) 
			map.put("add_file_41", "Y");
		else
			map.put("add_file_41", ""); 
		map.put("yyyy1",        		request.getParameter("yyyy1"));											
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									
		map.put("code_pers1",        	request.getParameter("code_pers1"));									
		map.put("result_no1",        	request.getParameter("result_no1"));
		map.put("code_seq1",        	request.getParameter("code_seq1"));										//순번(key)
		map.put("result_start_dt1",     request.getParameter("result_start_dt1"));									
		map.put("result_end_dt1",       request.getParameter("result_end_dt1"));										
		map.put("print_state1",       	request.getParameter("print_state1"));	
		map.put("season1",				request.getParameter("season1"));
		
		map.put("oper_name1", StringUtil.nullToStr("", request.getParameter("oper_name1")));
//		JUMIN_DEL
//		map.put("oper_no11", StringUtil.nullToStr("", request.getParameter("oper_no11")));
//		map.put("oper_no12", StringUtil.nullToStr("", request.getParameter("oper_no12")));
		map.put("oper_birth1", StringUtil.nullToStr("", request.getParameter("oper_birth1")));
		map.put("oper_lic_no1", StringUtil.nullToStr("", request.getParameter("oper_lic_no1")));
		
		map.put("add_file_1", request.getParameter("add_file_1"));
		map.put("add_file_2", request.getParameter("add_file_2"));
		map.put("add_file_3", request.getParameter("add_file_3"));
		map.put("add_file_4", request.getParameter("add_file_4"));
		map.put("add_file_5", request.getParameter("add_file_5"));
		//jsp 넘긴 항목이랑 같은 갯수로 값을 받아야 한다.
		/*educationDao dao1 = new educationDao(); */
		licenseDao   dao = new licenseDao(); 

		int updatlicenserenewalList = dao.updatlicenserenewalList(map);
		
		request.setAttribute("etest2", map);
		request.setAttribute("msg", "저장됐습니다.");
		return mapping.findForward("renewalSandList");//저장후 저장한값을 화면에 다시 표출해야한다
	}
	
	public ActionForward licenserenewalListSaveBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();
		licenseDao dao = new licenseDao();
		
		List<String> oper_infos =  null;
		if(!"".equals(StringUtil.NVL(request.getParameter("oper_infos")))){
			oper_infos =  Arrays.asList(request.getParameter("oper_infos"	).split("__"));
		}
		
	    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
		map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
		map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
		map.put("print_state1",        	request.getParameter("print_state1"));							//상태
		map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
		map.put("check", "2");
//		map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
		map.put("yyyy1", request.getParameter("yyyy1"));

		if(request.getParameter("print_state1").equals("0")||request.getParameter("print_state1").equals("4")||request.getParameter("print_state1").equals("5")){
			map.put("selB", "Y");
			map.put("mm1", StringUtil.nullToStr("", request.getParameter("mm1")));
			map.put("dd1", StringUtil.nullToStr("", request.getParameter("dd1")));
		}else{
			map.put("selA", "Y");
			map.put("season1",        		request.getParameter("season1"));								//발급반기
			map.put("season2",        		request.getParameter("season2"));								//발급반기											
		}
		
		map.put("person_yns", oper_infos);

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		List<Map> result = dao.selectrenewal(map);
    	
    	for(int i=0; i<result.size(); i++){
			String code_certifi1 = (String) result.get(i).get("code_certifi_c"); 
			String code_pers1 = (String) result.get(i).get("code_pers");
			String result_no1 = (String) result.get(i).get("result_no");
			String code_seq1 = (String) result.get(i).get("code_seq");
			
			Map map1 = new HashMap();
    		map1.put("code_certifi1",        code_certifi1);									
    		map1.put("code_pers1",        	code_pers1);									
    		map1.put("result_no1",        	result_no1);
    		map1.put("code_seq1",        	code_seq1);
    		
			map1.put("result_start_dt1",     request.getParameter("result_start_dt1"));									
			map1.put("result_end_dt1",       request.getParameter("result_end_dt1"));										
			map1.put("print_state1",       	request.getParameter("print_state1"));	
    		
			map1.put("add_file_11", "Y");
			map1.put("add_file_31", "Y");
			map1.put("add_file_41", "Y");
			
			int updatlicenserenewalList = dao.updatlicenserenewalList(map1);
    	}   
    	
		request.setAttribute("code_certifi1", StringUtil.NVL(request.getParameter("code_certifi1")));
		request.setAttribute("yyyy1", StringUtil.NVL(request.getParameter("yyyy1")));
		request.setAttribute("season1", StringUtil.NVL(request.getParameter("season1"))+StringUtil.NVL(request.getParameter("season2")));
		request.setAttribute("print_state1", StringUtil.NVL(request.getParameter("print_state1")));
		request.setAttribute("save_msg", "일괄처리 되었습니다.");
		return mapping.findForward("renewalSandList");//저장후 저장한값을 화면에 다시 표출해야한다
	}
	
	public ActionForward printStateChg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		licenseDao   dao = new licenseDao();
//		if(request.getParameter("yyyy1"	)!=null)
//			map.put("yyyy1",              request.getParameter("yyyy1"));
//		if(request.getParameter("code_certifi1"	)!=null)
//			map.put("code_certifi1",              request.getParameter("code_certifi1"));
//		if(request.getParameter("code_operation1"	)!=null&&!request.getParameter("code_operation1"	).equals("0"))
//			map.put("code_operation1",              request.getParameter("code_operation1"));
//		if(request.getParameter("season1"	)!=null)
//			map.put("season1",              request.getParameter("season1"));
//		if(request.getParameter("season2"	)!=null)
//			map.put("season2",              request.getParameter("season2"));
		if(request.getParameter("sel")==null){
			List<Map> selectPrint=dao.selectPrint(map);
			for(int i=0;i<selectPrint.size();i++){
				map.put("code_pers", selectPrint.get(i).get("code_pers"));
				map.put("code_certifi", request.getParameter("code_certifi"));
				map.put("code_seq", "01");
				map.put("result_no", selectPrint.get(i).get("result_no"));
				int uPstate=dao.uPstate(map);
			}			
		}else{
			map.put("code_certifi", request.getParameter("code_certifi"));
			map.put("code_seq", "01");
			
			String temp1="";
			String temp2="";
			String code_pers[];
			String result_no[];
			if(request.getParameter("code_pers"	)!=null)
				temp1=request.getParameter("code_pers"	);
			if(request.getParameter("result_no"	)!=null)
				temp2=request.getParameter("result_no"	);
			
			code_pers=temp1.split(",");
			result_no=temp2.split(",");
			int len=code_pers.length;
			if(len!=0){
				for(int i=0;i<len;i++){
					map.put("code_pers", code_pers[i]);
					map.put("result_no", result_no[i]);
					int uPstate=dao.uPstate(map);
				}
			}			
		}			

		return mapping.findForward("issueSandList");
	}
	
	public ActionForward printStateChg1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		licenseDao   dao = new licenseDao();
		if(request.getParameter("yyyy1"	)!=null)
			map.put("yyyy1",              request.getParameter("yyyy1"));
		if(request.getParameter("code_certifi1"	)!=null)
			map.put("code_certifi1",              request.getParameter("code_certifi1"));		
		if(request.getParameter("season1"	)!=null)
			map.put("season1",              "Y");
		if(request.getParameter("season2"	)!=null)
			map.put("season2",              "Y");
		if(request.getParameter("sel")==null){
			List<Map> selectPrint=dao.selectPrint1(map);
			for(int i=0;i<selectPrint.size();i++){
				map.put("code_pers", selectPrint.get(i).get("code_pers"));
				map.put("code_certifi", request.getParameter("code_certifi1"));
				map.put("code_seq", selectPrint.get(i).get("code_seq"));
				map.put("result_no", selectPrint.get(i).get("result_no"));
				int uPstate=dao.uPstate(map);
			}			
		}else{
			map.put("code_certifi", request.getParameter("code_certifi1"));			
			
			String temp1="";
			String temp2="";
			String temp3="";
			String code_pers[];
			String result_no[];
			String code_seq[];
			if(request.getParameter("code_pers"	)!=null)
				temp1=request.getParameter("code_pers"	);
			if(request.getParameter("result_no"	)!=null)
				temp2=request.getParameter("result_no"	);
			if(request.getParameter("code_seq"	)!=null)
				temp3=request.getParameter("code_seq"	);
			code_pers=temp1.split(",");
			result_no=temp2.split(",");
			code_seq =temp3.split(",");
			int len=code_pers.length;
			if(len!=0){
				for(int i=0;i<len;i++){
					map.put("code_pers", code_pers[i]);
					map.put("result_no", result_no[i]);
					map.put("code_seq",  code_seq[i]);
					int uPstate=dao.uPstate(map);					
				}
			}			
		}			

		return mapping.findForward("renewalSandList");
	}
}
	
	




