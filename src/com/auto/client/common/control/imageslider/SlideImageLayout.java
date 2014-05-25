package com.auto.client.common.control.imageslider;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.auto.client.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class SlideImageLayout {
	// ����ͼƬ��ArrayList
	private ArrayList<ImageView> _imageList;
	private SherlockFragment _fragment;
	// Բ��ͼƬ����
	private ImageView[] _imageViews; 
	private ImageView _imageView;
	private NewsXmlParser _parser;
	// ��ʾ��ǰ����ͼƬ������
	private int _pageIndex = 0;
	
	public SlideImageLayout(SherlockFragment fragment) {
		_fragment = fragment;
		_imageList = new ArrayList<ImageView>();
		_parser = new NewsXmlParser();
	}
	
	/**
	 * ���ɻ���ͼƬ���򲼾�
	 * @param index
	 * @return
	 */
	public View getSlideImageLayout(int index){
		Context context = _fragment.getActivity();
		// ����TextView��LinearLayout
		LinearLayout imageLinerLayout = new LinearLayout(context);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT,
				1);
		
		ImageView iv = new ImageView(context);
		iv.setBackgroundResource(index);
		iv.setOnClickListener(new ImageOnClickListener());
		imageLinerLayout.addView(iv,imageLinerLayoutParames);
		_imageList.add(iv);
		
		return imageLinerLayout;
	}
	
	/**
	 * ��ȡLinearLayout
	 * @param view
	 * @param width
	 * @param height
	 * @return
	 */
	public View getLinearLayout(View view,int width,int height){
		LinearLayout linerLayout = new LinearLayout(_fragment.getActivity());
		LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(
				width, 
				height,
				1);
		// �������Ҳ�Զ������ã�����Ȥ���Լ����á�
		linerLayout.setPadding(10, 0, 10, 0);
		linerLayout.addView(view, linerLayoutParames);
		
		return linerLayout;
	}
	
	/**
	 * ����Բ�����
	 * @param size
	 */
	public void setCircleImageLayout(int size){
		_imageViews = new ImageView[size];
	}
	
	/**
	 * ����Բ��ͼƬ���򲼾ֶ���
	 * @param index
	 * @return
	 */
	public ImageView getCircleImageLayout(int index){
		_imageView = new ImageView(_fragment.getActivity());  
		_imageView.setLayoutParams(new LayoutParams(10,10));
        _imageView.setScaleType(ScaleType.FIT_XY);
        
        _imageViews[index] = _imageView;
         
        if (index == 0) {  
            //Ĭ��ѡ�е�һ��ͼƬ
            _imageViews[index].setBackgroundResource(R.drawable.dot_selected);  
        } else {  
            _imageViews[index].setBackgroundResource(R.drawable.dot_none);  
        }  
         
        return _imageViews[index];
	}
	
	/**
	 * ���õ�ǰ����ͼƬ������
	 * @param index
	 */
	public void setPageIndex(int index){
		_pageIndex = index;
	}
	
	public int getPageIndex() {
		return _pageIndex;
	}

	// ����ҳ�����¼�������
    private class ImageOnClickListener implements OnClickListener{
    	@Override
    	public void onClick(View v) {
    		//Toast.makeText(_fragment.getActivity(), _parser.getSlideTitles()[pageIndex], Toast.LENGTH_SHORT).show();
    		//Toast.makeText(_fragment.getActivity(), _parser.getSlideUrls()[pageIndex], Toast.LENGTH_SHORT).show();
    	}
    }
}
