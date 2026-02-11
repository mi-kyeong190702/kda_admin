package com.ant.common.config;

import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class MyAppSqlConfig {
	private static final SqlMapClient sqlMap; 
	private static final SqlMapClient sqlMap_sp; 
	private static final SqlMapClient sqlMap2; 
											
	static {
		try {
			// kda_ver3 DB
			String resource = "com/ant/common/ibatis/SqlMapConfig.xml";
			
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader); 
																	
			// kda_ver3 DB SuperUser
			String resource_sp = "com/ant/common/ibatis/SqlMapConfig_sp.xml";
			
			Reader reader_sp = Resources.getResourceAsReader(resource_sp);
			sqlMap_sp = SqlMapClientBuilder.buildSqlMapClient(reader_sp); 
			
			// dietitian_ver3 DB
			String resource2 = "com/ant/common/ibatis/SqlMapConfig2.xml";
			
			Reader reader2 = Resources.getResourceAsReader(resource2);
			sqlMap2 = SqlMapClientBuilder.buildSqlMapClient(reader2); 
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error initializing MyAppSqlConfig class. Cause: " + e);
		}
	}

	public static SqlMapClient getSqlMapInstance() {
		return sqlMap;
	}
	
	public static SqlMapClient getSqlMapInstance_sp() {
		return sqlMap_sp;
	}
	
	public static SqlMapClient getSqlMapInstance2() {
		return sqlMap2;
	}
}
