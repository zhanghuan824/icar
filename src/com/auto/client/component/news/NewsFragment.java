package com.auto.client.component.news;

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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;
import com.auto.client.common.control.post.Post;
import com.auto.client.common.control.post.PostAdapter;
import com.auto.client.common.control.post.PostLink;
import com.auto.client.common.control.pulldownview.PullDownView;
import com.auto.client.common.control.pulldownview.PullDownView.OnPullDownListener;
import com.auto.client.domain.dynamic.News;
import com.auto.client.domain.dynamic.NewsSubItem;
import com.auto.client.entity.reader.DynamicNewsJsonReader;
import com.auto.client.persistence.NewsDAO;
import com.auto.client.startup.StartupActivity;

public class NewsFragment extends SherlockFragment implements 
		OnPullDownListener, OnItemClickListener {

	public static final String TITLE = "动态";
	public static final int LIST_DIVIDER_HEIGHT = 35;
	public static final int LIST_PADDING = 35;
	public static final int FETCH_COUNT = 10; //一次获取的条目数
	
	private MessageHandler _handler;
	private PostAdapter _adapter;
	private LinkedList<Post> _postList = new LinkedList<Post>();
	
	private PullDownView _pullDownView;
	private ListView _listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.component_news,
				container, false);	
		_handler = new MessageHandler(this);
		_adapter = new PostAdapter(getActivity(), R.layout.control_post, _postList);
		_pullDownView = (PullDownView)rootView.findViewById(R.id.news_pull_down_view);
		_pullDownView.setOnPullDownListener(this);
		_listView = _pullDownView.getListView();		
		_listView.setOnItemClickListener(this);
		_listView.setAdapter(_adapter);
		
		//设置PullDownView及内部ListView样式
		_listView.setDivider(null);
		_listView.setDividerHeight(LIST_DIVIDER_HEIGHT);
		_listView.setPadding(LIST_PADDING, 0, LIST_PADDING, 0);
		_listView.setVerticalScrollBarEnabled(false);
		
		_pullDownView.enableAutoFetchMore(true, 1);
		//loadData();
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(_listView.getAdapter().getCount() == 0)
			loadData();
	}
	
	@Override
	public void onDestroy() {
		if(_handler != null)
			_handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
	
	private void loadData() {
		//先显示最近的十条
		NewsDAO dao = new NewsDAO(((StartupActivity)getActivity()).getDbHelper());
		List<News> newsList = dao.query(0, 10);
		for(News n : newsList) {
			_postList.add(fillNewsToPost(n));
		}
		_adapter.notifyDataSetChanged();
		
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					DynamicNewsJsonReader reader = new DynamicNewsJsonReader();
					List<Object> newsList = reader.get(
						getResources().getString(R.string.server)
						+ "/server/service/dynamic/1/1/1/1");
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_LOAD_DATA);
					msg.obj = newsList;
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
		Toast.makeText(this.getActivity(), "啊，你点了 " + position, Toast.LENGTH_SHORT).show();
		//Intent intent = new Intent(getActivity(), MaintenanceCategoryActivity.class);
		//getActivity().startActivity(intent);
	}
	
	@Override
	public void onRefresh() {
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					DynamicNewsJsonReader reader = new DynamicNewsJsonReader();
					List<Object> newsList = reader.get(
							getResources().getString(R.string.server) 
							+ "/server/service/dynamic/1/1/1/1");
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_REFRESH);
					msg.obj = newsList;
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
					int fromIndex = _postList.size();
					NewsDAO dao = new NewsDAO(((StartupActivity)getActivity()).getDbHelper());
					List<News> newsList = dao.query(fromIndex, FETCH_COUNT);
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_MORE);
					msg.obj = newsList;
					msg.sendToTarget();
				} catch(Exception e) {
					e.printStackTrace();
					Message msg = _handler.obtainMessage(PullDownView.WHAT_DID_MORE_FAIL);
					msg.sendToTarget();
				}
			}
		}).start();
	}
	
	static Post fillNewsToPost(News news) {
		List<NewsSubItem> nsiList = news.getSubItems();
		Post p = new Post();
		p.setTitle(news.getTitle());
		p.setImage(news.getImage());
		p.setPostTime(news.getUpdateTime());
		p.setShortDescription(news.getDesc());
		for(NewsSubItem nsi : nsiList) {
			PostLink pl = new PostLink(nsi.getTitle(), nsi.getImage(), nsi.getUrl());
			p.getPostLinkList().add(pl);
		}
		return p;
	}
	
	static class MessageHandler extends Handler {
		private WeakReference<NewsFragment> _fragment;
		
		public MessageHandler(NewsFragment newsFragment) {
			this._fragment = new WeakReference<NewsFragment>(newsFragment);
		}
		
		/**
		 * 将数据更新到界面
		 * @param list
		 * @param fragment
		 * @param fromDB 该数据是否来自数据库
		 */
		private void populate(List<Object> list, NewsFragment fragment, boolean fromDB) {
			if(!list.isEmpty()) {
				List<News> persistList = new ArrayList<News>();
				for(Object o : list) {
					News news = (News)o;
					Post post = fillNewsToPost(news);
					if(fromDB == false)
						fragment._postList.addFirst(post);
					else fragment._postList.add(post);
					persistList.add(news);
				}
				fragment._adapter.notifyDataSetChanged();
				
				//写入本地数据库
				if(fromDB == false) {
					NewsDAO newsDao = new NewsDAO(((StartupActivity)_fragment.get().getActivity()).getDbHelper());
					newsDao.add(persistList);
				}
			}
		}
		
		@Override
		public void handleMessage(Message msg) {
			NewsFragment fragmentObj = _fragment.get();
			if(fragmentObj == null) return;
			
			switch(msg.what) {
			case PullDownView.WHAT_DID_LOAD_DATA:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					this.populate(list, fragmentObj, false);
					list.clear();
					list = null;
				}
				//告诉数据加载完毕
				fragmentObj._pullDownView.notifyDidLoad();
				break;
				
			case PullDownView.WHAT_DID_MORE:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					this.populate(list, fragmentObj, true);
					list.clear();
					list = null;
				}
				//告诉获取更多完毕
				fragmentObj._pullDownView.notifyDidMore();
				break;
				
			case PullDownView.WHAT_DID_REFRESH:
				if(msg.obj != null) {
					@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>) msg.obj;
					this.populate(list, fragmentObj, false);
					list.clear();
					list = null;
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
