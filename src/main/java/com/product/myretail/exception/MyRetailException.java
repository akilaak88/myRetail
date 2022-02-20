package com.product.myretail.exception;

public class MyRetailException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String errorCode;
	private final String description;
	public MyRetailException(String errorCode, String description) {
		super();
		this.errorCode = errorCode;
		this.description = description;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public String getDescription() {
		return description;
	}
	
	public MyRetailException(String errorCode, String description, String message) {
		super(message);
		this.errorCode = errorCode;
		this.description = description;
	}

}
