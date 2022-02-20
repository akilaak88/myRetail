package com.product.myretail.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyRetailProduct {
	
	@JsonProperty("id")
	long id;
	
	@JsonProperty("name")
	String name;
	
	@JsonProperty("currentPrice")
	CurrentPrice currentPrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CurrentPrice getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(CurrentPrice currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public String toString()
	{
		return "myRetailProduct{"
				+ "id='"+ id + ","
						+ "name=" + name + ","
						+ "currentPrice=" + currentPrice + "}";
		
	}

}
