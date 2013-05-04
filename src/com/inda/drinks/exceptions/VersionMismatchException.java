package com.inda.drinks.exceptions;

import java.sql.SQLException;

public class VersionMismatchException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6939790740824673676L;

	public VersionMismatchException(String s) {
		super(s);
	}}
