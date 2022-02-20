package com.product.myretail.Model;

import javax.ws.rs.DefaultValue;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentPrice {
	
	@JsonProperty("value")
	double value;
	
	@JsonProperty("currencyCode")
	@DefaultValue("USD")
	String currencyCode;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String toString()
	{
		return "myRetailProduct{"
				+ "value='"+ value + ","
						+ "currencyCode=" + currencyCode + "}";
		
	}

}
