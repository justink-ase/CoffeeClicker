package me.justinkrejcha.ccgame;

public class Building {
	private int id;
	private String name;
	private int basePrice;
	private int amount;
	private double bonus;
	
	public Building(int id, String name, int basePrice, double bonus) {
		this(id, name, basePrice, 0, bonus);
	}
	
	public Building(int id, String name, int basePrice, int amount, double bonus) {
		this.id = id;
		this.name = name;
		this.basePrice = basePrice;
		this.amount = amount;
		this.bonus = bonus;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	* Gets the name of this building.
	* @return Building's name.
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Returns the base price. Use getPrice() if you want to get the cost for
	* this item.
	* @return Base price for this item.
	*/
	public int getBasePrice() {
		return basePrice;
	}
	
	/**
	* Gets the amount of this type of building owned.
	* @return Amount of buildings owned for this.
	*/
	public int getAmount() {
		return amount;
	}
	
	/**
	* Sets the amount of this type of building owned.
	* @param amount Value to set
	*/
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	* Gets the bonus. Use getTotalBonus() to get the total bonus for
	* this building based on amount.
	*/
	public double getBonus() {
		return bonus;
	}
	
	public double getTotalBonus() {
		return bonus * amount;
	}
	
	/**
	* Returns the price to buy another building.<br>
	* <b>Formula:</b> Base Cost * (1.15 ^ Amount owned - 1) / 0.15
	* @return The new price to buy another building.
	*/
	public long getPrice() {
		// base cost * (1.15^amount - 1) / 0.15
		if (amount == 0) {
			return (long)basePrice;
		}
		return (long)((long)basePrice * (Math.pow(1.15, amount) - 1) / 0.15);
	}
	
	/**
	* Increments the amount of this building. This does not check for enough
	* coffees to upgrade.
	*/
	public void increment() {
		amount++;
	}
}