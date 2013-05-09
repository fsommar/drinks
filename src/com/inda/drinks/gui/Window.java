package com.inda.drinks.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
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
