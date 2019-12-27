package com.payara.log4j.config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionFactory {

	protected static DataSource dataSource = null;

	private static final String dataSourceJNDIName = "jdbc/ds-test";

	private static void getDataSource() throws Exception {
		if (dataSource == null) {
			try {
				Context context = new InitialContext();
				dataSource = (javax.sql.DataSource) context.lookup(dataSourceJNDIName);
			} catch (NamingException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		try {
			if (dataSource == null) {
				getDataSource();
			}
			return dataSource.getConnection();
		} catch (Exception e) {
			throw new SQLException();
		}
	}
//	private static BasicDataSource dataSource;
//
//	private ConnectionFactory() {
//	}
//
//	public static Connection getConnection() throws SQLException {
//		if (dataSource == null) {
//			dataSource = new BasicDataSource();
//			dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//			dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
//			dataSource.setUsername("USER");
//			dataSource.setPassword("PASS");
//		}
//		return dataSource.getConnection();
//	}
//
//	private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
//
//	public static void main(String[] args) {
//		
//		logger.warn("WARN ");
//	}
}