package me.justinkrejcha.ccgame.frontend;

import me.justinkrejcha.ccgame.*;
import me.justinkrejcha.ccgame.frontend.event.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Justin
 * @since 5/20/2015 5:02 PM
 */
public class CoffeeGameForm {
	private CoffeeGame game;
	private Thread textUpdater;
	private Thread buildingUpdater;

	private static String LOADING_TEXT = "Loading...";
	private static String IMAGE_PATH = "H:/AP Comp Sci/Final/Coffee Clicker Project/src/data/coffee.png";
	//private static String IMAGE_PATH = "/data/coffee.png";

	private JFrame window;
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel centerPanel;
	private JPanel leftEastPanel;
	
	private JLabel nameLabel;
	private JLabel countLabel;
	private JLabel cpsLabel;
	private JLabel coffeeImgLabel;

	private java.util.List<JButton> purchaseButtons;
	private java.util.List<JLabel> buildingLabels;

	public CoffeeGameForm(CoffeeGame game) {
		this.game = game;
		initializeForm(); // put objects on form and set everything up
	}

	private void initializeForm() {
		window = new JFrame("Coffee Clicker");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setSize(new Dimension(500, 400));
		window.setLocation(new Point(200, 400));
		window.setLayout(new BorderLayout());

		northPanel = new JPanel(new GridLayout(3, 0));

		nameLabel = new JLabel(game.getPlayer().getName() + "'s Coffee Shop");
		nameLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

		countLabel = new JLabel(LOADING_TEXT);
		countLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));

		cpsLabel = new JLabel(LOADING_TEXT);
		cpsLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 16));

		textUpdater = new Thread(new TextUpdaterRunnable(game, countLabel,
				cpsLabel));

		initializeBuildingsPanel();
		
		buildingUpdater = new Thread(new BuildingUpdaterRunnable(game,
				purchaseButtons, buildingLabels));

		coffeeImgLabel = new JLabel();
		coffeeImgLabel.setIcon(new ImageIcon(IMAGE_PATH));
		coffeeImgLabel.addMouseListener(
				new CoffeeClickListener(game.getPlayer()));

		if (game.getPlayer().getName() != null) {
			northPanel.add(nameLabel);
		}
		northPanel.add(countLabel);
		northPanel.add(cpsLabel);

		centerPanel = new JPanel();
		centerPanel.add(coffeeImgLabel);

		window.add(northPanel, BorderLayout.NORTH);
		window.add(centerPanel, BorderLayout.CENTER);
		window.add(leftEastPanel, BorderLayout.EAST);
		window.add(eastPanel, BorderLayout.EAST);
	}

	private void initializeBuildingsPanel() {
		purchaseButtons = new ArrayList<JButton>();
		buildingLabels = new ArrayList<JLabel>();
		
		CoffeePlayer player = game.getPlayer();
		BuildingList buildings = player.getBuildingList();

		for (Building b : buildings.getAllBuildings()) {
			JButton button = new JButton(LOADING_TEXT);
			button.addActionListener(new BuildingPurchaseListener(player, b,
					button));
			purchaseButtons.add(button);
			buildingLabels.add(new JLabel("0"));
		}
		
		int size = buildings.size();

		eastPanel = new JPanel(new GridLayout(size, 1));
		leftEastPanel = new JPanel(new GridLayout(size, 1));
		for (int i = 0; i < buildings.size(); i++) {
			leftEastPanel.add(buildingLabels.get(i));
			eastPanel.add(purchaseButtons.get(i));
		}
	}

	public void show() {
		window.setVisible(true);
		textUpdater.start();
		buildingUpdater.start();
	}

	public void close() {
		window.setVisible(false);
		textUpdater.interrupt();
		buildingUpdater.interrupt();
	}
}
