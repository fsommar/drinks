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

	public Ingredient(String name, String subtitle, double ABV,
			Category category, int systembolaget_id) {
		this(-1, name, subtitle, ABV, category, systembolaget_id);
	}

	public Ingredient(String name, String subtitle, double ABV,
			Category category) {
		this(-1, name, subtitle, ABV, category, -1);
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

}
