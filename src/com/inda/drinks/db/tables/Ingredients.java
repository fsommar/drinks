package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.properties.Ingredient;

public class Ingredients extends TableHelper<Ingredient> {
	public static final String TABLE_NAME = "Ingredients";
	public static final int TABLE_VERSION = 1;
	private final PreparedStatement insert;

	public Ingredients(DbWrapper db) throws SQLException {
		super(db, TABLE_NAME, TABLE_VERSION);
		insert = db.prepare("INSERT INTO " + TABLE_NAME
				+ " VALUES(default, ?, ?, ?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE "
				+ TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY,"
				+ " name VARCHAR(30) NOT NULL, subtitle VARCHAR(30) NOT NULL,"
				+ " ABV DOUBLE NOT NULL, category INT NOT NULL,"
				+ " sb_varunummer INT NOT NULL, FOREIGN KEY(category) REFERENCES "
				+ Categories.TABLE_NAME + "(id),"
				+ "FOREIGN KEY(sb_varunummer) REFERENCES " + Systembolaget.TABLE_NAME
				+ "(varunummer));");
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
