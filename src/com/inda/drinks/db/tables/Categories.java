package com.inda.drinks.db.tables;

import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.exceptions.VersionMismatchException;
import com.inda.drinks.properties.Category;

public class Categories extends TableHelper<Category> {
	public static final String TABLE_NAME = "Categories";
	public static final int TABLE_VERSION = 1;

	public Categories(DbWrapper db) throws VersionMismatchException {
		super(db, TABLE_NAME, TABLE_VERSION);
	}

	@Override
	public void onCreate() throws SQLException {
		db.execute("CREATE TABLE " + TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY, name TEXT NOT NULL, parent INT NOT NULL);");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Category value) {
		// TODO Auto-generated method stub

	}

}
