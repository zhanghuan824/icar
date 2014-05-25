package com.auto.client.component.artical;

import java.util.LinkedList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;
import com.auto.client.common.InternetImageLoader;
import com.auto.client.domain.ListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticalAdapter extends BaseAdapter {

	private Context context;
	private LinkedList<ListItem> articalList;
	private int resourceId;
	
	public ArticalAdapter(Context context, int resourceId, LinkedList<ListItem> articalList) {
		this.context = context;
		this.resourceId = resourceId;
		this.articalList = articalList;
	}
	
	@Override
	public int getCount() {
		return articalList.size();
	}

	@Override
	public Object getItem(int position) {
		return articalList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = ((SherlockFragmentActivity)context).getLayoutInflater();
			convertView = inflater.inflate(resourceId, parent, false);
		}
		Artical artical = (Artical)articalList.get(position);
		TextView tvTitle = (TextView)convertView.findViewById(R.id.list_item_title);
		ImageView imgView = (ImageView)convertView.findViewById(R.id.list_item_image);
		TextView tvContent = (TextView)convertView.findViewById(R.id.list_item_content);
		TextView tvTime = (TextView)convertView.findViewById(R.id.list_item_time);
		
		tvTitle.setText(artical.getTitle());
		tvContent.setText(artical.getShortDesc());
		tvTime.setText(artical.getUpdateTime().getHours() + ":" + artical.getUpdateTime().getMinutes());
		if(artical.getImage().length() > 0) {
			InternetImageLoader imageLoader = new InternetImageLoader(context);
			imageLoader.DisplayImage(artical.getImage(), imgView);
		} else {
			imgView.setVisibility(View.GONE);
		}
		return convertView;
	}

}
