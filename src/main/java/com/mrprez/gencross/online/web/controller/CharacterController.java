package com.mrprez.gencross.online.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;
import com.mrprez.gencross.online.service.CharacterService;
import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;

@Controller
@RequestMapping("/character")
public class CharacterController {
	
	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;
	
	@Autowired
	private CharacterService characterService;
	
	
	@PostMapping
	public ModelAndView create(@RequestParam("tableId") TableId tableId, @RequestParam("name") String name) throws Exception {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		characterService.createCharacter(tableId, userId, name);
		return new ModelAndView("redirect:home", "tableId", tableId.getInt());
	}
	

}
