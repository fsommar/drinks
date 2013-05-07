package com.inda.drinks.properties;

public final class Ingredient {
	private final int id;
	private final String name;
	private final String subtitle;
	private final double ABV;
	private final Category category;
	private final int systembolaget_id;

	private Ingredient(int id, String name, String subtitle, double ABV,
			Category category, int systembolaget_id) {
		this.id = id;
		this.name = name;
		this.subtitle = subtitle;
		this.ABV = ABV;
		this.category = category;
		this.systembolaget_id = systembolaget_id;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public double getABV() {
		return ABV;
	}

	public Category getCategory() {
		return category;
	}

	public int getSystembolagetID() {
		return systembolaget_id;
	}

	/**
	 * Builder pattern used for setting one parameter at a time since
	 * Ingredient is immutable.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Builder {
		private int id;
		private String name, subtitle;
		private double ABV;
		private Category category;
		private int systembolaget_id;

		public Builder() {
			this.id = -1;
			this.systembolaget_id = -1;
		}

		public Ingredient build() {
			// Check for validity
			return new Ingredient(id, name, subtitle, ABV, category,
					systembolaget_id);
		}

		public Builder ID(int id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder subtitle(String subtitle) {
			this.subtitle = subtitle;
			return this;
		}

		public Builder ABV(double ABV) {
			this.ABV = ABV;
			return this;
		}

		public Builder category(Category category) {
			this.category = category;
			return this;
		}

		public Builder systembolagetID(int systembolaget_id) {
			this.systembolaget_id = systembolaget_id;
			return this;
		}
	}

}
