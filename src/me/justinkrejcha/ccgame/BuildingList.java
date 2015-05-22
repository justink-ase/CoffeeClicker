package me.justinkrejcha.ccgame;

import java.util.List;
import java.util.ArrayList;

import me.justinkrejcha.Util;

public class BuildingList {
	private List<Building> buildings;
	
	/**
	* TODO: Javadoc
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
	
	public double getTotalBonus() {
		double bonus = 0.0;
		for (Building b : buildings) {
			bonus += b.getTotalBonus();
		}
		return bonus;
	}

	public Building[] getAllBuildings() {
		return buildings.toArray(new Building[size()]);
	}
}