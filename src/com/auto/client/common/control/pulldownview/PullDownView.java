package com.auto.client.common.control.pulldownview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.auto.client.R;
import com.auto.client.common.control.pulldownview.ScrollOverListView.OnScrollOverListener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PullDownView extends LinearLayout implements OnScrollOverListener {

	public static final String TAG = "PullDownView";
	
	public static final int START_PULL_DEVIATION = 50; 	//移动误差
	public static final int AUTO_INCREMENTAL = 10;		//自增量，用于回弹
	
	public static final int WHAT_DID_LOAD_DATA = 1;		//Handler what 数据加载完毕
	public static final int WHAT_ON_REFRESH = 2;		//Handler what 刷新中
	public static final int WHAT_DID_REFRESH = 3;		//Handler what 已经刷新完成
	public static final int WHAT_SET_HEADER_HEIGHT = 4; //Handler what 设置高度
	public static final int WHAT_DID_MORE = 5;			//Handler what 已经获取完更多
	public static final int WHAT_DID_LOAD_FAIL = 6;		//刷新失败，网络连接错误或超时
	public static final int WHAT_DID_MORE_FAIL = 7;		//加载更多失败，网络连接错误或者超时
	
	public static final int DEFAULT_HEADER_VIEW_HEIGHT = 105; //头部文件原来的高度
	private static SimpleDateFormat _dateFormat = new SimpleDateFormat("MM-dd HH:mm");
	
	private View _headerView;
	private LayoutParams _headerViewParams;
	private TextView _headerViewDateView;
	private TextView _headerTextView;
	private ImageView _headerArrowView;
	private View _headerLoadingView;
	private View _footerView;
	private TextView _footerTextView;
	private View _footerLoadingView;
	private ScrollOverListView _listView;
	
	private OnPullDownListener _onPullDownListener;
	private RotateAnimation _rotate0To180Animation;
	private RotateAnimation _rotate180To0Animation;
	
	private int _headerIncremental; 			//增量
	private float _motionDownLastY; 			//按下时候的Y轴坐标
	
	private boolean _isDown;					//是否按下
	private boolean _isRefreshing;			//是否下拉刷新中
	private boolean _isFetchMoreing;			//是否获取更多中
	private boolean _isPullUpDone;			//是否回推完成
	private boolean _enableAutoFetchMore;	//是否允许自动获取更多
	
	//头部文件的状态
	private static final int HEADER_VIEW_STATE_IDLE = 0;			//空闲
	private static final int HEADER_VIEW_STATE_NOT_OVER_HEIGHT = 1; //没有超过默认高度
	private static final int HEADER_VIEW_STATE_OVER_HEIGHT = 2;		//超过默认高度
	private int headerViewState = HEADER_VIEW_STATE_IDLE;
	
	public PullDownView(Context context) {
		super(context);
		initHeaderViewAndFooterViewAndListView(context);
	}
	
	public PullDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderViewAndFooterViewAndListView(context);
	}

	/**
	 * 刷新事件接口
	 * @author zhanghuan
	 *
	 */
	public interface OnPullDownListener {
		void onRefresh();
		void onMore();
	}
	
	/**
	 * 通知加载完了数据，要放在Adapter.notifyDataSetChanged后面
	 * 当你加载完数据的时候，调用这个notifyDidLoad()
	 * 才会隐藏头部，并初始化数据等
	 */
	public void notifyDidLoad() {
		uiHandler.sendEmptyMessage(WHAT_DID_LOAD_DATA);
	}
	
	/**
	 * 通知已经刷新完了，要放在Adapter.notifyDataSetChanged后面
	 * 当你执行完刷新之后，调用这个notifyDidRefresh()
	 * 才会隐藏掉头部文件等操作
	 */
	public void notifyDidRefresh() {
		uiHandler.sendEmptyMessage(WHAT_DID_REFRESH);
	}
	
	/**
	 * 通知已经获取完更多了，要放在Adapter.notifyDataSetChanged后面
	 * 当你执行完更多任务之后，调用这个notifyDidMore()，才会显示隐藏加载圈等操作
	 */
	public void notifyDidMore() {
		uiHandler.sendEmptyMessage(WHAT_DID_MORE);
	}
	
	public void notifyLoadFailure() {
		uiHandler.sendEmptyMessage(WHAT_DID_LOAD_FAIL);
	}
	
	public void notifyMoreFailure() {
		uiHandler.sendEmptyMessage(WHAT_DID_MORE_FAIL);
	}
	
	/**
	 * 设置监听器
	 * @param listener
	 */
	public void setOnPullDownListener(OnPullDownListener listener) {
		_onPullDownListener = listener;
	}
	
	/**
	 * 获取内嵌的listView
	 * @return
	 */
	public ListView getListView() {
		return _listView;
	}
	
	/**
	 * 是否开启自动获取更多
	 * 自动获取更多将会隐藏footer，并在到达底部的时候自动刷新
	 * @param enable
	 * @param index 倒数第几个触发
	 */
	public void enableAutoFetchMore(boolean enable, int index) {
		if(enable) {
			_listView.setBottomPosition(index);
			_footerLoadingView.setVisibility(View.VISIBLE);
		} else {
			_footerTextView.setText("更多");
			_footerLoadingView.setVisibility(View.GONE);
		}
		_enableAutoFetchMore = enable;
	}
	
	//=========== 具体实现下拉刷新操作 ================
	
	/**
	 * 初始化界面
	 * @param context
	 */
	private void initHeaderViewAndFooterViewAndListView(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		//自定义头部文件，放在这里是因为考虑到很多界面都需要使用
		//如果要修改，和它相关的设置都需要修改
		_headerView = LayoutInflater.from(context).inflate(R.layout.pulldown_header, null);
		_headerViewParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		_headerViewParams.height = 70;
		addView(_headerView, 0, _headerViewParams);
		
		_headerTextView = (TextView) _headerView.findViewById(R.id.pulldown_header_text);
		_headerArrowView = (ImageView) _headerView.findViewById(R.id.pulldown_header_arrow);
		_headerLoadingView = _headerView.findViewById(R.id.pulldown_header_loading);
		
		//注意图片旋转以后，再执行旋转，坐标会重新开始计算
		_rotate0To180Animation = new RotateAnimation(0, 180,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		_rotate0To180Animation.setDuration(250);
		_rotate0To180Animation.setFillAfter(true);
		
		_rotate180To0Animation = new RotateAnimation(180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		_rotate180To0Animation.setDuration(250);
		_rotate180To0Animation.setFillAfter(true);
		
		//自定义脚部文件
		_footerView = LayoutInflater.from(context).inflate(R.layout.pulldown_footer, null);
		_footerTextView = (TextView) _footerView.findViewById(R.id.pulldown_footer_text);
		_footerLoadingView = _footerView.findViewById(R.id.pulldown_footer_loading);
		_footerLoadingView.setVisibility(View.GONE);
		_footerView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!_isFetchMoreing) {
					_isFetchMoreing = true;
					_footerLoadingView.setVisibility(View.VISIBLE);
					_onPullDownListener.onMore();
				}
			}
		});
		
		//ScrollOverListView同样是考虑到都是使用，所以放在这里
		//同时因为需要它的监听事件
		_listView = new ScrollOverListView(context);
		_listView.setOnScrollOverListener(this);
		_listView.setCacheColorHint(0);
		addView(_listView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		//空的listener
		_onPullDownListener = new OnPullDownListener() {
			@Override
			public void onRefresh() {}
			@Override
			public void onMore() {}
		};
	}
	
	/**
	 * 在下拉和回推的时候检查头部文件的状态
	 * 如果超过了默认高度，就显示松开可以刷新
	 * 否则显示下拉可以刷新
	 */
	private void checkHeaderViewState() {
		if(_headerViewParams.height >= DEFAULT_HEADER_VIEW_HEIGHT) {
			if(headerViewState == HEADER_VIEW_STATE_OVER_HEIGHT) return;
			headerViewState = HEADER_VIEW_STATE_OVER_HEIGHT;
			_headerTextView.setText("松开可以刷新");
			_headerArrowView.startAnimation(_rotate0To180Animation);
		} else {
			if(headerViewState == HEADER_VIEW_STATE_NOT_OVER_HEIGHT 
					|| headerViewState == HEADER_VIEW_STATE_IDLE) 
				return;
			headerViewState = HEADER_VIEW_STATE_NOT_OVER_HEIGHT;
			_headerTextView.setText("下拉可以刷新");
			_headerArrowView.startAnimation(_rotate180To0Animation);
		}
	}
	
	private void setHeaderHeight(final int height) {
		_headerIncremental = height;
		_headerViewParams.height = height;
		_headerView.setLayoutParams(_headerViewParams);
	}
	
	/**
	 * 自动隐藏动画
	 * @author zhanghuan
	 *
	 */
	class HideHeaderViewTask extends TimerTask {

		@Override
		public void run() {
			if(_isDown) {
				cancel();
				return;
			}
			_headerIncremental -= AUTO_INCREMENTAL;
			if(_headerIncremental > 0) 
				uiHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
			else {
				_headerIncremental = 0;
				uiHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
				cancel();
			}	
		}
	}
	
	/**
	 * 自动显示动画
	 * @author zhanghuan
	 *
	 */
	class ShowHeaderViewTask extends TimerTask {

		@Override
		public void run() {
			if(_isDown) {
				cancel();
				return;
			}
			_headerIncremental -= AUTO_INCREMENTAL;
			if(_headerIncremental > DEFAULT_HEADER_VIEW_HEIGHT) {
				uiHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
			} else {
				_headerIncremental = DEFAULT_HEADER_VIEW_HEIGHT;
				uiHandler.sendEmptyMessage(WHAT_SET_HEADER_HEIGHT);
				if(!_isRefreshing) {
					_isRefreshing = true;
					uiHandler.sendEmptyMessage(WHAT_ON_REFRESH);
				}
				cancel();
			}
		}
	}
	
	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case WHAT_DID_LOAD_DATA:
				_headerViewParams.height = 0;
				_headerLoadingView.setVisibility(View.GONE);
				_headerTextView.setText("下拉可以刷新");
				_headerViewDateView = (TextView) _headerView.findViewById(R.id.pulldown_header_date);
				_headerViewDateView.setVisibility(View.VISIBLE);
				_headerViewDateView.setText("更新于" + _dateFormat.format(new Date(System.currentTimeMillis())));
				_headerArrowView.setVisibility(View.VISIBLE);
				showFooterView();
				break;
				
			case WHAT_ON_REFRESH:
				//要清除掉动画，否则无法隐藏
				_headerArrowView.clearAnimation();
				_headerArrowView.setVisibility(View.INVISIBLE);
				_headerLoadingView.setVisibility(View.VISIBLE);
				_onPullDownListener.onRefresh();
				break;
				
			case WHAT_DID_REFRESH:
				_isRefreshing = false;
				headerViewState = HEADER_VIEW_STATE_IDLE;
				_headerArrowView.setVisibility(View.VISIBLE);
				_headerLoadingView.setVisibility(View.GONE);
				_headerViewDateView.setText("更新于" + _dateFormat.format(new Date(System.currentTimeMillis())));
				setHeaderHeight(0);
				showFooterView();
				break;
				
			case WHAT_SET_HEADER_HEIGHT:
				setHeaderHeight(_headerIncremental);
				break;
				
			case WHAT_DID_MORE:
				_isFetchMoreing = false;
				_footerTextView.setText("更多");
				_footerLoadingView.setVisibility(View.GONE);
				break;
			
			case WHAT_DID_LOAD_FAIL:
				_isRefreshing = false;
				_headerViewParams.height = 0;
				_headerLoadingView.setVisibility(View.GONE);
				_headerTextView.setText("下拉可以刷新");
				_headerViewDateView = (TextView) _headerView.findViewById(R.id.pulldown_header_date);
				_headerViewDateView.setVisibility(View.VISIBLE);
				_headerViewDateView.setText("更新于" + _dateFormat.format(new Date(System.currentTimeMillis())));
				_headerArrowView.setVisibility(View.VISIBLE);
				showFooterView();
				Toast.makeText(getContext(), "加载失败，请检查网络设置...", Toast.LENGTH_LONG).show();
				break;
				
			case WHAT_DID_MORE_FAIL:
				_isFetchMoreing = false;
				_footerTextView.setText("更多");
				_footerLoadingView.setVisibility(View.GONE);
				Toast.makeText(getContext(), "加载失败，请检查网络设置...", Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
	
	/**
	 * 显示底部文件
	 */
	private void showFooterView() {
		if(_listView.getFooterViewsCount() == 0 && isFillScreenItem()) {
			_listView.addFooterView(_footerView);
			_listView.setAdapter(_listView.getAdapter());
		}
	}
	
	/**
	 * 条目是否填满整个屏幕
	 * @return
	 */
	private boolean isFillScreenItem() {
		final int firstVisiblePosition = _listView.getFirstVisiblePosition();
		final int lastVisiblePosition = _listView.getLastVisiblePosition() - _listView.getFooterViewsCount();
		final int visibleItemCount = lastVisiblePosition - firstVisiblePosition + 1;
		final int totalItemCount = _listView.getCount() - _listView.getFooterViewsCount();
		
		if(visibleItemCount < totalItemCount) 
			return true;
		return false;
	}
	
	
	//========= 实现OnScrollOverListener接口 =======
	//============================================
	@Override
	public boolean onListViewTopAndPullDown(int delta) {
		if(_isRefreshing || _listView.getCount() - _listView.getFooterViewsCount() == 0)
			return false;
		int absDelta = Math.abs(delta);
		final int i = (int)Math.ceil((double)absDelta / 2);
		
		_headerIncremental += i;
		if(_headerIncremental >= 0) {
			setHeaderHeight(_headerIncremental);
			checkHeaderViewState();
		}
		return true;
	}

	@Override
	public boolean onListViewBottomAndPullUp(int delta) {
		if(!_enableAutoFetchMore || _isFetchMoreing) return false;
		//数量充满屏幕才触发
		if(_enableAutoFetchMore && isFillScreenItem()) {
			_isFetchMoreing = true;
			_footerTextView.setText("加载更多...");
			//_footerLoadingView.setVisibility(View.VISIBLE);
			_onPullDownListener.onMore();
			return true;
		}
		return false;
	}

	@Override
	public boolean onMotionDown(MotionEvent evt) {
		_isDown = true;
		_isPullUpDone = false;
		_motionDownLastY = evt.getRawY();
		return false;
	}

	@Override
	public boolean onMotionMove(MotionEvent evt, int delta) {
		//当头部文件回推消失的时候，不允许滚动
		if(_isPullUpDone) return true;
		
		//如果开始按下到滑动距离不超过误差值，则不滑动
		final int absMotionY = (int)Math.abs(evt.getRawY() - _motionDownLastY);
		if(absMotionY < START_PULL_DEVIATION) return true;
		
		final int absDelta = Math.abs(delta);
		final int i = (int)Math.ceil((double)absDelta / 2);
		
		// onTopDown在顶部，并上回推和onTopUp相对
		if(_headerViewParams.height > 0 && delta < 0) {
			_headerIncremental -= i;
			if(_headerIncremental > 0) {
				setHeaderHeight(_headerIncremental);
				checkHeaderViewState();
			} else {
				headerViewState = HEADER_VIEW_STATE_IDLE;
				_headerIncremental = 0;
				setHeaderHeight(_headerIncremental);
				_isPullUpDone = true;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onMotionUp(MotionEvent evt) {
		_isDown = false;
		//避免和点击事件冲突
		if(_headerViewParams.height > 0) {
			//判断头文件拉动的距离与设定的高度，小了就隐藏，多了就固定高度
			int x = _headerIncremental - DEFAULT_HEADER_VIEW_HEIGHT;
			Timer timer = new Timer(true);
			if(x < 0) {
				timer.scheduleAtFixedRate(new HideHeaderViewTask(), 0, 10);
			} else {
				timer.scheduleAtFixedRate(new ShowHeaderViewTask(), 0, 10);
			}
			return true;
		}
		return false;
	}

}
