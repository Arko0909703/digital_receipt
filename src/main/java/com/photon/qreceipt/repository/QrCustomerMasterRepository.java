package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.QrCustomerMaster;

public interface QrCustomerMasterRepository extends JpaRepository<QrCustomerMaster, Long> {

}