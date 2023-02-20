package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.app.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService service;
	

	@GetMapping("/login")
	public String getLogin(Model model) {
		return "login";
	}

	@GetMapping("/logout")
	public String getLogout() {
		return"redirect:/login";
	}

}