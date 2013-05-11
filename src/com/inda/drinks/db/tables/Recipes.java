package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Recipe;

public class Recipes extends Table<Recipe> {
	private PreparedStatement insert;

	public Recipes(Database db) throws SQLException {
		super(db, "Recipes", 1);
		super.addDependency(Glasses.class);
		insert = db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(default, ?, ?, ?)");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY, name VARCHAR(30) NOT NULL,"
				+ " instructions TEXT NOT NULL, glass INT NOT NULL,"
				+ " FOREIGN KEY(glass) REFERENCES "
				+ Table.get(Glasses.class).TABLE_NAME + "(id));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Recipe e) throws SQLException {
		insert.setString(1, e.getName());
		insert.setString(2, e.getInstructions());
		insert.setInt(3, e.getGlassID());
		insert.executeUpdate();
	}

	public Set<Recipe> getAll() {
		Set<Recipe> recipes = new HashSet<Recipe>();
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ ";");
			Recipe.Builder builder = new Recipe.Builder();
			while (res.next()) {
				Recipe recipe = builder.ID(res.getInt(1))
						.name(res.getString(2)).instructions(res.getString(3))
						.glassID(res.getInt(4)).build();
				recipes.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipes;
	}
}
