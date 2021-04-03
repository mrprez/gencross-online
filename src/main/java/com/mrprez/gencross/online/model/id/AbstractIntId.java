package com.mrprez.gencross.online.model.id;

public abstract class AbstractIntId {
	private final int intId;
	
	
	
	
	public AbstractIntId(int id) {
		super();
		this.intId = id;
	}
	
	public AbstractIntId(String id) {
		this(Integer.parseInt(id));
	}


	public int getInt() {
		return intId;
	}
	
	@Override
	public int hashCode() {
		return intId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(this.getClass())) {
			return intId == ((AbstractIntId) obj).intId;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.valueOf(intId);
	}

}
