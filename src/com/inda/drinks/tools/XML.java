package com.inda.drinks.tools;

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Includes methods for parsing XML.
 * 
 * @author Fredrik Sommar
 */
public class XML {

	/**
	 * Parses an XML file using the supplied handler.
	 * 
	 * @param file
	 *            the XML file to be parsed.
	 * @param handler
	 *            the handler which takes care of any parsing logic.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void parse(File file, DefaultHandler handler)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		InputStream inputStream = new FileInputStream(file);
		Reader reader = new InputStreamReader(inputStream,"UTF-8");

		InputSource is = new InputSource(reader);
		is.setEncoding("UTF-8");

		sp.parse(is, handler);
	}

}
