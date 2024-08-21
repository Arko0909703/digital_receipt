package com.photon.qreceipt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DashboardLicenseResponse {
        @JsonProperty("license_number")
        public String licenseNumber;

        @JsonProperty("client_id")
        public String saasId;

        @JsonProperty("client_name")
        public String clientName;

        @JsonProperty("store_id")
        private String storeId;

        @JsonProperty("store_name")
        private String storeName;

        @JsonProperty("store_location")
        private String storeLocation;

        @JsonProperty("status")
        private String status;

        @JsonProperty("effective_date")
        private LocalDateTime effectiveDate;

        @JsonProperty("expiry_date")
        private LocalDate expiryDate;

        @JsonProperty("trial_period")
        private int trialPeriod;

        @JsonProperty("license_period")
        private int licensePeriod;

        @JsonProperty("applicability")
        private String applicability;

}
