package com.mrprez.gencross.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "/jsp/login.jsp";
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println(username + "/" + password);
        return "/jsp/login.jsp";
    }

}
