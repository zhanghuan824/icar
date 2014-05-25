package com.auto.client.domain.chat;

import java.util.Date;

public class ChatHistory {
	private int peerUserId;
	private String userName;
	private String shortMessage;
	private Date chatTime;
	
	public int getPeerUserId() {
		return peerUserId;
	}
	public void setPeerUserId(int peerUserId) {
		this.peerUserId = peerUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getShortMessage() {
		return shortMessage;
	}
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}
	public Date getChatTime() {
		return chatTime;
	}
	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}	
}
