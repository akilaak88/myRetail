package com.product.myretail.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.product.myretail.Model.CurrentPrice;
import com.product.myretail.Model.ProductPriceRequest;
import com.product.myretail.dao.MyRetailDao;
import com.product.myretail.dao.impl.MyRetailDaoImpl;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@SpringBootTest
public class MyRetailDaoTest {
	
	@Autowired
	MyRetailDao dao;
	

	
	@Test
	@Rollback(true)
	public final void testCreate() {
		//dao = new MyRetailDaoImpl();
		
		dao.updateProductPrice(new ProductPriceRequest(54456119,110.00,"USD","Akila"));
		
		CurrentPrice entity = dao.getProductPrice(54456119, "USD");
		assertEquals("USD", entity.getCurrencyCode());
		assertEquals(110.00, entity.getValue());
		
	}
	

}
