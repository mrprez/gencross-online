package com.mrprez.gencross.online.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.online.model.id.CharacterId;
import com.mrprez.gencross.online.model.id.UserId;
import com.mrprez.gencross.online.service.CharacterService;
import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;
import com.mrprez.gencross.online.web.dto.CharacterDto;
import com.mrprez.gencross.online.web.dto.mapper.CharacterBeanToDtoMapper;

@RestController
@RequestMapping(value= "/rest/character")
public class CharacterRestController {

	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;
	
	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private CharacterBeanToDtoMapper characterBeanToDtoMapper;

	
	@PostMapping(path = "{characterId}/setValue")
	public CharacterDto setValue(@PathVariable("characterId") CharacterId characterId, @RequestParam("property") String property, 
			@RequestParam("value") String value) throws Exception {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		Personnage personnage = characterService.setValue(characterId, property, value, userId);
		return characterBeanToDtoMapper.apply(personnage);
	}
	
	
}
