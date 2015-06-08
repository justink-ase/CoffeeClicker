package me.justinkrejcha.ccgame;

import java.util.List;

import me.justinkrejcha.Util;

/**
 * This class contains buildings and some helper methods for the game.
 */
public class BuildingList {
	private List<Building> buildings;

	/**
	 * Creates a new {@link BuildingList}
	 * @param buildings The buildings to create the method game.
	 * @throws java.lang.NullPointerException if any elements in buildings
	 * are null
	 */
	public BuildingList(List<Building> buildings) {
		Util.validateNoNullElements(buildings);
		this.buildings = buildings;
	}
	
	/**
	* Gets a building by it's position
	* @param pos Position of building to get
	* @return Building at position
	*/
	public Building get(int pos) {
		return buildings.get(pos);
	}
	
	/**
	* Gets the amount of buildings. A valid index will be one less
	* than this number.
	*/
	public int size() {
		return buildings.size();
	}
	
	/**
	* Gets the total amount of buildings owned.
	* @return The total amount of buildings owned.
	*/
	public int getTotalOwned() {
		int total = 0;
		for (Building b : buildings) {
			total += b.getAmount();
		}
		return total;
	}

	/**
	 * Gets the total coffees per second bonus that all of the buildings
	 * there are.
	 * @return The sum of each buildings coffees per second bonus.
	 * @see me.justinkrejcha.ccgame.Building#getTotalBonus
	 */
	public double getTotalBonus() {
		double bonus = 0.0;
		for (Building b : buildings) {
			bonus += b.getTotalBonus();
		}
		return bonus;
	}

	/**
	 * Gets all of the buildings as an array.
	 * @return Array of all buildings.
	 */
	public Building[] getAllBuildings() {
		return buildings.toArray(new Building[size()]);
	}
}