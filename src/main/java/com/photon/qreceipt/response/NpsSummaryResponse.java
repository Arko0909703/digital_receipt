package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NpsSummaryResponse {
	@JsonProperty("company_name")
	private String companyName;

	@JsonProperty("store_name")
	private String storeName;

	@JsonProperty("promotor")
	private Long promotor;

	@JsonProperty("passive")
	private Long passive;

	@JsonProperty("detractor")
	private Long dectractor;

	@JsonProperty("nps_score")
	private float npsScore;

	@JsonProperty("response_rate")
	private float responseRate;

	@JsonProperty("invoice_generated")
	private long invoiceGenerated;

	@JsonProperty("store_id")
	private String storeId;
}
