package com.inda.drinks.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DbWrapper implementation for the small java database H2.
 * @author Fredrik Sommar
 *
 */
public class H2Db implements DbWrapper {
	private Connection conn;
	private Statement statement;
	
	@Override
	public ResultSet query(String sql) throws SQLException {
		return getStatement().executeQuery(sql);
	}

	@Override
	public Statement execute(String sql) throws SQLException {
		getStatement().execute(sql);
		return getStatement();
	}
	
	@Override
	public PreparedStatement prepare(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

	@Override
	public void open(String loc, String usr, String pwd) throws SQLException {
		try {
			Class.forName("org.h2.Driver");
		} catch(ClassNotFoundException e) {
			// Do we want to reveal what kind of driver we are using?
			throw new SQLException("Unable to instantiate SQL driver.");
		}
		conn = DriverManager.getConnection("jdbc:h2:file:"+loc, usr, pwd);
	}

	@Override
	public void close() {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// We only ever need one statement object for our purposes
	private Statement getStatement() throws SQLException {
		if (statement == null) {
			statement = conn.createStatement();
		}
		return statement;
	}
}
