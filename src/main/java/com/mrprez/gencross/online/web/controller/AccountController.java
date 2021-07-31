package com.mrprez.gencross.online.web.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mrprez.gencross.online.exception.EmailAlreadyExistException;
import com.mrprez.gencross.online.exception.UsernameAlreadyExistException;
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
    public ModelAndView post(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			userService.createUser(username, email, password);
		} catch (UsernameAlreadyExistException | EmailAlreadyExistException e) {
			ModelAndView modelAndView = new ModelAndView("/jsp/createAccount.jsp", "exception", e);
			modelAndView.addObject("username", username);
			modelAndView.addObject("email", email);
			modelAndView.addObject("password", password);
			return modelAndView;
		}
		return new ModelAndView("/jsp/login.jsp");
    }
	
	@RequestMapping(method = RequestMethod.PUT)
    public void put(@RequestParam("userId") UserId userId, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		userService.updateUser(userId, username, email, password);
    }



}
