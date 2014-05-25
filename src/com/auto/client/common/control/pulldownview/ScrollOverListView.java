package com.auto.client.common.control.pulldownview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 一个可以监听ListView是否滚动到最顶部或最底部的自定义控件，
 * 只能监听由触摸产生的，如果是ListView本身Flying导致的，则不能监听，
 * 如果加以改进，可以实现监听scroll滚动的具体位置等
 * @author zhanghuan
 *
 */
public class ScrollOverListView extends ListView {

	private int _lastY;
	private int _topPosition;
	private int _bottomPosition;
	
	public ScrollOverListView(Context context) {
		super(context);
		init();
	}
	
	public ScrollOverListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public ScrollOverListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		_topPosition = 0;
		_bottomPosition = 0;
	}

	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		final int action = evt.getAction();
		final int y = (int) evt.getRawY();
		
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			_lastY = y;
			final boolean isHandled = onScrollOverListener.onMotionDown(evt);
			if(isHandled) {
				_lastY = y;
				return isHandled;
			}
			break;
		
		case MotionEvent.ACTION_MOVE:
			final int childCount = getChildCount();
			if(childCount == 0)
				return super.onTouchEvent(evt);
			final int itemCount = getAdapter().getCount() - _bottomPosition;
			final int deltaY = y - _lastY;
			final int firstTop = getChildAt(0).getTop();
			final int listPadding = getListPaddingTop();
			final int lastBottom = getChildAt(childCount - 1).getBottom();
			final int end = getHeight() - getPaddingBottom();
			final int firstVisiblePosition = getFirstVisiblePosition();
			final boolean isHandleMotionMove = onScrollOverListener.onMotionMove(evt, deltaY);
			
			if(isHandleMotionMove) {
				_lastY = y;
				return true;
			}
			
			if(firstVisiblePosition <= _topPosition && firstTop >= listPadding && deltaY > 0) {
				if(onScrollOverListener.onListViewTopAndPullDown(deltaY)) {
					_lastY = y;
					return true;
				}
			}
			
			if(firstVisiblePosition + childCount >= itemCount && lastBottom <= end && deltaY < 0) {
				if(onScrollOverListener.onListViewBottomAndPullUp(deltaY)) {
					_lastY = y;
					return true;
				}
			}
			break;
			
		case MotionEvent.ACTION_UP:
			if(onScrollOverListener.onMotionUp(evt)) {
				_lastY = y;
				return true;
			}
			break;
		}
		
		_lastY = y;
		return super.onTouchEvent(evt);
	}
	
	private OnScrollOverListener onScrollOverListener = new OnScrollOverListener() {

		@Override
		public boolean onListViewTopAndPullDown(int delta) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onListViewBottomAndPullUp(int delta) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onMotionDown(MotionEvent evt) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onMotionMove(MotionEvent evt, int delta) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onMotionUp(MotionEvent evt) {
			// TODO Auto-generated method stub
			return false;
		}
		
	};
	
	/**
	 * 可以自定义其中一个条目为头部，头部触发的事件将以这个为准，默认为第一个
	 * @param index 正数第几个，必须在条目数范围之内
	 */
	public void setTopPosition(int index) {
		if(getAdapter() == null) 
			throw new NullPointerException("You must set adapter before setTopPosition");
		if(index < 0) 
			throw new IllegalArgumentException("Top position must > 0");
		_topPosition = index;
	}
	
	/**
	 * 可以自定义其中一个条目为尾部，尾部触发的事件将以这个为准，默认为最后一个
	 * @param index
	 */
	public void setBottomPosition(int index) {
		if(getAdapter() == null) 
			throw new NullPointerException("You must set adapter before setBottomPosition");
		if(index < 0) 
			throw new IllegalArgumentException("Bottom position must > 0");
		_bottomPosition = index;
	}
	
	/**
	 * 设置这个listener可以监听是否到达顶端，或者是否可以到达底端等事件
	 * @param onScrollOverListener
	 */
	public void setOnScrollOverListener(OnScrollOverListener onScrollOverListener) {
		this.onScrollOverListener = onScrollOverListener;
	}
	
	public interface OnScrollOverListener {
		
		/**
		 * 到达最顶部触发
		 * @param delta 手指点击移动产生的偏移量
		 * @return
		 */
		boolean onListViewTopAndPullDown(int delta);
		
		/**
		 * 到达最底部触发
		 * @param delta 手指点击移动产生的偏移量
		 * @return
		 */
		boolean onListViewBottomAndPullUp(int delta);
		
		/**
		 * 手指触摸按下触发，相当于{@link MotionEvent#ACTION_DOWN}
		 * @param evt
		 * @return 返回true表示自己处理
		 */
		boolean onMotionDown(MotionEvent evt);
		
		/**
		 * 手指触摸移动触发，相当于{@link MotionEvent#ACTION_MOVE}
		 * @param evt
		 * @param delta
		 * @return 返回true表示自己处理
		 */
		boolean onMotionMove(MotionEvent evt, int delta);
		
		/**
		 * 手指触摸后提起触发，相当于{@link MotionEvent#ACTION_UP}
		 * @param evt
		 * @return 返回true表示自己处理
		 */
		boolean onMotionUp(MotionEvent evt);
	}
}
