package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.properties.Category;

public class Categories extends Table<Category> {
	private final PreparedStatement insert;

	public Categories(Database db) throws SQLException {
		super(db, "Categories", 1);
		insert = super.db.prepare("INSERT INTO " + super.TABLE_NAME
				+ " VALUES(?, ?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		super.db.execute("CREATE TABLE " + super.TABLE_NAME
				+ " (id INT NOT NULL PRIMARY KEY,"
				+ " name VARCHAR(50) NOT NULL, parent INT NOT NULL);");
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		// Nothing here yet
	}

	@Override
	public void insert(Category e) throws SQLException {
		insert.setInt(1, e.getID());
		insert.setString(2, e.getName());
		insert.setInt(3, e.getParentID());
		insert.executeUpdate();
	}

	/**
	 * Returns the category with the supplied name and parent id.
	 * 
	 * @param name
	 *            the name of the Category.
	 * @param parent
	 *            the id of the parent to the Category.
	 * @return a Category object, or null if the entry doesn't exist.
	 */
	public Category getCategory(String name, int parent) {
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ " WHERE name = '" + name + "' AND parent = " + parent
					+ " LIMIT 1;");
			Category.Builder builder = new Category.Builder();
			if (res.next()) {
				return builder.ID(res.getInt(1)).name(res.getString(2))
						.parent(res.getInt(3)).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the Category object with the supplied id.
	 * 
	 * @param id
	 *            the id of the Category.
	 * @return the Category object, or null if it doesn't exist.
	 */
	public Category getCategory(int id) {
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ " WHERE id = " + id + " LIMIT 1;");
			Category.Builder builder = new Category.Builder();
			if (res.next()) {
				return builder.ID(res.getInt(1)).name(res.getString(2))
						.parent(res.getInt(3)).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a list of all Categories currently in the database.
	 * 
	 * @return the list of Category objects, which is empty if there aren't any
	 *         Categories currently in the database.
	 */
	public List<Category> getAll() {
		List<Category> categories = new ArrayList<Category>();
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ ";");
			Category.Builder builder = new Category.Builder();
			while (res.next()) {
				Category category = builder.ID(res.getInt(1))
						.name(res.getString(2)).parent(res.getInt(3)).build();
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	/**
	 * Returns a list of all the Category objects that has the supplied parent
	 * id.
	 * 
	 * @param parentID
	 *            the id of the Category parent.
	 * @return a list of Category objects, which is empty if no Categories with
	 *         the supplied parent id exist.
	 */
	public List<Category> getAllWithParent(int parentID) {
		List<Category> categories = new ArrayList<Category>();
		try {
			ResultSet res = super.db.query("SELECT * FROM " + super.TABLE_NAME
					+ " WHERE parent = " + parentID + " ORDER BY name;");
			Category.Builder builder = new Category.Builder();
			while (res.next()) {
				Category category = builder.ID(res.getInt(1))
						.name(res.getString(2)).parent(res.getInt(3)).build();
				categories.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	/**
	 * Returns the next valid ID for categories, useful when inserting new
	 * categories into the database.
	 * 
	 * @return the next valid id for categories, or -1 if it fails.
	 */
	public int getNextID() {
		try {
			ResultSet res = super.db.query("SELECT MAX(id) FROM "
					+ super.TABLE_NAME + ";");
			if (res.next()) {
				return res.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
