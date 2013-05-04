package com.inda.drinks.exceptions;

public class MissingDependencyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 493146513684779304L;

	public MissingDependencyException(String s) {
		super(s);
	}
}
