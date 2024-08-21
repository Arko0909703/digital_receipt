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
@Table(name = "other_reference")
public class OtherReferences {
	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "invoice_number", columnDefinition = "VARCHAR(50)")
	private String invoiceNumber;
	@Column(name = "reference1", columnDefinition = "VARCHAR(500)")
	private String reference1;
	@Column(name = "reference2", columnDefinition = "VARCHAR(500)")
	private String reference2;
	@Column(name = "reference3", columnDefinition = "VARCHAR(500)")
	private String reference3;
	@Column(name = "reference4", columnDefinition = "VARCHAR(500)")
	private String reference4;
	@Column(name = "reference5", columnDefinition = "VARCHAR(500)")
	private String reference5;
	@Column(name = "reference6", columnDefinition = "VARCHAR(500)")
	private String reference6;
	@Column(name = "reference7", columnDefinition = "VARCHAR(500)")
	private String reference7;
	@Column(name = "reference8", columnDefinition = "VARCHAR(500)")
	private String reference8;
	@Column(name = "reference9", columnDefinition = "VARCHAR(500)")
	private String reference9;
	@Column(name = "reference10", columnDefinition = "VARCHAR(500)")
	private String reference10;
	@Column(name = "otherReferences", columnDefinition = "text")
	private String allnonparsedReferences;

}
