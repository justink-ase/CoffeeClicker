package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.*;

import java.awt.event.*;
import javax.swing.*;

public class BuildingPurchaseListener implements ActionListener {
	private CoffeePlayer p;
	private Building b;
	private JComponent component;
	
	public BuildingPurchaseListener(CoffeePlayer p, Building b, 
									JComponent component) {
		this.p = p;
		this.b = b;
		this.component = component;
	}

	public void actionPerformed(ActionEvent event) {
		p.buy(b);
		component.setEnabled(p.canBuy(b));
	}
}