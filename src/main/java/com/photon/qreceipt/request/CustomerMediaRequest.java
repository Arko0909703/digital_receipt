package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CustomerMediaRequest {

	
	@JsonProperty("video_ref")
	private String videoRef;

	@JsonProperty("leaflet_ref")
	private String leafletRef;
	
	@JsonProperty("coupon_ref")
	private String couponRef;

	@JsonProperty("promotional_message")
	private String promotionalMessage;
	
	
}

