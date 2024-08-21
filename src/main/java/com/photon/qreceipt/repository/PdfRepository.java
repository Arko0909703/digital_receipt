package com.photon.qreceipt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.PdfData;
import com.photon.qreceipt.entity.VideoData;

public interface PdfRepository extends JpaRepository<PdfData, Long>{

	Optional<PdfData> findByNameAndSaasId(String fileName,String saasId);

}
