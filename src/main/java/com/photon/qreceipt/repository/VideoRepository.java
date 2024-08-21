package com.photon.qreceipt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.VideoData;

public interface VideoRepository extends JpaRepository<VideoData, Long>{

	Optional<VideoData> findByNameAndSaasId(String fileName,String saasId);

}