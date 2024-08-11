package com.manager.web.app.abs;

import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

@Component
public class DataSet {

	private Sql2o sql2o;

	@Autowired
	public DataSet(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	private static final Logger LOGGER = Logger.getLogger(DataSet.class.getName());
	private String hostname;
	private String dbUrl;
	private String dbUsername;
	private String dbPassword;
	private String dbDatabaseName;
	private boolean ssl = false;
	private Map<String, Object> parameters = new HashMap<>();
//
//	static {
//		try {
//			com.mysql.cj.jdbc.Driver driver = new com.mysql.cj.jdbc.Driver();
//			DriverManager.registerDriver(driver);
//		} catch (Exception e) {
//			LOGGER.log(Level.SEVERE, "Failed to register MySQL driver", e);
//		}
//	}

	public DataSet() {
		this.sql2o = null;
	}

	public void setSSL(boolean ssl) {
		this.ssl = ssl;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public void setDbDatabaseName(String dbDatabaseName) {
		this.dbDatabaseName = dbDatabaseName;
	}

	private String getDbUrl() {
		if (ssl) {
			return "jdbc:mysql://" + hostname + ":3306/" + dbDatabaseName + "?useUnicode=yes&characterEncoding=UTF-8";
		} else {
			return "jdbc:mysql://" + hostname + ":3306/" + dbDatabaseName
					+ "?useSSL=false&useUnicode=yes&characterEncoding=UTF-8";
		}
	}

	private synchronized Sql2o getSql2o() {
		if (sql2o == null) {
			try {
				sql2o = new Sql2o(getDbUrl(), dbUsername, dbPassword);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Failed to initialize Sql2o", e);
			}
		}
		return sql2o;
	}

	public void setParameter(String key, Object value) {
		parameters.put(key, value);
	}

	public void setParameter(Map params) {
		parameters.putAll(params);
		;
	}

	public void clearParameters() {
		parameters.clear();
	}

	public int update(String sql) {
		try (Connection connection = getSql2o().open()) {
			Query query = connection.createQuery(sql);
			query = setParameters(query);
			query.getConnection().getJdbcConnection().setAutoCommit(false);
			int result = query.executeUpdate().getResult();
			query.getConnection().getJdbcConnection().commit();
			return result;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to execute update", e);
			return 0;
		}
	}

	public List<Map<String, Object>> searchAndRetrieve(String sql) {
		try (Connection connection = getSql2o().open()) {
			Query query = connection.createQuery(sql);
			query = setParameters(query);
			return query.executeAndFetchTable().asList();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Failed to execute query", e);
			return null;
		}
	}

	private Query setParameters(Query query) {
		try {
			parameters.forEach(query::addParameter);
		} catch (Exception e) {
			// Log the exception or handle it accordingly
			System.err.println("Failed to set parameters: " + e.getMessage());
			// Optionally, rethrow the exception if you want to propagate it
			// throw new RuntimeException("Failed to set parameters", e);
		}
		return query;
	}

}
