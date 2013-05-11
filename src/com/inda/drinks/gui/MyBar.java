package com.inda.drinks.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class that displays the window for adding and removing drinks to/from the
 * users stash
 * 
 * @author Robin Hellgren
 * 
 */

public class MyBar implements Tab {
	// Fönster där användaren lägger till/tar bort från sitt förråd
	public JComponent showWindow() {
		final JPanel panel = new JPanel(new GridBagLayout());

		// Drink list
		final DefaultListModel listModel = new DefaultListModel();
		String[] data = { "one", "two", "three", "four" };
		for (String s : data) {
			listModel.addElement(s);
		}

		// Right drink list area
		final JList rightDrinkList = new JList(listModel);
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.weightx = 1;
		c.gridheight = 20;
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		panel.add(rightDrinkList, c);

		// Left options panel
		JPanel leftOptions = new JPanel(new GridLayout(4, 0));

		// Category box for liqueur
		String[] data2 = { "Vodka ren", "Vodka Jordgubb", "Vodka Choklad",
				"Vodka Hallon" }; // TODO: riktig data

		final JComboBox boozeBox = new JComboBox(data2);
		boozeBox.setSelectedItem(null);
		leftOptions.add(boozeBox);

		final ArrayList<String> data3 = new ArrayList<String>();
		final DefaultComboBoxModel model = new DefaultComboBoxModel(
				data3.toArray());

		data3.addAll(Arrays.asList(new String[] { "Absolut vodka",
				"Vanlig vodka", "HB", "Jelzin" }));

		boozeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// Object item = event.getItem(); TODO: hämta spriiiiit från
					// item
					model.removeAllElements();
					for (String s : data3) { // temporär
						model.addElement(s);
					}
				}
			}
		});

		// Box for spriiiiiiit
		final JComboBox alcohol = new JComboBox(model);
		c = new GridBagConstraints();
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 1;
		leftOptions.add(alcohol, c);

		// Add liqueur button
		JButton addDrink = new JButton(Resources.ADD);
		addDrink.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!listModel.contains(alcohol.getSelectedItem())) {
					listModel.addElement(alcohol.getSelectedItem());
					model.removeAllElements();
					boozeBox.setSelectedItem(null);
				}
			}
		});
		c = new GridBagConstraints();
		c.gridy = 2;
		leftOptions.add(addDrink, c);

		// Remove liqueur button
		JButton removeDrink = new JButton(Resources.REMOVE);
		removeDrink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object selected = rightDrinkList.getSelectedValue();
				if (!listModel.isEmpty() && selected != null) {
					int n = JOptionPane.showConfirmDialog(panel,
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
		c.gridy = 3;
		leftOptions.add(removeDrink, c);

		// Add left options to panel
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		panel.add(leftOptions, c);

		return panel;
	}

	@Override
	public void update() {

	}
}
