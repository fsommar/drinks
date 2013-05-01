package com.inda.drinks.properties;

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

}
