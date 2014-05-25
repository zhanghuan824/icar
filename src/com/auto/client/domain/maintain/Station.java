package com.auto.client.domain.maintain;

import java.util.List;

import com.auto.client.domain.Location;
import com.auto.client.domain.maintain.ServiceCategory;

public class Station {

	private int id;
	private String name;
	private String image;
	private String address;
	private String desc;
	private int commentCount;
	private int visitCount;
	private double score;
	private int level;
	private double longitude;
	private double latitude;
	private Location location;
	private List<ServiceCategory> serviceCategories;
	
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public List<ServiceCategory> getServiceCategories() {
		return serviceCategories;
	}
	public void setServiceCategories(List<ServiceCategory> serviceCategories) {
		this.serviceCategories = serviceCategories;
	}
	
	
}
