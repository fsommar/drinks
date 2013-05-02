package com.inda.drinks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.inda.drinks.db.H2Db;
import com.inda.drinks.external.Systembolaget;

public class Main {

	public static void main(String[] args) throws ParseException {
		// testDB();
		try {
			// Systembolaget.fetchXML();
			// Systembolaget.parseXML();
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
			System.out
					.println("Table: " + result.getMetaData().getTableName(1));
			System.out.println("Schema: "
					+ result.getMetaData().getSchemaName(1));
			for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
				System.out.println("Column " + i + " "
						+ result.getMetaData().getColumnName(i));
			}
			while (result.next()) {
				String fname = result.getString("firstName");
				String lname = result.getString("lastName");
				int id = result.getInt("id");
				System.out.println("[" + id + "] " + lname + " | " + fname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

}
