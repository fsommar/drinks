package com.inda.drinks.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DbWrapper {
	public void execute(String s) throws SQLException;
	public ResultSet query(String s) throws SQLException;
	public PreparedStatement prepare(String s) throws SQLException;
	public void open() throws SQLException;
	public void close();
}
