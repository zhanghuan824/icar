package com.auto.client.component.chat;

import java.util.Date;
import com.auto.client.domain.User;

public class ChatMessage {

	private String _message;
	private Date _createTime;
	private User _from;
	private User _to;
	
	public ChatMessage(User from, User to, String message, Date createTime) {
		this.setFrom(from);
		this.setTo(to);
		this.setMessage(message);
		this.setCreateTime(createTime);
	}

	public String getMessage() {
		return _message;
	}

	public void setMessage(String _message) {
		this._message = _message;
	}

	public Date getCreateTime() {
		return _createTime;
	}

	public void setCreateTime(Date _createTime) {
		this._createTime = _createTime;
	}

	public User getFrom() {
		return _from;
	}

	public void setFrom(User from) {
		this._from = from;
	}
	
	public User getTo() {
		return _to;
	}

	public void setTo(User to) {
		this._to = to;
	}
}
