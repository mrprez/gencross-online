package com.mrprez.gencross.online.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.TableWithCharacters;
import com.mrprez.gencross.online.model.id.TableId;
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
	public String create(@RequestParam("name") String name, @RequestParam("game") String game, RedirectAttributes redirectAttributes) {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		Table table = tableService.createTable(name, game, userId);
		redirectAttributes.addAttribute("tableId", table.getId());
		return "redirect:home";
	}
	
	@GetMapping(path = "/{tableId}/include/createCharacter")
	public ModelAndView getCreateCharacter(@PathVariable("tableId") TableId tableId) {
		return new ModelAndView("/jsp/include/createCharacterModal.jsp", "tableId", tableId);
    }
	
	@GetMapping(path = "/{tableId}")
	public ModelAndView get(@PathVariable("tableId") TableId tableId) {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		TableWithCharacters tableWithCharacters = tableService.getTableAsGm(tableId, userId);
		return new ModelAndView("/jsp/table.jsp", "table", tableWithCharacters.getTable());
    }
	

}
