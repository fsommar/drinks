package com.inda.drinks.gui;


public class Resources {

	public static final String CL = "cl";
	public static final String ADD = "L�gg till";
	public static final String NAME = "Namn";
	public static final String REMOVE = "Ta bort";
	public static final String BAR = "F�rr�d";
	public static final String BAR_INFO = "L�gg till eller ta bort fr�n ditt f�rr�d.";
	public static final String ADD_DRINK = "L�gg till drink";
	public static final String DRINK_LIST = "Drinklista";
	public static final String DRINK_LIST_INFO = "Lista de drinkar du kan g�ra.";
	public static final String ALL_DRINKS = "Samtliga drinkar";
	public static final String ALL_DRINKS_INFO = "Lista samtliga drinkar";
	public static final String DESCRIPTION = "Instruktion och beskrivning";
	public static final String CONTENTS = "Inneh�ll";
	public static final String SPECIFIC = "Specifik";
	public static final String ERROR_FIRST = "V�nligen ange ";
	public static final String DRINK_NAME = "drinkens namn";
	public static final String DRINK_GLASS = "typ av glas";
	public static final String DRINK_INGREDIENTS = "drinkens ingredienser";
	public static final String DRINK_DESCRIPTION = "beskrivning av drinken";
	public static final String ERROR_LAST = " f�r att l�gga till drinken.";

	public static String removeDialog(String s) {
		return String.format(
				"�r du s�ker p� att du vill ta bort %s ifr�n ditt f�rr�d?", s);
	}

}
