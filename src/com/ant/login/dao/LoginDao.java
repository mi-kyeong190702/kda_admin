package com.ant.login.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ant.common.config.MyAppSqlConfig;

public class LoginDao{

	public List<Map> selectListLogin(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] LoginManagerDao [SQLID] LoginManager.selectLogin");
		return sqlMap.queryForList("selectLogin", map);
	}
	
	public Map selectLogin(Map map) throws SQLException {
	    SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    System.out.println("into LoginDao : id = " + map.get("usrId"));
	    return (Map)sqlMap.queryForObject("selectLogin", map);
	}
	
	public List<Map> userPower(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] LoginManagerDao [SQLID] userPower");
		return sqlMap.queryForList("userPower", map);
	}
	
	public int updateSALT(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("into LoginDao : id = " + map.get("usrId"));
		return sqlMap.update("updateSALT", map); 
	}
}
