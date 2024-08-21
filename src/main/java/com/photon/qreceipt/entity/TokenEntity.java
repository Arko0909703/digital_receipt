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
@Table(name = "token")
public class TokenEntity {

	@Id
	@Column(name = "token_id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tokenId;
	@Column(name = "device_id", columnDefinition = "VARCHAR(50)")
	private String deviceId;
	@Column(name = "token", columnDefinition = "varchar(100)")
	private String token;
}
