package com.inda.drinks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.inda.drinks.db.Table;
import com.inda.drinks.db.tables.Bar;
import com.inda.drinks.db.tables.Categories;
import com.inda.drinks.db.tables.Ingredients;
import com.inda.drinks.properties.Category;
import com.inda.drinks.properties.Ingredient;

/**
 * Class that displays the window for adding and removing drinks to/from the
 * users stash
 * 
 * @author Robin Hellgren
 * 
 */

public class MyBar extends JPanel implements Tab {
	private static final long serialVersionUID = 268354442820169093L;
	private DefaultListModel listModel = new DefaultListModel();
	private DefaultComboBoxModel alcoholModel = new DefaultComboBoxModel();

	// Fšönster däŠr användaren läŠgger till/tar bort fråŒn sitt fšörråŒd
	public MyBar() {
		super(new GridBagLayout());

		// Drink list
		/*
		 * String[] data = { "one", "two", "three", "four" }; for (String s :
		 * data) { listModel.addElement(s); }
		 */

		// Right drink list area
		final JList rightDrinkList = new JList(listModel);
		final GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.weightx = 8;
		c.gridheight = 20;
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		add(rightDrinkList, c);

		// Left options panel
		final JPanel leftOptions = new JPanel(new GridLayout(15, 0));

		DefaultComboBoxModel categoryModel = new DefaultComboBoxModel();
		final JComboBox categoryBox = new JComboBox(categoryModel);
		for (Category category : Table.get(Categories.class).getAllWithParent(
				Category.NO_PARENT)) {
			categoryModel.addElement(category);
		}
		categoryBox.setSelectedItem(null);
		leftOptions.add(categoryBox);

		// Subcategory box
		final DefaultComboBoxModel subcategoryModel = new DefaultComboBoxModel();
		final JComboBox subcategoryBox = new JComboBox(subcategoryModel);
		categoryBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED
						&& categoryBox.getSelectedIndex() != -1) {
					if (event.getItem() instanceof Category) {
						subcategoryModel.removeAllElements();
						Set<Category> subCategories = Table.get(
								Categories.class).getAllWithParent(
								((Category) event.getItem()).getID());
						if (subCategories.isEmpty()) {
							subcategoryBox.setEnabled(false);
							fillIngredientList(((Category) event.getItem()).getID());
						} else {
							// subcategoryBox.setVisible(true);
							subcategoryBox.setEnabled(true);
							for (Category category : subCategories) {
								subcategoryModel.addElement(category);
							}
						}
					}
				}
			}
		});
		leftOptions.add(subcategoryBox);

		// Liqueur box
		final JComboBox alcohol = new JComboBox(alcoholModel);
		leftOptions.add(alcohol, c);
		c.weighty = 1;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 4;

		subcategoryBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED
						&& subcategoryBox.getSelectedIndex() != -1) {
					if (event.getItem() instanceof Category) {
						fillIngredientList(((Category) event.getItem()).getID());
					}
				}
			}
		});

		// Add liqueur button
		final JButton addDrink = new JButton(Resources.ADD);
		c.gridy = 5;
		leftOptions.add(addDrink, c);

		// TODO: Remove from DB as well
		// Remove liqueur button
		final JButton removeDrink = new JButton(Resources.REMOVE);
		removeDrink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object selected = rightDrinkList.getSelectedValue();
				if (!listModel.isEmpty() && selected != null) {
					int n = JOptionPane.showConfirmDialog(MyBar.this,
							Resources.removeDialog(selected.toString()),
							Resources.REMOVE + " " + selected,
							JOptionPane.OK_CANCEL_OPTION);
					if (n == JOptionPane.OK_OPTION) {
						if (rightDrinkList.getSelectedIndex() != -1) {
							listModel.remove(rightDrinkList.getSelectedIndex());
						}
					}
				}
			}
		});
		c.gridy = 6;
		leftOptions.add(removeDrink, c);

		// TODO: Add to DB as well
		// If user adds drink
		addDrink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!listModel.contains(alcohol.getSelectedItem())
						&& alcohol.getSelectedItem() != null) {
					listModel.addElement(alcohol.getSelectedItem());
					alcohol.setSelectedItem(null);
					subcategoryModel.removeAllElements();
					categoryBox.setSelectedItem(null);
					alcoholModel.removeAllElements();
					subcategoryBox.setSelectedItem(null);
				}
			}
		});

		// Add left options to panel
		c.gridx = 1;
		c.gridy = 1;
		add(leftOptions, c);
	}

	private void fillIngredientList(int categoryID) {
		alcoholModel.removeAllElements();
		for (Ingredient ingredient : Table.get(Ingredients.class)
				.getAllWithCategory(categoryID)) {
			alcoholModel.addElement(ingredient);
		}
	}

	@Override
	public void update() {
		listModel.removeAllElements();
		for (Ingredient ingredient : Table.get(Bar.class).getAllIngredients()) {
			listModel.addElement(ingredient);
		}

	}
}
