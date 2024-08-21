package com.photon.qreceipt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.photon.qreceipt.entity.CoreReceiptEntity;

@Repository
public interface CoreReceiptRepository extends JpaRepository<CoreReceiptEntity, Long> {

	@Query(value = "SELECT * from qreceipt_core WHERE store_id=?1 and register_id=?2 and business_date=?3 and saas_id=?4 order by receipt_id desc limit 1", nativeQuery = true)
	CoreReceiptEntity getReceipt(String storeId, String registerId, String businessDate, String saasId);

	@Query(value = "SELECT * from qreceipt_core WHERE store_id=?1 and register_id=?2 and business_date=?3 and saas_id=?4 and device_id=?5 order by receipt_id desc limit 1", nativeQuery = true)
	CoreReceiptEntity getReceiptByDeviceId(String storeId, String registerId, String businessDate, String saasId,
			String deviceId);

	@Query(value = "SELECT * FROM qreceipt_core WHERE store_id=?1 and business_date=?2", nativeQuery = true)
	List<CoreReceiptEntity> getListGenerated(String storeId, String businessDate);

	@Query(value = "SELECT * FROM qreceipt_core WHERE store_id=?1 and business_date=?2 LIMIT ?4 OFFSET ?3", nativeQuery = true)
	List<CoreReceiptEntity> getReceiptData(String storeId, String businessDate, int page, int limit);

	@Query(value = "SELECT * FROM qreceipt_core WHERE store_id=?1 AND business_date BETWEEN ?2 AND ?3  LIMIT ?5 OFFSET ?4", nativeQuery = true)
	List<CoreReceiptEntity> getReceipts(String storeId, String startBusinessDate, String endBusinessDate, int page, int limit);

	@Query(value = "SELECT register_id FROM register_master WHERE register_nick=?1", nativeQuery = true)
	String getRegisterIDByRegisterNick(String nick);

	@Query(value = "SELECT COUNT(*) FROM qreceipt_core WHERE store_id=?1 and business_date=?2", nativeQuery = true)
	long getReceiptDataCount(String storeId, String businessDate);

	@Query(value = "SELECT COUNT(*) FROM qreceipt_core WHERE store_id=?1 and business_date BETWEEN ?2 AND ?3", nativeQuery = true)
	long getReceiptsCountWithFilter(String storeId, String startBusinessDate, String endBusinessDate);

	@Query(value = "SELECT * FROM qreceipt_core WHERE store_id=?1 and business_date=?3 and receipt_download=?2", nativeQuery = true)
	List<CoreReceiptEntity> getListDownloaded(String storeId, String download, String businessDate);

	@Query(value = "SELECT COUNT(*) from qreceipt_core WHERE store_id=?1 and business_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE()", nativeQuery = true)
	float getStoreGenerated(String storeId);

	@Query(value = "SELECT COUNT(*) from qreceipt_core WHERE store_id=?1 and receipt_download=?2 and business_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE()", nativeQuery = true)
	float getStoreDownload(String storeId, String download);

	@Query(value = "SELECT COUNT(*) from qreceipt_core WHERE saas_id=?1 and business_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE()", nativeQuery = true)
	long getCountGenerated(String saasId);

	@Query(value = "SELECT COUNT(*) from qreceipt_core WHERE saas_id=?1 and receipt_download=?2 and business_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE()", nativeQuery = true)
	long getCountDownload(String saasId, String download);

	@Query(value = "SELECT COUNT(*) from qreceipt_core WHERE saas_id=?1 and receipt_scanned=?2 and business_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE()", nativeQuery = true)
	long getCountScanned(String saasId, String download);

	@Query(value = "SELECT COUNT(*) from qreceipt_core WHERE store_id=?1 and receipt_scanned=?2 and business_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE()", nativeQuery = true)
	long getStoreScanned(String saasId, String download);
}
