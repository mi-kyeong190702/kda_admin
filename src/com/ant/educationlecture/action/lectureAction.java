package com.ant.educationlecture.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.Normalizer.Form;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ant.common.util.CommonUtil;
import com.ant.common.util.StringUtil;
import com.ant.document.dao.documentDao;
import com.ant.educationlecture.dao.lectureDao;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class lectureAction extends DispatchAction {
	
	protected static Logger logger = Logger.getRootLogger();
	protected static String sel="";
	protected static String tnm="";
	protected static String keyword=""; 
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
	
	/*   강사 검색    */		
	public ActionForward lecturedata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		return (mapping.findForward("lectureList"));		
	}
		
	public ActionForward lectureList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				
			Map map = new HashMap();
			lectureDao dao = new lectureDao();			
			
			if(request.getParameter("lt_name")!=null){
				String lt_name = URLDecoder.decode(request.getParameter("lt_name"),"UTF-8");				
				map.put("lt_name",           lt_name);}
//			JUMIN_DEL
//			if(request.getParameter("lt_pers_no")!=null){				
//				map.put("lt_pers_no",              request.getParameter("lt_pers_no"));}	
			if(request.getParameter("lt_pers_birth")!=null){				
				map.put("lt_pers_birth",              request.getParameter("lt_pers_birth"));}	
			if(request.getParameter("lt_kind")!=null){	
				map.put("lt_kind",                 request.getParameter("lt_kind"));}
			
			map.put("check",          "2");
					
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
			List<Map> lecturecnt=dao.lecturecnt(map);
			ncount = ((Integer)(lecturecnt.get(0).get("cnt"))).intValue();
	
			int ntotpage = (ncount/nrows)+1;
			
			List<Map> lecture = dao.lecture(map);
			
			StringBuffer result = new StringBuffer();
					
			result.append("{\"page\":\""  +	npage	+"\",");		
			result.append("\"total\":\""  + ntotpage+"\",");
			result.append("\"records\":\""+	ncount	+"\",");
			result.append("\"rows\":[");
			
			for(int i=0; i<lecture.size(); i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( lecture.get(i).get("lt_name")	          == null ? ""       :     lecture.get(i).get("lt_name"))                +"\",");
				// JUMIN_DEL
