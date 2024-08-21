package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginResponse {

	@JsonProperty("username")
	private String userName;

	@JsonProperty("saas_id")
	private String saasId;

	@JsonProperty("company_name")
	private String companyName;

}
