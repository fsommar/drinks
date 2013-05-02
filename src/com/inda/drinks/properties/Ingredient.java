package com.inda.drinks.properties;

public final class Ingredient {
	final int id;
	final String name;
	final String subtitle;
	final double ABV;
	final Category category;

	private Ingredient(int id, String name, String subtitle, double ABV,
			Category category) {
		this.id = id;
		this.name = name;
		this.subtitle = subtitle;
		this.ABV = ABV;
		this.category = category;
	}

	public Ingredient(String name, String subtitle, double ABV, Category category) {
		this(-1, name, subtitle, ABV, category);
	}

	public int getID() {
		return id;
	}
	
	public double getABV() {
		return ABV;
	}

}
