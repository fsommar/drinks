package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;

public class Bar extends Table<Integer> {
	private final PreparedStatement insert;

	public Bar(DbWrapper db) throws SQLException {
		super(db, "Bar", 1);
		super.addDependency(Ingredients.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME + " VALUES(?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY, FOREIGN KEY(id) REFERENCES "
				+ Table.get(Ingredients.class).TABLE_NAME + "(id));");
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
}
