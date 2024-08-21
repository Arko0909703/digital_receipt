package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ValidateLicenseRequest {

	@JsonProperty("saas_id")
	public String saasId;
	@JsonProperty("register_id")
	public String registerId;
	@JsonProperty("store_id")
	public String storeId;
	@JsonProperty("license_key")
	public String licenseNumber;
	@JsonProperty("system_key")
	public String systemKey;
}
