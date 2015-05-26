package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.CoffeeGame;
import me.justinkrejcha.ccgame.frontend.CoffeeGameForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO: Implement
 * @author Justin
 * @since 5/21/2015 9:46 PM
 */
public class UtilityButtonListener implements ActionListener {
	private CoffeeGame game;
	private EventType eventType;
	private CoffeeGameForm form;

	/**
	 * Creates a new utility button listener.
	 * @param game      The game
	 * @param eventType The event type, which changes what this button does.
	 */
	public UtilityButtonListener(CoffeeGame game, CoffeeGameForm form,
	                             EventType eventType) {
		this.game = game;
		this.form = form;
		this.eventType = eventType;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (eventType) {
			case INFO:
				
				break;
			case LOAD:
				if (!form.save(true)) return;
				form.load();
				break;
			case SAVE:
				form.save(false);
				break;
		}
	}
}
