package com.mrprez.gencross.online.web.dto.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.mrprez.gencross.PoolPoint;
import com.mrprez.gencross.online.web.dto.PointPoolDto;

@Component
public class PointPoolBeanToDtoMapper implements Function<PoolPoint, PointPoolDto> {
	
	
	@Override
	public PointPoolDto apply(PoolPoint bean) {
		PointPoolDto dto = new PointPoolDto();
		dto.setName(bean.getName());
		dto.setRemaining(bean.getRemaining());
		dto.setTotal(bean.getTotal());
		
		return dto;
	}
	

}
