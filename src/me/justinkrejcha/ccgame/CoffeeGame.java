package me.justinkrejcha.ccgame;

import java.util.List;
import java.util.ArrayList;

import me.justinkrejcha.game.*;

public class CoffeeGame extends SinglePlayerGame {
	private Thread autoClickerThread;
	private int fps;

	/**
	* Constructor for a new game.
	*/
	public CoffeeGame() {
		this(new CoffeePlayer());
	}

	public CoffeeGame(CoffeePlayer p) {
		this(p, 30);
	}
	
	public CoffeeGame(CoffeePlayer p, int fps) {
		this.setPlayer(p);
		this.fps = fps;
		this.autoClickerThread = new Thread(new AutoCoffeeRunnable(this));
	}
	
	public CoffeePlayer getPlayer() {
		return (CoffeePlayer) super.getPlayer();
	}

	public int getFps() {
		return fps;
	}
	
	public void start() {
		autoClickerThread.start();
		setGameRunning(true);
	}
	
	public void stop() {
		autoClickerThread.interrupt(); //send interrupt to it
		setGameRunning(false);
	}
	
	public static BuildingList createDefaultBuildings() {
		List<Building> l = new ArrayList<Building>();
		l.add(new Building(0, "Stirrer", 15, 0.1));
		l.add(new Building(1, "Coffee Pot", 100, 0.5));
		l.add(new Building(2, "Brewery", 600, 5));
		l.add(new Building(3, "Factory", 3250, 15));
		l.add(new Building(4, "Cocoa Plant", 11000, 50));
		return new BuildingList(l);
	}
}