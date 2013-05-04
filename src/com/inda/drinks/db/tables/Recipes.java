package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Recipe;

public class Recipes extends Table<Recipe> {
	public static final String TABLE_NAME = "Recipes";
	public static final int TABLE_VERSION = 1;
	private final PreparedStatement insert;

	public Recipes(DbWrapper db) throws SQLException {
		super(db, TABLE_NAME, TABLE_VERSION);
		super.addDependency(Glasses.class);
		insert = db.prepare("INSERT INTO " + TABLE_NAME
				+ " VALUES(default, ?, ?, ?)");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY, name VARCHAR(30) NOT NULL,"
				+ " instructions TEXT NOT NULL, glass INT NOT NULL,"
				+ " FOREIGN KEY(glass) REFERENCES " + Glasses.TABLE_NAME
				+ "(id));");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Recipe e) throws SQLException {
		insert.setString(1, e.getName());
		insert.setString(2, e.getInstructions());
		insert.setInt(3, e.getGlass().getID());
		// TODO: insert Content as well
		// SELECT MAX(id) FROM <TABLE_NAME>;
		insert.executeUpdate();
	}

	@Override
	public void drop() throws SQLException {
		super.db.execute("DROP TABLE IF EXISTS "+TABLE_NAME+";");		
	}

}
