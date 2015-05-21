package me.justinkrejcha;

import java.util.List;

/**
* A utility class. Initalization isn't allowed.
*/
public class Util {
	private Util() {
		// private initalizer to prevent initalization
	}
	
	/**
	* Returns true if any of the elements in the array
	* are true. The best case for time will be when the first
	* value is true, and the worst case is when it's false.
	* @param values The values to test
	* @return true if any of them are true, false otherwise
	*/
	public static boolean any(boolean... values) {
		for (boolean b : values) {
			if (b) return true;
		}
		return false;
	}
	
	/**
	* Returns true if all of the elements of the array
	* are true. The best case for time will be when the first
	* value is false, and the worse case is when it's true.
	* @param values The values to test
	* @return true if all of them are true, false otherwise
	*/
	public static boolean all(boolean... values) {
		for (boolean b : values) {
			if (!b) return false;
		}
		return true;
	}
	
	/**
	* Throws an exception if the value passed is null.
	* @param obj Object to validate
	*/
	public static void validateNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("Validation of non-null object fail");
		}
	}
	
	/**
	* Throws an exception if the value contains any null
	* elements or if the object is null.
	* @param obj List to validate
	*/
	public static void validateNoNullElements(List obj) {
		validateNotNull(obj);
		for (Object element : obj) {
			validateNotNull(element);
		}
	}
	
	/**
	* Validates that each element in this list is different and the list is not null.
	* @param obj List to validate
	*/
	public static void validateElementsDifferent(List obj) {
		validateNotNull(obj);
		for (int i = 0; i < obj.size(); i++) {
			for (int j = i + 1; j < obj.size(); j++) {
				if (obj.get(i).equals(obj.get(j))) {
					throw new IllegalStateException("Value at indices " + i + " and " + j +
						" are the same");
				}
			}
		}
	}
}