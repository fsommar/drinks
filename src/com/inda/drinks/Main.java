package com.inda.drinks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.inda.drinks.db.H2Db;
import com.inda.drinks.external.SystembolagetAPI;
import com.inda.drinks.tools.Tables;

public class Main {

	public static void main(String[] args) throws ParseException {
		testDB();
		try {
			// SystembolagetAPI.fetchXML();
			// SystembolagetAPI.parseXML();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testDB() {
		H2Db db = new H2Db();
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
			System.out.println(Tables.getTableInfo(result));
			System.out.println(Tables.getColumnInfo(result));
			System.out.println(Tables.getRowInfo(result));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

}
