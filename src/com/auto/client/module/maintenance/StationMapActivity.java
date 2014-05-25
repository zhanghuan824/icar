package com.auto.client.module.maintenance;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.auto.client.R;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.os.Bundle;
import android.support.v4.app.NavUtils;

public class StationMapActivity extends SherlockActivity {

	private BMapManager _mapManager = null;
	private MapView _mapView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_mapManager = new BMapManager(getApplication());
		_mapManager.init(null);
		
		setContentView(R.layout.activity_maintain_map);
		// Show the Up button in the action bar.
		setupActionBar();
		
		_mapView = (MapView)findViewById(R.id.maintain_baidu_mapview);
		_mapView.setBuiltInZoomControls(true);
		//打开实时路况
		_mapView.setTraffic(true);
		MapController mapController = _mapView.getController();
		GeoPoint point = new GeoPoint((int)(39.915*1E6), (int)(116.404 *1E6));
		mapController.setCenter(point);
		mapController.setZoom(12);
	}
	
	protected void onDestroy() {
		_mapView.destroy();
		if(_mapManager != null) {
			_mapManager.destroy();
			_mapManager = null;
		}
		super.onDestroy();
	}
	
	protected void onPause() {
		_mapView.onPause();
		if(_mapManager != null) {
			_mapManager.stop();
		}
		super.onPause();
	}
	
	protected void onResume() {
		_mapView.onResume();
		if(_mapManager != null) {
			_mapManager.start();
		}
		super.onResume();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.maintain_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
