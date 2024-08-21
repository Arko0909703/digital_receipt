package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.QreceiptCustomerEntity;

public interface QreceiptCustomerRepository extends JpaRepository<QreceiptCustomerEntity, Long> {

}
