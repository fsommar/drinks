package com.inda.drinks.properties;

public final class Category {
	public static final int NO_PARENT = 0;
	private final int id;
	private final String name;
	private final int parent;
	
	public Category(int id, String name, int parent) {
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
}
