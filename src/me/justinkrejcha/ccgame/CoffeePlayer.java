package me.justinkrejcha.ccgame;

import me.justinkrejcha.Util;
import me.justinkrejcha.exceptions.NotImplementedException;
import me.justinkrejcha.game.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
* The player class for the game. This class contains all buildings, and other
* data used for the game.
*/
public class CoffeePlayer extends Player {
	private String name;
	private boolean cheated;
	private boolean funRuined;
	private double coffees;
	private double totalCoffees;
	private double perSecond;
	private BuildingList buildings;
	
	public CoffeePlayer() {
		this(null);
	}
	
	public CoffeePlayer(String name) {
		this(name, false, false, 0.0, 0.0, 0.0,
				CoffeeGame.createDefaultBuildings());
	}
	
	public CoffeePlayer(String name, boolean cheated, boolean funRuined,
	                    double coffees, double totalCoffees, double perSecond,
						BuildingList buildings) {
		if (name != null && name.trim().equals("")) {
			name = null;
		}
		this.name = name;
		this.cheated = cheated;
		this.funRuined = funRuined;
		this.coffees = coffees;
		this.totalCoffees = totalCoffees;
		this.perSecond = perSecond;
		this.buildings = buildings;
		doCheatCheck();
	}
	
	public double getCoffees() {
		return coffees;
	}
	
	public double getTotalCoffees() {
		return totalCoffees;
	}
	
	public double getPerSecond() {
		return perSecond;
	}
	
	/**
	* Checks if cheated, and then returns that value. This should be called even
	* within the class instead of directly accessing the boolean variable.
	* @return If this game has been cheated on.
	*/
	public boolean hasCheated() {
		doCheatCheck();
		return cheated;
	}

	public boolean isFunRuined() {
		return funRuined;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BuildingList getBuildingList() {
		return buildings;
	}
	
	public void recalculateCpS() {
		perSecond = buildings.getTotalBonus();
		doCheatCheck();
	}
	
	public void add(double amount) {
		totalCoffees += amount;
		coffees += amount;
		doCheatCheck();
	}
	
	public void increment() {
		add(1.0);
	}
	
	public boolean buy(Building b) {
		if (!canBuy(b)) return false;
		double price = b.getPrice();
		b.increment();
		coffees -= price;
		recalculateCpS();
		return true;
	}
	
	public boolean canBuy(Building b) {
		return coffees >= b.getPrice() || funRuined;
	}
	
	private void doCheatCheck() {
		cheated = Util.any(cheated, funRuined, coffees > totalCoffees,
				perSecond > totalCoffees, totalCoffees < 0, coffees < 0,
				perSecond < 0);
	}
	
	public String toString() {
		return getClass().getName() + ": [name: " + getName() + ", coffees: " + 
				coffees + ", total: " + totalCoffees + ", cps: " + perSecond + 
				", cheated: " + hasCheated() + "]";
	}
	
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof CoffeePlayer)) {
			return false; //null or not this class
		}
		CoffeePlayer compare = (CoffeePlayer) obj;
		return Util.all(compare.coffees == coffees, 
						compare.totalCoffees == totalCoffees, 
						compare.perSecond == perSecond,
						compare.name.equals(name),
						compare.buildings.equals(buildings));
	}

	/**
	 * Creates a CoffeePlayer object from a file.
	 * @param file The file to load.
	 * @return A CoffeePlayer with stats loaded.
	 * @throws IOException If the file doesn't exist or cannot be loaded for
	 * some other reason (permissions, etc)
	 */
	public static CoffeePlayer load(Path file) throws IOException {
		byte[][] data = Util.splitByteArray(Files.readAllBytes(file),
				CoffeeGame.SEPARATOR);
		// Magic numbers. Verifies whether this is actually a save file for
		// the game or not.
		if (data.length < 6 || data[0][0] != 0x43 || data[0][1] != 0x43) {
			throw new IllegalStateException("Not a valid save file");
		}

		String name = new String(data[1], StandardCharsets.UTF_8);
		BuildingList buildings = CoffeeGame.createDefaultBuildings();

		if (data.length < buildings.size() + 7) {
			throw new IllegalStateException("Not a valid save file");
		}
		for (int i = 0; i < buildings.size(); i++) {
			buildings.get(i).setAmount(ByteBuffer.wrap(data[i + 7]).getInt());
		}

		return new CoffeePlayer(name, data[2][0] != (byte)0x00,
				data[6][0] == (byte)0xFF,
				ByteBuffer.wrap(data[3]).getDouble(),
				ByteBuffer.wrap(data[4]).getDouble(),
				ByteBuffer.wrap(data[5]).getDouble(), buildings);
	}

	public void save(Path file) throws IOException {
		byte[] header = new byte[]{0x43, 0x43, 0x00, CoffeeGame.SEPARATOR};
		byte[] nameBytes = new byte[0];

		if (name != null) {
			nameBytes = name.getBytes(StandardCharsets.UTF_8);
		}
		/*
		Sep. byte (1) + cheated (1) + sep. byte (1) + 3 doubles (24) + 3 sep.
		bytes (3). Add building ct. * 5) + End byte (1) + padding (1)
		 */

		//Double length is 8. 9 is needed because of the separator byte.
		int length = 33 + (buildings.size() * 5);
		ByteBuffer bb = ByteBuffer.allocate(length);
		bb.put(CoffeeGame.SEPARATOR);
		if (hasCheated()) {
			bb.put((byte)0xFF);
		} else {
			bb.put((byte)0x00);
		}
		bb.put(CoffeeGame.SEPARATOR);
		bb.putDouble(coffees);
		bb.put(CoffeeGame.SEPARATOR);
		bb.putDouble(totalCoffees);
		bb.put(CoffeeGame.SEPARATOR);
		bb.putDouble(perSecond);
		bb.put(CoffeeGame.SEPARATOR);
		if (funRuined) {
			bb.put((byte)0xFF);
		} else {
			bb.put((byte)0x00);
		}
		bb.put(CoffeeGame.SEPARATOR);

		for (Building b : buildings.getAllBuildings()) {
			bb.putInt(b.getAmount());
			bb.put(CoffeeGame.SEPARATOR);
		}
		bb.put(CoffeeGame.END_MARKER);
		// combine data of all of the arrays
		byte[] data = Util.combineByteArray(header, nameBytes, bb.array());
		Files.write(file, data); // write all data
	}
}