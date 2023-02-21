package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.ApiStatus;
import com.example.app.domain.Protein;
import com.example.app.login.LoginUserDetails;
import com.example.app.service.ProteinService;
import com.example.app.service.WebSocketMessage;

@RestController
@RequestMapping("/rest")
public class RESTController {
	@Autowired
	ProteinService service;
	@Autowired
	WebSocketMessage message;

	@PostMapping("/add")
	public ApiStatus add(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Protein protein) throws Exception {
		if (loginUserDetails.getLoginUser().getId() == protein.getUserId() || protein.getUserId() != null) {
			service.addProtein(protein);
			message.sendProteinToUser(protein.getTrainingLogId());
			return new ApiStatus("succses", "add OK");
		}
		return new ApiStatus("error", "add ERROR");
	}

	@PostMapping("/del")
	public ApiStatus del(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Protein protein) throws Exception {
		if (loginUserDetails.getLoginUser().getId() == protein.getUserId() || protein.getUserId() != null) {
			service.delProtein(protein);
			return new ApiStatus("succses", "del OK");
		}
		return new ApiStatus("error", "del ERROR");
	}
}
