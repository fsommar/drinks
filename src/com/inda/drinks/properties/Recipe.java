package com.inda.drinks.properties;

public class Recipe {
	private final String name, instructions;
	private final int glass_id;
	
	private Recipe(String name, String instructions, int glass_id) {
		this.name = name;
		this.instructions = instructions;
		this.glass_id = glass_id;
	}

	public String getName() {
		return name;
	}

	public String getInstructions() {
		return instructions;
	}
	
	public int getGlassID() {
		return glass_id;
	}
	
}
