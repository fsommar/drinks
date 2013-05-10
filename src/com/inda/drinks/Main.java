package com.inda.drinks;

import java.io.File;
import java.sql.SQLException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.inda.drinks.db.Database;
import com.inda.drinks.db.H2Database;
import com.inda.drinks.db.Table;
import com.inda.drinks.db.tables.Bar;
import com.inda.drinks.db.tables.Categories;
import com.inda.drinks.db.tables.Contents;
import com.inda.drinks.db.tables.Glasses;
import com.inda.drinks.db.tables.Ingredients;
import com.inda.drinks.db.tables.Recipes;
import com.inda.drinks.db.tables.Systembolaget;
import com.inda.drinks.external.SystembolagetAPI;
import com.inda.drinks.gui.Window;
import com.inda.drinks.properties.Ingredient;

/*
 * TODO:
 *  [ ] Documentation
 *    [ ] Use log tool (log4j?) for misc actions
 *    [x] Javadoc
 *  [ ] JUnit test code
 *    [ ] Insert statements (focus on negative tests)
 *    [ ] Queries, of many items and of single items
 *  [ ] GUI
 *    [ ] Working bar (starting with just text of categories)
 *    [ ] Adding recipes
 *  [ ] Error handling
 *    [ ] Check input before building objects (e.g. recipe name length <= 30)
 *    [x] Programmatic dependencies for Table (check dependency is registered)
 *  [-] Rework category layout, no SQL query will work for multiple nesting as it stands
 *    // Actually, yes it will. A simple 'while getParent != null' will suffice
 *    
 * LEGEND: [ ] not done, [x] done, [-] skipped.
 */
public class Main {

	public static void main(String[] args) {
		new File("data").mkdir();
		Database db = new H2Database();
		try {
			db.open("data/really_unique_name", "usr", "pwd");
			registerTables(db);
			// SystembolagetAPI.fetchXML();
			SystembolagetAPI.parseXML();
			for (Ingredient i : Table.get(Ingredients.class).getAll()) {
				System.out.println(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Window().setVisible(true);
			}
		});
	}

	private static void registerTables(Database db) throws SQLException { //
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
