package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;

import java.awt.*; // layout stuff
import javax.swing.*; // graphics! excitement!

public class Runner {

	private static Thread textUpdater;
	
	public static void main(String[] args) {
		setSystemLAF();
		String name = getName();
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
	* Sets the system look and feel.<br>
	* Taken from the tutorial to set the look and feel.<br>
	* See: https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
	*/
	private static void setSystemLAF() {
    	try {
            // Set System L&F
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (UnsupportedLookAndFeelException e) {
       		
   		} catch (ClassNotFoundException e) {
       		
    	} catch (InstantiationException e) {
       
    	} catch (IllegalAccessException e) {
     	  
		}
		// Use the default if it somehow fails (which it shouldn't)...
	}
}