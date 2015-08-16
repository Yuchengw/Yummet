package com.yummet.utilities;

/**
 * The util class for all  String 
 * @author jassica
 * @version 1
 */
public class TextUtil {
	/**
	 * Determine if the input string is empty or null after trim
	 * @param s
	 * @return
	 */
	public static boolean isNullOrEmpty(String s) {
		if (s == null || s.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}