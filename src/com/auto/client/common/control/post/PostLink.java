package com.auto.client.common.control.post;

/**
 * PostLink是控件公用类，对于控件公用类，其余类需将值匹配到公用类；
 * 而对于非控件公用类，在构建Adapter时，可以直接使用模型类
 * @author zhanghuan
 */
public class PostLink {

	private String _linkText;
	private String _linkImg;
	private String _linkUrl;
	
	public PostLink(String linkText, String linkImage, String linkUrl) {
		this.setLinkImg(linkImage);
		this.setLinkText(linkText);
		this.setLinkUrl(linkUrl);
	}

	public String getLinkText() {
		return _linkText;
	}

	public void setLinkText(String _linkText) {
		this._linkText = _linkText;
	}

	public String getLinkImg() {
		return _linkImg;
	}

	public void setLinkImg(String _linkImg) {
		this._linkImg = _linkImg;
	}

	public String getLinkUrl() {
		return _linkUrl;
	}

	public void setLinkUrl(String _linkUrl) {
		this._linkUrl = _linkUrl;
	}
}
