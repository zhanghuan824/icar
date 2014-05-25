package com.auto.client.common.control.post;

import java.util.Date;
import java.util.LinkedList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;
import com.auto.client.common.InternetImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PostAdapter extends BaseAdapter {

	// RowHeight控制Postlink listview每一行的高度
	private static final int ROW_HEIGHT = 110;
	
	private Context _context;
	private LinkedList<Post> _postList;
	private int _resourceId;
	
	public PostAdapter(Context context, int resourceId, LinkedList<Post> postList) {
		_context = context;
		_resourceId = resourceId;
		_postList = postList;
	}

	@Override
	public int getCount() {
		return _postList.size();
	}

	@Override
	public Object getItem(int position) {
		return _postList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = ((SherlockFragmentActivity)_context).getLayoutInflater();
			convertView = inflater.inflate(_resourceId, parent, false);
		}
		Post post = _postList.get(position);
		if(post.getTitle().length() > 0) {
			TextView tvPostTitle = (TextView)convertView.findViewById(R.id.control_post_title_text);
			tvPostTitle.setText(post.getTitle());
		}
		if(post.getImage().length() > 0) {
			ImageView imgView = (ImageView)convertView.findViewById(R.id.control_post_big_image);
			InternetImageLoader imageLoader = new InternetImageLoader(_context);
			imageLoader.DisplayImage(post.getImage(), imgView);
		}
		Date date = post.getPostTime();
		if(date != null) {
			TextView tvPostTime = (TextView)convertView.findViewById(R.id.control_post_time);
			tvPostTime.setText(date.getHours() + ":" + date.getMinutes());
		}
		if(post.getShortDescription().length() > 0) {
			TextView tvDesc = (TextView)convertView.findViewById(R.id.control_post_short_desc);
			tvDesc.setText(post.getShortDescription());
		}
		if(post.getPostLinkList().size() > 0) {
			ListView lvPostLinks = (ListView)convertView.findViewById(R.id.control_post_postlinks);
			
			lvPostLinks.getLayoutParams().height = post.getPostLinkList().size() * ROW_HEIGHT; 
			PostLinkAdapter plAdapter = new PostLinkAdapter(_context, R.layout.control_post_link, post.getPostLinkList());
			lvPostLinks.setAdapter(plAdapter);
		}
		
		return convertView;
	}

}
