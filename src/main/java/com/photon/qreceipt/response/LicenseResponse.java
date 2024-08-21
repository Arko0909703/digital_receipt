package com.photon.qreceipt.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LicenseResponse{
	@JsonProperty("license-number")
	public String licenseNumber;
	@JsonProperty("client-id")
	public String saasId;
	@JsonProperty("store-id")
	private String storeId;

	@JsonProperty("register_id")
	private String registerId;

	@JsonProperty("status")
	private String status;

	@JsonProperty("effective_date")
	private LocalDateTime effectiveDate;

	@JsonProperty("expiry_date")
	private LocalDate expiryDate;

	@JsonProperty("renew_date")
	private LocalDateTime renewDate;

	@JsonProperty("trial_period")
	private int trialPeriod;

	@JsonProperty("license_period")
	private int licensePeriod;
	@JsonProperty("applicability")
	private String applicability;


}
