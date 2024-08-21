package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.FooterDetails;

public interface FooterDetailsRepository extends JpaRepository<FooterDetails, String> {
	public FooterDetails findByInvoiceNumber(String invoiceNumber);
}
