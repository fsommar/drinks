package com.inda.drinks.exceptions;

import java.sql.SQLException;

public class MissingDependencyException extends SQLException {

	private static final long serialVersionUID = 493146513684779304L;

	public MissingDependencyException(String s) {
		super(s);
	}
}
