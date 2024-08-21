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
@Table(name = "qrecipt_promodetails")
public class QreceiptPromo {
	@Id
	@Column(name = "promo_id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long promoId;
	@Column(name = "client_id", columnDefinition = "BIGINT")
	private Long clientId;

	@Column(name = "store_id", columnDefinition = "VARCHAR(20)")
	private String storeId;

	@Column(name = "register_id", columnDefinition = "VARCHAR(20)")
	private String registerId;

	@Column(name = "qreceipt_promo", columnDefinition = "TEXT")
	private String qreceiptPromo;

}
