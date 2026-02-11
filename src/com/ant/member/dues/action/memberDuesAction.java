package com.ant.member.dues.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.common.code.CommonCode;
import com.ant.common.util.CommonUtil;
import com.ant.common.util.StringUtil;
import com.ant.member.deposit.dao.memberDepositDao;
import com.ant.member.dues.dao.memberDuesDao;
import com.ant.member.search.dao.memberSearchDao;
import com.ant.member.state.dao.memberStateDao;






import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class memberDuesAction extends DispatchAction {
	protected static Logger logger = Logger.getRootLogger();
	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\" ;


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
		memberDuesDao dao=new memberDuesDao();
			
		//검색을 위한 파라메터를 맵에 넣는다.
		// 변수에 값 받아서 넣음
		if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
		if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
		if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
		if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
		if(request.getParameter("conform_yn"	)!=null) {																						//확인여부
			if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
			if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
		}
		if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	 , request.getParameter("code_receipt"	));	//입금장소
		if(request.getParameter("code_pay_flag"	)!=null) map.put("code_pay_flag" , request.getParameter("code_pay_flag"	));	//입금방법
		if(request.getParameter("cfyn"	)!=null){ //선인증목록
			if("Y".equals(request.getParameter("cfyn"	)))
				map.put("cfyn"	, "2");	 
		}
		//여기서 구하고 넘긴다.
		List<Map> count = new ArrayList();
		if(request.getParameter("duesp"	)!=null){ //선인증목록
			count=dao.getMemberDuesPtabelCount(map);		
		} else {
			count = dao.getMemberDuesCount(map);		
		}
		int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
		request.setAttribute("ncount", ncount);
		
		//검색 조건을 파라메터로 넘긴다.
		String param = "";
		if(request.getParameter("regi_dt"		)!=null) param+="&regi_dt="		+ request.getParameter("regi_dt"		);	//등록일
		if(request.getParameter("dues_gubun"	)!=null) param+="&dues_gubun="	+ request.getParameter("dues_gubun"		);	//회비구분
		if(request.getParameter("code_member"	)!=null) param+="&code_member="	+ request.getParameter("code_member"	);	//회원구분
		if(request.getParameter("code_bran"		)!=null) param+="&code_bran="	+ request.getParameter("code_bran"		);	//소속지부
		if(request.getParameter("code_sub"		)!=null) param+="&code_sub="	+ request.getParameter("code_sub"		);	//산하단체
		if(request.getParameter("incom_flag"	)!=null) param+="&incom_flag="	+ request.getParameter("incom_flag"		);	//완납여부
		if(request.getParameter("conform_yn"	)!=null) param+="&conform_yn="  + request.getParameter("conform_yn"	);//확인/미확인
		if(request.getParameter("duesp"	)!=null) param+="&duesp="	+ request.getParameter("duesp"		);	//Dues_P 테이블
		if(request.getParameter("code_receipt"	)!=null) param+="&code_receipt=" + request.getParameter("code_receipt"	);	//입금장소
		if(request.getParameter("code_pay_flag"	)!=null) param+="&code_pay_flag="+ request.getParameter("code_pay_flag"	);	//입금방법
		if(request.getParameter("cfyn"	)!=null){if("Y".equals(request.getParameter("cfyn"	))) param+="&cfyn="+ request.getParameter("cfyn"	); }
		request.setAttribute("param", param);
		request.setAttribute("url", "memberDues");
		
	return (mapping.findForward("pers_check"));
			
	}
	

	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.14
	 * 작  업 : memberDuesExcel 	회원 회비처리 엑셀 출력
	 */

	public void memberDuesExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberDuesDao dao=new memberDuesDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		System.out.println("USER ============>"+user);
		
		//여기부터 개발자 변경 필요--------------------------------------

		String header_e[]={"code_pers","pers_name","lic_no","han_code_member","han_code_bran","bigname","sectname","han_dues_gubun",
				           "mem_dues","auth_start","auth_end","incom_flag","conform_yn",
				           "han_code_sub","regi_dt","adrs","company_name","cadrs"}; //헤더 영문
		String header_k[]={"회원코드", "이름","면허번호", "회원구분", "소속지부", "소속분과","소속분회","회비구분", 
				           "회비", "시작일자", "만료일자", "완납여부", "확인여부",
				           "산하단체", "등록일","주소","회사명","회사주소"}; //헤더 국문
		int c_size[]={15,15,15,15,15,
				      15,15,15,15,15,15,15,15,15,15,
				      15,15,15};  //열 넓이를 위한 배열
		
		//검색조건
		
		//맵에 조건을 넣는다.
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));

		// 변수에 값 받아서 넣음
		if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
		if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
		if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
		if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
		System.out.println("conform_yn =======>0"+request.getParameter("conform_yn"	));
		if(request.getParameter("conform_yn"	)!=null) {																						//확인여부
			if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
			if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
		}
		if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt", request.getParameter("code_receipt"	));	//입금장소
	//	System.out.println("asdfasdf = "+request.getParameter("code_receipt"));
		if(request.getParameter("cfyn"	)!=null){ //선인증목록
			if("Y".equals(request.getParameter("cfyn"	)))
				map.put("cfyn"	, "2");	 
		}
		//검색
		List<Map> memberlist = new ArrayList();
		if(request.getParameter("duesp"	)!=null){ //선인증목록
			memberlist=dao.getMemberDuesPtabelList(map);
		} else {
			memberlist=dao.getMemberDuesList(map);
		}
		
		//여기까지 변경--------------------------------------------------
		
		String name			="memberDuesList"; //프로그램명
		String s_name		="회원 회비처리"; //엑셀 시트명
		
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
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.23
	 * 작  업 : membershipForm		회원증 출력 폼으로 포워딩
	 */
	public ActionForward membershipForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		memberDuesDao dao=new memberDuesDao();
			
		//검색을 위한 파라메터를 맵에 넣는다.
		// 변수에 값 받아서 넣음
		if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
		if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
		if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
		if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
		if(request.getParameter("conform_yn"	)!=null) {																						//확인여부
			if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
			if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
		}
		if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	 , request.getParameter("code_receipt"	));	//입금장소
		if(request.getParameter("code_pay_flag"	)!=null) map.put("code_pay_flag" , request.getParameter("code_pay_flag"	));	//입금방법

		//전체 갯수를 구한다.
		List<Map> count = dao.getMembershipCnt(map);		
		int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();

		request.setAttribute("ncount",ncount);
