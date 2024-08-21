package com.photon.qreceipt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.ImageData;

public interface ImageRepository extends JpaRepository<ImageData, Long>{

	Optional<ImageData> findByNameAndSaasId(String fileName, String saasId);

}
