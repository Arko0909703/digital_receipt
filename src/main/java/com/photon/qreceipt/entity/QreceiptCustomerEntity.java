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
@Table(name = "qreceipt_customer")
public class QreceiptCustomerEntity {

	@Id
	@Column(name = "receipt_id", columnDefinition = "VARCHAR(20)")
	private Long receiptId;

	@Column(name = "business_date", columnDefinition = "VARCHAR(20)")
	private String businessDate;

	@Column(name = "user_email", columnDefinition = "VARCHAR(20)")
	private String email;

	@Column(name = "user_mobileno", columnDefinition = "VARCHAR(20)")
	private String mobileNo;
}
