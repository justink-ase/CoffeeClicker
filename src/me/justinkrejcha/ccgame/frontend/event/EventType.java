package me.justinkrejcha.ccgame.frontend.event;

/**
 * Event types.
 * @author Justin
 * @since 5/21/2015 9:47 PM
 */
public enum EventType {
	/**
	 * Represents someone pressing the Info button. This is only on the main
	 * game screen.
	 */
	INFO,
	/**
	 * Represents someone pressing the Load button, either on the main menu
	 * or in the main game.
	 */
	LOAD,
	/**
	 * Represents someone pressing the Save button. This is only on the main
	 * game screen.
	 */
	SAVE,
	/**
	 * Represents someone pressing the Start button. This is only on the menu
	 * screen.
	 */
	START,
	/**
	 * Represents someone pressing the Quit button. This is only on the menu
	 * screen.
	 */
	QUIT,
}
