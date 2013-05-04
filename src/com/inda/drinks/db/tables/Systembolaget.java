package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.SystembolagetItem;

public class Systembolaget extends Table<SystembolagetItem> {
	private final PreparedStatement insert;

	public Systembolaget(DbWrapper db) throws SQLException {
		super(db, "Systembolaget", 1);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY,"
				+ " varunummer INT NOT NULL, price DOUBLE NOT NULL,"
				+ " volume_ml INT NOT NULL);");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(SystembolagetItem e) throws SQLException {
		insert.setInt(1, e.getID());
		insert.setInt(2, e.getVarunummer());
		insert.setDouble(3, e.getPrice());
		insert.setInt(4, e.getVolumeInMl());
		insert.executeUpdate();
	}
}
