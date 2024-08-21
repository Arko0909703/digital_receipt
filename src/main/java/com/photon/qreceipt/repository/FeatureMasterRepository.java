package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.photon.qreceipt.entity.FeatureMasterEntity;

@Repository
public interface FeatureMasterRepository extends JpaRepository<FeatureMasterEntity, Long> {
	@Query(value = "SELECT * from feature_master WHERE saas_id=?1", nativeQuery = true)
	FeatureMasterEntity getFeatureDetails(String saasId);
}
