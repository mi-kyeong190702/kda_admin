package com.ant.member.insert.dao;

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

public class memberInsertDao {

	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.16
	 * 작   업 : getChkLicNo	라이센스 중복 검사
	 * */
	public List<Map> getChkLicNo(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getChkLicNo", map);
	}
	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.16
	 * 작   업 : getMemberCodePers	회원번호 얻기
	 * */
	public List<Map> getMemberCodePers(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberCodePers", map);
	}
	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.15
	 * 작   업 : setMemberInsertIdTbl	아이디테이블 등록
	 * */
	public void setMemberInsertIdTbl(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setMemberInsertIdTbl", map);
	}
	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.15
	 * 작   업 : setMemberInsertBasic	회원 기본정보 등록
	 * */
	public void setMemberInsertBasic(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setMemberInsertBasic", map);
	}
	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.25
	 * 작   업 : setPerson_M_history	회원히스톨정보 등록
	 * */
	public void setPerson_M_history(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setPerson_M_history", map);
	}
	
	/*
	 * 작업명 : Home>회원>회원등록
	 * 작업자 : 김성훈
	 * 작업일 : 2012.10.15
	 * 작   업 : setMemberInsertCom	회원 근무처정보 등록
	 * */
	public void setMemberInsertCom(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setMemberInsertCom", map);
	}
	
	public List<Map> getIdCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getIdCnt", map);
	}
}