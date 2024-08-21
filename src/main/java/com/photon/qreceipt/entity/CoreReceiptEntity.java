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
@Table(name = "qreceipt_core")
public class CoreReceiptEntity {
	@Id
	@Column(name = "receipt_id", columnDefinition = "VARCHAR(20)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long receiptId;

	@Column(name = "invoice_number", columnDefinition = "VARCHAR(50)")
	private String invoiceNumber;

	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;

	@Column(name = "store_id", columnDefinition = "VARCHAR(20)")
	private String storeId;

	@Column(name = "register_id", columnDefinition = "VARCHAR(20)")
	private String registerId;

	@Column(name = "business_date", columnDefinition = "VARCHAR(20)")
	private String businessDate;

	@Column(name = "uploaded_by", columnDefinition = "VARCHAR(20)")
	private String uploadedBy;

	@Column(name = "receipt_content", columnDefinition = "TEXT")
	private String receiptContent;

	@Column(name = "create_date", columnDefinition = "DATETIME")
	private LocalDateTime createDate;

	@Column(name = "file_location", columnDefinition = "VARCHAR(100)")
	private String fileLocation;

	@Column(name = "receipt_delivered", columnDefinition = "VARCHAR(1)")
	private String receiptDelivered;

	@Column(name = "device_id", columnDefinition = "VARCHAR(50)")
	private String deviceId;

	@Column(name = "receipt_download", columnDefinition = "VARCHAR(2)")
	private String receiptDownloadStatus;

	@Column(name = "receipt_scanned", columnDefinition = "VARCHAR(2)")
	private String receiptScannedStatus;

	@Column(name = "choice", columnDefinition = "VARCHAR(1)")
	private String choice;

	@Column(name = "mobile_number", columnDefinition = "VARCHAR(12)")
	private String mobileNumber;
	
	@Column(name = "country_code", columnDefinition = "VARCHAR(10)")
	private String countryCode;
}
