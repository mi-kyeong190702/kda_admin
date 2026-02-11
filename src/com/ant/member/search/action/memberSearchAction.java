package com.ant.member.search.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.common.code.CommonCode;
import com.ant.common.util.CommonUtil;
import com.ant.common.util.StringUtil;
import com.ant.member.dues.dao.memberDuesDao;
import com.ant.member.fund.dao.memberFundDao;
import com.ant.member.search.dao.memberSearchDao;
import com.ant.member.state.dao.memberStateDao;

public class memberSearchAction extends DispatchAction {
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

		//전체건수 구해서 넘긴다.
		Map map = new HashMap();
		memberSearchDao dao=new memberSearchDao();

if(request.getParameter("check"			)!=null) map.put("check"		, request.getParameter("check"));	//주민번호 유형
if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));		//이름
//System.out.println("asdfasdfasdfasdf 엑셀 1 이름 = :"+URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));
String isEnd = "";
//System.out.println("asdfasdfasdfasdf 엑셀 출력에서 isEnd = "+isEnd);
if(request.getParameter("isEnd")!=null) isEnd = request.getParameter("isEnd");;

if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며, 유효자를 검색하기 위해서 아래를 내보낸다.
	if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
	if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일
}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
	if(request.getParameter("auth_start")!=null) map.put("e_auth_start"	, request.getParameter("auth_start"		));
	if(request.getParameter("auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("auth_end"		));
	map.put("isEnd", "Y");
}

//System.out.println("asdfasdfasdfasdf 엑셀 출력에서 isEnd = "+isEnd);

if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬

//둘째줄-----------------------------------------------------------------------------------
if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급

//셋째줄-----------------------------------------------------------------------------------
//if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태

//넷째줄-----------------------------------------------------------------------------------
if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회

//다섯째줄---------------------------------------------------------------------------------
if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

//여섯째줄---------------------------------------------------------------------------------
if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분

		//여기서 구하고 넘긴다.
		List<Map> count = dao.getMemberSearchCount(map);
		int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
		request.setAttribute("ncount", ncount);

		
		//검색 조건을 파라메터로 넘긴다.
		String param = "";
		
		//이름 넘김
		if(request.getParameter("pers_name"	)!=null)request.setAttribute("pers_name",  URLDecoder.decode(request.getParameter("pers_name"),"UTF-8") );

		//첫째줄-----------------------------------------------
//		if(request.getParameter("pers_name")	!=  null)	param+="&pers_name="	+request.getParameter("pers_name");	//이름
		if(request.getParameter("auth_start")	!=  null)	param+="&auth_start="	+request.getParameter("auth_start");		//(회원 유효기간은 시작일만 가지고 한다.)
		if(request.getParameter("auth_end")		!=  null)	param+="&auth_end="		+request.getParameter("auth_end");		//(회원 유효기간은 시작일만 가지고 한다.)

		if(request.getParameter("isEnd")!=null) param+="&isEnd="	+request.getParameter("isEnd");		//만료자


		if(request.getParameter("code_bran")	!=  null)	param+="&code_bran="	+request.getParameter("code_bran");		//지부
		if(request.getParameter("code_place")	!=  null)	param+="&code_place="	+request.getParameter("code_place");		//발송지 수신처
		if(request.getParameter("job_line_code")!=  null)	param+="&job_line_code="+request.getParameter("job_line_code");	//직렬
 		
		//둘째줄-----------------------------------------------
		if(request.getParameter("lic_no")			!=  null)	param+="&lic_no="			+request.getParameter("lic_no");			//면허번호이름
		if(request.getParameter("conform_dt_start")	!=  null)	param+="&conform_dt_start="	+request.getParameter("conform_dt_start");	//입금기간은 확인일자가 시작일과
		if(request.getParameter("conform_dt_end")	!=  null)	param+="&conform_dt_end="	+request.getParameter("conform_dt_end");//종료일 안에 있으면 된다.
		if(request.getParameter("code_big")			!=  null)	param+="&code_big="			+request.getParameter("code_big");		//분과명
		if(request.getParameter("code_call")		!=  null)	param+="&code_call="		+request.getParameter("code_call");		//발송호칭
		if(request.getParameter("job_level_code")	!=  null)	param+="&job_level_code="	+request.getParameter("job_level_code");//직급

		//셋째줄-----------------------------------------------

//		if(request.getParameter("pers_no")			!=  null)	param+="&pers_no="		+request.getParameter("pers_no");//주민등록번호 // JUMIN_DEL
		if(request.getParameter("pers_birth")		!=  null)	param+="&pers_birth="	+request.getParameter("pers_birth");	//생년월일
		if(request.getParameter("code_receipt")		!=  null)	param+="&code_receipt="	+request.getParameter("code_receipt");	//인증장소
		if(request.getParameter("code_sect")		!=  null)	param+="&code_sect="	+request.getParameter("code_sect");		//분회명
		if(request.getParameter("code_scholar")		!=  null)	param+="&code_scholar="	+request.getParameter("code_scholar");	//최종학력
		if(request.getParameter("code_use")			!=  null)	param+="&code_use="		+request.getParameter("code_use");		//운영형태
		
		//넷째줄-----------------------------------------------
		if(request.getParameter("code_member")		!=  null)	param+="&code_member="	+request.getParameter("code_member");	//회원종류
		if(request.getParameter("code_pay_flag")	!=  null)	param+="&code_pay_flag="+request.getParameter("code_pay_flag");	//입금방법 code_inout_gubun이 1인것 중.
		if(request.getParameter("code_small")		!=  null)	param+="&code_small="	+request.getParameter("code_small");		//근무처소분류
		if(request.getParameter("code_univer")		!=  null)	param+="&code_univer="	+request.getParameter("code_univer");	//학위
		if(request.getParameter("code_sub")			!=  null)	param+="&code_sub="		+request.getParameter("code_sub");		//산하단체및 분야회				
		//다섯째줄---------------------------------------------

		if(request.getParameter("kda_level")		!=  null)	param+="&kda_level="	+request.getParameter("kda_level");		//협회직급
		if(request.getParameter("code_certifi")		!=  null)	param+="&code_certifi="	+request.getParameter("code_certifi");	//자격증종류
		if(request.getParameter("print_state")		!=  null)	param+="&print_state="	+request.getParameter("print_state");	//자격증말소자
		if(request.getParameter("id")				!=  null)	param+="&id="			+request.getParameter("id");				//아이디
		if(request.getParameter("pers_state")		!=  null)	param+="&pers_state="	+request.getParameter("pers_state");		//회원상태
		
		//여섯째줄---------------------------------------------
		if(request.getParameter("dues_gubun")		!=	null)	param+="&dues_gubun="	+request.getParameter("dues_gubun");	//회비구분
		

		request.setAttribute("param", param);
		request.setAttribute("url", "memberSearch");
		
	return (mapping.findForward("pers_check"));
			
	}
	
	
	
	


	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.13
	 * 작   업 : memberSearchExcel	회원 조건검색 엑셀 출력
	 */
	public void memberSearchExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map map	= new HashMap();
		Map map1= new HashMap();
		memberSearchDao dao=new memberSearchDao();
		
		String format="yyyyMMdd";
		SimpleDateFormat	sdf		=new SimpleDateFormat(format, Locale.KOREA);
		String				date	=sdf.format(new java.util.Date()); //현재 날짜		
		String				user	=request.getParameter("register"); //유저ID
		
		//여기터 개발자 변경 필요--------------------------------------
		
		String name			="memberSearchList"; //프로그램명
		String s_name		="조건검색"; //엑셀 시트명

		// 학교명 추가 2025.10
