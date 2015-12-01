package com.fredtm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SwaggerController {

	@RequestMapping(value = "/swagger")
	public ModelAndView simpleUrlForDocumentation() {
		return new ModelAndView("");
	}

}
