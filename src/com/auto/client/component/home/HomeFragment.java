package com.auto.client.component.home;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;
import com.auto.client.persistence.DBHelper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends SherlockFragment {

	public static final String TITLE = "³µÓÑ";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.component_home,
				container, false);	
		return rootView;
	}
}
