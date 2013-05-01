package com.inda.drinks.exceptions;

import java.sql.SQLException;

public class VersionMismatchException extends SQLException {

	public VersionMismatchException(String s) {
		super(s);
	}}
