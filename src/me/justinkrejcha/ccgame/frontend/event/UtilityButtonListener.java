package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.CoffeeGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO: Implement + Javadoc
 * @author Justin
 * @since 5/21/2015 9:46 PM
 */
public class UtilityButtonListener implements ActionListener {
	private CoffeeGame game;
	private EventType eventType;

	/**
	 * Creates a new utility button listener.
	 * @param game      The game
	 * @param eventType The event type, which changes what this button does.
	 */
	public UtilityButtonListener(CoffeeGame game, EventType eventType) {
		this.game = game;
		this.eventType = eventType;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (eventType) {
			case INFO:

				break;
			case LOAD:

				break;
			case SAVE:

				break;
		}
	}
}
