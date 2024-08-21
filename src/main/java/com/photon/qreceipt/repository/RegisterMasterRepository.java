package com.photon.qreceipt.repository;


import com.photon.qreceipt.entity.RegisterDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.RegisterMaster;
import org.springframework.data.jpa.repository.Query;

public interface RegisterMasterRepository extends JpaRepository<RegisterMaster, Long>{
    @Query(value = "SELECT register_id FROM register_master WHERE register_nick=?1", nativeQuery = true)
    String getRegisterIDByNick(String nick);

    @Query(value = "SELECT r.store_id AS storeId, r.register_id AS registerId, s.saas_id AS saasId FROM register_master r JOIN store_master s ON s.store_id=r.store_id WHERE register_nick=?1", nativeQuery = true)
    RegisterDetails getRegisterDetails(String nick);
}
