package com.product.myretail.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

	private static final Logger log = LoggerFactory.getLogger(MyRetailServiceImpl.class);

	@Autowired
	MyRetailDao myRetailDao;

	@Autowired
	CacheManager cacheManager;

	@Value("${products.api.uri}")
	private String productApiURL;

	RestTemplate restTemplate;

	@Override
	@Cacheable(cacheNames = "productDetail", key = "new org.springframework.cache.interceptor.SimpleKey(#productId, #currencyCode)")
	public MyRetailProduct getProductDetail(long productId, String currencyCode) {
		log.info("Fetching product name for {}", productId);
		MyRetailProduct response = new MyRetailProduct();

		response = fetchProductDetailsFromApi(productId);

		CurrentPrice currentPrice = myRetailDao.getProductPrice(productId, currencyCode);
		response.setCurrentPrice(currentPrice);

		return response;
	}

	@PostConstruct
	public void initializeRestTemplate() {
		restTemplate = new RestTemplateBuilder().build();
	}

	MyRetailProduct fetchProductDetailsFromApi(long productId) {
		MyRetailProduct response = new MyRetailProduct();
		ObjectMapper mapper = new ObjectMapper();
		String uri = productApiURL.concat(String.valueOf(productId));
		log.info("URL {}", uri);
		String apiResponse = restTemplate.getForObject(uri, String.class);
		TypeReference<JSONObject> typeReference = new TypeReference<JSONObject>() {
		};
		try {
			JSONObject json = mapper.readValue(apiResponse, typeReference);
			JsonObject jsonNode = new Gson().fromJson(json.toString(), JsonObject.class);
			JsonObject dataNode = jsonNode.get("data").getAsJsonObject();
			JsonObject productNode = dataNode.get("product").getAsJsonObject();

			response.setId(productNode.get("tcin").getAsLong());
			JsonObject itemNode = productNode.get("item").getAsJsonObject();
			JsonObject pdtDescNode = itemNode.get("product_description").getAsJsonObject();
			response.setName(pdtDescNode.get("title").getAsString());
		} catch (Exception e) {
			log.debug("Exception Occurred while fetching data from API " + e.getMessage());
		}

		return response;
	}

	@Override
	public void updateProductPrice(ProductPriceRequest productPriceRequest) {
		log.info("Price to be updated as {} for {} by {}", productPriceRequest.getPrice(), productPriceRequest.getId(),
				productPriceRequest.getCreateUser());
		myRetailDao.updateProductPrice(productPriceRequest);
		evictCache(productPriceRequest.getId(), productPriceRequest.getCurrencyCode());

	}

	@Override
	public void evictCache(long productId, String currencyCode) {
		SimpleKey key = new SimpleKey(productId, currencyCode);
		log.info("Cache Information evicted for product {} with currency code {}", productId, currencyCode);
		cacheManager.getCache("productDetail").evictIfPresent(key);

	}

	public MyRetailProduct getCacheData(long productId, String currencyCode) {
		log.info("Fetching Cache Information for product {} with currency code {}", productId, currencyCode);
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
