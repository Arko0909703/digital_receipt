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
@Table(name = "client_details")
public class ClientEntity {
	@Id
	@Column(name = "client_id", columnDefinition = "VARCHAR(20)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String clientId;
	@Column(name = "client_name", columnDefinition = "VARCHAR(20)")
	private String clientName;
	@Column(name = "cleint_address", columnDefinition = "TEXT")
	private String cleintAddress;
	@Column(name = "client_url", columnDefinition = "VARCHAR(200)")
	private String clientUrl;
	@Column(name = "client_logo", columnDefinition = "VARCHAR(200)")
	private String clientLogo;
	@Column(name = "is_active", columnDefinition = "VARCHAR(1)")
	private String isActive;
	@Column(name = "create_date", columnDefinition = "DATETIME")
	private LocalDateTime createDate;
	@Column(name = "cleint_promo", columnDefinition = "TEXT")
	private String cleintPromo;
}
