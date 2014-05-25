package com.auto.client.module.maintenance;

import com.actionbarsherlock.app.SherlockActivity;
import com.auto.client.R;

import android.os.Bundle;
import com.actionbarsherlock.view.Menu;

public class ReservationMaintainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reservation_maintain);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.reservation_maintain, menu);
		return true;
	}

	
	
}
