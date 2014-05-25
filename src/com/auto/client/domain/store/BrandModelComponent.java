package com.auto.client.domain.store;

import com.auto.client.domain.store.Component;
import com.auto.client.domain.store.Model;

public class BrandModelComponent {

	private int subBrandModelId;
	private int componentId;
	private String subCategory;
	private String description;
	private double price;
	
	public BrandModelComponent() {}
	
	public BrandModelComponent(int subBrandModelId, int componentId, 
			String subCategory, String description, double price) {
		this.setSubBrandModelId(subBrandModelId);
		this.setComponentId(componentId);
		this.setSubCategory(subCategory);
		this.setDescription(description);
		this.setPrice(price);
	}

	public int getSubBrandModelId() {
		return subBrandModelId;
	}

	public void setSubBrandModelId(int subBrandModelId) {
		this.subBrandModelId = subBrandModelId;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public Model getModel() {
		return null;
	}
	
	public Component getComponent() {
		return null;
	}
}
