package com.ant.educationlecture.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

	public class lectureDao {
		
		/* 작업명 : 엑셀 파일 로그*/	 
		public List<Map> iExcelLog(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return (List<Map>)sqlMap.insert("iExcelLog", map);
		}
		
		/* 강사 등록*/	 
		public List<Map> lecturecnt(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return sqlMap.queryForList("lecturecnt", map);
		}
		
		public List<Map> lecture(Map map) throws SQLException {
			return lecture(map,false);
		}		
		public List<Map> lecture(Map map,boolean sp) throws SQLException {
			SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
			return sqlMap.queryForList("lecture", map);
		}		
		
		public List<Map> insertlecture(Map map) throws SQLException {		
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return (List<Map>)sqlMap.insert("insertlecture", map);
		}
		
		public int delete_lecture(Map map) throws SQLException {
		   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		    return sqlMap.delete("delete_lecture", map);
		}	
		
		public int update_lecture(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return sqlMap.update("update_lecture", map);
		}	
}