//				result.append("\"" + ( lecture.get(i).get("lt_pers_no")	          == null ? ""       :     lecture.get(i).get("lt_pers_no"))             +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_pers_birth")	          == null ? ""       :     lecture.get(i).get("lt_pers_birth"))             +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_kind")              == null ? ""       :     lecture.get(i).get("lt_kind"))                +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_major")	          == null ? ""       :     lecture.get(i).get("lt_major"))               +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major_all")	          == null ? ""       :     lecture.get(i).get("lt_major_all"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_comp_name")         == null ? ""       :     lecture.get(i).get("lt_comp_name"))           +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_comp_tel")          == null ? ""       :     lecture.get(i).get("lt_comp_tel"))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_area")	          == null ? ""       :     lecture.get(i).get("lt_area"))                +"\",");				
				result.append("\"" + ( lecture.get(i).get("lt_code")	          == null ? ""       :     lecture.get(i).get("lt_code"))                +"\",");			
				result.append("\"" + ( lecture.get(i).get("lt_image")	          == null ? ""       :     lecture.get(i).get("lt_image"))               +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_post")              == null ? ""       :     lecture.get(i).get("lt_post"))                +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_add")	              == null ? ""       :     lecture.get(i).get("lt_add"))                 +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_hp")                == null ? ""       :     lecture.get(i).get("lt_hp"))                  +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_email")             == null ? ""       :     lecture.get(i).get("lt_email"))               +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_duty")	          == null ? ""       :     lecture.get(i).get("lt_duty"))                +"\",");				
				result.append("\"" + ( lecture.get(i).get("lt_comp_add")	      == null ? ""       :     lecture.get(i).get("lt_comp_add"))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_comp_post")	      == null ? ""       :     lecture.get(i).get("lt_comp_post"))           +"\",");			
				result.append("\"" + ( lecture.get(i).get("lt_univer1")	          == null ? ""       :     lecture.get(i).get("lt_univer1").toString().replace("\t", " "))             +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer2")	          == null ? ""       :     lecture.get(i).get("lt_univer2").toString().replace("\t", " "))             +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer3")	          == null ? ""       :     lecture.get(i).get("lt_univer3").toString().replace("\t", " "))             +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer4")	          == null ? ""       :     lecture.get(i).get("lt_univer4").toString().replace("\t", " "))             +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer5")	          == null ? ""       :     lecture.get(i).get("lt_univer5").toString().replace("\t", " "))             +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier1")          == null ? ""       :     lecture.get(i).get("lt_carrier1").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier2")          == null ? ""       :     lecture.get(i).get("lt_carrier2").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier3")          == null ? ""       :     lecture.get(i).get("lt_carrier3").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier4")          == null ? ""       :     lecture.get(i).get("lt_carrier4").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier5")          == null ? ""       :     lecture.get(i).get("lt_carrier5").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier6")          == null ? ""       :     lecture.get(i).get("lt_carrier6").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier7")          == null ? ""       :     lecture.get(i).get("lt_carrier7").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier8")          == null ? ""       :     lecture.get(i).get("lt_carrier8").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier9")          == null ? ""       :     lecture.get(i).get("lt_carrier9").toString().replace("\t", " "))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier10")         == null ? ""       :     lecture.get(i).get("lt_carrier10").toString().replace("\t", " "))           +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_bankname")	      == null ? ""       :     lecture.get(i).get("lt_bankname"))            +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_bankaccount")       == null ? ""       :     lecture.get(i).get("lt_bankaccount"))         +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_bankReceiptName")   == null ? ""       :     lecture.get(i).get("lt_bankReceiptName"))     +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_remark")	          == null ? ""       :     lecture.get(i).get("lt_remark"))              +"\",");
				
				result.append("\"" + ( lecture.get(i).get("lt_major_chk")	          == null ? ""       :     lecture.get(i).get("lt_major_chk"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major2")	          == null ? ""       :     lecture.get(i).get("lt_major2"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major_chk2")	          == null ? ""       :     lecture.get(i).get("lt_major_chk2"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major3")	          == null ? ""       :     lecture.get(i).get("lt_major3"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major_chk3")	          == null ? ""       :     lecture.get(i).get("lt_major_chk3"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major4")	          == null ? ""       :     lecture.get(i).get("lt_major4"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major_chk4")	          == null ? ""       :     lecture.get(i).get("lt_major_chk4"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major5")	          == null ? ""       :     lecture.get(i).get("lt_major5"))              +"\",");
			    result.append("\"" + ( lecture.get(i).get("lt_major_chk5")	          == null ? ""       :     lecture.get(i).get("lt_major_chk5"))              +"\",");

				result.append("\"" + ( lecture.get(i).get("lt_univer_year1")	          == null ? ""       :     lecture.get(i).get("lt_univer_year1"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_mon1")	          == null ? ""       :     lecture.get(i).get("lt_univer_mon1"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_year2")	          == null ? ""       :     lecture.get(i).get("lt_univer_year2"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_mon2")	          == null ? ""       :     lecture.get(i).get("lt_univer_mon2"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_year3")	          == null ? ""       :     lecture.get(i).get("lt_univer_year3"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_mon3")	          == null ? ""       :     lecture.get(i).get("lt_univer_mon3"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_year4")	          == null ? ""       :     lecture.get(i).get("lt_univer_year4"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_mon4")	          == null ? ""       :     lecture.get(i).get("lt_univer_mon4"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_year5")	          == null ? ""       :     lecture.get(i).get("lt_univer_year5"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_univer_mon5")	          == null ? ""       :     lecture.get(i).get("lt_univer_mon5"))              +"\",");

				result.append("\"" + ( lecture.get(i).get("lt_carrier_year1")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year1"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon1")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon1"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year2")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year2"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon2")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon2"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year3")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year3"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon3")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon3"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year4")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year4"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon4")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon4"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year5")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year5"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon5")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon5"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year6")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year6"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon6")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon6"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year7")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year7"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon7")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon7"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year8")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year8"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon8")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon8"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year9")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year9"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon9")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon9"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_year10")	          == null ? ""       :     lecture.get(i).get("lt_carrier_year10"))              +"\",");
				result.append("\"" + ( lecture.get(i).get("lt_carrier_mon10")	          == null ? ""       :     lecture.get(i).get("lt_carrier_mon10"))              +"\"");
				
				result.append("]}");
			}
			
			result.append("]}");
			
			request.setAttribute("result",result);		
			return (mapping.findForward("ajaxout"));			
	}	
	
	public ActionForward insert_lecture(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	HttpSession session=request.getSession();
    	lectureDao dao = new lectureDao();     	
    	
		map.put("updater",       		  session.getAttribute("G_NAME"));
		map.put("lt_name",      		  request.getParameter("lt_name"));
//		JUMIN_DEL
//	    map.put("lt_pers_no",   		  request.getParameter("lt_pers_no")); 
	    map.put("lt_pers_birth",   		  request.getParameter("lt_pers_birth")); 
	  	map.put("lt_hp",      		      request.getParameter("lt_hp"));
	    map.put("lt_image",     		  request.getParameter("lt_image"));   	
	    map.put("lt_major",     		  request.getParameter("lt_major"));
	    map.put("lt_post",                request.getParameter("lt_post").replace("-",""	));       	
		map.put("lt_add",      		      request.getParameter("lt_add"));
		map.put("lt_comp_name",           request.getParameter("lt_comp_name"));		
	    map.put("lt_comp_post",           request.getParameter("lt_comp_post").replace("-",""	));  
	    map.put("lt_comp_add",    	      request.getParameter("lt_comp_add")); 
	  	map.put("lt_duty",      	      request.getParameter("lt_duty"));
	    map.put("lt_comp_tel",     	      request.getParameter("lt_comp_tel"));   	
	    map.put("lt_email",     		  request.getParameter("lt_email"));
    	map.put("lt_code",     		      request.getParameter("lt_code"));    	
		map.put("lt_kind",      		  request.getParameter("lt_kind"));
		map.put("lt_area",      		  request.getParameter("lt_area"));			
		map.put("lt_univer1",      		  request.getParameter("lt_univer1"));
		map.put("lt_univer2",      		  request.getParameter("lt_univer2"));
		map.put("lt_univer3",      		  request.getParameter("lt_univer3"));
		map.put("lt_univer4",       	  request.getParameter("lt_univer4"));
		map.put("lt_univer5",      		  request.getParameter("lt_univer5"));
	    map.put("lt_carrier1",      	  request.getParameter("lt_carrier1")); 
	    map.put("lt_carrier2",     		  request.getParameter("lt_carrier2")); 
	    map.put("lt_carrier3",     		  request.getParameter("lt_carrier3")); 
	    map.put("lt_carrier4",     		  request.getParameter("lt_carrier4")); 
	    map.put("lt_carrier5",     	      request.getParameter("lt_carrier5")); 
	    map.put("lt_carrier6",     	      request.getParameter("lt_carrier6")); 
	    map.put("lt_carrier7",     	      request.getParameter("lt_carrier7")); 
	    map.put("lt_carrier8",      	  request.getParameter("lt_carrier8")); 
	    map.put("lt_carrier9",   	      request.getParameter("lt_carrier9")); 
	    map.put("lt_carrier10",     	  request.getParameter("lt_carrier10")); 
	    map.put("lt_remark",              request.getParameter("lt_remark")); 
	    map.put("lt_bankname",            request.getParameter("lt_bankname")); 
	    map.put("lt_bankReceiptName",     request.getParameter("lt_bankReceiptName")); 
	    map.put("lt_bankaccount",         request.getParameter("lt_bankaccount"));
	    
	    
	    map.put("lt_major_chk",         request.getParameter("lt_major_chk"));
	    map.put("lt_major2",         request.getParameter("lt_major2"));
	    map.put("lt_major_chk2",         request.getParameter("lt_major_chk2"));
	    map.put("lt_major3",         request.getParameter("lt_major3"));
	    map.put("lt_major_chk3",         request.getParameter("lt_major_chk3"));
	    map.put("lt_major4",         request.getParameter("lt_major4"));
	    map.put("lt_major_chk4",         request.getParameter("lt_major_chk4"));
	    map.put("lt_major5",         request.getParameter("lt_major5"));
	    map.put("lt_major_chk5",         request.getParameter("lt_major_chk5"));
	    
		map.put("lt_univer_year1",      		  request.getParameter("lt_univer_year1"));
		map.put("lt_univer_mon1",      		  request.getParameter("lt_univer_mon1"));
		map.put("lt_univer_year2",      		  request.getParameter("lt_univer_year2"));
		map.put("lt_univer_mon2",      		  request.getParameter("lt_univer_mon2"));
		map.put("lt_univer_year3",      		  request.getParameter("lt_univer_year3"));
		map.put("lt_univer_mon3",      		  request.getParameter("lt_univer_mon3"));
		map.put("lt_univer_year4",      		  request.getParameter("lt_univer_year4"));
		map.put("lt_univer_mon4",      		  request.getParameter("lt_univer_mon4"));
		map.put("lt_univer_year5",      		  request.getParameter("lt_univer_year5"));
		map.put("lt_univer_mon5",      		  request.getParameter("lt_univer_mon5"));
	    
		map.put("lt_carrier_year1",      		  request.getParameter("lt_carrier_year1"));
		map.put("lt_carrier_mon1",      		  request.getParameter("lt_carrier_mon1"));
		map.put("lt_carrier_year2",      		  request.getParameter("lt_carrier_year2"));
		map.put("lt_carrier_mon2",      		  request.getParameter("lt_carrier_mon2"));
		map.put("lt_carrier_year3",      		  request.getParameter("lt_carrier_year3"));
		map.put("lt_carrier_mon3",      		  request.getParameter("lt_carrier_mon3"));
		map.put("lt_carrier_year4",      		  request.getParameter("lt_carrier_year4"));
		map.put("lt_carrier_mon4",      		  request.getParameter("lt_carrier_mon4"));
		map.put("lt_carrier_year5",      		  request.getParameter("lt_carrier_year5"));
		map.put("lt_carrier_mon5",      		  request.getParameter("lt_carrier_mon5"));
		map.put("lt_carrier_year6",      		  request.getParameter("lt_carrier_year6"));
		map.put("lt_carrier_mon6",      		  request.getParameter("lt_carrier_mon6"));
		map.put("lt_carrier_year7",      		  request.getParameter("lt_carrier_year7"));
		map.put("lt_carrier_mon7",      		  request.getParameter("lt_carrier_mon7"));
		map.put("lt_carrier_year8",      		  request.getParameter("lt_carrier_year8"));
		map.put("lt_carrier_mon8",      		  request.getParameter("lt_carrier_mon8"));
		map.put("lt_carrier_year9",      		  request.getParameter("lt_carrier_year9"));
		map.put("lt_carrier_mon9",      		  request.getParameter("lt_carrier_mon9"));
		map.put("lt_carrier_year10",      		  request.getParameter("lt_carrier_year10"));
		map.put("lt_carrier_mon10",      		  request.getParameter("lt_carrier_mon10"));
		
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertlecture(map);	
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);
		return mapping.findForward("insert_ok");	
	} 
	
