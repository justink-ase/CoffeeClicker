package me.justinkrejcha.ccgame.frontend.event;

import me.justinkrejcha.ccgame.CoffeeGame;
import me.justinkrejcha.ccgame.CoffeePlayer;
import me.justinkrejcha.ccgame.frontend.CoffeeGameForm;
import me.justinkrejcha.ccgame.frontend.MainMenuForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Hover effects and other ones.
 * @author Justin
 * @since 6/6/2015 6:56 PM
 */
public class MainMenuLabelListener implements MouseListener {
	private MainMenuForm form;
	private EventType eventType;

	public MainMenuLabelListener(MainMenuForm form, EventType eventType) {
		this.form = form;
		this.eventType = eventType;
	}

	/**
	 * Starts, loads, or quits based on what the user selects.
	 * @param e Event
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (eventType) {
			case LOAD:
				try {
					new CoffeeGameForm(null).show();
				} catch (RuntimeException ex) {
					return; // don't close form if this throws an exception
				}
				break;
			case START:
				String name = MainMenuForm.getName();
				CoffeeGame g = new CoffeeGame(new CoffeePlayer(name));
				g.start();
				new CoffeeGameForm(g).show();
				break;
			case QUIT:
				System.exit(0);
				break;
			default:
				throw new IllegalStateException("Unexpected EventType: " +
						eventType);
		}
		form.close();
	}

	/**
	 * Does nothing
	 * @param e Event
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Does nothing
	 * @param e Event
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 * @param e Event
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel component = (JLabel) e.getComponent();
		component.setForeground(Color.BLACK);
		component.setOpaque(true);
		component.repaint();
	}

	/**
	 * Invoked when the mouse exits a component.
	 * @param e Event
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		JLabel component = (JLabel) e.getComponent();
		component.setOpaque(false);
		component.setForeground(Color.WHITE);
		component.repaint();
	}
}
