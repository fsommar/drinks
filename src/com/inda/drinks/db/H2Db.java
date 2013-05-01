package com.inda.drinks.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Db implements DbWrapper {
	private Connection conn;
	private Statement statement;
	
	@Override
	public ResultSet query(String sql) throws SQLException {
		return getStatement().executeQuery(sql);
	}

	@Override
	public void execute(String sql) throws SQLException {
		getStatement().execute(sql);
	}
	
	@Override
	public PreparedStatement prepare(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

	@Override
	public void open() throws SQLException {
		try {
			Class.forName("org.h2.Driver");
		} catch(ClassNotFoundException e) {
			// Do we want to reveal what kind of driver we are using?
			throw new SQLException("Unable to instantiate SQL driver.");
		}
		conn = DriverManager.getConnection("jdbc:h2:/data/really_unique_name", "usr", "pwd");
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
	
	private Statement getStatement() throws SQLException {
		if (statement == null) {
			statement = conn.createStatement();
		}
		return statement;
	}
}
