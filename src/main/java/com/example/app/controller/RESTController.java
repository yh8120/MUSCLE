package com.example.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.ApiStatus;
import com.example.app.domain.MUser;
import com.example.app.domain.RestProtein;
import com.example.app.service.ProteinService;

@RestController
@RequestMapping("/rest")
public class RESTController {
	@Autowired
	ProteinService service;

	@PostMapping("/add")
	public ApiStatus add(HttpSession session, @RequestBody RestProtein restProtein) throws Exception {
		System.out.println(restProtein);
		MUser user = (MUser) session.getAttribute("user");
		if (user.getId() == restProtein.getUid() && restProtein.getUid() != null) {
			service.addProtein(restProtein);
			return new ApiStatus("succses", "add OK");
		}
		return new ApiStatus("error", "add ERROR");
	}

	@PostMapping("/del")
	public ApiStatus del(HttpSession session, @RequestBody RestProtein restProtein) throws Exception {
		System.out.println(restProtein);
		MUser user = (MUser) session.getAttribute("user");
		if (user.getId() == restProtein.getUid() && restProtein.getUid() != null) {
			service.delProtein(restProtein);
			return new ApiStatus("succses", "del OK");
		}
		return new ApiStatus("error", "del ERROR");
	}
}
