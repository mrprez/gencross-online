package com.mrprez.gencross.online.web.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mrprez.gencross.disk.PluginDescriptor;
import com.mrprez.gencross.online.model.aggregation.TableWithCharactersAndPlayers;
import com.mrprez.gencross.online.service.CharacterService;
import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;
import com.mrprez.gencross.online.service.TableService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;

	
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView get() {
		List<TableWithCharactersAndPlayers> userGmTables = tableService.getUserGmTables(authenticationProvider.getAuthenticatedUser().getId());
		return new ModelAndView("/jsp/home.jsp", "userGmTables", userGmTables);
    }
	
	@RequestMapping(value = "/include/createTable", method = RequestMethod.GET)
    public ModelAndView getCreateTable() {
		Collection<PluginDescriptor> pluginList = characterService.getPluginList();
        return new ModelAndView("/jsp/include/createTableModal.jsp", "pluginList", pluginList);
    }
	

}
