package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.Util;
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

	/**
	 * Creates a new info form based on an information form.
	 * @param player Player to use on the form.
	 */
	public InfoForm(CoffeePlayer player) {
		this.player = player;

		DecimalFormat f = new DecimalFormat("#.#");

		window = new JFrame("Statistics");
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setSize(new Dimension(300, 200));
		window.setLocation(Util.getCenterScreen(window));
		window.setResizable(false);

		String headerLabel = STATS_HEADER;
		if (player.getName() != null) {
			headerLabel = headerLabel + " for " + player.getName();
		}
		JLabel header = new JLabel(headerLabel);
		header.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

		String main = "<html>Coffees: " + f.format(player.getCoffees()) +
				"<br>- All Time: " + f.format(player.getTotalCoffees()) +
				"<br>- Per Second: " + f.format(player.getPerSecond()) +
				"<br>- Spent: " + calculateSpent() +
				"<br><br>Buildings: " +
					player.getBuildingList().getTotalOwned();

		if (player.hasCheated()) {
			main += "<br><br>Cheated: Yes";
			if (player.isFunRuined()) {
				main += "<br>- This player also ruined the fun :(";
			}
		}

		JLabel mainText = new JLabel();
		mainText.setText(main + "</html>");
		mainText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

		window.add(header, BorderLayout.NORTH);
		window.add(mainText, BorderLayout.CENTER);
		window.pack();
	}

	/**
	 * Shows the form.
	 */
	public void show() {
		window.setVisible(true);
	}

	/**
	 * Calculates how much this player spent.
	 * @return Spent amount.
	 */
	private long calculateSpent() {
		return (long)Math.ceil(player.getTotalCoffees() - player.getCoffees());
	}
}
