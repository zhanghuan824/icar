package com.auto.client.domain.store;

import com.auto.client.domain.store.Part;

public class Component {

	private int id;
	private String name;
	private int partId;
	
	public Component() {}
	
	public Component(int id, String name, int partId) {
		this.setId(id);
		this.setName(name);
		this.setPartId(partId);
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

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}
	
	public Part getPart() {
		return null;
	}
	
}
