package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.*;
import me.justinkrejcha.Util;

import java.awt.event.*;
import javax.swing.*;

public class BuildingPurchaseListener implements ActionListener {
	private CoffeePlayer p;
	private Building b;
	private JButton component;
	private JLabel textComponent;
	
	public BuildingPurchaseListener(CoffeePlayer p, Building b, 
									JButton component, JLabel textComponent) {
		this.p = p;
		this.b = b;
		this.component = component;
		this.textComponent = textComponent;
	}

	public void actionPerformed(ActionEvent event) {
		p.buy(b);
		component.setEnabled(p.canBuy(b));
		component.setText(Util.format(CoffeeGame.BUTTON_TEXT, b.getName(), 
					"" + b.getPrice()));
		int amount = b.getAmount();

		textComponent.setText("" + amount);
		textComponent.setToolTipText(Util.format(CoffeeGame.HOVER_TEXT, "" +
				b.getAmount(), b.getName()));
	}
}