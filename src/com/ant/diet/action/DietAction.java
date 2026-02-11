package com.ant.diet.action;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ant.common.util.StringUtil;
import com.ant.diet.dao.DietDao;

public class DietAction extends DispatchAction {
	
	public ActionForward diet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("dietlist");
	} 
	

	public ActionForward dietlist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map map = new HashMap();
		DietDao dao=new DietDao();
		
		
		
		//조회조건 넣기
			//jqgride 용 변수 (처음에는 기본, jsp페이지에서 넘어오면 그 값이 들어간다.)
//			String page = request.getParameter("page");	// 페이지(기본값=) 1
//			String rows = request.getParameter("rows");	// 출력줄(기본값=) 10
			String page = StringUtil.NVL(request.getParameter("page"),"1");
			String rows = StringUtil.NVL(request.getParameter("rows"),"10");
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


			//검색한 카운터용 개수와 출력줄 값을 이용하여 페이지를 구한다.---**조회
			List<Map> count = dao.dietlistcnt(map);
			int ncount = ((Integer)(count.get(0).get("ncount"))).intValue();

			int ntotpage = (ncount/nrows)+1;



			//화면에 출력할 값을 검색한다.-------------------------------**조회
			List<Map> list=dao.dietlist(map);
			
			//jsp에 넘겨줄 result값을 만든다.
			StringBuffer result = new StringBuffer();
		
			result.append("{\"page\":\""	+npage		+"\",");		
			result.append("\"total\":\"" 	+ntotpage	+"\",");
			result.append("\"records\":\""	+ncount		+"\",");
			result.append("\"rows\":[");

			//서브 셀렉트 검사
			
			for(int i=0;i<list.size();i++){

				if(i>0) result.append(",");
				
				result.append("{\"id\":\"" + (i+1) + "\",\"cell\":[");
				
				result.append(getcolval(list,i,"ncount"));
				result.append(getcolval(list,i,"qa_Serial_no"));
				result.append(getcolval(list,i,"qa_Depth_no"));
				result.append(getcolval(list,i,"qa_Parent_no"));
				result.append(getcolval(list,i,"qa_Url"));
				result.append(getcolval(list,i,"qa_address"));
				result.append(getcolval(list,i,"qa_User_name"));
				result.append(getcolval(list,i,"qa_wrt_date"));
				result.append(getcolval(list,i,"qa_wrt_time"));
//				result.append(getcolval(list,i,"qa_title"));
//				result.append(getcolval(list,i,"qa_content"));
				result.append(getcolval(list,i,"qa_read_no"));
				result.append(getcolval(list,i,"qa_passwordq"));
				result.append(getcolval(list,i,"Status",true));

				result.append("]}");
			}
			result.append("]}");

			request.setAttribute("result",result);
			
		return (mapping.findForward("ajaxout"));
	}

	private String getcol(List<Map> list, int i, String colnm) throws Exception {
		return colnm + ":" + getcolval(list,i,colnm);
	}
	
	private String getcol(List<Map> list, int i, String colnm, boolean last) throws Exception {
		return colnm + ":" + getcolval(list,i,colnm,last);
	}
	
	private String getcolval(List<Map> list, int i, String colnm) throws Exception {
		return getcolval(list,i,colnm,false);
	}
	
	private String getcolval(List<Map> list, int i, String colnm, boolean last) throws Exception {
		return "\"" + getcolvalNoQuot(list,i,colnm) + "\"" + (last?"":",");
	}
	
	private String getcolvalNoQuot(List<Map> list, int i, String colnm) throws Exception {
		return list.get(i).get(colnm) == null ? "" : "" + list.get(i).get(colnm);
	}

}
	
