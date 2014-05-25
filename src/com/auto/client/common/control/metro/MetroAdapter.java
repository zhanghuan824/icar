package com.auto.client.common.control.metro;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.auto.client.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MetroAdapter extends ArrayAdapter<MetroItem> {

	private Context _context;
	private int _layoutResourceId;
	private final List<MetroItem> _data;
	
	public MetroAdapter(Context context, int layoutResourceId, List<MetroItem> data) {
		super(context, layoutResourceId, data);
		_data = new ArrayList<MetroItem>(data);
		this._context = context;
		this._layoutResourceId = layoutResourceId;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = ((SherlockActivity)_context).getLayoutInflater();
			convertView = inflater.inflate(this._layoutResourceId, parent, false);
			MetroItemView metroItemView = (MetroItemView)convertView;
			
			metroItemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String clazzName = _data.get(position).getTargetActivityClazz();
					try {
						Intent intent = new Intent(_context, Class.forName(clazzName));						
						intent.putExtra("name", clazzName);
						_context.startActivity(intent);
						Toast.makeText(_context, "aaa", Toast.LENGTH_LONG);
					} catch(Exception e) {
						Toast.makeText(_context, "error, not found: " + clazzName, Toast.LENGTH_LONG);
						e.printStackTrace();
					}
				}
			});
		}
		
		ImageView image = (ImageView)convertView.findViewById(R.id.control_metro_item_image);
		TextView text = (TextView)convertView.findViewById(R.id.control_metro_item_title);
		
		text.setText(_data.get(position).getTitle());
		image.setBackgroundColor(_data.get(position).getColor());
		return convertView;
	}
}
