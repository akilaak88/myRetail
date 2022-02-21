package com.product.myretail.service;

import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.Model.ProductPriceRequest;

public interface MyRetailService {
	/**
	 * This method will retrieve the product using product id and Currency Code. It invokes the 
	 * external API to fetch the product name and Cassandra db for price information
	 *
	 * @param productId
	 * @param currencyCode
	 * @return
	 */
	
	MyRetailProduct getProductDetail(long productId,String currencyCode);
	
	/**
	 * This method will modify the price information of product 
	 * 
	 * @param ProductPriceRequest
	 */
	
	void updateProductPrice(ProductPriceRequest productPriceRequest);
	
	
	/**
	 * This method will Evict the cache when there is an update in price
	 * 
	 * @param productId
	 * @param currencyCode
	 */
	void evictCache(long productId, String currencyCode);
	
	/**
	 * This method will pull the Product information from cache for a given Product Id and Currency Code (created for demo purpose only)
	 * 
	 * @param productId
	 * @param currencyCode
	 */
	public MyRetailProduct getCacheData(long productId, String currencyCode);
	
}
