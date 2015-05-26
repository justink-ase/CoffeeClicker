package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;

import java.text.DecimalFormat;
import java.net.URL;
import java.nio.file.*;

public class TestRunner {
	public static void main(String[] args) {
		URL location = TestRunner.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
		
		CoffeePlayer p = new CoffeePlayer("Test", false, false, 15000.0,
				Double.MAX_VALUE, 150.0, CoffeeGame.createDefaultBuildings());
		CoffeeGame g = new CoffeeGame(p);
		try {
			p.save(Paths.get(
				"\\\\EPS-FS-01\\Student\\Data\\K\\krejcjus000\\Desktop\\a.ccs"));
		} catch (java.io.IOException e) {
			System.out.println(e.toString());
		}
		/*g.start();
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
		}*/
	}
}