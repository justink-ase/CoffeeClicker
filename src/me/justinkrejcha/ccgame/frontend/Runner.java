package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;


import javax.swing.*; // graphics! excitement!
import java.io.File;

public class Runner {
	/**
	 * Main entry point for the game. Environment variables are ignored.
	 * @param args Environment variables are unused.
	 */
	public static void main(String[] args) {
		setSystemLAF();

		if (!resourcesExist()) {
			JOptionPane.showMessageDialog(null, "Could not find resources " +
					"required for the game to run.\nThe game will now exit.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int selected = JOptionPane.showConfirmDialog(null, 
						"Would you like to load a save?", "Coffee Clicker",
						JOptionPane.YES_NO_OPTION);
							
		boolean loadSave = selected == JOptionPane.YES_OPTION;
							
		String name = null;
		if (!loadSave) {
			name = getName();
		}
		CoffeeGame game = new CoffeeGame(new CoffeePlayer(name));
		CoffeeGameForm form = new CoffeeGameForm(game);
		game.start();
		if (loadSave) {
			if (!form.load()) {
				form.close();
				System.exit(0); // exit here
			}
		}
		form.show();
	}
	
	/**
	 * Shows a dialog box asking the user to enter their name.
	 * @return Name stripped of leading and trailing whitespace or null if blank
	 * or the cancel button was selected.
	 */
	private static String getName() {
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

	/**
	 * Returns true if all required resources exist.
	 *
	 * @return A boolean value indicating whether all of the required
	 * resources for the game exist.
	 */
	public static boolean resourcesExist() {
		File f = new File("resources");
		if (!f.exists() || !f.isDirectory()) return false;

		f = new File(f.getPath() + "\\coffee.png");
		return f.exists();
	}
	
	/**
	 * Sets the system look and feel, because in my opinion, the default Java
	 * one doesn't look that good...<br>
	 * Adapted from the tutorial to set the look and feel.<br>
	 * See: https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
	 */
	private static void setSystemLAF() {
    	try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (UnsupportedLookAndFeelException |
			    ClassNotFoundException |
			    InstantiationException |
			    IllegalAccessException e) {

   		}
		// Use the default if it somehow fails (which it shouldn't)...
	}
}