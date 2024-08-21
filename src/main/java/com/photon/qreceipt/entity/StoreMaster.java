package com.photon.qreceipt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "store_master")
public class StoreMaster {

	@Id
	@Column(name = "store_id", columnDefinition = "VARCHAR(20)")
	private String storeId;

	@Column(name = "store_name", columnDefinition = "VARCHAR(100)")
	private String storeName;

	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;

	@Column(name = "store_country", columnDefinition = "VARCHAR(20)")
	private String storeCountry;

	@Column(name = "store_location", columnDefinition = "VARCHAR(20)")
	private String storeLocation;

	@Column(name = "store_address1", columnDefinition = "VARCHAR(250)")
	private String storeAddress1;

	@Column(name = "store_address2", columnDefinition = "VARCHAR(250)")
	private String storeAddress2;

	@Column(name = "store_email", columnDefinition = "VARCHAR(30)")
	private String storeEmail;

	@Column(name = "store_phone", columnDefinition = "VARCHAR(20)")
	private String storePhone;

	@Column(name = "store_trn", columnDefinition = "VARCHAR(40)")
	private String storeTrn;
}
