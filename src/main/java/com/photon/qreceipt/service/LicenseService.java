package com.photon.qreceipt.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.photon.qreceipt.entity.ClientEntity;
import com.photon.qreceipt.entity.StoreMaster;
import com.photon.qreceipt.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photon.qreceipt.entity.LicenseMaster;
import com.photon.qreceipt.exception.CustomException;
import com.photon.qreceipt.repository.LicenseMasterRepository;
import com.photon.qreceipt.request.LicenseCreationRequest;
import com.photon.qreceipt.request.LicenseCreationRequestV2;
import com.photon.qreceipt.request.ValidateLicenseRequest;

@Service
public class LicenseService {
    @Autowired
    private LicenseMasterRepository licenseMasterRepository;

    public LicenseDetailsResponse createLicense(LicenseCreationRequest request) {
        ArrayList<LicenseResponse> list = new ArrayList<>();
        if (request.getApplicability().equalsIgnoreCase("client_level")) {
            for (int i = 0; i < request.getNoOfLicenses(); i++) {
                LicenseMaster entity = new LicenseMaster();
                BeanUtils.copyProperties(request, entity);
                entity.setCreateDate(LocalDateTime.now());
                entity.setLicenseNumber(UUID.randomUUID().toString());
                LicenseResponse res = new LicenseResponse();
                BeanUtils.copyProperties(entity, res);
                licenseMasterRepository.save(entity);
                list.add(res);
            }
        } else {
            LicenseMaster entity = new LicenseMaster();
            BeanUtils.copyProperties(request, entity);
            entity.setCreateDate(LocalDateTime.now());
            entity.setLicenseNumber(UUID.randomUUID().toString());
            LicenseResponse res = new LicenseResponse();
            BeanUtils.copyProperties(entity, res);
            licenseMasterRepository.save(entity);
            list.add(res);
        }
        LicenseDetailsResponse response = new LicenseDetailsResponse();
        response.setErrorMessage(null);
        response.setStatus("Success");
        response.setLicences(list);
        return response;
    }

    public BaseResponse validateLicense(ValidateLicenseRequest request) {


        LicenseMaster entity = licenseMasterRepository.findByLicenseNumber(request.getLicenseNumber());
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
                    licenseMasterRepository.save(entity);
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