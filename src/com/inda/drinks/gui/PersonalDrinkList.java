package com.inda.drinks.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * Class that displays the drinks a user can make
 * with his or hers current stash
 * @author Robin Hellgren
 *
 */

public class PersonalDrinkList {
	public PersonalDrinkList() {
	}

	// Drinklista genererad utifrån användarens förråd
	public JComponent showWindow() {

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
		drinkInfo.setBorder(BorderFactory.createEtchedBorder());

		panel.add(drinkInfo, BorderLayout.CENTER);

		return panel;
	}
}
