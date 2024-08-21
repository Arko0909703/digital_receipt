package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoginRequest {

	@JsonProperty("username")
	private String userName;

	@JsonProperty("password")
	private String password;
}
