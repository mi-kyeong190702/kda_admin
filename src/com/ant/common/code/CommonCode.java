package com.ant.common.code;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

/*
 * 프로그램명 : 공통코드 관리모듈
 * 작업자 : 김성훈
 * 작업일 : 2012.09.19
 */
public class CommonCode {
	
	private static CommonCode							instance	=null;	//싱글톤
	private static List<HashMap<String, String>>	comList	=null;	//전체공통코드
	private static List<HashMap<String, String>>	subList	=null;	//산하단체 리스트
	private static List<HashMap<String, String>>	certifiList=null;	//자격증 종류 리스트
	

	private static HashMap<String, List>				comMap	=null;	//종류별로 분류한 맵
	
	
	//싱글톤 처리
	public static CommonCode getInstance() throws SQLException {
		if(instance == null) {
			
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			//comList		= sqlMap.queryForList("getComCodeList");		//전체공통코드
			//subList		= sqlMap.queryForList("getSubCodeList");		//산하단체 리스트
			//certifiList	= sqlMap.queryForList("getCertifiCodeList");	//자격증 종류 리스트
			
			instance 	= new CommonCode();
			comMap		= new HashMap();
		}
		
		return instance;
	}
	

	//전체 공통쿼리 리턴
	public static JSONArray getCodeList() throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		comList		= sqlMap.queryForList("getComCodeList");		//전체공통코드
		JSONArray jsoncode	= JSONArray.fromObject(comList);
		return jsoncode;
	}
	//산하단체 리스트 리턴
	public static JSONArray getSubList() throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		subList		= sqlMap.queryForList("getSubCodeList");		//산하단체 리스트
		JSONArray jsoncode	= JSONArray.fromObject(subList);
		return jsoncode;
	}
	//자격증 종류 리스트 리턴
	public static JSONArray getCertifList() throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		certifiList	= sqlMap.queryForList("getCertifiCodeList");	//자격증 종류 리스트
		JSONArray jsoncode	= JSONArray.fromObject(certifiList);
		return jsoncode;
	}

	

	
}


