public ActionForward update_lecture(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	HttpSession session=request.getSession();
    	lectureDao dao = new lectureDao();
    	
		map.put("updater",       		  session.getAttribute("G_NAME"));
		map.put("lt_name",      		  request.getParameter("lt_name"));
//		JUMIN_DEL
//	    map.put("lt_pers_no",   		  request.getParameter("lt_pers_no")); 
	    map.put("lt_pers_birth",   		  request.getParameter("lt_pers_birth")); 
	  	map.put("lt_hp",      		      request.getParameter("lt_hp"));
	    map.put("lt_image",     		  request.getParameter("lt_image"));   	
	    map.put("lt_major",     		  request.getParameter("lt_major"));
	    map.put("lt_post",                request.getParameter("lt_post").replace("-",""	));    
		map.put("lt_add",      		      request.getParameter("lt_add"));
		map.put("lt_comp_name",           request.getParameter("lt_comp_name"));
	    map.put("lt_comp_post",           request.getParameter("lt_comp_post").replace("-",""	));  		
	    map.put("lt_comp_add",    	      request.getParameter("lt_comp_add")); 
	  	map.put("lt_duty",      	      request.getParameter("lt_duty"));
	    map.put("lt_comp_tel",     	      request.getParameter("lt_comp_tel"));   	
	    map.put("lt_email",     		  request.getParameter("lt_email"));
    	map.put("lt_code",     		      request.getParameter("lt_code"));    	
		map.put("lt_kind",      		  request.getParameter("lt_kind"));
		map.put("lt_area",      		  request.getParameter("lt_area"));			
		map.put("lt_univer1",      		  request.getParameter("lt_univer1"));
		map.put("lt_univer2",      		  request.getParameter("lt_univer2"));
		map.put("lt_univer3",      		  request.getParameter("lt_univer3"));
		map.put("lt_univer4",       	  request.getParameter("lt_univer4"));
		map.put("lt_univer5",      		  request.getParameter("lt_univer5"));
	    map.put("lt_carrier1",      	  request.getParameter("lt_carrier1")); 
	    map.put("lt_carrier2",     		  request.getParameter("lt_carrier2")); 
	    map.put("lt_carrier3",     		  request.getParameter("lt_carrier3")); 
	    map.put("lt_carrier4",     		  request.getParameter("lt_carrier4")); 
	    map.put("lt_carrier5",     	      request.getParameter("lt_carrier5")); 
	    map.put("lt_carrier6",     	      request.getParameter("lt_carrier6")); 
	    map.put("lt_carrier7",     	      request.getParameter("lt_carrier7")); 
	    map.put("lt_carrier8",      	  request.getParameter("lt_carrier8")); 
	    map.put("lt_carrier9",   	      request.getParameter("lt_carrier9")); 
	    map.put("lt_carrier10",     	  request.getParameter("lt_carrier10")); 
	    map.put("lt_remark",              request.getParameter("lt_remark")); 
	    map.put("lt_bankname",            request.getParameter("lt_bankname")); 
	    map.put("lt_bankReceiptName",     request.getParameter("lt_bankReceiptName")); 
	    map.put("lt_bankaccount",         request.getParameter("lt_bankaccount"));
	    
	    map.put("lt_major_chk",         request.getParameter("lt_major_chk"));
	    map.put("lt_major2",         request.getParameter("lt_major2"));
	    map.put("lt_major_chk2",         request.getParameter("lt_major_chk2"));
	    map.put("lt_major3",         request.getParameter("lt_major3"));
	    map.put("lt_major_chk3",         request.getParameter("lt_major_chk3"));
	    map.put("lt_major4",         request.getParameter("lt_major4"));
	    map.put("lt_major_chk4",         request.getParameter("lt_major_chk4"));
	    map.put("lt_major5",         request.getParameter("lt_major5"));
	    map.put("lt_major_chk5",         request.getParameter("lt_major_chk5"));
	    
		map.put("lt_univer_year1",      		  request.getParameter("lt_univer_year1"));
		map.put("lt_univer_mon1",      		  request.getParameter("lt_univer_mon1"));
		map.put("lt_univer_year2",      		  request.getParameter("lt_univer_year2"));
		map.put("lt_univer_mon2",      		  request.getParameter("lt_univer_mon2"));
		map.put("lt_univer_year3",      		  request.getParameter("lt_univer_year3"));
		map.put("lt_univer_mon3",      		  request.getParameter("lt_univer_mon3"));
		map.put("lt_univer_year4",      		  request.getParameter("lt_univer_year4"));
		map.put("lt_univer_mon4",      		  request.getParameter("lt_univer_mon4"));
		map.put("lt_univer_year5",      		  request.getParameter("lt_univer_year5"));
		map.put("lt_univer_mon5",      		  request.getParameter("lt_univer_mon5"));
	    
		map.put("lt_carrier_year1",      		  request.getParameter("lt_carrier_year1"));
		map.put("lt_carrier_mon1",      		  request.getParameter("lt_carrier_mon1"));
		map.put("lt_carrier_year2",      		  request.getParameter("lt_carrier_year2"));
		map.put("lt_carrier_mon2",      		  request.getParameter("lt_carrier_mon2"));
		map.put("lt_carrier_year3",      		  request.getParameter("lt_carrier_year3"));
		map.put("lt_carrier_mon3",      		  request.getParameter("lt_carrier_mon3"));
		map.put("lt_carrier_year4",      		  request.getParameter("lt_carrier_year4"));
		map.put("lt_carrier_mon4",      		  request.getParameter("lt_carrier_mon4"));
		map.put("lt_carrier_year5",      		  request.getParameter("lt_carrier_year5"));
		map.put("lt_carrier_mon5",      		  request.getParameter("lt_carrier_mon5"));
		map.put("lt_carrier_year6",      		  request.getParameter("lt_carrier_year6"));
		map.put("lt_carrier_mon6",      		  request.getParameter("lt_carrier_mon6"));
		map.put("lt_carrier_year7",      		  request.getParameter("lt_carrier_year7"));
		map.put("lt_carrier_mon7",      		  request.getParameter("lt_carrier_mon7"));
		map.put("lt_carrier_year8",      		  request.getParameter("lt_carrier_year8"));
		map.put("lt_carrier_mon8",      		  request.getParameter("lt_carrier_mon8"));
		map.put("lt_carrier_year9",      		  request.getParameter("lt_carrier_year9"));
		map.put("lt_carrier_mon9",      		  request.getParameter("lt_carrier_mon9"));
		map.put("lt_carrier_year10",      		  request.getParameter("lt_carrier_year10"));
		map.put("lt_carrier_mon10",      		  request.getParameter("lt_carrier_mon10"));
	    
	    
	    {
		    Map map1 = new HashMap();
			map1.put("lt_code",     		      request.getParameter("lt_code"));
			map1.put("nstart", 0);
			map1.put("nend", 1000000);
			List<Map> lecture = dao.lecture(map1);
				
			if(lecture!=null && lecture.size()>0){
				if(!StringUtil.NVL(lecture.get(0).get("lt_hp")).equals(request.getParameter("lt_hp"))) map.put("lt_hp_upok","Y"); 
				if(!StringUtil.NVL(lecture.get(0).get("lt_email")).equals(request.getParameter("lt_email"))) map.put("lt_email_upok","Y"); 
			}
	    }
		
	    String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.update_lecture(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);				
		return mapping.findForward("update_ok");	
	} 

