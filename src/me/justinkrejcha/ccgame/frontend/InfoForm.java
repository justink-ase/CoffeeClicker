package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.Building;
import me.justinkrejcha.ccgame.CoffeePlayer;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Shows information about the game's state. Useful for getting statistics.
 * @author Justin
 * @since 5/26/2015 7:06 PM
 */
public class InfoForm {
	public static String STATS_HEADER = "Coffee Clicker Statistics";

	private CoffeePlayer player;
	private JFrame window;
	private JLabel header;
	private JLabel mainText;

	public InfoForm(CoffeePlayer player) {
		this.player = player;

		DecimalFormat f = new DecimalFormat("#.#");

		window = new JFrame("Statistics");
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setSize(new Dimension(300, 200));
		window.setLocation(new Point(400, 200));

		String headerLabel = STATS_HEADER;
		if (player.getName() != null) {
			headerLabel = headerLabel + " for " + player.getName();
		}
		header = new JLabel(headerLabel);
		header.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

		String main = "<html>Coffees: " + f.format(player.getCoffees()) +
				"<br>- All Time: " + f.format(player.getTotalCoffees()) +
				"<br>- Per Second: " + f.format(player.getPerSecond()) +
				"<br>- Spent: " + calculateSpent() +
				"<br><br>Buildings: " + getCount();

		if (player.hasCheated()) {
			main = main + "<br><br>Cheated: Yes";
		}
		if (player.isFunRuined()) {
			main = main + "<br>- This player also ruined the fun :(";
		}

		mainText = new JLabel();
		mainText.setText(main + "</html>");
		mainText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

		window.add(header, BorderLayout.NORTH);
		window.add(mainText, BorderLayout.CENTER);
		window.pack();
	}

	public void show() {
		window.setVisible(true);
	}

	private int getCount() {
		int count = 0;
		for (Building b : player.getBuildingList().getAllBuildings()) {
			count += b.getAmount();
		}
		return count;
	}

	private long calculateSpent() {
		long spent = 0;
		for (Building building : player.getBuildingList().getAllBuildings()) {
			spent += Math.ceil(building.getBasePrice() *
					(Math.pow(1.15, building.getAmount()) - 1) / 0.15);
		}
		return spent;
	}
}
