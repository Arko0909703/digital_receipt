package com.photon.qreceipt.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LicenseCreationRequest {
	@JsonProperty("saas_id")
	public String saasId;
	@JsonProperty("license_period")
	public Integer licensePeriod;
	@JsonProperty("applicability")
	public String applicability;
	@JsonProperty("no_of_licenses")
	public Integer noOfLicenses;
	@JsonProperty("license_type")
	public String licenseType;
	
}
