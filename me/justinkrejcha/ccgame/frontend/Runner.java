package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;

import java.awt.*; // layout stuff
import javax.swing.*; // graphics! excitement!

public class Runner {

	private static Thread textUpdater;
	
	public static void main(String[] args) {
		String name = getName();
		setSystemLAF();
		CoffeeGame game = new CoffeeGame(new CoffeePlayer(name));
		
		JFrame window = createWindow(game);
		window.setVisible(true);
		game.start();
		textUpdater.start();
	}
	
	private static String getName() {
		String name = "";
		while (name.equals("")) {
			name = JOptionPane.showInputDialog(null, "Please enter your name:",
					"Coffee Clicker", JOptionPane.DEFAULT_OPTION).trim();
		}
		return name;
	}
	
	private static JFrame createWindow(CoffeeGame game) {
		JFrame window = new JFrame("Coffee Clicker");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setSize(new Dimension(500, 400));
		window.setLocation(new Point(20, 40));
		window.setLayout(new FlowLayout()); //TODO: Change!
		
		JLabel nameLabel = new JLabel(game.getPlayer().getName() + "'s coffee shop");
		nameLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		
		JLabel countLabel = new JLabel("Loading...");
		countLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		
		JLabel cpsLabel = new JLabel("Loading...");
		cpsLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 16));
		
		window.add(nameLabel);
		window.add(countLabel);
		window.add(cpsLabel);
		
		textUpdater = new Thread(new TextUpdaterRunnable(game, countLabel, cpsLabel, 1000 / 33));
		
		return window;
	}
	
	/**
	* Sets the system look and feel.
	* Taken from the tutorial to set the look and feel.
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