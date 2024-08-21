package com.photon.qreceipt.service;

import com.photon.qreceipt.request.LoginRequest;
import com.photon.qreceipt.response.*;

import java.util.List;

public interface DashboardService {

	LoginResponse login(LoginRequest request);

	ListResponse getGenerated(String storeId, int page, String businessDate);

	ListResponse getReceiptData(String storeId, int page, String businessDate);

	ListResponse getReceipts(String storeId, String startBusinessDate, String endBusinessDate, String DownloadStatus, String ScannedStatus, int page );

	ListResponse getDownloaded(String storeId, int page, String businessDate);

	ListSummaryResponse getSummary(String saasId);

	ClientSummaryResponse getClientSummary(String saasId);

	CountResponse getCountGenerated(String saasId);

	CountResponse getCountDownload(String saasId);

	CountResponse getCountScanned(String saasId);

	 CO2Response getCo2EmissionSaved(String saasId);

	public LicenseSummaryResponse getLicenseSummary(String saasId);

	public List<DashboardLicenseResponse> processLicenses(String saasId);

}
