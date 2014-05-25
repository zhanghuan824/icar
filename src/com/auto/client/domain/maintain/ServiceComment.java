package com.auto.client.domain.maintain;

import java.util.Date;

public class ServiceComment {

	private String userName;
	private String userImage;
	private String comment;
	private Date commentTime;
	private String serviceConsumed;
	private float rating;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	public String getServiceConsumed() {
		return serviceConsumed;
	}
	public void setServiceConsumed(String serviceConsumed) {
		this.serviceConsumed = serviceConsumed;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
}
