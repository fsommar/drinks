package com.inda.drinks.properties;

/**
 * Represents a drink recipe. This class is immutable, use Recipe.Builder to
 * create an object.
 * 
 * @author Fredrik Sommar
 */
public class Recipe {
	private final String name, instructions;
	private final int glassID, id;

	private Recipe(int id, String name, String instructions, int glassID) {
		this.id = id;
		this.name = name;
		this.instructions = instructions;
		this.glassID = glassID;
	}

	public int getID() {
		return id;
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

	/**
	 * Builder pattern used for setting one parameter at a time since Recipe is
	 * immutable.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Builder {
		private String _name, _instructions;
		private int _id, _glassID;

		public Builder ID(int id) {
			this._id = id;
			return this;
		}

		public Builder name(String name) {
			this._name = name;
			return this;
		}

		public Builder instructions(String name) {
			this._name = name;
			return this;
		}

		public Builder glassID(int glassID) {
			this._glassID = glassID;
			return this;
		}

		public Recipe build() {
			return new Recipe(_id, _name, _instructions, _glassID);
		}
	}
}
