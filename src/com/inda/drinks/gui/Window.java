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
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabbedPane.getSelectedComponent() instanceof Tab) {
					((Tab) tabbedPane.getSelectedComponent()).update();
				}
			}
		});

		tabbedPane.addTab(Resources.BAR, null, new MyBar(),
				Resources.BAR_INFO);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		tabbedPane.addTab(Resources.DRINK_LIST, null, new PersonalDrinkList(),
				Resources.DRINK_LIST_INFO);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		
		tabbedPane.addTab(Resources.ALL_DRINKS, null, new AllDrinks(),
				Resources.ALL_DRINKS_INFO);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		tabbedPane.addTab(Resources.ADD_DRINK, null, new AddDrinks(),
				Resources.ADD_DRINK);
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		add(tabbedPane, BorderLayout.CENTER);

		setSize(700, 500);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static final long serialVersionUID = -8920552184022900925L;

}
