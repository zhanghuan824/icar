package com.auto.client.module.maintenance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.auto.client.R;
import com.auto.client.R.drawable;
import com.auto.client.common.InternetImageLoader;
import com.auto.client.domain.maintain.ServiceComment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MaintenanceCategoryActivity extends SherlockFragmentActivity {

	private String[] tabNames = { "详细\n介绍", "服务\n项目", "统计\n信息", "车友\n点评" };
	
	private static final String DETAIL_INFORMATION_TAG = "tab_detail_information";
	private static final String SERVICE_LIST_TAG = "tab_service_list";
	private static final String STATISTICS_TAG = "tab_statistics";
	private static final String SERVICE_COMMENT_TAG = "tab_service_comment";
	
	private ExpandableListView _expandListView;
	private MaintenanceCategoryAdapter _adapter;
	private Map<String, ArrayList<String>> _categoryMap;
	private LinearLayout parentLayout;
	
	public MaintenanceCategoryActivity() {
		_categoryMap = new HashMap<String, ArrayList<String>>();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String name1 = this.getIntent().getExtras().get("station_name").toString();
		this.setTitle(name1);
		
		setContentView(R.layout.activity_maintenance_category);
		final TabHost tabHost = (TabHost) findViewById(R.id.station_tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec(DETAIL_INFORMATION_TAG).setIndicator(tabNames[0]).setContent(R.id.station_detail_information));
		tabHost.addTab(tabHost.newTabSpec(SERVICE_LIST_TAG).setIndicator(tabNames[1]).setContent(R.id.station_services_list));
		tabHost.addTab(tabHost.newTabSpec(STATISTICS_TAG).setIndicator(tabNames[2]).setContent(R.id.station_statistics));
		tabHost.addTab(tabHost.newTabSpec(SERVICE_COMMENT_TAG).setIndicator(tabNames[3]).setContent(R.id.station_service_comments));
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals(DETAIL_INFORMATION_TAG)) {
					loadDetailInformation();
				} else if(tabId.equals(SERVICE_LIST_TAG)) {
					//loadServiceList();
				} else if(tabId.equals(STATISTICS_TAG)) {
					loadStatistics();
				} else {
					loadComment();
				}
			}
			
		});
		
		// 指向第一个Tab，并加载内容
		tabHost.setCurrentTab(0);
		loadDetailInformation();
		
		initData();
		
		loadServiceList();
		
		/*
		this.initData();
		
		_adapter = new MaintenanceCategoryAdapter(this, this._categoryMap);
		_expandListView = (ExpandableListView) findViewById(R.id.maintenance_category_list);
		_expandListView.setAdapter(_adapter);
		_expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				String itemName = _adapter.getChild(groupPosition, childPosition).toString();
				Bundle data = new Bundle();
				data.putString("item_name", itemName);
				data.putString("maintener_id", "0010213");
				//Intent intent = new Intent(MaintenanceCategoryActivity.this, MaintenanceItemActivity.class);
				///intent.putExtras(data);
				Intent intent = new Intent(MaintenanceCategoryActivity.this, com.auto.client.startup.StartupMetroActivity.class);
				startActivity(intent);
				return true;
			}
		});
		
		parentLayout = (LinearLayout) findViewById(R.id.station_services_list);
		TextView tv = new TextView(this);
		*/
	}

	private void initData() {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("更换刹车油");
		list1.add("更换汽车尾灯");
		list1.add("更换机油");
		_categoryMap.put("外围设备维修", list1);
		
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("更换刹车油");
		list2.add("更换汽车尾灯");
		list2.add("更换机油");
		_categoryMap.put("发动机", list2);
		
		ArrayList<String> list3 = new ArrayList<String>();
		list3.add("更换刹车油");
		list3.add("更换汽车尾灯");
		list3.add("更换机油");
		_categoryMap.put("空调系统", list3);
	}

	protected void loadDetailInformation() {
		TextView tv_station_detail_information = (TextView) findViewById(R.id.tv_station_detail_information);
		String stationDetailInfo = "链家地产市场研究部张旭认为，去年四季度以来复杂的市场形势导致市场持续冷淡，尤其是刚需陷入浓重的观望情绪致使近郊区二手住宅成交占比有所下降。而近期学区房成交旺季的来临在促进中心城区成交占比有所上升的同时也在一定程度上拉高了成交结构。全市成交结构的整体提升是不少区域价格有所下降但全市成交均价基本维持稳定的主要原因。但如果从在售房源的挂牌价调整情况和已售房源的议价空间走势情况来看，今年以来二手住宅价格松动的迹象已较为明显，且呈逐渐扩大的趋势，价格下降的趋势已基本确立。";
		tv_station_detail_information.setText(stationDetailInfo);
		
		ImageView stationImage = (ImageView) findViewById(R.id.station_image_set);
		InternetImageLoader imageLoader = new InternetImageLoader(this);
		imageLoader.DisplayImage("http://pic25.nipic.com/20121126/6832356_091502999000_2.jpg", stationImage);
	}
	
	protected void loadServiceList() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.station_services_list);
		
		RelativeLayout opLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		opLayout.setLayoutParams(rlp);
		
		Iterator<String> iterator = _categoryMap.keySet().iterator();
		while(iterator.hasNext()) {
			String categoryTitle = iterator.next();
			TextView tv = new TextView(this);
			tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			tv.setText(categoryTitle);
			tv.setTextSize(14);
			tv.getPaint().setFakeBoldText(true);
			
			GridView gv = new GridView(this);
			gv.setBackgroundColor(Color.GRAY);
			gv.setNumColumns(3);
			gv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			gv.setAdapter(new GridViewServiceDataAdapter(this, _categoryMap.get(categoryTitle)));
			
			layout.addView(tv);
			layout.addView(gv);
		}
		
		
		/*
		TextView tv = new TextView(this);
		tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		tv.setText("hahahhahahahaha");
		layout.addView(tv);*/
		
	}
	
	protected void loadStatistics() {
		
	}
	
	protected void loadComment() {
		ListView lvComments = (ListView) findViewById(R.id.station_service_comments_list);
		LinkedList<ServiceComment> scList = new LinkedList<ServiceComment>();
		ServiceComment sc = new ServiceComment();
		sc.setComment("服务态度是真心不错的，条件和设施也很好，价格公道，比较便宜和实惠，并且附送很多小礼品，下次一定还来");
		sc.setCommentTime(new Date());
		sc.setRating(4.0f);
		sc.setServiceConsumed("修保险箱，发动机清理");
		sc.setUserImage("http://pic25.nipic.com/20121126/6832356_091502999000_2.jpg");
		sc.setUserName("我的APP一定大卖");
		scList.add(sc);
		scList.add(sc);
		ServiceCommentAdapter adapter = new ServiceCommentAdapter(this, scList);
		lvComments.setAdapter(adapter);
	}
	
	class GridViewServiceDataAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<String> serviceList;
		
		public GridViewServiceDataAdapter(Context context, ArrayList<String> data) {
			this.context = context;
			this.serviceList = data;
		}
		
		@Override
		public int getCount() {
			return serviceList.size();
		}

		@Override
		public Object getItem(int position) {
			return serviceList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(convertView == null) {
				RelativeLayout relativeLayout = new RelativeLayout(context);
				relativeLayout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				relativeLayout.setPadding(10, 10, 10, 10);
				
				TextView tv = new TextView(context);
				tv.setTextSize(13);
				tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				tv.setText(serviceList.get(position));
				RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
				tvParams.addRule(RelativeLayout.CENTER_IN_PARENT);
				//tv.setLayoutParams(tvParams);
				
				relativeLayout.addView(tv, tvParams);
				convertView = relativeLayout;
			}
			
			return convertView;
		}
		
	}
	
	
	class MaintenanceCategoryAdapter extends BaseExpandableListAdapter {

		private Activity _context;
		private Map<String, ArrayList<String>> _categoryMap;
		
		public MaintenanceCategoryAdapter(Activity context, Map<String, ArrayList<String>> categoryMap) {
			this._context = context;
			this._categoryMap = categoryMap;
		}
		
		public Activity getContext() {
			return _context;
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			Object[] keys = this._categoryMap.keySet().toArray();
			if(groupPosition >= 0 && groupPosition < keys.length && keys[groupPosition] != null) {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>)this._categoryMap.get(keys[groupPosition]);
				if(childPosition >= 0 && childPosition < list.size()) 
					return list.get(childPosition);
			}
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(final int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			LayoutInflater inflater = _context.getLayoutInflater();
			if(convertView == null)
				convertView = inflater.inflate(R.layout.list_item_maintenance_category, null);
			TextView item = (TextView) convertView.findViewById(R.id.maintenance_category_sub_item);
			item.setText(String.valueOf(getChild(groupPosition, childPosition)));
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			Object[] keys = this._categoryMap.keySet().toArray();
			if(groupPosition >= 0 && groupPosition < keys.length && keys[groupPosition] != null) {
				List<String> list = this._categoryMap.get(keys[groupPosition]);
				return list.size();
			}
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			Object[] keys = this._categoryMap.keySet().toArray();
			if(groupPosition >= 0 && groupPosition < keys.length && keys[groupPosition] != null) {
				return keys[groupPosition];
			}
			return "";
		}

		@Override
		public int getGroupCount() {
			return this._categoryMap.keySet().size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if(convertView == null) {
				LayoutInflater inflater = this._context.getLayoutInflater();
				convertView = inflater.inflate(R.layout.list_item_group_maintenance_category, null);
			}
			
			TextView group = (TextView) convertView.findViewById(R.id.maintenance_category_list_group);
			group.setTypeface(null, Typeface.BOLD);
			group.setText(String.valueOf(getGroup(groupPosition)));
			
			ImageView image = (ImageView) convertView.findViewById(R.id.img_collapse);
			
			if(isExpanded == false) {
				image.setBackgroundResource(drawable.ic_action_expand);
				image.setTag(drawable.ic_action_expand);
			} else {
				image.setBackgroundResource(drawable.ic_action_collapse);
				image.setTag(drawable.ic_action_collapse);
			}
			
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEmpty() {
			return this._categoryMap.size() > 0 ? false : true;
		}
	}
}
