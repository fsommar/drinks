package com.inda.drinks.properties;

/**
 * 
 * @author Fredrik Sommar
 * @version database 1
 */
public enum VolumeUnit {
	CL("cl"), FILL_UP("resten");

	private String s;

	private VolumeUnit(String s) {
		this.s = s;
	}

	public int getID() {
		return ordinal() + 1;
	}

	@Override
	public String toString() {
		return s;
	}
}
