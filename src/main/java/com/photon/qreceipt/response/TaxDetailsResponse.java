package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TaxDetailsResponse {
	@JsonProperty("tax_type")
	private String taxType;

	@JsonProperty("tax_percent")
	private String taxPercent;

	@JsonProperty("tax_amount")
	private String taxAmount;

}
