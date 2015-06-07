package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.Util;
import me.justinkrejcha.ccgame.*;
import me.justinkrejcha.ccgame.frontend.event.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;

/**
 * Form which handles most of the GUI components of the game.
 * @author Justin
 * @since 5/20/2015 5:02 PM
 */
public class CoffeeGameForm {
	private CoffeeGame game;
	private Thread textUpdater;
	private Thread buildingUpdater;

	private static String LOADING_TEXT = "Loading...";
	private static String RES_DIRECTORY = System.getProperty("user.dir") +
			"\\resources\\";
	private static String IMAGE_PATH = RES_DIRECTORY + "coffee.png";

	private static String UNSAVED_WARNING = "Some progress has not been saved" +
			". Would you like to save your progress now?";

	private static String LOAD_ERROR = "An error occurred while loading your " +
			"save file. Please try again with another save file.";

	private JFrame window;
	private JPanel northPanel;
	private JPanel eastPanel;

	public CoffeeGameForm(CoffeeGame game) {
		window = new JFrame("Coffee Clicker");
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.setResizable(false);
		window.setSize(new Dimension(500, 470));
		window.setLocation(Util.getCenterScreen(window));
		window.setLayout(new BorderLayout());

		this.game = game;
		if (this.game == null) {
			this.game = new CoffeeGame(new CoffeePlayer());
			if (!load()) {
				throw new RuntimeException("User cancelled loading.");
				//This is probably a bad idea, but the alternative would be to
				//restructure how all the loading is done, and this works.
			}
		}
		initializeForm(); // put objects on form and set everything up
	}

	/**
	 * Initializes the form and loads all components of it.
	 */
	private void initializeForm() {
		initializeBuildingsPanel();

		JLabel coffeeImgLabel = new JLabel();
		coffeeImgLabel.setIcon(new ImageIcon(IMAGE_PATH));
		coffeeImgLabel.addMouseListener(
				new CoffeeClickListener(game));


		JButton infoButton = new JButton("Info");
		infoButton.addActionListener(new UtilityButtonListener(game, this,
				EventType.INFO));

		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new UtilityButtonListener(game, this,
				EventType.LOAD));

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new UtilityButtonListener(game, this,
				EventType.SAVE));

		JPanel centerPanel = new JPanel();
		centerPanel.add(coffeeImgLabel);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
		bottomPanel.add(infoButton);
		bottomPanel.add(loadButton);
		bottomPanel.add(saveButton);

		initializeTopPanel();

		window.add(centerPanel, BorderLayout.CENTER);
		window.add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * This method redraws the top panel with the name and coffees (and per
	 * second). This is so we can put a re-insert or remove a name when it's
	 * running if we need to.
	 */
	private void initializeTopPanel() {
		if (textUpdater != null) {
			textUpdater.interrupt(); // stop auto updating
		}
		if (northPanel != null) {
			window.remove(northPanel);
		}

		northPanel = new JPanel(new GridLayout(3, 0));

		JLabel nameLabel = new JLabel(game.getPlayer().getName() + "'s Coffee Shop");
		nameLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

		JLabel countLabel = new JLabel(LOADING_TEXT);
		countLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

		JLabel cpsLabel = new JLabel(LOADING_TEXT);
		cpsLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 16));

		if (game.getPlayer().getName() != null) {
			northPanel.add(nameLabel);
		}
		northPanel.add(countLabel);
		northPanel.add(cpsLabel);

		textUpdater = new Thread(new TextUpdaterRunnable(game, countLabel,
				cpsLabel), "Text Updater Thread");
		textUpdater.start();

		window.add(northPanel, BorderLayout.NORTH);
	}

	/**
	 * This method redraws the buildings panel (the prices and counts). This
	 * is so we can reset the counts to what they were when we load a save file.
	 */
	private void initializeBuildingsPanel() {
		if (buildingUpdater != null) {
			buildingUpdater.interrupt(); // stop auto updating
		}
		if (eastPanel != null) {
			window.remove(eastPanel);
		}

		java.util.List<JButton> purchaseButtons = new ArrayList<JButton>();
		java.util.List<JLabel> buildingLabels = new ArrayList<JLabel>();
		
		CoffeePlayer player = game.getPlayer();
		BuildingList buildings = player.getBuildingList();

		for (Building b : buildings.getAllBuildings()) {
			JButton button = new JButton(LOADING_TEXT);
			purchaseButtons.add(button);
			JLabel label = new JLabel("???");
			label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
			buildingLabels.add(label);

			button.addActionListener(new BuildingPurchaseListener(game, b,
					button, label));
		}

		int size = buildings.size();

		eastPanel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;

		for (int i = 0; i < size; i++) {
			c.gridy++;
			c.gridx = 0; //X = 0 on our grid.
			c.ipady = 0; //set extra height to 0

			eastPanel.add(buildingLabels.get(i), c); //add label
			c.gridx++; //increment X coordinate
			c.weightx = 0.5;
			c.ipady = 40; //height
			eastPanel.add(purchaseButtons.get(i), c);
			c.gridy++; //increment row
		}

		buildingUpdater = new Thread(new BuildingUpdaterRunnable(
				game.getPlayer(), purchaseButtons, buildingLabels),
				"Building Updater Thread");
		buildingUpdater.start();

		window.add(eastPanel, BorderLayout.EAST);
	}
	
	/**
	 * Saves the game. Optionally can show a warning.
	 * @param ifShowWarning Only save if 'Yes' is clicked on the warning dialog.
	 * @return If we should continue loading/exiting.
	 */
	public boolean save(boolean ifShowWarning) {
		if (ifShowWarning) {
			int result = showUnsavedProgressWarning();
			if (result == JOptionPane.CANCEL_OPTION) {
				return false;
			} else if (result == JOptionPane.NO_OPTION) {
				return true;
			}
		}
		JFileChooser f = new JFileChooser();

		if (f.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
			try {
				game.save(f.getSelectedFile().toPath());
			} catch (IOException e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Loads a game from file if the user wants to discard unsaved data. This
	 * method will show an error dialog box if loading fails for some reason
	 * other than user cancellation.
	 * @return Whether loading of a save file occurred successfully.
	 */
	public boolean load() {
		JFileChooser f = new JFileChooser();
		if (f.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
			try {
				game.load(f.getSelectedFile().toPath());
				initializeTopPanel();
				initializeBuildingsPanel();
				return true;
			} catch (BufferUnderflowException |
					IllegalStateException |
					IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(window, LOAD_ERROR, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return false;
	}

	/**
	 * Shows the form.
	 */
	public void show() {
		window.setVisible(true);
	}

	/**
	 * Shows the warning dialog about unsaved progress.
	 * @return An integer depending on which option was selected. Options
	 * will be either {@link JOptionPane#YES_OPTION},
	 * {@link JOptionPane#NO_OPTION} or {@link JOptionPane#CANCEL_OPTION}.
	 */
	public int showUnsavedProgressWarning() {
		int option = JOptionPane.showConfirmDialog(window, UNSAVED_WARNING,
					"Coffee Clicker", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
		if (option == JOptionPane.CLOSED_OPTION) {
			option = JOptionPane.CANCEL_OPTION;
			//set it to cancel if they closed the window
		}
		return option;
	}

	/**
	 * Closes the form and stops any threads.
	 */
	public void close() {
		if (!save(true)) return;
		window.setVisible(false);
		textUpdater.interrupt();
		buildingUpdater.interrupt();
	}
}
