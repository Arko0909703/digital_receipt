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
@Table(name = "item_details")
public class ItemDetails {
	@Id
	@Column(name = "id", columnDefinition = "BIGINT")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "invoice_number", columnDefinition = "VARCHAR(50)")
	private String invoiceNumber;
	@Column(name = "item_name", columnDefinition = "VARCHAR(100)")
	private String itemName;
	@Column(name = "item_qty", columnDefinition = "VARCHAR(3)")
	private String itemQty;
	@Column(name = "unit_price", columnDefinition = "VARCHAR(30)")
	private String unitPrice;
	@Column(name = "discount_price", columnDefinition = "VARCHAR(30)")
	private String discountPrice;
	@Column(name = "net_price", columnDefinition = "VARCHAR(30)")
	private String netPrice;
	@Column(name = "item_tax", columnDefinition = "VARCHAR(30)")
	private String itemTax;
	@Column(name = "item_tax_rate", columnDefinition = "VARCHAR(30)")
	private String itemTaxRate;
	@Column(name = "item_tax_type", columnDefinition = "VARCHAR(30)")
	private String itemTaxType;
	@Column(name = "line_number", columnDefinition = "Integer")
	private Integer lineNumber;
}
