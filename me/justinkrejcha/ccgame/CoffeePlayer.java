package me.justinkrejcha.ccgame;

import me.justinkrejcha.Util;
import me.justinkrejcha.game.*;
import me.justinkrejcha.exceptions.*;

/**
* The player class for the game. This class contains all buildings, and other
* data used for the game.
*/
public class CoffeePlayer extends Player {
	private String name;
	private boolean cheated;
	private double coffees;
	private double totalCoffees;
	private double perSecond;
	private BuildingList buildings;
	
	public CoffeePlayer() {
		this(null);
	}
	
	public CoffeePlayer(String name) {
		this(name, false, 0.0, 0.0, 0.0, CoffeeGame.createDefaultBuildings());
	}
	
	public CoffeePlayer(String name, boolean cheated, double coffees, 
						double totalCoffees, double perSecond, BuildingList buildings) {
		this.name = name;
		this.cheated = cheated;
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
		cheated = Util.any(cheated, coffees > totalCoffees, perSecond > totalCoffees,
					totalCoffees < 0, coffees < 0, perSecond < 0);
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
}