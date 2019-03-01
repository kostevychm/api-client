package me.kostevych.zonkyapi.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import me.kostevych.zonkyapi.services.LoanService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MainController {
	
	@Autowired
	private LoanService loanService;
	
	@GetMapping
	public ModelAndView index(Map<String, Object> model) {
		model.put("loans", loanService.getLoans());
		return new ModelAndView("loans", model);
	}
}
