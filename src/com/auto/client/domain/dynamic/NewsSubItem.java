package com.auto.client.domain.dynamic;

public class NewsSubItem {
	private long id;
	private String title;
	private String image;
	private String url;
	private long newsId;
	
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
	public long getNewsId() {
		return newsId;
	}
	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}	
}
