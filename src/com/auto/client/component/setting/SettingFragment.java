package com.auto.client.component.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;

public class SettingFragment extends SherlockFragment {
	
	public static final String TITLE = "…Ë÷√";
	private View _userInfoView;
	private View _areaView;
	private View _brandView;
	private View _buyYearView;
	private View _checkUpdateView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.component_setting,
				container, false);
		
		_userInfoView = rootView.findViewById(R.id.user_info_div);
		_userInfoView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getSherlockActivity(), UserInfoSettingActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		_areaView = rootView.findViewById(R.id.area_div);
		_areaView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getSherlockActivity(), AreaSettingActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		_brandView = rootView.findViewById(R.id.brand_car_div);
		_brandView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getSherlockActivity(), CarModelSettingActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		_buyYearView = rootView.findViewById(R.id.buy_car_year_div);
		_buyYearView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getSherlockActivity(), BuyCarRegistActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		
		_checkUpdateView = rootView.findViewById(R.id.check_update_div);
		_checkUpdateView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getSherlockActivity(), BuyCarRegistActivity.class);
				getSherlockActivity().startActivity(intent);
			}
		});
		
		return rootView;
	}
}
