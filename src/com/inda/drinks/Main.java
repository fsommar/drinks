package com.inda.drinks;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.inda.drinks.db.H2Db;

public class Main {

	public static void main(String[] args) {
		H2Db db = new H2Db();
		try {
			db.open();
			db.execute("DROP TABLE IF EXISTS Test;");
			db.execute("CREATE TABLE Test (id INT IDENTITY PRIMARY KEY,"+
					" lastName VARCHAR(255) NOT NULL, firstName VARCHAR(255));");
			PreparedStatement prepare = db.prepare("INSERT INTO Test (lastName, firstName) VALUES(?, ?);");
			prepare.setString(1, "Sommar");
			prepare.setString(2, "Fredrik");
			prepare.executeUpdate();
			ResultSet result = db.query("SELECT * FROM Test;");
			System.out.println("Table: "+result.getMetaData().getTableName(1));
			for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
				System.out.println("Column "+i+" "+result.getMetaData().getColumnName(i));
			}
			while(result.next()) {
				String fname = result.getString("firstName");
				String lname = result.getString("lastName");
				int id = result.getInt("id");
				System.out.println("["+id+"] " +fname + " " +lname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		
	}

}
