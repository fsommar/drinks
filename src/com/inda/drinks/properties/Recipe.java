package com.inda.drinks.properties;

import java.text.ParseException;
import java.util.Map;

import com.inda.drinks.tools.Formatter;

public class Recipe {
	private String name, instructions;
	private Glass glass;
	private Content content;

	public String getName() {
		return name;
	}

	public String getInstructions() {
		return instructions;
	}

	public Glass getGlass() {
		return glass;
	}

	public Content getContent() {
		return content;
	}

	/**
	 * Time complexity: O(n) where n is the number of ingredients.
	 * @return -1 if failed, else ABV in percent.
	 */
	public double calculateABV() {
		double n = 0;
		double total = 0;
		for (Map.Entry<Ingredient, Integer> m : content.getIngredients()
				.entrySet()) {
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
