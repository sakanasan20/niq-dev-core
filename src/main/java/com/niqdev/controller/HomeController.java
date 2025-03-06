package com.niqdev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = HomeController.PATH)
public class HomeController {
	
	public static final String PATH = "/home";

	@GetMapping
	public String home() {
		return "home";
	}
	
}
