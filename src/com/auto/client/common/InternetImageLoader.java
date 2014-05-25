package com.auto.client.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.auto.client.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

public class InternetImageLoader {
	private static final int ThreadCount = 5;
	private static final int CONNECT_TIMEOUT = 10000;
	private static final int READ_TIMEOUT = 10000;

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;
	
	public InternetImageLoader(Context context) {
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(ThreadCount);
	}
	
	//should be R.drawable.no_iamge
	final int stub_id = R.drawable.ic_action_refresh;
	
	public void DisplayImage(String url, ImageView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if(bitmap != null)
			imageView.setImageBitmap(bitmap);
		else {
			queuePhoto(url, imageView);
			imageView.setImageResource(stub_id);
		}
	}
	
	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad ptl = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(ptl));
	}
	
	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);
		
		//from sd card
		Bitmap b = decodeFile(f);
		if(b != null)
			return b;
		
		//from internet
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setReadTimeout(READ_TIMEOUT);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			bitmap = decodeFile(f);
			return bitmap;
		} catch(SocketTimeoutException e) {
			e.printStackTrace();
			return null;
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	//decode file to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			//decode the image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			
			//找到正确的刻度值，它应该是2的幂
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth;
			int height_tmp = o.outHeight;
			int scale = 1;
			
			while(true) {
				if(width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch(FileNotFoundException e) {}
		return null;		
	}
	
	//任务队列
	class PhotoToLoad {
		public String url;
		public ImageView imageView;
		public PhotoToLoad(String u, ImageView v) {
			this.url = u;
			this.imageView = v;
		}
	}
	
	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;
		
		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}
		
		@Override
		public void run() {
			if(imageViewReused(photoToLoad)) return;
			Activity activity = (Activity)photoToLoad.imageView.getContext();
			Bitmap bmp = getBitmap(photoToLoad.url);
			if(bmp == null) {
				Toast.makeText(activity, "网络连接超时或网络错误", Toast.LENGTH_SHORT).show();
				return;
			}
			memoryCache.put(photoToLoad.url, bmp);
			if(imageViewReused(photoToLoad)) return;
			
			BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
			activity.runOnUiThread(bd);
		}
	}
	
	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if(tag == null || !tag.equals(photoToLoad.url)) return true;
		return false;
	}
	
	//用于显示位图的UI线程
	class BitmapDisplayer implements Runnable {

		Bitmap bitmap;
		PhotoToLoad photoToLoad;
		
		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			this.bitmap = b;
			this.photoToLoad = p;
		}
		
		@Override
		public void run() {
			if(imageViewReused(photoToLoad)) return;
			if(bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}
	
	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}
	
}
