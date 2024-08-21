package com.photon.qreceipt.service;

import com.photon.qreceipt.request.FromDateToDateRequest;
import com.photon.qreceipt.request.NpsRequest;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.response.FeatureResponse;
import com.photon.qreceipt.response.ListNpsSummaryResponse;
import com.photon.qreceipt.response.NpsResponse;

public interface NpsService {
	NpsResponse getPromotor(String saasId, FromDateToDateRequest reqest);

	NpsResponse getPassive(String saasId, FromDateToDateRequest request);

	NpsResponse getDetractor(String saasId, FromDateToDateRequest request);

	ListNpsSummaryResponse listNpsSummary(String saasId, FromDateToDateRequest request);

	BaseResponse checkNps(String receiptId);

	BaseResponse saveNpsDetails(NpsRequest request);

	FeatureResponse featureDetails(String saasId);
}
