package com.mrprez.gencross.online.web.dto.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.mrprez.gencross.history.HistoryItem;
import com.mrprez.gencross.online.web.dto.HistoryItemDto;

@Component
public class HistoryItemBeanToDtoMapper implements Function<HistoryItem, HistoryItemDto> {

	@Override
	public HistoryItemDto apply(HistoryItem bean) {
		HistoryItemDto dto = new HistoryItemDto();
		dto.setAction(bean.getAction());
		dto.setAbsoluteName(bean.getAbsoluteName());
		dto.setDate(bean.getDate());
		dto.setPointPool(bean.getPointPool());
		if (bean.getOldValue() != null) {
			dto.setOldValue(bean.getOldValue().getString());
		}
		if (bean.getNewValue() != null) {
			dto.setNewValue(bean.getNewValue().getString());
		}
		dto.setCost(bean.getCost());
		
		return dto;
	}
	
}
