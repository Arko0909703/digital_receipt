package com.photon.qreceipt.response;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StoreResponse {

	@JsonProperty("store_id")
	private String storeId;

	@JsonProperty("store_name")
	private String storeName;

	@JsonProperty("store_country")
	private String storeCountry;

	@JsonProperty("store_location")
	private String storeLocation;

	@JsonProperty("store_address1")
	private String storeAddress1;

	@JsonProperty("store_adress2")
	private String storeAddress2;

	@JsonProperty("store_email")
	private String storeEmail;

	@JsonProperty("store_phone")
	private String storePhone;

	@JsonProperty("store_trn")
	private String storeTrn;

}
