package com.fredtm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChangePasswordController {

	@RequestMapping(value = "/fred/change/password", method = RequestMethod.GET)
	public ModelAndView getChangePassword(@RequestParam String token) {
		ModelAndView modelAndView = new ModelAndView("changePassword");
		modelAndView.addObject("token",token);
		return modelAndView;
	}
	
}
