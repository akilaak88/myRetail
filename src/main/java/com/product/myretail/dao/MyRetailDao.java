package com.product.myretail.dao;

import com.product.myretail.Model.CurrentPrice;
import com.product.myretail.Model.ProductPriceRequest;

public interface MyRetailDao {
	
	CurrentPrice getProductPrice(long productId,String currencyCode);
	
	void updateProductPrice(ProductPriceRequest productPriceRequest); 

}
