package com.inda.drinks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.H2Db;
import com.inda.drinks.db.Table;
import com.inda.drinks.db.tables.Bar;
import com.inda.drinks.db.tables.Categories;
import com.inda.drinks.db.tables.Contents;
import com.inda.drinks.db.tables.Glasses;
import com.inda.drinks.db.tables.Ingredients;
import com.inda.drinks.db.tables.Recipes;
import com.inda.drinks.db.tables.Systembolaget;
import com.inda.drinks.external.SystembolagetAPI;

/*
 * TODO:
 *  [ ] Documentation
 *    [ ] Use log tool (log4j?) for misc actions
 *    [ ] Javadoc
 *  [ ] JUnit test code
 *    [ ] Insert statements (focus on negative tests)
 *    [ ] Queries, of long list and of single items
 *  [ ] GUI
 *    [ ] Working bar (starting with just text of categories)
 *    [ ] Adding recipes
 *  [ ] Error handling
 *    [ ] Check input before inserting in DB (TableHelper.insert implementations)
 *    [ ] Programmatic dependencies for Table (check dependency is registered)
 *    
 * LEGEND: [ ] not done, [x] done, [-] skipped.
 */
public class Main {

	public static void main(String[] args) throws ParseException {
		testDB();
		try {
			// SystembolagetAPI.fetchXML();
			SystembolagetAPI.parseXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testDB() {
		DbWrapper db = new H2Db();
		try {
			db.open();
			db.execute("DROP TABLE IF EXISTS Test;");
			db.execute("CREATE TABLE Test (id INT IDENTITY PRIMARY KEY,"
					+ " lastName VARCHAR(255) NOT NULL, firstName VARCHAR(255));");
			PreparedStatement prepare = db
					.prepare("INSERT INTO Test VALUES(default, ?, ?);");
			prepare.setString(1, "Wikingsson");
			prepare.setString(2, "Fredrik");
			prepare.executeUpdate();
			prepare.setString(1, "Reinfeldt");
			prepare.executeUpdate();
			prepare.setString(2, "Filippa");
			prepare.executeUpdate();
			ResultSet result = db.query("SELECT * FROM Test;");
			System.out.println(Table.getInfo(result));
			System.out.println(Table.getColumnInfo(result));
			System.out.println(Table.getRowInfo(result));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	private static void registerTables(DbWrapper db) throws SQLException { //
		// No dependencies
		Table.register(new Glasses(db));
		Table.register(new Categories(db));
		Table.register(new Systembolaget(db));

		// Dependencies (order matters!!)
		Table.register(new Recipes(db)); // #1
		Table.register(new Ingredients(db)); // #2
		Table.register(new Contents(db)); // #3
		Table.register(new Bar(db)); // #4
	}

}
