package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CoreReceiptRequest {
	@JsonProperty("store_id")
	private String storeId;

	@JsonProperty("register_id")
	private String registerId;

	@JsonProperty("saas_id")
	private String saasId;

	@JsonProperty("business_date")
	private String businessDate;

	@JsonProperty("device_id")
	private String deviceId;

}