/*
		request.setAttribute("regi_dt"		,request.getParameter("regi_dt"		));
		request.setAttribute("dues_gubun"	,request.getParameter("dues_gubun"	));
		request.setAttribute("code_member"	,request.getParameter("code_member"	));
		request.setAttribute("code_bran"	,request.getParameter("code_bran"	));
		request.setAttribute("code_sub"		,request.getParameter("code_sub"	));
		request.setAttribute("incom_flag"	,request.getParameter("incom_flag"	));
		request.setAttribute("conform_yn"	,request.getParameter("conform_yn"	));
		request.setAttribute("code_receipt"	,request.getParameter("code_receipt"));
		*/
		return (mapping.findForward("membershipForm"));
	}
	

	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.23
	 * 작  업 : membershipLink		회원증 출력 링크 화면으로 링크
	 */
	public ActionForward membershipLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		memberDuesDao dao=new memberDuesDao();
		String param = "doc_code=0105&doc_seq=";
			
		//검색을 위한 파라메터를 맵에 넣는다.
		// 변수에 값 받아서 넣음
		if(request.getParameter("nstart"	)!=null) map.put("nstart"	, request.getParameter("nstart"	));
		if(request.getParameter("nend"		)!=null) map.put("nend"		, request.getParameter("nend"	));
		
		if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
		if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
		if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
		if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
		if(request.getParameter("conform_yn"	)!=null) {																						//확인여부
			if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
			if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
		}
		if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	, request.getParameter("code_receipt"	));	//입금장소
		
		//화면에 출력할 값을 검색한다.-------------------------------**조회
		List<Map> list=dao.getMembershipList(map);

		for( int i=0; i<list.size(); i++) {
			if( i > 0) param+=",";			
			param+="'"+list.get(i).get("code_MS")+"'";
			System.out.println("asdfasdfadsf = "+list.get(i).get("code_MS"));
			System.out.println("asdfasdfadsf = "+list.get(i).get("code_pers"));
			System.out.println("asdfasdfadsf = "+list.get(i).get("dues_gubun"));
			System.out.println("asdfasdfadsf = "+list.get(i).get("dues_h_seq"));
		}

		request.setAttribute("btnCnt", request.getParameter("btnCnt"));
		request.setAttribute("param",param);
		return (mapping.findForward("membershipLink"));
	}
	


	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.17
	 * 작  업 : sendDMForm		DM 발송 화면 폼 
	 */
	public ActionForward sendDMForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		memberDuesDao dao=new memberDuesDao();
		
		String chk	=request.getParameter("chk");		//선택, 전체 여부

		//선택여부 등록자 넣음
		request.setAttribute("chk"		, request.getParameter("chk"		));	//선택, 전체 여부
		String register = request.getParameter("register"	);	//등록자
		request.setAttribute("register"	, URLDecoder.decode(register,"UTF-8"));	//등록자
		
		if("CHK".equals(chk)) {//선택 저장
			

			//전체 개수 구함
			String temp			= request.getParameter("code_pers"		);	//회원 번호
			String code_pers[]	= temp.split(",");

			//회원번호 넣음
			request.setAttribute("param"	,"&code_pers="+request.getParameter("code_pers"));
			//전체개수 넣음
			request.setAttribute("ncount"	, code_pers.length);
			
		} else if("ALL".equals(chk)) {

			//개수 검색해서 넣음
			// 변수에 값 받아서 넣음
			if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
			if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
			if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
			if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
			if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
			if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
			if(request.getParameter("conform_yn"	)!=null) {	//확인여부
				if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
				if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
			}
			if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	 , request.getParameter("code_receipt"	));	//입금장소
			if(request.getParameter("code_pay_flag"	)!=null) map.put("code_pay_flag" , request.getParameter("code_pay_flag"	));	//입금방법
			if(request.getParameter("cfyn"	)!=null){ //선인증목록
				if("Y".equals(request.getParameter("cfyn"	)))
					map.put("cfyn"	, "2");	 
			}
			//전체 개수 검색
			List<Map> count = dao.getDuesDMForCnt(map);
			String t = count.get(0).get("ncount")+"";
			int ncount = Integer.parseInt(t);
		
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param = "";
			if(request.getParameter("regi_dt"		)!=null) param+="&regi_dt="		 + request.getParameter("regi_dt"		);	//등록일
			if(request.getParameter("dues_gubun"	)!=null) param+="&dues_gubun="	 + request.getParameter("dues_gubun"	);	//회비구분
			if(request.getParameter("code_member"	)!=null) param+="&code_member="	 + request.getParameter("code_member"	);	//회원구분
			if(request.getParameter("code_bran"		)!=null) param+="&code_bran="	 + request.getParameter("code_bran"		);	//소속지부
			if(request.getParameter("code_sub"		)!=null) param+="&code_sub="	 + request.getParameter("code_sub"		);	//산하단체
			if(request.getParameter("incom_flag"	)!=null) param+="&incom_flag="	 + request.getParameter("incom_flag"	);	//완납여부
			if(request.getParameter("conform_yn"	)!=null) param+="&conform_yn="	 + request.getParameter("conform_yn"	);	//확인여부
			if(request.getParameter("code_receipt"	)!=null) param+="&code_receipt=" + request.getParameter("code_receipt"	);	//입금장소
			if(request.getParameter("code_pay_flag"	)!=null) param+="&code_pay_flag="+ request.getParameter("code_pay_flag"	);	//입금방법
			if(request.getParameter("cfyn"	)        !=null) param+="&cfyn=Y";	//선인증여부
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);

		}
		request.setAttribute("from", "memberDues");		//조건검색인지, 회비처리인지 구분
		return (mapping.findForward("sendDMForm"));
	}


	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.17
	 * 작  업 : sendDMLink		DM 발송 실행 링크 
	 */
	public ActionForward sendDMLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		List<Map> count = null; //오류났을때 10개 이상인가 체크용
		List<Map> list= null;
		
		Map map = new HashMap();
		memberDuesDao dao=new memberDuesDao();
		
		//오류 출력 여부는 우선 제외한다.
	//	String error_code_pers = "";
		int tot_cnt = 0;
		int success_cnt = 0;
		int faile_cnt = 0;
		
		String chk		=request.getParameter("chk");		//선택, 전체 여부
		String register	=request.getParameter("register");	//유저ID//생성자
		register	=URLDecoder.decode(register,"UTF-8");

		int outcount = 0;	//화면으로 뿌리는 개수
		
		if("CHK".equals(chk)) {//선택 저장
			
			String temp			= request.getParameter("code_pers"		);	//회원 번호
			String code_pers[]	= temp.split(",");
			
			tot_cnt = code_pers.length;

			map.put("code_book_kind", "3");	//dm 종류
			count = dao.getDuesDmForSeq(map);
			String dm_print_yymm_seq = count.get(0).get("dm_print_yymm_seq")+"";
			//for 문 돌면서 저장한다.
			for( int i=0; i<code_pers.length; i++) {

				map.clear();
				
				if(code_pers.length>0) {
					//현재 seq 값을 알아와
					map.put("code_book_kind", "3");	//dm 종류
					map.put("dm_pers_code"	, code_pers[i]);//회원 번호
					map.put("dm_print_yymm_seq"	, dm_print_yymm_seq);
	
						//대상을 조회하고
						map.put("code_pers"			, code_pers[i]);
						list=dao.getDuesDMForList(map,true);
						
						if(list.size() > 0) {
							map.put("rece_yn"			, "2");			//수신여부
							map.put("dm_creater"		, register);	//DM생성자
							map.put("dm_name"			, "회비처리");//DM명
							
							if(list.get(0).get("code_member") != null) map.put("code_member"	, list.get(0).get("code_member"	));
							if(list.get(0).get("pers_name"	) != null) map.put("pers_name"		, list.get(0).get("pers_name"	));
							if(list.get(0).get("lic_no"		) != null) map.put("lic_no"			, list.get(0).get("lic_no"		));
							if(list.get(0).get("code_post"	) != null) map.put("code_post"		, list.get(0).get("code_post"	));
							if(list.get(0).get("pers_add"	) != null) map.put("pers_add"		, list.get(0).get("pers_add"	));
							if(list.get(0).get("pers_hp"	) != null) map.put("pers_hp"		, list.get(0).get("pers_hp"		));
							if(list.get(0).get("email"		) != null) map.put("email"			, list.get(0).get("email"		));
							if(list.get(0).get("code_bran"	) != null) map.put("code_bran"		, list.get(0).get("code_bran"	));
							if(list.get(0).get("print_call"	) != null) map.put("print_call"		, list.get(0).get("print_call"	));
							if(list.get(0).get("oper_comp_name1") != null) map.put("oper_comp_name1", list.get(0).get("oper_comp_name1"));
							if(list.get(0).get("code_pers"	) != null) map.put("code_place"		, list.get(0).get("code_place"	));
							
							//셋 한다.
							try {
								dao.setDuesDMList(map);
								success_cnt++;
							} catch (Exception e) {
								faile_cnt++;
							}
						}
				}
			}
		} else if("ALL".equals(chk)) {
 
			// 변수에 값 받아서 넣음
			
			if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
			if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
			if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
			if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
			if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
			if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
			if(request.getParameter("conform_yn"	)!=null) {	//확인여부
				if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
				if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
			}
			if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	, request.getParameter("code_receipt"	));	//입금장소
			if(request.getParameter("cfyn"	)!=null){ //선인증목록
				if("Y".equals(request.getParameter("cfyn"	)))
					map.put("cfyn"	, "2");	 
			}
