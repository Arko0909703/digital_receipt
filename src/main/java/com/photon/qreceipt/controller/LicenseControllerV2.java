package com.photon.qreceipt.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import com.photon.qreceipt.entity.LicenseMaster;
import com.photon.qreceipt.response.LicenseResponse;
import com.photon.qreceipt.response.LicenseResponseV2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.photon.qreceipt.request.LicenseCreationRequestV2;
import com.photon.qreceipt.request.ValidateLicenseRequest;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.response.LicenseDetailsResponse;
import com.photon.qreceipt.response.LicenseDetailsResponseV2;
import com.photon.qreceipt.service.LicenseService;
import com.photon.qreceipt.service.LicenseServiceV2;

import java.util.List;

@RestController
@RequestMapping("/api/license/v2")
@CrossOrigin(origins = "*")
public class LicenseControllerV2 {
	@Autowired
	private LicenseServiceV2 licenseServiceV2;
	@GetMapping("/get-license")
	public String show() {
		return "Hello world!";
	}
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/create")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public LicenseDetailsResponseV2 create(@RequestBody @Validated LicenseCreationRequestV2 request) {
		return licenseServiceV2.createLicenseV2(request);
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping(path = "/validate")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse validate(@RequestBody @Validated ValidateLicenseRequest request) {
		return licenseServiceV2.validateLicense(request);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/fetchregistrationcode")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public LicenseResponseV2 fetchregistrationcode(@RequestBody @Validated LicenseCreationRequestV2 request) {
		return licenseServiceV2.fetchLicenseDetailsByRegistrationCode(request.getRegistrationCode());
	}
	
	@GetMapping("/fetch/{registrationCode}")
	public ResponseEntity<List<LicenseResponseV2>> fetchLicenses(@PathVariable String registrationCode) {
		List<LicenseResponseV2> responses = licenseServiceV2.fetchLicenseByRegistrationCode(registrationCode);
		return ResponseEntity.ok(responses);
	}

}
