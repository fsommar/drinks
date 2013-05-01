package com.inda.drinks.properties;

public final class Ingredient {
	final int id;
	final String name;
	final int ABV;
	final Category category;
	
	private Ingredient(int id, String name, int ABV, Category category) {
		this.id = id;
		this.name = name;
		this.ABV = ABV;
		this.category = category;
	}

	public int getID() {
		return id;
	}
	
}
