package com.mrprez.gencross.online.model.id;

public abstract class AbstractIntId {
	private final int id;
	
	
	
	
	public AbstractIntId(int id) {
		super();
		this.id = id;
	}
	
	public AbstractIntId(String id) {
		super();
		this.id = Integer.parseInt(id);
	}


	public int getInt() {
		return id;
	}

}
