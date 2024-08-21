package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CountResponse {

	@JsonProperty("count")
	private long count;
}
