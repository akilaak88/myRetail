package com.product.myretail.dao.impl;


import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.product.myretail.Model.CurrentPrice;
import com.product.myretail.Model.ProductPriceRequest;
import com.product.myretail.dao.MyRetailDao;
import com.product.myretail.exception.MyRetailException;

@Repository
public class MyRetailDaoImpl implements MyRetailDao {
	Logger log = LoggerFactory.getLogger(MyRetailDaoImpl.class);
	
	String EXCEPTION_ERROR_CODE="CERR-1000";
	
	@Autowired
	Session session;
	
	
	  PreparedStatement getPreparedStatement; 
	  PreparedStatement updatePreparedStatement;
	  
	  @PostConstruct void setup() { 
		  getPreparedStatement = session.prepare("select * from myretail.product_price where tcin = ? and currency_code = ?"); 
		  updatePreparedStatement = session.prepare("insert into myretail.PRODUCT_PRICE(tcin,price,currency_code,create_user, create_ts) values (?,?,?,?, dateof(now()))"); 
	  }
	 

	@Override
	public CurrentPrice getProductPrice(long productId, String currencyCode) {
		//PreparedStatement getPreparedStatement = session.prepare("select * from myretail.product_price where tcin = ? and currency_code = ?");
				
		CurrentPrice currentPrice = new CurrentPrice();
		try {
			BoundStatement bound = getPreparedStatement.bind(productId,currencyCode);
			ResultSet result = session.execute(bound);
			if(result!=null)  {
				for (Row row : result) {
				currentPrice.setValue(row.getDouble("price"));
				currentPrice.setCurrencyCode(row.getString("currency_code"));
				
				}
				currentPrice.setCurrencyCode(currentPrice.getCurrencyCode()!=null ? currentPrice.getCurrencyCode() : "USD");
			}
			
		} catch (Exception e) {
			log.error("Exception occurred while Fetching from cassandra db"+e.getMessage());
		}
		return currentPrice;
	}

	@Override
	public void updateProductPrice(ProductPriceRequest productPriceRequest) throws MyRetailException {
		//PreparedStatement updatePreparedStatement = session.prepare("insert into myretail.PRODUCT_PRICE(tcin,price,currency_code,create_user, create_ts) values (?,?,?,?, dateof(now()))");
		try {
			
			BoundStatement bound = updatePreparedStatement.bind(productPriceRequest.getId(),
					productPriceRequest.getPrice(), productPriceRequest.getCurrencyCode().toUpperCase(),
					productPriceRequest.getCreateUser());
			session.execute(bound);
		} catch (MyRetailException e) {
			log.error("Exception occurred while Updating cassandra db"+e.getMessage());
			
		}
		
	}
	

}
