package com.auto.client.component.setting;

import com.auto.client.R;
import com.auto.client.R.layout;
import com.auto.client.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserInfoSettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_setting);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info_setting, menu);
		return true;
	}

}
