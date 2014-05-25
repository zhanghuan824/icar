package com.auto.client.domain.store;

import com.auto.client.domain.store.Country;
import com.auto.client.domain.store.Serial;

public class AutoBrand {

	private int id;
	private String name;
	private String firstChar;
	private int countryId;
	private int serialId;
	
	public AutoBrand() {
		
	}
	
	public AutoBrand(int id, String name, String firstChar, int countryId, int serialId) {
		this.id = id;
		this.setName(name);
		this.setFirstChar(firstChar);
		this.setCountryId(countryId);
		this.setSerialId(serialId);
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

	public String getFirstChar() {
		return firstChar;
	}

	public void setFirstChar(String firstChar) {
		this.firstChar = firstChar;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getSerialId() {
		return serialId;
	}

	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}
	
	public Serial getSerial() {
		return null;
	}
	
	public Country getCountry() {
		return null;
	}
}
