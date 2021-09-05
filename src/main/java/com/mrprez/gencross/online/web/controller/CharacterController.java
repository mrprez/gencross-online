package com.mrprez.gencross.online.web.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mrprez.gencross.online.exception.NotAllowedAccessException;
import com.mrprez.gencross.online.exception.UserNotFoundException;
import com.mrprez.gencross.online.model.RpgCharacterWithTable;
import com.mrprez.gencross.online.model.TableWithCharacters;
import com.mrprez.gencross.online.model.id.CharacterId;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;
import com.mrprez.gencross.online.service.CharacterService;
import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;
import com.mrprez.gencross.online.service.TableService;

@Controller
@RequestMapping("/character")
public class CharacterController {
	
	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;
	
	@Autowired
	private CharacterService characterService;

	@Autowired
	private TableService tableService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@PostMapping
	public ModelAndView create(@RequestParam("tableId") TableId tableId, @RequestParam("name") String name) throws Exception {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		characterService.createCharacter(tableId, userId, name);
		return new ModelAndView("redirect:home", "tableId", tableId.getInt());
	}
	
	@GetMapping(path = "{characterId}")
	public ModelAndView get(@PathVariable("characterId") CharacterId characterId) throws Exception {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		RpgCharacterWithTable characterWithTable = characterService.getRpgCharacterWithTable(characterId, userId);
		ModelAndView modelAndView = new ModelAndView("/jsp/character.jsp");
		modelAndView.addObject("character", characterWithTable.getRpgCharacter());
		modelAndView.addObject("table", characterWithTable.getTable());
		return modelAndView;
	}
	
	@GetMapping(path = "{characterId}/include/attributeToPlayer")
	public ModelAndView getAttributeToPlayerModal(@PathVariable("characterId") CharacterId characterId) {
		return new ModelAndView("/jsp/include/attributeToPlayerModal.jsp", "characterId", characterId);
	}
	
	@PostMapping(path = "{characterId}/attributeToPlayer")
	public ModelAndView attributeToPlayer(@PathVariable("characterId") CharacterId characterId, 
			@RequestParam("searchOrInvite") String searchOrInvite, @RequestParam("searchedPlayer") String searchedPlayer,
			Locale locale) {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		RpgCharacterWithTable characterWithTable = characterService.getRpgCharacterWithTable(characterId, userId);
		if (! characterWithTable.getTable().getGmId().equals(userId)) {
			throw new NotAllowedAccessException();
		}
		
		if (searchOrInvite.equals("searchPlayer")) {
			try {
				characterService.attributeToExistingPlayer(characterId, searchedPlayer);
			} catch (UserNotFoundException e) {
				List<TableWithCharacters> userGmTables = tableService.getUserGmTables(authenticationProvider.getAuthenticatedUser().getId());
				ModelAndView modelAndView = new ModelAndView("/jsp/home.jsp");
				modelAndView.addObject("tableId", characterWithTable.getTable().getId());
				modelAndView.addObject("userGmTables", userGmTables);
				modelAndView.addObject("error", e);
				modelAndView.addObject("errorMessage", messageSource.getMessage("label.playerNotFound", new Object[] {searchedPlayer}, locale));
				return modelAndView;
			}
		}
		return new ModelAndView("redirect:/dispatcher/home");
	}
	

}
