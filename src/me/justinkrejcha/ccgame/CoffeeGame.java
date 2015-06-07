package me.justinkrejcha.ccgame;

import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

import me.justinkrejcha.game.*;

/**
 * Represents a game of Coffee Clicker.
 * @author Justin
 * @see me.justinkrejcha.game.SinglePlayerGame
 */
public class CoffeeGame extends SinglePlayerGame {
	private Thread autoClickerThread;
	
	public static String BUTTON_TEXT = "Buy 1 {0} ({1} coffees)";

	public static byte END_MARKER = 0x03;
	public static byte SEPARATOR = 0x1D;

	/**
	 * Constructor for a new game with no name and empty stats. Usually only
	 * used for testing.
	 */
	public CoffeeGame() {
		this(new CoffeePlayer());
	}

	/**
	 * Constructor for a new game with a player set and the default frames
	 * per second (30) This is equivalent to
	 * {@link #CoffeeGame(CoffeePlayer, int)} with an fps set to 30.
	 * @param p Player object to use
	 */
	public CoffeeGame(CoffeePlayer p) {
		this(p, 30);
	}

	/**
	 * Constructor for a new game which sets both the FPS and frames per
	 * second.
	 * @param p Player to use
	 * @param fps Frames per second
	 */
	public CoffeeGame(CoffeePlayer p, int fps) {
		this.setPlayer(p);
		this.setFps(fps);
	}

	/**
	 * {@inheritDoc}
	 */
	public CoffeePlayer getPlayer() {
		return (CoffeePlayer) super.getPlayer();
	}

	/**
	 * {@inheritDoc}
	 */
	public void start() {
		if (autoClickerThread != null) {
			autoClickerThread.interrupt(); 
		}
		this.autoClickerThread = new Thread(new AutoCoffeeRunnable(this),
				"Auto Clicker Thread");
		autoClickerThread.start();
		setGameRunning(true);
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
		if (autoClickerThread != null) {
			autoClickerThread.interrupt();
		}
		setGameRunning(false);
	}

	/**
	 * Creates a building list based on the default buildings with a count of
	 * 0 for each building. This is used to help load the buildings.
	 * @return A new building list.
	 */
	public static BuildingList createDefaultBuildings() {
		List<Building> l = new ArrayList<>();
		l.add(new Building("Stirrer", 15, 0.1));
		l.add(new Building("Coffee Pot", 100, 0.5));
		l.add(new Building("Brewery", 600, 5));
		l.add(new Building("Factory", 3250, 15));
		l.add(new Building("Cocoa Plant", 11000, 50));
		return new BuildingList(l);
	}

	/**
	 * Loads a player file, replacing the current player. This stops the game
	 * (a call to {@link #stop()}), sets the player to the loaded one and
	 * restarts the game (a call to {@link #start()}.
	 * @param file File to load from.
	 * @throws java.io.IOException This method may throw an IOException if
	 * there are permissions problems reading the file, or another error
	 * occurs. An error box should be shown, informing the user of this.
	 * @see CoffeePlayer#load(Path)
	 */
	public void load(java.nio.file.Path file) throws java.io.IOException {
		stop();
		setPlayer(CoffeePlayer.load(file));
		start();
	}
	
	/**
	 * Saves the player's player data.
	 * @param file The file to save it to.
	 * @throws java.io.IOException if there is an IO error
	 * @see CoffeePlayer#save(Path)
	 */
	public void save(java.nio.file.Path file) throws java.io.IOException {
		getPlayer().save(file);
	}
}