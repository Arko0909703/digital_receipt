package com.photon.qreceipt.controller;

import com.photon.qreceipt.request.ReceiptsFilterRequest;
import com.photon.qreceipt.response.*;
import com.photon.qreceipt.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.photon.qreceipt.request.LoginRequest;
import com.photon.qreceipt.service.DashboardService;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private LicenseService licenseService;

	@CrossOrigin(origins = "*")
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return dashboardService.login(request);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-generated/{storeId}/{page}/{businessDate}")
	public ListResponse getGenerated(@PathVariable String storeId, @PathVariable int page,
			@PathVariable String businessDate) {
		return dashboardService.getGenerated(storeId, page, businessDate);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-receipts/{storeId}/{page}/{businessDate}")
	public ListResponse getReceiptData(@PathVariable String storeId, @PathVariable int page,
									 @PathVariable String businessDate) {
		return dashboardService.getReceiptData(storeId, page, businessDate);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-receipts")
	public ListResponse getReceipts(ReceiptsFilterRequest receiptsFilter) {
		return dashboardService.getReceipts(receiptsFilter.getStoreId(), receiptsFilter.getStartBusinessDate(), receiptsFilter.getEndBusinessDate(), receiptsFilter.getDownloadStatus(), receiptsFilter.getEndBusinessDate(), receiptsFilter.getPage());
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-downloaded/{storeId}/{page}/{businessDate}")
	public ListResponse getDownloaded(@PathVariable String storeId, @PathVariable int page,
			@PathVariable String businessDate) {
		return dashboardService.getDownloaded(storeId, page, businessDate);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-store-summary/{saasId}")
	public ListSummaryResponse getStoreSummary(@PathVariable String saasId) {
		return dashboardService.getSummary(saasId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-count-download/{saasId}")
	public CountResponse getCountDownload(@PathVariable String saasId) {
		return dashboardService.getCountDownload(saasId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-count-scanned/{saasId}")
	public CountResponse getCountScanned(@PathVariable String saasId) {
		return dashboardService.getCountScanned(saasId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-co2-saved/{saasId}")
	public CO2Response getCo2Saved(@PathVariable String saasId) {
		return dashboardService.getCo2EmissionSaved(saasId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-client-summary/{saasId}")
	public ClientSummaryResponse getClientSummary(@PathVariable String saasId) {
		return dashboardService.getClientSummary(saasId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get-count-generated/{saasId}")
	public CountResponse getCountGenerated(@PathVariable String saasId) {
		return dashboardService.getCountGenerated(saasId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/license-summary/{saasId}")
	public LicenseSummaryResponse getSummary(@PathVariable String saasId){
		return dashboardService.getLicenseSummary(saasId);
	}


	@CrossOrigin(origins = "*")
	@GetMapping("/license-details/{saasId}")
	public List<DashboardLicenseResponse> processLicenses(@PathVariable String saasId) {

		return dashboardService.processLicenses(saasId);
	}
}
