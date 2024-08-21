package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReceiptResponse {

	@JsonProperty("receipt_id")
	private long id;

	@JsonProperty("saas_id")
	private String saasId;

	@JsonProperty("company_name")
	private String companyName;

	@JsonProperty("generated_pdf")
	private String generatedPdf;

	@JsonProperty("invoice_number")
	private String invoiceNumber;

	@JsonProperty("receipt_downloaded")
	private String receiptDownloaded;

	@JsonProperty("receipt_scanned")
	private String receiptScanned;

	@JsonProperty("business_date")
	private String businessDate;

	@JsonProperty("device_id")
	private String deviceId;

}
