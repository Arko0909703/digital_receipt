package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SummaryResponse {

	@JsonProperty("company_name")
	private String companyName;

	@JsonProperty("store_name")
	private String storeName;

	@JsonProperty("invoice_generated")
	private float invoiceGenerated;

	@JsonProperty("invoice_download")
	private float invoiceDownload;

	@JsonProperty("invoice_scanned")
	private float invoiceScanned;

	@JsonProperty("co2_emission_saved")
	private double co2EmissionSaved;

	@JsonProperty("percentage")
	private float percentage;

	@JsonProperty("store_id")
	private String storeId;
}
