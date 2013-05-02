package com.inda.drinks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.inda.drinks.db.H2Db;

public class Main {

	public static void main(String[] args) {
		H2Db db = new H2Db();
		try {
			db.open();
			db.execute("DROP TABLE IF EXISTS Test;");
			db.execute("CREATE TABLE Test (id INT IDENTITY PRIMARY KEY,"+
					" lastName VARCHAR(255) NOT NULL, firstName VARCHAR(255));");
			PreparedStatement prepare = db.prepare("INSERT INTO Test VALUES(default, ?, ?);");
			prepare.setString(1, "Wikingsson");
			prepare.setString(2, "Fredrik");
			prepare.executeUpdate();
			prepare.setString(1, "Reinfeldt");
			prepare.executeUpdate();
			prepare.setString(2, "Filippa");
			prepare.executeUpdate();
			ResultSet result = db.query("SELECT * FROM Test;");
			System.out.println("Table: "+result.getMetaData().getTableName(1));
			System.out.println("Schema: "+result.getMetaData().getSchemaName(1));
			for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
				System.out.println("Column "+i+" "+result.getMetaData().getColumnName(i));
			}
			while(result.next()) {
				String fname = result.getString("firstName");
				String lname = result.getString("lastName");
				int id = result.getInt("id");
				System.out.println("["+id+"] " +lname + " | " +fname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		parseDatXML("http://www.systembolaget.se/Assortment.aspx?Format=Xml");
	}
	
	public static void parseDatXML(String url) {
		File f = new File("data/systembolaget.xml");
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		try {
			new File("data").mkdir();
			f.createNewFile();
			URL dl = new URL(url);
			rbc = Channels.newChannel(dl.openStream());
			fos = new FileOutputStream(f);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24); // 2^24 ~= 16 MB
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (rbc != null) {
					rbc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser sp = spf.newSAXParser();
			if (f.exists()) {
				SAXHandler handler = new SAXHandler();
				sp.parse(f, handler);
				System.out.println("Column names: ");
				for (String c : handler.getColumNames()) {
					System.out.print(c+"    ");
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class SAXHandler extends DefaultHandler {
		private String tmp;
		private Set<String> columns = new HashSet<String>();
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attr) throws SAXException {
			tmp = "";
			columns.add(qName);
		}
		
		@Override
		public void characters(char[] ch, int start, int len)
				throws SAXException {
			tmp = new String(ch, start, len);
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			
		}
		
		public Set<String> getColumNames() {
			return columns;
		}
	}
}
