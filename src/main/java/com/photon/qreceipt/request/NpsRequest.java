package com.photon.qreceipt.request;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NpsRequest {
	@JsonProperty("saas_id")
	private String saasId;

	@JsonProperty("store_id")
	private String storeId;

	@JsonProperty("receipt_id")
	private String receiptId;

	@JsonProperty("nps_score")
	private String npsScore;

	@JsonProperty("nps_reason")
	private String npsReason;

	@JsonProperty("business_date")
	private String businessDate;

}
