package com.photon.qreceipt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.photon.qreceipt.entity.LicenseMaster;
import com.photon.qreceipt.entity.LicenseMasterV2;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseMasterRepositoryV2 extends JpaRepository<LicenseMasterV2, String> {
	LicenseMasterV2 findBySystemKey(String systemKey);
	LicenseMasterV2 findByLicenseNumber(String licenseNumber);
	List<LicenseMasterV2> findByRegistrationNo(String registrationNo);
	
	List<LicenseMasterV2> findByRegistrationCode(String registrationCode);
	@Query(value = "SELECT MAX(CAST(SUBSTRING(register_id, LENGTH(?2) + 1) AS UNSIGNED)) FROM license_master_new WHERE saas_id = ?1 AND registration_code=?2", nativeQuery = true)
	Integer findMaxRegisterIdBySaasIdAndRegistrationCode(String saasId, String registrationCode);

	@Query(value = "SELECT * from license_master_new WHERE registration_code=?1 order by license_id desc limit 1", nativeQuery = true)
	LicenseMasterV2 getLicenseDetailsByRegistrationCode(String registrationCode);
	
	@Query(value = "SELECT * from license_master_new WHERE saas_id=?1 order by license_id desc limit 1", nativeQuery = true)
	LicenseMasterV2 getLicense(String saasId);

	@Query(value= "SELECT COUNT(*) AS total FROM license_master_new WHERE saas_id=?1", nativeQuery = true)
	long getLicenseCount(String saasId);

	@Query(value= "SELECT COUNT(distinct registration_no) AS total FROM license_master_new WHERE saas_id=?1", nativeQuery = true)
	Integer getStoreCount(String saasId);
	
	@Query(value="SELECT COUNT(*) AS used FROM license_master_new WHERE saas_id=?1 AND effective_date IS NOT NULL", nativeQuery = true)
	long getUsedLicenseCount(String saasId);

	@Query(value="SELECT COUNT(*) AS unused FROM license_master_new WHERE saas_id=?1 AND effective_date is NULL", nativeQuery = true)
	long getUnusedLicenseCount(String saasId);

	@Query(value="SELECT COUNT(*) AS expired FROM license_master_new WHERE saas_id=?1 AND status='expired'", nativeQuery = true)
	long getExpiredLicenseCount(String saasId);
	
	@Query(value = "SELECT * from license_master_new WHERE saas_id=?1 order by license_id desc", nativeQuery = true)
	List<LicenseMasterV2> getLicenses(String saasId);







}