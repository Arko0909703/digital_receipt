package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.photon.qreceipt.entity.ClientEntity;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {
	@Query(value = "SELECT * FROM  client_details WHERE client_id=?1", nativeQuery = true)
	ClientEntity getClient(String saasID);
}
