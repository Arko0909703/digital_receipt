package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDetailRequest {

	@JsonProperty("mobile_no")
	public String mobileNo;

	@JsonProperty("email")
	public String email;

	@JsonProperty("business_date")
	public String businessDate;

	@JsonProperty("receipt_id")
	public long receiptId;
}
