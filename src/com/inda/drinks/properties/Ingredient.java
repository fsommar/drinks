package com.inda.drinks.properties;

public final class Ingredient {
	private final int id;
	private final String name;
	private final String subtitle;
	private final double ABV;
	private final Category category;
	private final int partNumber; // "varnummer" in Systembolaget API

	private Ingredient(int id, String name, String subtitle, double ABV,
			Category category, int partNumber) {
		this.id = id;
		this.name = name;
		this.subtitle = subtitle;
		this.ABV = ABV;
		this.category = category;
		this.partNumber = partNumber;
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

	public int getPartNumber() {
		return partNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Ingredient)) {
			return false;
		}
		Ingredient i = (Ingredient) o;
		return i.id == this.id && i.name.equals(this.name)
				&& i.subtitle.equals(this.subtitle) && i.ABV == this.ABV
				&& i.getCategory().equals(this.getCategory())
				&& i.partNumber == this.partNumber;
	}

	@Override
	public String toString() {
		// return String.format("Ingredient[%d, %s, %s, %2.1f, %s, %d]",
		// getID(), getName(), getSubtitle(), getABV(), getCategory(),
		// getPartNumber());
		return name + (subtitle != null ? " - " + subtitle : "");
	}

	/**
	 * Builder pattern used for setting one parameter at a time since Ingredient
	 * is immutable.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Builder {
		// Underscores used for builder variables
		private int _id, _partNumber;
		private String _name, _subtitle;
		private double _ABV;
		private Category _category;

		public Builder() {
			this._id = -1;
			this._partNumber = -1;
		}

		public Ingredient build() {
			// Check for validity
			return new Ingredient(_id, _name, _subtitle, _ABV, _category,
					_partNumber);
		}

		public Builder ID(int id) {
			this._id = id;
			return this;
		}

		public Builder name(String name) {
			this._name = name;
			return this;
		}

		public Builder subtitle(String subtitle) {
			this._subtitle = subtitle;
			return this;
		}

		public Builder ABV(double ABV) {
			this._ABV = ABV;
			return this;
		}

		public Builder category(Category category) {
			this._category = category;
			return this;
		}

		public Builder partNumber(int partNumber) {
			this._partNumber = partNumber;
			return this;
		}
	}

}
