package com.auto.client.common.control.pulldownview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * һ�����Լ���ListView�Ƿ�������������ײ����Զ���ؼ���
 * ֻ�ܼ����ɴ��������ģ������ListView����Flying���µģ����ܼ�����
 * ������ԸĽ�������ʵ�ּ���scroll�����ľ���λ�õ�
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
	 * �����Զ�������һ����ĿΪͷ����ͷ���������¼��������Ϊ׼��Ĭ��Ϊ��һ��
	 * @param index �����ڼ�������������Ŀ����Χ֮��
	 */
	public void setTopPosition(int index) {
		if(getAdapter() == null) 
			throw new NullPointerException("You must set adapter before setTopPosition");
		if(index < 0) 
			throw new IllegalArgumentException("Top position must > 0");
		_topPosition = index;
	}
	
	/**
	 * �����Զ�������һ����ĿΪβ����β���������¼��������Ϊ׼��Ĭ��Ϊ���һ��
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
	 * �������listener���Լ����Ƿ񵽴ﶥ�ˣ������Ƿ���Ե���׶˵��¼�
	 * @param onScrollOverListener
	 */
	public void setOnScrollOverListener(OnScrollOverListener onScrollOverListener) {
		this.onScrollOverListener = onScrollOverListener;
	}
	
	public interface OnScrollOverListener {
		
		/**
		 * �����������
		 * @param delta ��ָ����ƶ�������ƫ����
		 * @return
		 */
		boolean onListViewTopAndPullDown(int delta);
		
		/**
		 * ������ײ�����
		 * @param delta ��ָ����ƶ�������ƫ����
		 * @return
		 */
		boolean onListViewBottomAndPullUp(int delta);
		
		/**
		 * ��ָ�������´������൱��{@link MotionEvent#ACTION_DOWN}
		 * @param evt
		 * @return ����true��ʾ�Լ�����
		 */
		boolean onMotionDown(MotionEvent evt);
		
		/**
		 * ��ָ�����ƶ��������൱��{@link MotionEvent#ACTION_MOVE}
		 * @param evt
		 * @param delta
		 * @return ����true��ʾ�Լ�����
		 */
		boolean onMotionMove(MotionEvent evt, int delta);
		
		/**
		 * ��ָ���������𴥷����൱��{@link MotionEvent#ACTION_UP}
		 * @param evt
		 * @return ����true��ʾ�Լ�����
		 */
		boolean onMotionUp(MotionEvent evt);
	}
}
