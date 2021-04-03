package com.mrprez.gencross.online.web.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrprez.gencross.online.service.UserService;

@Controller
@RequestMapping("/createAccount")
public class CreateAccountController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "/jsp/createAccount.jsp";
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		userService.createUser(username, email, password);
		return "/jsp/login.jsp";
    }


}