/*
			System.out.println("1. regi_dt      ="+request.getParameter("regi_dt"		));
			System.out.println("2. dues_gubun   ="+request.getParameter("dues_gubun"	));
			System.out.println("3. code_member  ="+request.getParameter("code_member"	));
			System.out.println("4. code_bran    ="+request.getParameter("code_bran"		));
			System.out.println("5. code_sub     ="+request.getParameter("code_sub"		));
			System.out.println("6. incom_flag   ="+request.getParameter("incom_flag"	));
			System.out.println("7. conform_yn   ="+request.getParameter("conform_yn"	));
			System.out.println("8. code_receipt ="+request.getParameter("code_receipt"	));
*/			
			//대상 검색
			list=dao.getDuesDMForList(map,true);

			tot_cnt = list.size();

			map.put("code_book_kind", "3");	//dm 종류
			count = dao.getDuesDmForSeq(map);
			String dm_print_yymm_seq = count.get(0).get("dm_print_yymm_seq")+"";

			//여기서 포문 돌면서 인서트 한다.
			for( int i=0; i<list.size(); i++) {

				map.clear();
				
				//현재 seq 값을 알아와
				map.put("code_book_kind", "3");	//dm 종류

					map.clear();

					map.put("dm_pers_code"		, list.get(i).get("code_pers"));//회원 번호
					map.put("code_book_kind"	, "3");
					map.put("dm_print_yymm_seq"	, dm_print_yymm_seq);
					map.put("rece_yn"			, "2");
					map.put("dm_creater"		, register);
					map.put("dm_name"			, "회비처리");//DM명
					
					if(list.get(i).get("code_member") != null) map.put("code_member"	, list.get(i).get("code_member"));
					if(list.get(i).get("pers_name"	) != null) map.put("pers_name"		, list.get(i).get("pers_name"));
					if(list.get(i).get("lic_no"		) != null) map.put("lic_no"			, list.get(i).get("lic_no"));
					if(list.get(i).get("code_post"	) != null) map.put("code_post"		, list.get(i).get("code_post"));
					if(list.get(i).get("pers_add"	) != null) map.put("pers_add"		, list.get(i).get("pers_add"));
					if(list.get(i).get("pers_hp"	) != null) map.put("pers_hp"		, list.get(i).get("pers_hp"));
					if(list.get(i).get("email"		) != null) map.put("email"			, list.get(i).get("email"));
					if(list.get(i).get("code_bran"	) != null) map.put("code_bran"		, list.get(i).get("code_bran"));
					if(list.get(i).get("print_call"	) != null) map.put("print_call"		, list.get(i).get("print_call"));
					if(list.get(i).get("code_pers"	) != null) map.put("code_place"		, list.get(i).get("code_place"));
					if(list.get(i).get("oper_comp_name1") != null) map.put("oper_comp_name1", list.get(i).get("oper_comp_name1"));

					//셋 한다.
					try {

						dao.setDuesDMList(map);
						success_cnt++;

					} catch (Exception e) {
						faile_cnt++;
					}
				
			}

		}

		//request.setAttribute("error_code_pers",error_code_pers);
		request.setAttribute("tot_cnt",tot_cnt);
		request.setAttribute("success_cnt",success_cnt);
		request.setAttribute("faile_cnt",faile_cnt);
		
		return (mapping.findForward("sendAllLink"));

	}

	

	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.28
	 * 작  업 : sendUmsForm		문자 발송 화면 폼 
	 */
	public ActionForward sendUmsForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		memberDuesDao dao=new memberDuesDao();
		
		String chk	=request.getParameter("chk");		//선택, 전체 여부

		//선택여부 등록자 넣음
		request.setAttribute("chk"		, request.getParameter("chk"		));	//선택, 전체 여부
		request.setAttribute("register"	, request.getParameter("register"	));	//등록자
		
		if("CHK".equals(chk)) {//선택 저장
			

			//전체 개수 구함
			String temp			= request.getParameter("pers_hp"		);	//전화 번호
			String pers_hp[]	= temp.split(",");

			
			request.setAttribute("ncount"	, pers_hp.length);								//전체개수 넣음
			request.setAttribute("param"	,"&pers_hp="+request.getParameter("pers_hp"));	//전화 번호 넣음
			request.setAttribute("pers_hp"	, request.getParameter("pers_hp"));				//전화 번호만
			request.setAttribute("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));
			request.setAttribute("code_pers"	, request.getParameter("code_pers"));				
			
		} else if("ALL".equals(chk)) {

			//개수 검색해서 넣음
			// 변수에 값 받아서 넣음
			if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
			if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
			if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
			if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
			if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
			if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
			if(request.getParameter("conform_yn"	)!=null) {	//확인여부
				if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
				if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
			}
			if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	 , request.getParameter("code_receipt"	));	//입금장소
			if(request.getParameter("code_pay_flag"	)!=null) map.put("code_pay_flag" , request.getParameter("code_pay_flag"	));	//입금방법
			if(request.getParameter("cfyn"	)!=null){ //선인증목록
				if("Y".equals(request.getParameter("cfyn"	)))
					map.put("cfyn"	, "2");	 
			}

			//전체 개수 검색
			List<Map> count = dao.getDuesUmsForCnt(map);
			String t = count.get(0).get("ncount")+"";
			int ncount = Integer.parseInt(t);

			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
			
			map.clear();
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param = "";
		
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			if(request.getParameter("regi_dt"		)!=null) param+="&regi_dt="		 + request.getParameter("regi_dt"		);	//등록일
			if(request.getParameter("dues_gubun"	)!=null) param+="&dues_gubun="	 + request.getParameter("dues_gubun"		);	//회비구분
			if(request.getParameter("code_member"	)!=null) param+="&code_member="	 + request.getParameter("code_member"	);	//회원구분
			if(request.getParameter("code_bran"		)!=null) param+="&code_bran="	 + request.getParameter("code_bran"		);	//소속지부
			if(request.getParameter("code_sub"		)!=null) param+="&code_sub="	 + request.getParameter("code_sub"		);	//산하단체
			if(request.getParameter("incom_flag"	)!=null) param+="&incom_flag="	 + request.getParameter("incom_flag"		);	//완납여부
			if(request.getParameter("conform_yn"	)!=null) param+="&conform_yn="	 + request.getParameter("conform_yn"		);	//확인여부
			if(request.getParameter("code_receipt"	)!=null) param+="&code_receipt=" + request.getParameter("code_receipt"	);	//입금장소
			if(request.getParameter("code_pay_flag"	)!=null) param+="&code_pay_flag="+ request.getParameter("code_pay_flag"	);	//입금방법
			if(request.getParameter("cfyn"	)        !=null) param+="&cfyn=Y";	//선인증여부 
			//검색 변수 넘김
			request.setAttribute("param", param);
			

		}
		
		request.setAttribute("from", "memberDues" );	//조건검색인지, 회비처리인지 구분
		return (mapping.findForward("sendUmsForm"));
	}


	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.28
	 * 작  업 : sendUmsLink		문자 발송 실행 링크 
	 */
	public ActionForward sendUmsLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		List<Map> count = null; //오류났을때 10개 이상인가 체크용
		List<Map> list= null;
		
		Map map = new HashMap();
		memberDuesDao dao=new memberDuesDao();
		HttpSession session=request.getSession(); 
		
		//오류 출력 여부는 우선 제외한다.
	//	String error_code_pers = "";
		int tot_cnt = 0;
		int success_cnt = 0;
		int faile_cnt = 0;
		
		String chk			=request.getParameter("chk");		//선택, 전체 여부
		String register		=request.getParameter("register");	//유저ID//생성자
		
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
    	
		
	/*	System.out.println("msg_type = "+msg_type);
		System.out.println("param = "+param);
		System.out.println("send_phone = "+send_phone);
		System.out.println("subject = "+subject);
		System.out.println("msg_body = "+msg_body);
		System.out.println("chk = "+chk);*/
		int outcount = 0;	//화면으로 뿌리는 개수

		if("CHK".equals(chk)) {//선택 저장test
			
			String temp			= request.getParameter("pers_hp"		);	//전화번호
			String pers_hp[]	= temp.split(",");
			String temp1		= URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");	//이름
			String pers_name[]	= temp1.split(",");
			String temp2		= StringUtil.NVL(request.getParameter("code_pers"));
			String code_pers[]	= temp2.split(",");
			
			tot_cnt = pers_hp.length;
			//for 문 돌면서 저장한다.
			for( int i=0; i<pers_hp.length; i++) {

				if(!"".equals(temp2)){
					map.put("code_pers"		, code_pers[i] );
					list = null;
					list=dao.getDuesUmsList(map,true);
					if(list!=null && list.size()>0) pers_hp[i] = (String) list.get(0).get("pers_hp");
				}
				
				map.clear();
				
				if(pers_hp.length>0) {
					
					//seq 번호는 필요 없다.
					//아이디만 있으면 되므로 대상 조회가 필요 없다.아이디도 위에서 넣었다.
					map.put("msg_type"		, msg_type );
					map.put("dest_phone"	, pers_hp[i]);
					map.put("send_phone"	, send_phone);
					map.put("subject"		, subject);
					map.put("msg_body"		, msg_body);
					map.put("register"		, register);
					map.put("etc1",                      session.getAttribute("G_ID"));
		    		map.put("etc2",                      session.getAttribute("G_BRAN"));
		    		map.put("etc3",         pers_name[i]);
		    		System.out.println("pers_name====>"+pers_name[i]);
		    		//2013.01.16추가
		    		map.put("umscnt"		, i+1);
					//셋 한다.
					try {
						if(request_time.length() > 0 ) {
							map.put("request_time", request_time);
							dao.setDuesUmsResultList(map);
						}else {
							dao.setDuesUmsList(map);
						}
						success_cnt++;
					} catch (Exception e) {
						faile_cnt++;
					}
				}
			}
		} else if("ALL".equals(chk)) {

			// 변수에 값 받아서 넣음
			
			if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
			if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
			if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
			if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
			if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
			if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
			if(request.getParameter("conform_yn"	)!=null) {	//확인여부
				if( "Y".equals(request.getParameter("conform_yn")) ) map.put("conform_y"	, "Y"	);//확인
				if( "N".equals(request.getParameter("conform_yn")) ) map.put("conform_n"	, "N"	);//미확인	
			}
			if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	, request.getParameter("code_receipt"	));	//입금장소
			if(request.getParameter("cfyn"	)!=null){ //선인증목록
				if("Y".equals(request.getParameter("cfyn"	)))
					map.put("cfyn"	, "2");	 
			}

