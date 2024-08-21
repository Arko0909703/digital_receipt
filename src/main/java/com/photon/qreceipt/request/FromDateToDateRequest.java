package com.photon.qreceipt.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FromDateToDateRequest {
	@JsonProperty("from_date")
	private String fromDate;

	@JsonProperty("to_date")
	private String toDate;
}
