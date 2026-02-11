package com.ant.setup.action;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.common.util.StringUtil;
import com.ant.conference.dao.conferenceDao;
import com.ant.setup.dao.setupduesDao;

public class setupduesAction extends DispatchAction {
	
	/*회비 환경설정*/	
	public ActionForward setupdues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    return mapping.findForward("setupduesList");
	}
	
	public ActionForward setupduesList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();
		
		if(request.getParameter("yyyymm")!=null){
			map.put("yyyymm",                request.getParameter("yyyymm"));}	
		if(request.getParameter("dues_gubun1")!=null){
			map.put("dues_gubun1",           request.getParameter("dues_gubun1"));}	
		if(request.getParameter("code_member1")!=null){	
			map.put("code_member1",          request.getParameter("code_member1"));}
		if(request.getParameter("code_bran1")!=null){	 
			map.put("code_bran1",            request.getParameter("code_bran1"));}	
		if(request.getParameter("use_yn1")!=null){	
			map.put("use_yn1",               request.getParameter("use_yn1"));}	
	    
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
		List<Map> setupduescnt=dao.setupduescnt(map);
		ncount = ((Integer)(setupduescnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map>  setupdues = dao.setupdues(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i< setupdues.size(); i++){
			
			if(i > 0){
				result.append(",");
			}
			
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( setupdues.get(i).get("yyyymm")	        == null ? ""         :     setupdues.get(i).get("yyyymm"))  +"\",");			
			result.append("\"" + ( setupdues.get(i).get("dues_gubun")	    == null ? ""         :     setupdues.get(i).get("dues_gubun"))  +"\",");
			result.append("\"" + ( setupdues.get(i).get("dues_gubuncode")	== null ? ""         :     setupdues.get(i).get("dues_gubuncode"))  +"\",");
			result.append("\"" + ( setupdues.get(i).get("code_member")	    == null ? ""         :     setupdues.get(i).get("code_member"))  +"\",");
			result.append("\"" + ( setupdues.get(i).get("code_membercode")	== null ? ""         :     setupdues.get(i).get("code_membercode"))  +"\",");
			result.append("\"" + ( setupdues.get(i).get("code_bran")	    == null ? ""         :     setupdues.get(i).get("code_bran"))  +"\",");
			result.append("\"" + ( setupdues.get(i).get("code_brancode")	== null ? ""         :     setupdues.get(i).get("code_brancode"))  +"\",");
			result.append("\"" + ( setupdues.get(i).get("bran_dues")	    == null ? ""         :     setupdues.get(i).get("bran_dues"))  +"\",");			
			result.append("\"" + ( setupdues.get(i).get("bran_dues1")	    == null ? ""         :     setupdues.get(i).get("bran_dues1"))  +"\",");
			result.append("\"" + ( setupdues.get(i).get("head_dues")    	== null ? ""         :     setupdues.get(i).get("head_dues"))  +"\",");	
			result.append("\"" + ( setupdues.get(i).get("sum_dues")	        == null ? ""         :     setupdues.get(i).get("sum_dues"))  +"\",");			
			result.append("\"" + ( setupdues.get(i).get("use_yn")	        == null ? ""         :     setupdues.get(i).get("use_yn"))  +"\"");			
			result.append("]}");
		}	
		
		result.append("]}");		
	
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	public ActionForward insert_dues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
				
    	Map map = new HashMap();
    	setupduesDao dao = new setupduesDao();
    	
    	map.put("yyyymm",                    request.getParameter("yyyymm"));   	
		map.put("dues_gubun1",               request.getParameter("dues_gubun1"));
		map.put("code_member1",              request.getParameter("code_member1"));   	
	    map.put("code_bran1",                request.getParameter("code_bran1"));
    	map.put("bran_dues1",                request.getParameter("bran_dues1"));    	
		map.put("bran_dues11",               request.getParameter("bran_dues11"));
		map.put("head_dues1",                request.getParameter("head_dues1"));
		map.put("sum_dues1",                 request.getParameter("sum_dues1"));
		
		if(request.getParameter("use_yn1").equals("")){
			map.put("use_yn1", "Y");
		}else{
			map.put("use_yn1", request.getParameter("use_yn1"));
		}
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg = "이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertsetupdues(map);
			resetToken(request);
			errMsg = "저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);			
		return mapping.findForward("insertdues_ok");	
	} 
	
	public ActionForward update_dues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();
		
    	map.put("yyyymm",                    request.getParameter("yyyymm"));   	
		map.put("dues_gubun1",               request.getParameter("dues_gubun1"));
		map.put("code_member1",              request.getParameter("code_member1"));   	
	    map.put("code_bran1",                request.getParameter("code_bran1"));
    	map.put("bran_dues1",                request.getParameter("bran_dues1"));    	
		map.put("bran_dues11",               request.getParameter("bran_dues11"));
		map.put("head_dues1",                request.getParameter("head_dues1"));
		map.put("sum_dues1",                 request.getParameter("sum_dues1"));
		
		if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatesetupdues(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);				
		return mapping.findForward("updatedues_ok");	
	} 	
	
	/*산하단체회비 환경설정*/	
	public ActionForward setupsubdues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("setupsubduesList"));
	}
	
	public ActionForward setupsubduesList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();
		
		if(request.getParameter("yyyymm")!=null){
			map.put("yyyymm",                request.getParameter("yyyymm"));}	
		if(request.getParameter("dues_gubun1")!=null){
			map.put("dues_gubun1",           request.getParameter("dues_gubun1"));}		
		if(request.getParameter("code_bran1")!=null){	 
			map.put("code_bran1",            request.getParameter("code_bran1"));}
		if(request.getParameter("code_sub1")!=null){	
			map.put("code_sub1",          request.getParameter("code_sub1"));}
		if(request.getParameter("use_yn1")!=null){	
			map.put("use_yn1",               request.getParameter("use_yn1"));}	
		
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
		List<Map> setupsubduescnt=dao.setupsubduescnt(map);
		ncount = ((Integer)(setupsubduescnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map>  setupsubdues = dao.setupsubdues(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i< setupsubdues.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( setupsubdues.get(i).get("yyyymm")	== null ? ""          :     setupsubdues.get(i).get("yyyymm"))  +"\",");			
			result.append("\"" + ( setupsubdues.get(i).get("dues_gubun")	== null ? ""      :     setupsubdues.get(i).get("dues_gubun"))  +"\",");
			result.append("\"" + ( setupsubdues.get(i).get("dues_gubuncode")	== null ? ""  :     setupsubdues.get(i).get("dues_gubuncode"))  +"\",");
			result.append("\"" + ( setupsubdues.get(i).get("code_bran")	== null ? ""          :     setupsubdues.get(i).get("code_bran"))  +"\",");
			result.append("\"" + ( setupsubdues.get(i).get("code_brancode")	== null ? ""      :     setupsubdues.get(i).get("code_brancode"))  +"\",");
			result.append("\"" + ( setupsubdues.get(i).get("sub_name")	== null ? ""          :     setupsubdues.get(i).get("sub_name"))  +"\",");			
			result.append("\"" + ( setupsubdues.get(i).get("code_sub")	== null ? ""          :     setupsubdues.get(i).get("code_sub"))  +"\",");
			result.append("\"" + ( setupsubdues.get(i).get("sub_dues")	== null ? ""          :     setupsubdues.get(i).get("sub_dues"))  +"\",");			
			result.append("\"" + ( setupsubdues.get(i).get("use_yn")	== null ? ""          :     setupsubdues.get(i).get("use_yn"))  +"\"");			
			result.append("]}");
		}	
		
		result.append("]}");		
	
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	public ActionForward insert_sub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
    	Map map = new HashMap();
    	setupduesDao dao = new setupduesDao();
    	
    	map.put("yyyymm",                    request.getParameter("yyyymm"));   	
		map.put("dues_gubun1",               request.getParameter("dues_gubun1"));
		map.put("code_bran1",                request.getParameter("code_bran1"));
    	map.put("code_sub1",                 request.getParameter("code_sub1"));    	
		map.put("sub_dues1",                 request.getParameter("sub_dues1"));		
		if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}				
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertsetupduessub(map);	
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);			
		return mapping.findForward("insertsub_ok");	
	} 
	
	public ActionForward update_sub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();	
		
		map.put("yyyymm",                    request.getParameter("yyyymm"));   	
		map.put("dues_gubun1",               request.getParameter("dues_gubun1"));
		map.put("code_bran1",                request.getParameter("code_bran1"));
    	map.put("code_sub1",                 request.getParameter("code_sub1"));    	
		map.put("sub_dues1",                 request.getParameter("sub_dues1"));		
		if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}	
    	
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatesetupduessub(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);				
		return mapping.findForward("updatesub_ok");	
	} 	
	
	/*공통코드 환경설정*/	
	public ActionForward comcode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("comcodeList"));
	}
	
	public ActionForward comcodeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao(); 
		
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
		List<Map> comcodecnt=dao.comcodecnt(map);
		ncount = ((Integer)(comcodecnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map>  comcode = dao.comcode(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i< comcode.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( comcode.get(i).get("groupcode")	== null ? ""      :     comcode.get(i).get("groupcode"))  +"\",");			
			result.append("\"" + ( comcode.get(i).get("groupname")	== null ? ""      :     comcode.get(i).get("groupname"))  +"\",");		
			result.append("\"" + ( comcode.get(i).get("codelen")	== null ? ""      :     comcode.get(i).get("codelen"))  +"\"");			
			result.append("]}");
		}		
		result.append("]}");		
	
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}
	
	/*공통코드 환경설정 (세부)*/
	public ActionForward comcodeList2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();
		
		map.put("groupcode",        request.getParameter("groupcode"));
		
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
		List<Map> comcodecnt=dao.comcodedetailcnt(map);
		ncount = ((Integer)(comcodecnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map>  comcodedetail = dao.comcodedetail(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i< comcodedetail.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( comcodedetail.get(i).get("groupcode")	== null ? ""   :     comcodedetail.get(i).get("groupcode"))    +"\",");
			result.append("\"" + ( comcodedetail.get(i).get("groupname")	== null ? ""   :     comcodedetail.get(i).get("groupname"))    +"\",");			
			result.append("\"" + ( comcodedetail.get(i).get("detcode")	    == null ? ""   :     comcodedetail.get(i).get("detcode"))      +"\",");
			result.append("\"" + ( comcodedetail.get(i).get("detcodename")	== null ? ""   :     comcodedetail.get(i).get("detcodename"))  +"\",");			
			result.append("\"" + ( comcodedetail.get(i).get("tempcd1")   	== null ? ""   :     comcodedetail.get(i).get("tempcd1"))      +"\",");		
			result.append("\"" + ( comcodedetail.get(i).get("tempcd2")	    == null ? ""   :     comcodedetail.get(i).get("tempcd2"))      +"\",");			
			result.append("\"" + ( comcodedetail.get(i).get("tempcd3")   	== null ? ""   :     comcodedetail.get(i).get("tempcd3"))      +"\",");		
			result.append("\"" + ( comcodedetail.get(i).get("cdseq")	    == null ? ""   :     comcodedetail.get(i).get("cdseq"))        +"\",");			
			result.append("\"" + ( comcodedetail.get(i).get("use_yn")	    == null ? ""   :     comcodedetail.get(i).get("use_yn"))       +"\"");			
			result.append("]}");
		}		
		result.append("]}");		
	
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}
	
	public ActionForward insert_comcode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	setupduesDao dao = new setupduesDao();
    	
    	String groupname1   = URLDecoder.decode(request.getParameter("groupname1"),"UTF-8");
    	String detcodename1 = URLDecoder.decode(request.getParameter("detcodename1"),"UTF-8");
    	
    	map.put("groupcode1",                  request.getParameter("groupcode1"));
    	map.put("groupname1",                  groupname1);   	
		map.put("detcode1",                    request.getParameter("detcode1"));
		map.put("detcodename1",                detcodename1);
    	map.put("cdseq1",                      request.getParameter("cdseq1"));    	
		map.put("tempcd11",                    request.getParameter("tempcd11"));
		map.put("tempcd21",                    request.getParameter("tempcd21"));
		map.put("tempcd31",                    request.getParameter("tempcd31"));
		map.put("use_yn1",                     request.getParameter("use_yn1"));				
		map.put("gr",                     request.getParameter("gr"));
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertcomcode(map);		
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);	
		request.setAttribute("remap", map);
		return mapping.findForward("insertcomcode_ok");	
	} 
	
	public ActionForward update_comcode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();	
		
		String groupname1   = URLDecoder.decode(request.getParameter("groupname1"),"UTF-8");
    	String detcodename1 = URLDecoder.decode(request.getParameter("detcodename1"),"UTF-8");
    	
    	map.put("groupcode1",                  request.getParameter("groupcode1"));
    	map.put("groupname1",                  groupname1);   	
		map.put("detcode1",                    request.getParameter("detcode1"));
		map.put("detcodename1",                detcodename1);
    	map.put("cdseq1",                      request.getParameter("cdseq1"));    	
		map.put("tempcd11",                    request.getParameter("tempcd11"));
		map.put("tempcd21",                    request.getParameter("tempcd21"));
		map.put("tempcd31",                    request.getParameter("tempcd31"));
		map.put("use_yn1",                     request.getParameter("use_yn1"));				
		map.put("gr",                     request.getParameter("gr"));
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatecomcode(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);	
		request.setAttribute("remap", map);
		return mapping.findForward("updatecomcode_ok");	
	} 
	
	/*교육 및 시험명 환경설정*/	
	public ActionForward setupedu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("setupeduList"));
	}
	
	public ActionForward setupeduList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao(); 
		
		if(request.getParameter("isSelect")!=null){
			if(request.getParameter("edutest_name1")!=null){
				String edutest_name1 = URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8");
				map.put("edutest_name1",        edutest_name1);
			}
			if(request.getParameter("code_certifi1")!=null){
				map.put("code_certifi1",        request.getParameter("code_certifi1"));
			}
			if(request.getParameter("code_kind1")!=null){
				map.put("code_kind1",           request.getParameter("code_kind1"));
			}
			if(request.getParameter("outside_yn1")!=null){
				map.put("outside_yn1",          request.getParameter("outside_yn1"));
			}
			if(request.getParameter("edu_group1")!=null){
				map.put("edu_group1",           request.getParameter("edu_group1"));
			}
			if(request.getParameter("use_yn1")!=null){
				map.put("use_yn1",              request.getParameter("use_yn1"));
			}
			if(request.getParameter("conform1")!=null){
				map.put("conform1",          request.getParameter("conform1"));
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
			List<Map> setupeducnt=dao.setupeducnt(map);
			ncount = ((Integer)(setupeducnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> setupedu = dao.setupedu(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
						
			for(int i=0; i<setupedu.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( setupedu.get(i).get("edutest_name")	    == null ? ""         :     URLEncoder.encode(setupedu.get(i).get("edutest_name").toString(), "UTF-8"))    +"\",");			
				result.append("\"" + ( setupedu.get(i).get("code_certifi")	    == null ? ""         :     setupedu.get(i).get("code_certifi"))    +"\",");
				result.append("\"" + ( setupedu.get(i).get("certifi_name")	    == null ? ""         :     setupedu.get(i).get("certifi_name"))    +"\",");
				result.append("\"" + ( setupedu.get(i).get("code_kind")     	== null ? ""         :     setupedu.get(i).get("code_kind"))       +"\",");
				result.append("\"" + ( setupedu.get(i).get("code_kind_name")	== null ? ""         :     setupedu.get(i).get("code_kind_name"))  +"\",");
				result.append("\"" + ( setupedu.get(i).get("outside_yn")	    == null ? ""         :     setupedu.get(i).get("outside_yn"))      +"\",");
				result.append("\"" + ( setupedu.get(i).get("outside_yn_name")	== null ? ""         :     setupedu.get(i).get("outside_yn_name")) +"\",");
				result.append("\"" + ( setupedu.get(i).get("edu_group")	        == null ? ""         :     setupedu.get(i).get("edu_group"))       +"\",");
				result.append("\"" + ( setupedu.get(i).get("edu_group_name")	== null ? ""         :     setupedu.get(i).get("edu_group_name"))  +"\",");
				result.append("\"" + ( setupedu.get(i).get("use_yn")	        == null ? ""         :     setupedu.get(i).get("use_yn"))          +"\",");
				result.append("\"" + ( setupedu.get(i).get("conform")	        == null ? ""         :     setupedu.get(i).get("conform"))         +"\",");
				result.append("\"" + ( setupedu.get(i).get("code_seq")	        == null ? ""         :     setupedu.get(i).get("code_seq"))        +"\",");			
				result.append("\"" + ( setupedu.get(i).get("point_manage")   	== null ? ""         :     setupedu.get(i).get("point_manage"))    +"\",");
//				result.append("\"" + ( setupedu.get(i).get("edu_marks")	        == null ? ""         :     setupedu.get(i).get("edu_marks"))       +"\",");
//				result.append("\"" + ( setupedu.get(i).get("service_marks")    	== null ? ""         :     setupedu.get(i).get("service_marks"))   +"\",");
				result.append("\"" + ( setupedu.get(i).get("print_kind")	    == null ? ""         :     setupedu.get(i).get("print_kind"))      +"\",");
				result.append("\"" + ( setupedu.get(i).get("print_kind_name")	== null ? ""         :     setupedu.get(i).get("print_kind_name")) +"\",");
//				result.append("\"" + ( setupedu.get(i).get("finish_time")	    == null ? ""         :     setupedu.get(i).get("finish_time"))     +"\",");
//				result.append("\"" + ( setupedu.get(i).get("finish_date")	    == null ? ""         :     setupedu.get(i).get("finish_date"))     +"\","); 
//				result.append("\"" + ( setupedu.get(i).get("finish_point")    	== null ? ""         :     setupedu.get(i).get("finish_point"))    +"\",");
//				result.append("\"" + ( setupedu.get(i).get("new_cost")	        == null ? ""         :     setupedu.get(i).get("new_cost"))        +"\",");
//				result.append("\"" + ( setupedu.get(i).get("re_cost")	        == null ? ""         :     setupedu.get(i).get("re_cost"))         +"\",");
//				result.append("\"" + ( setupedu.get(i).get("mr_cost")	        == null ? ""         :     setupedu.get(i).get("mr_cost"))         +"\",");
				result.append("\"" + ( setupedu.get(i).get("remark")	        == null ? ""         :     URLEncoder.encode(setupedu.get(i).get("remark").toString(), "UTF-8"))          +"\"");
				result.append("]}");
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		}
		return (mapping.findForward("ajaxout"));	
	}	
	
	public ActionForward insert_edu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
    	HttpSession session=request.getSession();
    	setupduesDao dao = new setupduesDao(); 
    	Map map = new HashMap();
    	
    	String edutest_name1 = URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8");
    	    	
    	map.put("register1",                 session.getAttribute("G_NAME"));
		map.put("edutest_name1",             edutest_name1);
		map.put("code_certifi1",             request.getParameter("code_certifi1"));
		map.put("code_kind1",                request.getParameter("code_kind1"));
		map.put("outside_yn1",               request.getParameter("outside_yn1"));
		map.put("edu_group1",                request.getParameter("edu_group1"));
		map.put("use_yn1",                   request.getParameter("use_yn1"));
		map.put("conform1",                  request.getParameter("conform1"));
		map.put("code_seq1",                 request.getParameter("code_seq1"));    			
	    map.put("point_manage1",             request.getParameter("point_manage1"));   	
//		map.put("edu_marks1",                request.getParameter("edu_marks1"));
//		map.put("service_marks1",            request.getParameter("service_marks1"));
		map.put("print_kind1",               request.getParameter("print_kind1"));   	
//	    map.put("finish_time1",              request.getParameter("finish_time1"));
//    	map.put("finish_date1",              request.getParameter("finish_date1"));    	
//		map.put("finish_point1",             request.getParameter("finish_point1"));
//		map.put("new_cost1",                 request.getParameter("new_cost1"));
//		map.put("re_cost1",                  request.getParameter("re_cost1"));	
//		map.put("mr_cost1",                  request.getParameter("mr_cost1"));	
		map.put("remark1",                   request.getParameter("remark1"));
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertedu(map);	
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		if(request.getParameter("edutest_name2")!=null){			
			String edutest_name2 = URLDecoder.decode(request.getParameter("edutest_name2"),"UTF-8");
			map.put("edutest_name2",        edutest_name2);}							
		if(request.getParameter("code_certifi2")!=null){
			map.put("code_certifi2",        request.getParameter("code_certifi2"));}	
		if(request.getParameter("code_kind2")!=null){
			map.put("code_kind2",           request.getParameter("code_kind2"));}	
		if(request.getParameter("outside_yn2")!=null){	
			map.put("outside_yn2",          request.getParameter("outside_yn2"));}
		if(request.getParameter("edu_group2")!=null){	 
			map.put("edu_group2",           request.getParameter("edu_group2"));}
		if(request.getParameter("use_yn2")!=null){
			map.put("use_yn2",              request.getParameter("use_yn2"));}
		if(request.getParameter("conform2")!=null){
			map.put("conform2",          request.getParameter("conform2"));}
		
		request.setAttribute("errMsg", errMsg);	
		request.setAttribute("remap",map);
		return mapping.findForward("insertedu_ok");	
	} 
	
	public ActionForward update_edu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		setupduesDao dao = new setupduesDao();	
    	Map map = new HashMap();      	
		
    	String edutest_name1 = URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8");  
    	
    	map.put("register1",                 session.getAttribute("G_NAME"));
		map.put("edutest_name1",             edutest_name1);
		map.put("code_certifi1",             request.getParameter("code_certifi1"));
		map.put("code_seq1",                 request.getParameter("code_seq1"));
		map.put("code_kind1",                request.getParameter("code_kind1"));
		map.put("outside_yn1",               request.getParameter("outside_yn1"));
		map.put("edu_group1",                request.getParameter("edu_group1"));
		map.put("use_yn1",                   request.getParameter("use_yn1"));
		map.put("conform1",                  request.getParameter("conform1"));
		map.put("code_seq1",                 request.getParameter("code_seq1"));    			
	    map.put("point_manage1",             request.getParameter("point_manage1"));   	
//		map.put("edu_marks1",                request.getParameter("edu_marks1"));
//		map.put("service_marks1",            request.getParameter("service_marks1"));
		map.put("print_kind1",               request.getParameter("print_kind1"));   	
//	    map.put("finish_time1",              request.getParameter("finish_time1"));
//    	map.put("finish_date1",              request.getParameter("finish_date1"));    	
//		map.put("finish_point1",             request.getParameter("finish_point1"));
//		map.put("new_cost1",                 request.getParameter("new_cost1"));
//		map.put("re_cost1",                  request.getParameter("re_cost1"));	
//		map.put("mr_cost1",                  request.getParameter("mr_cost1"));	
		map.put("remark1",                   request.getParameter("remark1"));  	
    	
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updateedu(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		if(request.getParameter("edutest_name2")!=null){			
			String edutest_name2 = URLDecoder.decode(request.getParameter("edutest_name2"),"UTF-8");
			map.put("edutest_name2",        edutest_name2);}							
		if(request.getParameter("code_certifi2")!=null){
			map.put("code_certifi2",        request.getParameter("code_certifi2"));}	
		if(request.getParameter("code_kind2")!=null){
			map.put("code_kind2",           request.getParameter("code_kind2"));}	
		if(request.getParameter("outside_yn2")!=null){	
			map.put("outside_yn2",          request.getParameter("outside_yn2"));}
		if(request.getParameter("edu_group2")!=null){	 
			map.put("edu_group2",           request.getParameter("edu_group2"));}
		if(request.getParameter("use_yn2")!=null){
			map.put("use_yn2",              request.getParameter("use_yn2"));}
		if(request.getParameter("conform2")!=null){
			map.put("conform2",          request.getParameter("conform2"));}
		
		request.setAttribute("errMsg", errMsg);	
		request.setAttribute("remap",map);
		return mapping.findForward("updateedu_ok");	
	} 
	
	/* 자격증 종류 */	
	public ActionForward setupcertifi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("setupcertifiList"));
	}
	
	public ActionForward setupcertifiList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			Map map = new HashMap();
			setupduesDao dao = new setupduesDao();
			
			if(request.getParameter("use_yn1")!=null){
				map.put("use_yn1",              request.getParameter("use_yn1"));}
		
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
			List<Map> setupcertificnt=dao.setupcertificnt(map);
			ncount = ((Integer)(setupcertificnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> setupcertifi = dao.setupcertifi(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<setupcertifi.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( setupcertifi.get(i).get("code_certifi")	== null ? ""         :     setupcertifi.get(i).get("code_certifi"))  +"\",");			
				result.append("\"" + ( setupcertifi.get(i).get("certifi_name")	== null ? ""         :     setupcertifi.get(i).get("certifi_name"))  +"\",");
				result.append("\"" + ( setupcertifi.get(i).get("renewal")	    == null ? ""         :     setupcertifi.get(i).get("renewal"))  +"\",");
				result.append("\"" + ( setupcertifi.get(i).get("finish_marks")	== null ? ""         :     setupcertifi.get(i).get("finish_marks"))  +"\",");
				result.append("\"" + ( setupcertifi.get(i).get("center_marks")	== null ? ""         :     setupcertifi.get(i).get("center_marks"))  +"\",");
				result.append("\"" + ( setupcertifi.get(i).get("service_marks")	== null ? ""         :     setupcertifi.get(i).get("service_marks"))  +"\",");
				result.append("\"" + ( setupcertifi.get(i).get("use_yn")	    == null ? ""         :     setupcertifi.get(i).get("use_yn"))  +"\"");				
				result.append("]}");
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		
		return (mapping.findForward("ajaxout"));	
	}
	
	public ActionForward insert_certifi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	setupduesDao dao = new setupduesDao(); 
    	
    	String certifi_name1 = URLDecoder.decode(request.getParameter("certifi_name1"),"UTF-8");
    	    	
    	map.put("certifi_name1",             certifi_name1);
    	if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}	
		map.put("code_certifi1",             request.getParameter("code_certifi1"));
		map.put("renewal1",                  request.getParameter("renewal1"));
		map.put("finish_marks1",             request.getParameter("finish_marks1"));
		map.put("center_marks1",             request.getParameter("center_marks1"));
		map.put("service_marks1",            request.getParameter("service_marks1"));
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertcertifi(map);			
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);		
		return mapping.findForward("insertcertifi_ok");	
	} 
	
	public ActionForward update_certifi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();	
		
		String certifi_name1 = URLDecoder.decode(request.getParameter("certifi_name1"),"UTF-8");
    	
    	map.put("certifi_name1",             certifi_name1);
    	if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}	
		map.put("code_certifi1",             request.getParameter("code_certifi1"));
		map.put("renewal1",                  request.getParameter("renewal1"));
		map.put("finish_marks1",             request.getParameter("finish_marks1"));
		map.put("center_marks1",             request.getParameter("center_marks1"));
		map.put("service_marks1",            request.getParameter("service_marks1"));
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatecertifi(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);				
		return mapping.findForward("updatecertifi_ok");	
	} 
	
	/* 산하단체명 */	
	public ActionForward setupsub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("setupsubList"));
	}
	
	public ActionForward setupsubList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			Map map = new HashMap();
			setupduesDao dao = new setupduesDao();
			
			if(request.getParameter("use_yn1")!=null){
				map.put("use_yn1",              request.getParameter("use_yn1"));}
			
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
			List<Map> setupsubcnt=dao.setupsubcnt(map);
			ncount = ((Integer)(setupsubcnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> setupsub = dao.setupsub(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<setupsub.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( setupsub.get(i).get("code_sub")	         == null ? ""     :     setupsub.get(i).get("code_sub"))  +"\",");			
				result.append("\"" + ( setupsub.get(i).get("sub_name")           == null ? ""     :     setupsub.get(i).get("sub_name"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("sub_tel")	         == null ? ""     :     setupsub.get(i).get("sub_tel"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("sub_fax")	         == null ? ""     :     setupsub.get(i).get("sub_fax"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("sub_master")	     == null ? ""     :     setupsub.get(i).get("sub_master"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("sub_email")          == null ? ""     :     setupsub.get(i).get("sub_email"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("code_email_name")	 == null ? ""     :     setupsub.get(i).get("code_email_name"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("code_email_comp")	 == null ? ""     :     setupsub.get(i).get("code_email_comp"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("sub_account")	     == null ? ""     :     setupsub.get(i).get("sub_account"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("sub_bank")	         == null ? ""     :     setupsub.get(i).get("sub_bank"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("sub_bank_ints")	     == null ? ""     :     setupsub.get(i).get("sub_bank_ints"))  +"\",");
				result.append("\"" + ( setupsub.get(i).get("use_yn")	         == null ? ""     :     setupsub.get(i).get("use_yn"))  +"\"");				
				result.append("]}");
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		
		return (mapping.findForward("ajaxout"));	
	}
	
	public ActionForward insertsub_sub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	setupduesDao dao = new setupduesDao(); 
    	
    	String sub_name1 = URLDecoder.decode(request.getParameter("sub_name1"),"UTF-8");
    	    	
    	map.put("sub_name1",             sub_name1);		
		map.put("code_sub1",             request.getParameter("code_sub1"));
		map.put("sub_tel1",              request.getParameter("sub_tel1"));
		map.put("sub_fax1",              request.getParameter("sub_fax1"));
		map.put("sub_master1",           request.getParameter("sub_master1"));
		map.put("sub_email1",            request.getParameter("sub_email1"));
		map.put("code_email_comp1",      request.getParameter("code_email_comp1"));
		map.put("sub_account1",          request.getParameter("sub_account1"));
		map.put("sub_bank1",             request.getParameter("sub_bank1"));
		map.put("sub_bank_ints1",        request.getParameter("sub_bank_ints1"));
		if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}	
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertsub(map);	
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);		
		return mapping.findForward("insertsubsub_ok");	
	} 
	
	public ActionForward updatesub_sub(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap(); 
		setupduesDao dao = new setupduesDao();		
		
		String sub_name1 = URLDecoder.decode(request.getParameter("sub_name1"),"UTF-8");
    	
    	map.put("sub_name1",             sub_name1);		
		map.put("code_sub1",             request.getParameter("code_sub1"));
		map.put("sub_tel1",              request.getParameter("sub_tel1"));
		map.put("sub_fax1",              request.getParameter("sub_fax1"));
		map.put("sub_master1",           request.getParameter("sub_master1"));
		map.put("sub_email1",            request.getParameter("sub_email1"));
		map.put("code_email_comp1",      request.getParameter("code_email_comp1"));
		map.put("sub_account1",          request.getParameter("sub_account1"));
		map.put("sub_bank1",             request.getParameter("sub_bank1"));
		map.put("sub_bank_ints1",        request.getParameter("sub_bank_ints1"));
		if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}		

		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			
			map.put("nstart", 0);
			map.put("nend", 10);
			List<Map> setupsub = dao.setupsub(map);
			if(setupsub!=null && setupsub.size()>0){
				String sub_email = StringUtil.NVL(String.valueOf(setupsub.get(0).get("sub_email")));
				if(!StringUtil.NVL(request.getParameter("sub_email1")).equals(sub_email)){
					map.put("sub_email_upok","Y");
				}
			}
			
			int result = dao.updatesub(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);					
		return mapping.findForward("updatesubsub_ok");	
	} 
	
	/* 사용자 등록 */	
	public ActionForward setupuser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("setupuserList"));
	}
	
	public ActionForward setupuserList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			Map map = new HashMap();
			setupduesDao dao = new setupduesDao();
			
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
			List<Map> setupusercnt=dao.setupusercnt(map);
			ncount = ((Integer)(setupusercnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> setupuser = dao.setupuser(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<setupuser.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( setupuser.get(i).get("UserName")	       == null ? ""     :     setupuser.get(i).get("UserName"))  +"\",");			
				result.append("\"" + ( setupuser.get(i).get("UserId")          == null ? ""     :     setupuser.get(i).get("UserId"))  +"\",");
				result.append("\"" + ( setupuser.get(i).get("UserPW")	       == null ? ""     :     setupuser.get(i).get("UserPW"))  +"\",");
				result.append("\"" + ( setupuser.get(i).get("UserBranname")    == null ? ""     :     setupuser.get(i).get("UserBranname"))  +"\",");
				result.append("\"" + ( setupuser.get(i).get("UserBran")	       == null ? ""     :     setupuser.get(i).get("UserBran"))  +"\",");
				result.append("\"" + ( setupuser.get(i).get("UserPowername")   == null ? ""     :     setupuser.get(i).get("UserPowername"))  +"\",");
				result.append("\"" + ( setupuser.get(i).get("UserPower")	   == null ? ""     :     setupuser.get(i).get("UserPower"))  +"\",");
				result.append("\"" + ( setupuser.get(i).get("use_yn")	       == null ? ""     :     setupuser.get(i).get("use_yn"))  +"\"");				
				result.append("]}");
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		
		return (mapping.findForward("ajaxout"));	
	}
	
	/* 프로그램 리스트 */	
	public ActionForward setupuserList2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			Map map = new HashMap();
			setupduesDao dao = new setupduesDao();
			
			map.put("UserId",    request.getParameter("UserId"));			
			
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
			List<Map> setupusercnt2=dao.setupusercnt2(map);
			ncount = ((Integer)(setupusercnt2.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> setupuser2 = dao.setupuser2(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");

			for(int i=0; i<setupuser2.size(); i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( setupuser2.get(i).get("progid")	  	   == null ? ""     :     setupuser2.get(i).get("progid"))  +"\",");			
				result.append("\"" + ( setupuser2.get(i).get("progname")       == null ? ""     :     setupuser2.get(i).get("progname"))  +"\",");
				result.append("\"" + ( setupuser2.get(i).get("proghanname")	   == null ? ""     :     setupuser2.get(i).get("proghanname"))  +"\",");
				result.append("\"" + ( setupuser2.get(i).get("use_yn")	       == null ? ""     :     setupuser2.get(i).get("use_yn"))  +"\"");			
				result.append("]}");			
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		
		return (mapping.findForward("ajaxout"));	
	}
	
public ActionForward insert_user(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	
    	String UserName1 = URLDecoder.decode(request.getParameter("UserName1"),"UTF-8");
    	    	
    	map.put("UserName1",             UserName1);		
		map.put("UserId1",               request.getParameter("UserId1"));
		map.put("UserPW1",               request.getParameter("UserPW1"));
		map.put("UserBran1",             request.getParameter("UserBran1"));
		map.put("UserPower1",            request.getParameter("UserPower1"));
		map.put("use_yn1",               request.getParameter("use_yn1"));
				
		setupduesDao dao = new setupduesDao(); 
		
		int ncount = 0;
		List<Map> setupuserexist = dao.setupuserexist(map);
		ncount = ((Integer)(setupuserexist.get(0).get("cnt"))).intValue();
		
		String errMsg="";
		if (ncount > 0) {
			errMsg="중복 아이디 입니다.";
		}else{
			List<Map> result = dao.insertuser(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}
		
		request.setAttribute("errMsg", errMsg);
		return mapping.findForward("insertuser_ok");	
	} 
	
	public ActionForward update_user(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();      	
		
		String UserName1 = URLDecoder.decode(request.getParameter("UserName1"),"UTF-8");
    	
    	map.put("UserName1",             UserName1);		
		map.put("UserId1",               request.getParameter("UserId1"));		
		map.put("UserBran1",             request.getParameter("UserBran1"));
		map.put("UserPower1",            request.getParameter("UserPower1"));
		map.put("use_yn1",               request.getParameter("use_yn1"));
		
		setupduesDao dao = new setupduesDao();
		int result = dao.updateuser(map);
		
		if(!request.getParameter("UserPW1").equals("00000000")){			
			map.put("UserPW1",           request.getParameter("UserPW1"));
			int result1 = dao.updatepass(map);
		}		
				
		return mapping.findForward("updateuser_ok");	
	} 
	
	public ActionForward update_user1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();		
		
		map.put("UserId1",               request.getParameter("UserId1"));

		setupduesDao dao = new setupduesDao();		
		int result = dao.deleteuser1(map);
		
		String temp="";
		if(request.getParameter("progid1")!=null){
			temp=request.getParameter("progid1");
		}
		String progid[]=temp.split(",");
		
		map.put("use_yn1",               "1");
		
		if(progid.length>0){
			for(int i=0;i<progid.length;i++){
				map.put("progid1", progid[i]);
				List<Map> insertuser11 = dao.insertuser1(map);
			}
		}
		
		return mapping.findForward("updateuser1_ok");	
	} 
	
	/* 기관지 등록 */	
	public ActionForward setupbooks(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("setupbooksList"));
	}
	
	public ActionForward setupbooksList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			Map map = new HashMap();
			setupduesDao dao = new setupduesDao();
			
			if(request.getParameter("code_book_kind1")!=null){	
				map.put("code_book_kind1",       request.getParameter("code_book_kind1"));}
			if(request.getParameter("use_yn1")!=null){	
				map.put("use_yn1",               request.getParameter("use_yn1"));}	
						
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
			List<Map> setupbookscnt=dao.setupbookscnt(map);
			ncount = ((Integer)(setupbookscnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> setupbooks = dao.setupbooks(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<setupbooks.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( setupbooks.get(i).get("code_book_kind")	   == null ? ""     :     setupbooks.get(i).get("code_book_kind"))  +"\",");			
				result.append("\"" + ( setupbooks.get(i).get("book_name")          == null ? ""     :     setupbooks.get(i).get("book_name"))  +"\",");
				result.append("\"" + ( setupbooks.get(i).get("book_maker")	       == null ? ""     :     setupbooks.get(i).get("book_maker"))  +"\",");
				result.append("\"" + ( setupbooks.get(i).get("company_tel")        == null ? ""     :     setupbooks.get(i).get("company_tel"))  +"\",");
				result.append("\"" + ( setupbooks.get(i).get("pers_name")	       == null ? ""     :     setupbooks.get(i).get("pers_name"))  +"\",");
				result.append("\"" + ( setupbooks.get(i).get("job_kind")           == null ? ""     :     setupbooks.get(i).get("job_kind"))  +"\",");				
				result.append("\"" + ( setupbooks.get(i).get("use_yn")	           == null ? ""     :     setupbooks.get(i).get("use_yn"))  +"\"");				
				result.append("]}");
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		
		return (mapping.findForward("ajaxout"));	
	}
	
public ActionForward insert_books(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	setupduesDao dao = new setupduesDao(); 
    	
    	String book_name1  = URLDecoder.decode(request.getParameter("book_name1"),"UTF-8");
    	String book_maker1 = URLDecoder.decode(request.getParameter("book_maker1"),"UTF-8");
    	String pers_name1  = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
    	String job_kind1   = URLDecoder.decode(request.getParameter("job_kind1"),"UTF-8");
    	    	
    	map.put("book_name1",              book_name1);
    	map.put("book_maker1",             book_maker1);
    	map.put("pers_name1",              pers_name1);
    	map.put("job_kind1",               job_kind1);
		map.put("code_book_kind1",         request.getParameter("code_book_kind1"));
		map.put("company_tel1",            request.getParameter("company_tel1"));
		if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertbooks(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);
		return mapping.findForward("insertbooks_ok");	
	} 
	
	public ActionForward update_books(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();
		setupduesDao dao = new setupduesDao();
		
		String book_name1  = URLDecoder.decode(request.getParameter("book_name1"),"UTF-8");
    	String book_maker1 = URLDecoder.decode(request.getParameter("book_maker1"),"UTF-8");
    	String pers_name1  = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
    	String job_kind1   = URLDecoder.decode(request.getParameter("job_kind1"),"UTF-8");
    	    	
    	map.put("book_name1",              book_name1);
    	map.put("book_maker1",             book_maker1);
    	map.put("pers_name1",              pers_name1);
    	map.put("job_kind1",               job_kind1);
		map.put("code_book_kind1",         request.getParameter("code_book_kind1"));
		map.put("company_tel1",            request.getParameter("company_tel1"));
		if(request.getParameter("use_yn1").equals("")){			
			map.put("use_yn1",                   "Y");
		}else{
			map.put("use_yn1",                   request.getParameter("use_yn1"));
		}		

		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatebooks(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);					
		return mapping.findForward("updatebooks_ok");	
	} 
	
	/*목표 인원 */	
	public ActionForward setuptarget(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("setuptargetList"));
	}
	
	public ActionForward setuptargetList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			Map map = new HashMap();
			setupduesDao dao = new setupduesDao();
			
			if(request.getParameter("yyyy1")!=null){
				map.put("yyyy1",                request.getParameter("yyyy1"));}	
			if(request.getParameter("code_bran1")!=null){
				map.put("code_bran1",           request.getParameter("code_bran1"));}	
			if(request.getParameter("code_big1")!=null){	
				map.put("code_big1",            request.getParameter("code_big1"));}
						
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
			List<Map> setuptargetcnt=dao.setuptargetcnt(map);
			ncount = ((Integer)(setuptargetcnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> setuptarget = dao.setuptarget(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<setuptarget.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( setuptarget.get(i).get("yyyy")	           == null ? ""     :     setuptarget.get(i).get("yyyy"))  +"\",");	
				result.append("\"" + ( setuptarget.get(i).get("code_branname")     == null ? ""     :     setuptarget.get(i).get("code_branname"))  +"\",");
				result.append("\"" + ( setuptarget.get(i).get("code_bran")         == null ? ""     :     setuptarget.get(i).get("code_bran"))  +"\",");
				result.append("\"" + ( setuptarget.get(i).get("code_bigname")	   == null ? ""     :     setuptarget.get(i).get("code_bigname"))  +"\",");
				result.append("\"" + ( setuptarget.get(i).get("code_big")	       == null ? ""     :     setuptarget.get(i).get("code_big"))  +"\",");
				result.append("\"" + ( setuptarget.get(i).get("target_cnt")        == null ? ""     :     setuptarget.get(i).get("target_cnt"))  +"\"");						
				result.append("]}");
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		
		return (mapping.findForward("ajaxout"));	
	}
	
public ActionForward insert_target(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		setupduesDao dao = new setupduesDao(); 
    	Map map = new HashMap();
    	
    	map.put("register1",     		 session.getAttribute("G_NAME"));
    	map.put("yyyy1",                 request.getParameter("yyyy1"));
		map.put("code_bran1",            request.getParameter("code_bran1"));
		map.put("code_big1",             request.getParameter("code_big1"));
		map.put("target_cnt1",           request.getParameter("target_cnt1"));		
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.inserttarget(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);		
		return mapping.findForward("inserttarget_ok");	
	} 
	
	public ActionForward update_target(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		setupduesDao dao = new setupduesDao();	
		Map map = new HashMap();      	
		
		map.put("register1",     		 session.getAttribute("G_NAME"));
    	map.put("yyyy1",                 request.getParameter("yyyy1"));
		map.put("code_bran1",            request.getParameter("code_bran1"));
		map.put("code_big1",             request.getParameter("code_big1"));
		map.put("target_cnt1",           request.getParameter("target_cnt1"));		
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatetarget(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);				
		return mapping.findForward("updatetarget_ok");	
	} 
	
}
	
