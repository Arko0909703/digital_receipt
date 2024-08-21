package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RegisterDetailsResponse {
    @JsonProperty("store_id")
    private String storeId;

    @JsonProperty("register_id")
    private String registerId;

    @JsonProperty("saas_id")
    private String saasId;

}
