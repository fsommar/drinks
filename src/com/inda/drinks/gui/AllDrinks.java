package com.inda.drinks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
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

public class AllDrinks extends JPanel implements Tab {
	private static final long serialVersionUID = 8015165371620920618L;
	private DefaultListModel recipeModel = new DefaultListModel();
	private JList recipeList = new JList(recipeModel);
	private SimpleAttributeSet boldItalics;
	private JTextPane drinkInfo;

	// Visar samtliga drinkar i databasen
	public AllDrinks() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Left drink menu
		recipeList.setBorder(BorderFactory.createEtchedBorder());
		JScrollPane scroll = new JScrollPane(recipeList);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(scroll, c);


		// Remove Button
		JButton removeDrink = new JButton(Resources.REMOVE);
		removeDrink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(recipeList.getSelectedIndex() != -1) {
					recipeModel.remove(recipeList.getSelectedIndex());
				} else {
					JOptionPane.showMessageDialog(AllDrinks.this, Resources.SELECTION_ERROR);
				}
			}
		});
		c.gridy = 10;
		c.weightx = 0;
		c.weighty = 0;
		add(removeDrink, c);
		
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
		c.gridy = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		add(drinkInfo, c);
	}

	private void displayInfo(Recipe r) {
		drinkInfo.setText("");
		StyledDocument doc = drinkInfo.getStyledDocument();
		try {
			doc.insertString(0, r.getName() + "\n", boldItalics);
			Content c = Table.get(Contents.class).getContent(r.getID());
			for (Content.Item item : c.getContents()) {
				doc.insertString(doc.getLength(), "    " + item.getVolume()
						+ " " + Resources.CL + "  " + item.getIngredient()
						+ "\n", null);
			}
			doc.insertString(doc.getLength(), "\n" + r.getInstructions(), null);
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
		recipeList.requestFocus();
		recipeList.setSelectedIndex(Math.min(0, recipeModel.size() - 1));
	}
}
