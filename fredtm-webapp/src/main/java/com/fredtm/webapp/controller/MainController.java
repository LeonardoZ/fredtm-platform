package com.fredtm.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	
	@RequestMapping("/cadastro")
	public ModelAndView cadastro() {
		return new ModelAndView("cadastro");
	}
	
	@RequestMapping("/main")
	public ModelAndView main() {
		return new ModelAndView("main");
	}

	
}
