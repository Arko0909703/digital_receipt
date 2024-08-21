package com.photon.qreceipt.service;

import com.photon.qreceipt.request.SaveMetaRequest;
import com.photon.qreceipt.response.BaseResponse;

public interface UtilityService {
	public BaseResponse saveMeta(SaveMetaRequest request);
}
