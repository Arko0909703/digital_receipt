package com.photon.qreceipt.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.photon.qreceipt.entity.*;
import com.photon.qreceipt.repository.*;
import com.photon.qreceipt.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photon.qreceipt.exception.CustomException;
import com.photon.qreceipt.request.LoginRequest;

@Service
public class DashboardServiceImpl implements DashboardService {

	private final int limit = 10;
	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private CoreReceiptRepository coreReceiptRepository;

	@Autowired
	private StoreMasterRepository storeMasterRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private LicenseMasterRepository licenseMasterRepository;

	private final int NUM_OF_PAGES = 1;

	private final double CO2_EMISSION_FACTOR = 2.5; // in grams


	@Override
	public LoginResponse login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		LoginMaster entity = loginRepository.findByUserNameAndPassword(request.getUserName(), request.getPassword());

		if (entity == null) {
			throw new CustomException("false", "User doesn't exist..!!");
		}
		response.setUserName(entity.getUserName());
		response.setSaasId(entity.getSaasId());
		response.setCompanyName(entity.getCompanyName());

		return response;
	}

	@Override
	public ListResponse getGenerated(String storeId, int page, String businessDate) {
		List<CoreReceiptEntity> list = coreReceiptRepository.getListGenerated(storeId, businessDate);
		if (list.isEmpty()) {
			throw new CustomException("false", "StoreId doesn't Exist");
		}
		List<ReceiptResponse> response = new ArrayList<>();
		ListResponse mainResponse = new ListResponse();
		for (CoreReceiptEntity item : list) {
			ReceiptResponse receipt = new ReceiptResponse();
			receipt.setSaasId(item.getSaasId());
			receipt.setGeneratedPdf(item.getFileLocation());
			receipt.setId(item.getReceiptId());
			receipt.setInvoiceNumber(item.getInvoiceNumber());
			receipt.setBusinessDate(item.getBusinessDate());
			response.add(receipt);
		}

		int startIndex = (page - 1) * limit;
		int endIndex = (page) * limit;
		response = response.subList(startIndex, Math.min(endIndex, response.size()));
		mainResponse.setCount(list.size());
		mainResponse.setData(response);
		return mainResponse;
	}

	@Override
	public ListResponse getReceiptData(String storeId, int page, String businessDate) {
		long count = coreReceiptRepository.getReceiptDataCount(storeId, businessDate);
		List<CoreReceiptEntity> list = coreReceiptRepository.getReceiptData(storeId, businessDate, (page-1)*limit, limit);
		if (list.isEmpty()) {
			throw new CustomException("false", "StoreId doesn't Exist");
		}
		List<ReceiptResponse> response = new ArrayList<>();
		ListResponse mainResponse = new ListResponse();
		for (CoreReceiptEntity item : list) {
			ReceiptResponse receipt = new ReceiptResponse();
			receipt.setSaasId(item.getSaasId());
			receipt.setGeneratedPdf(item.getFileLocation());
			receipt.setId(item.getReceiptId());
			receipt.setInvoiceNumber(item.getInvoiceNumber());
			receipt.setBusinessDate(item.getBusinessDate());
			receipt.setReceiptDownloaded(item.getReceiptDownloadStatus());
			receipt.setReceiptScanned(item.getReceiptScannedStatus());
			response.add(receipt);
		}

//		int startIndex = (page - 1) * limit;
//		int endIndex = (page) * limit;
//		response = response.subList(startIndex, Math.min(endIndex, response.size()));
		mainResponse.setCount(count);
		mainResponse.setData(response);
		return mainResponse;
	}

	@Override
	public ListResponse getReceipts(String storeId, String startBusinessDate, String endBusinessDate, String downloadStatus, String scannedStatus, int page) {
		long count = coreReceiptRepository.getReceiptsCountWithFilter(storeId, startBusinessDate, endBusinessDate);
		List<CoreReceiptEntity> list = coreReceiptRepository.getReceipts(storeId, startBusinessDate, endBusinessDate, (page-1)*limit, limit);
		if (list.isEmpty()) {
			throw new CustomException("false", "StoreId doesn't Exist");
		}
		List<ReceiptResponse> response = new ArrayList<>();
		ListResponse mainResponse = new ListResponse();
		for (CoreReceiptEntity item : list) {
			ReceiptResponse receipt = new ReceiptResponse();
			receipt.setSaasId(item.getSaasId());
			receipt.setGeneratedPdf(item.getFileLocation());
			receipt.setId(item.getReceiptId());
			receipt.setInvoiceNumber(item.getInvoiceNumber());
			receipt.setBusinessDate(item.getBusinessDate());
			receipt.setReceiptDownloaded(item.getReceiptDownloadStatus());
			receipt.setReceiptScanned(item.getReceiptScannedStatus());
			response.add(receipt);
		}

		mainResponse.setCount(count);
		mainResponse.setData(response);
		return mainResponse;
	}

	@Override
	public ListResponse getDownloaded(String storeId, int page, String businessDate) {
		List<CoreReceiptEntity> list = coreReceiptRepository.getListDownloaded(storeId, "Y", businessDate);

		List<ReceiptResponse> response = new ArrayList<>();
		ListResponse mainResponse = new ListResponse();
		if (!list.isEmpty()) {
			for (CoreReceiptEntity item : list) {
				ReceiptResponse receipt = new ReceiptResponse();
				receipt.setSaasId(item.getSaasId());
				receipt.setGeneratedPdf(item.getFileLocation());
				receipt.setId(item.getReceiptId());
				receipt.setInvoiceNumber(item.getInvoiceNumber());
				receipt.setBusinessDate(item.getBusinessDate());
				receipt.setDeviceId(item.getDeviceId());
				receipt.setReceiptDownloaded(item.getReceiptDownloadStatus());
				response.add(receipt);
			}
		}

		int startIndex = (page - 1) * limit;
		int endIndex = (page) * limit;
		response = response.subList(startIndex, Math.min(endIndex, response.size()));
		mainResponse.setCount(list.size());
		mainResponse.setData(response);
		return mainResponse;

	}

	@Override
	public ListSummaryResponse getSummary(String saasId) {
		List<StoreMaster> entity = storeMasterRepository.getSummary(saasId);
		ClientEntity client = clientRepository.getClient(saasId);
		if (entity == null || client == null) {
			throw new CustomException("false", "SaasId doesn't Exist");
		}
		List<SummaryResponse> listResponse = new ArrayList<>();
		ListSummaryResponse response = new ListSummaryResponse();

		String companyName = client.getClientName();
		for (StoreMaster item : entity) {
			float downloadCount = coreReceiptRepository.getStoreDownload(item.getStoreId(), "Y");
			float generatedCount = coreReceiptRepository.getStoreGenerated(item.getStoreId());
			float scannedCount = coreReceiptRepository.getStoreScanned(item.getStoreId(), "Y");
			double co2EmissionSaved = scannedCount * NUM_OF_PAGES * CO2_EMISSION_FACTOR;
			String str = String.format("%1.4f", co2EmissionSaved);
			double co2Emission = Double.parseDouble(str);
			SummaryResponse sr = new SummaryResponse();
			sr.setCompanyName(companyName);
			sr.setStoreName(item.getStoreName());
			sr.setInvoiceDownload(downloadCount);
			sr.setInvoiceGenerated(generatedCount);
			sr.setInvoiceScanned(scannedCount);
			float percentage = (float) (downloadCount / generatedCount) * 100;
			sr.setStoreId(item.getStoreId());
			sr.setPercentage(percentage);
			sr.setCo2EmissionSaved(co2Emission);

			listResponse.add(sr);
		}
		response.setData(listResponse);
		return response;
	}

	@Override
	public ClientSummaryResponse getClientSummary(String saasId) {
		ClientEntity client = clientRepository.getClient(saasId);
		if (client == null) {
			throw new CustomException("false", "SaasId doesn't Exist");
		}

		String companyName = client.getClientName();

		long downloadCount = coreReceiptRepository.getCountDownload(saasId, "Y");
		long generatedCount = coreReceiptRepository.getCountGenerated(saasId);
		long scannedCount = coreReceiptRepository.getCountScanned(saasId, "Y");
		double co2EmissionSaved = scannedCount * NUM_OF_PAGES * CO2_EMISSION_FACTOR;
		String str = String.format("%1.4f", co2EmissionSaved);
		double co2Emission = Double.parseDouble(str);
		ClientSummaryResponse sr = new ClientSummaryResponse();
		sr.setCompanyName(companyName);
		sr.setInvoiceDownload(downloadCount);
		sr.setInvoiceGenerated(generatedCount);
		sr.setInvoiceScanned(scannedCount);
		sr.setCo2EmissionSaved(co2Emission);

		return sr;
	}

	public String date() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		return ft.format(dNow).toString();
	}

	@Override
	public CountResponse getCountGenerated(String saasId) {
		CountResponse response = new CountResponse();
		long count = coreReceiptRepository.getCountGenerated(saasId);
		response.setCount(count);
		return response;

	}

	@Override
	public CountResponse getCountDownload(String saasId) {
		CountResponse response = new CountResponse();
		long count = coreReceiptRepository.getCountDownload(saasId, "Y");
		response.setCount(count);
		return response;
	}

	@Override
	public CountResponse getCountScanned(String saasId) {
		CountResponse response = new CountResponse();
		long count = coreReceiptRepository.getCountScanned(saasId, "Y");
		response.setCount(count);
		return response;
	}

	@Override
	public CO2Response getCo2EmissionSaved(String saasId) {
		CO2Response response = new CO2Response();
		long count = coreReceiptRepository.getCountScanned(saasId, "Y");
		double co2EmissionSaved = count * NUM_OF_PAGES * CO2_EMISSION_FACTOR;
		String str = String.format("%1.2f", co2EmissionSaved);
		double d = Double.parseDouble(str);
		System.out.println("CO2EMISSION: "+ d);
		response.setCount(d);
		return response;
	}

	@Override
	public LicenseSummaryResponse getLicenseSummary(String saasId) {

		long licenseCount = licenseMasterRepository.getLicenseCount(saasId);
		long usedLicenseCount = licenseMasterRepository.getUsedLicenseCount(saasId);
		long unusedLicenseCount = licenseMasterRepository.getUnusedLicenseCount(saasId);
		long expiredLicenseCount = licenseMasterRepository.getExpiredLicenseCount(saasId);
		LicenseSummaryResponse license = new LicenseSummaryResponse();
		license.setLicenseCount(licenseCount);
		license.setUsedLicenseCount(usedLicenseCount);
		license.setUnusedLicenseCount(unusedLicenseCount);
		license.setExpiredLicenseCount(expiredLicenseCount);
		return license;
	}

	@Override
	public List<DashboardLicenseResponse> processLicenses(String saasId) {
		List<LicenseMaster> licenses = licenseMasterRepository.getLicenses(saasId);

		List<DashboardLicenseResponse> responseList = new ArrayList<>();

		for (LicenseMaster license : licenses) {
			DashboardLicenseResponse response = new DashboardLicenseResponse();
			ClientEntity client = clientRepository.getClient(saasId);
			response.setClientName(client.getClientName());
			response.setSaasId(client.getClientId());
			response.setLicenseNumber(license.getLicenseNumber());
			response.setStatus(license.getStatus());
			response.setEffectiveDate(license.getEffectiveDate());
			response.setExpiryDate(license.getExpiryDate());
			response.setTrialPeriod(license.getTrialPeriod());
			response.setLicensePeriod(license.getLicensePeriod());
			response.setApplicability(license.getApplicability());
			if(license.getStoreId() != null) {
				StoreMaster store = storeMasterRepository.getStoreDetail(license.getStoreId());
				response.setStoreId(license.getStoreId());
				response.setStoreName(store.getStoreName());
				response.setStoreLocation(store.getStoreLocation());
			}

			responseList.add(response);
		}
		return responseList;
	}

}