/*
			System.out.println("1. regi_dt      ="+request.getParameter("regi_dt"		));
			System.out.println("2. dues_gubun   ="+request.getParameter("dues_gubun"	));
			System.out.println("3. code_member  ="+request.getParameter("code_member"	));
			System.out.println("4. code_bran    ="+request.getParameter("code_bran"		));
			System.out.println("5. code_sub     ="+request.getParameter("code_sub"		));
			System.out.println("6. incom_flag   ="+request.getParameter("incom_flag"	));
			System.out.println("7. conform_yn   ="+request.getParameter("conform_yn"	));
			System.out.println("8. code_receipt ="+request.getParameter("code_receipt"	));
*/			
			//대상 검색//여기서 인덱스 오류남
			list=dao.getDuesUmsList(map,true);

			tot_cnt = list.size();
			//여기서 포문 돌면서 인서트 한다.
			for( int i=0; i<list.size(); i++) {

				map.clear();

				//deq는 알아오지 않는다.


				//휴대번호만 있으면 되므로 대상 조회가 필요 없다
				map.put("msg_type"		, msg_type );
				map.put("dest_phone"	, list.get(i).get("pers_hp"));
				map.put("send_phone"	, send_phone);
				map.put("subject"		, subject);
				map.put("msg_body"		, msg_body);
				map.put("register"		, register);
				map.put("etc1",                      session.getAttribute("G_ID"));
	    		map.put("etc2",                      session.getAttribute("G_BRAN"));
	    		map.put("etc3", list.get(i).get("pers_name"));
	    		//2013.01.16추가
	    		map.put("umscnt"		, i+1);
				//셋 한다.
				try {
					if(request_time.length() > 0 ) {
						map.put("request_time", request_time);
						dao.setDuesUmsResultList(map);
					}else {
						dao.setDuesUmsList(map);
					}
					success_cnt++;
				} catch (Exception e) {
					faile_cnt++;
				}		
			}
		}

		//request.setAttribute("error_code_pers",error_code_pers);
		request.setAttribute("tot_cnt",tot_cnt);
		request.setAttribute("success_cnt",success_cnt);
		request.setAttribute("faile_cnt",faile_cnt);
		
		return (mapping.findForward("sendAllLink"));

	}

	
	
	

	
	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.26
	 * 작   업 : memberDues			회원 회비처리 실 조회 화면으로 포워딩
	 */
	public ActionForward memberDues(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		//메뉴 위치 보낸다.
		request.setAttribute("menu", "memberDuesList");

		//공통쿼리 제이슨으로 보낸다.
		/*List<HashMap<String, String>> comList	= CommonCode.getInstance().getCodeList();
		JSONArray jsoncode	= JSONArray.fromObject(comList);
		request.setAttribute("comList",jsoncode);*/
		
		return (mapping.findForward("memberDuesList"));
	}
	
	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.26
	 * 작   업 : memberDuesList 	회원 회비처리 리스트 조회
	 */
	public ActionForward memberDuesList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		memberDuesDao dao=new memberDuesDao();
		
		//조회조건 넣기
		if(request.getParameter("isSelect")!=null){
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
					
			
			//검색을 위한 파라메터를 맵에 넣는다.
			// 변수에 값 받아서 넣음
			if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
			if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
			if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
			if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
			if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
			if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
			if(request.getParameter("conform_yn"	)!=null) {																						//확인여부
				if( "Y".equals(request.getParameter("conform_yn")) ) {
					map.put("conform_y"	, "Y"	);//확인
					map.put("conform_n"	, ""	);//미확인	
				}
				if( "N".equals(request.getParameter("conform_yn")) ) {
					map.put("conform_y"	, ""	);//확인
					map.put("conform_n"	, "N"	);//미확인	
				}
			}
			if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	, request.getParameter("code_receipt"	));	//입금장소
			//2022.06.24 추가
			if(request.getParameter("code_pay_flag"	)!=null) map.put("code_pay_flag", request.getParameter("code_pay_flag"	));	//입금방법
			
			System.out.println("cfyn=================>"+request.getParameter("cfyn"	));
			if(request.getParameter("cfyn"	)!=null){ //선인증목록
				if("Y".equals(request.getParameter("cfyn"	)))
					map.put("cfyn"	, "2");	 
			}  
			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.---**조회
			List<Map> count = new ArrayList();
			if(request.getParameter("duesp"	)!=null){ 
				count=dao.getMemberDuesPtabelCount(map);		
			} else {
				count = dao.getMemberDuesCount(map);		
			}
			
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
			int ntotpage = (ncount/nrows)+1;
			
			//화면에 출력할 값을 검색한다.-------------------------------**조회			
			List<Map> list = new ArrayList();
			if(request.getParameter("duesp"	)!=null){ 
				list=dao.getMemberDuesPtabelList(map);
			} else {
				list=dao.getMemberDuesList(map);
			}

			//jsp에 넘겨줄 result값을 만든다.			
			StringBuffer result = new StringBuffer();

			//입출일자
			String pres_let = "";
			//시작일자
			String auth_start		= "";
			//종료일자
			String auth_end		= "";
			
			result.append("{\"page\":\""	+npage		+"\",");
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");
			
			//서브 셀렉트 검사
			for(int i=0;i<list.size();i++){

				pres_let	= list.get(i).get("pres_let_dt")	== null ? "" : list.get(i).get("pres_let_dt")+"";
				if(pres_let.length() > 0) {
					String a = pres_let.substring(0, 4) + "년 " + pres_let.substring(4, 6) + "월 " + pres_let.substring(6, 8) + "일";
					pres_let = a;
				}

				auth_start	= list.get(i).get("auth_start")	== null ? "" : list.get(i).get("auth_start")+"";
				auth_end	= list.get(i).get("auth_end")	== null ? "" : list.get(i).get("auth_end")+"";
				if(auth_start.length() > 0) {
					String a = auth_start.substring(0, 4) + "년 " + auth_start.substring(4, 6) + "월 " + auth_start.substring(6, 8) + "일";
					auth_start = a;
				}
				if(auth_end.length() > 0) {
					if(request.getParameter("duesp"	)!=null){ //선인증목록
						auth_end = auth_end.substring(0, 4) + "년 " + auth_end.substring(4,auth_end.length()) + "차 ";
					} else {
						auth_end = auth_end.substring(0, 4) + "년 " + auth_end.substring(4, 6) + "월 " + auth_end.substring(6, 8) + "일";
					}
				}
				
				if(i>0) result.append(",");
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				result.append("\"" + ( list.get(i).get("ncount")			== null ? "" : list.get(i).get("ncount")			)+"\",");//번호
				result.append("\"" + ( list.get(i).get("code_pers")			== null ? "" : list.get(i).get("code_pers")			)+"\",");//회원코드
				result.append("\"" + ( list.get(i).get("pers_name")			== null ? "" : list.get(i).get("pers_name")			)+"\",");//이름
				result.append("\"" + ( list.get(i).get("lic_no")			== null ? "" : list.get(i).get("lic_no")			)+"\",");//이름
				result.append("\"" + ( list.get(i).get("code_member")		== null ? "" : list.get(i).get("code_member")		)+"\",");//회원구분
				result.append("\"" + ( list.get(i).get("han_code_member")	== null ? "" : list.get(i).get("han_code_member")	)+"\",");//한_회원구분
				result.append("\"" + ( list.get(i).get("code_bran")			== null ? "" : list.get(i).get("code_bran")			)+"\",");//소속지부
				result.append("\"" + ( list.get(i).get("han_code_bran")		== null ? "" : list.get(i).get("han_code_bran")		)+"\",");//한_소속지부
				result.append("\"" + ( list.get(i).get("bigname")			== null ? "" : list.get(i).get("bigname")			)+"\",");//한_소속분과
				result.append("\"" + ( list.get(i).get("sectname")			== null ? "" : list.get(i).get("sectname")			)+"\",");//한_소속분회
				result.append("\"" + ( list.get(i).get("dues_gubun")		== null ? "" : list.get(i).get("dues_gubun")		)+"\",");//회비구분
				result.append("\"" + ( list.get(i).get("han_dues_gubun")	== null ? "" : list.get(i).get("han_dues_gubun")	)+"\",");//한_회비구분
				result.append("\"" + ( list.get(i).get("mem_dues")			== null ? "" : list.get(i).get("mem_dues")			)+"\",");//회비
				result.append("\"" + auth_start																														+"\",");//시작일자
				result.append("\"" + auth_end																															+"\",");//만료일자
				result.append("\"" + ( list.get(i).get("incom_flag")		== null ? "" : list.get(i).get("incom_flag").equals("Y") ?"완납":"미완납"	)	+"\",");//완납여부
				result.append("\"" + ( list.get(i).get("conform_yn")		== null ? "" : list.get(i).get("conform_yn").equals("") ?"미처리":"처리"	)	+"\",");//확인여부
				result.append("\"" + ( list.get(i).get("code_sub")			== null ? "" : list.get(i).get("code_sub")			)+"\",");//산하단체
				result.append("\"" + ( list.get(i).get("han_code_sub")		== null ? "" : list.get(i).get("han_code_sub")		)+"\",");//한_산하단체
				result.append("\"" + ( list.get(i).get("regi_dt")			== null ? "" : list.get(i).get("regi_dt")			)+"\",");//등록일
				result.append("\"" + ( list.get(i).get("email")				== null ? "" : list.get(i).get("email")				)+"\",");//이메일
				result.append("\"" + ( list.get(i).get("pers_hp")			== null ? "" : list.get(i).get("pers_hp")			)+"\",");//핸폰
				result.append("\"" + ( list.get(i).get("code_receipt")		== null ? "" : list.get(i).get("code_receipt")		)+"\",");//입금위치
				result.append("\"" + ( list.get(i).get("adrs")				== null ? "" : list.get(i).get("adrs")				)+"\",");//주소
				result.append("\"" + ( list.get(i).get("company_name")		== null ? "" : list.get(i).get("company_name")		)+"\",");//회사명
				result.append("\"" + ( list.get(i).get("cadrs")				== null ? "" : list.get(i).get("cadrs")				)+"\",");//회사주소
				result.append("\"" + ( list.get(i).get("code_MS")			== null ? "" : list.get(i).get("code_MS")			)+"\",");//회원증발급용
				result.append("\"" + ( list.get(i).get("dues_h_seq")		== null ? "" : list.get(i).get("dues_h_seq")		)+"\"");
				result.append("]}");
			}
			result.append("]}");
			request.setAttribute("result",result);
		}
		
		return (mapping.findForward("ajaxout"));
	}

	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.09
	 * 작   업 : memberDuesBatch	회원 회비처리 일괄 배치처리
	 */
	public ActionForward memberDuesBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		//배치 처리
		
		Map<String, Object> map		= new HashMap();	//필요 회원 리스트 뽑아오는 조건
		Map<String, Object> dMap	= new HashMap();	//년회비, 평생회비 및 산하단체회비 가져오는 조건
		Map<String, Object> iMap	= new HashMap();	//평생회비 예수금 테이블에 인서트 해넣을 자료 넣는 조건
		Map<String, Object> ihMap	= new HashMap();	//회원별 회비 테이블에 수정 해넣을 자료 넣는 조건
		
		memberDuesDao dao=new memberDuesDao();
		
		if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일

		if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
		if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
		if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
		if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
		if(request.getParameter("conform_yn"	)!=null) {																						//확인여부
			if( "Y".equals(request.getParameter("conform_yn")) ) {
				map.put("conform_y"	, "Y"	);//확인
				map.put("conform_n"	, ""	);//미확인	
			}
			if( "N".equals(request.getParameter("conform_yn")) ) {
				map.put("conform_y"	, ""	);//확인
				map.put("conform_n"	, "N"	);//미확인	
			}
		}
		if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	 , request.getParameter("code_receipt"	));	//입금장소
		if(request.getParameter("code_pay_flag"	)!=null) map.put("code_pay_flag" , request.getParameter("code_pay_flag"	));	//입금방법
		System.out.println("cfyn=================>"+request.getParameter("cfyn"	));
		if(request.getParameter("cfyn"	)!=null){ //선인증목록
			if("Y".equals(request.getParameter("cfyn"	)))
				map.put("cfyn"	, "2");	 
		}  
		if(request.getParameter("code_pers"	)!=null) {
			List<String> itemInfoList =  Arrays.asList(request.getParameter("code_pers"	).split("__"));
			List<List<String>> code_persInfoList = new ArrayList<List<String>>();
			for(String itemInfo : itemInfoList){
				List<String> code_persInfo =  Arrays.asList(itemInfo.split("_"));
				code_persInfoList.add(code_persInfo);
			}
			map.put("code_pers"	, code_persInfoList);
		}
		
