package com.photon.qreceipt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photon.qreceipt.request.SaveMetaRequest;
import com.photon.qreceipt.response.BaseResponse;
import com.photon.qreceipt.service.UtilityService;

@RestController
@RequestMapping("/api/utility")
@CrossOrigin(origins = "*")
public class UtilityController {
	@Autowired
	UtilityService utilityService;
	@PostMapping("/savemeta")
	public BaseResponse saveMeta(@RequestBody @Validated SaveMetaRequest request) {
		return utilityService.saveMeta(request);
	}
}
