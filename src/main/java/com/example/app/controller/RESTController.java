package com.example.app.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.MUser;
import com.example.app.domain.RestProtein;
import com.example.app.service.ProteinService;

@RestController
@RequestMapping("/rest")
public class RESTController {
	@Autowired
	ProteinService service;

	@PostMapping("/add")
	@ResponseBody
	public String add(HttpSession session, @RequestBody RestProtein restProtein) throws Exception {
		System.out.println(restProtein);
		MUser user = (MUser) session.getAttribute("user");
		if (user.getId() != restProtein.getUid()&&restProtein.getUid()!=null) {
			service.addProtein(restProtein);
			return "addProtein OK";
		}
		return "failed";
	}

	@PostMapping("/del")
	@ResponseBody
	public String del(HttpSession session,@RequestBody RestProtein restProtein) throws Exception {
		System.out.println(restProtein);
		MUser user = (MUser) session.getAttribute("user");
		if (user.getId() != restProtein.getUid()&&restProtein.getUid()!=null) {
			service.delProtein(restProtein);
			return "delProtein OK";
		}
		return "faild";
	}
}
