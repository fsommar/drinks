package com.inda.drinks.properties;

import java.util.Map;

public class Content {
	private int id;
	private Map<Ingredient, Volume> ingredients;

	public int getID() {
		return id;
	}
	
	public Map<Ingredient, Volume> getIngredients() {
		return ingredients;
	}

}
