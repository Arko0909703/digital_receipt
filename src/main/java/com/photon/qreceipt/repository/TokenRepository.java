package com.photon.qreceipt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, String> {
	public TokenEntity findByDeviceId(String deviceId);
}
