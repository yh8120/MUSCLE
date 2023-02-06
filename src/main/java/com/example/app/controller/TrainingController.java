package com.example.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.MUser;
import com.example.app.domain.Training;
import com.example.app.domain.TrainingLog;
import com.example.app.domain.TrainingPart;
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
	public String getTraining(HttpSession session,
			@AuthenticationPrincipal UserDetails loginUser, Model model) throws Exception {
		MUser user = userService.getUserbyLogin(loginUser.getUsername());
		session.setAttribute("user", user);
		List<TrainingPart> trainingPartList = trainingService.getTrainingListOrderByPart(user.getId());
		model.addAttribute("trainingList", trainingPartList);
		webSocketMessage.sendToUser(user.getId());
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
	public String postAddTraining(HttpSession session,
			@Valid Training training,
			Errors errors,
			Model model) throws Exception {
		if (errors.hasErrors()) {
			model.addAttribute("trainingPartList", trainingPartService.getTrainingPartList());
			model.addAttribute("trainingTypeList", trainingTypeService.getTrainingTypeList());
			model.addAttribute("priorityList", priorityService.getPriorityList());
			model.addAttribute("weekdayList", weekdayService.getWeekdayList());
			model.addAttribute("training", new Training());
			return "training/add-training";
		}
		
		MUser user = (MUser)session.getAttribute("user");
		training.setUserId(user.getId());
		
		trainingService.addTraining(training);
		
		return "redirect:/training";
	}

	@GetMapping("/log/{id}")
	public String getLog(HttpSession session,
			RedirectAttributes redirectAttributes,
			@PathVariable("id") Integer trainingId,
			Model model) throws Exception {

		Training training = trainingService.getTraining(trainingId);
		MUser user = (MUser) session.getAttribute("user");
		if (training.getUserId() != user.getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}

		model.addAttribute("training", training);

		return "training/log";
	}

	@GetMapping("/log/add/{id}")
	public String getAddLog(HttpSession session,
			RedirectAttributes redirectAttributes,
			@PathVariable("id") Integer trainingId,
			Model model) throws Exception {

		Training training = trainingService.getTraining(trainingId);
		MUser user = (MUser) session.getAttribute("user");
		if (training.getUserId() != user.getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}

		model.addAttribute("training", training);

		return "training/add-log";
	}

	@PostMapping("/log/add/{trainingId}")
	public String postAddLog(
			HttpSession session,
			@PathVariable("trainingId") Integer trainingId,
			@Valid TrainingLog trainingLog,
			Errors errors,
			Model model) throws Exception {
		Training training = trainingService.getTraining(trainingId);
		if (errors.hasErrors()) {
			model.addAttribute("training", training);
			return "training/add-log";
		}

		MUser user = (MUser) session.getAttribute("user");
		training.setId(trainingId);

		trainingLog.setUser(user);
		trainingLog.setTraining(training);

		Integer trainingLogId = trainingService.addTrainingLog(trainingLog);
		webSocketMessage.send(trainingLogId);

		return "redirect:/training/log/" + trainingId;
	}

	@GetMapping("/log/edit/{trainingLogId}")
	public String getEditLog(
			HttpSession session,
			RedirectAttributes redirectAttributes,
			@PathVariable("trainingLogId") Integer trainingLogId,
			Model model) throws Exception {
		TrainingLog trainingLog = trainingService.getTrainingLog(trainingLogId);
		MUser user = trainingLog.getUser();
		MUser sessionUser = (MUser) session.getAttribute("user");
		if (user.getId() != sessionUser.getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}

		session.setAttribute("trainingLog", trainingLog);
		return "training/edit-log";
	}

	@PostMapping("/log/edit/{id}")
	public String postEditLog(
			HttpSession session,
			RedirectAttributes redirectAttributes,
			@PathVariable("id") Integer id,
			@Valid TrainingLog trainingLog,
			Errors errors) throws Exception {
		TrainingLog sessionTrainingLog = (TrainingLog) session.getAttribute("trainingLog");
		TrainingLog checkLog = trainingService.getTrainingLog(id);

		MUser user = sessionTrainingLog.getUser();
		MUser checkUser = checkLog.getUser();

		if (user.getId() != checkUser.getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}
		if (errors.hasErrors()) {
			return "training/edit-log/" + id;
		}
		trainingLog.setUser(user);
		trainingLog.setTraining(sessionTrainingLog.getTraining());

		trainingService.editTrainingLog(trainingLog);

		Integer trainingId = (sessionTrainingLog.getTraining()).getId();
		session.removeAttribute("trainingLog");

		return "redirect:/training/log/" + trainingId;
	}

	@GetMapping("/log/delete")
	public String getDeleteLog(
			@RequestParam("id") Integer trainingId,
			@RequestParam("log") Integer trainingLogId,
			HttpSession session,
			RedirectAttributes redirectAttributes,
			Model model) throws Exception {
		TrainingLog trainingLog = trainingService.getTrainingLog(trainingLogId);
		MUser user = trainingLog.getUser();
		MUser sessionUser = (MUser) session.getAttribute("user");
		if (user.getId() != sessionUser.getId()) {
			redirectAttributes.addFlashAttribute("message", "不正な参照です");
			return "redirect:/training";
		}

		trainingService.deleteTrainingLog(trainingLog);
		session.removeAttribute("trainingLog");
		return "redirect:/training/log/" + trainingId;
	}

}
