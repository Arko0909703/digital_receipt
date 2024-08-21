package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class QRResponse {
	@JsonProperty("message")
	String message;
	@JsonProperty("receipt_url")
	String receiptUrl;
	@JsonProperty("client_promo")
	String clientPromo;
	@JsonProperty("qrecipt_promo")
	String qrceiptPromo;
	@JsonProperty("pdf_name")
	String pdfName;
	@JsonProperty("receipt_id")
	Long receiptId;
	@JsonProperty("company_name")
	String companyName;
}
