package com.product.myretail.service;

import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.Model.ProductPriceRequest;

public interface MyRetailService {
	MyRetailProduct getProductDetail(long productId,String currencyCode);
	
	void updateProductPrice(ProductPriceRequest productPriceRequest);
	
	void evictCache(long productId, String currencyCode);
	
	public MyRetailProduct getCacheData(long productId, String currencyCode);
	
}
