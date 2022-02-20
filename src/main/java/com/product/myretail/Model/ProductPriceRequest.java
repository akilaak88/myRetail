package com.product.myretail.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPriceRequest {
	
	@Override
	public String toString() {
		return "ProductPriceRequest {id=" + id + ", price=" + price + ", currencyCode=" + currencyCode + ", createUser="
				+ createUser + "}";
	}

	@JsonProperty("id")
	long id;
	
	@JsonProperty("value")
	double price;
	
	@JsonProperty("currencyCode")
	String currencyCode;
	
	@JsonProperty("createUser")
	String createUser;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public ProductPriceRequest(long id, double price, String currencyCode, String createUser) {
		super();
		this.id = id;
		this.price = price;
		this.currencyCode = currencyCode;
		this.createUser = createUser;
	}

	
}
