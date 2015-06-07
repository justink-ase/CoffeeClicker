package me.justinkrejcha.ccgame.frontend;


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
		new MainMenuForm();
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
			//Use the default L&F if there is a problem with it. There should
			// be no cases where this happens, but catching these exceptions
			// is required.
   		}
	}
}