package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CO2Response {
    @JsonProperty("count")
    private double count;
}
