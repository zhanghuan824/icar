package com.auto.client.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.auto.client.common.DateTool;
import com.auto.client.domain.dynamic.News;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsDAO {

	private DBHelper helper;
	
	public NewsDAO(DBHelper dbHelper) {
		helper = dbHelper;
	}
	
	@SuppressLint("SimpleDateFormat")
	public synchronized void add(List<News> news) {
		SQLiteDatabase db = null;
		try {
			NewsSubItemDAO nsiDao = new NewsSubItemDAO(helper);
			db = helper.getWritableDatabase();
			db.beginTransaction();
			for(News inews : news) {
				db.execSQL("insert into dynamic_news (id,url,title,`desc`,image,update_time) " +
						"values (?,?,?,?,?,?)", 
					new Object[] {
					String.valueOf(inews.getId()),
					inews.getUrl(),
					inews.getTitle(),
					inews.getDesc(),
					inews.getImage(),
					DateTool.format(inews.getUpdateTime(), "yyyy-MM-dd HH:mm:ss.SSS")
				});
				
				nsiDao.addWithTransaction(inews.getSubItems(), db);
			}
			db.setTransactionSuccessful();
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			if(db != null) {
				db.endTransaction();
			}
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public synchronized List<News> query(int fromIndex, int count) {
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			NewsSubItemDAO nsiDao = new NewsSubItemDAO(helper);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			List<News> newsList = new ArrayList<News>();
			db = helper.getReadableDatabase();
			cursor = db.rawQuery("select id,url,title,`desc`,image,update_time from dynamic_news order by update_time desc limit ? offset ? ", 
					new String[] { String.valueOf(count), String.valueOf(fromIndex) });
			while(cursor.moveToNext()) {
				News news = new News();
				long id = Long.parseLong(cursor.getString(0));
				news.setId(id);
				news.setUrl(cursor.getString(1));
				news.setTitle(cursor.getString(2));
				news.setDesc(cursor.getString(3));
				news.setImage(cursor.getString(4));
				news.setUpdateTime(sdf.parse(cursor.getString(5)));
				news.setSubItems(nsiDao.queryByNewsId(id, db));
				newsList.add(news);
			}
			return newsList;
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(cursor != null) cursor.close();
		}
	}
}
