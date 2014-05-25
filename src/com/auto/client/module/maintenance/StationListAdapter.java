package com.auto.client.module.maintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;
import com.auto.client.module.maintenance.StationSherlockFragment;
import com.auto.client.common.InternetImageLoader;
import com.auto.client.domain.maintain.Station;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * PartnerListAdapter provides data adapter for the list view in 
 * partner fragment. Which include some fields, such as title,
 * content, rating and the image who comes from internet.
 */
public class StationListAdapter extends BaseAdapter {

	public static final String PARTNER_TITLE = "title";
	public static final String PARTNER_CONTENT = "content";
	public static final String PARTNER_RATING = "rating";
	public static final String PARTNER_IMAGE = "image";
	
	private SherlockFragment _fragment;	
	private List<Station> _data;
	
	/**
	 * imageLoader is the toolkit to load image from Internet
	 */
	private InternetImageLoader _imageLoader;
	
	private static LayoutInflater _inflater = null;
	
	public StationListAdapter(SherlockFragment fragment) {
		this(fragment, null);
	}
	
	public StationListAdapter(SherlockFragment fragment, List<Station> data) {
		this._fragment = fragment;
		this._data = data;
		Activity activity = fragment.getActivity();
		
		// all the fragments in same activity share the same instance of LayoutInflater.
		_inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_imageLoader = new InternetImageLoader(activity.getApplicationContext());
	}
	
	public List<Station> getData() {
		return _data;
	}
	
	public void setData(List<Station> data) {
		this._data = data;
	}
	
	@Override
	public int getCount() {
		return _data.size();
	}

	@Override
	public Object getItem(int position) {
		if(position < _data.size() && position >= 0)
			return _data.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if(convertView == null)
			vi = _inflater.inflate(R.layout.partner_list_item, null);
		TextView title = (TextView)vi.findViewById(R.id.title);
		TextView content = (TextView)vi.findViewById(R.id.content);
		TextView rating = (TextView)vi.findViewById(R.id.rating);
		ImageView thumb_image = (ImageView)vi.findViewById(R.id.list_image);
		
		Station station = _data.get(position);
		
		title.setText(station.getName());
		content.setText(station.getDesc());
		rating.setText(String.valueOf(station.getScore()));
		_imageLoader.DisplayImage(station.getImage(), thumb_image);
		return vi;
	}
}
