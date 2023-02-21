package com.example.app.controller;

import java.util.Objects;
import java.util.UUID;

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
import com.example.app.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	UserRegisterService userRegisterService;
	@Autowired
	SexService sexService;
	@Autowired
	UserFormService userFormService;
	@Autowired
	MessageSenderService sendMessageService;

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

		//メアド変更時メール送信処理
		MUser user = userRegisterService.checkUserbyEmail(userForm.getEmail());
		if (!userForm.getEmail().equals(loginUserDetails.getLoginUser().getEmail())) {
			if (Objects.nonNull(user)) {
				errors.rejectValue("email", "error.already_registered");
				model.addAttribute("sexList", sexService.getSexList());
				return "edit-user";
			}
			
			String uuid = UUID.randomUUID().toString();
			
			userFormService.reservationEmail(userForm,loginUserDetails.getLoginUser().getEmail(), uuid);

			String subject = "【DARK MUSCLE】会員情報更新のご案内";
			String message = String.format(
					"会員情報はまだ変更されていません。30分以内に"
							+ "下記URLにアクセスして更新を承認してください。"
							+ "localhost:8080/accounts/user/edit-submit/%s",uuid);
			sendMessageService.sendMessage(userForm.getEmail(), subject, message);

			redirectAttributes.addFlashAttribute("message", "メールアドレスが変更されたため、確認用メールを送信しました。30分以内にメールリンクより変更を承認してください。");
			userForm.setEmail(loginUserDetails.getLoginUser().getEmail());
			userFormService.updateAccount(userForm);
			return "redirect:/training";
		}

		userFormService.updateAccount(userForm);
		redirectAttributes.addFlashAttribute("message", "会員情報を更新しました。");
		return "redirect:/training";
		
	}
	
	

}