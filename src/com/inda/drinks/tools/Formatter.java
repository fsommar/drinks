package com.inda.drinks.tools;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Handles common conversions and rounding of numbers.
 * 
 * @author Fredrik Sommar
 */
public class Formatter {
	private static NumberFormat oneDecHalfEven, twoDecHalfEven;
	static {
		oneDecHalfEven = NumberFormat.getNumberInstance(Locale.US);
		oneDecHalfEven.setRoundingMode(RoundingMode.HALF_EVEN);
		oneDecHalfEven.setMaximumFractionDigits(1);

		twoDecHalfEven = NumberFormat.getNumberInstance(Locale.US);
		twoDecHalfEven.setRoundingMode(RoundingMode.HALF_EVEN);
		twoDecHalfEven.setMaximumFractionDigits(2);
	}

	/**
	 * Parses a String and returns its double value rounded to one decimal
	 * through half-even rounding.
	 * 
	 * @param s
	 *            the String to parse a double from.
	 * @return the parsed double.
	 * @throws NumberFormatException
	 *             in case of parsing error.
	 */
	public static double oneDecHalfEven(String s) {
		return round(oneDecHalfEven, s);
	}

	/**
	 * Rounds a double to one decimal using half-even rounding
	 * 
	 * @param d
	 *            the double to be rounded.
	 * @return the one decimal rounded double.
	 * @throws NumberFormatException
	 *             in case of parsing error.
	 */
	public static double oneDecHalfEven(double d) {
		return round(oneDecHalfEven, d);
	}

	/**
	 * Parses a String and returns its double value rounded to two decimals
	 * through half-even rounding.
	 * 
	 * @param s
	 *            the String to parse a double from.
	 * @return the parsed double.
	 * @throws NumberFormatException
	 *             in case of parsing error.
	 */
	public static double twoDecHalfEven(String s) {
		return round(twoDecHalfEven, s);
	}

	/**
	 * Rounds a double to two decimals using half-even rounding.
	 * 
	 * @param d
	 *            the double to be rounded.
	 * @return the one decimal rounded double.
	 * @throws NumberFormatException
	 *             in case of parsing error.
	 */
	public static double twoDecHalfEven(double d) {
		return round(twoDecHalfEven, d);
	}

	private static double round(NumberFormat nf, String s) {
		try {
			return nf.parse(s).doubleValue();
		} catch (ParseException e) {
			throw new NumberFormatException();
		}
	}

	private static double round(NumberFormat nf, double d) {
		return round(nf, nf.format(d));
	}

}
