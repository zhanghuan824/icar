package com.auto.client.common.control.moduleslider;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;
import com.auto.client.module.community.CommunityMainActivity;
import com.auto.client.module.maintenance.MaintainActivity;
import com.auto.client.module.market.MarketMainActivity;
import com.auto.client.module.store.StoreMainActivity;
import com.auto.client.module.used.UsedMainActivity;

public class ModuleSlider extends SherlockFragment {

	private List<ModuleItem> _moduleList = new ArrayList<ModuleItem>();
	private GridView _gridView;
	private ModuleAdapter _adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = (ViewGroup)inflater.inflate(R.layout.control_module_slide, container, false);
		
		_moduleList.add(new ModuleItem("汽配", "", StoreMainActivity.class.getName()));
		_moduleList.add(new ModuleItem("保养", "", MaintainActivity.class.getName()));
		_moduleList.add(new ModuleItem("二手车", "", UsedMainActivity.class.getName()));
		_moduleList.add(new ModuleItem("行情", "", MarketMainActivity.class.getName()));
		_moduleList.add(new ModuleItem("车友", "", CommunityMainActivity.class.getName()));
		_moduleList.add(new ModuleItem("购车", "", MaintainActivity.class.getName()));
		
		_adapter = new ModuleAdapter(this.getActivity(), R.layout.control_module_slide_item, _moduleList);
		_gridView = (GridView)contentView.findViewById(R.id.control_module_gridview);
		_gridView.setAdapter(_adapter);
		return contentView;
	}
	
	public void addModules(List<ModuleItem> modules) {
		for(int i = 0; i < modules.size(); i++) {
			_moduleList.add(modules.get(i));
		}
		_adapter.notifyDataSetChanged();
	}
	
	public void addModule(ModuleItem module) {
		_moduleList.add(module);
		_adapter.notifyDataSetChanged();
	}
}
