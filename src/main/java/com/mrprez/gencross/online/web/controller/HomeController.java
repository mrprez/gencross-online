package com.mrprez.gencross.online.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mrprez.gencross.online.model.RpgTable;
import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;
import com.mrprez.gencross.online.service.TableService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private TableService rpgTableService;
	
	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;

	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
		List<RpgTable> userGmTables = rpgTableService.getUserGmTables(authenticationProvider.getAuthenticatedUser().getId());
		RpgTable table = new RpgTable();
		table.setName("TableName");
		userGmTables.add(table);
        return new ModelAndView("/jsp/home.jsp", "userGmTables", userGmTables);
    }

}
