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
@Table(name = "qreceipt_header")
public class ReceiptHeader {
	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "invoice_number", columnDefinition = "VARCHAR(50)")
	private String invoiceNumber;
	@Column(name = "invoice_date", columnDefinition = "VARCHAR(30)")
	private String invoiceDate;
	@Column(name = "header1", columnDefinition = "VARCHAR(200)")
	private String header1;
	@Column(name = "header2", columnDefinition = "VARCHAR(200)")
	private String header2;
	@Column(name = "header3", columnDefinition = "VARCHAR(200)")
	private String header3;
	@Column(name = "header4", columnDefinition = "VARCHAR(200)")
	private String header4;
	@Column(name = "header5", columnDefinition = "VARCHAR(200)")
	private String header5;
	@Column(name = "header6", columnDefinition = "VARCHAR(200)")
	private String header6;
	@Column(name = "header7", columnDefinition = "VARCHAR(200)")
	private String header7;
	@Column(name = "header8", columnDefinition = "VARCHAR(200)")
	private String header8;
}
