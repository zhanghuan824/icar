package com.auto.client.entity;

public class MaintainerRecommend {

	private String _title;
	private String _imageURL;
	private String _description;
	private Partner _partner;
	
	public String getTitle() {
		return _title;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}
	public String getImageURL() {
		return _imageURL;
	}
	public void setImageURL(String _imageURL) {
		this._imageURL = _imageURL;
	}
	public String getDescription() {
		return _description;
	}
	public void setDescription(String _description) {
		this._description = _description;
	}
	public Partner getPartner() {
		return _partner;
	}
	public void setPartner(Partner _partner) {
		this._partner = _partner;
	}
}
