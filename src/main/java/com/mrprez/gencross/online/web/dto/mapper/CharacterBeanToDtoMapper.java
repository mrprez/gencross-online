package com.mrprez.gencross.online.web.dto.mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.online.web.dto.CharacterDto;

@Component
public class CharacterBeanToDtoMapper implements Function<Personnage, CharacterDto> {
	
	@Autowired
	private PropertyBeanToDtoMapper propertyMapper;
	

	@Override
	public CharacterDto apply(Personnage bean) {
		CharacterDto dto = new CharacterDto();
		dto.setProperties(bean.getProperties().stream().map(propertyMapper).collect(Collectors.toList()));
		
		return dto;
	}
	

}
