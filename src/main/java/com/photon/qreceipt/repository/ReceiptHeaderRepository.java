package com.photon.qreceipt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.ReceiptHeader;

public interface ReceiptHeaderRepository extends JpaRepository<ReceiptHeader, String> {
	public List<ReceiptHeader> findByInvoiceNumber(String invoiceNumber);
}
