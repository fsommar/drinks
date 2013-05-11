package com.inda.drinks.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.inda.drinks.gui.AllDrinks;

/**
 * Creates main window and top tabs
 * @author Robin Hellgren
 *
 */
public class Window extends JFrame {

	public Window() {
		super("#wäng");
		// Top tab meny
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() instanceof Tab) {
					((Tab) e.getSource()).update();
				}
			}
		});

		MyBar userStash = new MyBar(Window.this);
		tabbedPane.addTab(Resources.BAR, null, userStash.showWindow(),
				Resources.BAR_INFO);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		PersonalDrinkList userDrinkWindow = new PersonalDrinkList();
		tabbedPane.addTab(Resources.DRINK_LIST, null, userDrinkWindow.showWindow(),
				Resources.DRINK_LIST_INFO);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		AllDrinks drinkWindow = new AllDrinks();
		tabbedPane.addTab(Resources.ALL_DRINKS, null, drinkWindow.showWindow(),
				Resources.ALL_DRINKS_INFO);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		AddDrinks drinkCreator = new AddDrinks();
		tabbedPane.addTab(Resources.ADD_DRINK, null, drinkCreator.showWindow(),
				Resources.ADD_DRINK);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		add(tabbedPane, BorderLayout.CENTER);

		setSize(700, 500);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static final long serialVersionUID = -8920552184022900925L;

}
