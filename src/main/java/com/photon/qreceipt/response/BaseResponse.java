package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BaseResponse {

	@JsonProperty("status")
	private String status;

	@JsonProperty("message")
	private String errorMessage;

}
