package it.lucarasconi.common.utils;

import it.lucarasconi.common.exception.NotEmptyException;
import it.lucarasconi.common.exception.OnlyOneException;

public class MyUtils {
	
	public static boolean isTrue( String value) {
        return value != null &&
                (value.equalsIgnoreCase("true") ||
                        value.equalsIgnoreCase("t") ||
                        value.equalsIgnoreCase("1") ||
                        value.equalsIgnoreCase("enabled") ||
                        value.equalsIgnoreCase("y") ||
                        value.equalsIgnoreCase("yes") ||
                        value.equalsIgnoreCase("on"));
    }
	
	public static void onlyOneOf(Object[] params) throws IllegalArgumentException {
		boolean check = false;
		for (Object p : params) {
			if (p != null) {
				if (check) {
					throw new OnlyOneException( String.format("only one element must be not empty") );
				}
				if (p instanceof String) {
					if (((String)p).length() > 0) {
						check = true;
					}
				} else {
					check = true;
				}	
			}
		}
		if (!check) {
			throw new OnlyOneException("all parameters cant't be empty");
		}
	}

	public static void notEmpty(Object[] params) throws IllegalArgumentException {
		for (Object p : params) {
			
			if (p == null ) {
				throw new NotEmptyException("null parameter is not valid");
			}
			if (p instanceof String) {
				if (((String)p).length() == 0) {
					throw new NotEmptyException("0 length paramter is not valid");
				}
			}
		}
		
	}

}
