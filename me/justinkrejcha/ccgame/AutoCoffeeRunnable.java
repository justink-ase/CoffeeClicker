package me.justinkrejcha.ccgame;

public class AutoCoffeeRunnable implements Runnable {
	private CoffeeGame game;
	private long delay;
	
	/**
	* Create a new CpS runnable
	* @param game  The game to use
	* @param delay The delay (usually 1000 / FPS)
	*/
	public AutoCoffeeRunnable(CoffeeGame game, long delay) {
		this.game = game;
		this.delay = delay;
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