String header_e[]={	"pers_name","lic_no", "pers_birth", "han_code_bran","han_code_big","han_code_sect","han_code_member","han_pers_state","auth_start","auth_end",
				"conform_dt","han_code_receipt","pers_post","pers_add","pers_tel","pers_hp","email","han_kda_level","han_code_great","han_code_small",
				"company_name", "company_tel","company_post","company_add","han_job_line_code","id","han_code_place","han_code_call","han_job_level_code","han_code_scholar",
				"han_code_use","han_dues_gubun","han_code_pay_flag", "han_code_univer","han_code_sub","han_code_certifi","print_state","code_pers","memo_text","han_code_school"}; //헤더 영문
String header_k[]={"이름","면허번호", "생년월일", "지부명","분과명","분회명","회원종류", "회원상태","회원유효기간시작일","회원유효기간종료일",
			   "인증일","인증장소","우편번호","집주소","집전화번호","핸드폰","이메일","협회직급","근무처대분류","근무처소분류",
			   "근무처명","근무처전화번호","근무처우편번호","근무처주소","직렬","아이디","발송지","발송호칭","직급","최종학력",
			   "운영형태","회비구분","입금방법","학위","산하단체", "자격증종류", "자격증유효성","회원번호","탈퇴사유","학교명"}; //헤더 국문
	int c_size[]={	15,15,15,15,15,15,15,15,15,15,
					15,15,15,15,15,15,15,15,15,15,
					15,15,15,15,15,15,15,15,15,15,
					15,15,15,15,15,15,15,15,15,15};  //열 넓이를 위한 배열
		//검색조건

		//맵에 조건을 넣는다.
		map.put("nstart", request.getParameter("nstart"));
		map.put("nend"	, request.getParameter("nend"));
		//map.put("nend", 20000);

//if(request.getParameter("check"			)!=null) map.put("check"		, request.getParameter("check"));	//주민번호 유형 // JUMIN_DEL
if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));		//이름
//System.out.println("asdfasdfasdfasdf 엑셀 이름 = :"+URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));
String isEnd = "";
//System.out.println("asdfasdfasdfasdf 엑셀 출력에서 isEnd = "+isEnd);
if(request.getParameter("isEnd")!=null) isEnd = request.getParameter("isEnd");;

