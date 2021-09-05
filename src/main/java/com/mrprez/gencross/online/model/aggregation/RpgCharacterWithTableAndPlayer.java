package com.mrprez.gencross.online.model.aggregation;

import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.User;

public class RpgCharacterWithTableAndPlayer {
	private RpgCharacter rpgCharacter;
	private Table table;
	private User player;
	
	
	public RpgCharacter getRpgCharacter() {
		return rpgCharacter;
	}
	
	public void setRpgCharacter(RpgCharacter rpgCharacter) {
		this.rpgCharacter = rpgCharacter;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public User getPlayer() {
		return player;
	}

	public void setPlayer(User player) {
		this.player = player;
	}
	

}
