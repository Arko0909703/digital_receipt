package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photon.qreceipt.entity.MetaData;

@Repository
public interface SaveMetaDataRepository extends JpaRepository<MetaData, Long>{

}
