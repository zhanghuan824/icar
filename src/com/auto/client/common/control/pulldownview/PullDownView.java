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
	
	public static final int START_PULL_DEVIATION = 50; 	//�ƶ����
	public static final int AUTO_INCREMENTAL = 10;		//�����������ڻص�
	
	public static final int WHAT_DID_LOAD_DATA = 1;		//Handler what ���ݼ������
	public static final int WHAT_ON_REFRESH = 2;		//Handler what ˢ����
	public static final int WHAT_DID_REFRESH = 3;		//Handler what �Ѿ�ˢ�����
	public static final int WHAT_SET_HEADER_HEIGHT = 4; //Handler what ���ø߶�
	public static final int WHAT_DID_MORE = 5;			//Handler what �Ѿ���ȡ�����
	public static final int WHAT_DID_LOAD_FAIL = 6;		//ˢ��ʧ�ܣ��������Ӵ����ʱ
	public static final int WHAT_DID_MORE_FAIL = 7;		//���ظ���ʧ�ܣ��������Ӵ�����߳�ʱ
	
	public static final int DEFAULT_HEADER_VIEW_HEIGHT = 105; //ͷ���ļ�ԭ���ĸ߶�
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
	
	private int _headerIncremental; 			//����
	private float _motionDownLastY; 			//����ʱ���Y������
	
	private boolean _isDown;					//�Ƿ���
	private boolean _isRefreshing;			//�Ƿ�����ˢ����
	private boolean _isFetchMoreing;			//�Ƿ��ȡ������
	private boolean _isPullUpDone;			//�Ƿ�������
	private boolean _enableAutoFetchMore;	//�Ƿ������Զ���ȡ����
	
	//ͷ���ļ���״̬
	private static final int HEADER_VIEW_STATE_IDLE = 0;			//����
	private static final int HEADER_VIEW_STATE_NOT_OVER_HEIGHT = 1; //û�г���Ĭ�ϸ߶�
	private static final int HEADER_VIEW_STATE_OVER_HEIGHT = 2;		//����Ĭ�ϸ߶�
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
	 * ˢ���¼��ӿ�
	 * @author zhanghuan
	 *
	 */
	public interface OnPullDownListener {
		void onRefresh();
		void onMore();
	}
	
	/**
	 * ֪ͨ�����������ݣ�Ҫ����Adapter.notifyDataSetChanged����
	 * ������������ݵ�ʱ�򣬵������notifyDidLoad()
	 * �Ż�����ͷ��������ʼ�����ݵ�
	 */
	public void notifyDidLoad() {
		uiHandler.sendEmptyMessage(WHAT_DID_LOAD_DATA);
	}
	
	/**
	 * ֪ͨ�Ѿ�ˢ�����ˣ�Ҫ����Adapter.notifyDataSetChanged����
	 * ����ִ����ˢ��֮�󣬵������notifyDidRefresh()
	 * �Ż����ص�ͷ���ļ��Ȳ���
	 */
	public void notifyDidRefresh() {
		uiHandler.sendEmptyMessage(WHAT_DID_REFRESH);
	}
	
	/**
	 * ֪ͨ�Ѿ���ȡ������ˣ�Ҫ����Adapter.notifyDataSetChanged����
	 * ����ִ�����������֮�󣬵������notifyDidMore()���Ż���ʾ���ؼ���Ȧ�Ȳ���
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
	 * ���ü�����
	 * @param listener
	 */
	public void setOnPullDownListener(OnPullDownListener listener) {
		_onPullDownListener = listener;
	}
	
	/**
	 * ��ȡ��Ƕ��listView
	 * @return
	 */
	public ListView getListView() {
		return _listView;
	}
	
	/**
	 * �Ƿ����Զ���ȡ����
	 * �Զ���ȡ���ཫ������footer�����ڵ���ײ���ʱ���Զ�ˢ��
	 * @param enable
	 * @param index �����ڼ�������
	 */
	public void enableAutoFetchMore(boolean enable, int index) {
		if(enable) {
			_listView.setBottomPosition(index);
			_footerLoadingView.setVisibility(View.VISIBLE);
		} else {
			_footerTextView.setText("����");
			_footerLoadingView.setVisibility(View.GONE);
		}
		_enableAutoFetchMore = enable;
	}
	
	//=========== ����ʵ������ˢ�²��� ================
	
	/**
	 * ��ʼ������
	 * @param context
	 */
	private void initHeaderViewAndFooterViewAndListView(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		//�Զ���ͷ���ļ���������������Ϊ���ǵ��ܶ���涼��Ҫʹ��
		//���Ҫ�޸ģ�������ص����ö���Ҫ�޸�
		_headerView = LayoutInflater.from(context).inflate(R.layout.pulldown_header, null);
		_headerViewParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		_headerViewParams.height = 70;
		addView(_headerView, 0, _headerViewParams);
		
		_headerTextView = (TextView) _headerView.findViewById(R.id.pulldown_header_text);
		_headerArrowView = (ImageView) _headerView.findViewById(R.id.pulldown_header_arrow);
		_headerLoadingView = _headerView.findViewById(R.id.pulldown_header_loading);
		
		//ע��ͼƬ��ת�Ժ���ִ����ת����������¿�ʼ����
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
		
		//�Զ���Ų��ļ�
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
		
		//ScrollOverListViewͬ���ǿ��ǵ�����ʹ�ã����Է�������
		//ͬʱ��Ϊ��Ҫ���ļ����¼�
		_listView = new ScrollOverListView(context);
		_listView.setOnScrollOverListener(this);
		_listView.setCacheColorHint(0);
		addView(_listView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		//�յ�listener
		_onPullDownListener = new OnPullDownListener() {
			@Override
			public void onRefresh() {}
			@Override
			public void onMore() {}
		};
	}
	
	/**
	 * �������ͻ��Ƶ�ʱ����ͷ���ļ���״̬
	 * ���������Ĭ�ϸ߶ȣ�����ʾ�ɿ�����ˢ��
	 * ������ʾ��������ˢ��
	 */
	private void checkHeaderViewState() {
		if(_headerViewParams.height >= DEFAULT_HEADER_VIEW_HEIGHT) {
			if(headerViewState == HEADER_VIEW_STATE_OVER_HEIGHT) return;
			headerViewState = HEADER_VIEW_STATE_OVER_HEIGHT;
			_headerTextView.setText("�ɿ�����ˢ��");
			_headerArrowView.startAnimation(_rotate0To180Animation);
		} else {
			if(headerViewState == HEADER_VIEW_STATE_NOT_OVER_HEIGHT 
					|| headerViewState == HEADER_VIEW_STATE_IDLE) 
				return;
			headerViewState = HEADER_VIEW_STATE_NOT_OVER_HEIGHT;
			_headerTextView.setText("��������ˢ��");
			_headerArrowView.startAnimation(_rotate180To0Animation);
		}
	}
	
	private void setHeaderHeight(final int height) {
		_headerIncremental = height;
		_headerViewParams.height = height;
		_headerView.setLayoutParams(_headerViewParams);
	}
	
	/**
	 * �Զ����ض���
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
	 * �Զ���ʾ����
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
				_headerTextView.setText("��������ˢ��");
				_headerViewDateView = (TextView) _headerView.findViewById(R.id.pulldown_header_date);
				_headerViewDateView.setVisibility(View.VISIBLE);
				_headerViewDateView.setText("������" + _dateFormat.format(new Date(System.currentTimeMillis())));
				_headerArrowView.setVisibility(View.VISIBLE);
				showFooterView();
				break;
				
			case WHAT_ON_REFRESH:
				//Ҫ����������������޷�����
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
				_headerViewDateView.setText("������" + _dateFormat.format(new Date(System.currentTimeMillis())));
				setHeaderHeight(0);
				showFooterView();
				break;
				
			case WHAT_SET_HEADER_HEIGHT:
				setHeaderHeight(_headerIncremental);
				break;
				
			case WHAT_DID_MORE:
				_isFetchMoreing = false;
				_footerTextView.setText("����");
				_footerLoadingView.setVisibility(View.GONE);
				break;
			
			case WHAT_DID_LOAD_FAIL:
				_isRefreshing = false;
				_headerViewParams.height = 0;
				_headerLoadingView.setVisibility(View.GONE);
				_headerTextView.setText("��������ˢ��");
				_headerViewDateView = (TextView) _headerView.findViewById(R.id.pulldown_header_date);
				_headerViewDateView.setVisibility(View.VISIBLE);
				_headerViewDateView.setText("������" + _dateFormat.format(new Date(System.currentTimeMillis())));
				_headerArrowView.setVisibility(View.VISIBLE);
				showFooterView();
				Toast.makeText(getContext(), "����ʧ�ܣ�������������...", Toast.LENGTH_LONG).show();
				break;
				
			case WHAT_DID_MORE_FAIL:
				_isFetchMoreing = false;
				_footerTextView.setText("����");
				_footerLoadingView.setVisibility(View.GONE);
				Toast.makeText(getContext(), "����ʧ�ܣ�������������...", Toast.LENGTH_LONG).show();
				break;
			}
		}
	};
	
	/**
	 * ��ʾ�ײ��ļ�
	 */
	private void showFooterView() {
		if(_listView.getFooterViewsCount() == 0 && isFillScreenItem()) {
			_listView.addFooterView(_footerView);
			_listView.setAdapter(_listView.getAdapter());
		}
	}
	
	/**
	 * ��Ŀ�Ƿ�����������Ļ
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
	
	
	//========= ʵ��OnScrollOverListener�ӿ� =======
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
		//����������Ļ�Ŵ���
		if(_enableAutoFetchMore && isFillScreenItem()) {
			_isFetchMoreing = true;
			_footerTextView.setText("���ظ���...");
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
		//��ͷ���ļ�������ʧ��ʱ�򣬲��������
		if(_isPullUpDone) return true;
		
		//�����ʼ���µ��������벻�������ֵ���򲻻���
		final int absMotionY = (int)Math.abs(evt.getRawY() - _motionDownLastY);
		if(absMotionY < START_PULL_DEVIATION) return true;
		
		final int absDelta = Math.abs(delta);
		final int i = (int)Math.ceil((double)absDelta / 2);
		
		// onTopDown�ڶ��������ϻ��ƺ�onTopUp���
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
		//����͵���¼���ͻ
		if(_headerViewParams.height > 0) {
			//�ж�ͷ�ļ������ľ������趨�ĸ߶ȣ�С�˾����أ����˾͹̶��߶�
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
