package com.inda.drinks.db.tables;

import com.inda.drinks.db.TableHelper;
import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.exceptions.VersionMismatchException;

public class Glasses extends TableHelper {
	private static final int TABLE_VERSION = 1;
	private static final String TABLE_NAME = "Glasses";
	
	public Glasses(DbWrapper db) throws VersionMismatchException {
		super(db, TABLE_NAME, TABLE_VERSION);
	}

	@Override
	public void onCreate(DbWrapper db) {
//		db.execSQL("");
	}

	@Override
	public void onUpgrade(DbWrapper db, int from, int to) {
		// Nothing here yet
	}

}
