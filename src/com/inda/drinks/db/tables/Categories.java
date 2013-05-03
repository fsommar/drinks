package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.properties.Category;

public class Categories extends TableHelper<Category> {
	public static final String TABLE_NAME = "Categories";
	public static final int TABLE_VERSION = 1;
	private final PreparedStatement insert;

	public Categories(DbWrapper db) throws SQLException {
		super(db, TABLE_NAME, TABLE_VERSION);
		insert = super.db.prepare("INSERT INTO "+TABLE_NAME+" VALUES(?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + TABLE_NAME
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
