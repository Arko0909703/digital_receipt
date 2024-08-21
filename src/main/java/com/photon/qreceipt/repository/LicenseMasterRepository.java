package com.photon.qreceipt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.photon.qreceipt.entity.LicenseMaster;

public interface LicenseMasterRepository extends JpaRepository<LicenseMaster, String> {
	LicenseMaster findByLicenseNumber(String licenseNumber);
	@Query(value = "SELECT * from license_master WHERE saas_id=?1 order by license_id desc limit 1", nativeQuery = true)
	LicenseMaster getLicense(String saasId);

	@Query(value= "SELECT COUNT(*) AS total FROM license_master WHERE saas_id=?1", nativeQuery = true)
	long getLicenseCount(String saasId);

	@Query(value="SELECT COUNT(*) AS used FROM license_master WHERE saas_id=?1 AND effective_date IS NOT NULL", nativeQuery = true)
	long getUsedLicenseCount(String saasId);

	@Query(value="SELECT COUNT(*) AS unused FROM license_master WHERE saas_id=?1 AND effective_date is NULL", nativeQuery = true)
	long getUnusedLicenseCount(String saasId);

	@Query(value="SELECT COUNT(*) AS expired FROM license_master WHERE saas_id=?1 AND status='expired'", nativeQuery = true)
	long getExpiredLicenseCount(String saasId);

	@Query(value = "SELECT * from license_master WHERE saas_id=?1 order by license_id desc", nativeQuery = true)
	List<LicenseMaster> getLicenses(String saasId);





}