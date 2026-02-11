package com.ant.educationexam.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.common.util.CommonUtil;
import com.ant.common.util.StringUtil;
import com.ant.document.dao.documentDao;
import com.ant.educationexam.dao.educationDao;
import com.ant.member.basic.dao.basicDao;
import com.ant.member.dues.dao.memberDuesDao;
import com.ant.member.search.dao.memberSearchDao;
import com.ant.member.state.dao.memberStateDao;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class educationAction extends DispatchAction {

	protected static String fileDir="D:\\WEB\\KDA_VER3\\downExcel\\";
	
//	protected static String fileDir="D:\\KDA_WORKSPACE\\kda\\WebContent\\downExcel\\";
//	protected static String fileDir="D:\\KDA_PROJECT\\kda\\WebContent\\downExcel\\";
	
	
	


	/*
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.30
	 * 작  업 : sendDMForm		DM 발송 화면 폼 
	 */
	public ActionForward sendDMForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		educationDao dao=new educationDao();
		
		String chk	=request.getParameter("chk");		//선택, 전체 여부

		//선택여부 등록자 넣음
		request.setAttribute("chk"		, request.getParameter("chk"		));	//선택, 전체 여부
		String register = request.getParameter("register"	);	//등록자
		request.setAttribute("register"	, URLDecoder.decode(register,"UTF-8"));	//등록자
		
		if("CHK".equals(chk)) {//선택 저장


			//전체 개수 구함
			String temp			= request.getParameter("code_kind"		);	//전화번호
			String code_kind[]	= temp.split(",");
			
			//다시 넘겨줄 검색용 정보
			String param = "";
			param += "&code_kind="		+ request.getParameter("code_kind"		);
			param += "&code_certifi="	+ request.getParameter("code_certifi"	);
			param += "&code_seq="		+ request.getParameter("code_seq"		);
			param += "&code_bran="		+ request.getParameter("code_bran"		);
			param += "&bran_seq="		+ request.getParameter("bran_seq"		);
			param += "&receipt_no="		+ request.getParameter("receipt_no"		);
			//회원번호 넣음
			request.setAttribute("param"	,param);
			//전체개수 넣음
			request.setAttribute("ncount"	, code_kind.length);
			
		} else if("ALL".equals(chk)) {

			//개수 검색해서 넣음
			// 변수에 값 받아서 넣음
			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
//			if(request.getParameter("oper_no1"			)!=null)map.put("oper_no1"			, request.getParameter("oper_no1"		));	//주민번호 // JUMIN_DEL
			if(request.getParameter("oper_birth1"			)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
			if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
			if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
			if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
			if(request.getParameter("sel") != null)		map.put("sel",       	"Y");			
			
/*
System.out.println("sendDMForm 에서 카운터 검색용 변수");
if(request.getParameter("edutest_name1"		)!=null)System.out.println("edutest_name1	= "	+ URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
if(request.getParameter("oper_name1"		)!=null)System.out.println("oper_name1		= "	+ URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
if(request.getParameter("oper_comp_name11"	)!=null)System.out.println("oper_comp_name11= "	+ URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
if(request.getParameter("yyyy1"				)!=null)System.out.println("yyyy1			= "	+ request.getParameter("yyyy1"			));	//교육년도
if(request.getParameter("code_bran1"		)!=null)System.out.println("code_bran1		= "	+ request.getParameter("code_bran1"		));	//교육주최
if(request.getParameter("code_kind1"		)!=null)System.out.println("code_kind1		= "	+ request.getParameter("code_kind1"		));	//교육종류
if(request.getParameter("code_certifi1"		)!=null)System.out.println("code_certifi1	= "	+ request.getParameter("code_certifi1"	));	//자격증 구분
if(request.getParameter("code_certifi1"		)!=null)System.out.println("oper_no1		= "	+ request.getParameter("oper_no1"		));	//주민번호 // JUMIN_DEL
if(request.getParameter("oper_lic_no1"		)!=null)System.out.println("oper_lic_no1	= "	+ request.getParameter("oper_lic_no1"	));	//면허번호
if(request.getParameter("oper_hp1"			)!=null)System.out.println("oper_hp1		= "	+ request.getParameter("oper_hp1"		));	//핸드폰
if(request.getParameter("code_operation1"	)!=null)System.out.println("code_operation1	= "	+ request.getParameter("code_operation1"));	//대상자
if(request.getParameter("result_end_dt1"	)!=null)System.out.println("result_end_dt1	= "	+ request.getParameter("result_end_dt1"	));	//유효일자
if(request.getParameter("start_dt1"			)!=null)System.out.println("start_dt1		= "	+ request.getParameter("start_dt1"		));	//유예시작일
if(request.getParameter("end_dt1"			)!=null)System.out.println("end_dt1			= "	+ request.getParameter("end_dt1"		));	//유예마지막일
if(request.getParameter("oper_state1"		)!=null)System.out.println("oper_state1		= "	+ request.getParameter("oper_state1"	));	//진행상태
if(request.getParameter("code_pay_flag1"	)!=null)System.out.println("code_pay_flag1	= "	+ request.getParameter("code_pay_flag1"	));	//결제방법
if(request.getParameter("receipt_dt1"		)!=null)System.out.println("receipt_dt1		= "	+ request.getParameter("receipt_dt1"	));	//신청일자
if(request.getParameter("code_seq1"			)!=null)System.out.println("code_seq1		= "	+ request.getParameter("code_seq1"		));	//순번
if(request.getParameter("detcode1"			)!=null)System.out.println("detcode1		= "	+ request.getParameter("detcode1"		));	//승인여부
			
*/
			//전체 개수 검색
			List<Map> count = dao.getEduDMForCnt(map);
			String t = count.get(0).get("ncount")+"";
			int ncount = Integer.parseInt(t);

			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			if(request.getParameter("edutest_name1"		)!=null)request.setAttribute("edutest_name1"	,  URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8") );	//내용
			if(request.getParameter("oper_name1"		)!=null)request.setAttribute("oper_name1"		,  URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8") );	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)request.setAttribute("oper_comp_name11"	,  URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8") );	//근무처명
			
			String param = "";
			if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
			if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);	//교육주최
			if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
//			if(request.getParameter("oper_no1"			)!=null)param+="&oper_no1="			+ request.getParameter("oper_no1"		);	//주민번호 // JUMIN_DEL
			if(request.getParameter("oper_birth1"			)!=null)param+="&oper_birth1="			+ request.getParameter("oper_birth1"		);	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)param+="&oper_lic_no1="		+ request.getParameter("oper_lic_no1"	);	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)param+="&oper_hp1="			+ request.getParameter("oper_hp1"		);	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)param+="&result_end_dt1="	+ request.getParameter("result_end_dt1"	);	//유효일자
			if(request.getParameter("start_dt1"			)!=null)param+="&start_dt1="		+ request.getParameter("start_dt1"		);	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)param+="&end_dt1="			+ request.getParameter("end_dt1"		);	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)param+="&oper_state1="		+ request.getParameter("oper_state1"	);	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)param+="&code_pay_flag1="	+ request.getParameter("code_pay_flag1"	);	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)param+="&receipt_dt1="		+ request.getParameter("receipt_dt1"	);	//신청일자
			if(request.getParameter("code_seq1"			)!=null)param+="&code_seq1="		+ request.getParameter("code_seq1"		);	//순번
			if(request.getParameter("detcode1"			)!=null)param+="&detcode1="			+ request.getParameter("detcode1"		);	//승인여부
			if(request.getParameter("sel"			)!=null)param+="&sel="			+ request.getParameter("sel"		);	//승인여부

			//검색 변수 넘김
			request.setAttribute("param", param);
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);

		}
		request.setAttribute("from", "education");		//조건검색인지, 회비처리인지 구분
		return (mapping.findForward("sendDMForm"));
	}


	/*
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.30
	 * 작  업 : sendDMLink		DM 발송 실행 링크 
	 */
	public ActionForward sendDMLink(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		List<Map> count = null; //오류났을때 10개 이상인가 체크용
		List<Map> list= null;
		
		Map map = new HashMap();
		educationDao dao=new educationDao();
		
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
			
			//검색용 정보 만듬
			String temp_code_kind	= request.getParameter("code_kind"		);
			String temp_code_certifi= request.getParameter("code_certifi"	);
			String temp_code_seq	= request.getParameter("code_seq"		);
			String temp_code_bran	= request.getParameter("code_bran"		);
			String temp_bran_seq	= request.getParameter("bran_seq"		);
			String temp_receipt_no	= request.getParameter("receipt_no"		);

			String code_kind[]		= temp_code_kind.split(",");
			String code_certifi[]	= temp_code_certifi.split(",");
			String code_seq[]		= temp_code_seq.split(",");
			String code_bran[]		= temp_code_bran.split(",");
			String bran_seq[]		= temp_bran_seq.split(",");
			String receipt_no[]		= temp_receipt_no.split(",");
			
			
			tot_cnt = code_kind.length;
	//		System.out.println("asdfasdf length = "+code_kind.length);
			map.put("code_book_kind", "3");	//dm 종류
			count = dao.getEduDmForSeq(map);
			String dm_print_yymm_seq = count.get(0).get("dm_print_yymm_seq")+"";

			//for 문 돌면서 저장한다.
			for( int i=0; i<code_kind.length; i++) {

				map.clear();
				
				if(code_kind.length>0) {

					map.clear();

					//먼저 검색부터 하고 seq를 조사한다.
					map.put("code_book_kind", "3");	//dm 종류
					map.put("dm_print_yymm_seq"	, dm_print_yymm_seq);
					map.put("code_kind"		, code_kind[i]);
					map.put("code_certifi"	, code_certifi[i]);
					map.put("code_seq"		, code_seq[i]);
					map.put("code_bran"		, code_bran[i]);
					map.put("bran_seq"		, bran_seq[i]);
					map.put("receipt_no"	, receipt_no[i]);

					//대상을 조회하고
					list=dao.getEduDMForList(map,true);
					
					//seq를 알아온다.
					map.put("code_book_kind", "3");
					//만약 검색했는데 주소나 우편번호가 없으면 그냥 지나간다.
					if( list.size()>0 && list.get(0).get("code_pers") != null ) {
						map.put("dm_pers_code"	, list.get(0).get("code_pers"));//회원 번호
	
							map.clear();
							if(list.size() > 0) {
							/*	
								System.out.println("sendDMLink 저장전 검색 결과");
								System.out.println("");
								System.out.println("code_book_kind   = 3");
								System.out.println("dm_pers_code     = "+ list.get(0).get("code_pers"		));
								System.out.println("dm_print_yymm    = 쿼리에서 처리");
								System.out.println("dm_print_yymm_seq= "+ dm_print_yymm_seq+1);
								System.out.println("");
								System.out.println("code_member      = "+ list.get(0).get("code_member"		));
								System.out.println("pers_name        = "+ list.get(0).get("pers_name"		));
								System.out.println("lic_no           = "+ list.get(0).get("lic_no"			));
								System.out.println("code_post        = "+ list.get(0).get("code_post"		));
								System.out.println("pers_add         = "+ list.get(0).get("pers_add"		));
								System.out.println("pers_hp          = "+ list.get(0).get("pers_hp"			));
								System.out.println("email            = "+ list.get(0).get("email"			));
								System.out.println("code_bran        = "+ list.get(0).get("code_bran"		));
								System.out.println("print_call       = "+ list.get(0).get("print_call"		));
								System.out.println("code_pers        = "+ list.get(0).get("code_place"		));
								System.out.println("rece_yn          = 2");
								System.out.println("dm_creater       = "+ register);
								System.out.println("dm_name          = 교육별응시현황");
						*/		
								map.put("dm_pers_code"		, list.get(0).get("code_pers"));//회원 번호
								map.put("code_book_kind"	, "3");
								map.put("dm_print_yymm_seq"	, dm_print_yymm_seq);
								
								map.put("rece_yn"			, "2");			//수신여부
								map.put("dm_creater"		, register);	//DM생성자
								map.put("dm_name"			, "교육별응시현황");//DM명
								
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
									dao.setEduDMList(map);
									System.out.println("DM 개별전송 성공");
									success_cnt++;
								} catch (Exception e) {
									faile_cnt++;
									System.out.println("DM 개별전송 실패");
								}
							}
						}
				}else {
					System.out.println("DM 개별전송 code_kind.length==0 이다");
				}
			}

		} else if("ALL".equals(chk)) {
 
			// 변수에 값 받아서 넣음
			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
//			if(request.getParameter("code_certifi1"		)!=null)map.put("oper_no1"			, request.getParameter("oper_no1"		));	//주민번호 // JUMIN_DEL
			if(request.getParameter("oper_birth1"		)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
			if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
			if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
			if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
			if(request.getParameter("sel") != null)		map.put("sel",       	"Y");			
	/*		
System.out.println("sendDMLink 에서 대상 검색용 변수");
if(request.getParameter("edutest_name1"		)!=null)System.out.println("edutest_name1	= "	+ URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
if(request.getParameter("oper_name1"		)!=null)System.out.println("oper_name1		= "	+ URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
if(request.getParameter("oper_comp_name11"	)!=null)System.out.println("oper_comp_name11= "	+ URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
if(request.getParameter("yyyy1"				)!=null)System.out.println("yyyy1			= "	+ request.getParameter("yyyy1"			));	//교육년도
if(request.getParameter("code_bran1"		)!=null)System.out.println("code_bran1		= "	+ request.getParameter("code_bran1"		));	//교육주최
if(request.getParameter("code_kind1"		)!=null)System.out.println("code_kind1		= "	+ request.getParameter("code_kind1"		));	//교육종류
if(request.getParameter("code_certifi1"		)!=null)System.out.println("code_certifi1	= "	+ request.getParameter("code_certifi1"	));	//자격증 구분
if(request.getParameter("code_certifi1"		)!=null)System.out.println("oper_no1		= "	+ request.getParameter("oper_no1"		));	//주민번호 // JUMIN_DEL
if(request.getParameter("oper_lic_no1"		)!=null)System.out.println("oper_lic_no1	= "	+ request.getParameter("oper_lic_no1"	));	//면허번호
if(request.getParameter("oper_hp1"			)!=null)System.out.println("oper_hp1		= "	+ request.getParameter("oper_hp1"		));	//핸드폰
if(request.getParameter("code_operation1"	)!=null)System.out.println("code_operation1	= "	+ request.getParameter("code_operation1"));	//대상자
if(request.getParameter("result_end_dt1"	)!=null)System.out.println("result_end_dt1	= "	+ request.getParameter("result_end_dt1"	));	//유효일자
if(request.getParameter("start_dt1"			)!=null)System.out.println("start_dt1		= "	+ request.getParameter("start_dt1"		));	//유예시작일
if(request.getParameter("end_dt1"			)!=null)System.out.println("end_dt1			= "	+ request.getParameter("end_dt1"		));	//유예마지막일
if(request.getParameter("oper_state1"		)!=null)System.out.println("oper_state1		= "	+ request.getParameter("oper_state1"	));	//진행상태
if(request.getParameter("code_pay_flag1"	)!=null)System.out.println("code_pay_flag1	= "	+ request.getParameter("code_pay_flag1"	));	//결제방법
if(request.getParameter("receipt_dt1"		)!=null)System.out.println("receipt_dt1		= "	+ request.getParameter("receipt_dt1"	));	//신청일자
if(request.getParameter("code_seq1"			)!=null)System.out.println("code_seq1		= "	+ request.getParameter("code_seq1"		));	//순번
if(request.getParameter("detcode1"			)!=null)System.out.println("detcode1		= "	+ request.getParameter("detcode1"		));	//승인여부
*/

			//대상 검색
			list=dao.getEduDMForList(map,true);

			tot_cnt = list.size();

			map.put("code_book_kind", "3");	//dm 종류
			count = dao.getEduDmForSeq(map);
			String dm_print_yymm_seq = count.get(0).get("dm_print_yymm_seq")+"";

			//여기서 포문 돌면서 인서트 한다.
			for( int i=0; i<list.size(); i++) {

				map.clear();
				
				//현재 seq 값을 알아와
				map.put("code_book_kind", "3");	//dm 종류
				map.put("dm_pers_code"	, list.get(i).get("code_pers"));//회원 번호
				
					map.clear();

					map.put("code_book_kind"	, "3");
					map.put("dm_print_yymm_seq"	, dm_print_yymm_seq);
					if(list.get(i).get("code_member") != null) map.put("dm_pers_code"	, list.get(i).get("code_pers"	));//회원 번호
					if(list.get(i).get("code_member") != null) map.put("code_member"	, list.get(i).get("code_member"	));
					if(list.get(i).get("pers_name"	) != null) map.put("pers_name"		, list.get(i).get("pers_name"	));
					if(list.get(i).get("lic_no"		) != null) map.put("lic_no"			, list.get(i).get("lic_no"		));
					if(list.get(i).get("code_post"	) != null) map.put("code_post"		, list.get(i).get("code_post"	));
					if(list.get(i).get("pers_add"	) != null) map.put("pers_add"		, list.get(i).get("pers_add"	));
					if(list.get(i).get("pers_hp"	) != null) map.put("pers_hp"		, list.get(i).get("pers_hp"		));
					if(list.get(i).get("email"		) != null) map.put("email"			, list.get(i).get("email"		));
					if(list.get(i).get("code_bran"	) != null) map.put("code_bran"		, list.get(i).get("code_bran"	));
					if(list.get(i).get("print_call"	) != null) map.put("print_call"		, " 님 귀하");
					if(list.get(i).get("oper_comp_name1"	) != null) map.put("oper_comp_name1"		, list.get(i).get("oper_comp_name1"	));
					if(list.get(i).get("code_pers"	) != null) map.put("code_place"		, list.get(i).get("code_place"	));
					map.put("rece_yn"			, "2");
					map.put("dm_creater"		, register);
					map.put("dm_name"			, "교육별응시현황");//DM명
			/*		
System.out.println("sendDMLink 저장전 검색 결과");
System.out.println("code_book_kind   = 3");
System.out.println("dm_pers_code     = "+ list.get(i).get("code_pers"		));
System.out.println("dm_print_yymm_seq= "+ dm_print_yymm_seq+1);
System.out.println("code_member      = "+ list.get(i).get("code_member"		));
System.out.println("pers_name        = "+ list.get(i).get("pers_name"		));
System.out.println("lic_no           = "+ list.get(i).get("lic_no"			));
System.out.println("code_post        = "+ list.get(i).get("code_post"		));
System.out.println("pers_add         = "+ list.get(i).get("pers_add"			));
System.out.println("pers_hp          = "+ list.get(i).get("pers_hp"			));
System.out.println("email            = "+ list.get(i).get("email"			));
System.out.println("code_bran        = "+ list.get(i).get("code_bran"		));
System.out.println("print_call       = "+ list.get(i).get("print_call"		));
System.out.println("code_pers        = "+ list.get(i).get("code_place"		));
System.out.println("rece_yn          = 2");
System.out.println("dm_creater       = "+ register);
System.out.println("dm_name          = 교육별응시현황");
*/
					//셋 한다.
					try {

						dao.setEduDMList(map);
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

	
	
	
	
	
	
	/*교육별응시현황*/	
	public ActionForward educationSend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Map map = new HashMap();
		educationDao dao = new educationDao(); 

		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null){
		
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		
		/*3가지 조건에 맞는 교육명 출력*/
		List<Map> educationsend1 = dao.educationsend1(map);//educationsend1커리를 타고 실행 넘어온값 educationsend1 list<map>객체에 집어 넣는다
		JSONArray j_educationsend=JSONArray.fromObject(educationsend1);
		request.setAttribute("educationsend1" , educationsend1);
		request.setAttribute("j_educationsend" , j_educationsend);
		request.setAttribute("edutake", map);
		}
		
		return (mapping.findForward("educationSend"));
	}
	
	/*교육별응시현황 리스트*/
	public ActionForward educationSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		educationDao dao = new educationDao(); 

			if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null
			||request.getParameter("edutest_name1") != null||request.getParameter("code_operation1") != null||request.getParameter("receipt_dt1") != null
			||request.getParameter("code_pay_flag1") != null||request.getParameter("code_certifi1") != null||request.getParameter("oper_name1") != null
//			JUMIN_DEL
//			||request.getParameter("oper_no1") != null
			||request.getParameter("oper_birth1") != null
			||request.getParameter("oper_lic_no1") != null||request.getParameter("oper_hp1") != null
			||request.getParameter("oper_comp_name11") != null||request.getParameter("result_end_dt1") != null||request.getParameter("start_dt1") != null
			||request.getParameter("end_dt1") != null||request.getParameter("oper_state1") != null||request.getParameter("code_seq1") != null
			/*||request.getParameter("bran_seq1") != null*/){
	
				/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		map.put("edutest_name1",        URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//내용
		//map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
//		map.put("oper_no1",       		request.getParameter("oper_no1"));										//주민번호 // JUMIN_DEL
		map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일 // JUMIN_DEL
		map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
		map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
		map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
		map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
		map.put("result_end_dt1",       request.getParameter("result_end_dt1"));								//유효일자
		map.put("start_dt1",       		request.getParameter("start_dt1"));										//유예시작일
		map.put("end_dt1",       		request.getParameter("end_dt1"));										//유예마지막일
		map.put("oper_state1",       	request.getParameter("oper_state1"));									//진행상태
		map.put("code_pay_flag1",       request.getParameter("code_pay_flag1"));								//결제방법
		map.put("receipt_dt1",       	request.getParameter("receipt_dt1"));									//신청일자
		map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
		//교육명 선택시 특정교육만 조회
		map.put("detcode1",        		request.getParameter("detcode1"));										//승인여부
		map.put("confirm_dt",        	request.getParameter("confirm_dt"));
		if(request.getParameter("sel") != null)		map.put("sel",       	"Y");											
		/*System.out.println("이름값="+URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));
		System.out.println("이름값1="+request.getParameter("oper_name1"));*/
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
		List<Map> educationcount=dao.educationsendcnt(map); 
		
		ncount = ((Integer)(educationcount.get(0).get("cnt"))).intValue();

		int ntotpage = (ncount/nrows)+1;
		
		List<Map> selecteducation = dao.selecteducation(map);
		
		
		StringBuffer result = new StringBuffer();
				
		result.append("{\"page\":\""+	npage	+"\",");		
		result.append("\"total\":\"" + ntotpage+"\",");
		result.append("\"records\":\""+	ncount	+"\",");
		result.append("\"rows\":[");	
		
		
		for(int i=0; i<selecteducation.size(); i++){
			/* 그리드에 출력하는 컬럼 순서*/
			if(i>0) result.append(",");
			result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
			result.append("\"" + ( selecteducation.get(i).get("detcode")	    	== null ? "" : selecteducation.get(i).get("detcode") )      	+"\",");	//key
			result.append("\"" + ( selecteducation.get(i).get("edutest_name")		== null ? "" : selecteducation.get(i).get("edutest_name"))  	+"\",");   	//내용
			result.append("\"" + ( selecteducation.get(i).get("receipt_dt")   		== null ? "" : selecteducation.get(i).get("receipt_dt") )   	+"\",");	//시작일
			result.append("\"" + ( selecteducation.get(i).get("receipt_no")			== null ? "" : selecteducation.get(i).get("receipt_no"))  		+"\",");   	//접수번호
			result.append("\"" + ( selecteducation.get(i).get("person_yn")			== null ? "" : selecteducation.get(i).get("person_yn")) 	  	+"\",");	//회원여부
			result.append("\"" + ( selecteducation.get(i).get("oper_name")	    	== null ? "" : selecteducation.get(i).get("oper_name") )    	+"\",");	//이름
			result.append("\"" + ( selecteducation.get(i).get("oper_lic_no")		== null ? "" : selecteducation.get(i).get("oper_lic_no") )  	+"\",");	//면허번호
//			JUMIN_DEL
//			result.append("\"" + ( selecteducation.get(i).get("oper_no")			== null ? "" : selecteducation.get(i).get("oper_no") )      	+"\",");	//주민번호
			result.append("\"" + ( selecteducation.get(i).get("oper_birth")			== null ? "" : selecteducation.get(i).get("oper_birth") )      	+"\",");	//생년월일
			result.append("\"" + ( selecteducation.get(i).get("oper_comp_name1")	== null ? "" : URLEncoder.encode(selecteducation.get(i).get("oper_comp_name1").toString(), "UTF-8") ) 	+"\",");	//근무처	
			result.append("\"" + ( selecteducation.get(i).get("oper_hp")	   		== null ? "" : selecteducation.get(i).get("oper_hp") )     		+"\",");	//핸드폰
			result.append("\"" + ( selecteducation.get(i).get("oper_email")	    	== null ? "" : selecteducation.get(i).get("oper_email") )     	+"\",");	//이메일	
			
			result.append("\"" + ( selecteducation.get(i).get("code_pay_flag")		== null ? "" : selecteducation.get(i).get("code_pay_flag") )  	+"\","); 	//결제방법
			result.append("\"" + ( selecteducation.get(i).get("payflag")			== null ? "" : selecteducation.get(i).get("payflag") )  		+"\","); 	//결제방법
			result.append("\"" + ( selecteducation.get(i).get("oper_account")	    == null ? "" : selecteducation.get(i).get("oper_account") )   	+"\",");	//입금자
			result.append("\"" + ( selecteducation.get(i).get("operstate")	    	== null ? "" : selecteducation.get(i).get("operstate") )     	+"\",");	//상태
			result.append("\"" + ( selecteducation.get(i).get("detcodename")	    == null ? "" : selecteducation.get(i).get("detcodename") )    	+"\",");	//첨부파일

			result.append("\"" + ( selecteducation.get(i).get("yyyy")	   			== null ? "" : selecteducation.get(i).get("yyyy") )   	 		+"\",");	//교육년도
			result.append("\"" + ( selecteducation.get(i).get("code_bran")	    	== null ? "" : selecteducation.get(i).get("code_bran") )    	+"\",");	//교육주최
			result.append("\"" + ( selecteducation.get(i).get("code_kind")	    	== null ? "" : selecteducation.get(i).get("code_kind") )      	+"\",");	//교육종류
			result.append("\"" + ( selecteducation.get(i).get("certifi_name")	    == null ? "" : selecteducation.get(i).get("certifi_name") )   	+"\",");	//자격증구분
			result.append("\"" + ( selecteducation.get(i).get("code_operation")		== null ? "" : selecteducation.get(i).get("code_operation") ) 	+"\",");	//대상자
			
			result.append("\"" + ( selecteducation.get(i).get("result_end_dt")		== null ? "" : selecteducation.get(i).get("result_end_dt") )  	+"\",");	//유효일자			
			result.append("\"" + ( selecteducation.get(i).get("start_dt")	    	== null ? "" : selecteducation.get(i).get("start_dt") )       	+"\",");	//유예시작일
			result.append("\"" + ( selecteducation.get(i).get("end_dt")	    		== null ? "" : selecteducation.get(i).get("end_dt") )      		+"\",");	//유예마지막일
			result.append("\"" + ( selecteducation.get(i).get("oper_state")	    	== null ? "" : selecteducation.get(i).get("oper_state") )     	+"\",");	//상태
			result.append("\"" + ( selecteducation.get(i).get("regi_date")	    	== null ? "" : selecteducation.get(i).get("regi_date") )      	+"\",");	//결제여부
			
			result.append("\"" + ( selecteducation.get(i).get("code_certifi")	    == null ? "" : selecteducation.get(i).get("code_certifi") )   	+"\",");	//자격증구분코드값
			result.append("\"" + ( selecteducation.get(i).get("bran_seq")	    	== null ? "" : selecteducation.get(i).get("bran_seq") )     	+"\",");		//히든 지부순번
//			result.append("\"" + ( selecteducation.get(i).get("oper_no1")	    	== null ? "" : selecteducation.get(i).get("oper_no1") )     	+"\",");		//히든 주민번호(xml에서 추가한 명칭값과동일하게 ) // JUMIN_DEL
			result.append("\"" + ( selecteducation.get(i).get("code_seq")	    	== null ? "" : selecteducation.get(i).get("code_seq") )     	+"\",");		//히든 순번(xml에서 추가한 명칭값과동일하게 )
			result.append("\"" + ( selecteducation.get(i).get("person_yn1")	    	== null ? "" : selecteducation.get(i).get("person_yn1") )     	+"\",");			//히든 회원코드값 )
			result.append("\"" + ( selecteducation.get(i).get("code_seq")	    == null ? "" : selecteducation.get(i).get("code_seq") )   			+"\",");	//자격증구분코드값
			result.append("\"" + ( selecteducation.get(i).get("bran_seq")	    == null ? "" : selecteducation.get(i).get("bran_seq") )   			+"\",");	//자격증구분코드값
			result.append("\"" + ( selecteducation.get(i).get("certifi_name")	    == null ? "" : selecteducation.get(i).get("certifi_name") )   			+"\",");	//자격증구분코드값
			result.append("\"" + ( selecteducation.get(i).get("receipt_no")	    == null ? "" : selecteducation.get(i).get("receipt_no") )   		+"\",");	//자격증구분코드값
			result.append("\"" + ( selecteducation.get(i).get("confirm_dt")	    == null ? "" : selecteducation.get(i).get("confirm_dt") )   		+"\"");	//확정일자
			result.append("]}");
			
		}	
		

		result.append("]}");
		request.setAttribute("result",result);
	}

		return (mapping.findForward("ajaxout"));
}
	

	/*교육별 응시현황 개인정보 일괄변경 -----------------------------------------------*/
	public ActionForward edutakeUpdateMemBatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// return 용 map 
		Map mapReturn = new HashMap();
		educationDao dao = new educationDao();
		
		mapReturn.put("yyyy1",        		request.getParameter("yyyy1"));										//교육년도
		mapReturn.put("code_bran1",        	request.getParameter("code_bran1"));								//교육주체
		mapReturn.put("code_kind1",        	request.getParameter("code_kind1"));								//교육종류
		//교육명 선택시 특정교육만 조회
		mapReturn.put("detcode1",        	request.getParameter("detcode1"));									//교육및시험종류
		
		
		String rcheck = "N";	// 선택여부
		
		if(request.getParameter("sel") != null){
			rcheck = request.getParameter("sel");
		}
		
		
		String errMsg = "";
		
		//System.out.println("rcheck : " + rcheck + " // request.getParameter(sel) : " + request.getParameter("sel"));
		
		//if("Y".equals(rcheck)){

			Map mapSelect = new HashMap();
			List<String> receipt_infos_org =  Arrays.asList(request.getParameter("receipt_infos").split("__"));
			List<List<String>> receipt_infos = new ArrayList<List<String>>();
			
			for(String item : receipt_infos_org){
				List<String> receipt_info =  Arrays.asList(item.split("_"));
				receipt_infos.add(receipt_info);
			}
			
			for(List<String> receipt_item : receipt_infos){
				String save_code_kind    = receipt_item.get(0);
				String save_code_certifi = receipt_item.get(1);
				String save_code_seq     = receipt_item.get(2);
				String save_code_bran    = receipt_item.get(3);
				String save_bran_seq     = receipt_item.get(4);
				String save_receipt_no   = receipt_item.get(5);
				String save_code_pers    = receipt_item.get(6);
				mapSelect.put("code_pers1", save_code_pers);
				
				List<Map> selectpersonmtbl = dao.selectpersonmtbl(mapSelect);
				//System.out.println("selectpersonmtbl.size() check ===========> " + selectpersonmtbl.size());
				
				if( selectpersonmtbl.size() > 0 ) {
					
					Map mapSave = new HashMap();
					mapSave.put("code_pers",         ( save_code_pers ) );
					mapSave.put("code_email_comp",   ( selectpersonmtbl.get(0).get("code_email_comp") == null ? "" : selectpersonmtbl.get(0).get("code_email_comp")) );	//code_email_comp
					mapSave.put("code_post",         ( selectpersonmtbl.get(0).get("code_post")       == null ? "" : selectpersonmtbl.get(0).get("code_post")) );		//우편번호
					mapSave.put("oper_comp_name1",   ( selectpersonmtbl.get(0).get("company_name")    == null ? "" : selectpersonmtbl.get(0).get("company_name")) );	//근무처명
					mapSave.put("oper_comp_add1_1",  ( "" ) );
					mapSave.put("oper_comp_add1_2",  ( selectpersonmtbl.get(0).get("comp_add")        == null ? "" : selectpersonmtbl.get(0).get("comp_add")) );		//주소

					mapSave.put("code_kind",         save_code_kind );
					mapSave.put("code_certifi",      save_code_certifi );
					mapSave.put("code_seq",          save_code_seq );
					mapSave.put("code_bran",         save_code_bran );
					mapSave.put("bran_seq",          save_bran_seq );
					mapSave.put("receipt_no",        save_receipt_no );
					//mapSave.put("receipt_infos",     receipt_infos);
					
					int updateeducation = dao.updateeducationMemberBatch(mapSave);
					
				} else {
					// update 하지 말 것!!
					errMsg = "회원정보를 찾을 수 없습니다.";
					request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
					
					/* 3가지 조건에 맞는 교육명 출력 */
					List<Map> educationsend1  = dao.educationsend1(mapReturn);	//educationsend1커리를 타고 실행 넘어온값 educationsend1 list<mapReturn>객체에 집어 넣는다
					JSONArray j_educationsend = JSONArray.fromObject(educationsend1);
					request.setAttribute("educationsend1"  , educationsend1);
					request.setAttribute("j_educationsend" , j_educationsend);
					request.setAttribute("edutake", mapReturn);
					
					return (mapping.findForward("educationSend"));
				}
				
			}
		//}

		errMsg = "개인정보 일괄변경 처리 했습니다.";
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
		
		/* 3가지 조건에 맞는 교육명 출력 */
		List<Map> educationsend1  = dao.educationsend1(mapReturn);	//educationsend1커리를 타고 실행 넘어온값 educationsend1 list<mapReturn>객체에 집어 넣는다
		JSONArray j_educationsend = JSONArray.fromObject(educationsend1);
		request.setAttribute("educationsend1"  , educationsend1);
		request.setAttribute("j_educationsend" , j_educationsend);
		request.setAttribute("edutake", mapReturn);
		
		return (mapping.findForward("educationSend"));
	}
	
	
	
	/*교육별 응시현황 저장-----------------------------------------------*/
	public ActionForward edutakeSave (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		
		Map map = new HashMap();
		Map map1 = new HashMap();
		
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		map.put("edutest_name1",        request.getParameter("edutest_name1"));		//내용
		map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
//		map.put("receipt_dt1",       	request.getParameter("receipt_dt1"));									//신청일자
		map.put("code_pay_flag1",       request.getParameter("code_pay_flag1"));								//결제방법
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
//		map.put("oper_no1",       		request.getParameter("oper_no1"));										//주민번호 // JUMIN_DEL
		map.put("oper_birth1",       		request.getParameter("oper_birth1"));								//생년월일
		
		map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
		map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
		map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
		map.put("result_end_dt1",       request.getParameter("result_end_dt1"));								//유효일자
		map.put("start_dt1",       		request.getParameter("start_dt1"));										//유예시작일
		map.put("end_dt1",       		request.getParameter("end_dt1"));									//유예마지막일
		String gState = request.getParameter("oper_state1");
		map.put("oper_state1",       	gState);									//진행상태
		map.put("receipt_no1",       	StringUtil.nullToStr("",request.getParameter("receipt_no1")));			//접수번호
		map.put("bran_seq1",       		request.getParameter("bran_seq1"));										//지부순번
		map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
		map.put("register1",       		g_name);	
		map.put("certifi_name",       	request.getParameter("certifi_name"));	//
		map.put("code_pers1",       	request.getParameter("code_pers1"));	//
		
		//확정 : 11 이 아니면 영수증 발행일 "" 저장
		if (gState.equals("11")) {
			map.put("confirm_dt",       	request.getParameter("confirm_dt"));	//확정일자
		}else{
			map.put("confirm_dt",       	"");	//확정일자
		}
		
		educationDao dao = new educationDao(); 

		List<Map> selectpersonmtbl = dao.selectpersonmtbl(map,true);
		System.out.println("selectpersonmtbl.size()===========>"+selectpersonmtbl.size());
		if ( selectpersonmtbl.size() > 0 ) {
			if (request.getParameter("code_kind1").toString().equals("4")) { //위생교육인지 체크
				map.put("person_yn1",       	( selectpersonmtbl.get(0).get("code_pers")	== null ? "" : selectpersonmtbl.get(0).get("code_pers")) );		//회원코드
			} else {
				map.put("oper_name1",        	( selectpersonmtbl.get(0).get("pers_name")	== null ? "" : selectpersonmtbl.get(0).get("pers_name")) );		//이름
//				map.put("oper_no1",       		( selectpersonmtbl.get(0).get("pers_no")	== null ? "" : selectpersonmtbl.get(0).get("pers_no")) );		//주민번호 // JUMIN_DEL
				map.put("oper_birth1",       		( selectpersonmtbl.get(0).get("pers_birth")	== null ? "" : selectpersonmtbl.get(0).get("pers_birth")) );		//생년월일
				map.put("oper_lic_no1",       	( selectpersonmtbl.get(0).get("lic_no")		== null ? "" : selectpersonmtbl.get(0).get("lic_no")) );		//면허번호
				map.put("oper_hp1",       		( selectpersonmtbl.get(0).get("pers_hp")	== null ? "" : selectpersonmtbl.get(0).get("pers_hp")) );		//핸드폰
				map.put("oper_email1",       	( selectpersonmtbl.get(0).get("email")		== null ? "" : selectpersonmtbl.get(0).get("email")) );			//email
				map.put("oper_comp_name11",     ( selectpersonmtbl.get(0).get("company_name")	== null ? "" : selectpersonmtbl.get(0).get("company_name")) );		//근무처명
				map.put("code_post1",       	( selectpersonmtbl.get(0).get("code_post")	== null ? "" : selectpersonmtbl.get(0).get("code_post")) );		//우편번호
				map.put("oper_comp_add1_21",    ( selectpersonmtbl.get(0).get("comp_add")	== null ? "" : selectpersonmtbl.get(0).get("comp_add")) );		//주소
				map.put("person_yn1",       	( selectpersonmtbl.get(0).get("code_pers")	== null ? "" : selectpersonmtbl.get(0).get("code_pers")) );		//회원코드
				map.put("code_use1",       		( selectpersonmtbl.get(0).get("code_use")	== null ? "" : selectpersonmtbl.get(0).get("code_use")) );		//운영형태
				map.put("trust_name1",       	( selectpersonmtbl.get(0).get("trust_name")	== null ? "" : selectpersonmtbl.get(0).get("trust_name")) );	//위탁업체명
				map.put("code_great1",       	( selectpersonmtbl.get(0).get("code_great")	== null ? "" : selectpersonmtbl.get(0).get("code_great")) );	//근무처대분류
				map.put("company_tel1",       	( selectpersonmtbl.get(0).get("company_tel")	== null ? "" : selectpersonmtbl.get(0).get("company_tel")) );		//전화번호
			}
		} else {
			map.put("person_yn1",       	"" );		//회원코드
		}
		
		
		
		String errMsg="";

		List<Map> selectoperater = dao.selectoperater(map); // BIRTH_CHECK
		List<Map> temp = new ArrayList();
		System.out.println("selectoperater.size===========>"+selectoperater.size());
		if ( selectoperater.size()>0) {
			 map.put("receipt_no1", selectoperater.get(0).get("receipt_no") );		//접수번호
			 
			 if(!StringUtil.NVL(map.get("oper_hp1")).equals(StringUtil.NVL(selectoperater.get(0).get("oper_hp")))){ map.put("oper_hp1_upok","Y"); }
			 
			 int updateeducation = dao.updateeducation11(map); // BIRTH_CHECK
		} else {
			if (request.getParameter("code_kind1").toString().equals("4")) { //위생교육인지 체크	
				System.out.println("위생교육!!!!!!!!!");
				List<Map> chkTestListCount =dao.ChkTestListCount(map); // BIRTH_CHECK
				if(chkTestListCount.get(0).get("cnt").toString().equals("0")){				
					map.put("receipt_no1", "" );		//접수번호		
					List<Map> educationinsert = dao.inserteducation11(map);	
					temp= dao.selectoperater(map);
					System.out.println("receipt_no1===============>"+temp.get(0).get("receipt_no"));
					map.put("receipt_no22", temp.get(0).get("receipt_no"));
				}else{
					errMsg="입금확인중이거나 확정된 위생교육이 있습니다.\\n변경/취소 후 다시 신청 해 주세요.";
					request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
					map.put("tmp_dup", "Y");
						
					map1.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
					map1.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
					map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
					map1.put("detcode1",        	request.getParameter("edutest_name1"));									//교육명키값
					List<Map> educationsend1 = dao.educationsend1(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
						
					System.out.println("code_certifi1==================>"+request.getParameter("code_certifi1"));
					JSONArray j_educationsend=JSONArray.fromObject(educationsend1);
					request.setAttribute("educationsend1" , educationsend1);
					request.setAttribute("j_educationsend" , j_educationsend);
					request.setAttribute("edutakeets", map);
						
					return mapping.findForward("eduinsert_send");
				}
			} else {
				map.put("receipt_no1", "" );		//접수번호		
				List<Map> educationinsert = dao.inserteducation11(map);	
				temp= dao.selectoperater(map);
				System.out.println("receipt_no1===============>"+temp.get(0).get("receipt_no"));
				map.put("receipt_no22", temp.get(0).get("receipt_no"));
			}
		}
		errMsg="저장했습니다.";
		
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
		map.put("tmp_dup", "N");
		
		map1.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map1.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		map1.put("detcode1",        	request.getParameter("edutest_name1"));									//교육명키값

		List<Map> educationsend1 = dao.educationsend1(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
		
		System.out.println("code_certifi1==================>"+request.getParameter("code_certifi1"));
		JSONArray j_educationsend=JSONArray.fromObject(educationsend1);
		request.setAttribute("educationsend1" , educationsend1);
		request.setAttribute("j_educationsend" , j_educationsend);
		request.setAttribute("edutakeets", map);
		
		if(request.getParameter("s_pgno")!=null){
			String pgno = request.getParameter("s_pgno");
			request.setAttribute("pgno",   pgno);
		}		  	

		return mapping.findForward("eduinsert_send");//저장후 저장한값을 화면에 다시 표출해야한다
	}
	
	/*교육별 응시현황 삭제-----------------------------------------------*/
	public ActionForward edutakeDel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
			HttpSession session=request.getSession();
			educationDao dao = new educationDao(); 

			if(request.getParameter("code_certifi1") != null&&request.getParameter("code_kind1") != null&&request.getParameter("code_seq1") != null
					&&request.getParameter("code_bran1") != null&&request.getParameter("bran_seq1") != null&&request.getParameter("receipt_no1") != null	&& ("cocone".equals((String)session.getAttribute("G_ID")) ||"minju51".equals((String)session.getAttribute("G_ID"))) )
			{
				// 교육 응시현황 삭제
				Map map = new HashMap();
				map.put("code_kind1",        		request.getParameter("code_kind1"));										
				map.put("code_certifi1",       		request.getParameter("code_certifi1"));										
				map.put("code_seq1",        		request.getParameter("code_seq1"));										
				map.put("code_bran1",        		request.getParameter("code_bran1"));										
				map.put("bran_seq1",        		request.getParameter("bran_seq1"));
				map.put("receipt_no1",        		request.getParameter("receipt_no1"));
				
				String errMsg="";
				int deleteedutest = dao.deleteeducation11(map);
				errMsg="삭제했습니다.";
				request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
			}

			if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null){
				
				Map map1 = new HashMap();
				map1.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
				map1.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
				map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
				map1.put("detcode1",        	request.getParameter("detcode1"));									//교육및시험종류
				
				/*3가지 조건에 맞는 교육명 출력*/
				List<Map> educationsend1 = dao.educationsend1(map1);//educationsend1커리를 타고 실행 넘어온값 educationsend1 list<map1>객체에 집어 넣는다
				JSONArray j_educationsend=JSONArray.fromObject(educationsend1);
				request.setAttribute("educationsend1" , educationsend1);
				request.setAttribute("j_educationsend" , j_educationsend);
				request.setAttribute("edutake", map1);
			}
			
			return (mapping.findForward("educationSend"));
	}



	/*교육별 응시현황 저장 전 응시자 카운트 체크-----------------------------------------------*/
	public ActionForward edutakeSaveCheck (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map map = new HashMap();
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
		map.put("oper_birth1",       		request.getParameter("oper_birth1"));								//생년월일
		
		educationDao dao = new educationDao(); 
		List<Map> selectpersonmtbl = dao.selectpersonmtbl(map);
		System.out.println("selectpersonmtbl.size() check ===========>"+selectpersonmtbl.size());
		
		
		StringBuffer result = new StringBuffer();
		
		result.append("{\"records\":\""+	selectpersonmtbl.size()	+"\",");
		result.append("\"rows\":[");	
		
		for(int i=0; i<selectpersonmtbl.size(); i++){
			if(i>0) result.append(",");
			result.append("{");
			result.append("\"code_pers\":\"" + ( selectpersonmtbl.get(i).get("code_pers") == null ? "" : selectpersonmtbl.get(i).get("code_pers") ) + "\",");
			result.append("\"id\":\"" + ( selectpersonmtbl.get(i).get("id") == null ? "" : selectpersonmtbl.get(i).get("id") ) + "\",");
			result.append("\"code_member\":\"" + ( selectpersonmtbl.get(i).get("code_member") == null ? "" : selectpersonmtbl.get(i).get("code_member") ) + "\",");
			result.append("\"lic_no\":\"" + ( selectpersonmtbl.get(i).get("lic_no") == null ? "" : selectpersonmtbl.get(i).get("lic_no") ) + "\",");
			result.append("\"pers_hp\":\"" + ( selectpersonmtbl.get(i).get("pers_hp") == null ? "" : selectpersonmtbl.get(i).get("pers_hp") ) + "\"");
			result.append("}");
		}	

		result.append("]}");
		request.setAttribute("result",result);

		return (mapping.findForward("ajaxout"));
	}
	

	/*교육별응시현황 일괄처리*/
	public ActionForward edutakeSaveBatch(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = new HashMap();
		educationDao dao = new educationDao(); 

			if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null
			||request.getParameter("edutest_name1") != null||request.getParameter("code_operation1") != null||request.getParameter("receipt_dt1") != null
			||request.getParameter("code_pay_flag1") != null||request.getParameter("code_certifi1") != null||request.getParameter("oper_name1") != null
//			JUMIN_DEL
//			||request.getParameter("oper_no1") != null
			||request.getParameter("oper_birth1") != null
			||request.getParameter("oper_lic_no1") != null||request.getParameter("oper_hp1") != null
			||request.getParameter("oper_comp_name11") != null||request.getParameter("result_end_dt1") != null||request.getParameter("start_dt1") != null
			||request.getParameter("end_dt1") != null||request.getParameter("oper_state1") != null||request.getParameter("code_seq1") != null
			/*||request.getParameter("bran_seq1") != null*/){
	
				/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		map.put("edutest_name1",        URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//내용
		//map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
//		map.put("oper_no1",       		request.getParameter("oper_no1"));										//주민번호 // JUMIN_DEL
		map.put("oper_birth1",       		request.getParameter("oper_birth1"));										//생년월일 // JUMIN_DEL
		map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
		map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
		map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
		map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
		map.put("result_end_dt1",       request.getParameter("result_end_dt1"));								//유효일자
		map.put("start_dt1",       		request.getParameter("start_dt1"));										//유예시작일
		map.put("end_dt1",       		request.getParameter("end_dt1"));										//유예마지막일
		map.put("oper_state1",       	request.getParameter("oper_state1"));									//진행상태
		map.put("code_pay_flag1",       request.getParameter("code_pay_flag1"));								//결제방법
		map.put("receipt_dt1",       	request.getParameter("receipt_dt1"));									//신청일자
		map.put("code_seq1",       		request.getParameter("code_seq1"));										//순번
		//교육명 선택시 특정교육만 조회
		map.put("detcode1",        		request.getParameter("detcode1"));										//승인여부
		map.put("confirm_dt",        	request.getParameter("confirm_dt"));
		
		if(request.getParameter("sel") != null)		map.put("sel",       	"Y");											

		map.put("nstart", 0);
		map.put("nend", 999999);
		
		String rcheck = request.getParameter("rcheck"); // 선택여부
		String errMsg="";
		
		if("Y".equals(rcheck)){
			List<String> receipt_infos_org =  Arrays.asList(request.getParameter("receipt_infos"	).split("__"));
			List<List<String>> receipt_infos = new ArrayList<List<String>>();
			
			for(String item : receipt_infos_org){
				List<String> receipt_info =  Arrays.asList(item.split("_"));
				receipt_infos.add(receipt_info);
			}
			Map mapSave = new HashMap();
			mapSave.put("receipt_infos", receipt_infos);
			mapSave.put("confirm_dt", request.getParameter("confirm_dt"));
			int updateeducation = dao.updateeducationBatch(mapSave); // BIRTH_CHECK
		} else {
			List<Map> selecteducation = dao.selecteducation(map);
			System.out.println("selecteducation.size===========>"+selecteducation.size());
			
			for(int i=0; i<selecteducation.size(); i++){
				List<List<String>> receipt_infos = new ArrayList<List<String>>();
				List<String> receipt_info =  new ArrayList<String>();
				receipt_info.add((String) selecteducation.get(i).get("code_kind"));
				receipt_info.add((String) selecteducation.get(i).get("code_certifi"));
				receipt_info.add((String) selecteducation.get(i).get("code_seq"));		
				receipt_info.add((String) selecteducation.get(i).get("code_bran"));		
				receipt_info.add((String) selecteducation.get(i).get("bran_seq"));		
				receipt_info.add((String) selecteducation.get(i).get("receipt_no"));
				receipt_infos.add(receipt_info);
				Map mapSave = new HashMap();
				mapSave.put("receipt_infos", receipt_infos);
				mapSave.put("confirm_dt",request.getParameter("confirm_dt"));
				int updateeducation = dao.updateeducationBatch(mapSave); // BIRTH_CHECK
			}	
		}

		errMsg="일괄 확정처리 했습니다.";
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));

		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null){
			
			Map map1 = new HashMap();
			map1.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
			map1.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
			map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
			map1.put("detcode1",        	request.getParameter("detcode1"));									//교육및시험종류
			
			/*3가지 조건에 맞는 교육명 출력*/
			List<Map> educationsend1 = dao.educationsend1(map1);//educationsend1커리를 타고 실행 넘어온값 educationsend1 list<map1>객체에 집어 넣는다
			JSONArray j_educationsend=JSONArray.fromObject(educationsend1);
			request.setAttribute("educationsend1" , educationsend1);
			request.setAttribute("j_educationsend" , j_educationsend);
			request.setAttribute("edutake", map1);
		}
		
	}

	return (mapping.findForward("educationSend"));
}
	
	
	
	/*교육별시험등록------------------------------------------------------------------------------ */
