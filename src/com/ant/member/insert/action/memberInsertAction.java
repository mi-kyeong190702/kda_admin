package com.ant.member.insert.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ant.common.code.CommonCode;
import com.ant.common.util.StringUtil;
import com.ant.member.basic.dao.basicDao;
import com.ant.member.insert.dao.memberInsertDao;
import com.ant.member.search.dao.memberSearchDao;


public class memberInsertAction extends DispatchAction {
	
	protected static Logger logger = Logger.getRootLogger();
	protected static String sel="";
	protected static String tnm="";
	protected static String keyword="";


	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.16
	 * 작   업 : postSearch		우편번호 검색 화면으로 포워딩
	 */
	public ActionForward postSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		if(request.getParameter("sel")!= null) {							   
			sel=request.getParameter("sel");
		} 
		return (mapping.findForward("postSearchr"));		
	}
		
	public ActionForward postSearchr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{	

	//	System.out.println("asdfasdf 되나");
		ArrayList<String[]> addressInfo = new ArrayList<String[]>();
		String apiurl = "http://openapi.epost.go.kr/postal/";
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchSe = request.getParameter("searchSe");
			
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
			
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
			
		int ncount = 0;
			
		StringBuffer result = new StringBuffer();
			
		if("Y".equals(request.getParameter("sel"))||"Y".equals(sel)){
			HttpURLConnection conn = null;
			keyword= StringUtil.removeWhiteSpace(URLDecoder.decode(request.getParameter("keyword"), "UTF-8"));
			apiurl = "http://openapi.epost.go.kr/postal/retrieveNewAdressService/retrieveNewAdressService/getNewAddressList?searchSe="+searchSe+"&srchwrd="+keyword+"&serviceKey=bYTGHnfOl/wxI/qSAsoSH6eyIkL17Gc9GLultBk05JhRG3KVH3weGucKec8VA4yJiC6vcmeL253SYRN/LEA9ow==";
			try{
				System.out.print("URL==============================>>>>"+ apiurl);
				URL url = new URL(apiurl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("accept-language","ko");
				
				SAXBuilder builder = new SAXBuilder();
				Document doc= builder.build(conn.getInputStream());
				Element xmlRoot = doc.getRootElement();
				List list1 = xmlRoot.getChildren();
				Element msgItem=(Element)list1.get(0);
				
				String successYN=msgItem.getChildText("successYN");
				String errMsg=msgItem.getChildText("errMsg");
				
				ncount = list1.size()-1;
				int ntotpage = (ncount/nrows)+1;
				
				result.append("{\"page\":\""+	npage	+"\",");
				result.append("\"total\":\"" + ntotpage+"\",");
				result.append("\"records\":\""+	ncount	+"\",");
				result.append("\"rows\":[");
				
				for(int i=1;i<list1.size();i++){
					Element item=(Element)list1.get(i);
					String zipNo = item.getChildText("zipNo");
					String rnAdres = item.getChildText("rnAdres");
					String lnmAdres = item.getChildText("lnmAdres");
					System.out.println("------->  "+zipNo+"   "+rnAdres+"   "+lnmAdres);      
					 
					if(i>1) result.append(",");
					result.append("{\"id\":\"" + i + "\",\"cell\":[");
					result.append("\"" + ( zipNo	== null ? "" : zipNo ) +"\",");
					result.append("\"" + ( lnmAdres== null ? "" : lnmAdres ) +"\",");
					result.append("\"" + ( rnAdres== null ? "" : rnAdres ) +"\",");
					result.append("\"" + ( successYN== null ? "" : successYN ) +"\",");
					result.append("\"" + ( errMsg== null ? "" : errMsg ) +"\"");
					result.append("]}");
					}
				result.append("]}");
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{if(conn != null) conn.disconnect(); } catch(Exception e){}
				}
			}
		request.setAttribute("result",result);
		keyword="";
		sel="";
		return (mapping.findForward("ajaxout"));
	}

	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.16
	 * 작   업 : compostSearch		업체 우편번호 검색 화면으로 포워딩
	 */
	public ActionForward compostSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		if(request.getParameter("sel")!= null) {							   
			sel=request.getParameter("sel");
		}
		
		return (mapping.findForward("compostSearchr"));		
	}
		
	public ActionForward compostSearchr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{	
			
		ArrayList<String[]> addressInfo = new ArrayList<String[]>();
		String apiurl = "http://openapi.epost.go.kr/postal/";
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String searchSe = request.getParameter("searchSe");
			
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
			
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
			
		int ncount = 0;
			
		StringBuffer result = new StringBuffer();
			
		if("Y".equals(request.getParameter("sel"))||"Y".equals(sel)){
			HttpURLConnection conn = null;
			keyword= StringUtil.removeWhiteSpace(URLDecoder.decode(request.getParameter("keyword"), "UTF-8"));
			apiurl = "http://openapi.epost.go.kr/postal/retrieveNewAdressService/retrieveNewAdressService/getNewAddressList?searchSe="+searchSe+"&srchwrd="+keyword+"&serviceKey=bYTGHnfOl/wxI/qSAsoSH6eyIkL17Gc9GLultBk05JhRG3KVH3weGucKec8VA4yJiC6vcmeL253SYRN/LEA9ow==";
			try{
				System.out.print("URL==============================>>>>"+ apiurl);
				URL url = new URL(apiurl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("accept-language","ko");
				
				SAXBuilder builder = new SAXBuilder();
				Document doc= builder.build(conn.getInputStream());
				Element xmlRoot = doc.getRootElement();
				List list1 = xmlRoot.getChildren();
				Element msgItem=(Element)list1.get(0);
				
				String successYN=msgItem.getChildText("successYN");
				String errMsg=msgItem.getChildText("errMsg");
				
				ncount = list1.size()-1;
				int ntotpage = (ncount/nrows)+1;
				
				result.append("{\"page\":\""+	npage	+"\",");
				result.append("\"total\":\"" + ntotpage+"\",");
				result.append("\"records\":\""+	ncount	+"\",");
				result.append("\"rows\":[");
				
				for(int i=1;i<list1.size();i++){
					Element item=(Element)list1.get(i);
					String zipNo = item.getChildText("zipNo");
					String rnAdres = item.getChildText("rnAdres");
					String lnmAdres = item.getChildText("lnmAdres");
					System.out.println("------->  "+zipNo+"   "+rnAdres+"   "+lnmAdres);      
					 
					if(i>1) result.append(",");
					result.append("{\"id\":\"" + i + "\",\"cell\":[");
					result.append("\"" + ( zipNo	== null ? "" : zipNo ) +"\",");
					result.append("\"" + ( lnmAdres== null ? "" : lnmAdres ) +"\",");
					result.append("\"" + ( rnAdres== null ? "" : rnAdres ) +"\",");
					result.append("\"" + ( successYN== null ? "" : successYN ) +"\",");
					result.append("\"" + ( errMsg== null ? "" : errMsg ) +"\"");
					result.append("]}");
					}
				result.append("]}");
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{if(conn != null) conn.disconnect(); } catch(Exception e){}
				}
			}
		request.setAttribute("result",result);
		keyword="";
		sel="";
		return (mapping.findForward("ajaxout"));
	}
	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.16
	 * 작   업 : sLicnoCnt		라이센스 중복 검사
	 */
	public ActionForward sLicnoCnt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String lic_no = request.getParameter("lic_no");
		
		Map map = new HashMap();
		memberInsertDao dao=new memberInsertDao();
			
		map.put("lic_no", lic_no);
		List<Map> list=dao.getChkLicNo(map);
	
		request.setAttribute("lic_no",lic_no);
		request.setAttribute("count", list.get(0).get("count"));

		return (mapping.findForward("sLicnoCnt"));
		
	}
	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.10
	 * 작   업 : memberInsert	회원저장 포워딩
	 */
	public ActionForward memberInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberInsertList");

		//제이슨으로 보낸다.
		List<HashMap<String, String>> comList	= CommonCode.getInstance().getCodeList();
		JSONArray jsoncode	= JSONArray.fromObject(comList);
		request.setAttribute("comList",jsoncode);

		return (mapping.findForward("memberInsertList"));
	}

	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.15
	 * 작   업 : memberInsertList	회원 저장
	 */
	// jqgrid 를 사용하지 않으니 (jsp에서 그리드를 최소화 하고 그냥 숨겨버렸다.)
	public ActionForward memberInsertList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		//기본정보인지 근무처정보인지 구분해서 처리한다.
		String isCase = "";
		if(request.getParameter("isCase")!=null)
			isCase = request.getParameter("isCase");

		//조회조건 넣기
		if("basic".equals(isCase)) {
			//기본정보 저장 해서 포워딩
			return memberInsertBasic(mapping, form, request, response);
		}else if("com".equals(isCase)) {
			//근무처정보 저장해서 포워딩
			return memberInsertCom(mapping, form, request, response);
		}
		
		return (mapping.findForward("memberInsertOut"));
	}
	
	
	
	//기본정보 저장
	public ActionForward memberInsertBasic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberInsertDao dao=new memberInsertDao();
		
		// 모든 파라미터 변수 선언
		String register = request.getParameter("register");
		String agreeDt = request.getParameter("agree_dt");
		String persName = request.getParameter("pers_name");
		String persNameDecoded = persName != null ? URLDecoder.decode(persName, "UTF-8") : "";
		String id = request.getParameter("id");
		String idValue = id != null ? id : "";
		String passwd = request.getParameter("passwd");
		String passwdValue = passwd != null ? passwd : "";
		String persBirth = request.getParameter("pers_birth");
		String codeSex = request.getParameter("code_sex");
		String licNo = request.getParameter("lic_no");
		String licPrintDt = request.getParameter("lic_print_dt");
		String codeMember = request.getParameter("code_member");
		String codeBran = request.getParameter("code_bran");
		String codeBig = request.getParameter("code_big");
		String codeSect = request.getParameter("code_sect");
		String codePost = request.getParameter("code_post");
		String codeAddGubun = request.getParameter("code_add_gubun");
		String persAdd = request.getParameter("pers_add");
		String persAddDetail = request.getParameter("pers_add_detail");
		String codePlace = request.getParameter("code_place");
		String persTel = request.getParameter("pers_tel");
		String persHp = request.getParameter("pers_hp");
		String email = request.getParameter("email");
		String codeEmailComp = request.getParameter("code_email_comp");
		String certifiView = request.getParameter("certifi_view");
		String codeSchool = request.getParameter("code_school");
		String codeScholar = request.getParameter("code_scholar");
		String codeUniver = request.getParameter("code_univer");
		String persCareer = request.getParameter("pers_career");
		String codeMarry = request.getParameter("code_marry");
		String codeReligion = request.getParameter("code_religion");
		String codeSub = request.getParameter("code_sub");
		String codeCall = request.getParameter("code_call");
		String recommender = request.getParameter("recommender");
		String recmHp = request.getParameter("recm_hp");
		String recmDivision = request.getParameter("recm_division");
		String kdaLevel = request.getParameter("kda_level");
		String persState = request.getParameter("pers_state");
		
		// map.put 정렬
		map.put("register"					, register != null ? register : "");			//등록자
		map.put("agree_dt"					, agreeDt != null ? agreeDt : "");			//동의일
		map.put("pers_name"			, persNameDecoded.trim());		//이름
		map.put("id"						, idValue.trim());	//아이디
		map.put("passwd"					, passwdValue.trim());	//패스워드
		map.put("pers_birth"			, persBirth != null ? persBirth : "");		//생년월일
		map.put("code_sex"				, codeSex != null ? codeSex : "");		//성별
		map.put("lic_no"					, licNo != null ? licNo : "");			//면허번호
		map.put("lic_print_dt"			, licPrintDt != null ? licPrintDt : "");	//발급일자(면허발급일자.)
		map.put("code_member"		, codeMember != null ? codeMember : "");//회원종류
		map.put("code_bran"				, codeBran != null ? codeBran : "");		//지부
		map.put("code_big"				, codeBig != null ? codeBig : "");		//분과명
		map.put("code_sect"				, codeSect != null ? codeSect : "");		//분회명
		map.put("code_post"				, codePost != null ? codePost : "");		//자택우편번호
		map.put("code_add_gubun"	, codeAddGubun != null ? codeAddGubun : "");		//주소구분
		map.put("pers_add"				, persAdd != null ? URLDecoder.decode(persAdd, "UTF-8") : "");		//자택주소
		map.put("pers_add_detail"		, persAddDetail != null ? URLDecoder.decode(persAddDetail, "UTF-8") : "");	//자택주소(상세)
		map.put("code_place"			, codePlace != null ? codePlace : "");		//우편물 수신처발송지구분
		map.put("pers_tel"				, persTel != null ? persTel : "");	//자택전화번호
		map.put("pers_hp"				, persHp != null ? persHp : "");	//휴대폰
		map.put("email"					, email != null ? email : "");	//이메일
		map.put("code_email_comp"	, codeEmailComp != null ? codeEmailComp : "");	//이메일업체
		map.put("certifi_view"			, certifiView != null ? certifiView : "");		//이메일
		map.put("code_school"			, codeSchool != null ? codeSchool : "");	//학교
		map.put("code_scholar"			, codeScholar != null ? codeScholar : "");	//최종학력
		map.put("code_univer"			, codeUniver != null ? codeUniver : "");	//학위
		map.put("pers_career"			, persCareer != null ? persCareer : "");	//경력
		map.put("code_marry"			, codeMarry != null ? codeMarry : "");	//혼인여부
		map.put("code_religion"			, codeReligion != null ? codeReligion : "");	//학위
		map.put("code_sub"				, codeSub != null ? codeSub : "");	//산야단체 및 분야회
		map.put("code_call"				, codeCall != null ? codeCall : "");	//발송호칭
		map.put("recommender"			, recommender != null ? URLDecoder.decode(recommender, "UTF-8") : "");	//추천인 성명
		map.put("recm_hp"				, recmHp != null ? recmHp : "");	//추천인핸드폰
		map.put("recm_division"			, recmDivision != null ? URLDecoder.decode(recmDivision, "UTF-8") : "");	//추천인근무처
		map.put("kda_level"				, kdaLevel != null ? kdaLevel : "");	//협회직급
		map.put("pers_state"			, persState != null ? persState : "");	//회원상태
		
		System.out.println("pers_name===========>"+persNameDecoded+"    "+persNameDecoded.trim());
		System.out.println("passwd===========>"+passwdValue+"    "+passwdValue.trim());

		//code_pers(회원번호)를 알아온다.
		List<Map> list=dao.getMemberCodePers(map);
		
		if (list == null || list.isEmpty()) {
		    request.setAttribute("errorMsg", "회원번호를 생성할 수 없습니다.");
		    return mapping.findForward("memberInsertList");
		}
		
		Map firstItem = list.get(0);
		if (firstItem == null || firstItem.get("code_pers") == null) {
		    request.setAttribute("errorMsg", "유효한 회원번호가 생성되지 않았습니다.");
		    return mapping.findForward("memberInsertList");
		}
		System.out.println(list.size());
		map.put("code_pers"				, list.get(0).get("code_pers"	));	//회원번호
		map.put("comp_seq"				, "1");	//회원번호

	//	System.out.println("asdfasdf =(String) list.get(0).get('code_pers'	)  ="+ list.get(0).get("code_pers"	));

		try {
			//회원정보 저장
	    	dao.setMemberInsertBasic(map);
			//회원히스토리정보 저장
	    	dao.setPerson_M_history(map);
			//아이디테이블 저장
	    	dao.setMemberInsertIdTbl(map);
			
			request.setAttribute("code_pers",list.get(0).get("code_pers"));//회원번호 
			request.setAttribute("code_part",request.getParameter("code_part"	));	//근무처구분
			request.setAttribute("code_great",request.getParameter("code_great"	));//근무처대분류분과
			request.setAttribute("code_small",request.getParameter("code_small"	));	//근무처소분류
	
			request.setAttribute("isBasicOk", "Y");
		
		} catch (Exception e) {
			e.printStackTrace();
			 request.setAttribute("errorMsg", "저장 중 오류가 발생했습니다: " + e.getMessage());
		}
		return (mapping.findForward("memberInsertList"));
	}
	
	
	//근무처정보 저장
	public ActionForward memberInsertCom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberInsertDao dao=new memberInsertDao();

		// 필수 파라미터 null 체크
		String codePers = request.getParameter("code_pers");
		if(codePers == null || codePers.trim().isEmpty()) {
			request.setAttribute("errorMsg", "회원번호가 없습니다. 기본정보를 먼저 저장해주세요.");
			request.setAttribute("code_pers","");
			request.setAttribute("code_part","");
			request.setAttribute("code_great","");
			request.setAttribute("code_small","");
			request.setAttribute("isCkPersNo","N");
			request.setAttribute("isCkLicNo","N");
			request.setAttribute("cTcode","");
			request.setAttribute("isComOk", "N");
			return (mapping.findForward("memberInsertList"));
		}

		// 모든 파라미터 변수 선언
		String compSeq = request.getParameter("comp_seq");
		String register = request.getParameter("register");
		String trustCode = request.getParameter("trust_code");
		String codePart = request.getParameter("code_part");
		String codeGreat = request.getParameter("code_great");
		String codeSmall = request.getParameter("code_small");
		String companyName = request.getParameter("company_name");
		String codePost = request.getParameter("code_post");
		String companyAdd = request.getParameter("company_add");
		String companyAddDetail = request.getParameter("company_add_detail");
		String companyTel = request.getParameter("company_tel");
		String companyFax = request.getParameter("company_fax");
		String persInDt = request.getParameter("pers_in_dt");
		String persOutDt = request.getParameter("pers_out_dt");
		String codeUse = request.getParameter("code_use");
		String jobDept = request.getParameter("job_dept");
		String jobLevelCode = request.getParameter("job_level_code");
		String jobLineCode = request.getParameter("job_line_code");
		String licPay = request.getParameter("lic_pay");
		String yearPay = request.getParameter("year_pay");
		String companySickBad = request.getParameter("company_sick_bad");
		String companyMeal = request.getParameter("company_meal");
		String companyCountMom = request.getParameter("company_count_mom");
		String companyCountLunch = request.getParameter("company_count_lunch");
		String companyCountDinner = request.getParameter("company_count_dinner");
		String companyCountMidnig = request.getParameter("company_count_midnig");
		String joinCon = request.getParameter("join_con");
		String joinCook = request.getParameter("join_cook");
		String persMulty = request.getParameter("pers_multy");
		String primaryFlag = request.getParameter("primary_flag");
		String secuNo = request.getParameter("secu_no");
		String trustName = request.getParameter("trust_name");
		String trustTel = request.getParameter("trust_tel");
		String trustAddr = request.getParameter("trust_addr");
		
		// map.put 정렬
		map.put("code_pers"					, codePers);			//회원번호
		map.put("comp_seq"					, compSeq != null ? compSeq : "");			//근무처순번
		map.put("register"					, register != null ? register : "");			//등록자
		map.put("trust_code"					, trustCode != null ? trustCode : "");			//근무처 코드
		map.put("code_part"					, codePart != null ? codePart : "");				//근무처 구분
		map.put("code_great"					, codeGreat != null ? codeGreat : "");			//근무처대분류 코드
		map.put("code_small"					, codeSmall != null ? codeSmall : "");			//근무처소분류 코드
		map.put("company_name"			, companyName != null ? URLDecoder.decode(companyName, "UTF-8") : "");	//근무처명
		map.put("code_post"					, codePost != null ? codePost : "");			//근무처우편번호
		map.put("company_add"			, companyAdd != null ? URLDecoder.decode(companyAdd, "UTF-8") : "");	//근무처주소
		map.put("company_add_detail"	, companyAddDetail != null ? URLDecoder.decode(companyAddDetail, "UTF-8") : "");	//근무처주소(상세)
		map.put("company_tel"				, companyTel != null ? companyTel : "");		//근무처전화번호
		map.put("company_fax"				, companyFax != null ? companyFax : "");		//근무처팩스번호
		map.put("pers_in_dt"					, persInDt != null ? persInDt : "");			//입사일
		map.put("pers_out_dt"				, persOutDt != null ? persOutDt : "");		//퇴사일
		map.put("code_use"					, codeUse != null ? codeUse : "");		//운영형태
		map.put("job_dept"					, jobDept != null ? URLDecoder.decode(jobDept, "UTF-8") : "");		//부서
		map.put("job_level_code"			, jobLevelCode != null ? jobLevelCode : "");		//직렬
		map.put("job_line_code"				, jobLineCode != null ? jobLineCode : "");		//직급
		map.put("lic_pay"						, licPay != null ? licPay : "");		//영양사면허수당
		map.put("year_pay"					, yearPay != null ? yearPay : "");		//연봉
		map.put("company_sick_bad"		, companySickBad != null ? companySickBad : "");			//허가병상
		map.put("company_meal"			, companyMeal != null ? companyMeal : "");				//1식재료비
		map.put("company_count_mom"	, companyCountMom != null ? companyCountMom : "");		//1일급식인원-아침
		map.put("company_count_lunch"	, companyCountLunch != null ? companyCountLunch : "");		//1일급식인원-점심
		map.put("company_count_dinner", companyCountDinner != null ? companyCountDinner : "");	//1일급식인원-저녁
		map.put("company_count_midnig", companyCountMidnig != null ? companyCountMidnig : "");	//1일급식인원-야식
		map.put("join_con"					, joinCon != null ? joinCon : "");		//공동관리
		map.put("join_cook"					, joinCook != null ? joinCook : "");		//공동조리
		map.put("pers_multy"				, persMulty != null ? persMulty : "");		//겸직여부
		map.put("primary_flag"				, primaryFlag != null ? primaryFlag : "");		//우선근무처여부
		map.put("secu_no"					, secuNo != null ? secuNo : "");		//산재번호
		map.put("trust_name"				, trustName != null ? URLDecoder.decode(trustName, "UTF-8") : "");		//소속위탁업체
		map.put("trust_tel"					, trustTel != null ? trustTel : "");		//위탁업체 전화번호
		map.put("trust_addr"					, trustAddr != null ? URLDecoder.decode(trustAddr, "UTF-8") : "");		//위탁업체주소
		
		try {
			dao.setMemberInsertCom(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "저장 중 오류가 발생했습니다: " + e.getMessage());
		}
		
		request.setAttribute("code_pers","");//회원번호 
		request.setAttribute("code_part","");//근무처구분
		request.setAttribute("code_great","");//근무처대분류분과
		request.setAttribute("code_small","");//근무처소분류

		request.setAttribute("isCkPersNo","N");//주민등록 번호 체크여부
		request.setAttribute("isCkLicNo","N");//라이센스번호 체크여부
		request.setAttribute("cTcode","");//소속위탁업체코드

		request.setAttribute("isComOk", "Y");

		return (mapping.findForward("memberInsertList"));
	}
	
	public ActionForward idChk(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		memberInsertDao dao=new memberInsertDao();		
		String id		=StringUtil.nullToStr("", request.getParameter("id"));		
		map.put("id", id);		
		List<Map> cnt=dao.getIdCnt(map);		
		request.setAttribute("result", cnt);
		request.setAttribute("iid", id);
		return (mapping.findForward("idChkr"));		
	}
}

















