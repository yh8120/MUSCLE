package com.example.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.ApiStatus;
import com.example.app.domain.MUser;
import com.example.app.domain.Protein;
import com.example.app.service.ProteinService;

@RestController
@RequestMapping("/rest")
public class RESTController {
	@Autowired
	ProteinService service;

	@PostMapping("/add")
	public ApiStatus add(HttpSession session, @RequestBody Protein protein) throws Exception {
		MUser user = (MUser) session.getAttribute("user");
		if (user.getId() == protein.getUid() && protein.getUid() != null) {
			service.addProtein(protein);
			return new ApiStatus("succses", "add OK");
		}
		return new ApiStatus("error", "add ERROR");
	}

	@PostMapping("/del")
	public ApiStatus del(HttpSession session, @RequestBody Protein protein) throws Exception {
		MUser user = (MUser) session.getAttribute("user");
		if (user.getId() == protein.getUid() && protein.getUid() != null) {
			service.delProtein(protein);
			return new ApiStatus("succses", "del OK");
		}
		return new ApiStatus("error", "del ERROR");
	}
}
