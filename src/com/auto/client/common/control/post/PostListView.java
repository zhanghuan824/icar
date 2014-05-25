package com.auto.client.common.control.post;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;

public class PostListView extends SherlockFragment {

	private ListView _postListContainer;
	private PostAdapter _adapter;
	private LinkedList<Post> _postList = new LinkedList<Post>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Post p = new Post();
		p.setImage("http://img1b.xgo-img.com.cn/pics/1503/1502117.jpg");
		p.setPostTime(null);
		p.setShortDescription("short desc");
		p.setTitle("这只是一个测试");
		PostLink pl = new PostLink("文章和姚笛在一起了", "http://ww2.sinaimg.cn/large/bca0b9cfjw1dzonmt7mzij.jpg", "http://www.163.com");
		PostLink pl1 = new PostLink("agaga文章和姚笛在一起了", "http://ww2.sinaimg.cn/large/bca0b9cfjw1dzonmt7mzij.jpg", "http://www.163.com");
		p.getPostLinkList().add(pl);
		p.getPostLinkList().add(pl1);
		p.getPostLinkList().add(pl1);
		_postList.add(p);
		
		Post p1 = new Post();
		p1.setImage("http://img1b.xgo-img.com.cn/pics/1503/1502117.jpg");
		p1.setPostTime(null);
		p1.setShortDescription("short desc");
		p1.setTitle("这只是一个测试");
		PostLink pl2 = new PostLink("文章和姚笛在一起了", "http://ww2.sinaimg.cn/large/bca0b9cfjw1dzonmt7mzij.jpg", "http://www.163.com");
		PostLink pl11 = new PostLink("agaga文章和姚笛在一起了", "http://ww2.sinaimg.cn/large/bca0b9cfjw1dzonmt7mzij.jpg", "http://www.163.com");
		p1.getPostLinkList().add(pl2);
		p1.getPostLinkList().add(pl11);
		
		_postList.add(p1);
		
		View rootView = inflater.inflate(R.layout.control_post_list, container);
		_postListContainer = (ListView) rootView.findViewById(R.id.control_post_list_container);
		_postListContainer.setBackgroundColor(Color.parseColor("#e4e4e4"));
		_adapter = new PostAdapter(this.getActivity(), R.layout.control_post, _postList);
		_postListContainer.setAdapter(_adapter);
		return rootView;
	}
	
	public void addPost(List<Post> posts) {
		for(int i = posts.size() - 1; i >= 0; i--) {
			_postList.addFirst(posts.get(i));
		}
		_adapter.notifyDataSetChanged();
	}
	
	public Post removePost(int index) {
		Post post = null;
		if(index >= 0 && index < _postList.size()) {
			post = _postList.remove(index);
			_adapter.notifyDataSetChanged();
		}
		return post;
	}
	
	public Post removeLast() {
		Post post = null;
		if(_postList.size() > 0) {
			post = _postList.removeLast();
			_adapter.notifyDataSetChanged();
		}
		return post;
	}
	
	public Post removeFirst() {
		Post post = null;
		if(_postList.size() > 0) {
			post = _postList.removeFirst();
			_adapter.notifyDataSetChanged();
		}
		return post;
	}
}
