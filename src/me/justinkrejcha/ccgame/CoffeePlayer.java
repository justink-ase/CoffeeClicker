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
		return coffees >= b.getPrice();
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
	public static CoffeePlayer load(String file) throws IOException {
		Path dataFile = new File(file).toPath();
		byte[][] data = Util.splitByteArray(Files.readAllBytes(dataFile),
				CoffeeGame.SEPARATOR);
		// Magic numbers. Verifies whether this is actually a save file for
		// the game or not.
		if (data[0][0] != 0x33 || data[0][1] != 0x33) {
			//TODO: Throw an exception here!
		}

		String name = new String(data[1], StandardCharsets.UTF_8);
		BuildingList buildings = CoffeeGame.createDefaultBuildings();

		for (int i = 7; i < buildings.size() + 7; i++) {
			buildings.get(i - 7).setAmount(ByteBuffer.wrap(data[i]).getInt());
		}

		return new CoffeePlayer(name, data[2][0] != (byte)0x00,
				data[6][0] == (byte)0xFF,
				ByteBuffer.wrap(data[3]).getDouble(),
				ByteBuffer.wrap(data[4]).getDouble(),
				ByteBuffer.wrap(data[5]).getDouble(), buildings);
	}

	public void save(String file) throws IOException {
		Path dataFile = new File(file).toPath();
		byte[] header = new byte[]{0x33, 0x33, 0x00, CoffeeGame.SEPARATOR};
		byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);

		//Cheated (1) + separator (1) + 3 doubles (24) + 3 separators (3)
		//Also add (building count * 4) + building count separators

		//Double length is 8. 9 is needed because of the separator byte.
		int length = 29 + (buildings.size() * 4) + buildings.size();
		ByteBuffer bb = ByteBuffer.allocate(length);
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
		Files.write(dataFile, data); // write all data
	}
}