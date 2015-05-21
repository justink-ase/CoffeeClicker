package me.justinkrejcha.game;

public abstract class Player {
	public abstract String getName();
	protected abstract void setName(String name);
	public abstract String toString();
	public abstract boolean equals(Object obj);
}