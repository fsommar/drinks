package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Glass;

public class Glasses extends Table<Glass> {
	private final PreparedStatement prepared;

	public Glasses(Database db) throws SQLException {
		super(db, "Glasses", 1);
		prepared = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?);");
		if (super.wasCreated()) {
			insertGlasses();
		}
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY, name VARCHAR(50));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		db.execute("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
		onCreate();
		insertGlasses();
	}

	@Override
	public void insert(Glass e) throws SQLException {
		prepared.setInt(1, e.getID());
		prepared.setString(2, e.toString());
		prepared.executeUpdate();
	}

	private void insertGlasses() throws SQLException {
		for (Glass glass : Glass.values()) {
			insert(glass);
		}
	}
}
