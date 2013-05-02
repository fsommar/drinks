package com.inda.drinks.properties;

import java.util.Map;

public class Content {
	private int id;
	private Map<Ingredient, Integer> ingredients;

	public int getID() {
		return id;
	}
	
	public Map<Ingredient, Integer> getIngredients() {
		return ingredients;
	}

}
