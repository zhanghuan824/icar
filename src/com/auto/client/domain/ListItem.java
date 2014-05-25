package com.auto.client.domain;

import java.util.Date;

public interface ListItem {
	String getTitle();
	void setTitle(String title);
	int getFollowUpCount();
	void setFollowUpCount(int count);
	String getImage();
	void setImage(String image);
	Date getUpdateTime();
	void setUpdateTime(Date updateTime);
	String getShortDesc();
	void setShortDesc(String shortDesc);
}
