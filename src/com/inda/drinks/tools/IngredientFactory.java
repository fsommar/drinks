package com.inda.drinks.tools;

import com.inda.drinks.properties.Category;
import com.inda.drinks.properties.Ingredient;

public class IngredientFactory {
		private static IngredientFactory factory;
		private String name, subtitle;
		private double ABV;
		private Category category;
		
		private IngredientFactory() {
			reset();
		}
		
		public static IngredientFactory newInstance() {
			if (factory == null) {
				factory = new IngredientFactory();
			}
			return factory;
		}
		
		public Ingredient create() {
			return new Ingredient(name, subtitle, ABV, category);
		}
		
		public void reset() {
			name = "";
			subtitle = "";
			ABV = -1;
			category = Category.DEFAULT;
		}

		public IngredientFactory setName(String name) {
			this.name = name;
			return this;
		}

		public IngredientFactory setSubtitle(String subtitle) {
			this.subtitle = subtitle;
			return this;
		}

		public IngredientFactory setABV(double ABV) {
			this.ABV = ABV;
			return this;
		}

		public IngredientFactory setCategory(Category category) {
			this.category = category;
			return this;
		}
		
	}
