package com.auto.client.common.control.imageslider;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;

public class ImageSlider extends SherlockFragment {
	//����ͼƬ�ļ���
	private ArrayList<View> _imagePageViews = new ArrayList<View>();
	private ViewGroup _main;
	private ViewPager _viewPager; 
	//��ǰViewPager������
	private int _pageIndex = 0;
	
	//����Բ��ͼƬ��View
	private ViewGroup _imageCircleView;
	private ImageView[] _imageCircleViews;
	
	//��������
	private TextView _tvSlideTitle;
	//����������
	private SlideImageLayout _slideLayout;
	//���ݽ�����
	private NewsXmlParser _parser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		_main = (ViewGroup)inflater.inflate(R.layout.control_image_slide, container, false);	
		_viewPager = (ViewPager)_main.findViewById(R.id.control_image_slide_page);
		
		//Բ��ͼƬ����
		_parser = new NewsXmlParser();
		int length = _parser.getSlideImages().length;
		_imageCircleViews = new ImageView[length];
		_imageCircleView = (ViewGroup)_main.findViewById(R.id.control_layout_circle_images);
		_slideLayout = new SlideImageLayout(ImageSlider.this);
		_slideLayout.setCircleImageLayout(length);
		
		for(int i = 0; i < length; i++) {
			_imagePageViews.add(_slideLayout.getSlideImageLayout(_parser.getSlideImages()[i]));
			_imageCircleViews[i] = _slideLayout.getCircleImageLayout(i);
			_imageCircleView.addView(_slideLayout.getLinearLayout(_imageCircleViews[i], 10, 10));
		}
		
		//����Ĭ�ϵĻ�������
		_tvSlideTitle = (TextView)_main.findViewById(R.id.control_tvSlideTitle);
		_tvSlideTitle.setText(_parser.getSlideTitles()[0]);
		
		_viewPager.setAdapter(new SlideImageAdapter());
		_viewPager.setOnPageChangeListener(new ImagePageChangeListener());
		return _main;
	}
	
	// ����ͼƬ����������
    private class SlideImageAdapter extends PagerAdapter {  
        @Override  
        public int getCount() { 
            return _imagePageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(_imagePageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
        	((ViewPager) arg0).addView(_imagePageViews.get(arg1));
            
            return _imagePageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    }
    
    // ����ҳ������¼�������
    private class ImagePageChangeListener implements OnPageChangeListener {
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
           
        }  
  
        @Override  
        public void onPageSelected(int index) {  
        	_pageIndex = index;
        	_slideLayout.setPageIndex(index);
        	_tvSlideTitle.setText(_parser.getSlideTitles()[index]);
        	
            for (int i = 0; i < _imageCircleViews.length; i++) {  
            	_imageCircleViews[index].setBackgroundResource(R.drawable.dot_selected);
                
                if (index != i) {  
                	_imageCircleViews[i].setBackgroundResource(R.drawable.dot_none);  
                }  
            }
        }  
    }
}
