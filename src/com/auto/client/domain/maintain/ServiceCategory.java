package com.auto.client.domain.maintain;

import java.util.List;

import com.auto.client.domain.maintain.Service;

public class ServiceCategory {

	private String name;
	private List<Service> services;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
	}
}
