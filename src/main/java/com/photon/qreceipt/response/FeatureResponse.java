package com.photon.qreceipt.response;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FeatureResponse {

	@JsonProperty("nps")
	private String nps;

	@JsonProperty("loyalty")
	private String loyalty;

	@JsonProperty("customer_info")
	private String customerInfo;

	@JsonProperty("customer_em")
	private String customerEm;

	@JsonProperty("promotion")
	private String promotion;

	@JsonProperty("saas_id")
	private String saasId;
}
