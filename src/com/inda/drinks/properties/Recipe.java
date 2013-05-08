package com.inda.drinks.properties;

public class Recipe {
	private final String name, instructions;
	private final int glassID;
	
	private Recipe(String name, String instructions, int glassID) {
		this.name = name;
		this.instructions = instructions;
		this.glassID = glassID;
	}

	public String getName() {
		return name;
	}

	public String getInstructions() {
		return instructions;
	}
	
	public int getGlassID() {
		return glassID;
	}	
}
