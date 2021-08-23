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
	@Autowired
	private HistoryItemBeanToDtoMapper historyItemMapper;
	

	@Override
	public CharacterDto apply(Personnage bean) {
		CharacterDto dto = new CharacterDto();
		dto.setPhase(bean.getPhase());
		dto.setPointPools(bean.getPointPools().values().stream().map(pointPoolMapper).collect(Collectors.toList()));
		dto.setErrors(bean.getErrors());
		dto.setActionMessage(bean.getActionMessage());
		dto.setProperties(bean.getProperties().stream().map(propertyMapper).collect(Collectors.toList()));
		dto.setNextPhase(bean.nextPhase() != null);
		dto.setNextPhaseAvailable(bean.phaseFinished());
		dto.setHistory(bean.getHistory().stream().map(historyItemMapper).collect(Collectors.toList()));
		
		return dto;
	}
	

}
