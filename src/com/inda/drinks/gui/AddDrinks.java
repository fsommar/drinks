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
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Class that displays the window for adding a drink
 * @author Robin Hellgren
 *
 */

public class AddDrinks {
	public AddDrinks() {

	}

	// Läga till drinkar till databasen
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
		String[] glasData = { "cocktail", "highball", "lowball", "shot" };
		final JComboBox glaswareList = new JComboBox(glasData);
		glaswareList.setSelectedItem(null);
		topOption.add(glaswareList);

		// Create CenterField
		JPanel centerField = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Categories List
		String[] data = { "Vodka ren", "Vodka jordubb", "Vodka Choklad",
				"Vodka Hallon" }; // TODO: riktig data
		final JList categories = new JList(data);
		categories.setBorder(BorderFactory.createEtchedBorder());
		c.gridx = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 2;
		centerField.add(categories, c);

		// Temporary liqueur data
		final ArrayList<String> data2 = new ArrayList<String>();
		final DefaultComboBoxModel model = new DefaultComboBoxModel(
				data2.toArray());
		data2.addAll(Arrays.asList(new String[] { "Absolut vodka",
				"Vanlig vodka", "HB", "Jelzin" }));

		categories.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// Object item = event.getItem(); TODO: hämta spriiiiit från
				// item
				model.removeAllElements();
				for (String s : data2) { // temporär
					model.addElement(s);
				}				
			}
		});

		// Booooooooze List
		final JList boozeList = new JList(model);
		boozeList.setBorder(BorderFactory.createEtchedBorder());
		c.gridx = 4;
		centerField.add(boozeList, c);

		// Measurement control
		ArrayList<String> measurements = new ArrayList<String>();
		for (int i = 1; i < 100; i++) {
			measurements.add("" + i);
		}
		final SpinnerListModel spinner = new SpinnerListModel(measurements);
		final JSpinner centilitres = new JSpinner(spinner);
		centilitres.setToolTipText(Resources.CL);

		// Drink Ingredients
		final DefaultListModel ingredientList = new DefaultListModel();

		// Adding boooze button
		JButton addBooze = new JButton(Resources.ADD);
		addBooze.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (boozeList.getSelectedIndex() != -1) {
					String temp = ((String) boozeList.getSelectedValue() + " "
							+ (String) centilitres.getValue() + " cl");
					ingredientList.addElement(temp);
					categories.clearSelection();
					model.removeAllElements();
				}
			}
		});

		// Area for Spinner and Button
		JPanel smallField = new JPanel(new FlowLayout());
		smallField.add(centilitres);
		smallField.add(addBooze);
		centilitres.setPreferredSize(new Dimension(50, 30));
		centerField.add(smallField, c);

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
		drinkDescription.setBorder(BorderFactory.createEtchedBorder());
		centerField.add(drinkDescription, c);

		// Add drink button
		JButton addDrink = new JButton(Resources.ADD_DRINK);
		addDrink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!drinkName.getText().equals(Resources.NAME)
						&& glaswareList.getSelectedItem() != null
						&& !ingredientList.isEmpty()
						&& !drinkDescription.getText().equals("")) {
					// Lä‰gg till drinken i DB

					// Rensa fälten
					drinkName.setForeground(Color.gray);
					drinkName.setText(Resources.NAME);
					glaswareList.setSelectedItem(null);
					categories.clearSelection();
					boozeList.clearSelection();
					ingredientList.clear();
					drinkDescription.setText("");
				}
			}
		});
		topOption.add(addDrink);

		// Add top meny to panel
		panel.add(topOption, BorderLayout.NORTH);

		panel.add(centerField, BorderLayout.CENTER);
		return panel;
	}
}
