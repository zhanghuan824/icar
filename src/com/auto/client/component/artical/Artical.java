package com.auto.client.component.artical;

import java.util.Date;

import com.auto.client.domain.ListItem;

public class Artical implements ListItem {

	private String title;
	private int followUpCount;
	private String image;
	private Date updateTime;
	private String shortDesc;
	private String url;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getFollowUpCount() {
		return followUpCount;
	}
	public void setFollowUpCount(int followUpCount) {
		this.followUpCount = followUpCount;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