public ActionForward edutestSend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	
	Map map = new HashMap();
	educationDao dao = new educationDao(); 
//	System.out.println("시험구분"+request.getParameter("code_certifi1"));

	if(request.getParameter("code_certifi1") != null||request.getParameter("code_kind1") != null){
	
	map.put("code_certifi1",        request.getParameter("code_certifi1"));									//교육및시험구분
	map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육및시험종류
//	System.out.println("교육주최="+request.getParameter("code_kind1"));
//	2가지 조건에 맞는 교육명 출력
	List<Map> educationsend2 = dao.educationsend2(map);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
	JSONArray j_educationsend1=JSONArray.fromObject(educationsend2);
	request.setAttribute("educationsend2" , educationsend2);
	request.setAttribute("j_educationsend1" , j_educationsend1);
	request.setAttribute("edutake1", map);
	}
	return (mapping.findForward("edutestSend"));

}
//교육및시험등록 리스트 출력
public ActionForward edutestSendList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	
//	HttpSession session=request.getSession();
	Map map = new HashMap();
	
//	session.getAttribute("G_NAME");
	
	educationDao dao = new educationDao(); 

	//session 에 담긴 값은 Attribute로 읽어야 한다.
//	System.out.println("등록자="+session.getAttribute("G_NAME"));
	
	if(request.getParameter("edutest_name1") != null||request.getParameter("code_certifi1") != null||request.getParameter("finish_time1") != null
		||request.getParameter("outside_yn1") != null||request.getParameter("code_kind1") != null||request.getParameter("finish_date1") != null
		||request.getParameter("use_yn1") != null||request.getParameter("finish_point1") != null||request.getParameter("edu_marks1") != null
		||request.getParameter("conform1") != null||request.getParameter("print_kind1") != null||request.getParameter("point_manage1") != null
		||request.getParameter("yyyy1") != null||request.getParameter("season1") != null||request.getParameter("operation_cnt1") != null
		||request.getParameter("operation1") != null||request.getParameter("oper_start_dt1") != null||request.getParameter("start_dt1") != null
		||request.getParameter("account_end_dt1") != null/*||request.getParameter("up_date1") != null*/||request.getParameter("account_way1") != null
		/*||request.getParameter("register1") != null*/||request.getParameter("receipt_pers_cnt1") != null||request.getParameter("operation_place1") != null
		||request.getParameter("oper_end_dt1") != null||request.getParameter("end_dt1") != null/*||request.getParameter("regi_date1") != null*/
		/*||request.getParameter("updater1") != null*/||request.getParameter("remark1") != null||request.getParameter("code_seq1") != null
		||request.getParameter("code_bran1") != null||request.getParameter("bran_seq1") != null||request.getParameter("bran_txt1") != null)
	{
		

//			 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. 
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//지부(교육주최)
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육및시험종류		
		map.put("season1",        		request.getParameter("season1"));										//학기
		map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//횟수
		map.put("operation1",        	request.getParameter("operation1"));									//시행대상
		map.put("start_dt1",        	request.getParameter("start_dt1"));										//접수시작일
		map.put("end_dt1",        		request.getParameter("end_dt1"));										//접수마감일
		map.put("oper_start_dt1",       request.getParameter("oper_start_dt1"));								//시행시작일
		map.put("oper_end_dt1",        	request.getParameter("oper_end_dt1"));									//시행종료일
		map.put("receipt_pers_cnt1",    request.getParameter("receipt_pers_cnt1"));								//접수인원
		map.put("operation_place1",     request.getParameter("operation_place1"));	//시행장소
		map.put("account_end_dt1",      request.getParameter("account_end_dt1"));								//결제마감일
		map.put("account_way1",        	request.getParameter("account_way1"));									//결제방법
		map.put("remark1",        		request.getParameter("remark1"));			//비고
		map.put("outside_yn1",        	request.getParameter("outside_yn1"));									//외부교육여부
		map.put("detcode1",        		request.getParameter("detcode1"));										//승인여부
		map.put("bran_seq1",        	request.getParameter("bran_seq1"));										//지부순번
		map.put("bran_txt1",        	URLDecoder.decode(request.getParameter("bran_txt1"),"UTF-8"));			//지부순번
		map.put("oper_start_tm1",       request.getParameter("oper_start_tm1"));								//시행시간1
		map.put("oper_end_tm1",        	request.getParameter("oper_end_tm1"));									//시행시간2

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
	List<Map> edutestsendcount=dao.edutestsendcnt1(map); 
	
	ncount = ((Integer)(edutestsendcount.get(0).get("cnt"))).intValue();

	int ntotpage = (ncount/nrows)+1;
	
	List<Map> selectedutest = dao.selectedutest(map);
	
	StringBuffer result = new StringBuffer();
			
	result.append("{\"page\":\""+	npage	+"\",");		
	result.append("\"total\":\"" + ntotpage+"\",");
	result.append("\"records\":\""+	ncount	+"\",");
	result.append("\"rows\":[");	
	
	for(int i=0; i<selectedutest.size(); i++){
//		 그리드에 출력하는 컬럼 순서
		//그리드에 출력될 값과 크기 조정작업중10-17일~~~~~~~~~~~~~위치 조정 필요~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		
		if(i>0) result.append(",");
		result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
		result.append("\"" + ( selectedutest.get(i).get("yyyy")					== null ? "" : selectedutest.get(i).get("yyyy"))  				+"\",");   	//교육년도
		result.append("\"" + ( selectedutest.get(i).get("codekindname")			== null ? "" : selectedutest.get(i).get("codekindname"))  		+"\",");   	//교육종류(key)
		result.append("\"" + ( selectedutest.get(i).get("branname")   			== null ? "" : selectedutest.get(i).get("branname") )   		+"\",");	//교육주최(key)
// select box에 표출시 code값으로 한다. 그리드표출시에는 텍스트로
		result.append("\"" + ( selectedutest.get(i).get("edutestname")			== null ? "" : selectedutest.get(i).get("edutestname")) 	  	+"\",");	//내용
		result.append("\"" + ( selectedutest.get(i).get("oper_start_dt")	    == null ? "" : selectedutest.get(i).get("oper_start_dt") )    	+"\",");	//시작일
		result.append("\"" + ( selectedutest.get(i).get("oper_end_dt")			== null ? "" : selectedutest.get(i).get("oper_end_dt") )  		+"\",");	//종료일
		result.append("\"" + ( selectedutest.get(i).get("end_dt")				== null ? "" : selectedutest.get(i).get("end_dt") )      		+"\",");	//접수마감일
		result.append("\"" + ( selectedutest.get(i).get("finish_date")			== null ? "" : selectedutest.get(i).get("finish_date")) 		+"\",");	//이수일수	
		result.append("\"" + ( selectedutest.get(i).get("finish_point")	   		== null ? "" : selectedutest.get(i).get("finish_point") )     	+"\",");	//점수
		result.append("\"" + ( selectedutest.get(i).get("remark")	    		== null ? "" : URLEncoder.encode(selectedutest.get(i).get("remark").toString(), "UTF-8") )     		+"\",");	//비고
		
		result.append("\"" + ( selectedutest.get(i).get("code_certifi")	    	== null ? "" : selectedutest.get(i).get("code_certifi") )   	+"\",");	//교육및시험구분(key)
		result.append("\"" + ( selectedutest.get(i).get("finish_time")	    	== null ? "" : selectedutest.get(i).get("finish_time") )     	+"\",");	//이수시간
		result.append("\"" + ( selectedutest.get(i).get("outside_yn")	    	== null ? "" : selectedutest.get(i).get("outside_yn") )    		+"\",");	//외부교육여부		
		result.append("\"" + ( selectedutest.get(i).get("edu_marks")	    	== null ? "" : selectedutest.get(i).get("edu_marks") )    		+"\",");	//평점		
		result.append("\"" + ( selectedutest.get(i).get("print_kind")			== null ? "" : selectedutest.get(i).get("print_kind") ) 		+"\",");	//수료증관련여부
		result.append("\"" + ( selectedutest.get(i).get("point_manage")	    	== null ? "" : selectedutest.get(i).get("point_manage") )      	+"\",");	//평점관리구분
		result.append("\"" + ( selectedutest.get(i).get("season")	    		== null ? "" : selectedutest.get(i).get("season") )   			+"\",");	//학기
		result.append("\"" + ( selectedutest.get(i).get("operation_cnt")		== null ? "" : selectedutest.get(i).get("operation_cnt") )  	+"\",");	//횟수
		result.append("\"" + ( selectedutest.get(i).get("operation")	    	== null ? "" : selectedutest.get(i).get("operation") )       	+"\",");	//시행대상
		result.append("\"" + ( selectedutest.get(i).get("start_dt")	    		== null ? "" : selectedutest.get(i).get("start_dt") )      		+"\",");	//접수시작일
		result.append("\"" + ( selectedutest.get(i).get("account_end_dt")	    == null ? "" : selectedutest.get(i).get("account_end_dt") )     +"\",");	//결제마감일
		result.append("\"" + ( selectedutest.get(i).get("account_way")	    	== null ? "" : selectedutest.get(i).get("account_way") )     	+"\",");	//결제방법
		result.append("\"" + ( selectedutest.get(i).get("receipt_pers_cnt")	    == null ? "" : selectedutest.get(i).get("receipt_pers_cnt") )   +"\",");	//접수인원
		result.append("\"" + ( selectedutest.get(i).get("operation_place")	    == null ? "" : URLEncoder.encode(selectedutest.get(i).get("operation_place").toString(), "UTF-8") )    +"\",");	//시행장소
		
		
		result.append("\"" + ( selectedutest.get(i).get("code_seq")	    		== null ? "" : selectedutest.get(i).get("code_seq") )     		+"\",");	//순번(key)
		result.append("\"" + ( selectedutest.get(i).get("bran_seq")	    		== null ? "" : selectedutest.get(i).get("bran_seq") )     		+"\",");	//지부순번(key)
		result.append("\"" + ( selectedutest.get(i).get("code_bran")   			== null ? "" : selectedutest.get(i).get("code_bran") )   		+"\",");	//교육주최(key)
		result.append("\"" + ( selectedutest.get(i).get("code_kind")   			== null ? "" : selectedutest.get(i).get("code_kind") )   		+"\",");	//교육및시험종류 코드값
		result.append("\"" + ( selectedutest.get(i).get("bran_txt")   			== null ? "" : URLEncoder.encode(selectedutest.get(i).get("bran_txt").toString(), "UTF-8") )   		+"\",");	//제목첨부
		result.append("\"" + ( selectedutest.get(i).get("detcode")   			== null ? "" : selectedutest.get(i).get("detcode") )   			+"\",");	//교육및시험 코드값
		result.append("\"" + ( selectedutest.get(i).get("use_yn")   			== null ? "" : selectedutest.get(i).get("use_yn") )   			+"\",");	//사용여부
		result.append("\"" + ( selectedutest.get(i).get("oper_start_tm")   		== null ? "" : selectedutest.get(i).get("oper_start_tm") )   	+"\",");	//시행시간1
		result.append("\"" + ( selectedutest.get(i).get("oper_end_tm")   		== null ? "" : selectedutest.get(i).get("oper_end_tm") )   		+"\",");	//시행시간2
		
		result.append("\"" + ( selectedutest.get(i).get("new_cost")   			== null ? "" : selectedutest.get(i).get("new_cost") )   		+"\",");	//회원 신규비용
		result.append("\"" + ( selectedutest.get(i).get("new_cost_nomem")   	== null ? "" : selectedutest.get(i).get("new_cost_nomem") ) 	+"\",");	//비회원 신규비용
		result.append("\"" + ( selectedutest.get(i).get("re_cost")   			== null ? "" : selectedutest.get(i).get("re_cost") )   			+"\",");	//재시험비용
		result.append("\"" + ( selectedutest.get(i).get("mr_cost")   			== null ? "" : selectedutest.get(i).get("mr_cost") )   			+"\",");	//석사이상비용
		result.append("\"" + ( selectedutest.get(i).get("bank_name")   			== null ? "" : selectedutest.get(i).get("bank_name") )   		+"\",");	//은행명
		result.append("\"" + ( selectedutest.get(i).get("bank_acc")   			== null ? "" : selectedutest.get(i).get("bank_acc") )   		+"\",");	//계좌번호
		result.append("\"" + ( selectedutest.get(i).get("bank_acc_owner")   	== null ? "" : selectedutest.get(i).get("bank_acc_owner") )   	+"\",");		//예금주
		result.append("\"" + ( selectedutest.get(i).get("service_marks")   	== null ? "" : selectedutest.get(i).get("service_marks") )   	+"\",");		//봉사점수
		result.append("\"" + ( selectedutest.get(i).get("code_target")   	== null ? "" : selectedutest.get(i).get("code_target") )   	+"\",");		//교육대상
		result.append("\"" + ( selectedutest.get(i).get("bran_cardjoin_tp")   	== null ? "" : selectedutest.get(i).get("bran_cardjoin_tp") )   	+"\"");		//지부가맹여부

		result.append("]}");
//		System.out.println("asdfasdfasdfasdf = "+selectedutest.get(i).get("code_kind")   			== null ? "" : selectedutest.get(i).get("code_kind"));		
	}	

	result.append("]}");
	request.setAttribute("result",result);
}

	return (mapping.findForward("ajaxout"));
	
}
//교육및 시험등록 저장
public ActionForward edutestSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		String upin	  = request.getParameter("upin").toString();
		
		Map map = new HashMap();

		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류(key)
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
//		map.put("edutest_name1",        URLDecoder.decode(request.getParameter("edutest_name1"),"UTF-8"));		//내용
		
		map.put("oper_start_dt1",       request.getParameter("oper_start_dt1"));								//시행시작일
		map.put("oper_end_dt1",        	request.getParameter("oper_end_dt1"));									//시행종료일
		map.put("end_dt1",        		request.getParameter("end_dt1"));										//접수마감일		
		map.put("remark1",        		URLDecoder.decode(request.getParameter("remark1"),"UTF-8"));			//비고
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드(교육및시험구분)(key)		
		map.put("use_yn1",        		request.getParameter("use_yn1"));										//사용여부		
		map.put("season1",        		request.getParameter("season1"));										//기준학기
		map.put("operation_cnt1",       request.getParameter("operation_cnt1"));								//시행횟수
		map.put("operation1",        	request.getParameter("operation1"));									//시행대상
		map.put("start_dt1",        	request.getParameter("start_dt1"));										//접수시작일
		map.put("account_end_dt1",      request.getParameter("account_end_dt1"));								//결제마감일
		map.put("account_way1",        	request.getParameter("account_way1"));									//결제방법
		map.put("receipt_pers_cnt1",    request.getParameter("receipt_pers_cnt1"));								//접수인원
		map.put("operation_place1",     URLDecoder.decode(request.getParameter("operation_place1"),"UTF-8"));	//시행장소
		map.put("code_seq1",        	request.getParameter("code_seq1"));										//순번(key)
		map.put("bran_seq1",        	request.getParameter("bran_seq1"));										//지부순번
		map.put("g_name",        		g_name);										//등록자
		map.put("bran_txt1",        	URLDecoder.decode(request.getParameter("bran_txt1"),"UTF-8"));			//제목첨부
		//교육명 선택시 특정교육만 조회
		map.put("detcode1",        		request.getParameter("detcode1"));										//교육명키값
		map.put("oper_start_tm1",       request.getParameter("oper_start_tm1"));								//시행시간1
		map.put("oper_end_tm1",         request.getParameter("oper_end_tm1"));									//시행시간2

		map.put("finish_date1",        	request.getParameter("finish_date1"));									//이수일수
		map.put("finish_point1",        request.getParameter("finish_point1"));									//이수점수
		map.put("finish_time1",        	request.getParameter("finish_time1"));									//이수시간
		map.put("outside_yn1",        	request.getParameter("outside_yn1"));									//외부교육여부
		map.put("edu_marks1",        	request.getParameter("edu_marks1"));									//교육평점
		map.put("conform1",        		request.getParameter("conform1"));										//승인여부
		map.put("print_kind1",        	request.getParameter("print_kind1"));									//발급증서
		map.put("point_manage1",        request.getParameter("point_manage1"));									//평점관리
		map.put("edu_t_name", URLDecoder.decode(request.getParameter("edu_t_name"),"UTF-8"));

		map.put("new_cost1",        request.getParameter("new_cost1"));									//회원 신규비용
		map.put("new_cost_nomem1",        request.getParameter("new_cost_nomem1"));						//비회원 신규비용
		map.put("re_cost1",        request.getParameter("re_cost1"));										//재시험비용
		map.put("mr_cost1",        request.getParameter("mr_cost1"));										//석사이상비용
		map.put("bank_name1", URLDecoder.decode(request.getParameter("bank_name1"),"UTF-8"));				//은행명
		map.put("bank_acc1",        request.getParameter("bank_acc1"));									//계좌번호
		map.put("bank_acc_owner1", URLDecoder.decode(request.getParameter("bank_acc_owner1"),"UTF-8"));	//예금주
		map.put("service_marks1", request.getParameter("service_marks1"));	//봉사점수
		map.put("code_target1", request.getParameter("code_target1"));	//교육대상
		map.put("bran_cardjoin_tp1", request.getParameter("bran_cardjoin_tp1"));	//지부가맹여부

			educationDao dao = new educationDao(); 

		String errMsg="";
		if(!isTokenValid(request)){
			errMsg="이미 저장했습니다.";	
		}else{
			if (upin.equals("up")){
				System.out.println("지부순번 update="+request.getParameter("bran_seq1"));
				int updateedutest = dao.updateedutest(map);
			}else{//이부분이 문제임.ㅡㅡ
				System.out.println("지부순번 insert="+request.getParameter("bran_seq1"));
				
				List<Map> edutestinsert = dao.insertedutest(map);
			}
			resetToken(request);
			errMsg="저장했습니다.";
		}
		request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
		
		
		
		Map map1 = new HashMap();
		dao = new educationDao(); 
