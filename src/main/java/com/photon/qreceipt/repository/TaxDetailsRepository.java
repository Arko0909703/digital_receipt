package com.photon.qreceipt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.TaxDetails;

public interface TaxDetailsRepository extends JpaRepository<TaxDetails, String> {
	public List<TaxDetails> findByInvoiceNumber(String invoiceNumber);
}
