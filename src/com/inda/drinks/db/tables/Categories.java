package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Category;

public class Categories extends Table<Category> {
	private final PreparedStatement insert;

	public Categories(DbWrapper db) throws SQLException {
		super(db, "Categories", 1);
		insert = super.db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY,"
				+ " name VARCHAR(15) NOT NULL, parent INT NOT NULL);");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Category e) throws SQLException {
		insert.setInt(1, e.getID());
		insert.setString(2, e.getName());
		insert.setInt(3, e.getParentID());
		insert.executeUpdate();
	}
}
