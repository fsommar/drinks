package com.inda.drinks.db;

import com.inda.drinks.exceptions.DatabaseException;

public interface DbWrapper {
	public void execSQL(String s);
	public void open() throws DatabaseException;
	public void close();
}
