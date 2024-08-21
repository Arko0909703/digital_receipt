package com.photon.qreceipt.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListSummaryResponse {

	@JsonProperty("data")
	List<SummaryResponse> data;
}
