package com.inda.drinks.tools;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Formatter {
	private static NumberFormat nf;
	static {
		nf = NumberFormat.getNumberInstance(Locale.US);
		nf.setRoundingMode(RoundingMode.HALF_EVEN);
		nf.setMaximumFractionDigits(1);
	}

	public static double oneDecHalfEven(String s) throws ParseException {
		return nf.parse(s).doubleValue();
	}
	
	public static double oneDecHalfEven(double d) throws ParseException {
		return oneDecHalfEven(nf.format(d));
	}

}
