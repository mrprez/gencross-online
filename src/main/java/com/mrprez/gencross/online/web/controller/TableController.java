package com.mrprez.gencross.online.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrprez.gencross.online.model.id.UserId;
import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;
import com.mrprez.gencross.online.service.TableService;

@Controller
@RequestMapping("/table")
public class TableController {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;
	
	
	@PostMapping
	public String create(@RequestParam("name") String name, @RequestParam("game") String game) {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		tableService.createTable(name, game, userId);
		return "redirect:home";
	}

}
