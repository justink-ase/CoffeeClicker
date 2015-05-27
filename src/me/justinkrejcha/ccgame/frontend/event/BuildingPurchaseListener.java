package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.*;
import me.justinkrejcha.Util;

import java.awt.event.*;
import javax.swing.*;

public class BuildingPurchaseListener implements ActionListener {
	private CoffeeGame g;
	private Building b;
	private JButton component;
	private JLabel textComponent;

	public BuildingPurchaseListener(CoffeeGame g, Building b,
									JButton component, JLabel textComponent) {
		this.g = g;
		this.b = b;
		this.component = component;
		this.textComponent = textComponent;
	}

	public void actionPerformed(ActionEvent event) {
		CoffeePlayer p = g.getPlayer();
		for (Building building : p.getBuildingList().getAllBuildings()) {
			if (b.getName().equals(building.getName())) {
				b = building;
			}
		}
		p.buy(b);
		component.setEnabled(p.canBuy(b));
		component.setText(Util.format(CoffeeGame.BUTTON_TEXT, b.getName(),
					"" + b.getPrice()));
		textComponent.setText("" + b.getAmount());
	}
}