package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClientSummaryResponse {

    @JsonProperty("company_name")
    private String companyName;


    @JsonProperty("invoice_generated")
    private long invoiceGenerated;

    @JsonProperty("invoice_download")
    private long invoiceDownload;

    @JsonProperty("invoice_scanned")
    private long invoiceScanned;

    @JsonProperty("co2_emission_saved")
    private double co2EmissionSaved;

}
