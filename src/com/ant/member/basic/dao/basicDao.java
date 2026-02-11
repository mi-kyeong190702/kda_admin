package com.ant.member.basic.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class basicDao {
	public List<Map> sMember(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sMember", map);
	}
	public List<Map> sMemberCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sMemberCnt", map);
	}
	
	public List<Map> tCompany(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("tCompany", map);
	}
	public List<Map> tComCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("tComCnt", map);
	}
	public List<Map> memInfo(Map map) throws SQLException{
		return memInfo(map,false);
	}
	public List<Map> memInfo(Map map,boolean sp) throws SQLException{
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("memInfo", map);
	}
	public List<Map> memInfo1(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("memInfo1", map);
	}
	public List<Map> getCode(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("code", map);
	}
	public List<Map> getCode1(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("code1", map);
	}
	public List<Map> getCode2(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("code2", map);
	}
	public List<Map> getCertifi(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("certifi", map);
	}
	public List<Map> getSubcom(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("subcom", map);
	}
	public List<Map> sDetail(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sDetail", map);
	}
	public List<Map> sDetailCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sDetailCnt", map);
	}
	public List<Map> sMemo(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sMemo", map);
	}
	public List<Map> sMemoCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sMemoCnt", map);
	}
	public List<Map> sEdu(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sEdu", map);
	}
	public List<Map> sEduCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sEduCnt", map);
	}
	public List<Map> sDues(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sDues", map);
	}
	public List<Map> sDuesCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sDuesCnt", map);
	}
	public List<Map> sBank(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sBank", map);
	}
	public List<Map> sBankCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sBankCnt", map);
	}
	public List<Map> sDuesHSeq(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sDuesHSeq", map);
	}
	public List<Map> sDuesH(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sDuesH", map);
	}
	public List<Map> sSubDues(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sSubDues", map);
	}
	public List<Map> sSumDues(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sSumDues", map);
	}
	public List<Map> sSubDues2(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sSubDues2", map);
	}
	public List<Map> sSumDues2(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sSumDues2", map);
	}
	public List<Map> sComp(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sComp", map);
	}
	public List<Map> sCompCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sCompCnt", map);
	}
	public List<Map> defComp(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("defComp", map);
	}
	public List<Map> sDon(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("donation", map);
	}
	public List<Map> sDonCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("donCnt", map);
	}
	public List<Map> sLicCnt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sLicCnt", map);
	}
	public List<Map> iModify(Map map) throws SQLException{
		return iModify(map,false);
	}
	public List<Map> iModify(Map map,boolean sp) throws SQLException{
		SqlMapClient sqlMap = sp ? MyAppSqlConfig.getSqlMapInstance_sp() : MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iModify", map);
	}
	public int uModify(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uModify", map);
	}
	public int ucModify(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("ucModify", map);
	}
	public int uPWreset(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uPWreset", map);
	}
	public int uComp(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uComp", map);
	}
	public int uDon(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDon", map);
	}
	public List<Map> iDuesH(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iDuesH", map);
	}
	public List<Map> iDuesB(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iDuesB", map);
	}
	public List<Map> iDuesB2(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iDuesB2", map);
	}
	public int uDuesB(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesB", map);
	}
	public int uDuesH(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesH", map);
	}
	public int uDuesH2(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesH2", map);
	}
	public int uDuesH3(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesH3", map);
	}
	public int uDuesH4(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesH4", map);
	}
	public int uDuesH5(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesH5", map);
	}
	public List<Map> iMemo(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iMemo", map);
	}
	public List<Map> iTcomp(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iTcomp", map);
	}
	public int uMemo(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uMemo", map);
	}
	
	public List<Map> sCertiList(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sCertiList", map);
	}
	public List<Map> sCertiList2(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sCertiList2", map);
	}
	public int uPersState(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uPersState", map);
	}
	public List<Map> sPromise(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("promise", map);
	}
	
	public int uCompPrime(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uCompPrime", map);
	}
	public List<Map> iComp(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iComp", map);
	}
	public List<Map> iDuesMemHis(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return (List<Map>)sqlMap.insert("iDuesMemHis", map);
	}
	public int uDuesCMem(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesCMem", map);
	}
	public int uDuesHmemDues(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uDuesHmemDues", map);
	}
	public List<Map> sMaxAend(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sMaxAend", map);
	}
	
	public int canDuesH(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("canDuesH", map);
	}
	public int canDuesB(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("canDuesB", map);
	}
	public int canDuesP(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.delete("canDuesP", map);
	}
	
	public List<Map> chkDuesP(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("chkDuesP", map);
	}
	public List<Map> chkDuesB(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("chkDuesB", map);
	}
	public int deleteDuesH(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.delete("deleteDuesH", map);
	}
	public int deleteDuesB(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.delete("deleteDuesB", map);
	}
	
	public List<Map> sLastCmname(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("sLastCmname", map);
	}
	
	public int uLastCmname(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uLastCmname", map);
	}
	
	public List<Map> getIdInfo(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("idInfo", map);
	}
	
	public List<Map> idCheck(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("idCheck", map);
	}
	
	public int idUpt(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("idUpt", map);
	}
	
	public int uCompany(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.update("uCompany", map);
	}
	
}