public ActionForward delete_lecture(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	
	Map map = new HashMap();
	lectureDao dao = new lectureDao();
	
	map.put("lt_code",     		      request.getParameter("lt_code"));    	
	
	String errMsg="";
	if(!isTokenValid(request)){
		errMsg="이미 삭제했습니다.";	
	}else{
		int result = dao.delete_lecture(map);
		resetToken(request);
		errMsg="삭제했습니다.";
	}	
	
	request.setAttribute("errMsg", errMsg);				
	return mapping.findForward("update_ok");	
} 

	/* 우편번호 검색 */
	public ActionForward postSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(request.getParameter("sel")!= null){							   
			sel=request.getParameter("sel");
		}
		return (mapping.findForward("postSearchr"));		
	}
	
	/* 우편번호 검색 */
	public ActionForward postSearch1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(request.getParameter("sel")!= null){							   
			sel=request.getParameter("sel");
		}
		return (mapping.findForward("postSearchr1"));		
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
	     
	       //keyword= StringUtil.removeWhiteSpace(URLDecoder.decode(request.getParameter("keyword"), "UTF-8"));
	       keyword= URLDecoder.decode(request.getParameter("keyword"), "UTF-8");
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
	            
	            System.out.println("-------->"+successYN+"     "+errMsg);
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
	     
	       //keyword= StringUtil.removeWhiteSpace(URLDecoder.decode(request.getParameter("keyword"), "UTF-8"));
	       keyword= URLDecoder.decode(request.getParameter("keyword"), "UTF-8");
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
	            
	            System.out.println("-------->"+successYN+"     "+errMsg);
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
	
	/* 주민번호 Forward */
	public ActionForward lecturepers_check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		String lta_param = "";  
		Map map = new HashMap();
		lectureDao dao = new lectureDao();
		
		if(request.getParameter("lta_name")!=null){
			String lt_name = URLDecoder.decode(request.getParameter("lta_name"),"UTF-8");
			request.setAttribute("lt_name",           lt_name);
			
			lta_param += "&lt_name="+request.getParameter("lta_name");
			map.put("lt_name",           lt_name);
		}
		
