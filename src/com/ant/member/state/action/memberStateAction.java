package com.ant.member.state.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.member.deposit.dao.memberDepositDao;
import com.ant.member.state.dao.memberStateDao;
import com.ant.common.util.CommonUtil;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/*
 * 프로그램명 : memberStateAction (회원 > 현황)
 * 제작자 : 김성훈
 * 제작일 : 2012.09.10
 * 설   명 : 회원>현황 이하 Action 모음
 */



public class memberStateAction extends DispatchAction {
	protected static Logger logger = Logger.getRootLogger();
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";


	/*
	 * 작업명 : Home>회원>현황 메뉴출력
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.19
	 * 작   업 : memberStateForm	회원현황 메뉴 선탟
	 */
	public ActionForward memberState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		request.setAttribute("menu", "memberState");
		return (mapping.findForward("memberState"));
	}
	
	/*
	 * 작업명 : Home>회원>현황>근무처 분류별 (포워딩, 조회) 메뉴 .2
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.10
	 * 작  업 : companyState		실 조회 화면으로 포워딩
	 *		    companyStateList	리스트 조회
	 *			companyStateExcel	엑셀 출력
	 */
	public ActionForward companyState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "companyStateList");
		return (mapping.findForward("companyStateList"));
	}
	
	public ActionForward companyStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao();
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
		
			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
					
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getCompanyStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
					
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getCompanyStateList(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
						
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("detcodename")	== null ? "" : memberlist.get(i).get("detcodename") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")		== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	//근무처 분류별 엑셀출력
	public void companyStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="companyStateList"; //프로그램명
		String s_name		="근무처분류별현황"; //엑셀 시트명
		
		String header_e[]	={"detcodename", "col1", "col2", "totcount"}; //헤더 영문
		String header_k[]	={"대분류", "집단급식소", "비집단급식소", "합계"}; //헤더 국문
		int	   c_size[]		={15, 15, 15, 15};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getCompanyStateList(map);

		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}



	
	/* 작업명 : Home>회원>현황>운영형태별 (포워딩, 조회) 메뉴 .2
	 * 작업자 : 이정기
	 * 작업일 : 2012.09.10
	 * 작  업 : codeUseMember		실 조회 화면으로 포워딩
	 *		    codeUseMemberList	분류별 현황 리스트 조회
	 *			codeUseMemberExcel	엑셀 출력
	 */
	public ActionForward codeUseMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "codeUseMemberList");
		return (mapping.findForward("codeUseMemberList"));
		
	}
	public ActionForward codeUseMemberList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
		

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
		
			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				

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
			
			List<Map> codeUseMember=dao.getCodeUseMemberList(map);
			
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""  +	"1"	+"\",");		
			result.append("\"total\":\""  + "1"+"\",");
			result.append("\"records\":\""+	"2"	+"\",");
			result.append("\"rows\":[");		
			
			for(int i=0; i<codeUseMember.size(); i++){
				
				String out = "";
				if(i == 0) out="전체";
				else out="회원";
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( codeUseMember.get(i).get("GUBUN")	== null ? "" : out ) +"\",");
				result.append("\"" + ( codeUseMember.get(i).get("VAR1")		== null ? "" : codeUseMember.get(i).get("VAR1") ) +"\",");
				result.append("\"" + ( codeUseMember.get(i).get("VAR2")		== null ? "" : codeUseMember.get(i).get("VAR2") ) +"\",");
				result.append("\"" + ( codeUseMember.get(i).get("VAR3")		== null ? "" : codeUseMember.get(i).get("VAR3") ) +"\",");
				result.append("\"" + ( codeUseMember.get(i).get("VAR4")		== null ? "" : codeUseMember.get(i).get("VAR4") ) +"\",");
				result.append("\"" + ( codeUseMember.get(i).get("SUM")		== null ? "" : codeUseMember.get(i).get("SUM") ) +"\"");
				result.append("]}");						
				
				
			}	
			
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));

	}

	//운영평태별 엑셀출력
	public void codeUseMemberExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="codeUseMemberList"; //프로그램명
		String s_name		="운영형태별"; //엑셀 시트명

		String header_e[]={"GUBUN", "VAR1", "VAR2", "VAR3", "VAR4", "SUM"}; //헤더 영문
		String header_k[]={"구분", "직영", "준직영", "위탁", "비집단급식소", "합계"}; //헤더 국문
		int c_size[]={15, 15, 15, 15, 15, 15};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getCodeUseMemberList(map);

		memberlist.get(0).put("GUBUN", "전체");
		memberlist.get(1).put("GUBUN", "회원");
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	/*
	 * 작업명 : Home>회원>현황>위탁업체 개인별
	 * 작업자 : 윤석희
	 * 작업일 : 2012.09.10
	 * 작  업 : subCodePerson		포워딩
	 *		    subCodePersonList	리스트 조회
	 *		    subCodePersonExcel	엑셀 출력
	 */
	public ActionForward subCodePerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "subCodePersonList");
		return (mapping.findForward("subCodePersonList"));
	}

	public ActionForward subCodePersonList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			
			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getSubCodePersonCount(map);
			int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getSubCodePersonList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("trust_name"	)== null ? "" : memberlist.get(i).get("trust_name"	))+"\",");
				result.append("\""+(memberlist.get(i).get("pers_count"	)== null ? "" : memberlist.get(i).get("pers_count"	))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//위탁업체개인별 엑셀출력
	public void subCodePersonExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="subCodePersonList"; //프로그램명
		String s_name		="위탁업체개인별"; //엑셀 시트명
		 
		String header_e[]	={"trust_name", "pers_count"}; //헤더 영문
		String header_k[]	={"소속위탁업체", "인원"}; //헤더 국문
		int    c_size[]		={30, 15};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getSubCodePersonList(map);
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	/*
	 * 작업명 : Home>회원>현황>산하단체 소속별 (포워딩, 조회) 메뉴.12
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.10
	 * 작  업 : subCodeState		실 조회 화면으로 포워딩
	 *		    subCodeStateList	리스트 조회
	 *		    subCodeStateExcel	엑셀 출력
	 */
	public ActionForward subCodeState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "subCodeStateList");
		return (mapping.findForward("subCodeStateList"));
	}

	public ActionForward subCodeStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			
			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getSubCodeStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getSubCodeStateList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("code_great"	)== null ? "" : memberlist.get(i).get("code_great"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_great")== null ? "" : memberlist.get(i).get("h_code_great"	))+"\",");
				result.append("\""+(memberlist.get(i).get("code_small"	)== null ? "" : memberlist.get(i).get("code_small"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_small")== null ? "" : memberlist.get(i).get("h_code_small"	))+"\",");
				result.append("\""+(memberlist.get(i).get("col1")		== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col2")		== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col3")		== null ? "" : memberlist.get(i).get("col3"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col4")		== null ? "" : memberlist.get(i).get("col4"			))+"\",");
				result.append("\""+(memberlist.get(i).get("totcount")	== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//위탁업체개인별 엑셀출력
	public void subCodeStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="subCodeStateList"; //프로그램명
		String s_name		="산하단체 소속별"; //엑셀 시트명
		
		String header_e[]	={"h_code_great", "h_code_small", "col1", "col2", "col3", "col4", "totcount"}; //헤더 영문
		String header_k[]	={"대분류", "소분류", "전국영양교사회", "전국학교영양사회", "전국교정영양사회", "전국당뇨병영양사회", "합계"}; //헤더 국문
		int    c_size[]		={30, 30, 20, 20, 20, 20, 20};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getSubCodeStateList(map);
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	

	/*
	 * 작업명 : Home>회원>현황>피 급식자별 (페이징용 카운터, 조회) 메뉴.13
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.05
	 * 작  업 : mealState		포워딩
	 *			mealStateList	현황 리스트 조회
	 *			mealStateExcel	엑셀출력
	 */
	public ActionForward mealState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "mealStateList");
		return (mapping.findForward("mealStateList"));
	}

	public ActionForward mealStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){

			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getMealStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMealStateList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("code_great"	)== null ? "" : memberlist.get(i).get("code_great"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_great")== null ? "" : memberlist.get(i).get("h_code_great"	))+"\",");
				result.append("\""+(memberlist.get(i).get("code_small"	)== null ? "" : memberlist.get(i).get("code_small"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_small")== null ? "" : memberlist.get(i).get("h_code_small"	))+"\",");
				result.append("\""+(memberlist.get(i).get("col1")		== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col2")		== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col3")		== null ? "" : memberlist.get(i).get("col3"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col4")		== null ? "" : memberlist.get(i).get("col4"			))+"\",");
				result.append("\""+(memberlist.get(i).get("totcount")	== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//피급식자별 엑셀출력
	public void mealStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="mealStateList"; //프로그램명
		String s_name		="피급식자별 현황"; //엑셀 시트명
		
		String header_e[]	={"h_code_great", "h_code_small", "col1", "col2", "col3", "col4", "totcount"}; //헤더 영문
		String header_k[]	={"대분류", "소분류", "아침", "점심", "저녁", "야식", "합계"}; //헤더 국문
		int    c_size[]		={30, 30, 20, 20, 20, 20, 20};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getMealStateList(map);
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	
	/*
	 * 작업명 : Home>회원>현황>영양사 연봉별 (페이징용 카운터, 조회) 메뉴.14
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.05
	 * 작  업 : yearPayState		포워딩
	 *			yearPayStateList	리스트 조회
	 *			yearPayStateExcel
	 */
	public ActionForward yearPayState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "yearPayStateList");
		return (mapping.findForward("yearPayStateList"));
	}

	public ActionForward yearPayStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			
			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getYearPayStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getYearPayStateList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("code_great"	)== null ? "" : memberlist.get(i).get("code_great"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_great")== null ? "" : memberlist.get(i).get("h_code_great"	))+"\",");
				result.append("\""+(memberlist.get(i).get("code_small"	)== null ? "" : memberlist.get(i).get("code_small"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_small")== null ? "" : memberlist.get(i).get("h_code_small"	))+"\",");
				result.append("\""+(memberlist.get(i).get("col1")		== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col2")		== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col3")		== null ? "" : memberlist.get(i).get("col3"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col4")		== null ? "" : memberlist.get(i).get("col4"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col5")		== null ? "" : memberlist.get(i).get("col5"			))+"\",");
				result.append("\""+(memberlist.get(i).get("totcount")	== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//영양사연봉별 엑셀출력
	public void yearPayStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="yearPayStateList"; //프로그램명
		String s_name		="영양사연봉별 현황"; //엑셀 시트명
		
		String header_e[]	={"h_code_great", "h_code_small", "col1", "col2", "col3", "col4", "col5", "totcount"}; //헤더 영문
		String header_k[]	={"대분류", "소분류", "1100~1199", "1200~1399", "1400~1599", "1600~1799", "1800~1999", "합계"}; //헤더 국문
		int    c_size[]		={30, 30, 20, 20, 20, 20, 20, 20};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getYearPayStateList(map);
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	

	/*
	 * 작업명 : Home>회원>현황>급식 종사자별 (페이징용 카운터, 조회) 메뉴.15
	 * 작업자 : 김성훈
	 * 작업일 : 2012..
	 * 작  업 : mealEmployState		포워드(없어서 보류)
	 *			mealEmployStateList	리스트 조회(없어서 보류)
	 *			mealEmployStateExcel엑셀출력(없어서 보류)
	 */
	public ActionForward mealEmployState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "mealEmployStateList");
		return (mapping.findForward("mealEmployStateList"));
	}

	public ActionForward mealEmployStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*없는것 보류*/
		return (mapping.findForward("ajaxout"));
	}

	public ActionForward mealEmployStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*없는것 보류*/
		return (mapping.findForward("ajaxout"));
	}

	/*
	 * 작업명 : Home>회원>현황>영양사 면허수당별 (페이징용 카운터, 조회) 메뉴.16
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.06
	 * 작  업 : licPayState			포워드
	 *			licPayStateList		리스트 조회
	 *			licPayStateExcel	엑셀출력
	 */
	public ActionForward licPayState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "licPayStateList");
		return (mapping.findForward("licPayStateList"));
	}

	public ActionForward licPayStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			
			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getLicPayStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getLicPayStateList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("code_great"	)== null ? "" : memberlist.get(i).get("code_great"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_great")== null ? "" : memberlist.get(i).get("h_code_great"	))+"\",");
				result.append("\""+(memberlist.get(i).get("code_small"	)== null ? "" : memberlist.get(i).get("code_small"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_small")== null ? "" : memberlist.get(i).get("h_code_small"	))+"\",");
				result.append("\""+(memberlist.get(i).get("col1")		== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col2")		== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col3")		== null ? "" : memberlist.get(i).get("col3"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col4")		== null ? "" : memberlist.get(i).get("col4"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col5")		== null ? "" : memberlist.get(i).get("col5"			))+"\",");
				result.append("\""+(memberlist.get(i).get("totcount")	== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//영양사 면허수당별 엑셀출력
	public void licPayStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="licPayStateList"; //프로그램명
		String s_name		="영양사 면허수당별 현황"; //엑셀 시트명
		
		String header_e[]	={"h_code_great", "h_code_small", "col1", "col2", "col3", "col4", "col5", "totcount"}; //헤더 영문
		String header_k[]	={"대분류", "소분류", "20000~29999", "30000~39999", "40000~49999", "50000~59999", "60000~69999", "합계"}; //헤더 국문
		int    c_size[]		={30, 30, 20, 20, 20, 20, 20, 20};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getLicPayStateList(map);
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}

	/*
	 * 작업명 : Home>회원>현황>1식 재료비별 (페이징용 카운터, 조회) 메뉴.17
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.06
	 * 작  업 : companyMealState		포워드
	 *			companyMealStateList	리스트 조회
	 *			companyMealStateExcel	엑셀출력
	 */
	public ActionForward companyMealState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "companyMealStateList");
		return (mapping.findForward("companyMealStateList"));
	}

	public ActionForward companyMealStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			
			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getCompanyMealStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getCompanyMealStateList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("code_great"	)== null ? "" : memberlist.get(i).get("code_great"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_great")== null ? "" : memberlist.get(i).get("h_code_great"	))+"\",");
				result.append("\""+(memberlist.get(i).get("code_small"	)== null ? "" : memberlist.get(i).get("code_small"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_small")== null ? "" : memberlist.get(i).get("h_code_small"	))+"\",");
				result.append("\""+(memberlist.get(i).get("col1")		== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col2")		== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col3")		== null ? "" : memberlist.get(i).get("col3"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col4")		== null ? "" : memberlist.get(i).get("col4"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col5")		== null ? "" : memberlist.get(i).get("col5"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col6")		== null ? "" : memberlist.get(i).get("col6"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col7")		== null ? "" : memberlist.get(i).get("col7"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col8")		== null ? "" : memberlist.get(i).get("col8"			))+"\",");
				result.append("\""+(memberlist.get(i).get("totcount")	== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//1식 재료비별별 엑셀출력
	public void companyMealStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="companyMealStateList"; //프로그램명
		String s_name		="1식 재료비별 현황"; //엑셀 시트명
		
		String header_e[]	={"h_code_great", "h_code_small", "col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "totcount"}; //헤더 영문
		String header_k[]	={"대분류", "소분류",
							  "500~1499","1500~2499","2500~3499","3500~4499","4500~6499","6500~7500","7501~8500","8501~10000",
							  "합계"}; //헤더 국문
		int    c_size[]		={30, 30, 20, 20, 20, 20, 20, 20, 20, 20, 20};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getCompanyMealStateList(map);
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}

	/*
	 * 작업명 : Home>회원>현황>겸직별 (페이징용 카운터, 조회) 메뉴.18
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.06
	 * 작  업 : multyState			포워딩
	 *			multyStateList		리스트 조회
	 *			multyStateExcel		엑셀출력
	 */
	public ActionForward multyState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "multyStateList");
		return (mapping.findForward("multyStateList"));
	}

	public ActionForward multyStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			
			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getMultyStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMultyStateList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("code_great"	)== null ? "" : memberlist.get(i).get("code_great"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_great")== null ? "" : memberlist.get(i).get("h_code_great"	))+"\",");
				result.append("\""+(memberlist.get(i).get("code_small"	)== null ? "" : memberlist.get(i).get("code_small"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_small")== null ? "" : memberlist.get(i).get("h_code_small"	))+"\",");
				result.append("\""+(memberlist.get(i).get("col1")		== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col2")		== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\""+(memberlist.get(i).get("totcount")	== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//겸직별 엑셀출력
	public void multyStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="multyStateList"; //프로그램명
		String s_name		="겸직별 현황"; //엑셀 시트명
		
		String header_e[]	={"h_code_great", "h_code_small", "col1", "col2", "totcount"}; //헤더 영문
		String header_k[]	={"대분류", "소분류",
							  "겸직", "비겸직",
							  "합계"}; //헤더 국문
		int    c_size[]		={30, 30, 20, 20, 20};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		//검색
		List<Map> memberlist=dao.getMultyStateList(map);
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}


	/*
	 * 작업명 : Home>회원>현황>급식인원별 (페이징용 카운터, 조회) 메뉴.13
	 * 작업자 : 김성훈
	 * 작업일 : 2012..
	 * 작  업 : mealNumberState			포워드
	 *			mealNumberStateList		리스트 조회
	 *			mealNumberStateExcel	엑셀출력
	 */
	public ActionForward mealNumberState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "mealNumberStateList");
		return (mapping.findForward("mealNumberStateList"));
	}

	public ActionForward mealNumberStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 

		
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			
			//조회 조건용 조회시작일과 종료일

			//조회 조건용 조회시작일과 종료일
			if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
			if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
			
				
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String sidx = request.getParameter("sidx");	// 정렬값(기본값=) pers_name
			String sord = request.getParameter("sord");	// 정렬(기본값=) asc, desc
			
			//넘겨받은 값으로 한 화면 표시할 개수를 구한다.
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
	
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
			List<Map> count = dao.getMealCountStateCount(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist = null;
			if(request.getParameter("company_count"	)!=null) {
				String company_count = request.getParameter("company_count"	)+"";
				if("1".equals(company_count))		memberlist=dao.getMealCountStateList_1(map);
				else if("2".equals(company_count))	memberlist=dao.getMealCountStateList_2(map);
				else if("3".equals(company_count))	memberlist=dao.getMealCountStateList_3(map);
				else if("4".equals(company_count))	memberlist=dao.getMealCountStateList_4(map);
			}else									memberlist=dao.getMealCountStateList(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
	
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
	
			for(int i=0;i<memberlist.size();i++){
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\""+(memberlist.get(i).get("code_bran"	)== null ? "" : memberlist.get(i).get("code_bran"		))+"\",");
				result.append("\""+(memberlist.get(i).get("h_code_bran")== null ? "" : memberlist.get(i).get("h_code_bran"	))+"\",");
				result.append("\""+(memberlist.get(i).get("col1")		== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col2")		== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col3")		== null ? "" : memberlist.get(i).get("col3"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col4")		== null ? "" : memberlist.get(i).get("col4"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col5")		== null ? "" : memberlist.get(i).get("col5"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col6")		== null ? "" : memberlist.get(i).get("col6"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col7")		== null ? "" : memberlist.get(i).get("col7"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col8")		== null ? "" : memberlist.get(i).get("col8"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col9")		== null ? "" : memberlist.get(i).get("col9"			))+"\",");
				result.append("\""+(memberlist.get(i).get("col10")		== null ? "" : memberlist.get(i).get("col10"		))+"\",");
				result.append("\""+(memberlist.get(i).get("totcount")	== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}

		return (mapping.findForward("ajaxout"));
	}
	//급식인원별 엑셀출력
	public void mealNumberStateExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberStateDao dao=new memberStateDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID

		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="mealNumberStateList"; //프로그램명
		String s_name		="급식인원별 현황"; //엑셀 시트명
		
		String header_e[]	={"h_code_bran", "col1", "col2", "col3", "col4", "col5", "col6", "col7", "col8", "col9", "col10", "totcount"}; //헤더 영문
		String header_k[]	={"지회명",
	            			  "1~50","51~100","101~200","201~300","301~500","501~700","701~1000","1001~1500",
	            			  "1501~2000","2001~3000",
							  "합계"}; //헤더 국문
		int    c_size[]		={30, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};  //열 넓이를 위한 배열
		
		//검색조건
		map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		map.put("enddate"	, request.getParameter("enddate"));		//검색기간종료
		

		//검색
		List<Map> memberlist = null;
		
		if(request.getParameter("company_count"	)!=null) {
			String company_count = request.getParameter("company_count"	)+"";
			
			if("1".equals(company_count))		{
				memberlist=dao.getMealCountStateList_1(map);
				s_name+="_아침";
			}
			else if("2".equals(company_count))	{
				memberlist=dao.getMealCountStateList_2(map);
				s_name+="_점심";
			}
			else if("3".equals(company_count))	{
				memberlist=dao.getMealCountStateList_3(map);
				s_name+="_저녁";
			}
			else if("4".equals(company_count))	{
				memberlist=dao.getMealCountStateList_4(map);
				s_name+="_야식";
			}else	{
				memberlist=dao.getMealCountStateList(map);
				s_name+="_전체";
			}
		}else	{
			memberlist=dao.getMealCountStateList(map);
			s_name+="_전체";
		}
		
		//여기까지 변경--------------------------------------------------
		
		
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		CommonUtil.makeExcelFile(memberlist, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	

	
	
	
	
	
	/*
	 * 작업명 : Home>회원>현황>고용형태별 직급별 인원수 집계
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.09
	 */
	public ActionForward jLevelState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "jLevelStateList");
		return (mapping.findForward("jLevelStateList"));
	}

	public ActionForward jLevelStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
			
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getJLevelStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getJLevelStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("tempcd1")			== null ? "" : memberlist.get(i).get("tempcd1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename")	== null ? "" : memberlist.get(i).get("detcodename") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7")				== null ? "" : memberlist.get(i).get("col7") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8")				== null ? "" : memberlist.get(i).get("col8") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	public void jLevelStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="jLevelStateList"; //프로그램명
		String s_name="고용형태별(직급)현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"tempcd1", "detcodename", "col1", "col2", "col3","col4","col5","col6","col7","col8","totcount"}; //헤더 영문
		String header_k[]={"대분류", "소분류","5급", "6급", "7급", "8급","9급","10급","무급","기타","합계"}; //헤더 국문
		int c_size[]={13,13,12,12,12,12,12,12,12,12,12};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 0);
		map.put("nend", 100000);
		//여기까지 변경
		List<Map>jLevelStateList=dao.getJLevelStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(jLevelStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	/*
	 * 작업명 : Home>회원>현황>고용형태별 직렬별 인원수 집계
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.09
	 */
	public ActionForward jLineState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "jLineStateList");
		return (mapping.findForward("jLineStateList"));
	}

	public ActionForward jLineStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getJLineStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getJLineStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("tempcd1")			== null ? "" : memberlist.get(i).get("tempcd1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename")	== null ? "" : memberlist.get(i).get("detcodename") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7")				== null ? "" : memberlist.get(i).get("col7") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8")				== null ? "" : memberlist.get(i).get("col8") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9")				== null ? "" : memberlist.get(i).get("col9") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	public void jLineStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="jLineStateList"; //프로그램명
		String s_name="고용형태별(직렬)현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"tempcd1", "detcodename", "col1", "col2", "col3","col4","col5","col6","col7","col8","col9","totcount"}; //헤더 영문
		String header_k[]={"대분류", "소분류","일용직", "보건직", "식품위생직", "기능직","상용직","영양교사직","학교회계직","무직","기타","합계"}; //헤더 국문
		int c_size[]={13,13,12,12,12,12,12,12,12,12,12,12};  //열 넓이를 위한 배열		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 0);
		map.put("nend", 100000);
		//여기까지 변경
		List<Map>jLineStateList=dao.getJLineStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(jLineStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	/*
	 * 작업명 : Home>회원>현황>고용형태별 자격증소지별 인원수 집계
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.09
	 */
	public ActionForward certifiState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "certifiStateList");
		return (mapping.findForward("certifiStateList"));
	}

	public ActionForward certifiStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getCertifiStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getCertifiStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("tempcd1")			== null ? "" : memberlist.get(i).get("tempcd1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename")	== null ? "" : memberlist.get(i).get("detcodename") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");				
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	public void certifiStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="certifiStateList"; //프로그램명
		String s_name="자격증소지별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"tempcd1", "detcodename", "col1", "col2", "col3","totcount"}; //헤더 영문
		String header_k[]={"대분류", "소분류","급식경영", "임상영양", "산업보건","합계"}; //헤더 국문
		int c_size[]={13,13,12,12,12,12};  //열 넓이를 위한 배열		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 0);
		map.put("nend", 100000);
		//여기까지 변경
		List<Map>certifiStateList=dao.getCertifiStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(certifiStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	/*
	 * 작업명 : Home>회원>현황>고용형태별 최종학위별 인원수 집계
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.09
	 */
	public ActionForward univerState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "univerStateList");
		return (mapping.findForward("univerStateList"));
	}

	public ActionForward univerStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getUniverStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getUniverStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("tempcd1")			== null ? "" : memberlist.get(i).get("tempcd1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename")	== null ? "" : memberlist.get(i).get("detcodename") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	public void univerStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="univerStateList"; //프로그램명
		String s_name="최종학위별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"tempcd1", "detcodename", "col1", "col2", "col3","col4","col5","col6","totcount"}; //헤더 영문
		String header_k[]={"대분류", "소분류","고졸", "전문학사", "학사", "석사","박사","기타","합계"}; //헤더 국문
		int c_size[]={13,13,12,12,12,12,12,12,12};  //열 넓이를 위한 배열	
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 0);
		map.put("nend", 100000);
		//여기까지 변경
		List<Map>univerStateList=dao.getUniverStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(univerStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	/*
	 * 작업명 : Home>회원>현황>공동조리별 인원수 집계
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.09
	 */
	public ActionForward cookState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "cookStateList");
		return (mapping.findForward("cookStateList"));
	}

	public ActionForward cookStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getCookStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getCookStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("tempcd1")			== null ? "" : memberlist.get(i).get("tempcd1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename")	== null ? "" : memberlist.get(i).get("detcodename") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7")				== null ? "" : memberlist.get(i).get("col7") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8")				== null ? "" : memberlist.get(i).get("col8") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9")				== null ? "" : memberlist.get(i).get("col9") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col10")				== null ? "" : memberlist.get(i).get("col10") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col11")				== null ? "" : memberlist.get(i).get("col11") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	public void cookStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="cookStateList"; //프로그램명
		String s_name="공동조리별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"tempcd1", "detcodename", "col1", "col2", "col3","col4","col5","col6","col7","col8","col9","col10","col11","totcount"}; //헤더 영문
		String header_k[]={"대분류", "소분류","1명", "2명", "3명", "4명","5명","6명","7명","8명","9명","10명","기타","합계"}; //헤더 국문
		int c_size[]={13,13,12,12,12,12,12,12,12,12,12,12,12,12};  //열 넓이를 위한 배열		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 0);
		map.put("nend", 100000);
		//여기까지 변경
		List<Map>cookStateList=dao.getCookStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(cookStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	/*
	 * 작업명 : Home>회원>현황>공동관리별 인원수 집계
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.09
	 */
	public ActionForward conState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "conStateList");
		return (mapping.findForward("conStateList"));
	}

	public ActionForward conStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getConStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getConStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("tempcd1")			== null ? "" : memberlist.get(i).get("tempcd1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename")	== null ? "" : memberlist.get(i).get("detcodename") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7")				== null ? "" : memberlist.get(i).get("col7") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8")				== null ? "" : memberlist.get(i).get("col8") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9")				== null ? "" : memberlist.get(i).get("col9") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col10")				== null ? "" : memberlist.get(i).get("col10") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col11")				== null ? "" : memberlist.get(i).get("col11") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	public void conStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="conStateList"; //프로그램명
		String s_name="공동관리별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"tempcd1", "detcodename", "col1", "col2", "col3","col4","col5","col6","col7","col8","col9","col10","col11","totcount"}; //헤더 영문
		String header_k[]={"대분류", "소분류","1명", "2명", "3명", "4명","5명","6명","7명","8명","9명","10명","기타","합계"}; //헤더 국문
		int c_size[]={13,13,12,12,12,12,12,12,12,12,12,12,12,12};  //열 넓이를 위한 배열		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 0);
		map.put("nend", 100000);
		//여기까지 변경
		List<Map>conStateList=dao.getConStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(conStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	/*
	 * 작업명 : Home>회원>현황>위탁업체 업체별
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.09
	 */
	public ActionForward trustComState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "trustComStateList");
		return (mapping.findForward("trustComStateList"));
	}
	
	public ActionForward trustComStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		if(request.getParameter("trust_nm")!=null){
			map.put("trust_nm",URLDecoder.decode(request.getParameter("trust_nm"), "UTF-8"));
								
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
				
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
				
			int ncount = dao.getTrustCnt(map);
			int ntotpage = (ncount/nrows)+1;
									
			List<Map> memberlist=dao.getTrustList(map);
						
			StringBuffer result = new StringBuffer();
				
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("trust_code")	== null ? "" : memberlist.get(i).get("trust_code") ) +"\",");
				result.append("\"" + ( memberlist.get(i).get("trust_name")	== null ? "" : memberlist.get(i).get("trust_name") ) +"\",");
				result.append("\"" + ( memberlist.get(i).get("trust_post")	== null ? "" : memberlist.get(i).get("trust_post") ) +"\",");
				result.append("\"" + ( memberlist.get(i).get("trust_addr")	== null ? "" : memberlist.get(i).get("trust_addr") ) +"\",");
				result.append("\"" + ( memberlist.get(i).get("trust_tel")		== null ? "" : memberlist.get(i).get("trust_tel") ) +"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	
	public void trustComStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="trustCompanyList"; //프로그램명
		String s_name="위탁업체업체별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"trust_code", "trust_name", "trust_post", "trust_addr", "trust_tel"}; //헤더 영문
		String header_k[]={"업체코드", "위탁업체명", "우편번호", "주소", "전화번호"}; //헤더 국문
		int c_size[]={9, 30, 9, 70, 12};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("trust_keyword")!=null){
			map.put("trust_nm",URLDecoder.decode(request.getParameter("trust_keyword"), "UTF-8"));
		}
		
		//여기까지 변경
		List<Map> trustComList=dao.getTrustListAll(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(trustComList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	/*
	 * 작업명 : Home>회원>현황>지부별 회원-분과별
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.15
	 */
	public ActionForward bigState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "bigStateList");
		return (mapping.findForward("bigStateList"));
	}
	public ActionForward bigStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			List<Map> memberlist=dao.getBigStateList(map);
			int ncount = memberlist.size();
			//int ncount = dao.getBigStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			//List<Map> memberlist=dao.getBigStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("groupname1")	== null ? "" : memberlist.get(i).get("groupname1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("groupname2")	== null ? "" : memberlist.get(i).get("groupname2") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7")				== null ? "" : memberlist.get(i).get("col7") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8")				== null ? "" : memberlist.get(i).get("col8") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9")				== null ? "" : memberlist.get(i).get("col9") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col10")				== null ? "" : memberlist.get(i).get("col10") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col11")				== null ? "" : memberlist.get(i).get("col11") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col12")				== null ? "" : memberlist.get(i).get("col12") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col13")				== null ? "" : memberlist.get(i).get("col13") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col14")				== null ? "" : memberlist.get(i).get("col14") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	
	public void bigStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="bigStateList"; //프로그램명
		String s_name="지부별회원-분과별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"groupname1", "groupname2", "col1", "col2", "col3","col4","col5","col6","col7","col8","col9","col10","col11","col12","col13","col14","totcount"}; //헤더 영문
		String header_k[]={"근무처구분", "소속분과", "서울지부", "부산지부", "인천지부","경기지부","강원지부","충북지부","대전충남지부","전북지부","광주전남지부","대구경북지부","경남지부","울산지부","제주지부","기타","합계"}; //헤더 국문
		int c_size[]={13, 14, 12, 12, 12,12,12,12,12,12,12,12,12,12,12,12,12};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 1);
		map.put("nend", 1000);
		//여기까지 변경
		List<Map>bigStateList=dao.getBigStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(bigStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	/*
	 * 작업명 : Home>회원>현황>지부별 회원-분과별
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.15
	 */
	public ActionForward smallState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "smallStateList");
		return (mapping.findForward("smallStateList"));
	}
	public ActionForward smallStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getSmallStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getSmallStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("groupname1")	== null ? "" : memberlist.get(i).get("groupname1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("groupname2")	== null ? "" : memberlist.get(i).get("groupname2") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("groupname3")	== null ? "" : memberlist.get(i).get("groupname3") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7")				== null ? "" : memberlist.get(i).get("col7") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8")				== null ? "" : memberlist.get(i).get("col8") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9")				== null ? "" : memberlist.get(i).get("col9") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col10")				== null ? "" : memberlist.get(i).get("col10") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col11")				== null ? "" : memberlist.get(i).get("col11") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col12")				== null ? "" : memberlist.get(i).get("col12") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col13")				== null ? "" : memberlist.get(i).get("col13") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col14")				== null ? "" : memberlist.get(i).get("col14") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	
	public void smallStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="smallStateList"; //프로그램명
		String s_name="지부별회원-소분류별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"groupname1", "groupname2","groupname3", "col1", "col2", "col3","col4","col5","col6","col7","col8","col9","col10","col11","col12","col13","col14","totcount"}; //헤더 영문
		String header_k[]={"근무처구분", "근무처대분류","근무처소분류", "서울지부", "부산지부", "인천지부","경기지부","강원지부","충북지부","대전충남지부","전북지부","광주전남지부","대구경북지부","경남지부","울산지부","제주지부","기타","합계"}; //헤더 국문
		int c_size[]={13,13, 14, 12, 12, 12,12,12,12,12,12,12,12,12,12,12,12,12};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 1);
		map.put("nend", 10000);
		//여기까지 변경
		List<Map>smallStateList=dao.getSmallStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(smallStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	/*
	 * 작업명 : Home>회원>현황>지부별 회원-회원종류별
	 * 작업자 : 박상모
	 * 작업일 : 2012.10.15
	 */
	public ActionForward memGubunState(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setAttribute("menu", "memberState");
		request.setAttribute("sub", "memGubunStateList");
		return (mapping.findForward("memGubunStateList"));
	}
	public ActionForward memGubunStateList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		memberStateDao dao=new memberStateDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){
			String startdate	= request.getParameter("startdate");
			String enddate	= request.getParameter("enddate");
			
			map.put("startdate",startdate);
			map.put("enddate",enddate);
									
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10							
			
			int npage	= Integer.parseInt(page);
			int nrows	= Integer.parseInt(rows);
			int nstart	= (npage-1)*nrows;
			int nend		= nstart + nrows;
			map.put("nstart", nstart);
			map.put("nend", nend);
			
			int ncount = dao.getMemGubunStateCount(map);
			int ntotpage = (ncount/nrows)+1;
								
			List<Map> memberlist=dao.getMemGubunStateList(map);
					
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("groupname1")	== null ? "" : memberlist.get(i).get("groupname1") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("groupname2")	== null ? "" : memberlist.get(i).get("groupname2") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("groupname3")	== null ? "" : memberlist.get(i).get("groupname3") )	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1")				== null ? "" : memberlist.get(i).get("col1") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2")				== null ? "" : memberlist.get(i).get("col2") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3")				== null ? "" : memberlist.get(i).get("col3") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4")				== null ? "" : memberlist.get(i).get("col4") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5")				== null ? "" : memberlist.get(i).get("col5") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6")				== null ? "" : memberlist.get(i).get("col6") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7")				== null ? "" : memberlist.get(i).get("col7") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8")				== null ? "" : memberlist.get(i).get("col8") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9")				== null ? "" : memberlist.get(i).get("col9") )				+"\",");
				result.append("\"" + ( memberlist.get(i).get("col10")				== null ? "" : memberlist.get(i).get("col10") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col11")				== null ? "" : memberlist.get(i).get("col11") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col12")				== null ? "" : memberlist.get(i).get("col12") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col13")				== null ? "" : memberlist.get(i).get("col13") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("col14")				== null ? "" : memberlist.get(i).get("col14") )			+"\",");
				result.append("\"" + ( memberlist.get(i).get("totcount")			== null ? "" : memberlist.get(i).get("totcount")  )		+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}
	public void memGubunStateDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		memberStateDao dao=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=request.getParameter("register"); //유저ID
		//여기부터 개발자 변경 필요
		String name="memGubunStateList"; //프로그램명
		String s_name="지부별회원-회원종류별현황"; //엑셀 시트명
		String filename=date+"_"+user+"_"+name+".xls"; //생성될 엑셀 파일이름
		String header_e[]={"groupname1", "groupname2","groupname3", "col1", "col2", "col3","col4","col5","col6","col7","col8","col9","col10","col11","col12","col13","col14","totcount"}; //헤더 영문
		String header_k[]={"회원구분1", "회원구분2","회원구분3", "서울지부", "부산지부", "인천지부","경기지부","강원지부","충북지부","대전충남지부","전북지부","광주전남지부","대구경북지부","경남지부","울산지부","제주지부","기타","합계"}; //헤더 국문
		int c_size[]={13,13, 14, 12, 12, 12,12,12,12,12,12,12,12,12,12,12,12,12};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("startdate")!=null){
			map.put("startdate",request.getParameter("startdate"));
		}
		if(request.getParameter("enddate")!=null){
			map.put("enddate",request.getParameter("enddate"));
		}
		map.put("nstart", 1);
		map.put("nend", 10000);
		//여기까지 변경
		List<Map>mgStateList=dao.getMemGubunStateList(map);
		
		
		//이 이하는 변경할 필요 없음
		CommonUtil.makeExcelFile(mgStateList, filename, s_name,header_e,header_k,c_size);
		
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
		
		List<Map> i=dao.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
}

















