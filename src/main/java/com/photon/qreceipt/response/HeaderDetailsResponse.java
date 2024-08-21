package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class HeaderDetailsResponse {

	@JsonProperty("invoice_number")
	private String invoiceNumber;
	@JsonProperty("invoice_date")
	private String invoiceDate;
	@JsonProperty("header1")
	private String header1;
	@JsonProperty("header2")
	private String header2;
	@JsonProperty("header3")
	private String header3;
	@JsonProperty("header4")
	private String header4;
	@JsonProperty("header5")
	private String header5;
	@JsonProperty("header6")
	private String header6;
	@JsonProperty("header7")
	private String header7;
	@JsonProperty("header8")
	private String header8;
	@JsonProperty("document_url")
	private String documentUrl;
}
