package com.ant.member.basic.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;
/*import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;*/
import com.ant.common.util.EmailSender;
import com.ant.common.util.StringUtil;
import com.ant.document.dao.documentDao;
import com.ant.educationexam.dao.educationDao;
import com.ant.educationlecture.dao.licenseDao;
import com.ant.member.basic.dao.basicDao;
import com.ant.member.dues.dao.memberDuesDao;
import com.ant.member.search.dao.memberSearchDao;
import com.ant.setup.dao.setupduesDao;
import com.oreilly.servlet.MultipartRequest;

public class basicAction extends DispatchAction {
	protected static Logger logger = Logger.getRootLogger();
	protected static String sel="";
	protected static String tnm="";
	protected static String keyword="";
	
	public ActionForward mSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("mSearchr"));		
	}

	public ActionForward mSearchr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		Map map = new HashMap();
		basicDao dao=new basicDao(); 
		
		System.out.println(request.getParameter("memberid")!=null);
		if(request.getParameter("memberid")!=null){
			map.put("id",URLDecoder.decode(request.getParameter("memberid"), "UTF-8"));
		}
		if(request.getParameter("name")!=null){
			map.put("name",URLDecoder.decode(request.getParameter("name"), "UTF-8"));			
		}
//		JUMIN_DEL
//		if(request.getParameter("jumin")!=null){
//			map.put("jumin",URLDecoder.decode(request.getParameter("jumin"), "UTF-8"));
//		}
		if(request.getParameter("birth")!=null){
			map.put("birth",URLDecoder.decode(request.getParameter("birth"), "UTF-8"));
		}
		if(request.getParameter("licno")!=null){
			map.put("licno",URLDecoder.decode(request.getParameter("licno"), "UTF-8"));
		}
		if(request.getParameter("email")!=null){
			map.put("email",URLDecoder.decode(request.getParameter("email"), "UTF-8"));
		}
		if(request.getParameter("compname")!=null){
			map.put("compname",URLDecoder.decode(request.getParameter("compname"), "UTF-8"));
		}
		if(request.getParameter("gbran")!=null&&(!"01".equals(request.getParameter("gbran")))){
			map.put("gbran",request.getParameter("gbran"));
		}
		System.out.println("name = " + map.get("name"));
		logger.debug("name = " + map.get("name"));
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String sidx = request.getParameter("sidx");//정렬기준 : pers_name
		String sord = request.getParameter("sord");//정렬 : asc, desc
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sMemberCnt=dao.sMemberCnt(map);
		ncount = ((Integer)(sMemberCnt.get(0).get("cnt"))).intValue();

		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;

		//전체 자료 가져오기
		List<Map> sMember=dao.sMember(map);
				
		StringBuffer result = new StringBuffer();
		
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");

		for(int i=0;i<sMember.size();i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sMember.get(i).get("pers_name")	== null ? "" : sMember.get(i).get("pers_name") ) +"\",");
//			JUMIN_DEL
//			result.append("\"" + ( sMember.get(i).get("pers_no")	== null ? "" : sMember.get(i).get("pers_no") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("pers_birth")	== null ? "" : sMember.get(i).get("pers_birth") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("lic_no")	== null ? "" : sMember.get(i).get("lic_no") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("company_name")		== null ? "" : sMember.get(i).get("company_name") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("pers_add")	== null ? "" : sMember.get(i).get("pers_add") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("code_member")		== null ? "" : sMember.get(i).get("code_member") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("pers_state")		== null ? "" : sMember.get(i).get("pers_state") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("id")		== null ? "" : sMember.get(i).get("id") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("pers_hp")		== null ? "" : sMember.get(i).get("pers_hp") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("email")		== null ? "" : sMember.get(i).get("email") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("pers_tel")		== null ? "" : sMember.get(i).get("pers_tel") ) +"\",");
			result.append("\"" + ( sMember.get(i).get("code_pers")		== null ? "" : sMember.get(i).get("code_pers") ) +"\"");
			result.append("]}");		
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));

	}
	
	public ActionForward tSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(URLDecoder.decode(request.getParameter("tName"), "UTF-8") != null){	
			tnm=URLDecoder.decode(request.getParameter("tName"), "UTF-8");
			sel=request.getParameter("sel");
		}
		return (mapping.findForward("tSearchr"));		
	}

	public ActionForward tSearchr(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		Map map = new HashMap();
		basicDao dao=new basicDao(); 
		if(tnm != null){			
			map.put("trust_name",tnm);			
		}
		
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> tComCnt=dao.tComCnt(map);
		ncount = ((Integer)(tComCnt.get(0).get("cnt"))).intValue();

		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;
		StringBuffer result = new StringBuffer();
		
		if("Y".equals(request.getParameter("sel"))||"Y".equals(sel)){
			//전체 자료 가져오기
			List<Map> tCompany=dao.tCompany(map);
	
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<tCompany.size();i++){			
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( tCompany.get(i).get("trust_code")	== null ? "" : tCompany.get(i).get("trust_code") ) +"\",");
				result.append("\"" + ( tCompany.get(i).get("trust_name")	== null ? "" : tCompany.get(i).get("trust_name") ) +"\",");
				result.append("\"" + ( tCompany.get(i).get("trust_post")	== null ? "" : tCompany.get(i).get("trust_post") ) +"\",");
				result.append("\"" + ( tCompany.get(i).get("trust_addr")	== null ? "" : tCompany.get(i).get("trust_addr") ) +"\",");
				result.append("\"" + ( tCompany.get(i).get("trust_tel")	== null ? "" : tCompany.get(i).get("trust_tel") ) +"\"");			
				result.append("]}");		
			}	
			result.append("]}");
		}
		request.setAttribute("result",result);
		tnm="";
		sel="";
		return (mapping.findForward("ajaxout"));

	}
	
	public ActionForward sLicnoCnt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		
		String lic_no		=StringUtil.nullToStr("", request.getParameter("lic_no"));
		String code_pers	=StringUtil.nullToStr("", request.getParameter("code_pers"));
		
		map.put("lic_no", lic_no);
		map.put("code_pers", code_pers);
		
		List<Map> cnt=dao.sLicCnt(map);
		
		request.setAttribute("result", cnt);
		request.setAttribute("map", map);
		return (mapping.findForward("sLicnor"));		
	}
	
	public ActionForward mTcomp(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao=new basicDao();
		
		String trust_name	=StringUtil.nullToStr("", request.getParameter("trust_name"));
		String trust_post		=StringUtil.nullToStr("", request.getParameter("trust_post"));
		String trust_addr		=StringUtil.nullToStr("", request.getParameter("trust_addr"));
		String trust_tel			=StringUtil.nullToStr("", request.getParameter("trust_tel"));
		String register			=StringUtil.nullToStr("", request.getParameter("register"));

		map.put("trust_name", trust_name);
		map.put("trust_post", trust_post);
		map.put("trust_addr", trust_addr);
		map.put("trust_tel", trust_tel);
		map.put("register", register);
		
		List<Map> iTcomp=dao.iTcomp(map);
		
		return (mapping.findForward("tSearchr"));
	}
	
	public ActionForward memInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo(map);
		List<Map> code=dao.getCode(map);
		List<Map> code1=dao.getCode1(map);
		List<Map> subcom=dao.getSubcom(map);
		List<Map> g_certifi=dao.getCertifi(map);
		JSONArray jsoncode=JSONArray.fromObject(code1);
		request.setAttribute("result",memInfo);
		request.setAttribute("code",code);
		request.setAttribute("subcom", subcom);
		request.setAttribute("g_certifi", g_certifi);
		request.setAttribute("jcode", jsoncode);
		
		//토큰 저장
		saveToken(request);
		return (mapping.findForward("pop"));		
	}
	
	public ActionForward mDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		basicDao dao=new basicDao(); 

		if(request.getParameter("pCode")!=null){
			map.put("pCode",URLDecoder.decode(request.getParameter("pCode"), "UTF-8"));
		}
		
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String sidx = request.getParameter("sidx");//정렬기준 : pers_name
		String sord = request.getParameter("sord");//정렬 : asc, desc
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sDetailCnt=dao.sDetailCnt(map);
		ncount = ((Integer)(sDetailCnt.get(0).get("cnt"))).intValue();

		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;

		//전체 자료 가져오기
		List<Map> sDetail=dao.sDetail(map);
				
		StringBuffer result = new StringBuffer();
		
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");

		for(int i=0;i<sDetail.size();i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sDetail.get(i).get("up_dt")	== null ? "" : sDetail.get(i).get("up_dt") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("up_dt_seq")	== null ? "" : sDetail.get(i).get("up_dt_seq") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("register")	== null ? "" : sDetail.get(i).get("register") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_pers")	== null ? "" : sDetail.get(i).get("code_pers") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("pers_name")	== null ? "" : sDetail.get(i).get("pers_name") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("id")	== null ? "" : sDetail.get(i).get("id") ) +"\",");			
//			result.append("\"" + ( sDetail.get(i).get("pers_no")	== null ? "" : sDetail.get(i).get("pers_no") ) +"\","); // JUMIN_DEL
			result.append("\"" + ( sDetail.get(i).get("lic_no")	== null ? "" : sDetail.get(i).get("lic_no") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_member")	== null ? "" : sDetail.get(i).get("code_member") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_bran")	== null ? "" : sDetail.get(i).get("code_bran") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_big")	== null ? "" : sDetail.get(i).get("code_big") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_sect")	== null ? "" : sDetail.get(i).get("code_sect") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_post")	== null ? "" : sDetail.get(i).get("code_post") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("pers_add")	== null ? "" : sDetail.get(i).get("pers_add") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("pers_add_detail")	== null ? "" : sDetail.get(i).get("pers_add_detail") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_place")	== null ? "" : sDetail.get(i).get("code_place") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("pers_tel")	== null ? "" : sDetail.get(i).get("pers_tel") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("certifi_view")	== null ? "" : sDetail.get(i).get("certifi_view") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("pers_hp")	== null ? "" : sDetail.get(i).get("pers_hp") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("email")	== null ? "" : sDetail.get(i).get("email") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_school")	== null ? "" : sDetail.get(i).get("code_school") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_scholar")	== null ? "" : sDetail.get(i).get("code_scholar") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_univer")	== null ? "" : sDetail.get(i).get("code_univer") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("pers_career")	== null ? "" : sDetail.get(i).get("pers_career") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_marry")	== null ? "" : sDetail.get(i).get("code_marry") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_religion")	== null ? "" : sDetail.get(i).get("code_religion") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_sub")	== null ? "" : sDetail.get(i).get("code_sub") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_call")	== null ? "" : sDetail.get(i).get("code_call") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("recommender")	== null ? "" : sDetail.get(i).get("recommender") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("recm_division")	== null ? "" : sDetail.get(i).get("recm_division") ) +"\",");			
			result.append("\"" + ( sDetail.get(i).get("recm_hp")	== null ? "" : sDetail.get(i).get("recm_hp") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("kda_level")	== null ? "" : sDetail.get(i).get("kda_level") ) +"\","); 
			result.append("\"" + ( sDetail.get(i).get("code_sex")	== null ? "" : sDetail.get(i).get("code_sex") ) +"\","); //여기부터 추가
			result.append("\"" + ( sDetail.get(i).get("lic_print_dt")	== null ? "" : sDetail.get(i).get("lic_print_dt") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_add_gubun")	== null ? "" : sDetail.get(i).get("code_add_gubun") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("code_email_comp")	== null ? "" : sDetail.get(i).get("code_email_comp") ) +"\",");
			result.append("\"" + ( sDetail.get(i).get("pers_state")	== null ? "" : sDetail.get(i).get("pers_state") ) +"\"");
			result.append("]}");		
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));

	}
		
	public ActionForward mModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao=new basicDao();
		
		String m_name=StringUtil.nullToStr("", request.getParameter("m_name"));
		String m_id=StringUtil.nullToStr("", request.getParameter("m_id"));
		String m_pw=StringUtil.nullToStr("", request.getParameter("m_pw"));
		String code_pers=StringUtil.nullToStr("", request.getParameter("mCode_pers"));
//		String mID=StringUtil.nullToStr("", request.getParameter("mId1"))+StringUtil.nullToStr("", request.getParameter("mId2")); // JUMIN_DEL
		String mSex=StringUtil.nullToStr("", request.getParameter("mSex"));
		String m_licno=StringUtil.nullToStr("", request.getParameter("m_licno"));
		String mCode=StringUtil.nullToStr("", request.getParameter("mCode"));
		String mBran=StringUtil.nullToStr("", request.getParameter("mBran"));
		String mBig=StringUtil.nullToStr("", request.getParameter("mBig"));
		String mSect=StringUtil.nullToStr("", request.getParameter("mSect"));
		String mPart=StringUtil.nullToStr("", request.getParameter("mPart"));
		String mGreat=StringUtil.nullToStr("", request.getParameter("mGreat"));
		String mSmall=StringUtil.nullToStr("", request.getParameter("mSmall"));
		String m_post=StringUtil.nullToStr("", request.getParameter("m_post")).replace("-",""	);
		String pGubun=StringUtil.nullToStr("", request.getParameter("pgubun")).replace("-",""	);
		String m_addr=StringUtil.nullToStr("", request.getParameter("m_address"));
		String d_addr=StringUtil.nullToStr("", request.getParameter("d_address"));
		String mPlace=StringUtil.nullToStr("", request.getParameter("mPlace"));
		String m_tel=StringUtil.nullToStr("", request.getParameter("m_tel"));
		String m_hp=StringUtil.nullToStr("", request.getParameter("m_hp"));
		String m_email=StringUtil.nullToStr("", request.getParameter("m_email"));
		String mEcomp=StringUtil.nullToStr("", request.getParameter("mEcomp"));
		String mCerti=StringUtil.nullToStr("00000", request.getParameter("mCertiview"));
		String mSchool=StringUtil.nullToStr("", request.getParameter("mSchool"));
		String mScholar=StringUtil.nullToStr("", request.getParameter("mScholar"));
		String mUniver=StringUtil.nullToStr("", request.getParameter("mUniver"));
		String mYear=StringUtil.nullToStr("", request.getParameter("mYear"));
		String mMonth=StringUtil.nullToStr("", request.getParameter("mMonth"));
		if(mYear.length()==1){
			mYear="0"+mYear;
		}
		if(mMonth.length()==1){
			mMonth="0"+mMonth;
		}
		String mCareer=mYear+mMonth;
		String mMarry=StringUtil.nullToStr("", request.getParameter("mMarry"));
		String mReligion=StringUtil.nullToStr("", request.getParameter("mReligion"));
		String mCall=StringUtil.nullToStr("", request.getParameter("mCall"));
		String mRecm=StringUtil.nullToStr("", request.getParameter("mRecm"));
		String mRdivision=StringUtil.nullToStr("", request.getParameter("mRdivision"));
		String mReHp=StringUtil.nullToStr("", request.getParameter("mReHp"));
		String mLevel=StringUtil.nullToStr("", request.getParameter("mLevel"));
		String register=StringUtil.nullToStr("", request.getParameter("register"));
		String mState=StringUtil.nullToStr("", request.getParameter("mState"));
		String mSub=StringUtil.nullToStr("", request.getParameter("mSub"));
		String comp_seq=StringUtil.nullToStr("", request.getParameter("comp_seq"));
		String mBirth=StringUtil.nullToStr("", request.getParameter("mBirth"));
		
		//2024.03.25 소속분과(mBig) 회원상태(mState) 에 따른 산하단체(mSub) 값 변경
		mSub = "00";						//없음
		if (mState.equals("01")) {			//정상
			if (mBig.equals("103")) {		//영양교사
				mSub = "01";				//전국영양교사회
			}else if (mBig.equals("104")) {	//학교영양사
				mSub = "02";				//전국학교영양사회
			}
		}
		
		map.put("m_name", m_name);
		map.put("code_pers", code_pers);
