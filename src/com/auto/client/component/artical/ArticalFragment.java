package com.auto.client.component.artical;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import com.auto.client.domain.ListItem;
import com.auto.client.entity.reader.ArticalJsonReader;
import com.auto.client.persistence.ArticalDAO;
import com.auto.client.startup.StartupActivity;

public class ArticalFragment extends SherlockFragment implements 
		OnPullDownListener, OnItemClickListener {

	public static final String SERVICE_END_POINT = "/server/service/artical";
	
	public static final String TITLE = "资讯";
	public static final int LIST_DIVIDER_HEIGHT = 3;
	public static final int LIST_PADDING = 0; //35
	public static final int FETCH_COUNT = 20;
	
	private MessageHandler handler = null;
	
	private LinkedList<ListItem> articalList = new LinkedList<ListItem>();
	private PullDownView pullDownView;
	private ListView listView;
	private ArticalAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.component_artical, container, false);
		handler = new MessageHandler(this);
		adapter = new ArticalAdapter(getActivity(), R.layout.control_list_item, articalList);
		pullDownView = (PullDownView)rootView.findViewById(R.id.artival_pull_down_view);
		pullDownView.setOnPullDownListener(this);
		listView = pullDownView.getListView();
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		
		//设置PullDownView及内部ListView样式
		listView.setDivider(null);
		listView.setDividerHeight(LIST_DIVIDER_HEIGHT);
		listView.setPadding(LIST_PADDING, 0, LIST_PADDING, 0);
		listView.setVerticalScrollBarEnabled(false);
		
		pullDownView.enableAutoFetchMore(true, 1);
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(listView.getAdapter().getCount() == 0)
			loadData();
	}
	
	private void loadData() {
		ArticalDAO dao = new ArticalDAO(((StartupActivity)getActivity()).getDbHelper());
		List<Artical> articals = dao.query(0, FETCH_COUNT);
		for(Artical artical : articals) {
			articalList.add(artical);
		}
		adapter.notifyDataSetChanged();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ArticalJsonReader reader = new ArticalJsonReader();
					List<Object> articalList = reader.get(getResources().getString(R.string.server)
							+ SERVICE_END_POINT
							+ "/1/1");
					Message msg = handler.obtainMessage(PullDownView.WHAT_DID_LOAD_DATA);
					msg.obj = articalList;
					msg.sendToTarget();
				} catch(Exception e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage(PullDownView.WHAT_DID_LOAD_FAIL);
					msg.sendToTarget();
				}
			}
		}).start();
	}
	
	@Override
	public void onDestroy() {
		if(handler != null)
			handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					ArticalJsonReader reader = new ArticalJsonReader();
					List<Object> articalList = reader.get(
							getResources().getString(R.string.server) 
							+  SERVICE_END_POINT
							+ "/1/1");
					Message msg = handler.obtainMessage(PullDownView.WHAT_DID_REFRESH);
					msg.obj = articalList;
					msg.sendToTarget();
				} catch(Exception e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage(PullDownView.WHAT_DID_LOAD_FAIL);
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
					int fromIndex = articalList.size();
					ArticalDAO dao = new ArticalDAO(((StartupActivity)getActivity()).getDbHelper());
					List<Artical> articals = dao.query(fromIndex, FETCH_COUNT);
					Message msg = handler.obtainMessage(PullDownView.WHAT_DID_MORE);
					msg.obj = articals;
					msg.sendToTarget();
				} catch(Exception e) {
					e.printStackTrace();
					Message msg = handler.obtainMessage(PullDownView.WHAT_DID_MORE_FAIL);
					msg.sendToTarget();
				}
			}
		}).start();
	}
	
	static class MessageHandler extends Handler {
		private WeakReference<ArticalFragment> fragmentRef;
		
		public MessageHandler(ArticalFragment fragment) {
			this.fragmentRef = new WeakReference<ArticalFragment>(fragment);
		}
		
		private void populate(List<Object> list, ArticalFragment fragment, boolean fromDB) {
			if(!list.isEmpty()) {
				List<Artical> persistList = new ArrayList<Artical>();
				for(Object o : list) {
					Artical artical = (Artical)o;
					if(fromDB == false)
						fragment.articalList.addFirst(artical);
					else fragment.articalList.add(artical);
					persistList.add(artical);
				}
				fragment.adapter.notifyDataSetChanged();
				
				//写入本地数据库
				if(fromDB == false) {
					ArticalDAO dao = new ArticalDAO(((StartupActivity)fragmentRef.get().getActivity()).getDbHelper());
					for(Artical a : persistList) 
						dao.add(a);
				}
			}
		}
		
		public void handleMessage(Message msg) {
			ArticalFragment fragment = fragmentRef.get();
			if(fragment == null) return;
			
			switch(msg.what) {
			case PullDownView.WHAT_DID_LOAD_DATA:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					this.populate(list, fragment, false);
					list.clear();
					list = null;
				}
				fragment.pullDownView.notifyDidLoad(); //告诉数据加载完毕
				break;
				
			case PullDownView.WHAT_DID_MORE:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					this.populate(list, fragment, true);
					list.clear();
					list = null;
				}
				//告诉获取更多完毕
				fragment.pullDownView.notifyDidMore();
				break;
				
			case PullDownView.WHAT_DID_REFRESH:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					this.populate(list, fragment, false);
					list.clear();
					list = null;
				}
				//告诉获取更多完毕
				fragment.pullDownView.notifyDidRefresh();
				break;
				
			case PullDownView.WHAT_DID_LOAD_FAIL:
				fragment.pullDownView.notifyLoadFailure();
				break;
				
			case PullDownView.WHAT_DID_MORE_FAIL:
				fragment.pullDownView.notifyMoreFailure();
				break;
			}
		}
	}
}