//		map.put("incom_flag", "Y"	); //완납여부
//		map.put("conform_y"	, ""	);//확인
//		map.put("conform_n"	, "N"	);//미확인	
//		map.put("conform_n"	, "N"	); //미확인(등록일자 regi_dt 가 null)
		map.put("nstart", 0);
		map.put("nend", 1000000);

		String code_bran	= request.getParameter("code_bran");
		String code_receipt	= request.getParameter("code_receipt");
		String dues_gubun	= request.getParameter("dues_gubun");
		
		System.out.println("dues_gubun============>"+StringUtil.nullToStr("널이다", dues_gubun));
		System.out.println("code_bran============>"+StringUtil.nullToStr("널이다", code_bran));
		//산하단체회비인 경우
		if("3".equals(dues_gubun)) {
			map.put("code_receipt", "1");
			System.out.println("산하단체회비");
		//본회이고 본회전체	
		}else if(code_bran==null && "1".equals(code_receipt)) {
			map.put("code_receipt", "1");
			System.out.println("본회이고 본회전체");
		//지회일때 (본회인데 지회선택 이거나, 아예 지회일때)
		}else {
			map.put("code_bran", request.getParameter("code_bran") );	//소속지부	//소속 지부의 지회 처리
			map.put("code_receipt", "2");
			
			System.out.println("지회일때 (본회인데 지회선택 이거나, 아예 지회일때)");
		}
		
		//완납이 Y 이고 regi_dt 가 null 인 것을 검색한다.
		List<Map> list				=dao.getMemberDuesList(map);
		/*필요값
		 * 년회비, 평생회비		산하단체회비
		 * 회비구분					회비구분			dues_gubun
		 * 회원구분코드									code_member
		 * 지부						지부				code_bran
		 * 								산하단체코드		code_sub
		 */
		
		Date today = null;

		for(int i=0; i<list.size(); i++) {
			//배치 처리한다.

		    if( list.get(i).get("dues_gubun").equals("1")) {	//년회비라면
		    	dMap.clear();
				dMap.put("dues_gubun"	, list.get(i).get("dues_gubun")		== null ? "" : list.get(i).get("dues_gubun") 	);
				dMap.put("code_member"	, list.get(i).get("code_member")	== null ? "" : list.get(i).get("code_member")	);
				dMap.put("code_bran"	, list.get(i).get("code_bran")		== null ? "" : list.get(i).get("code_bran") 		);
				dMap.put("code_sub"		, list.get(i).get("code_sub")		== null ? "" : list.get(i).get("code_sub") 		);
				
		    	//회비를 조회한다.
				List<Map> duesList			=dao.getDuesList(dMap);
				
				if(duesList.size() > 0) {	
			    	iMap.put("code_pers"			, list.get(i).get("code_pers"		)	);	//회원코드
			    	iMap.put("dues_gubun"			, list.get(i).get("dues_gubun"		)	);	//회비구분
			    	iMap.put("dues_h_seq"			, list.get(i).get("dues_h_seq"		)	);	//회비관리순번
			    	iMap.put("pers_mem_order"	, "1"											);	//예수금차수
			    	iMap.put("code_bran"			, list.get(i).get("code_bran"		)	);	//해당지부
			    	iMap.put("center_money"		, duesList.get(0).get("head_dues")	);	//본회비
			    	iMap.put("bran_money"			, duesList.get(0).get("bran_dues")	);	//지회비
			    	iMap.put("sub_dues"				, ""											);	//산하단체회비
					
					today = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat year = new SimpleDateFormat("yyyy");
					SimpleDateFormat time = new SimpleDateFormat("MMddHHmm");

			    	iMap.put("receipt_year"			, year.format(today)					);	//입금년도
			    	iMap.put("receipt_dt"			, date.format(today));//time.format(today)	);	//입금일
			    	iMap.put("auth_flag"			, "Y"										);	//인증유무
			    	iMap.put("register", session.getAttribute("G_NAME"));	
			    	//년회비를 넣는다.
			    	dao.setDues_P(iMap);

			    	ihMap.put("code_pers"		, list.get(i).get("code_pers"		));
			    	ihMap.put("dues_gubun"	, list.get(i).get("dues_gubun"		));
			    	ihMap.put("dues_h_seq"		, list.get(i).get("dues_h_seq"		));
			    	//회비 저장후 회원별회비의 등록일을 처리한다. (미확을 확인으로 바꾼다.)
			    	dao.setDues_h_tbl(ihMap);
			    	
					}
				
				////////////////////////////////////////////////////////////
				}else if( list.get(i).get("dues_gubun").equals("4")) {	//CMS회비라면
					dMap.clear();
					dMap.put("dues_gubun"	, list.get(i).get("dues_gubun")		== null ? "" : list.get(i).get("dues_gubun") 	);
					dMap.put("code_member"	, list.get(i).get("code_member")	== null ? "" : list.get(i).get("code_member")	);
					dMap.put("code_bran"	, list.get(i).get("code_bran")		== null ? "" : list.get(i).get("code_bran") 		);
					dMap.put("code_sub"		, list.get(i).get("code_sub")		== null ? "" : list.get(i).get("code_sub") 		);
					
					//회비를 조회한다.
					List<Map> duesList			=dao.getDuesList(dMap);
					
					if(duesList.size() > 0) {	
					iMap.put("code_pers"			, list.get(i).get("code_pers"		)	);	//회원코드
					iMap.put("dues_gubun"			, list.get(i).get("dues_gubun"		)	);	//회비구분
					iMap.put("dues_h_seq"			, list.get(i).get("dues_h_seq"		)	);	//회비관리순번
					iMap.put("pers_mem_order"	, "1"											);	//예수금차수
					iMap.put("code_bran"			, list.get(i).get("code_bran"		)	);	//해당지부
					iMap.put("center_money"		, duesList.get(0).get("head_dues")	);	//본회비
					iMap.put("bran_money"			, duesList.get(0).get("bran_dues")	);	//지회비
					iMap.put("sub_dues"				, ""											);	//산하단체회비
					
					today = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat year = new SimpleDateFormat("yyyy");
					SimpleDateFormat time = new SimpleDateFormat("MMddHHmm");
					
					iMap.put("receipt_year"			, year.format(today)					);	//입금년도
					iMap.put("receipt_dt"			, date.format(today));//time.format(today)	);	//입금일
					iMap.put("auth_flag"			, "Y"										);	//인증유무
					iMap.put("register", session.getAttribute("G_NAME"));	
					//년회비를 넣는다.
					dao.setDues_P(iMap);
					
					ihMap.put("code_pers"		, list.get(i).get("code_pers"		));
					ihMap.put("dues_gubun"	, list.get(i).get("dues_gubun"		));
					ihMap.put("dues_h_seq"		, list.get(i).get("dues_h_seq"		));
					//회비 저장후 회원별회비의 등록일을 처리한다. (미확을 확인으로 바꾼다.)
					dao.setDues_h_tbl(ihMap);
				
				}
				/////////////////////////////////////////////////////////////

	    	}else  if( list.get(i).get("dues_gubun").equals("3")) {	//산업체회비라면
	    		dMap.clear();
				dMap.put("dues_gubun"		, list.get(i).get("dues_gubun")		== null ? "" : list.get(i).get("dues_gubun") 	);
				dMap.put("code_member"	, list.get(i).get("code_member")	== null ? "" : list.get(i).get("code_member")	);
				dMap.put("code_bran"		, list.get(i).get("code_bran")		== null ? "" : list.get(i).get("code_bran") 		);
				dMap.put("code_sub"		, list.get(i).get("code_sub")		== null ? "" : list.get(i).get("code_sub") 		);

	    		//산업체 회비의 값을 가져와 가진다.
	    		List<Map> duesList	=dao.getDuesList(dMap);
		
				if(duesList.size() > 0) {	
			    	iMap.put("code_pers"			, list.get(i).get("code_pers")			);	//회원코드
			    	iMap.put("dues_gubun"			, list.get(i).get("dues_gubun")			);	//회비구분
			    	iMap.put("dues_h_seq"			, list.get(i).get("dues_h_seq")			);	//회비관리순번
			    	iMap.put("pers_mem_order"		, "1"									);	//예수금차수
			    	iMap.put("code_bran"			, list.get(i).get("code_bran")			);	//해당지부
			    	iMap.put("center_money"			, duesList.get(0).get("head_dues")		);	//본회비
			    	iMap.put("bran_money"			, duesList.get(0).get("bran_dues")		);	//지회비
			    	iMap.put("sub_dues"				, ""	);	//산하단체회비

					
					today = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat year = new SimpleDateFormat("yyyy");
					SimpleDateFormat time = new SimpleDateFormat("MMddHHmm");

			    	iMap.put("receipt_year"			, year.format(today)					);	//입금년도
			    	iMap.put("receipt_dt"			, date.format(today));//time.format(today)	);	//입금일
			    	iMap.put("auth_flag"				, "Y"										);	//인증유무
			    	iMap.put("register", session.getAttribute("G_NAME"));
			    	//년회비를 넣는다.
			    	dao.setDues_P(iMap);

			    	ihMap.put("code_pers"		, list.get(i).get("code_pers"		));
			    	ihMap.put("dues_gubun"	, list.get(i).get("dues_gubun"		));
			    	ihMap.put("dues_h_seq"		, list.get(i).get("dues_h_seq"		));
			    	//회비 저장후 회원별회비의 등록일을 처리한다. (미확을 확인으로 바꾼다.)
			    	dao.setDues_h_tbl(ihMap);
			    	
				}
		    }	
		}
		
		//리턴 (다시 검색)
		return ( memberDuesList(mapping, form, request, response));
		
	}
	
	/*
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.12.05
	 * 작   업 : memberAllLifeDuesBatch	회원 평생회원 회비처리 일괄 배치처리
	 */
	public ActionForward memberDuesAllLifeBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession(); 
		String MM=request.getParameter("MM");
		//배치 처리

		Map<String, Object> map		= new HashMap();	//필요 회원 리스트 뽑아오는 조건
		Map<String, Object> dMap	= new HashMap();	//년회비, 평생회비 및 산하단체회비 가져오는 조건
		Map<String, Object> iMap	= new HashMap();	//평생회비 예수금 테이블에 인서트 해넣을 자료 넣는 조건
		Map<String, Object> ihMap	= new HashMap();	//회원별 회비 테이블에 수정 해넣을 자료 넣는 조건
		
		memberDuesDao dao=new memberDuesDao();
		
		if(request.getParameter("regi_dt"		)!=null) map.put("regi_dt"		, request.getParameter("regi_dt"	));	//등록일
		
		if(request.getParameter("dues_gubun"	)!=null) map.put("dues_gubun"	, request.getParameter("dues_gubun"	));	//회비구분
		if(request.getParameter("code_member"	)!=null) map.put("code_member"	, request.getParameter("code_member"));	//회원구분
		if(request.getParameter("code_bran"		)!=null) map.put("code_bran"	, request.getParameter("code_bran"	));	//소속지부
		if(request.getParameter("code_sub"		)!=null) map.put("code_sub"		, request.getParameter("code_sub"	));	//산하단체
		if(request.getParameter("incom_flag"	)!=null) map.put("incom_flag"	, request.getParameter("incom_flag"	));	//완납여부
		if(request.getParameter("conform_yn"	)!=null) {																						//확인여부
			if( "Y".equals(request.getParameter("conform_yn")) ) {
				map.put("conform_y"	, "Y"	);//확인
				map.put("conform_n"	, ""	);//미확인	
			}
			if( "N".equals(request.getParameter("conform_yn")) ) {
				map.put("conform_y"	, ""	);//확인
				map.put("conform_n"	, "N"	);//미확인	
			}
		}
		if(request.getParameter("code_receipt"	)!=null) map.put("code_receipt"	 , request.getParameter("code_receipt"	));	//입금장소
		if(request.getParameter("code_pay_flag"	)!=null) map.put("code_pay_flag" , request.getParameter("code_pay_flag"	));	//입금방법
		System.out.println("cfyn=================>"+request.getParameter("cfyn"	));
		if(request.getParameter("cfyn"	)!=null){ //선인증목록
			if("Y".equals(request.getParameter("cfyn"	)))
				map.put("cfyn"	, "2");	 
		}  
		if(request.getParameter("code_pers"	)!=null) {
			List<String> itemInfoList =  Arrays.asList(request.getParameter("code_pers"	).split("__"));
			List<List<String>> code_persInfoList = new ArrayList<List<String>>();
			for(String itemInfo : itemInfoList){
				List<String> code_persInfo =  Arrays.asList(itemInfo.split("_"));
				code_persInfoList.add(code_persInfo);
			}
			map.put("code_pers"	, code_persInfoList);
		}

