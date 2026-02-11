package com.ant.document.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ant.common.util.CommonUtil;
import com.ant.common.util.EmailSender;
import com.ant.common.util.StringUtil;
import com.ant.document.dao.documentDao;
import com.ant.educationexam.dao.educationDao;
import com.ant.member.basic.dao.basicDao;
import com.ant.member.dues.dao.memberDuesDao;
import com.ant.member.state.dao.memberStateDao;
import com.oreilly.servlet.MultipartRequest;

public class documentAction extends DispatchAction {
	protected static Logger logger = Logger.getRootLogger();
	protected static String sel="";
	protected static String tnm="";
	protected static String keyword=""; 
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
	
	/*문서발송대장*/	
	public ActionForward documentSend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("documentSendList"));
	}
	
	public ActionForward documentSendList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("receive1")!=null){			
			String receive1 = URLDecoder.decode(request.getParameter("receive1"),"UTF-8");
			map.put("receive1",        receive1);}		
		if(request.getParameter("title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("title1"),"UTF-8");
			map.put("title1",          title1);}	
		if(request.getParameter("attach1")!=null){			
			String attach1 = URLDecoder.decode(request.getParameter("attach1"),"UTF-8");
			map.put("attach1",         attach1);}	
		if(request.getParameter("receipt_dt1")!=null){
			map.put("receipt_dt1",      request.getParameter("receipt_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}
		if(request.getParameter("center1")!=null){	 
			map.put("center1",          request.getParameter("center1"));}		  	
		if(request.getParameter("remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("remark1"),"UTF-8");
			map.put("remark1",        remark1);}
		if(request.getParameter("remark2")!=null){			
			String remark2 = URLDecoder.decode(request.getParameter("remark2"),"UTF-8");
			map.put("remark2",        remark2);}
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
		
		List<Map> documentcount=dao.documentsendcnt(map);
		ncount = ((Integer)(documentcount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> documentsend = dao.documentsend(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""   +	npage	 +"\",");		
		result.append("\"total\":\""   +    ntotpage +"\",");
		result.append("\"records\":\"" +	ncount	 +"\",");
		result.append("\"rows\":[");	
		
		
		for(int i=0; i<documentsend.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( documentsend.get(i).get("receipt_dt")	== null ? "" : documentsend.get(i).get("receipt_dt"))  +"\",");
			result.append("\"" + ( documentsend.get(i).get("detcodename")	== null ? "" : documentsend.get(i).get("detcodename")) +"\",");
			result.append("\"" + ( documentsend.get(i).get("detcode")	    == null ? "" : documentsend.get(i).get("detcode") )    +"\",");
			result.append("\"" + ( documentsend.get(i).get("year_seq")   	== null ? "" : documentsend.get(i).get("year_seq") )   +"\",");			
			result.append("\"" + ( documentsend.get(i).get("receive")		== null ? "" : URLEncoder.encode(documentsend.get(i).get("receive").toString(), "UTF-8") )    +"\",");
			result.append("\"" + ( documentsend.get(i).get("title")		    == null ? "" : URLEncoder.encode(documentsend.get(i).get("title").toString(), "UTF-8") )      +"\",");
			result.append("\"" + ( documentsend.get(i).get("attach")	    == null ? "" : URLEncoder.encode(documentsend.get(i).get("attach").toString(), "UTF-8") )     +"\",");
			result.append("\"" + ( documentsend.get(i).get("remark")	    == null ? "" : URLEncoder.encode(documentsend.get(i).get("remark").toString(), "UTF-8") )     +"\",");
			result.append("\"" + ( documentsend.get(i).get("remark2")	    == null ? "" : URLEncoder.encode(documentsend.get(i).get("remark2").toString(), "UTF-8") )    +"\",");
			result.append("\"" + ( documentsend.get(i).get("register")		== null ? "" : URLEncoder.encode(documentsend.get(i).get("register").toString(), "UTF-8") )   +"\"");
			result.append("]}");			
		}	
		
		result.append("]}");
		
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	public ActionForward insert_send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
    	Map map = new HashMap();
    	documentDao dao = new documentDao();
   		
		String register1 = URLDecoder.decode(request.getParameter("g_name"),"UTF-8");
		System.out.println("register1=================>"+register1);
		System.out.println("register1=================>"+URLDecoder.decode(request.getParameter("g_name"),"UTF-8"));
		map.put("register1",      register1);
		map.put("inqday1",        request.getParameter("inqday1"));
	    map.put("receipt_dt1",    request.getParameter("receipt_dt1")); 
	  	map.put("year_seq1",      request.getParameter("year_seq1"));
	    map.put("center1",        request.getParameter("center1"));   	
	    map.put("receive1",       request.getParameter("receive1"));
    	map.put("title1",         request.getParameter("title1"));    	
		map.put("attach1",        request.getParameter("attach1"));
		map.put("remark1",        request.getParameter("remark1"));
		map.put("remark2",        request.getParameter("remark2"));

		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertdocument(map);	
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);
		return mapping.findForward("insert_ok");	
	} 
	
	public ActionForward update_send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		documentDao dao = new documentDao();		
    	Map map = new HashMap();      	
		
    	String register1 = URLDecoder.decode(request.getParameter("g_name"),"UTF-8");
    	System.out.println("register1=================>"+register1);
		System.out.println("register1=================>"+URLDecoder.decode(request.getParameter("g_name"),"UTF-8"));
		map.put("register1",      register1);
		map.put("receipt_dt1",    request.getParameter("receipt_dt1"));	
    	map.put("year_seq1",      request.getParameter("year_seq1"));
    	map.put("center1",        request.getParameter("center1"));    	
    	map.put("receive1",       request.getParameter("receive1"));
    	map.put("title1",         request.getParameter("title1"));    	
		map.put("attach1",        request.getParameter("attach1"));
		map.put("remark1",        request.getParameter("remark1"));
		map.put("remark2",        request.getParameter("remark2"));
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatedocument(map);	
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);				
		request.setAttribute("sparam", getSparam(request));				
		request.setAttribute("sparam_org", getSparamOrg(request));				
		setSparam(request);				
		return mapping.findForward("update_ok");	
	} 
	
	private String getSparam(HttpServletRequest request) throws Exception{
		String sparam = "";
		if(request.getParameter("s_receive1")!=null){			
			String receive1 = request.getParameter("s_receive1");
			sparam+="&receive1="+receive1;}
		if(request.getParameter("s_title1")!=null){			
			String title1 = request.getParameter("s_title1");
			sparam+="&title1="+  title1;}	
		if(request.getParameter("s_attach1")!=null){			
			String attach1 = request.getParameter("s_attach1");
			sparam+="&attach1="+ attach1;}	
		if(request.getParameter("s_receipt_dt1")!=null){
			sparam+="&receipt_dt1"+      request.getParameter("s_receipt_dt1");}	
		if(request.getParameter("s_inqday1")!=null){
			sparam+="&inqday1="+  request.getParameter("s_inqday1");}	
		if(request.getParameter("s_inqday2")!=null){	
			sparam+="&inqday2="+ request.getParameter("s_inqday2");}
		if(request.getParameter("s_center1")!=null){	 
			sparam+="&center1="+  request.getParameter("s_center1");}		  	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = request.getParameter("s_remark1");
			sparam+="&remark1="+remark1;}
		if(request.getParameter("s_remark2")!=null){			
			String remark2 = request.getParameter("s_remark2");
			sparam+="&remark2="+remark2;}
		
		return sparam;
	}
	
	private String getSparamOrg(HttpServletRequest request) throws Exception{
		String sparam = "";
		if(request.getParameter("s_receive1")!=null){			
			String receive1 = URLEncoder.encode(request.getParameter("s_receive1"),"utf-8");
			sparam+="&s_receive1="+receive1;}
		if(request.getParameter("s_title1")!=null){			
			String title1 = URLEncoder.encode(request.getParameter("s_title1"),"utf-8");
			sparam+="&s_title1="+  title1;}	
		if(request.getParameter("s_attach1")!=null){			
			String attach1 = URLEncoder.encode(request.getParameter("s_attach1"),"utf-8");
			sparam+="&s_attach1="+ attach1;}	
		if(request.getParameter("s_receipt_dt1")!=null){
			sparam+="&s_receipt_dt1"+      request.getParameter("s_receipt_dt1");}	
		if(request.getParameter("s_inqday1")!=null){
			sparam+="&s_inqday1="+  request.getParameter("s_inqday1");}	
		if(request.getParameter("s_inqday2")!=null){	
			sparam+="&s_inqday2="+ request.getParameter("s_inqday2");}
		if(request.getParameter("s_center1")!=null){	 
			sparam+="&s_center1="+  request.getParameter("s_center1");}		  	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = URLEncoder.encode(request.getParameter("s_remark1"),"utf-8");
			sparam+="&s_remark1="+remark1;}
		if(request.getParameter("s_remark2")!=null){			
			String remark2 = URLEncoder.encode(request.getParameter("s_remark2"),"utf-8");
			sparam+="&s_remark2="+remark2;}
		
		return sparam;
	}
	
	private void setSparam(HttpServletRequest request) throws Exception{
		if(request.getParameter("s_receive1")!=null){			
			String receive1 = URLDecoder.decode(request.getParameter("s_receive1"),"utf-8");
			request.setAttribute("receive1", receive1);}
		if(request.getParameter("s_title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("s_title1"),"utf-8");
			request.setAttribute("title1",   title1);}	
		if(request.getParameter("s_attach1")!=null){			
			String attach1 = URLDecoder.decode(request.getParameter("s_attach1"),"utf-8");
			request.setAttribute("attach1",  attach1);}	
		if(request.getParameter("s_receipt_dt1")!=null){
			request.setAttribute("receipt_dt1",request.getParameter("s_receipt_dt1"));}	
		if(request.getParameter("s_inqday1")!=null){
			request.setAttribute("inqday1",   request.getParameter("s_inqday1"));}	
		if(request.getParameter("s_inqday2")!=null){	
			request.setAttribute("inqday2",  request.getParameter("s_inqday2"));}
		if(request.getParameter("s_center1")!=null){	 
			request.setAttribute("center1",   request.getParameter("s_center1"));}		  	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("s_remark1"),"utf-8");
			request.setAttribute("remark1", remark1);}
		if(request.getParameter("s_remark2")!=null){			
			String remark2 = URLDecoder.decode(request.getParameter("s_remark2"),"utf-8");
			request.setAttribute("remark2", remark2);}
		if(request.getParameter("s_pgno")!=null){			
			request.setAttribute("pgno",   request.getParameter("s_pgno"));}		  	
	}
	
	/* 문서발송대장 excel Down */
	public void documentsendDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		Map map1 = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("receive1")!=null){			
			String receive1 = URLDecoder.decode(request.getParameter("receive1"),"UTF-8");
			map.put("receive1",        receive1);}		
		if(request.getParameter("title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("title1"),"UTF-8");
			map.put("title1",          title1);}	
		if(request.getParameter("attach1")!=null){			
			String attach1 = URLDecoder.decode(request.getParameter("attach1"),"UTF-8");
			map.put("attach1",         attach1);}	
		if(request.getParameter("receipt_dt1")!=null){
			map.put("receipt_dt1",      request.getParameter("receipt_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}
		if(request.getParameter("center1")!=null){	 
			map.put("center1",          request.getParameter("center1"));}		
		if(request.getParameter("remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("remark1"),"UTF-8");
			map.put("remark1",        remark1);}
		if(request.getParameter("remark2")!=null){			
			String remark2 = URLDecoder.decode(request.getParameter("remark2"),"UTF-8");
			map.put("remark2",        remark2);}		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = request.getParameter("g_id"); //유저ID
		//여기부터 개발자 변경 필요
		String name="documentsend"; //프로그램명
		String s_name="문서발송대장"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"receipt_dt", "detcodename", "year_seq",  "receive", "title", "attach", "remark", "remark2", "register"}; //헤더 영문
		String header_k[]={"발송일", "부서명", "문서번호", "수신자", "제목", "첨부물", "비고", "비고2", "발송자"}; //헤더 국문
		int c_size[]={15, 15, 15, 15, 25, 15, 30, 30, 15};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> documentsenddown=dao.documentsenddown(map);
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(documentsenddown, filename, s_name,header_e,header_k,c_size);
			
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
	
	/*문서접수대장*/
	public ActionForward documentAccp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("documentAccpList"));
	}	
	
	public ActionForward documentAccpList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("send1")!=null){			
			String send1 = URLDecoder.decode(request.getParameter("send1"),"UTF-8");
			map.put("send1",           send1);}		
		if(request.getParameter("title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("title1"),"UTF-8");
			map.put("title1",          title1);}	
		if(request.getParameter("accach1")!=null){			
			String accach1 = URLDecoder.decode(request.getParameter("accach1"),"UTF-8");
			map.put("accach1",         accach1);}	
		if(request.getParameter("remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("remark1"),"UTF-8");
			map.put("remark1",         remark1);}
		if(request.getParameter("receipt_dt1")!=null){
			map.put("receipt_dt1",      request.getParameter("receipt_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}		
		if(request.getParameter("year_seq1")!=null){
			map.put("year_seq1",       request.getParameter("year_seq1"));}	
		if(request.getParameter("doc_seq1")!=null){
			String doc_seq1 = URLDecoder.decode(request.getParameter("doc_seq1"),"UTF-8");
			map.put("doc_seq1",        doc_seq1);}	
		if(request.getParameter("run_dt1")!=null){	
			map.put("run_dt1",         request.getParameter("run_dt1"));}
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
		List<Map> documentcount=dao.documentaccpcnt(map);
		ncount = ((Integer)(documentcount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> documentaccp = dao.documentaccp(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		
		for(int i=0; i<documentaccp.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( documentaccp.get(i).get("receipt_dt")	== null ? "" : documentaccp.get(i).get("receipt_dt"))  +"\",");
			result.append("\"" + ( documentaccp.get(i).get("year_seq")   	== null ? "" : documentaccp.get(i).get("year_seq") )   +"\",");
			result.append("\"" + ( documentaccp.get(i).get("send")   	    == null ? "" : URLEncoder.encode(documentaccp.get(i).get("send").toString(), "UTF-8") )       +"\",");
			result.append("\"" + ( documentaccp.get(i).get("doc_seq")   	== null ? "" : documentaccp.get(i).get("doc_seq") )    +"\",");
			result.append("\"" + ( documentaccp.get(i).get("run_dt")   	    == null ? "" : documentaccp.get(i).get("run_dt") )     +"\",");
			result.append("\"" + ( documentaccp.get(i).get("title")		    == null ? "" : URLEncoder.encode(documentaccp.get(i).get("title").toString(), "UTF-8") )      +"\",");
			result.append("\"" + ( documentaccp.get(i).get("accach")	    == null ? "" : URLEncoder.encode(documentaccp.get(i).get("accach").toString(), "UTF-8") )     +"\",");			
			result.append("\"" + ( documentaccp.get(i).get("detcodename")	== null ? "" : documentaccp.get(i).get("detcodename")) +"\",");
			result.append("\"" + ( documentaccp.get(i).get("detcode")	    == null ? "" : documentaccp.get(i).get("detcode") )    +"\",");
			//result.append("\"" + ( documentaccp.get(i).get("register")	    == null ? "" : URLEncoder.encode(documentaccp.get(i).get("register").toString(), "UTF-8") )    +"\",");
			result.append("\"" + ( documentaccp.get(i).get("remark")	    == null ? "" : URLEncoder.encode(documentaccp.get(i).get("remark").toString(), "UTF-8") )     +"\"");			
			result.append("]}");
		}	
		
		result.append("]}");
		
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	public ActionForward insert_accp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
    	Map map = new HashMap(); 
    	documentDao dao = new documentDao();
    		
		HttpSession session=request.getSession();

		map.put("receipt_dt1",    request.getParameter("receipt_dt1"));
		map.put("run_dt1",        request.getParameter("run_dt1"));
		map.put("updater",        session.getAttribute("G_NAME"));
		map.put("year_seq1",      request.getParameter("year_seq1"));
		map.put("doc_seq1",       request.getParameter("doc_seq1"));
		map.put("year_seq1",      request.getParameter("year_seq1"));
		map.put("center1",        request.getParameter("center1"));
		map.put("send1",          request.getParameter("send1"));
		map.put("title1",         request.getParameter("title1"));
		map.put("accach1",        request.getParameter("accach1"));
		map.put("register1",      request.getParameter("register1"));	
		map.put("remark1",        request.getParameter("remark1"));		
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertaccpdocument(map);	
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);		
		request.setAttribute("req", map);
		return mapping.findForward("insertaccp_ok");	
	} 
	
	public ActionForward update_accp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		documentDao dao = new documentDao();
    			
		HttpSession session=request.getSession();
				
		map.put("updater",        session.getAttribute("G_NAME"));
		map.put("receipt_dt1",    request.getParameter("receipt_dt1"));	
		map.put("year_seq1",      request.getParameter("year_seq1"));
		map.put("doc_seq1",       request.getParameter("doc_seq1"));
		map.put("year_seq1",      request.getParameter("year_seq1"));
		map.put("center1",        request.getParameter("center1"));
		map.put("run_dt1",        request.getParameter("run_dt1"));
		map.put("send1",          request.getParameter("send1"));
		map.put("title1",         request.getParameter("title1"));
		map.put("accach1",        request.getParameter("accach1"));
		map.put("register1",      request.getParameter("register1"));	
		map.put("remark1",        request.getParameter("remark1"));		
						
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updateaccpdocument(map);	
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);	
		request.setAttribute("req", map);
		request.setAttribute("sparam", getSparam_acc(request));				
		request.setAttribute("sparam_org", getSparamOrg_acc(request));				
		setSparam_acc(request);				
		return mapping.findForward("updateaccp_ok");	
	} 
	
	private String getSparam_acc(HttpServletRequest request) throws Exception{
		String sparam = "";
		if(request.getParameter("s_receipt_dt1")!=null){
			sparam+="&receipt_dt1"+      request.getParameter("s_receipt_dt1");}
		if(request.getParameter("s_inqday1")!=null){
			sparam+="&inqday1="+  request.getParameter("s_inqday1");}	
		if(request.getParameter("s_inqday2")!=null){	
			sparam+="&inqday2="+ request.getParameter("s_inqday2");}
		if(request.getParameter("s_doc_seq1")!=null){	
			sparam+="&doc_seq1="+ request.getParameter("s_doc_seq1");}
		if(request.getParameter("s_center1")!=null){	 
			sparam+="&center1="+  request.getParameter("s_center1");}		  	
		if(request.getParameter("s_run_dt1")!=null){	 
			sparam+="&run_dt1="+  request.getParameter("s_run_dt1");}		  	
		if(request.getParameter("s_send1")!=null){			
			String send1 = request.getParameter("s_send1");
			sparam+="&send1="+send1;}
		if(request.getParameter("s_title1")!=null){			
			String title1 = request.getParameter("s_title1");
			sparam+="&title1="+  title1;}	
		if(request.getParameter("s_accach1")!=null){			
			String accach1 = request.getParameter("s_accach1");
			sparam+="&accach1="+ accach1;}	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = request.getParameter("s_remark1");
			sparam+="&remark1="+remark1;}
		
		return sparam;
	}
	
	private String getSparamOrg_acc(HttpServletRequest request) throws Exception{
		String sparam = "";
		if(request.getParameter("s_receipt_dt1")!=null){
			sparam+="&s_receipt_dt1"+      request.getParameter("s_receipt_dt1");}
		if(request.getParameter("s_inqday1")!=null){
			sparam+="&s_inqday1="+  request.getParameter("s_inqday1");}	
		if(request.getParameter("s_inqday2")!=null){	
			sparam+="&s_inqday2="+ request.getParameter("s_inqday2");}
		if(request.getParameter("s_doc_seq1")!=null){	
			sparam+="&s_doc_seq1="+ URLEncoder.encode(request.getParameter("s_doc_seq1"),"utf-8");}
		if(request.getParameter("s_center1")!=null){	 
			sparam+="&s_center1="+  request.getParameter("s_center1");}		  	
		if(request.getParameter("s_run_dt1")!=null){	 
			sparam+="&s_run_dt1="+  request.getParameter("s_run_dt1");}		  	
		if(request.getParameter("s_send1")!=null){			
			String send1 = URLEncoder.encode(request.getParameter("s_send1"),"utf-8");
			sparam+="&s_send1="+send1;}
		if(request.getParameter("s_title1")!=null){			
			String title1 = URLEncoder.encode(request.getParameter("s_title1"),"utf-8");
			sparam+="&s_title1="+  title1;}	
		if(request.getParameter("s_accach1")!=null){			
			String accach1 = URLEncoder.encode(request.getParameter("s_accach1"),"utf-8");
			sparam+="&s_accach1="+ accach1;}	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = URLEncoder.encode(request.getParameter("s_remark1"),"utf-8");
			sparam+="&s_remark1="+remark1;}
		
		return sparam;
	}
	
	private void setSparam_acc(HttpServletRequest request) throws Exception{
		if(request.getParameter("s_receipt_dt1")!=null){request.setAttribute("receipt_dt1",request.getParameter("s_receipt_dt1"));}
		if(request.getParameter("s_inqday1")!=null){	request.setAttribute("inqday1",request.getParameter("s_inqday1"));}
		if(request.getParameter("s_inqday2")!=null){	request.setAttribute("inqday2",request.getParameter("s_inqday2"));}
		if(request.getParameter("s_doc_seq1")!=null){	request.setAttribute("doc_seq1",URLDecoder.decode(request.getParameter("s_doc_seq1"),"utf-8"));}
		if(request.getParameter("s_center1")!=null){	request.setAttribute("center1",request.getParameter("s_center1"));}
		if(request.getParameter("s_run_dt1")!=null){	request.setAttribute("run_dt1",request.getParameter("s_run_dt1"));}
		if(request.getParameter("s_send1")!=null){		request.setAttribute("send1",URLDecoder.decode(request.getParameter("s_send1"),"utf-8"));}
		if(request.getParameter("s_title1")!=null){		request.setAttribute("title1",URLDecoder.decode(request.getParameter("s_title1"),"utf-8"));}
		if(request.getParameter("s_accach1")!=null){	request.setAttribute("accach1",URLDecoder.decode(request.getParameter("s_accach1"),"utf-8"));}
		if(request.getParameter("s_remark1")!=null){	request.setAttribute("remark1",URLDecoder.decode(request.getParameter("s_remark1"),"utf-8"));}
		if(request.getParameter("s_pgno")!=null){			
			request.setAttribute("pgno",   request.getParameter("s_pgno"));}		  	
	}
	
	/* 문서접수대장 excel Down */
	public void documentAccpDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("send1")!=null){			
			String send1 = URLDecoder.decode(request.getParameter("send1"),"UTF-8");
			map.put("send1",           send1);}		
		if(request.getParameter("title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("title1"),"UTF-8");
			map.put("title1",          title1);}	
		if(request.getParameter("accach1")!=null){			
			String accach1 = URLDecoder.decode(request.getParameter("accach1"),"UTF-8");
			map.put("accach1",         accach1);}
		if(request.getParameter("remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("remark1"),"UTF-8");
			map.put("remark1",         remark1);}
		if(request.getParameter("receipt_dt1")!=null){
			map.put("receipt_dt1",      request.getParameter("receipt_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}		
		if(request.getParameter("year_seq1")!=null){
			map.put("year_seq1",       request.getParameter("year_seq1"));}	
		if(request.getParameter("doc_seq1")!=null){
			map.put("doc_seq1",        URLDecoder.decode(request.getParameter("doc_seq1"),"UTF-8"));}	
		if(request.getParameter("run_dt1")!=null){	
			map.put("run_dt1",         request.getParameter("run_dt1"));}
		if(request.getParameter("center1")!=null){	 
			map.put("center1",          request.getParameter("center1"));}	
				
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_ID")); //유저ID
		//여기부터 개발자 변경 필요
		String name="documentaccp"; //프로그램명
		String s_name="문서접수대장"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"receipt_dt", "year_seq", "send", "doc_seq", "run_dt", "title", "accach", "detcodename", "register", "remark"}; //헤더 영문
		String header_k[]={"접수일", "접수번호", "발신처", "문서번호", "시행일자", "제목", "첨부물", "관리부서", "담당자", "비고"}; //헤더 국문
		int c_size[]={15, 10, 15, 10, 15, 20, 25, 15, 15, 20};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> documentaccpdown=dao.documentaccpdown(map);
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(documentaccpdown, filename, s_name,header_e,header_k,c_size);
			
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
	
	/*내부 결제 공문대장*/
	public ActionForward documentInside(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("documentInsideList"));
	}
	
	public ActionForward documentInsideList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("register1")!=null){			
			String register1 = URLDecoder.decode(request.getParameter("register1"),"UTF-8");
			map.put("register1",           register1);}		
		if(request.getParameter("title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("title1"),"UTF-8");
			map.put("title1",          title1);}	
		if(request.getParameter("attach1")!=null){			
			String attach1 = URLDecoder.decode(request.getParameter("attach1"),"UTF-8");
			map.put("attach1",         attach1);}	
		if(request.getParameter("receipt_dt1")!=null){
			map.put("receipt_dt1",      request.getParameter("receipt_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}		
		if(request.getParameter("center1")!=null){	 
			map.put("center1",          request.getParameter("center1"));}	  	
		if(request.getParameter("remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("remark1"),"UTF-8");
			map.put("remark1",           remark1);}
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
		List<Map> documentcount=dao.documentinsidecnt(map);
		ncount = ((Integer)(documentcount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> documentinside = dao.documentinside(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		
		for(int i=0; i<documentinside.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( documentinside.get(i).get("receipt_dt")	== null ? "" : documentinside.get(i).get("receipt_dt"))  +"\",");
			result.append("\"" + ( documentinside.get(i).get("detcodename")	== null ? "" : documentinside.get(i).get("detcodename")) +"\",");
			result.append("\"" + ( documentinside.get(i).get("detcode")	    == null ? "" : documentinside.get(i).get("detcode") )    +"\",");
			result.append("\"" + ( documentinside.get(i).get("year_seq")   	== null ? "" : documentinside.get(i).get("year_seq") )   +"\",");		
			result.append("\"" + ( documentinside.get(i).get("title")	    == null ? "" : URLEncoder.encode(documentinside.get(i).get("title").toString(), "UTF-8") )      +"\",");
			result.append("\"" + ( documentinside.get(i).get("attach")	    == null ? "" : URLEncoder.encode(documentinside.get(i).get("attach").toString(), "UTF-8") )     +"\",");
			result.append("\"" + ( documentinside.get(i).get("remark")	    == null ? "" : URLEncoder.encode(documentinside.get(i).get("remark").toString(), "UTF-8") )     +"\",");
			result.append("\"" + ( documentinside.get(i).get("register")	== null ? "" : URLEncoder.encode(documentinside.get(i).get("register").toString(), "UTF-8") )   +"\"");		
			result.append("]}");
		}	
		
		result.append("]}");
		
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	/* 내부 결제 공문대장 excel Down */
	public void documentInsideDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		Map map1 = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("register1")!=null){			
			String register1 = URLDecoder.decode(request.getParameter("register1"),"UTF-8");
			map.put("register1",           register1);}		
		if(request.getParameter("title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("title1"),"UTF-8");
			map.put("title1",          title1);}	
		if(request.getParameter("attach1")!=null){			
			String attach1 = URLDecoder.decode(request.getParameter("attach1"),"UTF-8");
			map.put("attach1",         attach1);}	
		if(request.getParameter("receipt_dt1")!=null){
			map.put("receipt_dt1",      request.getParameter("receipt_dt1"));}	
		if(request.getParameter("inqday1")!=null){
			map.put("inqday1",          request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")!=null){	
			map.put("inqday2",         request.getParameter("inqday2"));}		
		if(request.getParameter("center1")!=null){	 
			map.put("center1",          request.getParameter("center1"));}	  
		if(request.getParameter("remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("remark1"),"UTF-8");
			map.put("remark1",           remark1);}		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = request.getParameter("g_id"); //유저ID
		//여기부터 개발자 변경 필요
		String name="documentInside"; //프로그램명
		String s_name="내부결제 공문대장"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"receipt_dt", "detcodename", "year_seq", "title", "attach", "remark", "register"}; //헤더 영문
		String header_k[]={"발송일", "부서명", "문서번호", "제목", "첨부물", "비고", "작성자"}; //헤더 국문
		int c_size[]={15, 10, 15, 25, 25, 25, 15};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> documentinsidedown=dao.documentinsidedown(map);
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(documentinsidedown, filename, s_name,header_e,header_k,c_size);
			
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
	
	public ActionForward insert_inside(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
				
    	Map map = new HashMap();
    	documentDao dao = new documentDao();
   		
    	HttpSession session=request.getSession();
		
		map.put("updater",        session.getAttribute("G_NAME"));
		map.put("inqday1",        request.getParameter("inqday1"));
	    map.put("receipt_dt1",    request.getParameter("receipt_dt1"));   	
		map.put("year_seq1",      request.getParameter("year_seq1"));
	    map.put("center1",        request.getParameter("center1")); 	   
    	map.put("title1",         request.getParameter("title1"));    	
		map.put("attach1",        request.getParameter("attach1"));
		map.put("remark1",        request.getParameter("remark1"));	
		map.put("register1",      request.getParameter("register1"));	
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertinsidedocument(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);			
		return mapping.findForward("insertinside_ok");	
	} 
	
	public ActionForward update_inside(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		documentDao dao = new documentDao();
    	Map map = new HashMap();      	
		
    	map.put("updater",        session.getAttribute("G_NAME"));
		map.put("receipt_dt1",    request.getParameter("receipt_dt1"));	
    	map.put("year_seq1",      request.getParameter("year_seq1"));
    	map.put("center1",        request.getParameter("center1"));
    	map.put("title1",         request.getParameter("title1"));    	
		map.put("attach1",        request.getParameter("attach1"));
		map.put("remark1",        request.getParameter("remark1"));
		map.put("register1",      request.getParameter("register1"));
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updateinsidedocument(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);					
		request.setAttribute("sparam", getSparam_inn(request));				
		request.setAttribute("sparam_org", getSparamOrg_inn(request));				
		setSparam_inn(request);				
		return mapping.findForward("updateinside_ok");	
	} 
	
	private String getSparam_inn(HttpServletRequest request) throws Exception{
		String sparam = "";
		if(request.getParameter("s_register1")!=null){			
			String register1 = request.getParameter("s_register1");
			sparam+="&register1="+register1;}
		if(request.getParameter("s_title1")!=null){			
			String title1 = request.getParameter("s_title1");
			sparam+="&title1="+  title1;}	
		if(request.getParameter("s_attach1")!=null){			
			String attach1 = request.getParameter("s_attach1");
			sparam+="&attach1="+ attach1;}	
		if(request.getParameter("s_receipt_dt1")!=null){
			sparam+="&receipt_dt1"+      request.getParameter("s_receipt_dt1");}	
		if(request.getParameter("s_inqday1")!=null){
			sparam+="&inqday1="+  request.getParameter("s_inqday1");}	
		if(request.getParameter("s_inqday2")!=null){	
			sparam+="&inqday2="+ request.getParameter("s_inqday2");}
		if(request.getParameter("s_center1")!=null){	 
			sparam+="&center1="+  request.getParameter("s_center1");}		  	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = request.getParameter("s_remark1");
			sparam+="&remark1="+remark1;}
		
		return sparam;
	}
	
	private String getSparamOrg_inn(HttpServletRequest request) throws Exception{
		String sparam = "";
		if(request.getParameter("s_register1")!=null){			
			String register1 = URLEncoder.encode(request.getParameter("s_register1"),"utf-8");
			sparam+="&s_register1="+register1;}
		if(request.getParameter("s_title1")!=null){			
			String title1 = URLEncoder.encode(request.getParameter("s_title1"),"utf-8");
			sparam+="&s_title1="+  title1;}	
		if(request.getParameter("s_attach1")!=null){			
			String attach1 = URLEncoder.encode(request.getParameter("s_attach1"),"utf-8");
			sparam+="&s_attach1="+ attach1;}	
		if(request.getParameter("s_receipt_dt1")!=null){
			sparam+="&s_receipt_dt1"+      request.getParameter("s_receipt_dt1");}	
		if(request.getParameter("s_inqday1")!=null){
			sparam+="&s_inqday1="+  request.getParameter("s_inqday1");}	
		if(request.getParameter("s_inqday2")!=null){	
			sparam+="&s_inqday2="+ request.getParameter("s_inqday2");}
		if(request.getParameter("s_center1")!=null){	 
			sparam+="&s_center1="+  request.getParameter("s_center1");}		  	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = URLEncoder.encode(request.getParameter("s_remark1"),"utf-8");
			sparam+="&s_remark1="+remark1;}
		
		return sparam;
	}
	
	private void setSparam_inn(HttpServletRequest request) throws Exception{
		if(request.getParameter("s_register1")!=null){			
			String register1 = URLDecoder.decode(request.getParameter("s_register1"),"utf-8");
			request.setAttribute("register1", register1);}
		if(request.getParameter("s_title1")!=null){			
			String title1 = URLDecoder.decode(request.getParameter("s_title1"),"utf-8");
			request.setAttribute("title1",   title1);}	
		if(request.getParameter("s_attach1")!=null){			
			String attach1 = URLDecoder.decode(request.getParameter("s_attach1"),"utf-8");
			request.setAttribute("attach1",  attach1);}	
		if(request.getParameter("s_receipt_dt1")!=null){
			request.setAttribute("receipt_dt1",request.getParameter("s_receipt_dt1"));}	
		if(request.getParameter("s_inqday1")!=null){
			request.setAttribute("inqday1",   request.getParameter("s_inqday1"));}	
		if(request.getParameter("s_inqday2")!=null){	
			request.setAttribute("inqday2",  request.getParameter("s_inqday2"));}
		if(request.getParameter("s_center1")!=null){	 
			request.setAttribute("center1",   request.getParameter("s_center1"));}		  	
		if(request.getParameter("s_remark1")!=null){			
			String remark1 = URLDecoder.decode(request.getParameter("s_remark1"),"utf-8");
			request.setAttribute("remark1", remark1);}
		if(request.getParameter("s_pgno")!=null){			
			request.setAttribute("pgno",   request.getParameter("s_pgno"));}		  	
	}
	

	/* 증서발급현황*/	
	public ActionForward documentreport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("documentreportList"));
	}
	
	public ActionForward documentreportList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("yyyy")            !=null){	 
			map.put("yyyy",                          request.getParameter("yyyy"));}
		if(request.getParameter("center")          !=null){
			map.put("center",                        request.getParameter("center"));}
		if(request.getParameter("doc_kind")        !=null){	 
			map.put("doc_kind",                      request.getParameter("doc_kind"));}
		if(request.getParameter("inqday1")         !=null){
			map.put("inqday1",                       request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")         !=null){	
			map.put("inqday2",                       request.getParameter("inqday2"));}		

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
		
		List<Map> docprintcnt=dao.docprintcnt(map);
		ncount = ((Integer)(docprintcnt.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> docprint = dao.docprint(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");		
		
		for(int i=0; i<docprint.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( docprint.get(i).get("printing_date")	    == null ? "" : docprint.get(i).get("printing_date"))    +"\",");
			result.append("\"" + ( docprint.get(i).get("yyyy")	            == null ? "" : docprint.get(i).get("yyyy"))             +"\",");
			result.append("\"" + ( docprint.get(i).get("yyyy_seq")       	== null ? "" : docprint.get(i).get("yyyy_seq") )        +"\",");
			result.append("\"" + ( docprint.get(i).get("operatier_id")	    == null ? "" : docprint.get(i).get("operatier_id"))     +"\",");
			result.append("\"" + ( docprint.get(i).get("center_name")	    == null ? "" : docprint.get(i).get("center_name") )     +"\",");
			result.append("\"" + ( docprint.get(i).get("center")	    	== null ? "" : docprint.get(i).get("center") )          +"\",");
			result.append("\"" + ( docprint.get(i).get("doc_kind_name")		== null ? "" : docprint.get(i).get("doc_kind_name") )   +"\",");
			result.append("\"" + ( docprint.get(i).get("doc_kind")		    == null ? "" : docprint.get(i).get("doc_kind") )        +"\",");
			result.append("\"" + ( docprint.get(i).get("reclaimant")	    == null ? "" : URLEncoder.encode(docprint.get(i).get("reclaimant").toString(), "UTF-8") )      +"\",");
//			JUMIN_DEL
//			result.append("\"" + ( docprint.get(i).get("reclaimant_no")	    == null ? "" : docprint.get(i).get("reclaimant_no") )   +"\",");
			result.append("\"" + ( docprint.get(i).get("reclaimant_birth")	    == null ? "" : docprint.get(i).get("reclaimant_birth") )   +"\",");
			result.append("\"" + ( docprint.get(i).get("reclaimant_add")    == null ? "" : URLEncoder.encode(docprint.get(i).get("reclaimant_add").toString(), "UTF-8") )  +"\",");
			result.append("\"" + ( docprint.get(i).get("printing_cnt")	    == null ? "" : docprint.get(i).get("printing_cnt") )    +"\",");
			result.append("\"" + ( docprint.get(i).get("remark")	        == null ? "" : URLEncoder.encode(docprint.get(i).get("remark").toString(), "UTF-8") )          +"\"");
			result.append("]}");
		}	
		
		result.append("]}");
		
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}
	
	/* 증서발급현황 excel Down */
	public void documentreportDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Map map = new HashMap();
		Map map1 = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("yyyy")            !=null){	 
			map.put("yyyy",                          request.getParameter("yyyy"));}
		if(request.getParameter("center")          !=null){
			map.put("center",                        request.getParameter("center"));}
		if(request.getParameter("doc_kind")        !=null){	 
			map.put("doc_kind",                      request.getParameter("doc_kind"));}	
		if(request.getParameter("inqday1")         !=null){
			map.put("inqday1",                       request.getParameter("inqday1"));}	
		if(request.getParameter("inqday2")         !=null){	
			map.put("inqday2",                       request.getParameter("inqday2"));}		

		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_ID")); //유저ID
		//여기부터 개발자 변경 필요
		String name="docprintreport"; //프로그램명
		String s_name="증서발급현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"printing_date", "yyyy", "yyyy_seq", "operatier_id", "center_name",  "doc_kind_name", "reclaimant",
//				"reclaimant_no",
				"reclaimant_birth",
				"reclaimant_add", "printing_cnt", "remark"}; //헤더 영문
		String header_k[]={"발급년월일", "발급년도", "발급번호", "발급자", "관리부서", "문서종류", "요청자",
//				JUMIN_DEL
//				"요청자No",
				"생년월일",
				"요청자주소", "발급건수", "비고"}; //헤더 국문
		int c_size[]={15, 10, 10, 10, 15, 20, 10, 10, 40, 10, 30};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> docprintdown=dao.docprintdown(map);
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(docprintdown, filename, s_name,header_e,header_k,c_size);
			
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
		
	/*기타 발송자*/	
	public ActionForward dmsendData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("dmsendList"));
	}
	
	public ActionForward dmsendList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("company_name1")   !=null){
			String company_name1 = URLDecoder.decode(request.getParameter("company_name1"),"UTF-8");
			map.put("company_name1",               company_name1);}			
		if(request.getParameter("pers_name1")      !=null){
			String pers_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",                  pers_name1);}		
		if(request.getParameter("job_kind1")       !=null){
			String job_kind1 = URLDecoder.decode(request.getParameter("job_kind1"),"UTF-8");
			map.put("job_kind1",                   job_kind1);}		
		if(request.getParameter("code_call1")      !=null){
			String code_call1 = URLDecoder.decode(request.getParameter("code_call1"),"UTF-8");
			map.put("code_call1",                  code_call1);}		
		if(request.getParameter("m_address")       !=null){
			String m_address = URLDecoder.decode(request.getParameter("m_address"),"UTF-8");
			map.put("m_address",                   m_address);}
		if(request.getParameter("d_address")       !=null){
			String d_address = URLDecoder.decode(request.getParameter("d_address"),"UTF-8");
			map.put("d_address",                   d_address);}		 
		if(request.getParameter("book_flag1")      !=null){
			map.put("book_flag1",                  request.getParameter("book_flag1"));}	
		if(request.getParameter("book_receipt_dt1")!=null){
			map.put("book_receipt_dt1",            request.getParameter("book_receipt_dt1"));}	
		if(request.getParameter("book_start_dt1")  !=null){	
			map.put("book_start_dt1",              request.getParameter("book_start_dt1"));}
		if(request.getParameter("book_end_dt1")    !=null){	 
			map.put("book_end_dt1",                request.getParameter("book_end_dt1"));}
		if(request.getParameter("company_tel1")    !=null){
			map.put("company_tel1",                request.getParameter("company_tel1"));}
		if(request.getParameter("m_post")          !=null){	 
			map.put("m_post",                      request.getParameter("m_post"));}
		if(request.getParameter("use_yn1")         !=null){
			map.put("use_yn1",                     request.getParameter("use_yn1"));}
		
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
		List<Map> dmsendcount=dao.dmsendcnt(map);
		ncount = ((Integer)(dmsendcount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> dmsend = dao.dmsend(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		
		for(int i=0; i<dmsend.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( dmsend.get(i).get("code_book")	    == null ? "" : dmsend.get(i).get("code_book"))       +"\",");
			result.append("\"" + ( dmsend.get(i).get("book_flag_name")  == null ? "" : dmsend.get(i).get("book_flag_name") ) +"\",");
			result.append("\"" + ( dmsend.get(i).get("book_flag")       == null ? "" : dmsend.get(i).get("book_flag") )      +"\",");
			result.append("\"" + ( dmsend.get(i).get("book_receipt_dt")	== null ? "" : dmsend.get(i).get("book_receipt_dt")) +"\",");
			result.append("\"" + ( dmsend.get(i).get("book_start_dt")	== null ? "" : dmsend.get(i).get("book_start_dt") )  +"\",");
			result.append("\"" + ( dmsend.get(i).get("book_end_dt")		== null ? "" : dmsend.get(i).get("book_end_dt") )    +"\",");
			result.append("\"" + ( dmsend.get(i).get("company_name")	== null ? "" : dmsend.get(i).get("company_name") )   +"\",");
			result.append("\"" + ( dmsend.get(i).get("company_tel")	    == null ? "" : dmsend.get(i).get("company_tel") )    +"\",");
			result.append("\"" + ( dmsend.get(i).get("pers_name")	    == null ? "" : dmsend.get(i).get("pers_name") )      +"\",");
			result.append("\"" + ( dmsend.get(i).get("job_kind")	    == null ? "" : dmsend.get(i).get("job_kind") )       +"\",");
			result.append("\"" + ( dmsend.get(i).get("code_call_name")	== null ? "" : dmsend.get(i).get("code_call_name") ) +"\",");
			result.append("\"" + ( dmsend.get(i).get("code_call")		== null ? "" : dmsend.get(i).get("code_call") )      +"\",");
			result.append("\"" + ( dmsend.get(i).get("code_post")		== null ? "" : dmsend.get(i).get("code_post") )      +"\",");
			result.append("\"" + ( dmsend.get(i).get("pers_add")	    == null ? "" : dmsend.get(i).get("pers_add") )       +"\",");
			result.append("\"" + ( dmsend.get(i).get("pers_add1")	    == null ? "" : dmsend.get(i).get("pers_add1") )      +"\",");
			result.append("\"" + ( dmsend.get(i).get("use_yn")	        == null ? "" : dmsend.get(i).get("use_yn") )         +"\"");
			result.append("]}");
		}	
		
		result.append("]}");
		
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}
	
	/*기타 발송자  excel Down */ 
	public void dmsendDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
		documentDao dao = new documentDao(); 
		
		if(request.getParameter("company_name1")   !=null){
			String company_name1 = URLDecoder.decode(request.getParameter("company_name1"),"UTF-8");
			map.put("company_name1",               company_name1);}			
		if(request.getParameter("pers_name1")      !=null){
			String pers_name1 = URLDecoder.decode(request.getParameter("pers_name1"),"UTF-8");
			map.put("pers_name1",                  pers_name1);}		
		if(request.getParameter("job_kind1")       !=null){
			String job_kind1 = URLDecoder.decode(request.getParameter("job_kind1"),"UTF-8");
			map.put("job_kind1",                   job_kind1);}		
		if(request.getParameter("code_call1")      !=null){
			String code_call1 = URLDecoder.decode(request.getParameter("code_call1"),"UTF-8");
			map.put("code_call1",                  code_call1);}		
		if(request.getParameter("m_address")       !=null){
			String m_address = URLDecoder.decode(request.getParameter("m_address"),"UTF-8");
			map.put("m_address",                   m_address);}
		if(request.getParameter("d_address")       !=null){
			String d_address = URLDecoder.decode(request.getParameter("d_address"),"UTF-8");
			map.put("d_address",                   d_address);}		 
		if(request.getParameter("book_flag1")      !=null){
			map.put("book_flag1",                  request.getParameter("book_flag1"));}	
		if(request.getParameter("book_receipt_dt1")!=null){
			map.put("book_receipt_dt1",            request.getParameter("book_receipt_dt1"));}	
		if(request.getParameter("book_start_dt1")  !=null){	
			map.put("book_start_dt1",              request.getParameter("book_start_dt1"));}
		if(request.getParameter("book_end_dt1")    !=null){	 
			map.put("book_end_dt1",                request.getParameter("book_end_dt1"));}
		if(request.getParameter("company_tel1")    !=null){
			map.put("company_tel1",                request.getParameter("company_tel1"));}
		if(request.getParameter("m_post")          !=null){	 
			map.put("m_post",                      request.getParameter("m_post"));}
		if(request.getParameter("use_yn1")         !=null){
			map.put("use_yn1",                     request.getParameter("use_yn1"));}
				
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_ID")); //유저ID
		//여기부터 개발자 변경 필요
		String name="dmsend"; //프로그램명
		String s_name="기타발송자"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"book_flag_name", "book_receipt_dt", "book_start_dt", "book_end_dt", "company_name", "company_tel", "pers_name", "job_kind", "code_call_name", "code_post", "pers_add", "pers_add1", "use_yn"}; //헤더 영문
		String header_k[]={"구독구분","신청일","기간 시작","기간 종료","근무처 명","연락처","이름","직위","발송호칭","우편번호","주소","상세주소","사용"}; //헤더 국문
		int c_size[]={15, 15, 15, 15, 25, 15, 15, 15, 25, 15, 50, 40, 10};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		//여기까지 변경
		List<Map> dmsenddown=dao.dmsenddown(map,true);
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(dmsenddown, filename, s_name,header_e,header_k,c_size);
			
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
	
	public ActionForward insert_dmsend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
    	Map map = new HashMap();
    	documentDao dao = new documentDao();
    	HttpSession session=request.getSession();
		
    	map.put("company_name1",      request.getParameter("company_name1"));
		map.put("pers_name1",         request.getParameter("pers_name1"));
	    map.put("job_kind1",          request.getParameter("job_kind1"));   	
		map.put("code_call1",         request.getParameter("code_call1"));
	    map.put("m_address",          request.getParameter("m_address")); 	   
    	map.put("d_address",          request.getParameter("d_address"));
    	String book_flag ="";
    	if(request.getParameter("book_flag1").equals("1")){
    		book_flag = "A"; }
    	if(request.getParameter("book_flag1").equals("2")){
    		book_flag = "B"; }    	
		map.put("book_flag",          book_flag);
		map.put("book_flag1",         request.getParameter("book_flag1"));
		map.put("book_receipt_dt1",   request.getParameter("book_receipt_dt1"));		
		map.put("book_start_dt1",     request.getParameter("book_start_dt1"));
	    map.put("book_end_dt1",       request.getParameter("book_end_dt1")); 	   
    	map.put("company_tel1",       request.getParameter("company_tel1"));    
		map.put("m_post",             request.getParameter("m_post").replace("-",""	));
		map.put("use_yn1",            request.getParameter("use_yn1"));	
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			List<Map> result = dao.insertdmsend(map);
			resetToken(request);
			errMsg="저장했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);			
		return mapping.findForward("insertdmsend_ok");	
	} 
	
	public ActionForward update_dmsend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		documentDao dao = new documentDao();	
    	Map map = new HashMap();      	
		
    	map.put("code_book1",         request.getParameter("code_book1"));
    	map.put("company_name1",      request.getParameter("company_name1"));
		map.put("pers_name1",         request.getParameter("pers_name1"));
	    map.put("job_kind1",          request.getParameter("job_kind1"));   	
		map.put("code_call1",         request.getParameter("code_call1"));
	    map.put("m_address",          request.getParameter("m_address")); 	   
    	map.put("d_address",          request.getParameter("d_address"));     	
		map.put("book_flag1",         request.getParameter("book_flag1"));
		map.put("book_receipt_dt1",   request.getParameter("book_receipt_dt1"));		
		map.put("book_start_dt1",     request.getParameter("book_start_dt1"));
	    map.put("book_end_dt1",       request.getParameter("book_end_dt1")); 	   
    	map.put("company_tel1",       request.getParameter("company_tel1"));    
		map.put("m_post",             request.getParameter("m_post").replace("-",""	));
		map.put("use_yn1",            request.getParameter("use_yn1"));

		Map map1 = new HashMap();
    	map1.put("code_book",         request.getParameter("code_book1"));
		map1.put("nstart", 0);
		map1.put("nend", 1);
		List<Map> dmsend = dao.dmsend(map1);
		if(dmsend!=null && dmsend.size()>0){
			String d = (String) dmsend.get(0).get("pers_add1");
			if(!StringUtil.NVL(request.getParameter("d_address")).equals(d)){
		    	map.put("d_address_upok","Y");     	
			}
		}

		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{
			int result = dao.updatedmsend(map);
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);					
		return mapping.findForward("updatedmsend_ok");	
	} 
	
	/*발송대장 대상생성*/	
	public ActionForward dmsendreportData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("dmsendreportList"));
	}
	
	public ActionForward dmsendreportList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao();	
		
		if(request.getParameter("isSelect")!=null){
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
			
			/*  전체 (회원+기타 구분코드 :"") */
			if(request.getParameter("sendgubun").equals(""))  {		
				
				map.put("sendgubun",                           request.getParameter("sendgubun"));
				map.put("code_book_kind",                      request.getParameter("code_book_kind"));	
				map.put("book_start_dt",                       request.getParameter("book_start_dt"));
				//map.put("book_end_dt",                         request.getParameter("book_end_dt"));	
							
				List<Map> dmreportallcount=dao.dmreportallcount(map);
				ncount = ((Integer)(dmreportallcount.get(0).get("cnt"))).intValue();
		
				int ntotpage = (ncount/nrows)+1;
				
				List<Map> books_kindall      = dao.books_kindall(map);				
				List<Map> dmsendreportall    = dao.dmsendreportall(map);		    	
				
				StringBuffer result = new StringBuffer();
						
				result.append("{\"page\":\""+	npage	+"\",");		
				result.append("\"total\":\"" + ntotpage+"\",");
				result.append("\"records\":\""+	ncount	+"\",");
				result.append("\"rows\":[");
						
				for(int i=0; i<dmsendreportall.size(); i++){
					
					if(i>0) result.append(",");
					result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")	   == null ? ""   :   books_kindall.get(0).get("code_book_kind"))       +"\",");
					result.append("\"" + ( books_kindall.get(0).get("book_name")	       == null ? ""   :   books_kindall.get(0).get("book_name"))            +"\",");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")      == null ? ""   :   books_kindall.get(0).get("code_book_kind") )      +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("dm_print_yymm")	   == null ? ""   :   dmsendreportall.get(i).get("dm_print_yymm"))      +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("dm_print_yymm_seq") == null ? ""   :   dmsendreportall.get(i).get("dm_print_yymm_seq"))  +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("pers_name")	       == null ? ""   :   dmsendreportall.get(i).get("pers_name"))          +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("lic_no")            == null ? ""   :   dmsendreportall.get(i).get("lic_no") )            +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_member_name")  == null ? ""   :   dmsendreportall.get(i).get("code_member_name") )  +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_member")	   == null ? ""   :   dmsendreportall.get(i).get("code_member"))        +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_call_name")    == null ? ""   :   dmsendreportall.get(i).get("code_call_name") )    +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_call")		   == null ? ""   :   dmsendreportall.get(i).get("code_call") )         +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("company_name")	   == null ? ""   :   dmsendreportall.get(i).get("company_name") )      +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_post")	       == null ? ""   :   dmsendreportall.get(i).get("code_post") )         +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("pers_add")	       == null ? ""   :   dmsendreportall.get(i).get("pers_add") )          +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("pers_hp")	       == null ? ""   :   dmsendreportall.get(i).get("pers_hp") )           +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("email")	           == null ? ""   :   dmsendreportall.get(i).get("email") )             +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_bran_name")    == null ? ""   :   dmsendreportall.get(i).get("code_bran_name") )    +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_bran")		   == null ? ""   :   dmsendreportall.get(i).get("code_bran") )         +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("rece_yn_name")      == null ? ""   :   dmsendreportall.get(i).get("rece_yn_name") )      +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("rece_yn")		   == null ? ""   :   dmsendreportall.get(i).get("rece_yn") )           +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("dm_creater")		   == null ? ""   :   dmsendreportall.get(i).get("dm_creater") )        +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("dm_key")	           == null ? ""   :   dmsendreportall.get(i).get("dm_key") )            +"\",");
					result.append("\"" + ( dmsendreportall.get(i).get("code_pers")	           == null ? ""   :   dmsendreportall.get(i).get("code_pers") )            +"\"");
					
					result.append("]}");
				}	
				
				result.append("]}");		
			request.setAttribute("result",result);	
			}//전체 (회원+기타)		
			
			/*  회원(구분코드 :1) */
			if(request.getParameter("sendgubun").equals("1"))  {			
				
				map.put("sendgubun",                           request.getParameter("sendgubun"));
				map.put("code_book_kind",                      request.getParameter("code_book_kind"));	
				map.put("book_start_dt",                       request.getParameter("book_start_dt"));
				//map.put("book_end_dt",                         request.getParameter("book_end_dt"));	
							
				List<Map> dmreportmemcount=dao.dmreportmemcount(map);
				ncount = ((Integer)(dmreportmemcount.get(0).get("cnt"))).intValue();
		
				int ntotpage = (ncount/nrows)+1;
				
				List<Map> books_kindall      = dao.books_kindall(map);				
				List<Map> dmsendreportmem    = dao.dmsendreportmem(map);
				
				StringBuffer result = new StringBuffer();
						
				result.append("{\"page\":\""+	npage	+"\",");		
				result.append("\"total\":\"" + ntotpage+"\",");
				result.append("\"records\":\""+	ncount	+"\",");
				result.append("\"rows\":[");
							
				for(int i=0; i<dmsendreportmem.size(); i++){
					
					if(i>0) result.append(",");
					result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")	   == null ? ""   :   books_kindall.get(0).get("code_book_kind"))       +"\",");
					result.append("\"" + ( books_kindall.get(0).get("book_name")	       == null ? ""   :   books_kindall.get(0).get("book_name"))            +"\",");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")      == null ? ""   :   books_kindall.get(0).get("code_book_kind") )      +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("dm_print_yymm")	   == null ? ""   :   dmsendreportmem.get(i).get("dm_print_yymm"))      +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("dm_print_yymm_seq") == null ? ""   :   dmsendreportmem.get(i).get("dm_print_yymm_seq"))  +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("pers_name")	       == null ? ""   :   dmsendreportmem.get(i).get("pers_name"))          +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("lic_no")            == null ? ""   :   dmsendreportmem.get(i).get("lic_no") )            +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_member_name")  == null ? ""   :   dmsendreportmem.get(i).get("code_member_name") )  +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_member")	   == null ? ""   :   dmsendreportmem.get(i).get("code_member"))        +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_call_name")    == null ? ""   :   dmsendreportmem.get(i).get("code_call_name") )    +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_call")		   == null ? ""   :   dmsendreportmem.get(i).get("code_call") )         +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("company_name")	   == null ? ""   :   dmsendreportmem.get(i).get("company_name") )      +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_post")	       == null ? ""   :   dmsendreportmem.get(i).get("code_post") )         +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("pers_add")	       == null ? ""   :   dmsendreportmem.get(i).get("pers_add") )          +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("pers_hp")	       == null ? ""   :   dmsendreportmem.get(i).get("pers_hp") )           +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("email")	           == null ? ""   :   dmsendreportmem.get(i).get("email") )             +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_bran_name")    == null ? ""   :   dmsendreportmem.get(i).get("code_bran_name") )    +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_bran")		   == null ? ""   :   dmsendreportmem.get(i).get("code_bran") )         +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("rece_yn_name")      == null ? ""   :   dmsendreportmem.get(i).get("rece_yn_name") )      +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("rece_yn")		   == null ? ""   :   dmsendreportmem.get(i).get("rece_yn") )           +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("dm_creater")		   == null ? ""   :   dmsendreportmem.get(i).get("dm_creater") )        +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("dm_key")	           == null ? ""   :   dmsendreportmem.get(i).get("dm_key") )            +"\",");
					result.append("\"" + ( dmsendreportmem.get(i).get("code_pers")	           == null ? ""   :   dmsendreportmem.get(i).get("code_pers") )            +"\"");
					
					result.append("]}");
				}	
				
				result.append("]}");		
			request.setAttribute("result",result);	
			}//회원	
			
			/*  회원+구독(구분코드 :2) */
			if(request.getParameter("sendgubun").equals("2"))  {			
				
				map.put("sendgubun",                           request.getParameter("sendgubun"));
				map.put("code_book_kind",                      request.getParameter("code_book_kind"));	
				map.put("book_start_dt",                       request.getParameter("book_start_dt"));
				//map.put("book_end_dt",                         request.getParameter("book_end_dt"));	
							
				List<Map> dmreportgoocount=dao.dmreportgoocount(map);
				ncount = ((Integer)(dmreportgoocount.get(0).get("cnt"))).intValue();
		
				int ntotpage = (ncount/nrows)+1;
				
				List<Map> books_kindall      = dao.books_kindall(map);
				List<Map> dmsendreportgoo    = dao.dmsendreportgoo(map);
				
				StringBuffer result = new StringBuffer();
						
				result.append("{\"page\":\""+	npage	+"\",");		
				result.append("\"total\":\"" + ntotpage+"\",");
				result.append("\"records\":\""+	ncount	+"\",");
				result.append("\"rows\":[");
							
				for(int i=0; i<dmsendreportgoo.size(); i++){
					
					if(i>0) result.append(",");
					result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")	   == null ? ""   :   books_kindall.get(0).get("code_book_kind"))       +"\",");
					result.append("\"" + ( books_kindall.get(0).get("book_name")	       == null ? ""   :   books_kindall.get(0).get("book_name"))            +"\",");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")      == null ? ""   :   books_kindall.get(0).get("code_book_kind") )      +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("dm_print_yymm")	   == null ? ""   :   dmsendreportgoo.get(i).get("dm_print_yymm"))      +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("dm_print_yymm_seq") == null ? ""   :   dmsendreportgoo.get(i).get("dm_print_yymm_seq"))  +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("pers_name")	       == null ? ""   :   dmsendreportgoo.get(i).get("pers_name"))          +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("lic_no")            == null ? ""   :   dmsendreportgoo.get(i).get("lic_no") )            +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("code_member_name")  == null ? ""   :   dmsendreportgoo.get(i).get("code_member_name") )  +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("code_member")	   == null ? ""   :   dmsendreportgoo.get(i).get("code_member"))        +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("code_call_name")    == null ? ""   :   dmsendreportgoo.get(i).get("code_call_name") )    +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("code_call")		   == null ? ""   :   dmsendreportgoo.get(i).get("code_call") )         +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("company_name")	   == null ? ""   :   dmsendreportgoo.get(i).get("company_name") )      +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("code_post")	       == null ? ""   :   dmsendreportgoo.get(i).get("code_post") )         +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("pers_add")	       == null ? ""   :   dmsendreportgoo.get(i).get("pers_add") )          +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("pers_hp")	       == null ? ""   :   dmsendreportgoo.get(i).get("pers_hp") )           +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("email")	           == null ? ""   :   dmsendreportgoo.get(i).get("email") )             +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("code_bran_name")    == null ? ""   :   dmsendreportgoo.get(i).get("code_bran_name") )    +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("code_bran")		   == null ? ""   :   dmsendreportgoo.get(i).get("code_bran") )         +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("rece_yn_name")      == null ? ""   :   dmsendreportgoo.get(i).get("rece_yn_name") )      +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("rece_yn")		   == null ? ""   :   dmsendreportgoo.get(i).get("rece_yn") )           +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("dm_creater")		   == null ? ""   :   dmsendreportgoo.get(i).get("dm_creater") )        +"\",");
					result.append("\"" + ( dmsendreportgoo.get(i).get("dm_key")	           == null ? ""   :   dmsendreportgoo.get(i).get("dm_key") )            +"\"");
					
					result.append("]}");
				}	
				
				result.append("]}");		
			request.setAttribute("result",result);	
			}//회원+구독	
			
			/*  회원+섭외(구분코드 :3) */
			if(request.getParameter("sendgubun").equals("3"))  {			
				
				map.put("sendgubun",                           request.getParameter("sendgubun"));
				map.put("code_book_kind",                      request.getParameter("code_book_kind"));
				map.put("book_start_dt",                       request.getParameter("book_start_dt"));
				//map.put("book_end_dt",                         request.getParameter("book_end_dt"));	
							
				List<Map> dmreportsupcount=dao.dmreportsupcount(map);
				ncount = ((Integer)(dmreportsupcount.get(0).get("cnt"))).intValue();
		
				int ntotpage = (ncount/nrows)+1;
				
				List<Map> books_kindall      = dao.books_kindall(map);				
				List<Map> dmsendreportsup    = dao.dmsendreportsup(map);
				
				StringBuffer result = new StringBuffer();
						
				result.append("{\"page\":\""+	npage	+"\",");		
				result.append("\"total\":\"" + ntotpage+"\",");
				result.append("\"records\":\""+	ncount	+"\",");
				result.append("\"rows\":[");
							
				for(int i=0; i<dmsendreportsup.size(); i++){
					
					if(i>0) result.append(",");
					result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")	   == null ? ""   :   books_kindall.get(0).get("code_book_kind"))       +"\",");
					result.append("\"" + ( books_kindall.get(0).get("book_name")	       == null ? ""   :   books_kindall.get(0).get("book_name"))            +"\",");
					result.append("\"" + ( books_kindall.get(0).get("code_book_kind")      == null ? ""   :   books_kindall.get(0).get("code_book_kind") )      +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("dm_print_yymm")	   == null ? ""   :   dmsendreportsup.get(i).get("dm_print_yymm"))      +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("dm_print_yymm_seq") == null ? ""   :   dmsendreportsup.get(i).get("dm_print_yymm_seq"))  +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("pers_name")	       == null ? ""   :   dmsendreportsup.get(i).get("pers_name"))          +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("lic_no")            == null ? ""   :   dmsendreportsup.get(i).get("lic_no") )            +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("code_member_name")  == null ? ""   :   dmsendreportsup.get(i).get("code_member_name") )  +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("code_member")	   == null ? ""   :   dmsendreportsup.get(i).get("code_member"))        +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("code_call_name")    == null ? ""   :   dmsendreportsup.get(i).get("code_call_name") )    +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("code_call")		   == null ? ""   :   dmsendreportsup.get(i).get("code_call") )         +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("company_name")	   == null ? ""   :   dmsendreportsup.get(i).get("company_name") )      +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("code_post")	       == null ? ""   :   dmsendreportsup.get(i).get("code_post") )         +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("pers_add")	       == null ? ""   :   dmsendreportsup.get(i).get("pers_add") )          +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("pers_hp")	       == null ? ""   :   dmsendreportsup.get(i).get("pers_hp") )           +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("email")	           == null ? ""   :   dmsendreportsup.get(i).get("email") )             +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("code_bran_name")    == null ? ""   :   dmsendreportsup.get(i).get("code_bran_name") )    +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("code_bran")		   == null ? ""   :   dmsendreportsup.get(i).get("code_bran") )         +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("rece_yn_name")      == null ? ""   :   dmsendreportsup.get(i).get("rece_yn_name") )      +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("rece_yn")		   == null ? ""   :   dmsendreportsup.get(i).get("rece_yn") )           +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("dm_creater")		   == null ? ""   :   dmsendreportsup.get(i).get("dm_creater") )        +"\",");
					result.append("\"" + ( dmsendreportsup.get(i).get("dm_key")	           == null ? ""   :   dmsendreportsup.get(i).get("dm_key") )            +"\"");
					
					result.append("]}");
				}	
				
				result.append("]}");		
			request.setAttribute("result",result);	
		}//회원+섭외
   }//isSelect
		return (mapping.findForward("ajaxout")); 
}
	
	/* 발송 대장 검색 */
	public ActionForward dmsendsearchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao();
		
		if(request.getParameter("pers_name")           !=null){
			String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");		
			map.put("pers_name",                   pers_name);}			
		if(request.getParameter("code_book_kind")      !=null){
			map.put("code_book_kind",              request.getParameter("code_book_kind"));}	
		if(request.getParameter("sendgubun")           !=null){
			map.put("sendgubun",                   request.getParameter("sendgubun"));}		
		if(request.getParameter("book_start_dt")       !=null){	
			String book_start_dt =  request.getParameter("book_start_dt");
			//book_start_dt        =  book_start_dt.substring(2,6);
			map.put("book_start_dt",               book_start_dt);}				
		if(request.getParameter("rece_yn")             !=null){
			map.put("rece_yn",                     request.getParameter("rece_yn"));}
		
		if(request.getParameter("dm_creater")          !=null){
			String dm_creater = URLDecoder.decode(request.getParameter("dm_creater"),"UTF-8");		
			map.put("dm_creater",                  dm_creater);}
		if(request.getParameter("dm_print_yymm_seq")          !=null){
			String dm_print_yymm_seq = request.getParameter("dm_print_yymm_seq");		
			map.put("dm_print_yymm_seq",                  dm_print_yymm_seq);}
		
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
					
		List<Map> dmsearchcount=dao.dmsearchcount(map);
		ncount = ((Integer)(dmsearchcount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> books_kindall      = dao.books_kindall(map);			
		List<Map> dmsearch           = dao.dmsearch(map);
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");
					
		for(int i=0; i<dmsearch.size(); i++){
			
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( dmsearch.get(i).get("dm_pers_code")	           == null ? ""   :   dmsearch.get(i).get("dm_pers_code"))              +"\",");
			result.append("\"" + ( dmsearch.get(i).get("dm_name")	               == null ? ""   :   dmsearch.get(i).get("dm_name"))                   +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_book_kind")           == null ? ""   :   dmsearch.get(i).get("code_book_kind") )           +"\",");
			result.append("\"" + ( dmsearch.get(i).get("dm_print_yymm")	           == null ? ""   :   dmsearch.get(i).get("dm_print_yymm"))             +"\",");
			result.append("\"" + ( dmsearch.get(i).get("dm_print_yymm_seq")        == null ? ""   :   dmsearch.get(i).get("dm_print_yymm_seq"))         +"\",");
			result.append("\"" + ( dmsearch.get(i).get("pers_name")	               == null ? ""   :   dmsearch.get(i).get("pers_name"))                 +"\",");
			result.append("\"" + ( dmsearch.get(i).get("lic_no")                   == null ? ""   :   dmsearch.get(i).get("lic_no") )                   +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_member_name")         == null ? ""   :   dmsearch.get(i).get("code_member_name") )         +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_member")	           == null ? ""   :   dmsearch.get(i).get("code_member"))               +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_call_name")           == null ? ""   :   dmsearch.get(i).get("code_call_name") )           +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_call")		           == null ? ""   :   dmsearch.get(i).get("code_call") )                +"\",");
			result.append("\"" + ( dmsearch.get(i).get("company_name")	           == null ? ""   :   dmsearch.get(i).get("company_name") )             +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_post")	               == null ? ""   :   dmsearch.get(i).get("code_post") )                +"\",");
			result.append("\"" + ( dmsearch.get(i).get("pers_add")	               == null ? ""   :   dmsearch.get(i).get("pers_add") )                 +"\",");
			result.append("\"" + ( dmsearch.get(i).get("pers_hp")	               == null ? ""   :   dmsearch.get(i).get("pers_hp") )                  +"\",");
			result.append("\"" + ( dmsearch.get(i).get("email")	                   == null ? ""   :   dmsearch.get(i).get("email") )                    +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_bran_name")           == null ? ""   :   dmsearch.get(i).get("code_bran_name") )           +"\",");
			result.append("\"" + ( dmsearch.get(i).get("code_bran")		           == null ? ""   :   dmsearch.get(i).get("code_bran") )                +"\",");
			result.append("\"" + ( dmsearch.get(i).get("rece_yn_name")             == null ? ""   :   dmsearch.get(i).get("rece_yn_name") )             +"\",");
			result.append("\"" + ( dmsearch.get(i).get("rece_yn")		           == null ? ""   :   dmsearch.get(i).get("rece_yn") )                  +"\",");
			result.append("\"" + ( dmsearch.get(i).get("dm_creater")	           == null ? ""   :   dmsearch.get(i).get("dm_creater") )               +"\",");
			result.append("\"" + ( dmsearch.get(i).get("dm_key")	               == null ? ""   :   dmsearch.get(i).get("dm_key") )                   +"\"");
			result.append("]}");
		}		
		result.append("]}");
		
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));
	}	
	
	/* 발송 대장 검색  excel Down */ 
	public void dmsendsearchDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
       	
    	Map map = new HashMap();
		Map map1 = new HashMap();
		Map map2 = new HashMap();
		documentDao dao = new documentDao(); 
			
		if(request.getParameter("pers_name")           !=null){
			String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");		
			map.put("pers_name",                   pers_name);}			
		if(request.getParameter("code_book_kind")      !=null){
			map.put("code_book_kind",              request.getParameter("code_book_kind"));}	
		if(request.getParameter("sendgubun")           !=null){
			map.put("sendgubun",                   request.getParameter("sendgubun"));}		
		if(request.getParameter("book_start_dt")       !=null){	
			String book_start_dt =  request.getParameter("book_start_dt");
			//book_start_dt        =  book_start_dt.substring(2,6);
			map.put("book_start_dt",               book_start_dt);}				
		if(request.getParameter("rece_yn")             !=null){
			map.put("rece_yn",                     request.getParameter("rece_yn"));}
		
		if(request.getParameter("dm_creater")          !=null){
			String dm_creater = URLDecoder.decode(request.getParameter("dm_creater"),"UTF-8");		
			map.put("dm_creater",                  dm_creater);}
		if(request.getParameter("dm_print_yymm_seq")          !=null){
			String dm_print_yymm_seq = request.getParameter("dm_print_yymm_seq");		
			map.put("dm_print_yymm_seq",                  dm_print_yymm_seq);}

		String format="yyyyMMdd";
		
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user = new String((String) session.getAttribute("G_ID")); //유저ID
		
		//여기부터 개발자 변경 필요
		String name="dmsendreport"; //프로그램명
		String s_name="발송대장"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"dm_name", "pers_name", "lic_no", "code_member_name", "code_call_name", "company_name", "code_post", "pers_add", "pers_hp", "email", "code_bran_name", "rece_yn_name", "bacode"}; //헤더 영문
		String header_k[]={"DM 명","이름","면허번호","회원구분","발송호칭","근무처명","우편번호","주소","핸드폰","이메일","지부","발송구분","바코드키"}; //헤더 국문
		int c_size[]={15, 15, 15, 25, 25, 30, 15, 50, 15, 20, 20, 15, 20};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
				
		//여기까지 변경
		List<Map> dmsearchdown=new ArrayList<Map>();
		if(request.getParameter("isBarcode").toString().equals("N")){
			dmsearchdown=dao.dmsearchdown(map,true);
		}else{
			String t_bacode=request.getParameter("t_bacode");	
			String [] bacode=t_bacode.split(",");
			String dm_bacode="";
			for(int i=0;i<bacode.length;i++){
				if(dm_bacode.length()==0) dm_bacode="'"+bacode[i]+"'";
				else dm_bacode +=",'"+bacode[i]+"'";
			}
			System.out.println("dm_bacode=====>"+dm_bacode);
			map2.put("dm_bacode", dm_bacode);
			dmsearchdown=dao.dmsearchBardown(map2,true);
		}
		
		
		
		//이 이하는 변경할 필요 없음
		File f=CommonUtil.makeExcelFile(dmsearchdown, filename, s_name,header_e,header_k,c_size);
			
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
	
	/* 우편번호 검색 */
	public ActionForward postSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(request.getParameter("sel")!= null){							   
			sel=request.getParameter("sel");
		}
		return (mapping.findForward("postSearchr"));		
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
	
	/* 쪽지 저장 전체*/
	public ActionForward dmsendnotePadall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		if(request.getParameter("code_book_kind")      !=null){
			String code_book_kind = request.getParameter("code_book_kind");	
		}
		if(request.getParameter("sendgubun")           !=null){
			String sendgubun      = request.getParameter("sendgubun");		
		}
		if(request.getParameter("book_start_dt")       !=null){	
			String book_start_dt  =  request.getParameter("book_start_dt");		
			book_start_dt         =  book_start_dt.substring(2,6);			
		}
		if(request.getParameter("book_end_dt")         !=null){	 
			String book_end_dt    =  request.getParameter("book_end_dt");			
			book_end_dt           =  book_end_dt.substring(2,6);		
		}
		if(request.getParameter("rece_yn")              !=null){
			String rece_yn        = request.getParameter("rece_yn");			
		}		
		String isSelect           = request.getParameter("isSelect");
		
		String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		String dm_pers_code = request.getParameter("dm_pers_code");
		
		return (mapping.findForward("notePadall"));
	}
	
	public ActionForward notePadall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();    		
    	HttpSession session=request.getSession(); 
    	documentDao dao = new documentDao();    	
    	
    	if(!request.getParameter("code_book_kind").equals("")){
    		map.put("code_book_kind",              request.getParameter("code_book_kind"));}		
		if(!request.getParameter("sendgubun").equals("")){
			map.put("sendgubun",                   request.getParameter("sendgubun"));}		
		if(!request.getParameter("book_start_dt").equals("")) {
			map.put("book_start_dt",               request.getParameter("book_start_dt"));}	
		if(!request.getParameter("book_end_dt").equals("")){
			map.put("book_end_dt",                 request.getParameter("book_end_dt"));}	
		if(!request.getParameter("rece_yn").equals("")){
			map.put("rece_yn",                     request.getParameter("rece_yn"));}
		
		List<Map> selectnotePad = dao.selectnotePad(map);
			
    	for(int i=0; i<selectnotePad.size(); i++){
    		
    		map.put("dm_pers_code" ,    selectnotePad.get(i).get("dm_pers_code"));
    		String note_oper     = URLDecoder.decode(request.getParameter("note_oper"),"UTF-8");
    		String note_title    = URLDecoder.decode(request.getParameter("note_title"),"UTF-8");
    		String note_contents = URLDecoder.decode(request.getParameter("note_contents"),"UTF-8");
    		map.put("note_title",                note_title);
    	    map.put("note_contents",             note_contents);   	
    		map.put("note_oper",                 note_oper);
    		map.put("st_dt",                     request.getParameter("st_dt"));   	
      		map.put("ed_dt",                     request.getParameter("ed_dt"));
    		map.put("register",                  session.getAttribute("G_NAME"));
    		int n = dao.insertnotePadall(map);    		
    	}   
    	
		return (mapping.findForward("insertnotePad_ok"));				
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
	
	/* 메일 전송 */
	public ActionForward eMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
			String param ="&addr_infos="+request.getParameter("addr_infos");
			request.setAttribute("param", param);
		}
		return (mapping.findForward("sendMail"));		
	}
	
	public void SendMail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String saveFolder="D:/WEB/KDA_VER3/upload";
		String to="";
