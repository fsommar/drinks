package com.inda.drinks.properties;

public final class Category {
	public static final int NO_PARENT = 0;
	private final int id;
	private final String name;
	private final int parent;
	
	private Category(int id, String name, int parent) {
		if (id <= 0) {
			throw new IllegalArgumentException("Category id should be > 0.");
		}
		if (parent < 0) {
			throw new IllegalArgumentException("Parent id should be >= 0.");
		}
		this.id = id;
		this.name = name;
		this.parent = parent;
	}

	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public int getParentID() {
		return parent;
	}
	
	public static class Builder {
		private int _id, _parent;
		private String _name;
		
		public Builder ID(int id) {
			this._id = id;
			return this;
		}
		
		public Builder name(String name) {
			this._name = name;
			return this;
		}
		
		public Builder parent(int parent) {
			this._parent = parent;
			return this;
		}
		
		public Category build() {
			return new Category(_id, _name, _parent);
		}
	}
}
