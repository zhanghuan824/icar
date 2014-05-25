package com.auto.client.module.maintenance;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;
import com.auto.client.common.control.pulldownview.PullDownView;
import com.auto.client.common.control.pulldownview.PullDownView.OnPullDownListener;
import com.auto.client.domain.maintain.Station;
import com.auto.client.entity.reader.StationJsonReader;
import com.auto.client.persistence.StationDAO;

public class StationSherlockFragment extends SherlockFragment implements
		OnPullDownListener, OnItemClickListener {
	
	public static final String SERVICE_END_POINT = "/server/service/home/maintain/station";
	
	private MessageHandler _handler;
	private StationListAdapter _adapter;
	private LinkedList<Station> _dataList;
	
	private PullDownView _pullDownView;
	private ListView _listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.pulldown,
				container, false);	
		_handler = new MessageHandler(this);
		_dataList = new LinkedList<Station>();
		_adapter = new StationListAdapter(this, _dataList);
		_pullDownView = (PullDownView) rootView.findViewById(R.id.pull_down_view);
		_pullDownView.setOnPullDownListener(this);
		_listView = _pullDownView.getListView();		
		_listView.setOnItemClickListener(this);
		_adapter.setData(_dataList);
		_listView.setAdapter(_adapter);		
		_pullDownView.enableAutoFetchMore(true, 1);		
		loadData();		
		return rootView;
	}
	
	@Override
	public void onDestroy() {
		if(_handler != null)
			_handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
	
	private void loadData() {
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					StationJsonReader reader = new StationJsonReader();
					List<Object> stationList = reader.get(
						getResources().getString(R.string.server)
						+ SERVICE_END_POINT 
						+ "/湖南/长沙");
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_LOAD_DATA);
					msg.obj = stationList;
					msg.sendToTarget();
				} catch(Exception e) {
					e.printStackTrace();
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_LOAD_FAIL);
					msg.sendToTarget();
				}
				
			}
		}).start();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(getActivity(), MaintenanceCategoryActivity.class);
		intent.putExtra("station_name", _dataList.get(position).getName());
		getActivity().startActivity(intent);
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {				
				try {
					StationJsonReader reader = new StationJsonReader();
					List<Object> stationList = reader.get(
							getResources().getString(R.string.server) 
							+ SERVICE_END_POINT 
							+ "/湖南/长沙");
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_REFRESH);
					msg.obj = stationList;
					msg.sendToTarget();
				} catch(Exception e) {
					e.printStackTrace();
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_LOAD_FAIL);
					msg.sendToTarget();
				}
			}
		}).start();
	}

	@Override
	public void onMore() {
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					StationJsonReader reader = new StationJsonReader();
					List<Object> stationList = reader.get(
						getResources().getString(R.string.server)
						+ SERVICE_END_POINT 
						+ "/湖南/长沙");
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_MORE);
					msg.obj = stationList;
					msg.sendToTarget();
				} catch(Exception e) {
					e.printStackTrace();
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_MORE_FAIL);
					msg.sendToTarget();
				}
			}
		}).start();
	}

	static class MessageHandler extends Handler {
		private WeakReference<StationSherlockFragment> fragmentRef;
		
		public MessageHandler(StationSherlockFragment fragment) {
			fragmentRef = new WeakReference<StationSherlockFragment>(fragment);
		}
		
		private void populate(List<Object> list, StationSherlockFragment fragment, boolean fromDB) {
			if(!list.isEmpty()) {
				List<Station> stationList = new ArrayList<Station>();
				for(Object o : list) {
					Station station = (Station)o;
					if(fromDB == false)
						fragment._dataList.addFirst(station);
					else
						fragment._dataList.add(station);
					stationList.add(station);
				}
				fragment._adapter.notifyDataSetChanged();
				
				if(fromDB == false) {
					StationDAO dao = new StationDAO(((MaintainActivity)fragment.getSherlockActivity()).getDbHelper());
					dao.add(stationList);
				}
			}
		}
		
		@Override
		public void handleMessage(Message msg) {
			StationSherlockFragment fragmentObj = fragmentRef.get();
			if(fragmentObj == null) return;
			
			switch(msg.what) {
			case PullDownView.WHAT_DID_LOAD_DATA:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					populate(list, fragmentObj, false);
				}
				//告诉数据加载完毕
				fragmentObj._pullDownView.notifyDidLoad();
				break;
				
			case PullDownView.WHAT_DID_MORE:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					populate(list, fragmentObj, true);
				}
				//告诉获取更多完毕
				fragmentObj._pullDownView.notifyDidMore();
				break;
				
			case PullDownView.WHAT_DID_REFRESH:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					populate(list, fragmentObj, false);
				}
				//告诉获取更多完毕
				fragmentObj._pullDownView.notifyDidRefresh();
				break;
				
			case PullDownView.WHAT_DID_LOAD_FAIL:
				fragmentObj._pullDownView.notifyLoadFailure();
				break;
				
			case PullDownView.WHAT_DID_MORE_FAIL:
				fragmentObj._pullDownView.notifyMoreFailure();
				break;
			}
		}
	}
}
