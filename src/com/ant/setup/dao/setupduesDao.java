package com.ant.setup.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class setupduesDao {
	
	public List<Map> codesubsearch(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("codesubsearch", map);
	}
	
	public List<Map> setupduescnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupduescnt", map);
	}
	public List<Map> setupdues(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupdues", map);
	}
	
	public List<Map> insertsetupdues(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertsetupdues", map);
	}
    
    public int updatesetupdues(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.update("updatesetupdues", map);
    }	
    
    public List<Map> setupsubduescnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupsubduescnt", map);
	}
	public List<Map> setupsubdues(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupsubdues", map);
	}
	
	public List<Map> insertsetupduessub(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertsetupduessub", map);
	}
    
    public int updatesetupduessub(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.update("updatesetupduessub", map);
    }	
	
    public List<Map> comcodecnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("comcodecnt", map);
	}
	public List<Map> comcode(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectcomcode", map);
	}
	
    public List<Map> comcodedetailcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("comcodedetailcnt", map);
	}
	public List<Map> comcodedetail(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectcomcodedetail", map);
	}

	public List<Map> insertcomcode(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertcomcode", map);
	}
	
	public int updatecomcode(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updatecomcode", map);
	}
	
    public List<Map> setupeducnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupeducnt", map);
	}
	public List<Map> setupedu(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupedu", map);
	}
	
	public List<Map> insertedu(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertedu", map);
	}
	
	public int updateedu(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updateedu", map);
	}
	
    public List<Map> setupcertificnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupcertificnt", map);
	}
	public List<Map> setupcertifi(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupcertifi", map);
	}
	
	public List<Map> insertcertifi(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertcertifi", map);
	}
	
	public int updatecertifi(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updatecertifi", map);
	}
	
	public List<Map> setupsubcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupsubcnt", map);
	}
	public List<Map> setupsub(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupsub", map);
	}
		
	public List<Map> insertsub(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertsub", map);
	}
		
	public int updatesub(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updatesub", map);
	}
	
	public List<Map> setupusercnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupusercnt", map);
	}
	
	public List<Map> setupuser(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupuser", map);
	}
	
	public List<Map> setupusercnt2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupusercnt2", map);
	}
	
	public List<Map> setupuser2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupuser2", map);
	}
	
	public List<Map> setupuserexist(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupuserexist", map);
	}
	
	public List<Map> insertuser(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertuser", map);
	}
	
	public int updateuser(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updateuser", map);
	}
	
	public int deleteuser1(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] User Power Delete");
	   	return sqlMap.delete("deleteuser1", map);
	}

	
	public List<Map> insertuser1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] User Power Insert");
		return (List<Map>)sqlMap.insert("insertuser1", map);
	}
	
	public int updatepass(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updatepass", map);
	}
	
	public List<Map> setupbookscnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setupbookscnt", map);
	}
	
	public List<Map> setupbooks(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetupbooks", map);
	}
	
	public List<Map> insertbooks(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertbooks", map);
	}
	
	public int updatebooks(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updatebooks", map);
	}
	
	public List<Map> setuptargetcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("setuptargetcnt", map);
	}
	
	public List<Map> setuptarget(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectsetuptarget", map);
	}
	
	public List<Map> inserttarget(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("inserttarget", map);
	}
	
	public int updatetarget(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	   	return sqlMap.update("updatetarget", map);
	}
}
