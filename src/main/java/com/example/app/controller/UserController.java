package com.example.app.controller;

import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.MUser;
import com.example.app.domain.UserForm;
import com.example.app.login.LoginUserDetails;
import com.example.app.service.MessageSenderService;
import com.example.app.service.SexService;
import com.example.app.service.UserFormService;
import com.example.app.service.UserRegisterService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRegisterService userRegisterService;
	@Autowired
	SexService sexService;
	@Autowired
	UserFormService userFormService;
	@Autowired
	MessageSenderService sendMessageService;

	private final Long EFFECTIVE_TIME = 1800000L;

	@GetMapping("/edit")
	public String getEditUser(
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			Model model) throws Exception {
		
		model.addAttribute("sexList", sexService.getSexList());
		model.addAttribute("userForm", new UserForm(loginUserDetails.getLoginUser()));
		return "edit-user";
	}

	@PostMapping("/edit")
	public String postEditUser(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			Model model,
			@Valid UserForm userForm,
			Errors errors) throws Exception {
		if (!userForm.getLoginPass().equals(userForm.getLoginPassCopy())) {
			errors.rejectValue("loginPass", "error.differ_password");
			model.addAttribute("sexList", sexService.getSexList());
			return "edit-user";
		}
		if (errors.hasErrors()) {
			model.addAttribute("sexList", sexService.getSexList());
			return "edit-user";
		}
		MUser user =userRegisterService.checkUserbyEmail(userForm.getEmail());
		if(user.getEmail()!=loginUserDetails.getLoginUser().getEmail()
				&& Objects.nonNull(user)) {
			errors.rejectValue("email", "error.already_registered");
			model.addAttribute("sexList", sexService.getSexList());
			return "edit-user";
		}
		
		
		
		String uuid = UUID.randomUUID().toString();
		userFormService.setTempUser(userForm,uuid);
		
		String subject = "【DARK MUSCLE】会員情報更新のご案内";
		String message = String.format(
				"会員情報はまだ変更されていません。30分以内に"
						+ "下記URLにアクセスして更新を承認してください。"
						+ "localhost:8080/accounts/user/esit-submit/%s",uuid);
		sendMessageService.sendMessage(userForm.getEmail(), subject, message);
		
		redirectAttributes.addFlashAttribute("message", "会員情報更新用メールを送信しました。30分以内にメールリンクより変更を承認してください。");
		return "redirect:/training";
	}
	
	
	
	@PostMapping("/edit-submit/{id}")
	public String postEditUser(HttpSession session,
			RedirectAttributes redirectAttributes,
			Model model,
			@Valid UserForm userForm,
			Errors errors) throws Exception {
		MUser user = (MUser) session.getAttribute("user");
		if (user.getId() != userForm.getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}
		if (!userForm.getLoginPass().equals(userForm.getLoginPassCopy())) {
			errors.rejectValue("loginPass", "error.differ_password");
			model.addAttribute("sexList", sexService.getSexList());
			return "edit-user";
		}
		if (errors.hasErrors()) {
			model.addAttribute("sexList", sexService.getSexList());
			return "edit-user";
		}

		userFormService.updateAccount(userForm);
		redirectAttributes.addFlashAttribute("message", "ユーザー情報を変更しました。");
		return "redirect:/training";
	}
}