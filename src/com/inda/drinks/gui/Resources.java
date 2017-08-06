package com.inda.drinks.gui;

public class Resources {

	public static final String CL = "cl";
	public static final String ADD = "Lägg till";
	public static final String NAME = "Namn";
	public static final String REMOVE = "Ta bort";
	public static final String BAR = "Förråd";
	public static final String BAR_INFO = "Lägg till eller ta bort från ditt förråd.";
	public static final String ADD_DRINK = "Lägg till drink";
	public static final String DRINK_LIST = "Drinklista";
	public static final String DRINK_LIST_INFO = "Lista de drinkar du kan göra.";
	public static final String ALL_DRINKS = "Samtliga drinkar";
	public static final String ALL_DRINKS_INFO = "Lista samtliga drinkar";
	public static final String DESCRIPTION = "Instruktion och beskrivning";
	public static final String CONTENTS = "Innehåll";
	public static final String SPECIFIC = "Specifik";
	public static final String ERROR_FIRST = "Vänligen ange ";
	public static final String DRINK_NAME = "drinkens namn";
	public static final String DRINK_GLASS = "typ av glas";
	public static final String DRINK_INGREDIENTS = "drinkens ingredienser";
	public static final String DRINK_DESCRIPTION = "beskrivning av drinken";
	public static final String ERROR_LAST = " för att lägga till drinken.";
	public static final String SELECTION_ERROR = "Ingen ingrediens vald.";
	public static final String DRINK_SELECTION_ERROR = "Ingen drink vald";
	public static final String EMPTY_ERROR = "Listan är tom, inget kan raderas.";
	
	public static String ingredientAlreadyAddedError(String s) {
		return String.format("%s är redan tillagd", s);
	}
	
	public static String addIngredientError(String s) {
		return String.format("%s är inte en tillåten ingrediens.", s);
	}

	public static String addError(String s) {
		return String.format("Något gick snett, %s har inte lagts till", s);
	}
	
	public static String removeError(String s) {
		return String.format("Något gick snett, %s har inte tagits bort", s);
	}

	public static String removeDrinkDialog(String s) {
		return String.format("Är du säker på att du vill ta bort %s från din drinksamling?", s);
	}
	
	public static String removeDialog(String s) {
		return String.format(
				"Är du säker på att du vill ta bort %s ifrån ditt förråd?", s);
	}

}