//		System.out.println("시험구분"+request.getParameter("code_certifi1"));

		if(request.getParameter("code_certifi1") != null||request.getParameter("code_kind1") != null){
			
			map1.put("code_certifi1",       request.getParameter("code_certifi1"));									//교육및시험구분
			map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육및시험종류
//			System.out.println("교육주최="+request.getParameter("code_kind1"));
//			2가지 조건에 맞는 교육명 출력
			List<Map> educationsend2 = dao.educationsend2(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
			JSONArray j_educationsend1=JSONArray.fromObject(educationsend2);
			request.setAttribute("educationsend2" , educationsend2);
			request.setAttribute("j_educationsend1" , j_educationsend1);
			request.setAttribute("edutake2", map);
			request.setAttribute("res", map);
		}
		return (mapping.findForward("edutestSend"));
		
}
	
//교육및 시험등록 삭제
public ActionForward edutestDel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		educationDao dao = new educationDao(); 

		if(request.getParameter("code_certifi1") != null&&request.getParameter("code_kind1") != null&&request.getParameter("code_seq1") != null
				&&request.getParameter("code_bran1") != null&&request.getParameter("bran_seq1") != null	&&("cocone".equals((String)session.getAttribute("G_ID")) || "minju51".equals((String)session.getAttribute("G_ID")) ) )
		{
			// 교육삭제
			Map map = new HashMap();
			map.put("code_kind1",        		request.getParameter("code_kind1"));										
			map.put("code_certifi1",       		request.getParameter("code_certifi1"));										
			map.put("code_seq1",        		request.getParameter("code_seq1"));										
			map.put("code_bran1",        		request.getParameter("code_bran1"));										
			map.put("bran_seq1",        		request.getParameter("bran_seq1"));
			
			String errMsg="";
			int deleteedutest = dao.deleteedutest(map);
			errMsg="삭제했습니다.";
			request.setAttribute("errMsg", URLEncoder.encode(errMsg,"UTF-8"));
		}

		Map map1 = new HashMap();
		if(request.getParameter("code_certifi1") != null||request.getParameter("code_kind1") != null){
			
			map1.put("code_certifi1",       request.getParameter("code_certifi1"));									//교육및시험구분
			map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육및시험종류
			map1.put("detcode1",        	request.getParameter("detcode1"));									//교육및시험종류
//			System.out.println("교육주최="+request.getParameter("code_kind1"));
//			2가지 조건에 맞는 교육명 출력
			List<Map> educationsend2 = dao.educationsend2(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
			JSONArray j_educationsend1=JSONArray.fromObject(educationsend2);
			request.setAttribute("educationsend2" , educationsend2);
			request.setAttribute("j_educationsend1" , j_educationsend1);
			request.setAttribute("edutake1", map1);
		}
		return (mapping.findForward("edutestSend"));
}


