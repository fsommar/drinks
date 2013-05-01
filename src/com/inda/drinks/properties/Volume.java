package com.inda.drinks.properties;

public class Volume {
	private final int amount;
	private final VolumeUnit unit;
	
	private Volume(int amount, VolumeUnit unit) {
		this.amount = amount;
		this.unit = unit;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public VolumeUnit getUnit() {
		return unit;
	}
}
