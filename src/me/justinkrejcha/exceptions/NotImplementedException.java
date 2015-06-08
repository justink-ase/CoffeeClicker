package me.justinkrejcha.exceptions;

/**
 * Exception class for functionality that has not been implemented yet, but
 * will be in the future.
 */
public class NotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NotImplementedException() {
		super("This function has not been implemented.");
	}
}