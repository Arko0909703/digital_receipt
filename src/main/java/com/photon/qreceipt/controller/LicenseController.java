package com.photon.qreceipt.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import com.photon.qreceipt.entity.LicenseMaster;
import com.photon.qreceipt.response.LicenseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.photon.qreceipt.request.LicenseCreationRequest;
import com.photon.qreceipt.request.LicenseCreationRequestV2;
import com.photon.qreceipt.request.ValidateLicenseRequest;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.response.LicenseDetailsResponse;
import com.photon.qreceipt.service.LicenseService;

import java.util.List;

@RestController
@RequestMapping("/api/license")
@CrossOrigin(origins = "*")
public class LicenseController {
	@Autowired
	private LicenseService licenseService;
	@GetMapping("/get-license")
	public String show() {
		return "Hello world!";
	}
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/create")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public LicenseDetailsResponse create(@RequestBody @Validated LicenseCreationRequest request) {
		return licenseService.createLicense(request);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/validate")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse validate(@RequestBody @Validated ValidateLicenseRequest request) {
		return licenseService.validateLicense(request);
	}

	

}
