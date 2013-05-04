package com.inda.drinks.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.prefs.Preferences;

import com.inda.drinks.exceptions.MissingDependencyException;
import com.inda.drinks.exceptions.VersionMismatchException;

/**
 * Allows for easier management of multiple tables.
 * 
 * @author Fredrik Sommar
 */
public abstract class Table<E> {
	// Must be one of the prettiest assignments I've ever seen
	private static final Map<Class<? extends Table<?>>, Table<?>> tables = new HashMap<Class<? extends Table<?>>, Table<?>>();
	private final Set<Class<? extends Table<?>>> deps;
	public final String TABLE_NAME;
	public final int TABLE_VERSION;
	protected final DbWrapper db;
	private final Preferences prefs;

	/**
	 * Called when the table is to be created for the very first time.
	 * 
	 * @throws SQLException
	 */
	public abstract void onCreate() throws SQLException;

	/**
	 * Called when database version is higher than before.
	 * 
	 * @param from
	 *            the old version number.
	 * @param to
	 *            the new version number.
	 * @throws SQLException
	 */
	public abstract void onUpgrade(int from, int to) throws SQLException;

	/**
	 * Inserts an object into the table.
	 * 
	 * @param e
	 *            the object to insert into the table.
	 * @throws SQLException
	 */
	public abstract void insert(E e) throws SQLException;

	/**
	 * Constructor for tables.
	 * 
	 * @param db
	 *            the database object used to execute queries on this table.
	 * @param name
	 *            the name of the table.
	 * @param version
	 *            the version of the table used to determine whether an upgrade
	 *            is necessary.
	 * @throws VersionMismatchException
	 *             if the new version is less than the current one.
	 */
	protected Table(DbWrapper db, String name, int version)
			throws VersionMismatchException {
		this.deps = new HashSet<Class<? extends Table<?>>>();
		this.TABLE_NAME = name;
		this.TABLE_VERSION = version;
		this.db = db;
		prefs = Preferences.userRoot().node(name);
		final boolean exists = prefs.getBoolean("exists", false);
		if (!exists) {
			try {
				onCreate();
				prefs.putBoolean("exists", true);
				prefs.putInt("version", version);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			final int n = prefs.getInt("version", version);
			if (n > version) {
				throw new VersionMismatchException(String.format(
						"Database version %d is greater than previous, %d.", n,
						version));
			} else if (n < version) {
				try {
					onUpgrade(version, n);
					prefs.putInt("version", version);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Adds a dependency for this table.
	 * 
	 * @param c
	 *            the table class with which this table has a dependency on.
	 */
	protected final <V extends Table<?>> void addDependency(Class<V> c) {
		deps.add(c);
	}

	/**
	 * @return the dependencies this table has.
	 */
	public final Set<Class<? extends Table<?>>> getDependencies() {
		return deps;
	}

	/**
	 * Drops the table and removes any traces of the table existing.
	 * 
	 * @throws SQLException
	 */
	public void drop() throws SQLException {
		db.execute("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
		prefs.remove("exists");
		prefs.remove("version");
	}

	/**
	 * Allows for fetching of tables by using their corresponding classes as a
	 * representation key.
	 * 
	 * @param c
	 *            the table class to get access to.
	 * @return the table object of the supplied class.
	 */
	@SuppressWarnings("unchecked")
	public static <V extends Table<?>> V get(Class<V> c) {
		return (V) tables.get(c);
	}

	/**
	 * Registers a table to be able to check for missing dependencies.
	 * 
	 * @param v
	 *            the table to be registered.
	 */
	@SuppressWarnings("unchecked")
	public static <V extends Table<?>> void register(V v) {
		if (v.getDependencies().contains(v.getClass())) {
			throw new MissingDependencyException(v.getClass().getSimpleName()
					+ ".class is missing one or more of its dependencies.");
		}
		tables.put((Class<? extends Table<?>>) v.getClass(), v);
	}

	/**
	 * Takes a ResultSet object and returns a String with table and schema name,
	 * separated by a newline.
	 * 
	 * @param result
	 *            the ResultSet object from where the information is drawn.
	 * @return a String of the table and schema name.
	 */
	public static String getInfo(ResultSet result) {
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

	/**
	 * Takes a ResultSet object and returns a String with every column name
	 * separated by a pipe character ('|').
	 * 
	 * @param result
	 *            the ResultSet object from where the information is drawn.
	 * @return a String of the column names.
	 */
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

	/**
	 * Takes a ResultSet object and returns a String with what's contained
	 * within in terms of row data.
	 * 
	 * @param result
	 *            the ResultSet object from where the information is drawn.
	 * @return a String of all the rows, with each column separated by a pipe
	 *         ('|') and each row separated by a newline.
	 */
	public static String getRowInfo(ResultSet result) {
		StringBuilder sb = new StringBuilder();
		ResultSetMetaData data;
		try {
			data = result.getMetaData();
			while (result.next()) {
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