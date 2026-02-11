package com.ant.document.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class documentDao {
	
	/* 작업명 : 엑셀 파일 로그*/	 
	public List<Map> iExcelLog(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iExcelLog", map);
	}
	
	public List<Map> documentsendcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("documentsendcnt", map);
	}
	
	public List<Map> documentsend(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdocument", map);
	}
	
	public List<Map> dmwritecnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmwritecnt", map);
	}
	
	public List<Map> dmwriteList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmwriteList", map);
	}
	
	public List<Map> documentsenddown(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("documentsenddown", map);
	}
	
	public List<Map> insertdocument(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertdocument", map);
	}
    
    public int updatedocument(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.update("updatedocument", map);
    }	
	
	public List<Map> documentaccpcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("documentaccpcnt", map);
	}

	public List<Map> documentaccp(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdocumentaccp", map);
	}
	
	public List<Map> documentaccpdown(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("documentaccpdown", map);
	}
	
	public List<Map> insertaccpdocument(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertaccpdocument", map);
	}
	
	 public int updateaccpdocument(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	return sqlMap.update("updateaccpdocument", map);
	}	
	 
	public List<Map> documentinsidecnt(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return sqlMap.queryForList("documentinsidecnt", map);
	}
		
	public List<Map> documentinside(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdocumentinside", map);
	}
	
	public List<Map> documentinsidedown(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("documentinsidedown", map);
	}
	
	public List<Map> insertinsidedocument(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertinsidedocument", map);
	}
	
	 public int updateinsidedocument(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	return sqlMap.update("updateinsidedocument", map);
	}
	 
	public List<Map> comcodesearch(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("comcodesearch", map);
	}
	
	public List<Map> certifisearch(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("certififind", map);
	}
	
	public List<Map> books_kind(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("books_kind", map);
	}
	
	public List<Map> dmsendcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmsendcnt", map);
	}
	
	public List<Map> dmsend(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdmsend", map);
	}
	
	public List<Map> dmsenddown(Map map) throws SQLException {
		return dmsenddown(map,false);
	}
	
	public List<Map> dmsenddown(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmsenddown", map);
	}
	
	public List<Map> insertdmsend(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("insertdmsend", map);
	}
	
	 public int updatedmsend(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	return sqlMap.update("updatedmsend", map);
	}
	
	public List<Map> dmreportallcount(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return sqlMap.queryForList("dmreportallcount", map);
	}
		
	public List<Map> dmsendreportall(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdmsendreportall", map);
	}
	
	 public List<Map> dmreportmemcount(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return sqlMap.queryForList("dmreportmemcount", map);
	}
		
	public List<Map> dmsendreportmem(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdmsendreportmem", map);
	}
	
	public List<Map> dmreportgoocount(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			return sqlMap.queryForList("dmreportgoocount", map);
	}
		
	public List<Map> dmsendreportgoo(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdmsendreportgoo", map);
	}
	
	public List<Map> dmreportsupcount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmreportsupcount", map);
	}
	
	public List<Map> dmsendreportsup(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdmsendreportsup", map);
	}

	public List<Map> comcodesearchall(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("comcodesearchall", map);
	}
	
	public List<Map> books_kindall(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("books_kindall", map);
	}
	
	public List<Map> dmsearchcount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmsearchcount", map);
	}
	
	public List<Map> dmsearch(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectdmsearch", map);
	}
	
	public List<Map> dm_creatersearch(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dm_creatersearch", map);
	}
	
	public List<Map> dm_creatersearch1(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dm_creatersearch1", map);
	}
	
	public List<Map> dmsearchdown(Map map) throws SQLException {
		return dmsearchdown(map,false);
	}
	public List<Map> dmsearchdown(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmsearchdown", map);
	}
	
	public List<Map> dmsearchBardown(Map map) throws SQLException {
		return dmsearchBardown(map,false);
	}
	public List<Map> dmsearchBardown(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmsearchBardown", map);
	}
	
	public int insertnotePad(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertnotePad", map);
		return n;
	}
	
	public List<Map> selectnotePad(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectnotePad", map);
	}
	
	public List<Map> documsData(Map map) throws SQLException {
		return documsData(map,false);
	}
	public List<Map> documsData(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("documsData", map);
	}
	
	public int insertnotePadall(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertnotePadall", map);
		return n;
	}
	
	public int insertumsData(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertumsData", map);
		return n;
	}
	
	public int insertumsResultData(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertumsResultData", map);
		return n;
	}
	
	public List<Map> docprintcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("docprintcnt", map);
	}
	
	public List<Map> docprint(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("docprint", map);
	}
	
	public List<Map> docprintdown(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("docprintdown", map);
	}
	
	public List<Map> dmbacodecount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmbacodecount", map);
	}
	
	public List<Map> dmbacode(Map map) throws SQLException {
		return dmbacode(map,false);
	}
	public List<Map> dmbacode(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("dmbacode", map);
	}
	
	public int updaterece_yn(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.update("updaterece_yn", map);
	}
	
	public int insertdmall(Map map) throws SQLException {		
		return insertdmall(map,false);
	}
	public int insertdmall(Map map,boolean sp) throws SQLException {		
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		int n =  sqlMap.update("insertdmall", map);
		return n;
	}
	
	public int insertdmmem(Map map) throws SQLException {		
		return insertdmmem(map,false);
	}
	public int insertdmmem(Map map,boolean sp) throws SQLException {		
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		int n =  sqlMap.update("insertdmmem", map);
		return n;
	}
	
	public int insertdmgoo(Map map) throws SQLException {		
		return insertdmgoo(map,false);
	}
	public int insertdmgoo(Map map,boolean sp) throws SQLException {		
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		int n =  sqlMap.update("insertdmgoo", map);
		return n;
	}
	
	public int insertdmsup(Map map) throws SQLException {		
		return insertdmsup(map,false);
	}
	public int insertdmsup(Map map,boolean sp) throws SQLException {		
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		int n =  sqlMap.update("insertdmsup", map);
		return n;
	}
	
}
