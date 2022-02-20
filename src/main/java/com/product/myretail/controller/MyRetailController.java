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

import com.product.myretail.Model.MyRetailErrorResponse;
import com.product.myretail.Model.MyRetailProduct;
import com.product.myretail.Model.ProductPriceRequest;
import com.product.myretail.Utility.RetailConstants;
import com.product.myretail.Utility.ValidationUtil;
import com.product.myretail.exception.MyRetailException;
import com.product.myretail.service.MyRetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Api(tags = "Product API")
@Tag(name = "Product API", description = "Fetch/Upsert Product Information for MYRetail App")
public class MyRetailController {
	
	private static final Logger log = LoggerFactory.getLogger(MyRetailController.class);

	@Autowired
	MyRetailService myRetailService;

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	@Operation(summary = "Get Product by id/tcin")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request")})
	ResponseEntity<Object> getProductData(@PathVariable(required = true, value = "id") long id, @RequestParam(defaultValue="USD") String currencyCode) {
		log.info("MyRetailController:: getProductData :: Product ID from the request " + id + currencyCode);
		MyRetailProduct result = new MyRetailProduct();

		try {
			if (ValidationUtil.isValidProductId(id)) {
				result = myRetailService.getProductDetail(id, currencyCode);
			}
			else {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body(new MyRetailErrorResponse(RetailConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getCode(),RetailConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getDescription()));
			}
				
		} catch (Exception e) {
			log.debug("MyRetailController:: getProductData :: Exception occurred"+e.getMessage());
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(new MyRetailErrorResponse(RetailConstants.DEFAULT_EXCEPTION.getCode(),RetailConstants.DEFAULT_EXCEPTION.getDescription()));
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}
	
	@RequestMapping(value = "/product", method = RequestMethod.PUT)
	@Operation(summary = "UpSert Product Price Information for an id/tcin ")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Bad Request")})
	  ResponseEntity<Object> updateProductData(@RequestBody(required = true) ProductPriceRequest productPriceRequest)  throws MyRetailException{
		log.info("MyRetailController:: updateProductData :: Product ID from the request " + productPriceRequest);
		try {
			MyRetailErrorResponse validationResponse = checkValidProductPriceRequest(productPriceRequest);
			if(validationResponse!= null) {
				return ResponseEntity
			            .status(HttpStatus.BAD_REQUEST)
			            .body(validationResponse);
			}
			else
				myRetailService.updateProductPrice(productPriceRequest);
		} catch (Exception e) {
			log.debug("MyRetailController:: updateProductData :: Exception occurred while Updating Product Information "+e.getMessage());
			return ResponseEntity
		            .status(HttpStatus.INTERNAL_SERVER_ERROR)
		            .body(new MyRetailErrorResponse(RetailConstants.DEFAULT_EXCEPTION.getCode(),RetailConstants.DEFAULT_EXCEPTION.getDescription()));
		}
		return ResponseEntity
	            .status(HttpStatus.OK)
	            .body(new MyRetailErrorResponse(RetailConstants.PRODUCT_UPDATE_SUCCESSFUL.getCode(),RetailConstants.PRODUCT_UPDATE_SUCCESSFUL.getDescription()));
	  
	  }
	
	MyRetailErrorResponse checkValidProductPriceRequest(ProductPriceRequest productPriceRequest) {
		if(!ValidationUtil.isValidProductId(productPriceRequest.getId())) {
			return new MyRetailErrorResponse(RetailConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getCode(),RetailConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getDescription());
		}
		else if(!ValidationUtil.isValidPrice(productPriceRequest.getPrice())) {
			return new MyRetailErrorResponse(RetailConstants.PRODUCT_PRICE_VALIDATION_EXCEPTION.getCode(),RetailConstants.PRODUCT_PRICE_VALIDATION_EXCEPTION.getDescription());
		}
		else if(!ValidationUtil.isValidCurrencyCode(productPriceRequest.getCurrencyCode())) {
			return new MyRetailErrorResponse(RetailConstants.PRODUCT_CURRENCY_CODE_VALIDATION_EXCEPTION.getCode(),RetailConstants.PRODUCT_CURRENCY_CODE_VALIDATION_EXCEPTION.getDescription());
		}
		else if(!ValidationUtil.isValidCreateUser(productPriceRequest.getCreateUser())) {
			return new MyRetailErrorResponse(RetailConstants.PRODUCT_CREATE_USER_VALIDATION_EXCEPTION.getCode(),RetailConstants.PRODUCT_CREATE_USER_VALIDATION_EXCEPTION.getDescription());
		}
		return null;
	}

}
