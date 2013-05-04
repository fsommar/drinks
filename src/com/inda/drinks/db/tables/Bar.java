package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;

public class Bar extends Table<Integer> {
	public static final String TABLE_NAME = "Bar";
	public static final int TABLE_VERSION = 1;
	private final PreparedStatement insert;

	public Bar(DbWrapper db) throws SQLException {
		super(db, TABLE_NAME, TABLE_VERSION);
		super.addDependency(Ingredients.class);
		insert = db.prepare("INSERT INTO " + TABLE_NAME + " VALUES(?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY, FOREIGN KEY(id) REFERENCES "
				+ Ingredients.TABLE_NAME + "(id));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet :-)
	}

	@Override
	public void insert(Integer e) throws SQLException {
		insert.setInt(1, e);
		insert.executeUpdate();
	}

	@Override
	public void drop() throws SQLException {
		super.db.execute("DROP TABLE IF EXISTS "+TABLE_NAME+";");		
	}

}
