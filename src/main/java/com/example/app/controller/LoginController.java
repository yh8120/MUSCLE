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
			switch(e){
				case 0:model.addAttribute("message","ログアウトしました。");break;
				case 1:model.addAttribute("message","メールアドレスは変更されませんでした。");break;
				case 2:model.addAttribute("message","メールアドレスが変更されました。再ログインしてください。");break;
			}
		}
		
		return "login";
	}

}