package com.photon.qreceipt.service;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photon.qreceipt.entity.MetaData;
import com.photon.qreceipt.repository.SaveMetaDataRepository;
import com.photon.qreceipt.request.SaveMetaRequest;
import com.photon.qreceipt.response.BaseResponse;
@Service
public class UtilityServiceImpl implements UtilityService{

	@Autowired
	SaveMetaDataRepository saveMetaDataRepository;
	@Override
	public BaseResponse saveMeta(SaveMetaRequest request) {
		MetaData saveMeta = new MetaData();
		BeanUtils.copyProperties(request, saveMeta);
		saveMeta.setBusinessDate(LocalDateTime.now());
		saveMetaDataRepository.save(saveMeta);
		BaseResponse response = new BaseResponse();
		response.setStatus("Success");
		response.setErrorMessage(null);
		return response;
	}

}
