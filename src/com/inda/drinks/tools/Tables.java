package com.inda.drinks.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import com.inda.drinks.db.TableHelper;

public class Tables {
	// Will have to be static for now. Not sure if it's optimal...
	private static Map<Class<? extends TableHelper<?>>, TableHelper<?>> tables;

	@SuppressWarnings("unchecked")
	public static <V extends TableHelper<?>> V get(Class<V> c) {
		return (V) tables.get(c);
	}

	@SuppressWarnings("unchecked")
	public static <V extends TableHelper<?>> void register(V v) {
		tables.put((Class<? extends TableHelper<?>>) v.getClass(), v);
	}
	
	public static String getTableInfo(ResultSet result) {
		StringBuilder sb = new StringBuilder();
		try {
			ResultSetMetaData data = result.getMetaData();
			sb.append("Table:  ").append(data.getTableName(1)).append("\n");
			sb.append("Schema: ").append(data.getSchemaName(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getColumnInfo(ResultSet result) {
		StringBuilder sb = new StringBuilder();
		ResultSetMetaData data;
		try {
			data = result.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				sb.append(data.getColumnName(i)).append(" | ");
			}				
			if (sb.length() > 3) {
				sb.setLength(sb.length() - 3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getRowInfo(ResultSet result) {
		StringBuilder sb = new StringBuilder();
		ResultSetMetaData data;
		try {
			data = result.getMetaData();
			while(result.next()) {
				for (int i = 1; i <= data.getColumnCount(); i++) {
					sb.append(result.getString(i)).append(" | ");
				}				
				if (sb.length() > 3) {
					sb.setLength(sb.length() - 3);
				}
				sb.append("\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}
