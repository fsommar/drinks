package com.inda.drinks.external;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.inda.drinks.db.Table;
import com.inda.drinks.db.tables.Categories;
import com.inda.drinks.db.tables.Ingredients;
import com.inda.drinks.db.tables.Systembolaget;
import com.inda.drinks.properties.Category;
import com.inda.drinks.properties.Ingredient;
import com.inda.drinks.tools.Formatter;
import com.inda.drinks.tools.Web;
import com.inda.drinks.tools.XML;

/**
 * A class for the handling of the API of Systembolaget.
 * 
 * @author Fredrik Sommar
 */
public class SystembolagetAPI {
	public static final String URL = "http://www.systembolaget.se/Assortment.aspx?Format=Xml";
	public static final File FILE = new File("data/systembolaget");
	private static final String[] SPECIAL_CASES = { "druvigt", "friskt",
			"fylligt", "lätt" };

	/**
	 * Downloads an XML file to data/systembolaget.
	 * 
	 * @throws IOException
	 */
	public static void fetchXML() throws IOException {
		FILE.createNewFile();
		Web.download(URL, FILE);
	}

	/**
	 * Parses the XML file at data/systembolaget and adds it to the tables
	 * Systembolaget and Ingredients.
	 * 
	 * @throws Exception
	 */
	public static void parseXML() throws Exception {
		XML.parse(FILE, new SystembolagetHandler());
	}

	/**
	 * Handler used for parsing the XML downloaded from Systembolaget.
	 * 
	 * @author Fredrik Sommar
	 */
	public static class SystembolagetHandler extends DefaultHandler {
		private Ingredient.Builder ingredient;
		private Systembolaget.Builder sb;
		private String val;
		private int partnr;
		private boolean varunummer, namn, namn2, varugrupp, alkoholhalt,
				prisinklmoms, volymiml;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attr) throws SAXException {
			if (qName.equalsIgnoreCase("artikel")) {
				ingredient = new Ingredient.Builder();
				sb = new Systembolaget.Builder();
			} else if (qName.equalsIgnoreCase("varnummer")) {
				varunummer = true;
			} else if (qName.equalsIgnoreCase("namn")) {
				namn = true;
			} else if (qName.equalsIgnoreCase("namn2")) {
				namn2 = true;
			} else if (qName.equalsIgnoreCase("varugrupp")) {
				varugrupp = true;
			} else if (qName.equalsIgnoreCase("alkoholhalt")) {
				alkoholhalt = true;
			} else if (qName.equalsIgnoreCase("prisinklmoms")) {
				prisinklmoms = true;
			} else if (qName.equalsIgnoreCase("volymiml")) {
				volymiml = true;
			}
		}

		@Override
		public void characters(char[] ch, int start, int len)
				throws SAXException {
			val = new String(ch, start, len).trim();
			if (varunummer) {
				partnr = Integer.parseInt(val);
				ingredient.partNumber(partnr);
				sb.partNumber(partnr);
				varunummer = false;
			} else if (namn) {
				ingredient.name(val);
				namn = false;
			} else if (namn2) {
				ingredient.subtitle(val);
				namn2 = false;
			} else if (varugrupp) {
				ingredient.category(filterCategory(partnr, val));
				varugrupp = false;
			} else if (alkoholhalt) {
				// Assume valid String (API _should_ not have faulty fields)
				ingredient.ABV(Formatter.oneDecHalfEven(val));
				alkoholhalt = false;
			} else if (prisinklmoms) {
				sb.price(Formatter.oneDecHalfEven(val));
				prisinklmoms = false;
			} else if (volymiml) {
				sb.volume((int) Double.parseDouble(val));
				volymiml = false;
			}

		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (qName.equalsIgnoreCase("artikel")) { // end of row
				try {
					// Order matters since ingredients references systembolaget
					Table.get(Systembolaget.class).merge(sb.build());
					Ingredient i = ingredient.build();
					if (i.getCategory() != null) {
						Table.get(Ingredients.class).merge(i);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (qName.equalsIgnoreCase("varnummer")) {
				varunummer = false;
			} else if (qName.equalsIgnoreCase("namn")) {
				namn = false;
			} else if (qName.equalsIgnoreCase("namn2")) {
				namn2 = false;
			} else if (qName.equalsIgnoreCase("varugrupp")) {
				varugrupp = false;
			} else if (qName.equalsIgnoreCase("alkoholhalt")) {
				alkoholhalt = false;
			} else if (qName.equalsIgnoreCase("prisinklmoms")) {
				prisinklmoms = false;
			} else if (qName.equalsIgnoreCase("volymiml")) {
				volymiml = false;
			}
		}

	}

	/**
	 * Converts the categories from the API of Systembolaget to that used by
	 * this application and returns that as an object.
	 * 
	 * @param id
	 *            the "varunummer" of the product to be categorized, used to
	 *            determine special (custom) categories.
	 * @param s
	 *            the category from Systembolaget.
	 * @return a Category object more suitable for this application.
	 */
	private static Category filterCategory(int id, String s) {
		Categories categories = Table.get(Categories.class);
		Category c = null;
		int parent = Category.NO_PARENT;
		Category.Builder builder;
		outer: for (String name : s.split(", ")) {
			name = name.trim().toLowerCase();
			for (String spec : SPECIAL_CASES) {
				// White wines' third category level is ignored
				if (name.equalsIgnoreCase(spec)) {
					break outer;
				}
			}
			builder = new Category.Builder();
			c = categories.getCategory(name, parent);
			if (c == null) { // Category does not yet exist
				c = builder.ID(categories.getNextID()).name(name)
						.parent(parent).build();
				try {
					categories.insert(c);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			parent = c.getID();
		}
		return c;
	}
}
