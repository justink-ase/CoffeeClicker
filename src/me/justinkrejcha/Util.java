package me.justinkrejcha;

import java.util.ArrayList;
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
	 * Splits an array into a list of byte arrays
	 * @param array     Array to split
	 * @param separator Separator byte to use
	 * @return List of arrays containing the split data.
	 */
	public static byte[][] splitByteArray(byte[] array, byte separator) {
		List<Byte[]> bytes = new ArrayList<Byte[]>();
		List<Byte> subBytes = new ArrayList<Byte>();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != separator) {
				subBytes.add(array[i]);
			} else {
				bytes.add(subBytes.toArray(new Byte[0]));
				subBytes = new ArrayList<Byte>();
			}
		}
		byte[][] converted = new byte[bytes.size()][];
		for (int i = 0; i < converted.length; i++) {
			Byte[] subData = bytes.get(i);
			converted[i] = new byte[subData.length];
			for (int j = 0; j < converted[i].length; j++) {
				converted[i][j] = subData[j];
			}
		}
		return converted;
	}

	public static byte[] combineByteArray(byte[]... arrays) {
		int totalSize = 0;
		for (byte[] b : arrays) {
			totalSize += b.length;
		}
		byte[] combined = new byte[totalSize];
		int position = 0;
		for (int i = 0; i < arrays.length; i++) {
			for (int j = 0; j < arrays[i].length; j++) {
				combined[position] = arrays[i][j];
				position++;
			}
		}
		return combined;
	}
	
	/**
	* Throws an exception if the value passed is null.
	* @param obj Object to validate
	*/
	public static void validateNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException("Expected non-null object but was null");
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
	
	/**
	* Formats a string and returns it.
	* @param format Format to use
	* @param args   Arguments
	* @return Formatted string
	*/
	public static String format(String format, String... args) {
		String newStr = format;
		for (int i = 0; i < args.length; i++) {
			newStr = newStr.replace("{" + i + "}", args[i]);
		}
		return newStr;
	}
}