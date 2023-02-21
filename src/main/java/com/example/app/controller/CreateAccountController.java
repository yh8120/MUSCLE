package com.example.app.controller;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.MUser;
import com.example.app.domain.ReservedEmail;
import com.example.app.domain.UserForm;
import com.example.app.domain.UserRegister;
import com.example.app.login.LoginUserDetails;
import com.example.app.service.MessageSenderService;
import com.example.app.service.SexService;
import com.example.app.service.UserFormService;
import com.example.app.service.UserRegisterService;
import com.example.app.service.UserService;

@Controller
@RequestMapping("/accounts")
public class CreateAccountController {

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

	private final Long EFFECTIVE_TIME = 1800000L;

	@GetMapping
	public String getAccounts(Model model) {
		model.addAttribute(new UserRegister());
		return "accounts";
	}

	@PostMapping
	public String postAccounts(
			RedirectAttributes redirectAttributes,
			@Valid UserRegister userRegister,
			Errors errors) throws Exception {

		MUser user = userRegisterService.checkUserbyEmail(userRegister.getEmail());
		if (Objects.nonNull(user)) {
			errors.rejectValue("email", "error.already_registered");
			return "accounts";
		}

		if (errors.hasErrors()) {
			return "accounts";
		}

		//userRegisterに仮登録
		String uuid = UUID.randomUUID().toString();
		userRegister.setUuid(uuid);
		userRegisterService.setUserRegister(userRegister);

		//emailの表題と本文
		String subject = "【DARK MUSCLE】仮登録のご案内";
		String message = String.format(
				"ご登録ありがとうございます。"
						+ "下記URLにアクセスして本登録を完了してください。"
						+ "localhost:8080/accounts/webcreate/%s",
				uuid);

		//email送信
		sendMessageService.sendMessage(userRegister.getEmail(), subject, message);

		redirectAttributes.addFlashAttribute("message", "送信されたメールリンクから30分以内にアカウント登録を行ってください。");
		return "redirect:/login";
	}

	@GetMapping("/mailsended")
	public String getMailSended() {
		return "mailsended";
	}

	@GetMapping("/webcreate/{id}")
	public String getWebCreate(
			RedirectAttributes redirectAttributes,
			@PathVariable String id,
			Model model) throws Exception {

		UserRegister userRegister = userRegisterService.findByRegistrationCode(id);

		//userRegisterに値が無い
		Date date = new Date();
		if (userRegister == null) {
			redirectAttributes.addFlashAttribute("message", "アカウントが不正です。");
			return "redirect:/login";
		}

		//アカウントの有効期限が過ぎている
		Long progressTime = date.getTime() - userRegister.getRegistered().getTime();
		if (progressTime > EFFECTIVE_TIME) {
			redirectAttributes.addFlashAttribute("message", "アカウントの有効期間が過ぎています。再度メールアドレスを登録してください。");
			return "redirect:/login";
		}

		UserForm userForm = new UserForm();
		userForm.setEmail(userRegister.getEmail());
		model.addAttribute("sexList", sexService.getSexList());
		model.addAttribute("userForm", userForm);
		return "webcreate";
	}

	@PostMapping("/webcreate")
	public String postWebCreate(RedirectAttributes redirectAttributes,
			@Valid UserForm userForm,
			Errors errors,
			Model model) throws Exception {
		if (!userForm.getLoginPass().equals(userForm.getLoginPassCopy())) {
			errors.rejectValue("loginPass", "error.differ_password");
			model.addAttribute("sexList", sexService.getSexList());
			return "webcreate";
		}
		if (errors.hasErrors()) {
			System.out.println(errors.getAllErrors());
			model.addAttribute("sexList", sexService.getSexList());
			return "webcreate";
		}

		userFormService.createAccount(userForm);

		redirectAttributes.addFlashAttribute("message", "ユーザー登録が完了しました。");
		redirectAttributes.addFlashAttribute("email", userForm.getEmail());
		redirectAttributes.addFlashAttribute("loginPass", userForm.getLoginPass());
		return "redirect:/login";
	}

	@GetMapping("/user/edit-submit/{uuid}")
	public String getEditSubmit(
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			Model model,
			@PathVariable("uuid") String uuid,
			@Valid UserForm userForm,
			Errors errors) throws Exception {

		ReservedEmail reservedEmail = userService.getReservedEmail(uuid);
		if (Objects.isNull(reservedEmail)) {
			if (Objects.isNull(loginUserDetails)) {
				return "redirect:/login?e=1";
			}
			return "redirect:/logout?e=1";
		}

		Date date = new Date();
		Long progressTime = date.getTime() - reservedEmail.getRegistered().getTime();

		if (progressTime > EFFECTIVE_TIME) {
			if (Objects.isNull(loginUserDetails)) {
				return "redirect:/login?e=1";
			}
			return "redirect:/logout?e=1";
		}

		userService.updateEmail(reservedEmail);
		if (Objects.isNull(loginUserDetails)) {
			redirectAttributes.addFlashAttribute("message", "メールアドレスを変更しました。再ログインして下さい。");
			return "redirect:/login?e=2";
		}
		return "redirect:/logout?e=2";
	}
}