package com.inda.drinks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.inda.drinks.db.Table;
import com.inda.drinks.db.tables.Contents;
import com.inda.drinks.db.tables.Recipes;
import com.inda.drinks.properties.Content;
import com.inda.drinks.properties.Recipe;

/**
 * Class that displays all drinks currently in the database
 * 
 * @author Robin Hellgren
 * 
 */

public class AllDrinks implements Tab {
	private DefaultListModel recipeModel = new DefaultListModel();
	private JTextPane drinkInfo;
	private SimpleAttributeSet boldItalics;

	// Visar samtliga drinkar i databasen
	public JComponent showWindow() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Left drink menu
		JList leftMenu = new JList(recipeModel);
		leftMenu.setBorder(BorderFactory.createEtchedBorder());
		JScrollPane scroll = new JScrollPane(leftMenu);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 1;
		panel.add(scroll, c);

		// Center Drink info
		drinkInfo = new JTextPane();
		drinkInfo.setBorder(BorderFactory.createEtchedBorder());
		drinkInfo.setEditable(false);
		boldItalics = new SimpleAttributeSet();
		StyleConstants.setItalic(boldItalics, true);
		StyleConstants.setBold(boldItalics, true);

		leftMenu.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getSource() instanceof Recipe) {
					final Recipe r = (Recipe) e.getSource();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							displayInfo(r);
						}
					});
				}
			}
		});

		c.weightx = 1;
		c.gridx = 2;
		c.gridwidth = 2;
		panel.add(drinkInfo, c);

		update();
		return panel;
	}

	private void displayInfo(Recipe r) {
		drinkInfo.setText("");
		StyledDocument doc = drinkInfo.getStyledDocument();
		try {
			doc.insertString(0, r.getName() + "\n", boldItalics);
			Content c = Table.get(Contents.class).getContent(r.getID());
			for (Content.Item item : c.getContents()) {
				doc.insertString(doc.getLength(), " - " + item.getIngredient()
						+ " " + item.getVolume() + " " + Resources.CL + "\n",
						null);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		Set<Recipe> recipes = Table.get(Recipes.class).getAll();
		recipeModel.clear();
		for (Recipe r : recipes) {
			recipeModel.addElement(r);
		}
	}
}
