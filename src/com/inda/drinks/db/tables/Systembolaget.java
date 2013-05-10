package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;

public class Systembolaget extends Table<Systembolaget.Item> {
	private final PreparedStatement insert, merge;

	public Systembolaget(Database db) throws SQLException {
		super(db, "Systembolaget", 1);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?);");
		merge = db.prepare("MERGE INTO " + super.TABLE_NAME
				+ " KEY(part_number) VALUES(?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (part_number INT NOT NULL PRIMARY KEY"
				+ ", price DOUBLE NOT NULL, volume INT NOT NULL);");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Item e) throws SQLException {
		preparedExecute(insert, e);
	}

	public void merge(Item e) throws SQLException {
		preparedExecute(merge, e);
	}

	private static void preparedExecute(PreparedStatement ps,
			Systembolaget.Item e) throws SQLException {
		ps.setInt(1, e.getPartNumber());
		ps.setDouble(2, e.getPrice());
		ps.setInt(3, e.getVolume());
		ps.executeUpdate();
	}

	/**
	 * Represents a row in the Systembolaget table.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Item {
		private final int partNumber; // "varunummer" in the Systembolaget API
		private final double price;
		private final int volume;

		private Item(int partNumber, double price, int volume) {
			this.partNumber = partNumber;
			this.price = price;
			this.volume = volume;
		}

		public int getPartNumber() {
			return partNumber;
		}

		public double getPrice() {
			return price;
		}

		public int getVolume() {
			return volume;
		}

		public String toString() {
			return String.format("Systembolaget.Item[%d, %.1f, %d]",
					getPartNumber(), getPrice(), getVolume());
		}
	}

	/**
	 * Builder pattern used for setting one parameter at a time since
	 * Systembolaget.Item is immutable.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Builder {
		// Underscore to separate from super class' variables of same name
		private int _volume, _partNumber;
		private double _price;

		public Systembolaget.Builder partNumber(int partNumber) {
			this._partNumber = partNumber;
			return this;
		}

		public Systembolaget.Builder price(double price) {
			this._price = price;
			return this;
		}

		public Systembolaget.Builder volume(int volume) {
			this._volume = volume;
			return this;
		}

		public Systembolaget.Item build() {
			// TODO: Check for validity
			return new Systembolaget.Item(_partNumber, _price, _volume);
		}

	}
}
