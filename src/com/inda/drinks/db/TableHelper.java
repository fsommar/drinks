package com.inda.drinks.db;

import java.sql.SQLException;
import java.util.prefs.Preferences;

import com.inda.drinks.exceptions.VersionMismatchException;

/**
 * Should allow for easier management of multiple tables.
 * 
 * @author Fredrik Sommar
 */
public abstract class TableHelper<E> {
	protected final DbWrapper db;

	protected TableHelper(DbWrapper db, String name, int version)
			throws VersionMismatchException {
		this.db = db;
		final Preferences prefs = Preferences.userRoot().node(name);
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

	public abstract void onCreate() throws SQLException;

	public abstract void onUpgrade(int from, int to) throws SQLException;

	public abstract void insert(E e) throws SQLException;
}
