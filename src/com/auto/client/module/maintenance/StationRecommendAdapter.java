package com.auto.client.module.maintenance;

import java.util.List;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;
import com.auto.client.entity.MaintainerRecommend;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StationRecommendAdapter extends BaseAdapter {

	private Context _context;
	private int _resourceId;
	private List<MaintainerRecommend> _recommendList;
	
	public StationRecommendAdapter(Context context, int resourceId, List<MaintainerRecommend> recommendList) {
		this._context = context;
		this._recommendList = recommendList;
		this._resourceId = resourceId;
	}
	
	@Override
	public int getCount() {
		return _recommendList.size();
	}

	@Override
	public Object getItem(int position) {
		return _recommendList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = ((SherlockFragmentActivity)_context).getLayoutInflater();
			convertView = inflater.inflate(_resourceId, null);
		}
		MaintainerRecommend mr = _recommendList.get(position);
		TextView tvTitle = (TextView)convertView.findViewById(R.id.hot_recommend_title_text);
		ImageView ivRecommend = (ImageView)convertView.findViewById(R.id.hot_recommend_big_image);
		TextView tvDesc = (TextView)convertView.findViewById(R.id.hot_recommend_short_desc);
		TextView tvTime = (TextView)convertView.findViewById(R.id.hot_recommend_time);
		
		tvTitle.setText(mr.getTitle());
		tvDesc.setText(mr.getDescription());
		tvTime.setText("23:23");
		ivRecommend.setBackgroundResource(R.drawable.audi);
		return convertView;
	}

}
