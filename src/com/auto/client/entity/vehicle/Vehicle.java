package com.auto.client.entity.vehicle;

public class Vehicle {

	private String _brand;
	private String _subBrand;
	private String _purchaseYear;
	
	public String getBrand() {
		return _brand;
	}
	public void setBrand(String _brand) {
		this._brand = _brand;
	}
	public String getPurchaseYear() {
		return _purchaseYear;
	}
	public void setPurchaseYear(String _purchaseYear) {
		this._purchaseYear = _purchaseYear;
	}
	public String getSubBrand() {
		return _subBrand;
	}
	public void setSubBrand(String _subBrand) {
		this._subBrand = _subBrand;
	}
}
