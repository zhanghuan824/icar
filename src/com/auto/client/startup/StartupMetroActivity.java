package com.auto.client.startup;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.auto.client.R;
import com.auto.client.R.id;
import com.auto.client.R.layout;
import com.auto.client.common.control.metro.MetroAdapter;
import com.auto.client.common.control.metro.MetroItem;
import com.auto.client.common.control.metro.MetroItemView;
import com.auto.client.module.maintenance.MaintainActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;

public class StartupMetroActivity extends SherlockActivity {

	private List<MetroItem> _metroItems = new ArrayList<MetroItem>();
	private GridView _gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_metro_startup);
		
		this.getActionBar().hide();
		
		_metroItems.add(new MetroItem(MaintainActivity.class.getName(), "商城", "", Color.parseColor("#213775"), 110, 80));
		_metroItems.add(new MetroItem(MaintainActivity.class.getName(), "保养", "", Color.parseColor("#ff8800"), 110, 80));
		_metroItems.add(new MetroItem(MaintainActivity.class.getName(), "二手车", "", Color.parseColor("#99cc00"), 110, 80));
		_metroItems.add(new MetroItem(MaintainActivity.class.getName(), "行情", "", Color.parseColor("#d52424"), 110, 80));
		_metroItems.add(new MetroItem(MaintainActivity.class.getName(), "车友", "", Color.parseColor("#9933cc"), 110, 80));
		_metroItems.add(new MetroItem(MaintainActivity.class.getName(), "设置", "", Color.parseColor("#33b5e5"), 110, 80));
		
		_gridView = (GridView)findViewById(R.id.metro_grid);
		MetroAdapter adapter = new MetroAdapter(this, R.layout.control_metro_item, _metroItems);
		_gridView.setAdapter(adapter);
	}
}
