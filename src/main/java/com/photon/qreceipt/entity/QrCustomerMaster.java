package com.photon.qreceipt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "qrcustomer_master")
public class QrCustomerMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_Name", columnDefinition = "VARCHAR(20)")
	private String firstName;

	@Column(name = "last_Name", columnDefinition = "VARCHAR(20)")
	private String lastName;

	@Column(name = "country", columnDefinition = "VARCHAR(20)")
	private String country;

	@Column(name = "city", columnDefinition = "VARCHAR(20)")
	private String city;

	@Column(name = "email", columnDefinition = "VARCHAR(40)")
	private String email;

	@Column(name = "phone", columnDefinition = "VARCHAR(15)")
	private String phone;

	@Column(name = "gender", columnDefinition = "VARCHAR(10)")
	private String gender;

	@Column(name = "nationality", columnDefinition = "VARCHAR(15)")
	private String nationality;

	@Column(name = "prefered_language", columnDefinition = "VARCHAR(20)")
	private String preferedLanguage;

	@Column(name = "dob", columnDefinition = "VARCHAR(20)")
	public String dob;

	@Column(name = "maratial_status", columnDefinition = "VARCHAR(20)")
	public String maratialStatus;
}
