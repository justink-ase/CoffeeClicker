package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;

import java.text.DecimalFormat;

import javax.swing.*;

public class TextUpdaterRunnable implements Runnable {
	private CoffeePlayer p;
	private JLabel label;
	private JLabel cpsLabel;
	private long delay;

	/**
	 * Creates a new text updater runnable
	 * @param game     Game to use
	 * @param label    Text saying the amount of coffees the player has
	 * @param cpsLabel Text saying the amount of CpS the player has
	 */
	public TextUpdaterRunnable(CoffeeGame game, JLabel label, JLabel cpsLabel) {
		this.p = game.getPlayer();
		this.label = label;
		this.cpsLabel = cpsLabel;
		this.delay = 1000 / game.getFps();
	}
	
	/**
	 * The method that the CpS task executes when it's started.
	 */
	public void run() {
		DecimalFormat f = new DecimalFormat("#.#");
		while (!Thread.interrupted()) {
			label.setText("Coffees: " + f.format(p.getCoffees()));
			cpsLabel.setText("per second: " + f.format(p.getPerSecond()));
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}