//		String to=StringUtil.nullToStr("", request.getParameter("to")); //받는 사람 메일주소. 여러명일때는 주소 사이를 콤마로 구분해준다.
		
		if(!"".equals(StringUtil.NVL(request.getParameter("addr_infos")))){
			List<String> addr_infos_org =  Arrays.asList(request.getParameter("addr_infos"	).split("__"));
			List<String> bacodes = new ArrayList<String>();
			List<String> codeperss = new ArrayList<String>();
			
			for(String item : addr_infos_org){
				List<String> addr_info =  Arrays.asList(item.split("_"));
				if(addr_info!=null&&addr_info.size()>0&&!"*".equals(addr_info.get(0))){ bacodes.add(addr_info.get(0));} 
				if(addr_info!=null&&addr_info.size()>1&&!"*".equals(addr_info.get(1))){ codeperss.add(addr_info.get(1));} 
			}

			StringBuffer toArr = new StringBuffer(); 
			documentDao dao       = new documentDao();    	
			for(String item : bacodes){
				Map map = new HashMap();
				map.put("dm_bacode_single", item);
				map.put("nstart", 0);
				map.put("nend", 10);
	    		List<Map> result = dao.dmbacode(map,true);     		

	    		if(result!= null && result.size()>0){
		    		String email = (String) result.get(0).get("email");
					if(email!=null && !"".equals(email)){ if(toArr.length()>0) toArr.append(","); toArr.append(email); }
	    		}
			}   
			basicDao dao2=new basicDao();
			for(String item : codeperss){
				Map map = new HashMap();
				map.put("pCode",item);
				List<Map> result = dao2.memInfo(map,true);     		
				
				if(result!= null && result.size()>0){
					String email = (String) result.get(0).get("email");
					if(email!=null && !"".equals(email)){ if(toArr.length()>0) toArr.append(","); toArr.append(email); }
				}
			}   
			to = toArr.toString();
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
	
	/* 파일 첨부 */
	public void upFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		
		String saveFolder="D:/WEB/KDA_VER3/upload";
		String encType="UTF-8";
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
		
		/*JSONObject result=new JSONObject();
		result.put("result", filename);
		
		request.setAttribute("result", result);
		return (mapping.findForward("ajaxout"));	*/	
	}
	
	/* 문자 전송 전체*/
	public ActionForward sendumsDataall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		if(request.getParameter("code_book_kind")      !=null){
			String code_book_kind = request.getParameter("code_book_kind");	
		}
		if(request.getParameter("sendgubun")           !=null){
			String sendgubun      = request.getParameter("sendgubun");		
		}
		if(request.getParameter("book_start_dt")       !=null){	
			String book_start_dt  =  request.getParameter("book_start_dt");		
			book_start_dt         =  book_start_dt.substring(2,6);			
		}
		if(request.getParameter("book_end_dt")         !=null){	 
			String book_end_dt    =  request.getParameter("book_end_dt");			
			book_end_dt           =  book_end_dt.substring(2,6);		
		}
		if(request.getParameter("rece_yn")              !=null){
			String rece_yn        = request.getParameter("rece_yn");			
		}		
		String isSelect           = request.getParameter("isSelect");
		
		String pers_hp            = request.getParameter("pers_hp");
		String dm_pers_code       = request.getParameter("dm_pers_code");
		*/
		
		int ncount = 0;		
		if("".equals(StringUtil.NVL(request.getParameter("t_bacode")))){
			Map map = new HashMap();
			documentDao dao = new documentDao();
			
			if(request.getParameter("pers_name")           !=null){
				String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");		
				map.put("pers_name",                   pers_name);}			
			if(request.getParameter("code_book_kind")      !=null){
				map.put("code_book_kind",              request.getParameter("code_book_kind"));}	
			if(request.getParameter("sendgubun")           !=null){
				map.put("sendgubun",                   request.getParameter("sendgubun"));}		
			if(request.getParameter("book_start_dt")       !=null){	
				String book_start_dt =  request.getParameter("book_start_dt");
				//book_start_dt        =  book_start_dt.substring(2,6);
				map.put("book_start_dt",               book_start_dt);}				
			if(request.getParameter("rece_yn")             !=null){
				map.put("rece_yn",                     request.getParameter("rece_yn"));}
			
			if(request.getParameter("dm_creater")          !=null){
				String dm_creater = URLDecoder.decode(request.getParameter("dm_creater"),"UTF-8");		
				map.put("dm_creater",                  dm_creater);}
			if(request.getParameter("dm_print_yymm_seq")          !=null){
				String dm_print_yymm_seq = request.getParameter("dm_print_yymm_seq");		
				map.put("dm_print_yymm_seq",                  dm_print_yymm_seq);}
			
			map.put("nstart", 0);
			map.put("nend", 100000000);
			
			List<Map> dmsearchcount=dao.dmsearchcount(map);
			ncount = ((Integer)(dmsearchcount.get(0).get("cnt"))).intValue();
		} else {
			String t_bacode=request.getParameter("t_bacode");	
			String [] bacode=t_bacode.split(",");		

			ncount = bacode.length;
		}
		
		request.setAttribute("ncount", ncount);
		return (mapping.findForward("umsDataall"));
	}
	
	public ActionForward umsDataall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session=request.getSession(); 
		documentDao dao = new documentDao();    	
		Map map = new HashMap();    		
		List<Map> documsData = null;
		
		if("".equals(StringUtil.NVL(request.getParameter("t_bacode")))){
	    	
//	    	if(!request.getParameter("code_book_kind").equals("")){
//	    		map.put("code_book_kind",              request.getParameter("code_book_kind"));}		
//			if(!request.getParameter("sendgubun").equals("")){
//				map.put("sendgubun",                   request.getParameter("sendgubun"));}		
//			if(!request.getParameter("book_start_dt").equals("")) {
//				map.put("book_start_dt",               request.getParameter("book_start_dt"));}	
//			if(!request.getParameter("book_end_dt").equals("")){
//				map.put("book_end_dt",                 request.getParameter("book_end_dt"));}	
//			if(!request.getParameter("rece_yn").equals("")){
//				map.put("rece_yn",                     request.getParameter("rece_yn"));}
			
			if(request.getParameter("pers_name")           !=null){
				String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");		
				map.put("pers_name",                   pers_name);}			
			if(request.getParameter("code_book_kind")      !=null){
				map.put("code_book_kind",              request.getParameter("code_book_kind"));}	
			if(request.getParameter("sendgubun")           !=null){
				map.put("sendgubun",                   request.getParameter("sendgubun"));}		
			if(request.getParameter("book_start_dt")       !=null){	
				String book_start_dt =  request.getParameter("book_start_dt");
				//book_start_dt        =  book_start_dt.substring(2,6);
				map.put("book_start_dt",               book_start_dt);}				
			if(request.getParameter("rece_yn")             !=null){
				map.put("rece_yn",                     request.getParameter("rece_yn"));}
			if(request.getParameter("dm_creater")          !=null){
				String dm_creater = URLDecoder.decode(request.getParameter("dm_creater"),"UTF-8");		
				map.put("dm_creater",                  dm_creater);}
			if(request.getParameter("dm_print_yymm_seq")          !=null){
				String dm_print_yymm_seq = request.getParameter("dm_print_yymm_seq");		
				map.put("dm_print_yymm_seq",                  dm_print_yymm_seq);}
			
			documsData = dao.documsData(map,true);
		} else {
			List<String> list = new ArrayList<String>();

			String t_bacode=request.getParameter("t_bacode");	
			String [] bacode=t_bacode.split(",");		
			String dm_bacode="";
			
			map.put("nstart", 0);
			map.put("nend", 100000000);
			
			for(int i=0;i<bacode.length;i++){
				if(dm_bacode.length()==0) dm_bacode="'"+bacode[i]+"'";
				else dm_bacode +=",'"+bacode[i]+"'";
			}
				
    		map.put("dm_bacode", dm_bacode);
    		documsData    = dao.dmbacode(map,true);     		
	    }
		
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
    	        

    	for(int i=0; i<documsData.size(); i++){
    		if(documsData.get(i).get("pers_hp").toString().length()>9){
    			map.put("pers_hp" ,                  documsData.get(i).get("pers_hp"));
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                request.getParameter("send_phone"));      		
        		map.put("register",                  session.getAttribute("G_NAME"));
        		map.put("etc1",                      session.getAttribute("G_ID"));
        		map.put("etc2",                      session.getAttribute("G_BRAN"));
        		map.put("etc3",                      documsData.get(i).get("pers_name"));
        		map.put("umscnt"		, i+1);    		
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
		String pers_hp          = request.getParameter("pers_hp");
		String pers_name          = request.getParameter("pers_name");
		String dm_pers_code     = request.getParameter("dm_pers_code");
		
		if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
			String hp_infos = request.getParameter("hp_infos");
			String param ="&hp_infos="+hp_infos;
			request.setAttribute("param", param);
			request.setAttribute("ncount", hp_infos.split("__").length);
		}
		
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
    	String [] pershp = pers_hp.split(","); 
    	String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
    	String [] persname = pers_name.split(",");
    	
		if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
			List<String> hp_infos_org =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
			List<String> bacodes = new ArrayList<String>();
			List<String> codeperss = new ArrayList<String>();
			
			for(String item : hp_infos_org){
				List<String> hp_info =  Arrays.asList(item.split("_"));
				if(hp_info!=null&&hp_info.size()>0&&!"*".equals(hp_info.get(0))){ bacodes.add(hp_info.get(0));} 
				if(hp_info!=null&&hp_info.size()>1&&!"*".equals(hp_info.get(1))){ codeperss.add(hp_info.get(1));} 
			}

			StringBuffer toHp = new StringBuffer(); 
			StringBuffer toName = new StringBuffer(); 
			documentDao dao       = new documentDao();    	
			for(String item : bacodes){
				Map map_bar = new HashMap();
				map_bar.put("dm_bacode_single", item);
				map_bar.put("nstart", 0);
				map_bar.put("nend", 10);
	    		List<Map> result = dao.dmbacode(map_bar,true);     		

	    		if(result!= null && result.size()>0){
		    		String hp = (String) result.get(0).get("pers_hp");
		    		String nm = (String) result.get(0).get("pers_name");
					if(hp!=null && !"".equals(hp)){
						if(toHp.length()>0) toHp.append(","); toHp.append(hp);
						if(toName.length()>0) toName.append(","); toName.append(nm);
					}
	    		}
			}   
			basicDao dao2=new basicDao();
			for(String item : codeperss){
				Map map_code = new HashMap();
				map_code.put("pCode",item);
				List<Map> result = dao2.memInfo(map_code,true);     		
				
				if(result!= null && result.size()>0){
					String hp = (String) result.get(0).get("pers_hp");
					String nm = (String) result.get(0).get("pers_name");
					if(hp!=null && !"".equals(hp)){
						if(toHp.length()>0) toHp.append(","); toHp.append(hp);
						if(toName.length()>0) toName.append(","); toName.append(nm);
					}
				}
			}   
			String tohp = toHp.toString();
			String tonm = toName.toString();
	    	pershp = tohp.split(","); 
	    	persname = tonm.split(",");
		}
    	
    	for(int i=0;i<pershp.length;i++){
    		if(pershp[i].length()>9){
    			map.put("pers_hp",                   pershp[i]); 
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                request.getParameter("send_phone"));      		
        		map.put("register",                  session.getAttribute("G_NAME"));
        		map.put("etc1",                      session.getAttribute("G_ID"));
        		map.put("etc2",                      session.getAttribute("G_BRAN"));
        		map.put("etc3",                      persname[i]);
        		System.out.println("pers_name=========>"+persname[i]);
        		map.put("umscnt"		, i+1);
        		documentDao dao = new documentDao();

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
	
	/* 발송 대장 바코드 검색 */
	public ActionForward dmbacode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();		
		List<String> list = new ArrayList<String>();

		String t_bacode=request.getParameter("t_bacode");	
		String [] bacode=t_bacode.split(",");		
		String dm_bacode="";
		
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
		
		ncount = bacode.length;

		int ntotpage = (ncount/nrows)+1;
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""   +	npage	 +"\",");		
		result.append("\"total\":\""   +    ntotpage +"\",");
		result.append("\"records\":\"" +	ncount	 +"\",");
		result.append("\"rows\":[");				
			
		for(int i=0;i<bacode.length;i++){
			if(dm_bacode.length()==0) dm_bacode="'"+bacode[i]+"'";
			else dm_bacode +=",'"+bacode[i]+"'";
		}
			
