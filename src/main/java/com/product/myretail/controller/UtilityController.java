package com.product.myretail.controller;

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
	
	@Autowired
	MyRetailService myRetailService;

	@RequestMapping(value = "/cache/{id}", method = RequestMethod.GET)
	 @Operation(summary = "Get Cache Information for a tcin")
    @ApiResponse(code = 200, message = "OK")
	ResponseEntity<MyRetailProduct> getProductData(@PathVariable long id, @RequestParam(defaultValue="USD") String currencyCode) {
		MyRetailProduct result = new MyRetailProduct();

		result = myRetailService.getCacheData(id,currencyCode);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
}
