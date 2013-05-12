package com.inda.drinks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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
import com.inda.drinks.db.tables.Bar;
import com.inda.drinks.db.tables.Contents;
import com.inda.drinks.db.tables.Recipes;
import com.inda.drinks.properties.Content;
import com.inda.drinks.properties.Ingredient;
import com.inda.drinks.properties.Recipe;

/**
 * Class that displays the drinks a user can make with his or hers current stash
 * 
 * @author Robin Hellgren
 * 
 */

public class PersonalDrinkList extends JPanel implements Tab {
	private static final long serialVersionUID = 1772643430818816788L;
	private Set<Recipe> personalDrinkList = new HashSet<Recipe>();
	private DefaultListModel recipeModel = new DefaultListModel();
	private JList recipeList = new JList(recipeModel);
	private JTextPane drinkInfo;
	private SimpleAttributeSet boldItalics;

	// Drinklista genererad utifråŒn anväŠndarens föšrråŒd
	public PersonalDrinkList() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(new JScrollPane(recipeList), c);

		// Center Drink info
		drinkInfo = new JTextPane();
		drinkInfo.setBorder(BorderFactory.createEtchedBorder());
		drinkInfo.setEditable(false);
		boldItalics = new SimpleAttributeSet();
		StyleConstants.setItalic(boldItalics, true);
		StyleConstants.setBold(boldItalics, true);

		recipeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (recipeList.getSelectedValue() instanceof Recipe) {
					final Recipe r = (Recipe) recipeList.getSelectedValue();
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
		add(drinkInfo, c);
	}

	private void displayInfo(Recipe r) {
		drinkInfo.setText("");
		if (r == null) {
			return;
		}
		StyledDocument doc = drinkInfo.getStyledDocument();
		try {
			doc.insertString(0, r.getName() + "\n", boldItalics);
			Content c = Table.get(Contents.class).getContent(r.getID());
			for (Content.Item item : c.getContents()) {
				doc.insertString(doc.getLength(), "    " + item + "\n", null);
			}
			doc.insertString(doc.getLength(), "\n" + r.getInstructions(), null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// Only part that needs change
		// Get Bar IDs => get ingredients => get all contents where one or more
		// of the ingredients exist OR if its not specific, find all contents
		// where the category of the ingredient matches
		Set<Recipe> recipes = Table.get(Recipes.class).getAll();
		List<Ingredient> bar = Table.get(Bar.class).getAllIngredients();
		// Clear previous info
		personalDrinkList.clear();
		drinkInfo.setText("");
		for (Recipe r : recipes) {
			Content c = Table.get(Contents.class).getContent(r.getID());
			boolean makeable = true;
			for (Content.Item item : c.getContents()) {
				if (item.isSpecific()) {
					if (!bar.contains(item.getIngredient())) {
						makeable = false;
						break;
					}
				} else {
					// if it's not specific check if categories are the same
					makeable = false;
					for (Ingredient i : bar) {
						// if at least one item in
						if (i.getCategory().equals(
								item.getIngredient().getCategory())) {
							makeable = true;
						}
					}
				}
			}
			if (makeable) {
				personalDrinkList.add(r);
			}
		}
		recipeModel.clear();
		for (Recipe r : personalDrinkList) {
			recipeModel.addElement(r);
		}
		recipeList.requestFocus();
		if (recipeList.getSelectedIndex() == -1) {
			recipeList.setSelectedIndex(Math.min(0, recipeModel.size() - 1));
		}
	}
}
