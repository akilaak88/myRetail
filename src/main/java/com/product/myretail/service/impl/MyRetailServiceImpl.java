package com.product.myretail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.product.myretail.Model.CurrentPrice;
import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.Model.ProductPriceRequest;
import com.product.myretail.dao.MyRetailDao;
import com.product.myretail.service.MyRetailService;

import net.minidev.json.JSONObject;

@Service
public class MyRetailServiceImpl implements MyRetailService {

	@Autowired
	MyRetailDao myRetailDao;

	@Autowired
	CacheManager cacheManager;

	@Override
	@Cacheable(cacheNames = "productDetail", key = "new org.springframework.cache.interceptor.SimpleKey(#productId, #currencyCode)")
	public MyRetailProduct getProductDetail(long productId, String currencyCode) {
		MyRetailProduct response = new MyRetailProduct();
		ObjectMapper mapper = new ObjectMapper();
		String uri = "https://redsky-uat.perf.target.com/redsky_aggregations/v1/redsky/case_study_v1?key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys&tcin="
				+ productId;
		System.out.println("uri->" + uri);
		RestTemplate restTemplate = new RestTemplate();
		String response1 = restTemplate.getForObject(uri, String.class);
		TypeReference<JSONObject> typeReference = new TypeReference<JSONObject>() {
		};
		try {
			JSONObject json = mapper.readValue(response1, typeReference);
			JsonObject jsonNode = new Gson().fromJson(json.toString(), JsonObject.class);
			JsonObject dataNode = jsonNode.get("data").getAsJsonObject();
			JsonObject productNode = dataNode.get("product").getAsJsonObject();

			response.setId(productNode.get("tcin").getAsLong());
			JsonObject itemNode = productNode.get("item").getAsJsonObject();
			JsonObject pdtDescNode = itemNode.get("product_description").getAsJsonObject();
			response.setName(pdtDescNode.get("title").getAsString());
			CurrentPrice currentPrice = myRetailDao.getProductPrice(productId, currencyCode);
			response.setCurrentPrice(currentPrice);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public void updateProductPrice(ProductPriceRequest productPriceRequest) {
		myRetailDao.updateProductPrice(productPriceRequest);
		evictCache(productPriceRequest.getId(), productPriceRequest.getCurrencyCode());

	}

	@Override
	public void evictCache(long productId, String currencyCode) {
		SimpleKey key = new SimpleKey(productId, currencyCode);
		cacheManager.getCache("productDetail").evictIfPresent(key);

	}

	public MyRetailProduct getCacheData(long productId, String currencyCode) {
		org.springframework.cache.Cache cache = cacheManager.getCache("productDetail");
		MyRetailProduct myRetailProduct = null;
		if (cache != null) {
			SimpleKey key = new SimpleKey(productId, currencyCode);
			ValueWrapper valueWrapper = cache.get(key);
			if (valueWrapper != null) {
				myRetailProduct = (MyRetailProduct) valueWrapper.get();
			}
		}

		return myRetailProduct;
	}

}
