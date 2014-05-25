package com.auto.client.module.maintenance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.auto.client.R;
import com.auto.client.persistence.DBHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

public class MaintainActivity extends SherlockFragmentActivity implements
	ActionBar.TabListener {

	private static List<SherlockFragment> _tabFragments = new ArrayList<SherlockFragment>();
	
	static {
		_tabFragments.add(new HotRecommendSherlockFragment());
		_tabFragments.add(new StationSherlockFragment());
	}
	
	SectionsPagerAdapter _sectionsPagerAdapter;
	ViewPager _viewPager;
	
	private DBHelper dbHelper;
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.maintainer, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onChooseLocation(MenuItem item) {
		Intent intent = new Intent(this, ChooseLocationActivity.class);
		startActivity(intent);
	}
	
	public void onClickReservation(MenuItem item) {
		Intent intent = new Intent(this, ReservationMaintainActivity.class);
		startActivity(intent);
	}
	
	public void onClickMap(MenuItem item) {
		Intent intent = new Intent(this, StationMapActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain);
		
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		_sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		_viewPager = (ViewPager) findViewById(R.id.pager);
		_viewPager.setAdapter(_sectionsPagerAdapter);
		
		_viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
		for(int i = 0; i < _sectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText(_sectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
		
		dbHelper = new DBHelper(this);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment fragment = _sectionsPagerAdapter.getItem(tab.getPosition());
		_viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		System.out.print("cccc");
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		public Fragment getItem(int position) {
			return _tabFragments.get(position);
		}
		
		public int getCount() {
			return _tabFragments.size();
		}
		
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch(position) {
			case 0:
				return getString(R.string.maintain_recommend).toUpperCase(l);
			case 1:
				return getString(R.string.maintain_4s).toUpperCase(l);
			default:
				return "";
			}
		}
	}
	
	protected void onDestroy() {
		if(dbHelper != null) 
			dbHelper.close();
		super.onDestroy();
	}
	
	public DBHelper getDbHelper() {
		return dbHelper;
	}
}
