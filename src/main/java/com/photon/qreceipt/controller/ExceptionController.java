package com.photon.qreceipt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.photon.qreceipt.exception.CustomException;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.response.MetaResponse;

@ControllerAdvice

public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<MetaResponse> exception(Exception ex) {
		logger.error("Exception unable to process request", ex);

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus("false");
		baseResponse.setErrorMessage(ex.getMessage());

		MetaResponse meta = new MetaResponse();
		meta.setBaseResponse(baseResponse);

		return new ResponseEntity<>(meta, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<MetaResponse> handleSSResourceNotFoundException(CustomException ex) {
		logger.error("Exception unable to process request", ex);
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(ex.getStatus());
		baseResponse.setErrorMessage(ex.getMessage());
		MetaResponse meta = new MetaResponse();
		meta.setBaseResponse(baseResponse);

		return new ResponseEntity<>(meta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
