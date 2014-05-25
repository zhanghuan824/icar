package com.auto.client.domain.store;

import com.auto.client.domain.store.AutoBrand;

public class SubAutoBrand {
	private int id;
	private int brandId;
	private String name;
	
	public SubAutoBrand() {}
	
	public SubAutoBrand(int id, int brandId, String name) {
		this.setId(id);
		this.setBrandId(brandId);
		this.setName(name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public AutoBrand getBrand() {
		return null;
	}
}
