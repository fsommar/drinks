package com.inda.drinks.properties;

public class SystembolagetItem {
	private final int id;
	private final int varunummer; // I really don't like mixing languages
	private final double price;
	private final int volume; // in ml

	private SystembolagetItem(int id, int varunummer, double price, int volume) {
		this.id = id;
		this.varunummer = varunummer;
		this.price = price;
		this.volume = volume;
	}

	public int getID() {
		return id;
	}

	public int getVarunummer() {
		return varunummer;
	}

	public double getPrice() {
		return price;
	}

	public int getVolumeInMl() {
		return volume;
	}

}
