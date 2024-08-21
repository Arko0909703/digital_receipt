package com.photon.qreceipt.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class LicenseSummaryResponse {

    @JsonProperty("license_count")
    private long licenseCount;
    @JsonProperty("unused_license_count")
    private long unusedLicenseCount;
    @JsonProperty("used_license_count")
    private long usedLicenseCount;
    @JsonProperty("expired_license_count")
    private long expiredLicenseCount;
}
