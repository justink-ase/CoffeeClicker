package me.justinkrejcha.game;

/**
 * Represents a player.
 */
public abstract class Player {
	/**
	 * Gets this player's name.
	 * @return Player name
	 */
	public abstract String getName();

	/**
	 * Sets this player's name.
	 * @param name Payer name
	 */
	protected abstract void setName(String name);

	/**
	 * Gets a string representation of this object.
	 * @return String representation of this player.
	 */
	public abstract String toString();

	/**
	 * Checks equivalency with another object.
	 * @param obj Object ot test.
	 * @return If these two objects equal each other.
	 */
	public abstract boolean equals(Object obj);
}