/*응시결과처리 3가지조건 충족 */	
public ActionForward examSend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	Map map = new HashMap();
	educationDao dao = new educationDao(); 

	if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null){
	
	map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
	map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
	map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
	
	/*3가지 조건에 맞는 교육명 출력*/
	List<Map> selectexam1 = dao.selectexam1(map);//selectexam커리를 타고 실행 넘어온값 selectexam list<map>객체에 집어 넣는다

	JSONArray j_educationsend3=JSONArray.fromObject(selectexam1);
	request.setAttribute("selectexam1" , selectexam1);
	
	request.setAttribute("j_educationsend3" , j_educationsend3);
	request.setAttribute("edutake4", map);
	
	}
	System.out.println("print_kind=>"+request.getParameter("print_kind")+"code_bran1=>"+request.getParameter("code_bran1")+"code_kind1=>"+request.getParameter("code_kind1"));
	return (mapping.findForward("examSend"));
}

/*응시결과처리 리스트*/
public ActionForward examSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	
	Map map = new HashMap();
	educationDao dao = new educationDao(); 
	
		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null
		||request.getParameter("edutest_name1") != null||request.getParameter("code_certifi1") != null||request.getParameter("oper_name1") != null
		||request.getParameter("oper_lic_no1") != null||request.getParameter("oper_hp1") != null||request.getParameter("oper_comp_name11") != null
		||request.getParameter("code_operation1") != null||request.getParameter("result_point1") != null||request.getParameter("attend_cnt1") != null
		||request.getParameter("time_cnt1") != null||request.getParameter("result_state1") != null||request.getParameter("oper_email1") != null
		||request.getParameter("oper_state1") != null||request.getParameter("result_no1") != null){
	
			
			/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		map.put("detcode1",        		request.getParameter("detcode1"));										//승인여부
		
	if(request.getParameter("cert").equals("Y")){
		map.put("cert",        	"Y");										
	}else{		
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
		map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
		map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
		map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
		map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
		map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
		map.put("result_point1",        request.getParameter("result_point1"));									//점수
		
		map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
		map.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
		map.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
		map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
		map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
		map.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
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
	List<Map> examcount=dao.examsendcnt(map); 
	
	ncount = ((Integer)(examcount.get(0).get("cnt"))).intValue();

	int ntotpage = (ncount/nrows)+1;
	

	List<Map> selectexam = dao.selectexam2(map);
	
	
	StringBuffer result = new StringBuffer();
			
	result.append("{\"page\":\""+	npage	+"\",");		
	result.append("\"total\":\"" + ntotpage+"\",");
	result.append("\"records\":\""+	ncount	+"\",");
	result.append("\"rows\":[");	
	
	
	for(int i=0; i<selectexam.size(); i++){
		/* 그리드에 출력하는 컬럼 순서*/
		if(i>0) result.append(",");
		result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
		result.append("\"" + ( selectexam.get(i).get("detcode")   			== null ? "" : selectexam.get(i).get("detcode") )   		+"\",");		//교육및시험 코드값
		result.append("\"" + ( selectexam.get(i).get("edutest_name")		== null ? "" : selectexam.get(i).get("edutest_name"))  	+"\",");   	//내용
		result.append("\"" + ( selectexam.get(i).get("oper_name")	    	== null ? "" : selectexam.get(i).get("oper_name") )    	+"\",");	//이름
		result.append("\"" + ( selectexam.get(i).get("result_no")			== null ? "" : selectexam.get(i).get("result_no") ) 		+"\",");	//발급번호
		result.append("\"" + ( selectexam.get(i).get("oper_lic_no")			== null ? "" : selectexam.get(i).get("oper_lic_no") )  	+"\",");	//면허번호
//		result.append("\"" + ( selectexam.get(i).get("oper_no")				== null ? "" : selectexam.get(i).get("oper_no") )      	+"\",");	//주민번호
		result.append("\"" + ( selectexam.get(i).get("oper_birth")				== null ? "" : selectexam.get(i).get("oper_birth") )      	+"\",");//생년월일
		result.append("\"" + ( selectexam.get(i).get("oper_hp")	   			== null ? "" : selectexam.get(i).get("oper_hp") )     	+"\",");	//핸드폰
		result.append("\"" + ( selectexam.get(i).get("operemail")	    	== null ? "" : selectexam.get(i).get("operemail") )    +"\",");		//이메일	
		result.append("\"" + ( selectexam.get(i).get("attend_cnt")	    	== null ? "" : selectexam.get(i).get("attend_cnt") )    +"\",");	//출석일수
		result.append("\"" + ( selectexam.get(i).get("time_cnt")	    	== null ? "" : selectexam.get(i).get("time_cnt") )     	+"\",");	//이수시간
		result.append("\"" + ( selectexam.get(i).get("result_point")		== null ? "" : selectexam.get(i).get("result_point") ) 		+"\",");	//점수
		
		result.append("\"" + ( selectexam.get(i).get("yyyy")	   			== null ? "" : selectexam.get(i).get("yyyy") )   	 		+"\",");	//교육년도
		result.append("\"" + ( selectexam.get(i).get("code_bran")	    	== null ? "" : selectexam.get(i).get("code_bran") )    		+"\",");	//교육주최(key)
		result.append("\"" + ( selectexam.get(i).get("code_kind")	    	== null ? "" : selectexam.get(i).get("code_kind") )      	+"\",");	//교육종류(key)
		result.append("\"" + ( selectexam.get(i).get("code_certifi")	    == null ? "" : selectexam.get(i).get("code_certifi") )   	+"\",");	//자격증구분(key)
		result.append("\"" + ( selectexam.get(i).get("code_operation")		== null ? "" : selectexam.get(i).get("code_operation") ) 	+"\",");	//대상자
		result.append("\"" + ( selectexam.get(i).get("oper_state")	    	== null ? "" : selectexam.get(i).get("oper_state") )      	+"\",");	//결제여부
		result.append("\"" + ( selectexam.get(i).get("oper_comp_name1")		== null ? "" : URLEncoder.encode(selectexam.get(i).get("oper_comp_name1").toString(), "UTF-8")) 	+"\",");	//근무처	
		result.append("\"" + ( selectexam.get(i).get("result_state")	    == null ? "" : selectexam.get(i).get("result_state") )     	+"\",");	//진행상태(코드값)
//		result.append("\"" + ( selectexam.get(i).get("result_no")			== null ? "" : selectexam.get(i).get("result_no") ) 		+"\",");	//발급번호
		
		result.append("\"" + ( selectexam.get(i).get("code_seq")	    	== null ? "" : selectexam.get(i).get("code_seq") )     		+"\",");	//순번(key)
		result.append("\"" + ( selectexam.get(i).get("bran_seq")	    	== null ? "" : selectexam.get(i).get("bran_seq") )     		+"\",");	//히든 지부순번(key)
		result.append("\"" + ( selectexam.get(i).get("receipt_no")	    	== null ? "" : selectexam.get(i).get("receipt_no") )      	+"\",");	//접수번호(key)
		result.append("\"" + ( selectexam.get(i).get("point_manage")	   	== null ? "" : selectexam.get(i).get("point_manage") )     	+"\",");	//점수관리자격증
		result.append("\"" + ( selectexam.get(i).get("register")	    	== null ? "" : selectexam.get(i).get("register") )     		+"\",");	//등록자
		result.append("\"" + ( selectexam.get(i).get("up_date")	    		== null ? "" : selectexam.get(i).get("up_date") )     		+"\",");	//수정일시
		result.append("\"" + ( selectexam.get(i).get("updater")	    		== null ? "" : selectexam.get(i).get("updater") )     		+"\",");		//수정자
		result.append("\"" + ( selectexam.get(i).get("pers_addr1")	   		== null ? "" : selectexam.get(i).get("pers_addr1") )     	+"\",");		     //히든 주소 )
		result.append("\"" + ( selectexam.get(i).get("code_pers")	   		== null ? "" : selectexam.get(i).get("code_pers") )     	+"\"");		     //히든 주소 )
		result.append("]}");
		
	}	

	result.append("]}");
	request.setAttribute("result",result);
}

	return (mapping.findForward("ajaxout"));
}




//응시현황처리 엑셀 업로드
public ActionForward upEducationSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
throws Exception {
return mapping.findForward("upEducationSendListr");
}
public ActionForward educationSendListUpExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setCharacterEncoding("UTF-8");
	Map map=new HashMap();
	Map map1=new HashMap();
	educationDao dao=new educationDao();
	List<Map> code=dao.sCodeExcel(map);
	
	HttpSession session=request.getSession();
	String g_name = session.getAttribute("G_NAME").toString(); 
	
//	String saveFolder="D:\\KDA_WORKSPACE\\kda\\WebContent\\upload\\excel\\";
	String saveFolder="D:\\WEB\\KDA_VER3\\upload\\excel\\";
	String encType="UTF-8";
	int maxSize=10*1024*1024;
	File f=null;
	MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
	String filename="";
	
	Workbook openWorkbook;
	Sheet openSheet;
	String sheetName = "";
	Cell openCell;

	try{
		Enumeration files=multi.getFileNames();
		while(files.hasMoreElements()){
			String name=(String)files.nextElement();
			filename=multi.getFilesystemName(name);
			f=new File(saveFolder+"/"+filename);
			
			// 엑셀을 불러 들어 온다.
			openWorkbook = Workbook.getWorkbook(f);
			//엑셀의 첫번째 시트 인덱스(0)을 얻어 온다.
			openSheet = openWorkbook.getSheet(0);
			  
			int iCols = openSheet.getColumns();
			int iRows = openSheet.getRows();

			String sResultString =  "";
			
			int cellLen = 0;
			int ins=0;
			int upd=0;
			for(int i=1; i < iRows; i++){
				Cell cell[] = openSheet.getRow(i);
			    cellLen = cell.length;			    
			    System.out.println("cellLen=======>"+cellLen);
			    System.out.println("cellLen=======>"+cell[1].getContents().substring(0, 1));
			    System.out.println("cellLen=======>"+cell[1].getContents().substring(1, 2));
			    System.out.println("cellLen=======>"+cell[1].getContents().substring(2, 6));
			    System.out.println("cellLen=======>"+cell[1].getContents().substring(6, 8));
			    System.out.println("cellLen=======>"+cell[1].getContents().substring(8, 11));
			    //수정이나 입력인 경우에만 처리하자
				if(!"".equals(cell[1].getContents())&&("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())||"i".equals(cell[0].getContents())||"I".equals(cell[0].getContents()))){
			    				
				    map.put("yyyy1",        		cell[3].getContents());		//교육년도
				    map.put("code_bran1",        	cell[1].getContents().substring(6, 8));		//교육주최(key)
				    map.put("code_kind1",        	cell[1].getContents().substring(0, 1));		//교육종류(key)
				    map.put("edutest_name1",        cell[1].getContents());		//내용
				    map.put("code_certifi1",        cell[1].getContents().substring(1, 2));				//구분코드(교육및시험구분)(key)				    
				    map.put("code_operation1",      getDetcode(code,"035",cell[34].getContents()));		//대상자					
					map.put("code_pay_flag1",       getDetcode(code,"014",cell[41].getContents()));		//결제방법
//					map.put("oper_no1",       		cell[9].getContents());								//주민번호 // JUMIN_DEL	
					map.put("oper_birth1",       		cell[9].getContents());								//생년월일	
					map.put("oper_state1",       	getDetcode(code,"022",cell[38].getContents()));		//진행상태
					map.put("receipt_no1",       	cell[2].getContents());								//접수번호
					map.put("bran_seq1",       		cell[1].getContents().substring(8, 11));				//지부순번
					map.put("code_seq1",       		cell[1].getContents().substring(2, 6));				//순번
					map.put("register1",       		g_name);											//등록자
					map.put("comp_add111",       	cell[17].getContents());							// 지역 1 --16
					map.put("comp_name21",       	cell[19].getContents());							// 근무처2 --18
					map.put("comp_add211",       	cell[20].getContents());	// 지역2 --19
					map.put("comp_add221",       	cell[21].getContents());	// 근무처주소2 --20
					map.put("comp_name31",       	cell[22].getContents());	// 근무처3 --21
					map.put("comp_add311",       	cell[23].getContents());	// 근지역3 --22
					map.put("comp_add321",       	cell[24].getContents());	// 근무처주소3 --23
					map.put("comp_name41",       	cell[25].getContents());	// 근무처4 --24
					map.put("comp_add411",       	cell[26].getContents());	// 지역4 --25
					map.put("comp_add421",       	cell[27].getContents());	// 근무처주소4 --26
					map.put("comp_name51",       	cell[28].getContents());	// 근무처5 --27
					map.put("comp_add511",       	cell[29].getContents());	// 지역5 --28
					map.put("comp_add521",       	cell[30].getContents());	// 근무처주소5 --29					
					map.put("receipt_dt1",       	cell[39].getContents());	// 신청일 --38
					map.put("notice1",       		cell[42].getContents());	// 통지번호 --41
					
					/*--20140307 modified by cocone
				    //주민등록번호로 회원정보 읽어오기
					List<Map> selectpersonmtbl = dao.selectpersonmtbl(map);
					
					//person_m_tbl에 정보가 있는 경우
					System.out.println("1. selectpersonmtbl.size()===>"+selectpersonmtbl.size());				
					if ( selectpersonmtbl.size() > 0) { 
						//pers_state가 01이나 07인경우 회원
						System.out.println("2. pers_state===>"+selectpersonmtbl.get(0).get("pers_state").toString());
						if(selectpersonmtbl.get(0).get("pers_state").toString().equals("01")||selectpersonmtbl.get(0).get("pers_state").toString().equals("07")){ 
							//code_kind=4 위생교육인 경우
							System.out.println("3. code_kind===>"+cell[1].getContents().substring(0, 1));
							if ( "4".equals(cell[1].getContents().substring(0, 1)) ) {
								map.put("person_yn1",       	( selectpersonmtbl.get(0).get("code_pers")	== null ? "" : selectpersonmtbl.get(0).get("code_pers")) );		//회원코드
								List<Map> chkTestListCount2 =dao.ChkTestListCount2(map);
								System.out.println("4. chkTestListCount2 Count===>"+chkTestListCount2.get(0).get("cnt").toString());
								if(chkTestListCount2.get(0).get("cnt").toString().equals("0")){
									map.put("oper_name1",        	cell[8].getContents());								//이름
									map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
									map.put("oper_hp1",       		cell[13].getContents());							//핸드폰
									map.put("oper_email1",       	cell[14].getContents() );			//email
									map.put("oper_comp_name11",     cell[15].getContents());							//근무처명
									map.put("code_post1",       	cell[16].getContents() );		//우편번호
									map.put("oper_comp_add1_21",    cell[18].getContents() );		//주소
									map.put("trust_name1",       	cell[33].getContents() );	//위탁업체명
									map.put("company_tel1",       	cell[12].getContents() );		//전화번호
								}else{
									System.out.println("5. 이미 등록된 위생교육 있음");
									continue; //이미 등록된 위생교육이 있으면 건너뛴다.
								}													
							} else {
								System.out.println("3-1. 위생교육아님");
								map.put("person_yn1",       	( selectpersonmtbl.get(0).get("code_pers")	== null ? "" : selectpersonmtbl.get(0).get("code_pers")) );		//회원코드
								
								map.put("oper_name1",        	( selectpersonmtbl.get(0).get("pers_name")	== null ? "" : selectpersonmtbl.get(0).get("pers_name")) );		//이름
								map.put("oper_lic_no1",       	( selectpersonmtbl.get(0).get("lic_no")		== null ? "" : selectpersonmtbl.get(0).get("lic_no")) );		//면허번호
								map.put("oper_hp1",       		( selectpersonmtbl.get(0).get("pers_hp")	== null ? "" : selectpersonmtbl.get(0).get("pers_hp")) );		//핸드폰
								map.put("oper_email1",       	( selectpersonmtbl.get(0).get("email")		== null ? "" : selectpersonmtbl.get(0).get("email")) );			//email
								map.put("oper_comp_name11",     ( selectpersonmtbl.get(0).get("company_name")	== null ? "" : selectpersonmtbl.get(0).get("company_name")) );		//근무처명
								map.put("code_post1",       	( selectpersonmtbl.get(0).get("code_post")	== null ? "" : selectpersonmtbl.get(0).get("code_post")) );		//우편번호
								map.put("oper_comp_add1_21",    ( selectpersonmtbl.get(0).get("comp_add")	== null ? "" : selectpersonmtbl.get(0).get("comp_add")) );		//주소
								map.put("person_yn1",       	( selectpersonmtbl.get(0).get("code_pers")	== null ? "" : selectpersonmtbl.get(0).get("code_pers")) );		//회원코드
								map.put("code_use1",       		( selectpersonmtbl.get(0).get("code_use")	== null ? "" : selectpersonmtbl.get(0).get("code_use")) );		//운영형태
								map.put("trust_name1",       	( selectpersonmtbl.get(0).get("trust_name")	== null ? "" : selectpersonmtbl.get(0).get("trust_name")) );	//위탁업체명
								map.put("code_great1",       	( selectpersonmtbl.get(0).get("code_great")	== null ? "" : selectpersonmtbl.get(0).get("code_great")) );	//근무처대분류
								map.put("company_tel1",       	( selectpersonmtbl.get(0).get("company_tel")	== null ? "" : selectpersonmtbl.get(0).get("company_tel")) );		//전화번호
							}
						}else{
							System.out.println("2-1. pers_state가 01 혹은 07 아님. 비회원!");
							map.put("person_yn1",       	"" );		//회원코드
							
							map.put("oper_name1",        	cell[8].getContents());								//이름
							map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
							map.put("oper_hp1",       		cell[13].getContents());							//핸드폰
							map.put("oper_email1",       	cell[14].getContents() );			//email
							map.put("oper_comp_name11",     cell[15].getContents());							//근무처명
							map.put("code_post1",       	cell[16].getContents() );		//우편번호
							map.put("oper_comp_add1_21",    cell[18].getContents() );		//주소
							map.put("trust_name1",       	cell[33].getContents() );	//위탁업체명
							map.put("company_tel1",       	cell[12].getContents() );		//전화번호
						}
					} else { //비회원인 경우
						System.out.println("비회원");
						if ( "4".equals(cell[1].getContents().substring(0, 1)) ) { //위생교육일때
							List<Map> chkTestListCount =dao.ChkTestListCount(map); //동일년도 위생교육 등록 or 수료한 상태인지 체크							
							System.out.println("4. chkTestListCount Count===>"+chkTestListCount.get(0).get("cnt").toString());
							if(chkTestListCount.get(0).get("cnt").toString().equals("0")){
								map.put("person_yn1",       	"" );		//회원코드
								
								map.put("oper_name1",        	cell[8].getContents());								//이름
								map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
								map.put("oper_hp1",       		cell[13].getContents());							//핸드폰
								map.put("oper_email1",       	cell[14].getContents() );			//email
								map.put("oper_comp_name11",     cell[15].getContents());							//근무처명
								map.put("code_post1",       	cell[16].getContents() );		//우편번호
								map.put("oper_comp_add1_21",    cell[18].getContents() );		//주소
								map.put("trust_name1",       	cell[33].getContents() );	//위탁업체명
								map.put("company_tel1",       	cell[12].getContents() );		//전화번호
							}else{
								System.out.println("5. 이미 등록된 위생교육 있음");
								continue; //이미 등록된 위생교육이 있으면 건너뛴다.
							}
						}else{ //위생교육이 아닐때
							System.out.println("3-2. 위생교육아님");
							map.put("person_yn1",       	"" );		//회원코드
							
							map.put("oper_name1",        	cell[8].getContents());								//이름
							map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
							map.put("oper_hp1",       		cell[13].getContents());							//핸드폰
							map.put("oper_email1",       	cell[14].getContents() );			//email
							map.put("oper_comp_name11",     cell[15].getContents());							//근무처명
							map.put("code_post1",       	cell[16].getContents() );		//우편번호
							map.put("oper_comp_add1_21",    cell[18].getContents() );		//주소
							map.put("trust_name1",       	cell[33].getContents() );	//위탁업체명
							map.put("company_tel1",       	cell[12].getContents() );		//전화번호
						}						
					}
					till cocone */ 
					
					//2014.03.10 교육국 요청사항
					//위생교육의 경우 회원, 비회원 상관없이 사용자 입력내용 그대로 물고 가야함. (회원의 경우 상태 변경 시 회원정보 물고 와서 넣으면 안됨)
					map.put("oper_name1",        	cell[8].getContents());
					List<Map> selectpersonmtbl = dao.selectpersonmtblnew(map,true); // BIRTH_CHECK
					//System.out.println("1. selectpersonmtbl.size()===>"+selectpersonmtbl.size());
					if ( selectpersonmtbl.size() > 0) {  //1. 회원테이블에 있는지 없는지 체크
						//System.out.println("2. pers_state===>"+selectpersonmtbl.get(0).get("pers_state").toString());
						if(selectpersonmtbl.get(0).get("pers_state").toString().equals("01")||selectpersonmtbl.get(0).get("pers_state").toString().equals("07")){ //2.pers_state가 01 or 07이면 회원
							//System.out.println("3-1. 회원");
							if ( "4".equals(cell[1].getContents().substring(0, 1)) ) { //위생교육인 경우 입력받은 값 그대로
								map.put("person_yn1",       	( selectpersonmtbl.get(0).get("code_pers")		== null ? "" : selectpersonmtbl.get(0).get("code_pers")) );		//회원코드							
								map.put("oper_name1",        	cell[8].getContents());								//이름
								map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
								map.put("oper_hp1",       		cell[13].getContents());							//핸드폰
								map.put("oper_email1",       	cell[14].getContents() );			//email
								map.put("oper_comp_name11",     cell[15].getContents());							//근무처명
								map.put("code_post1",       	cell[16].getContents() );		//우편번호
								map.put("oper_comp_add1_21",    cell[18].getContents() );		//주소
								map.put("code_use1",       		getDetcode(code,"001",cell[32].getContents()));	// 운영형태 --31
								map.put("trust_name1",       	cell[33].getContents() );	//위탁업체명
								map.put("code_great1",       	getDetcode(code,"043",cell[31].getContents()));
								map.put("company_tel1",       	cell[12].getContents() );		//전화번호
							}else{
								map.put("person_yn1",       	( selectpersonmtbl.get(0).get("code_pers")		== null ? "" : selectpersonmtbl.get(0).get("code_pers")) );		//회원코드							
								map.put("oper_name1",        	( selectpersonmtbl.get(0).get("pers_name")		== null ? "" : selectpersonmtbl.get(0).get("pers_name")) );		//이름
								map.put("oper_lic_no1",       	( selectpersonmtbl.get(0).get("lic_no")			== null ? "" : selectpersonmtbl.get(0).get("lic_no")) );		//면허번호
								map.put("oper_hp1",       		( selectpersonmtbl.get(0).get("pers_hp")		== null ? "" : selectpersonmtbl.get(0).get("pers_hp")) );		//핸드폰
								map.put("oper_email1",       	( selectpersonmtbl.get(0).get("email")			== null ? "" : selectpersonmtbl.get(0).get("email")) );			//email
								map.put("oper_comp_name11",     ( selectpersonmtbl.get(0).get("company_name")	== null ? "" : selectpersonmtbl.get(0).get("company_name")) );		//근무처명
								map.put("code_post1",       	( selectpersonmtbl.get(0).get("code_post")		== null ? "" : selectpersonmtbl.get(0).get("code_post")) );		//우편번호
								map.put("oper_comp_add1_21",    ( selectpersonmtbl.get(0).get("comp_add")		== null ? "" : selectpersonmtbl.get(0).get("comp_add")) );		//주소							
								map.put("code_use1",       		( selectpersonmtbl.get(0).get("code_use")		== null ? "" : selectpersonmtbl.get(0).get("code_use")) );		//운영형태
								map.put("trust_name1",       	( selectpersonmtbl.get(0).get("trust_name")		== null ? "" : selectpersonmtbl.get(0).get("trust_name")) );	//위탁업체명
								map.put("code_great1",       	( selectpersonmtbl.get(0).get("code_great")		== null ? "" : selectpersonmtbl.get(0).get("code_great")) );	//근무처대분류
								map.put("company_tel1",       	( selectpersonmtbl.get(0).get("company_tel")	== null ? "" : selectpersonmtbl.get(0).get("company_tel")) );		//전화번호
							}							
						}else{ //2-1. pers_state가 01 or 07이 아니므로 비회원
							//System.out.println("3-2. 비회원");
							map.put("person_yn1",       	"" );		//회원코드							
							map.put("oper_name1",        	cell[8].getContents());								//이름
							map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
							map.put("oper_hp1",       		cell[13].getContents());							//핸드폰
							map.put("oper_email1",       	cell[14].getContents() );			//email
							map.put("oper_comp_name11",     cell[15].getContents());							//근무처명
							map.put("code_post1",       	cell[16].getContents() );		//우편번호
							map.put("oper_comp_add1_21",    cell[18].getContents() );		//주소
							map.put("code_use1",       		getDetcode(code,"001",cell[32].getContents()));	// 운영형태 --31
							map.put("trust_name1",       	cell[33].getContents() );	//위탁업체명
							if ( "4".equals(cell[1].getContents().substring(0, 1)) ) 
								map.put("code_great1",       	getDetcode(code,"043",cell[31].getContents()));	// 근무처대분류 --30
							else
								map.put("code_great1",       	getDetcode(code,"004",cell[31].getContents()));	// 근무처대분류 --30
							map.put("company_tel1",       	cell[12].getContents() );		//전화번호														
						}
					}else{ //3. 회원테이블에 없으므로 비회원
						//System.out.println("3-3. 비회원");
						map.put("person_yn1",       	"" );		//회원코드						
						map.put("oper_name1",        	cell[8].getContents());								//이름
						map.put("oper_lic_no1",       	cell[10].getContents());							//면허번호
						map.put("oper_hp1",       		cell[13].getContents());							//핸드폰
						map.put("oper_email1",       	cell[14].getContents() );			//email
						map.put("oper_comp_name11",     cell[15].getContents());							//근무처명
						map.put("code_post1",       	cell[16].getContents() );		//우편번호
						map.put("oper_comp_add1_21",    cell[18].getContents() );		//주소
						map.put("code_use1",       		getDetcode(code,"001",cell[32].getContents()));	// 운영형태 --31
						map.put("trust_name1",       	cell[33].getContents() );	//위탁업체명
						if ( "4".equals(cell[1].getContents().substring(0, 1)) ) 
							map.put("code_great1",       	getDetcode(code,"043",cell[31].getContents()));	// 근무처대분류 --30
						else
							map.put("code_great1",       	getDetcode(code,"004",cell[31].getContents()));	// 근무처대분류 --30
						map.put("company_tel1",       	cell[12].getContents() );		//전화번호
					}
					
					/*System.out.println("접수번호="+cell[2].getContents());
					System.out.println("회원코드:		"+map.get("person_yn1"));
					System.out.println("이름:		"+map.get("oper_name1"));
					System.out.println("교육주최:	"+cell[1].getContents().substring(5, 7));
					System.out.println("교육종류:	"+cell[1].getContents().substring(0, 1));
					System.out.println("근무처대분류:	"+cell[31].getContents()+", "+map.get("code_great1"));
					System.out.println("운영형태:	"+cell[32].getContents()+", "+map.get("code_use1"));*/
	
					//교육코드와 주민등록번호로 동일인 동일교육 여부를 확인하여 입력인 경우는 PASS 수정은 처리
					//위생교육의 경우는 교육지역이 달라도 처리 안되게 해야 함
					if ( "4".equals(cell[1].getContents().substring(0, 1)) ) { //case1. 위생교육
							List<Map> selectoperater2 = dao.selectoperater2(map); // BIRTH_CHECK
							if("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())){
								//System.out.println("selectoperater2.size()========> "+selectoperater2.size());
								if ( selectoperater2.size() > 0 ) {
//										JUMIN_DEL
//									  if ("".equals(selectoperater2.get(0).get("edu_oper_no"))){
									  if ("".equals(selectoperater2.get(0).get("edu_oper_name")) && "".equals(selectoperater2.get(0).get("edu_oper_birth")) ){
											map.put("receipt_no1",       	( selectoperater2.get(0).get("receipt_no")	== null ? "" : selectoperater2.get(0).get("receipt_no")) );		//접수번호
						//							System.out.println("접수번호 up="+selectoperater.get(0).get("receipt_no"));
						
												try {
													int updateeducation = dao.updateeducation(map); // BIRTH_CHECK
												} catch (Exception e) {
													sResultString = sResultString + (i+1) +"번라인 수정중 에러\\n"; // +  (char) 13;
												}
									  } else {
											sResultString = sResultString + (i+1) +"번라인 타지회 등록 에러\\n"; // +  (char) 13;
									  }
								} else {
									sResultString = sResultString + (i+1) +"번라인 없는 데이터 수정 에러\\n"; // +  (char) 13;
								}
								upd++;
							} else if("i".equals(cell[0].getContents())||"I".equals(cell[0].getContents())){
								if ( selectoperater2.size() <= 0 ) {
			
										map.put("receipt_no1",       	"" );		//접수번호
			//							System.out.println("접수번호 in=");
										try {
											List<Map> educationinsert = dao.inserteducation(map);
										} catch (Exception e) {
											sResultString = sResultString + (i+1) +"번라인 입력중 에러\\n"; //+ (char) 13;
										}
								} else {
//									  if ("".equals(selectoperater2.get(0).get("edu_oper_no"))){ // JUMIN_DEL
									  if ("".equals(selectoperater2.get(0).get("edu_oper_name")) && "".equals(selectoperater2.get(0).get("edu_oper_birth")) ){
//										  sResultString = sResultString + (i+1) +"번라인 (주민번호 = "+selectoperater2.get(0).get("edu_oper_no")+") 입력중 중복에러\\n";
										  sResultString = sResultString + (i+1) +"번라인 (이름 = "+selectoperater2.get(0).get("edu_oper_name")+", 생년월일 = "+selectoperater2.get(0).get("edu_oper_birth")+") 입력중 중복에러\\n";
									  } else {
//										  sResultString = sResultString + (i+1) +"번라인 (주민번호 = "+selectoperater2.get(0).get("edu_oper_no")+") 입력중 타지역 중복에러\\n";
										  sResultString = sResultString + (i+1) +"번라인 (이름 = "+selectoperater2.get(0).get("edu_oper_name")+", 생년월일 = "+selectoperater2.get(0).get("edu_oper_birth")+") 입력중 타지역 중복에러\\n";
									  }
								}
								ins++;
							}
					} else { //case2. 위생교육 아닐
						List<Map> selectoperater = dao.selectoperater(map); // BIRTH_CHECK
						if("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())){
							
							if ( selectoperater.size() > 0 ) {
								map.put("receipt_no1",       	( selectoperater.get(0).get("receipt_no")	== null ? "" : selectoperater.get(0).get("receipt_no")) );		//접수번호
		//							System.out.println("접수번호 up="+selectoperater.get(0).get("receipt_no"));
		
								try {
									int updateeducation = dao.updateeducation(map); // BIRTH_CHECK
								} catch (Exception e) {
									sResultString = sResultString + (i+1) +"번라인 수정중 에러\\n"; // +  (char) 13;
								}
									
							}
							upd++;
						} else if("i".equals(cell[0].getContents())||"I".equals(cell[0].getContents())){
							if ( selectoperater.size() <= 0 ) {
		
									map.put("receipt_no1",       	"" );		//접수번호
		//							System.out.println("접수번호 in=");
									try {
										List<Map> educationinsert = dao.inserteducation(map);
									} catch (Exception e) {
										sResultString = sResultString + (i+1) +"번라인 입력중 에러\\n"; //+ (char) 13;
									}
							} else {
								sResultString = sResultString + (i+1) +"번라인 입력중 중복에러\\n";
								
							}
							ins++;
						}
					}
				}
			
				if(sResultString.length()<5) sResultString="업로드가 완료됐습니다.";
				map.clear();
			}  //for
			request.setAttribute("sResultString", sResultString);
			openWorkbook.close();
			
		}   //while
	}catch(Exception e){   //try
		e.printStackTrace();
	}
	return (mapping.findForward("excelLink"));
}  //Exception

