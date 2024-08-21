package com.photon.qreceipt.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListResponse {

	@JsonProperty("count")
	private long count;

	@JsonProperty("data")
	List<ReceiptResponse> data;
}
