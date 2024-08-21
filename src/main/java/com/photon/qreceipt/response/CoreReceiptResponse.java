package com.photon.qreceipt.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.photon.qreceipt.entity.TaxDetails;

import lombok.Data;

@Data
public class CoreReceiptResponse extends BaseResponse {

	@JsonProperty("store_id")
	private String storeId;

	@JsonProperty("register_id")
	private String registerId;

	@JsonProperty("saas-id")
	private String saasId;

	@JsonProperty("business_date")
	private String businessDate;

	@JsonProperty("create_date")
	private LocalDateTime createDate;

	@JsonProperty("receipt_content")
	private String receiptContent;

	@JsonProperty("client_name")
	private String clientName;

	@JsonProperty("client_url")
	private String clientUrl;

	@JsonProperty("client_logo")
	private String clientLogo;

	@JsonProperty("header_details")
	private HeaderDetailsResponse headerDetailsResponse;

	@JsonProperty("item_details")
	private List<ItemDetailsResponse> itemDetailsResponse;

	@JsonProperty("tax_details")
	private List<TaxDetailsResponse> taxDetailsResponse;

	@JsonProperty("footer_details")
	private FooterDetailsResponse footerDetailsResponse;
}
