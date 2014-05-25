package com.auto.client.startup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;
import com.auto.client.component.artical.ArticalFragment;
import com.auto.client.component.home.HomeFragment;
import com.auto.client.component.news.NewsFragment;
import com.auto.client.component.setting.SettingFragment;
import com.auto.client.persistence.DBHelper;

public class StartupActivity extends SherlockFragmentActivity implements OnClickListener {

	private DBHelper dbHelper;
	
	private SherlockFragment _homePage;
	private SherlockFragment _newsPage;
	private SherlockFragment _settingPage;
	private SherlockFragment _articalPage;
	
	private FragmentManager _fragmentManager;
	
	private ImageView _homeImage;
	private TextView _homeText;
	
	private ImageView _articalImage;
	private TextView _articalText;
	
	private ImageView _newsImage;
	private TextView _newsText;
	
	private ImageView _settingImage;
	private TextView _settingText;
	
	private View _homeLayout;
	private View _articalLayout;
	private View _newsLayout;
	private View _settingLayout;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		
		getSupportActionBar().hide();
		
		_fragmentManager = this.getSupportFragmentManager();
		initViews();
		setTabSelection(0);
		
		this.dbHelper = new DBHelper(this);
	}
	
	private void initViews() {
		_homeImage = (ImageView)findViewById(R.id.home_image);
		_homeText = (TextView)findViewById(R.id.home_text);
		_articalImage = (ImageView)findViewById(R.id.chat_image);
		_articalText = (TextView)findViewById(R.id.artical_text);
		_newsImage = (ImageView)findViewById(R.id.news_image);
		_newsText = (TextView)findViewById(R.id.news_text);
		_settingImage = (ImageView)findViewById(R.id.setting_image);
		_settingText = (TextView)findViewById(R.id.setting_text);
		_homeLayout = findViewById(R.id.home_layout);
		_articalLayout = findViewById(R.id.chat_layout);
		_newsLayout = findViewById(R.id.news_layout);
		_settingLayout = findViewById(R.id.setting_layout);
		_homeLayout.setOnClickListener(this);
		_articalLayout.setOnClickListener(this);
		_settingLayout.setOnClickListener(this);
		_newsLayout.setOnClickListener(this);
	}
	
	private void setTabSelection(int index) {
		ActionBar actionBar = this.getSupportActionBar();
		clearSelection();
		FragmentTransaction transaction = _fragmentManager.beginTransaction();
		hideFragments(transaction);
		
		switch(index) {
		case 0:
			_homeImage.setImageResource(R.drawable.message_selected);
			_homeText.setTextColor(Color.WHITE);
			if(_homePage == null) {
				_homePage = new HomeFragment();
				transaction.add(R.id.main_page_container, _homePage);
			} else {
				transaction.show(_homePage);
			}
			actionBar.setTitle(HomeFragment.TITLE);
			break;
		case 1:
			_articalImage.setImageResource(R.drawable.contacts_selected);
			_articalText.setTextColor(Color.WHITE);
			if(_articalPage == null) {
				_articalPage = new ArticalFragment();
				transaction.add(R.id.main_page_container, _articalPage);
			} else {
				transaction.show(_articalPage);
			}
			actionBar.setTitle(ArticalFragment.TITLE);
			break;
		case 2:
			_newsImage.setImageResource(R.drawable.news_selected);
			_newsText.setTextColor(Color.WHITE);
			if(_newsPage == null) {
				_newsPage = new NewsFragment();
				transaction.add(R.id.main_page_container, _newsPage);
			} else {
				transaction.show(_newsPage);
			}
			actionBar.setTitle(NewsFragment.TITLE);
			break;
		case 3:
		default:
			_settingImage.setImageResource(R.drawable.setting_selected);
			_settingText.setTextColor(Color.WHITE);
			if(_settingPage == null) {
				_settingPage = new SettingFragment();
				transaction.add(R.id.main_page_container, _settingPage);
			} else {
				transaction.show(_settingPage);
			}
			actionBar.setTitle(SettingFragment.TITLE);
			break;
		}
		transaction.commit();	
	}
	
	private void clearSelection() {
		_homeImage.setImageResource(R.drawable.message_unselected);
		_homeText.setTextColor(Color.parseColor("#82858b"));
		_articalImage.setImageResource(R.drawable.contacts_unselected);
		_articalText.setTextColor(Color.parseColor("#82858b"));
		_newsImage.setImageResource(R.drawable.news_unselected);
		_newsText.setTextColor(Color.parseColor("#82858b"));
		_settingImage.setImageResource(R.drawable.setting_unselected);
		_settingText.setTextColor(Color.parseColor("#82858b"));
	}
	
	private void hideFragments(FragmentTransaction transaction) {
		if (_homePage != null) {
			transaction.hide(_homePage);
		}
		if (_articalPage != null) {
			transaction.hide(_articalPage);
		}
		if (_newsPage != null) {
			transaction.hide(_newsPage);
		}
		if (_settingPage != null) {
			transaction.hide(_settingPage);
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.home_layout:
			setTabSelection(0);
			break;
		case R.id.chat_layout:
			setTabSelection(1);
			break;
		case R.id.news_layout:
			setTabSelection(2);
			break;
		case R.id.setting_layout:
			setTabSelection(3);
			break;
		default:
			break;
		}
	}
	
	public DBHelper getDbHelper() {
		return this.dbHelper;
	}

	@Override
	protected void onDestroy() {
		if(dbHelper != null) 
			dbHelper.close();
		super.onDestroy();
	}
}
