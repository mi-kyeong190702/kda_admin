package com.ant.member.search.dao;

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

public class memberSearchDao {
	
	

	 /* 2.5.0
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : 작  업 :  getSearchUmsSeq 문자발송 seq맥스값 얻기
	 * 얘는 맥스값 없이 그냥 진행한다.
	  */


	 /* 2.5.1
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	 * 작  업 : getSearchUmsForCnt	문자발송 개수
	  */
	public List<Map> getSearchUmsForCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchUmsForCnt", map);
	}

	 /* 2.5.2
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getSearchUmsList 문자발송리스트 조회
	  */
	public List<Map> getSearchUmsList(Map map) throws SQLException {
		return  getSearchUmsList(map,false);
	}
	public List<Map> getSearchUmsList(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchUmsList", map);
	}
	
	 /* 2.5.3
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : setSearchUmsList	문자발송리스트 저장
	  */
	public void setSearchUmsList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setSearchUmsList", map);
	}
	
	 /* 2.5.3
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.12.04
	 * 작  업 : setSearchUmsResultList	문자예약발송리스트 저장
	  */
	public void setSearchUmsResultList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setSearchUmsResultList", map);
	}
	
	
	

	 /* 2.4.0
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getSearchSmsSeq 쪽지발송리스트 seq 얻기
	  */
	public List<Map> getSearchSmsSeq(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchSmsSeq", map);
	}

	 /* 2.4.1
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getSearchMsgForCnt	쪽지발송 개수
	  */
	public List<Map> getSearchMsgForCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchMsgForCnt", map);
	}

	 /* 2.4.2
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getSearchMsgList 쪽지발송리스트 조회
	  */
	public List<Map> getSearchMsgList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchMsgList", map);
	}
	
	 /* 2.4.3
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : setSearchSmsList		쪽지 리스트 저장
	  */
	public void setSearchSmsList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setSearchSmsList", map);
	}
	
	
	
	
	
	
	 /* 2.3.0
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getSearchDmForSeq	DM리스트 목표 dm_print_yymm_seq맥스값 얻기
	  */
	public List<Map> getSearchDmForSeq(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchDmForSeq", map);
	}

	 /* 2.3.1
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getSearchDMForCnt	DM리스트 저장할 개수 조회
	  */
	public List<Map> getSearchDMForCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchDMForCnt", map);
	}

	 /* 2.3.2
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.27
	  * 작  업 : getSearchDMForList	DM리스트 저장할 목표물 조회
	  */
	public List<Map> getSearchDMForList(Map map) throws SQLException {
		return getSearchDMForList(map,false);
	}
	public List<Map> getSearchDMForList(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSearchDMForList", map);
	}
	
	 /* 2.3.3
	 * 작업명 : Home>회원>회비처리
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.27
	 * 작  업 : setSearchDMList		DM 발송 저장
	  */
	public void setSearchDMList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setSearchDMList", map);
	}
	
	
	
	
	
	
	
	
	
	
	
	 /*
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.09.10
	  * 작   업 : getMemberSearchCnt	회원 상세조회 페이징 카운터
	  */
	public List<Map> getMemberSearchCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberSearchCount", map);
	}
	
	 /*
	  * 작업명 : Home>회원>조건검색
	  * 작업자 : 김성훈
	  * 작업일 : 2012.09.10
	  * 작   업 : getMemberSearchList	회원 상세조회
	  */
	public List<Map> getMemberSearchList(Map map) throws SQLException {
		return getMemberSearchList(map,false);
	}
	public List<Map> getMemberSearchList(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberSearchList", map);
	}
	
	
}