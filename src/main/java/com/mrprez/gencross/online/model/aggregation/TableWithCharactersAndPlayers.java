package com.mrprez.gencross.online.model.aggregation;

import java.util.List;

import com.mrprez.gencross.online.model.Table;

public class TableWithCharactersAndPlayers {
	private Table table;
	private List<RpgCharacterWithPlayer> charactersWithPlayers;
	
	
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public List<RpgCharacterWithPlayer> getCharactersWithPlayers() {
		return charactersWithPlayers;
	}
	public void setCharactersWithPlayers(List<RpgCharacterWithPlayer> charactersWithPlayers) {
		this.charactersWithPlayers = charactersWithPlayers;
	}

}
