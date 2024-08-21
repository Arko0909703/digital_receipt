package com.photon.qreceipt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.photon.qreceipt.entity.StoreMaster;

public interface StoreMasterRepository extends JpaRepository<StoreMaster, String> {

	@Query(value = "SELECT * FROM  store_master WHERE saas_id=?1", nativeQuery = true)
	List<StoreMaster> getSummary(String saasID);

	@Query(value = "SELECT * FROM  store_master WHERE store_id=?1", nativeQuery = true)
	StoreMaster getStoreDetail(String storeId);
}
