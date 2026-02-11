package com.ant.member.state.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

public class memberDao {

	public List<Map> selectcount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectcount", map);
	}
	public List<Map> selectmember(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("selectmember", map);
	}
	public Map selectmember_list(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		List<Map> memberlist= sqlMap.queryForList("selectmember", map);
		return  memberlist.get(0);
	}

	public List<Map> codeUseMember(Map map) throws SQLException{
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("codeUseMember", map);
	}
	

	
	//KSH 2012.09.04 : HOME > ���?�� > ȸ�� ��Ȳ > �ٹ�ó �з� ��Ȳ

	//����¡�� ī����
	public int getCompanyMemberStatusCount(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		System.out.println("eeee");
		List<Map> companyMemberStatusList = sqlMap.queryForList("getCompanyMemberStatusCount", map);
		System.out.println("dddd = "+companyMemberStatusList.get(0).get("no"));
		int ncount = ((Integer)(companyMemberStatusList.get(0).get("no"))).intValue();
		return ncount;
	}
	
	public List<Map> getCompanyMemberStatusList(Map map) throws SQLException {
		SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
		return sqlMap.queryForList("getCompanyMemberStatusList", map);
	}

}