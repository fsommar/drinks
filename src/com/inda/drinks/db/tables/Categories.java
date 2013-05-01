package com.inda.drinks.db.tables;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.exceptions.VersionMismatchException;
import com.inda.drinks.properties.Category;

public class Categories extends TableHelper<Category> {
	private static final int TABLE_VERSION = 1;
	private static final String TABLE_NAME = "Categories";
	
	public Categories(DbWrapper db) throws VersionMismatchException {
		super(db, TABLE_NAME, TABLE_VERSION);
	}

	@Override
	public void onCreate() {
//		db.execSQL("");
	}

	@Override
	public void onUpgrade(int from, int to) {
		// Nothing here yet
	}

	@Override
	public void insert(Category value) {
		// TODO Auto-generated method stub
		
	}

}
