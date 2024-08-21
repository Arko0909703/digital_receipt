package com.photon.qreceipt.entity;

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
@Table(name = "save_meta")
public class MetaData {
	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "license_key", columnDefinition = "VARCHAR(50)")
	private String licenseKey;
	
	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;

	@Column(name = "store_id", columnDefinition = "VARCHAR(20)")
	private String storeId;

	@Column(name = "register_id", columnDefinition = "VARCHAR(20)")
	private String registerId;

	@Column(name = "business_date", columnDefinition = "DATETIME")
	private LocalDateTime businessDate;
}
