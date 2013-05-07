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
	private static NumberFormat nf;
	static {
		nf = NumberFormat.getNumberInstance(Locale.US);
		nf.setRoundingMode(RoundingMode.HALF_EVEN);
		nf.setMaximumFractionDigits(1);
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
		try {
			return nf.parse(s).doubleValue();
		} catch (ParseException e) {
			throw new NumberFormatException();
		}
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
	public static double oneDecHalfEven(double d) throws ParseException {
		return oneDecHalfEven(nf.format(d));
	}

}
