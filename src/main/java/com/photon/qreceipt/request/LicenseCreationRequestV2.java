package com.photon.qreceipt.request;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LicenseCreationRequestV2 {
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
	@JsonProperty("registration_code")
	private String registrationCode;

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}


	public String getSaasId() {
		return saasId;
	}

	public void setSaasId(String saasId) {
		this.saasId = saasId;
	}

	public Integer getLicensePeriod() {
		return licensePeriod;
	}

	public void setLicensePeriod(Integer licensePeriod) {
		this.licensePeriod = licensePeriod;
	}

	public String getApplicability() {
		return applicability;
	}

	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}

	public Integer getNoOfLicenses() {
		return noOfLicenses;
	}

	public void setNoOfLicenses(Integer noOfLicenses) {
		this.noOfLicenses = noOfLicenses;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

}
