package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Glass;

public class Glasses extends Table<Glass> {
	private final PreparedStatement insert;

	public Glasses(DbWrapper db) throws SQLException {
		super(db, "Glasses", 1);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY, name VARCHAR(255));");
		// Add enum values immediately to table
		for (Glass glass : Glass.values()) {
			this.insert(glass);
		}
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// First make sure all references are intact
		// db.execute("DROP TABLE IF EXISTS "+TABLE_NAME+";");
		// onCreate();
	}

	@Override
	public void insert(Glass e) throws SQLException {
		insert.setInt(1, e.getID());
		insert.setString(2, e.toString());
		insert.executeUpdate();
	}
}
