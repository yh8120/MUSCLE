package com.example.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.Login;
import com.example.app.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService service;
	

	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute(new Login());
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(HttpSession session,
			@Valid Login login,
			Errors errors) throws Exception {
//		spring security を経由した認証になるため不要なメソッド
//		if (errors.hasErrors()) {
//			return "login";
//		}
//
//		MUser user = service.getUserbyLogin(login.getEmail());
//		if (user == null || !login.isCorrectPassword(user.getLoginPass())) {
//			errors.rejectValue("loginPass", "error.incorrect_id_or_password");
//			return "login";
//		}
//		session.setAttribute("user", user);

		return "redirect:/training";
	}
	@GetMapping("/logout")
	public String getLogout() {
		return"redirect:/login";
	}

}