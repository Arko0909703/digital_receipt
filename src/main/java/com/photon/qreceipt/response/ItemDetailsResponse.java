package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ItemDetailsResponse {
	@JsonProperty("item_name")
	private String itemName;

	@JsonProperty("item_qty")
	private String itemQty;

	@JsonProperty("unit_price")
	private String unitPrice;

	@JsonProperty("discount_price")
	private String discountPrice;

	@JsonProperty("net_price")
	private String netPrice;

	@JsonProperty("item_tax")
	private String itemTax;

	@JsonProperty("item_tax_rate")
	private String itemTaxRate;

	@JsonProperty("item_tax_type")
	private String itemTaxType;

}