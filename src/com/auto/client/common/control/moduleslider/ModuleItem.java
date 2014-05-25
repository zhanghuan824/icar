package com.auto.client.common.control.moduleslider;

public class ModuleItem {
	
	private String _name;
	private String _moduleActivityClassName;
	private String _image;
	
	public ModuleItem(String name, String image, String moduleActivityClassName) {
		this._image = image;
		this._moduleActivityClassName = moduleActivityClassName;
		this._name = name;
	}

	public String getName() {
		return _name;
	}

	public void setName(String _name) {
		this._name = _name;
	}

	public String getModuleActivityClassName() {
		return _moduleActivityClassName;
	}

	public void setModuleActivityClassName(String _moduleActivityClassName) {
		this._moduleActivityClassName = _moduleActivityClassName;
	}

	public String getImage() {
		return _image;
	}

	public void setImage(String _image) {
		this._image = _image;
	}

}
