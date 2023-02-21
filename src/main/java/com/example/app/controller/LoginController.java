package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService service;

	@GetMapping("/login")
	public String getLogin(@RequestParam(name="e",required =false)Integer e,Model model) {
		if(e != null) {
			
			if(e==1) {
				model.addAttribute("message","メールアドレスは変更されませんでした。");
			}else if(e==2) {
				model.addAttribute("message","メールアドレスが変更されました。再ログインしてください。");
			}
		}
		
		return "login";
	}

}