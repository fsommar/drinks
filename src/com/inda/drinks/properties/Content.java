package com.inda.drinks.properties;

import java.util.HashMap;
import java.util.Map;

import com.inda.drinks.tools.Formatter;

/**
 * Represents a row in the Categories table.
 * 
 * @author Fredrik Sommar
 * 
 */
public class Content {
	private final int id;
	private final Map<Ingredient, Integer> ingredients;

	private Content(int id) {
		this.id = id;
		this.ingredients = new HashMap<Ingredient, Integer>();
	}

	/**
	 * @return the id of the Recipe that this object corresponds to.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Returns a map of ingredients and their volume for this entry.
	 * 
	 * @return a map of ingredients as keys with the volume as value.
	 */
	public Map<Ingredient, Integer> getIngredients() {
		return ingredients;
	}

	/**
	 * Calculates the ABV (Alcohol By Volume) of the contents and returns it as
	 * a percentage, e.g. 40. Time complexity: O(n) where n is the number of
	 * ingredients.
	 * 
	 * @return -1 if failed, else ABV in percent.
	 */
	public double calculateABV() {
		double n = 0;
		double total = 0;
		for (Map.Entry<Ingredient, Integer> m : getIngredients().entrySet()) {
			int volume = m.getValue();
			n += volume * m.getKey().getABV() / 100;
			total += volume;
		}
		return Formatter.oneDecHalfEven(100 * n / total);
	}

}
