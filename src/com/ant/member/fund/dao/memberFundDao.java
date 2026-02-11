package com.ant.member.fund.dao;

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

import java.util.List;
import java.util.Map;


import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class memberFundDao {

	
	 /*
	  * 작업명 : Home>회원>기부/기금 현황
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.01
	  * 작  업 : getMemberFundCount	예수금현황 페이징 카운터
	  */
	public List<Map> getMemberFundCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberFundCount", map);
	}
	
	 /*
	  * 작업명 : Home>회원>기부/기금 현황
	  * 작업자 : 김성훈
	  * 작업일 : 2012.11.01
	  * 작  업 : getMemberFundList	예수금현황 조회
	  */
	public List<Map> getMemberFundList(Map map) throws SQLException {
		return getMemberFundList(map,false);
	}
	public List<Map> getMemberFundList(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getMemberFundList", map);
	}

}