package com.ant.member.dues.dao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import com.ant.member.state.dao.memberStateDao;

import java.util.List;
import java.util.Map;


import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class memberDuesDao {

	
	 /* 3.5.0
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.28
	  * 작  업 : 작  업 :  getDuesUmsSeq 회비처리 seq맥스값 얻기
	 * 얘는 맥스값 없이 그냥 진행한다.
	  */


	 /* 3.5.1
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	 * 작  업 : getDuesUmsForCnt	회비처리 개수
	  */
	public List<Map> getDuesUmsForCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getDuesUmsForCnt", map);
	}

	 /* 3.5.2
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getDuesUmsList 회비처리리스트 조회
	  */
	public List<Map> getDuesUmsList(Map map) throws SQLException {
		return getDuesUmsList(map,false);
	}
	public List<Map> getDuesUmsList(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getDuesUmsList", map);
	}
	
	 /* 3.5.3
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : setDuesUmsList	회비처리리스트 저장
	  */
	public void setDuesUmsList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setDuesUmsList", map);
	}
	
	 /* 3.5.3
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.12.04
	 * 작  업 : setDuesUmsResultList	문자예약발송리스트 저장
	  */
	public void setDuesUmsResultList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setDuesUmsResultList", map);
	}
	

	/* 3.2.1
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.26
	 * 작  업 : getMembershipCnt	회원증 발급을 위한 카운터
	 */
	public List<Map> getMembershipCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMembershipCnt", map);
	}
	
	/* 3.2.2
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.23
	 * 작  업 : getMembershipList	회원증 출력용 조회
	 *
	 * 조  회 : 회원증 출력 - 아이디
	 * 기  타 : 회원증 출력 - http://www.dietitian.or.kr/doc_form/docu_print.asp?doc_code=  뒤에 code_pers 를 붙여서 보내는데 (,로)
	 *                        한번에 보내는게 750 여건 밖에 안된다.
	 *                        전체 조회 후 500 건씩 잘라서 붙여 보내는 걸로 처리한다.
	 */
	public List<Map> getMembershipList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMembershipList", map);
	}
	
	
	
	

	
	 /* 3.3.0
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getDuesDmForSeq	DM리스트 목표 dm_print_yymm_seq맥스값 얻기
	  */
	public List<Map> getDuesDmForSeq(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getDuesDmForSeq", map);
	}

	 /* 3.3.1
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getDuesDMForCnt	DM리스트 저장할 개수 조회
	  */
	public List<Map> getDuesDMForCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getDuesDMForCnt", map);
	}

	 /* 3.3.2
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getDuesDMForList	DM리스트 저장할 목표물 조회
	  */
	public List<Map> getDuesDMForList(Map map) throws SQLException {
		return getDuesDMForList(map,false);
	}
	public List<Map> getDuesDMForList(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getDuesDMForList", map);
	}
	
	 /* 3.3.3
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : setDuesDMList		DM 발송 저장
	  */
	public void setDuesDMList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setDuesDMList", map);
	}
	
	
	
	
	
	
	
	
	
	
	
	 /*
	  * 작업명 : Home>회원>회비처리
	  * 작업자 : 김성훈
	  * 작업일 : 2012.9.26
	  * 작   업 : getMemberDuesCount	회비처리 페이징 카운터
	  */
	public List<Map> getMemberDuesCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDuesCount", map);
	}
	public List<Map> getMemberDuesPtabelCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDuesPtabelCount", map);
	}
	
	 /*
	  * 작업명 : Home>회원>회비처리
	  * 작업자 : 김성훈
	  * 작업일 : 2012.9.26
	  * 작   업 : getMemberDuesList		회비처리 리스트 조회
	  */
	public List<Map> getMemberDuesList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDuesList", map);
	}
	public List<Map> getMemberDuesPtabelList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDuesPtabelList", map);
	}
	
	 /*
	  * 작업명 : Home>회원>회비처리
	  * 작업자 : 김성훈
	  * 작업일 : 2012.10.04
	  * 작   업 : getDuesList					년회비, 평생회비 조회
	  */
	public List<Map> getDuesList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getDuesList", map);
	}
	
	 /*
	  * 작업명 : Home>회원>회비처리
	  * 작업자 : 김성훈
	  * 작업일 : 2012.10.04
	  * 작   업 : getSubDuesList				산하단체회비 조회
	  */
	public List<Map> getSubDuesList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSubDuesList", map);
	}
	
	 /*
	  * 작업명 : Home>회원>회비처리
	  * 작업자 : 김성훈
	  * 작업일 : 2012.10.08
	  * 작   업 : setDues_P					년회비, 평생회비 저장
	  */
	public void setDues_P(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setDues_P", map);
	}
	
	/* 
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.09
	 * 작   업 : setDues_h_tbl		회비 저장후 회원별회비의 등록일을 처리한다. (미확을 확인으로 바꾼다.)
	 */
	public void setDues_h_tbl(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.update("setDues_h_tbl", map);
	}

	
	/* 
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.09
	 * 작   업 : getDues_p	평생회비 예수금 수정을 위한 조회 (1차 이후 1년 지난것)
	 */
	public List<Map> getDues_p(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getDues_p", map);
	}

	/* 
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.09
	 * 작   업 : updateDues_p	평생회비 예수금 수정  (1차 이후 1년 지난것)
	 */
	public void updateDues_p(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.update("updateDues_p", map);
	}

}