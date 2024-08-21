package com.photon.qreceipt.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "license_master")
public class LicenseMaster {

	@Id
	@Column(name = "license_id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long licenseId;

	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;

	@Column(name = "store_id", columnDefinition = "VARCHAR(20)")
	private String storeId;

	@Column(name = "register_id", columnDefinition = "VARCHAR(20)")
	private String registerId;

	@Column(name = "status", columnDefinition = "VARCHAR(20)")
	private String status;

	@Column(name = "create_date", columnDefinition = "DATETIME")
	private LocalDateTime createDate;
	
	@Column(name = "effective_date", columnDefinition = "DATETIME")
	private LocalDateTime effectiveDate;

	@Column(name = "expiry_date", columnDefinition = "DATE")
	private LocalDate expiryDate;

	@Column(name = "renew_date", columnDefinition = "DATETIME")
	private LocalDateTime renewDate;

	@Column(name = "trial_period", columnDefinition = "INT", nullable = false)
	private int trialPeriod;

	@Column(name = "license_period", columnDefinition = "INT")
	private int licensePeriod;
	
	@Column(name = "license_type", columnDefinition = "VARCHAR(10)")
	private String licenseType;
	
	@Column(name = "license_number", columnDefinition = "VARCHAR(50)")
	private String licenseNumber;
	
	@Column(name = "applicability", columnDefinition = "VARCHAR(20)")
	private String applicability;

}
