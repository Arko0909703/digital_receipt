package com.photon.qreceipt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photon.qreceipt.request.FromDateToDateRequest;
import com.photon.qreceipt.request.NpsRequest;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.response.CountResponse;
import com.photon.qreceipt.response.FeatureResponse;
import com.photon.qreceipt.response.ListNpsSummaryResponse;
import com.photon.qreceipt.response.ListSummaryResponse;
import com.photon.qreceipt.response.NpsResponse;
import com.photon.qreceipt.service.NpsService;

@RestController
@RequestMapping("/api/dashboard/nps")
@CrossOrigin(origins = "*")
public class NpsController {

	@Autowired
	private NpsService service;

	@PostMapping("/get-count-promotor/{saasId}")
	public NpsResponse getCountPromotor(@PathVariable String saasId, @RequestBody FromDateToDateRequest request) {
		return service.getPromotor(saasId, request);
	}

	@PostMapping("/get-count-passive/{saasId}")
	public NpsResponse getCountPassive(@PathVariable String saasId, @RequestBody FromDateToDateRequest request) {
		return service.getPassive(saasId, request);
	}

	@PostMapping("/get-count-detractor/{saasId}")
	public NpsResponse getCountDetractor(@PathVariable String saasId, @RequestBody FromDateToDateRequest request) {
		return service.getDetractor(saasId, request);
	}

	@PostMapping("/get-nps-summary/{saasId}")
	public ListNpsSummaryResponse getSummary(@PathVariable String saasId, @RequestBody FromDateToDateRequest request) {
		return service.listNpsSummary(saasId, request);
	}

	@GetMapping("/check-nps/{receiptId}")
	public BaseResponse check(@PathVariable String receiptId) {
		return service.checkNps(receiptId);
	}

	@GetMapping("/feature/{saasId}")
	public FeatureResponse getFeatureDetails(@PathVariable String saasId) {
		return service.featureDetails(saasId);
	}

	@PostMapping(path = "/nps-master")
	public BaseResponse saveCustomerMaster(@RequestBody NpsRequest request) {

		return service.saveNpsDetails(request);
	}

}
