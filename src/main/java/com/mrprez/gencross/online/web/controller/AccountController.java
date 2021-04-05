package com.mrprez.gencross.online.web.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrprez.gencross.online.model.id.UserId;
import com.mrprez.gencross.online.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="create", method = RequestMethod.GET)
    public String getCreate() {
        return "/jsp/createAccount.jsp";
    }
	
	@RequestMapping(value="include/update", method = RequestMethod.GET)
    public String getUpdate() {
        return "/jsp/include/updateAccountModal.jsp";
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		userService.createUser(username, email, password);
		return "/jsp/login.jsp";
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public void put(@RequestParam("userId") UserId userId, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		userService.updateUser(userId, username, email, password);
    }



}
