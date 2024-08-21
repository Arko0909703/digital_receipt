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
@Table(name = "tax_details")
public class TaxDetails {
	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "invoice_number", columnDefinition = "VARCHAR(50)")
	private String invoiceNumber;
	@Column(name = "tax_type", columnDefinition = "VARCHAR(10)")
	private String taxType;
	@Column(name = "tax_percent", columnDefinition = "VARCHAR(5)")
	private String taxPercent;
	@Column(name = "tax_total", columnDefinition = "VARCHAR(7)")
	private String taxTotal;

}
