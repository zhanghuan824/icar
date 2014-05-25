package com.auto.client.common;

import java.io.File;
import android.content.Context;
import android.os.Environment;

public class FileCache {

	private File cacheDir;
	
	public FileCache(Context context) {
		//find a path to cache the image
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			cacheDir = new File(Environment.getExternalStorageDirectory(), "icarList");
		else
			cacheDir = context.getCacheDir();
		if(!cacheDir.exists())
			cacheDir.mkdirs();
	}
	
	public File getFile(String url) {
		String filename = String.valueOf(url.hashCode());
		File file = new File(cacheDir, filename);
		return file;
	}
	
	public void clear() {
		File[] files = cacheDir.listFiles();
		if(files == null) return;
		for(File file : files)
			file.delete();
	}
}
