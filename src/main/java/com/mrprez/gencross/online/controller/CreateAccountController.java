package com.mrprez.gencross.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/createAccount")
public class CreateAccountController {
	
	@RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "/jsp/createAccount.jsp";
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) {
        return "/jsp/createAccount.jsp";
    }


}