if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며, 유효자를 검색하기 위해서 아래를 내보낸다.
	if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
	if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일
}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
	if(request.getParameter("auth_start")!=null) map.put("e_auth_start"	, request.getParameter("auth_start"		));
	if(request.getParameter("auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("auth_end"		));
	map.put("isEnd", "Y");
}

//System.out.println("asdfasdfasdfasdf 엑셀 출력에서 isEnd = "+isEnd);

if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬

//둘째줄-----------------------------------------------------------------------------------
if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급

//셋째줄-----------------------------------------------------------------------------------
//if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태

//넷째줄-----------------------------------------------------------------------------------
if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회

//다섯째줄---------------------------------------------------------------------------------
if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

//여섯째줄---------------------------------------------------------------------------------
if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분



//System.out.println("asdfasdfasdf into excel = "+request.getParameter("auth_start"	));
//System.out.println("asdfasdfasdf into excel = "+request.getParameter("auth_end"		));
//System.out.println("asdfasdfasdf into excel = "+isEnd);

		//검색
		List<Map> memberlist=dao.getMemberSearchList(map,true);
		
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
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : sendDMForm		DM 발송 화면 폼 
	 */
	public ActionForward sendDMForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		memberSearchDao dao=new memberSearchDao();
		
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

			if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));		//이름

			String isEnd = "";
			if(request.getParameter("isEnd")!=null) isEnd = request.getParameter("isEnd");
			
			if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며, 유효자를 검색하기 위해서 아래를 내보낸다.
				if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
				if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일
			}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
				if(request.getParameter("auth_start")!=null) map.put("e_auth_start"	, request.getParameter("auth_start"		));
				if(request.getParameter("auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("auth_end"		));
				map.put("isEnd", "Y");
			}
			
			
			if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
			if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
			if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
			if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
			if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
			if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
			if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
			if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
			if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
			if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
			if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
			if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
			if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
			if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태
			

			//여섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분

			
			//전체 개수 검색
			List<Map> count = dao.getSearchDMForCnt(map);
			String t = count.get(0).get("ncount")+"";
			int ncount = Integer.parseInt(t);

			map.clear();
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param = "";

			
			//이름 넘김
			if(request.getParameter("pers_name"	)!=null)request.setAttribute("pers_name",  URLDecoder.decode(request.getParameter("pers_name"),"UTF-8") );

			if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며+ 유효자를 검색하기 위해서 아래를 내보낸다.
				if(request.getParameter("auth_start")!=null) param+="&auth_start="	+ request.getParameter("auth_start"		); //회원유효기간 만료일
				if(request.getParameter("auth_end"	)!=null) param+="&auth_end="	+ request.getParameter("auth_end"		);//회원유효기간 만료일
			}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
				if(request.getParameter("auth_start")!=null) param+="&e_auth_start="+ request.getParameter("auth_start"		);
				if(request.getParameter("auth_end"	)!=null) param+="&e_auth_end="	+ request.getParameter("auth_end"		);
				param+="&isEnd=Y";
			}
			
			
			if(request.getParameter("code_bran"		)!=null)param+="&code_bran="	+ request.getParameter("code_bran"		);	//지부
			if(request.getParameter("code_place"	)!=null)param+="&code_place="	+ request.getParameter("code_place"		);	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)param+="&job_line_code="+ request.getParameter("job_line_code"	);	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)param+="&lic_no="		+ request.getParameter("lic_no");	//면허번호
			if(request.getParameter("conform_dt_start")!=null)param+="&conform_dt_start="	+request.getParameter("conform_dt_start");//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)param+="&conform_dt_end="		+request.getParameter("conform_dt_end"	);//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)param+="&code_big="		+ request.getParameter("code_big"		);	//분과명
			if(request.getParameter("code_call"		)!=null)param+="&code_call="	+ request.getParameter("code_call"		);	//발송호칭
			if(request.getParameter("job_level_code")!=null)param+="&job_level_code="+ request.getParameter("job_level_code"	);	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)param+="&pers_no="		+ request.getParameter("pers_no"		);	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth")		!=  null)	param+="&pers_birth="	+request.getParameter("pers_birth");	//생년월일
			if(request.getParameter("code_receipt"	)!=null)param+="&code_receipt="	+ request.getParameter("code_receipt"	);	//인증장소
			if(request.getParameter("code_sect"		)!=null)param+="&code_sect="	+ request.getParameter("code_sect"		);	//분회명
			if(request.getParameter("code_scholar"	)!=null)param+="&code_scholar="	+ request.getParameter("code_scholar"	);	//최종학력
			if(request.getParameter("code_use"		)!=null)param+="&code_use="		+ request.getParameter("code_use"		);	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)param+="&code_member="	+ request.getParameter("code_member"	);	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)param+="&code_pay_flag="+ request.getParameter("code_pay_flag"	);	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)param+="&code_small="	+ request.getParameter("code_small"		);	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)param+="&code_univer="	+ request.getParameter("code_univer"	);	//학위
			if(request.getParameter("code_sub"		)!=null)param+="&code_sub="		+ request.getParameter("code_sub"		);	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)param+="&kda_level="	+ request.getParameter("kda_level"		);	//협회직급
			if(request.getParameter("code_certifi"	)!=null)param+="&code_certifi="	+ request.getParameter("code_certifi"	);	//자격증종류
			if(request.getParameter("print_state"	)!=null)param+="&print_state="	+ request.getParameter("print_state"	);	//자격증말소자
			if(request.getParameter("id"			)!=null)param+="&id="			+ request.getParameter("id"				);	//아이디
			if(request.getParameter("pers_state"	)!=null)param+="&pers_state="	+ request.getParameter("pers_state"		);	//회원상태
			
			//여섯째줄---------------------------------------------
			if(request.getParameter("dues_gubun")		!=	null)	param+="&dues_gubun="	+request.getParameter("dues_gubun");	//회비구분
			
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
			

		}
		
		request.setAttribute("from", "memberSearch" );	//조건검색인지, 회비처리인지 구분
		return (mapping.findForward("sendDMForm"));
	}


	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : sendDMLink		DM 발송 실행 링크 
	 */
	public ActionForward sendDMLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		List<Map> count = null; //오류났을때 10개 이상인가 체크용
		List<Map> list= null;
		
		Map map = new HashMap();
		memberSearchDao dao=new memberSearchDao();
		
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
			count = dao.getSearchDmForSeq(map);
			String dm_print_yymm_seq = count.get(0).get("dm_print_yymm_seq")+"";
			//for 문 돌면서 저장한다.
			for( int i=0; i<code_pers.length; i++) {

				map.clear();
				
				if(code_pers.length>0) {
					//현재 seq 값을 알아와
					map.put("code_book_kind", "3");	//dm 종류
					map.put("dm_pers_code"	, code_pers[i]);//회원 번호
					
						//대상을 조회하고
						map.put("code_pers"			, code_pers[i]);
						map.put("code_pers_dm"			, code_pers[i]);
						list=dao.getSearchDMForList(map,true);

						if(list.size() > 0&&list.get(0).get("code_post"	) != null&&list.get(0).get("code_post"	).toString().length()==6&&(!list.get(0).get("code_post"	).toString().equals("000000"))) {
							map.put("dm_print_yymm_seq"	, dm_print_yymm_seq);
							map.put("rece_yn"			, "2");			//수신여부
							map.put("dm_creater"		, register);	//DM생성자
							map.put("dm_name"			, "조건검색");//DM명
							
							if(list.get(0).get("code_member") != null) map.put("code_member"	, list.get(0).get("code_member"	));
							if(list.get(0).get("pers_name"	) != null) map.put("pers_name"		, list.get(0).get("pers_name"	));
							if(list.get(0).get("lic_no"		) != null) map.put("lic_no"			, list.get(0).get("lic_no"		));
							if(list.get(0).get("code_post"	) != null) map.put("code_post"		, list.get(0).get("code_post"	));
							if(list.get(0).get("pers_add"	) != null) map.put("pers_add"		, list.get(0).get("pers_add"	));
							if(list.get(0).get("pers_hp"	) != null) map.put("pers_hp"		, list.get(0).get("pers_hp"		));
							if(list.get(0).get("email"		) != null) map.put("email"			, list.get(0).get("email"		));
							if(list.get(0).get("code_bran"	) != null) map.put("code_bran"		, list.get(0).get("code_bran"	));
							if(list.get(0).get("oper_comp_name1") != null) map.put("oper_comp_name1", list.get(0).get("oper_comp_name1"));
							if(list.get(0).get("print_call"	) != null) map.put("print_call"		, list.get(0).get("print_call"));
							if(list.get(0).get("code_place"	) != null) map.put("code_place"		, list.get(0).get("code_place"	));
							
							//셋 한다.
							try {
								dao.setSearchDMList(map);
								success_cnt++;
							} catch (Exception e) {
								faile_cnt++;
							}
						}
				}
			}
		} else if("ALL".equals(chk)) {
 
			// 변수에 값 받아서 넣음

			if(request.getParameter("pers_name")!=null) map.put("pers_name"		, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8")); //이름

			if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
			if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일

			if(request.getParameter("e_auth_start"	)!=null) map.put("e_auth_start"	, request.getParameter("e_auth_start"		));//만료자 처리
			if(request.getParameter("e_auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("e_auth_end"		));//앞단에서 넣었으니까 여기서는 그냥 받기만한다.
			if(request.getParameter("isEnd"	)!=null) map.put("isEnd"	, "Y");//만료자 처리
			
			if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
			if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
			if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
			if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
			if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			JUMIN_DEL
//			if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호
			if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
			if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
			if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
			if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
			if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
			if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
			if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
			if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
			if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
			if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

			//여섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분


			//대상 검색
			list=dao.getSearchDMForList(map,true);

			tot_cnt = list.size();
			System.out.println("tot_cnt=======>"+tot_cnt);
			map.put("code_book_kind", "3");	//dm 종류
			count = dao.getSearchDmForSeq(map);
			String dm_print_yymm_seq = count.get(0).get("dm_print_yymm_seq")+"";

			//여기서 포문 돌면서 인서트 한다.
			for( int i=0; i<list.size(); i++) {

					map.clear();
				
				//현재 seq 값을 알아와
					if(list.get(i).get("code_post"	) != null&&list.get(i).get("code_post"	).toString().length()==6&&(!list.get(i).get("code_post"	).toString().equals("000000"))) {
						map.put("dm_pers_code"		, list.get(i).get("code_pers"));//회원 번호
						map.put("dm_print_yymm_seq"	, dm_print_yymm_seq);
						map.put("code_book_kind"	, "3");
						map.put("rece_yn"			, "2");
						map.put("dm_creater"		, register);
						map.put("dm_name"			, "조건검색");//DM명
	
						
						if(list.get(i).get("code_member") != null) map.put("code_member"	, list.get(i).get("code_member"));
						if(list.get(i).get("pers_name"	) != null) map.put("pers_name"		, list.get(i).get("pers_name"));
						if(list.get(i).get("lic_no"		) != null) map.put("lic_no"			, list.get(i).get("lic_no"));
						if(list.get(i).get("code_post"	) != null) map.put("code_post"		, list.get(i).get("code_post"));
						if(list.get(i).get("pers_add"	) != null) map.put("pers_add"		, list.get(i).get("pers_add"));
						if(list.get(i).get("pers_hp"	) != null) map.put("pers_hp"		, list.get(i).get("pers_hp"));
						if(list.get(i).get("email"		) != null) map.put("email"			, list.get(i).get("email"));
						if(list.get(i).get("code_bran"	) != null) map.put("code_bran"		, list.get(i).get("code_bran"));
						if(list.get(i).get("oper_comp_name1"	) != null) map.put("oper_comp_name1"		, list.get(i).get("oper_comp_name1"	));
						if(list.get(i).get("print_call"	) != null) map.put("print_call"		, list.get(i).get("print_call"));
						if(list.get(i).get("code_pers"	) != null) map.put("code_place"		, list.get(i).get("code_place"));
						if(list.get(i).get("code_place"	) !=null) map.put("code_place"	, list.get(i).get("code_place"		));	//발송지(수신처)
/*
System.out.println(list.get(i).get("code_member"));
System.out.println(list.get(i).get("pers_name"));
System.out.println(list.get(i).get("lic_no"));
System.out.println(list.get(i).get("code_post"));
System.out.println(list.get(i).get("pers_add"));
System.out.println(list.get(i).get("pers_hp"));
System.out.println(list.get(i).get("email"));
System.out.println(list.get(i).get("code_bran"));
System.out.println(list.get(i).get("print_call"));
System.out.println(list.get(i).get("code_place"));
*/
					//셋 한다.
						try {
							dao.setSearchDMList(map);
							success_cnt++;
	
						} catch (Exception e) {
							e.printStackTrace();
							faile_cnt++;
						}
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
	 * 작업일 : 2012.11.27
	 * 작  업 : sendMsgForm		쪽지 발송 화면 폼 
	 */
	public ActionForward sendMsgForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		memberSearchDao dao=new memberSearchDao();
		
		String chk	=request.getParameter("chk");		//선택, 전체 여부

		//선택여부 등록자 넣음
		request.setAttribute("chk"		, request.getParameter("chk"		));	//선택, 전체 여부

		String register = request.getParameter("register"	);	//등록자
		request.setAttribute("register"	, URLDecoder.decode(register,"UTF-8"));	//등록자
		
		if("CHK".equals(chk)) {//선택 저장
			

			//전체 개수 구함
			String temp			= request.getParameter("code_pers"		);	//회원 번호
			String code_pers[]	= temp.split(",");

			
			request.setAttribute("ncount"	, code_pers.length);								//전체개수 넣음
			request.setAttribute("param"	,"&code_pers="+request.getParameter("code_pers"));	//회원번호 넣음
			request.setAttribute("code_pers", request.getParameter("code_pers"));				//회원번호만
			
		} else if("ALL".equals(chk)) {

			if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));		//이름
//System.out.println("asdfasdfasdfasdf 쪽찌 1 이름 = :"+URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));
			String isEnd = "";
			if(request.getParameter("isEnd")!=null) isEnd = request.getParameter("isEnd");
			
			if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며, 유효자를 검색하기 위해서 아래를 내보낸다.
				if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
				if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일
			}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
				if(request.getParameter("auth_start")!=null) map.put("e_auth_start"	, request.getParameter("auth_start"		));
				if(request.getParameter("auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("auth_end"		));
				map.put("isEnd", "Y");
			}
			
			if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
			if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
			if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
			if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
			if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
			if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
			if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
			if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
			if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
			if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
			if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
			if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
			if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
			if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

			//여섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분

			
			
			//전체 개수 검색
			List<Map> count = dao.getSearchMsgForCnt(map);
			String t = count.get(0).get("ncount")+"";
			int ncount = Integer.parseInt(t);

			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
			
			map.clear();
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param = "";

			
			//이름 넘김
			if(request.getParameter("pers_name"	)!=null)request.setAttribute("pers_name",  URLDecoder.decode(request.getParameter("pers_name"),"UTF-8") );

			if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며+ 유효자를 검색하기 위해서 아래를 내보낸다.
				if(request.getParameter("auth_start")!=null) param+="&auth_start="	+ request.getParameter("auth_start"		); //회원유효기간 만료일
				if(request.getParameter("auth_end"	)!=null) param+="&auth_end="	+ request.getParameter("auth_end"		);//회원유효기간 만료일
			}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
				if(request.getParameter("auth_start")!=null) param+="&e_auth_start="+ request.getParameter("auth_start"		);
				if(request.getParameter("auth_end"	)!=null) param+="&e_auth_end="	+ request.getParameter("auth_end"		);
				param+="&isEnd=Y";
			}
			
			
			if(request.getParameter("code_bran"		)!=null)param+="&code_bran="	+ request.getParameter("code_bran"		);	//지부
			if(request.getParameter("code_place"	)!=null)param+="&code_place="	+ request.getParameter("code_place"		);	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)param+="&job_line_code="+ request.getParameter("job_line_code"	);	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)param+="&lic_no="		+ request.getParameter("lic_no");	//면허번호
			if(request.getParameter("conform_dt_start")!=null)param+="&conform_dt_start="	+request.getParameter("conform_dt_start");//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)param+="&conform_dt_end="		+request.getParameter("conform_dt_end"	);//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)param+="&code_big="		+ request.getParameter("code_big"		);	//분과명
			if(request.getParameter("code_call"		)!=null)param+="&code_call="	+ request.getParameter("code_call"		);	//발송호칭
			if(request.getParameter("job_level_code")!=null)param+="&job_level_code="+ request.getParameter("job_level_code"	);	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)param+="&pers_no="		+ request.getParameter("pers_no"		);	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth")		!=  null)	param+="&pers_birth="	+request.getParameter("pers_birth");	//생년월일
			if(request.getParameter("code_receipt"	)!=null)param+="&code_receipt="	+ request.getParameter("code_receipt"	);	//인증장소
			if(request.getParameter("code_sect"		)!=null)param+="&code_sect="	+ request.getParameter("code_sect"		);	//분회명
			if(request.getParameter("code_scholar"	)!=null)param+="&code_scholar="	+ request.getParameter("code_scholar"	);	//최종학력
			if(request.getParameter("code_use"		)!=null)param+="&code_use="		+ request.getParameter("code_use"		);	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)param+="&code_member="	+ request.getParameter("code_member"	);	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)param+="&code_pay_flag="+ request.getParameter("code_pay_flag"	);	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)param+="&code_small="	+ request.getParameter("code_small"		);	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)param+="&code_univer="	+ request.getParameter("code_univer"	);	//학위
			if(request.getParameter("code_sub"		)!=null)param+="&code_sub="		+ request.getParameter("code_sub"		);	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)param+="&kda_level="	+ request.getParameter("kda_level"		);	//협회직급
			if(request.getParameter("code_certifi"	)!=null)param+="&code_certifi="	+ request.getParameter("code_certifi"	);	//자격증종류
			if(request.getParameter("print_state"	)!=null)param+="&print_state="	+ request.getParameter("print_state"	);	//자격증말소자
			if(request.getParameter("id"			)!=null)param+="&id="			+ request.getParameter("id"				);	//아이디
			if(request.getParameter("pers_state"	)!=null)param+="&pers_state="	+ request.getParameter("pers_state"		);	//회원상태
			
			//여섯째줄---------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)param+="&dues_gubun="	+ request.getParameter("dues_gubun"		);	//회비구분
			
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			

		}
		
		request.setAttribute("from", "memberSearch" );	//조건검색인지, 회비처리인지 구분
		return (mapping.findForward("sendMsgForm"));
	}


	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : sendMsgLink		쪽지 발송 실행 링크 
	 */
	public ActionForward sendMsgLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		List<Map> count = null; //오류났을때 10개 이상인가 체크용
		List<Map> list= null;
		
		Map map = new HashMap();
		memberSearchDao dao=new memberSearchDao();
		
		//오류 출력 여부는 우선 제외한다.
	//	String error_code_pers = "";
		int tot_cnt = 0;
		int success_cnt = 0;
		int faile_cnt = 0;
		
		String chk			=request.getParameter("chk");		//선택, 전체 여부
		String register		=request.getParameter("register");	//유저ID//생성자
		register	=URLDecoder.decode(register,"UTF-8");

		String note_title	= URLDecoder.decode(request.getParameter("note_title"),"UTF-8");
		String note_contents= URLDecoder.decode(request.getParameter("note_contents"),"UTF-8");
		String st_dt		= request.getParameter("st_dt");
		String ed_dt		= request.getParameter("ed_dt");
		String note_oper	= URLDecoder.decode(request.getParameter("note_oper"),"UTF-8");

		/*System.out.println("chk = "+chk);
		System.out.println("register = "+register);
		System.out.println("note_title = "+note_title);
		System.out.println("note_contents = "+note_contents);
		System.out.println("st_dt = "+st_dt);
		System.out.println("ed_dt = "+ed_dt);
		System.out.println("note_oper = "+note_oper);*/
		
		int outcount = 0;	//화면으로 뿌리는 개수

		if("CHK".equals(chk)) {//선택 저장
			
			String temp			= request.getParameter("code_pers"		);	//회원 번호
			String code_pers[]	= temp.split(",");
			
			tot_cnt = code_pers.length;
			//for 문 돌면서 저장한다.
			for( int i=0; i<code_pers.length; i++) {

				map.clear();
				
				if(code_pers.length>0) {
					
					//현재 seq 값을 알아와
					map.put("code_pers"	, code_pers[i]);//회원 번호
					
					count = dao.getSearchSmsSeq(map);
					String date_seq = count.get(0).get("date_seq")+"";

					//발송개수가 10개 이하라면 발송한다.
					if( !"00".equals(date_seq) ) {//100개까지 들어간다 100이되면 00 이 되므로 00만 피하면 된다.
	
						//아이디만 있으면 되므로 대상 조회가 필요 없다.아이디도 위에서 넣었다.
						map.put("date_seq"		, date_seq );
						map.put("note_title"	, note_title);
						map.put("note_contents"	, note_contents);
						map.put("st_dt"			, st_dt);
						map.put("ed_dt"			, ed_dt);
						map.put("note_oper"		, note_oper);
						map.put("register"		, register);

						//셋 한다.
						try {
							dao.setSearchSmsList(map);
							success_cnt++;
						} catch (Exception e) {
							faile_cnt++;
						}
					}
				}
			}
		} else if("ALL".equals(chk)) {
 
			// 변수에 값 받아서 넣음

			if(request.getParameter("pers_name")!=null) map.put("pers_name"		, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8")); //이름
//System.out.println("asdfasdfasdfasdf 쪽찌 이름 = :"+URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));
			if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
			if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일

			if(request.getParameter("e_auth_start"	)!=null) map.put("e_auth_start"	, request.getParameter("e_auth_start"		));//만료자 처리
			if(request.getParameter("e_auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("e_auth_end"		));//앞단에서 넣었으니까 여기서는 그냥 받기만한다.
			if(request.getParameter("isEnd"	)!=null) map.put("isEnd"	, "Y");
			
			if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
			if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
			if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
			if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
			if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
			if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
			if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
			if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
			if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
			if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
			if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
			if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
			if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
			if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

			//여섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분


			//대상 검색
			list=dao.getSearchMsgList(map);

			tot_cnt = list.size();
			//여기서 포문 돌면서 인서트 한다.
			for( int i=0; i<list.size(); i++) {

				map.clear();
				

				//현재 seq 값을 알아와
				map.put("code_pers"	, list.get(i).get("code_pers"));
				System.out.println("list.get(i).get(="+list.get(i).get("code_pers"));
				
				count = dao.getSearchSmsSeq(map);
				String date_seq = count.get(0).get("date_seq")+"";

				//발송개수가 10개 이하라면 발송한다.
				if( !"00".equals(date_seq) ) {//100개까지 들어간다 100이되면 00 이 되므로 00만 피하면 된다.

					//아이디만 있으면 되므로 대상 조회가 필요 없다.아이디도 위에서 넣었다.
					map.put("date_seq"		, date_seq );
					map.put("note_title"	, note_title);
					map.put("note_contents"	, note_contents);
					map.put("st_dt"			, st_dt);
					map.put("ed_dt"			, ed_dt);
					map.put("note_oper"		, note_oper);
					map.put("register"		, register);

					//셋 한다.
					try {
						dao.setSearchSmsList(map);
						success_cnt++;
					} catch (Exception e) {
						faile_cnt++;
					}
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
		memberSearchDao dao=new memberSearchDao();
		
		String chk	=request.getParameter("chk");		//선택, 전체 여부

		//선택여부 등록자 넣음
		request.setAttribute("chk"		, request.getParameter("chk"		));	//선택, 전체 여부
		String register = request.getParameter("register"	);	//등록자
		request.setAttribute("register"	, register);	//등록자
		
		if("CHK".equals(chk)) {//선택 저장
			

			//전체 개수 구함
			String temp			= request.getParameter("pers_hp"		);	//전화 번호
			String pers_hp[]	= temp.split(",");

			
			request.setAttribute("ncount"	, pers_hp.length);								//전체개수 넣음
			request.setAttribute("param"	,"&pers_hp="+request.getParameter("pers_hp"));	//전화 번호 넣음
			request.setAttribute("pers_hp"	, request.getParameter("pers_hp"));				//전화 번호만
			request.setAttribute("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));				//전화 번호만
			request.setAttribute("code_pers"	, request.getParameter("code_pers"));
			
		} else if("ALL".equals(chk)) {

			if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));		//이름

			String isEnd = "";
			if(request.getParameter("isEnd")!=null) isEnd = request.getParameter("isEnd");
			
			if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며, 유효자를 검색하기 위해서 아래를 내보낸다.
				if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
				if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일
			}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
				if(request.getParameter("auth_start")!=null) map.put("e_auth_start"	, request.getParameter("auth_start"		));
				if(request.getParameter("auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("auth_end"		));
				map.put("isEnd", "Y");
			}
			
			if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
			if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
			if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
			if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
			if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
			if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
			if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
			if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
			if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
			if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
			if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
			if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
			if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
			if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

			//여섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분

			
			
			//전체 개수 검색
			List<Map> count = dao.getSearchUmsForCnt(map);
			String t = count.get(0).get("ncount")+"";
			int ncount = Integer.parseInt(t);

			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
			
			map.clear();
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			String param = "";

			
			//이름 넘김
			if(request.getParameter("pers_name"	)!=null)request.setAttribute("pers_name",  URLDecoder.decode(request.getParameter("pers_name"),"UTF-8") );

			if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며+ 유효자를 검색하기 위해서 아래를 내보낸다.
				if(request.getParameter("auth_start")!=null) param+="&auth_start="	+ request.getParameter("auth_start"		); //회원유효기간 만료일
				if(request.getParameter("auth_end"	)!=null) param+="&auth_end="	+ request.getParameter("auth_end"		);//회원유효기간 만료일
			}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
				if(request.getParameter("auth_start")!=null) param+="&e_auth_start="+ request.getParameter("auth_start"		);
				if(request.getParameter("auth_end"	)!=null) param+="&e_auth_end="	+ request.getParameter("auth_end"		);
				param+="&isEnd=Y";
			}
			
			
			if(request.getParameter("code_bran"		)!=null)param+="&code_bran="	+ request.getParameter("code_bran"		);	//지부
			if(request.getParameter("code_place"	)!=null)param+="&code_place="	+ request.getParameter("code_place"		);	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)param+="&job_line_code="+ request.getParameter("job_line_code"	);	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)param+="&lic_no="		+ request.getParameter("lic_no");	//면허번호
			if(request.getParameter("conform_dt_start")!=null)param+="&conform_dt_start="	+request.getParameter("conform_dt_start");//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)param+="&conform_dt_end="		+request.getParameter("conform_dt_end"	);//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)param+="&code_big="		+ request.getParameter("code_big"		);	//분과명
			if(request.getParameter("code_call"		)!=null)param+="&code_call="	+ request.getParameter("code_call"		);	//발송호칭
			if(request.getParameter("job_level_code")!=null)param+="&job_level_code="+ request.getParameter("job_level_code"	);	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)param+="&pers_no="		+ request.getParameter("pers_no"		);	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth")		!=  null)	param+="&pers_birth="	+request.getParameter("pers_birth");	//생년월일
			if(request.getParameter("code_receipt"	)!=null)param+="&code_receipt="	+ request.getParameter("code_receipt"	);	//인증장소
			if(request.getParameter("code_sect"		)!=null)param+="&code_sect="	+ request.getParameter("code_sect"		);	//분회명
			if(request.getParameter("code_scholar"	)!=null)param+="&code_scholar="	+ request.getParameter("code_scholar"	);	//최종학력
			if(request.getParameter("code_use"		)!=null)param+="&code_use="		+ request.getParameter("code_use"		);	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)param+="&code_member="	+ request.getParameter("code_member"	);	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)param+="&code_pay_flag="+ request.getParameter("code_pay_flag"	);	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)param+="&code_small="	+ request.getParameter("code_small"		);	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)param+="&code_univer="	+ request.getParameter("code_univer"	);	//학위
			if(request.getParameter("code_sub"		)!=null)param+="&code_sub="		+ request.getParameter("code_sub"		);	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)param+="&kda_level="	+ request.getParameter("kda_level"		);	//협회직급
			if(request.getParameter("code_certifi"	)!=null)param+="&code_certifi="	+ request.getParameter("code_certifi"	);	//자격증종류
			if(request.getParameter("print_state"	)!=null)param+="&print_state="	+ request.getParameter("print_state"	);	//자격증말소자
			if(request.getParameter("id"			)!=null)param+="&id="			+ request.getParameter("id"				);	//아이디
			if(request.getParameter("pers_state"	)!=null)param+="&pers_state="	+ request.getParameter("pers_state"		);	//회원상태
			
			//여섯째줄---------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)param+="&dues_gubun="	+ request.getParameter("dues_gubun"		);	//회비구분
			
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			

		}
		
		request.setAttribute("from", "memberSearch" );	//조건검색인지, 회비처리인지 구분
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
		memberSearchDao dao=new memberSearchDao();
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
			String temp1			= URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");	//이름
			String pers_name[]	= temp1.split(",");
			String temp2			= StringUtil.NVL(request.getParameter("code_pers"));
			String code_pers[]	= temp2.split(",");
			tot_cnt = pers_hp.length;
			//for 문 돌면서 저장한다.
			for( int i=0; i<pers_hp.length; i++) {

				map.clear();
				
				if(!"".equals(temp2)){
					map.put("code_pers"		, code_pers[i] );
					list = null;
					list=dao.getSearchUmsList(map,true);
					if(list!=null && list.size()>0) pers_hp[i] = (String) list.get(0).get("pers_hp");
				}
				
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
							dao.setSearchUmsResultList(map);
						}else {
							dao.setSearchUmsList(map);
						}
						success_cnt++;
					} catch (Exception e) {						
						faile_cnt++;
					}
				}
			}
		} else if("ALL".equals(chk)) {
 
			// 변수에 값 받아서 넣음

			if(request.getParameter("pers_name")!=null) map.put("pers_name"		, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8")); //이름

			if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
			if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일

			if(request.getParameter("e_auth_start"	)!=null) map.put("e_auth_start"	, request.getParameter("e_auth_start"		));//만료자 처리
			if(request.getParameter("e_auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("e_auth_end"		));//앞단에서 넣었으니까 여기서는 그냥 받기만한다.
			if(request.getParameter("isEnd"	)!=null) map.put("isEnd"	, "Y");
			
			if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
			if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
			if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬
			
			//둘째줄-----------------------------------------------------------------------------------
			if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
			if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
			if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
			if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
			if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
			if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급
			
			//셋째줄-----------------------------------------------------------------------------------
//			if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
			if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
			if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
			if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
			if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
			if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태
			
			//넷째줄-----------------------------------------------------------------------------------
			if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
			if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
			if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
			if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
			if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회
			
			//다섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
			if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
			if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
			if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
			if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

			//여섯째줄---------------------------------------------------------------------------------
			if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분


			//대상 검색
			list=dao.getSearchUmsList(map,true);

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
	    		map.put("etc3",         list.get(i).get("pers_name"));
				//2013.01.16추가
	    		map.put("umscnt"		, i+1);
				//셋 한다.
				try {
					if(request_time.length() > 0 ) {
						map.put("request_time", request_time);
						dao.setSearchUmsResultList(map);
					}else {
						dao.setSearchUmsList(map);
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
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.10
	 * 작   업 : memberSearch		회원 조건검색 실 조회 화면으로 포워딩
	 */
	public ActionForward memberSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//메뉴 위치 보낸다.
		request.setAttribute("menu", "memberSearchList");

		//공통쿼리 제이슨으로 보낸다.
/*		List<HashMap<String, String>> comList	= CommonCode.getInstance().getCodeList();
		JSONArray jsoncode	= JSONArray.fromObject(comList);
		request.setAttribute("comList",jsoncode);*/
		
		return (mapping.findForward("memberSearchList"));
	}

	/*
	 * 작업명 : Home>회원>조건검색
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.10
	 * 작   업 : memberSearchList	회원 조건검색 리스트 조회
	 */
	public ActionForward memberSearchList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
				Map map = new HashMap();
				memberSearchDao dao=new memberSearchDao();
				
				
				
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
//if(request.getParameter("check"			)!=null) map.put("check"		, request.getParameter("check"));	//주민번호 유형 // JUMIN_DEL

if(request.getParameter("pers_name"		)!=null) map.put("pers_name"	, URLDecoder.decode(request.getParameter("pers_name"),"UTF-8"));		//이름

String isEnd = "";
if(request.getParameter("isEnd")!=null) isEnd = request.getParameter("isEnd");;

if( "0".equals(isEnd)){ //만료자 체크 들어오지 않았으며, 유효자를 검색하기 위해서 아래를 내보낸다.
	if(request.getParameter("auth_start")!=null) map.put("auth_start"	, request.getParameter("auth_start"		)); //회원유효기간 만료일
	if(request.getParameter("auth_end"	)!=null) map.put("auth_end"		, request.getParameter("auth_end"		));//회원유효기간 만료일
}else if( "1".equals(isEnd) ){ //만료자 체크 들어왔으면 만료자 검색을 위해서 아래로 넣는다.
	if(request.getParameter("auth_start")!=null) map.put("e_auth_start"	, request.getParameter("auth_start"		));
	if(request.getParameter("auth_end"	)!=null) map.put("e_auth_end"	, request.getParameter("auth_end"		));
	map.put("isEnd", "Y");
	System.out.println("요기!");
}


//System.out.println("asdfasdfasdfasdf isEnd = "+isEnd);

if(request.getParameter("code_bran"		)!=null)map.put("code_bran"		, request.getParameter("code_bran"		));	//지부
if(request.getParameter("code_place"	)!=null)map.put("code_place"	, request.getParameter("code_place"		));	//발송지(수신처)
if(request.getParameter("job_line_code"	)!=null)map.put("job_line_code"	, request.getParameter("job_line_code"	));	//직렬

//둘째줄-----------------------------------------------------------------------------------
if(request.getParameter("lic_no"		)!=null)map.put("lic_no"		, request.getParameter("lic_no"));	//면허번호
if(request.getParameter("conform_dt_start")!=null)map.put("conform_dt_start"	,request.getParameter("conform_dt_start"));//인증기간은 확인일자가 시작일과
if(request.getParameter("conform_dt_end")!=null)map.put("conform_dt_end"		,request.getParameter("conform_dt_end"	));//종료일 안에 있으면 된다.
if(request.getParameter("code_big"		)!=null)map.put("code_big"		, request.getParameter("code_big"		));	//분과명
if(request.getParameter("code_call"		)!=null)map.put("code_call"		, request.getParameter("code_call"		));	//발송호칭
if(request.getParameter("job_level_code")!=null)map.put("job_level_code", request.getParameter("job_level_code"	));	//직급

//셋째줄-----------------------------------------------------------------------------------
//if(request.getParameter("pers_no"		)!=null)map.put("pers_no"		, request.getParameter("pers_no"		));	//주민등록번호 // JUMIN_DEL
if(request.getParameter("pers_birth"	)!=null)map.put("pers_birth"	, request.getParameter("pers_birth"	));		//생년월일
if(request.getParameter("code_receipt"	)!=null)map.put("code_receipt"	, request.getParameter("code_receipt"	));	//인증장소
if(request.getParameter("code_sect"		)!=null)map.put("code_sect"		, request.getParameter("code_sect"		));	//분회명
if(request.getParameter("code_scholar"	)!=null)map.put("code_scholar"	, request.getParameter("code_scholar"	));	//최종학력
if(request.getParameter("code_use"		)!=null)map.put("code_use"		, request.getParameter("code_use"		));	//운영형태

//넷째줄-----------------------------------------------------------------------------------
if(request.getParameter("code_member"	)!=null)map.put("code_member"	, request.getParameter("code_member"	));	//회원종류
if(request.getParameter("code_pay_flag"	)!=null)map.put("code_pay_flag"	, request.getParameter("code_pay_flag"	));	//입금방법	code_inout_gubun가 1인것 중.
if(request.getParameter("code_small"	)!=null)map.put("code_small"	, request.getParameter("code_small"		));	//근무처소분류
if(request.getParameter("code_univer"	)!=null)map.put("code_univer"	, request.getParameter("code_univer"	));	//학위
if(request.getParameter("code_sub"		)!=null)map.put("code_sub"		, request.getParameter("code_sub"		));	//산하단체및 분야회

//다섯째줄---------------------------------------------------------------------------------
if(request.getParameter("kda_level"		)!=null)map.put("kda_level"		, request.getParameter("kda_level"		));	//협회직급
if(request.getParameter("code_certifi"	)!=null)map.put("code_certifi"	, request.getParameter("code_certifi"	));	//자격증종류
if(request.getParameter("print_state"	)!=null)map.put("print_state"	, request.getParameter("print_state"	));	//자격증말소자
if(request.getParameter("id"			)!=null)map.put("id"			, request.getParameter("id"				));	//아이디
if(request.getParameter("pers_state"	)!=null)map.put("pers_state"	, request.getParameter("pers_state"		));	//회원상태

//여섯째줄---------------------------------------------------------------------------------
if(request.getParameter("dues_gubun"	)!=null)map.put("dues_gubun"	, request.getParameter("dues_gubun"		));	//회비구분



					//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.---**조회
					List<Map> count = dao.getMemberSearchCount(map);
					int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();
	
					int ntotpage = (ncount/nrows)+1;



//System.out.println("asdfasdfasdf into search = "+request.getParameter("auth_start"	));
//System.out.println("asdfasdfasdf into search = "+request.getParameter("auth_end"		));
//System.out.println("asdfasdfasdf into search = "+isEnd);
					
					//화면에 출력할 값을 검색한다.-------------------------------**조회
					List<Map> list=dao.getMemberSearchList(map);
					
					//jsp에 넘겨줄 result값을 만든다.
					StringBuffer result = new StringBuffer();
				
					result.append("{\"page\":\""	+npage		+"\",");		
					result.append("\"total\":\"" 	+ntotpage	+"\",");
					result.append("\"records\":\""	+ncount		+"\",");
					result.append("\"rows\":[");
	
					//회원 유효기간
					String auth = "";
					String auth_start = "";
					String auth_end = "";
					
					//인증일
					String conform = "";
					String conform_dt = "";
					
					String out = "";
					
					//서브 셀렉트 검사
					
					for(int i=0;i<list.size();i++){

						if(i>0) result.append(",");
						result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
						result.append("\""+(list.get(i).get("ncount"	)== null ? "" : list.get(i).get("ncount"	))+"\",");//번호
						
result.append("\""+(list.get(i).get("pers_name"	)== null ? "" : list.get(i).get("pers_name"	))+"\",");//이름
result.append("\""+(list.get(i).get("lic_no"	)== null ? "" : list.get(i).get("lic_no"	))+"\",");//면허번호
//result.append("\""+(list.get(i).get("pers_no"	)== null ? "" : list.get(i).get("pers_no"	))+"\",");//주민등록번호 // JUMIN_DEL
result.append("\""+(list.get(i).get("pers_birth")== null ? "" : list.get(i).get("pers_birth"))+"\",");//생년월일
result.append("\""+(list.get(i).get("han_code_bran"		)== null ? "" : list.get(i).get("han_code_bran"		))+"\",");//지부명//한글화
result.append("\""+(list.get(i).get("han_code_big"		)== null ? "" : list.get(i).get("han_code_big"		))+"\",");//분과명//한글화
result.append("\""+(list.get(i).get("han_code_sect"		)== null ? "" : list.get(i).get("han_code_sect"		))+"\",");//분회명//한글화
result.append("\""+(list.get(i).get("han_code_member"	)== null ? "" : list.get(i).get("han_code_member"	))+"\",");//회원종류//한글화
result.append("\""+(list.get(i).get("han_pers_state"	)== null ? "" : list.get(i).get("han_pers_state"	))+"\",");//회원상태//한글화

result.append("\""+(list.get(i).get("auth_start"	)== null ? "" : list.get(i).get("auth_start"	))+"\",");//회원유효기간 시작
result.append("\""+(list.get(i).get("auth_end"		)== null ? "" : list.get(i).get("auth_end"		))+"\",");//회원유효기간 종료

result.append("\""+(list.get(i).get("conform_dt"		)== null ? "" : list.get(i).get("conform_dt"		))+"\",");//인증일
result.append("\""+(list.get(i).get("han_code_receipt"	)== null ? "" : list.get(i).get("han_code_receipt"	))+"\",");//인증장소//한글화
//result.append("\""+(list.get(i).get("pers_post"			)== null ? "" : list.get(i).get("pers_post"			))+"\",");//집주소 // del 1
result.append("\""+(list.get(i).get("pers_add"			)== null ? "" : list.get(i).get("pers_add"			))+"\",");//집주소
//result.append("\""+(list.get(i).get("pers_tel"			)== null ? "" : list.get(i).get("pers_tel"			))+"\",");//집전화번호 // del 2
result.append("\""+(list.get(i).get("pers_hp"			)== null ? "" : list.get(i).get("pers_hp"			))+"\",");//핸드폰
result.append("\""+(list.get(i).get("email"				)== null ? "" : list.get(i).get("email"				))+"\",");//이메일

//result.append("\""+(list.get(i).get("han_kda_level"	)== null ? "" : list.get(i).get("han_kda_level"	))+"\",");//협회직급//한글화 // del 3
result.append("\""+(list.get(i).get("han_code_great"	)== null ? "" : list.get(i).get("han_code_great"	))+"\",");//근무처대분류//한글화
result.append("\""+(list.get(i).get("han_code_small"	)== null ? "" : list.get(i).get("han_code_small"	))+"\",");//근무처소분류//한글화
result.append("\""+(list.get(i).get("company_name"	)== null ? "" : URLEncoder.encode(list.get(i).get("company_name").toString(), "UTF-8"))+"\",");//근무처명
result.append("\""+(list.get(i).get("company_tel"	)== null ? "" : list.get(i).get("company_tel"	))+"\",");//근무처전화번호
//result.append("\""+(list.get(i).get("company_post"		)== null ? "" : list.get(i).get("company_post"		))+"\",");//근무처주소 // del 4
result.append("\""+(list.get(i).get("company_add"		)== null ? "" : list.get(i).get("company_add"		))+"\",");//근무처주소
//result.append("\""+(list.get(i).get("han_job_line_code"	)== null ? "" : list.get(i).get("han_job_line_code"	))+"\",");//직렬//한글화 // del 5

//result.append("\""+(list.get(i).get("id"				)== null ? "" : list.get(i).get("id"				))+"\",");//아이디 // del 6

//result.append("\""+(list.get(i).get("han_code_place"	)== null ? "" : list.get(i).get("han_code_place"		))+"\",");//발송지 // del 7
//result.append("\""+(list.get(i).get("han_code_call"		)== null ? "" : list.get(i).get("han_code_call"			))+"\",");//발송호칭 // del 8
//result.append("\""+(list.get(i).get("han_job_level_code")== null ? "" : list.get(i).get("han_job_level_code"	))+"\",");//직급 // del 9
//result.append("\""+(list.get(i).get("han_code_scholar"	)== null ? "" : list.get(i).get("han_code_scholar"		))+"\",");//최종학력 // del 10
//result.append("\""+(list.get(i).get("han_code_use"		)== null ? "" : list.get(i).get("han_code_use"			))+"\",");//운영형태 // del 11
result.append("\""+(list.get(i).get("han_dues_gubun"	)== null ? "" : list.get(i).get("han_dues_gubun"		))+"\",");//회비구분
//result.append("\""+(list.get(i).get("han_code_pay_flag"	)== null ? "" : list.get(i).get("han_code_pay_flag"		))+"\",");//입금방법 // del 12
//result.append("\""+(list.get(i).get("han_code_univer"	)== null ? "" : list.get(i).get("han_code_univer"		))+"\","); //학위 // del 13
//result.append("\""+(list.get(i).get("han_code_sub"		)== null ? "" : list.get(i).get("han_code_sub"			))+"\","); //산하단체 // del 14
//result.append("\""+(list.get(i).get("han_code_certifi"	)== null ? "" : list.get(i).get("han_code_certifi"		))+"\",");//자격증종류 // del 15
//result.append("\""+(list.get(i).get("print_state"		)== null ? "" : list.get(i).get("print_state"			))+"\","); //자격증유효성 // del 16
result.append("\""+(list.get(i).get("code_pers"			)== null ? "" : list.get(i).get("code_pers"				))+"\","); //회원번호
result.append("\""+(list.get(i).get("memo_text"			)== null ? "" : list.get(i).get("memo_text"				))+"\","); //탈퇴사유

//dm 발송에 사용
result.append("\""+(list.get(i).get("code_member"	)== null ? "" : list.get(i).get("code_member"	))+"\","); //회원구분코드
result.append("\""+(list.get(i).get("code_post"		)== null ? "" : list.get(i).get("code_post"		))+"\","); //우편번호
result.append("\""+(list.get(i).get("pers_com_add"	)== null ? "" : list.get(i).get("pers_com_add"	))+"\","); //주소
result.append("\""+(list.get(i).get("code_bran"		)== null ? "" : list.get(i).get("code_bran"		))+"\","); //지부
result.append("\""+(list.get(i).get("code_call"		)== null ? "" : list.get(i).get("code_call"		))+"\","); //발송호칭
result.append("\""+(list.get(i).get("code_place"	)== null ? "" : list.get(i).get("code_place"	))+"\","); //수신처구분
result.append("\""+(list.get(i).get("rece_yn"		)== null ? "" : list.get(i).get("rece_yn"		))+"\"");  //수신여부


result.append("]}");
					}
					result.append("]}");

					request.setAttribute("result",result);
				}
				return (mapping.findForward("ajaxout"));
			}

}
