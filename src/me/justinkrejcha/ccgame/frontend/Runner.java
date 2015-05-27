package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;


import javax.swing.*; // graphics! excitement!
import java.io.File;

public class Runner {
	
	public static void main(String[] args) {
		setSystemLAF();

		if (!resourcesExist()) {
			JOptionPane.showMessageDialog(null, "Could not find resources " +
					"required for the game to run.\nThe game will now exit.",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String name = getName(); //TODO: Use getName() instead of test value
		//String name = "A random tester";
		CoffeeGame game = new CoffeeGame(new CoffeePlayer(name));
		
		CoffeeGameForm form = new CoffeeGameForm(game);
		form.show();
		game.start();
	}
	
	private static String getName() {
		String name = "";
		name = JOptionPane.showInputDialog(null, "Please enter your name " +
						"(blank for no name):",
					"Coffee Clicker", JOptionPane.DEFAULT_OPTION);
		if (name != null && name.equals("")) name = null;
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
	* Taken from the tutorial to set the look and feel.<br>
	* See: https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
	*/
	private static void setSystemLAF() {
    	try {
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (UnsupportedLookAndFeelException e) {

   		} catch (ClassNotFoundException e) {

    	} catch (InstantiationException e) {

    	} catch (IllegalAccessException e) {

		}
		// Use the default if it somehow fails (which it shouldn't)...
	}
}