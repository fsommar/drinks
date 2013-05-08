package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Content;

public class Contents extends Table<Content> {
	private final PreparedStatement insert;

	public Contents(DbWrapper db) throws SQLException {
		super(db, "Contents", 1);
		super.addDependency(Recipes.class);
		super.addDependency(Ingredients.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?, ?)");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (recipe_id INT NOT NULL, ingredient_id INT NOT NULL,"
				+ " specific BOOLEAN NOT NULL, volume INT NOT NULL,"
				+ " CONSTRAINT pk PRIMARY KEY(recipe_id, ingredient_id),"
				+ " FOREIGN KEY(recipe_id) REFERENCES "
				+ Table.get(Recipes.class).TABLE_NAME
				+ "(id), FOREIGN KEY(ingredient_id) REFERENCES "
				+ Table.get(Ingredients.class).TABLE_NAME + "(id));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Content e) throws SQLException {
		// TODO: insert Content as well
		// SELECT MAX(id) FROM <TABLE_NAME>;
		for (Content.Item ci : e.getContents()) {
			insert.setInt(1, e.getID());
			insert.setInt(2, ci.getIngredient().getID());
			insert.setBoolean(3, ci.isSpecific());
			insert.setInt(4, ci.getVolume());
			insert.executeUpdate();
		}
	}
}
