package com.auto.client.module.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;
import com.auto.client.entity.MaintainerRecommend;

public class HotRecommendSherlockFragment extends SherlockFragment {
	
	private StationRecommendAdapter _adapter = null;
	private ArrayList<MaintainerRecommend> _data = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.maintenance_hot_recommend_list_view,
				container, false);	
		_data = new ArrayList<MaintainerRecommend>();
		MaintainerRecommend mr = new MaintainerRecommend();
		mr.setTitle("我是一只小小鸟");
		mr.setDescription("宝马4S店为您提供最适合的保养服务");
		_data.add(mr);
		_data.add(mr);
		_adapter = new StationRecommendAdapter(getActivity(), 
				R.layout.maintenance_hot_recommend_list_view_item, 
				_data);
		ListView containerListView = (ListView)rootView.findViewById(R.id.maintenance_hot_recommand_list);
		containerListView.setAdapter(_adapter);
		return rootView;
	}
}
