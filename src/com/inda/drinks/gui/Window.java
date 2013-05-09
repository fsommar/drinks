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

import com.inda.drinks.gui.AllDrinks;

public class Window extends JFrame {

	public Window() {
		// Top tab meny
		JTabbedPane tabbedPane = new JTabbedPane();

		MyBar userStash = new MyBar(Window.this);
		tabbedPane.addTab("Ditt Förråd", null, userStash.showWindow(),
				"Lägg till eller ta bort ur ditt förråd.");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		PersonalDrinkList userDrinkWindow = new PersonalDrinkList();
		tabbedPane.addTab("Din Drinklista", null, userDrinkWindow.showWindow(),
				"Lista de drinkar du kan göra.");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		AllDrinks drinkWindow = new AllDrinks();
		tabbedPane.addTab("Samtliga Drinkar", null, drinkWindow.showWindow(),
				"Lista samtliga drinkar.");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		AddDrinks drinkCreator = new AddDrinks();
		tabbedPane.addTab("Lägg Till Drink", null, drinkCreator.showWindow(),
				"Lägg till drink.");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		add(tabbedPane, BorderLayout.CENTER);

		setSize(700, 500);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static final long serialVersionUID = -8920552184022900925L;

}
