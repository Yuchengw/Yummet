package com.iamhere.utilities;

/**
 * The util class for all  String 
 * @author jassica
 *
 */
public class TextUtil {
	/**
	 * Determine if the input string is empty or null after trim
	 * @param s
	 * @return
	 */
	public static boolean isNullOrEmpty(String s) {
		if (s == null) {
			return true;
		}
		if (s.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
