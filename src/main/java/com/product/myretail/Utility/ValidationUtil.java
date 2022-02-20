package com.product.myretail.Utility;

public class ValidationUtil {
	
	public static boolean isValidProductId(long id) {
		return (id > 0 && String.valueOf(id).length() == 8) ? true : false;
	}

	public static boolean isValidPrice(double price) {
		return (price > 0) ? true : false;
	}
	
	public static boolean isValidCurrencyCode(String currencyCode) {
		return (currencyCode!=null && !currencyCode.equals("") && currencyCode.length() == 3) ? true : false;
	}
	

	public static boolean isValidCreateUser(String createUser) {
		return (createUser!=null && !createUser.equals("")) ? true : false;
	}
}
