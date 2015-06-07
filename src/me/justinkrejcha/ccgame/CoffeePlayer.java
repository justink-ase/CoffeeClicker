package me.justinkrejcha.ccgame;

import me.justinkrejcha.Util;
import me.justinkrejcha.game.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The player class for the game. This class contains all buildings, and data
 * used during game-play.
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
	
	protected void setName(String name) {
		this.name = name;
	}
	
	public BuildingList getBuildingList() {
		return buildings;
	}

	/**
	 * Recalculates the total amount of coffees per second based on the
	 * amount of buildings the user has. Also performs a cheat check.
	 */
	public void recalculateCpS() {
		perSecond = buildings.getTotalBonus();
		doCheatCheck();
	}

	/**
	 * Increments the amount of coffees by a specified amount.
	 * @param amount Amount to add
	 */
	public void add(double amount) {
		totalCoffees += amount;
		coffees += amount;
		doCheatCheck();
	}

	/**
	 * Adds 1 to the coffees. Equivalent to {@link #add(double)} with an
	 * amount of 1.0.
	 */
	public void increment() {
		add(1.0);
	}

	/**
	 * Buys a building if the value returned by {@link #canBuy(Building)}
	 * returns true.
	 * @param b Building to buy
	 * @return The value returned by {@link #canBuy(Building)}
	 */
	public boolean buy(Building b) {
		if (!canBuy(b)) return false;
		double price = b.getPrice();
		b.increment();
		coffees -= price;
		recalculateCpS();
		return true;
	}

	/**
	 * Checks whether the user can buy this building.
	 * @param building Building to check for
	 * @return If the player has enough coffees to buy the building or if the
	 * fun ruined flag is set.
	 */
	public boolean canBuy(Building building) {
		return coffees >= building.getPrice() || funRuined;
	}

	/**
	 * Performs checks to see if this game has had events occur that would be
	 * impossible to when playing via normal means. This check will actually
	 * run the fastest when the 'cheated' flag is already set.<br>
	 * This checks for and sets the cheated flag to true if any are true:<br>
	 * <ul>
	 *     <li>The cheated flag</li>
	 *     <li>The fun ruined flag</li>
	 *     <li>Coffee amount is more than the total amount</li>
	 *     <li>The CpS is more than the total amount</li>
	 *     <li>Coffees, total or per second is less than 0</li>
	 * </ul>
	 */
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

	/**
	 * Returns true if this is a CoffeePlayer object and these are the same
	 * object.
	 * @param obj The object to test.
	 * @return Equivalency of objects.
	 */
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
	 * Creates a CoffeePlayer object from a file.<br><br>
	 *
	 * <p>The file format for this can help us
	 * detect cheaters and people who try to use external tools to help them
	 * (not many would do so in this case, but still, it ruins the fun). But
	 * to those dedicated, there is a hidden switch that is only activated
	 * by manually editing their save file.</p>
	 * <p><b>The divider byte is 0x1D (ASCII group separator).</b></p>
	 *
	 * <p>The first two bytes must be 0x33 (the
	 * ASCII letter C). If this is not correct, the rest of the file should
	 * be ignored and an error should be shown. The next byte is the save
	 * file version. 0x00 is the first save file version (which is this
	 * one).</p>
	 * <p>A divider byte should be added here.
	 * The string containing the player's name should be encoded here.
	 * Another divider byte should be inserted here.</p>
	 * <p>The table below describes the next
	 * values:</p>
	 * <table width=606 border=1 bordercolor="#666" cellpadding=7 cellspacing=0>
	 * 	<col>
	 * 	<tr>
	 * 		<td width=590 valign=top bgcolor="#ccc">
	 * 			<p>Normal Data</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>0x00 if the current game has the cheated flag on it or, 0xFF
	 * 			otherwise*.</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>Divider byte</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>The amount of coffees this game has. What this does is it
	 * 			encodes the amount of coffees as a number. This is gotten by
	 * 			ByteBuffer's getDouble() and putDouble() methods.</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>Divider byte</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>The amount of total coffees. This is encoded/decoded in the
	 * 			same way the coffees are.</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>Divider byte</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>The amount of coffees per second. This is encoded and
	 * 			decoded in the same way the coffees are. This is only recalculated
	 * 			when a building is bought.</p>
	 * 		</td>
	 * 	</tr>
	 * </table>
	 * <p><br>
	 * </p>
	 * <p>After this, all of the amount of each
	 * building is encoded, with a divider byte in between each one.
	 * Following this is a 0x03 byte (signaling the end of the data). All
	 * data after this should be ignored by the game.</p>
	 * <p>* Implementations should treat any
	 * value other than 0x00 as cheated, but they should always save as
	 * 0xFF. The game should run a cheat check after loading.
	 * </p>
	 * <p><br>
	 * </p>
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

		// Instead of failing when there are more buildings than data for it,
		// we can set the value to 0. This makes it more future proof.
		for (int i = 7; i < data.length; i++) {
			buildings.get(i - 7).setAmount(ByteBuffer.wrap(data[i]).getInt());
		}

		return new CoffeePlayer(name, data[2][0] != (byte)0x00,
				data[6][0] == (byte)0xFF,
				ByteBuffer.wrap(data[3]).getDouble(),
				ByteBuffer.wrap(data[4]).getDouble(),
				ByteBuffer.wrap(data[5]).getDouble(), buildings);
	}

	/**
	 * Saves a file containing the save data for a player.
	 *
	 * <p>The file format for this can help us
	 * detect cheaters and people who try to use external tools to help them
	 * (not many would do so in this case, but still, it ruins the fun). But
	 * to those dedicated, there is a hidden switch that is only activated
	 * by manually editing their save file.</p>
	 * <p><b>The divider byte is 0x1D (ASCII group separator).</b></p>
	 *
	 * <p>The first two bytes must be 0x33 (the
	 * ASCII letter C). If this is not correct, the rest of the file should
	 * be ignored and an error should be shown. The next byte is the save
	 * file version. 0x00 is the first save file version (which is this
	 * one).</p>
	 * <p>A divider byte should be added here.
	 * The string containing the player's name should be encoded here.
	 * Another divider byte should be inserted here.</p>
	 * <p>The table below describes the next
	 * values:</p>
	 * <table width=606 border=1 bordercolor="#666" cellpadding=7 cellspacing=0>
	 * 	<col>
	 * 	<tr>
	 * 		<td width=590 valign=top bgcolor="#ccc">
	 * 			<p>Normal Data</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>0x00 if the current game has the cheated flag on it or, 0xFF
	 * 			otherwise*.</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>Divider byte</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>The amount of coffees this game has. What this does is it
	 * 			encodes the amount of coffees as a number. This is gotten by
	 * 			ByteBuffer's getDouble() and putDouble() methods.</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>Divider byte</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>The amount of total coffees. This is encoded/decoded in the
	 * 			same way the coffees are.</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>Divider byte</p>
	 * 		</td>
	 * 	</tr>
	 * 	<tr>
	 * 		<td>
	 * 			<p>The amount of coffees per second. This is encoded and
	 * 			decoded in the same way the coffees are. This is only recalculated
	 * 			when a building is bought.</p>
	 * 		</td>
	 * 	</tr>
	 * </table>
	 * <p><br>
	 * </p>
	 * <p>After this, all of the amount of each
	 * building is encoded, with a divider byte in between each one.
	 * Following this is a 0x03 byte (signaling the end of the data). All
	 * data after this should be ignored by the game.</p>
	 * <p>* Implementations should treat any
	 * value other than 0x00 as cheated, but they should always save as
	 * 0xFF. The game should run a cheat check after loading.
	 * </p>
	 * <p><br>
	 * </p>
	 * @param file File to save to
	 * @throws IOException If the saving fails.
	 */
	public void save(Path file) throws IOException {
		byte[] header = new byte[]{0x43, 0x43, 0x00, CoffeeGame.SEPARATOR};
		byte[] nameBytes = new byte[0];

		if (name != null) {
			nameBytes = name.getBytes(StandardCharsets.UTF_8);
		}
		// Sep. byte (1) + cheated (1) + sep. byte (1) + 3 doubles (24) + 3 sep.
		// bytes (3). Add building ct. * 5) + End byte (1) + padding (1)

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