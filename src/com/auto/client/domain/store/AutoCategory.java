package com.auto.client.domain.store;

public class AutoCategory {
	
	private int id;
	private String name;
	
	public AutoCategory() {}
	
	public AutoCategory(int id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
