package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;

import java.text.DecimalFormat;
import java.net.URL;

public class TestRunner {
	public static void main(String[] args) {
		URL location = TestRunner.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
		
		CoffeePlayer p = new CoffeePlayer("Test", false, 15000.0,
				Double.MAX_VALUE, 150.0, CoffeeGame.createDefaultBuildings());
		CoffeeGame g = new CoffeeGame(p);
		g.start();
		while (true) {
			//System.out.println(new DecimalFormat("#.#").format(p.getCoffees()));
			System.out.println(p);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//skip
			}
			Building first = p.getBuildingList().get(0);
			while (p.canBuy(first)) {
				p.buy(first);
			}
		}
	}
}