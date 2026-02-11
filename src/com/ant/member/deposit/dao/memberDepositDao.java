package com.ant.member.deposit.dao;

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

/*
 * 프로그램명 : memberDepositDao (회원 > 예수금현황)
 * 제작자 : 김성훈
 * 제작일 : 2012.09.
 * 설   명 : 회원>예수금현황 Dao
 */

public class memberDepositDao {


	 /*
	  * 작업명 : Home>회원>예수금현황
	  * 작업자 : 김성훈
	  * 작업일 : 2012.09.
	  * 작   업 : getMemberDepositCnt	예수금현황 페이징 카운터
	  *           getMemberDepositList		예수금현황 조회
	  */
	public List<Map> getMemberDepositCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositCount", map);
	}
	public List<Map> getMemberDepositList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList", map);
	}
	

	
	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.24
	 * 작   업 : getMemberDepositCount_1and2	회원>예수금현황>예수금집계표(tab_1, tab_2) 페이징 카운터
	 */
	public List<Map> getMemberDepositCount_1and2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositCount_1and2", map);
	}
	
	/*
	 * 작업명 : Home>회원>예수금현황>예수금 집계표, 지부별 예수금 현황(tab_1, tab_2)
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.24
	 * 작   업 : getMemberDepositList_1and2	예수금 집계표, 지부별 예수금 현황(tab_1, tab_2) 조회 출력
	 * 기   타 : 카운터 조회하는 쿼리는 없다. 전체 20개도 안되므로 한 페이지에 출력한다.
	 */
	public List<Map> getMemberDepositList_1(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList_1", map);
	}
	public List<Map> getMemberDepositList_2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList_2", map);
	}

	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.25
	 * 작   업 : getMemberDepositCount_3	회원>예수금현황>평생회비 예수금(tab_3) 페이징 카운터
	 */
	public List<Map> getMemberDepositCount_3(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositCount_3", map);
	}

	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.25
	 * 작   업 : getMemberDepositList_3	회원>예수금현황>평생회비 예수금(tab_3) 조회 출력
	 */
	public List<Map> getMemberDepositList_3(Map map) throws SQLException {
		return getMemberDepositList_3(map,false);
	}
	public List<Map> getMemberDepositList_3(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList_3", map);
	}

	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.29
	 * 작   업 : getMemberDepositList_4	회원>예수금현황>평생회비 예수금 현황(tab_4) 조회 출력
	 */
	public List<Map> getMemberDepositList_4(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList_4", map);
	}
	


	

	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * 작  업 : getMemberDepositCount_5_1	회원>예수금현황>예수금현황_연회원(tab_5_1) 페이징용 카운터
	 */
	public List<Map> getMemberDepositCount_5_1(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositCount_5_1", map);
	}
	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * 작  업 : getMemberDepositList_5_1	회원>예수금현황>예수금현황_연회원(tab_5_1) 조회 출력
	 */
	public List<Map> getMemberDepositList_5_1(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList_5_1", map);
	}
	
	
	
	
	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * 작  업 : getMemberDepositCount_5_2	회원>예수금현황>예수금현황_평생회원(tab_5_2) 페이징용 카운터
	 */
	public List<Map> getMemberDepositCount_5_2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositCount_5_2", map);
	}
	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * 작  업 : getMemberDepositList_5_2	회원>예수금현황>예수금현황_평생회원(tab_5_2) 조회 출력
	 */
	public List<Map> getMemberDepositList_5_2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList_5_2", map);
	}
	
	
	
	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * 작  업 : getMemberDepositCount_6	회원>예수금현황>산하단체 예수금 현황(tab_6) 페이징용 카운터
	 */
	public List<Map> getMemberDepositCount_6(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositCount_6", map);
	}
	/*
	 * 작업명 : Home>회원>예수금현황
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.31
	 * 작  업 : getMemberDepositList_6	회원>예수금현황>산하단체 예수금 현황(tab_6) 조회 출력
	 */
	public List<Map> getMemberDepositList_6(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberDepositList_6", map);
	}
	
	
}