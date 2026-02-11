package com.ant.conference.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class conferenceDao {
	
	public List<Map> conferencecnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("conferencecnt", map);
	}
	public List<Map> conference(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectconference", map);
	}
	
	public List<Map> conferencemeet(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectconferencemeet", map);
	}
	
	public List<Map> insertconference(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertconference", map);
	}
    
    public int updateconference(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.update("updateconference", map);
    }	
	
    public int deleteconference(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.delete("deleteconference", map);
    }
}