//		JUMIN_DEL
//		if(request.getParameter("lta_pers_no")!=null){				
//			request.setAttribute("lt_pers_no",        request.getParameter("lta_pers_no"));}
		
		if(request.getParameter("lta_pers_birth")!=null){				
			request.setAttribute("lt_pers_birth",        request.getParameter("lta_pers_birth"));
			
			lta_param += "&lt_pers_birth="+request.getParameter("lta_pers_birth");
			map.put("lt_pers_birth",              request.getParameter("lta_pers_birth"));
		}
		if(request.getParameter("lta_kind")!=null){
			request.setAttribute("lt_kind",           request.getParameter("lta_kind"));
			
			lta_param += "&lt_kind="+request.getParameter("lta_kind");
			map.put("lt_kind",                 request.getParameter("lta_kind"));
		}

		int ncount = 0;
		List<Map> lecturecnt=dao.lecturecnt(map);
		ncount = ((Integer)(lecturecnt.get(0).get("cnt"))).intValue();
		
		request.setAttribute("param", lta_param);
		request.setAttribute("ncount", ncount);
		request.setAttribute("url", "lecture");
		
		return (mapping.findForward("pers_check"));
			
	}
	
	/* 강사 검색 excel Down */
	public void lectureDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Map map = new HashMap();
		Map map1 = new HashMap();
		lectureDao dao = new lectureDao(); 
		
		if(request.getParameter("lt_name")!=null){
			String lt_name = URLDecoder.decode(request.getParameter("lt_name"),"UTF-8");
			map.put("lt_name",           lt_name);}