//응시결과처리 엑셀 업로드
public ActionForward upExamSendList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	return mapping.findForward("upExamSendListr");
}

public ActionForward examSendListUpExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setCharacterEncoding("UTF-8");
	Map map=new HashMap();
	educationDao dao=new educationDao();
	List<Map> code=dao.sCodeExcel(map);
	
	HttpSession session=request.getSession();
	String g_name = session.getAttribute("G_NAME").toString(); 
	
//	String saveFolder="D:\\KDA_WORKSPACE\\kda\\WebContent\\upload\\excel\\";
	String saveFolder="D:\\WEB\\KDA_VER3\\upload\\excel\\";
	String encType="UTF-8";
	int maxSize=10*1024*1024;
	File f=null;
	MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType);
	String filename="";
	
	Workbook openWorkbook;
	Sheet openSheet;
	String sheetName = "";
	Cell openCell;

	try{
		Enumeration files=multi.getFileNames();
		while(files.hasMoreElements()){
			String name=(String)files.nextElement();
			filename=multi.getFilesystemName(name);
			f=new File(saveFolder+"/"+filename);
			
			// 엑셀을 불러 들어 온다.
			openWorkbook = Workbook.getWorkbook(f);
			//엑셀의 첫번째 시트 인덱스(0)을 얻어 온다.
			openSheet = openWorkbook.getSheet(0);
			  
			int iCols = openSheet.getColumns();
			int iRows = openSheet.getRows();
			  
			String sResultString = "";
			int cellLen = 0;
			int ins=0;
			int upd=0;
			for(int i=1; i < iRows; i++){
				Cell cell[] = openSheet.getRow(i);
			    cellLen = cell.length;
			    System.out.println("cell[1].getContents()=======>"+cell[1].getContents());
			    //수정이나 입력인 경우에만 처리하자
				if(!"".equals(cell[1].getContents())&&("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())||"i".equals(cell[0].getContents())||"I".equals(cell[0].getContents()))){
				    map.put("yyyy1",        		cell[28].getContents());		//교육년도				    
				    map.put("code_bran1",        	cell[1].getContents().substring(6, 8));		//교육주최(key)
				    map.put("code_kind1",        	cell[1].getContents().substring(0, 1));		//교육종류(key)
				    map.put("detcode1",        		cell[1].getContents());		//내용
				    map.put("code_certifi1",        cell[1].getContents().substring(1, 2));		//구분코드(교육및시험구분)(key)
	
				    map.put("result_point1",        cell[7].getContents());		//점수
				    map.put("attend_cnt1",        	cell[5].getContents());		//출석일수
				    map.put("time_cnt1",        	cell[6].getContents());		//이수시간
				    map.put("receipt_no1",        	cell[3].getContents());		//접수번호				    
				    map.put("bran_seq1",        	cell[1].getContents().substring(8, 11));		//지부순번(key)
				    map.put("code_seq1",        	cell[1].getContents().substring(2, 6));		//순번(key)
				    map.put("g_name",        		g_name);						//등록자
	
				    //교육및시험명 테이블에서 관리점수항목 불러오기
				    List<Map> selectexampoint = dao.selectexampoint(map);
				    
				    int v_edu_marks = Integer.parseInt(selectexampoint.get(0).get("edu_marks").toString());
				    int v_service_marks = Integer.parseInt(selectexampoint.get(0).get("service_marks").toString());
				    int v_finish_time = Integer.parseInt(selectexampoint.get(0).get("finish_time").toString());
				    int v_finish_date = Integer.parseInt(selectexampoint.get(0).get("finish_date").toString());
				    int v_finish_point = Integer.parseInt(selectexampoint.get(0).get("finish_point").toString());
				    String point_manage=selectexampoint.get(0).get("point_manage").toString();
				    
				    Map map2=new HashMap();
				    map2.put("pers_name", cell[4].getContents());
//				    JUMIN_DEL
//				    map2.put("pers_no", cell[10].getContents());
				    map2.put("pers_birth", cell[10].getContents());
				    map2.put("lic_no", cell[9].getContents());				    
				    
				    
				    List<Map> selCode_pers=dao.selCode_pers1(map2);

				    HashMap map3=new HashMap();
					List<Map> selCertifi=new ArrayList();
					if(selCode_pers.size()>0){
						map3.put("code_pers", selCode_pers.get(0).get("code_pers"));
						map3.put("oper_end_dt", selectexampoint.get(0).get("oper_end_dt").toString());
						selCertifi=dao.selCertifi(map3);
					}
					
					int pmanage=comparePmanage(point_manage,selCertifi);
					
					System.out.println("pmanage===========>"+pmanage);
				    if ( 
				    		(v_finish_point <= Integer.parseInt(StringUtil.nullToStr("0", cell[7].getContents())))&&
				    		(v_finish_date <= Integer.parseInt(StringUtil.nullToStr("0", cell[5].getContents())))&&
				    		(v_finish_time <= Integer.parseInt(StringUtil.nullToStr("0", cell[6].getContents())))
				       )
				    {
				    	if(selCode_pers.size()==0||pmanage==0){
				    		map.put("edu_marks1",       v_edu_marks);										//이수점수
							map.put("service_marks1",   v_service_marks);
							map.put("result_state1",    "10");	
							map.put("point_manage1", 	"0");							
						}else if(pmanage>0 && pmanage<6){
							map.put("edu_marks1",       v_edu_marks);										//이수점수
							map.put("service_marks1",   v_service_marks);
							map.put("result_state1",    "10");	
							map.put("point_manage1", 	pmanage);
						}else if(pmanage==10){
							map.put("edu_marks1",       v_edu_marks);										//이수점수
							map.put("service_marks1",   v_service_marks);
							map.put("result_state1",    "10");	
							map.put("point_manage1", 	"0");
						}
				    	
				    }else{
				    	if ( (v_finish_point > Integer.parseInt(StringUtil.nullToStr("0", cell[7].getContents())))
				    	){
				    		map.put("edu_marks1",        		"0");										//이수점수
							map.put("service_marks1",        	"0");									//봉사점수
							map.put("result_state1",        "02");					//상태 (시험성적미달)
							if(selCode_pers.size()==0||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
							else map.put("point_manage1", 	pmanage);
				    		
				    	}else if ( (v_finish_date > Integer.parseInt(StringUtil.nullToStr("0", cell[5].getContents())))
				    	){
				    		map.put("edu_marks1",        		"0");										//이수점수
							map.put("service_marks1",        	"0");									//봉사점수
							map.put("result_state1",        "03");					//상태 (출석일수미달)
							if(selCode_pers.size()==0||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
							else map.put("point_manage1", 	pmanage);
				    		
				    	} else	if ( (v_finish_time > Integer.parseInt(StringUtil.nullToStr("0", cell[6].getContents())))
				    	){
				    		map.put("edu_marks1",        		"0");										//이수점수
							map.put("service_marks1",        	"0");									//봉사점수
							map.put("result_state1",        "03");					//상태 (출석일수미달)
							if(selCode_pers.size()==0||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
							else map.put("point_manage1", 	pmanage);
				    		
				    	} else {
				    		map.put("edu_marks1",        		"0");										//이수점수
							map.put("service_marks1",        	"0");									//봉사점수
							map.put("result_state1",        "04");					//상태 (미이수)
							if(selCode_pers.size()==0||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
							else map.put("point_manage1", 	pmanage);
				    		
				    	}
				    }  //end if
				    
				    //2014.03.20 result_state=10 (이수)인 경우에 result_no를 읽어온다.
				    if(map.get("result_state1").toString().equals("10")){
				    	map.put("result_no1",        cell[8].getContents());
				    }else{
				    	map.put("result_no1",        "");
				    }
				    
				    if("u".equals(cell[0].getContents())||"U".equals(cell[0].getContents())){
						try {
							int updateexame = dao.updateexame(map);
							if ( updateexame < 1){
								sResultString = sResultString + (i+1) +"번라인 데이터가 없습니다."; // +  (char) 13;
							}
						} catch (Exception e) {
							sResultString = sResultString + (i+1) +"번라인 수정중 에러"; // +  (char) 13;
						}
						upd++;
					}else if("i".equals(cell[0].getContents())||"I".equals(cell[0].getContents())){
						try {
							int deletexam = dao.deletexam(map);

							List<Map> examinsert = dao.insertexam(map);
						} catch (Exception e) {
							sResultString = sResultString + (i+1) +"번라인 입력중 에러"; //+ (char) 13;
						}
						ins++;
					}
				}

			}  //for
			
			if(sResultString.length()<5) sResultString="삽입:"+ins+"건, 수정:"+upd+"건 업로드가 완료됐습니다.";
			
			request.setAttribute("sResultString", sResultString);
			openWorkbook.close();
		}   //while
	}catch(Exception e){   //try
		e.printStackTrace();
	}
	return (mapping.findForward("excelLink1"));
}  //Exception

public String getDetcode(List<Map> code,String gc,String detCN) throws SQLException{	
	String detcode="";
	for(int i=0;i<code.size();i++){
		if(gc.equals(code.get(i).get("groupcode"))){
			if(detCN.equals(code.get(i).get("detcodename"))){
				detcode=code.get(i).get("detcode").toString();
			}
		}
	}
	return detcode;
}

//응시결과처리 저장
public ActionForward examResultSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		Map map = new HashMap();

		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최(key)
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류(key)
		map.put("detcode1",        		request.getParameter("detcode1"));		//내용
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드(교육및시험구분)(key)
		
		map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
		map.put("oper_lic_no1",        	request.getParameter("oper_lic_no1"));									//면허번호
		map.put("oper_hp1",        		request.getParameter("oper_hp1"));										//핸드폰
		map.put("oper_email1",        	request.getParameter("oper_email1"));									//이메일
		map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
		map.put("oper_state1",        	request.getParameter("oper_state1"));									//결제여부
		map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
		map.put("result_point1",        request.getParameter("result_point1"));									//점수
		map.put("attend_cnt1",        	request.getParameter("attend_cnt1"));									//출석일수
		map.put("time_cnt1",        	request.getParameter("time_cnt1"));										//이수시간
		map.put("receipt_no1",        	request.getParameter("receipt_no1"));									//접수번호
		map.put("bran_seq1",        	request.getParameter("bran_seq1"));										//지부순번(key)
		map.put("code_seq1",        	request.getParameter("code_seq1"));										//순번(key)
		map.put("g_name",        		g_name);										//등록자
		
		
		//jsp 넘긴 항목이랑 같은 갯수로 값을 받아야 한다.
		educationDao dao = new educationDao(); 

		
		//교육및시험명 테이블에서 관리점수항목 불러오기
		List<Map> selectexampoint = dao.selectexampoint(map);

		int v_edu_marks = Integer.parseInt(selectexampoint.get(0).get("edu_marks").toString());
		int v_service_marks = Integer.parseInt(selectexampoint.get(0).get("service_marks").toString());
		int v_finish_time = Integer.parseInt(selectexampoint.get(0).get("finish_time").toString());
		int v_finish_date = Integer.parseInt(selectexampoint.get(0).get("finish_date").toString());
		int v_finish_point = Integer.parseInt(selectexampoint.get(0).get("finish_point").toString());
		String point_manage=selectexampoint.get(0).get("point_manage").toString();
		String code_pers=StringUtil.nullToStr("0", request.getParameter("code_pers"));
		
		
	    System.out.println("v_edu_marks=======>"+v_edu_marks);
	    System.out.println("v_service_marks=======>"+v_service_marks);
	    System.out.println("v_finish_time=======>"+v_finish_time);
	    System.out.println("v_finish_date=======>"+v_finish_date);
	    System.out.println("v_finish_point=======>"+v_finish_point);
		System.out.println("point_manage=======>"+point_manage);
		System.out.println("code_pers=======>"+code_pers);
		
		HashMap map2=new HashMap();
		List<Map> selCertifi=new ArrayList();
		if(!code_pers.equals("0")){
			map2.put("code_pers", code_pers);
			map2.put("oper_end_dt", selectexampoint.get(0).get("oper_end_dt").toString());
			selCertifi=dao.selCertifi(map2);
		}
		
		int pmanage=comparePmanage(point_manage,selCertifi);
		
		System.out.println("pmanage===========>"+pmanage);
		
		if ( (v_finish_point <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("result_point1"))))&&
			 (v_finish_date <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("attend_cnt1"))))&&
			 (v_finish_time <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("time_cnt1"))))
		){
			if(code_pers.equals("0")||pmanage==0){
				map.put("edu_marks1",       v_edu_marks);										//이수점수
				map.put("service_marks1",   v_service_marks);
				map.put("result_state1",    "10");	
				map.put("point_manage1", 	"0");
			}else if(pmanage>0 && pmanage<6){
				map.put("edu_marks1",       v_edu_marks);										//이수점수
				map.put("service_marks1",   v_service_marks);
				map.put("result_state1",    "11");	
				map.put("point_manage1", 	pmanage);
			}else if(pmanage==10){
				map.put("edu_marks1",       v_edu_marks);										//이수점수
				map.put("service_marks1",   v_service_marks);
				map.put("result_state1",    "10");	
				map.put("point_manage1", 	"0");
			}			
		}else{
			if ( (v_finish_point > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("result_point1"))))){
				map.put("edu_marks1",        		"0");										//이수점수
				map.put("service_marks1",        	"0");									//봉사점수
				map.put("result_state1",        "02");					//상태 (시험성적미달)
				if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
				else map.put("point_manage1", 	pmanage);
			}else if ( (v_finish_date > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("attend_cnt1"))))){
				map.put("edu_marks1",        		"0");										//이수점수
				map.put("service_marks1",        	"0");									//봉사점수
				map.put("result_state1",        "03");					//상태 (합격/이수)
				if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
				else map.put("point_manage1", 	pmanage);
			} else	if ( (v_finish_time > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("time_cnt1"))))){
				map.put("edu_marks1",        		"0");										//이수점수
				map.put("service_marks1",        	"0");									//봉사점수
				map.put("result_state1",        "03");					//상태 (합격/이수)
				if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
				else map.put("point_manage1", 	pmanage);
			} else {
				map.put("edu_marks1",        		"0");										//이수점수
				map.put("service_marks1",        	"0");									//봉사점수
				map.put("result_state1",        "04");					//상태 (합격/이수)
				if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
				else map.put("point_manage1", 	pmanage);
			}
		}
		//if(점수나 출석일수나 이수시간이 있으면 update,존재하지 않으면 insert)
		/*if (request.getParameter("point_manage1").length() > 1 ){
			map.put("point_manage1", request.getParameter("point_manage1").toString().substring(1, 2));									//점수관리 자격증
		}*/
		if ("U".equals(request.getParameter("point_manage1").toString().substring(0, 1))){
			int updateexame = dao.updateexame(map);
			System.out.println("update=======>"+updateexame);
		}else{
			int deletexam = dao.deletexam(map);
			List<Map> examinsert = dao.insertexam(map);
			System.out.println("insert=======>aa"+examinsert);
		}
		
		Map map1 = new HashMap();
		dao = new educationDao(); 

		map1.put("yyyy1",        		request.getParameter("yyyy1"));					//교육년도
		map1.put("code_bran1",        	request.getParameter("code_bran1"));			//교육주최
		map1.put("code_kind1",        	request.getParameter("code_kind1"));			//교육종류
		map1.put("detcode1",        	request.getParameter("detcode1"));				//교육명키값

		List<Map> selectexam1 = dao.selectexam1(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
		JSONArray j_educationsend3=JSONArray.fromObject(selectexam1);
		request.setAttribute("selectexam1" , selectexam1);
		request.setAttribute("j_educationsend3" , j_educationsend3);
		request.setAttribute("edutakeets4", map);
		
		if(request.getParameter("s_pgno")!=null){
			String pgno = request.getParameter("s_pgno");
			request.setAttribute("pgno",   pgno);
		}		  	

		return mapping.findForward("examResultSave");//저장후 저장한값을 화면에 다시 표출해야한다
	}

