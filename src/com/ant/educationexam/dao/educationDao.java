package com.ant.educationexam.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class educationDao {
	

	/* 3.3.0
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 김성훈
	 * 작업일 : 2013.11.30
	 * 작  업 : getEduDmSeq DM리스트 dm_print_yymm_seq맥스값 얻기
	 */
	public List<Map> getEduDmForSeq(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getEduDmForSeq", map);
	}

	/* 3.3.1
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 김성훈
	 * 작업일 : 2013.11.30
	 * 작  업 : getEduDMForCnt	DM리스트 저장할 개수 조회
	 */
	public List<Map> getEduDMForCnt(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getEduDMForCnt", map);
	}

	/* 3.3.2
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 김성훈
	 * 작업일 : 2013.11.30
	 * 작  업 : getEduDMList DM리스트 목표 조회
	 */
	public List<Map> getEduDMForList(Map map) throws SQLException {
		return getEduDMForList(map,false);
	}
	public List<Map> getEduDMForList(Map map,boolean sp) throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getEduDMForList", map);
	}
	
	/* 3.3.3
	 * 작업명 : Home>교육>교육별응시현황
	 * 작업자 : 김성훈
	 * 작업일 : 2013.11.30
	 * 작  업 : setEduDMList		DM리스트 저장
	 */
	public void setEduDMList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		sqlMap.insert("setEduDMList", map);
	}
	
	
	
	
	
	
	
	/*3가지 조건에 맞는 값  교육별 응시현황*/
	public List<Map> educationsend1(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] educationsend1");
		return sqlMap.queryForList("educationsend1", map);
	}
	
	/*2가지 조건에 맞는 값(교육및시험구분,교육및시험종류) */
	public List<Map> educationsend2(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] educationsend2");
		return sqlMap.queryForList("educationsend2", map);
	}
	/*select 교육별응시현황 문자쪽지*/
	public List<Map> selecteducation(Map map)  throws SQLException {
		return selecteducation(map,false);
	}
	public List<Map> selecteducation(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selecteducation");
		return sqlMap.queryForList("selecteducation", map);
	}
	/*select Batch 교육별응시현황 문자쪽지*/
	public List<Map> selecteducationBatch(Map map)  throws SQLException {
		return selecteducationBatch(map,false);
	}
	public List<Map> selecteducationBatch(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selecteducationBatch");
		return sqlMap.queryForList("selecteducationBatch", map);
	}
	/*count 교육별응시현황*/
	public List<Map> educationsendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] educationsendcnt");
		return sqlMap.queryForList("educationsendcnt", map);
	}
	/*insert 교육별응시현황*/
	public List<Map> inserteducation(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] inserteducation");
		return (List<Map>)sqlMap.insert("inserteducation", map);
	}
	/*insert 교육별응시현황*/
	public List<Map> inserteducation11(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] inserteducation11");
		return (List<Map>)sqlMap.insert("inserteducation11", map);
	}
	/*update 교육별응시현황*/
	public int updateeducation(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] updateeducation");
    	return sqlMap.update("updateeducation", map);
    }
	/*update 교육별응시현황*/
	public int updateeducation11(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] updateeducation11");
    	return sqlMap.update("updateeducation11", map);
    }
	/*update Batch 교육별응시현황*/
	public int updateeducationBatch(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] updateeducationBatch");
		return sqlMap.update("updateeducationBatch", map);
	}
	/*update MemberBatch 교육별응시현황 개인정보 일괄변경*/
	public int updateeducationMemberBatch(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] updateeducationMemberBatch");
		return sqlMap.update("updateeducationMemberBatch", map);
	}
	/*delete 교육별응시현황*/
	public int deleteeducation11(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] deleteeducation11");
		return sqlMap.delete("deleteeducation11", map);
	}
	/*count 교육별응시현황*/
	public List<Map> edutestsendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] edutestsendcnt");
		return sqlMap.queryForList("edutestsendcnt", map);
	}
	/*count 교육및시험등록*/
	public List<Map> edutestsendcnt1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] edutestsendcnt1");
		return sqlMap.queryForList("edutestsendcnt1", map);
	}
	/*select 교육및시험등록*/
	public List<Map> selectedutest(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectedutest");
		return sqlMap.queryForList("selectedutest", map);
	}
	/*insert 교육및시험등록*/
	public List<Map> insertedutest(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] insertedutest");
		return (List<Map>)sqlMap.insert("insertedutest", map);
	}
	/*update 교육및시험등록*/
	public int updateedutest(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] updateedutest");
    	return sqlMap.update("updateedutest", map);
    }
	/*delete 교육및시험등록*/
	public int deleteedutest(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] deleteedutest");
		return sqlMap.delete("deleteedutest", map);
	}
	/*select 응시결과처리*/
	public List<Map> examsendcnt(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] examsendcnt");
		return sqlMap.queryForList("examsendcnt", map);
	}
	/*select 응시결과처리 3가지 조건 충족*/
	public List<Map> selectexam1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectexam1");
		return sqlMap.queryForList("selectexam1", map);
	}
	
	/*select 응시결과처리*/
	public List<Map> selectexam2(Map map)  throws SQLException {
		return selectexam2(map,false);
	}
	public List<Map> selectexam2(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectexam2");
		return sqlMap.queryForList("selectexam2", map);
	}
	
	/*select 응시결과2처리 교육 및 시험에 대한 합격 이수 관리*/
	public List<Map> selectexampoint(Map map)  throws SQLException {
		System.out.println("==============================================================");
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectexampoint");
		return sqlMap.queryForList("selectexampoint", map);
	}
	
	/*select 응시결과처리 엑셀다운용*/
	public List<Map> selectexamExcel(Map map)  throws SQLException {
		return selectexamExcel(map,false);
	}
	public List<Map> selectexamExcel(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectexamExcel");
		return sqlMap.queryForList("selectexamExcel", map);
	}
	/*select 교육별응시현황 엑셀다운용*/
	public List<Map> examresultExcel(Map map)  throws SQLException {
		return examresultExcel(map,false);
	}
	public List<Map> examresultExcel(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] examresultExcel");
		return sqlMap.queryForList("examresultExcel", map);
	}
	
	/*select 응시결과처리 엑셀 업로드용 공통코드 조회*/
	public List<Map> sCodeExcel(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] sCodeExcel");
		return sqlMap.queryForList("sCodeExcel", map);
	}
	
	/*insert 응시결과처리*/
	public int deletexam(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao deletexam");
	   	return sqlMap.delete("deletexam", map);
	}

	public List<Map> insertexam(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] insertexam");
		return (List<Map>)sqlMap.insert("insertexam", map);
	}
	/*update 응시결과처리*/
	public int updateexame(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] updateexame");
    	return sqlMap.update("updateexame", map);
    }
	
	
	
	public List<Map> certifisearch(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] certifisearch");
		return sqlMap.queryForList("certifisearch", map);
	}
	//자격증 구분
	public List<Map> certifisearch1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] certifisearch1");
		return sqlMap.queryForList("certifisearch1", map);
	}
	
	//사용 구분
		public List<Map> useyn(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] useyn");
			return sqlMap.queryForList("useyn", map);
		}
	
	//진행상태 
	public List<Map> pointserch(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] pointserch");
		return sqlMap.queryForList("pointserch", map);
	}
	
	//발급상태 
		public List<Map> printserch(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] printserch");
			return sqlMap.queryForList("printserch", map);
		}
	
		public List<Map> certifisearch2(Map map)  throws SQLException {
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] certifisearch2");
			return sqlMap.queryForList("certifisearch2", map);
		}
		
	public List<Map> certifisearch3(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] certifisearch3");
		return sqlMap.queryForList("certifisearch3", map);
	}
	
	public int deletinspection(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao deletinspection");
	   	return sqlMap.delete("deletinspection1", map);
	}
	public List<Map> insertinspection(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] insertinspection");
		return (List<Map>)sqlMap.insert("insertinspection1", map);
	}
	public List<Map> selectAFiles(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectAFiles");
		return sqlMap.queryForList("selectAFiles", map);
	}
	public int deletAFiles(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao deletAFiles");
	   	return sqlMap.delete("deletAFiles", map);
	}
	//주민등록번호로 회원정보 읽어 오기
	public List<Map> selectpersonmtbl(Map map)  throws SQLException {
		return selectpersonmtbl(map,false);
	}
	//주민등록번호로 회원정보 읽어 오기
	public List<Map> selectpersonmtbl(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectpersonmtbl");
		return sqlMap.queryForList("SelectPersonMtbl", map);
	}
	public List<Map> selectpersonmtblnew(Map map)  throws SQLException {
		return selectpersonmtblnew(map,false);
	}	
	public List<Map> selectpersonmtblnew(Map map,boolean sp)  throws SQLException {
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectpersonmtblnew");
		return sqlMap.queryForList("SelectPersonMtblNew", map);
	}	
	//등록된 동일인이 있는지 확인
	public List<Map> selectoperater(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectoperater");
		return sqlMap.queryForList("SelectOperater", map);
	}
	public List<Map> selectoperater2(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selectoperater2");
		return sqlMap.queryForList("SelectOperater2", map);
	}
	
	public Map callResultNoCreat_STR(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao callResultNoCreat_STR");		
		try{
			sqlMap.startTransaction();
		   	sqlMap.queryForObject("pResultNoCreat_STR",map);
		   	sqlMap.getCurrentConnection().commit();
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		sqlMap.endTransaction();
	   	}
		return map;  
	}
	
	public Map callLicenseDataCreat_STR(Map map) throws SQLException {
	   	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance_sp();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao callLicenseDataCreat_STR");		
		try{
			sqlMap.startTransaction();
		   	sqlMap.queryForObject("pLicenseDataCreat_STR",map);
		   	sqlMap.getCurrentConnection().commit();
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		sqlMap.endTransaction();
	   	}
		return map;  
	}
	
	public int upinspection1(Map map) throws SQLException {
    	SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
    	System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] upinspection1");
    	return sqlMap.update("upinspection1", map);
    }
	
	public List<Map> selCertifi(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selCertifi11");
		return sqlMap.queryForList("selCertifi11", map);
	}
	
	public List<Map> selCode_pers1(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] selCode_pers1");
		return sqlMap.queryForList("selCode_pers1", map);
	}
	
	//비회원, 위생교육일때 같은 해에 위생교육은 중복신청건이 있는지 체크
	public List<Map> ChkTestListCount(Map map)  throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] ChkTestListCount");
		return sqlMap.queryForList("ChkTestListCount", map);
	}
	
	//회원, 위생교육일때 같은 해에 위생교육은 중복신청건이 있는지 체크
	public List<Map> ChkTestListCount2(Map map)  throws SQLException { 
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("+++++++++++++++++++>>[DAO] educationDao [SQLID] ChkTestListCount2");
		return sqlMap.queryForList("ChkTestListCount2", map);
	}
}
