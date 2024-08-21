package com.photon.qreceipt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "nps_master")
public class NpsMaster {

	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;

	@Column(name = "store_id", columnDefinition = "VARCHAR(20)")
	private String storeId;

	@Column(name = "receipt_id", columnDefinition = "VARCHAR(20)")
	private String receiptId;

	@Column(name = "nps_score", columnDefinition = "INT")
	private String npsScore;

	@Column(name = "nps_reason", columnDefinition = "VARCHAR(30)")
	private String npsReason;

	@Column(name = "business_date", columnDefinition = "VARCHAR(20)")
	private String businessDate;
}
