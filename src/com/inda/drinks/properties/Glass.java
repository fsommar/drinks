package com.inda.drinks.properties;

/**
 * @author Fredrik Sommar
 * @version database 1
 */
public enum Glass {
	LOWBALL("lowball", 1), HIGHBALL("highball", 2);

	private final String s;
	private final int id;

	private Glass(String s, int id) {
		this.s = s;
		this.id = id;
	}

	/**
	 * @return the ID of the glass in the Glasses table.
	 */
	public int getID() {
		return id;
	}

	@Override
	public String toString() {
		return s;
	}

	/**
	 * Loops through enum values looking for an id match.
	 * Time complexity: worst case O(n).
	 * @param id the id of the Glass to be returned.
	 * @return the Glass enum with the given id. If it doesn't exist it returns null.
	 */
	public static Glass get(int id) {
		if (id < 1) {
			throw new IllegalArgumentException("Glass id #"+id+" should be greater than 0.");
		}
		for (Glass g : values()) {
			if (g.getID() == id) {
				return g;
			}
		}
		return null;
	}
}
