package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Ingredient;

public class Ingredients extends Table<Ingredient> {
	private final PreparedStatement insert;

	public Ingredients(DbWrapper db) throws SQLException {
		super(db, "Ingredients", 1);
		super.addDependency(Systembolaget.class);
		super.addDependency(Categories.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(default, ?, ?, ?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE "
				+ super.TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY,"
				+ " name VARCHAR(30) NOT NULL, subtitle VARCHAR(30) NOT NULL,"
				+ " ABV DOUBLE NOT NULL, category INT NOT NULL,"
				+ " part_number INT NOT NULL, FOREIGN KEY(category) REFERENCES "
				+ Table.get(Categories.class).TABLE_NAME + "(id),"
				+ "FOREIGN KEY(part_number) REFERENCES "
				+ Table.get(Systembolaget.class).TABLE_NAME + "(part_number));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Ingredient e) throws SQLException {
		insert.setString(1, e.getName());
		insert.setString(2, e.getSubtitle());
		insert.setDouble(3, e.getABV());
		insert.setInt(4, e.getCategory().getID());
		insert.setInt(5, e.getPartNumber());
		insert.executeUpdate();
	}

	/**
	 * @return a set of all ingredients currently in the database. The set is
	 *         empty if the database query fails for any reason.
	 */
	public Set<Ingredient> getAll() {
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ ";");
			Ingredient.Builder builder = new Ingredient.Builder();
			while (res.next()) {
				Ingredient ingredient = builder
						.ID(res.getInt(1))
						.name(res.getString(2))
						.ABV(res.getDouble(3))
						.category(
								Table.get(Categories.class).getCategory(
										res.getInt(4)))
						.partNumber(res.getInt(5)).build();
				ingredients.add(ingredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredients;
	}
}
