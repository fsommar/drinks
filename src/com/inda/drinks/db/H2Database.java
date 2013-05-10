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
public class H2Database implements Database {
	private Connection conn;
	private Statement statement;
	
	@Override
	public ResultSet query(String sql) throws SQLException {
		return conn.createStatement().executeQuery(sql);
	}

	@Override
	public Statement execute(String sql) throws SQLException {
		Statement m = conn.createStatement();
		m.execute(sql);
		return m;
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
}