//			String code_book_kind     =  bacode[i].substring(0,1);
//    		String dm_pers_code       =  bacode[i].substring(1,10);
//    		String dm_print_yymm      =  bacode[i].substring(10,14);
//    		String dm_print_yymm_seq  =  bacode[i].substring(14,15);   
//    		
//    		map.put("code_book_kind",                code_book_kind);
//    		map.put("dm_pers_code",                  dm_pers_code);
//    		map.put("dm_print_yymm",                 dm_print_yymm);
//    		map.put("dm_print_yymm_seq",             dm_print_yymm_seq);
    		
    		map.put("dm_bacode", dm_bacode);
    		
    		documentDao dao       = new documentDao();    	
    		List<Map> dmbacode    = dao.dmbacode(map);     		

    	for(int i=0;i<dmbacode.size();i++){
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( dmbacode.get(i).get("dm_pers_code")	           == null ? ""   :   dmbacode.get(i).get("dm_pers_code"))              +"\",");
			result.append("\"" + ( dmbacode.get(i).get("dm_name")	               == null ? ""   :   dmbacode.get(i).get("dm_name"))                   +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_book_kind")           == null ? ""   :   dmbacode.get(i).get("code_book_kind") )           +"\",");
			result.append("\"" + ( dmbacode.get(i).get("dm_print_yymm")	           == null ? ""   :   dmbacode.get(i).get("dm_print_yymm"))             +"\",");
			result.append("\"" + ( dmbacode.get(i).get("dm_print_yymm_seq")        == null ? ""   :   dmbacode.get(i).get("dm_print_yymm_seq"))         +"\",");
			result.append("\"" + ( dmbacode.get(i).get("pers_name")	               == null ? ""   :   dmbacode.get(i).get("pers_name"))                 +"\",");
			result.append("\"" + ( dmbacode.get(i).get("lic_no")                   == null ? ""   :   dmbacode.get(i).get("lic_no") )                   +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_member_name")         == null ? ""   :   dmbacode.get(i).get("code_member_name") )         +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_member")	           == null ? ""   :   dmbacode.get(i).get("code_member"))               +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_call_name")           == null ? ""   :   dmbacode.get(i).get("code_call_name") )           +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_call")		           == null ? ""   :   dmbacode.get(i).get("code_call") )                +"\",");
			result.append("\"" + ( dmbacode.get(i).get("company_name")	           == null ? ""   :   dmbacode.get(i).get("company_name") )             +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_post")	               == null ? ""   :   dmbacode.get(i).get("code_post") )                +"\",");
			result.append("\"" + ( dmbacode.get(i).get("pers_add")	               == null ? ""   :   dmbacode.get(i).get("pers_add") )                 +"\",");
			result.append("\"" + ( dmbacode.get(i).get("pers_hp")	               == null ? ""   :   dmbacode.get(i).get("pers_hp") )                  +"\",");
			result.append("\"" + ( dmbacode.get(i).get("email")	                   == null ? ""   :   dmbacode.get(i).get("email") )                    +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_bran_name")           == null ? ""   :   dmbacode.get(i).get("code_bran_name") )           +"\",");
			result.append("\"" + ( dmbacode.get(i).get("code_bran")		           == null ? ""   :   dmbacode.get(i).get("code_bran") )                +"\",");
			result.append("\"" + ( dmbacode.get(i).get("rece_yn_name")             == null ? ""   :   dmbacode.get(i).get("rece_yn_name") )             +"\",");
			result.append("\"" + ( dmbacode.get(i).get("rece_yn")		           == null ? ""   :   dmbacode.get(i).get("rece_yn") )                  +"\",");
			result.append("\"" + ( dmbacode.get(i).get("dm_creater")		       == null ? ""   :   dmbacode.get(i).get("dm_creater") )               +"\",");
			result.append("\"" + ( dmbacode.get(i).get("dm_key")		           == null ? ""   :   dmbacode.get(i).get("dm_key") )                   +"\"");
			result.append("]}");
			
		}		
		result.append("]}");    	
	
		request.setAttribute("result",result);		
		return (mapping.findForward("ajaxout"));    	
	}	
	
	/* 발송대장 (발송구분 수정) */
	public ActionForward update_rece_yn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();	
		
		String t_bacode=request.getParameter("t_bacode");
		String [] bacode=t_bacode.split(",");	

		for(int i=0;i<bacode.length;i++){
    		String code_book_kind     =  bacode[i].substring(0,1);
    		String dm_pers_code       =  bacode[i].substring(1,10);
    		String dm_print_yymm      =  bacode[i].substring(10,14);  
    		String dm_print_yymm_seq  =  bacode[i].substring(14,15); 
    		map.put("code_book_kind",                code_book_kind);
    		map.put("dm_pers_code",                  dm_pers_code);
    		map.put("dm_print_yymm",                 dm_print_yymm);
    		map.put("dm_print_yymm_seq",             dm_print_yymm_seq);
    		map.put("rece_yn",                       request.getParameter("rece_yn"));   		
    		
    		documentDao dao = new documentDao();
    		int result = dao. updaterece_yn(map);    		
    	} 
		
		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 수정했습니다.";	
		}else{			
			resetToken(request);
			errMsg="수정했습니다.";
		}	
		
		request.setAttribute("errMsg", errMsg);					
		return mapping.findForward("updaterece_yn_ok");	
	} 
	
	/* dmList 생성 */
	public ActionForward insert_dmlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session=request.getSession();
		Map map = new HashMap();
		documentDao dao = new documentDao();
		
		map.put("sendgubun",                           request.getParameter("sendgubun"));
		map.put("code_book_kind",                      request.getParameter("code_book_kind"));
		map.put("book_start_dt",                       request.getParameter("book_start_dt"));
		map.put("book_end_dt",                         request.getParameter("book_end_dt"));	
		map.put("register",                            session.getAttribute("G_NAME"));
		
		if(request.getParameter("isSelect")!=null){
						
			/*  전체 (구분코드 :"") */			
			if(request.getParameter("sendgubun").equals(""))  {			    		
				int  n = dao.insertdmall(map,true); 
				String msg =(n>0)?n + "건 정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
				request.setAttribute("msg",msg);
				if(n > 0   ){			
					return (mapping.findForward("dminsert_ok"));
				}else{
					return (mapping.findForward("dminsert_fail"));
				}	   	
			}			
			/*  회원(구분코드 :1) */
			if(request.getParameter("sendgubun").equals("1"))  {		
				int  n = dao.insertdmmem(map,true);
				String msg =(n>0)?n + "건 정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
				request.setAttribute("msg",msg);
				if(n > 0   ){			
					return (mapping.findForward("dminsert_ok"));
				}else{
					return (mapping.findForward("dminsert_fail"));
				}	   	
			}	
			/*  회원+구독(구분코드 :2) */
			if(request.getParameter("sendgubun").equals("2"))  {	
				int  n = dao.insertdmgoo(map,true);
				String msg =(n>0)?n + "건 정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
				request.setAttribute("msg",msg);
				if(n > 0   ){			
					return (mapping.findForward("dminsert_ok"));
				}else{
					return (mapping.findForward("dminsert_fail"));
				}	   	
			}
			/*  회원+섭외(구분코드 :3) */
			if(request.getParameter("sendgubun").equals("3"))  {	
				int  n = dao.insertdmsup(map,true);	
				String msg =(n>0)?n + "건 정상적으로 등록 되었습니다.":"등록 실패하였습니다.";
				request.setAttribute("msg",msg);
				if(n > 0   ){			
					return (mapping.findForward("dminsert_ok"));
				}else{
					return (mapping.findForward("dminsert_fail"));
				}	   	
			}	   	
		
		}		
		return mapping.findForward("insertdmlist_ok");	
	}	
	
	/* 발송 대장 라벨 출력 */
	public ActionForward dmwriteall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		documentDao dao = new documentDao();
		
		if(request.getParameter("pers_name")           !=null){
			String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");		
			map.put("pers_name",                   pers_name);}			
		if(request.getParameter("code_book_kind")      !=null){
			map.put("code_book_kind",              request.getParameter("code_book_kind"));}	
		if(request.getParameter("sendgubun")           !=null){
			map.put("sendgubun",                   request.getParameter("sendgubun"));}		
		if(request.getParameter("book_start_dt")       !=null){	
			String book_start_dt =  request.getParameter("book_start_dt");
			//book_start_dt        =  book_start_dt.substring(2,6);
			map.put("book_start_dt",               book_start_dt);}				
		if(request.getParameter("rece_yn")             !=null){
			map.put("rece_yn",                     request.getParameter("rece_yn"));}
		
		if(request.getParameter("dm_creater")          !=null){
			String dm_creater = URLDecoder.decode(request.getParameter("dm_creater"),"UTF-8");		
			map.put("dm_creater",                  dm_creater);}
		if(request.getParameter("dm_print_yymm_seq")          !=null){
			String dm_print_yymm_seq = request.getParameter("dm_print_yymm_seq");		
			map.put("dm_print_yymm_seq",                  dm_print_yymm_seq);}

		List<Map> count           = dao.dmwritecnt(map);
		int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
		
		request.setAttribute("ncount",    ncount);
		
		/*request.setAttribute("pers_name",		request.getParameter("pers_name"));
		request.setAttribute("code_book_kind",  request.getParameter("code_book_kind"));
		request.setAttribute("sendgubun",       request.getParameter("sendgubun"));
		request.setAttribute("book_start_dt",   request.getParameter("book_start_dt"));
		request.setAttribute("book_end_dt",     request.getParameter("book_end_dt"));
		request.setAttribute("rece_yn",         request.getParameter("rece_yn"));
		request.setAttribute("dm_creater",      request.getParameter("dm_creater"));*/
		
		return (mapping.findForward("dmwriteForm"));
	}	
	
	public ActionForward dmwriteLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		documentDao dao = new documentDao();
		String doc_seq = "";
		
		if(request.getParameter("nstart"	)!=null) map.put("nstart",       request.getParameter("nstart"	));
		if(request.getParameter("nend"		)!=null) map.put("nend"  ,       request.getParameter("nend"	));		
		   
		if(request.getParameter("pers_name")           !=null){		
			String pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");			
			map.put("pers_name",                   pers_name);}			
		if(request.getParameter("code_book_kind")      !=null){
			map.put("code_book_kind",              request.getParameter("code_book_kind"));}	
		if(request.getParameter("sendgubun")           !=null){
			map.put("sendgubun",                   request.getParameter("sendgubun"));}		
		if(request.getParameter("book_start_dt")       !=null){	
			String book_start_dt =  request.getParameter("book_start_dt");
			book_start_dt        =  book_start_dt.substring(2,6);
			map.put("book_start_dt",               book_start_dt);}		
		if(request.getParameter("book_end_dt")         !=null){	 
			String book_end_dt =  request.getParameter("book_end_dt");			
			book_end_dt          =  book_end_dt.substring(2,6);			
			map.put("book_end_dt",                 book_end_dt);}
		if(request.getParameter("rece_yn")             !=null){
			map.put("rece_yn",                     request.getParameter("rece_yn"));}		
		if(request.getParameter("dm_creater")          !=null){
			String dm_creater = URLDecoder.decode(request.getParameter("dm_creater"),"UTF-8");		
			map.put("dm_creater",                  dm_creater);}		
		
		//화면에 출력할 값을 검색한다.-------------------------------**조회
		List<Map> list=dao.dmwriteList(map);
		
		for( int i=0; i<list.size(); i++) {
			if( i > 0) doc_seq+=",";			
			doc_seq+="'"+list.get(i).get("dm_key")+"'";
		}
		
		request.setAttribute("btnCnt",     request.getParameter("btnCnt"));
		request.setAttribute("doc_seq",    doc_seq);
		return (mapping.findForward("dmwriteLink"));
	}
	
}
	
