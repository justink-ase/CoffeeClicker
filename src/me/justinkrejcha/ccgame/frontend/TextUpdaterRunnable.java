package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;

import java.text.DecimalFormat;

import javax.swing.*;

public class TextUpdaterRunnable implements Runnable {
	private CoffeeGame game;
	private JLabel label;
	private JLabel cpsLabel;
	private long delay;
	
	/**
	* Create a new CpS runnable
	* @param game  The game to use
	*/
	public TextUpdaterRunnable(CoffeeGame game, JLabel label, JLabel cpsLabel) {
		this.game = game;
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
			CoffeePlayer p = game.getPlayer();
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