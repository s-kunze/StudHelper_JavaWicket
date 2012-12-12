package de.kunze.studhelper.util;

public class Util {

	public static boolean isNullOrEmpty(String string) {
		return (string == null || string.length() == 0);
	}
	
	public static boolean isNotNullOrEmpty(String string) {
		return !isNullOrEmpty(string);
	}
	
}
