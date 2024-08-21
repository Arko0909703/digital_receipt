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
@Table(name = "footer_details")
public class FooterDetails {
	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "invoice_number", columnDefinition = "VARCHAR(50)")
	private String invoiceNumber;
	@Column(name = "footer1", columnDefinition = "VARCHAR(100)")
	private String footer1;
	@Column(name = "footer2", columnDefinition = "VARCHAR(100)")
	private String footer2;
	@Column(name = "footer3", columnDefinition = "VARCHAR(100)")
	private String footer3;
	@Column(name = "footer4", columnDefinition = "VARCHAR(100)")
	private String footer4;
	@Column(name = "footer5", columnDefinition = "VARCHAR(100)")
	private String footer5;
	@Column(name = "footer6", columnDefinition = "VARCHAR(100)")
	private String footer6;
	@Column(name = "footer7", columnDefinition = "VARCHAR(100)")
	private String footer7;
	@Column(name = "footer8", columnDefinition = "VARCHAR(100)")
	private String footer8;
	@Column(name = "footer9", columnDefinition = "VARCHAR(100)")
	private String footer9;
}
