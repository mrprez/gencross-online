package com.mrprez.gencross.online.web.dto.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.mrprez.gencross.Property;
import com.mrprez.gencross.online.web.dto.PropertyDto;

@Component
public class PropertyBeanToDtoMapper implements Function<Property, PropertyDto> {

	@Override
	public PropertyDto apply(Property bean) {
		PropertyDto dto = new PropertyDto();
		dto.setName(bean.getName());
		dto.setAbsoluteName(bean.getAbsoluteName());
		if (bean.getValue() != null) {
			dto.setValue(bean.getValue().getString());
		}
		dto.setValue(null);
		return dto;
	}
	
}
