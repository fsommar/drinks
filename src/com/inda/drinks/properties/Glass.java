package com.inda.drinks.properties;

/**
 * @author Fredrik Sommar
 * @version database 1
 */
public enum Glass {
	LOWBALL("lowball"), HIGHBALL("highball");

	private String s;

	private Glass(String s) {
		this.s = s;
	}

	/**
	 * @return the ID of the glass in the Glasses table.
	 */
	public int getID() {
		return ordinal() + 1;
	}

	@Override
	public String toString() {
		return s;
	}
}
