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
import com.inda.drinks.exceptions.NotImplementedException;
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
	private static final File file = new File("data/systembolaget");

	/**
	 * Downloads an XML file to data/systembolaget.
	 * 
	 * @throws IOException
	 */
	public static void fetchXML() throws IOException {
		file.createNewFile();
		Web.download(URL, file);
	}

	/**
	 * Parses the XML file at data/systembolaget and adds it to the tables
	 * Systembolaget and Ingredients.
	 * 
	 * @throws Exception
	 */
	public static void parseXML() throws Exception {
		XML.parse(file, new SystembolagetHandler());
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
		private boolean artikelid, varunummer, namn, namn2, varugrupp,
				alkoholhalt, prisinklmoms, volymiml;

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attr) throws SAXException {
			if (qName.equalsIgnoreCase("artikel")) {
				ingredient = new Ingredient.Builder();
				sb = new Systembolaget.Builder();
			} else if (qName.equalsIgnoreCase("artikelid")) {
				artikelid = true;
			} else if (qName.equalsIgnoreCase("varunummer")) {
				varunummer = true;
			} else if (qName.equalsIgnoreCase("namn")) {
				namn = true;
			} else if (qName.equalsIgnoreCase("namn2")) {
				namn2 = true;
			} else if (qName.equalsIgnoreCase("varugrupp")) {
				varugrupp = true;
			} else if (qName.equalsIgnoreCase("alkoholhalt")) {
				alkoholhalt = true;
			} else if (qName.equals("prisinklmoms")) {
				prisinklmoms = true;
			} else if (qName.equals("volymiml")) {
				volymiml = true;
			}
		}

		@Override
		public void characters(char[] ch, int start, int len)
				throws SAXException {
			val = new String(ch, start, len).trim();
			if (artikelid) {
				sb.articleID(Integer.parseInt(val));
				artikelid = false;
			} else if (varunummer) {
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
				sb.volume(Integer.parseInt(val));
				volymiml = false;
			}

		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (qName.equals("artikel")) {
				// end of row
				try {
					Table.get(Ingredients.class).insert(ingredient.build());
					Table.get(Systembolaget.class).insert(sb.build());
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
		// Hashmap with name -> Category
		// map["alkoholfritt"] would yield the Category object representing
		// "alkoholfritt"
		Categories categories = Table.get(Categories.class);
		Category c = null; // TODO: prettier plsss
		int parent = Category.NO_PARENT;
		Category.Builder builder;
		for (String name : s.split(", ")) {
			builder = new Category.Builder();
			c = categories.getCategory(name);
			if (c == null) {
				c = builder.ID(categories.getNextID()).name(name)
						.parent(parent).build();
				// categories.insert(c);
				parent = c.getID();
			}

		}
		throw new NotImplementedException();
		// return c;
	}
}
