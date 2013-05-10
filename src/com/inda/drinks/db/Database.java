package com.inda.drinks.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Small wrapper class for java database functionality.
 * @author Fredrik Sommar
 *
 */
public interface Database {
	/**
	 * Executes the supplied SQL statement. Used when multiple ResultSets are
	 * expected.
	 * 
	 * @param SQL
	 *            the SQL statement to be executed.
	 * @return the Statement on which the SQL was executed on. First ResultSet
	 *         is acquired by <code>getResultSet</code> and in case there are
	 *         multiple results <code>getMoreResults</code> has to be called.
	 * @throws SQLException
	 */
	public Statement execute(String SQL) throws SQLException;

	/**
	 * Queries the supplied SQL statement and returns the ResultSet.
	 * 
	 * @param SQL
	 *            the SQL query to be executed.
	 * @return the ResultSet which contains whatever the query returns.
	 * @throws SQLException
	 */
	public ResultSet query(String SQL) throws SQLException;

	/**
	 * Creates a PreparedStatement object based on the supplied SQL.
	 * 
	 * @param SQL
	 *            the statement to prepare for future executions.
	 * @return the PreparedStatement object made of the supplied SQL.
	 * @throws SQLException
	 */
	public PreparedStatement prepare(String SQL) throws SQLException;

	/**
	 * Opens a connection to the database making it available for duty :-).
	 * 
	 * @param loc
	 *            the location of the database.
	 * @param usr
	 *            the username for the database.
	 * @param pwd
	 *            the password for the database.
	 * @throws SQLException
	 */
	public void open(String loc, String usr, String pwd) throws SQLException;

	/**
	 * Closes the database connection with its related Statements and
	 * ResultSets.
	 */
	public void close();
}
