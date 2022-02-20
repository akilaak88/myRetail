package com.product.myretail.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.Model.ProductPriceRequest;
import com.product.myretail.exception.MyRetailException;
import com.product.myretail.service.MyRetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Api(tags = "Product API")
@Tag(name = "Product API", description = "Perform CRUD operations on Products API ")
public class MyRetailController {
	
	private static final Logger log = LoggerFactory.getLogger(MyRetailController.class);

	@Autowired
	MyRetailService myRetailService;

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	 @Operation(summary = "Get Product by id")
     @ApiResponse(code = 200, message = "OK")
	
	ResponseEntity<MyRetailProduct> getProductData(@PathVariable(required = true, value = "id") long id, @RequestParam(defaultValue="USD") String currencyCode) {
		log.info("MyRetailController:: getProductData :: Product ID from the request " + id+currencyCode);
		MyRetailProduct result = new MyRetailProduct();

		result = myRetailService.getProductDetail(id,currencyCode);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/product", method = RequestMethod.PUT)
	  ResponseEntity<String> updateProductData(@RequestBody(required = true) ProductPriceRequest productPriceRequest)  throws MyRetailException{
		log.info("MyRetailController:: updateProductData :: Product ID from the request " + productPriceRequest);
		myRetailService.updateProductPrice(productPriceRequest);
		
	  
		return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
	  
	  }
	 

}
