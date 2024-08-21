package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.SignatureMaster;

public interface SignatureRepository extends JpaRepository<SignatureMaster, Long>{

	SignatureMaster findAllBySaasId(String saasId);
	
	

}