//		map.put("mId", mID); // JUMIN_DEL
		map.put("mSex", mSex);
		map.put("m_licno", m_licno);
		map.put("mCode", mCode);
		map.put("mBran", mBran);
		map.put("mBig", mBig);
		map.put("mSect", mSect);
		map.put("mPart", mPart);
		map.put("mGreat", mGreat);
		map.put("mSmall", mSmall);
		map.put("m_post", m_post);
		map.put("pGubun", pGubun);
		map.put("m_addr", m_addr);
		map.put("d_addr", d_addr);
		map.put("mPlace", mPlace);
		map.put("m_tel", m_tel);
		map.put("m_hp", m_hp);
		map.put("m_email", m_email);
		map.put("mEcomp",mEcomp);
		map.put("mCerti", mCerti);
		map.put("mSchool", mSchool);
		map.put("mScholar", mScholar);
		map.put("mUniver", mUniver);
		map.put("mCareer", mCareer);
		map.put("mMarry", mMarry);
		map.put("mReligion", mReligion);
		map.put("mCall", mCall);
		map.put("mRecm", mRecm);
		map.put("mRdivision", mRdivision);
		map.put("mReHp", mReHp);
		map.put("mLevel", mLevel);
		map.put("register", register);
		map.put("mState",mState);
		map.put("mSub", mSub);
		map.put("comp_seq", comp_seq);
		map.put("mBirth", mBirth);
		String errMsg="";
		
		try{
			if("".equals(code_pers)){
				errMsg="이미 저장했습니다.";	
			}else{
		    	if(!"".equals(code_pers)){
		    		Map map1 = new HashMap();
					map1.put("code_pers"		, code_pers );
					memberSearchDao dao1=new memberSearchDao();
					List<Map> list = dao1.getSearchUmsList(map1);
					if(list!=null && list.size()>0) {
						String pers_add_detail = (String) list.get(0).get("pers_add_detail");
						String pers_tel =  (String) list.get(0).get("pers_tel");
						String pers_hp =  (String) list.get(0).get("pers_hp");
						String email =  (String) list.get(0).get("email");
						
						String d_addr_upok = "";
						String m_tel_upok = "";
						String m_hp_upok = "";
						String m_email_upok = "";
						
						if(!d_addr.equals(pers_add_detail)) d_addr_upok = "Y";
						if(!m_tel.equals(pers_tel)) m_tel_upok = "Y";
						if(!m_hp.equals(pers_hp)) m_hp_upok = "Y";
						if(!m_email.equals(email)) m_email_upok = "Y";
						
						map.put("d_addr_upok", d_addr_upok);
						map.put("m_tel_upok", m_tel_upok);
						map.put("m_hp_upok", m_hp_upok);
						map.put("m_email_upok", m_email_upok);
					}
		    	}
		    	
				List<Map> iModify=dao.iModify(map,true);
				int uModify=dao.uModify(map);
				int ucModify=dao.ucModify(map);
				resetToken(request);
				errMsg="저장했습니다.";
			}
		} catch (SQLException e) {
	        logger.error("mModify SQLException: ", e);
	        errMsg = "저장 중 오류가 발생했습니다. 다시 시도해주세요.";
	        // 세션은 유지되도록 함
	    } catch (Exception e) {
	        logger.error("mModify Exception: ", e);
	        errMsg = "처리 중 오류가 발생했습니다.";
	    }
	    
		map2.put("pCode",code_pers);
		List<Map> memInfo=dao.memInfo(map2);
		List<Map> code=dao.getCode(map2);
		List<Map> subcom=dao.getSubcom(map2);
		List<Map> g_certifi=dao.getCertifi(map2);
		List<Map> code1=dao.getCode1(map);
		JSONArray jsoncode=JSONArray.fromObject(code1);
		request.setAttribute("result",memInfo);
		request.setAttribute("code",code);
		request.setAttribute("pCode", code_pers);
		request.setAttribute("subcom", subcom);
		request.setAttribute("g_certifi", g_certifi);
		request.setAttribute("jcode", jsoncode);
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
		return (mapping.findForward("pop"));
	}
	
	public ActionForward uPasswd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		String code_pers=StringUtil.nullToStr("", request.getParameter("mCode_pers"));
		String pw=request.getParameter("pw");
		String id=request.getParameter("id");
		System.out.println("==========>"+code_pers+"       "+pw);
		map.put("code_pers",code_pers);
		map.put("pCode",code_pers);
		//map.put("pw", pw);
		
		int randomlen = 4;
		boolean tmpPwResult = true;
		StringBuilder sb = new StringBuilder("");
		
		if ((pw.length() == 10 || pw.length() == 11)) { // 휴대폰 번호가정상인경우
			if (pw.length() == 10)
				randomlen = 3;

			for (int i = 0; i < 4; i++) {
				sb.append((char) (Math.random() * 26 + 97));
			}
			
			pw = sb.toString() + pw.substring(randomlen + 3, pw.length());
			log.info("임시비밀번호 생성 :" + pw);
		} else {
			tmpPwResult = false; // 휴대폰번호가 비정상적으로 들어간 경우
		}
		
		map.put("pw", pw);
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="패스워드가 이미 초기화됐습니다.";	
		}else{
			int upass=dao.uPWreset(map);
			
			if ( upass < 1 )
				errMsg = "error 입니다.";
			else if (upass > 0){
				if(tmpPwResult){
					errMsg =  "요청하신 임시비밀번호는 다음과 같습니다.\\r\\n" + sb.toString()+"****" + "(****은 저장된 휴대폰 뒷번호 4자리)\\r\\n 로그인 후 반드시 변경하여 사용해 주시기 바랍니다.\\r\\n휴대폰 번호가 변경되신 경우 02-823-5680(311)로 문의주세요.";
					setupduesDao dao2 = new setupduesDao();
					map.put("UserPW1",           pw);
					map.put("UserId1",             id);
					int result1 = dao2.updatepass(map);
					
				}else {
					errMsg = "관리자(02-823-5680 내선311)에게 문의해 주세요";
				}
				
			}else errMsg = "success=" + pw;
			resetToken(request);
			//errMsg="패스워드를 휴대폰 번호로 초기화했습니다.";
		}
		
		List<Map> memInfo=dao.memInfo(map);
		List<Map> code=dao.getCode(map);
		List<Map> subcom=dao.getSubcom(map);
		List<Map> g_certifi=dao.getCertifi(map);
		List<Map> code1=dao.getCode1(map);
		JSONArray jsoncode=JSONArray.fromObject(code1);
		request.setAttribute("result",memInfo);
		request.setAttribute("code",code);
		request.setAttribute("pCode", code_pers);
		request.setAttribute("subcom", subcom);
		request.setAttribute("g_certifi", g_certifi);
		request.setAttribute("jcode", jsoncode);
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
		return (mapping.findForward("pop"));		
	}
	
	//팝업 메뉴2. 근무처 정보
	public ActionForward comp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo1(map);
		//List<Map> code=dao.getCode(map);
		List<Map> defcomp=dao.defComp(map);		
		request.setAttribute("result",memInfo);
		//request.setAttribute("code",code);
		request.setAttribute("defcomp", defcomp);
		return (mapping.findForward("pop1"));			
	}
	public ActionForward compList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		basicDao dao=new basicDao(); 
		map.put("pCode",request.getParameter("pCode"));
		
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String sidx = request.getParameter("sidx");//정렬기준 : pers_name
		String sord = request.getParameter("sord");//정렬 : asc, desc
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sCompCnt=dao.sCompCnt(map);
		ncount = ((Integer)(sCompCnt.get(0).get("cnt"))).intValue();
			//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;
			//전체 자료 가져오기
		List<Map> sComp=dao.sComp(map);
				
		StringBuffer result = new StringBuffer();
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		for(int i=0;i<sComp.size();i++){
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sComp.get(i).get("comp_seq")	== null ? ""	 			: sComp.get(i).get("comp_seq") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_name")	== null ? "" 			: sComp.get(i).get("company_name") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("code_post")	== null ? "" 				: sComp.get(i).get("code_post") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_add")	== null ? "" 			: sComp.get(i).get("company_add") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_add_detail")	== null ? ""	: sComp.get(i).get("company_add_detail") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_tel")	== null ? "" 				: sComp.get(i).get("company_tel") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_fax")	== null ? "" 			: sComp.get(i).get("company_fax") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("pers_in_dt")	== null ? "" 				: sComp.get(i).get("pers_in_dt") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("pers_out_dt")	== null ? "" 				: sComp.get(i).get("pers_out_dt") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("code_use")	== null ? ""				 	: sComp.get(i).get("code_use") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("useName")	== null ? ""				 	: sComp.get(i).get("useName") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("job_dept")	== null ? "" 					: sComp.get(i).get("job_dept") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("job_level_code")	== null ? "" 			: sComp.get(i).get("job_level_code") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("levelName")	== null ? ""				: sComp.get(i).get("levelName") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("job_line_code")	== null ? "" 			: sComp.get(i).get("job_line_code") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("lineName")	== null ? ""				 	: sComp.get(i).get("lineName") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("lic_pay")	== null ? "" 						: sComp.get(i).get("lic_pay") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("year_pay")	== null ? "" 					: sComp.get(i).get("year_pay") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_sick_bad")	== null ? "" 		: sComp.get(i).get("company_sick_bad") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_meal")	== null ? "" 			: sComp.get(i).get("company_meal") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("join_con")	== null ? ""			 		: sComp.get(i).get("join_con") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("join_cook")	== null ? "" 					: sComp.get(i).get("join_cook") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("primary_flag")	== null ? "" 				: sComp.get(i).get("primary_flag") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("pers_multy")	== null ? "" 				: sComp.get(i).get("pers_multy") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_count_mom")	== null ? "" 			: sComp.get(i).get("company_count_mom") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_count_lunch")	== null ? "" 			: sComp.get(i).get("company_count_lunch") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_count_dinner")	== null ? "" 			: sComp.get(i).get("company_count_dinner") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("company_count_midnig")	== null ? "" 			: sComp.get(i).get("company_count_midnig") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("trust_code")	== null ? "" 				: sComp.get(i).get("trust_code") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("trust_name")	== null ? "" 				: sComp.get(i).get("trust_name") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("trust_addr")	== null ? "" 				: sComp.get(i).get("trust_addr") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("trust_tel")	== null ? "" 					: sComp.get(i).get("trust_tel") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("secu_no")	== null ? "" 					: sComp.get(i).get("secu_no") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("regi_date")	== null ? "" 					: sComp.get(i).get("regi_date") ) +"\",");
			result.append("\"" + ( sComp.get(i).get("register")	== null ? "" 					: sComp.get(i).get("register") ) +"\"");
			result.append("]}");		
		}
			
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));
	}
	
	public ActionForward mComp(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao = new basicDao();

		String code_pers            = StringUtil.nullToStr("", request.getParameter("mCode_pers")).trim();
		String comp_seq             = StringUtil.nullToStr("", request.getParameter("comp_seq")).trim();
		String company_name         = StringUtil.nullToStr("", request.getParameter("cName")).trim();
		String code_post            = StringUtil.nullToStr("", request.getParameter("cPost")).replace("-", "").trim();
		String company_add          = StringUtil.nullToStr("", request.getParameter("cAddr")).trim();
		String company_add_detail   = StringUtil.nullToStr("", request.getParameter("cAddrd")).trim();
		String company_tel          = StringUtil.nullToStr("", request.getParameter("cTel")).trim();
		String company_fax          = StringUtil.nullToStr("", request.getParameter("cFax")).trim();
		String pers_in_dt           = StringUtil.nullToStr("", request.getParameter("in_dt")).trim();
		String pers_out_dt          = StringUtil.nullToStr("", request.getParameter("out_dt")).trim();
		String code_use             = StringUtil.nullToStr("", request.getParameter("cUse")).trim();
		String job_dept             = StringUtil.nullToStr("", request.getParameter("cDept")).trim();
		String job_level_code       = StringUtil.nullToStr("", request.getParameter("cLevel")).trim();
		String job_line_code        = StringUtil.nullToStr("", request.getParameter("cLine")).trim();
		String lic_pay              = StringUtil.nullToStr("", request.getParameter("lPay")).replace(",", "").trim();
		String year_pay             = StringUtil.nullToStr("", request.getParameter("yPay")).replace(",", "").trim();
		String company_sick_bad     = StringUtil.nullToStr("", request.getParameter("cBad")).trim();
		String company_meal         = StringUtil.nullToStr("", request.getParameter("cMeal")).replace(",", "").trim();
		String join_con             = StringUtil.nullToStr("", request.getParameter("cCom")).trim();
		String join_cook            = StringUtil.nullToStr("", request.getParameter("cCook")).trim();
		String primary_flag         = StringUtil.nullToStr("", request.getParameter("cPrime")).trim();
		String pers_multy           = StringUtil.nullToStr("", request.getParameter("cMulty")).trim();
		String company_count_mom    = StringUtil.nullToStr("", request.getParameter("cMom")).trim();
		String company_count_lunch  = StringUtil.nullToStr("", request.getParameter("cLunch")).trim();
		String company_count_dinner = StringUtil.nullToStr("", request.getParameter("cDinner")).trim();
		String company_count_midnig = StringUtil.nullToStr("", request.getParameter("cMid")).trim();
		String trust_code           = StringUtil.nullToStr("", request.getParameter("cTcode")).trim();
		String trust_name           = StringUtil.nullToStr("", request.getParameter("cTName")).trim();
		String trust_addr           = StringUtil.nullToStr("", request.getParameter("cTaddr")).trim();
		String trust_tel            = StringUtil.nullToStr("", request.getParameter("cTtel")).trim();
		String secu_no              = StringUtil.nullToStr("", request.getParameter("cSecu")).trim();
		String register             = StringUtil.nullToStr("", request.getParameter("register")).trim();

		// map에 값 넣기
		map.put("code_pers",              code_pers);
		map.put("comp_seq",               comp_seq);
		map.put("company_name",           company_name);
		map.put("code_post",              code_post);
		map.put("company_add",            company_add);
		map.put("company_add_detail",     company_add_detail);
		map.put("company_tel",            company_tel);
		map.put("company_fax",            company_fax);
		map.put("pers_in_dt",             pers_in_dt);
		map.put("pers_out_dt",            pers_out_dt);
		map.put("code_use",               code_use);
		map.put("job_dept",               job_dept);
		map.put("job_level_code",         job_level_code);
		map.put("job_line_code",          job_line_code);
		map.put("lic_pay",                lic_pay);
		map.put("year_pay",               year_pay);
		map.put("company_sick_bad",       company_sick_bad);
		map.put("company_meal",           company_meal);
		map.put("join_con",               join_con);
		map.put("join_cook",              join_cook);
		map.put("primary_flag",           primary_flag);
		map.put("pers_multy",             pers_multy);
		map.put("company_count_mom",      company_count_mom);
		map.put("company_count_lunch",    company_count_lunch);
		map.put("company_count_dinner",   company_count_dinner);
		map.put("company_count_midnig",   company_count_midnig);
		map.put("trust_code",             trust_code);
		map.put("trust_name",             trust_name);
		map.put("trust_addr",             trust_addr);
		map.put("trust_tel",              trust_tel);
		map.put("secu_no",                secu_no);
		map.put("register",               register);

		String errMsg = "";

		// 필수값 null/빈값 체크
		if (code_pers == null || "".equals(code_pers)) {
		    // 필요에 따라 메시지 조정
		    errMsg = "필수값(code_pers)이 없습니다. 저장되지 않았습니다.";
		} else {
		    try {
		        // primary_flag 비교 시 NPE 방지: "1".equals(primary_flag)
		        if ("1".equals(primary_flag)) {
		            int uCompPrime = dao.uCompPrime(map);
		            // 필요하면 반환값 체크
		        }
		        int uComp = dao.uComp(map);
		        // 추가 DAO 호출이 필요하면 여기서 처리 (예외 처리 포함)
		        resetToken(request);
		        errMsg = "저장했습니다.";
		    } catch (Exception e) {
		        // 실제로는 로거로 남기는 것이 좋음
		        e.printStackTrace();
		        errMsg = "저장 중 오류가 발생했습니다. (" + e.getMessage() + ")";
		    }
		}

		try {
		    request.setAttribute("errMsg", URLEncoder.encode(errMsg, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    // 거의 발생하지 않음. 인코딩 실패 시 원본 메시지 설정
		    request.setAttribute("errMsg", errMsg);
		}

		// map2에 pCode 넣기 (null 안전하게)
		map2.put("pCode", code_pers == null ? "" : code_pers);

		List<Map> memInfo = null;
		List<Map> defcomp = null;
		try {
		    memInfo = dao.memInfo1(map2);
		    defcomp = dao.defComp(map2);
		} catch (Exception e) {
		    e.printStackTrace();
		    // 실패 시 빈 리스트로 설정 (null로 두지 않음)
		    memInfo = memInfo == null ? new ArrayList<Map>() : memInfo;
		    defcomp = defcomp == null ? new ArrayList<Map>() : defcomp;
		}

		request.setAttribute("result", memInfo);
		request.setAttribute("defcomp", defcomp);
		request.setAttribute("pCode", code_pers);

		return (mapping.findForward("pop1"));

	}
	
public ActionForward iComp(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception {
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao=new basicDao();
		
		String code_pers			=StringUtil.nullToStr("", request.getParameter("mCode_pers"));		
		String company_name			=StringUtil.nullToStr("", request.getParameter("cName"));
	    String code_post			=StringUtil.nullToStr("", request.getParameter("cPost")).replace("-",""	);
	    String company_add			=StringUtil.nullToStr("", request.getParameter("cAddr"));
	    String company_add_detail	=StringUtil.nullToStr("", request.getParameter("cAddrd"));
	    String company_tel			=StringUtil.nullToStr("", request.getParameter("cTel"));
	    String company_fax			=StringUtil.nullToStr("", request.getParameter("cFax"));
	    String code_part			=StringUtil.nullToStr("", request.getParameter("code_part"));
	    String code_great			=StringUtil.nullToStr("", request.getParameter("code_great"));
	    String code_small			=StringUtil.nullToStr("", request.getParameter("code_small"));
	    String pers_in_dt			=StringUtil.nullToStr("", request.getParameter("in_dt"));
	    String pers_out_dt			=StringUtil.nullToStr("", request.getParameter("out_dt"));
	    String code_use				=StringUtil.nullToStr("", request.getParameter("cUse"));
	    String job_dept				=StringUtil.nullToStr("", request.getParameter("cDept"));
	    String job_level_code		=StringUtil.nullToStr("", request.getParameter("cLevel"));
	    String job_line_code		=StringUtil.nullToStr("", request.getParameter("cLine"));
	    String lic_pay				=StringUtil.nullToStr("", request.getParameter("lPay")).replace(",",""	);
	    String year_pay				=StringUtil.nullToStr("", request.getParameter("yPay")).replace(",",""	);
	    String company_sick_bad		=StringUtil.nullToStr("", request.getParameter("cBad"));
	    String company_meal			=StringUtil.nullToStr("", request.getParameter("cMeal")).replace(",",""	);
	    String join_con				=StringUtil.nullToStr("", request.getParameter("cCom"));
	    String join_cook			=StringUtil.nullToStr("", request.getParameter("cCook"));
	    String primary_flag			=StringUtil.nullToStr("", request.getParameter("cPrime"));
	    String pers_multy			=StringUtil.nullToStr("", request.getParameter("cMulty"));
	    String company_count_mom	=StringUtil.nullToStr("", request.getParameter("cMom"));
	    String company_count_lunch	=StringUtil.nullToStr("", request.getParameter("cLunch"));
	    String company_count_dinner	=StringUtil.nullToStr("", request.getParameter("cDinner"));	
	    String company_count_midnig	=StringUtil.nullToStr("", request.getParameter("cMid"));
	    String trust_code			=StringUtil.nullToStr("", request.getParameter("cTcode"));
	    String trust_name			=StringUtil.nullToStr("", request.getParameter("cTName"));
	    String trust_addr			=StringUtil.nullToStr("", request.getParameter("cTaddr"));
	    String trust_tel			=StringUtil.nullToStr("", request.getParameter("cTtel"));
	    String secu_no				=StringUtil.nullToStr("", request.getParameter("cSecu"));
	    String register				=StringUtil.nullToStr("", request.getParameter("register"));
		
		
		map.put("code_pers", 			code_pers);
		map.put("company_name", 		company_name);
	    map.put("code_post", 			code_post);
	    map.put("company_add", 			company_add);
	    map.put("company_add_detail", 	company_add_detail);
	    map.put("company_tel", 			company_tel);
	    map.put("company_fax", 			company_fax);
	    map.put("code_part", 			code_part);
	    map.put("code_great", 			code_great);
	    map.put("code_small", 			code_small);
	    map.put("pers_in_dt", 			pers_in_dt);
	    map.put("pers_out_dt", 			pers_out_dt);
	    map.put("code_use", 			code_use);
	    map.put("job_dept", 			job_dept);
	    map.put("job_level_code", 		job_level_code);
	    map.put("job_line_code", 		job_line_code);
	    map.put("lic_pay", 				lic_pay);
	    map.put("year_pay", 			year_pay);
	    map.put("company_sick_bad", 	company_sick_bad);
	    map.put("company_meal", 		company_meal);
	    map.put("join_con", 			join_con);
	    map.put("join_cook", 			join_cook);
	    map.put("primary_flag", 		primary_flag);
	    map.put("pers_multy", 			pers_multy);
	    map.put("company_count_mom",	company_count_mom);
	    map.put("company_count_lunch",	company_count_lunch);
	    map.put("company_count_dinner",	company_count_dinner);
	    map.put("company_count_midnig",	company_count_midnig);
	    map.put("trust_code", 			trust_code);
	    map.put("trust_name", 			trust_name);
	    map.put("trust_addr", 			trust_addr);
	    map.put("trust_tel", 			trust_tel);
	    map.put("secu_no", 				secu_no);
	    map.put("register", 			register);
		
	    String errMsg="";
	    if("".equals(code_pers)){
			errMsg="이미 저장했습니다.";	
		}else{
			if(primary_flag.equals("1")){
				int uCompPrime=dao.uCompPrime(map);
			}
			List<Map> iComp=dao.iComp(map);
			//int uComp=dao.uComp(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
		
	    map2.put("pCode",code_pers);
	    List<Map> memInfo=dao.memInfo1(map2);
		//List<Map> code=dao.getCode(map2);		
		List<Map> defcomp=dao.defComp(map2);		
		request.setAttribute("result",memInfo);
		//request.setAttribute("code",code);
		request.setAttribute("defcomp", defcomp);
		request.setAttribute("pCode", code_pers);
		return (mapping.findForward("pop1"));
	}
	
	//팝업 메뉴3. 회비입출금
	public ActionForward bank(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
		map.put("code_pers",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo1(map);
		List<Map> code2=dao.getCode2(map);
		List<Map> dusH=dao.sDuesHSeq(map);	
		List<Map> smd=dao.sSumDues2(map);
		int pm=0;
		for(int i=0;i<smd.size();i++){
			if(smd.get(i).get("dues_gubun").toString().equals("1")&&smd.get(i).get("code_bran").toString().equals(memInfo.get(0).get("code_bran").toString())&&smd.get(i).get("code_member").toString().equals("85")){
				pm=(int)(Float.parseFloat(smd.get(i).get("sum_dues").toString()));
			}				
		}
		request.setAttribute("pm", pm);
		JSONArray jsoncode=JSONArray.fromObject(code2);
		JSONArray subdues=JSONArray.fromObject(dao.sSubDues2(map));
		JSONArray sumdues=JSONArray.fromObject(dao.sSumDues2(map));
		request.setAttribute("result",memInfo);
		request.setAttribute("code2", code2);
		request.setAttribute("jsoncode", jsoncode);
		request.setAttribute("subdues", subdues);
		request.setAttribute("sumdues", sumdues);
		request.setAttribute("dusH", dusH);
		JSONArray jdusH=JSONArray.fromObject(dusH);
		request.setAttribute("jdusH", jdusH);
		return (mapping.findForward("pop2"));		
	}
			
	public ActionForward bankList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
			
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
						
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
				
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sBankCnt=dao.sBankCnt(map);
		ncount = ((Integer)(sBankCnt.get(0).get("cnt"))).intValue();

		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;

		//전체 자료 가져오기
		List<Map> sBank=dao.sBank(map);
						
		StringBuffer result = new StringBuffer();
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0;i<sBank.size();i++){
			if(i>0) {
				result.append(",");
			}
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sBank.get(i).get("pres_let_dt")      == null ? ""   : sBank.get(i).get("pres_let_dt") )      +"\",");
			result.append("\"" + ( sBank.get(i).get("code_inout_gubun") == null ? ""   : sBank.get(i).get("code_inout_gubun") ) +"\",");
			result.append("\"" + ( sBank.get(i).get("iog_name")         == null ? ""   : sBank.get(i).get("iog_name") )         +"\",");
			result.append("\"" + ( sBank.get(i).get("dues_gubun")       == null ? ""   : sBank.get(i).get("dues_gubun") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("dg_name")          == null ? ""   : sBank.get(i).get("dg_name") )          +"\",");
			result.append("\"" + ( sBank.get(i).get("code_member")      == null ? ""   : sBank.get(i).get("code_member") )      +"\",");
			result.append("\"" + ( sBank.get(i).get("cm_name")          == null ? ""   : sBank.get(i).get("cm_name") )          +"\",");
			result.append("\"" + ( sBank.get(i).get("pres_money")       == null ? ""   : sBank.get(i).get("pres_money") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("code_receipt")     == null ? ""   : sBank.get(i).get("code_receipt") )     +"\",");
			result.append("\"" + ( sBank.get(i).get("rc_name")          == null ? ""   : sBank.get(i).get("rc_name") )          +"\",");
			result.append("\"" + ( sBank.get(i).get("code_pay_flag")    == null ? ""   : sBank.get(i).get("code_pay_flag") )    +"\",");
			result.append("\"" + ( sBank.get(i).get("pf_name")          == null ? ""   : sBank.get(i).get("pf_name") )          +"\",");
			result.append("\"" + ( sBank.get(i).get("bank_name")        == null ? ""   : sBank.get(i).get("bank_name") )        +"\",");
			result.append("\"" + ( sBank.get(i).get("auth_start")       == null ? ""   : sBank.get(i).get("auth_start") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("auth_end")         == null ? ""   : sBank.get(i).get("auth_end") )         +"\",");
			result.append("\"" + ( sBank.get(i).get("conform_yn")       == null ? ""   : sBank.get(i).get("conform_yn") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("cf_name")          == null ? ""   : sBank.get(i).get("cf_name") )          +"\",");
			result.append("\"" + ( sBank.get(i).get("conform_dt")       == null ? ""   : sBank.get(i).get("conform_dt") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("incom_flag")       == null ? ""   : sBank.get(i).get("incom_flag") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("if_name")          == null ? ""   : sBank.get(i).get("if_name") )          +"\",");
			result.append("\"" + ( sBank.get(i).get("register")         == null ? ""   : sBank.get(i).get("register") )         +"\",");
			result.append("\"" + ( sBank.get(i).get("dues_h_seq")       == null ? ""   : sBank.get(i).get("dues_h_seq") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("dues_b_seq")       == null ? ""   : sBank.get(i).get("dues_b_seq") )       +"\",");
			result.append("\"" + ( sBank.get(i).get("duesDelYn")        == null ? ""   : sBank.get(i).get("duesDelYn") )        +"\"");
			result.append("]}");
		}
				
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));
	}
	
	
	public ActionForward ccancel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao=new basicDao();

		String code_pers	=StringUtil.nullToStr("0", request.getParameter("mCode_pers"));
		String dues_gubun	=StringUtil.nullToStr("0", request.getParameter("dGubun"));
		String dues_h_seq	=StringUtil.nullToStr("100", request.getParameter("dues_h_seq"));
		String dues_b_seq 	=StringUtil.nullToStr("100", request.getParameter("dues_b_seq"));
		
		System.out.println("code_pers===>"+code_pers+"    dues_gubun===>"+dues_gubun+"    dues_h_seq===>"+dues_h_seq+"    dues_b_seq===>"+dues_b_seq);
		
		map.put("code_pers", 		code_pers);
		map.put("dues_gubun", 		dues_gubun);
		map.put("dues_h_seq", 		dues_h_seq);
		map.put("dues_b_seq", 		dues_b_seq);
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			int canDuesH=dao.canDuesH(map);
			int canDuesB=dao.canDuesB(map);
			int canDuesP=dao.canDuesP(map);
			
			List<Map> lastCnnameList=dao.sLastCmname(map);
			if(lastCnnameList!=null&&lastCnnameList.size()>0){
				String lastCnname = (String)lastCnnameList.get(0).get("lastcmname");
				String code_member = (String)lastCnnameList.get(0).get("code_member");
				if(!StringUtil.NVL(code_member).equals("")){
					map.put("code_member", code_member);
					int lastUp = dao.uLastCmname(map);
				}
			}
			
			resetToken(request);
			errMsg="선택하신 항목을 인증 취소했습니다.";
		}
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));

		map2.put("pCode",code_pers);
		map2.put("code_pers",code_pers);
		List<Map> memInfo=dao.memInfo1(map2);		
		List<Map> code2=dao.getCode2(map2);
		List<Map> dusH=dao.sDuesHSeq(map2);
		JSONArray jsoncode=JSONArray.fromObject(code2);
		JSONArray subdues=JSONArray.fromObject(dao.sSubDues2(map));
		JSONArray sumdues=JSONArray.fromObject(dao.sSumDues2(map));
		List<Map> smd=dao.sSumDues2(map);
		int pm=0;
		for(int i=0;i<smd.size();i++){
			if(smd.get(i).get("dues_gubun").toString().equals("1")&&smd.get(i).get("code_bran").toString().equals(memInfo.get(0).get("code_bran").toString())&&smd.get(i).get("code_member").toString().equals("-1")){
				pm=(int)(Float.parseFloat(smd.get(i).get("sum_dues").toString()));
			}				
		}
		request.setAttribute("pm", pm);
		request.setAttribute("result",memInfo);
		request.setAttribute("code2", code2);
		request.setAttribute("jsoncode", jsoncode);	
		request.setAttribute("subdues", subdues);
		request.setAttribute("sumdues", sumdues);
		request.setAttribute("pCode", code_pers);
		request.setAttribute("dusH", dusH);
		return (mapping.findForward("pop2"));
	}
	
	public ActionForward delBank(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("################  delBank  ################");
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao = new basicDao();

		String code_pers	= StringUtil.nullToStr("", request.getParameter("mCode_pers"));
		String dues_gubun	= StringUtil.nullToStr("", request.getParameter("dGubun"));
		String dues_h_seq	= StringUtil.nullToStr("", request.getParameter("dues_h_seq"));
		String dues_b_seq 	= StringUtil.nullToStr("", request.getParameter("dues_b_seq"));
		
		logger.debug("code_pers===> "+code_pers+"    dues_gubun ===> "+dues_gubun+"    dues_h_seq ===> "+dues_h_seq+"    dues_b_seq ===> "+dues_b_seq);
		
		map.put("code_pers", 		code_pers);
		map.put("dues_gubun", 		dues_gubun);
		map.put("dues_h_seq", 		dues_h_seq);
		map.put("dues_b_seq", 		dues_b_seq);
		
		String errMsg = "";
		
		if(!isTokenValid(request)){
			errMsg = "이미 삭제했습니다.";
			
		} else {
			
			// 1. dues_p 테이블에 데이터 존재유무 체크 (있으면 삭제 불가 , 없으면 PASS)
			List<Map> getChkDuesPInfo = null;
			
			getChkDuesPInfo = dao.chkDuesP(map);
			logger.debug("getChkDuesPInfo.size ===> " + getChkDuesPInfo.size());
			
			if(getChkDuesPInfo.size() > 0) {
				// 있으면 ERROR -> 삭제 불가 
				errMsg = "회비처리가 완료되어 삭제 불가능합니다.";
			} else {

				List<Map> getChkDuesBInfo = null;
				
				getChkDuesBInfo = dao.chkDuesB(map);
				logger.debug("getChkDuesBInfo.size ===> " + getChkDuesBInfo.size());
				
				if(getChkDuesBInfo.size() > 1) {
					// 2개 이상 있으면 삭제 불가(확인 필요)
					errMsg = "회비내역이 여러건으로 확인이 필요합니다.\\r\\n - code_pers : " + code_pers + "\\r\\n - dues_gubun : " + dues_gubun + "\\r\\n - dues_h_seq : " + dues_h_seq ;
					
				} else {
					
					// 2. 삭제 - dues_h_tbl
					int cntDelDuesH = dao.deleteDuesH(map);
					
					// 3. 삭제 - dues_b_tbl
					int cntDelDuesB = dao.deleteDuesB(map);
					
					logger.debug("cntDelDuesH ===> " + cntDelDuesH + " // cntDelDuesH : " + cntDelDuesB);
					
					resetToken(request);
					errMsg = "선택하신 항목의 회비입출내역이 삭제되었습니다.";
				}
			}
		}
		
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));

		map2.put("pCode",code_pers);
		map2.put("code_pers",code_pers);
		
		List<Map> memInfo=dao.memInfo1(map2);		
		List<Map> code2=dao.getCode2(map2);
		List<Map> dusH=dao.sDuesHSeq(map2);
		JSONArray jsoncode=JSONArray.fromObject(code2);
		JSONArray subdues=JSONArray.fromObject(dao.sSubDues2(map));
		JSONArray sumdues=JSONArray.fromObject(dao.sSumDues2(map));
		List<Map> smd=dao.sSumDues2(map);
		int pm=0;
		for(int i=0;i<smd.size();i++){
			if(smd.get(i).get("dues_gubun").toString().equals("1")&&smd.get(i).get("code_bran").toString().equals(memInfo.get(0).get("code_bran").toString())&&smd.get(i).get("code_member").toString().equals("-1")){
				pm=(int)(Float.parseFloat(smd.get(i).get("sum_dues").toString()));
			}				
		}
		request.setAttribute("pm", pm);
		request.setAttribute("result",memInfo);
		request.setAttribute("code2", code2);
		request.setAttribute("jsoncode", jsoncode);	
		request.setAttribute("subdues", subdues);
		request.setAttribute("sumdues", sumdues);
		request.setAttribute("pCode", code_pers);
		request.setAttribute("dusH", dusH);
		return (mapping.findForward("pop2"));
	}
	
	public ActionForward mBank(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		Map map3 = new HashMap();
		basicDao dao=new basicDao();
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		Calendar cal=Calendar.getInstance();
		
		String code_pers		=StringUtil.nullToStr("", request.getParameter("mCode_pers"));
		String gubun			=StringUtil.nullToStr("", request.getParameter("gubun"));
		String code_inout_gubun	=StringUtil.nullToStr("", request.getParameter("iogGubun"));
		String dues_gubun		=StringUtil.nullToStr("", request.getParameter("dGubun"));
		String pres_money		=StringUtil.nullToStr("", request.getParameter("pMoney"));
		String code_pay_flag	=StringUtil.nullToStr("", request.getParameter("pFlag"));
		String bank_name		=StringUtil.nullToStr("", request.getParameter("bName"));
		String code_member		=StringUtil.nullToStr("", request.getParameter("cMember"));
		String conform_yn		=StringUtil.nullToStr("", request.getParameter("cf_yn"));
		String register			=StringUtil.nullToStr("", request.getParameter("register"));
		String code_receipt 	=StringUtil.nullToStr("", request.getParameter("rc"));
		String code_bran		=StringUtil.nullToStr("", request.getParameter("code_bran"));
		String code_sub			=StringUtil.nullToStr("", request.getParameter("code_sub"));
		String pres_let_dt 		=StringUtil.nullToStr("", request.getParameter("presDt"));
		String start_dt			=StringUtil.nullToStr("0", request.getParameter("authDt"));
		String mem_dues 		="";
		String mStart			=StringUtil.nullToStr("", request.getParameter("mStart"));
		String mChange			=StringUtil.nullToStr("", request.getParameter("mChange"));
		map.put("code_pers", 		code_pers);
	
		/* 1. 입금인지 출금인지 우선 체크
		 * 2. 출금의 경우 dues_h_tbl에서  가장 최근의 row를 select해서 regi_dt가 존재할 경우 이미 분배처리가 끝난 것이므로 출금 못함. (클라이언트단에서 처리 완료)
		 * 3. 출금후 dues_h_tbl의 incom_flag가 Y였을때, sum_dues(합계금액)이 mem_dues보다 작아지면 incom_flag를 N으로 변경
		 * 4. 입금의 경우 dues_h_tbl의 가장 최근의 row를 select해와서 incom_flag를 체크
		 * 4-1. incom_flag가 Y면 읽어온 dues_h_seq에 +1, Null이면 dues_h_seq는 1, N이면 dues_h_seq는 읽어온 값 그대로
		 * 4-2. 사용자가 입력한 유효시작일이 있다면 비교후 처리 
		 * 
		 * 주의)선인증일때 dues_h_seq에 금액반영하지 않는다. (당연히 완납으로 변경도 안됨)
		 */
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			if(code_inout_gubun.equals("1")){  //입출구분이 입금인 경우
				if("yes".equals(mStart)){		//확인여부 항목이 확인인 경우는 유효시작일만 변경 가능	
					String tmpDgubun	=StringUtil.nullToStr("", request.getParameter("tmpDgubun"));
					String dues_h_seq 	=StringUtil.nullToStr("", request.getParameter("dues_h_seq"));	
	
					Date def=sdf.parse(start_dt);
					cal.setTime(def);
					String auth_end="";
					if("1".equals(tmpDgubun)||"3".equals(tmpDgubun)){  
						cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
						cal.add(cal.DATE, -1); //1일을 뺀다.
						auth_end=sdf.format(cal.getTime());
					}else if("2".equals(tmpDgubun)){
						auth_end="99991231"; //평생회원의 경우 유효 만료일을 9999년 12월 31일로
				////////////////////////////////CMS추가///////////////////////////
					}else if("4".equals(tmpDgubun)){
						//39(C신규취업회원(년)), 40(C기취업회원(년))인 경우 인증일 1년
						if("39".equals(code_member) || "40".equals(code_member)){
							cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
							cal.add(cal.DATE, -1); //1일을 뺀다.
							auth_end=sdf.format(cal.getTime());
						}else{
							cal.add(cal.MONTH, 1);//받아온 start_dt에 1달을 더한 값을 구한다.
							cal.add(cal.DATE, -1); //1일을 뺀다.
							auth_end=sdf.format(cal.getTime());
						}
                ////////////////////////////////////////////////////////////////
					}			
					map.put("dues_h_seq", dues_h_seq);
					map.put("dues_gubun", 	tmpDgubun);
					map.put("auth_start", start_dt);
					map.put("auth_end", auth_end);
	
					int uDuesH3=dao.uDuesH3(map);
				}else{
					map.put("dues_gubun", 	dues_gubun);
					if("insert".equals(gubun)){
						List<Map> sDuesHSeq=dao.sDuesHSeq(map);  //dues_h_tbl 테이블에서 dues_h_seq를 select
						String dHSeq	= "";
						String iFlag		= "";
						String auth		= "";
						System.out.println("sDuesHSeq.size()====>"+sDuesHSeq.size());
						if(sDuesHSeq.size()>0){
							dHSeq	=sDuesHSeq.get(0).get("dues_h_seq").toString();
							iFlag		=sDuesHSeq.get(0).get("incom_flag").toString();
							auth		=sDuesHSeq.get(0).get("auth_end").toString();
							System.out.println("dHSeq===>"+dHSeq+"    iFlag====>"+iFlag+"   auth====>"+auth);
						}else{
							dHSeq	="1";
							iFlag		="";
							auth		="0";
						}
						map.put("code_bran", 		code_bran);
						map.put("code_sub", 		code_sub);
						map.put("code_member", 	code_member);
						
						//dues_gubun(회비구분)이 1, 2, 4인 경우엔 회비 테이블에서 sum_dues를, 그 이외의 경우 산하단체회비 테이블에서 sub_dues를 select
						//2024.03.25 산하단체도 추가
						List<Map> sumDues=dao.sSumDues(map);
						mem_dues=sumDues.get(0).get("sum_dues").toString();
						System.out.println("mem_dues====>"+mem_dues);
						/*if("1".equals(dues_gubun)||"2".equals(dues_gubun)||"4".equals(dues_gubun)){
							List<Map> sumDues=dao.sSumDues(map);
							mem_dues=sumDues.get(0).get("sum_dues").toString();
							System.out.println("mem_dues====>"+mem_dues);
						}else{
							List<Map> subDues=dao.sSubDues(map);
							mem_dues=subDues.get(0).get("sub_dues").toString();
						}*/
				
						if("Y".equals(iFlag)||"".equals(iFlag)||"D".equals(iFlag)){
							//incom_flag가 Y이거나 null인 경우 받아온 dues_h_seq로 dues_h_tbl에 insert 
							map.put("dues_h_seq", dHSeq);
							map.put("mem_dues", mem_dues);
							map.put("incom_flag", "N");
							System.out.println("start_dt====================>"+start_dt);
							Date def=sdf.parse(start_dt);
							cal.setTime(def);
							String auth_end="";
							if("1".equals(dues_gubun)||"3".equals(dues_gubun)){  
								cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
								cal.add(cal.DATE, -1); //1일을 뺀다.
								auth_end=sdf.format(cal.getTime());
								System.out.println("auth_end====================>"+auth_end);
							}else if("2".equals(dues_gubun)){
								auth_end="99991231"; //평생회원의 경우 유효 만료일을 9999년 12월 31일로
								////////////////////////////////CMS추가///////////////////////////
							}else if("4".equals(dues_gubun)){
								//39(C신규취업회원(년)), 40(C기취업회원(년))인 경우 인증일 1년
								if("39".equals(code_member) || "40".equals(code_member)){
									cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
									cal.add(cal.DATE, -1); //1일을 뺀다.
									auth_end=sdf.format(cal.getTime());
								}else{
									cal.add(cal.MONTH, 1);//받아온 start_dt에 1달을 더한 값을 구한다.
									cal.add(cal.DATE, -1); //1일을 뺀다.
									auth_end=sdf.format(cal.getTime());
								}
		                        ////////////////////////////////////////////////////////////////
							}	
							
							System.out.println("start_dt====================>"+start_dt);
							System.out.println("auth_end====================>"+auth_end);
							map.put("auth_start", start_dt);
							map.put("auth_end", auth_end);
	
							List<Map> iDuesH=dao.iDuesH(map); 
							
							//dues_h_tbl에 insert후 jsp에서 입력받은 값으로 dues_b_tbl에 insert 
							map.put("dues_b_seq", 1);
							map.put("pres_let_dt", pres_let_dt);
							map.put("code_inout_gubun", code_inout_gubun);
							map.put("bank_name", bank_name);
							map.put("code_receipt", code_receipt);
							map.put("pres_money", pres_money);
							map.put("code_pay_flag", code_pay_flag);
							map.put("register", register);
							map.put("conform_yn", conform_yn);
							if("2".equals(conform_yn)||"3".equals(conform_yn)){					
								String date=sdf.format(new java.util.Date());
								if("3".equals(conform_yn)){
									map.put("conform_dt", date);
								}else{
									map.put("conform_dt", " ");
								}								
								setAuthDate(code_pers,dues_gubun,dHSeq,pres_money,conform_yn);
							}else{
								map.put("conform_dt"," ");
							}
							List<Map> iDuesB=dao.iDuesB(map);
						}else{
							//incom_flag가 N인경우
							map.put("dues_h_seq", dHSeq);			
							map.put("pres_let_dt", pres_let_dt);
							map.put("code_inout_gubun", code_inout_gubun);
							map.put("bank_name", bank_name);
							map.put("code_receipt", code_receipt);
							map.put("pres_money", pres_money);
							map.put("code_pay_flag", code_pay_flag);
							map.put("register", register);
							map.put("conform_yn", conform_yn);
							if("2".equals(conform_yn)||"3".equals(conform_yn)){					
								String date=sdf.format(new java.util.Date());
								if("3".equals(conform_yn)){
									map.put("conform_dt", date);
								}else{
									map.put("conform_dt", " ");
								}
								setAuthDate2(code_pers,dues_gubun,dHSeq,pres_money,start_dt,conform_yn,code_member);
							}else{
								map.put("conform_dt"," ");
							}
							List<Map> iDuesB=dao.iDuesB2(map);
						}
					}else{ //그리드에 입력된 것 update
						if(mChange.equals("N")){ //회원구분 변경안됐을때
							String dues_h_seq =StringUtil.nullToStr("", request.getParameter("dues_h_seq"));
							String dues_b_seq =StringUtil.nullToStr("", request.getParameter("dues_b_seq"));
							
							map.put("dues_h_seq", dues_h_seq);			
							map.put("dues_b_seq", dues_b_seq);
							map.put("pres_let_dt", pres_let_dt);
							map.put("code_inout_gubun", code_inout_gubun);
							map.put("bank_name", bank_name);
							map.put("code_receipt", code_receipt);
							map.put("pres_money", pres_money);
							map.put("code_pay_flag", code_pay_flag);
							map.put("register", register);
							map.put("conform_yn", conform_yn);
							if("2".equals(conform_yn)||"3".equals(conform_yn)){
								String date=sdf.format(new java.util.Date());
								if("3".equals(conform_yn)){
									map.put("conform_dt", date);
								}else{
									map.put("conform_dt", " ");
								}
								setAuthDate2(code_pers,dues_gubun,dues_h_seq,pres_money,start_dt,conform_yn,code_member);
							}else{
								map.put("conform_dt"," ");
							}
							int uDuesB=dao.uDuesB(map);
						}else{//회원구분 변경됐을때
							String dues_h_seq =StringUtil.nullToStr("", request.getParameter("dues_h_seq"));
							String dues_b_seq =StringUtil.nullToStr("", request.getParameter("dues_b_seq"));
							
							map.put("code_bran", 		code_bran);
							map.put("code_sub", 		code_sub);
							map.put("code_member", 	code_member);
							
							//dues_gubun(회비구분)이 1, 2, 4인 경우엔 회비 테이블에서 sum_dues를, 그 이외의 경우 산하단체회비 테이블에서 sub_dues를 select
							//2024.03.25 산하단체도 추가
							List<Map> sumDues=dao.sSumDues(map);
							mem_dues=sumDues.get(0).get("sum_dues").toString();
							/*if("1".equals(dues_gubun)||"2".equals(dues_gubun)||"4".equals(dues_gubun)){
								List<Map> sumDues=dao.sSumDues(map);
								mem_dues=sumDues.get(0).get("sum_dues").toString();
							}else{
								List<Map> subDues=dao.sSubDues(map);
								mem_dues=subDues.get(0).get("sub_dues").toString();
							}*/
							map.put("mem_dues", mem_dues);	
							map.put("dues_h_seq", dues_h_seq);			
							int uDuesHmemDues=dao.uDuesHmemDues(map);
							
							map.put("dues_b_seq", dues_b_seq);
							map.put("pres_let_dt", pres_let_dt);
							map.put("code_inout_gubun", code_inout_gubun);
							map.put("bank_name", bank_name);
							map.put("code_receipt", code_receipt);
							map.put("pres_money", pres_money);
							map.put("code_pay_flag", code_pay_flag);
							map.put("register", register);
							map.put("conform_yn", conform_yn);
							if("2".equals(conform_yn)||"3".equals(conform_yn)){
								String date=sdf.format(new java.util.Date());
								if("3".equals(conform_yn)){
									map.put("conform_dt", date);
								}else{
									map.put("conform_dt", " ");
								}
								setAuthDate2(code_pers,dues_gubun,dues_h_seq,pres_money,start_dt,conform_yn,code_member);
							}else{
								map.put("conform_dt"," ");
							}
							int uDuesB=dao.uDuesB(map);
						}
						
					}
					//2013.01.15 추가 - 인증여부가 인증이고 입출금여부가 입금이며, 회비구분이 연회비 혹은 평생회비일때 
					//Person_M_TBL을 UPDATE, Person_M_history는 INSERT 
					if(conform_yn.equals("3")){
						map3.put("code_pers", code_pers);
						map3.put("pCode", code_pers);
						System.out.println("code_pers==========>"+code_pers);
						List<Map> memInfo=dao.memInfo(map3,true);
						
						//map3.put("code_member", code_member);
						map3.put("pers_name", memInfo.get(0).get("pers_name"));
	//						map3.put("pers_no", memInfo.get(0).get("pers_no")); // JUMIN_DEL
						map3.put("pers_birth", memInfo.get(0).get("pers_birth"));
						map3.put("lic_no", memInfo.get(0).get("lic_no"));
						map3.put("lic_print_dt", memInfo.get(0).get("lic_print_dt"));
						map3.put("code_add_gubun", memInfo.get(0).get("code_add_gubun"));
						map3.put("code_post", memInfo.get(0).get("code_post"));
						map3.put("pers_add", memInfo.get(0).get("pers_add"));
						map3.put("pers_add_detail", memInfo.get(0).get("pers_add_detail"));
						map3.put("pers_tel", memInfo.get(0).get("pers_tel"));
						map3.put("pers_hp", memInfo.get(0).get("pers_hp"));
						map3.put("email", memInfo.get(0).get("email"));
						map3.put("code_email_comp", memInfo.get(0).get("code_email_comp"));
						map3.put("code_sex", memInfo.get(0).get("code_sex"));
						map3.put("code_religion", memInfo.get(0).get("code_religion"));
						map3.put("code_marry", memInfo.get(0).get("code_marry"));
						map3.put("code_bran", memInfo.get(0).get("code_bran"));
						map3.put("code_big", memInfo.get(0).get("code_big"));
						map3.put("code_sect", memInfo.get(0).get("code_sect"));
						map3.put("code_scholar", memInfo.get(0).get("code_scholar"));
						map3.put("code_univer", memInfo.get(0).get("code_univer"));
						map3.put("code_school", memInfo.get(0).get("code_school"));
						map3.put("code_call", memInfo.get(0).get("code_call"));
						map3.put("code_place", memInfo.get(0).get("code_place"));
						map3.put("pers_career", memInfo.get(0).get("pers_career"));
						map3.put("certifi_view", memInfo.get(0).get("certifi_view"));
						//map3.put("code_sub", memInfo.get(0).get("code_sub"));
						map3.put("regi_dt", memInfo.get(0).get("regi_dt"));
						map3.put("kda_level", memInfo.get(0).get("kda_level"));
						map3.put("recommender", memInfo.get(0).get("recommender"));
						map3.put("recm_division", memInfo.get(0).get("recm_division"));
						map3.put("recm_hp", memInfo.get(0).get("recm_hp"));
						//map3.put("pers_state", memInfo.get(0).get("pers_state"));
						
						//2024.03.25 연회비, 평생회비, cms 인 경우
						if((dues_gubun.equals("1")||dues_gubun.equals("2")||dues_gubun.equals("4"))){
							map3.put("code_member", code_member);
							//소속분과 영양교사 / 학교영양사
							if (memInfo.get(0).get("code_big").equals("103") || memInfo.get(0).get("code_big").equals("104")) {
								// 회원상태 유효기간만료(협회&산하단체)
								if (memInfo.get(0).get("pers_state").equals("10") || memInfo.get(0).get("pers_state").equals("09")) {
									map3.put("pers_state", "09");	//유효기간만료(산하단체)
									map3.put("code_sub", "00");		//없음
								}else {
									map3.put("pers_state", "01");
									if (memInfo.get(0).get("code_big").equals("103")) {
										map3.put("code_sub", "01");	//전국영양교사회
									}else {
										map3.put("code_sub", "02");	//전국학교영양사회
									}
								}
								
							}else {
								map3.put("pers_state", "01");
								map3.put("code_sub", memInfo.get(0).get("code_sub"));
							}
						}else {	//산하단체인 경우
							map3.put("code_member", memInfo.get(0).get("code_member"));
							// 회원상태 유효기간만료(협회&산하단체)
							if (memInfo.get(0).get("pers_state").equals("10") || memInfo.get(0).get("pers_state").equals("05")) {
								map3.put("pers_state", "05");	//유효기간만료(협회)
								map3.put("code_sub", "00");		//없음
							}else {
								map3.put("pers_state", "01");
								if (memInfo.get(0).get("code_big").equals("103")) {
									map3.put("code_sub", "01");
								}else {
									map3.put("code_sub", "02");
								}
							}
						}
						
						map3.put("register", register);
						
						int uDuesCMem=dao.uDuesCMem(map3);
						List<Map> iDuesMemHis=dao.iDuesMemHis(map3);
					}
				}	
			}else if(code_inout_gubun.equals("2")){ //입출구분이 출금인 경우
				map.put("dues_gubun", 	dues_gubun);
				if("insert".equals(gubun)){ //신규 입력일때
					List<Map> sDuesHSeq=dao.sDuesHSeq(map);  //dues_h_tbl 테이블에서 dues_h_seq를 select
					
					String dHSeq	=sDuesHSeq.get(0).get("dues_h_seq").toString();
					String iFlag		=sDuesHSeq.get(0).get("incom_flag").toString();
					int astart		=Integer.parseInt(sDuesHSeq.get(0).get("auth_start").toString());
					
					if("Y".equals(iFlag)){
						int dseq=Integer.parseInt(dHSeq)-1;
						map.put("dues_h_seq", dseq);
						Date def=sdf.parse(start_dt);
						cal.setTime(def);
						String auth_end="";
						if("1".equals(dues_gubun)||"3".equals(dues_gubun)){  
							cal.add(cal.YEAR, 1);//받아온 auth_end에 1년을 더한 값을 구한다.
							cal.add(cal.DATE, -1); //1일을 뺀다.
							auth_end=sdf.format(cal.getTime());
						}else if("2".equals(dues_gubun)){
							auth_end="99991231"; //평생회원의 경우 유효 만료일을 9999년 12월 31일로
							////////////////////////////////CMS추가///////////////////////////
						}else if("4".equals(dues_gubun)){
							//39(C신규취업회원(년)), 40(C기취업회원(년))인 경우 인증일 1년
							if("39".equals(code_member) || "40".equals(code_member)){
								cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
								cal.add(cal.DATE, -1); //1일을 뺀다.
								auth_end=sdf.format(cal.getTime());
							}else{
								cal.add(cal.MONTH, 1);//받아온 start_dt에 1달을 더한 값을 구한다.
								cal.add(cal.DATE, -1); //1일을 뺀다.
								auth_end=sdf.format(cal.getTime());
							}
	                        ////////////////////////////////////////////////////////////////
						}	
						map.put("auth_start", start_dt);
						map.put("auth_end", auth_end);
						
						int uDuesH3=dao.uDuesH3(map);
	
						map.put("pres_let_dt", pres_let_dt);
						map.put("code_inout_gubun", code_inout_gubun);
						map.put("bank_name", bank_name);
						map.put("code_receipt", code_receipt);
						map.put("pres_money", pres_money);
						map.put("code_pay_flag", code_pay_flag);
						map.put("register", register);
						map.put("conform_yn", conform_yn);
						if("3".equals(conform_yn)){					
							String date=sdf.format(new java.util.Date());
							if("3".equals(conform_yn)){
								map.put("conform_dt", date);
							}else{
								map.put("conform_dt", " ");
							}
							setAuthDate3(code_pers,dues_gubun,dseq,pres_money,conform_yn,astart);
						}else{
							map.put("conform_dt"," ");
						}
						List<Map> iDuesB=dao.iDuesB2(map);													
					}else{
						int dseq=Integer.parseInt(dHSeq);
						map.put("dues_h_seq", dseq);
						Date def=sdf.parse(start_dt);
						cal.setTime(def);
						String auth_end="";
						if("1".equals(dues_gubun)||"3".equals(dues_gubun)){  
							cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
							cal.add(cal.DATE, -1); //1일을 뺀다.
							auth_end=sdf.format(cal.getTime());
						}else if("2".equals(dues_gubun)){
							auth_end="99991231"; //평생회원의 경우 유효 만료일을 9999년 12월 31일로
							////////////////////////////////CMS추가///////////////////////////
	     				}else if("4".equals(dues_gubun)){
	     					//39(C신규취업회원(년)), 40(C기취업회원(년))인 경우 인증일 1년
							if("39".equals(code_member) || "40".equals(code_member)){
								cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
								cal.add(cal.DATE, -1); //1일을 뺀다.
								auth_end=sdf.format(cal.getTime());
							}else{
								cal.add(cal.MONTH, 1);//받아온 start_dt에 1달을 더한 값을 구한다.
								cal.add(cal.DATE, -1); //1일을 뺀다.
								auth_end=sdf.format(cal.getTime());
							}
                        ////////////////////////////////////////////////////////////////
						}	
						map.put("auth_start", start_dt);
						map.put("auth_end", auth_end);
						
						int uDuesH3=dao.uDuesH3(map);
	
						map.put("pres_let_dt", pres_let_dt);
						map.put("code_inout_gubun", code_inout_gubun);
						map.put("bank_name", bank_name);
						map.put("code_receipt", code_receipt);
						map.put("pres_money", pres_money);
						map.put("code_pay_flag", code_pay_flag);
						map.put("register", register);
						map.put("conform_yn", conform_yn);
						if("3".equals(conform_yn)){					
							String date=sdf.format(new java.util.Date());
							if("3".equals(conform_yn)){
								map.put("conform_dt", date);
							}else{
								map.put("conform_dt", " ");
							}
							setAuthDate3(code_pers,dues_gubun,dseq,pres_money,conform_yn,astart);
						}else{
							map.put("conform_dt"," ");
						}
						List<Map> iDuesB=dao.iDuesB2(map);
					}
				}else{ //그리드에서 선택 후 변경일때
					if(mChange.equals("N")){
						int dues_h_seq =Integer.parseInt(StringUtil.nullToStr("", request.getParameter("dues_h_seq")));
						String dues_b_seq =StringUtil.nullToStr("", request.getParameter("dues_b_seq"));
						
						map.put("dues_h_seq", dues_h_seq);			
						map.put("dues_b_seq", dues_b_seq);
						map.put("pres_let_dt", pres_let_dt);
						map.put("code_inout_gubun", code_inout_gubun);
						map.put("bank_name", bank_name);
						map.put("code_receipt", code_receipt);
						map.put("pres_money", pres_money);
						map.put("code_pay_flag", code_pay_flag);
						map.put("register", register);
						map.put("conform_yn", conform_yn);
						if("3".equals(conform_yn)){
							String date=sdf.format(new java.util.Date());
							map.put("conform_dt", date);
							setAuthDate4(code_pers,dues_gubun,dues_h_seq,pres_money,conform_yn,start_dt,code_member);
						}else{
							map.put("conform_dt"," ");
						}
						int uDuesB=dao.uDuesB(map);
					}else{
						int dues_h_seq =Integer.parseInt(StringUtil.nullToStr("", request.getParameter("dues_h_seq")));
						String dues_b_seq =StringUtil.nullToStr("", request.getParameter("dues_b_seq"));
						
						map.put("code_bran", 		code_bran);
						map.put("code_sub", 		code_sub);
						map.put("code_member", 	code_member);
						
						//dues_gubun(회비구분)이 1, 2, 4인 경우엔 회비 테이블에서 sum_dues를, 그 이외의 경우 산하단체회비 테이블에서 sub_dues를 select
						if("1".equals(dues_gubun)||"2".equals(dues_gubun)||"4".equals(dues_gubun)){
							List<Map> sumDues=dao.sSumDues(map);
							mem_dues=sumDues.get(0).get("sum_dues").toString();
						}else{
							List<Map> subDues=dao.sSubDues(map);
							mem_dues=subDues.get(0).get("sub_dues").toString();
						}
						map.put("mem_dues", mem_dues);	
						map.put("dues_h_seq", dues_h_seq);			
						int uDuesHmemDues=dao.uDuesHmemDues(map);
						
						map.put("dues_b_seq", dues_b_seq);
						map.put("pres_let_dt", pres_let_dt);
						map.put("code_inout_gubun", code_inout_gubun);
						map.put("bank_name", bank_name);
						map.put("code_receipt", code_receipt);
						map.put("pres_money", pres_money);
						map.put("code_pay_flag", code_pay_flag);
						map.put("register", register);
						map.put("conform_yn", conform_yn);
						if("3".equals(conform_yn)){
							String date=sdf.format(new java.util.Date());
							map.put("conform_dt", date);
							setAuthDate4(code_pers,dues_gubun,dues_h_seq,pres_money,conform_yn,start_dt,code_member);
						}else{
							map.put("conform_dt"," ");
						}
						int uDuesB=dao.uDuesB(map);
					}					
				}
			}
		resetToken(request);
		errMsg="저장했습니다.";
		}
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));

		map2.put("pCode",code_pers);
		map2.put("code_pers",code_pers);
		List<Map> memInfo=dao.memInfo1(map2);		
		List<Map> code2=dao.getCode2(map2);
		List<Map> dusH=dao.sDuesHSeq(map2);
		JSONArray jsoncode=JSONArray.fromObject(code2);
		JSONArray subdues=JSONArray.fromObject(dao.sSubDues2(map));
		JSONArray sumdues=JSONArray.fromObject(dao.sSumDues2(map));
		List<Map> smd=dao.sSumDues2(map);
		int pm=0;
		for(int i=0;i<smd.size();i++){
			if(smd.get(i).get("dues_gubun").toString().equals("1")&&smd.get(i).get("code_bran").toString().equals(memInfo.get(0).get("code_bran").toString())&&smd.get(i).get("code_member").toString().equals("-1")){
				pm=(int)(Float.parseFloat(smd.get(i).get("sum_dues").toString()));
			}				
		}
		request.setAttribute("pm", pm);
		request.setAttribute("result",memInfo);
		request.setAttribute("code2", code2);
		request.setAttribute("jsoncode", jsoncode);	
		request.setAttribute("subdues", subdues);
		request.setAttribute("sumdues", sumdues);
		request.setAttribute("pCode", code_pers);
		request.setAttribute("dusH", dusH);
		return (mapping.findForward("pop2"));
	}
	
	public void setAuthDate(String code_pers,String dues_gubun,String dues_h_seq,String pres_money,String conform_yn) throws Exception{
		Map map=new HashMap();
		basicDao dao=new basicDao();
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		Calendar cal=Calendar.getInstance();
		
		map.put("code_pers", code_pers);
		if("3".equals(conform_yn)){  //conform_yn이 인증일때
			//산하단체회비 ‘인증’ 시 소속분과가 '영양교사:103' 인 경우 근무처정보 내 직렬을 '영양교시직:60' 으로 변경
			//산하단체회비 ‘인증’ 시 소속분과가 '학교영양사:104' 인 경우 근무처정보 내 직렬을 ‘학교회계직:70’ 으로 변경
			if("3".equals(dues_gubun)){
				int uCompany=dao.uCompany(map);
			}
			
			map.put("dues_gubun", dues_gubun);
			map.put("dues_h_seq", dues_h_seq);
			map.put("pres_money", pres_money);
	
			int uDuesH=dao.uDuesH(map);
			
			List<Map> sDuesH=dao.sDuesH(map);
			float sum_dues=Float.parseFloat(sDuesH.get(0).get("sum_dues").toString());
			float mem_dues=Float.parseFloat(sDuesH.get(0).get("mem_dues").toString());
		    String auth_start="";
		    
			if(sum_dues>=mem_dues){
				map.put("incom_flag", "Y");
				int uDuesH2=dao.uDuesH2(map);
				/*
				map.put("pers_state", "01");
				int uPersState=dao.uPersState(map);*/
			}
		}else{ //선인증일때
			map.put("pers_state", "07");
			int uPersState=dao.uPersState(map);
		}
	}
	
	public void setAuthDate2(String code_pers,String dues_gubun,String dues_h_seq,String pres_money,String start_dt,String conform_yn,String code_member) throws Exception{
		Map map=new HashMap();
		basicDao dao=new basicDao();
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		Calendar cal=Calendar.getInstance();
		
		map.put("code_pers", code_pers);
		if("3".equals(conform_yn)){  //conform_yn이 인증일때
			//산하단체회비 ‘인증’ 시 소속분과가 '영양교사:103' 인 경우 근무처정보 내 직렬을 '영양교시직:60' 으로 변경
			//산하단체회비 ‘인증’ 시 소속분과가 '학교영양사:104' 인 경우 근무처정보 내 직렬을 ‘학교회계직:70’ 으로 변경
			if("3".equals(dues_gubun)){
				int uCompany=dao.uCompany(map);
			}
			
			map.put("dues_gubun", dues_gubun);
			map.put("dues_h_seq", dues_h_seq);
			map.put("pres_money", pres_money);
			Date def=sdf.parse(start_dt);
			cal.setTime(def);
			String auth_end="";
			if("1".equals(dues_gubun)||"3".equals(dues_gubun)){  
				cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
				cal.add(cal.DATE, -1); //1일을 뺀다.
				auth_end=sdf.format(cal.getTime());
			}else if("2".equals(dues_gubun)){
				auth_end="99991231"; //평생회원의 경우 유효 만료일을 9999년 12월 31일로
        ////////////////////////////////CMS추가///////////////////////////
			}else if("4".equals(dues_gubun)){
				//39(C신규취업회원(년)), 40(C기취업회원(년))인 경우 인증일 1년
				if("39".equals(code_member) || "40".equals(code_member)){
					cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
					cal.add(cal.DATE, -1); //1일을 뺀다.
					auth_end=sdf.format(cal.getTime());
				}else{
					cal.add(cal.MONTH, 1);//받아온 start_dt에 1달을 더한 값을 구한다.
					cal.add(cal.DATE, -1); //1일을 뺀다.
					auth_end=sdf.format(cal.getTime());
				}
        ////////////////////////////////////////////////////////////////
			}					
			map.put("auth_start", start_dt);
			map.put("auth_end", auth_end);

			int uDuesH=dao.uDuesH4(map);
			
			List<Map> sDuesH=dao.sDuesH(map);
			float sum_dues=Float.parseFloat(sDuesH.get(0).get("sum_dues").toString());
			float mem_dues=Float.parseFloat(sDuesH.get(0).get("mem_dues").toString());
		    String auth_start="";
		    
			if(sum_dues>=mem_dues){
				map.put("incom_flag", "Y");
				int uDuesH2=dao.uDuesH2(map);
				/*
				map.put("pers_state", "01");
				int uPersState=dao.uPersState(map);*/
			}
		}else{ //선인증일때
			map.put("pers_state", "07");
			int uPersState=dao.uPersState(map);
		}
	}
	
	public void setAuthDate3(String code_pers,String dues_gubun,int dues_h_seq,String pres_money,String conform_yn,int astart) throws Exception{
		Map map=new HashMap();
		basicDao dao=new basicDao();
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		Calendar cal=Calendar.getInstance();
		
		map.put("code_pers", code_pers);
		if("3".equals(conform_yn)){  //conform_yn이 인증일때
			map.put("dues_gubun", dues_gubun);
			map.put("dues_h_seq", dues_h_seq);
			map.put("pres_money", pres_money);
			
			int uDuesH5=dao.uDuesH5(map);
			
			List<Map> sDuesH=dao.sDuesH(map);
			float sum_dues=Float.parseFloat(sDuesH.get(0).get("sum_dues").toString());
			float mem_dues=Float.parseFloat(sDuesH.get(0).get("mem_dues").toString());
		    String auth_start="";
		    
			if(sum_dues>=mem_dues){
				map.put("incom_flag", "Y");
				int uDuesH2=dao.uDuesH2(map);

			}else if(sum_dues<mem_dues&&sum_dues>0){
				map.put("incom_flag", "N");  //미완납으로 변경
				int uDuesH2=dao.uDuesH2(map);
	
				int uPersState=dao.uPersState(map); //회원상태를 유효기간 만료로 변경
			}else if(sum_dues<=0){
				map.put("incom_flag", "D");  
				int uDuesH2=dao.uDuesH2(map);
				
				int date=Integer.parseInt(sdf.format(new java.util.Date()));
				List<Map> sMaxAend=dao.sMaxAend(map);
								
				if(sMaxAend.get(0).get("auth_end")!=null){
					if(Integer.parseInt(sMaxAend.get(0).get("auth_end").toString())<date){  //오늘 날짜와 현재 dues_h_tbl의 유효시작일자를 비교
						map.put("pers_state", "05");  					
					}else{
						map.put("pers_state", "01");
					}
				}else{
					map.put("pers_state", "05");
				}
				
				int uPersState=dao.uPersState(map); //회원상태를 유효기간 만료로 변경
			}
		}
	}
	
	public void setAuthDate4(String code_pers,String dues_gubun,int dues_h_seq,String pres_money,String conform_yn,String start_dt,String code_member) throws Exception{
		Map map=new HashMap();
		basicDao dao=new basicDao();
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		Calendar cal=Calendar.getInstance();
		
		map.put("code_pers", code_pers);
		if("3".equals(conform_yn)){  //conform_yn이 인증일때
			map.put("dues_gubun", dues_gubun);
			map.put("dues_h_seq", dues_h_seq);
			map.put("pres_money", -(Integer.parseInt(pres_money)));
			Date def=sdf.parse(start_dt);
			cal.setTime(def);
			String auth_end="";
			if("1".equals(dues_gubun)||"3".equals(dues_gubun)){  
				cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
				cal.add(cal.DATE, -1); //1일을 뺀다.
				auth_end=sdf.format(cal.getTime());
			}else if("2".equals(dues_gubun)){
				auth_end="99991231"; //평생회원의 경우 유효 만료일을 9999년 12월 31일로
			////////////////////////////////CMS추가///////////////////////////
			}else if("4".equals(dues_gubun)){
				//39(C신규취업회원(년)), 40(C기취업회원(년))인 경우 인증일 1년
				if("39".equals(code_member) || "40".equals(code_member)){
					cal.add(cal.YEAR, 1);//받아온 start_dt에 1년을 더한 값을 구한다.
					cal.add(cal.DATE, -1); //1일을 뺀다.
					auth_end=sdf.format(cal.getTime());
				}else{
					cal.add(cal.MONTH, 1);//받아온 start_dt에 1달을 더한 값을 구한다.
					cal.add(cal.DATE, -1); //1일을 뺀다.
					auth_end=sdf.format(cal.getTime());
				}
			////////////////////////////////////////////////////////////////
			}					
			map.put("auth_start", start_dt);
			map.put("auth_end", auth_end);

			int uDuesH=dao.uDuesH4(map);
			
			List<Map> sDuesH=dao.sDuesH(map);
			float sum_dues=Float.parseFloat(sDuesH.get(0).get("sum_dues").toString());
			float mem_dues=Float.parseFloat(sDuesH.get(0).get("mem_dues").toString());
		    String auth_start="";
		    
			if(sum_dues>=mem_dues){
				map.put("incom_flag", "Y");
				int uDuesH2=dao.uDuesH2(map);
		
			}else  if(sum_dues<mem_dues&&sum_dues>0){
				map.put("incom_flag", "N");  //미완납으로 변경
				int uDuesH2=dao.uDuesH2(map);
	
				int uPersState=dao.uPersState(map); //회원상태를 유효기간 만료로 변경
			}else  if(sum_dues<=0){
				map.put("incom_flag", "D");  //미완납으로 변경
				int uDuesH2=dao.uDuesH2(map);
				
				int date=Integer.parseInt(sdf.format(new java.util.Date()));
				
				System.out.println("start_dt====>"+start_dt+"   date======>"+date);
				List<Map> sMaxAend=dao.sMaxAend(map);								
				
				if(sMaxAend.get(0).get("auth_end")!=null){
					if(Integer.parseInt(sMaxAend.get(0).get("auth_end").toString())<date){  //오늘 날짜와 현재 dues_h_tbl의 유효시작일자를 비교
						map.put("pers_state", "05");  					
					}else{
						map.put("pers_state", "01");
					}
				}else{
					map.put("pers_state", "05");
				}
				
				int uPersState=dao.uPersState(map); //회원상태를 유효기간 만료로 변경
			}
		}
	}
	//팝업 메뉴4. 회비처리
		public ActionForward dues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
			Map map = new HashMap();
			basicDao dao=new basicDao();
			map.put("pCode",request.getParameter("pCode"));
			List<Map> memInfo=dao.memInfo1(map);
			//List<Map> code=dao.getCode(map);						
			request.setAttribute("result",memInfo);
			//request.setAttribute("code",code);
			return (mapping.findForward("pop3"));		
		}
		
		public ActionForward duesList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			Map map = new HashMap();
			basicDao dao=new basicDao();
			map.put("pCode",request.getParameter("pCode"));
			
			String page = request.getParameter("page");//페이지 : 1
			String rows = request.getParameter("rows");//출력줄 : 10
			String sidx = request.getParameter("sidx");//정렬기준 : pers_name
			String sord = request.getParameter("sord");//정렬 : asc, desc
			
			int npage = Integer.parseInt(page);
			int nrows = Integer.parseInt(rows);
			
			//현재 페이지로 시작 위치와 끝 위치 설정
			int nstart = (npage-1)*nrows;
			int nend = nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			//전체 개수 얻어오기
			int ncount = 0;
			List<Map> sDuesCnt=dao.sDuesCnt(map);
			ncount = ((Integer)(sDuesCnt.get(0).get("cnt"))).intValue();

			//전체 개수로 전체 페이지 설정
			int ntotpage = (ncount/nrows)+1;

			//전체 자료 가져오기
			List<Map> sDues=dao.sDues(map);
					
			StringBuffer result = new StringBuffer();
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");

			for(int i=0;i<sDues.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( sDues.get(i).get("gubun")	== null ? "" 				: sDues.get(i).get("gubun") ) +"\",");
				result.append("\"" + ( sDues.get(i).get("pers_mem_order")	== null ? ""	: sDues.get(i).get("pers_mem_order") ) +"\",");
				result.append("\"" + ( sDues.get(i).get("auth_start")	== null ? "" 			: sDues.get(i).get("auth_start") ) +"\",");
				result.append("\"" + ( sDues.get(i).get("auth_end")	== null ? "" 			: sDues.get(i).get("auth_end") ) +"\",");
				result.append("\"" + ( sDues.get(i).get("conform_dt")	== null ? "" 		: sDues.get(i).get("conform_dt") ) +"\",");
				result.append("\"" + ( sDues.get(i).get("receipt_dt")	== null ? "" 			: sDues.get(i).get("receipt_dt") ) +"\",");
				result.append("\"" + ( sDues.get(i).get("center_money")	== null ? "" 	: sDues.get(i).get("center_money") ) +"\",");
				result.append("\"" + ( sDues.get(i).get("bran_money")	== null ? "" 		: sDues.get(i).get("bran_money") ) +"\",");				
				result.append("\"" + ( sDues.get(i).get("bran_name")	== null ? "" 		: sDues.get(i).get("bran_name") ) +"\"");
				result.append("]}");		
			}
			
			result.append("]}");
			request.setAttribute("result",result);
			
			return (mapping.findForward("ajaxout"));
		}
	
	//팝업 메뉴5. 교육
	public ActionForward edu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo1(map);
		//List<Map> code=dao.getCode(map);			
		request.setAttribute("result",memInfo);
		//request.setAttribute("code",code);
		return (mapping.findForward("pop4"));		
	}
	
	public ActionForward eduList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		//Map map1 = new HashMap();
		basicDao dao=new basicDao(); 

		map.put("pCode",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo(map);
		//String operno=memInfo.get(0).get("pers_no").toString();
		
		//map1.put("pCode",request.getParameter("pCode"));
		
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String sidx = request.getParameter("sidx");//정렬기준 : pers_name
		String sord = request.getParameter("sord");//정렬 : asc, desc
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sEduCnt=dao.sEduCnt(map);

		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;
			
		//전체 자료 가져오기
		List<Map> sEdu=dao.sEdu(map);
					
		StringBuffer result = new StringBuffer();
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
	
		for(int i=0;i<sEdu.size();i++){
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sEdu.get(i).get("yyyy")	== null ? "" 				: sEdu.get(i).get("yyyy") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("code_kind")	== null ? "" 		: sEdu.get(i).get("code_kind") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("edutest_name")	== null ? ""	: sEdu.get(i).get("edutest_name") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("oper_start_dt")	== null ? ""	: sEdu.get(i).get("oper_start_dt") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("oper_end_dt")	== null ? ""	: sEdu.get(i).get("oper_end_dt") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("result_point")	== null ? ""	: sEdu.get(i).get("result_point") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("attend_cnt")	== null ? ""	: sEdu.get(i).get("attend_cnt") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("time_cnt")	== null ? ""	: sEdu.get(i).get("time_cnt") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("result_state")	== null ? "" 		: sEdu.get(i).get("result_state") ) +"\",");
			result.append("\"" + ( sEdu.get(i).get("up_date")	== null ? "" 			: sEdu.get(i).get("up_date") ) +"\"");
			result.append("]}");		
		}
			
		result.append("]}");
		request.setAttribute("result",result);
		return (mapping.findForward("ajaxout"));
	}
	
	//팝업 메뉴6. 자격증
	public ActionForward certifi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo1(map);				
		request.setAttribute("result",memInfo);		
		return (mapping.findForward("pop5"));		
	}
	
	public ActionForward certifiList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();		
		basicDao dao=new basicDao(); 

		map.put("code_pers",request.getParameter("pCode"));
