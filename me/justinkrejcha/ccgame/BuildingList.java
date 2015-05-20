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
		validateDifferentIds();
	}
	
	/**
	* Gets a building by it's ID
	* @param id ID of building to get
	* @return Building that has the same id or null if doesn't exist.
	*/
	public Building get(int id) {
		for (Building b : buildings) {
			if (b.getId() == id) return b;
		}
		return null;
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
	
	private void validateDifferentIds() {
		List<Integer> ids = new ArrayList<Integer>();
		for (Building b : buildings) {
			ids.add(b.getId());
		}
		Util.validateElementsDifferent(ids);
	}
}