package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.CustomerMedia;

public interface CustomerMediaRepository extends JpaRepository<CustomerMedia, Long>{

	CustomerMedia findBySaasId(String saasId);
	
}
