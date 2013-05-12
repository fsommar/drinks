package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Ingredient;

public class Bar extends Table<Integer> {
	private final PreparedStatement insert;

	public Bar(Database db) throws SQLException {
		super(db, "Bar", 1);
		super.addDependency(Ingredients.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME + " VALUES(?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY, FOREIGN KEY(id) REFERENCES "
				+ Table.get(Ingredients.class).TABLE_NAME + "(id));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet :-)
	}

	@Override
	public void insert(Integer e) throws SQLException {
		insert.setInt(1, e);
		insert.executeUpdate();
	}

	public Set<Ingredient> getAllIngredients() {
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		try {
			ResultSet res = super.db.query("SELECT i.* FROM "
					+ super.TABLE_NAME
					+ " as b JOIN Ingredients AS i ON b.id = i.id;");
			Ingredient.Builder builder = new Ingredient.Builder();
			while (res.next()) {
				Ingredient ingredient = builder
						.ID(res.getInt(1))
						.name(res.getString(2))
						.subtitle(res.getString(3))
						.ABV(res.getDouble(4))
						.category(
								Table.get(Categories.class).getCategory(
										res.getInt(5)))
						.partNumber(res.getInt(6)).build();
				ingredients.add(ingredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredients;
	}
}
