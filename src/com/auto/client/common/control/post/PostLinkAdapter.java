package com.auto.client.common.control.post;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;
import com.auto.client.WebBrowserActivity;
import com.auto.client.common.InternetImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PostLinkAdapter extends BaseAdapter {

	private List<PostLink> _postLinkList;
	private Context _context;
	private int _resourceId;
	
	public PostLinkAdapter(Context context, int resourceId, final List<PostLink> postLinkList) {
		this._postLinkList = postLinkList;
		_context = context;
		_resourceId = resourceId;
	}
	
	@Override
	public int getCount() {
		return _postLinkList.size();
	}

	@Override
	public Object getItem(int position) {
		if(_postLinkList.size() > position && position >= 0)
			return _postLinkList.get(position);
		else return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = ((SherlockFragmentActivity)_context).getLayoutInflater();
			convertView = inflater.inflate(_resourceId, parent, false);
		}
		
		RelativeLayout relativeLayout = (RelativeLayout)convertView;
		final PostLink pl = _postLinkList.get(position);
		if(pl != null && pl.getLinkText().length() > 0) {
			TextView tvLinkTxt = (TextView)relativeLayout.findViewById(R.id.control_post_link_item_text);
			tvLinkTxt.setText(_postLinkList.get(position).getLinkText());
			
			if(pl.getLinkImg().length() > 0) {
				ImageView ivLink = (ImageView)relativeLayout.findViewById(R.id.control_post_link_item_img);
				InternetImageLoader imageLoader = new InternetImageLoader(_context);
				imageLoader.DisplayImage(pl.getLinkImg(), ivLink);
			}
			
			if(pl.getLinkImg().length() > 0) {
				//点击每个link的时候，若该link确实存在，则跳转到WebBrowserActivity
				relativeLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(_context, WebBrowserActivity.class);
						intent.putExtra("url", pl.getLinkUrl());
						_context.startActivity(intent);
					}
				});
			}
		}
		
		return convertView;
	}

}
