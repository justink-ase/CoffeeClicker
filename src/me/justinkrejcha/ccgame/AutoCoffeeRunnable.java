package me.justinkrejcha.ccgame;

public class AutoCoffeeRunnable implements Runnable {
	private CoffeeGame game;
	private long delay;
	
	/**
	* Create a new CpS runnable
	* @param game  The game to use
	*/
	public AutoCoffeeRunnable(CoffeeGame game) {
		this.game = game;
		this.delay = 1000 / game.getFps();
	}
	
	/**
	* The method that the CpS task executes when it's started.
	*/
	public void run() {
		while (!Thread.interrupted()) {
			// delay divided by the CpS
			CoffeePlayer p = game.getPlayer();
			p.add(p.getPerSecond() / delay);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
}