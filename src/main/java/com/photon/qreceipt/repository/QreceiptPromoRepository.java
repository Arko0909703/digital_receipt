package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.photon.qreceipt.entity.QreceiptPromo;

public interface QreceiptPromoRepository extends JpaRepository<QreceiptPromo, Long> {
	@Query(value = "SELECT qreceipt_promo from qrecipt_promodetails WHERE store_id=?1 and register_id=?2 and client_id=?3  limit 1", nativeQuery = true)
	String getQreceiptPromo(String storeId, String registerId, String saasId);
}
