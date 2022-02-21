package com.product.myretail.dao;

import com.product.myretail.Model.CurrentPrice;
import com.product.myretail.Model.ProductPriceRequest;

public interface MyRetailDao {
	
	/**
	 * This method will retrieve the product using product id and Currency Code from Cassandra db for price information
	 *
	 * @param productId
	 * @param currencyCode
	 * @return
	 */
	
	CurrentPrice getProductPrice(long productId,String currencyCode);
	
	/**
	 * This method will Update the product Information to Cassandra db
	 *
	 * @param ProductPriceRequest
	 */
	void updateProductPrice(ProductPriceRequest productPriceRequest); 

}
