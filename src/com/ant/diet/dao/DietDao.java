package com.ant.diet.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class DietDao {
	
	public List<Map> dietlist(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance2();
		return sqlMap.queryForList("dietlist", map);
	}
	
	public List<Map> dietlistcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance2();
		return sqlMap.queryForList("dietlistcnt", map);
	}

}
