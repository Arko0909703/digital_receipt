package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerMasterRequest {
	@JsonProperty("phone")
	public String phone;

	@JsonProperty("email")
	public String email;

	@JsonProperty("first_name")
	public String firstName;

	@JsonProperty("last_name")
	public String lastName;

	@JsonProperty("country")
	public String country;

	@JsonProperty("city")
	public String city;

	@JsonProperty("gender")
	public String gender;

	@JsonProperty("nationality")
	public String nationality;

	@JsonProperty("prefered_language")
	public String preferedLanguage;

	@JsonProperty("dob")
	public String dob;

	@JsonProperty("maratial_status")
	public String maratialStatus;
}
