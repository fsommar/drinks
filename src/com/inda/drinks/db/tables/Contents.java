package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Content;
import com.inda.drinks.properties.Ingredient;

public class Contents extends Table<Content> {
	private final PreparedStatement insert;

	public Contents(Database db) throws SQLException {
		super(db, "Contents", 1);
		super.addDependency(Recipes.class);
		super.addDependency(Ingredients.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?, ?)");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE "
				+ super.TABLE_NAME
				+ " (recipe_id INT NOT NULL, ingredient_id INT NOT NULL,"
				+ " specific BOOLEAN NOT NULL, volume INT NOT NULL,"
				+ " CONSTRAINT contents_pk PRIMARY KEY(recipe_id, ingredient_id),"
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

	public Content getContent(int id) {
		try {
			ResultSet res = super.db
					.query("SELECT i.id, i.name, i.subtitle, i.ABV, i.category, i.part_number,"
							+ " c.volume, c.specific FROM "
							+ super.TABLE_NAME
							+ " as c JOIN "
							+ Table.get(Ingredients.class).TABLE_NAME
							+ " as i ON c.ingredient_id = i.id WHERE c.recipe_id = "
							+ id + ";");
			Content c = new Content(id);
			Ingredient.Builder iBuilder = new Ingredient.Builder();
			while (res.next()) {
				Ingredient ingredient = iBuilder
						.ID(res.getInt(1))
						.name(res.getString(2))
						.subtitle(res.getString(3))
						.ABV(res.getDouble(4))
						.category(
								Table.get(Categories.class).getCategory(
										res.getInt(5)))
						.partNumber(res.getInt(6)).build();
				c.add(ingredient, res.getInt(7), res.getBoolean(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
