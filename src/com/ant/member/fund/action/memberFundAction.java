package com.ant.member.fund.action;

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

public class memberFundAction extends DispatchAction {
	protected static Logger logger = Logger.getRootLogger();
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
	

	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.14
	 * 작   업 : memberpers_no		주민번호 체크
	 */
	public ActionForward pers_check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map map	= new HashMap();
		memberFundDao dao=new memberFundDao();
		

		//맵에 조건을 넣는다.
		if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
		if(request.getParameter("gubun"			)!=null) map.put("gubun"		, request.getParameter("gubun"));		//구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));	//이름
		if(request.getParameter("lic_no"		)!=null) map.put("lic_no"		, request.getParameter("lic_no"));		//면허번호
		if(request.getParameter("company_name"	)!=null) map.put("company_name"	, URLDecoder.decode(request.getParameter("company_name"),"UTF-8"));	//근무처명
		
		//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.
		List<Map>  lcount = dao.getMemberFundCount(map);
		int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
		request.setAttribute("ncount", ncount);

		//검색 조건을 파라메터로 넘긴다.
		if(request.getParameter("pers_name"	)!=null)request.setAttribute("pers_name",  URLDecoder.decode(request.getParameter("pers_name"),"UTF-8") );
		if(request.getParameter("company_name"	)!=null)request.setAttribute("company_name",  URLDecoder.decode(request.getParameter("company_name"),"UTF-8") );
		
		String param = "";
		if(request.getParameter("startdate"		)!=null) param+="&startdate="	+ request.getParameter("startdate");	//검색기간시작
		if(request.getParameter("enddate"		)!=null) param+="&enddate="		+ request.getParameter("enddate");		//검색기간종료
		if(request.getParameter("gubun"			)!=null) param+="&gubun="		+ request.getParameter("gubun");		//구분
		if(request.getParameter("code_member"	)!=null) param+="&code_member="	+ request.getParameter("code_member");	//회원구분
		if(request.getParameter("lic_no"		)!=null) param+="&lic_no="		+ request.getParameter("lic_no");		//면허번호

// 		System.out.println("param = "+param);


		request.setAttribute("param", param);
		request.setAttribute("url", "memberFund");

	return (mapping.findForward("pers_check"));
	
	}
	
	

	/*
	 * 작업명 : Home>회원>기부/기금 현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.
	 * 작   업 : memberFundList		회원  기부/기금 현황 조회
	 */
	public ActionForward memberFundList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		memberFundDao dao=new memberFundDao(); 
		//맵에 조건을 넣는다.
		if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
		if(request.getParameter("gubun"			)!=null) map.put("gubun"		, request.getParameter("gubun"));		//구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));	//이름
		if(request.getParameter("lic_no"		)!=null) map.put("lic_no"		, request.getParameter("lic_no"));		//면허번호
		if(request.getParameter("company_name"	)!=null) map.put("company_name"	, URLDecoder.decode(request.getParameter("company_name"),"UTF-8"));	//근무처명

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
		List<Map>  lcount = dao.getMemberFundCount(map);
		int ncount =  ((Integer)(lcount.get(0).get("ncount"))).intValue();
		int ntotpage = (ncount/nrows)+1;
			
		//화면에 출력할 값을 검색한다.
		List<Map> memberlist=dao.getMemberFundList(map);
					
		//jsp에 넘겨줄 result값을 만든다.
		StringBuffer result = new StringBuffer();
			
		result.append("{\"page\":\""	+npage		+"\",");		
		result.append("\"total\":\"" 	+ntotpage	+"\",");
		result.append("\"records\":\""	+ncount		+"\",");
		result.append("\"rows\":[");
			
		for(int i=0;i<memberlist.size();i++){
					
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\""+(memberlist.get(i).get("pres_let_dt"		)== null ? "" : memberlist.get(i).get("pres_let_dt"		))+"\",");
			result.append("\""+(memberlist.get(i).get("gubun"			)== null ? "" : memberlist.get(i).get("gubun"			))+"\",");
			result.append("\""+(memberlist.get(i).get("han_gubun"		)== null ? "" : memberlist.get(i).get("han_gubun"		))+"\",");
			result.append("\""+(memberlist.get(i).get("promise_no"		)== null ? "" : memberlist.get(i).get("promise_no"		))+"\",");
			result.append("\""+(memberlist.get(i).get("promise_mon"		)== null ? "" : memberlist.get(i).get("promise_mon"		))+"\",");
			result.append("\""+(memberlist.get(i).get("pres_money"		)== null ? "" : memberlist.get(i).get("pres_money"		))+"\",");
			result.append("\""+(memberlist.get(i).get("pers_name"		)== null ? "" : memberlist.get(i).get("pers_name"		))+"\",");
