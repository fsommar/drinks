package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.properties.Glass;

public class Glasses extends TableHelper<Glass> {
	public static final String TABLE_NAME = "Glasses";
	public static final int TABLE_VERSION = 1;
	private PreparedStatement insert;

	public Glasses(DbWrapper db) throws SQLException {
		super(db, TABLE_NAME, TABLE_VERSION);
		insert = db.prepare("INSERT INTO " + TABLE_NAME + " VALUES(?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		db.execute("CREATE TABLE " + TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY, name VARCHAR(255));");
		for (Glass glass : Glass.values()) {
			insert(glass);
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
