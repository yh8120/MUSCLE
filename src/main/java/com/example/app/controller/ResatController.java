package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.RestProtein;
import com.example.app.service.ProteinService;

@RestController
@RequestMapping("/rest")
public class ResatController {
	@Autowired
	ProteinService service;
	
	@PutMapping("/add")
	public String add(RestProtein restProtein)throws Exception {
		service.addProtein(restProtein);
		return "addProtein OK";
		
	}
	
	@PutMapping("/del")
	public String del(RestProtein restProtein)throws Exception {
		service.delProtein(restProtein);
		return "delProtein OK";
		
	}
}
