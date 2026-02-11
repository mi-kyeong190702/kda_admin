package com.ant.conference.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.common.util.CommonUtil;
import com.ant.conference.dao.conferenceDao;
import com.ant.educationlecture.dao.licenseDao;
import com.ant.member.state.dao.memberStateDao;

public class conferenceAction extends DispatchAction {
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
	/*회의실 일자별*/	
	public ActionForward conference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("conferenceList"));
	}
	
	public ActionForward conferenceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		conferenceDao dao = new conferenceDao(); 
		
		if(request.getParameter("register1")!=null){			
			String register1 = URLDecoder.decode(request.getParameter("register1"),"UTF-8");
			map.put("register1",        register1);}		
		if(request.getParameter("conference_dt1")!=null){
			map.put("conference_dt1",      request.getParameter("conference_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}
		if(request.getParameter("code_conferencename1")!=null){	 
			map.put("code_conferencename1",          request.getParameter("code_conferencename1"));}	
		if(request.getParameter("center1")!=null){	 
			map.put("center1",          request.getParameter("center1"));}		
	    
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
		List<Map> conferencecnt=dao.conferencecnt(map);
		ncount = ((Integer)(conferencecnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> conference = dao.conference(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i<conference.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( conference.get(i).get("conference_seq")	    == null ? ""     :     conference.get(i).get("conference_seq"))       +"\",");			
			result.append("\"" + ( conference.get(i).get("code_conference")	    == null ? ""     :     conference.get(i).get("code_conference"))      +"\",");
			result.append("\"" + ( conference.get(i).get("use_st_hhmm")	        == null ? ""     :     conference.get(i).get("use_st_hhmm"))          +"\",");
			result.append("\"" + ( conference.get(i).get("use_ed_hhmm")	        == null ? ""     :     conference.get(i).get("use_ed_hhmm"))          +"\",");
			result.append("\"" + ( conference.get(i).get("subject")      	    == null ? ""     :     conference.get(i).get("subject"))              +"\",");   
			result.append("\"" + ( conference.get(i).get("register")	        == null ? ""     :     conference.get(i).get("register"))             +"\",");			
			result.append("\"" + ( conference.get(i).get("conference_dt")	    == null ? ""     :     conference.get(i).get("conference_dt"))        +"\",");
			result.append("\"" + ( conference.get(i).get("code_conferencename")	== null ? ""     :     conference.get(i).get("code_conferencename"))  +"\",");
			result.append("\"" + ( conference.get(i).get("detcodename")	        == null ? ""     :     conference.get(i).get("detcodename"))          +"\",");
			result.append("\"" + ( conference.get(i).get("center")	            == null ? ""     :     conference.get(i).get("center"))               +"\",");			
			result.append("\"" + ( conference.get(i).get("k08")	                == null ? ""     :     conference.get(i).get("k08"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k09")	                == null ? ""     :     conference.get(i).get("k09"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k10")	                == null ? ""     :     conference.get(i).get("k10"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k11")	                == null ? ""     :     conference.get(i).get("k11"))  +"\","); 
			result.append("\"" + ( conference.get(i).get("k12")	                == null ? ""     :     conference.get(i).get("k12"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k13")	                == null ? ""     :     conference.get(i).get("k13"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k14")	                == null ? ""     :     conference.get(i).get("k14"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k15")	                == null ? ""     :     conference.get(i).get("k15"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k16")	                == null ? ""     :     conference.get(i).get("k16"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k17")	                == null ? ""     :     conference.get(i).get("k17"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k18")	                == null ? ""     :     conference.get(i).get("k18"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k19")	                == null ? ""     :     conference.get(i).get("k19"))  +"\",");			
			result.append("\"" + ( conference.get(i).get("k20")	                == null ? ""     :     conference.get(i).get("k20"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k21")	                == null ? ""     :     conference.get(i).get("k21"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k22")	                == null ? ""     :     conference.get(i).get("k22"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k23")	                == null ? ""     :     conference.get(i).get("k23"))  +"\",");			
			result.append("\"" + ( conference.get(i).get("k24")	                == null ? ""     :     conference.get(i).get("k24"))  +"\"");			
			result.append("]}");
		}	
		
		result.append("]}");		
	
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	public ActionForward insert_conference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
    	HttpSession session=request.getSession();
    	conferenceDao dao = new conferenceDao();
    	Map map = new HashMap();
    	
    	map.put("updater",                   session.getAttribute("G_NAME"));		
	    map.put("conference_dt1",            request.getParameter("conference_dt1"));   	
		map.put("code_conferencename1",      request.getParameter("code_conferencename1"));
		map.put("conference_seq1",           request.getParameter("conference_seq1"));   	
	    map.put("use_st_hhmm1",              request.getParameter("use_st_hhmm1"));
    	map.put("use_st_hhmm2",              request.getParameter("use_st_hhmm2"));    	
		map.put("use_ed_hhmm1",              request.getParameter("use_ed_hhmm1"));
		map.put("use_ed_hhmm2",              request.getParameter("use_ed_hhmm2"));
		map.put("center1",                   request.getParameter("center1"));	
		map.put("register1",                 request.getParameter("register1"));
		map.put("subject1",                  request.getParameter("subject1"));	
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertconference(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);			
		return mapping.findForward("insert_ok");	
	} 
	
	public ActionForward update_conference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		conferenceDao dao = new conferenceDao();
    	Map map = new HashMap();      	
		
    	map.put("updater",                   session.getAttribute("G_NAME"));
    	map.put("conference_dt1",            request.getParameter("conference_dt1"));   
		map.put("code_conferencename1",      request.getParameter("code_conferencename1"));	
    	map.put("conference_seq1",           request.getParameter("conference_seq1"));
    	map.put("use_st_hhmm1",              request.getParameter("use_st_hhmm1"));
     	map.put("use_st_hhmm2",              request.getParameter("use_st_hhmm2"));    	
 		map.put("use_ed_hhmm1",              request.getParameter("use_ed_hhmm1"));
 		map.put("use_ed_hhmm2",              request.getParameter("use_ed_hhmm2"));
 		map.put("center1",                   request.getParameter("center1"));
 		map.put("register1",                 request.getParameter("register1"));
		map.put("subject1",                  request.getParameter("subject1"));	    	
    	
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updateconference(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);					
		return mapping.findForward("update_ok");	
	} 
	
	public ActionForward delete_conference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		conferenceDao dao = new conferenceDao();
    	Map map = new HashMap();
		map.put("conference_dt1",            request.getParameter("conference_dt1"));   
		map.put("code_conferencename1",      request.getParameter("code_conferencename1"));	
    	map.put("conference_seq1",           request.getParameter("conference_seq1"));
    	
    	String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 삭제했습니다.";	
		}else{
			int result = dao.deleteconference(map);
			resetToken(request);
			errMsg="삭제했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);
		return mapping.findForward("delete_ok");
	}
	
	/*회의실 회의실별*/	
	public ActionForward conferencemeet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("conferencemeetList"));
	}
	
	public ActionForward conferencemeetList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		conferenceDao dao = new conferenceDao(); 
		
		map.put("conference_dt1",    request.getParameter("conference_dt1"));
	    
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
		List<Map> conferencecnt=dao.conferencecnt(map);
		ncount = ((Integer)(conferencecnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> conference = dao.conferencemeet(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i<conference.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( conference.get(i).get("conference_seq")	== null ? ""     :     conference.get(i).get("conference_seq"))  +"\",");			
			result.append("\"" + ( conference.get(i).get("conference_dt")	== null ? ""     :     conference.get(i).get("conference_dt"))  +"\",");
			result.append("\"" + ( conference.get(i).get("code_conference")	== null ? ""     :     conference.get(i).get("code_conference"))  +"\",");
			result.append("\"" + ( conference.get(i).get("use_st_hhmm")	== null ? ""         :     conference.get(i).get("use_st_hhmm"))  +"\",");
			result.append("\"" + ( conference.get(i).get("use_ed_hhmm")	== null ? ""         :     conference.get(i).get("use_ed_hhmm"))  +"\",");
			result.append("\"" + ( conference.get(i).get("subject")	== null ? ""             :     conference.get(i).get("subject"))  +"\",");
			result.append("\"" + ( conference.get(i).get("detcodename")	== null ? ""         :     conference.get(i).get("detcodename"))  +"\",");
			result.append("\"" + ( conference.get(i).get("detcode")	== null ? ""             :     conference.get(i).get("detcode"))  +"\",");			
			result.append("\"" + ( conference.get(i).get("code_conferencename")	== null ? "" :     conference.get(i).get("code_conferencename"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k08")	== null ? ""                 :     conference.get(i).get("k08"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k09")	== null ? ""                 :     conference.get(i).get("k09"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k10")	== null ? ""                 :     conference.get(i).get("k10"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k11")	== null ? ""                 :     conference.get(i).get("k11"))  +"\","); 
			result.append("\"" + ( conference.get(i).get("k12")	== null ? ""                 :     conference.get(i).get("k12"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k13")	== null ? ""                 :     conference.get(i).get("k13"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k14")	== null ? ""                 :     conference.get(i).get("k14"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k15")	== null ? ""                 :     conference.get(i).get("k15"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k16")	== null ? ""                 :     conference.get(i).get("k16"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k17")	== null ? ""                 :     conference.get(i).get("k17"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k18")	== null ? ""                 :     conference.get(i).get("k18"))  +"\",");
			result.append("\"" + ( conference.get(i).get("k19")	== null ? ""                 :     conference.get(i).get("k19"))  +"\",");			
			result.append("\"" + ( conference.get(i).get("k20")	== null ? ""                 :     conference.get(i).get("k20"))  +"\"");			
			result.append("]}");
		}	
		
		result.append("]}");
		
	
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	public ActionForward insert_conferencemeet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
    	HttpSession session=request.getSession();
    	conferenceDao dao = new conferenceDao();
    	Map map = new HashMap();
    	
    	map.put("register1",                 session.getAttribute("G_NAME"));		
	    map.put("conference_dt1",            request.getParameter("conference_dt1"));   	
		map.put("code_conferencename1",      request.getParameter("code_conferencename1"));
		map.put("conference_seq1",           request.getParameter("conference_seq1"));   	
	    map.put("use_st_hhmm1",              request.getParameter("use_st_hhmm1"));
    	map.put("use_st_hhmm2",              request.getParameter("use_st_hhmm2"));    	
		map.put("use_ed_hhmm1",              request.getParameter("use_ed_hhmm1"));
		map.put("use_ed_hhmm2",              request.getParameter("use_ed_hhmm2"));
		map.put("center1",                   request.getParameter("center1"));	
		map.put("subject1",                  request.getParameter("subject1"));	
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertconference(map);	
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);			
		return mapping.findForward("insert_meetok");	
	} 
	
	public ActionForward update_conferencemeet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		conferenceDao dao = new conferenceDao();	
    	Map map = new HashMap();      	
		
    	map.put("register1",                 session.getAttribute("G_NAME"));
    	map.put("conference_dt1",            request.getParameter("conference_dt1"));   
		map.put("code_conferencename1",      request.getParameter("code_conferencename1"));	
    	map.put("conference_seq1",           request.getParameter("conference_seq1"));
    	map.put("use_st_hhmm1",              request.getParameter("use_st_hhmm1"));
     	map.put("use_st_hhmm2",              request.getParameter("use_st_hhmm2"));    	
 		map.put("use_ed_hhmm1",              request.getParameter("use_ed_hhmm1"));
 		map.put("use_ed_hhmm2",              request.getParameter("use_ed_hhmm2"));
 		map.put("center1",                   request.getParameter("center1"));	
		map.put("subject1",                  request.getParameter("subject1"));	    	
    	
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updateconference(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);					
		return mapping.findForward("update_meetok");	
	} 
	
	public void conferenceListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1= new HashMap();
		conferenceDao dao = new conferenceDao();  
		memberStateDao dao1=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		HttpSession session=request.getSession();
		String user=session.getAttribute("G_ID").toString(); //유저ID
		//여기부터 개발자 변경 필요
		String name="conferenceList"; //프로그램명==>자격증심사 엑셀저장
		String s_name="회의실관리"; //엑셀 시트명
		String header_e[]={"conferencedt","code_conferencename", "st_time", "ed_time", "subject", "detcodename","register"}; //헤더 영문
		String header_k[]={"회의일자","회의실", "시작시간", "종료시간", "주제", "주관부서","신청자"}; //헤더 국문
		int c_size[]={20,20,10,10,40,13,20};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("register1")!=null){			
			String register1 = URLDecoder.decode(request.getParameter("register1"),"UTF-8");
			map.put("register1",        register1);}		
		if(request.getParameter("conference_dt1")!=null){
			map.put("conference_dt1",      request.getParameter("conference_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}
		if(request.getParameter("code_conferencename1")!=null){	 
			map.put("code_conferencename1",          request.getParameter("code_conferencename1"));}	
		if(request.getParameter("center1")!=null){	 
			map.put("center1",          request.getParameter("center1"));}
		map.put("nstart", 0);
		map.put("nend", 10000000);
		List<Map> conference = dao.conference(map);
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		
		
		//-----------여기까지 변경---------------------------------------------------------------------------------------
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(conference, filename, s_name,header_e,header_k,c_size);
		
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
}
	
