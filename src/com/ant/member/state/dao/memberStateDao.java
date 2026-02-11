package com.ant.member.state.dao;

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
 * 프로그램명 : memberStateDao (회원 > 현황)
 * 제작자 : 김성훈
 * 제작일 : 2012.09.10
 * 설   명 : 회원>현황 이하 Dao 모음
 */

public class memberStateDao {

	/*
	  * 작업명 : 엑셀 파일 로그
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.12
	  */
	public List<Map> iExcelLog(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iExcelLog", map);
	}
	
	 /*
	  * 작업명 : Home>회원>현황>근무처분류별 (페이징용 카운터, 조회) 메뉴. 2
	  * 작업자 : 김성훈
	  * 작업일 : 2012.09.13
	  * 작  업 : getCompanyMemberStatusCount	근무처 분류별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	  *			 getCompanyMemberStatusList		근무처 분류별 현황 리스트 조회
	  */
	public List<Map> getCompanyStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCompanyStateCount", map);
	}
	public List<Map> getCompanyStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCompanyStateList", map);
	}
	
	 /*
	  * 작업명 : Home>회원>현황>운영형태별
	  * 작업자 : 이정기
	  * 작업일 : 2012.09.10
	  * 작  업 : getCompanyTypeList		근무처 분류별 현황 리스트 조회
	  */
	public List<Map> getCodeUseMemberList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCodeUseMemberList", map);
	}
	
	/*
	 * 작업명 : Home>회원>현황>위탁업체 개인별
	 * 작업자 : 윤석희
	 * 작업일 : 2012.09.10
	 * 작  업 : getSubCodePersonCount	위탁업체 개인별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *		    getSubCodePersonList	위탁업체 개인별 현황 리스트 조회
	 */
	public List<Map> getSubCodePersonCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSubCodePersonCount", map);
	}
	public List<Map> getSubCodePersonList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSubCodePersonList", map);
	}
	
	
	
	/*
	 * 작업명 : Home>회원>현황>산하단체소속별 (페이징용 카운터, 조회) 메뉴.12
	 * 작업자 : 김성훈
	 * 작업일 : 2012.09.10
	 * 작  업 : getSubCodeStateCount	산하단체소속별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *		    getSubCodeStateList		산하단체소속별 현황 리스트 조회
	 */
	public List<Map> getSubCodeStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSubCodeStateCount", map);
	}
	public List<Map> getSubCodeStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSubCodeStateList", map);
	}
	
	

	/*
	 * 작업명 : Home>회원>현황>피 급식자별 (페이징용 카운터, 조회) 메뉴.13
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.05
	 * 작  업 : getMealStateCount	피 급식자별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *			getMealStateList	피 급식자별 현황 리스트 조회
	 */
	public List<Map> getMealStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealStateCount", map);
	}
	public List<Map> getMealStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealStateList", map);
	}

	/*
	 * 작업명 : Home>회원>현황>영양사 연봉별 (페이징용 카운터, 조회) 메뉴.14
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.05
	 * 작  업 : getYearPayStateCount	영양사 연봉별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *			getYearPayStateList		영양사 연봉별 현황 리스트 조회
	 */
	public List<Map> getYearPayStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getYearPayStateCount", map);
	}
	public List<Map> getYearPayStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getYearPayStateList", map);
	}

	/*
	 * 작업명 : Home>회원>현황>급식 종사자별 (페이징용 카운터, 조회) 메뉴.15
	 * 작업자 : 김성훈
	 * 작업일 : 2012..
	 * 작  업 : getMealEmployStateCount	급식 종사자별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *			getMealEmployStateList	급식 종사자별 현황 리스트 조회
	 */
	public List<Map> getMealEmployStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealEmployStateCount", map);
	}
	public List<Map> getMealEmployStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealEmployStateList", map);
	}

	/*
	 * 작업명 : Home>회원>현황>영양사 면허수당별 (페이징용 카운터, 조회) 메뉴.16
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.06
	 * 작  업 : getLicPayStateCount	영양사 면허수당별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *			getLicPayStateList	영양사 면허수당별 현황 리스트 조회
	 */
	public List<Map> getLicPayStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getLicPayStateCount", map);
	}
	public List<Map> getLicPayStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getLicPayStateList", map);
	}

	/*
	 * 작업명 : Home>회원>현황>1식 재료비별 (페이징용 카운터, 조회) 메뉴.17
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.06
	 * 작  업 : getCompanyMealStateCount	1식 재료비별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *			getCompanyMealStateList		1식 재료비별 현황 리스트 조회
	 */
	public List<Map> getCompanyMealStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCompanyMealStateCount", map);
	}
	public List<Map> getCompanyMealStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCompanyMealStateList", map);
	}

	/*
	 * 작업명 : Home>회원>현황>겸직별 (페이징용 카운터, 조회) 메뉴.18
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.06
	 * 작  업 : getMultyStateCount	겸직별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *			getMultyStateList	겸직별 현황 리스트 조회
	 */
	public List<Map> getMultyStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMultyStateCount", map);
	}
	public List<Map> getMultyStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMultyStateList", map);
	}

	/*
	 * 작업명 : Home>회원>현황>급식인원별
	 * 작업자 : 김성훈
	 * 작업일 : 2012.11.06
	 * 작  업 : getMealNumberStateCount	급식인원별 현황 리스트 출력시 페이징 처리를 위한 전체 갯수 얻기
	 *			getMealNumberStateList	급식인원별 현황 리스트 조회
	 */
	public List<Map> getMealCountStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealNumberStateCount", map);
	}
	public List<Map> getMealCountStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealNumberStateList", map);
	}
	public List<Map> getMealCountStateList_1(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealNumberStateList_1", map);
	}
	public List<Map> getMealCountStateList_2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealNumberStateList_2", map);
	}
	public List<Map> getMealCountStateList_3(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealNumberStateList_3", map);
	}
	public List<Map> getMealCountStateList_4(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMealNumberStateList_4", map);
	}


	
	
	
	
	
	
	 /*
	  * 작업명 : Home>회원>현황>고용형태별 직급별 인원수집계
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.09
	  */
	public int getJLevelStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getJLevelStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getJLevelStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getJLevelStateList", map);
	}
	
	 /*
	  * 작업명 : Home>회원>현황>고용형태별 직렬별 인원수집계
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.09
	  */
	public int getJLineStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getJLineStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getJLineStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getJLineStateList", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>자격증소지별 인원수집계
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.09
	  */
	public int getCertifiStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getCertifiStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getCertifiStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCertifiStateList", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>고용형태별 최종학위별 인원수집계
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.09
	  */
	public int getUniverStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getUniverStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getUniverStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getUniverStateList", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>공동조리별 인원수집계
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.09
	  */
	public int getCookStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getCookStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getCookStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCookStateList", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>공동관리별 인원수집계
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.09
	  */
	public int getConStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getConStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getConStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getConStateList", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>위탁업체 업체별
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.09
	  */
	public int getTrustCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("trustCnt", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getTrustList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("trustList", map);
	}
	public List<Map> getTrustListAll(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("trustListAll", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>지부별 회원-분과별
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.15
	  */
	public int getBigStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getBigStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getBigStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getBigStateList", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>지부별 회원-소분류별
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.15
	  */
	public int getSmallStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getSmallStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getSmallStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getSmallStateList", map);
	}
	
	/*
	  * 작업명 : Home>회원>현황>지부별 회원-회원구분별
	  * 작업자 : 박상모
	  * 작업일 : 2012.10.15
	  */
	public int getMemGubunStateCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> list = sqlMap.queryForList("getMemGubunStateCount", map);
		int ncount = ((Integer)(list.get(0).get("ncount"))).intValue();
		return ncount;
	}
	public List<Map> getMemGubunStateList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemGubunStateList", map);
	}
	
	public List<Map> selectAll(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectAll", map);
	}
	public List<Map> sMember(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sMember", map);
	}
	public List<Map> sMemberCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sMemberCnt", map);
	}
}