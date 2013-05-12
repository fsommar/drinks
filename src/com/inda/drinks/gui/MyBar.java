package com.inda.drinks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	private JComboBox alcoholBox = new JComboBox(alcoholModel);

	// Fšönster däŠr användaren läŠgger till/tar bort fråŒn sitt fšörråŒd
	public MyBar() {
		super(new GridBagLayout());

		final JList rightDrinkList = new JList(listModel);
		alcoholBox.setEnabled(false);

		final DefaultComboBoxModel subcategoryModel = new DefaultComboBoxModel();
		final JComboBox subcategoryBox = new JComboBox(subcategoryModel);
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
		subcategoryBox.setVisible(false);

		DefaultComboBoxModel categoryModel = new DefaultComboBoxModel();
		final JComboBox categoryBox = new JComboBox(categoryModel);
		for (Category category : Table.get(Categories.class).getAllWithParent(
				Category.NO_PARENT)) {
			categoryModel.addElement(category);
		}
		categoryBox.setSelectedItem(null);
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
							// subcategoryBox.setEnabled(false);
							subcategoryBox.setVisible(false);
							fillIngredientList(((Category) event.getItem())
									.getID());
						} else {
							subcategoryBox.setVisible(true);
							// subcategoryBox.setEnabled(true);
							for (Category category : subCategories) {
								subcategoryModel.addElement(category);
							}
						}
					}
				}
			}
		});

		// Remove liqueur button
		final JButton removeDrink = new JButton(Resources.REMOVE);
		removeDrink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (rightDrinkList.getSelectedValue() instanceof Ingredient) {
					Ingredient selected = (Ingredient) rightDrinkList
							.getSelectedValue();
					if (!listModel.isEmpty() && selected != null) {
						int n = JOptionPane.showConfirmDialog(MyBar.this,
								Resources.removeDialog(selected.toString()),
								Resources.REMOVE + " " + selected,
								JOptionPane.OK_CANCEL_OPTION);
						if (n == JOptionPane.OK_OPTION) {
							int index = rightDrinkList.getSelectedIndex();
							if (index != -1) {
								try {
									Table.get(Bar.class).remove(
											selected.getID());
									listModel.remove(rightDrinkList
											.getSelectedIndex());
									rightDrinkList.setSelectedIndex(Math.max(
											index - 1, -1));
								} catch (SQLException e) {
									// TODO: Show JOptionPane saying delete failed.
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		});

		// Add liqueur button
		final JButton addContent = new JButton(Resources.ADD);
		addContent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (!listModel.contains(alcoholBox.getSelectedItem())
						&& alcoholBox.getSelectedItem() instanceof Ingredient) {
					Ingredient ingredient = (Ingredient) alcoholBox
							.getSelectedItem();
					try {
						Table.get(Bar.class).insert(ingredient.getID());
						listModel.addElement(ingredient);
						// Clear boxes
						alcoholBox.setSelectedItem(null);
						alcoholBox.setEnabled(false);
						alcoholModel.removeAllElements();
						subcategoryModel.removeAllElements();
						subcategoryBox.setSelectedItem(null);
						subcategoryBox.setVisible(false);
						categoryBox.setSelectedItem(null);
					} catch (SQLException e) {
						// TODO: Show JOptionPane saying add failed.
						e.printStackTrace();
					}
				}
			}
		});

		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		add(categoryBox, c);
		c.gridx = 2;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		add(subcategoryBox, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1;
		add(alcoholBox, c);
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0;
		c.gridwidth = 1;
		add(addContent, c);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		JScrollPane scroll = new JScrollPane(rightDrinkList);
		add(scroll, c);
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0;
		c.weighty = 0;
		add(removeDrink, c);
	}

	private void fillIngredientList(int categoryID) {
		alcoholModel.removeAllElements();
		for (Ingredient ingredient : Table.get(Ingredients.class)
				.getAllWithCategory(categoryID)) {
			alcoholModel.addElement(ingredient);
		}
		alcoholBox.setEnabled(true);
	}

	@Override
	public void update() {
		listModel.removeAllElements();
		for (Ingredient ingredient : Table.get(Bar.class).getAllIngredients()) {
			listModel.addElement(ingredient);
		}

	}
}
