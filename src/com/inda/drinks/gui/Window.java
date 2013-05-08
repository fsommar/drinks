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
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Window extends JFrame {

	public Window() {
		// Top tab meny
		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Bar om rumpan.", null, myBar(),
				"Lägg till eller ta bort ur ditt förråd.");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Drick dig full!", null, drinkList(),
				"Lista de drinkar du kan göra.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		tabbedPane.addTab("Alla blir fulla?", null, allDrinks(),
				"Lista samtliga drinkar.");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		tabbedPane.addTab("Lägg till spriiiiiiiiiit", null, newDrink(),
				"Lägg till drink.");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		add(tabbedPane, BorderLayout.CENTER);

		setSize(700, 500);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Fönster där användaren lägger till/tar bort från sitt förråd
	private JComponent myBar() {
		JPanel panel = new JPanel(new GridBagLayout());

		// Drink list
		final DefaultListModel listModel = new DefaultListModel();
		String[] data = { "one", "two", "three", "four" };
		for (String s : data) {
			listModel.addElement(s);
		}

		// Right drink list area
		final JList rightDrinkList = new JList(listModel);
		rightDrinkList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					int n = JOptionPane.showConfirmDialog(Window.this,
							"Do you want to delete this item from your stock?",
							"hello world", JOptionPane.OK_CANCEL_OPTION);
					if (n == JOptionPane.OK_OPTION) {
						rightDrinkList.setValueIsAdjusting(true);
						listModel.remove(rightDrinkList.getSelectedIndex());
					}
				}
			}
		});
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1;
		c.weightx = 1;
		c.gridheight = 20;
		c.gridx = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		panel.add(rightDrinkList, c);

		// Left options panel
		JPanel leftOptions = new JPanel(new GridLayout(3, 0));

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
		JButton addDrink = new JButton("Lägg till");
		addDrink.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!listModel.contains(alcohol.getSelectedItem())) {
					listModel.addElement(alcohol.getSelectedItem());
				}
			}
		});
		c = new GridBagConstraints();
		c.gridy = 2;
		leftOptions.add(addDrink, c);

		// Add left options to panel
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		panel.add(leftOptions, c);

		return panel;
	}

	// Drinklista genererad utifrån användarens förråd
	private JComponent drinkList() {

		JPanel panel = new JPanel(new BorderLayout());

		// West flow-layout
		JPanel leftSide = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		panel.add(leftSide, BorderLayout.WEST);

		// Left drink meny
		String[] data = { "one", "two", "three", "four" };
		JList leftMeny = new JList(data);
		leftMeny.setBorder(BorderFactory.createEtchedBorder());
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		leftSide.add(leftMeny, c);

		// Center Drink info
		JTextPane drinkInfo = new JTextPane();

		panel.add(drinkInfo, BorderLayout.CENTER);

		return panel;
	}

	// Visar samtliga drinkar i databasen
	private JComponent allDrinks() {

		JPanel panel = new JPanel(new BorderLayout());

		// West flow-layout
		JPanel leftSide = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		panel.add(leftSide, BorderLayout.WEST);

		// Left drink meny
		String[] data = { "one", "two", "three", "four" };
		JList leftMeny = new JList(data);
		leftMeny.setBorder(BorderFactory.createEtchedBorder());
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		leftSide.add(leftMeny, c);

		// Center Drink info
		JTextPane drinkInfo = new JTextPane();

		panel.add(drinkInfo, BorderLayout.CENTER);

		return panel;
	}

	// Läga till drinkar till databasen
	private JComponent newDrink() {
		JPanel panel = new JPanel(new BorderLayout());

		// Top Options
		JPanel topOption = new JPanel(new FlowLayout());

		// Name of the drink
		final JTextField drinkName = new JTextField();
		drinkName.setPreferredSize(new Dimension(150, 20));
		drinkName.setForeground(Color.gray);
		drinkName.setText("Namn");
		drinkName.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				drinkName.setForeground(Color.black);
				if (drinkName.getText().equals("Namn")) {
					drinkName.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (drinkName.getText().equals("")) {
					drinkName.setForeground(Color.gray);
					drinkName.setText("Namn");
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
		JList categories = new JList(data);
		categories.setBorder(BorderFactory.createEtchedBorder());
		c.gridx = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 2;
		centerField.add(categories, c);

		// Booooooooze List
		String[] data2 = { "Absolut vodka", "Vanlig vodka", "HB", "Jelzin" };
		final JList boozeList = new JList(data2);
		boozeList.setBorder(BorderFactory.createEtchedBorder());
		c.gridx = 4;
		centerField.add(boozeList, c);

		// Measurement control
		ArrayList<String> measurements = new ArrayList<String>();
		for (int i = 1; i < 100; i++) {
			measurements.add("" + i);
		}
		SpinnerListModel spinner = new SpinnerListModel(measurements);
		final JSpinner centilitres = new JSpinner(spinner);
		centilitres.setToolTipText("cl");

		// Drink Ingredients
		final DefaultListModel ingredientList = new DefaultListModel();

		// Adding boooze button
		JButton addBooze = new JButton("Lägg till");
		addBooze.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (boozeList.getSelectedIndex() != -1) {
					String temp = ((String) boozeList.getSelectedValue() + " "
							+ (String) centilitres.getValue() + " cl");
					ingredientList.addElement(temp);
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
		JList ingredientArea = new JList(ingredientList);
		ingredientArea.setBorder(BorderFactory.createEtchedBorder());
		centerField.add(ingredientArea, c);

		// Description area
		final JTextArea drinkDescription = new JTextArea();
		drinkDescription.setBorder(BorderFactory.createEtchedBorder());
		centerField.add(drinkDescription, c);

		// Add drink button
		JButton addDrink = new JButton("Lägg till drink");
		addDrink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!drinkName.getText().equals("Namn")
						&& glaswareList.getSelectedItem() != null
						&& !ingredientList.isEmpty()
						&& !drinkDescription.getText().equals("")) {
					// Lägg till drinken i DB
				}
			}
		});
		topOption.add(addDrink);

		// Add top meny to panel
		panel.add(topOption, BorderLayout.NORTH);

		panel.add(centerField, BorderLayout.CENTER);
		return panel;
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	private static final long serialVersionUID = -8920552184022900925L;

}
