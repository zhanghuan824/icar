package com.auto.client.module.maintenance;

import java.text.SimpleDateFormat;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;
import com.auto.client.common.InternetImageLoader;
import com.auto.client.domain.maintain.ServiceComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceCommentAdapter extends BaseAdapter {
	
	private Context context;
	private List<ServiceComment> list;
	private InternetImageLoader imageLoader;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	
	public ServiceCommentAdapter(Context context, List<ServiceComment> list) {
		this.context = context;
		this.list = list;
		this.imageLoader = new InternetImageLoader(context.getApplicationContext());
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			SherlockFragmentActivity activity = (SherlockFragmentActivity)context;
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.list_item_service_comment, null);
		}
		ServiceComment sc = list.get(position);
		TextView tv_username = (TextView)convertView.findViewById(R.id.username);
		tv_username.setText(sc.getUserName());
		tv_username.getPaint().setFakeBoldText(true);
		ImageView iv_user_level_img = (ImageView)convertView.findViewById(R.id.user_level_img);
		imageLoader.DisplayImage(sc.getUserImage(), iv_user_level_img);
		ImageView iv_rating_img = (ImageView)convertView.findViewById(R.id.rating_img);
		iv_rating_img.setImageDrawable(null); /////////////////////
		TextView tv_comment_time = (TextView)convertView.findViewById(R.id.comment_time);
		tv_comment_time.setText(dateFormat.format(sc.getCommentTime()));
		TextView tv_comment_txt = (TextView)convertView.findViewById(R.id.comment_txt);
		tv_comment_txt.setText(sc.getComment());
		return convertView;
	}

}
