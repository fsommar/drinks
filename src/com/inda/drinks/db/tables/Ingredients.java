package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
				+ " sb_varunummer INT NOT NULL, FOREIGN KEY(category) REFERENCES "
				+ Table.get(Categories.class).TABLE_NAME + "(id),"
				+ "FOREIGN KEY(sb_varunummer) REFERENCES "
				+ Table.get(Systembolaget.class).TABLE_NAME + "(varunummer));");
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
		insert.setInt(5, e.getSystembolagetID());
		insert.executeUpdate();
	}
}