//			JUMIN_DEL
//			result.append("\""+(memberlist.get(i).get("pers_no"			)== null ? "" : memberlist.get(i).get("pers_no"			))+"\",");
			result.append("\""+(memberlist.get(i).get("lic_no"			)== null ? "" : memberlist.get(i).get("lic_no"			))+"\",");
			result.append("\""+(memberlist.get(i).get("pers_tel"		)== null ? "" : memberlist.get(i).get("pers_tel"		))+"\",");
			result.append("\""+(memberlist.get(i).get("code_member"		)== null ? "" : memberlist.get(i).get("code_member"		))+"\",");
			result.append("\""+(memberlist.get(i).get("han_code_member"	)== null ? "" : memberlist.get(i).get("han_code_member"	))+"\",");
			result.append("\""+(memberlist.get(i).get("company_name"	)== null ? "" : memberlist.get(i).get("company_name"	))+"\"");
			result.append("]}");
		}
		result.append("]}");
		request.setAttribute("result",result);

		return (mapping.findForward("ajaxout"));
	}
	
	
	
	
	/*
	 * 작업명 : Home>회원>기부/기금 현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.13
	 * 작   업 : memberFundExcel		회원  기부/기금 엑셀 출력
	 */
	public void memberFundExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberFundDao dao=new memberFundDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		
		
		//여기부터 개발자 변경 필요--------------------------------------
		
		String name			="memberFundList"; //프로그램명
		String s_name		="기부_기금현황"; //엑셀 시트명

		
		String header_e[]={"pres_let_dt","han_gubun","promise_no","promise_mon","pres_money","pers_name",
//				JUMIN_DEL
//				"pers_no",
				"lic_no","pers_tel","han_code_member","company_name"}; //헤더 영문
		String header_k[]={"입금일자","구분","약정번호","회차","금액","이름",
//				"주민번호", // JUMIN_DEL
				"면허번호","전화번호","회원구분","근무처명"}; //헤더 국문
		int c_size[]={15,15,15,15,15,15,15,15,15,15 /*,15*/};  //열 넓이를 위한 배열
		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));

		if(request.getParameter("check"			)!=null) map.put("check"		, request.getParameter("check"));	//주민번호 유형
		if(request.getParameter("startdate"		)!=null) map.put("startdate"	, request.getParameter("startdate"));	//검색기간시작
		if(request.getParameter("enddate"		)!=null) map.put("enddate"		, request.getParameter("enddate"));	//검색기간종료
		if(request.getParameter("gubun"			)!=null) map.put("gubun"		, request.getParameter("gubun"));		//구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));	//이름
		if(request.getParameter("lic_no"		)!=null) map.put("lic_no"		, request.getParameter("lic_no"));		//면허번호
		if(request.getParameter("company_name"	)!=null) map.put("company_name"	, URLDecoder.decode(request.getParameter("company_name"),"UTF-8"));	//근무처명

//		System.out.println("asdfasdfasdf = "+request.getParameter("company_name"	));

		//검색
		List<Map> memberlist=dao.getMemberFundList(map,true);
		
		//여기까지 변경--------------------------------------------------
		
		
		
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
	 * 작업명 : Home>회원>기부/기금 현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.
	 * 작   업 : memberFund		회원  기부/기금 현황 실 조회 화면으로 포워딩
	 */
	public ActionForward memberFund(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//메뉴 위치 보낸다.
		request.setAttribute("menu", "memberFundList");

		//공통쿼리 제이슨으로 보낸다.
		/*List<HashMap<String, String>> comList	= CommonCode.getInstance().getCodeList();
		JSONArray jsoncode	= JSONArray.fromObject(comList);
		request.setAttribute("comList",jsoncode);*/
		
		return (mapping.findForward("memberFundList"));
	}

}

















