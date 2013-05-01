package com.inda.drinks.db.tables;

import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.exceptions.VersionMismatchException;
import com.inda.drinks.properties.Ingredient;

public class Ingredients extends TableHelper<Ingredient> {
	private static final int TABLE_VERSION = 1;
	private static final String TABLE_NAME = "Ingredients";
	
	public Ingredients(DbWrapper db) throws VersionMismatchException {
		super(db, TABLE_NAME, TABLE_VERSION);
	}

	@Override
	public void onCreate() {
		
	}

	@Override
	public void onUpgrade(int from, int to) {
		// Nothing here yet
	}

	@Override
	public void insert(Ingredient e) throws SQLException {
		
	}

}
