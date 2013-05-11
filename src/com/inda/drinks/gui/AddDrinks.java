package com.inda.drinks.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import com.inda.drinks.properties.Glass;

/**
 * Class that displays the window for adding a drink
 * 
 * @author Robin Hellgren
 * 
 */

public class AddDrinks implements Tab {

	// Lä‰ga till drinkar till databasen
	public JComponent showWindow() {
		JPanel panel = new JPanel(new BorderLayout());

		// Top Options
		JPanel topOption = new JPanel(new FlowLayout());

		// Name of the drink
		final JTextField drinkName = new JTextField();
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
		topOption.add(drinkName);

		// Type of glas
		final JComboBox glaswareList = new JComboBox(Glass.values());
		glaswareList.setSelectedItem(null);
		topOption.add(glaswareList);

		// Create CenterField
		JPanel centerField = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Left Options area
		final JPanel leftOptions = new JPanel(new GridLayout(15, 0));
		centerField.add(leftOptions);

		// Category box
		String[] data2 = { "Vodka", "Likˆr", "Whisky", "Alkfritt" };
		final JComboBox categoryBox = new JComboBox(data2);
		categoryBox.setSelectedItem(null);
		leftOptions.add(categoryBox);
		c.gridx = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 2;

		// Subcategory box
		final ArrayList<String> data3 = new ArrayList<String>();
		final DefaultComboBoxModel model = new DefaultComboBoxModel();
		data3.addAll(Arrays.asList(new String[] { "Ren", "Citron", "Mandarin",
				"Apelsin" }));
		final JComboBox subcategoryBox = new JComboBox(model);
		subcategoryBox.setSelectedItem(null);
		categoryBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					for (String s : data3) { // tempor‰är
						model.addElement(s);
					}
					subcategoryBox.setSelectedItem(null);
				}
			}

		});
		leftOptions.add(subcategoryBox);

		// Specific ingredient
		final JCheckBox specific = new JCheckBox();
		leftOptions.add(specific);

		// Measurement control
		ArrayList<String> measurements = new ArrayList<String>();
		for (int i = 1; i < 100; i++) {
			measurements.add("" + i);
		}
		final SpinnerListModel spinner = new SpinnerListModel(measurements);
		final JSpinner centilitres = new JSpinner(spinner);
		centilitres.setToolTipText(Resources.CL);
		leftOptions.add(centilitres);

		// Add liqueur button
		final JButton addDrink = new JButton(Resources.ADD);
		c.gridy = 5;
		leftOptions.add(addDrink, c);

		// Remove liqueur button
		final JButton removeDrink = new JButton(Resources.REMOVE);
		c.gridy = 6;
		leftOptions.add(removeDrink, c);

		// Liqueur box
		final ArrayList<String> data4 = new ArrayList<String>();
		final DefaultComboBoxModel model2 = new DefaultComboBoxModel(
				data4.toArray());
		final JComboBox alcohol = new JComboBox(model2);
		alcohol.setSelectedItem(null);
		data4.addAll(Arrays.asList(new String[] { "Absolut vodka",
				"Vanlig vodka", "HB", "Jelzin" }));

		c.weighty = 1;
		c.weightx = 1;
		c.gridx = 1;
		c.gridy = 4;

		specific.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (specific.isSelected()) {
					model2.removeAllElements();
					leftOptions.remove(addDrink);
					leftOptions.remove(removeDrink);
					GridBagConstraints c = new GridBagConstraints();

					leftOptions.add(alcohol, c);
					for (String s : data4) { // temporä‰r
						model2.addElement(s);
					}
					leftOptions.add(addDrink);
					leftOptions.add(removeDrink);

				}
				if (!specific.isSelected()) {
					model2.removeAllElements();
					leftOptions.remove(alcohol);
				}
			}
		});
		
		// Drink Ingredients
		final DefaultListModel ingredientList = new DefaultListModel();

		// Field for Description and Ingredients
		c = new GridBagConstraints();
		c.gridx = 6;
		c.gridwidth = 20;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		// Ingredients area
		final JList ingredientArea = new JList(ingredientList);
		ingredientArea.setBorder(BorderFactory.createEtchedBorder());
		centerField.add(ingredientArea, c);

		// Description area
		final JTextArea drinkDescription = new JTextArea();
		c.gridx = 26;
		drinkDescription.setBorder(BorderFactory.createEtchedBorder());
		centerField.add(drinkDescription, c);
		
		// If user adds drink
		addDrink.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO: if (spriten inte finns i ingredienslistan ‰nnu)
				if(specific.isSelected()) {
				String temp = ((String) alcohol.getSelectedItem() + " "
						+ (String) centilitres.getValue() + " cl");
						 ingredientList.addElement(temp);
				} else {
					String temp = ((String) categoryBox.getSelectedItem() + " " + (String) subcategoryBox.getSelectedItem() + " "
							+ (String) centilitres.getValue() + " cl");
							 ingredientList.addElement(temp);
				}
				model.removeAllElements();
				categoryBox.setSelectedItem(null);
				model2.removeAllElements();
				subcategoryBox.setSelectedItem(null);
				// }
			}
		});
		
		// If user removes drink
		removeDrink.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!ingredientList.isEmpty() && ingredientArea.getSelectedIndex() != -1) {
					int removeIndex = ingredientArea.getSelectedIndex();
					ingredientList.remove(removeIndex);
				}
			}
		});

		// // Temporary liqueur data
		// categories.addListSelectionListener(new ListSelectionListener() {
		// @Override
		// public void valueChanged(ListSelectionEvent arg0) {
		// // Object item = event.getItem(); TODO: hämta spriiiiit från
		// // item
		// /*
		// * model.removeAllElements(); for (String s : data2) { //
		// * temporär model.addElement(s); }
		// */
		// }
		// });

		// // Adding boooze button
		// JButton addBooze = new JButton(Resources.ADD);
		// addBooze.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent arg0) {
		// if (boozeList.getSelectedIndex() != -1) {
		// String temp = ((String) boozeList.getSelectedValue() + " "
		// + (String) centilitres.getValue() + " cl");
		// ingredientList.addElement(temp);
		// model.removeAllElements();
		// }
		// }
		// });

		// Add drink button
		JButton addWholeDrink = new JButton(Resources.ADD_DRINK);
		addWholeDrink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!drinkName.getText().equals(Resources.NAME)
						&& glaswareList.getSelectedItem() != null
						&& !ingredientList.isEmpty()
						&& !drinkDescription.getText().equals("")) {
					// Lä‰gg till drinken i DB

					// Rensa fä‰lten
					drinkName.setForeground(Color.gray);
					drinkName.setText(Resources.NAME);
					glaswareList.setSelectedItem(null);
					categoryBox.setSelectedItem(null);
					subcategoryBox.setSelectedItem(null);
					ingredientList.clear();
					drinkDescription.setText("");
					specific.setEnabled(false);
					//TODO: Rensa spinner
				}
			}
		});
		topOption.add(addWholeDrink);

		// Add top meny to panel
		panel.add(topOption, BorderLayout.NORTH);
		panel.add(centerField, BorderLayout.CENTER);
		return panel;
	}

	@Override
	public void update() {

	}
}
