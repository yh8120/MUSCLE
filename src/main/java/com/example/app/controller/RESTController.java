package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.ApiStatus;
import com.example.app.domain.Id;
import com.example.app.domain.ListChartData;
import com.example.app.domain.LogChartData;
import com.example.app.domain.Protein;
import com.example.app.login.LoginUserDetails;
import com.example.app.service.ProteinService;
import com.example.app.service.TrainingService;
import com.example.app.service.WebSocketMessageService;

@RestController
@RequestMapping("/rest")
public class RESTController {
	@Autowired
	ProteinService proteinService;
	@Autowired
	WebSocketMessageService message;
	@Autowired
	TrainingService trainingService;

	@PostMapping("/add")
	public ApiStatus add(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Protein protein) throws Exception {
		if (loginUserDetails.getLoginUser().getId() == protein.getUserId() || protein.getUserId() != null) {
			proteinService.addProtein(protein);
			message.sendProteinToUser(protein.getTrainingLogId());
			return new ApiStatus("succses", "add OK");
		}
		return new ApiStatus("error", "add ERROR");
	}

	@PostMapping("/del")
	public ApiStatus del(@AuthenticationPrincipal LoginUserDetails loginUserDetails, @RequestBody Protein protein) throws Exception {
		if (loginUserDetails.getLoginUser().getId() == protein.getUserId() || protein.getUserId() != null) {
			proteinService.delProtein(protein);
			return new ApiStatus("succses", "del OK");
		}
		return new ApiStatus("error", "del ERROR");
	}
	
	@PostMapping("/listchart")
	public List<ListChartData> postListChart(@AuthenticationPrincipal LoginUserDetails loginUserDetails) throws Exception {
		 List<ListChartData> dataList = trainingService.getListChartData(loginUserDetails.getLoginUser().getId());
		
		return dataList;
	}
	
	@PostMapping("/logchart")
	public List<LogChartData> postlogChart(@AuthenticationPrincipal LoginUserDetails loginUserDetails,@RequestBody Id id) throws Exception {
		 List<LogChartData> dataList = trainingService.getLogChartData(id.getId());
		
		return dataList;
	}
}
