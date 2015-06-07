package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.Util;
import me.justinkrejcha.ccgame.frontend.event.EventType;
import me.justinkrejcha.ccgame.frontend.event.MainMenuLabelListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Justin
 * @since 6/6/2015 6:36 PM
 */
public class MainMenuForm {
	private JFrame window;

	private static Color BG_COLOR = new Color(0, 0, 51);

	public MainMenuForm() {
		window = new JFrame("Main Menu");
		window.getContentPane().setBackground(BG_COLOR);
		window.setLayout(new GridBagLayout());
		window.setSize(new Dimension(500, 470));
		window.setLocation(Util.getCenterScreen(window));
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel startLabel = new JLabel("Start");
		JLabel loadLabel = new JLabel("Load");
		JLabel quitLabel = new JLabel("Quit");

		setLabelProperties(startLabel, EventType.START);
		setLabelProperties(loadLabel, EventType.LOAD);
		setLabelProperties(quitLabel, EventType.QUIT);

		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(BG_COLOR);
		panel.add(startLabel);
		panel.add(loadLabel);
		panel.add(quitLabel);

		GridBagConstraints c = new GridBagConstraints();

		JLabel title = new JLabel("Coffee Clicker");
		title.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 36));
		title.setForeground(Color.WHITE);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;

		window.add(title, c);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridy = 1;
		c.ipady = 40;
		c.ipadx = 80;

		window.add(panel, c);

		for (int i = 0; i < 3; i++) {
			if (i == 1) continue;
			c.gridx = i;
			for (int j = 1; j < 2; j++) {
				c.gridy = j;
				JPanel p = new JPanel();
				p.setBackground(BG_COLOR);
				window.add(p, c);
			}
		}
		window.setVisible(true);
	}

	public void close() {
		window.setVisible(false);
	}

	private void setLabelProperties(JLabel label, EventType type) {
		label.setForeground(Color.WHITE);
		label.setBackground(Color.WHITE);
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
		label.setOpaque(false);
		label.addMouseListener(new MainMenuLabelListener(this, type));
	}

	/**
	 * Shows a dialog box asking the user to enter their name.
	 * @return Name stripped of leading and trailing whitespace or null if blank
	 * or the cancel button was selected.
	 */
	public static String getName() {
		String name = "";
		name = JOptionPane.showInputDialog(null, "Please enter your name " +
						"(blank for no name):",
				"Coffee Clicker", JOptionPane.DEFAULT_OPTION);
		if (name != null) {
			name = name.trim();
			if (name.equals("")) name = null;
		}
		return name;
	}
}