//응시결과처리 저장 Batch
public ActionForward examResultSaveBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		Map map = new HashMap();

		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최(key)
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류(key)
		map.put("detcode1",        		request.getParameter("detcode1"));		//내용
		map.put("code_certifi1",        request.getParameter("code_certifi1"));									//구분코드(교육및시험구분)(key)
//		
//		map.put("oper_name1",       	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
//		map.put("oper_lic_no1",        	request.getParameter("oper_lic_no1"));									//면허번호
//		map.put("oper_hp1",        		request.getParameter("oper_hp1"));										//핸드폰
//		map.put("oper_email1",        	request.getParameter("oper_email1"));									//이메일
//		map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
//		map.put("oper_state1",        	request.getParameter("oper_state1"));									//결제여부
//		map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
//		map.put("result_point1",        request.getParameter("result_point1"));									//점수
//		map.put("attend_cnt1",        	request.getParameter("attend_cnt1"));									//출석일수
//		map.put("time_cnt1",        	request.getParameter("time_cnt1"));										//이수시간
//		map.put("receipt_no1",        	request.getParameter("receipt_no1"));									//접수번호
//		map.put("bran_seq1",        	request.getParameter("bran_seq1"));										//지부순번(key)
//		map.put("code_seq1",        	request.getParameter("code_seq1"));										//순번(key)
		map.put("g_name",        		g_name);										//등록자
		
		
		//jsp 넘긴 항목이랑 같은 갯수로 값을 받아야 한다.
		educationDao dao = new educationDao(); 

		
		//교육및시험명 테이블에서 관리점수항목 불러오기
		List<Map> selectexampoint = dao.selectexampoint(map);

		int v_edu_marks = Integer.parseInt(selectexampoint.get(0).get("edu_marks").toString());
		int v_service_marks = Integer.parseInt(selectexampoint.get(0).get("service_marks").toString());
		int v_finish_time = Integer.parseInt(selectexampoint.get(0).get("finish_time").toString());
		int v_finish_date = Integer.parseInt(selectexampoint.get(0).get("finish_date").toString());
		int v_finish_point = Integer.parseInt(selectexampoint.get(0).get("finish_point").toString());
		String point_manage=selectexampoint.get(0).get("point_manage").toString();
		
	    System.out.println("v_edu_marks=======>"+v_edu_marks);
	    System.out.println("v_service_marks=======>"+v_service_marks);
	    System.out.println("v_finish_time=======>"+v_finish_time);
	    System.out.println("v_finish_date=======>"+v_finish_date);
	    System.out.println("v_finish_point=======>"+v_finish_point);
	    System.out.println("point_manage=======>"+point_manage);

	    map.put("result_point1",        String.valueOf(v_finish_point));									//점수
		map.put("attend_cnt1",        	String.valueOf(v_finish_date));									//출석일수
		map.put("time_cnt1",        	String.valueOf(v_finish_time));										//이수시간
		
		String oper_infos_org_str =  (String)request.getParameter("oper_infos");
		String errMsg = "";
		
		int ins=0; /*20160205 일괄처리시 건수 메세지 표지*/
		String Msg = "";
		
		//-- 전체 일괄처리의 경우 [START]
		if("".equals(StringUtil.NVL(oper_infos_org_str))){
			Map map3 = new HashMap();    		
			if(request.getParameter("yyyy1")!=null) map3.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
			if(request.getParameter("code_bran1")!=null) map3.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
			if(request.getParameter("code_kind1")!=null) map3.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
			if(request.getParameter("edutest_name1")!=null) map3.put("detcode1",        		request.getParameter("edutest_name1"));										//승인여부
		
			if("Y".equals(request.getParameter("cert"))){
				map3.put("cert",        	"Y");
			}else{
				if(request.getParameter("code_certifi1")!=null) map3.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
				if(request.getParameter("oper_name1")!=null) map3.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
				if(request.getParameter("oper_lic_no1")!=null) map3.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
				if(request.getParameter("oper_hp1")!=null) map3.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
				if(request.getParameter("oper_comp_name11")!=null) map3.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
				if(request.getParameter("code_operation1")!=null) map3.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
				if(request.getParameter("result_point1")!=null) map3.put("result_point1",        request.getParameter("result_point1"));									//점수
		
				if(request.getParameter("attend_cnt1")!=null) map3.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
				if(request.getParameter("time_cnt1")!=null) map3.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
				if(request.getParameter("result_state1")!=null) map3.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
				if(request.getParameter("oper_email1")!=null) map3.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
				if(request.getParameter("oper_state1")!=null) map3.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
				if(request.getParameter("result_no1")!=null) map3.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
			}

			map3.put("nstart", 0);
			map3.put("nend", 1000000);
			
			List<Map> result= dao.selectexam2(map3);
	    	
	    	StringBuffer oper_infos_org_str_buf = new StringBuffer();
	    	if(result!=null){
		    	for(int i=0; i<result.size(); i++){
					Map rowdata= 	result.get(i);
					
					 if ( "".equals(StringUtil.NVL(rowdata.get("pers_addr"))) && "4".equals((String)rowdata.get("code_kind"))){		
						/* 위생교육-주소가 없는 응시자는 처리할 수 없다 20121124 경명신과장 */
						 errMsg = "일괄처리가 취소 되었습니다(전체취소).\\n\\n위생교육에선 주소가 없는 응시자는 결과를 등록할 수 없습니다.\\n\\n이름: "+
								 (String)rowdata.get("oper_name")+" , 면허번호: "+(String)rowdata.get("oper_lic_no")+" , 생년월일: "+(String)rowdata.get("oper_birth");
						break;
					}
					 
					String point_manage_search = "";
					if("1".equals(((String)rowdata.get("point_manage")).substring(0,1))){
						point_manage_search = "0";
					}else if("1".equals(((String)rowdata.get("point_manage")).substring(1,2))){
						point_manage_search = "1";
					}else if("1".equals(((String)rowdata.get("point_manage")).substring(2,3))){
						point_manage_search = "2";
					}else if("1".equals(((String)rowdata.get("point_manage")).substring(3,4))){
						point_manage_search = "3";
					}else if("1".equals(((String)rowdata.get("point_manage")).substring(4,5))){
						point_manage_search = "4";
					}
					String v_comper = String.valueOf(rowdata.get("result_point"))+String.valueOf(rowdata.get("attend_cnt"))+String.valueOf(rowdata.get("time_cnt"));
					v_comper = v_comper.replace("null","");
					if(!"".equals(v_comper.replace(" ",""))){
						point_manage_search    		= "U" + point_manage_search;		//입력수정여부 + 점수관리 
					}else{
						point_manage_search    		= "I" + point_manage_search;		//입력수정여부 + 점수관리
					}
					
					if(oper_infos_org_str_buf.length() > 0) {
						oper_infos_org_str_buf.append("__");
					}
					oper_infos_org_str_buf.append((String)rowdata.get("code_kind")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("code_certifi")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("code_seq")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("code_bran")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("bran_seq")).append("_");
					oper_infos_org_str_buf.append((String)rowdata.get("receipt_no")).append("_");
					oper_infos_org_str_buf.append(!"".equals(StringUtil.NVL((String)rowdata.get("code_pers"))) ? (String)rowdata.get("code_pers") : "x").append("_");
					oper_infos_org_str_buf.append(point_manage_search);
					
					ins++;
					
		    	} Msg = "총" +ins + "건이 처리되었습니다.";
	    	}
	    	oper_infos_org_str = oper_infos_org_str_buf != null ?  oper_infos_org_str_buf.toString() : "";
		}
		//-- 전체 일괄처리의 경우 [END]
		
		if("".equals(errMsg) && !"".equals(StringUtil.NVL(oper_infos_org_str))){
			List<String> oper_infos_org =  Arrays.asList(oper_infos_org_str.split("__"));
			List<List<String>> oper_infos = new ArrayList<List<String>>();
			
			for(String item : oper_infos_org){
				List<String> oper_info =  Arrays.asList(item.split("_"));
				oper_infos.add(oper_info);
			}
			
			
			for(List<String> oper_info : oper_infos){
				String code_kind = oper_info.get(0);
				String code_certifi = oper_info.get(1);
				String code_seq = oper_info.get(2);
				String code_bran = oper_info.get(3);
				String bran_seq = oper_info.get(4);
				String receipt_no = oper_info.get(5);
				String code_pers = oper_info.get(6);
				String point_manage_param = oper_info.get(7);
				
				map.put("receipt_no1",        	receipt_no);									//접수번호
				map.put("bran_seq1",        	bran_seq);										//지부순번(key)
				map.put("code_seq1",        	code_seq);										//순번(key)
				
				System.out.println("code_pers=======>"+code_pers);
				
				HashMap map2=new HashMap();
				List<Map> selCertifi=new ArrayList();
				if(!code_pers.equals("0")){
					map2.put("code_pers", code_pers);
					map2.put("oper_end_dt", selectexampoint.get(0).get("oper_end_dt").toString());
					selCertifi=dao.selCertifi(map2);
				}
				
				int pmanage=comparePmanage(point_manage,selCertifi);
				
				System.out.println("pmanage===========>"+pmanage);
				
//				if ( (v_finish_point <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("result_point1"))))&&
//					 (v_finish_date <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("attend_cnt1"))))&&
//					 (v_finish_time <= Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("time_cnt1"))))
//				){
					if(code_pers.equals("0")||pmanage==0){
						map.put("edu_marks1",       v_edu_marks);										//이수점수
						map.put("service_marks1",   v_service_marks);
						map.put("result_state1",    "10");	
						map.put("point_manage1", 	"0");
					}else if(pmanage>0 && pmanage<6){
						map.put("edu_marks1",       v_edu_marks);										//이수점수
						map.put("service_marks1",   v_service_marks);
						map.put("result_state1",    "11");	
						map.put("point_manage1", 	pmanage);
					}else if(pmanage==10){
						map.put("edu_marks1",       v_edu_marks);										//이수점수
						map.put("service_marks1",   v_service_marks);
						map.put("result_state1",    "10");	
						map.put("point_manage1", 	"0");
					}			
//				}else{
//					if ( (v_finish_point > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("result_point1"))))){
//						map.put("edu_marks1",        		"0");										//이수점수
//						map.put("service_marks1",        	"0");									//봉사점수
//						map.put("result_state1",        "02");					//상태 (시험성적미달)
//						if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
//						else map.put("point_manage1", 	pmanage);
//					}else if ( (v_finish_date > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("attend_cnt1"))))){
//						map.put("edu_marks1",        		"0");										//이수점수
//						map.put("service_marks1",        	"0");									//봉사점수
//						map.put("result_state1",        "03");					//상태 (합격/이수)
//						if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
//						else map.put("point_manage1", 	pmanage);
//					} else	if ( (v_finish_time > Integer.parseInt(StringUtil.nullToStr("0", request.getParameter("time_cnt1"))))){
//						map.put("edu_marks1",        		"0");										//이수점수
//						map.put("service_marks1",        	"0");									//봉사점수
//						map.put("result_state1",        "03");					//상태 (합격/이수)
//						if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
//						else map.put("point_manage1", 	pmanage);
//					} else {
//						map.put("edu_marks1",        		"0");										//이수점수
//						map.put("service_marks1",        	"0");									//봉사점수
//						map.put("result_state1",        "04");					//상태 (합격/이수)
//						if(code_pers.equals("0")||pmanage==0 ||pmanage==10) map.put("point_manage1", 	"0");
//						else map.put("point_manage1", 	pmanage);
//					}
//				}
				//if(점수나 출석일수나 이수시간이 있으면 update,존재하지 않으면 insert)
				/*if (request.getParameter("point_manage1").length() > 1 ){
					map.put("point_manage1", request.getParameter("point_manage1").toString().substring(1, 2));									//점수관리 자격증
				}*/
//				if ("U".equals(request.getParameter("point_manage1").toString().substring(0, 1))){
//					int updateexame = dao.updateexame(map);
//					System.out.println("update=======>"+updateexame);
//				}else{
//					int deletexam = dao.deletexam(map);
//					List<Map> examinsert = dao.insertexam(map);
//					System.out.println("insert=======>aa"+examinsert);
//				}
				if ("U".equals(point_manage_param.substring(0, 1))){
					int updateexame = dao.updateexame(map);
					
				
					System.out.println("update=======>"+updateexame);
				}else{
					int deletexam = dao.deletexam(map);
					List<Map> examinsert = dao.insertexam(map);
					System.out.println("insert=======>aa"+examinsert);
				}

				
			}
		}
		
		Map map1 = new HashMap();
		dao = new educationDao(); 

//		map1.put("yyyy1",        		request.getParameter("yyyy1"));					//교육년도
//		map1.put("code_bran1",        	request.getParameter("code_bran1"));			//교육주최
//		map1.put("code_kind1",        	request.getParameter("code_kind1"));			//교육종류
//		map1.put("detcode1",        	request.getParameter("detcode1"));				//교육명키값
//
//		List<Map> selectexam1 = dao.selectexam1(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
//		JSONArray j_educationsend3=JSONArray.fromObject(selectexam1);
//		request.setAttribute("selectexam1" , selectexam1);
//		request.setAttribute("j_educationsend3" , j_educationsend3);
//		request.setAttribute("edutakeets4", map);

		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null){
			
			map1.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
			map1.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
			map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
			map1.put("detcode1",        	request.getParameter("detcode1"));
			map1.put("code_certifi1",        	request.getParameter("code_certifi1"));
			
			/*3가지 조건에 맞는 교육명 출력*/
			List<Map> selectexam1 = dao.selectexam1(map1);//selectexam커리를 타고 실행 넘어온값 selectexam list<map>객체에 집어 넣는다

			JSONArray j_educationsend3=JSONArray.fromObject(selectexam1);
			request.setAttribute("selectexam1" , selectexam1);
			
			request.setAttribute("j_educationsend3" , j_educationsend3);
			request.setAttribute("edutake4", map1);
			request.setAttribute("batchSaved", "Y");
			request.setAttribute("errMsg", errMsg);
			request.setAttribute("Msg", Msg);
		}

		return mapping.findForward("examResultSave");//저장후 저장한값을 화면에 다시 표출해야한다
	}

	public static int comparePmanage(String point_manage,List<Map> selCertifi){ 
		
		int pmanage=0;
		int certi=selCertifi.size();
		
		int i=0;
		if(point_manage.substring(0,1).equals("1")) i+=1; //급식
		if(point_manage.substring(1,2).equals("1")) i+=1; //임상
		if(point_manage.substring(2,3).equals("1")) i+=1; //산업보건
		if(point_manage.substring(3,4).equals("1")) i+=1; //노인
		if(point_manage.substring(4,5).equals("1")) i+=1; //스포츠
		if(point_manage.substring(5,6).equals("1")) i+=1; //기타
		if(point_manage.equals("1111111111")) i=10;
		
		if(certi==1){
			if(point_manage.substring(0,1).equals("1")&&selCertifi.get(0).get("code_certifi").toString().equals("1")) pmanage=1; //급식
			else if(point_manage.substring(1,2).equals("1")&&selCertifi.get(0).get("code_certifi").toString().equals("2")) pmanage=2; //임상
			else if(point_manage.substring(2,3).equals("1")&&selCertifi.get(0).get("code_certifi").toString().equals("3")) pmanage=3; //산업보건	
			else if(point_manage.substring(3,4).equals("1")&&selCertifi.get(0).get("code_certifi").toString().equals("4")) pmanage=4; //노인
			else if(point_manage.substring(4,5).equals("1")&&selCertifi.get(0).get("code_certifi").toString().equals("5")) pmanage=5; //스포츠
		}else if(certi>1){
			if(i==1){
				for(int j=0;j<certi;j++){
					if(point_manage.substring(0,1).equals("1")&&selCertifi.get(j).get("code_certifi").toString().equals("1")) pmanage=1; //급식
					else if(point_manage.substring(1,2).equals("1")&&selCertifi.get(j).get("code_certifi").toString().equals("2")) pmanage=2; //임상
					else if(point_manage.substring(2,3).equals("1")&&selCertifi.get(j).get("code_certifi").toString().equals("3")) pmanage=3; //산업보건
					else if(point_manage.substring(3,4).equals("1")&&selCertifi.get(j).get("code_certifi").toString().equals("4")) pmanage=4; //노인
					else if(point_manage.substring(4,5).equals("1")&&selCertifi.get(j).get("code_certifi").toString().equals("5")) pmanage=5; //스포츠
				}
			}else{
				pmanage=10;
			}
		}
		
		return pmanage;
	}
//document.sForm.action = "education.do?method=resultNoCreate&code_kind1="+code_kind1+"&detcode1="+edutest_name1;
//발급번호 생성

public ActionForward resultNoCreate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session=request.getSession();
		String g_name = session.getAttribute("G_NAME").toString(); 
		Map map = new HashMap();

		map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최(key)
		map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류(key)
		map.put("detcode1",        		request.getParameter("detcode1"));		//내용
		
		educationDao dao = new educationDao(); 

//1.자격증 교육인지 위생교육인지 판단
//2.해당교육 이수자를 읽어서 map에 담는다
//3.위생교육이면 4-년도-지부코드-00001 으로 번호 생성하여 결과에 업데이트 한다.
//4.자격증교육이면 같은년도 자격증 시험 응시자로 등록한다.(이때 자격증시험 응사자로 등록된 사람은 제외하고 저장해야한다)
//5.자격증시험 응시자로 저장 후 1학기와 2학기 모두 이수한 사람을 찾아 1-년도-지부코드-00001 으로 번호 생성하여 1학기, 2학기 교육결과에 update 한다.

		
		//교육및시험명 테이블에서 관리점수항목 불러오기
		System.out.println("detcode1==================>"+request.getParameter("detcode1"));
		//교육및시험명 테이블에서 관리점수항목 불러오기
		int procReturn=0;
		map.put("iDetcode", request.getParameter("detcode1"));
		map.put("procReturn",procReturn);
		Map selectexampoint1 = dao.callResultNoCreat_STR(map);
		System.out.println("callResultNoCreat_STR --- procReturn===========>"+selectexampoint1);
		Map selectexampoint2 = dao.callLicenseDataCreat_STR(map);
		System.out.println("callLicenseDataCreat_STR --- procReturn===========>"+selectexampoint2);

		Map map1 = new HashMap();
		dao = new educationDao(); 

		map1.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		map1.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		map1.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		map1.put("detcode1",        	request.getParameter("detcode1"));									//교육명키값		
		List<Map> selectexam1 = dao.selectexam1(map1);//educationsend2커리를 타고 실행 넘어온값 educationsend2 list<map>객체에 집어 넣는다
		JSONArray j_educationsend3=JSONArray.fromObject(selectexam1);
		request.setAttribute("selectexam1" , selectexam1);
		request.setAttribute("j_educationsend3" , j_educationsend3);
		request.setAttribute("edutakeets5", map1);

		return mapping.findForward("examResultSave");//저장후 저장한값을 화면에 다시 표출해야한다
	}

	public ActionForward uFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String code_kind1 		= request.getParameter("code_kind1");
		String code_certifi1 	= request.getParameter("code_certifi1");
		String code_seq1 		= request.getParameter("code_seq1");
		String code_bran1 		= request.getParameter("code_bran1");
		String bran_seq1 		= request.getParameter("bran_seq1");
		String receipt_no1 		= request.getParameter("receipt_no1");
		
		Map map = new HashMap();
		educationDao dao = new educationDao(); 
		
		map.put("code_kind1", code_kind1);
		map.put("code_certifi1", code_certifi1);
		map.put("code_seq1", code_seq1);
		map.put("code_bran1", code_bran1);
		map.put("bran_seq1", bran_seq1);
		map.put("receipt_no1", receipt_no1);
		
		List<Map> sFiles=dao.selectAFiles(map);
		
		request.setAttribute("sFiles", sFiles);
		
		return (mapping.findForward("fileUP"));		
	}
	public ActionForward upFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setCharacterEncoding("UTF-8");
		Map map = new HashMap();
		educationDao dao = new educationDao();
	
		String saveFolder="D:/WEB/KDA_VER3/upload/education";
//		String saveFolder="D:/KDA_WORKSPACE/kda/WebContent/upload/education";

		String encType="UTF-8";
		int maxSize=10*1024*1024;
		File f1=null;
		File f2=null;
		File f3=null;
		MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType,new DefaultFileRenamePolicy());
		
		String oper_name1=StringUtil.nullToStr("",URLDecoder.decode(multi.getParameter("opername"),"UTF-8"));
		String codeoperation=StringUtil.nullToStr("", URLDecoder.decode(multi.getParameter("codeoperation2"),"UTF-8"));
		String param=StringUtil.nullToStr("", multi.getParameter("param"));
		String code_operation1=StringUtil.nullToStr("", multi.getParameter("code_operation1"));
		
		System.out.println("oper_name1>>>>>>>"+oper_name1);
		String orgfilename="";
		String filename="";
		String msg="";
		String temp="";
		
		try{
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();				
				orgfilename=multi.getFilesystemName(name);
				if(orgfilename!=null){					
					System.out.println("name="+name+"       orginal filename="+orgfilename);
					String[] ext=orgfilename.split("\\.");
					filename=multi.getParameter("filename")+name;
					System.out.println("filename==============>"+filename);
					f1=new File(saveFolder+"/"+multi.getOriginalFileName(name));
					f2=new File(saveFolder+"/"+filename+"."+ext[ext.length-1]);
					f3=new File(saveFolder+"/"+filename+"."+ext[ext.length-1]);
					
					System.out.println(f1.getName()+"       "+f2.getName()+"       "+f3.getName());
					
					if(f3.exists()){
						boolean a=f3.delete();
						System.out.println("f3.delete(); >>>> "+a);
					}
					if(f1.exists()) {
						boolean b=f1.renameTo(f2);
						System.out.println("f1.renameTo(f2) >>>> "+b);
					}
					request.setAttribute(name+"YN", filename+"."+ext[ext.length-1]);
					temp+=filename+"."+ext[ext.length-1]+" ";
				}
			}

			if(temp.length()!=0){
				String[] files1=temp.split(" ");
				int fileNo=files1.length;
				for(int i=0;i<fileNo;i++){
					String[] tmp=files1[i].split("\\.");				
					//map.put("add_file_no", tmp[0].substring(14, 16));
					map.put("add_file_no", tmp[0].substring(16, 18));
					map.put("code_kind1", multi.getParameter("code_kind1"));
					map.put("code_certifi1", multi.getParameter("code_certifi1"));
					map.put("code_seq1", multi.getParameter("code_seq1"));
					map.put("code_bran1", multi.getParameter("code_bran1"));
					map.put("bran_seq1", multi.getParameter("bran_seq1"));
					map.put("receipt_no1", multi.getParameter("receipt_no"));
					map.put("chang_file_name", files1[i]);
					List<Map> sFiles=dao.selectAFiles(map);
					if(sFiles.size()==0){
						List<Map> insertinspection1 = dao.insertinspection(map);
					}else{
						int upinspection1=dao.upinspection1(map);
					}											
				}
			}
			
			msg="화일 첨부가 완료됐습니다.";

		}catch(Exception e){
			e.printStackTrace();
		}
		
		map.put("code_kind1", multi.getParameter("code_kind1"));
		map.put("code_certifi1", multi.getParameter("code_certifi1"));
		map.put("code_seq1", multi.getParameter("code_seq1"));
		map.put("code_bran1", multi.getParameter("code_bran1"));
		map.put("bran_seq1", multi.getParameter("bran_seq1"));
		map.put("receipt_no1", multi.getParameter("receipt_no"));
		
		List<Map> sFiles=dao.selectAFiles(map);
		
		request.setAttribute("sFiles", sFiles);
		String bran1=multi.getParameter("code_bran1");
		System.out.println("sFiles.size()==================>"+sFiles.size());
		request.setAttribute("msg", msg);
		request.setAttribute("oper_name1", oper_name1);
		request.setAttribute("codeoperation", codeoperation);
		request.setAttribute("param", param);
		request.setAttribute("code_operation1", code_operation1);
		request.setAttribute("yyyy1", multi.getParameter("yyyy1"));
		request.setAttribute("code_kind1", multi.getParameter("code_kind1"));
		request.setAttribute("code_bran1", bran1);
		request.setAttribute("edutest_name1", multi.getParameter("edutest_name1"));
		request.setAttribute("code_certifi1", multi.getParameter("code_certifi1"));
		request.setAttribute("receipt_no", multi.getParameter("receipt_no"));
		request.setAttribute("del", "N");		
		request.setAttribute("code_seq", multi.getParameter("code_seq1"));
		request.setAttribute("bran_seq", multi.getParameter("bran_seq1"));
				
		return (mapping.findForward("fileUP"));		
	}
	
	public ActionForward delFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
			Map map = new HashMap();
			educationDao dao = new educationDao();
		
			String saveFolder="D:/WEB/KDA_VER3/upload/education";
