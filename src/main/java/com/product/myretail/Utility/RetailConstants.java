package com.product.myretail.Utility;

public enum RetailConstants {
	
	DEFAULT_EXCEPTION ("5000","Error while fetching Product Information. Please try after sometime."),
	PRODUCT_ID_VALIDATION_EXCEPTION("4000", "Invalid Product ID"),
	PRODUCT_NAME_NOT_FOUND_EXCEPTION("4001", "Unable to find Product Name"),
	PRODUCT_PRICE_NOT_FOUND_EXCEPTION("4002", "Unable to find Product Price"),
	PRODUCT_PRICE_VALIDATION_EXCEPTION("4003", "Invalid Product Price "),
	PRODUCT_OBJ_NOT_FOUND_EXCEPTION("4004", "Product Information does not exist"),
	PRODUCT_CURRENCY_CODE_VALIDATION_EXCEPTION("4005", "Invalid Currency Code"),
	PRODUCT_CREATE_USER_VALIDATION_EXCEPTION("4006", "Create User cannot be blank"),
	PRODUCT_UPDATE_SUCCESSFUL("2000", "Product details updated successfully");
	
	private final String code;
	private final String description;
	
	private RetailConstants(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

}
