package com.auto.client.domain;

import java.util.Date;

import com.auto.client.domain.Location;

public class User {

	private int userId;
	private String email;
	private String password;
	private char sex;
	private Date birthday;
	private String nickname;
	private Location location;
	private String faceImage;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFaceImage() {
		return faceImage;
	}

	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}
}
