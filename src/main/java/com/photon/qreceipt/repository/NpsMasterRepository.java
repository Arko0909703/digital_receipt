package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.photon.qreceipt.entity.CoreReceiptEntity;
import com.photon.qreceipt.entity.NpsMaster;

@Repository
public interface NpsMasterRepository extends JpaRepository<NpsMaster, Long> {

	@Query(value = "SELECT COUNT(*) from nps_master WHERE nps_score > 8 and saas_id=?1 AND business_date BETWEEN ?2 AND ?3", nativeQuery = true)
	Long getPromotor(String saasId, String from, String to);

	@Query(value = "SELECT COUNT(*) from nps_master WHERE nps_score in (7,8) and saas_id=?1 AND business_date BETWEEN ?2 AND ?3", nativeQuery = true)
	Long getPassive(String saasId, String from, String to);

	@Query(value = "SELECT COUNT(*) from nps_master WHERE nps_score < 7 and saas_id=?1 AND business_date BETWEEN ?2 AND ?3", nativeQuery = true)
	Long getDetractor(String saasId, String from, String to);

	@Query(value = "SELECT COUNT(*) from nps_master WHERE nps_score > 8 and store_id=?1 AND business_date BETWEEN ?2 AND ?3", nativeQuery = true)
	Long getStorePromotor(String storeId, String from, String to);

	@Query(value = "SELECT COUNT(*) from nps_master WHERE nps_score in (7,8) and store_id=?1 AND business_date BETWEEN ?2 AND ?3", nativeQuery = true)
	Long getStorePassive(String storeId, String from, String to);

	@Query(value = "SELECT COUNT(*) from nps_master WHERE nps_score < 7 and store_id=?1 AND business_date BETWEEN ?2 AND ?3", nativeQuery = true)
	Long getStoreDetractor(String storeId, String from, String to);

	@Query(value = "SELECT * from nps_master WHERE receipt_id=?1", nativeQuery = true)
	NpsMaster check(String receiptId);

}
