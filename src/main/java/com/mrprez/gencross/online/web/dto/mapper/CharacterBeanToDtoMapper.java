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
	@Autowired
	private PointPoolBeanToDtoMapper pointPoolMapper;
	

	@Override
	public CharacterDto apply(Personnage bean) {
		CharacterDto dto = new CharacterDto();
		dto.setPhase(bean.getPhase());
		dto.setPointPools(bean.getPointPools().values().stream().map(pointPoolMapper).collect(Collectors.toList()));
		dto.setErrors(bean.getErrors());
		dto.setProperties(bean.getProperties().stream().map(propertyMapper).collect(Collectors.toList()));
		if (bean.getPhaseList() != null && !bean.getPhaseList().isEmpty()) {
			String lastPhase = bean.getPhaseList().get(bean.getPhaseList().size()-1);
			dto.setNextPhase(! lastPhase.equals(bean.getPhase()));
		} else {
			dto.setNextPhase(false);
		}
		
		dto.setNextPhaseAvailable(bean.phaseFinished());
		
		return dto;
	}
	

}
