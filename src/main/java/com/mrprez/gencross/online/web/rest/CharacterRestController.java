package com.mrprez.gencross.online.web.rest;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mrprez.gencross.online.service.CharacterService;

@RestController
@RequestMapping(value= "/character")
public class CharacterRestController {
	
	@Autowired
	private CharacterService characterService;
	
	@RequestMapping( method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<String> getPluginList() {
		return characterService.getPluginList().stream()
				.map(pluginDescriptor -> pluginDescriptor.getName())
				.collect(Collectors.toList());
	}

}
