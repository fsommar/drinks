package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.properties.Recipe;

public class Recipes extends TableHelper<Recipe> {
	private static final int TABLE_VERSION = 1;
	private static final String TABLE_NAME = "Recipes";
	private PreparedStatement insert;
	
	public Recipes(DbWrapper db) throws SQLException {
		super(db, TABLE_NAME, TABLE_VERSION);
		insert = db.prepare("INSERT INTO "+TABLE_NAME+" VALUES(?, ?, ?, ?)");
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onUpgrade(int from, int to) {
		// Nothing here yet
	}

	@Override
	public void insert(Recipe e) throws SQLException {
		insert.setString(1, e.getName());
		insert.setString(2, e.getInstructions());
		insert.setInt(3, e.getGlass().getID());
		insert.setInt(4, e.getContent().getID());
		// TODO: insert Content as well
		// SELECT MAX(id) FROM <TABLE_NAME>;
		insert.executeUpdate();
	}

}
