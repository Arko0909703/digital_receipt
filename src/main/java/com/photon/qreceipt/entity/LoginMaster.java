package com.photon.qreceipt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "login_master")
public class LoginMaster {

	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;

	@Column(name = "status", columnDefinition = "VARCHAR(20)")
	private String status;

	@Column(name = "company_name", columnDefinition = "VARCHAR(50)")
	private String companyName;

	@Column(name = "user_name", columnDefinition = "VARCHAR(50)")
	private String userName;

	@Column(name = "password", columnDefinition = "VARCHAR(50)")
	private String password;
}
