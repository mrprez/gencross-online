package com.mrprez.gencross.online.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.online.model.LoadedCharacter;
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

	
	@GetMapping(path = "{characterId}")
	public CharacterDto get(@PathVariable("characterId") CharacterId characterId) throws Exception {
		LoadedCharacter loadedCharacter = characterService.getCharachter(characterId);
		return characterBeanToDtoMapper.apply(loadedCharacter.getData());
	}
	
	@PutMapping(path = "{characterId}/setValue",
			  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public CharacterDto setValue(@PathVariable("characterId") CharacterId characterId,
			@RequestParam("property") String property, @RequestParam("value") String value) throws Exception {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		Personnage personnage = characterService.setValue(characterId, property, value, userId);
		return characterBeanToDtoMapper.apply(personnage);
	}
	
	@PostMapping(path = "{characterId}/addProperty",
			  consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public CharacterDto addProperty(@PathVariable("characterId") CharacterId characterId,
			@RequestParam("parentProperty") String parentAbsoluteName, @RequestParam("name") String newPropertyName) throws Exception {
		UserId userId = authenticationProvider.getAuthenticatedUser().getId();
		Personnage personnage = characterService.addProperty(characterId, parentAbsoluteName, newPropertyName, userId);
		return characterBeanToDtoMapper.apply(personnage);
	}
	
	
}
