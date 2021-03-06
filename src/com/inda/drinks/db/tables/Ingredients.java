package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Ingredient;

public class Ingredients extends Table<Ingredient> {
	private final PreparedStatement insert, merge;

	public Ingredients(Database db) throws SQLException {
		super(db, "Ingredients", 1);
		super.addDependency(Systembolaget.class);
		super.addDependency(Categories.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(default, ?, ?, ?, ?, ?);");
		merge = db.prepare("MERGE INTO " + super.TABLE_NAME
				+ " KEY(part_number) VALUES(default, ?, ?, ?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY,"
				+ " name VARCHAR(140) NOT NULL, subtitle VARCHAR(140),"
				+ " ABV DOUBLE NOT NULL, category INT NOT NULL,"
				+ " part_number INT NOT NULL UNIQUE,"
				+ " FOREIGN KEY(part_number) REFERENCES "
				+ Table.get(Systembolaget.class).TABLE_NAME + "(part_number),"
				+ " FOREIGN KEY(category) REFERENCES "
				+ Table.get(Categories.class).TABLE_NAME + "(id));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Ingredient e) throws SQLException {
		preparedExecute(insert, e);
	}

	/**
	 * Like {@link#insert(Ingredient)} but updates the Ingredient if it happens
	 * to already exist.
	 * 
	 * @param e
	 *            the Ingredient to insert.
	 * @throws SQLException
	 */
	public void merge(Ingredient e) throws SQLException {
		preparedExecute(merge, e);
	}

	private static void preparedExecute(PreparedStatement ps, Ingredient e)
			throws SQLException {
		ps.setString(1, e.getName());
		ps.setString(2, e.getSubtitle());
		ps.setDouble(3, e.getABV());
		ps.setInt(4, e.getCategory().getID());
		ps.setInt(5, e.getPartNumber());
		ps.executeUpdate();
	}

	/**
	 * @return a list of all ingredients currently in the database. The list is
	 *         empty if the database query fails for any reason.
	 */
	public List<Ingredient> getAll() {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ "ORDER BY name;");
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

	/**
	 * Queries the database for all Ingredients belonging to the specified
	 * category id.
	 * 
	 * @param categoryID
	 *            the category id to query for.
	 * @return a list of all the Ingredients belonging to the category id.
	 */
	public List<Ingredient> getAllWithCategory(int categoryID) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ " WHERE category = " + categoryID + " ORDER BY name;");
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
