package com.mrprez.gencross.online.model.aggregation;

import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.User;

public class RpgCharacterWithPlayer {
	private RpgCharacter character;
	private User player;
	
	
	public RpgCharacter getCharacter() {
		return character;
	}
	public void setCharacter(RpgCharacter character) {
		this.character = character;
	}
	public User getPlayer() {
		return player;
	}
	public void setPlayer(User player) {
		this.player = player;
	}

}
