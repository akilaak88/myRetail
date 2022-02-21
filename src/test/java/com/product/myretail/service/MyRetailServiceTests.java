package com.product.myretail.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.product.myretail.Model.CurrentPrice;
import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.Model.ProductPriceRequest;
import com.product.myretail.dao.MyRetailDao;
import com.product.myretail.dao.impl.MyRetailDaoImpl;
import com.product.myretail.service.MyRetailService;
import com.product.myretail.service.impl.MyRetailServiceImpl;

@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@SpringBootTest
public class MyRetailServiceTests {
	
	@Autowired
	MyRetailDao prodDAO;
	
	@Value("${products.api.uri}")
	private String productApiURL;
	
	
	@Autowired
	MyRetailService prodService;
	 
		@Test
		public final void testgetProductPrice() {
			MyRetailProduct entity = prodService.getProductDetail(54456119, "USD");
			assertNotNull(entity);
		}

		@Test
		public final void testUpdateProductPrice() {
			ProductPriceRequest product = new ProductPriceRequest(13860428,220.00,"USD","Akila");
			prodService.updateProductPrice(product);
			MyRetailProduct entity = prodService.getProductDetail(13860428, "USD");
			assertNotNull(entity);
		}
}
