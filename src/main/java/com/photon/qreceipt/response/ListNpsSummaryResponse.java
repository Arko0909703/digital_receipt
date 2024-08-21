package com.photon.qreceipt.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ListNpsSummaryResponse {
	@JsonProperty("data")
	List<NpsSummaryResponse> data;
}
