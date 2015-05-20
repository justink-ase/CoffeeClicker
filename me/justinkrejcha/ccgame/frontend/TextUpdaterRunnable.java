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
	* @param delay The delay (usually 1000 / FPS)
	*/
	public TextUpdaterRunnable(CoffeeGame game, JLabel label, JLabel cpsLabel, long delay) {
		this.game = game;
		this.label = label;
		this.cpsLabel = cpsLabel;
		this.delay = delay;
	}
	
	/**
	* The method that the CpS task executes when it's started.
	*/
	public void run() {
		CoffeePlayer p = game.getPlayer();
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