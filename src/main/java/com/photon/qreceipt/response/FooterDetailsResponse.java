package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FooterDetailsResponse {

	@JsonProperty("footer1")
	private String footer1;
	@JsonProperty("footer2")
	private String footer2;
	@JsonProperty("footer3")
	private String footer3;
	@JsonProperty("footer4")
	private String footer4;
	@JsonProperty("footer5")
	private String footer5;
	@JsonProperty("footer6")
	private String footer6;
}
