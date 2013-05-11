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
import javax.swing.JCheckBox;
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
	// Fšönster däŠr anväŠndaren läŠgger till/tar bort fråŒn sitt fšörråŒd

	Window Window;

	public MyBar(Window main) {
		Window = main;
	}

	// Fšönster däŠr användaren läŠgger till/tar bort fråŒn sitt fšörråŒd
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
		final GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.weightx = 8;
		c.gridheight = 20;
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		panel.add(rightDrinkList, c);

		// Left options panel
		final JPanel leftOptions = new JPanel(new GridLayout(15, 0));

		// Category box
		String[] data2 = { "Vodka", "Likör", "Whisky", "Alkfritt" }; // TODO:
																		// riktig
																		// data
		final JComboBox categoryBox = new JComboBox(data2);
		categoryBox.setSelectedItem(null);
		leftOptions.add(categoryBox);

		// Subcategory box
		final ArrayList<String> data3 = new ArrayList<String>();
		final DefaultComboBoxModel model = new DefaultComboBoxModel(
				data3.toArray());

		data3.addAll(Arrays.asList(new String[] { "Ren", "Citron", "Mandarin",
				"Apelsin" }));
		final JComboBox subcategoryBox = new JComboBox(model);
		subcategoryBox.setSelectedItem(null);

		categoryBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// Object item = event.getItem(); TODO: hŠämta spriiiiit
					// fråŒn
					// item
					model.removeAllElements();
					for (String s : data3) { // temporäŠr
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

		// Add liqueur button
		final JButton addDrink = new JButton(Resources.ADD);
		c.gridy = 5;
		leftOptions.add(addDrink, c);

		// Remove liqueur button
		final JButton removeDrink = new JButton(Resources.REMOVE);
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
					leftOptions.add(alcohol, c);
					for (String s : data4) { // temporŠär
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

		// If user adds drink
		addDrink.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!listModel.contains(alcohol.getSelectedItem())) {
					listModel.addElement(alcohol.getSelectedItem());
					model.removeAllElements();
					categoryBox.setSelectedItem(null);
					model2.removeAllElements();
					subcategoryBox.setSelectedItem(null);
				}
			}
		});
		
		// Add left options to panel
		c.gridx = 1;
		c.gridy = 1;
		panel.add(leftOptions, c);

		return panel;
	}

	@Override
	public void update() {

	}
}
