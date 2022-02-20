package com.product.myretail.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyRetailErrorResponse {
	
	public MyRetailErrorResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@JsonProperty("code")
	String errorCode;
	
	@JsonProperty("message")
	String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
