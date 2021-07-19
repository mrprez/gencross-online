package com.mrprez.gencross.online.web.dto.mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mrprez.gencross.Property;
import com.mrprez.gencross.online.web.dto.PropertyDto;

@Component
public class PropertyBeanToDtoMapper implements Function<Property, PropertyDto> {

	@Override
	public PropertyDto apply(Property bean) {
		PropertyDto dto = new PropertyDto();
		dto.setName(bean.getFullName());
		dto.setAbsoluteName(bean.getAbsoluteName());
		if (bean.getValue() != null) {
			dto.setValue(bean.getValue().getString());
			dto.setValueType(bean.getValue().getClass().getSimpleName());
			if (bean.getValue().getOffset() != null) {
				dto.setValueOffset(bean.getValue().getOffset().toString());
			}
		}
		if (bean.getMin() != null) {
			dto.setMinValue(bean.getMin().getString());
		}
		if (bean.getMax() != null) {
			dto.setMaxValue(bean.getMax().getString());
		}
		if (bean.getOptions() != null) {
			dto.setOptions(bean.getOptions().stream().map(Object::toString).collect(Collectors.toList()));
		}
		dto.setEditable(bean.isEditable());
		if (bean.getOwner() instanceof Property && !((Property) bean.getOwner()).getSubProperties().isFixe()) {
			dto.setRemovable(bean.isRemovable());
		}
		
		if (bean.getSubProperties()!=null) {
			dto.setSubProperties(bean.getSubProperties().stream().map(this).collect(Collectors.toList()));
			dto.setSubPropertiesListFixe(bean.getSubProperties().isFixe());
			if (bean.getSubProperties().getOptions() != null) {
				dto.setSubPropertiesListOptions(bean.getSubProperties().getOptions().keySet().stream().sorted().collect(Collectors.toList()));
			}
			dto.setSubPropertiesListOpen(bean.getSubProperties().isOpen());
		}
		
		return dto;
	}
	
}
