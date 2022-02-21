package com.product.myretail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.service.MyRetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags = "Utility API")
@Tag(name = "Utility API", description = "Utility endpoints for demo ")
@RestController
public class UtilityController {
	private static final Logger log = LoggerFactory.getLogger(UtilityController.class);
	@Autowired
	MyRetailService myRetailService;

	
	/**
	 * This method used to get the product details from cache for a given Product Id and Currency Code
	 * This is only for demo Purpose
	 *
	 * This method accept 2 parameters
	 * Id 
	 * Currency Code 
	 */
	@RequestMapping(value = "/cache/{id}", method = RequestMethod.GET)
	 @Operation(summary = "Get Cache Information for a tcin")
    @ApiResponse(code = 200, message = "OK")
	ResponseEntity<MyRetailProduct> getProductData(@PathVariable long id, @RequestParam(defaultValue="USD") String currencyCode) {
		log.info("UtilityController:: getProductData :: Product ID from the request " + id + currencyCode);
		MyRetailProduct result = new MyRetailProduct();

		result = myRetailService.getCacheData(id,currencyCode);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
}
