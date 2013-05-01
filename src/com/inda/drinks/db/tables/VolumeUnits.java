package com.inda.drinks.db.tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.inda.drinks.db.DbWrapper;
import com.inda.drinks.db.TableHelper;
import com.inda.drinks.properties.VolumeUnit;

public class VolumeUnits extends TableHelper<VolumeUnit> {
	private static final int TABLE_VERSION = 1;
	private static final String TABLE_NAME = "VolumeUnits";
	private PreparedStatement insert;

	public VolumeUnits(DbWrapper db) throws SQLException {
		super(db, TABLE_NAME, TABLE_VERSION);
		insert = db.prepare("INSERT INTO "+TABLE_NAME+" VALUES(?, ?);");
	}

	@Override
	public void onCreate() throws SQLException {
		db.execute("CREATE TABLE " + TABLE_NAME
				+ " (id INT IDENTITY PRIMARY KEY, name VARCHAR(255));");
		for (VolumeUnit unit : VolumeUnit.values()) {
			insert(unit);
		}
	}

	@Override
	public void onUpgrade(int from, int to) throws SQLException {
		db.execute("DROP TABLE IF EXISTS "+TABLE_NAME+";");
		onCreate();
	}

	@Override
	public void insert(VolumeUnit e) throws SQLException {
		insert.setInt(1, e.getID());
		insert.setString(2, e.toString());
		insert.executeUpdate();
	}

}
