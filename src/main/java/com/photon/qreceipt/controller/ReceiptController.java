package com.photon.qreceipt.controller;

import com.photon.qreceipt.response.RegisterDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.photon.qreceipt.entity.TokenEntity;
import com.photon.qreceipt.request.CoreReceiptRequest;
import com.photon.qreceipt.request.CustomerMasterRequest;
import com.photon.qreceipt.request.NpsRequest;
import com.photon.qreceipt.request.UserDetailRequest;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.response.QRResponse;
import com.photon.qreceipt.response.StoreResponse;
import com.photon.qreceipt.service.CoreReceiptService;

@RestController
@RequestMapping("/api/receipt")
@CrossOrigin(origins = "*")
public class ReceiptController {

	@Autowired
	private CoreReceiptService service;

	@GetMapping("/save-receipt")
	public String show() {
		return "Hello world!";
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/upload-document", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public BaseResponse uploadFile(@RequestPart(value = "file", required = true) MultipartFile file,
			@RequestPart("uploaded-by") String uploadedby, @RequestPart("file-type") String filetype,
			@RequestPart("store-id") String storeId, @RequestPart("register-id") String registerId,
			@RequestPart("business-date") String businessDate, @RequestPart("saas-id") String saasId) {
		return service.upload(file, uploadedby, storeId, registerId, businessDate, saasId);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public BaseResponse upload(@RequestPart(value = "file", required = true) MultipartFile file,
			@RequestPart("uploaded-by") String uploadedby, @RequestPart("file-type") String filetype,
			@RequestPart("store-id") String storeId, @RequestPart("register-id") String registerId,
			@RequestPart("business-date") String businessDate, @RequestPart("saas-id") String saasId,
			@RequestPart("choice") String choice, @RequestPart("mobile-number") String mobileNumber,
			@RequestPart("country-code") String countryCode)
	{
		return service.uploadDocument(file, uploadedby, storeId, registerId, businessDate, saasId, choice, mobileNumber,countryCode);
	}
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/get-receipt")
	public BaseResponse getReceipt(@RequestBody CoreReceiptRequest request) {

		return service.getReceipt(request);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/get-qrdetails")
	public QRResponse getQrDetails(@RequestBody CoreReceiptRequest request) {

		return service.getQRDetails(request);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/{nick}/get-receipt")
	public QRResponse getQReceipt(@PathVariable("nick") String registerNick, @RequestBody CoreReceiptRequest request) {
		return service.getQRDetails(registerNick, request);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/download-status")
	public BaseResponse dowloadStatus(@RequestBody CoreReceiptRequest request) {

		return service.downloadReceiptStatus(request);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generate-token/{device_id}")
	public String getToken(@PathVariable("device_id") String deviceId) {
		return service.getToken(deviceId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generate-token1/{device_id}")
	public TokenEntity geToken(@PathVariable("device_id") String deviceId) {
		return service.generateToken(deviceId);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/user-details")
	public BaseResponse saveUserDetails(@RequestBody UserDetailRequest request) {

		return service.saveUserDetails(request);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/customer-master")
	public BaseResponse saveCustomerMaster(@RequestBody CustomerMasterRequest request) {

		return service.saveQrCustomerDetails(request);
	}

	@CrossOrigin(origins = "*")
	@GetMapping(path = "/store-detail/{storeId}")
	public StoreResponse getStoreDetails(@PathVariable String storeId) {
		return service.getStoreDetail(storeId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping(path = "/register-detail/{nick}")
	public RegisterDetailsResponse getRegisterDetails(@PathVariable("nick") String registerNick) {
		return service.getRegisterDetails(registerNick);
	}

}
