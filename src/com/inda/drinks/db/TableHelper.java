package com.inda.drinks.db;

import java.util.prefs.Preferences;

import com.inda.drinks.exceptions.VersionMismatchException;

/**
 * Should allow for easier management of multiple tables.
 * @author Fredrik Sommar
 */
public abstract class TableHelper {
	protected TableHelper(DbWrapper db, String name, int version)
			throws VersionMismatchException {
		final Preferences prefs = Preferences.userRoot().node(name);
		final boolean exists = prefs.getBoolean("exists", false);
		if (!exists) {
			onCreate(db);
			prefs.putBoolean("exists", true);
		}
		final int n = prefs.getInt("version", version);
		if (n > version) {
			throw new VersionMismatchException(String.format(
					"Database version %d is greater than previous, %d.", n, version));
		} else if (n < version) {
			onUpgrade(db, version, n);
		}
		prefs.putInt("version", version);
	}

	public abstract void onCreate(DbWrapper db);
	public abstract void onUpgrade(DbWrapper db, int from, int to);
}
