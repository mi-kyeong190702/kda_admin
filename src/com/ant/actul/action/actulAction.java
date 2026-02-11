package com.ant.actul.action;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.actul.dao.actulDao;
import com.ant.common.util.CommonUtil;
import com.ant.common.util.EmailSender;
import com.ant.common.util.StringUtil;
import com.ant.member.state.dao.memberStateDao;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class actulAction extends DispatchAction {
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
	//protected static String fileDir="E:\\WEB\\KDA_VER3\\downExcel\\";

	/*   영양사   실태신고    */		
	public ActionForward actulconfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return (mapping.findForward("actulconfirm"));
		
	}	
	
	/*   영양사   실태신고  검색  */
	public ActionForward actulconfirmSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao();		
		
		if(request.getParameter("isSelect").equals("N")||(request.getParameter("isSelect").equals("K"))){
			String ar_name = URLDecoder.decode(request.getParameter("ar_name"),"UTF-8");			
			map.put("ar_name",      ar_name);			
		}else{
			map.put("ar_name",      request.getParameter("ar_name"));
		}
				
		map.put("yyyy",         request.getParameter("yyyy"));		
		map.put("ar_lic_no",    request.getParameter("ar_lic_no"));
		    
		List<Map>  result = dao.actulconfirm(map);
		List<Map>  result1 = dao.kda_edu(map);
		
		if(result.size() > 0   ){			
			request.setAttribute("result",result);
			if(result1.size() > 0   ){
				request.setAttribute("result1",result1);}
			if(request.getParameter("isSelect").equals("N")||(request.getParameter("isSelect").equals("Y"))){
				  return (mapping.findForward("good"));
			}else{
				  return (mapping.findForward("success"));}
	   	}else{
	   		request.setAttribute("ar_name", request.getParameter("ar_name"));
			request.setAttribute("yyyy",request.getParameter("yyyy"));
			request.setAttribute("ar_lic_no",request.getParameter("ar_lic_no"));	   
			return (mapping.findForward("fail"));
		}
	}	
	
	public ActionForward actulinsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
			String yy =request.getParameter("yy");	
	   		String lic_no =request.getParameter("lic_no");		   		
			request.setAttribute("name",name);
			request.setAttribute("yy",yy);
			request.setAttribute("lic_no",lic_no);
			
			return (mapping.findForward("actulinsert"));		
	}	
	
	public ActionForward actulinsertdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao(); 		
		
		map.put("yyyy",                 request.getParameter("yyyy"));
		map.put("ar_name",              request.getParameter("ar_name"));
		map.put("ar_lic_no",            request.getParameter("ar_lic_no"));
		String ar_no = request.getParameter("ar_no1") + request.getParameter("ar_no2");		
		map.put("ar_no",                ar_no);		
		map.put("ar_lic_print_dt",      request.getParameter("ar_lic_print_dt"));
		map.put("ar_code_sex",          request.getParameter("ar_code_sex"));
		map.put("ar_email",             request.getParameter("ar_email"));
		map.put("ar_add1",              request.getParameter("ar_add1"));
		map.put("ar_add2",              request.getParameter("ar_add2"));	
		map.put("ar_tel_home",          request.getParameter("ar_tel_home"));
		map.put("ar_tel_job",           request.getParameter("ar_tel_job"));
		map.put("ar_tel_hp",            request.getParameter("ar_tel_hp"));
		map.put("ar_code_part",         request.getParameter("ar_code_part"));		
		map.put("ar_code_big1",         request.getParameter("ar_code_big1"));
		map.put("ar_comp_name1",        request.getParameter("ar_comp_name1"));
		map.put("ar_comp_add11",        request.getParameter("ar_comp_add11"));
		map.put("ar_comp_add12",        request.getParameter("ar_comp_add12"));		
		map.put("ar_comp_name2",        request.getParameter("ar_comp_name2"));
		map.put("ar_comp_add21",        request.getParameter("ar_comp_add21"));
		map.put("ar_comp_add22",        request.getParameter("ar_comp_add22"));
		map.put("ar_comp_name3",        request.getParameter("ar_comp_name3"));		
		map.put("ar_comp_add31",        request.getParameter("ar_comp_add31"));
		map.put("ar_comp_add32",        request.getParameter("ar_comp_add32"));
		map.put("ar_comp_name4",        request.getParameter("ar_comp_name4"));
		map.put("ar_comp_add41",        request.getParameter("ar_comp_add41"));		
		map.put("ar_comp_add42",        request.getParameter("ar_comp_add42"));
		map.put("ar_comp_name5",        request.getParameter("ar_comp_name5"));
		map.put("ar_comp_add51",        request.getParameter("ar_comp_add51"));
		map.put("ar_comp_add52",        request.getParameter("ar_comp_add52"));		
		map.put("ar_code_big2",         request.getParameter("ar_code_big2"));
		map.put("ar_comp_name6",        request.getParameter("ar_comp_name6"));
		map.put("ar_comp_add61",        request.getParameter("ar_comp_add61"));
		map.put("ar_comp_add62",        request.getParameter("ar_comp_add62"));
		map.put("ar_finish_point",      request.getParameter("ar_finish_point"));
		map.put("ar_marks_point",       request.getParameter("ar_marks_point"));	    
		
		int  n = dao.actulinsert(map);		
		String msg =(n>0)?"정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
		request.setAttribute("msg",msg);
		if(n > 0   ){			
			return (mapping.findForward("insert_ok"));
		}else{
			return (mapping.findForward("insert_fail"));
		}	   	
	}
	
	public ActionForward actulupdatedata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao();		
		
		map.put("yyyy",                 request.getParameter("yyyy"));
		map.put("yyyy_seq",             request.getParameter("yyyy_seq"));
		map.put("ar_name",              request.getParameter("ar_name"));
		map.put("ar_lic_no",            request.getParameter("ar_lic_no"));
		String ar_no = request.getParameter("ar_no1") + request.getParameter("ar_no2");		
		map.put("ar_no",                ar_no);		
		map.put("ar_lic_print_dt",      request.getParameter("ar_lic_print_dt"));
		map.put("ar_code_sex",          request.getParameter("ar_code_sex"));
		map.put("ar_email",             request.getParameter("ar_email"));
		map.put("ar_add1",              request.getParameter("ar_add1"));
		map.put("ar_add2",              request.getParameter("ar_add2"));	
		map.put("ar_tel_home",          request.getParameter("ar_tel_home"));
		map.put("ar_tel_job",           request.getParameter("ar_tel_job"));
		map.put("ar_tel_hp",            request.getParameter("ar_tel_hp"));
		map.put("ar_code_part",         request.getParameter("ar_code_part"));		
		map.put("ar_code_big1",         request.getParameter("ar_code_big1"));
		map.put("ar_comp_name1",        request.getParameter("ar_comp_name1"));
		map.put("ar_comp_add11",        request.getParameter("ar_comp_add11"));
		map.put("ar_comp_add12",        request.getParameter("ar_comp_add12"));		
		map.put("ar_comp_name2",        request.getParameter("ar_comp_name2"));
		map.put("ar_comp_add21",        request.getParameter("ar_comp_add21"));
		map.put("ar_comp_add22",        request.getParameter("ar_comp_add22"));
		map.put("ar_comp_name3",        request.getParameter("ar_comp_name3"));		
		map.put("ar_comp_add31",        request.getParameter("ar_comp_add31"));
		map.put("ar_comp_add32",        request.getParameter("ar_comp_add32"));
		map.put("ar_comp_name4",        request.getParameter("ar_comp_name4"));
		map.put("ar_comp_add41",        request.getParameter("ar_comp_add41"));		
		map.put("ar_comp_add42",        request.getParameter("ar_comp_add42"));
		map.put("ar_comp_name5",        request.getParameter("ar_comp_name5"));
		map.put("ar_comp_add51",        request.getParameter("ar_comp_add51"));
		map.put("ar_comp_add52",        request.getParameter("ar_comp_add52"));		
		map.put("ar_code_big2",         request.getParameter("ar_code_big2"));
		map.put("ar_comp_name6",        request.getParameter("ar_comp_name6"));
		map.put("ar_comp_add61",        request.getParameter("ar_comp_add61"));
		map.put("ar_comp_add62",        request.getParameter("ar_comp_add62"));
		map.put("ar_finish_point",      request.getParameter("ar_finish_point"));
		map.put("ar_marks_point",       request.getParameter("ar_marks_point"));	    
		
		int  n = dao.actulupdate(map);
		
		String msg =(n>0)?"정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
		request.setAttribute("msg",msg);
		if(n > 0   ){			
			return (mapping.findForward("update_ok"));
		}else{
			return (mapping.findForward("update_fail"));
		}	   	
	}
	
	/*   영양사   실태신고 관리    */		
	public ActionForward actulsearchdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return (mapping.findForward("actulsearchList"));		
	}	
	
	public ActionForward actulsearchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		Map map = new HashMap();
		actulDao dao = new actulDao();
	
		if(request.getParameter("ar_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("ar_name1"),"UTF-8");
			map.put("ar_name1",           ar_name1);}			
		if(request.getParameter("yyyy1")!=null){
			map.put("yyyy1",              request.getParameter("yyyy1"));}	
		if(request.getParameter("ar_add11")!=null){
			map.put("ar_add11",           request.getParameter("ar_add11"));}	
		if(request.getParameter("ar_code_part1")!=null){	
			map.put("ar_code_part1",      request.getParameter("ar_code_part1"));}
		if(request.getParameter("ar_code_big11")!=null){	 
			map.put("ar_code_big11",      request.getParameter("ar_code_big11"));}
		if(request.getParameter("ar_state1")!=null){
			map.put("ar_state1",          request.getParameter("ar_state1"));}
				    
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
			List<Map> actulmanacnt=dao.actulmanacnt(map);
			ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> actulmana = dao.actulmana(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<actulmana.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( actulmana.get(i).get("yyyy")	== null ? ""                 :     actulmana.get(i).get("yyyy"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("yyyy_seq")	== null ? ""             :     actulmana.get(i).get("yyyy_seq"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_name")	== null ? ""             :     actulmana.get(i).get("ar_name"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_no")	== null ? ""             :     actulmana.get(i).get("ar_no"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_lic_no")	== null ? ""         :     actulmana.get(i).get("ar_lic_no"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_add1_name")	== null ? ""         :     actulmana.get(i).get("ar_add1_name"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_add1")	== null ? ""             :     actulmana.get(i).get("ar_add1"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_code_part_name")	== null ? "" :     actulmana.get(i).get("ar_code_part_name"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_code_part")	== null ? ""         :     actulmana.get(i).get("ar_code_part"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_code_big1_name")	== null ? "" :     actulmana.get(i).get("ar_code_big1_name"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_code_big1")	== null ? ""         :     actulmana.get(i).get("ar_code_big1"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_email")	== null ? ""             :     actulmana.get(i).get("ar_email"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_tel_hp")	== null ? ""         :     actulmana.get(i).get("ar_tel_hp"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_finish_point")	== null ? ""     :     actulmana.get(i).get("ar_finish_point"))  +"\",");			
				result.append("\"" + ( actulmana.get(i).get("ar_marks_point")	== null ? ""     :     actulmana.get(i).get("ar_marks_point"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_state_name")	== null ? ""     :     actulmana.get(i).get("ar_state_name"))  +"\",");
				result.append("\"" + ( actulmana.get(i).get("ar_state")	== null ? ""             :     actulmana.get(i).get("ar_state"))  +"\"");
				result.append("]}");
			}
			
			result.append("]}");
			
			request.setAttribute("result",result);		
			return (mapping.findForward("ajaxout"));
			
	}	
	
	/* 주민번호 Forward */
	public ActionForward actulpers_no(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
						
		if(request.getParameter("ar_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("ar_name1"),"UTF-8");
			request.setAttribute("ar_name1",           ar_name1);}				
		if(request.getParameter("yyyy1")!=null){
			request.setAttribute("yyyy1",              request.getParameter("yyyy1"));}	
		if(request.getParameter("ar_add11")!=null){
			request.setAttribute("ar_add11",           request.getParameter("ar_add11"));}	
		if(request.getParameter("ar_code_part1")!=null){	
			request.setAttribute("ar_code_part1",      request.getParameter("ar_code_part1"));}
		if(request.getParameter("ar_code_big11")!=null){	 
			request.setAttribute("ar_code_big11",      request.getParameter("ar_code_big11"));}
		if(request.getParameter("ar_state1")!=null){
			request.setAttribute("ar_state1",          request.getParameter("ar_state1"));}
		
	return (mapping.findForward("pers_check"));
			
	}
	
	/* 영양사   실태신고 관리  excel Down */ 
	public void actulsearchDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
	
		actulDao dao = new actulDao();
		
		if(request.getParameter("yyyy1")!=null){
			map.put("yyyy1",              request.getParameter("yyyy1"));}	
		if(request.getParameter("ar_add11")!=null){
			map.put("ar_add11",           request.getParameter("ar_add11"));}	
		if(request.getParameter("ar_code_part1")!=null){	
			map.put("ar_code_part1",      request.getParameter("ar_code_part1"));}
		if(request.getParameter("ar_code_big11")!=null){	 
			map.put("ar_code_big11",      request.getParameter("ar_code_big11"));}
		if(request.getParameter("ar_state1")!=null){
			map.put("ar_state1",          request.getParameter("ar_state1"));}
		if(request.getParameter("check")!=null){
			map.put("check",          request.getParameter("check"));}		
		
		String format="yyyyMMdd";
		
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_NAME")); //유저ID
		
		//여기부터 개발자 변경 필요
		String name="actulmana"; //프로그램명
		String s_name="영양사 신고관리"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"yyyy","yyyy_seq","ar_name", "ar_no", "ar_lic_no","ar_lic_print_dt","ar_code_sex_name","ar_email","ar_add1_name","ar_add2","ar_tel_home","ar_tel_job","ar_tel_hp","ar_code_part_name","ar_code_big1_name","ar_comp_name1","ar_comp_add11_name","ar_comp_add12","ar_comp_name2","ar_comp_add21_name","ar_comp_add22","ar_comp_name3","ar_comp_add31_name","ar_comp_add32","ar_comp_name4","ar_comp_add41_name","ar_comp_add42","ar_comp_name5","ar_comp_add51_name","ar_comp_add52","ar_code_big2_name","ar_comp_name6","ar_comp_add61_name","ar_comp_add62","ar_finish_point", "ar_marks_point","ar_add_file1","ar_add_file2","ar_add_file3","ar_finish_yn_name","ar_state_name","ar_regi_date"}; //헤더 영문
		String header_k[]={"년도","순번","이름","주민 번호","면허 번호","발급년월일","성별","이메일","지역","주소","집연락처","근무처연락처","핸드폰","활동 구분","집단급식소구분","근무처1","지역1","주소1","근무처2","지역2","주소2","근무처3","지역3","주소3","근무처4","지역4","주소4","근무처5","지역5","주소5","집단급식소명2","근무처6","지역6","주소6","총 시간","이수 시간","첨부파일1","첨부파일2","첨부파일3","발송구분","진행 상태","신고일자"}; //헤더 국문
		int c_size[]={15, 15, 15, 25, 15, 15, 15, 25, 15, 40, 15, 15, 15, 20, 20, 20, 25 ,20, 40, 25 ,20, 40, 25 ,20, 40, 25 ,20, 40, 25 ,20, 40, 25, 25, 20, 40, 15, 15, 15, 15, 15, 15, 15, 15, 15 };  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
				
		//여기까지 변경
		List<Map> actulmanadown=dao.actulmanadown(map);
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(actulmanadown, filename, s_name,header_e,header_k,c_size);
			
		//File f=new File(fileDir+filename); //파일생성
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}		
	}
	
	/*   영양사   실태신고 현황    */	
	public ActionForward actulformdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return (mapping.findForward("actulformList"));		
	}	
	
	public ActionForward actulformList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao();
		
		if(request.getParameter("isSelect")!=null){
			map.put("yyyy",             request.getParameter("yyyy"));				
	    
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
			/*List<Map> actullistcnt=dao.actullistcnt(map);
			ncount = ((Integer)(actullistcnt.get(0).get("cnt"))).intValue();*/
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> actullist = dao.actullist(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<actullist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( actullist.get(i).get("tempcd1")	    == null ? "" : actullist.get(i).get("tempcd1") )	+"\",");
				result.append("\"" + ( actullist.get(i).get("groupname1")	== null ? "" : actullist.get(i).get("groupname1") )	+"\",");
				result.append("\"" + ( actullist.get(i).get("groupname2")	== null ? "" : actullist.get(i).get("groupname2") )	+"\",");
				result.append("\"" + ( actullist.get(i).get("col1")			== null ? "" : actullist.get(i).get("col1") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col2")			== null ? "" : actullist.get(i).get("col2") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col3")			== null ? "" : actullist.get(i).get("col3") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col4")			== null ? "" : actullist.get(i).get("col4") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col5")			== null ? "" : actullist.get(i).get("col5") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col6")			== null ? "" : actullist.get(i).get("col6") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col7")			== null ? "" : actullist.get(i).get("col7") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col8")			== null ? "" : actullist.get(i).get("col8") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col9")			== null ? "" : actullist.get(i).get("col9") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col10")		== null ? "" : actullist.get(i).get("col10") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col11")		== null ? "" : actullist.get(i).get("col11") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col12")		== null ? "" : actullist.get(i).get("col12") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col13")		== null ? "" : actullist.get(i).get("col13") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("col14")		== null ? "" : actullist.get(i).get("col14") )		+"\",");
				result.append("\"" + ( actullist.get(i).get("totcount")		== null ? "" : actullist.get(i).get("totcount")  )	+"\"");
				result.append("]}");
			}
			
			result.append("]}");	
		
			request.setAttribute("result",result);	
		}
		return (mapping.findForward("ajaxout"));	
	}	
	
	/* 영양사   실태신고 현황  excel Down */ 
	public void actulformDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
	
		actulDao dao = new actulDao();
		
		map.put("yyyy",             request.getParameter("yyyy"));	
		
		String format="yyyyMMdd";
		
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_NAME")); //유저ID
		
		//여기부터 개발자 변경 필요
		String name="actullist"; //프로그램명
		String s_name="영양사 실태현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"groupname1", "groupname2", "col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9", "col10", "col11", "col12", "col13", "col14", "totcount"}; //헤더 영문
		String header_k[]={"근무처","근무기관","서울지부","부산지부","인천지부","경기지부","강원지부","충북지부","대전충남","전북지부","광주전남","대구경북","경남지부","울산지부","제주지부","기타","합계"}; //헤더 국문
		int c_size[]={30, 30, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
				
		//여기까지 변경
		List<Map> actullist=dao.actullist(map);
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(actullist, filename, s_name,header_e,header_k,c_size);
			
		//File f=new File(fileDir+filename); //파일생성
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}		
	}
	
	/* 문자 전송 전체*/
	public ActionForward sendumsDataall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {			
		
		if(request.getParameter("ar_name1")        !=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("ar_name1"),"UTF-8");	
		}
		if(request.getParameter("yyyy1")           !=null){
			String yyyy1            = request.getParameter("yyyy1");		
		}
		if(request.getParameter("ar_add11")        !=null){	
			String ar_add11         =  request.getParameter("ar_add11");					
		}
		if(request.getParameter("ar_code_part1")   !=null){	 
			String ar_code_part1    =  request.getParameter("ar_code_part1");					
		}
		if(request.getParameter("ar_code_big11")   !=null){
			String ar_code_big11    = request.getParameter("ar_code_big11");			
		}	
		if(request.getParameter("ar_state1")       !=null){
			String ar_state1        = request.getParameter("ar_state1");			
		}		
		
		String isSelect             = request.getParameter("isSelect");
		String ar_tel_hp            = request.getParameter("ar_tel_hp");		
		
		return (mapping.findForward("umsDataall"));
	}
	
	public ActionForward umsDataall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();    		
    	HttpSession session=request.getSession(); 
    	actulDao dao = new actulDao();    	
    	
    	if(!request.getParameter("yyyy1").equals("")){
			map.put("yyyy1",                   request.getParameter("yyyy1"));}		
		if(!request.getParameter("ar_add11").equals("")) {
			map.put("ar_add11",                request.getParameter("ar_add11"));}	
		if(!request.getParameter("ar_code_part1").equals("")){
			map.put("ar_code_part1",           request.getParameter("ar_code_part1"));}	
		if(!request.getParameter("ar_code_big11").equals("")){
			map.put("ar_code_big11",           request.getParameter("ar_code_big11"));}
		if(!request.getParameter("ar_state1").equals("")){
			map.put("ar_state1",               request.getParameter("ar_state1"));}
		
		List<Map> actulumsData = dao.actulumsData(map);
		
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
    	        
    	
    	for(int i=0; i<actulumsData.size(); i++){
    		if(actulumsData.get(i).get("ar_tel_hp").toString().length()>9){
    			map.put("ar_tel_hp" ,                actulumsData.get(i).get("ar_tel_hp"));
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                request.getParameter("send_phone"));      		
        		map.put("register",                  session.getAttribute("G_NAME"));
        		map.put("etc1",                      session.getAttribute("G_ID"));
        		map.put("etc2",                      session.getAttribute("G_BRAN"));
        		map.put("etc3",                      actulumsData.get(i).get("ar_name"));
        		map.put("umscnt", i+1);
        		//예약발송
        		int n = 0;
        		if( request_time.length() > 0 ) {
    				map.put("request_time", request_time);
        			n = dao.insertumsResultData(map);
        		}else {
        			n = dao.insertumsData(map);
        		} 
    		}    		  		
    	}   
    	
		return (mapping.findForward("insertumsData_ok"));				
	}
	
	/* 문자 전송 */
	public ActionForward sendumsData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ar_tel_hp          = request.getParameter("ar_tel_hp");		
		String ar_name            = URLDecoder.decode(request.getParameter("ar_name"),"UTF-8");
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
    	
    	String msg_type = strlength <= 80 ? "0" : "5";     

    	//예약발송용
    	String request_time = "";
    	if( request.getParameter("yyyyMMdd") != null )
    		request_time = request.getParameter("yyyyMMdd") + " " + request.getParameter("HHmm");
    	        
    	String ar_tel_hp    = request.getParameter("ar_tel_hp");    	
    	String [] artelhp = ar_tel_hp.split(","); 
    	String ar_name    = URLDecoder.decode(request.getParameter("ar_name"),"UTF-8");    	
    	String [] arname = ar_name.split(",");
    	
    	for(int i=0;i<artelhp.length;i++){
    		if(artelhp[i].length()>9){
    			map.put("ar_tel_hp",                 artelhp[i]); 
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                request.getParameter("send_phone"));      		
        		map.put("register",                  session.getAttribute("G_NAME"));
        		map.put("etc1",                      session.getAttribute("G_ID"));
        		map.put("etc2",                      session.getAttribute("G_BRAN"));
        		map.put("etc3",                      arname[i]);
        		System.out.println("arname=========>"+arname[i]);
        		map.put("umscnt", i+1);
        		actulDao dao = new actulDao();

        		//예약발송
        		int n = 0;
        		if( request_time.length() > 0 ) {
    				map.put("request_time", request_time);
        			n = dao.insertumsResultData(map);
        		}else {
        			n = dao.insertumsData(map);
        		} 
    		}    		 		
    	}     	
		return (mapping.findForward("insertumsData_ok"));				
	}
	
	/*보수교육 내역 sample excel Down */ 
	public void upExcelsample(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
		actulDao dao = new actulDao();
		
		map.put("yyyy",                          request.getParameter("yyyy"));
		map.put("ar_lic_no",                     request.getParameter("ar_lic_no"));
				
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_NAME")); //유저ID
		//여기부터 개발자 변경 필요
		String name="upExcel"; //프로그램명
		String s_name="보수교육내역"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"gubun", "yyyy", "yyyy_seq", "ke_name", "ke_lic_no","ke_birth", "ke_seq", "ke_subject", "ke_fromto", "ke_isutime", "ke_jesukaing", "ke_total", "ke_finish_dt", "ke_finish_no", "ke_finish_yn"}; //헤더 영문
		String header_k[]={"신규/갱신 구분","년도","순번","이름","면허번호","생년월일","교육순번","교육명","이수기간","이수시간","재수강여부","총시간","이수일자","이수번호","이수여부"}; //헤더 국문
		int c_size[]={10, 10, 10, 10, 15, 20, 15, 30, 20, 10, 10, 10, 15, 10, 10};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> upedusample=dao.upedusample(map);		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(upedusample, filename, s_name,header_e,header_k,c_size);
			
		//File f=new File(fileDir+filename); //파일생성
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}		
	}

	public ActionForward upExamSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.setAttribute("yyyy",              request.getParameter("yyyy"));
		request.setAttribute("ar_lic_no",         request.getParameter("ar_lic_no"));
				
		return mapping.findForward("upExamSendListr");
	}
	
	//교육상세 엑셀 업로드
	public ActionForward actulupExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		Map map=new HashMap();
		actulDao dao = new actulDao();		
		
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		
		String saveFolder="D:\\WEB\\KDA_VER3\\upload\\excel\\";
		//saveFolder="E:\\WEB\\KDA_VER3\\upload\\excel\\";
		String encType="UTF-8";
		int maxSize=10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
		String filename="";
		
		Workbook openWorkbook;
		Sheet openSheet;
		String sheetName = "";
		Cell openCell;
		
		String msg="";
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				filename=multi.getFilesystemName(name);
				f=new File(saveFolder+"/"+filename);
			}
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
				    System.out.println("cellLen=======>"+cellLen);
				    map.put("yyyy",        		        cell[1].getContents());	    //년도
				    map.put("yyyy_seq",    		        cell[2].getContents());	    //순번
				    map.put("ke_name",        		    cell[3].getContents());		//이름
				    map.put("ke_lic_no",        		cell[4].getContents());		//면허번호
				    map.put("ke_birth",        		cell[5].getContents());		//면허번호
				    /*map.put("ke_jumin",        		    cell[5].getContents());	*/	//주민번호
				    map.put("ke_seq",        		    cell[6].getContents());		//교육순번
				    map.put("ke_subject",        		cell[7].getContents());		//교육명
				    map.put("ke_fromto",        		cell[8].getContents());		//이수기간
				    map.put("ke_isutime",        		cell[9].getContents());		//이수시간
				    map.put("ke_jesukaing",        		cell[10].getContents());		//재수강여부
				    map.put("ke_total",        		    cell[11].getContents());	//총시간
				    map.put("ke_finish_dt",        		cell[12].getContents());	//이수일자
				    map.put("ke_finish_no",        		cell[13].getContents());	//이수번호도
				    map.put("ke_finish_yn",        		cell[14].getContents());	//이수여부	 
				
				    if("u".equals(cell[0].getContents())){
						int update_edu = dao.update_edu(map);						
					}else if("i".equals(cell[0].getContents())){
						int insert_edu = dao.insert_edu(map);						
					}				  			    
				}  //for 
				openWorkbook.close();
			//}   //while
		}catch(Exception e){   //try
			e.printStackTrace();
			msg="fail";
		}
		
		if(msg.equals("")){
			request.setAttribute("msg", "업로드가 완료됐습니다.");
		}else{
			request.setAttribute("msg", "업로드를 실패했습니다.");
		}

		return mapping.findForward("upExamSendListr2");
	}  //Exception
	
	
	
	
	public ActionForward actulmanageData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.setAttribute("menu", "actulmanage");
		return (mapping.findForward("actulmanageList"));		
	}
	
	public ActionForward actulmanageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao();
		
		if(request.getParameter("pers_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",           ar_name1);}			
		if(request.getParameter("yyyy1")!=null){
			map.put("yyyy1",          request.getParameter("yyyy1"));}
		if(request.getParameter("lic_no1")!=null){
			map.put("lic_no1",              request.getParameter("lic_no1"));}	
		if(request.getParameter("pers_hp1")!=null){
			map.put("pers_hp1",              request.getParameter("pers_hp1"));}	
		if(request.getParameter("email1")!=null){
			map.put("email1",           request.getParameter("email1"));}	
		if(request.getParameter("ar_finish_yn1")!=null){
			map.put("ar_finish_yn1",          request.getParameter("ar_finish_yn1"));}
		if(request.getParameter("memo")!=null){
			map.put("memo",          URLDecoder.decode(request.getParameter("memo"),"UTF-8"));}
	
	
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
		List<Map> actulmanacnt=dao.actulmanagecnt(map);
		ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		
		int ntotpage = (ncount/nrows)+1;
		
		List<Map> actulmanageList = dao.actulmanageList(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i<actulmanageList.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( actulmanageList.get(i).get("code_seq")	== null ? ""                 :     actulmanageList.get(i).get("code_seq"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_regi_date")	         == null ? ""     :     actulmanageList.get(i).get("ar_regi_date"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_conform_date")	         == null ? ""     :     actulmanageList.get(i).get("ar_conform_date"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_finish_yn_kr")	         == null ? ""     :     actulmanageList.get(i).get("ar_finish_yn_kr"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("yyyy")	== null ? ""             :     actulmanageList.get(i).get("yyyy"))  +"\",");
			
			
			
			result.append("\"" + ( actulmanageList.get(i).get("pers_name")	== null ? ""             :     actulmanageList.get(i).get("pers_name"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("pers_year")	== null ? ""             :     actulmanageList.get(i).get("pers_year"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("lic_no")	== null ? ""             :     actulmanageList.get(i).get("lic_no"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("lic_print_dt")	== null ? ""             :     actulmanageList.get(i).get("lic_print_dt"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("cs_con_education")	         == null ? ""     :     actulmanageList.get(i).get("cs_con_education"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_marks_point")	         == null ? ""     :     actulmanageList.get(i).get("ar_marks_point"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_state")	         == null ? ""     :     actulmanageList.get(i).get("ar_state"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_state_kr")	         == null ? ""     :     actulmanageList.get(i).get("ar_state_kr"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_finish_yn")	         == null ? ""     :     actulmanageList.get(i).get("ar_finish_yn"))  +"\",");
			
			/*result.append("\"" + ( actulmanageList.get(i).get("ar_regi_date")	         == null ? ""     :     actulmanageList.get(i).get("ar_regi_date"))  +"\",");*/
			result.append("\"" + ( actulmanageList.get(i).get("memo")	== null ? ""         :     actulmanageList.get(i).get("memo"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("email")	== null ? ""         :     actulmanageList.get(i).get("email"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("addr")	== null ? ""         :     actulmanageList.get(i).get("addr"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("pers_tel")	== null ? ""         :     actulmanageList.get(i).get("pers_tel"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("pers_hp")	== null ? ""         :     actulmanageList.get(i).get("pers_hp"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_code_part")	         == null ? ""     :     actulmanageList.get(i).get("ar_code_part"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_code_part_kr")	         == null ? ""     :     actulmanageList.get(i).get("ar_code_part_kr"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("kitchen_code")	== null ? ""         :     actulmanageList.get(i).get("kitchen_code"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_add_file_url")	== null ? ""         :     actulmanageList.get(i).get("ar_add_file_url"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("ar_add_file_name")	== null ? ""         :     actulmanageList.get(i).get("ar_add_file_name"))  +"\",");
			result.append("\"" + ( actulmanageList.get(i).get("yyyy2")	== null ? ""         :     actulmanageList.get(i).get("yyyy2"))  +"\"");
			result.append("]}");
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));
	}
	
	public ActionForward actulstatusData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("menu", "actulstatus");
		return (mapping.findForward("actulstatusList"));		
	}
	
	public ActionForward actulstatusList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		/*System.out.println("000000000000000000000=======>");*/
		
		Map map = new HashMap();
		actulDao dao = new actulDao();
		
		Calendar cal = Calendar.getInstance(); //20160627 2016년 실태신고를 위한 수정
		int yyyy2 = cal.get(Calendar.YEAR); 	//20160627 2016년 실태신고를 위한 수정
		
		if(request.getParameter("yyyy1")!=null){
			map.put("yyyy1",          request.getParameter("yyyy1"));}
	
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
		
		/*System.out.println("1111111111111111111111=======>");*/
		/*
		//20160627 2016년 실태신고를 위한 수정 시작
		if (yyyy2%2 == 0){   		//짝수년도에 보수교육 이수년도 2개 담음
			map.put("yyyy3", yyyy2-1);
			map.put("yyyy4", yyyy2-3);
			 
		 }else{						
			map.put("yyyy3", yyyy2-2);
			map.put("yyyy4", yyyy2-4);	//홀수년도에 보수교육 이수년도 1개 담음
			 
		 }
		//20160627 2016년 실태신고를 위한 수정 끝
		*/
		int ncount = 0;		
		List<Map> actulmanacnt=dao.actulstatuscnt1(map);
		ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		
		int ntotpage = (ncount/nrows)+1;
		
		List<Map> actulstatusList = dao.actulstatusList1(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i<actulstatusList.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( actulstatusList.get(i).get("code_seq")	== null ? ""                 :     actulstatusList.get(i).get("code_seq"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("pers_name")	         == null ? ""     :     actulstatusList.get(i).get("pers_name"))   +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("lic_no")	== null ? ""             :     actulstatusList.get(i).get("lic_no"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("lic_print_dt")	== null ? ""             :     actulstatusList.get(i).get("lic_print_dt"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("addr")	== null ? ""             :     actulstatusList.get(i).get("addr"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("pers_tel")	== null ? ""             :     actulstatusList.get(i).get("pers_tel"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("pers_hp")	== null ? ""             :     actulstatusList.get(i).get("pers_hp"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("email")	== null ? ""         :     actulstatusList.get(i).get("email"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_code_part_kr")	== null ? ""         :     actulstatusList.get(i).get("ar_code_part_kr"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("kitchen_code")	== null ? ""         :     actulstatusList.get(i).get("kitchen_code"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("kitchen_name")	== null ? ""         :     actulstatusList.get(i).get("kitchen_name"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("kitchen__add_name")	         == null ? ""     :     actulstatusList.get(i).get("kitchen__add_name"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("report_year")	         == null ? ""     :     actulstatusList.get(i).get("report_year"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("cs_con_education_13")	== null ? ""         :     actulstatusList.get(i).get("cs_con_education_13"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_marks_point_13")	== null ? ""         :     actulstatusList.get(i).get("ar_marks_point_13"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_state_kr_13")	         == null ? ""     :     actulstatusList.get(i).get("ar_state_kr_13"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("cs_con_education_15")	         == null ? ""     :     actulstatusList.get(i).get("cs_con_education_15"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_marks_point_15")	         == null ? ""     :     actulstatusList.get(i).get("ar_marks_point_15"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_state_kr_15")	         == null ? ""     :     actulstatusList.get(i).get("ar_state_kr_15"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("cs_con_education_17")	         == null ? ""     :     actulstatusList.get(i).get("cs_con_education_17"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_marks_point_17")	         == null ? ""     :     actulstatusList.get(i).get("ar_marks_point_17"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_state_kr_17")	         == null ? ""     :     actulstatusList.get(i).get("ar_state_kr_17"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_regi_date2")	         == null ? ""     :     actulstatusList.get(i).get("ar_regi_date2"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("ar_conform_date2")	         == null ? ""     :     actulstatusList.get(i).get("ar_conform_date2"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("YYYY")	         == null ? ""     :     actulstatusList.get(i).get("YYYY"))  +"\",");
			result.append("\"" + ( actulstatusList.get(i).get("no")	         == null ? ""     :     actulstatusList.get(i).get("no"))  +"\"");
			result.append("]}");
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));
	}
	
	public ActionForward actulrecipientData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("menu", "actulrecipient");
		return (mapping.findForward("actulrecipientList"));
	}
	
	public ActionForward actulrecipientList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao();
	
		if(request.getParameter("pers_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",           ar_name1);}			
		if(request.getParameter("lic_no1")!=null){
			map.put("lic_no1",              request.getParameter("lic_no1"));}	
		if(request.getParameter("email1")!=null){
			map.put("email1",               request.getParameter("email1"));}	
		if(request.getParameter("ar_state1")!=null){
			map.put("ar_state1",            request.getParameter("ar_state1"));}
		if(request.getParameter("memo")!=null){
			map.put("memo",          		URLDecoder.decode(request.getParameter("memo"),"UTF-8"));}
		
		
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
		List<Map> actulmanacnt=dao.actulrecipientcnt(map);
		ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		
		int ntotpage = (ncount/nrows)+1;
		
		List<Map> actulrecipientList = dao.actulrecipientList(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i<actulrecipientList.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( actulrecipientList.get(i).get("ar_regi_date")	== null ? ""         :     actulrecipientList.get(i).get("ar_regi_date"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("code_seq")	== null ? ""                 :     actulrecipientList.get(i).get("code_seq"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("ar_state_kr")	== null ? ""         :     actulrecipientList.get(i).get("ar_state_kr"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("pers_name")	== null ? ""         :     actulrecipientList.get(i).get("pers_name"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("lic_no")	== null ? ""         :     actulrecipientList.get(i).get("lic_no"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("pers_year")	== null ? ""         :     actulrecipientList.get(i).get("pers_year"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("code_sex_kr")	         == null ? ""     :     actulrecipientList.get(i).get("code_sex_kr"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_con_education")	== null ? ""             :     actulrecipientList.get(i).get("cs_con_education"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_non_target")	== null ? ""         :     actulrecipientList.get(i).get("cs_non_target"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("memo")	         == null ? ""     :     actulrecipientList.get(i).get("memo"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("email")	== null ? ""             :     actulrecipientList.get(i).get("email"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_attachments")	         == null ? ""     :     actulrecipientList.get(i).get("cs_attachments"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_attachments_url")	== null ? ""         :     actulrecipientList.get(i).get("cs_attachments_url"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_secter")	== null ? ""             :     actulrecipientList.get(i).get("cs_secter"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_secter_kr")	== null ? ""             :     actulrecipientList.get(i).get("cs_secter_kr"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_secter_organ")	== null ? ""             :     actulrecipientList.get(i).get("cs_secter_organ"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("code_post")	== null ? ""         :     actulrecipientList.get(i).get("code_post"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("pers_add")	== null ? ""         :     actulrecipientList.get(i).get("pers_add"))  +"\",");
			/*result.append("\"" + ( actulrecipientList.get(i).get("cs_non_target")	== null ? ""         :     actulrecipientList.get(i).get("cs_non_target"))  +"\",");*/
			result.append("\"" + ( actulrecipientList.get(i).get("cs_non_target_detail")	== null ? ""         :     actulrecipientList.get(i).get("cs_non_target_detail"))  +"\",");
			/*result.append("\"" + ( actulrecipientList.get(i).get("code_sex")	         == null ? ""     :     actulrecipientList.get(i).get("code_sex"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("pers_hp")	== null ? ""             :     actulrecipientList.get(i).get("pers_hp"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("pers_add_detail")	== null ? ""         :     actulrecipientList.get(i).get("pers_add_detail"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("ar_conform_date")	== null ? ""         :     actulrecipientList.get(i).get("ar_conform_date"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("up_date")	== null ? ""         :     actulrecipientList.get(i).get("up_date"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_attachments_type")	         == null ? ""     :     actulrecipientList.get(i).get("cs_attachments_type"))  +"\",");
			result.append("\"" + ( actulrecipientList.get(i).get("cs_attachments_size")	== null ? ""         :     actulrecipientList.get(i).get("cs_attachments_size"))  +"\",");*/
			result.append("\"" + ( actulrecipientList.get(i).get("ar_state")	         == null ? ""     :     actulrecipientList.get(i).get("ar_state"))  +"\"");
			
			result.append("]}");
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));	
		
	}
	
	
	public ActionForward actullicenseData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		request.setAttribute("menu", "actullicense");
		return (mapping.findForward("actullicenseList"));		
	}
	
	public ActionForward actullicenseList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao();
	
		if(request.getParameter("pers_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",           ar_name1);}		
		if(request.getParameter("lic_no1")!=null){
			map.put("lic_no1",              request.getParameter("lic_no1"));}	
		if(request.getParameter("atc_wrtr_cttno1")!=null){
			map.put("atc_wrtr_cttno1",              request.getParameter("atc_wrtr_cttno1"));}	
		if(request.getParameter("email1")!=null){
			map.put("email1",           request.getParameter("email1"));}	
		if(request.getParameter("ar_state1")!=null){
			map.put("ar_state1",          request.getParameter("ar_state1"));}
		
		
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
		List<Map> actulmanacnt=dao.actullicensecnt(map);
		ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		
		int ntotpage = (ncount/nrows)+1;
		
		List<Map> actullicenseList = dao.actullicenseList(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
		
		for(int i=0; i<actullicenseList.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( actullicenseList.get(i).get("atc_sno")	== null ? ""         :     actullicenseList.get(i).get("atc_sno"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("bbs_cd")	== null ? ""         :     actullicenseList.get(i).get("bbs_cd"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("frst_rgt_dttm") == null ? ""     :     actullicenseList.get(i).get("frst_rgt_dttm"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("ar_state_kr") == null ? ""       :     actullicenseList.get(i).get("ar_state_kr"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("pers_name")	== null ? ""         :     actullicenseList.get(i).get("pers_name"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("lic_no")	== null ? ""         :     actullicenseList.get(i).get("lic_no"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("lic_print_dt")	== null ? ""     :     actullicenseList.get(i).get("lic_print_dt"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("atc_wrtr_cttno")	== null ? "" :     actullicenseList.get(i).get("atc_wrtr_cttno"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("email")	== null ? ""         	 :     actullicenseList.get(i).get("email"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("atc_wrtr_eml")	== null ? ""     :     actullicenseList.get(i).get("atc_wrtr_eml"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("bbs_file")	== null ? ""         :     actullicenseList.get(i).get("bbs_file"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("ar_state")	== null ? ""         :     actullicenseList.get(i).get("ar_state"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("bbs_file_url")	== null ? ""     :     actullicenseList.get(i).get("bbs_file_url"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("bbs_file_type")	== null ? ""     :     actullicenseList.get(i).get("bbs_file_type"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("bbs_file_size")	== null ? ""     :     actullicenseList.get(i).get("bbs_file_size"))  +"\",");
			result.append("\"" + ( actullicenseList.get(i).get("bbs_file_name")	== null ? ""     :     actullicenseList.get(i).get("bbs_file_name"))  +"\"");
			result.append("]}");
		}
		
		result.append("]}");
		request.setAttribute("result",result);
		
		return (mapping.findForward("ajaxout"));	
		
	}
	
	public ActionForward personInsert(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("personInsert");
	}
	
	public ActionForward personInsertProc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		Map map = new HashMap();
		actulDao dao = new actulDao();
	
		if(request.getParameter("new_pers_name")!=null){
			String pers_name = URLDecoder.decode(request.getParameter("new_pers_name"),"UTF-8");
			map.put("pers_name",           pers_name);}
		if(request.getParameter("new_lic_no")!=null){
			map.put("lic_no",              request.getParameter("new_lic_no"));}
		if(request.getParameter("new_lic_print_dt")!=null){
			map.put("lic_print_dt",              request.getParameter("new_lic_print_dt"));}
		
		int n = 0;
		int per =0;
		String msg ="";
		
		List<Map> personCnt=dao.personCnt(map);
		per= ((Integer)(personCnt.get(0).get("cnt"))).intValue();
		
		if( per ==0){
			n = dao.personReg(map);
			msg = "정상적으로 등록 되었습니다.";
		}else{
			msg = "입력하신 정보는 이미 등록된 회원입니다.";
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("action","actulmanageData");
		
		return (mapping.findForward("actul_msg"));
	}
	
	public ActionForward personUpt(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if(request.getParameter("grid_pers_name")!=null){
			String pers_name = URLDecoder.decode(request.getParameter("grid_pers_name"),"UTF-8");
			request.setAttribute("pers_name",           pers_name);}		
		if(request.getParameter("grid_lic_no")!=null){
			request.setAttribute("lic_no",              request.getParameter("grid_lic_no"));}
		if(request.getParameter("grid_lic_print_dt")!=null){
			request.setAttribute("lic_print_dt",              request.getParameter("grid_lic_print_dt"));}	
		
		return mapping.findForward("personUpt");
	}
	
	public ActionForward personUptProc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		actulDao dao = new actulDao();
	
		if(request.getParameter("old_pers_name")!=null){
			String old_pers_name = URLDecoder.decode(request.getParameter("old_pers_name"),"UTF-8");
			map.put("old_pers_name",           old_pers_name);}		
		if(request.getParameter("old_lic_no")!=null){
			map.put("old_lic_no",              request.getParameter("old_lic_no"));}
		if(request.getParameter("old_lic_print_dt")!=null){
			map.put("old_lic_print_dt",              request.getParameter("old_lic_print_dt"));}
		if(request.getParameter("new_pers_name")!=null){
			String pers_name = URLDecoder.decode(request.getParameter("new_pers_name"),"UTF-8");
			map.put("new_pers_name",           pers_name);
			map.put("pers_name",           pers_name);}		
		if(request.getParameter("new_lic_no")!=null){
			map.put("new_lic_no",              request.getParameter("new_lic_no"));
			map.put("lic_no",              request.getParameter("new_lic_no"));}
		if(request.getParameter("new_lic_print_dt")!=null){
			map.put("new_lic_print_dt",              request.getParameter("new_lic_print_dt"));}
		
		int n = 0;
		int per =0;
		String msg ="";
		
		List<Map> personCnt=dao.personCnt(map);
		per= ((Integer)(personCnt.get(0).get("cnt"))).intValue();
		
		/*if( per ==0){*/
			n = dao.updateInfo_person(map);
			if(n>0){
				dao.updateInfo_status(map);
			}
			msg = "정상적으로 등록 되었습니다.";
		/*}else{
			msg = "입력하신 정보는 이미 등록된 회원입니다.";
		}*/
		
		request.setAttribute("msg",msg);
		request.setAttribute("action","actulmanageData");
		
		return (mapping.findForward("actul_msg"));
		
	}
	
	public ActionForward statusUpt(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		actulDao dao = new actulDao();
		String mode ="";
		Calendar cal = Calendar.getInstance();
		int yyyy = cal.get(Calendar.YEAR);
		String year = Integer.toString(yyyy);
		
		if(request.getParameter("mode")!=null){
			mode =request.getParameter("mode");}
		
		if("I".equals(mode)){

			request.setAttribute("yyyy", year);
			
		}else if("U".equals(mode)){

			if(request.getParameter("grid_pers_name")!=null){
				String pers_name = URLDecoder.decode(request.getParameter("grid_pers_name"),"UTF-8");
				map.put("pers_name",           pers_name);}		
			if(request.getParameter("grid_lic_no")!=null){
				map.put("lic_no",              request.getParameter("grid_lic_no"));}	
			if(request.getParameter("grid_yyyy")!=null){
				map.put("yyyy",              request.getParameter("grid_yyyy"));
				request.setAttribute("yyyy",              request.getParameter("grid_yyyy"));}	
			if(request.getParameter("grid_cs_con_education")!=null){
				map.put("cs_con_education",              request.getParameter("grid_cs_con_education"));
				request.setAttribute("cs_con_education",              request.getParameter("grid_cs_con_education"));}	
			
			request.setAttribute("now_year", year);
			request.setAttribute("result",dao.getStatusData(map));			
			request.setAttribute("person",dao.getPersonData(map));			
		}
		request.setAttribute("recentRow", dao.getRecent(map));
		request.setAttribute("recentYearRow", dao.getRecentYear(map));
		request.setAttribute("mode", mode);
		request.setAttribute("getEmail", dao.getEmail(map));
		
		return mapping.findForward("statusUpt");
	}
	
	public ActionForward statusUptProc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		actulDao dao = new actulDao();
		String mode ="";
		String ar_finish_yn ="";
		
		Calendar cal = Calendar.getInstance();
		int yyyy1 = cal.get(Calendar.YEAR);
		String year1 = Integer.toString(yyyy1);
		String month = Integer.toString(cal.get ( Calendar.MONTH ) + 1);
		String date = Integer.toString(cal.get ( Calendar.DATE ));
		String today = year1.substring(2, 4)+"/"+month+"/"+date;
		System.out.println("====================="+today);
		String saveFolder="D:\\WEB\\dietitian.or.kr_ver3\\upload\\report\\";
		//String saveFolder="E:\\WEB\\KDA_VER3\\upload\\excel\\";
		String encType="UTF-8";
		int maxSize=10*10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType, new DefaultFileRenamePolicy());

		if(multi.getParameter("file_yn").equals("Y")){
			upFile(multi, request);
		}
		
		if(multi.getParameter("ar_finish_yn")!=null){
			ar_finish_yn =multi.getParameter("ar_finish_yn");}
		
		if(multi.getParameter("mode")!=null){
			mode =multi.getParameter("mode");}
		if(multi.getParameter("email")!=null) map.put("email",              			  	URLDecoder.decode(multi.getParameter("email"),"UTF-8"));
		if(multi.getParameter("code_post")!=null) map.put("code_post",             		  	URLDecoder.decode(multi.getParameter("code_post"),"UTF-8"));	
		if(multi.getParameter("pers_add")!=null) map.put("pers_add",              		  	URLDecoder.decode(multi.getParameter("pers_add"),"UTF-8"));
		if(multi.getParameter("pers_add_detail")!=null) map.put("pers_add_detail",		  	URLDecoder.decode(multi.getParameter("pers_add_detail"),"UTF-8"));
		if(multi.getParameter("pers_tel")!=null) map.put("pers_tel",              		  	URLDecoder.decode(multi.getParameter("pers_tel"),"UTF-8"));
		if(multi.getParameter("pers_hp")!=null) map.put("pers_hp",              		  	URLDecoder.decode(multi.getParameter("pers_hp"),"UTF-8"));
		if(multi.getParameter("pers_year")!=null) map.put("pers_year",              	  	URLDecoder.decode(multi.getParameter("pers_year"),"UTF-8"));
		if(multi.getParameter("code_sex")!=null) map.put("code_sex",              		  	URLDecoder.decode(multi.getParameter("code_sex"),"UTF-8"));
		if(multi.getParameter("ar_code_part")!=null) map.put("ar_code_part",         	  	URLDecoder.decode(multi.getParameter("ar_code_part"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_code")!=null) map.put("cs_kitchen_code",        	URLDecoder.decode(multi.getParameter("cs_kitchen_code"),"UTF-8"));
		if(multi.getParameter("ncs_kitchen_code")!=null) map.put("ncs_kitchen_code",      	URLDecoder.decode(multi.getParameter("ncs_kitchen_code"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_act_code")!=null) map.put("cs_kitchen_act_code",	URLDecoder.decode(multi.getParameter("cs_kitchen_act_code"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_name1")!=null) map.put("cs_kitchen_name1",      	URLDecoder.decode(multi.getParameter("cs_kitchen_name1"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_name2")!=null) map.put("cs_kitchen_name2",      	URLDecoder.decode(multi.getParameter("cs_kitchen_name2"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_name3")!=null) map.put("cs_kitchen_name3",      	URLDecoder.decode(multi.getParameter("cs_kitchen_name3"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_name4")!=null) map.put("cs_kitchen_name4",      	URLDecoder.decode(multi.getParameter("cs_kitchen_name4"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_name5")!=null) map.put("cs_kitchen_name5",      	URLDecoder.decode(multi.getParameter("cs_kitchen_name5"),"UTF-8"));
		if(multi.getParameter("code_post1")!=null) map.put("code_post1",              	  	URLDecoder.decode(multi.getParameter("code_post1"),"UTF-8"));
		if(multi.getParameter("code_post2")!=null) map.put("code_post2",             		URLDecoder.decode(multi.getParameter("code_post2"),"UTF-8"));
		if(multi.getParameter("code_post3")!=null) map.put("code_post3",              		URLDecoder.decode(multi.getParameter("code_post3"),"UTF-8"));
		if(multi.getParameter("code_post4")!=null) map.put("code_post4",              		URLDecoder.decode(multi.getParameter("code_post4"),"UTF-8"));
		if(multi.getParameter("code_post5")!=null) map.put("code_post5",              		URLDecoder.decode(multi.getParameter("code_post5"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add11")!=null) map.put("cs_kitchen_add11",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add11"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add12")!=null) map.put("cs_kitchen_add12",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add12"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add21")!=null) map.put("cs_kitchen_add21",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add21"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add22")!=null) map.put("cs_kitchen_add22",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add22"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add31")!=null) map.put("cs_kitchen_add31",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add31"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add32")!=null) map.put("cs_kitchen_add32",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add32"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add41")!=null) map.put("cs_kitchen_add41",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add41"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add42")!=null) map.put("cs_kitchen_add42",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add42"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add51")!=null) map.put("cs_kitchen_add51",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add51"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_add52")!=null) map.put("cs_kitchen_add52",       	URLDecoder.decode(multi.getParameter("cs_kitchen_add52"),"UTF-8"));
		if(multi.getParameter("ncs_kitchen_name1")!=null) map.put("ncs_kitchen_name1",  	URLDecoder.decode(multi.getParameter("ncs_kitchen_name1"),"UTF-8"));
		if(multi.getParameter("code_post6")!=null) map.put("code_post6",              		URLDecoder.decode(multi.getParameter("code_post6"),"UTF-8"));
		if(multi.getParameter("ncs_kitchen_add61")!=null) map.put("ncs_kitchen_add61",   	URLDecoder.decode(multi.getParameter("ncs_kitchen_add61"),"UTF-8"));
		if(multi.getParameter("ncs_kitchen_add62")!=null) map.put("ncs_kitchen_add62",   	URLDecoder.decode(multi.getParameter("ncs_kitchen_add62"),"UTF-8"));
		if(multi.getParameter("cs_kitchen_ret_code")!=null) map.put("cs_kitchen_ret_code",	URLDecoder.decode(multi.getParameter("cs_kitchen_ret_code"),"UTF-8"));
		if(multi.getParameter("ncs_kitchen_ret_code")!=null) map.put("ncs_kitchen_ret_code",URLDecoder.decode(multi.getParameter("ncs_kitchen_ret_code"),"UTF-8"));
		if(multi.getParameter("ar_finish_yn")!=null) map.put("ar_finish_yn",              	URLDecoder.decode(multi.getParameter("ar_finish_yn"),"UTF-8"));
		if(multi.getParameter("pers_name")!=null) map.put("pers_name",              		URLDecoder.decode(multi.getParameter("pers_name"),"UTF-8"));
		if(multi.getParameter("lic_no")!=null) map.put("lic_no",              				URLDecoder.decode(multi.getParameter("lic_no"),"UTF-8"));
		if(multi.getParameter("lic_print_dt")!=null) map.put("lic_print_dt",              	URLDecoder.decode(multi.getParameter("lic_print_dt"),"UTF-8"));
		if(multi.getParameter("yyyy")!=null) map.put("yyyy",              					URLDecoder.decode(multi.getParameter("yyyy"),"UTF-8"));
		if(multi.getParameter("cs_con_education")!=null) map.put("cs_con_education",        URLDecoder.decode(multi.getParameter("cs_con_education"),"UTF-8"));
		if(multi.getParameter("ar_marks_point")!=null) map.put("ar_marks_point",       		URLDecoder.decode(multi.getParameter("ar_marks_point"),"UTF-8"));
		if(multi.getParameter("ar_state")!=null) map.put("ar_state",       					URLDecoder.decode(multi.getParameter("ar_state"),"UTF-8"));
		if(multi.getParameter("ar_regi_date")!=null) map.put("ar_regi_date",       			URLDecoder.decode(multi.getParameter("ar_regi_date"),"UTF-8"));
		if(multi.getParameter("ar_add_file_name")!=null) map.put("ar_add_file_name",       	URLDecoder.decode(multi.getParameter("ar_add_file_name"),"UTF-8"));
		if(multi.getParameter("ar_add_file_url")!=null) map.put("ar_add_file_url",       	URLDecoder.decode(multi.getParameter("ar_add_file_url"),"UTF-8"));
		if(multi.getParameter("ar_add_file_type")!=null) map.put("ar_add_file_type",       	URLDecoder.decode(multi.getParameter("ar_add_file_type"),"UTF-8"));
		if(multi.getParameter("ar_add_file_size")!=null) map.put("ar_add_file_size",       	URLDecoder.decode(multi.getParameter("ar_add_file_size"),"UTF-8"));
		
		map.put("year1",year1);
		
		int per = 0;
		int sta = 0;
		String msg ="";
		
		if("I".equals(mode)){
			per = dao.statusUptProc1(map);
			
			if(per==1){

				if("2".equals(ar_finish_yn) ){
					dao.statusUptProc4(map);
					this.autoMail((String)map.get("pers_name"), (String)map.get("email"), today, ar_finish_yn, "manage");
						
					msg="정상적으로 완료 되었습니다.";
					
				}else{
					dao.statusUptProc3(map);
					msg="정상적으로 등록 되었습니다.";
				}
			}else{
				msg="입력하신 정보는 면허등록 된 회원이 아닙니다.확인 바랍니다.";
			}
			
		}else if("U".equals(mode)){
			
			per = dao.statusUptProc1(map);
			
			if(per==1){

				if("2".equals(ar_finish_yn) ){
					dao.statusUptProc4(map);
					this.autoMail((String)map.get("pers_name"), (String)map.get("email"), (String)map.get("ar_regi_date"), ar_finish_yn, "manage");
						
					msg="정상적으로 완료 되었습니다.";
					
				}else{
					dao.statusUptProc3(map);
					msg="정상적으로 등록 되었습니다.";
				}
			}else{
				msg="입력하신 정보는 면허등록 된 회원이 아닙니다.확인 바랍니다.";
			}
			
		}
		
		request.setAttribute("msg",msg);
		
		return (mapping.findForward("statusUpt"));
		
	}
	
	public ActionForward statusPrint(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		actulDao dao = new actulDao();
		String mode ="";
		
		Calendar cal = Calendar.getInstance();
		int yyyy = cal.get(Calendar.YEAR);
		int month = cal.get ( Calendar.MONTH ) + 1 ;
		int date = cal.get ( Calendar.DATE ) ;
		
		request.setAttribute("year",yyyy);
		request.setAttribute("month",month);
		request.setAttribute("date",date);
			
			
		if(request.getParameter("print_pers_name")!=null){
			String pers_name = URLDecoder.decode(request.getParameter("print_pers_name"),"UTF-8");
			map.put("pers_name", pers_name);}		
		if(request.getParameter("print_lic_no")!=null){
			map.put("lic_no", request.getParameter("print_lic_no"));}	
		if(request.getParameter("print_yyyy")!=null){
			map.put("yyyy", request.getParameter("print_yyyy"));
			request.setAttribute("yyyy", request.getParameter("print_yyyy"));}	
		if(request.getParameter("print_cs_con_education")!=null){
			map.put("cs_con_education", request.getParameter("print_cs_con_education"));}
		/*
		Map tempMap = new HashMap();
		tempMap.put("yyyy", request.getParameter("print_yyyy"));
		tempMap.put("pers_name", URLDecoder.decode(request.getParameter("print_pers_name"),"UTF-8"));
		tempMap.put("lic_no", request.getParameter("print_lic_no"));
		request.setAttribute("recentRow", dao.getRecent(tempMap));
		request.setAttribute("recentYearRow", dao.getRecentYear(tempMap));
		*/
		request.setAttribute("recentRow", dao.getRecent(map));
		request.setAttribute("recentYearRow", dao.getRecentYear(map));
		request.setAttribute("result",dao.getStatusData(map));			
		request.setAttribute("person",dao.getPersonData(map));			
		
		return mapping.findForward("statusPrint");
	}
	
	public ActionForward updateFinishyn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		actulDao dao = new actulDao(); 		
		
		String []data_pers_name = request.getParameter("data_pers_name").split(",");
		String []data_to_mail = request.getParameter("data_to_mail").split(",");
		String []data_ar_regi_date = request.getParameter("data_ar_regi_date").split(",");
		String []data_lic_no = request.getParameter("data_lic_no").split(",");
		//String []data_yyyy = request.getParameter("data_yyyy").split(",");
		String []data_lic_print_dt = request.getParameter("data_lic_print_dt").split(",");
		String []data_code_seq = request.getParameter("data_code_seq").split(",");
		String []data_cs_con_education = request.getParameter("data_cs_con_education").split(",");
		String data_ar_finish_yn = request.getParameter("data_ar_finish_yn");
		String data_memo = request.getParameter("data_memo");
		
		
		int n = 0;
		int m = 0;
		int x = 0;
		String msg = "";
		String msg2 = "";	
		
		for(int i=0; i<data_pers_name.length;i++){
			map.put("pers_name", data_pers_name[i]);
			map.put("lic_no", data_lic_no[i]);
			//map.put("yyyy", data_yyyy[i]);
			map.put("lic_print_dt", data_lic_print_dt[i]);	
			map.put("code_seq", data_code_seq[i]);	
			map.put("cs_con_education", data_cs_con_education[i]);
			map.put("ar_finish_yn", data_ar_finish_yn);
			map.put("memo", data_memo);
			
			//메모내용이 있으면 메모만 저장 하도록 함
			if(data_memo != null && !"".equals(data_memo)) {
				m = dao.manageMemoUpt(map);
				n = n+m;
			} else {
				//메모내용이 없을 경우
				
				m = dao.updateFinishyn(map);
				if(m>0){
					n = n+m;
					//if("2".equals(data_ar_finish_yn) || "3".equals(data_ar_finish_yn) || "4".equals(data_ar_finish_yn)){ //회송,보류 막음
					if("2".equals(data_ar_finish_yn)){
						this.autoMail(data_pers_name[i],data_to_mail[i],data_ar_regi_date[i],data_ar_finish_yn,"manage");
						
						//System.out.println("111111111111111111111111111111");
					}
				}else{
					if(msg2!="") msg2+=",";
					msg2+=data_pers_name[i];
					
					//System.out.println("22222222222222222222222222222");
				}
			}
			
			
		}

		msg =(n==data_pers_name.length)?"정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
		
		if(msg2!=""){
			msg+= msg2+" 님은 등록에 실패하였습니다.";
			
			//System.out.println("33333333333333333333333333333333333");
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("action","actulmanageData");

		return (mapping.findForward("actul_msg"));
		
	}
	
	public ActionForward recipientUpt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		actulDao dao = new actulDao(); 		
		
		String []data_pers_name = request.getParameter("data_pers_name").split(",");
		String []data_to_mail = request.getParameter("data_to_mail").split(",");
		String []data_frst_rgt_dttm = request.getParameter("data_ar_regi_date").split(",");
		String []data_lic_no = request.getParameter("data_lic_no").split(",");
		String []data_cs_con_education = request.getParameter("data_cs_con_education").split(",");
		String []data_code_seq = request.getParameter("data_code_seq").split(",");
		String data_ar_state = request.getParameter("data_ar_state");
		String data_memo = request.getParameter("data_memo");
		System.out.println("----------"+data_memo +"1111111111111");
		int n = 0;
		int m = 0;
		int x = 0;
		String msg = "";		
		String msg2 = "";
		Calendar cal = Calendar.getInstance();
		int yyyy = cal.get(Calendar.YEAR);
		String year = Integer.toString(yyyy);
		
		

		for(int i=0; i<data_pers_name.length;i++){
			map.put("pers_name", data_pers_name[i]);
			map.put("lic_no", data_lic_no[i]);
			map.put("cs_con_education", data_cs_con_education[i]);
			map.put("code_seq", data_code_seq[i]);	
			map.put("ar_state2", data_ar_state);
			map.put("memo", data_memo);
			
			//메모내용이 있으면 메모만 저장 하도록 함
			if(data_memo != null && !"".equals(data_memo)) {
				m = dao.recipientMemoUpt(map);
				n = n+m;
				
			} else {
			//메모내용이 없을 경우
				
				if("1".equals(data_ar_state)){
					x = dao.recipientUpt(map);
					if(x==0) {
						map.put("yyyy", year);
						map.put("report_year", year);
						map.put("ar_finish_point", 6);
						map.put("ar_marks_point", 0);
						map.put("ar_state", "4");
						
						List<Map> actulmanacnt=dao.statusCnt(map);
						int ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
						if(ncount >0) {
							if(msg2!="") {
								msg2+=",";
							}
							msg2 += data_pers_name[i];
						} else {
							int insert_edu = dao.insertStatus(map);
							m = dao.recipientArStateUpt(map);
							n = n+m;
							// 기존 data_ar_regi_date[i]였으나 사용하지 않아서 필요한 보수교육(data_cs_con_education[i]) 년도로 대체 처리함. 
							this.autoMail(data_pers_name[i],data_to_mail[i],data_cs_con_education[i],data_ar_state,"recipient");
						}
					} else {
						m = dao.recipientArStateUpt(map);
						n = n+m;
						this.autoMail(data_pers_name[i],data_to_mail[i],data_cs_con_education[i],data_ar_state,"recipient");
					}
				} else {
					m = dao.recipientArStateUpt(map);
					n = n+m;
					//this.autoMail(data_pers_name[i],data_to_mail[i],data_frst_rgt_dttm[i],data_ar_state,"recipient"); //회송,보류 수동메일작성
				}
			}
			
		}
		
		msg =(n==data_pers_name.length)?"정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
		
		if(msg2!=""){
			msg+= msg2+" 님은 "+year+"년도 면허(실태)신고 데이터가 존재하여 신규 등록할 수 없습니다.보수교육년도를 확인하세요.";
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("action","actulrecipientData");
		
		return (mapping.findForward("actul_msg"));
		
	}
	
	
	public ActionForward personReg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		actulDao dao = new actulDao(); 		
		
		String []data_pers_name = request.getParameter("data_pers_name").split(",");
		String []data_to_mail = request.getParameter("data_to_mail").split(",");
		String []data_frst_rgt_dttm = request.getParameter("data_frst_rgt_dttm").split(",");
		String []data_lic_no = request.getParameter("data_lic_no").split(",");
		String []data_lic_print_dt = request.getParameter("data_lic_print_dt").split(",");
		String []data_atc_sno = request.getParameter("data_atc_sno").split(",");
		String data_ar_state = request.getParameter("data_ar_state");
		String []data_atc_wrtr_cttno = request.getParameter("data_atc_wrtr_cttno").split(",");
		
		int n = 0;
		int m = 0;
		int x = 0;
		int per = 0;
		String msg = "";
		String msg2 = "";
		
		for(int i=0; i<data_pers_name.length;i++){
			map.put("pers_name", data_pers_name[i]);
			map.put("lic_no", data_lic_no[i]);
			map.put("lic_print_dt", data_lic_print_dt[i]);	
			map.put("atc_sno", data_atc_sno[i]);	
			map.put("ar_state", data_ar_state);
			map.put("to_email", data_to_mail[i]);
			map.put("atc_wrtr_cttno", data_atc_wrtr_cttno[i]);
			
			if("1".equals(data_ar_state)){
				
				List<Map> personCnt=dao.personCnt(map);
				per= ((Integer)(personCnt.get(0).get("cnt"))).intValue();
				
				if(per<1){
					x = dao.personReg(map);					
					if(x==0){
						
					}else{
						m = dao.arStateUpt(map);
						n = n+m;	
						this.autoMail(data_pers_name[i],data_to_mail[i],data_frst_rgt_dttm[i],data_ar_state,"license");
					}
				}else {
					if(msg2.length()>0) msg2 +=",";
					msg2 += data_pers_name[i];
				}
				
			}else{
				m = dao.arStateUpt(map);
				n = n+m;	
				//this.autoMail(data_pers_name[i],data_to_mail[i],data_frst_rgt_dttm[i],data_ar_state,"license");	//회송,보류 수동메일작성
			}
			
		}
		
		msg =(n==data_pers_name.length)?"정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
		
		if(msg2.length()>0) {
			msg2 += "  회원은 이미 존재하는 회원입니다.확인바랍니다.";
			msg = msg +msg2;
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("action","actullicenseData");

		return (mapping.findForward("actul_msg"));
		
	}
	
	public ActionForward recipientForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap();
		actulDao dao = new actulDao();
		
		if(request.getParameter("code_seq")!=null){
			map.put("code_seq",              request.getParameter("code_seq"));}
		
		request.setAttribute("result",dao.getRecipient(map));
		request.setAttribute("getEmail", dao.getEmail(map));
		
		return mapping.findForward("recipientForm");
	}
	
	public ActionForward recipientFormProc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		actulDao dao = new actulDao();
	
		if(request.getParameter("data2_code_seq")!=null){
			map.put("code_seq",              request.getParameter("data2_code_seq"));}
		if(request.getParameter("data2_pers_name")!=null){
			String pers_name = URLDecoder.decode(request.getParameter("data2_pers_name"),"UTF-8");
			map.put("pers_name",           pers_name);}		
		if(request.getParameter("data2_lic_no")!=null){
			map.put("lic_no",              request.getParameter("data2_lic_no"));}
		if(request.getParameter("data2_code_sex")!=null){
			map.put("code_sex",              request.getParameter("data2_code_sex"));}
		if(request.getParameter("data2_pers_year")!=null){
			map.put("pers_year",              request.getParameter("data2_pers_year"));}
		if(request.getParameter("data2_email")!=null){
			map.put("email",              request.getParameter("data2_email"));}
		if(request.getParameter("data2_cs_secter")!=null){
			map.put("cs_secter",              request.getParameter("data2_cs_secter"));}
		if(request.getParameter("data2_cs_secter_organ")!=null){
			String cs_secter_organ = URLDecoder.decode(request.getParameter("data2_cs_secter_organ"),"UTF-8");
			map.put("cs_secter_organ",           cs_secter_organ);}
		if(request.getParameter("data2_cs_con_education")!=null){
			map.put("cs_con_education",              request.getParameter("data2_cs_con_education"));}
		if(request.getParameter("data2_code_post")!=null){
			map.put("code_post",              request.getParameter("data2_code_post"));}
		if(request.getParameter("data2_pers_add")!=null){
			String pers_add = URLDecoder.decode(request.getParameter("data2_pers_add"),"UTF-8");
			map.put("pers_add",           pers_add);}
		if(request.getParameter("data2_cs_non_target")!=null){
			map.put("cs_non_target",              request.getParameter("data2_cs_non_target"));}
		if(request.getParameter("data2_authority_premises")!=null){
			map.put("authority_premises",              request.getParameter("data2_authority_premises"));}
		if(request.getParameter("data2_cs_non_target_detail")!=null){
			String cs_non_target_detail = URLDecoder.decode(request.getParameter("data2_cs_non_target_detail"),"UTF-8");
			map.put("cs_non_target_detail",           cs_non_target_detail);}
		
		if(request.getParameter("data2_pers_hp")!=null){
			map.put("pers_hp",              request.getParameter("data2_pers_hp"));}
		
		int n = 0;
		String msg ="";
		
		n = dao.subjectsUpt(map);
		
		if( n ==1){
			msg = "정상적으로 수정 되었습니다.";
		}else{
			msg = "다시 시도해 주세요.";
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("action","actulrecipientData");
		
		return (mapping.findForward("actul_msg"));
		
	}
	
	public ActionForward excel_manage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map map = new HashMap();
		actulDao dao = new actulDao();

		if(request.getParameter("pers_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",           ar_name1);}			
		if(request.getParameter("yyyy1")!=null){
			map.put("yyyy1",          request.getParameter("yyyy1"));}
		if(request.getParameter("lic_no1")!=null){
			map.put("lic_no1",              request.getParameter("lic_no1"));}	
		if(request.getParameter("pers_hp1")!=null){
			map.put("pers_hp1",              request.getParameter("pers_hp1"));}	
		if(request.getParameter("email1")!=null){
			map.put("email1",           request.getParameter("email1"));}	
		if(request.getParameter("ar_finish_yn1")!=null){
			map.put("ar_finish_yn1",          request.getParameter("ar_finish_yn1"));}
		if(request.getParameter("memo")!=null){
			map.put("memo",           URLDecoder.decode(request.getParameter("memo"),"UTF-8"));}
		
		
		String code_seq = request.getParameter("code_seq");
		if("0".equals(code_seq)){
			map.put("check", "0");
			request.setAttribute("check", "0");
		}else{
			map.put("check", "1");
			request.setAttribute("check", "1");
			
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}
		request.setAttribute("code_seq", code_seq);
		
		List<Map> actulmanacnt=dao.actulmanagecnt(map);
		int ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		request.setAttribute("ncount", ncount);
		
		String param = "";
		
		if(request.getParameter("pers_name1"	)!=null)request.setAttribute("pers_name1",  URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8") );
		if(request.getParameter("yyyy1")!=null) param+="&yyyy1="	+request.getParameter("yyyy1");
		if(request.getParameter("lic_no1")	!=  null)	param+="&lic_no1="	+request.getParameter("lic_no1");
		if(request.getParameter("atc_wrtr_cttno1")	!=  null)	param+="&atc_wrtr_cttno1="	+request.getParameter("atc_wrtr_cttno1");
		if(request.getParameter("email1")		!=  null)	param+="&email1="		+request.getParameter("email1");
		if(request.getParameter("ar_finish_yn1")!=null) param+="&ar_finish_yn1="	+request.getParameter("ar_finish_yn1");
		if(request.getParameter("memo")!=null) param+="&memo="	+URLDecoder.decode(request.getParameter("memo"),"UTF-8");


		request.setAttribute("param", param);
		request.setAttribute("url", "excel_manage");
		
		return (mapping.findForward("excelLicence"));
			
	}
	
	public void excelDown_manage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		actulDao dao = new actulDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		String name			="actulmanageList"; //프로그램명
		String s_name		="조건검색"; //엑셀 시트명

		String header_e[]={	"yyyy","pers_name","pers_year","lic_no","lic_print_dt","cs_con_education","ar_marks_point","ar_state_kr","memo","email","addr","pers_tel","pers_hp","ar_code_part_kr","kitchen_code","addr2","yyyy2"}; //헤더 영문
		String header_k[]={"년도","이름","생년","면허번호","면허발급년월일","보수교육이수년도","이수시간","상태","메모","메일","주소","전화","휴대전화","활동여부","근무기관","근무처주소","최근신고년도"}; //헤더 국문
		int c_size[]={	15,15,15,15,15,15,15,15,20,30,15,15,15,15,15,15,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));

		if(request.getParameter("pers_name1"		)!=null) map.put("pers_name1"	, URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8"));
		if(request.getParameter("yyyy1"	)!=null)map.put("yyyy1"	, request.getParameter("yyyy1"	));	
		if(request.getParameter("lic_no1"		)!=null)map.put("lic_no1"		, request.getParameter("lic_no1"		));
		if(request.getParameter("atc_wrtr_cttno1"		)!=null)map.put("atc_wrtr_cttno1"		, request.getParameter("atc_wrtr_cttno1"		));
		if(request.getParameter("email1"	)!=null)map.put("email1"	, request.getParameter("email1"		));
		if(request.getParameter("ar_finish_yn1"	)!=null)map.put("ar_finish_yn1"	, request.getParameter("ar_finish_yn1"	));
		if(request.getParameter("memo"	)!=null)map.put("memo"	, URLDecoder.decode(request.getParameter("memo"),"UTF-8"));
		
		String check = request.getParameter("check"	);
		String code_seq= request.getParameter("code_seq"	);
		map.put("check", check);
		if("1".equals(check)){
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}
		
		//검색
		List<Map> actulmanageList = dao.actulmanageList(map);
		
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);

		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
		CommonUtil.makeExcelFile(actulmanageList, filename, s_name,header_e,header_k,c_size);
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	public ActionForward excel_status(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map map = new HashMap();
		actulDao dao = new actulDao();

		if(request.getParameter("yyyy1")!=null){
			map.put("yyyy1",          request.getParameter("yyyy1"));}
		
		
		String code_seq = request.getParameter("code_seq");
		if("0".equals(code_seq)){
			map.put("check", "0");
			request.setAttribute("check", "0");
		}else{
			map.put("check", "1");
			request.setAttribute("check", "1");
			
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}
		request.setAttribute("code_seq", code_seq);
		/*
		//20160627 2016년 실태신고를 위한 수정 시작	
		Calendar cal = Calendar.getInstance();
		int yyyy2 = cal.get(Calendar.YEAR);
		

				if (yyyy2%2 == 0){   		//짝수년도에 보수교육 이수년도 2개 담음
					map.put("yyyy3", yyyy2-1);
					map.put("yyyy4", yyyy2-3);
					 
				 }else{						
					map.put("yyyy3", yyyy2-2);
					map.put("yyyy4", yyyy2-4);	//홀수년도에 보수교육 이수년도 2개 담음
					 
				 }
		//20160627 2016년 실태신고를 위한 수정 끝
		*/		
		
		List<Map> actulmanacnt=dao.actulstatuscnt1(map);//20160127 2016 report
		int ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		request.setAttribute("ncount", ncount);
		
		String param = "";
		
		if(request.getParameter("yyyy1")!=null) param+="&yyyy1="	+request.getParameter("yyyy1");


		request.setAttribute("param", param);
		request.setAttribute("url", "excel_status");
		
		return (mapping.findForward("excelLicence"));
			
	}
	
	public void excelDown_status(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		actulDao dao = new actulDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		String name			="actulstatusList"; //프로그램명
		String s_name		="조건검색"; //엑셀 시트명

		String header_e[]={	"pers_name","lic_no","lic_print_dt","addr","pers_tel","pers_hp","email","ar_code_part_kr","kitchen_code","kitchen_name","kitchen__add_name","report_year","cs_con_education_13","ar_marks_point_13","ar_state_kr_13","cs_con_education_15","ar_marks_point_15","ar_state_kr_15","cs_con_education_17","ar_marks_point_17","ar_state_kr_17","ar_regi_date2","ar_conform_date2"}; //헤더 영문
		String header_k[]={"이름","면허번호","면허번호발급일","자택주소","일반전화","휴대전화","email","활동여부","구분","근무처명","주소","신고년도","신고년도","이수시간13","이수여부13","보수교육년도15","이수시간15","이수여부15","보수교육년도17","이수시간17","이수여부17","등록날짜","확정날짜"}; //헤더 국문
		int c_size[]={15,15,15,15,15,20,30,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));

		if(request.getParameter("yyyy1"	)!=null)map.put("yyyy1"	, request.getParameter("yyyy1"	));	
		
		String check = request.getParameter("check"	);
		String code_seq= request.getParameter("code_seq"	);
		map.put("check", check);
		if("1".equals(check)){
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}
		/*
		//20160627 2016년 실태신고를 위한 수정 시작	
		Calendar cal = Calendar.getInstance();
		int yyyy2 = cal.get(Calendar.YEAR);
		

				if (yyyy2%2 == 0){   		//짝수년도에 보수교육 이수년도 2개 담음
					map.put("yyyy3", yyyy2-1);
					map.put("yyyy4", yyyy2-3);
					 
				 }else{						
					map.put("yyyy3", yyyy2-2);
					map.put("yyyy4", yyyy2-4);	//홀수년도에 보수교육 이수년도 2개 담음
					 
				 }
		//20160627 2016년 실태신고를 위한 수정 끝
		*/

		//검색
		List<Map> actulstatustList = dao.actulstatusList1(map); //20160127 2016 report
		
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);

		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
		CommonUtil.makeExcelFile(actulstatustList, filename, s_name,header_e,header_k,c_size);
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	public ActionForward excel_recipient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map map = new HashMap();
		actulDao dao = new actulDao();

		if(request.getParameter("pers_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",           ar_name1);}			
		if(request.getParameter("lic_no1")!=null){
			map.put("lic_no1",              request.getParameter("lic_no1"));}	
		if(request.getParameter("email1")!=null){
			map.put("email1",           request.getParameter("email1"));}	
		if(request.getParameter("ar_state1")!=null){
			map.put("ar_state1",          request.getParameter("ar_state1"));}
		if(request.getParameter("memo")!=null){
			String memo = URLDecoder.decode(request.getParameter("memo"),"UTF-8");
			map.put("memo",           memo);}			
		
		String code_seq = request.getParameter("code_seq");
		if("0".equals(code_seq)){
			map.put("check", "0");
			request.setAttribute("check", "0");
		}else{
			map.put("check", "1");
			request.setAttribute("check", "1");
			
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}
		request.setAttribute("code_seq", code_seq);
		
		List<Map> actulmanacnt=dao.actulrecipientcnt(map);
		int ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		request.setAttribute("ncount", ncount);
		
		String param = "";
		
		if(request.getParameter("pers_name1"	)!=null)request.setAttribute("pers_name1",  URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8") );
		if(request.getParameter("lic_no1")	!=  null)	param+="&lic_no1="	+request.getParameter("lic_no1");
		if(request.getParameter("email1")		!=  null)	param+="&email1="		+request.getParameter("email1");
		if(request.getParameter("ar_state1")!=null) param+="&ar_state1="	+request.getParameter("ar_state1");
		if(request.getParameter("memo"	)!=null)request.setAttribute("memo",  URLDecoder.decode(request.getParameter("memo"),"UTF-8") );


		request.setAttribute("param", param);
		request.setAttribute("url", "excel_recipient");
		
		return (mapping.findForward("excelLicence"));
			
	}
	
	public void excelDown_recipient(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		actulDao dao = new actulDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		String name			="actulrecipientList"; //프로그램명
		String s_name		="조건검색"; //엑셀 시트명
		
		String header_e[]={	"ar_regi_date","pers_name","lic_no","pers_year","code_sex_kr","cs_con_education","pers_hp","email","cs_attachments","cs_secter_kr","cs_secter_organ","code_post","pers_add","cs_non_target","cs_non_target_detail","ar_state_kr","memo"}; //헤더 영문
		String header_k[]={"등록날짜","이름","면허번호","생년","성별","보수교육년도","휴대폰번호","메일","첨부파일","미활동(미취업)","활동내용","우편번호","주소","미대상사유","기타", "상태","메모"}; //헤더 국문
		int c_size[]={	15,15,15,15,15,20,15,15,15,15,15,15,15,15,15,15,15	};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));

		if(request.getParameter("pers_name1"		)!=null) map.put("pers_name1"	, URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8"));
		if(request.getParameter("lic_no1"		)!=null)map.put("lic_no1"		, request.getParameter("lic_no1"		));
		if(request.getParameter("atc_wrtr_cttno1"		)!=null)map.put("atc_wrtr_cttno1"		, request.getParameter("atc_wrtr_cttno1"		));
		if(request.getParameter("email1"	)!=null)map.put("email1"	, request.getParameter("email1"		));
		if(request.getParameter("ar_state1"	)!=null)map.put("ar_state1"	, request.getParameter("ar_state1"	));	
		if(request.getParameter("memo"	)!=null)map.put("memo"	, request.getParameter("memo"	));
		
		String check = request.getParameter("check"	);
		String code_seq= request.getParameter("code_seq"	);
		map.put("check", check);
		if("1".equals(check)){
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}
		

		//검색
		List<Map> actulrecipientList = dao.actulrecipientList(map);
		
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);

		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
		CommonUtil.makeExcelFile(actulrecipientList, filename, s_name,header_e,header_k,c_size);
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	public ActionForward excel_licence(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map map = new HashMap();
		actulDao dao = new actulDao();

		if(request.getParameter("pers_name1")!=null){
			String ar_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",           ar_name1);}	
		if(request.getParameter("lic_no1")!=null){
			map.put("lic_no1",              request.getParameter("lic_no1"));}
		if(request.getParameter("atc_wrtr_cttno1")!=null){
			map.put("atc_wrtr_cttno1",              request.getParameter("atc_wrtr_cttno1"));}	
		if(request.getParameter("email1")!=null){
			map.put("email1",           request.getParameter("email1"));}	
		if(request.getParameter("ar_state1")!=null){
			map.put("ar_state1",          request.getParameter("ar_state1"));}
		
		
		String code_seq = request.getParameter("code_seq");
		if("0".equals(code_seq)){
			map.put("check", "0");
			request.setAttribute("check", "0");
		}else{
			map.put("check", "1");
			request.setAttribute("check", "1");
			
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}
		request.setAttribute("code_seq", code_seq);
		
		List<Map> actulmanacnt=dao.actullicensecnt(map);
		int ncount = ((Integer)(actulmanacnt.get(0).get("cnt"))).intValue();
		request.setAttribute("ncount", ncount);
		
		String param = "";
		
		if(request.getParameter("pers_name1"	)!=null)request.setAttribute("pers_name1",  URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8") );
		if(request.getParameter("lic_no1")	!=  null)	param+="&lic_no1="	+request.getParameter("lic_no1");
		if(request.getParameter("atc_wrtr_cttno1")	!=  null)	param+="&atc_wrtr_cttno1="	+request.getParameter("atc_wrtr_cttno1");
		if(request.getParameter("email1")		!=  null)	param+="&email1="		+request.getParameter("email1");
		if(request.getParameter("ar_state1")!=null) param+="&ar_state1="	+request.getParameter("ar_state1");


		request.setAttribute("param", param);
		request.setAttribute("url", "excel_licence");
		
		return (mapping.findForward("excelLicence"));
			
	}
	
	public void excelDown_licence(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		actulDao dao = new actulDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		String name			="actullicenseList"; //프로그램명
		String s_name		="조건검색"; //엑셀 시트명

		String header_e[]={	"frst_rgt_dttm","pers_name","lic_no","lic_print_dt","atc_wrtr_cttno","email","bbs_file","ar_state_kr"}; //헤더 영문
		String header_k[]={"등록날짜","이름","면허번호","면허발급년월일","연락처","메일","첨부파일", "상태"}; //헤더 국문
		int c_size[]={	15,15,15,15,15,20,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));

		if(request.getParameter("pers_name1"		)!=null) map.put("pers_name1"	, URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8"));
		if(request.getParameter("lic_no1"		)!=null)map.put("lic_no1"		, request.getParameter("lic_no1"		));
		if(request.getParameter("atc_wrtr_cttno1"		)!=null)map.put("atc_wrtr_cttno1"		, request.getParameter("atc_wrtr_cttno1"		));
		if(request.getParameter("email1"	)!=null)map.put("email1"	, request.getParameter("email1"		));
		if(request.getParameter("ar_state1"	)!=null)map.put("ar_state1"	, request.getParameter("ar_state1"	));	
		
		
		String check = request.getParameter("check"	);
		String code_seq= request.getParameter("code_seq"	);
		map.put("check", check);
		if("1".equals(check)){
			List<String> codeList = new ArrayList<String>();
			
			String [] idx = code_seq.split(",");
			
			for(int i=0;i<idx.length;i++){
				codeList .add(idx[i]);
			}
			map.put("codeList", codeList);
		}

		//검색
		List<Map> actullicenseList = dao.actullicenseList(map);
		
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);

		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
		CommonUtil.makeExcelFile(actullicenseList, filename, s_name,header_e,header_k,c_size);
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	
	public ActionForward eMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String param ="&to_addr="+request.getParameter("to_addr");
		request.setAttribute("param", param);
			
		return (mapping.findForward("sendMail"));		
	}
	
	public void SendMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
		String saveFolder="D:/WEB/KDA_VER3/upload";
		//String saveFolder="E:/WEB/KDA_VER3/upload";
		String to=StringUtil.nullToStr("", request.getParameter("to")); //받는 사람 메일주소. 여러명일때는 주소 사이를 콤마로 구분해준다.
		
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
	
public void autoMail(String to_name, String to_mail,String data_frst_rgt_dttm,String ar_state,String methodNm) throws Exception{
	
		String saveFolder = "D:/WEB/KDA_VER3/upload";
		//String saveFolder = "E:/WEB/KDA_VER3/upload";
		String to = to_mail; //받는 사람 메일주소. 여러명일때는 주소 사이를 콤마로 구분해준다.
		
		Calendar cal = Calendar.getInstance();
		int yyyy = cal.get(Calendar.YEAR);


		// 메일발송 test
	    //methodNm = "recipient";	// manage (2) , recipient (1) , license (1)
	    //ar_state = "1";
	    //yyyy = 2020;
	    System.out.println("### yyyy =======> " + yyyy);
	    
		
		String vCsConEducationYr = "";
		int tmpYear1 = 0;
		int tmpYear2 = 0;
				
		if( yyyy % 2 == 0) {
			// 짝수년도에 보수교육 이수년도 2개 담음
			// 2020년인 경우 : 2017, 2019년
			tmpYear1 = yyyy - 3;
			tmpYear2 = yyyy - 1;
			
			vCsConEducationYr = tmpYear1 + ", " + tmpYear2;
			
		} else {
			// 홀수년도에 보수교육 이수년도 1개 담음
			// 2021년인 경우 : 2019년
			tmpYear1 = yyyy - 2;
			
			vCsConEducationYr = tmpYear1 + "";
		}
		
		
		
		String fr_name = "대한영양사협회";	// 보내는 사람 이름
		//String from = "kdasys@hanmail.net";	// 보내는 사람 메일주소
		//String from = "kda516@hanmail.net";		// 보내는 사람 메일주소
		String from = "kda516@kdiet.or.kr";		// 보내는 사람 메일주소 2022.09.14 수정
		String subj    = ""; 	//메일 제목
		String content = "";	//메일 본문
		
		
		if("manage".equals(methodNm)) {
			// 1. 신고관리
			/*
			 * [관리자 > 영양사실태신고 > 신고관리 ]
			 * : 대상자 선택 -> 상단 조회조건에서 [상태:완료] 변경 -> 저장 클릭시 mail 이 자동 발송된다.
			 * 현재는 완료시에만 메일 발송하고,  보류/회송시에는 메일 안보내고 있음
			 */
		    
			String []regDate = data_frst_rgt_dttm.split("/"); /*20160205 날짜포맷 미대상자와 신고관리 달라서 신고관리만 오토메일로 날짜 보내게함*/
			String vRegYear  = "20" + regDate[0];
			String vRegMonth = regDate[1];
			String vRegDay   = regDate[2].replaceAll(" ","");
			int nextYear = Integer.parseInt(vRegYear);
			nextYear = nextYear + 3;

		    
		    System.out.println("### data_frst_rgt_dttm =======> " + data_frst_rgt_dttm);	// 20/12/31
		    System.out.println("### vRegYear           =======> " + vRegYear + " // vRegMonth : " + vRegMonth + " // vRegDay : " + vRegDay + ".");
		    System.out.println("### nextYear           =======> " + nextYear);
		    
		    System.out.println("### methodNm           =======> " + methodNm);
		    System.out.println("### ar_state           =======> " + ar_state);
		    
			
			//메일 제목
			subj = "[대한영양사협회 영양사 면허신고센터] 영양사 면허신고 승인 완료 안내드립니다.";
			
			if("2".equals(ar_state)) {
				// 실태신고 완료시 발송 메일

				content	=  "<strong>[영양사 면허신고 승인 완료]</strong><br />&nbsp;<br />";
				
				content	+= to_name + " 선생님, 안녕하십니까.<br />";
				content += "대한영양사협회 영양사 면허신고센터입니다.<br />&nbsp;<br />";
				
				content	+= vRegYear + "년 " + vRegMonth + "월 " + vRegDay + "일 제출해주신 <strong>영양사 면허신고 승인이 완료</strong>되었습니다.<br />";
				content += "영양사 면허신고 확인서는 영양사 면허신고센터 홈페이지 ▶ 마이페이지 ▶ 면허신고현황에서 출력이 가능합니다.<br />&nbsp;<br />";
				
				content	+= "다음 영양사 면허신고 연도는 " + nextYear + "년이며, 해당년도 1월부터 12월까지 면허신고가 가능합니다.<br />&nbsp;<br />";
				
				content	+= "감사합니다.<br />&nbsp;<br />";
				content	+= "☞ 영양사 면허신고센터 바로가기" + "<a href='https://url.kr/x1g2ml' >(https://url.kr/x1g2ml)</a>";
				
			} else if("3".equals(ar_state)) {
				// 실태신고 보류시 발송 메일
				content ="안녕하십니까.<br />대한영양사협회 영양사 실태신고센터입니다.<br />&nbsp;<br />";
				content	+= to_name+" 영양사님<br />&nbsp;<br />" + vRegYear + "년 " + vRegMonth + "월 " + vRegDay + "일 제출한 영양사 실태 등의 신고 접수가 보류중입니다.<br />";
				content	+= "제출하신 내용 중 &lsquo;법령 및 지침&rdquo;외의 내용이 있어 보건복지부에 질의하였습니다.<br />";
				content	+= "질의 사항은 14일 이내로 처리될 예정이오니 이점 참고해 주십시오.<br />";
				content	+= "답변에 따라 실태신고 결과를 개별 통보해 드리겠습니다.<br />&nbsp;<br />";
				content	+= "감사합니다.<br />안녕히 계십시오.<br />&nbsp;<br />";
				content	+= "<a href='https://www.dietitian.or.kr/research_index.jsp' >영양사 실태신고센터 바로가기</a>";
				
			} else if("4".equals(ar_state)) {
				// 실태신고 회송시 발송 메일
				content ="안녕하십니까.<br />대한영양사협회 영양사 실태신고센터입니다.<br />&nbsp;<br />";
				content	+= to_name+" 영양사님<br />&nbsp;<br />" + vRegYear + "년 " + vRegMonth + "월 " + vRegDay + "일 제출한 영양사 실태 등의 신고 접수가 회송되었습니다.<br />";
				content	+= "&nbsp;<br />1. 제출하신 영양사 면허증과 정보가 일치하지 않은 경우<br />";
				content	+= "2. 기본 인적 사항에 오류가 있는 경우(E-mail, 전화번호 오류)<br />3. 기타 사항에 대해 기입하지 않은 경우<br />";
				content	+= "4. 근무처 주소가 명확하지 않은 경우<br />5. 기타의 경우<br />";
				content	+= "&nbsp;<br />회송된 실태신고서는 실태신고센터 마이페이지 &gt; 실태신고 수정에서<br />";
				content	+= "수정 후 다시 제출해 주시기를 부탁 드립니다.<br />&nbsp;<br />";
				content	+= "감사합니다.<br />안녕히 계십시오.<br />&nbsp;<br />";
				content	+= "<a href='https://www.dietitian.or.kr/research_index.jsp' >영양사 실태신고센터 바로가기</a>";
			}
			
		} else if("recipient".equals(methodNm)) {
			// 2. 보수교육 미대상자
			/*
			 * [관리자 > 영양사실태신고 > 보수교육 미대상자 ]
			 * : 대상자 선택 -> 상단 조회조건에서 [상태:완료] 변경 -> 저장 클릭시 mail 이 자동 발송된다.
			 * 현재는 완료시에만 메일 발송하고,  보류/회송시에는 메일 안보내고 있음
			 */

		    System.out.println("### data_frst_rgt_dttm =======> " + data_frst_rgt_dttm);	// YYYY(2013)
		    System.out.println("### vCsConEducationYr  =======> " + vCsConEducationYr);
		    
			
			//메일 제목
			subj = "[대한영양사협회 영양사 면허신고센터] 보수교육 미대상자 승인 완료 안내드립니다.";
			
			if("1".equals(ar_state)) {
				// 완료

				content	=  "<strong>[보수교육 미대상자 승인 완료]</strong><br />&nbsp;<br />";
				
				content	+= to_name + " 선생님, 안녕하십니까.<br />";
				content += "대한영양사협회 영양사 면허신고센터입니다.<br />&nbsp;<br />";
				
				content	+= "신청해주신 <strong>" + data_frst_rgt_dttm + "년도 보수교육 미대상자 승인이 완료</strong>되었음을 안내드리오니, 영양사 면허신고센터 홈페이지에서 영양사 면허신고 신청을 해주시기 바랍니다.<br />&nbsp;<br />";

				content	+= "※ " + yyyy + "년도 영양사 면허신고는 " + vCsConEducationYr + "년도 보수교육 이수 여부를 확인합니다. 따라서 " + vCsConEducationYr + "년도 보수교육 이수, 면제, 미대상자 여부 확인이 완료되신 후에 면허신고가 가능합니다. <br />";
				content	+= "최초 신고자의 경우 2013, 2015, 2017, 2019, 2021년도 보수교육 이수 여부를 확인합니다.<br />&nbsp;<br />";
				
				content	+= "<strong> &lt; 영양사 면허신고 방법(보수교육 이수, 면제, 미대상 여부 확인이 완료되신 경우)  &gt; </strong><br />";
				content	+= "영양사 면허신고센터 홈페이지(<a href='https://url.kr/x1g2ml' >https://url.kr/x1g2ml</a>) ▶ 면허신고 신청 ▶ 성명, 면허번호 입력 ▶ 개인정보제공동의 ▶ 핸드폰 또는 아이핀 본인인증 ▶ 비밀번호 설정 ▶ 면허신고 신청(성명, 면허번호, 비밀번호 입력) ▶ 면허신고서 작성 및 면허증 첨부 후 제출 클릭 ▶ 7일 후 마이페이지 면허신고 현황에서 확인 및 면허신고 확인서 출력 <br />&nbsp;<br />";
				
				content	+= "<strong> &lt; 보수교육 이수 여부 확인이 되지 않는 경우  &gt; </strong><br />";
				content	+= "<strong> 1) 보수교육 대상자</strong> : 보건소, 보건지소, 의료기관, 집단급식소, 육아종합지원센터, 어린이급식관리지원센터, 건강기능식품판매업소에 종사하는 영양사 <strong>→ 보수교육 미이수 시 추가 보수교육 이수 후 면허신고</strong> <br />";
				content	+= "- 영양사 보수교육센터(www.kdaedu.or.kr) ▶ 로그인 ▶ 보수교육 신청 ▶ 해당년도 추가 보수교육 신청 ▶ 교육비 결제 ▶ 나의학습방에서 교육 수강 ▶ 교육 수강 완료 1일 후 면허신고 가능<br />&nbsp;<br />";
				
				content	+= "<strong> 2) 보수교육 면제대상자</strong> : 군복무, 본인의 질병 또는 그 밖의 불가피한 사유(출산 및 육아휴직, 질병휴직 등)로 보수교육을 받기 어렵다고 보건복지부 장관이 인정하는 사람 <strong>→ 보수교육 면제 신청 후 면허신고</strong> <br />";
				content	+= "- 영양사 보수교육센터(www.kdaedu.or.kr) ▶ 로그인 ▶ 면제신청 클릭 ▶ 등록 클릭 ▶ 면제신청 해당년도 선택 ▶ 면제신청서 작성 및 증빙서류(휴직기간이 명시되고 기관장 직인이 포함된 휴직증명서 또는 재직증명서) 첨부 후 등록 클릭 ▶ 면제 확정 1일 후 면허신고 가능 <br />&nbsp;<br />";
				
				content	+= "<strong> 3) 보수교육 미대상자</strong> : 영양사 외에 다른 분야 근무, 대학교 및 대학원 진학, 미취업 등으로 보수교육대상자가 아니어서 보수교육을 받지 않아도 되는 사람 <strong>→ 보수교육 미대상자 신청 후 면허신고</strong> <br />";
				content	+= "- 영양사 면허신고센터 홈페이지(<a href='https://url.kr/x1g2ml' >https://url.kr/x1g2ml</a>) ▶ 보수교육 미대상자 ▶ 성명, 면허번호, 미대상자 신청년도 선택 후 확인 ▶ 현재 인적사항 작성 ▶ 미대상자 사유 선택 및 증빙서류 첨부 ▶ 제출 ▶ 미대상자 신청 완료 메일 확인 후 면허신고 가능<br />&nbsp;<br />";
				
				content	+= "감사합니다<br />&nbsp;<br />";
				content	+= "☞ 영양사 면허신고센터 바로가기" + "<a href='https://url.kr/x1g2ml' >(https://url.kr/x1g2ml)</a>";
				
			} else if("2".equals(ar_state)) { 
				// 보류
				content ="안녕하십니까.<br />대한영양사협회 영양사 실태신고센터입니다.<br />&nbsp;<br />";
				content	+= to_name+" 영양사님<br />&nbsp;<br />영양사 보수교육 미대상의 신고 접수가 보류중입니다.<br />";
				content	+= "제출하신 내용 중 &lsquo;법령 및 지침&rdquo;외의 내용이 있어 보건복지부에 질의하였습니다.<br />";
				content	+= "질의 사항은 14일 이내로 처리될 예정이오니 이점 참고해 주십시오.<br />";
				content	+= "답변에 따라 보수교육미대상 결과를 개별 통보해 드리겠습니다.<br />&nbsp;<br />";
				content	+= "감사합니다.<br />안녕히 계십시오.<br />&nbsp;<br />";
				content	+= "<a href='https://www.dietitian.or.kr/research_index.jsp' >영양사 실태신고센터 바로가기</a>";
				
			} else if("3".equals(ar_state)) {
				// 회송
				content ="안녕하십니까.<br />대한영양사협회 영양사 실태신고센터입니다.<br />&nbsp;<br />";
				content	+= to_name+" 영양사님<br />&nbsp;<br />영양사 보수교육 미대상 신청 접수가 회송되었습니다.<br />";
				content	+= "제출하신 자료를 확인한 바 영양사님께서는 영양사 보수교육 대상자임을 알려드립니다.<br />";
				content	+= "이에 따라 영양사 보수교육센터에 로그인하시어, 보수교육을 이수해 주십시오.<br />&nbsp;<br />";
				content	+= "영양사 보수교육은 신고년도를 제외한 최근 3년간의 보수교육에 대하여<br />소급하여 이수하셔야 합니다.<br />";
				content	+= "보수교육을 이수한 후 실태신고센터에서 실태신고를 해 주십시오.<br />&nbsp;<br />";
				content	+= "감사합니다.<br />안녕히 계십시오.<br />&nbsp;<br />";
				content	+= "<a href='https://www.dietitian.or.kr/research_index.jsp' >영양사 실태신고센터 바로가기</a>";
			}
			
		} else if("license".equals(methodNm)) {
			// 3. 면허정보등록
			/*
			 * [관리자 > 영양사실태신고 > 면허정보 등록자]
			 * : 대상자 선택 -> 상단 조회조건에서 [상태:완료] 변경 -> 저장 클릭시 mail 이 자동 발송된다.
			 * 현재는 완료시에만 메일 발송하고,  보류시에는 메일 안보내고 있음
			 */
			
			//메일 제목
			subj = "[대한영양사협회 영양사 면허신고센터] 영양사 면허정보 등록 완료 안내드립니다.";
			
			if("1".equals(ar_state)){
				// 완료

				content	=  "<strong>[영양사 면허정보 등록 완료]</strong><br />&nbsp;<br />";
				
				content	+= to_name + " 선생님, 안녕하십니까.<br />";
				content += "대한영양사협회 영양사 면허신고센터입니다.<br />&nbsp;<br />";
				
				content += "<strong>영양사 면허정보 등록이 완료</strong>되었음을 안내드리오니, 영양사 면허신고센터 홈페이지에서 영양사 면허신고 신청을 해주시기 바랍니다.<br />&nbsp;<br />";

				content	+= "※ " + yyyy + "년도 영양사 면허신고는 " + vCsConEducationYr + "년도 보수교육 이수 여부를 확인합니다. 따라서 " + vCsConEducationYr + "년도 보수교육 이수, 면제, 미대상자 여부 확인이 완료되신 후에 면허신고가 가능합니다. <br />";
				content	+= "최초 신고자의 경우 2013, 2015, 2017, 2019, 2021년도 보수교육 이수 여부를 확인합니다.<br />&nbsp;<br />";
				
				content	+= "<strong> &lt; 영양사 면허신고 방법(보수교육 이수, 면제, 미대상 여부 확인이 완료되신 경우) &gt; </strong><br />";
				content	+= "<strong> 1) 보수교육 대상자</strong> : 보건소, 보건지소, 의료기관, 집단급식소, 육아종합지원센터, 어린이급식관리지원센터, 건강기능식품판매업소에 종사하는 영양사 <strong>→ 보수교육 미이수 시 추가 보수교육 이수 후 면허신고</strong> <br />";
				content	+= "- 영양사 보수교육센터(www.kdaedu.or.kr) ▶ 로그인 ▶ 보수교육 신청 ▶ 해당년도 추가 보수교육 신청 ▶ 교육비 결제 ▶ 나의학습방에서 교육 수강 ▶ 교육 수강 완료 1일 후 면허신고 가능<br />&nbsp;<br />";

				content	+= "<strong> 2) 보수교육 면제대상자</strong> : 군복무, 본인의 질병 또는 그 밖의 불가피한 사유(출산 및 육아휴직, 질병휴직 등)로 보수교육을 받기 어렵다고 보건복지부 장관이 인정하는 사람 <strong>→ 보수교육 면제 신청 후 면허신고</strong> <br />";
				content	+= "- 영양사 보수교육센터(www.kdaedu.or.kr) ▶ 로그인 ▶ 면제신청 클릭 ▶ 등록 클릭 ▶ 면제신청 해당년도 선택 ▶ 면제신청서 작성 및 증빙서류(휴직기간이 명시되고 기관장 직인이 포함된 휴직증명서 또는 재직증명서) 첨부 후 등록 클릭 ▶ 면제 확정 1일 후 면허신고 가능 <br />&nbsp;<br />";
				
				content	+= "<strong> 3) 보수교육 미대상자</strong> : 영양사 외에 다른 분야 근무, 대학교 및 대학원 진학, 미취업 등으로 보수교육대상자가 아니어서 보수교육을 받지 않아도 되는 사람 <strong>→ 보수교육 미대상자 신청 후 면허신고</strong> <br />";
				content	+= "- 영양사 면허신고센터 홈페이지(<a href='https://www.dietitian.or.kr/research_nindex.jsp' >https://www.dietitian.or.kr/research_nindex.jsp</a>) ▶ 보수교육 미대상자 ▶ 성명, 면허번호, 미대상자 신청년도 선택 후 확인 ▶ 현재 인적사항 작성 ▶ 미대상자 사유 선택 및 증빙서류 첨부 ▶ 제출 ▶ 미대상자 신청 완료 메일 확인 후 면허신고 가능<br />&nbsp;<br />";
				
				content	+= "감사합니다<br />&nbsp;<br />";
				content	+= "☞ 영양사 면허신고센터 바로가기" + "<a href='https://url.kr/x1g2ml' >(https://url.kr/x1g2ml)</a>";
				
			} else if("2".equals(ar_state)){
				// 보류
				content ="안녕하십니까.<br />대한영양사협회 영양사 실태신고센터입니다.<br />&nbsp;<br />";
				content	+= to_name+" 영양사님<br />&nbsp;<br />요청하신 영양사 면허 정보 등록 결과<br />";
				content	+= "작성하신 내용과 첨부하신 영양사 면허증의 내용이 다릅니다<br />";
				content	+= "다시 한번 확인하시어 정확한 정보로 수정 등록해 주시길 부탁 드립니다.<br />";
				content	+= "감사합니다.<br />안녕히 계십시오.<br />&nbsp;<br />";
				content	+= "<a href='https://www.dietitian.or.kr/research_index.jsp' >영양사 실태신고센터 바로가기</a>";
			}
		}

	    System.out.println("### content =======> " + content);
	    
		
		String filename = "";
		String upYN = "";
		
		Properties mailProps = new Properties();
		//gmail을 이용할 경우 
		//mailProps.put("mail.smtp.host", "smtp.gmail.com");
		//mailProps.put("mail.smtp.port", "587");
		//mailProps.put("mail.smtp.auth", "true");
		//mailProps.put("mail.smtp.starttls.enable", "true");
		mailProps.put("mail.smtp.host", "localhost");
		EmailSender esender = new EmailSender(mailProps);
		esender.sendEmail(from, fr_name, to, subj, content, upYN, saveFolder, filename);
		
	}
	
	
	public ActionForward sendUmsForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		actulDao dao = new actulDao();
		
		String chk	=request.getParameter("chk");		//선택, 전체 여부
		request.setAttribute("chk"		, request.getParameter("chk"		));	//선택, 전체 여부
		String register = request.getParameter("register"	);	//등록자
		request.setAttribute("register"	, register);	//등록자
		String menu = request.getParameter("menu"	);	//등록자
		request.setAttribute("menu"	, menu);	//등록자
		
		
		if("CHK".equals(chk)) {//선택 저장
			
			//전체 개수 구함
			String temp			= request.getParameter("pers_hp"		);	//전화 번호
			String pers_hp[]	= temp.split(",");

			
			request.setAttribute("ncount"	, pers_hp.length);								//전체개수 넣음
			request.setAttribute("param"	,"&pers_hp="+request.getParameter("pers_hp"));	//전화 번호 넣음
			request.setAttribute("pers_hp"	, request.getParameter("pers_hp"));				//전화 번호만
			request.setAttribute("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));				//전화 번호만
			
		} else if("ALL".equals(chk)) {

			if(request.getParameter("pers_name1")!=null){
				String ar_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
				map.put("pers_name1",           ar_name1);}		
			if(request.getParameter("lic_no1")!=null){
				map.put("lic_no1",              request.getParameter("lic_no1"));}	
			if(request.getParameter("atc_wrtr_cttno1")!=null){
				map.put("atc_wrtr_cttno1",              request.getParameter("atc_wrtr_cttno1"));}	
			if(request.getParameter("email1")!=null){
				map.put("email1",           request.getParameter("email1"));}	
			if(request.getParameter("ar_state1")!=null){
				map.put("ar_state1",          request.getParameter("ar_state1"));}
			if(request.getParameter("ar_finish_yn1")!=null){
				map.put("ar_finish_yn1",          request.getParameter("ar_finish_yn1"));}
			if(request.getParameter("yyyy1")!=null){
				map.put("yyyy1",          request.getParameter("yyyy1"));}
			if(request.getParameter("pers_hp1")!=null){
				map.put("pers_hp1",          request.getParameter("pers_hp1"));}

			
			int ncount = 0;
			List<Map> count = null; 
			
			if("manage".equals(menu)){				
				count=dao.actulmanagecnt(map);
			}else if("recipient".equals(menu)){
				count=dao.actulrecipientcnt(map);				
			}else if("license".equals(menu)){
				count=dao.actullicensecnt(map);				
			}
			
			ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
			
			map.clear();
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param = "";

			
			if(request.getParameter("pers_name1"	)!=null)request.setAttribute("pers_name",  URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8") );
			if(request.getParameter("lic_no1"		)!=null)param+="&lic_no1="	+ request.getParameter("lic_no1"		);
			if(request.getParameter("atc_wrtr_cttno1"	)!=null)param+="&atc_wrtr_cttno1="	+ request.getParameter("atc_wrtr_cttno1"		);
			if(request.getParameter("email1"	)!=null)param+="&email1="+ request.getParameter("email1"	);
			if(request.getParameter("ar_state1"		)!=null)param+="&ar_state1="		+ request.getParameter("ar_state1");
			if(request.getParameter("ar_finish_yn1"		)!=null)param+="&ar_finish_yn1="		+ request.getParameter("ar_finish_yn1");
			if(request.getParameter("yyyy1"		)!=null)param+="&yyyy1="		+ request.getParameter("yyyy1");
			
			
			//검색 변수 넘김
			request.setAttribute("param", param);

		}
		
		request.setAttribute("from", menu );
		return (mapping.findForward("sendUmsForm"));
		
	}
	
	
	public ActionForward sendUmsLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		List<Map> count = null; //오류났을때 10개 이상인가 체크용
		List<Map> list= null;
		
		Map map = new HashMap();
		actulDao dao = new actulDao();
		HttpSession session=request.getSession(); 
		
		//오류 출력 여부는 우선 제외한다.
		int tot_cnt = 0;
		int success_cnt = 0;
		int faile_cnt = 0;
		
		String chk			=request.getParameter("chk");		//선택, 전체 여부
		String register		=request.getParameter("register");	//유저ID//생성자
		String menu=request.getParameter("menu");		//쿼리 구분자
		
		String param		=request.getParameter("param"		);
		String send_phone	=request.getParameter("send_phone"	);
		String subject		= URLDecoder.decode(request.getParameter("subject"),"UTF-8");
		String msg_body		= URLDecoder.decode(request.getParameter("msg_body"),"UTF-8");	
		
		//예약발송용
		String request_time = "";
		if( request.getParameter("yyyyMMdd") != null )
			request_time = request.getParameter("yyyyMMdd") + " " + request.getParameter("HHmm");

			
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
    	
		
		int outcount = 0;	//화면으로 뿌리는 개수

		if("CHK".equals(chk)) {//선택 저장test

			String temp			= request.getParameter("pers_hp"		);	//전화번호
			String pers_hp[]	= temp.split(",");
			String temp1			= URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");	//이름
			String pers_name[]	= temp1.split(",");
			tot_cnt = pers_hp.length;
			//for 문 돌면서 저장한다.
			for( int i=0; i<pers_hp.length; i++) {

				map.clear();
				
				if(pers_hp.length>0) {
					
					//seq 번호는 필요 없다.
					//아이디만 있으면 되므로 대상 조회가 필요 없다.아이디도 위에서 넣었다.
					map.put("msg_type"		, msg_type );
					map.put("ar_tel_hp"	, pers_hp[i]);
					map.put("send_phone"	, send_phone);
					map.put("subject"		, subject);
					map.put("msg_body"		, msg_body);
					map.put("register"		, register);
					map.put("etc1",                      session.getAttribute("G_ID"));
		    		map.put("etc2",                      session.getAttribute("G_BRAN"));  
		    		map.put("etc3",         pers_name[i]);
		    		
		    		//2013.01.16추가
		    		map.put("umscnt"		, i+1);
					//셋 한다.
					try {
						if(request_time.length() > 0 ) {
							map.put("request_time", request_time);
							dao.insertumsResultData(map);
						}else {
							dao.insertumsData(map);
						}
						success_cnt++;
					} catch (Exception e) {						
						faile_cnt++;
					}
				}
			}
		} else if("ALL".equals(chk)) {
 
			// 변수에 값 받아서 넣음

			if(request.getParameter("pers_name")!=null) map.put("pers_name1"		, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));
			if(request.getParameter("lic_no1")!=null) map.put("lic_no1"	, request.getParameter("lic_no1"		));
			if(request.getParameter("atc_wrtr_cttno1")!=null) map.put("atc_wrtr_cttno1"	, request.getParameter("atc_wrtr_cttno1"		));
			if(request.getParameter("email1")!=null) map.put("email1"	, request.getParameter("email1"		));
			if(request.getParameter("ar_state1")!=null) map.put("ar_state1"	, request.getParameter("ar_state1"		));
			if(request.getParameter("ar_finish_yn1")!=null) map.put("ar_finish_yn1"	, request.getParameter("ar_finish_yn1"		));
			if(request.getParameter("yyyy1")!=null) map.put("yyyy1"	, request.getParameter("yyyy1"		));
			if(request.getParameter("pers_hp1")!=null) map.put("pers_hp1"	, request.getParameter("pers_hp1"		));

			//대상 검색
			if("manage".equals(menu)){				
				list=dao.actulmanageList(map);
			}else if("recipient".equals(menu)){				
				list=dao.actulrecipientList(map);
			}else if("license".equals(menu)){
				list=dao.actullicenseList(map);				
			}

			tot_cnt = list.size();
			//여기서 포문 돌면서 인서트 한다.
			for( int i=0; i<list.size(); i++) {

				map.clear();

				//deq는 알아오지 않는다.


				//휴대번호만 있으면 되므로 대상 조회가 필요 없다  
		
				map.put("msg_type"		, msg_type );
				if("manage".equals(menu)){
					map.put("ar_tel_hp"	, list.get(i).get("pers_hp"));					
				}else{
					map.put("ar_tel_hp"	, list.get(i).get("atc_wrtr_cttno"));
				}
				map.put("send_phone"	, send_phone);
				map.put("subject"		, subject);
				map.put("msg_body"		, msg_body);
				map.put("register"		, register);
				map.put("etc1",                      session.getAttribute("G_ID"));
	    		map.put("etc2",                      session.getAttribute("G_BRAN"));
	    		map.put("etc3",         list.get(i).get("pers_name"));
				//2013.01.16추가
	    		map.put("umscnt"		, i+1);
				//셋 한다.
				try {
					if(request_time.length() > 0 ) {
						map.put("request_time", request_time);
						dao.insertumsResultData(map);
					}else {
						dao.insertumsData(map);
					}
					success_cnt++;
				} catch (Exception e) {
					faile_cnt++;
				}				
			}
		}

		request.setAttribute("tot_cnt",tot_cnt);
		request.setAttribute("success_cnt",success_cnt);
		request.setAttribute("faile_cnt",faile_cnt);
		
		return (mapping.findForward("sendAllLink"));

	}
	
	//신고관리 상세검색 첨부파일 업로드 화면 jsp
	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			String pers_name			=request.getParameter("pers_name"	);
			String yyyy					=request.getParameter("yyyy"		);
			String lic_no				=request.getParameter("lic_no"		);
			String cs_con_education		=request.getParameter("cs_con_education"		);
			
			request.setAttribute("pers_name",pers_name);
			request.setAttribute("yyyy",yyyy);
			request.setAttribute("lic_no",lic_no);
			request.setAttribute("cs_con_education",cs_con_education);
		
			return mapping.findForward("upFile");
	}
	
	//신고관리 상세검색 첨부파일 업로드 
	//public void upFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	public void upFile(MultipartRequest multi, HttpServletRequest request) throws Exception{
		//response.setCharacterEncoding("UTF-8");
		Map map=new HashMap();
		actulDao dao = new actulDao();
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		HttpSession session=request.getSession();

		String msg="";
		String filePath = "D:/WEB/dietitian.or.kr_ver3/upload/report/";
		String filename = request.getSession().getId() + "_" +formatter.format(date) + "_" + 1;
		String upfilename = multi.getFilesystemName("bbs_file1");		
		String realfilename = multi.getOriginalFileName("bbs_file1");
		String ar_add_file_url = "";
		String ar_add_file_type = multi.getContentType("bbs_file1").substring(multi.getContentType("bbs_file1").indexOf("/")+1, multi.getContentType("bbs_file1").length());
		String ext = "";
		String [] exts = multi.getOriginalFileName("bbs_file1").split("\\.");
		
		if(!upfilename.equals("") && upfilename != null){
			
			if ( exts.length > 0 ){
				ext = exts[ exts.length - 1].toLowerCase();
				String check = "jpg,jpeg,gif,bmp,png,swf,tiff,tif,mpg,mpeg,avi,asf,wma,wmv,rm,ra,mp3,xls,xlsx,hwp,pdf";
				if ( check.indexOf(ext) >= 0 )
					filename += "." + ext;
			}
			
			//업로드한 파일명 수정
			File file= multi.getFile("bbs_file1");
			File f1 = new File(filePath + upfilename);
			File f2 = new File(filePath + filename);
			
			if(f1.exists()){
				f1.renameTo(f2);
			}
			
			long filesize=file.length();
			
			try{
					map.put("pers_name",      URLDecoder.decode(multi.getParameter("pers_name"),"UTF-8"));
					map.put("yyyy",         multi.getParameter("yyyy"));
					map.put("lic_no",    multi.getParameter("lic_no"));
					map.put("cs_con_education",    multi.getParameter("cs_con_education"));
					map.put("code_seq",    multi.getParameter("code_seq"));
					
					map.put("ar_add_file_name",        		realfilename);		//파일
				    map.put("ar_add_file_url",        		"/upload/report/" + filename);	//파일
				    map.put("ar_add_file_type",        		ext);		//파일
				    map.put("ar_add_file_size",        		filesize);		//파일
				    
					int uptFile = dao.uptFile(map);
	
			}catch(Exception e){   //try
				e.printStackTrace();
				msg+="업로드를 실패했습니다.";
			}
			
			if(msg.equals("")){
				request.setAttribute("msg", "업로드가 완료됐습니다.");
				session.setAttribute("ar_add_file_name",realfilename);
				session.setAttribute("ar_add_file_url", ar_add_file_url);
			}else{
				request.setAttribute("msg", msg);
			}
		}
	
		//return mapping.findForward("upFile");
	}  //Exception
	
	

		
	
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			return mapping.findForward("uploadExcel");
	}
	
	public void actulSampleExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
		actulDao dao = new actulDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_NAME")); //유저ID
		//여기부터 개발자 변경 필요
		String name="upExcel"; //프로그램명
		String s_name="보수교육내역"; //엑셀 시트명
		String filename=date+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"gubun", 	 	"yyyy", 	"lic_no",	"pers_name",	"cs_con_education",	"ar_fnish_point",	"ar_marks_point",	"ar_state","report_year"}; //헤더 영문
		String header_k[]={"신규/갱신 구분",	"등록년도",	"면허번호","성명",			"보수교육년도",		"총시간",				"이수기간"	,				"진행코드",	"신고년도"}; //헤더 국문
		int c_size[]={10, 10, 15,20, 10, 10, 10, 10, 10};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> sampleExcel=dao.sampleExcel(map);		
		map.put("gubun", "i");
		map.put("yyyy", "2015");
		map.put("lic_no", "123456");
		map.put("pers_name", "홍길동");
		map.put("cs_con_education", "2013");
		map.put("ar_fnish_point", "6");
		map.put("ar_marks_point", "6");
		map.put("ar_state", "0");
		map.put("report_year", "2015");
		sampleExcel.set(0, map);
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(sampleExcel, filename, s_name,header_e,header_k,c_size);
		
		//File f=new File(fileDir+filename); //파일생성
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}		
	}
	
	public ActionForward actulUploadExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		Map map=new HashMap();
		actulDao dao = new actulDao();
		
		
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		
		String saveFolder="D:\\WEB\\KDA_VER3\\upload\\excel\\";
		//String saveFolder="E:\\WEB\\KDA_VER3\\upload\\excel\\";
		String encType="UTF-8";
		int maxSize=10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
		String filename="";
		
		Workbook openWorkbook;
		Sheet openSheet;
		String sheetName = "";
		Cell openCell;
		int per = 0;
		
		String msg="";
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				filename=multi.getFilesystemName(name);
				f=new File(saveFolder+"/"+filename);
			}
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
				    map.put("yyyy",    		        cell[1].getContents());	    //등록년도
				    map.put("lic_no",        		    cell[2].getContents());		//면허번호
				    map.put("pers_name",        		cell[3].getContents());		//이름
				    map.put("cs_con_education",        		    cell[4].getContents());		//보수교육년도
				    map.put("ar_finish_point",        		    cell[5].getContents());		//총시간
				    map.put("ar_marks_point",        		cell[6].getContents());		//이수시간
				    map.put("ar_state",        		cell[7].getContents());		//진행코드
				    map.put("report_year",        		cell[8].getContents());		//신고년도
				    
					if("u".equals(cell[0].getContents())){
						
						int update_edu = dao.updateStatus(map);	
					}else if("i".equals(cell[0].getContents())){
						
						/*List<Map> personCnt=dao.personCnt(map);*/
						List<Map> personCnt=dao.statusCnt(map);
						per= ((Integer)(personCnt.get(0).get("cnt"))).intValue();
						if(per<1){
							int insert_edu = dao.insertStatus(map);
						}else{
							msg+="이름 "+cell[3].getContents()+" 면허번호"+cell[2].getContents()+" 의 회원은 이미 등록된 회원입니다.업로드 할 수 없습니다.";
						}
					}			  			    						
				
				}  //for 
				openWorkbook.close();
			//}   //while
		}catch(Exception e){   //try
			e.printStackTrace();
			
			msg+="업로드를 실패했습니다.";
		}
		
		if(msg.equals("")){
			request.setAttribute("msg", "업로드가 완료됐습니다.");
		}else{
			request.setAttribute("msg", msg);
		}

		return mapping.findForward("uploadExcel");
	}  //Exception
	
	
	/*   영양사   실태신고 관리    */		
	public ActionForward edusearchdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return (mapping.findForward("edusearchList"));		
	}	
	
	public ActionForward edusearchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
		Map map = new HashMap();
		actulDao dao = new actulDao();
	
		if(request.getParameter("ke_name1")!=null){
			String ke_name1 = URLDecoder.decode(request.getParameter("ke_name1"),"UTF-8");
			map.put("ke_name1",           ke_name1);}			
		if(request.getParameter("yyyy1")!=null){
			map.put("yyyy1",              request.getParameter("yyyy1"));}	
		if(request.getParameter("ke_finish_yn1")!=null){
			map.put("ke_finish_yn1",           request.getParameter("ke_finish_yn1"));}	
		if(request.getParameter("ke_lic_no1")!=null){
			map.put("ke_lic_no1",           request.getParameter("ke_lic_no1"));}	
				    
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
			List<Map> kdaeducnt=dao.getKda_eduCnt(map);
			ncount = ((Integer)(kdaeducnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> kdaeud = dao.getKda_edu(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""+	npage	+"\",");		
			result.append("\"total\":\"" + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<kdaeud.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( kdaeud.get(i).get("yyyy")	== null ? ""                 :     kdaeud.get(i).get("yyyy"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("yyyy_seq")	== null ? ""             :     kdaeud.get(i).get("yyyy_seq"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_name")	== null ? ""             :     kdaeud.get(i).get("ke_name"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_lic_no")	== null ? ""             :     kdaeud.get(i).get("ke_lic_no"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_seq")	== null ? ""         :     kdaeud.get(i).get("ke_seq"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_subject")	== null ? ""         :     kdaeud.get(i).get("ke_subject"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_fromto")	== null ? ""             :     kdaeud.get(i).get("ke_fromto"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_isutime")	== null ? "" :     kdaeud.get(i).get("ke_isutime"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_jesukaing")	== null ? ""         :     kdaeud.get(i).get("ke_jesukaing"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_total")	== null ? "" :     kdaeud.get(i).get("ke_total"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_finish_dt")	== null ? ""         :     kdaeud.get(i).get("ke_finish_dt"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_finish_no")	== null ? ""             :     kdaeud.get(i).get("ke_finish_no"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_finish_yn")	== null ? ""         :     kdaeud.get(i).get("ke_finish_yn"))  +"\",");
				result.append("\"" + ( kdaeud.get(i).get("ke_birth")	== null ? ""             :     kdaeud.get(i).get("ke_birth"))  +"\"");
				result.append("]}");
			}
			
			result.append("]}");
			
			request.setAttribute("result",result);		
			return (mapping.findForward("ajaxout"));
			
	}
	
	public ActionForward uploadEduExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			return mapping.findForward("uploadEduExcel");
	}
	
public void eduSampleExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
		actulDao dao = new actulDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_NAME")); //유저ID
		//여기부터 개발자 변경 필요
		String name="upEduExcel"; //프로그램명
		String s_name="보수교육관리"; //엑셀 시트명
		String filename=date+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"gubun", 	 	"yyyy", 	"ke_name",	"ke_lic_no",	"ke_subject",	"ke_fromto",	"ke_isutime",	"ke_jesukaing","ke_total","ke_finish_dt","ke_finish_no","ke_finish_yn","ke_birth"}; //헤더 영문
		String header_k[]={"신규/갱신 구분",	"년도",	"이름","면허번호",			"제목",		"보수교육기간",				"이수시간"	,				"재수강여부",	"총시간","이수일자","이수번호","이수여부","생년월일"}; //헤더 국문
		int c_size[]={10, 10, 10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> sampleExcel=dao.sampleEduExcel(map);		
		map.put("gubun", "i");
		map.put("yyyy", "2015");
		map.put("ke_name", "홍길동");
		map.put("ke_lic_no", "123456");
		map.put("ke_subject", "2015영양사 보수교육");
		map.put("ke_fromto", "2015021520151231");
		map.put("ke_isutime", "6");
		map.put("ke_jesukaing", "N");
		map.put("ke_total", "6");
		map.put("ke_finish_dt", "20150812");
		map.put("ke_finish_no", "12345");
		map.put("ke_finish_yn", "Y");
		map.put("ke_birth", "19880101");
		sampleExcel.set(0, map);
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(sampleExcel, filename, s_name,header_e,header_k,c_size);
		
		//File f=new File(fileDir+filename); //파일생성
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}		
	}

	public ActionForward eduUploadExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		Map map=new HashMap();
		actulDao dao = new actulDao();
		
		
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		
		String saveFolder="D:\\WEB\\KDA_VER3\\upload\\excel\\";
		//String saveFolder="E:\\WEB\\KDA_VER3\\upload\\excel\\";
		String encType="UTF-8";
		int maxSize=10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
		String filename="";
		
		Workbook openWorkbook;
		Sheet openSheet;
		String sheetName = "";
		Cell openCell;
		int per = 0;
		
		String msg="";
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				filename=multi.getFilesystemName(name);
				f=new File(saveFolder+"/"+filename);
			}
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
				    System.out.println("cellLen=======>"+cellLen);
				    map.put("yyyy",    		        cell[1].getContents());	    //년도
				    map.put("ke_name",        		    cell[2].getContents());		//이름
				    map.put("ke_lic_no",        		cell[3].getContents());		//면허번호
				    map.put("ke_subject",        		    cell[4].getContents());		//제목
				    map.put("ke_fromto",        		    cell[5].getContents());		//보수교육기간
				    map.put("ke_isutime",        		cell[6].getContents());		//이수시간
				    map.put("ke_jesukaing",        		cell[7].getContents());		//재수강여부
				    map.put("ke_total",        		cell[8].getContents());		//총시간
				    map.put("ke_finish_dt",        		cell[9].getContents());		//이수일자
				    map.put("ke_finish_no",        		cell[10].getContents());		//이수번호
				    map.put("ke_finish_yn",        		cell[11].getContents());		//이수여부
				    map.put("ke_birth",        		cell[12].getContents());		//생년월일
				    
					if("u".equals(cell[0].getContents())){
						int update_edu = dao.updateKda_edu(map);						
					}else if("i".equals(cell[0].getContents())){
						
						List<Map> personCnt=dao.eduCnt(map);
						per= ((Integer)(personCnt.get(0).get("cnt"))).intValue();
						
						if(per<1){
							int insert_edu = dao.insertKda_edu(map);
						}else{
							msg+="년도"+cell[1].getContents()+"이름 "+cell[2].getContents()+" 면허번호"+cell[3].getContents()+" 의 회원은 이미 등록된 회원입니다.업로드 할 수 없습니다.";
						}
					}			  			    						
				
				}  //for 
				openWorkbook.close();
			//}   //while
		}catch(Exception e){   //try
			e.printStackTrace();
			msg+="업로드를 실패했습니다.";
		}
		
		if(msg.equals("")){
			request.setAttribute("msg", "업로드가 완료됐습니다.");
		}else{
			request.setAttribute("msg", msg);
		}
	
		return mapping.findForward("uploadExcel");
	}  //Exception
	
	public void recipientFileDown(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		actulDao dao = new actulDao();
		
		if(request.getParameter("code_seq")!=null){
			map.put("code_seq",              request.getParameter("code_seq"));}
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+request.getParameter("code_seq"));
		
		List<Map> getRecipient = dao.getRecipient(map);
		
		String fileName = "";
		String fileUrl = "";
		
		fileName = (String) getRecipient.get(0).get("cs_attachments");
		fileUrl = (String) getRecipient.get(0).get("cs_attachments_url");
		fileUrl = "http://127.0.0.1:9090"+fileUrl;
		fileUrl = "http://210.127.56.232:5050/upload/003/AC0F9EE3BE3D5EB3F40A5981C878F327_201505261424_1.hwp";
		fileUrl = "http://127.0.0.1:8080/upload/license/5202201010001_24.hwp";
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fileUrl);
		
		
		FileOutputStream fos = null;
        InputStream is = null;
        try {
            fos = new FileOutputStream("E:\\"+fileName);
 
            URL url = new URL("http://127.0.0.1:9090/upload/recipient/201506122057498371.hwp");
            URLConnection urlConnection = url.openConnection();
            is = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = is.read(buffer)) != -1) {
                fos.write(buffer, 0, readBytes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		              
		
	}

	public ActionForward actulCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		Map chkMap = new HashMap();
		actulDao dao = new actulDao();
		String mode ="I";
		String ar_finish_yn ="";
		
		Calendar cal = Calendar.getInstance();
		int yyyy1 = cal.get(Calendar.YEAR);
		String year1 = Integer.toString(yyyy1);

		String saveFolder="D:\\WEB\\dietitian.or.kr_ver3\\upload\\report\\";
		//String saveFolder="E:\\WEB\\KDA_VER3\\upload\\excel\\";
		String encType="UTF-8";
		int maxSize=10*10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType, new DefaultFileRenamePolicy());

		if(multi.getParameter("pers_name")!=null) chkMap.put("PersName", URLDecoder.decode(multi.getParameter("pers_name"),"UTF-8"));
		if(multi.getParameter("lic_no")!=null) chkMap.put("LicNo", URLDecoder.decode(multi.getParameter("lic_no"),"UTF-8"));
		if(multi.getParameter("lic_print_dt")!=null) map.put("lic_print_dt", URLDecoder.decode(multi.getParameter("lic_print_dt"),"UTF-8"));
	
		map.put("pers_name",chkMap.get("PersName"));
		map.put("lic_no",chkMap.get("LicNo"));
		
		String msg ="";
		int procReturn = 0;
		String ReturnStateStr = "";
		String ReturnFinishYN = "";
		
		int yyyy = cal.get(Calendar.YEAR);
		int month = cal.get ( Calendar.MONTH ) + 1 ;
		int date = cal.get ( Calendar.DATE ) ;
		String year = Integer.toString(yyyy);
		
		Map result = dao.callReportState_chk(chkMap);
		
		if(result.get("ReturnState")!=null){
			procReturn = (Integer) result.get("ReturnState");
		}
		if(result.get("ReturnStateStr")!=null){
			ReturnStateStr = (String) result.get("ReturnStateStr");
		}
		if(result.get("ReturnFinishYN")!=null){
			ReturnFinishYN = (String) result.get("ReturnFinishYN");
		}
		System.out.println("procReturn===========>"+procReturn);
		System.out.println("ReturnStateStr===========>"+ReturnStateStr);
		System.out.println("ReturnFinishYN===========>"+ReturnFinishYN);
		
		// ReturnState : 1 (대상자아님-면허발급일자가 3년 이내) => 신규페이지 필요
		if(procReturn == 1) {
			msg="면허(실태)신고가 대상자가 아닙니다.";
		}
		// ReturnState : 2 (면허발급일자 작성) => 면허발급일자 작성 페이지
		else if (procReturn == 2) {
			msg="면허정보수정에서 면허발급일자를 등록하세요.";
		}
		// ReturnState : 3 (교육이수필요-실태신고자격미달)(실태신고 기록없음)
		else if (procReturn == 3) {
			msg=ReturnStateStr+"도 보수교육 내역이 없습니다.";
		}
		// ReturnState : 4 (실태신고작성가능)
		else if (procReturn == 4) {
			if(request.getParameter("grid_cs_con_education")!=null){
				map.put("cs_con_education",              request.getParameter("grid_cs_con_education"));
				request.setAttribute("cs_con_education",              request.getParameter("grid_cs_con_education"));}	
			
			request.setAttribute("now_year", year);
			//request.setAttribute("result",dao.getStatusData(map));			
			request.setAttribute("person",dao.getPersonData(map));	
			request.setAttribute("recentRow", dao.getRecentSearch(map));
			//request.setAttribute("recentYearRow", dao.getRecentYear(map));
			request.setAttribute("mode", mode);
			request.setAttribute("getEmail", dao.getEmail(map));
			request.setAttribute("fileInfo", dao.getFileInfo(map));
		}
		// ReturnState : 5 (실태신고 저장상태)
		else if (procReturn == 5) {
			msg="면허(실태)신고가 이미 저장되어있습니다.";
		}
		// ReturnState : 6 (실태신고완료)
		else if (procReturn == 6) {
			msg="면허(실태)신고가 이미 완료 되었습니다.";
		}
		// ReturnState : 7 (존재하지 않는 사용자)
		else if (procReturn == 7) {
			msg="존재하지 않는 사용자 입니다.";
		}
		
		request.setAttribute("yyyy", year);
		request.setAttribute("msg", msg);
		request.setAttribute("chk", "chk");
		request.setAttribute("mode", mode);
		return (mapping.findForward("statusUpt"));
		
	}
	
	
}	

	


