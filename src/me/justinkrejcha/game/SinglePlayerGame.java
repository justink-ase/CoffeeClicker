package me.justinkrejcha.game;

/**
 * Represents a game that is played by one player.
 */
public abstract class SinglePlayerGame {
	private int fps;
	private boolean gameRunning;
	private Player player;
	
	/**
	 * Gets the player user for the game.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Sets the player used for this game. This should only be called by a
	 * constructor or method to reset the game.
	 * @param player Player
	 */
	protected void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the frames per second.
	 * @return Frames per second of the game.
	 */
	public int getFps() {
		return fps;
	}

	/**
	 * Sets the frames per second of the game.
	 * @param fps Frames per second
	 */
	public void setFps(int fps) {
		this.fps = fps;
	}

	/**
	 * Gets if the game is running.
	 */
	public boolean isGameRunning() {
		return gameRunning;
	}
	
	/**
	 * Sets the state of if the game is running. This should only be called by
	 * the start() and stop() methods.
	 */
	protected void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	/**
	 * Starts the game.
	 */
	public abstract void start();

	/**
	 * Stops the game and performs any cleanup actions. This does not the
	 * prevent the game from being restarted.
	 */
	public abstract void stop();
}