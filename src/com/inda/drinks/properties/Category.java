package com.inda.drinks.properties;

public final class Category {
	public static final Category DEFAULT = new Category(0);
	private final int id;
	private final Category parent;

	private Category(int id) {
		this(id, DEFAULT);
	}

	public Category(int id, Category parent) {
		if (id <= 0) {
			throw new IllegalArgumentException("Category id should be > 0.");
		}
		this.id = id;
		this.parent = parent;
	}

	public int getID() {
		return id;
	}

	public Category getParent() {
		return parent;
	}
}
