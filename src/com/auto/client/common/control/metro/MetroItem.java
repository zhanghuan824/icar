package com.auto.client.common.control.metro;

public class MetroItem {
	private String _title;
	private String _imgUrl;
	private int _color;
	private int _columnSpan = 1;
	private int _width;
	private int _height;
	private String _targetActivityClazz;
	
	public MetroItem(String target, String title, String img, int color, int width, int height) {
		this(target, title, img, color, width, height, 1);
	}
	
	public MetroItem(String target, String title, String img, int color, int width, int height, int columnSpan) {
		this.setTargetActivityClazz(target);
		this.setTitle(title);
		this.setImgUrl(img);
		this.setColor(color);
		this.setHeight(height);
		this.setWidth(width);
		this.setColumnSpan(columnSpan);
	}
	
	public String getTitle() {
		return _title;
	}
	
	public void setTitle(String _title) {
		this._title = _title;
	}
	
	public String getImgUrl() {
		return _imgUrl;
	}
	
	public void setImgUrl(String _imgUrl) {
		this._imgUrl = _imgUrl;
	}
	
	public int getColor() {
		return _color;
	}
	public void setColor(int _color) {
		this._color = _color;
	}

	public int getColumnSpan() {
		return _columnSpan;
	}

	public void setColumnSpan(int _columnSpan) {
		this._columnSpan = _columnSpan;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int _width) {
		this._width = _width;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int _height) {
		this._height = _height;
	}

	public String getTargetActivityClazz() {
		return _targetActivityClazz;
	}

	public void setTargetActivityClazz(String target) {
		this._targetActivityClazz = target;
	}
}
