package com.inda.drinks.db;

import java.util.prefs.Preferences;

import com.inda.drinks.exceptions.VersionMismatchException;

public abstract class DBHelper {
	protected DBHelper(DBWrapper db, String name, int version)
			throws VersionMismatchException {
		Preferences prefs = Preferences.userRoot().node(name);
		final boolean b = prefs.getBoolean("exists", false);
		if (!b) {
			onCreate(db);
			prefs.putBoolean("exists", true);
		}
		final int n = prefs.getInt("version", version);
		if (n > version) {
			throw new VersionMismatchException(String.format(
					"Database version %d is greater than previous, %d", n, version));
		} else if (n < version) {
			onUpgrade(db, version, n);
			prefs.putInt("version", version);
		}
	}

	public abstract void onCreate(DBWrapper db);
	public abstract void onUpgrade(DBWrapper db, int from, int to);
}
