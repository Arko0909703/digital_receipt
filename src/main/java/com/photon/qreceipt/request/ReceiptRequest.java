package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReceiptRequest {

	@JsonProperty("business_date")
	private String businessDate;

	@JsonProperty("store_id")
	private String storeId;
}
