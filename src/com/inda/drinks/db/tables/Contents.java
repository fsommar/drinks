package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Content;
import com.inda.drinks.properties.Ingredient;

public class Contents extends Table<Content> {
	private final PreparedStatement insert;

	public Contents(DbWrapper db) throws SQLException {
		super(db, "Contents", 1);
		super.addDependency(Recipes.class);
		super.addDependency(Ingredients.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?)");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (recipe_id INT NOT NULL, ingredient_id INT NOT NULL,"
				+ " volume_cl INT NOT NULL,"
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
		for (Map.Entry<Ingredient, Integer> entry : e.getIngredients()
				.entrySet()) {
			insert.setInt(1, e.getID());
			insert.setInt(2, entry.getKey().getID());
			insert.setInt(3, entry.getValue());
			insert.executeUpdate();
		}
	}
}
