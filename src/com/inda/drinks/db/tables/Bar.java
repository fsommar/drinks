package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Ingredient;

public class Bar extends Table<Integer> {
	private final PreparedStatement insert, remove;

	public Bar(Database db) throws SQLException {
		super(db, "Bar", 1);
		super.addDependency(Ingredients.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME + " VALUES(?);");
		remove = db.prepare("DELETE FROM " + super.TABLE_NAME
				+ " WHERE id = ?;");
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
	
	/**
	 * Removes the bar ingredient with the supplied id.
	 * @param id the ingredient id of the ingredient to be removed.
	 * @throws SQLException
	 */
	public void remove(int id) throws SQLException {
		remove.setInt(1, id);
		remove.executeUpdate();
	}

	/**
	 * Returns a set of all the ingredients currently in the user's bar.
	 * 
	 * @return a set of ingredients, which is empty if there's nothing in the
	 *         bar.
	 */
	public List<Ingredient> getAllIngredients() {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			ResultSet res = super.db.query("SELECT i.* FROM "
					+ super.TABLE_NAME
					+ " as b JOIN Ingredients AS i ON b.id = i.id ORDER BY i.name;");
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
