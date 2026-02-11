package com.ant.educationlecture.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ant.common.config.MyAppSqlConfig;
//import com.ant.educationexam.dao.callLicenseNoCreat_STR;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;

public class licenseDao {


	/* 문자 저장 */
	public int insertLicUmsData(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertLicUmsData", map);
		return n;
	}
	/* 문자 예약 저장 */
	public int insertLicUmsResultData(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		int n = sqlMap.update("insertLicUmsResultData", map);
		return n;
	}
	
	
	
	/* 부서코드  display*/
	public List<Map> licenseserch(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] licenseserch");
		return sqlMap.queryForList("licenseserch", map);
	}
	
	/*자격증 구분 코드  display*/
	public List<Map> licenseserch1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] licenseserch1");
		return sqlMap.queryForList("licenseserch1", map);
	}
	
	/*count 시험별응시현황*/
	public List<Map> licensesendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] licensesendcnt");
		return sqlMap.queryForList("licensesendcnt", map);
	}
	
	/*select 시험별응시현황 출력*/
	public List<Map> selectlicense(Map map)  throws SQLException {
		return selectlicense(map,false);
	}
	public List<Map> selectlicense(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectlicense");
		return sqlMap.queryForList("selectlicense", map);
	}
	
	/*select 시험별응시현황 문자,쪽지,메일*/
	public List<Map> selectlicenseA(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectlicenseA");
		return sqlMap.queryForList("selectlicenseA", map);
	}
	
	/*count 결과등록*/
	public List<Map> resultsendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] resultsendcnt");
		return sqlMap.queryForList("resultsendcnt", map);
	}
	
	/*select 결과등록 엑셀저장*/
	public List<Map> selectresult(Map map)  throws SQLException {
		return selectresult(map,false);
	}
	public List<Map> selectresult(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectresult");
		return sqlMap.queryForList("selectresult", map);
	}
	
	/*select 결과등록 엑셀저장*/
	public List<Map> selectresultA(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectresultA");
		return sqlMap.queryForList("selectresultA", map);
	}
	/*insert 결과등록*/
	public int deletresult(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao deletresult");
	   	return sqlMap.delete("deletresult", map);
	}
	
	/*insert 결과등록*/
	public List<Map> insertresult(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] insertresult");
		return (List<Map>)sqlMap.insert("insertresult", map);
	}
	/*update 결과등록*/
	public int updateresult(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] updateresult");
    	return sqlMap.update("updateresult", map);
    }
	
	public int upBFiles(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] upBFiles");
    	return sqlMap.update("upBFiles", map);
    }
	
	/*3가지 조건에 맞는 값  교육별 응시현황*/
	public List<Map> licensesand(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] licensesand");
		return sqlMap.queryForList("licensesand", map);
	}
	
	/*count 자격증심사*/
	public List<Map> inspectionsendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] inspectionsendcnt");
		return sqlMap.queryForList("inspectionsendcnt", map);
	}
	
	/*자격증심사 삭제 + 업데이트*/
	public int deletinspection(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao deletinspection");
	   	return sqlMap.delete("deletinspection", map);
	}
	
	/*select 자격증심사 엑셀저장*/
	public List<Map> selectlinspection(Map map)  throws SQLException {
		return selectlinspection(map,false);
	}
	public List<Map> selectlinspection(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectlinspection");
		return sqlMap.queryForList("selectlinspection", map);
	}
	
	/*select 자격증심사 문자,쪽지,메일*/
	public List<Map> selectlinspectionA(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectlinspectionA");
		return sqlMap.queryForList("selectlinspectionA", map);
	}
	
	/*count 자격증발급*/
	public List<Map> issuesendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] issuesendcnt");
		return sqlMap.queryForList("issuesendcnt", map);
	}
	
	/*select 자격증발급 엑셀저장*/
	public List<Map> selectissue(Map map)  throws SQLException {
		return selectissue(map,false);
	}
	public List<Map> selectissue(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectissue");
		return sqlMap.queryForList("selectissue", map);
	}
	
	/*select 자격증발급 문자,쪽지,메일*/
	public List<Map> selectissueA(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectissueA");
		return sqlMap.queryForList("selectissueA", map);
	}
	
	/*count 발급현황조회*/
	public List<Map> nowsendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] nowsendcnt");
		return sqlMap.queryForList("nowsendcnt", map);
	}
	
	/*select 발급현황조회*/
	public List<Map> selectnow(Map map)  throws SQLException {
		return selectnow(map,false);
	}
	public List<Map> selectnow(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectnow");
		return sqlMap.queryForList("selectnow", map);
	}
	
	/* 자격증 발급번호 생성 */
	public Map callLicenseNoCreat_STR(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao callLicenseNoCreat_STR");		
		try{
			sqlMap.startTransaction();
		   	sqlMap.queryForObject("pLicenseNoCreat_STR",map);
		   	sqlMap.getCurrentConnection().commit();
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		sqlMap.endTransaction();
	   	}
		return map;  
	}
	
	/* 자격증 발급번호 갱신 */
	public Map callRenewal_STR(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao callRenewal_STR");		
		try{
			sqlMap.startTransaction();
		   	sqlMap.queryForObject("pRenewal_STR",map);
		   	sqlMap.getCurrentConnection().commit();
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		sqlMap.endTransaction();
	   	}
		return map;  
	}
	
	/*select 발급현황조회 문자,쪽지,메일*/
	public List<Map> selectnowA(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectnowA");
		return sqlMap.queryForList("selectnowA", map);
	}
	
	/*count 자격증갱신*/
	public List<Map> renewalsendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] renewalsendcnt");
		return sqlMap.queryForList("renewalsendcnt", map);
	}
	
	/*select 자격증갱신*/
	public List<Map> selectrenewal(Map map)  throws SQLException {
		return selectrenewal(map,false);
	}
	public List<Map> selectrenewal(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectrenewal");
		return sqlMap.queryForList("selectrenewal", map);
	}
	
	/*select 개별 자격증갱신*/
	public int updatlicenserenewalList(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] updatlicenserenewalList");
    	return sqlMap.update("updatlicenserenewalList", map);
    }
	
	
	/*select 자격증갱신 문자,쪽지,메일*/
	public List<Map> selectrenewalA(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectrenewalA");
		return sqlMap.queryForList("selectrenewalA", map);
	}
	
	/*insert 자격증심사*/
	public List<Map> insertinspection(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] insertinspection");
		return (List<Map>)sqlMap.insert("insertinspection", map);
	}
	/*update 자격증심사*/
	public int updateinspection(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] updateinspection");
    	return sqlMap.update("updateinspection", map);
    }
	
	/*select 결과2처리 교육 및 시험에 대한 합격 이수 관리*/
	public List<Map> selectexampoint1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectexampoint1");
		return sqlMap.queryForList("selectexampoint1", map);
	}
	
	
	/*select 시험별응시현황 엑셀다운용*/
	public List<Map> conditionExcel(Map map)  throws SQLException {
		return conditionExcel(map,false);
	}
	public List<Map> conditionExcel(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] conditionExcel");
		return sqlMap.queryForList("conditionExcel", map);
	}
	/*자격증심사첨부파일 선택 */
	public List<Map> selectCFiles(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectCFiles");
		return sqlMap.queryForList("selectCFiles", map);
	}
	/*자격증심사첨부파일 선택 Batch */
	public List<Map> selectCFilesBatch(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectCFilesBatch");
		return sqlMap.queryForList("selectCFilesBatch", map);
	}
	/*첨부파일 삭제*/
	public int deletCFiles(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao deletCFiles");
	   	return sqlMap.delete("deletCFiles", map);
	   	
	}
	
	/*자격증갱신첨부파일 선택 */
	public List<Map> selectBFiles(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectBFiles");
		return sqlMap.queryForList("selectBFiles", map);
	}

	/*첨부파일 삭제*/
	public int deletBFiles(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao deletBFiles");
	   	return sqlMap.update("deletBFiles", map);
	}
	
	/*select 시험별응시현황 엑셀 업로드용 공통코드 조회*/
	public List<Map> sCodeExcel1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] sCodeExcel1");
		return sqlMap.queryForList("sCodeExcel1", map);
	}
	//주민등록번호로 회원정보 읽어 오기
		public List<Map> selectpersonmtbl1(Map map)  throws SQLException {
			return selectpersonmtbl1(map,false);
		}
		public List<Map> selectpersonmtbl1(Map map,boolean sp)  throws SQLException {
			SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectpersonmtbl");
			return sqlMap.queryForList("SelectPersonMtbl1", map);
		}
		//시험별응시현황 등록된 동일인이 있는지 확인
		public List<Map> selectoperater1(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectoperater");
			return sqlMap.queryForList("SelectOperater1", map);
		}	
		/*update 시험별응시현황*/
		public int updatelicense(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] updatelicense");
	    	return sqlMap.update("updatelicense", map);
	    }
		/*insert 시험별응시현황*/
		public List<Map> insertlicense(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] insertlicense");
			return (List<Map>)sqlMap.insert("insertlicense", map);
		}
		
		
		public List<Map> selectEtestN(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectEtestN");
			return sqlMap.queryForList("selectEtestN", map);
		}
		
		public int uRstate(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] uRstate");
	    	return sqlMap.update("uRstate", map);
	    }
		public int upinspection2(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] upinspection2");
	    	return sqlMap.update("upinspection2", map);
	    }
		
		public List<Map> selectPrint(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectPrint");
			return sqlMap.queryForList("selectPrint", map);
		}
		public List<Map> selectPrint1(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] selectPrint1");
			return sqlMap.queryForList("selectPrint1", map);
		}
		public int uPstate(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] uPstate");
	    	return sqlMap.update("uPstate", map);
	    }
		public int uOPstate(Map map) throws SQLException {
	    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
	    	System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] uOPstate");
	    	return sqlMap.update("uOPstate", map);
	    }
		public int uOPstateBatch(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] uOPstateBatch");
			return sqlMap.update("uOPstateBatch", map);
		}
		public int uOPdelete(Map map) throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] licenseDao [SQLID] uOPdelete");
			return sqlMap.delete("uOPdelete", map);
		}
}
