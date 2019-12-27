package com.payara.jdbc.module;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CloudDatabaseModule {
	private static final Logger logger = Logger.getLogger(CloudDatabaseModule.class.getName());

	protected static DataSource dataSource = null;

	private static final String dataSourceJNDIName = "jdbc/ds-test";

	private void getDataSource() throws Exception {
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

	public synchronized void appendInvalidLogin(String message, String logger, String level) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO APP_AUDIT (LOG_ID, ENTRY_DATE, LOGGER, LOG_LEVEL, MESSAGE) VALUES (?, ? , ?, ?, ?)");
			stmt.setString(1, UUID.randomUUID().toString());
			stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			stmt.setString(3, logger);
			stmt.setString(4, level);
			stmt.setString(5, message);
			stmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(stmt);
			closeConnection(conn);
		}
	}

	private Connection getConnection() throws Exception {
		try {
			if (dataSource == null) {
				getDataSource();
			}
			return dataSource.getConnection();
		} catch (Exception e) {
			logger.log(Level.ALL, "Failed to get connection: " + e, e);
			throw new Exception();
		}
	}

	private void closeConnection(Connection connnection) {
		try {
			if (connnection != null) {
				connnection.close();
				connnection = null;
			}
		} catch (SQLException e) {
			logger.log(Level.ALL, "Error while closing connection : " + e, e);
		}
	}

	private void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (SQLException e) {
			logger.log(Level.ALL, "Error while closing statement : " + e, e);
		}
	}

}
