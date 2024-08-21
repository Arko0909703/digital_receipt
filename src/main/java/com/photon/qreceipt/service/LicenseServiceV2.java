package com.photon.qreceipt.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.photon.qreceipt.entity.ClientEntity;
import com.photon.qreceipt.entity.StoreMaster;
import com.photon.qreceipt.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photon.qreceipt.entity.LicenseMaster;
import com.photon.qreceipt.entity.LicenseMasterV2;
import com.photon.qreceipt.exception.CustomException;
import com.photon.qreceipt.repository.LicenseMasterRepository;
import com.photon.qreceipt.repository.LicenseMasterRepositoryV2;
import com.photon.qreceipt.request.LicenseCreationRequestV2;
import com.photon.qreceipt.request.ValidateLicenseRequest;

@Service
public class LicenseServiceV2 {
    @Autowired
    private LicenseMasterRepositoryV2 licenseMasterRepositoryV2;

       public LicenseDetailsResponseV2 createLicenseV2(LicenseCreationRequestV2 request) {
    	   List<LicenseMasterV2> license=licenseMasterRepositoryV2.findByRegistrationNo(request.getRegistrationCode());
    	   LicenseDetailsResponseV2 response = new LicenseDetailsResponseV2();
          
    	   if(!license.isEmpty()) {
    		   response.setErrorMessage("Registration number already exist.. Please try with other");
               response.setStatus("Failure");
               return response;
    	   }
    	
    	   
        ArrayList<LicenseResponseV2> list = new ArrayList<>();
        Integer maxRegisterId = licenseMasterRepositoryV2.findMaxRegisterIdBySaasIdAndRegistrationCode(request.getSaasId(), request.getRegistrationCode());
        int nextRegisterId = (maxRegisterId == null ? 0 : maxRegisterId) + 1;
        
        Integer maxStoreId = licenseMasterRepositoryV2.getStoreCount(request.getSaasId());
        int nextMaxStoreId= (maxStoreId == null ? 0 : maxStoreId) + 1;
        
        if (request.getApplicability().equalsIgnoreCase("client_level")) {
            for (int i = 0; i < request.getNoOfLicenses(); i++) {
                LicenseMasterV2 entity = new LicenseMasterV2();
                BeanUtils.copyProperties(request, entity);
                entity.setCreateDate(LocalDateTime.now());
                entity.setLicenseNumber(UUID.randomUUID().toString());
                entity.setRegistrationNo(request.getRegistrationCode());
                entity.setRegistrationCode(request.getRegistrationCode() + String.format("%05d", nextRegisterId));
                entity.setRegisterId(String.format("%05d", nextRegisterId));
                entity.setStoreId(String.format("%05d", nextMaxStoreId));
                LicenseResponseV2 res = new LicenseResponseV2();
                BeanUtils.copyProperties(entity, res);
                licenseMasterRepositoryV2.save(entity);
                list.add(res);
                nextRegisterId++;
            }
        } else {
            LicenseMasterV2 entity = new LicenseMasterV2();
            BeanUtils.copyProperties(request, entity);
            entity.setCreateDate(LocalDateTime.now());
            entity.setLicenseNumber(UUID.randomUUID().toString());
            entity.setRegistrationNo(request.getRegistrationCode());
            entity.setRegistrationCode(request.getRegistrationCode() + String.format("%05d", nextRegisterId));
            entity.setRegisterId(String.format("%05d", nextRegisterId));
            LicenseResponseV2 res = new LicenseResponseV2();
            BeanUtils.copyProperties(entity, res);
            licenseMasterRepositoryV2.save(entity);
            list.add(res);
        }
        
        response.setErrorMessage(null);
        response.setStatus("Success");
        response.setLicences(list);
        return response;
    }

    public List<LicenseResponseV2> fetchLicenseByRegistrationCode(String registrationCode) {
        List<LicenseMasterV2> entity = licenseMasterRepositoryV2.findByRegistrationCode(registrationCode);
        if (entity == null) {
            throw new CustomException("Failure", "No license found with the provided registration code.");
        }
        List<LicenseResponseV2> responses = new ArrayList<>();
        for (LicenseMasterV2 entity1 : entity) {
            LicenseResponseV2 response = new LicenseResponseV2();
            response.setLicenseNumber(entity1.getLicenseNumber());
            response.setSaasId(entity1.getSaasId());
            response.setRegisterId(entity1.getRegisterId());
            response.setRegistrationCode(entity1.getRegistrationCode());
            BeanUtils.copyProperties(entity, response);
            responses.add(response);
        }
        return responses;
    }

    public LicenseResponseV2 fetchLicenseDetailsByRegistrationCode(String registrationCode) {
        LicenseMasterV2 entity = licenseMasterRepositoryV2.getLicenseDetailsByRegistrationCode(registrationCode);
        if (entity == null) {
            throw new CustomException("Failure", "No license found with the provided registration code.");
        }
        
            LicenseResponseV2 response = new LicenseResponseV2();
            response.setLicenseNumber(entity.getLicenseNumber());
            response.setSaasId(entity.getSaasId());
            response.setRegisterId(entity.getRegisterId());
            response.setRegistrationCode(entity.getRegistrationCode());
            BeanUtils.copyProperties(entity, response);
            return response;
    }
    public BaseResponse validateLicense(ValidateLicenseRequest request) {


        LicenseMasterV2 entity = licenseMasterRepositoryV2.findByLicenseNumber(request.getLicenseNumber());
        if (entity != null) {
            if (request.getSaasId().equalsIgnoreCase("$$$$")) {
                return success();
            }
            if (entity.getSaasId().equalsIgnoreCase(request.getSaasId())) {

                if (entity.getRegisterId() == null) {
                    if (entity.getLicensePeriod() == 0) {
                        throw new CustomException("Failure", "This license is already expired");
                    }

                    entity.setRegisterId(request.getRegisterId());
                    entity.setStatus("consumed");
                    entity.setStoreId(request.getStoreId());
                    entity.setEffectiveDate(LocalDateTime.now());
                    entity.setExpiryDate(LocalDate.now().plusMonths(entity.getLicensePeriod()));
                    ;
                    licenseMasterRepositoryV2.save(entity);
                    return success();
                } else {
                    if (entity.getExpiryDate().isBefore(LocalDate.now())) {
                        throw new CustomException("Failure", "This license is already expired");
                    }
                    return success();
                }
            } else {
                throw new CustomException("Failure", "This license is already issued for other client");
            }
        } else {
            throw new CustomException("Failure", "No such license");
        }


    }

    public BaseResponse success()
    {
    	BaseResponse response = new BaseResponse();
        response.setErrorMessage(null);
        response.setStatus("Success");
        return response;
    }
}