//		map.put("incom_flag", "Y"	); //완납여부
//		map.put("conform_y"	, ""	);//확인
//		map.put("conform_n"	, "N"	);//미확인	
//		map.put("conform_n"	, "N"	); //미확인(등록일자 regi_dt 가 null)
//		map.put("code_receipt", "1");// 평생회비는 본회 밖에 없느니 그냥 본회로 넣자.
		
		map.put("nstart", 0);
		map.put("nend", 1000000);

		Date today = null;	
		today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat time = new SimpleDateFormat("MMddHHmm");

//		System.out.println("평생회비처리 1 ******************************* ");	
		
		String receipt_dt = "";
		if(request.getParameter("regi_dt")!=null){
			receipt_dt = request.getParameter("regi_dt");
		}else{
			receipt_dt = date.format(year);
		}
//		System.out.println("평생회비처리 2 ****** receipt_dt ************* "+receipt_dt);	
//		System.out.println("평생회비처리 MM ****** "+MM);	
	
		List<Map> list=null;
		if("12".equals(MM)) {
//			System.out.println("평생회비처리 3 ****** "+MM);	
			
			//완납이 Y 이고 regi_dt 가 null 인 것을 검색한다. 본회 입금이고, 평생회비인것
			list=dao.getMemberDuesList(map);
			
			for(int i=0; i<list.size(); i++) {//배치 처리한다.
//				System.out.println("평생회비처리 4 ****** "+i);	
	
			    if( list.get(i).get("dues_gubun").equals("2")) {			//평생회비라면
			    	
			    	dMap.clear();
					dMap.put("dues_gubun"	, list.get(i).get("dues_gubun"	)== null ? "" : list.get(i).get("dues_gubun"	));
					dMap.put("code_member"	, list.get(i).get("code_member"	)== null ? "" : list.get(i).get("code_member"	));
					dMap.put("code_bran"	, list.get(i).get("code_bran"	)== null ? "" : list.get(i).get("code_bran"		));
					dMap.put("code_sub"		, list.get(i).get("code_sub"	)== null ? "" : list.get(i).get("code_sub"		));
					
			    	//회비를 조회한다.
//					System.out.println("평생회비처리 5 ****** ");	
					List<Map> duesList			=dao.getDuesList(dMap);
					if(duesList.size() > 0) {			
						for(int ii=1; ii<=21; ii++ ) {//평생 회비는 20년치를 한번에 넣는다.
//							System.out.println("평생회비처리 6 ****** "+ii);	
					    	iMap.put("code_pers"		, list.get(i).get("code_pers"	));	//회원코드
					    	iMap.put("dues_gubun"		, list.get(i).get("dues_gubun"	));	//회비구분
					    	iMap.put("dues_h_seq"		, list.get(i).get("dues_h_seq"	));	//회비관리순번
					    	iMap.put("pers_mem_order"	, ii							);	//예수금차수
					    	
					    	if( ii == 1)	iMap.put("code_bran", list.get(i).get("code_bran"	));	//해당지부
					    	else			iMap.put("code_bran", ""							);	//해당지부
					    	
					    	if( ii == 1 )  	iMap.put("center_money"		, duesList.get(0).get("head_dues"));	//본회비
					    	else 			iMap.put("center_money"		, duesList.get(0).get("head_dues2"));	//본회비
					    	
					    	if( ii == 1 )  	iMap.put("bran_money", duesList.get(0).get("bran_dues1"	));	//지회비
					    	else		    iMap.put("bran_money", duesList.get(0).get("bran_dues"	));	//지회비
					    	
					    	iMap.put("sub_dues"			, ""							);	//산하단체회비
	
							int a = Integer.parseInt(receipt_dt);
							int b = a+(ii-1);
					    	iMap.put("receipt_year"			, b+""				);	//입금년도 //1회차는 올해, 2회차는 올해+1, ... , 20회차는 올해+19
					    	
					    	if( ii == 1 ) {//1회차 분만 입금일과 인증유무를 확인처리한다.
						    	iMap.put("receipt_dt"	, receipt_dt+"1231");//date.format(today)//time.format(today));	//입금일
						    	iMap.put("auth_flag"	, "Y"				);	//인증유무
					    	}else {
						    	iMap.put("receipt_dt"	, ""				);	//입금일
						    	iMap.put("auth_flag"	, "N"				);	//인증유무
					    	}
					    	iMap.put("register", session.getAttribute("G_NAME"));
					    	//년회비를 넣는다.
					    	dao.setDues_P(iMap);
						}
	
				    	ihMap.put("code_pers"	, list.get(i).get("code_pers"		));
				    	ihMap.put("dues_gubun"	, list.get(i).get("dues_gubun"		));
				    	ihMap.put("dues_h_seq"	, list.get(i).get("dues_h_seq"		));
				    	//회비 저장후 회원별회비의 등록일을 처리한다. (미확을 확인으로 바꾼다.)
				    	dao.setDues_h_tbl(ihMap);
				    	
					}		
		    	}
			}
		    
		}//if 

		
		//평생회비 예수금 수정을 위한 조회 (1차 이후 1년 지난것)
		if("03".equals(MM)) {
			
			//자 이제 dues_p 에 저장된 평생 회원중 receipt_year 이 올해 (2012) 인것을 찾아서, 그것의 입금일(receipt_dt, 10090415), 인증유무(auth_flag, 'Y'),
			//		등록일자(regi_date=수정일), 등록자(register=수정자)로 업데이트 한다.
	
			Map<String, Object> dpMap	= new HashMap();
			
	//		dpMap.put("receipt_year", "2014"); //테스트
//			System.out.println("평생회비처리  receipt_dt ****** "+receipt_dt);	
			dpMap.put("receipt_year", receipt_dt);//실처리
	
			//조회해 온다.
			List<Map> dpList	=dao.getDues_p(dpMap);
	
			for(int i=0; i<dpList.size(); i++) {
	
				dpMap.put("receipt_dt"		, receipt_dt+"0331");//time.format(today)				);
				dpMap.put("receipt_year"	, dpList.get(i).get("receipt_year"	));
				dpMap.put("code_pers"		, dpList.get(i).get("code_pers"		));
				dpMap.put("dues_gubun"		, dpList.get(i).get("dues_gubun"	));
				dpMap.put("dues_h_seq"		, dpList.get(i).get("dues_h_seq"	));
				dpMap.put("pers_mem_order"	, dpList.get(i).get("pers_mem_order"));
				dpMap.put("code_bran"		, dpList.get(i).get("bran2"			)==null?"":dpList.get(i).get("bran2"));
				//조회해 온것을 적용한다.
				dao.updateDues_p(dpMap);
			}
		}//1년차 이후것 조회 
		
		//리턴 (다시 검색)
		return ( memberDuesList(mapping, form, request, response));
		
	}

}
