package com.ant.member.deposit.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.ant.common.code.CommonCode;
import com.ant.common.util.CommonUtil;
import com.ant.member.deposit.dao.memberDepositDao;
import com.ant.member.fund.dao.memberFundDao;
import com.ant.member.search.dao.memberSearchDao;
import com.ant.member.state.dao.memberStateDao;
import com.xensource.xenapi.Auth;

/*
 * 프로그램명 : memberDepositAction (회원 > 예수금현황)
 * 제작자 : 김성훈
 * 제작일 : 2012.09.
 * 설   명 : 회원>예수금현황 Action
 */



public class memberDepositAction extends DispatchAction {
	protected static Logger logger = Logger.getRootLogger();
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
	


	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.
	 * 작   업 : memberDeposit		회원 예수금현황 실 조회 화면으로 포워딩
	 */
	public ActionForward memberDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//메뉴 위치 보낸다.
		request.setAttribute("menu", "memberDepositList");

		//공통쿼리 제이슨으로 보낸다.
/*		List<HashMap<String, String>> comList	= CommonCode.getInstance().getCodeList();
		JSONArray jsoncode	= JSONArray.fromObject(comList);
		request.setAttribute("comList",jsoncode);*/
		
		return (mapping.findForward("memberDepositList"));
	}

	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.
	 * 작  업 : memberDepositList		회원 예수금현황 조회
	 * 작  업 : memberDepositExcel		회원 예수금현황 엑셀출력
	 */
	public ActionForward memberDepositList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String nTabCnt = "";
		if(	request.getParameter("nTabCnt") != null ) {
			nTabCnt = request.getParameter("nTabCnt");
//System.out.println(nTabCnt);
		if( "1".equals(nTabCnt))	return selectTab1( mapping, form, request, response);
		if( "2".equals(nTabCnt))	return selectTab2( mapping, form, request, response);
		if( "3".equals(nTabCnt))	return selectTab3( mapping, form, request, response);
		if( "4".equals(nTabCnt))	return selectTab4( mapping, form, request, response);
		if( "5_1".equals(nTabCnt))	return selectTab5_1( mapping, form, request, response);
		if( "5_2".equals(nTabCnt))	return selectTab5_2( mapping, form, request, response);
		if( "6".equals(nTabCnt))	return selectTab6( mapping, form, request, response);
		
		}
		return (mapping.findForward("ajaxout"));
	}
	
	public void memberDepositExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String nTabCnt = "";
		if(	request.getParameter("nTabCnt") != null ) {
			nTabCnt = request.getParameter("nTabCnt");

		if( "1".equals(nTabCnt))	excelTab1(nTabCnt, mapping, form, request, response);
		if( "2".equals(nTabCnt))	excelTab2(nTabCnt, mapping, form, request, response);
		if( "3".equals(nTabCnt))	excelTab3( mapping, form, request, response);
		if( "4".equals(nTabCnt))	excelTab4( mapping, form, request, response);
		if( "5_1".equals(nTabCnt))	excelTab5_1( mapping, form, request, response);
		if( "5_2".equals(nTabCnt))	excelTab5_2( mapping, form, request, response);
		if( "6".equals(nTabCnt))	excelTab6( mapping, form, request, response);
		
		}
	}

	
	/*
	 * 작업명 : Home>회원>예수금현황>예수금 집계표, 지부별 예수금 현황(tab_1, tab_2)
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.24
	 * DAO	  : memberDepositDao()
	 * 함수	  : getMemberDepositCount_1and2(Map map)
	 *          getMemberDepositList_1and2(Map map)
	 *          getMemberDepositExcel_1and2(Map map)
	 */
	public ActionForward selectTab1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){

			//조회 조건용 조회시작일과 종료일
			map.put("startdate",request.getParameter("startdate"));
			map.put("enddate",request.getParameter("enddate"));
			//지부 선택
			map.put("code_bran",request.getParameter("code_bran"));
			//본회 지회 구분
			map.put("code_receipt", request.getParameter("code_receipt"));
			
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
			
			List<Map>  lcount = dao.getMemberDepositCount_1and2(map);
			int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMemberDepositList_1(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
						
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				
				result.append("\"" + ( memberlist.get(i).get("tempcd1"		)	== null ? "" : memberlist.get(i).get("tempcd1"		))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1"				)	== null ? "" : memberlist.get(i).get("col1"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2"				)	== null ? "" : memberlist.get(i).get("col2"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3"				)	== null ? "" : memberlist.get(i).get("col3"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4"				)	== null ? "" : memberlist.get(i).get("col4"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5"				)	== null ? "" : memberlist.get(i).get("col5"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6"				)	== null ? "" : memberlist.get(i).get("col6"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7"				)	== null ? "" : memberlist.get(i).get("col7"				))	+"\",");
				
				result.append("\"" + ( memberlist.get(i).get("col12"			)	== null ? "" : memberlist.get(i).get("col12"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col10"  			)	== null ? "" : memberlist.get(i).get("col10"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col13"			)	== null ? "" : memberlist.get(i).get("col13"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col11"			)	== null ? "" : memberlist.get(i).get("col11"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9"				)	== null ? "" : memberlist.get(i).get("col9"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8"				)	== null ? "" : memberlist.get(i).get("col8"				))	+"\",");
				
				/*result.append("\"" + ( memberlist.get(i).get("col14"			)	== null ? "" : memberlist.get(i).get("col14"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col15"			)	== null ? "" : memberlist.get(i).get("col15"			))	+"\",");*/
				
				result.append("\"" + ( memberlist.get(i).get("totcount"		)	== null ? "" : memberlist.get(i).get("totcount"		))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("bran_money"	)	== null ? "" : memberlist.get(i).get("bran_money"	))	+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}

	public void excelTab1(String nTabCnt, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDepositDao dao=new memberDepositDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------

		String header_e[]={"tempcd1","col1","col2","col3","col4",
				           "col5","col6","col7",
				           "col12","col10","col13","col11","col9","col8","totcount","bran_money",}; //헤더 영문
		String header_k[]={"지부명", "기 취업회원", "신규 취업회원", "기 미취업회원", "신규 미취업회원",
				           "기 특별회원", "신규 특별회원", "학생회원",
				           "C기취업회원", "C신규취업회원", "C기미취업회원", "C신규미취업회원", "C기취업회원(년)", "C신규취업회원(년)", "인원수", "지부예수금"}; //헤더 국문
		int c_size[]={15,15,15,15,15,
				      15,15,15,
				      15,15,15,15,15,15,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		//조회 조건용 조회시작일과 종료일
		map.put("startdate",request.getParameter("startdate"));
		map.put("enddate",request.getParameter("enddate"));
		//지부 선택
		map.put("code_bran",request.getParameter("code_bran"));
		//본회 지회 구분
		map.put("code_receipt", request.getParameter("code_receipt"));
		
		//검색
		List<Map> memberlist=dao.getMemberDepositList_1(map);
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDepositList"; //프로그램명
		String s_name		="";
		if(nTabCnt.equals("1"))s_name=	"현황_예수금 집계표"; //엑셀 시트명
		if(nTabCnt.equals("2"))s_name=	"현황_지부별 예수금 현황"; //엑셀 시트명
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}

	public ActionForward selectTab2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		if((request.getParameter("startdate")!=null)&&(request.getParameter("enddate")!=null)){

			//조회 조건용 조회시작일과 종료일
			map.put("startdate",request.getParameter("startdate"));
			map.put("enddate",request.getParameter("enddate"));
			//지부 선택
			map.put("code_bran",request.getParameter("code_bran"));
			//본회 지회 구분
			map.put("code_receipt", request.getParameter("code_receipt"));
			
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
			
			List<Map>  lcount = dao.getMemberDepositCount_1and2(map);
			int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMemberDepositList_2(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
						
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				
				
				result.append("\"" + ( memberlist.get(i).get("tempcd1"		)	== null ? "" : memberlist.get(i).get("tempcd1"		))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1"				)	== null ? "" : memberlist.get(i).get("col1"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2"				)	== null ? "" : memberlist.get(i).get("col2"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col3"				)	== null ? "" : memberlist.get(i).get("col3"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col4"				)	== null ? "" : memberlist.get(i).get("col4"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5"				)	== null ? "" : memberlist.get(i).get("col5"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6"				)	== null ? "" : memberlist.get(i).get("col6"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7"				)	== null ? "" : memberlist.get(i).get("col7"				))	+"\",");
				
				result.append("\"" + ( memberlist.get(i).get("col12"			)	== null ? "" : memberlist.get(i).get("col12"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col10"  			)	== null ? "" : memberlist.get(i).get("col10"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col13"			)	== null ? "" : memberlist.get(i).get("col13"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col11"			)	== null ? "" : memberlist.get(i).get("col11"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9"				)	== null ? "" : memberlist.get(i).get("col9"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8"				)	== null ? "" : memberlist.get(i).get("col8"				))	+"\",");
				
				/*result.append("\"" + ( memberlist.get(i).get("col14"			)	== null ? "" : memberlist.get(i).get("col14"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("col15"			)	== null ? "" : memberlist.get(i).get("col15"			))	+"\",");*/
				result.append("\"" + ( memberlist.get(i).get("totcount"		)	== null ? "" : memberlist.get(i).get("totcount"		))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("bran_money"	)	== null ? "" : memberlist.get(i).get("bran_money"	))	+"\"");
				result.append("]}");
				
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}

	public void excelTab2(String nTabCnt, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDepositDao dao=new memberDepositDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------

/*		String header_e[]={"tempcd1","col1","col2","col3","col4",
				           "col5","col6","col7","totcount","bran_money",}; //헤더 영문
		String header_k[]={"지부명", "기 취업회원", "신규 취업회원", "기 미취업회원", "신규 미취업회원",
				           "기 특별회원", "신규 특별회원", "학생회원", "인원수", "지부예수금"}; //헤더 국문
		int c_size[]={15,15,15,15,15,
				      15,15,15,15,15};  //열 넓이를 위한 배열
*/		
		String header_e[]={"tempcd1","col1","col2","col3","col4",
					       "col5","col6","col7",
					       "col12","col10","col13","col11","col9","col8","totcount","bran_money",}; //헤더 영문
		String header_k[]={"지부명", "기 취업회원", "신규 취업회원", "기 미취업회원", "신규 미취업회원",
					       "기 특별회원", "신규 특별회원", "학생회원",
					       "C기취업회원","C신규취업회원","C기미취업회원","C신규미취업회원","C기취업회원(년)","C신규취업회원(년)","인원수", "지부예수금"}; //헤더 국문
		int c_size[]={15,15,15,15,15,
				      15,15,15,
				      15,15,15,15,15,15,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		//조회 조건용 조회시작일과 종료일
		map.put("startdate",request.getParameter("startdate"));
		map.put("enddate",request.getParameter("enddate"));
		//지부 선택
		map.put("code_bran",request.getParameter("code_bran"));
		//본회 지회 구분
		map.put("code_receipt", request.getParameter("code_receipt"));
		
		//검색
		List<Map> memberlist=dao.getMemberDepositList_2(map);
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDepositList"; //프로그램명
		String s_name		="";
		if(nTabCnt.equals("1"))s_name=	"현황_예수금 집계표"; //엑셀 시트명
		if(nTabCnt.equals("2"))s_name=	"현황_지부별 예수금 현황"; //엑셀 시트명
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	

	/*
	 * 작업명 : 회원>예수금현황>평생회비 예수금(tab_3) 조회 출력
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.25
	 * DAO	  : memberDepositDao()
	 * 함수	  : getMemberDepositCount_3(Map map)
	 *          getMemberDepositList_3(Map map)
	 *          getMemberDepositExcel_3(Map map)
	 */
	public ActionForward selectTab3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		//if((request.getParameter("is")!=null)&&(request.getParameter("enddate")!=null)){ //조건이 안들어오면 전체 검색한다.

			//조회년도 , 차수
			if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
			if( request.getParameter("pers_mem_order")	!=null ) map.put("pers_mem_order",request.getParameter("pers_mem_order"));
			
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
			
			List<Map>  lcount = dao.getMemberDepositCount_3(map);
			int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMemberDepositList_3(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){

				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("pers_name"			)	== null ? "" : memberlist.get(i).get("pers_name"			))	+"\",");
//				JUMIN_DEL
//				result.append("\"" + ( memberlist.get(i).get("pers_no"				)	== null ? "" : memberlist.get(i).get("pers_no"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("pers_birth"				)	== null ? "" : memberlist.get(i).get("pers_birth"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("lic_no"				)	== null ? "" : memberlist.get(i).get("lic_no"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("regi_dt"				)	== null ? "" : memberlist.get(i).get("regi_dt"				))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("pers_lert_st"		)	== null ? "" : memberlist.get(i).get("pers_lert_st"		))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("pers_mem_order"	)	== null ? "" : memberlist.get(i).get("pers_mem_order"	))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("code_bran"			)	== null ? "" : memberlist.get(i).get("code_bran"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("company_name"	)	== null ? "" : memberlist.get(i).get("company_name"	))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("pers_tel"				)	== null ? "" : memberlist.get(i).get("pers_tel"			))	+"\",");
				result.append("\"" + ( memberlist.get(i).get("email"					)	== null ? "" : memberlist.get(i).get("email"				))	+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
//		}
		return (mapping.findForward("ajaxout"));
	}

	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.14
	 * 작   업 : memberpers_no		주민번호 체크
	 */
	public ActionForward pers_check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//전체건수 구해서 넘긴다.
		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		//조회년도 , 차수
		if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
		if( request.getParameter("pers_mem_order")	!=null ) map.put("pers_mem_order",request.getParameter("pers_mem_order"));
	
		//여기서 구하고 넘긴다.			
		List<Map>  lcount = dao.getMemberDepositCount_3(map);
		int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
		request.setAttribute("ncount", ncount);

		String param = "";
		if( request.getParameter("nTabCnt"			)!=null ) param+="&nTabCnt="		+request.getParameter("nTabCnt"			);
		if( request.getParameter("receipt_year"		)!=null ) param+="&receipt_year="	+request.getParameter("receipt_year"	);
		if( request.getParameter("pers_mem_order"	)!=null ) param+="&pers_mem_order="	+request.getParameter("pers_mem_order"	);
		
 //		System.out.println("param = "+param);

		request.setAttribute("param", param);
		request.setAttribute("url", "memberDeposit");

	return (mapping.findForward("pers_check"));
	
	}
	public void excelTab3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDepositDao dao=new memberDepositDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------

		String header_e[]={"pers_name",
//						   "pers_no", // JUMIN_DEL
							"pers_birth",
						   "lic_no","regi_dt","pers_lert_st",
				           "pers_mem_order","code_bran","han_code_big","han_code_sect","han_code_member",
				           "company_name","comp_addr","pers_addr","pers_tel","email",}; //헤더 영문
		String header_k[]={"성명",
//						   "주민번호", // JUMIN_DEL
							"생년월일",
						   "면허번호", "회원 가입일", "인증일",
				           "차수", "소속지부", "분과명", "분회명", "회원종류",
				           "근무처명", "근무처 주소", "집 주소", "연락처", "이메일"}; //헤더 국문
		int c_size[]={15,15,15,15, 15,
				      10,15,15,15,15,
				      20,30,30,15,20};  //열 넓이를 위한 배열
		
	    //검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));
		//map.put("nend", 20000);
		

		//주민번호 표출,조회년도 , 차수
		
		if( request.getParameter("check")	!=null ) map.put("check",request.getParameter("check"));
		if( request.getParameter("receipt_year")	!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
		if( request.getParameter("pers_mem_order")	!=null ) map.put("pers_mem_order",request.getParameter("pers_mem_order"));
		
		
		//검색
		List<Map> memberlist=dao.getMemberDepositList_3(map,true);
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDepositList"; //프로그램명
		String s_name		="현황_평생회비 예수금"; //엑셀 시트명
		
		//이 이하는 변경할 필요 없음
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);

		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	




	/*
	 * 작업명 : 회원>예수금현황>평생회비 예수금 현황(tab_4) 조회 출력
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.29
	 * DAO	: memberDepositDao()
	 * 함수	: 전체 21개이므로 카운터 페이지는 없다.
	 *           getMemberDepositList_4(Map map)
	 *           getMemberDepositExcel_4(Map map)
	 */
	public ActionForward selectTab4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		//if((request.getParameter("is")!=null)&&(request.getParameter("enddate")!=null)){ //조건이 안들어오면 전체 검색한다.

			//조회년도 , 차수
			if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
			String page = request.getParameter("page");	// 페이지(기본값=) 1
			String rows = "30"; //request.getParameter("rows");	// 출력줄(기본값=) 10
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
			
			//List<Map>  lcount = dao.getMemberDepositCount_3(map);
			int ncount =  21;
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMemberDepositList_4(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");

			int nNow = 0;
			int nCount = 0;
			int yyyy = Integer.parseInt(request.getParameter("receipt_year"));
			for(int i=0; i<21; i++) {
				if(i>0) result.append(",");
				
				if( nCount < memberlist.size() ){
					nNow = Integer.parseInt(memberlist.get(nCount).get("pers_mem_order"	)+"");
				}	else nNow=99;
			
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				if(i+1 < nNow) {
					result.append("\"" + (yyyy-i) +"\",");
					result.append("\"" + (i+1)+"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\",");
					result.append("\"" + "0" +"\"");
					
				}else if(memberlist.get(nCount).get("receipt_year"	)!= null){	
					result.append("\"" + ( memberlist.get(nCount).get("receipt_year"	)== null ? "" : memberlist.get(nCount).get("receipt_year"	))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("pers_mem_order"	)== null ? "" : memberlist.get(nCount).get("pers_mem_order"	))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col2"			)== null ? "" : memberlist.get(nCount).get("col2"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col3"			)== null ? "" : memberlist.get(nCount).get("col3"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col4"			)== null ? "" : memberlist.get(nCount).get("col4"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col5"			)== null ? "" : memberlist.get(nCount).get("col5"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col6"			)== null ? "" : memberlist.get(nCount).get("col6"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col7"			)== null ? "" : memberlist.get(nCount).get("col7"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col8"			)== null ? "" : memberlist.get(nCount).get("col8"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col9"			)== null ? "" : memberlist.get(nCount).get("col9"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col10"			)== null ? "" : memberlist.get(nCount).get("col10"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col11"			)== null ? "" : memberlist.get(nCount).get("col11"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col12"			)== null ? "" : memberlist.get(nCount).get("col12"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col13"			)== null ? "" : memberlist.get(nCount).get("col13"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("col14"			)== null ? "" : memberlist.get(nCount).get("col14"			))+"\",");
					result.append("\"" + ( memberlist.get(nCount).get("totcount"		)== null ? "" : memberlist.get(nCount).get("totcount"		))+"\"");
					nCount++;
				}
				result.append("]}");
				//System.out.println("asdf result ("+i+")="+result);
			}
			result.append("]}");
			request.setAttribute("result",result);
		return (mapping.findForward("ajaxout"));
	}

	public void excelTab4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDepositDao dao=new memberDepositDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------

		String header_e[]={"receipt_year","pers_mem_order","col2","col3","col4",
		                   "col5","col6","col7","col8","col9",
		                   "col10","col11","col12","col13","col14",
		                   "totcount"}; //헤더 영문
		String header_k[]={"가입년도", "차수", "서울", "부산", "인천", 
				           "경기", "강원", "충북", "대전충남", "전북", 
				           "광주전남", "대구경북", "경남", "울산", "제주", 
				           "총합"}; //헤더 국문
		int c_size[]={15,15,15,15,15,
			          15,15,15,15,15,
			          15,15,15,15,15,
			          15};  //열 넓이를 위한 배열
		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", 0);
		map.put("nend", 1000000);
		

		//조회년도 , 차수
		if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
		
		//검색
		List<Map> memberlist=dao.getMemberDepositList_4(map);
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDepositList"; //프로그램명
		String s_name		="현황_평생회비 예수금 현황"; //엑셀 시트명
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}



	/*
	 * 작업명 : 회원>예수금현황>예수금 현황_연회원(tab_5_1) 조회 출력
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * DAO 	  : memberDepositDao()
	 * 함수   : getMemberDepositCount_5_1(Map map)
	 *          getMemberDepositList_5_1(Map map)
	 *          getMemberDepositExcel_5_1(Map map)
	 */
	public ActionForward selectTab5_1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		if(request.getParameter("receipt_dt")!=null){ //조건이 안들어오면 검색하지 않는다 

			//조회년도
			//if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
			if( request.getParameter("receipt_dt")		!=null ) map.put("receipt_dt",request.getParameter("receipt_dt"));
			
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
			
			List<Map>  lcount = dao.getMemberDepositCount_5_1(map);
			int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMemberDepositList_5_1(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("number"		)== null ? "" : memberlist.get(i).get("number"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("code_receipt"	)== null ? "" : memberlist.get(i).get("code_receipt"	))+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename"	)== null ? "" : memberlist.get(i).get("detcodename"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("code_bran"	)== null ? "" : memberlist.get(i).get("code_bran"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("tempcd1"		)== null ? "" : memberlist.get(i).get("tempcd1"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col1"			)== null ? "" : memberlist.get(i).get("col1"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col2"			)== null ? "" : memberlist.get(i).get("col2"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col5"			)== null ? "" : memberlist.get(i).get("col5"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col6"			)== null ? "" : memberlist.get(i).get("col6"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col7"			)== null ? "" : memberlist.get(i).get("col7"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col8"			)== null ? "" : memberlist.get(i).get("col8"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col9"			)== null ? "" : memberlist.get(i).get("col9"			))+"\",");
				
				result.append("\"" + ( memberlist.get(i).get("col45"				)== null ? "" : memberlist.get(i).get("col45"				))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col41"				)== null ? "" : memberlist.get(i).get("col41"				))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col46"				)== null ? "" : memberlist.get(i).get("col46"				))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col42"				)== null ? "" : memberlist.get(i).get("col42"				))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col40"				)== null ? "" : memberlist.get(i).get("col40"				))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col39"				)== null ? "" : memberlist.get(i).get("col39"				))+"\",");/* 20150113 CMS추가 */
				
				/*result.append("\"" + ( memberlist.get(i).get("col48"				)== null ? "" : memberlist.get(i).get("col48"				))+"\",");
				result.append("\"" + ( memberlist.get(i).get("col49"				)== null ? "" : memberlist.get(i).get("col49"				))+"\",");*/
								
				result.append("\"" + ( memberlist.get(i).get("totcount"		)== null ? "" : memberlist.get(i).get("totcount"		))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}

	public void excelTab5_1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDepositDao dao=new memberDepositDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		String header_e[]={"detcodename","tempcd1","col1","col2","col5",
						   "col6","col7","col8","col9",
						   "col45","col41","col46","col42","col40","col39",
						   "totcount"
						  }; //헤더 영문
		String header_k[]={"입금장소",  "구분", "신규 취업회원", "신규 미취업회원", 
						   "기 취업회원", "기 미취업회원", "학생회원", "신규 특별회원", "기 특별회원",
						   "C기취업회원", "C신규취업회원", "C기미취업회원", "C신규미취업회원", "C기취업회원(년)", "C신규취업회원(년)",
						   "소계"
						  }; //헤더 국문
		int c_size[]={15,15,15,15,
				 	  15,15,15,15,15,
				 	  15,15,15,15,15,15,
				 	  15
				 	 };  //열 넓이를 위한 배열

		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", 0);
		map.put("nend", 1000000);
		

		//조회년도
		//if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
		if( request.getParameter("receipt_dt")		!=null ) map.put("receipt_dt",request.getParameter("receipt_dt"));
		
		//검색
		List<Map> memberlist=dao.getMemberDepositList_5_1(map);
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDepositList"; //프로그램명
		String s_name		="현황_예수금현황_년회원"; //엑셀 시트명
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}



	/*
	 * 작업명 : 회원>예수금현황>예수금 현황_평생회원(tab_5_2) 조회 출력
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * DAO	  : memberDepositDao()
	 * 함수	  : getMemberDepositCount_5_2(Map map)
	 *          getMemberDepositList_5_2(Map map)
	 *          getMemberDepositExcel_5_2(Map map)
	 */
	public ActionForward selectTab5_2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		if(request.getParameter("receipt_dt")!=null){ //조건이 안들어오면 검색하지 않는다 

			//조회년도
			//if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
			if( request.getParameter("receipt_dt")		!=null ) map.put("receipt_dt",request.getParameter("receipt_dt"));
			//System.out.println(request.getParameter("receipt_dt"));
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
			
			List<Map>  lcount = dao.getMemberDepositCount_5_2(map);
			int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMemberDepositList_5_2(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("number"		)== null ? "" : memberlist.get(i).get("number"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("code_bran"	)== null ? "" : memberlist.get(i).get("code_bran"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("tempcd1"		)== null ? "" : memberlist.get(i).get("tempcd1"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("code_member"	)== null ? "" : memberlist.get(i).get("code_member"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename"	)== null ? "" : memberlist.get(i).get("detcodename"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("bran_money"	)== null ? "" : memberlist.get(i).get("bran_money"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("totCount"		)== null ? "" : memberlist.get(i).get("totCount"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("totBran_money")== null ? "" : memberlist.get(i).get("totBran_money"	))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}

	public void excelTab5_2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDepositDao dao=new memberDepositDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------

		String header_e[]={"tempcd1","detcodename","bran_money","totCount","totBran_money"}; //헤더 영문
		String header_k[]={"구분", "회원종류", "예수금", "인원수", "예수금 집계"}; //헤더 국문
		int c_size[]={15,15,15,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", 0);
		map.put("nend", 1000000);
		

		//조회년도
		//if( request.getParameter("receipt_year")		!=null ) map.put("receipt_year",request.getParameter("receipt_year"));
		if( request.getParameter("receipt_dt")		!=null ) map.put("receipt_dt",request.getParameter("receipt_dt"));
		
		//검색
		List<Map> memberlist=dao.getMemberDepositList_5_2(map);
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDepositList"; //프로그램명
		String s_name		="현황_예수금현황_평생 회원"; //엑셀 시트명
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}



	/*
	 * 작업명 : 회원>예수금현황>산하단체 회원(tab_6) 조회 출력
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * DAO	  : memberDepositDao()
	 * 함수	  : getMemberDepositCount_6(Map map)
	 *          getMemberDepositList_6(Map map)
	 *          getMemberDepositExcel_6(Map map)
	 */
	public ActionForward selectTab6(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map = new HashMap();
		memberDepositDao dao=new memberDepositDao(); 
				
		if(request.getParameter("receipt_dt")!=null){ //조건이 안들어오면 검색하지 않는다 

			//조회년도
			if( request.getParameter("receipt_dt")		!=null ) map.put("receipt_dt",request.getParameter("receipt_dt"));
			//산하단체구분
			if( request.getParameter("code_sub")		!=null ) map.put("code_sub",request.getParameter("code_sub"));
			
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
			
			List<Map>  lcount = dao.getMemberDepositCount_6(map);
			int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.
			List<Map> memberlist=dao.getMemberDepositList_6(map);
					
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
			
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			for(int i=0;i<memberlist.size();i++){
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( memberlist.get(i).get("number"		)== null ? "" : memberlist.get(i).get("number"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("code_bran"	)== null ? "" : memberlist.get(i).get("code_bran"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("tempcd1"		)== null ? "" : memberlist.get(i).get("tempcd1"			))+"\",");
				result.append("\"" + ( memberlist.get(i).get("code_member"	)== null ? "" : memberlist.get(i).get("code_member"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("detcodename"	)== null ? "" : memberlist.get(i).get("detcodename"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("sub_dues"		)== null ? "" : memberlist.get(i).get("sub_dues"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("totCount"		)== null ? "" : memberlist.get(i).get("totCount"		))+"\",");
				result.append("\"" + ( memberlist.get(i).get("totSub_dues")== null ? "" : memberlist.get(i).get("totSub_dues"	))+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		return (mapping.findForward("ajaxout"));
	}

	public void excelTab6(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDepositDao dao=new memberDepositDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------

		String header_e[]={"tempcd1","detcodename","sub_dues","totCount","totSub_dues"}; //헤더 영문
		String header_k[]={"구분", "회원종류", "예수금", "인원수", "예수금 집계"}; //헤더 국문
		int c_size[]={15,15,15,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", 0);
		map.put("nend", 1000000);
		

		//조회년도
		if( request.getParameter("receipt_dt")		!=null ) map.put("receipt_dt",request.getParameter("receipt_dt"));
		
		//검색
		List<Map> memberlist=dao.getMemberDepositList_6(map);
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDepositList"; //프로그램명
		String s_name		="현황_산하단체 예수금 현황"; //엑셀 시트명
		
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

		memberStateDao dao2=new memberStateDao();
		List<Map> i=dao2.iExcelLog(map1); //엑셀 로그 INSERT
		
		try{
			if(f.exists()) f.delete();
		}catch(Exception e){e.printStackTrace();}
		
	}
	
	
}

















