package com.photon.qreceipt.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private String status;

	public CustomException(String status, String message) {
		super(message, new Throwable(message));
		this.status = status;
		this.message = message;
	}

	public CustomException(String message) {
		super(message);
		message = message;
	}

}