//		map.put("pers_no", request.getParameter("pers_no")); // JUMIN_DEL
		
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String sidx = request.getParameter("sidx");//정렬기준 : pers_name
		String sord = request.getParameter("sord");//정렬 : asc, desc
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sCertifiList=dao.sCertiList(map);
		ncount = sCertifiList.size();
				
		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;
						
		StringBuffer result = new StringBuffer();
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
	
		for(int i=0;i<sCertifiList.size();i++){
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sCertifiList.get(i).get("code_certifi")		== null ? "" 	: sCertifiList.get(i).get("code_certifi") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("certifi_name")		== null ? "" 	: sCertifiList.get(i).get("certifi_name") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("resultdt")			== null ? ""	: sCertifiList.get(i).get("resultdt") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("result_start_dt")	== null ? ""	: sCertifiList.get(i).get("result_start_dt") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("result_end_dt")	== null ? ""	: sCertifiList.get(i).get("result_end_dt") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("col1")				== null ? "" 	: sCertifiList.get(i).get("col1") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("col2")				== null ? "" 	: sCertifiList.get(i).get("col2") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("total")				== null ? "" 	: sCertifiList.get(i).get("total") ) +"\",");
			result.append("\"" + ( sCertifiList.get(i).get("print_state")		== null ? "" 	: sCertifiList.get(i).get("print_state") ) +"\"");
			result.append("]}");		
		}
		result.append("]}");
		request.setAttribute("result",result);

		return (mapping.findForward("ajaxout"));
	}
	
	public ActionForward certifiList2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map1 = new HashMap();		
		basicDao dao=new basicDao(); 
		
		String pCode =request.getParameter("pCode");
		String startdt   =request.getParameter("startdt");
		String enddt    =request.getParameter("enddt");
		String code_certifi =request.getParameter("code_certifi");
		
		
		if(pCode!=null&&startdt!=null&&enddt!=null){		
			System.out.println("pCode="+pCode+"       startdt="+startdt+"      enddt="+enddt);
			map1.put("pCode", pCode);
			map1.put("start_dt", startdt);
			map1.put("end_dt", enddt);
			map1.put("code_certifi", code_certifi);
			
			String page = request.getParameter("page");//페이지 : 1
			String rows = request.getParameter("rows");//출력줄 : 10
			String sidx = request.getParameter("sidx");//정렬기준 : pers_name
			String sord = request.getParameter("sord");//정렬 : asc, desc
			
			int npage = Integer.parseInt(page);
			int nrows = Integer.parseInt(rows);
			
			//현재 페이지로 시작 위치와 끝 위치 설정
			int nstart = (npage-1)*nrows;
			int nend = nstart + nrows;
			map1.put("nstart", nstart);
			map1.put("nend", nend);
			
			//전체 개수 얻어오기
			int ncount = 0;
			List<Map> sCertifiList=dao.sCertiList2(map1);
			ncount = sCertifiList.size();
					
			//전체 개수로 전체 페이지 설정
			int ntotpage = (ncount/nrows)+1;
							
			StringBuffer result = new StringBuffer();
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
		
			for(int i=0;i<sCertifiList.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( sCertifiList.get(i).get("edutest_name")		== null ? "" 	: sCertifiList.get(i).get("edutest_name") ) +"\",");
				result.append("\"" + ( sCertifiList.get(i).get("outside_yn")		== null ? "" 	: sCertifiList.get(i).get("outside_yn") ) +"\",");
				result.append("\"" + ( sCertifiList.get(i).get("oper_end_dt")			== null ? ""	: sCertifiList.get(i).get("oper_end_dt") ) +"\",");
				result.append("\"" + ( sCertifiList.get(i).get("result_point")	== null ? ""	: sCertifiList.get(i).get("result_point") ) +"\",");
				result.append("\"" + ( sCertifiList.get(i).get("result_state")	== null ? ""	: sCertifiList.get(i).get("result_state") ) +"\"");
				result.append("]}");		
			}
			result.append("]}");
			request.setAttribute("result",result);
		}	
		return (mapping.findForward("ajaxout"));
	}
	
	//팝업 메뉴7. 기부기금
	public ActionForward donation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo1(map);
		List<Map> promise=dao.sPromise(map);		
		request.setAttribute("promise", promise);
		List<Map> defcomp=dao.defComp(map);		
		request.setAttribute("result",memInfo);
		request.setAttribute("promise", promise);
		//request.setAttribute("code",code);
		return (mapping.findForward("pop6"));			
	}
	
	public ActionForward donList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		basicDao dao=new basicDao(); 

		if(request.getParameter("pCode")!=null){
			map.put("pCode",URLDecoder.decode(request.getParameter("pCode"), "UTF-8"));
		}
		
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String sidx = request.getParameter("sidx");//정렬기준 : pers_name
		String sord = request.getParameter("sord");//정렬 : asc, desc
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sDonCnt=dao.sDonCnt(map);
		ncount = ((Integer)(sDonCnt.get(0).get("cnt"))).intValue();

		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;

		//전체 자료 가져오기
		List<Map> sDon=dao.sDon(map);
				
		StringBuffer result = new StringBuffer();
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");

		for(int i=0;i<sDon.size();i++){
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sDon.get(i).get("gubun")	== null ? "" 				: sDon.get(i).get("gubun") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("gName")	== null ? "" 				: sDon.get(i).get("gName") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("pres_let_dt")	== null ? "" 			: sDon.get(i).get("pres_let_dt") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("day_seq")	== null ? "" 				: sDon.get(i).get("day_seq") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("code_pay_flag")	== null ? "" 		: sDon.get(i).get("code_pay_flag") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("pfName")	== null ? "" 				: sDon.get(i).get("pfName") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("bank_name")	== null ? "" 			: sDon.get(i).get("bank_name") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("pres_money")	== null ? "" 		: sDon.get(i).get("pres_money") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("promise_no")	== null ? "" 			: sDon.get(i).get("promise_no") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("promise_seq")	== null ? "" 		: sDon.get(i).get("promise_seq") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("donation_remark")	== null ? ""	: sDon.get(i).get("donation_remark") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("conform_dt")	== null ? "" 			: sDon.get(i).get("conform_dt") ) +"\",");
			result.append("\"" + ( sDon.get(i).get("register")	== null ? "" 				: sDon.get(i).get("register") ) +"\"");
			result.append("]}");		
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));
	}
	
	public ActionForward mDon(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao=new basicDao();
		
		String code_pers				=StringUtil.nullToStr("", request.getParameter("mCode_pers"));
		String gubun					=StringUtil.nullToStr("", request.getParameter("gubun"));
		String pres_money			=StringUtil.nullToStr("", request.getParameter("pres_money"));
		String bank_name			=StringUtil.nullToStr("", request.getParameter("bank_name"));
		String code_pay_flag		=StringUtil.nullToStr("", request.getParameter("code_pay_flag"));
		String donation_remark	=StringUtil.nullToStr("", request.getParameter("donation_remark"));
		String conform_dt			=StringUtil.nullToStr("", request.getParameter("conform_dt"));
		String register					=StringUtil.nullToStr("", request.getParameter("register"));
		String pres_let_dt				=StringUtil.nullToStr("", request.getParameter("pres_let_dt"));
		String day_seq					=StringUtil.nullToStr("", request.getParameter("day_seq"));
		
		map.put("code_pers", 			code_pers);
		map.put("gubun", 				gubun);
		map.put("pres_money", 		pres_money);
		map.put("bank_name", 			bank_name);
		map.put("code_pay_flag", 		code_pay_flag);
		map.put("donation_remark", 	donation_remark);
		map.put("conform_dt", 			conform_dt);
		map.put("register", 				register);
		map.put("pres_let_dt", 			pres_let_dt);
		map.put("day_seq", 				day_seq);
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			int uDon=dao.uDon(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));

		map2.put("pCode",code_pers);
		List<Map> memInfo=dao.memInfo1(map2);
		List<Map> promise=dao.sPromise(map2);		
		request.setAttribute("promise", promise);
		//List<Map> code=dao.getCode(map2);	
		request.setAttribute("result",memInfo);
		//request.setAttribute("code",code);
		request.setAttribute("pCode", code_pers);
		return (mapping.findForward("pop6"));
	}
	
	//팝업 메뉴8. 메모
	public ActionForward memo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		map.put("pCode",request.getParameter("pCode"));
		List<Map> memInfo=dao.memInfo1(map);
		//List<Map> code=dao.getCode(map);				
		request.setAttribute("result",memInfo);
		//request.setAttribute("code",code);
		return (mapping.findForward("pop7"));		
	}
	

	public ActionForward memoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		basicDao dao=new basicDao(); 

		if(request.getParameter("pCode")!=null){
			map.put("pCode",URLDecoder.decode(request.getParameter("pCode"), "UTF-8"));
		}
		
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String sidx = request.getParameter("sidx");//정렬기준 : pers_name
		String sord = request.getParameter("sord");//정렬 : asc, desc
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		map.put("nstart", nstart);
		map.put("nend", nend);
		
		//전체 개수 얻어오기
		int ncount = 0;
		List<Map> sMemoCnt=dao.sMemoCnt(map);
		ncount = ((Integer)(sMemoCnt.get(0).get("cnt"))).intValue();

		//전체 개수로 전체 페이지 설정
		int ntotpage = (ncount/nrows)+1;

		//전체 자료 가져오기
		List<Map> sMemo=dao.sMemo(map);

		StringBuffer result = new StringBuffer();
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0;i<sMemo.size();i++){
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( sMemo.get(i).get("rdate")	== null ? "" : sMemo.get(i).get("rdate") ) +"\",");
			result.append("\"" + ( sMemo.get(i).get("code_memo")	== null ? "" : sMemo.get(i).get("code_memo") ) +"\",");
			result.append("\"" + ( sMemo.get(i).get("code_memo_name")	== null ? "" : sMemo.get(i).get("code_memo_name") ) +"\",");
			result.append("\"" + ( sMemo.get(i).get("memo_text")	== null ? "" : URLEncoder.encode(sMemo.get(i).get("memo_text").toString(), "UTF-8") ) +"\",");
			result.append("\"" + ( sMemo.get(i).get("register")	== null ? "" : sMemo.get(i).get("register") ) +"\"");
			result.append("]}");		
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));
	}
	
	public ActionForward mMemo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		Map map2 = new HashMap();
		basicDao dao=new basicDao();
		
		String code_pers=StringUtil.nullToStr("", request.getParameter("mCode_pers"));
		String rdate=StringUtil.nullToStr("", request.getParameter("rDate"));
		String register=StringUtil.nullToStr("", request.getParameter("register"));
		String code_memo=StringUtil.nullToStr("", request.getParameter("cMemo"));
		String memo_text=StringUtil.nullToStr("", request.getParameter("mText"));
		String gubun=StringUtil.nullToStr("", request.getParameter("gubun"));
		
		map.put("code_pers", code_pers);
		map.put("rdate", rdate);
		map.put("register", register);
		map.put("code_memo", code_memo);
		map.put("memo_text", memo_text);
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			if("insert".equals(gubun)){
				List<Map> iMemo=dao.iMemo(map);
			}else if("update".equals(gubun)){
				int uMemo=dao.uMemo(map);
			}
			resetToken(request);
			errMsg="저장했습니다.";
		}
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));

		map2.put("pCode",code_pers);
		List<Map> memInfo=dao.memInfo1(map2);
		//List<Map> code=dao.getCode(map2);	
		request.setAttribute("result",memInfo);
		//request.setAttribute("code",code);
		request.setAttribute("pCode", code_pers);
		return (mapping.findForward("pop7"));
	}
	
	
	
	//우편번호 검색
	public ActionForward postSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		/*if(request.getParameter("sel")!= null){							   
			sel=request.getParameter("sel");
		}*/
		return (mapping.findForward("post2"));		
	}
	public ActionForward postSearch1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(request.getParameter("sel")!= null){							   
			sel=request.getParameter("sel");
		}
		return (mapping.findForward("postSearchr1"));		
	}
	public ActionForward postSearch3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("post3"));		
	}
	public ActionForward postSearch4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("post4"));		
	}
	public ActionForward postAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("postAjaxr"));		
	}
	public ActionForward postSearchr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{	
		ArrayList<String[]> addressInfo = new ArrayList<String[]>();
		String apiurl = "http://openapi.epost.go.kr/postal/";
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String searchSe = request.getParameter("searchSe");
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		//전체 개수 얻어오기
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
				//전체 개수로 전체 페이지 설정
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
	
	public ActionForward postSearchr1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{	
		ArrayList<String[]> addressInfo = new ArrayList<String[]>();
		String apiurl = "http://openapi.epost.go.kr/postal/";
		String page = request.getParameter("page");//페이지 : 1
		String rows = request.getParameter("rows");//출력줄 : 10
		String searchSe = request.getParameter("searchSe");
		
		int npage = Integer.parseInt(page);
		int nrows = Integer.parseInt(rows);
		
		//현재 페이지로 시작 위치와 끝 위치 설정
		int nstart = (npage-1)*nrows;
		int nend = nstart + nrows;
		
		//전체 개수 얻어오기
		int ncount = 0;
		
		StringBuffer result = new StringBuffer();
		
		if("Y".equals(request.getParameter("sel"))||"Y".equals(sel)){
	       HttpURLConnection conn = null;
	       keyword= StringUtil.removeWhiteSpace(URLDecoder.decode(request.getParameter("keyword"), "UTF-8"));	       
	       apiurl = "http://openapi.epost.go.kr/postal/retrieveLotNumberAdressService/retrieveLotNumberAdressService/getDetailList?searchSe="+searchSe+"&srchwrd="+keyword+"&serviceKey=bYTGHnfOl/wxI/qSAsoSH6eyIkL17Gc9GLultBk05JhRG3KVH3weGucKec8VA4yJiC6vcmeL253SYRN/LEA9ow==";	       
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
				//전체 개수로 전체 페이지 설정
				int ntotpage = (ncount/nrows)+1;
				     
	    		result.append("{\"page\":\""+	npage	+"\",");		
	    		result.append("\"total\":\"" + ntotpage+"\",");
	    		result.append("\"records\":\""+	ncount	+"\",");
	    		result.append("\"rows\":[");
	    		
	            for(int i=1;i<list1.size();i++){
		            Element item=(Element)list1.get(i);		            
		            String zipNo = item.getChildText("zipNo");
		            String adres = item.getChildText("adres");
		            
		            System.out.println("------->  "+zipNo+"   "+adres);      
		             
		            if(i>1) result.append(",");
		 			result.append("{\"id\":\"" + i + "\",\"cell\":[");
		 			result.append("\"" + ( zipNo	== null ? "" : zipNo ) +"\",");
		 			result.append("\"" + ( adres== null ? "" : adres ) +"\",");		 				 			
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
	
	public ActionForward eMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if("LicenseRenewal".equals(request.getParameter("paramfrom"))){
			
				Map map = new HashMap();
				licenseDao dao = new licenseDao(); 
				
			    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
				map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
				map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
				map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
				map.put("print_state1",        	request.getParameter("print_state1"));							//상태
				map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
				map.put("check", "2");
//				map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
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
				
				param+="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+request.getParameter("paramfrom");
		
				//검색 변수 넘김
				request.setAttribute("param", param);
				//전체 개수 넘김
				request.setAttribute("ncount", ncount);

		}
		else if("LicenseIssue".equals(request.getParameter("paramfrom"))){
			
			Map map = new HashMap();
			licenseDao dao = new licenseDao();
			
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
			
			
			String chk = request.getParameter("button_ab");
			
			List<Map> issuesendcnt = null;
			List<Map> nowsendcnt = null;
			String ncount = "0";
			
			if("".equals(StringUtil.NVL(request.getParameter("addr_infos")))){				
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
			}
			
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param="";
			if(request.getParameter("code_certifi1")!=null)param+="&code_certifi1="        	+ request.getParameter("code_certifi1");									//자격증구분
			if(request.getParameter("yyyy1")!=null)param+="&yyyy1="        	+ request.getParameter("yyyy1");											//년도
			if(request.getParameter("code_operation1")!=null)param+="&code_operation1="        	+ request.getParameter("code_operation1");								//대상자
			if(request.getParameter("season1")!=null)param+="&season1="        	+ request.getParameter("season1");		
			if(request.getParameter("button_ab")!=null)param+="&button_ab="        	+ request.getParameter("button_ab"); 
			
			param+="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+request.getParameter("paramfrom");
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
			
		}
		else if("LicenseInspection".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
			else {
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
				
				param+="&paramfrom="+request.getParameter("paramfrom");
				
				//검색 변수 넘김
				request.setAttribute("param", param);
				//전체 개수 넘김
				request.setAttribute("ncount", ncount);
			}
		}
		else if("ResultRegistration".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
			else {
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
				
				param+="&paramfrom="+request.getParameter("paramfrom");
				
				//검색 변수 넘김
				request.setAttribute("param", param);
				//전체 개수 넘김
				request.setAttribute("ncount", ncount);
			}
		}
		else if("TestTakeCondition".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
			else {
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
				param+="&paramfrom="+request.getParameter("paramfrom");
				
				//검색 변수 넘김
				request.setAttribute("param", param);
				//전체 개수 넘김
				request.setAttribute("ncount", ncount);
			}
		}
		else if("ExamResult".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
			else {
				Map map = new HashMap();
				educationDao dao=new educationDao();
				
				if(request.getParameter("yyyy1")!=null) map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
				if(request.getParameter("code_bran1")!=null) map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
				if(request.getParameter("code_kind1")!=null) map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
				if(request.getParameter("edutest_name1")!=null) map.put("detcode1",        		request.getParameter("edutest_name1"));										//승인여부
				
				if("Y".equals(request.getParameter("cert"))){
					map.put("cert",        	"Y");
				}else{
					if(request.getParameter("code_certifi1")!=null) map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
					if(request.getParameter("oper_name1")!=null) map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
					if(request.getParameter("oper_lic_no1")!=null) map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
					if(request.getParameter("oper_hp1")!=null) map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
					if(request.getParameter("oper_comp_name11")!=null) map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
					if(request.getParameter("code_operation1")!=null) map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
					if(request.getParameter("result_point1")!=null) map.put("result_point1",        request.getParameter("result_point1"));									//점수
					
					if(request.getParameter("attend_cnt1")!=null) map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
					if(request.getParameter("time_cnt1")!=null) map.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
					if(request.getParameter("result_state1")!=null) map.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
					if(request.getParameter("oper_email1")!=null) map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
					if(request.getParameter("oper_state1")!=null) map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
					if(request.getParameter("result_no1")!=null) map.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
				}
				
				//전체 개수 검색
				List<Map> count = dao.examsendcnt(map);
				String ncount = String.valueOf( ((Integer)count.get(0).get("cnt")) );
				
				// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
				String param = "";
				if(request.getParameter("yyyy1")!=null)	param+="&yyyy1="+request.getParameter("yyyy1");
				if(request.getParameter("code_bran1")!=null)	param+="&code_bran1="+request.getParameter("code_bran1");
				if(request.getParameter("code_kind1")!=null)	param+="&code_kind1="+request.getParameter("code_kind1");
				if(request.getParameter("edutest_name1")!=null)	param+="&edutest_name1="+request.getParameter("edutest_name1");
				if(request.getParameter("code_certifi1")!=null)	param+="&code_certifi1="+request.getParameter("code_certifi1");
				if(request.getParameter("oper_name1")!=null)	param+="&oper_name1="+request.getParameter("oper_name1");
				if(request.getParameter("oper_lic_no1")!=null)	param+="&oper_lic_no1="+request.getParameter("oper_lic_no1");
				if(request.getParameter("oper_hp1")!=null)	param+="&oper_hp1="+request.getParameter("oper_hp1");
				if(request.getParameter("oper_email1")!=null)	param+="&oper_email1="+request.getParameter("oper_email1");
				if(request.getParameter("code_operation1")!=null)	param+="&code_operation1="+request.getParameter("code_operation1");
				if(request.getParameter("oper_state1")!=null)	param+="&oper_state1="+request.getParameter("oper_state1");
				if(request.getParameter("oper_comp_name11")!=null)	param+="&oper_comp_name11="+request.getParameter("oper_comp_name11");
				if(request.getParameter("result_point1")!=null)	param+="&result_point1="+request.getParameter("result_point1");
				if(request.getParameter("attend_cnt1")!=null)	param+="&attend_cnt1="+request.getParameter("attend_cnt1");
				if(request.getParameter("time_cnt1")!=null)	param+="&time_cnt1="+request.getParameter("time_cnt1");
				if(request.getParameter("result_no1")!=null)	param+="&result_no1="+request.getParameter("result_no1");
				if(request.getParameter("result_state1")!=null)	param+="&result_state1="+request.getParameter("result_state1");
				if(request.getParameter("cert")!=null)	param+="&cert="+request.getParameter("cert");
				param+="&paramfrom="+request.getParameter("paramfrom");
				
				//검색 변수 넘김
				request.setAttribute("param", param);
				//전체 개수 넘김
				request.setAttribute("ncount", ncount);
			}
		}
		else if("mBasic".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
		}
		else if("memberSearchList".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
		}
		else if("memberDuesList".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
		}
		else if("EduTakeStatus".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String param ="&addr_infos="+request.getParameter("addr_infos")+"&paramfrom="+ request.getParameter("paramfrom");
				request.setAttribute("param", param);
			}
			else {
				Map map = new HashMap();
				educationDao dao=new educationDao();
				
				if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
				if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
				if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
				if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
				if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
				if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
				if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
				if(request.getParameter("oper_birth1"		)!=null)map.put("oper_birth1"		, request.getParameter("oper_birth1"	));	//생년월일
				if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
				if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
				if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
				if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
				if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
				if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
				if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
				if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
				if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
				if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
				if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
				if(request.getParameter("sel") != null)		map.put("sel",       	"Y");			
					
				//전체 개수 검색
				List<Map> educationcount=dao.educationsendcnt(map); 
				String ncount = String.valueOf( ((Integer)educationcount.get(0).get("cnt")) );
		
				// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
				String param = "";
				if(request.getParameter("edutest_name1"		)!=null)param+="&edutest_name1="	+ request.getParameter("edutest_name1"	);	//내용
				if(request.getParameter("oper_name1"		)!=null)param+="&oper_name1="		+ request.getParameter("oper_name1"		);	//이름
				if(request.getParameter("oper_comp_name11"	)!=null)param+="&oper_comp_name11="	+ request.getParameter("oper_comp_name11");	//근무처명
				
				if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
				if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);	//교육주최
				if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);	//교육종류
				if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
				if(request.getParameter("oper_birth1"		)!=null)param+="&oper_birth1="		+ request.getParameter("oper_birth1"	);	//생년월일
				if(request.getParameter("oper_lic_no1"		)!=null)param+="&oper_lic_no1="		+ request.getParameter("oper_lic_no1"	);	//면허번호
				if(request.getParameter("oper_hp1"			)!=null)param+="&oper_hp1="			+ request.getParameter("oper_hp1"		);	//핸드폰
				if(request.getParameter("code_operation1"	)!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");	//대상자
				if(request.getParameter("result_end_dt1"	)!=null)param+="&result_end_dt1="	+ request.getParameter("result_end_dt1"	);	//유효일자
				if(request.getParameter("start_dt1"			)!=null)param+="&start_dt1="		+ request.getParameter("start_dt1"		);	//유예시작일
				if(request.getParameter("end_dt1"			)!=null)param+="&end_dt1="			+ request.getParameter("end_dt1"		);	//유예마지막일
				if(request.getParameter("oper_state1"		)!=null)param+="&oper_state1="		+ request.getParameter("oper_state1"	);	//진행상태
				if(request.getParameter("code_pay_flag1"	)!=null)param+="&code_pay_flag1="	+ request.getParameter("code_pay_flag1"	);	//결제방법
				if(request.getParameter("receipt_dt1"		)!=null)param+="&receipt_dt1="		+ request.getParameter("receipt_dt1"	);	//신청일자
				if(request.getParameter("code_seq1"			)!=null)param+="&code_seq1="		+ request.getParameter("code_seq1"		);	//순번
				if(request.getParameter("detcode1"			)!=null)param+="&detcode1="			+ request.getParameter("detcode1"		);	//승인여부
				if(request.getParameter("sel"				)!=null)param+="&sel="				+ request.getParameter("sel"			);
				param+="&paramfrom="+ request.getParameter("paramfrom");
		
				//검색 변수 넘김
				request.setAttribute("param", param);
				//전체 개수 넘김
				request.setAttribute("ncount", ncount);
			}
		}

		return (mapping.findForward("sendMail"));		
	}
	public void SendMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String saveFolder="D:/WEB/KDA_VER3/upload";
		
		String to="";
		if(request.getParameter("to")!=null && !"".equals(request.getParameter("to")) && !"ALL".equals(request.getParameter("to")) && "".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
			to=StringUtil.nullToStr("", request.getParameter("to")); //받는 사람 메일주소. 여러명일때는 주소 사이를 콤마로 구분해준다.
		}
		else if("LicenseRenewal".equals(request.getParameter("paramfrom"))){
			List<String> addr_infos =  null;
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				addr_infos =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
			}
				
				Map map = new HashMap(); 
				
			    map.put("code_certifi1",       	request.getParameter("code_certifi1"));							//자격증구분		  
				map.put("result_no1",        	request.getParameter("result_no1"));							//자격증번호
				map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));	//이름
				map.put("oper_birth1",        		request.getParameter("oper_birth1"));								//생년월일				
				map.put("print_state1",        	request.getParameter("print_state1"));							//상태
				map.put("code_seq1",        	request.getParameter("code_seq1"));							//상태
				map.put("check", "2");
