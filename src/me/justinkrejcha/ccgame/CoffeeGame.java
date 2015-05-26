package me.justinkrejcha.ccgame;

import java.util.List;
import java.util.ArrayList;

import me.justinkrejcha.game.*;

public class CoffeeGame extends SinglePlayerGame {
	private Thread autoClickerThread;
	private int fps;
	
	public static String BUTTON_TEXT = "Buy 1 {0} ({1} coffees)";
	public static String HOVER_TEXT = "You have {0} of the {1} building";

	public static byte END_MARKER = 0x03;
	public static byte SEPARATOR = 0x1D;

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
		l.add(new Building("Stirrer", 15, 0.1));
		l.add(new Building("Coffee Pot", 100, 0.5));
		l.add(new Building("Brewery", 600, 5));
		l.add(new Building("Factory", 3250, 15));
		l.add(new Building("Cocoa Plant", 11000, 50));
		return new BuildingList(l);
	}

	public void load(java.nio.file.Path file) throws java.io.IOException {
		stop();
		setPlayer(CoffeePlayer.load(file));
		start();
	}

	public void save(java.nio.file.Path file) throws java.io.IOException {
		getPlayer().save(file);
	}
}