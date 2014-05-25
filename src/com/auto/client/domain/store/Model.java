package com.auto.client.domain.store;

import com.auto.client.domain.store.SubAutoBrand;

public class Model {
	private int id;
	private int subBrandId;
	private String name;
	
	public Model() {}
	
	public Model(int id, int subBrandId, String name) {
		this.setId(id);
		this.setSubBrandId(subBrandId);
		this.setName(name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubBrandId() {
		return subBrandId;
	}

	public void setSubBrandId(int subBrandId) {
		this.subBrandId = subBrandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SubAutoBrand getSubAutoBrand() {
		return null;
	}
}
