package com.inda.drinks.external;

import java.io.File;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.inda.drinks.properties.Category;
import com.inda.drinks.tools.Formatter;
import com.inda.drinks.tools.IngredientFactory;
import com.inda.drinks.tools.Web;
import com.inda.drinks.tools.XML;

public class Systembolaget {
	private static Set<String> categories = new HashSet<String>();
	public static final String URL = "http://www.systembolaget.se/Assortment.aspx?Format=Xml";

	public static void fetchAndParseXML() throws Exception {
		File file = new File("data/systembolaget.xml");
		new File("data").mkdir();
		file.createNewFile();
		Web.download(URL, file);
		XML.parse(file, new SAXHandler());
	}

	// TODO: fix parsing
	public static class SAXHandler extends DefaultHandler {
		private IngredientFactory infactusuk;
		private String val;
		
		public SAXHandler() {
			infactusuk = IngredientFactory.newInstance();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attr) throws SAXException {
			if (qName.equals("artikel")) {
				// start of row
				infactusuk.reset();
			}
			if (!(qName.equals("Namn") || qName.equals("Namn2") || qName.equals("Varugrupp") || qName.equals("Alkoholhalt"))) {
				val = "";	
			}
		}

		@Override
		public void characters(char[] ch, int start, int len)
				throws SAXException {
			val = new String(ch, start, len);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (qName.equals("artikel")) {
				// end of row, post to observer?
//				Ingredient i = infactusuk.create();
			} else if (qName.equals("Namn")) {
				infactusuk.setName(val);
			} else if (qName.equals("Namn2")) {
				infactusuk.setSubtitle(val);
			} else if (qName.equals("Varugrupp")) {
				infactusuk.setCategory(filterCategory(val));
				categories.add(val);
			} else if (qName.equals("Alkoholhalt")) {
				double d = -1;
				try {
					d = Formatter.oneDecHalfEven(val);
				} catch (ParseException e) {
					System.err.println(val +" "+ e);
				}
				infactusuk.setABV(d);
			} else if (qName.equals("Prisinklmoms")) {
				// add to systemet table
			} else if (qName.equals("Volymiml")) {
				
			}
		}

	}
	
	public static Category filterCategory(String s) {
		// TODO
		return null;
	}
	
	public static Set<String> getCategories() {
		return categories;
	}
	
}
