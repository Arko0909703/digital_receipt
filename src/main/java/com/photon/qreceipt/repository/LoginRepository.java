package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photon.qreceipt.entity.LoginMaster;

@Repository
public interface LoginRepository extends JpaRepository<LoginMaster, Long> {

	LoginMaster findByUserNameAndPassword(String userName, String password);
}
