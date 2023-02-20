package com.example.app.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
import com.example.app.domain.Training;
import com.example.app.domain.TrainingLog;
import com.example.app.domain.TrainingPart;
import com.example.app.login.LoginUserDetails;
import com.example.app.service.PriorityService;
import com.example.app.service.TrainingPartService;
import com.example.app.service.TrainingService;
import com.example.app.service.TrainingTypeService;
import com.example.app.service.UserService;
import com.example.app.service.WebSocketMessage;
import com.example.app.service.WeekdayService;

@Controller
@RequestMapping("/training")
public class TrainingController {

	@Autowired
	UserService userService;
	@Autowired
	TrainingService trainingService;
	@Autowired
	PriorityService priorityService;
	@Autowired
	TrainingPartService trainingPartService;
	@Autowired
	TrainingTypeService trainingTypeService;
	@Autowired
	WeekdayService weekdayService;
	@Autowired
	WebSocketMessage webSocketMessage;

	@GetMapping
	public String getTraining(Authentication authentication,Principal principal,
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,Model model) throws Exception {
		List<TrainingPart> trainingPartList = trainingService.getTrainingListOrderByPart(loginUserDetails.getLoginUser().getId());
		model.addAttribute("trainingList", trainingPartList);
		webSocketMessage.sendTrainingLogToUser(loginUserDetails.getUsername());
		return "training/list";
	}

	@GetMapping("/add")
	public String getAddTraining(Model model) throws Exception {

		model.addAttribute("trainingPartList", trainingPartService.getTrainingPartList());
		model.addAttribute("trainingTypeList", trainingTypeService.getTrainingTypeList());
		model.addAttribute("priorityList", priorityService.getPriorityList());
		model.addAttribute("weekdayList", weekdayService.getWeekdayList());
		model.addAttribute("training", new Training());
		return "training/add-training";
	}

	@PostMapping("/add")
	public String postAddTraining(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			@Valid Training training,
			Errors errors,
			Model model) throws Exception {
		if (errors.hasErrors()) {
			model.addAttribute("trainingPartList", trainingPartService.getTrainingPartList());
			model.addAttribute("trainingTypeList", trainingTypeService.getTrainingTypeList());
			model.addAttribute("priorityList", priorityService.getPriorityList());
			model.addAttribute("weekdayList", weekdayService.getWeekdayList());
			return "training/add-training";
		}
		
		training.setUserId(loginUserDetails.getLoginUser().getId());
		
		trainingService.addTraining(training);
		
		return "redirect:/training";
	}
	
	@GetMapping("/edit/{trainingId}")
	public String getEditTraining(@AuthenticationPrincipal LoginUserDetails loginUserDetails,Model model,@PathVariable("trainingId")Integer trainingId) throws Exception {
		
		Training training =trainingService.getTraining(trainingId);
		
		model.addAttribute("training",training);
		model.addAttribute("trainingPartList", trainingPartService.getTrainingPartList());
		model.addAttribute("trainingTypeList", trainingTypeService.getTrainingTypeList());
		model.addAttribute("priorityList", priorityService.getPriorityList());
		model.addAttribute("weekdayList", weekdayService.getWeekdayList());
		return "training/edit-training";
	}
	
	@PostMapping("/edit")
	public String postEditTraining(
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			Model model,
			@Valid Training training,
			Errors errors) throws Exception {
		
		Training oldTraining =trainingService.getTraining(training.getId());
		if (oldTraining.getUserId() != loginUserDetails.getLoginUser().getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}
		if(errors.hasErrors()) {
			model.addAttribute("trainingPartList", trainingPartService.getTrainingPartList());
			model.addAttribute("trainingTypeList", trainingTypeService.getTrainingTypeList());
			model.addAttribute("priorityList", priorityService.getPriorityList());
			model.addAttribute("weekdayList", weekdayService.getWeekdayList());
			return "training/edit-training";
		}
		
		trainingService.editTraining(training);
		
		return "redirect:/training/log/"+training.getId();
	}
	
	@PostMapping("/delete/{trainingId}")
	public String postDeleteTraining(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			@PathVariable("trainingId")Integer trainingId
			) throws Exception {
		
		Training training =trainingService.getTraining(trainingId);
		if (training.getUserId() != loginUserDetails.getLoginUser().getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}
		
		trainingService.deleteTraining(trainingId);
		
		return "redirect:/training";
	}

	@GetMapping("/log/{id}")
	public String getLog(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			@PathVariable("id") Integer trainingId,
			Model model) throws Exception {
		Training training = trainingService.getAllTrainingLog(trainingId);
		if (training.getUserId() != loginUserDetails.getLoginUser().getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}

		model.addAttribute("training", training);

		return "training/log";
	}

	@GetMapping("/log/add/{id}")
	public String getAddLog(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			@PathVariable("id") Integer trainingId,
			Model model) throws Exception {

		Training training = trainingService.getTraining(trainingId);
		if (training.getUserId() != loginUserDetails.getLoginUser().getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}
		TrainingLog trainingLog = new TrainingLog();
		trainingLog.setTraining(training);
		model.addAttribute("trainingLog", trainingLog);

		return "training/add-log";
	}

	@PostMapping("/log/add")
	public String postAddLog(
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			@Valid TrainingLog trainingLog,
			Errors errors,
			Model model) throws Exception {
		Training training = trainingService.getTraining(trainingLog.getTraining().getId());
		
		if (errors.hasErrors()) {
			model.addAttribute("training", training);
			return "training/add-log";
		}

		MUser user = loginUserDetails.getLoginUser();

		trainingLog.setUser(user);
		trainingLog.setTraining(training);

		webSocketMessage.sendTrainingLog(trainingService.addTrainingLog(trainingLog));

		return "redirect:/training/log/" + trainingLog.getTraining().getId();
	}

	@GetMapping("/log/edit/{trainingLogId}")
	public String getEditLog(HttpSession session,
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			@PathVariable("trainingLogId") Integer trainingLogId,
			Model model) throws Exception {
		TrainingLog trainingLog = trainingService.getTrainingLog(trainingLogId);
		if (trainingLog.getUser().getId() != loginUserDetails.getLoginUser().getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}

		model.addAttribute("trainingLog", trainingLog);
		return "training/edit-log";
	}

	@PostMapping("/log/edit")
	public String postEditLog(HttpSession session,
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			@Valid TrainingLog trainingLog,
			Errors errors) throws Exception {
		TrainingLog checkLog = trainingService.getTrainingLog(trainingLog.getId());

		if (loginUserDetails.getLoginUser().getId() != checkLog.getUser().getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}
		if (errors.hasErrors()) {
			return "training/edit-log/" + trainingLog.getId();
		}

		trainingService.editTrainingLog(trainingLog);
		session.removeAttribute("trainingLog");

		return "redirect:/training/log/" + trainingLog.getTraining().getId();
	}

	@PostMapping("/log/delete/{trainingLogId}")
	public String postDeleteLog(HttpSession session,
			@PathVariable("trainingLogId")Integer trainingLogId,
			@AuthenticationPrincipal LoginUserDetails loginUserDetails,
			RedirectAttributes redirectAttributes,
			Model model) throws Exception {
		TrainingLog trainingLog = trainingService.getTrainingLog(trainingLogId);
		MUser user = trainingLog.getUser();
		if (loginUserDetails.getLoginUser().getId() != trainingLog.getUser().getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}

		trainingService.deleteTrainingLog(trainingLog);
		session.removeAttribute("trainingLog");
		return "redirect:/training/log/" + trainingLog.getTraining().getId();
	}

}
