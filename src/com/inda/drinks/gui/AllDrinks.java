package com.inda.drinks.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Class that displays all drinks currently in the database
 * @author Robin Hellgren
 *
 */

public class AllDrinks {
	public AllDrinks() {
	}

	// Visar samtliga drinkar i databasen
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
		final JPanel drinkInfo = new JPanel(new GridBagLayout());
		drinkInfo.setBorder(BorderFactory.createEtchedBorder());

		// Drink list listener, displays chosen drink
		leftMeny.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				//Display drink from DB in drinkInfo
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				drinkInfo.removeAll(); //hä‰nder aldrig?	
			}
		});
		
		panel.add(drinkInfo, BorderLayout.CENTER);

		return panel;
	}
}
