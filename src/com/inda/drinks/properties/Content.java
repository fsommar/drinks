package com.inda.drinks.properties;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.inda.drinks.tools.Formatter;

public class Content {
	private final int id;
	private final Map<Ingredient, Integer> ingredients;

	private Content(int id) {
		this.id = id;
		this.ingredients = new HashMap<Ingredient, Integer>();
	}

	public int getID() {
		return id;
	}

	public Map<Ingredient, Integer> getIngredients() {
		return ingredients;
	}

	/**
	 * Time complexity: O(n) where n is the number of ingredients.
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
		double r = -1;
		try {
			r = Formatter.oneDecHalfEven(100 * n / total);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return r;
	}

}
