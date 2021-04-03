package com.mrprez.gencross.online.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.disk.PersonnageFactory;
import com.mrprez.gencross.disk.PluginDescriptor;

@Service
public class CharacterService {
	
	@Autowired
	private PersonnageFactory personnageFactory;
	
	
	public Collection<PluginDescriptor> getPluginList() {
		return personnageFactory.getPluginList();
	}

}
