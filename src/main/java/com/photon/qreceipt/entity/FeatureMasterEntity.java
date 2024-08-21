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
@Table(name = "feature_master")
public class FeatureMasterEntity {

	@Id
	@Column(name = "id", columnDefinition = "VARCHAR(20)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nps", columnDefinition = "VARCHAR(5)")
	private String nps;

	@Column(name = "loyalty", columnDefinition = "VARCHAR(5)")
	private String loyalty;

	@Column(name = "customer_info", columnDefinition = "VARCHAR(5)")
	private String customerInfo;

	@Column(name = "customer_em", columnDefinition = "VARCHAR(5)")
	private String customerEm;

	@Column(name = "promotion", columnDefinition = "VARCHAR(5)")
	private String promotion;

	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;
}