//		JUMIN_DEL
//		if(request.getParameter("lt_pers_no")!=null){				
//			map.put("lt_pers_no",              request.getParameter("lt_pers_no"));}	
		if(request.getParameter("lt_pers_birth")!=null){				
			map.put("lt_pers_birth",              request.getParameter("lt_pers_birth"));}	
		if(request.getParameter("lt_kind")!=null){	
			map.put("lt_kind",                 request.getParameter("lt_kind"));}	
		if(request.getParameter("check")!=null){
			map.put("check",          request.getParameter("check"));}		
				
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_NAME")); //유저ID
		//여기부터 개발자 변경 필요
		String name="LecturerRegi"; //프로그램명
		String s_name="강사 검색"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"lt_code", "lt_name",
//				JUMIN_DEL
//				"lt_pers_no",
				"lt_pers_birth",
				"lt_major_all", "lt_major_chk_all", "lt_image", "lt_post", "lt_add", "lt_hp", "lt_email", "lt_comp_name", "lt_duty", "lt_comp_post", "lt_comp_add", "lt_comp_tel", "lt_area",
				
//				"lt_univer1" ,"lt_univer2", "lt_univer3", "lt_univer4", "lt_univer5",
//				"lt_carrier1", "lt_carrier2", "lt_carrier3", "lt_carrier4", "lt_carrier5",
//				"lt_carrier6", "lt_carrier7", "lt_carrier8", "lt_carrier9", "lt_carrier10",
				
				"lt_univer_year1", "lt_univer_mon1", "lt_univer1", "lt_univer_year2", "lt_univer_mon2", "lt_univer2", "lt_univer_year3", "lt_univer_mon3", "lt_univer3", "lt_univer_year4", "lt_univer_mon4", "lt_univer4", "lt_univer_year5", "lt_univer_mon5", "lt_univer5",
				"lt_carrier_year1", "lt_carrier_mon1", "lt_carrier1", "lt_carrier_year2", "lt_carrier_mon2", "lt_carrier2", "lt_carrier_year3", "lt_carrier_mon3", "lt_carrier3", "lt_carrier_year4", "lt_carrier_mon4", "lt_carrier4", "lt_carrier_year5", "lt_carrier_mon5", "lt_carrier5",
				"lt_carrier_year6", "lt_carrier_mon6", "lt_carrier6", "lt_carrier_year7", "lt_carrier_mon7", "lt_carrier7", "lt_carrier_year8", "lt_carrier_mon8", "lt_carrier8", "lt_carrier_year9", "lt_carrier_mon9", "lt_carrier9", "lt_carrier_year10", "lt_carrier_mon10", "lt_carrier10",

				"lt_remark", "lt_bankReceiptName", "lt_bankname", "lt_bankaccount", "up_date", "updater"}; //헤더 영문
		String header_k[]={"강사순번", "강사명",
//				JUMIN_DEL
//				"주민번호",
				"생년월일",
				"과목", "과목선택", "사진", "우편번호", "주소", "핸드폰", "이메일", "근무처명", "직책", "근무처우편번호", "근무처주소", "전화번호", "지역",
				
//				"학력1", "학력2", "학력3", "학력4", "학력5",
//				"경력1", "경력2", "경력3", "경력4", "경력5",
//				"경력6", "경력7", "경력8", "경력9", "경력10",
				
				"학력1년", "학력1월", "학력1", "학력2년", "학력2월", "학력2", "학력3년", "학력3월", "학력3", "학력4년", "학력4월", "학력4", "학력5년", "학력5월", "학력5",
				"경력1년", "경력1월", "경력1", "경력2년", "경력2월", "경력2", "경력3년", "경력3월", "경력3", "경력4년", "경력4월", "경력4", "경력5년", "경력5월", "경력5",
				"경력6년", "경력6월", "경력6", "경력7년", "경력7월", "경력7", "경력8년", "경력8월", "경력8", "경력9년", "경력9월", "경력9", "경력10년", "경력10월", "경력10",
				
				"비고", "예금주", "은행명", "계좌번호", "등록자", "등록일자"}; //헤더 국문
		int c_size[]={15, 15, 15, 45, 10, 15, 15, 50, 20, 20, 50, 15, 15, 50, 15, 15,
				
//				30, 30, 30, 30, 30,
//				30, 30, 30, 30, 30,
//				30, 30, 30, 30, 30,
				
				9, 9, 30, 9, 9, 30, 9, 9, 30, 9, 9, 30, 9, 9, 30, 
				9, 9, 30, 9, 9, 30, 9, 9, 30, 9, 9, 30, 9, 9, 30, 
				9, 9, 30, 9, 9, 30, 9, 9, 30, 9, 9, 30, 9, 9, 30, 
				
				15, 15, 15, 25, 15, 15};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//맵에 조건을 넣는다.
		map.put("nstart", 0);
		map.put("nend", 1000000);
				
		//여기까지 변경
		List<Map> lecture = dao.lecture(map,true);		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(lecture, filename, s_name,header_e,header_k,c_size);
			
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
	
	/* 파일 첨부 */
	public ActionForward picture(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
	return (mapping.findForward("picture_up"));			
	}
	
	/* 파일 첨부 upload */
	public ActionForward picture_up(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		response.setCharacterEncoding("UTF-8");
		
		//String saveFolder="D:/WEB/KDA_VER3/upload/picture";		
		String saveFolder = request.getSession().getServletContext().getRealPath("/upload/picture");
		System.out.println("saveFolder====>"+saveFolder);
		
		String encType="EUC-KR";
		int maxSize=10*1024*1024;
		File f=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType,new DefaultFileRenamePolicy());
		String filename="";
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				filename=multi.getFilesystemName(name);
				System.out.println("name===>"+name+"    filename====>"+filename);
				f=new File(saveFolder+"/"+filename);
				int n=+1;
				String msg =(n>0)?"등록 성공\\n\\n(등록하신 사진은 저장을 하시는 경우 최종 반영 됩니다.)":"등록 실패";
				String loc="javascript:history.back()";
				request.setAttribute("msg",msg);				
				request.setAttribute("name", filename);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}						
		return mapping.findForward("picture_up");
	}
	
}