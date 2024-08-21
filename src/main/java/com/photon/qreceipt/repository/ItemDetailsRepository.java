package com.photon.qreceipt.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.photon.qreceipt.entity.ItemDetails;

public interface ItemDetailsRepository extends JpaRepository<ItemDetails, String> {
	@Query(value = "SELECT  it.id,it.discount_price,it.item_tax_type,it.item_tax,item_tax_rate,a.line_number,it.invoice_number,it.net_price,it.item_qty,it.unit_price,a.item_name FROM item_details it LEFT JOIN (SELECT invoice_number,line_number, GROUP_CONCAT( item_name) AS item_name FROM item_details GROUP BY invoice_number,line_number) a ON it.invoice_number=a.invoice_number AND it.line_number=a.line_number  WHERE it.item_qty!='' AND it.invoice_number=?", nativeQuery = true)
	public List<ItemDetails> findByInvoiceNumber(String invoiceNumber);
}
