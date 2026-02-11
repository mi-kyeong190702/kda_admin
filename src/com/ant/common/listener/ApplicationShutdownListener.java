package com.ant.common.listener;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.ant.common.config.MyAppSqlConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 애플리케이션 종료 시 DB 리소스 정리를 위한 리스너
 * SQL Server JDBC 연결 및 iBatis SqlMapClient 리소스 정리
 * DB2 JDBC 드라이버가 클래스패스에 있는 경우 타이머 정리
 */
public class ApplicationShutdownListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(ApplicationShutdownListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Application started - SQL Server DB resources initialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Application shutting down - Cleaning up DB resources...");
		
		try {
			// iBatis SqlMapClient 인스턴스 정리
			SqlMapClient sqlMap = MyAppSqlConfig.getSqlMapInstance();
			if (sqlMap != null) {
				logger.info("Cleaning up SqlMapClient instance (main DB)");
			}
			
			SqlMapClient sqlMap_sp = MyAppSqlConfig.getSqlMapInstance_sp();
			if (sqlMap_sp != null) {
				logger.info("Cleaning up SqlMapClient_sp instance (superuser DB)");
			}
			
			SqlMapClient sqlMap2 = MyAppSqlConfig.getSqlMapInstance2();
			if (sqlMap2 != null) {
				logger.info("Cleaning up SqlMapClient2 instance (dietitian DB)");
			}
			
			// DB2 JDBC 드라이버가 클래스패스에 있는 경우 타이머 정리
			cleanupDB2Driver();
			
			// JDBC 드라이버 등록 해제
			deregisterJDBCDrivers();
			
			logger.info("DB resources cleanup completed");
			
		} catch (Exception e) {
			// 애플리케이션 종료 중이므로 로그만 기록
			logger.warn("Error during DB resources cleanup: " + e.getMessage(), e);
		}
	}
	
	/**
	 * DB2 JDBC 드라이버의 백그라운드 타이머 정리
	 * DB2 JDBC 드라이버가 클래스패스에 있지만 사용하지 않는 경우 발생하는 오류 방지
	 */
	private void cleanupDB2Driver() {
		try {
			// DB2 JDBC 드라이버 클래스 확인
			Class<?> db2DriverClass = null;
			try {
				db2DriverClass = Class.forName("com.ibm.db2.jcc.DB2Driver");
			} catch (ClassNotFoundException e) {
				// DB2 드라이버가 없으면 정리할 필요 없음
				return;
			}
			
			if (db2DriverClass != null) {
				logger.info("DB2 JDBC driver found in classpath - attempting to cleanup timer threads");
				
				// DB2 JDBC 드라이버의 GlobalProperties 클래스를 통해 타이머 정리 시도
				try {
					Class<?> globalPropsClass = Class.forName("com.ibm.db2.jcc.am.GlobalProperties");
					Method cleanupMethod = globalPropsClass.getDeclaredMethod("cleanup");
					cleanupMethod.setAccessible(true);
					cleanupMethod.invoke(null);
					logger.info("DB2 JDBC driver timer threads cleaned up successfully");
				} catch (Exception e) {
					// cleanup 메서드가 없거나 접근할 수 없는 경우 무시
					logger.debug("Could not cleanup DB2 JDBC driver timer threads: " + e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.debug("Error checking for DB2 JDBC driver: " + e.getMessage());
		}
	}
	
	/**
	 * 등록된 JDBC 드라이버 해제
	 */
	private void deregisterJDBCDrivers() {
		try {
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver driver = drivers.nextElement();
				try {
					DriverManager.deregisterDriver(driver);
					logger.debug("Deregistered JDBC driver: " + driver.getClass().getName());
				} catch (Exception e) {
					logger.debug("Could not deregister JDBC driver " + driver.getClass().getName() + ": " + e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.debug("Error deregistering JDBC drivers: " + e.getMessage());
		}
	}
}

