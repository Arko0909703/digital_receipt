package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NpsResponse {

	@JsonProperty("count")
	private Long count;

	@JsonProperty("message")
	private String message;
}
