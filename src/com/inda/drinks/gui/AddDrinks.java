package com.inda.drinks.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.inda.drinks.db.Table;
import com.inda.drinks.db.tables.Categories;
import com.inda.drinks.db.tables.Contents;
import com.inda.drinks.db.tables.Ingredients;
import com.inda.drinks.db.tables.Recipes;
import com.inda.drinks.properties.Category;
import com.inda.drinks.properties.Content;
import com.inda.drinks.properties.Glass;
import com.inda.drinks.properties.Ingredient;
import com.inda.drinks.properties.Recipe;

/**
 * Class that displays the window for adding a drink
 * 
 * @author Robin Hellgren
 * 
 */
public class AddDrinks extends JPanel implements Tab {
	private static final long serialVersionUID = 1425527818076719715L;
	private DefaultComboBoxModel alcoholModel = new DefaultComboBoxModel();
	private JComboBox alcoholBox = new JComboBox(alcoholModel);
	private JTextField drinkName = new JTextField();
	private JCheckBox specific = new JCheckBox(Resources.SPECIFIC);
	private Content content;

	// LŠäga till drinkar till databasen
	public AddDrinks() {
		super(new BorderLayout());
		final Recipes recipes = Table.get(Recipes.class);

		JPanel topOption = new JPanel(new FlowLayout());
		final JPanel centerField = new JPanel(new GridBagLayout());
		alcoholBox.setEnabled(false);
		// Glass types
		final JComboBox glassList = new JComboBox(Glass.values());
		glassList.setSelectedItem(null);

		drinkName.setPreferredSize(new Dimension(150, 20));
		drinkName.setForeground(Color.gray);
		drinkName.setText(Resources.NAME);
		drinkName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				drinkName.setForeground(Color.black);
				if (drinkName.getText().equals(Resources.NAME)) {
					drinkName.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (drinkName.getText().equals("")) {
					drinkName.setForeground(Color.gray);
					drinkName.setText(Resources.NAME);
				}
			}
		});

		// Measurement control
		ArrayList<String> measurements = new ArrayList<String>();
		for (int i = 1; i < 51; i++) {
			measurements.add(Integer.toString(i));
		}
		final SpinnerListModel volumeModel = new SpinnerListModel(measurements);
		final JSpinner volumeSpinner = new JSpinner(volumeModel);
		volumeSpinner.setToolTipText(Resources.CL);
		volumeSpinner.setPreferredSize(new Dimension(40, 20));
		volumeModel.setValue(Integer.toString(4));
		final JButton addContent = new JButton(Resources.ADD);
		final JButton removeContent = new JButton(Resources.REMOVE);

		// Drink contents
		final DefaultListModel ingredientModel = new DefaultListModel();
		final JList ingredientList = new JList(ingredientModel);
		final JTextArea drinkDescription = new JTextArea();

		final DefaultComboBoxModel subcategoryModel = new DefaultComboBoxModel();
		final JComboBox subcategoryBox = new JComboBox(subcategoryModel);
		subcategoryBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED
						&& subcategoryBox.getSelectedIndex() != -1) {
					if (event.getItem() instanceof Category) {
						fillList(((Category) event.getItem()).getID());
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
							subcategoryBox.setVisible(false);
							fillList(((Category) event.getItem()).getID());
						} else {
							subcategoryBox.setVisible(true);
							for (Category category : subCategories) {
								subcategoryModel.addElement(category);
							}
						}
					}
				}
			}
		});

		specific.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() == specific) {
					alcoholBox.setEnabled(specific.isSelected());
				}
			}
		});

		// If user adds an ingredient
		addContent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (alcoholBox.getSelectedIndex() == -1
						|| !(alcoholBox.getSelectedItem() instanceof Ingredient)) {
					return;
				}
				if (content == null) {
					content = new Content();
				}
				Ingredient ingredient = (Ingredient) alcoholBox
						.getSelectedItem();
				int volume = Integer.parseInt((String) volumeSpinner.getValue());
				Content.Item item = new Content.Item(ingredient, volume,
						specific.isSelected());
				if (!ingredientModel.contains(item)) {
					content.add(item);
					ingredientModel.addElement(item);
					// Clear boxes
					alcoholBox.setSelectedItem(null);
					alcoholBox.setEnabled(false);
					alcoholModel.removeAllElements();
					subcategoryModel.removeAllElements();
					subcategoryBox.setSelectedItem(null);
					subcategoryBox.setVisible(false);
					categoryBox.setSelectedItem(null);
				}
			}
		});

		// If user removes drink
		removeContent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!ingredientModel.isEmpty()
						&& ingredientList.getSelectedIndex() != -1) {
					int removeIndex = ingredientList.getSelectedIndex();
					ingredientModel.remove(removeIndex);
				}
			}
		});

		// Add drink button
		JButton addDrink = new JButton(Resources.ADD_DRINK);
		addDrink.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!drinkName.getText().equals(Resources.NAME)
						&& glassList.getSelectedItem() != null
						&& !ingredientModel.isEmpty()
						&& !drinkDescription.getText().equals("")) {

						Glass g = (Glass) glassList.getSelectedItem();
						Recipe r = new Recipe.Builder().ID(-1)
								.name(drinkName.getText())
								.instructions(drinkDescription.getText())
								.glassID(g.getID()).build();
						try {
							recipes.insert(r);
							Table.get(Contents.class).insert(
									recipes.getPreviousID(), content);
							content = null;
							// Rensa fŠälten
							drinkName.setForeground(Color.gray);
							drinkName.setText(Resources.NAME);
							glassList.setSelectedItem(null);
							categoryBox.setSelectedItem(null);
							subcategoryBox.setSelectedItem(null);
							ingredientModel.clear();
							drinkDescription.setText("");
							specific.setEnabled(false);
							volumeSpinner.setValue(Integer.toString(4));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					} else {
						ArrayList<String> errors = new ArrayList<String>();
						if (drinkName.getText().equals(Resources.NAME)) {
							errors.add(Resources.DRINK_NAME);
						}
						if (glassList.getSelectedItem() == null) {
							errors.add(Resources.DRINK_GLASS);
						}
						if (ingredientModel.isEmpty()) {
							errors.add(Resources.DRINK_INGREDIENTS);
						}
						if (drinkDescription.getText().equals("")) {
							errors.add(Resources.DRINK_DESCRIPTION);
						}
						String errorMessage = Resources.ERROR_FIRST;
						if (errors.size() == 1) {
							errorMessage += errors.get(0) + Resources.ERROR_LAST;
							JOptionPane
									.showMessageDialog(centerField, errorMessage);
							return;
						} else {
							errorMessage += errors.get(0);
							// Lägger till alla errors utom första och sista
							for (int i = 1; i < errors.size() - 1; i++) {
								errorMessage += ", " + errors.get(i);
							}
							errorMessage += " och " + errors.get(errors.size() - 1)
									+ Resources.ERROR_LAST;
							JOptionPane
									.showMessageDialog(centerField, errorMessage);
							return;
						}
				}
			}
		});

		/**/
		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		centerField.add(categoryBox, c);
		c.gridx += c.gridwidth;
		c.gridwidth = 1;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		centerField.add(subcategoryBox, c);
		c.gridx++;
		centerField.add(specific, c);
		c.gridx = 1;
		c.gridy++;
		c.gridwidth = 5;
		c.weightx = 1;
		centerField.add(alcoholBox, c);
		c.gridx = 1;
		c.gridy++;
		c.weightx = 0;
		c.gridwidth = 1;
		centerField.add(volumeSpinner, c);
		c.gridx++;
		centerField.add(addContent, c);
		/**/
		c.gridx = 1;
		c.gridy++;
		c.weighty = 0.05;
		c.anchor = GridBagConstraints.SOUTHWEST;
		c.gridwidth = 6;
		centerField.add(new JLabel(Resources.CONTENTS), c);
		c.gridx = 1;
		c.gridy = GridBagConstraints.RELATIVE;
		c.weightx = 1;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;
		centerField.add(new JScrollPane(ingredientList), c);
		c.gridy = GridBagConstraints.RELATIVE;
		c.weighty = 0.05;
		c.anchor = GridBagConstraints.SOUTHWEST;
		centerField.add(new JLabel(Resources.DESCRIPTION), c);
		c.gridy = GridBagConstraints.RELATIVE;
		c.weighty = 0.5;
		centerField.add(new JScrollPane(drinkDescription), c);

		topOption.add(drinkName);
		topOption.add(glassList);
		topOption.add(addDrink);

		add(centerField, BorderLayout.CENTER);
		add(topOption, BorderLayout.NORTH);
	}

	private void fillList(int categoryID) {
		alcoholModel.removeAllElements();
		for (Ingredient ingredient : Table.get(Ingredients.class)
				.getAllWithCategory(categoryID)) {
			alcoholModel.addElement(ingredient);
		}
		alcoholBox.setEnabled(specific.isSelected());
	}

	@Override
	public void update() {
		drinkName.requestFocus();
	}
}
