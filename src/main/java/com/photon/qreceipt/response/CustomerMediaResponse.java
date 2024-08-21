package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerMediaResponse {

	@JsonProperty("video_ref")
	private String videoRef;

	@JsonProperty("leaflet_ref")
	private String leafletRef;
	
	@JsonProperty("coupon_ref")
	private String couponRef;

	@JsonProperty("promotional_message")
	private String promotionalMessage;
	
	
}
