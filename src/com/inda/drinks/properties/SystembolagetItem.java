package com.inda.drinks.properties;

public class SystembolagetItem {
	private int id;
	private int varunummer; // I really don't like mixing languages
	private double price;
	private int volume; // in ml

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

	public int getVolume() {
		return volume;
	}

}
