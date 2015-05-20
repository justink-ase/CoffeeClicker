package me.justinkrejcha.exceptions;

public class NotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NotImplementedException() {
		super("This function has not been implemented.");
	}
}