//				map.put("yyyy1", Integer.toString(Integer.parseInt( request.getParameter("yyyy1"))-1));
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
				
				map.put("person_yns", addr_infos);
				map.put("nstart", 0);
				map.put("nend", 1000000);
				
				licenseDao dao = new licenseDao();
				List<Map> result = dao.selectrenewal(map,true);
		    	
		    	StringBuffer toArr = new StringBuffer(); 
		    	for(int i=0; i<result.size(); i++){
		    		String oper_email = (String) result.get(i).get("oper_email");
		    		if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
		    	}   
		    	to = toArr.toString();
		}
		else if("LicenseIssue".equals(request.getParameter("paramfrom"))){
			List<String> addr_infos =  null;
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				addr_infos =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
			}
			
			Map map = new HashMap(); 
			
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
			
			
			String chk = request.getParameter("button_ab");
			
			map.put("person_yns", addr_infos);
			map.put("nstart", 0);
			map.put("nend", 1000000);
			
			licenseDao dao = new licenseDao();
			List<Map> result = new ArrayList<Map>();
			//대상검색
			if(chk.equals("a")){
				result=dao.selectissue(map,true);
			}
			else if(chk.equals("b")){
				result=dao.selectnow(map,true);
			}
			
			StringBuffer toArr = new StringBuffer(); 
			for(int i=0; i<result.size(); i++){
				String oper_email = (String) result.get(i).get("operemail");
				if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
			}   
			to = toArr.toString();
		}
		else if("LicenseInspection".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				List<String> addr_infos_org =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
				List<List<String>> addr_infos = new ArrayList<List<String>>();
				
				for(String item : addr_infos_org){
					List<String> addr_info =  Arrays.asList(item.split("_"));
					addr_infos.add(addr_info);
				}
				Map map = new HashMap();
				map.put("receipt_infos", addr_infos);
				
				educationDao dao = new educationDao();
				List<Map> result= dao.selecteducationBatch(map,true);
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("oper_email");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
			else {
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
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("operemail");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
		}
		else if("ResultRegistration".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				List<String> addr_infos_org =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
				List<List<String>> addr_infos = new ArrayList<List<String>>();
				
				for(String item : addr_infos_org){
					List<String> addr_info =  Arrays.asList(item.split("_"));
					addr_infos.add(addr_info);
				}
				Map map = new HashMap();
				map.put("receipt_infos", addr_infos);
				
				educationDao dao = new educationDao();
				List<Map> result= dao.selecteducationBatch(map,true);
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("oper_email");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
			else {
				Map map = new HashMap(); 
				
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
				
				map.put("nstart", 0);
				map.put("nend", 1000000);
				
				licenseDao dao = new licenseDao();
				List<Map> result = dao.selectresult(map,true);
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("oper_email");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
		}
		else if("TestTakeCondition".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				List<String> addr_infos_org =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
				List<List<String>> addr_infos = new ArrayList<List<String>>();
				
				for(String item : addr_infos_org){
					List<String> addr_info =  Arrays.asList(item.split("_"));
					addr_infos.add(addr_info);
				}
				Map map = new HashMap();
				map.put("receipt_infos", addr_infos);
				
				educationDao dao = new educationDao();
				List<Map> result= dao.selecteducationBatch(map,true);
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("oper_email");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
			else {
				Map map = new HashMap(); 
				
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
				
				map.put("nstart", 0);
				map.put("nend", 1000000);
				
				licenseDao dao = new licenseDao();
				List<Map> result= dao.selectlicense(map,true);
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("oper_email");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
		}
		else if("ExamResult".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				List<String> addr_infos_org =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
				List<List<String>> addr_infos = new ArrayList<List<String>>();
				
				for(String item : addr_infos_org){
					List<String> addr_info =  Arrays.asList(item.split("_"));
					addr_infos.add(addr_info);
				}
				Map map = new HashMap();
				map.put("receipt_infos", addr_infos);
				
				educationDao dao = new educationDao();
				List<Map> result= dao.selecteducationBatch(map,true);
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("oper_email");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
			else {
				Map map = new HashMap();    		
				if(request.getParameter("yyyy1")!=null) map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
				if(request.getParameter("code_bran1")!=null) map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
				if(request.getParameter("code_kind1")!=null) map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
				if(request.getParameter("edutest_name1")!=null) map.put("detcode1",        		request.getParameter("edutest_name1"));										//승인여부
				
				if("Y".equals(request.getParameter("cert"))){
					map.put("cert",        	"Y");
				}else{
					if(request.getParameter("code_certifi1")!=null) map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
					if(request.getParameter("oper_name1")!=null) map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
					if(request.getParameter("oper_lic_no1")!=null) map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
					if(request.getParameter("oper_hp1")!=null) map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
					if(request.getParameter("oper_comp_name11")!=null) map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
					if(request.getParameter("code_operation1")!=null) map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
					if(request.getParameter("result_point1")!=null) map.put("result_point1",        request.getParameter("result_point1"));									//점수
					
					if(request.getParameter("attend_cnt1")!=null) map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
					if(request.getParameter("time_cnt1")!=null) map.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
					if(request.getParameter("result_state1")!=null) map.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
					if(request.getParameter("oper_email1")!=null) map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
					if(request.getParameter("oper_state1")!=null) map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
					if(request.getParameter("result_no1")!=null) map.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
				}
				
				map.put("nstart", 0);
				map.put("nend", 1000000);
				
				educationDao dao = new educationDao();
				List<Map> result= dao.selectexam2(map,true);
				
				StringBuffer toArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_email = (String) result.get(i).get("operemail");
					if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
				}   
				to = toArr.toString();
			}
		}
		else if("mBasic".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String temp2		= StringUtil.NVL(request.getParameter("addr_infos"));
				String code_pers[]	= temp2.split(",");
				educationDao dao = new educationDao();
				StringBuffer toArr = new StringBuffer();
				if(!"".equals(temp2)){
					for(int i=0; i<code_pers.length;i++){
						Map map = new HashMap();
						map.put("code_pers1"		, code_pers[i] );
						List<Map> list = dao.selectpersonmtbl(map,true);
						if(list!=null && list.size()>0) {
							if(toArr.length()>0) toArr.append(","); toArr.append((String) list.get(0).get("email"));
						}
					}
			    	to = toArr.toString();
				}
			}
		}
		else if("memberSearchList".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String temp2		= StringUtil.NVL(request.getParameter("addr_infos"));
				String code_pers[]	= temp2.split(",");
				educationDao dao = new educationDao();
				StringBuffer toArr = new StringBuffer();
				if(!"".equals(temp2)){
					for(int i=0; i<code_pers.length;i++){
						Map map = new HashMap();
						map.put("code_pers1"		, code_pers[i] );
						List<Map> list = dao.selectpersonmtbl(map,true);
						if(list!=null && list.size()>0) {
							if(toArr.length()>0) toArr.append(","); toArr.append((String) list.get(0).get("email"));
						}
					}
			    	to = toArr.toString();
				}
			}
		}
		else if("memberDuesList".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				String temp2		= StringUtil.NVL(request.getParameter("addr_infos"));
				String code_pers[]	= temp2.split(",");
				educationDao dao = new educationDao();
				StringBuffer toArr = new StringBuffer();
				if(!"".equals(temp2)){
					for(int i=0; i<code_pers.length;i++){
						Map map = new HashMap();
						map.put("code_pers1"		, code_pers[i] );
						List<Map> list = dao.selectpersonmtbl(map,true);
						if(list!=null && list.size()>0) {
							if(toArr.length()>0) toArr.append(","); toArr.append((String) list.get(0).get("email"));
						}
					}
			    	to = toArr.toString();
				}
			}
		}
		else if("EduTakeStatus".equals(request.getParameter("paramfrom"))){
			if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
				List<String> addr_infos_org =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
				List<List<String>> addr_infos = new ArrayList<List<String>>();
				
				for(String item : addr_infos_org){
					List<String> addr_info =  Arrays.asList(item.split("_"));
					addr_infos.add(addr_info);
				}
				Map map = new HashMap();
				map.put("receipt_infos", addr_infos);
				
		    	educationDao dao = new educationDao();
		    	List<Map> result= dao.selecteducationBatch(map,true);
		    	
		    	StringBuffer toArr = new StringBuffer(); 
		    	for(int i=0; i<result.size(); i++){
		    		String oper_email = (String) result.get(i).get("oper_email");
		    		if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
		    	}   
		    	to = toArr.toString();
			}
			else {
				Map map = new HashMap();    		
				if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
				if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
				if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
				if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
				if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
				if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
				if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
				if(request.getParameter("oper_birth1"		)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
				if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
				if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
				if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
				if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
				if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
				if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
				if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
				if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
				if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
				if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
				if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
				if(request.getParameter("sel") != null)		map.put("sel",       	"Y");

				map.put("nstart", 0);
				map.put("nend", 1000000);
				
		    	educationDao dao = new educationDao();
		    	List<Map> result= dao.selecteducation(map,true);
		    	
		    	StringBuffer toArr = new StringBuffer(); 
		    	for(int i=0; i<result.size(); i++){
		    		String oper_email = (String) result.get(i).get("oper_email");
		    		if(oper_email!=null && !"".equals(oper_email)){ if(toArr.length()>0) toArr.append(","); toArr.append(oper_email); }
		    	}   
		    	to = toArr.toString();
			}
		}
		
		String fr_name=URLDecoder.decode(StringUtil.nullToStr("", request.getParameter("fr_name")), "UTF-8"); //보내는 사람 이름
		String from=StringUtil.nullToStr("", request.getParameter("from")); //보내는 사람 메일주소
		String subj=URLDecoder.decode(StringUtil.nullToStr("", request.getParameter("subj")), "UTF-8"); //메일 제목
		String content=URLDecoder.decode(StringUtil.nullToStr("", request.getParameter("content")), "UTF-8"); //메일 내용
		String fileName=URLDecoder.decode(StringUtil.nullToStr("", request.getParameter("fileName")), "UTF-8"); //첨부파일 이름
		
		String filename="";
		
		String upYN=request.getParameter("upYN");		
		if(upYN.equals("y")&&(!"".equals(fileName))){
			StringTokenizer st=new StringTokenizer(fileName,"\\");
			String[] array=new String[st.countTokens()];
			int i=0;
			while(st.hasMoreElements()){
				array[i++]=st.nextToken();
			}
			filename=array[i-1];
		}
		
		//return (mapping.findForward("ajax"));

		Properties mailProps=new Properties();
		//gmail을 이용할 경우 
		//mailProps.put("mail.smtp.host", "smtp.gmail.com");
		//mailProps.put("mail.smtp.port", "587");
		//mailProps.put("mail.smtp.auth", "true");
		//mailProps.put("mail.smtp.starttls.enable", "true");
		mailProps.put("mail.smtp.host", "localhost");
			
		EmailSender esender=new EmailSender(mailProps);
		esender.sendEmail(from, fr_name, to, subj, content,upYN,saveFolder,filename);
	}
	
	public void upFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		
		String saveFolder="D:/WEB/KDA_VER3/upload";
		String encType="EUC-KR";
		int maxSize=10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
		String filename="";
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				filename=multi.getFilesystemName(name);
				f=new File(saveFolder+"/"+filename);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* 쪽지 저장 */
	public ActionForward dmsendnotePad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		String dm_pers_code = request.getParameter("dm_pers_code");
		
		return (mapping.findForward("notePad"));
	}
	
	public ActionForward notePad(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();    		
    	HttpSession session=request.getSession();     		
    	
    	String dm_pers_code=request.getParameter("dm_pers_code");    	

    	map.put("dmperscode",		dm_pers_code);
    	String note_oper = URLDecoder.decode(request.getParameter("note_oper"),"UTF-8");
    	String note_title = URLDecoder.decode(request.getParameter("note_title"),"UTF-8");
    	String note_contents = URLDecoder.decode(request.getParameter("note_contents"),"UTF-8");
    	map.put("note_title",		note_title);
    	map.put("note_contents",	note_contents);   	
    	map.put("note_oper",		note_oper);
    	map.put("st_dt",			request.getParameter("st_dt"));   	
     	map.put("ed_dt",			request.getParameter("ed_dt"));
    	map.put("register",			session.getAttribute("G_NAME"));
    		
    	documentDao dao = new documentDao();
    	int n = dao.insertnotePad(map);    		     	
		
    	return (mapping.findForward("insertnotePad_ok"));				
	}
	
	/* 문자 전송 */
	public ActionForward sendumsData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		return (mapping.findForward("umsData"));
	}
	
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
    	
    	String msg_type = strlength < 81 ? "0" : "5"; 
    	//예약발송용
    	String request_time = "";
    	if( request.getParameter("yyyyMMdd") != null )
    		request_time = request.getParameter("yyyyMMdd") + " " + request.getParameter("HHmm");
    	
    	String pers_hp   = request.getParameter("pers_hp");
    	String pers_name   = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
    	String dm_pers_code = StringUtil.NVL(request.getParameter("dm_pers_code"));
    	
    	if(!"".equals(dm_pers_code)){
    		Map map1 = new HashMap();
			map1.put("code_pers"		, dm_pers_code );
			memberSearchDao dao=new memberSearchDao();
			List<Map> list = dao.getSearchUmsList(map1,true);
			if(list!=null && list.size()>0) pers_hp = (String) list.get(0).get("pers_hp");
    	}

    	map.put("pers_hp",		pers_hp); 
    	map.put("msg_type",		msg_type);
    	map.put("subject",		subject);
    	map.put("msg_body",		msg_body);     		
    	map.put("send_phone",	request.getParameter("send_phone"));      		
    	map.put("register",		session.getAttribute("G_NAME"));
    	map.put("etc1",         session.getAttribute("G_ID"));
		map.put("etc2",         session.getAttribute("G_BRAN"));
		map.put("etc3",         pers_name);
    	map.put("umscnt", "1");	
    	documentDao dao = new documentDao();
  
    	//예약발송
		int n = 0;
		if( request_time.length() > 0 ) {
			map.put("request_time", request_time);
			n = dao.insertumsResultData(map);
		}else {
			n = dao.insertumsData(map);
		}
		
		request.setAttribute("msg", "전송됐습니다.");
		return (mapping.findForward("insertumsData_ok"));				
	}
	
	public ActionForward SetID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		String pers_name = "";
		
		String lic_no = request.getParameter("lic_no");
		String use_lic_no = request.getParameter("use_lic_no");
		String adm = request.getParameter("adm");
		String pers_birth = request.getParameter("pers_birth");
		
		if(request.getParameter("pers_name")!=null){
			pers_name = URLDecoder.decode(request.getParameter("pers_name"), "UTF-8");			
			map.put("pers_name",pers_name);
		}
		
		String msg = "";
		
		map.put("lic_no",lic_no);
		map.put("use_lic_no",use_lic_no);
		map.put("adm",adm);
		map.put("pers_birth", pers_birth);
		map.put("use_pers_birth", "Y");
		
		List<Map> getIdInfo = null;
		
		if(!"".equals(pers_name)){
			getIdInfo = dao.getIdInfo(map);
			if(getIdInfo.size() <1)	{
				msg ="0";
			}else if(getIdInfo.size() >1)	{
				msg ="2";	
			}else{
				request.setAttribute("pers_name", getIdInfo.get(0).get("pers_name"));
				request.setAttribute("lic_no", getIdInfo.get(0).get("lic_no"));
				request.setAttribute("pers_hp", getIdInfo.get(0).get("pers_hp"));
				request.setAttribute("id", getIdInfo.get(0).get("id"));
				request.setAttribute("code_pers", getIdInfo.get(0).get("code_pers"));
				msg ="1";
			}
		}
		
		request.setAttribute("msg", msg);
		
		//토큰 저장
		saveToken(request);
		return (mapping.findForward("idpop"));		
	}
	
	public ActionForward idCheck(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		String msg ="";
		int ncount = 0;
		
		String pers_id= request.getParameter("pers_id");
		Map map = new HashMap();
		basicDao dao=new basicDao();
		
		map.put("pers_id",pers_id);
		List<Map> idCheck=dao.idCheck(map);
		ncount = ((Integer)(idCheck.get(0).get("cnt"))).intValue();
		
		if(ncount == 0){
			msg = "Y";
		}else if(ncount >0){
			msg = "N";
		}
		
		request.setAttribute("code", "check");
		request.setAttribute("msg", msg);
		return (mapping.findForward("checkidpop"));	
		
	}
	
	public ActionForward idUpt(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		basicDao dao=new basicDao();
		String msg ="";
		int ncount = 0;
		
		String pers_id= request.getParameter("pers_id");
		String code_pers= request.getParameter("code_pers");
		
		map.put("pers_id",pers_id);
		map.put("code_pers",code_pers);
		
		ncount = dao.idUpt(map);
		
		if(ncount==1){
			msg ="저장하였습니다";
		}else{
			msg ="저장에 실패하였습니다";
		}
		
		request.setAttribute("code", "upt");
		request.setAttribute("msg", msg);
		return (mapping.findForward("checkidpop"));	
	}
	
}
