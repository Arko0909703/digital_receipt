package com.photon.qreceipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.photon.qreceipt.entity.ClientReceiptSetting;

public interface ClientBrandSettingRepository extends JpaRepository<ClientReceiptSetting, Long> {
	public ClientReceiptSetting findByClientIdAndBrandId(Long clientId, Integer brandId);
}
