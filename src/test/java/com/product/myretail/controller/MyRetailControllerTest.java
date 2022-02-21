package com.product.myretail.controller;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.product.myretail.Model.CurrentPrice;
import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.service.MyRetailService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class MyRetailControllerTest {
	
	@MockBean
	MyRetailService myRetailService;
	
	
	 @Autowired
	MockMvc mockMvc;
	
	
	@Test
	public final void testgetProductDetail() throws Exception {
		MyRetailProduct product = new MyRetailProduct();
		product.setId(13860428);
		product.setName("TestProduct");
		CurrentPrice price = new CurrentPrice();
		price.setCurrencyCode("USD");
		price.setValue(100.00);
		product.setCurrentPrice(price);
		
		when(myRetailService.getProductDetail(13860428, "USD")).thenReturn(product);
		
		
		
	this.mockMvc.perform(MockMvcRequestBuilders.get("/product/13860428")).andExpect(status().isOk()).andExpect(content().json("{}"));
		
		
	}
	
	@Test
	public final void testUpdateProductPrice() throws Exception {
		String request = "{\n"
				+ "  \"id\":  13860428,\n"
				+ "  \"value\": 200,\n"
				+ "  \"currencyCode\": \"USD\",\n"
				+ "  \"createUser\": \"Akila\"\n"
				+ "}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/product").content(request).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isOk()).andExpect(content().json("{}"));
				
		
	}
	
	@Test
	public final void testgetProductDetailWithInvalidId() throws Exception {		
		
	this.mockMvc.perform(MockMvcRequestBuilders.get("/product/13860")).andExpect(status().is4xxClientError()).andExpect(content().json("{}"));
	this.mockMvc.perform(MockMvcRequestBuilders.get("/product/0")).andExpect(status().is4xxClientError()).andExpect(content().json("{}"));
	}
	
	@Test
	public final void testUpdateProductDetailWithInvalidId() throws Exception {		
		
		String request = "{\n"
				+ "  \"id\":  100,\n"
				+ "  \"value\": 200,\n"
				+ "  \"currencyCode\": \"USD\",\n"
				+ "  \"createUser\": \"Akila\"\n"
				+ "}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/product").content(request).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().is4xxClientError()).andExpect(content().json("{}"));
	}
	
	@Test
	public final void testUpdateProductDetailWithInvalidPrice() throws Exception {		
		
		String request = "{\n"
				+ "  \"id\": 13860428,\n"
				+ "  \"value\": -23,\n"
				+ "  \"currencyCode\": \"USD\",\n"
				+ "  \"createUser\": \"Akila\"\n"
				+ "}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/product").content(request).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().is4xxClientError()).andExpect(content().json("{}"));
	}
	
	@Test
	public final void testUpdateProductDetailWithInvalidCurrencyCode() throws Exception {		
		
		String request = "{\n"
				+ "  \"id\": 13860428,\n"
				+ "  \"value\": 23,\n"
				+ "  \"currencyCode\": \"DOllar\",\n"
				+ "  \"createUser\": \"Akila\"\n"
				+ "}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/product").content(request).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().is4xxClientError()).andExpect(content().json("{}"));
	}
	
	@Test
	public final void testUpdateProductDetailWithInvalidCreateUser() throws Exception {		
		
		String request = "{\n"
				+ "  \"id\": 13860428,\n"
				+ "  \"value\": 23,\n"
				+ "  \"currencyCode\": \"USD\",\n"
				+ "  \"createUser\": \"\"\n"
				+ "}";
		
		this.mockMvc.perform(MockMvcRequestBuilders.put("/product").content(request).
				accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().is4xxClientError()).andExpect(content().json("{}"));
	}

}
