package com.auto.client.component.chat;


import java.util.Date;
import com.auto.client.domain.User;

public class ChatThread {

	private User peerUser;
	private String message;
	private Date lastTime;
	
	public User getPeerUser() {
		return peerUser;
	}
	public void setPeerUser(User peerUser) {
		this.peerUser = peerUser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	
}
