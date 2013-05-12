package com.inda.drinks.properties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.inda.drinks.db.Table;
import com.inda.drinks.db.tables.Categories;
import com.inda.drinks.gui.Resources;
import com.inda.drinks.tools.Formatter;

/**
 * Represents all the content for one recipe; the ID is the same as that of the
 * recipe it represents. This class is immutable.
 * 
 * @author Fredrik Sommar
 */
public class Content {
	private final int id;
	private final Set<Content.Item> ingredients;

	/**
	 * Constructor for content.
	 * 
	 * @param id
	 *            the id of the recipe these contents are linked to.
	 */
	public Content(int id) {
		this.id = id;
		this.ingredients = new HashSet<Content.Item>();
	}

	/**
	 * @return the id of the Recipe that this object corresponds to.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Adds an ingredient and its volume to contents.
	 * 
	 * @param ingredient
	 *            the ingredient to add to contents.
	 * @param volume
	 *            the ingredient's volume in millilitres.
	 * @param specific
	 *            whether to use this specific ingredient or just its category.
	 */
	public void add(Ingredient ingredient, int volume, boolean specific) {
		add(new Item(ingredient, volume, specific));
	}

	/**
	 * Adds a content item to contents. Functionalitywise the same as
	 * {@link#add(Ingredient, int, boolean)}.
	 * 
	 * @param item
	 *            the content item to add to contents.
	 */
	public void add(Content.Item item) {
		ingredients.add(item);
	}

	/**
	 * Returns a map of ingredients and their volume for this entry.
	 * 
	 * @return a map of ingredients as keys with the volume as value.
	 */
	public Set<Content.Item> getContents() {
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
		for (Content.Item ci : getContents()) {
			int volume = ci.getVolume();
			n += volume * ci.getIngredient().getABV() / 100;
			total += volume;
		}
		return Formatter.oneDecHalfEven(100 * n / total);
	}

	/**
	 * Represents a row in the Contents table, i.e. an ingredient linked to a
	 * recipe.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Item {
		private final Ingredient ingredient;
		private final int volume;
		private final boolean specific;

		public Item(Ingredient ingredient, int volume, boolean specific) {
			this.ingredient = ingredient;
			this.volume = volume;
			this.specific = specific;
		}

		public Ingredient getIngredient() {
			return ingredient;
		}

		public int getVolume() {
			return volume;
		}

		public boolean isSpecific() {
			return specific;
		}

		@Override
		public boolean equals(Object e) {
			if (!(e instanceof Item)) {
				return false;
			}
			Item i = (Item) e;
			if (!specific /* If not specific and categories don't match */
					&& i.ingredient.getCategory().equals(
							this.ingredient.getCategory())) {
				return true;
			}
			return i.ingredient.equals(this.ingredient)
					&& i.volume == this.volume && i.specific == this.specific;
		}

		public String toString() {
			String str = "";
			if (specific) {
				str = ingredient.toString();
			} else {
				ArrayList<Category> l = new ArrayList<Category>();
				Category c = ingredient.getCategory();
				l.add(c);
				while (c.getParentID() != Category.NO_PARENT) {
					c = Table.get(Categories.class)
							.getCategory(c.getParentID());
					l.add(c);
				}
				for (int i = l.size() - 1; i >= 0; i--) {
					str += l.get(i).getName() + " ";
				}
			}
			return volume + " " + Resources.CL + " " + str;
		}

	}

}
