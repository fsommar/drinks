package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;

public class Systembolaget extends Table<Systembolaget.Item> {
	private final PreparedStatement insert;

	public Systembolaget(DbWrapper db) throws SQLException {
		super(db, "Systembolaget", 1);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (article_id INT NOT NULL PRIMARY KEY,"
				+ " part_number INT NOT NULL, price DOUBLE NOT NULL,"
				+ " volume INT NOT NULL);");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Item e) throws SQLException {
		insert.setInt(1, e.getArticleID());
		insert.setInt(2, e.getPartNumber());
		insert.setDouble(3, e.getPrice());
		insert.setInt(4, e.getVolume());
		insert.executeUpdate();
	}

	/**
	 * Represents a row in the Systembolaget table.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Item {
		private final int articleID;
		private final int partNumber; // "varunummer" in the Systembolaget API
		private final double price;
		private final int volume;

		private Item(int articleID, int partNumber, double price, int volume) {
			this.articleID = articleID;
			this.partNumber = partNumber;
			this.price = price;
			this.volume = volume;
		}

		public int getArticleID() {
			return articleID;
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
	}

	/**
	 * Builder pattern used for setting one parameter at a time since
	 * Systembolaget.Item is immutable.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class Builder {
		// Underscore to separate from super class' variables of same name
		private int _articleID, _volume, _partNumber;
		private double _price;

		public Systembolaget.Builder articleID(int articleID) {
			this._articleID = articleID;
			return this;
		}

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
			return new Systembolaget.Item(_articleID, _partNumber, _price,
					_volume);
		}

	}
}
