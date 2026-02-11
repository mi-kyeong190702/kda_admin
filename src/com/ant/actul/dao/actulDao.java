package com.ant.actul.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class actulDao {
	
	/* 작업명 : 엑셀 파일 로그*/	 
	public List<Map> iExcelLog(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iExcelLog", map);
	}
	
	/* 작업명 : 엑셀 파일 업로드 sample*/	
	public List<Map> upedusample(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("upedusample", map);
	}	
	
	/* 작업명 : 엑셀 파일 업로드 보수교육 입력*/
	public int insert_edu(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insert_edu", map);
		return n;		
	}
    
	/* 작업명 : 엑셀 파일 업로드 보수교육 수정*/
    public int update_edu(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("update_edu", map);
    	return n;
    }
    
	public List<Map> actulconfirm(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulconfirm", map);
	}
	
	public List<Map> kda_edu(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("kda_edu", map);
	}
	
	public int actulinsert(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("actulinsert", map);
		return n;		
	}
    
    public int actulupdate(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("actulupdate", map);
    	return n;
    }
    
    public List<Map> actulmanacnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulmanacnt", map);
	}
    
    public List<Map> actulmana(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulmana", map);
	}
    
    public List<Map> actulmanadown(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulmanadown", map);
	}
    
    public List<Map> actullistcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actullistcnt", map);
	}
    
    public List<Map> actullist(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actullist", map);
	}
    
    public List<Map> actulumsData(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulumsData", map);
	}
    
    public int insertumsData(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertumsdata", map);
		return n;
	}
	
	public int insertumsResultData(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertumsResultdata", map);
		return n;
	}
	
	
	public List<Map> actulmanagecnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulmanagecnt", map);
	}
    
    public List<Map> actulmanageList(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulmanageList", map);		
	}
	
	public List<Map> actulstatuscnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulstatuscnt", map);
	}
    
    public List<Map> actulstatusList(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulstatusList", map);
	}
    
	public List<Map> actulstatuscnt1(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulstatuscnt1", map);
	}
	
    public List<Map> actulstatusList1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulstatusList1", map);
	}
	
	public List<Map> actulrecipientcnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulrecipientcnt", map);
	}
    
    public List<Map> actulrecipientList(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("actulrecipientList", map);
	}
    
    public List<Map> getRecipient(Map map)  throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getRecipient", map);
    }
    
    public int subjectsUpt(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("subjectsUpt", map);
    	return n;	
    }
    
    public List<Map> actullicensecnt(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("actullicensecnt", map);
    }
    
    public List<Map> actullicenseList(Map map)  throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("actullicenseList", map);
    }
    
    public int recipientUpt(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("recipientUpt", map);
    	return n;	
    }
    
    public int recipientArStateUpt(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("recipientArStateUpt", map);
    	return n;	
    }
    
    public int personReg(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n =sqlMap.update("personReg", map);
		return n;
	}
    
    public int arStateUpt(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("arStateUpt", map);
    	return n;	
    }
    
    public List<Map> sampleExcel(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sampleExcel", map);
	}
    
    public int insertStatus(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n =sqlMap.update("insertStatus", map);
		return n;
	}
    
    public int updateStatus(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("updateStatus", map);
    	return n;	
    }
    
    public int updateState(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("updateState", map);
    	return n;	
    }
    
    public int updateFinishyn(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("updateFinishyn", map);
    	return n;
    }
    
    public List personCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("personCnt", map);
	}
    
    public List<Map> statusCnt(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("statusCnt", map);
    }
    
    public int statusUptProc1(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n =sqlMap.update("statusUptProc1", map);
		return n;
	}
    
    public int statusUptProc2(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("statusUptProc2", map);
    	return n;
    }
    
    public int statusUptProc3(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("statusUptProc3", map);
    	return n;
    }
    
    public int statusUptProc4(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("statusUptProc4", map);
    	return n;
    }
    
    public int statusUptProc5(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("statusUptProc5", map);
    	return n;
    }
    
    public int statusUptProc6(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("statusUptProc6", map);
    	return n;
    }
    
    public int statusUptProc7(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("statusUptProc7", map);
    	return n;
    }
    
    
    public int fileAr_status(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("fileAr_status", map);
    	return n;
    }
    
    public int updateInfo_status(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("updateInfo_status", map);
    	return n;
    }
    
    public int updateInfo_person(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("updateInfo_person", map);
    	return n;
    }
    
    public List<Map> getStatusData(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getStatusData", map);
    }
    
    public List<Map> getPersonData(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getPersonData", map);
    }
    
    public List<Map> getRecentYear(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getRecentYear", map);
    }
    
    public List<Map> getRecent(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getRecent", map);
    }
    
    public List<Map> getRecentSearch(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getRecentSearch", map);
    }
    
    public List<Map> getEmail(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getEmail", map);
    }
    
    public List<Map> getKda_eduCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getKda_eduCnt", map);
	}
    
    public List<Map> getKda_edu(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getKda_edu", map);
	}
    

    public List<Map> sampleEduExcel(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sampleEduExcel", map);
	}
    
    public List<Map> eduCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("eduCnt", map);
	}
    
    public int insertKda_edu(Map map) throws SQLException {		
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n =sqlMap.update("insertKda_edu", map);
		return n;
	}
    
    public int updateKda_edu(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("updateKda_edu", map);
    	return n;
    }
    
    public int uptFile(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n =sqlMap.update("uptFile", map);
    	return n;
    }
    
    public int recipientMemoUpt(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("recipientMemoUpt", map);
    	return n;	
    }
    
    public int manageMemoUpt(Map map) throws SQLException {		
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	int n = sqlMap.update("manageMemoUpt", map);
    	return n;	
    }
    
    public Map callReportState_chk(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] actulDao ReportState_chk");		
		try{
			sqlMap.startTransaction();
		   	sqlMap.queryForObject("ReportState_chk",map);
		   	sqlMap.getCurrentConnection().commit();
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		sqlMap.endTransaction();
	   	}
		return map;  
	}
    
    public List<Map> getFileInfo(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	return sqlMap.queryForList("getFileInfo", map);
    }
}