//			String saveFolder="D:/KDA_WORKSPACE/kda/WebContent/upload/education";

			String encType="UTF-8";
			int maxSize=10*1024*1024;
			File f1=null;
			File f2=null;
			File f3=null;
			MultipartRequest multi=new MultipartRequest(request,saveFolder,maxSize,encType,new DefaultFileRenamePolicy());
			
			String oper_name1=StringUtil.nullToStr("",URLDecoder.decode(multi.getParameter("opername1"),"UTF-8"));
			String codeoperation=StringUtil.nullToStr("", URLDecoder.decode(multi.getParameter("codeoperation2"),"UTF-8"));
			String param=StringUtil.nullToStr("", multi.getParameter("param"));
			String code_operation1=StringUtil.nullToStr("", multi.getParameter("code_operation1"));
			
			System.out.println("oper_name1>>>>>>>"+oper_name1);
			String orgfilename="";
			String filename="";
			String msg="";
			String temp="";
			String dFilename="";
			try{
				dFilename=multi.getParameter("dFilename");
				System.out.println("dFilename==========>"+dFilename);
				
				f1=new File(saveFolder+"/"+dFilename);
				boolean del=false;
				if(f1.exists()){
					del=f1.delete();
					System.out.println(dFilename+"삭제 결과="+del);
				}
				
				if(del)  msg="파일이 삭제됐습니다.";
				
				//map.put("add_file_no", dFilename.substring(14, 16));
				map.put("add_file_no", dFilename.substring(16, 18));
				map.put("code_kind1", multi.getParameter("code_kind1"));
				map.put("code_certifi1", multi.getParameter("code_certifi1"));
				map.put("code_seq1", multi.getParameter("code_seq1"));
				map.put("code_bran1", multi.getParameter("code_bran1"));
				map.put("bran_seq1", multi.getParameter("bran_seq1"));
				map.put("receipt_no1", multi.getParameter("receipt_no"));
				map.put("chang_file_name", "");				
				
				int deletAFiles=dao.deletAFiles(map);
				
			}catch(Exception e){
				e.printStackTrace();
			}	
			
			Map map1=new HashMap();
			map1.put("code_kind1", multi.getParameter("code_kind1"));
			map1.put("code_certifi1", multi.getParameter("code_certifi1"));
			map1.put("code_seq1", multi.getParameter("code_seq1"));
			map1.put("code_bran1", multi.getParameter("code_bran1"));
			map1.put("bran_seq1", multi.getParameter("bran_seq1"));
			map1.put("receipt_no1", multi.getParameter("receipt_no"));
			
			List<Map> sFiles=dao.selectAFiles(map1);
			
			request.setAttribute("sFiles", sFiles);
			String bran1=multi.getParameter("code_bran1");
			System.out.println("sFiles.size()==================>"+sFiles.size());
			request.setAttribute("msg", msg);
			request.setAttribute("oper_name1", oper_name1);
			System.out.println("oper_name1==========>"+oper_name1);
			request.setAttribute("codeoperation", codeoperation);
			request.setAttribute("param", param);
			request.setAttribute("code_operation1", code_operation1);
			request.setAttribute("yyyy1", multi.getParameter("yyyy1"));
			request.setAttribute("code_kind1", multi.getParameter("code_kind1"));
			request.setAttribute("code_bran1", bran1);
			request.setAttribute("edutest_name1", multi.getParameter("edutest_name1"));
			request.setAttribute("code_certifi1", multi.getParameter("code_certifi1"));
			request.setAttribute("del", "Y");
			request.setAttribute("code_seq", multi.getParameter("code_seq1"));
			request.setAttribute("bran_seq", multi.getParameter("bran_seq1"));
			request.setAttribute("receipt_no", multi.getParameter("receipt_no"));
			return (mapping.findForward("fileUP"));		
		}
	/*
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.21
	 * 작   업 : sendnotePad	쪽지발송
	 */
	public ActionForward sendnotePad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String code_pers = "";
		String pers_name = "";
		if(request.getParameter("code_pers")!= null)	code_pers = request.getParameter("code_pers")+"";
		if(request.getParameter("pers_name")!= null)	pers_name = URLDecoder.decode(request.getParameter("pers_name"),"UTF-8");
		
		
		request.setAttribute("code_pers", code_pers);
		request.setAttribute("pers_name", pers_name);
		request.setAttribute("chk", "");
		return (mapping.findForward("sendnotePadAll"));
	}
	/*
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.21
	 * 작   업 : sendnotePadAll	쪽지발송전체
	 */
	public ActionForward sendnotePadAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		Map map = new HashMap();
		
		map.put("nstart", 0);
		map.put("nend", 1000000);
		
		
		//검색합시다.
		educationDao dao = new educationDao(); 
	 		
		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null
		||request.getParameter("edutest_name1") != null||request.getParameter("code_certifi1") != null||request.getParameter("cert") != null||request.getParameter("from") != null){
		
			/* 파라미터 값을 xml에 where 절에 보내는 항목 값 갯수 동일하게 맞춰야 한다. */
//			map.put("yyyy1",        		request.getParameter("yyyy1"));						//교육년도
//			map.put("code_bran1",        	request.getParameter("code_bran1"));				//교육주최
//			map.put("code_kind1",        	request.getParameter("code_kind1"));				//교육종류
//			map.put("detcode1",       		request.getParameter("edutest_name1"));				//내용
//			map.put("code_certifi1",        request.getParameter("code_certifi1"));				//자격증 구분
			
			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
			if(request.getParameter("oper_birth1"			)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
			if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
			if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
			if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
			if(request.getParameter("sel") != null)		map.put("sel",       	"Y");			

			List<Map> list = new ArrayList();
			if(request.getParameter("from").equals("edu1"))
				list=dao.selecteducation(map);
			else 
				list=dao.selectexam2(map);
			//검색 종료
	
			//전송할 전화번호 
			String code_pers ="";
			String pers_name ="";
			
			for(int i=0; i<list.size(); i++) {
				
				if(!(list.get(i).get("person_yn1")+"").equals("")) {
					if((list.get(i).get("person_yn1")+"").length()>0){
						if(code_pers.length() > 0) code_pers+= ",";
						code_pers += list.get(i).get("person_yn1");
					}
				}
	
				if(!(list.get(i).get("oper_name")+"").equals("")) {
					if((list.get(i).get("oper_name")+"").length()>0){
						if(pers_name.length() > 0) pers_name+= ",";
						pers_name += list.get(i).get("oper_name");
					}
				}
			}
			request.setAttribute("code_pers", code_pers);
			request.setAttribute("pers_name", pers_name);
			request.setAttribute("chk", "ALL");
		}

		return (mapping.findForward("sendnotePadAll"));
	}
	
	/*
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.21
	 * 작   업 : sendumsDataAll		문자발송 전체
	 */
	public ActionForward smssandAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		if("Y".equals(request.getParameter("edutake_paramok"		))){
			Map map = new HashMap();
			educationDao dao=new educationDao();
			
			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
			if(request.getParameter("oper_birth1"			)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
			if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
			if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
			if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
			if(request.getParameter("sel") != null)		map.put("sel",       	"Y");			
				
			//전체 개수 검색
			List<Map> educationcount=dao.educationsendcnt(map); 
			int ncount = ((Integer)(educationcount.get(0).get("cnt"))).intValue();
	
			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
			if(request.getParameter("edutest_name1"		)!=null)request.setAttribute("edutest_name1"	,  URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8") );	//내용
			if(request.getParameter("oper_name1"		)!=null)request.setAttribute("oper_name1"		,  URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8") );	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)request.setAttribute("oper_comp_name11"	,  URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8") );	//근무처명
			
			String param = "";
			if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
			if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);	//교육주최
			if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
			if(request.getParameter("oper_birth1"			)!=null)param+="&oper_birth1="			+ request.getParameter("oper_birth1"		);	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)param+="&oper_lic_no1="		+ request.getParameter("oper_lic_no1"	);	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)param+="&oper_hp1="			+ request.getParameter("oper_hp1"		);	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)param+="&result_end_dt1="	+ request.getParameter("result_end_dt1"	);	//유효일자
			if(request.getParameter("start_dt1"			)!=null)param+="&start_dt1="		+ request.getParameter("start_dt1"		);	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)param+="&end_dt1="			+ request.getParameter("end_dt1"		);	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)param+="&oper_state1="		+ request.getParameter("oper_state1"	);	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)param+="&code_pay_flag1="	+ request.getParameter("code_pay_flag1"	);	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)param+="&receipt_dt1="		+ request.getParameter("receipt_dt1"	);	//신청일자
			if(request.getParameter("code_seq1"			)!=null)param+="&code_seq1="		+ request.getParameter("code_seq1"		);	//순번
			if(request.getParameter("detcode1"			)!=null)param+="&detcode1="			+ request.getParameter("detcode1"		);	//승인여부
			if(request.getParameter("sel"			)!=null)param+="&sel="			+ request.getParameter("sel"		);	//승인여부
			param+="&edutake_paramok=Y";
	
			//검색 변수 넘김
			request.setAttribute("param", param);
			request.setAttribute("edutake_paramok", "Y");
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
		}
		else if("Y".equals(request.getParameter("exam_paramok"		))){
			
			//전체건수 구해서 넘긴다.
			Map map = new HashMap();
			educationDao dao=new educationDao();
			
			//조건 입력
			// 변수에 값 받아서 넣음
//			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
//			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
//			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
//			if(request.getParameter("edutest_name1"			)!=null)map.put("detcode1"			, request.getParameter("edutest_name1"		));	//승인여부
//			if(request.getParameter("cert"			)!=null){
//				map.put("cert"			, "Y");	//승인여부
//			}else{
//				if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분				
//			}
				
			if(request.getParameter("yyyy1")!=null) map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
			if(request.getParameter("code_bran1")!=null) map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
			if(request.getParameter("code_kind1")!=null) map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
			if(request.getParameter("edutest_name1")!=null) map.put("detcode1",        		request.getParameter("edutest_name1"));										//승인여부
		
			if("Y".equals(request.getParameter("cert"))){
				map.put("cert",        	"Y");
			}else{
				if(request.getParameter("code_certifi1")!=null) map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
				if(request.getParameter("oper_name1")!=null) map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
				if(request.getParameter("oper_lic_no1")!=null) map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
				if(request.getParameter("oper_hp1")!=null) map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
				if(request.getParameter("oper_comp_name11")!=null) map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
				if(request.getParameter("code_operation1")!=null) map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
				if(request.getParameter("result_point1")!=null) map.put("result_point1",        request.getParameter("result_point1"));									//점수
		
				if(request.getParameter("attend_cnt1")!=null) map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
				if(request.getParameter("time_cnt1")!=null) map.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
				if(request.getParameter("result_state1")!=null) map.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
				if(request.getParameter("oper_email1")!=null) map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
				if(request.getParameter("oper_state1")!=null) map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
				if(request.getParameter("result_no1")!=null) map.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
			}

			//여기서 구하고 넘긴다.
			List<Map> count = dao.examsendcnt(map);
			int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

			// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)	
			String param = "";
//			if(request.getParameter("edutest_name1"		)!=null)param+="&edutest_name1="	+ request.getParameter("edutest_name1"	);	//내용
//			if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
//			if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);	//교육주최
//			if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);	//교육종류
//			if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분	
//			if(request.getParameter("cert"			)!=null)param+="&cert=Y";
			
			if(request.getParameter("yyyy1")!=null)	param+="&yyyy1="+request.getParameter("yyyy1");
			if(request.getParameter("code_bran1")!=null)	param+="&code_bran1="+request.getParameter("code_bran1");
			if(request.getParameter("code_kind1")!=null)	param+="&code_kind1="+request.getParameter("code_kind1");
			if(request.getParameter("edutest_name1")!=null)	param+="&edutest_name1="+request.getParameter("edutest_name1");
			if(request.getParameter("code_certifi1")!=null)	param+="&code_certifi1="+request.getParameter("code_certifi1");
			if(request.getParameter("oper_name1")!=null)	param+="&oper_name1="+request.getParameter("oper_name1");
			if(request.getParameter("oper_lic_no1")!=null)	param+="&oper_lic_no1="+request.getParameter("oper_lic_no1");
			if(request.getParameter("oper_hp1")!=null)	param+="&oper_hp1="+request.getParameter("oper_hp1");
			if(request.getParameter("oper_email1")!=null)	param+="&oper_email1="+request.getParameter("oper_email1");
			if(request.getParameter("code_operation1")!=null)	param+="&code_operation1="+request.getParameter("code_operation1");
			if(request.getParameter("oper_state1")!=null)	param+="&oper_state1="+request.getParameter("oper_state1");
			if(request.getParameter("oper_comp_name11")!=null)	param+="&oper_comp_name11="+request.getParameter("oper_comp_name11");
			if(request.getParameter("result_point1")!=null)	param+="&result_point1="+request.getParameter("result_point1");
			if(request.getParameter("attend_cnt1")!=null)	param+="&attend_cnt1="+request.getParameter("attend_cnt1");
			if(request.getParameter("time_cnt1")!=null)	param+="&time_cnt1="+request.getParameter("time_cnt1");
			if(request.getParameter("result_no1")!=null)	param+="&result_no1="+request.getParameter("result_no1");
			if(request.getParameter("result_state1")!=null)	param+="&result_state1="+request.getParameter("result_state1");
			if(request.getParameter("cert")!=null)	param+="&cert="+request.getParameter("cert");
			
			param+="&gubun=result&exam_paramok=Y";
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			request.setAttribute("exam_paramok", "Y");
			//전체 개수 넘김
			request.setAttribute("ncount", ncount);
		}

		return (mapping.findForward("umsDataall"));
	}
	
	/*
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 윤석희
	 * 작업일 : 2012.11.21
	 * 작   업 : sendumsDataAll		문자발송 선택
	 */
	public ActionForward smssand(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{

		if("Y".equals(request.getParameter("edutake_paramok"		))){
			String hp_infos = "";
			if(request.getParameter("hp_infos")!= null)
				hp_infos = request.getParameter("hp_infos");
			
			String param = "&hp_infos="+hp_infos+"&edutake_paramok=Y";
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			request.setAttribute("edutake_paramok", "Y");
			//전체 개수 넘김
			request.setAttribute("ncount", hp_infos.split("__").length);
		}
		else if("Y".equals(request.getParameter("exam_paramok"		))){
			String hp_infos = "";
			if(request.getParameter("hp_infos")!= null)
				hp_infos = request.getParameter("hp_infos");
			
			String param = "&hp_infos="+hp_infos+"&exam_paramok=Y";
			
			//검색 변수 넘김
			request.setAttribute("param", param);
			request.setAttribute("exam_paramok", "Y");
			//전체 개수 넘김
			request.setAttribute("ncount", hp_infos.split("__").length);
		}
		
		String oper_hp = "";
		if(request.getParameter("oper_hp")!= null)
			oper_hp = request.getParameter("oper_hp");
		String oper_name = "";
		if(request.getParameter("oper_name")!= null)
			oper_name = request.getParameter("oper_name");
		
		//System.out.println(pers_name);
		
		request.setAttribute("oper_hp", oper_hp);
		request.setAttribute("oper_name", oper_name);
		request.setAttribute("isSelect", "Y");
		return (mapping.findForward("umsDataall"));
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
	
	/*
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.14
	 * 작   업 : memberpers_no		주민번호 체크
	 */
	public ActionForward pers_check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//전체건수 구해서 넘긴다.
		Map map = new HashMap();
		educationDao dao=new educationDao();
		
		//조건 입력
		// 변수에 값 받아서 넣음
		if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, request.getParameter("edutest_name1"	));	//내용
		if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
		if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
		if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
		if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
		if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
		if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
//		if(request.getParameter("oper_no1"			)!=null)map.put("oper_no1"			, request.getParameter("oper_no1"		));	//주민번호 // JUMIN_DEL
		if(request.getParameter("oper_birth1"			)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
		if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
		if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
		if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
		if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
		if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
		if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
		if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
		if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
		if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
		if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
		if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
		if(request.getParameter("sel") != null)		map.put("sel",       	"Y");											

System.out.println("request.getParameter('edutest_name1'	)="+request.getParameter("edutest_name1"	));
		//여기서 구하고 넘긴다.
		List<Map> count = dao.educationsendcnt(map);
		int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

		// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)
//		if(request.getParameter("edutest_name1"		)!=null)request.setAttribute("edutest_name1"	,  URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8") );	//내용
		if(request.getParameter("oper_name1"		)!=null)request.setAttribute("oper_name1"		,  URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8") );	//이름
		if(request.getParameter("oper_comp_name11"	)!=null)request.setAttribute("oper_comp_name11"	,  URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8") );	//근무처명
		
		String param = "";
		if(request.getParameter("edutest_name1"		)!=null)param+="&edutest_name1="	+ request.getParameter("edutest_name1"	);	//내용
		if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
		if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);	//교육주최
		if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);	//교육종류
		if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분
//		if(request.getParameter("oper_no1"			)!=null)param+="&oper_no1="			+ request.getParameter("oper_no1"		);	//주민번호 // JUMIN_DEL
		if(request.getParameter("oper_birth1"			)!=null)param+="&oper_birth1="			+ request.getParameter("oper_birth1"		);	//생년월일
		if(request.getParameter("oper_lic_no1"		)!=null)param+="&oper_lic_no1="		+ request.getParameter("oper_lic_no1"	);	//면허번호
		if(request.getParameter("oper_hp1"			)!=null)param+="&oper_hp1="			+ request.getParameter("oper_hp1"		);	//핸드폰
		if(request.getParameter("code_operation1"	)!=null)param+="&code_operation1="	+ request.getParameter("code_operation1");	//대상자
		if(request.getParameter("result_end_dt1"	)!=null)param+="&result_end_dt1="	+ request.getParameter("result_end_dt1"	);	//유효일자
		if(request.getParameter("start_dt1"			)!=null)param+="&start_dt1="		+ request.getParameter("start_dt1"		);	//유예시작일
		if(request.getParameter("end_dt1"			)!=null)param+="&end_dt1="			+ request.getParameter("end_dt1"		);	//유예마지막일
		if(request.getParameter("oper_state1"		)!=null)param+="&oper_state1="		+ request.getParameter("oper_state1"	);	//진행상태
		if(request.getParameter("code_pay_flag1"	)!=null)param+="&code_pay_flag1="	+ request.getParameter("code_pay_flag1"	);	//결제방법
		if(request.getParameter("receipt_dt1"		)!=null)param+="&receipt_dt1="		+ request.getParameter("receipt_dt1"	);	//신청일자
		if(request.getParameter("code_seq1"			)!=null)param+="&code_seq1="		+ request.getParameter("code_seq1"		);	//순번
		if(request.getParameter("detcode1"			)!=null)param+="&detcode1="			+ request.getParameter("detcode1"		);	//승인여부
		if(request.getParameter("sel"			)!=null)param+="&sel="			+ request.getParameter("sel"		);	

		//검색 변수 넘김
		request.setAttribute("param", param);
		//전체 개수 넘김
		request.setAttribute("ncount", ncount);

		request.setAttribute("url", "education");
		
	return (mapping.findForward("pers_check"));
			
	}

	//교육별응시현황 엑셀 저장	
	public void edutakeListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		educationDao dao = new educationDao(); 
		memberStateDao dao1=new memberStateDao(); 
		
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		HttpSession session=request.getSession();
		String user = session.getAttribute("G_ID").toString(); //유저ID
		//여기부터 개발자 변경 필요
		String name="educationSendList"; //프로그램명
		String s_name="교육별응시현황"; //엑셀 시트명

		String header_e[]={"upYN", "detcode", "receipt_no", "yyyy", "code_bran", "code_kind", "edutest_name", "code_certifi", "oper_name"
				,"oper_id"
				,"oper_birth"
				, "oper_lic_no","member", "company_tel", "oper_hp", "operemail", "oper_comp_name1", "code_post", "comp_add1_1", "comp_add1_2", "comp_name2", "comp_add2_1"
				, "comp_add2_2", "comp_name3", "comp_add3_1", "comp_add3_2", "comp_name4", "comp_add4_1", "comp_add4_2", "comp_name5", "comp_add5_1", "comp_add5_2"
				, "code_great", "code_use", "trust_name", "code_operation", "result_end_dt", "start_dt", "end_dt", "oper_state", "receipt_dt", "person_yn"
				, "code_pay_flag", "notice"
				}; //헤더 영문
		String header_k[]={"*신규/갱신(i/u)", "*Key", "(접수번호)", "*교육년도", "(교육주최)", "(교육종류)", "(내용)", "(자격증구분)", "*이름"
				,"*아이디"
				,"*생년월일"
				, "*면허번호","(회원구분)", "*근무처전화", "*핸드폰", "*이메일", "*근무처명1", "*우편번호_1", "(지역_1)", "*주소_1", "(근무처명2)", "(지역_2)"
				, "(주소_2)", "(근무처명3)", "(지역_3)", "(주소_3)", "(근무처명4)", "(지역_4)", "(주소_4)", "(근무처명5)", "(지역_5)", "(주소_5)"
				, "(근무처대분류)", "(운영형태)", "(위탁업체명)", "(대상자)", "(유효일자)", "(유예시작일)", "(유예마지막일)", "(진행상태)", "(신청일)", "(회원코드)"
				, "(결제방법)","(통지번호)"
				}; //헤더 국문
		int c_size[]={15, 15,  8,  8, 15, 20, 40, 10, 12, 15, 15
				, 10, 20, 13, 15, 30, 40,  8,  6, 20, 10, 10
				,  6, 20, 10,  6, 20, 10,  6, 20, 10,  6
				, 15, 10, 10, 12, 12, 10, 10, 10, 10, 10
				,  8, 20};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null){
		

			map.put("nstart", request.getParameter("nstart"));
			map.put("nend"	, request.getParameter("nend"));
		
//			if(request.getParameter("yyyy1"		)!=null)map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
//			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
//			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
//			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
//			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1",        request.getParameter("edutest_name1"));		//내용
			
			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
			if(request.getParameter("oper_birth1"		)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
			if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
			if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
			if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
			if(request.getParameter("sel") != null)		map.put("sel",       	"Y");			

			map.put("check", request.getParameter("check"));
			
		}
		
		List<Map> examresultExcel=dao.examresultExcel(map,true);
		//-----------여기까지 변경---------------------------------------------------------------------------------------
		
		//이 이하는 변경할 필요 없음
		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
		CommonUtil.makeExcelFile(examresultExcel, filename, s_name,header_e,header_k,c_size);
		
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
	

/*
 * 작업명 : Home>교육>응시결과처리
 * 작업자 : 김성훈
 * 작업일 : 2012.11.14
 * 작   업 : memberpers_no		주민번호 체크
 */
public ActionForward pers_check2(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

	//전체건수 구해서 넘긴다.
	Map map = new HashMap();
	educationDao dao=new educationDao();
	
	//조건 입력
	// 변수에 값 받아서 넣음
	if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
	if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
	if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
	if(request.getParameter("edutest_name1"			)!=null)map.put("detcode1"			, request.getParameter("edutest_name1"		));	//승인여부
	if(request.getParameter("cert"			)!=null){
		map.put("cert"			, "Y");	//승인여부
	}else{
		if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분				
	}
		
	if("ExamResult".equals(request.getParameter("paramfrom"))){
		map.clear();
		
		if(request.getParameter("yyyy1")!=null) map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
		if(request.getParameter("code_bran1")!=null) map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
		if(request.getParameter("code_kind1")!=null) map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
		if(request.getParameter("edutest_name1")!=null) map.put("detcode1",        		request.getParameter("edutest_name1"));										//승인여부
	
		if("Y".equals(request.getParameter("cert"))){
			map.put("cert",        	"Y");
		}else{
			if(request.getParameter("code_certifi1")!=null) map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
			if(request.getParameter("oper_name1")!=null) map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
			if(request.getParameter("oper_lic_no1")!=null) map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
			if(request.getParameter("oper_hp1")!=null) map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
			if(request.getParameter("oper_comp_name11")!=null) map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
			if(request.getParameter("code_operation1")!=null) map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
			if(request.getParameter("result_point1")!=null) map.put("result_point1",        request.getParameter("result_point1"));									//점수
	
			if(request.getParameter("attend_cnt1")!=null) map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
			if(request.getParameter("time_cnt1")!=null) map.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
			if(request.getParameter("result_state1")!=null) map.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
			if(request.getParameter("oper_email1")!=null) map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
			if(request.getParameter("oper_state1")!=null) map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
			if(request.getParameter("result_no1")!=null) map.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
		}
	}

	//여기서 구하고 넘긴다.
	List<Map> count = dao.examsendcnt(map);
	int ncount = ((Integer)(count.get(0).get("cnt"))).intValue();

	// 변수에 값 받아서 넣음 (jsp로 보내서 다시 포워딩할때 사용)	
	String param = "";
	if(request.getParameter("edutest_name1"		)!=null)param+="&edutest_name1="	+ request.getParameter("edutest_name1"	);	//내용
	if(request.getParameter("yyyy1"				)!=null)param+="&yyyy1="			+ request.getParameter("yyyy1"			);	//교육년도
	if(request.getParameter("code_bran1"		)!=null)param+="&code_bran1="		+ request.getParameter("code_bran1"		);	//교육주최
	if(request.getParameter("code_kind1"		)!=null)param+="&code_kind1="		+ request.getParameter("code_kind1"		);	//교육종류
	if(request.getParameter("code_certifi1"		)!=null)param+="&code_certifi1="	+ request.getParameter("code_certifi1"	);	//자격증 구분	
	if(request.getParameter("cert"			)!=null)param+="&cert=Y";
	
	
	if("ExamResult".equals(request.getParameter("paramfrom"))){
		param = "";
		if(request.getParameter("yyyy1")!=null)	param+="&yyyy1="+request.getParameter("yyyy1");
		if(request.getParameter("code_bran1")!=null)	param+="&code_bran1="+request.getParameter("code_bran1");
		if(request.getParameter("code_kind1")!=null)	param+="&code_kind1="+request.getParameter("code_kind1");
		if(request.getParameter("edutest_name1")!=null)	param+="&edutest_name1="+request.getParameter("edutest_name1");
		if(request.getParameter("code_certifi1")!=null)	param+="&code_certifi1="+request.getParameter("code_certifi1");
		if(request.getParameter("oper_name1")!=null)	param+="&oper_name1="+request.getParameter("oper_name1");
		if(request.getParameter("oper_lic_no1")!=null)	param+="&oper_lic_no1="+request.getParameter("oper_lic_no1");
		if(request.getParameter("oper_hp1")!=null)	param+="&oper_hp1="+request.getParameter("oper_hp1");
		if(request.getParameter("oper_email1")!=null)	param+="&oper_email1="+request.getParameter("oper_email1");
		if(request.getParameter("code_operation1")!=null)	param+="&code_operation1="+request.getParameter("code_operation1");
		if(request.getParameter("oper_state1")!=null)	param+="&oper_state1="+request.getParameter("oper_state1");
		if(request.getParameter("oper_comp_name11")!=null)	param+="&oper_comp_name11="+request.getParameter("oper_comp_name11");
		if(request.getParameter("result_point1")!=null)	param+="&result_point1="+request.getParameter("result_point1");
		if(request.getParameter("attend_cnt1")!=null)	param+="&attend_cnt1="+request.getParameter("attend_cnt1");
		if(request.getParameter("time_cnt1")!=null)	param+="&time_cnt1="+request.getParameter("time_cnt1");
		if(request.getParameter("result_no1")!=null)	param+="&result_no1="+request.getParameter("result_no1");
		if(request.getParameter("result_state1")!=null)	param+="&result_state1="+request.getParameter("result_state1");
		if(request.getParameter("cert")!=null)	param+="&cert="+request.getParameter("cert");
		param+="&paramfrom="+request.getParameter("paramfrom");
	}
	
	
	//검색 변수 넘김
	request.setAttribute("param", param);
	//전체 개수 넘김
	request.setAttribute("ncount", ncount);

	request.setAttribute("url", "education2");
	
return (mapping.findForward("pers_check"));
		
}

//응시결과처리 엑셀 저장
	public void examSendListDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		Map map1=new HashMap();
		educationDao dao = new educationDao(); 
		memberStateDao dao1=new memberStateDao(); 
		HttpSession session=request.getSession();
		String format="yyyyMMdd";
		SimpleDateFormat sdf=new SimpleDateFormat(format, Locale.KOREA);
		String date=sdf.format(new java.util.Date()); //현재 날짜
		
		String user=session.getAttribute("G_ID").toString(); //유저ID
				
		//여기부터 개발자 변경 필요
		String name="examSendList"; //프로그램명
		String s_name="응시결과처리"; //엑셀 시트명
		String header_e[]={"upYN","detcode","edutest_name","receipt_no", "oper_name","attend_cnt","time_cnt"
				,"result_point","result_no", "oper_lic_no",
//				JUMIN_DEL
//				"oper_no",
				"oper_birth",
				"oper_comp_name1","company_tel","code_post","oper_comp_add1","oper_comp_name2","oper_comp_add2","oper_comp_name3","oper_comp_add3","oper_comp_name4","oper_comp_add4","oper_comp_name5","oper_comp_add5","oper_hp","operemail","code_great","code_use","trust_name","yyyy","code_bran","code_kind","code_certifi","code_operation","oper_state","result_state","point_manage"}; //헤더 영문
		String header_k[]={"*신규/갱신(i/u)","*Key","(내용)","*접수번호", "(이름)", "*출석일수", "*이수시간", "*점수","발급번호", "(면허번호)",
//				JUMIN_DEL
//				"(주민번호)",
				"(생년월일)",
				"(근무처1)","(근무처전화번호)","(우편번호)","(근무처주소1)", "(근무처2)","(근무처주소2)", "(근무처3)","(근무처주소3)", "(근무처4)","(근무처주소4)", "(근무처5)","(근무처주소5)","(핸드폰)","(이메일)","(근무처대분류)","(운영형태)","(위탁업체명)","(교육년도)","(교육주최)","(교육종류)","(자격증구분)","(대상자)","(결제여부)","(진행상태)","(점수관리)"}; //헤더 국문
		int c_size[]={15,15,39,10,10,8,8,8,10,9,15,31,12,10,50,31,50,31,50,31,50,31,50,15,25,15,15,15,8,22,10,10,10,10,10,10};  //열 넓이를 위한 배열
		
		//map1에 엑셀 로그 테이블에 넣기 위한 파라미터를 넣어준다
		map1.put("prog_name", name);
		map1.put("create_user", user);
		map1.put("sheet_name", s_name);
		
		if(request.getParameter("yyyy1") != null||request.getParameter("code_bran1") != null||request.getParameter("code_kind1") != null){

			map.put("nstart", request.getParameter("nstart"));
			map.put("nend"	, request.getParameter("nend"));

			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류			
			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1",        request.getParameter("edutest_name1"));		//내용
			if(request.getParameter("cert"		)!=null){
				map.put("cert","Y");
			}else{
				if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
			}								
			
			
			map.put("check", request.getParameter("check"));
			
			if("ExamResult".equals(request.getParameter("paramfrom"))){
				map.clear();
				
				map.put("nstart", request.getParameter("nstart"));
				map.put("nend"	, request.getParameter("nend"));
				
				map.put("paramfrom"	, request.getParameter("paramfrom"));
				
				if(request.getParameter("yyyy1")!=null) map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
				if(request.getParameter("code_bran1")!=null) map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
				if(request.getParameter("code_kind1")!=null) map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
				if(request.getParameter("edutest_name1")!=null) map.put("edutest_name1",        		request.getParameter("edutest_name1"));										//승인여부
			
				if("Y".equals(request.getParameter("cert"))){
					map.put("cert",        	"Y");
				}else{
					if(request.getParameter("code_certifi1")!=null) map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
					if(request.getParameter("oper_name1")!=null) map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
					if(request.getParameter("oper_lic_no1")!=null) map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
					if(request.getParameter("oper_hp1")!=null) map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
					if(request.getParameter("oper_comp_name11")!=null) map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
					if(request.getParameter("code_operation1")!=null) map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
					if(request.getParameter("result_point1")!=null) map.put("result_point1",        request.getParameter("result_point1"));									//점수
			
					if(request.getParameter("attend_cnt1")!=null) map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
					if(request.getParameter("time_cnt1")!=null) map.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
					if(request.getParameter("result_state1")!=null) map.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
					if(request.getParameter("oper_email1")!=null) map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
					if(request.getParameter("oper_state1")!=null) map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
					if(request.getParameter("result_no1")!=null) map.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
				}
			}
		}
		
//		List<Map> selectexamExcel=dao.selectexam2(map);
		List<Map> selectexamExcel=dao.selectexamExcel(map,true);
		//-----------여기까지 변경---------------------------------------------------------------------------------------
		
		//이 이하는 변경할 필요 없음
		int btnCnt = Integer.parseInt(request.getParameter("btnCnt"))+1;
		String filename=date+"_"+user+"_"+name+"_"+btnCnt+".xls"; //생성될 엑셀 파일이름 
		CommonUtil.makeExcelFile(selectexamExcel, filename, s_name,header_e,header_k,c_size);
		
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
    	
    	String oper_hp   = request.getParameter("oper_hp");    	
    	String [] operhp = oper_hp.split(","); 
    	String oper_name = URLDecoder.decode(request.getParameter("oper_name"),"UTF-8");
    	String [] opername = oper_name.split(",");
    	
		if("Y".equals(request.getParameter("edutake_paramok"		))){
			if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
				List<String> hp_infos_org =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
				List<List<String>> hp_infos = new ArrayList<List<String>>();
				
				for(String item : hp_infos_org){
					List<String> hp_info =  Arrays.asList(item.split("_"));
					hp_infos.add(hp_info);
				}
				Map map1 = new HashMap();
				map1.put("receipt_infos", hp_infos);
				
		    	educationDao dao = new educationDao();
		    	List<Map> result= dao.selecteducationBatch(map1,true);
		    	
		    	StringBuffer oper_hpArr = new StringBuffer(); 
		    	StringBuffer oper_nameArr = new StringBuffer(); 
		    	for(int i=0; i<result.size(); i++){
		    		String oper_hp_tmp = (String) result.get(i).get("oper_hp");
		    		String oper_name_tmp = (String) result.get(i).get("oper_name");
		    		if(oper_hp_tmp!=null && !"".equals(oper_hp_tmp)){
		    			if(oper_hpArr.length()>0) oper_hpArr.append(","); oper_hpArr.append(oper_hp_tmp);
		    			if(oper_nameArr.length()>0) oper_nameArr.append(","); oper_nameArr.append(oper_name_tmp);
		    		}
		    	}   
		    	oper_hp   = oper_hpArr.toString();    	
		    	oper_name = oper_nameArr.toString();
		    	operhp = oper_hp.split(","); 
		    	opername = oper_name.split(",");
			}
		}
		else if("Y".equals(request.getParameter("exam_paramok"		))){
			if(!"".equals(StringUtil.NVL(request.getParameter("hp_infos")))){
				List<String> hp_infos_org =  Arrays.asList(request.getParameter("hp_infos"	).split("__"));
				List<List<String>> hp_infos = new ArrayList<List<String>>();
				
				for(String item : hp_infos_org){
					List<String> hp_info =  Arrays.asList(item.split("_"));
					hp_infos.add(hp_info);
				}
				Map map1 = new HashMap();
				map1.put("receipt_infos", hp_infos);
				
				educationDao dao = new educationDao();
				List<Map> result= dao.selecteducationBatch(map1,true);
				
				StringBuffer oper_hpArr = new StringBuffer(); 
				StringBuffer oper_nameArr = new StringBuffer(); 
				for(int i=0; i<result.size(); i++){
					String oper_hp_tmp = (String) result.get(i).get("oper_hp");
					String oper_name_tmp = (String) result.get(i).get("oper_name");
					if(oper_hp_tmp!=null && !"".equals(oper_hp_tmp)){
						if(oper_hpArr.length()>0) oper_hpArr.append(","); oper_hpArr.append(oper_hp_tmp);
						if(oper_nameArr.length()>0) oper_nameArr.append(","); oper_nameArr.append(oper_name_tmp);
					}
				}   
				oper_hp   = oper_hpArr.toString();    	
				oper_name = oper_nameArr.toString();
				operhp = oper_hp.split(","); 
				opername = oper_name.split(",");
			}
		}
    	
    	int rtn=0;
    	for(int i=0;i<operhp.length;i++){
    		if(operhp[i].length()>9){
    			map.put("pers_hp",                   operhp[i]); 
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                request.getParameter("send_phone"));      		
        		map.put("register",                  session.getAttribute("G_NAME"));
        		map.put("etc1",                      session.getAttribute("G_ID"));
        		map.put("etc2",                      session.getAttribute("G_BRAN"));
        		map.put("etc3",                      opername[i]);
        		System.out.println("opername[i]=============>"+opername[i]);
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
        		rtn++;
    		}    		
    	}     	
    	request.setAttribute("msg", rtn+"건 저장됐습니다.");
		return (mapping.findForward("umsDataall"));				
	}
	
	public ActionForward umsDataall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		Map map = new HashMap();    		
    	HttpSession session=request.getSession(); 
    	documentDao dao = new documentDao();    	
    	educationDao dao1 = new educationDao();
    	
    	map.put("nstart", 0);
		map.put("nend", 100000000);
		
    	if(!request.getParameter("yyyy1").equals("")){
    		map.put("yyyy1",              request.getParameter("yyyy1"));
    	}		
		if(!request.getParameter("code_bran1").equals("")){
			map.put("code_bran1",                   request.getParameter("code_bran1"));
		}		
		if(!request.getParameter("code_kind1").equals("")) {
			map.put("code_kind1",               request.getParameter("code_kind1"));
		}	
		if(!request.getParameter("edutest_name1").equals("")){
			map.put("detcode1",                 request.getParameter("edutest_name1"));
		}
		/*if(!request.getParameter("code_operation1").equals("")&&!request.getParameter("code_operation1").equals("0")){
			map.put("code_operation1",                 request.getParameter("code_operation1"));
		}
		if(!request.getParameter("oper_state1").equals("")){
			map.put("oper_state1",                 request.getParameter("oper_state1"));
		}*/
		
		
		if("Y".equals(request.getParameter("edutake_paramok"		))){
			if(request.getParameter("edutest_name1"		)!=null)map.put("edutest_name1"		, URLDecoder.decode(request.getParameter("edutest_name1"	),"UTF-8"));	//내용
			if(request.getParameter("oper_name1"		)!=null)map.put("oper_name1"		, URLDecoder.decode(request.getParameter("oper_name1"		),"UTF-8"));	//이름
			if(request.getParameter("oper_comp_name11"	)!=null)map.put("oper_comp_name11"	, URLDecoder.decode(request.getParameter("oper_comp_name11"	),"UTF-8"));	//근무처명
			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
			if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분
			if(request.getParameter("oper_birth1"		)!=null)map.put("oper_birth1"			, request.getParameter("oper_birth1"		));	//생년월일
			if(request.getParameter("oper_lic_no1"		)!=null)map.put("oper_lic_no1"		, request.getParameter("oper_lic_no1"	));	//면허번호
			if(request.getParameter("oper_hp1"			)!=null)map.put("oper_hp1"			, request.getParameter("oper_hp1"		));	//핸드폰
			if(request.getParameter("code_operation1"	)!=null)map.put("code_operation1"	, request.getParameter("code_operation1"));	//대상자
			if(request.getParameter("result_end_dt1"	)!=null)map.put("result_end_dt1"	, request.getParameter("result_end_dt1"	));	//유효일자
			if(request.getParameter("start_dt1"			)!=null)map.put("start_dt1"			, request.getParameter("start_dt1"		));	//유예시작일
			if(request.getParameter("end_dt1"			)!=null)map.put("end_dt1"			, request.getParameter("end_dt1"		));	//유예마지막일
			if(request.getParameter("oper_state1"		)!=null)map.put("oper_state1"		, request.getParameter("oper_state1"	));	//진행상태
			if(request.getParameter("code_pay_flag1"	)!=null)map.put("code_pay_flag1"	, request.getParameter("code_pay_flag1"	));	//결제방법
			if(request.getParameter("receipt_dt1"		)!=null)map.put("receipt_dt1"		, request.getParameter("receipt_dt1"	));	//신청일자
			if(request.getParameter("code_seq1"			)!=null)map.put("code_seq1"			, request.getParameter("code_seq1"		));	//순번
			if(request.getParameter("detcode1"			)!=null)map.put("detcode1"			, request.getParameter("detcode1"		));	//승인여부
			if(request.getParameter("sel") != null)		map.put("sel",       	"Y");
		}
		else if("Y".equals(request.getParameter("exam_paramok"		))){
			map.clear();
	    	map.put("nstart", 0);
			map.put("nend", 100000000);
			
//			if(request.getParameter("yyyy1"				)!=null)map.put("yyyy1"				, request.getParameter("yyyy1"			));	//교육년도
//			if(request.getParameter("code_bran1"		)!=null)map.put("code_bran1"		, request.getParameter("code_bran1"		));	//교육주최
//			if(request.getParameter("code_kind1"		)!=null)map.put("code_kind1"		, request.getParameter("code_kind1"		));	//교육종류
//			if(request.getParameter("edutest_name1"			)!=null)map.put("detcode1"			, request.getParameter("edutest_name1"		));	//승인여부
//			if(request.getParameter("cert"			)!=null){
//				map.put("cert"			, "Y");	//승인여부
//			}else{
//				if(request.getParameter("code_certifi1"		)!=null)map.put("code_certifi1"		, request.getParameter("code_certifi1"	));	//자격증 구분				
//			}
			
			if(request.getParameter("yyyy1")!=null) map.put("yyyy1",        		request.getParameter("yyyy1"));											//교육년도
			if(request.getParameter("code_bran1")!=null) map.put("code_bran1",        	request.getParameter("code_bran1"));									//교육주최
			if(request.getParameter("code_kind1")!=null) map.put("code_kind1",        	request.getParameter("code_kind1"));									//교육종류
			if(request.getParameter("edutest_name1")!=null) map.put("detcode1",        		request.getParameter("edutest_name1"));										//승인여부
		
			if("Y".equals(request.getParameter("cert"))){
				map.put("cert",        	"Y");
			}else{
				if(request.getParameter("code_certifi1")!=null) map.put("code_certifi1",        request.getParameter("code_certifi1"));									//자격증 구분
				if(request.getParameter("oper_name1")!=null) map.put("oper_name1",        	URLDecoder.decode(request.getParameter("oper_name1"),"UTF-8"));			//이름
				if(request.getParameter("oper_lic_no1")!=null) map.put("oper_lic_no1",       	request.getParameter("oper_lic_no1"));									//면허번호
				if(request.getParameter("oper_hp1")!=null) map.put("oper_hp1",       		request.getParameter("oper_hp1"));										//핸드폰
				if(request.getParameter("oper_comp_name11")!=null) map.put("oper_comp_name11",     URLDecoder.decode(request.getParameter("oper_comp_name11"),"UTF-8"));	//근무처명
				if(request.getParameter("code_operation1")!=null) map.put("code_operation1",      request.getParameter("code_operation1"));								//대상자
				if(request.getParameter("result_point1")!=null) map.put("result_point1",        request.getParameter("result_point1"));									//점수
		
				if(request.getParameter("attend_cnt1")!=null) map.put("attend_cnt1",       	request.getParameter("attend_cnt1"));									//출석일수
				if(request.getParameter("time_cnt1")!=null) map.put("time_cnt1",       		request.getParameter("time_cnt1"));										//이수시간
				if(request.getParameter("result_state1")!=null) map.put("result_state1",       	request.getParameter("result_state1"));									//진행상태
				if(request.getParameter("oper_email1")!=null) map.put("oper_email1",       	request.getParameter("oper_email1"));									//이메일
				if(request.getParameter("oper_state1")!=null) map.put("oper_state1",       	request.getParameter("oper_state1"));									//결제여부
				if(request.getParameter("result_no1")!=null) map.put("result_no1",       	request.getParameter("result_no1"));									//발급번호
			}
		}

		List<Map> result= new ArrayList();
		if(request.getParameter("gubun")!=null){
			result=dao1.selectexam2(map,true);
		}else if(request.getParameter("gubun")==null&&!request.getParameter("code_certifi1").equals("")){
			map.put("code_certifi1",                     request.getParameter("code_certifi1"));
			
			if(!"Y".equals(request.getParameter("edutake_paramok"		))){
				map.put("sel", "Y");
			}
			result = dao1.selecteducation(map,true);
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
    	        
    	int rtn=0;
    	for(int i=0; i<result.size(); i++){
    		if(result.get(i).get("oper_hp").toString().length()>9){
    			map.put("pers_hp" ,                  result.get(i).get("oper_hp"));
        		map.put("msg_type",                  msg_type);
        		map.put("subject",                   subject);
        	    map.put("msg_body",                  msg_body);     		
        		map.put("send_phone",                request.getParameter("send_phone"));      		
        		map.put("register",                  session.getAttribute("G_NAME"));
        		map.put("etc1",                      session.getAttribute("G_ID"));
        		map.put("etc2",                      session.getAttribute("G_BRAN"));
        		map.put("etc3",                      result.get(i).get("oper_name"));
        		map.put("umscnt"		, i+1);    		
        		//예약발송
        		int n = 0;
        		if( request_time.length() > 0 ) {
    				map.put("request_time", request_time);
        			n = dao.insertumsResultData(map);
        		}else {
        			n = dao.insertumsData(map);
        		}
        		rtn++;
    		}    		
    	}   
    	request.setAttribute("msg", rtn+"건 저장됐습니다.");
		return (mapping.findForward("umsDataall"));				
	}
	
	public ActionForward fDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return (mapping.findForward("download"));
	}
	
}
	