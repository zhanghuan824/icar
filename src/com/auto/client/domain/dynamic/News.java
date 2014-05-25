package com.auto.client.domain.dynamic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auto.client.domain.dynamic.NewsSubItem;

public class News {
	
	private long id;
	private String title;
	private String desc;
	private String image;
	private String url;
	private Date updateTime;
	private List<NewsSubItem> subItems;

	public News() {
		subItems = new ArrayList<NewsSubItem>();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<NewsSubItem> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<NewsSubItem> subItems) {
		this.subItems = subItems;
	}
	
	public void addNewsSubItem(NewsSubItem nsi) {
		subItems.add(nsi);
	}
}
