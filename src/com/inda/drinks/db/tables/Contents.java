package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.properties.Content;
import com.inda.drinks.properties.Ingredient;

public class Contents extends TableHelper<Content> {
	private static final int TABLE_VERSION = 1;
	private static final String TABLE_NAME = "Contents";
	private PreparedStatement insert;
	
	public Contents(DbWrapper db) throws SQLException {
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
	public void insert(Content e) throws SQLException {
		for (Map.Entry<Ingredient, Integer> entry : e.getIngredients().entrySet()) {
			Ingredient i = entry.getKey();
			insert.setInt(1, e.getID());			
			insert.setInt(2, i.getID());
			insert.setInt(3, entry.getValue());
			insert.executeUpdate();
		}
	}

}
