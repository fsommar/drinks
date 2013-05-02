package com.inda.drinks.properties;

import java.util.Map;

public class Content {
	private final int id;
	private Map<Ingredient, Integer> ingredients;
	
	private Content(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public Map<Ingredient, Integer> getIngredients() {
		return ingredients;
	}

}
