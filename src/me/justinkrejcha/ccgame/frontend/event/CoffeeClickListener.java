package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.CoffeeGame;
import me.justinkrejcha.ccgame.CoffeePlayer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Justin
 * @since 5/20/2015 7:48 PM
 */
public class CoffeeClickListener implements MouseListener {
	private CoffeeGame game;

	public CoffeeClickListener(CoffeeGame game) {
		this.game = game;
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed
	 * and released) on a component.<br>
	 *
	 * A workaround since we can't click on labels normally.<br>
	 * See: http://stackoverflow.com/a/5260577/2805120
	 *
	 * @param e Mouse event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		game.getPlayer().increment();
	}

	/**
	 * Does nothing.
	 * @param e Mouse event
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Does nothing.
	 * @param e Mouse event
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Does nothing.
	 * @param e Mouse event
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Does nothing.
	 * @param e Mouse event
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
