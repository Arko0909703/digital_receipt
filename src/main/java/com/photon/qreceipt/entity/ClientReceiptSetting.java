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
@Table(name = "client_receipt_setting_invoice")
public class ClientReceiptSetting {
	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@Column(name = "client_id", columnDefinition = "BIGINT")
	private Long clientId;
	@Column(name = "brand_id", columnDefinition = "INTEGER")
	private Integer brandId;
	@Column(name = "invoice_position", columnDefinition = "BIGINT")
	private Long invoicePosition;
	@Column(name = "invoice_string_remove", columnDefinition = "Varchar(200)")
	private String stringToBeRemoved;
	@Column(name = "header1_position", columnDefinition = "BIGINT")
	private Long header1Position;
	@Column(name = "header2_position", columnDefinition = "BIGINT")
	private Long header2Position;
	@Column(name = "header3_position", columnDefinition = "BIGINT")
	private Long header3Position;
	@Column(name = "header4_position", columnDefinition = "BIGINT")
	private Long header4Position;
	@Column(name = "header5_position", columnDefinition = "BIGINT")
	private Long header5Position;
	@Column(name = "header6_position", columnDefinition = "BIGINT")
	private Long header6Position;
	@Column(name = "header7_position", columnDefinition = "BIGINT")
	private Long header7Position;
	@Column(name = "header8_position", columnDefinition = "BIGINT")
	private Long header8Position;
	@Column(name = "item_start_position", columnDefinition = "BIGINT")
	private Long itemStartPosition;
	@Column(name = "item_end_position", columnDefinition = "varchar(200)")
	private String itemEndString;
	@Column(name = "tax_start_position", columnDefinition = "varchar(200)")
	private String taxStartString;
	@Column(name = "tax_end_position", columnDefinition = "varchar(200)")
	private String taxEndString;
	@Column(name = "invoice_date_position", columnDefinition = "INTEGER")
	private Integer invoiceDate;
	@Column(name = "footer_start_string", columnDefinition = "varchar(200)")
	private String footerStartPoistion;
}
