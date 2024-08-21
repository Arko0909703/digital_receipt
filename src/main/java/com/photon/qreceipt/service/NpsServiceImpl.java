package com.photon.qreceipt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photon.qreceipt.entity.ClientEntity;
import com.photon.qreceipt.entity.FeatureMasterEntity;
import com.photon.qreceipt.entity.NpsMaster;
import com.photon.qreceipt.entity.StoreMaster;
import com.photon.qreceipt.exception.CustomException;
import com.photon.qreceipt.repository.ClientRepository;
import com.photon.qreceipt.repository.CoreReceiptRepository;
import com.photon.qreceipt.repository.FeatureMasterRepository;
import com.photon.qreceipt.repository.NpsMasterRepository;
import com.photon.qreceipt.repository.StoreMasterRepository;
import com.photon.qreceipt.request.FromDateToDateRequest;
import com.photon.qreceipt.request.NpsRequest;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.response.FeatureResponse;
import com.photon.qreceipt.response.ListNpsSummaryResponse;
import com.photon.qreceipt.response.ListSummaryResponse;
import com.photon.qreceipt.response.NpsResponse;
import com.photon.qreceipt.response.NpsSummaryResponse;
import com.photon.qreceipt.response.SummaryResponse;

@Service
public class NpsServiceImpl implements NpsService {

	@Autowired
	private NpsMasterRepository npsMasterRepository;

	@Autowired
	private StoreMasterRepository storeMasterRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private CoreReceiptRepository coreRepository;

	@Autowired
	private FeatureMasterRepository featureMasterRepository;

	private final static String message = "Data fetch successfully!!";

	@Override
	public NpsResponse getPromotor(String saasId, FromDateToDateRequest request) {
		Long count = npsMasterRepository.getPromotor(saasId, request.getFromDate(), request.getToDate());
		NpsResponse response = new NpsResponse();
		response.setCount(count);
		response.setMessage(message);
		return response;
	}

	@Override
	public NpsResponse getPassive(String saasId, FromDateToDateRequest request) {
		Long count = npsMasterRepository.getPassive(saasId, request.getFromDate(), request.getToDate());
		NpsResponse response = new NpsResponse();
		response.setCount(count);
		response.setMessage(message);
		return response;

	}

	@Override
	public NpsResponse getDetractor(String saasId, FromDateToDateRequest request) {
		Long count = npsMasterRepository.getDetractor(saasId, request.getFromDate(), request.getToDate());
		NpsResponse response = new NpsResponse();
		response.setCount(count);
		response.setMessage(message);
		return response;
	}

	@Override
	public ListNpsSummaryResponse listNpsSummary(String saasId, FromDateToDateRequest request) {
		List<StoreMaster> entity = storeMasterRepository.getSummary(saasId);
		ClientEntity client = clientRepository.getClient(saasId);
		if (entity == null || client == null) {
			throw new CustomException("false", "SaasId doesn't Exist");
		}
		List<NpsSummaryResponse> listResponse = new ArrayList<>();
		ListNpsSummaryResponse response = new ListNpsSummaryResponse();

		for (StoreMaster item : entity) {
			NpsSummaryResponse nsr = new NpsSummaryResponse();
			nsr.setCompanyName(client.getClientName());
			nsr.setStoreName(item.getStoreName());
			Long promotor = npsMasterRepository.getStorePromotor(item.getStoreId(), request.getFromDate(),
					request.getToDate());
			Long passive = npsMasterRepository.getStorePassive(item.getStoreId(), request.getFromDate(),
					request.getToDate());
			Long detractor = npsMasterRepository.getStoreDetractor(item.getStoreId(), request.getFromDate(),
					request.getToDate());
			Long totalInvoiceGenerated = (long) coreRepository.getStoreGenerated(item.getStoreId());
			float npsScore = (promotor - detractor);
			float totalFeedback = (promotor + passive + detractor);
			nsr.setDectractor(detractor);
			nsr.setPromotor(promotor);
			nsr.setPassive(passive);
			nsr.setNpsScore(npsScore / 100);
			nsr.setStoreId(item.getStoreId());
			nsr.setInvoiceGenerated(totalInvoiceGenerated);
			nsr.setResponseRate(totalFeedback / totalInvoiceGenerated);
			listResponse.add(nsr);
		}
		response.setData(listResponse);

		return response;
	}

	@Override
	public BaseResponse checkNps(String receiptId) {
		NpsMaster entity = npsMasterRepository.check(receiptId);
		BaseResponse response = new BaseResponse();

		if (entity != null) {
			response.setStatus("false");
			response.setErrorMessage("User feedback already present");
			return response;
		}
		response.setStatus("true");
		response.setErrorMessage("User feedback not present");
		return response;
	}

	public BaseResponse saveNpsDetails(NpsRequest request) {
		BaseResponse response = new BaseResponse();
		NpsMaster entity = new NpsMaster();
		BeanUtils.copyProperties(request, entity);
		npsMasterRepository.save(entity);
		response.setStatus("true");
		response.setErrorMessage("Data saved successfully");
		return response;
	}

	@Override
	public FeatureResponse featureDetails(String saasId) {
		FeatureMasterEntity entity = featureMasterRepository.getFeatureDetails(saasId);
		FeatureResponse response = new FeatureResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

}
