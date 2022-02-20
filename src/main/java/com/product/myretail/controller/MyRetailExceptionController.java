package com.product.myretail.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.product.myretail.exception.MyRetailException;

@ControllerAdvice
public class MyRetailExceptionController {
	
	@ExceptionHandler(value=MyRetailException.class)
	public ResponseEntity<Object> myRetailExceptionHandler(MyRetailException exception) {
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
