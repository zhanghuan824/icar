package com.auto.client.common.control.metro;

import com.auto.client.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MetroItemView extends RelativeLayout {

	public static final String TAG = "MetroItemView";
	
	private ImageView _imageView;
	private TextView _textView;
	
	public MetroItemView(Context context) {
		super(context);
	}
	
	public MetroItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setColor(int color) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.control_metro_item, null);
		view.setBackgroundColor(color);
	}
	
	public void setImage(int resource) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.control_metro_item, null);
		_imageView = (ImageView)view.findViewById(R.id.control_metro_item_image);
		_imageView.setBackgroundColor(resource); //ÐèÒª¸Ä
	}
	
	public void setTitle(String title) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.control_metro_item, null);
		_textView = (TextView)view.findViewById(R.id.control_metro_item_title);
		_textView.setText(title);
	}
	
	public void setWidth(int width) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.control_metro_item, null);
		AbsListView.LayoutParams params = (AbsListView.LayoutParams)view.getLayoutParams();
		params.width = width;
	}
	
	public void setHeight(int height) {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.control_metro_item, null);
		AbsListView.LayoutParams params = (AbsListView.LayoutParams)view.getLayoutParams();
		params.height = height;
	}
}
