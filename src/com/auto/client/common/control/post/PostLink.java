package com.auto.client.common.control.post;

/**
 * PostLink�ǿؼ������࣬���ڿؼ������࣬�������轫ֵƥ�䵽�����ࣻ
 * �����ڷǿؼ������࣬�ڹ���Adapterʱ������ֱ��ʹ��ģ����
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
