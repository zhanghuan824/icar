package com.auto.client.common.control.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Post�ǿؼ������࣬���ڿؼ������࣬�������轫ֵƥ�䵽�����ࣻ
 * �����ڷǿؼ������࣬�ڹ���Adapterʱ������ֱ��ʹ��ģ����
 * @author zhanghuan
 */
public class Post {
	private String _title;
	private Date _postTime;
	private String _image;
	private String _shortDescription;
	private List<PostLink> _postLinkList = new ArrayList<PostLink>();
	
	public String getTitle() {
		return _title;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}
	public Date getPostTime() {
		return _postTime;
	}
	public void setPostTime(Date _postTime) {
		this._postTime = _postTime;
	}
	public String getImage() {
		return _image;
	}
	public void setImage(String _image) {
		this._image = _image;
	}
	public String getShortDescription() {
		return _shortDescription;
	}
	public void setShortDescription(String _shortDescription) {
		this._shortDescription = _shortDescription;
	}
	public List<PostLink> getPostLinkList() {
		return _postLinkList;
	}
	public void setPostLinkList(List<PostLink> _postLinkList) {
		this._postLinkList = _postLinkList;
	}
	
	
}
