package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.*;
import me.justinkrejcha.Util;

import java.awt.event.*;
import javax.swing.*;

public class BuildingPurchaseListener implements ActionListener {
	private CoffeePlayer p;
	private Building b;
	private JButton component;
	
	public BuildingPurchaseListener(CoffeePlayer p, Building b, 
									JButton component) {
		this.p = p;
		this.b = b;
		this.component = component;
	}

	public void actionPerformed(ActionEvent event) {
		p.buy(b);
		component.setEnabled(p.canBuy(b));
		component.setText(Util.format(CoffeeGame.BUTTON_TEXT, b.getName(), 
					"" + b.getPrice()));
	}
}